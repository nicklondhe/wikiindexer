/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.indexer;

/**
 * @author nikhillo
 * This is a marker interface that defines methods that all disk writeable classes
 * should implement.
 */
public interface Writeable {
	/**
	 * Method to flush the current contents of the object to the disk
	 * The class constructor etc. should take of initializing corresponding
	 * writers to the disk etc.
	 * This method will be called at most once externally, at the end of all
	 * processing. The expectation is for the class to internally call this as needed
	 * @throws IndexerException: Any exception raised while indexing
	 */
	public abstract void writeToDisk() throws IndexerException;
	
	/**
	 * This method would be called to indicate completion of all indexing
	 * The implementing classes should do all cleanup and post-processing
	 * when this method is called.
	 */
	public abstract void cleanUp();
}
