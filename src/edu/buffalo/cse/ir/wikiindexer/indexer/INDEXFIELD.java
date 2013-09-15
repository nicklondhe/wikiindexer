/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.indexer;

/**
 * This is simply an enumeration that lists the different field types
 * @author nikhillo
 *
 */
public enum INDEXFIELD {
	TERM, //The main index, includes all terms from the page text
	AUTHOR, //The author index
	CATEGORY, //The category index
	LINK //THe link index, also synonym for document dictionary
}
