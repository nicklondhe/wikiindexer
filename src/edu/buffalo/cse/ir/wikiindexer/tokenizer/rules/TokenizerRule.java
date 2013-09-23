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
		PUNCTUATION { public String toString() {return "PUNCTUATION";}}, //for punctuation marks that could mark end of sentence {. ? ! }
		APOSTROPHE { public String toString() {return "APOSTROPHE";}}, //for apostrophes only
		HYPHEN { public String toString() {return "HYPHEN";}}, //for hyphens only
		SPECIALCHARS { public String toString() {return "SPECIALCHARS";}}, //for anything non alphanumeric not covered by above
		DATES { public String toString() {return "DATES";}}, //for dates
		NUMBERS { public String toString() {return "NUMBERS";}}, //for numbers
		CAPITALIZATION { public String toString() {return "CAPITALIZATION";}}, //for capitalization
		ACCENTS { public String toString() {return "ACCENTS";}}, //acccents and diacritics
		WHITESPACE { public String toString() {return "WHITESPACE";}}, //split by whitespace
		DELIM { public String toString() {return "DELIM";}}, //split by any delim *will not be tested*
		STEMMER { public String toString() {return "STEMMER";}}, //stemmer
		STOPWORDS { public String toString() {return "STOPWORDS";}} //stop words
	};
}
