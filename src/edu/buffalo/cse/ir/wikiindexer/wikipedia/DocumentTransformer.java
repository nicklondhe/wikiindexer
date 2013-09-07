/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.wikipedia;

import java.util.Map;
import java.util.concurrent.Callable;

import edu.buffalo.cse.ir.wikiindexer.indexer.INDEXFIELD;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.Tokenizer;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;

/**
 * A Callable document transformer that converts the given WikipediaDocument object
 * into an IndexableDocument object using the given Tokenizer
 * @author nikhillo
 *
 */
public class DocumentTransformer implements Callable<IndexableDocument> {
	/**
	 * Default constructor, DO NOT change
	 * @param tknizerMap: A map mapping a fully initialized tokenizer to a given field type
	 * @param doc: The WikipediaDocument to be processed
	 */
	public DocumentTransformer(Map<INDEXFIELD, Tokenizer> tknizerMap, WikipediaDocument doc) {
		//TODO: Implement this method
	}
	
	/**
	 * Method to trigger the transformation
	 * @throws TokenizerException Inc ase any tokenization error occurs
	 */
	public IndexableDocument call() throws TokenizerException {
		// TODO Implement this method
		return null;
	}
	
}
