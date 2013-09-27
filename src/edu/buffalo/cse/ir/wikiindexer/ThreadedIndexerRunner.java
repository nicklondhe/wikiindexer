/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer;

import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentLinkedQueue;

import edu.buffalo.cse.ir.wikiindexer.indexer.INDEXFIELD;
import edu.buffalo.cse.ir.wikiindexer.indexer.IndexWriter;
import edu.buffalo.cse.ir.wikiindexer.indexer.IndexerException;
import edu.buffalo.cse.ir.wikiindexer.indexer.Partitioner;

/**
 * @author nikhillo
 * 
 */
public class ThreadedIndexerRunner {
	private Properties props;
	private RunnerThread[] rthreads;

	protected ThreadedIndexerRunner(Properties idxProps) {
		this.props = idxProps;
		int numParts = Partitioner.getNumPartitions();
		
		if (numParts > 0) {
			rthreads = new RunnerThread[numParts];
			for (int i = 0; i < numParts; i++) {
				rthreads[i] = new RunnerThread(i);
			}
		}
	}

	protected void addToIndex(Map<String, Integer> tokenmap, int docid) {
		String term;
		int numOccur, numPart;
		TermIndexEntry tidx;
		RunnerThread currThread;

		for (Entry<String, Integer> etr : tokenmap.entrySet()) {
			term = etr.getKey();
			numOccur = etr.getValue();
			
			if (term != null && numOccur > 0) {
				numPart = Partitioner.getPartitionNumber(term);
				
				if (numPart >= 0 && numPart < rthreads.length) {
					tidx = new TermIndexEntry(term, docid, numOccur);
					currThread = rthreads[numPart];
					currThread.pvtQueue.add(tidx);
					if (!currThread.isRunning) {
						currThread.isRunning = true;
						new Thread(currThread).start();
					}
				}
			}
		}
	}

	protected void cleanup() {
		for (RunnerThread thr : rthreads) {
			thr.setComplete();
		}
	}
	
	protected boolean isFinished() {
		boolean flag = true;
		
		for (RunnerThread thr : rthreads) {
			flag &= (thr.isComplete && thr.isQueueEmpty());
		}
		
		return flag;
	}

	private class TermIndexEntry {
		private String term;
		private int docId;
		private int numOccurances;

		private TermIndexEntry(String ipTerm, int ipDocId, int ipNumOccur) {
			term = ipTerm;
			docId = ipDocId;
			numOccurances = ipNumOccur;
		}
	}

	private class RunnerThread implements Runnable {
		private ConcurrentLinkedQueue<TermIndexEntry> pvtQueue;
		private IndexWriter writer;
		private boolean isComplete;
		private boolean isRunning;

		private RunnerThread(int pnum) {
			pvtQueue = new ConcurrentLinkedQueue<ThreadedIndexerRunner.TermIndexEntry>();
			writer = new IndexWriter(props, INDEXFIELD.TERM, INDEXFIELD.LINK);
			writer.setPartitionNumber(pnum);
		}

		private void setComplete() {
			isComplete = true;
		}
		
		private boolean isQueueEmpty() {
			synchronized (pvtQueue) {
				return pvtQueue.isEmpty();
			}
		}

		public void run() {
			TermIndexEntry etr;

			while (true) {
				etr = pvtQueue.poll();

				if (etr == null) {
					if (isComplete) {
						try {
							writer.writeToDisk();
						} catch (IndexerException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						writer.cleanUp();
						break; // everything is done
					} else {
						try {
							Thread.sleep(2000); // 2 seconds -- config maybe?
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} else {
					// we have an entry
					try {
						writer.addToIndex(etr.term, etr.docId,
								etr.numOccurances);
					} catch (IndexerException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
}
