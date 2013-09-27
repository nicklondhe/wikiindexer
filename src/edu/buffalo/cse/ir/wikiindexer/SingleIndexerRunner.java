/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Properties;

import edu.buffalo.cse.ir.wikiindexer.indexer.INDEXFIELD;
import edu.buffalo.cse.ir.wikiindexer.indexer.IndexWriter;
import edu.buffalo.cse.ir.wikiindexer.indexer.IndexerException;
import edu.buffalo.cse.ir.wikiindexer.indexer.SharedDictionary;

/**
 * @author nikhillo
 * 
 */
public class SingleIndexerRunner {
	private ConcurrentLinkedQueue<Object[]> pvtQueue;
	private IndexWriter idxWriter;
	private SharedDictionary docDict;
	private boolean lookupBoth;
	private RunnerThread thr;
	
	/**
	 * 
	 * @param props
	 * @param keyfield
	 * @param valueField
	 * @param dict
	 */
	protected SingleIndexerRunner(Properties props, INDEXFIELD keyfield,
			INDEXFIELD valueField, SharedDictionary dict, boolean isFwd) {
		idxWriter = new IndexWriter(props, keyfield, valueField, isFwd);
		docDict = dict;
		lookupBoth = (keyfield == INDEXFIELD.LINK);
		pvtQueue = new ConcurrentLinkedQueue<Object[]>();
		thr = new RunnerThread();
		
	}
	
	/**
	 * 
	 * @param docid
	 * @param map
	 * @throws IndexerException
	 */
	protected void processTokenMap(int docid, Map<String, Integer> map) throws IndexerException {
		String key;
		int value;
		Object[] arrObj;
		for (Entry<String, Integer> etr : map.entrySet()) {
			key = etr.getKey();
			value = etr.getValue();
			
			if (key != null) {
				arrObj = new Object[3];
				
				if (lookupBoth) {
					arrObj[0] = docid;
					arrObj[1] = docDict.lookup(key);
				} else {
					arrObj[0] = key;
					arrObj[1] = docid;
				}
				
				arrObj[2] = value;
				pvtQueue.add(arrObj);
				
				if (!thr.isRunning) {
					thr.isRunning = true;
					new Thread(thr).start();
				}
			}
		}
	}
	
	protected boolean isFinished() {
		return thr.isComplete && thr.isQueueEmpty();
	}
	
	/**
	 * 
	 * @throws IndexerException
	 */
	protected void cleanup() throws IndexerException {
		thr.setComplete();
	}
	
	/**
	 * 
	 * @author nikhillo
	 *
	 */
	private class RunnerThread implements Runnable {
		private boolean isComplete;
		private boolean isRunning;
		
		/**
		 * 
		 */
		private RunnerThread() {
			
		}
		
		/**
		 * 
		 */
		private void setComplete() {
			isComplete = true;
		}
		
		private boolean isQueueEmpty() {
			synchronized (pvtQueue) {
				return pvtQueue.isEmpty();
			}
		}
		
		/**
		 * 
		 */
		public void run() {
			Object[] etr;
			while (true) {
				etr = pvtQueue.poll();

				if (etr == null) {
					if (isComplete) {
						try {
							idxWriter.writeToDisk();
						} catch (IndexerException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						idxWriter.cleanUp();
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
						if (lookupBoth) {
							idxWriter.addToIndex((Integer) etr[0], (Integer) etr[1], (Integer) etr[2]);
						} else {
							idxWriter.addToIndex((String) etr[0], (Integer) etr[1], (Integer) etr[2]);
						}
					} catch (IndexerException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
}
