/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.tokenizer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule.RULENAMES;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.RuleClass;

/**
 * This is the main Tokenizer class. It simply calls one TokenizerRule after another
 * All operations can be assumed to be thread safe.
 * @author nikhillo
 * 
 */
public class Tokenizer {
	/* The set of all valid rule names, simply the RULENAMES enum as a set */
	private static Set<RULENAMES> validRules;
	
	/* The ordered rule collection to be applied */
	private final Collection<TokenizerRule> tRules;
	
	/**
	 * Default constructor
	 * @param rules: The ordered collection of rules to be applied
	 * Each rule instance is expected to eb annotated with a valid rule name
	 * @throws TokenizerException: If there is any error in instantiating the tokenizer
	 */
	public Tokenizer(TokenizerRule... rules) throws TokenizerException {
		//init the set if not already done
		if (validRules == null) {
			initRuleSet();
		}
		
		//if some rules are given
		if (rules != null) {
			tRules = new ArrayList<TokenizerRule>(rules.length); // TODO: Change this if you like
																	
			for (TokenizerRule tr : rules) {
				if (annotationPresent(tr)) { //validate annotation
					tRules.add(tr);
				} else {
					throw new TokenizerException(
							"The class "
									+ tr.getClass().getSimpleName()
									+ " either does not have the RuleClass annotation or has an invalid className specified");
				}
			}
		} else {
			tRules = null;
			throw new TokenizerException(
					"Error in initializing Tokenizer: No rules have been defined");
		}
	}
	
	/**
	 * Main method used to tokenize. IT simply calls the rules one by one
	 * @param stream: The TokenStream to be worked upon
	 * @throws TokenizerException: If any tokenization exception occurs
	 */
	public void tokenize(TokenStream stream) throws TokenizerException {
		for (TokenizerRule rule : tRules) {
			rule.apply(stream);
		}
	}
	
	/**
	 * Method to initialize the static rule set
	 */
	private void initRuleSet() {
		RULENAMES[] rules = RULENAMES.values();
		validRules = new HashSet<RULENAMES>(rules.length);
		for (RULENAMES r : rules) {
			validRules.add(r);
		}
		
	}

	/**
	 * Method to validate that the passed class has the expected annotation
	 * @param tr: The class to be validated
	 * @return: True if the annotation is present and valid, false otherwise
	 */
	private boolean annotationPresent(TokenizerRule tr) {
		if (tr != null) {
			Class<?> tokClass = tr.getClass();
			return (tokClass.isAnnotationPresent(RuleClass.class));
			
		}
		
		return false;
	}
}
