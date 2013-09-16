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

		rthreads = new RunnerThread[numParts];
		for (int i = 0; i < numParts; i++) {
			rthreads[i] = new RunnerThread();
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
			numPart = Partitioner.getPartitionNumber(term);
			tidx = new TermIndexEntry(term, docid, numOccur);
			currThread = rthreads[numPart];
			currThread.pvtQueue.add(tidx);
			if (!currThread.isRunning) {
				currThread.isRunning = true;
				currThread.run();
			}
		}
	}

	protected void cleanup() {
		for (RunnerThread thr : rthreads) {
			thr.setComplete();
		}
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

		private RunnerThread() {
			pvtQueue = new ConcurrentLinkedQueue<ThreadedIndexerRunner.TermIndexEntry>();
			writer = new IndexWriter(props, INDEXFIELD.TERM, INDEXFIELD.LINK);
		}

		private void setComplete() {
			isComplete = true;
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
