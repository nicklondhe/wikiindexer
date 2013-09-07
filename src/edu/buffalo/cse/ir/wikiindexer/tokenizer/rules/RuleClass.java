/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.tokenizer.rules;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule.RULENAMES;

/**
 * Simply defines the annotation
 * @author nikhillo
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RuleClass {
	public RULENAMES className();
}
