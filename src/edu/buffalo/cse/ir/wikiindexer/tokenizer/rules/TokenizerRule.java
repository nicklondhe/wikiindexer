/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.tokenizer.rules;

import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;

/**
 * Interface that must be implemented by any tokenizer rule class
 * @author nikhillo
 *
 */
public interface TokenizerRule {
	/**
	 * Method to trigger transformation on the stream based on the current rule
	 * @param stream: The stream to be transformed
	 * @throws TokenizerException IF any tokenization exception occurs
	 */
	public void apply (TokenStream stream) throws TokenizerException;
	
	/**
	 * The enum of pre-defined rules, you may add more rules if you like
	 * Refer the specifications for documentation on each rule
	 * @author nikhillo
	 *
	 */
	public enum RULENAMES {
		PUNCTUATION, //for punctuation marks that could mark end of sentence {. ? ! }
		APOSTROPHE, //for apostrophes only
		HYPHEN, //for hyphens only
		SPECIALCHARS, //for anything non alphanumeric not covered by above
		DATES, //for dates
		NUMBERS, //for numbers
		CAPITALIZATION, //for capitalization
		ACCENTS, //acccents and diacritics
		WHITESPACE, //split by whitespace
		DELIM, //split by any delim *will not be tested*
		STEMMER, //stemmer
		STOPWORDS //stop words
	};
}
