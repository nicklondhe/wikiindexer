/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.tokenizer.test;

import java.util.Properties;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import edu.buffalo.cse.ir.wikiindexer.IndexerConstants;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;

/**
 * @author nikhillo
 *
 */
@RunWith(Parameterized.class)
public class SpecialCharRuleTest extends TokenizerRuleTest {

	public SpecialCharRuleTest(Properties props) {
		super(props, IndexerConstants.SPECIALCHARRULE);
	}
	
	@Test
	public void testRule() {
		if (rule == null) {
			 fail("Rule not implemented");
		} else {
			try {
				if (isPreTokenization) {
					//special symbols one by one
					assertArrayEquals(new Object[]{"destructor is method"}, 
							runtest("destructor is ~method()")); //tilda, brackets
					assertArrayEquals(new Object[]{"email is test buffalo.edu"}, 
							runtest("email is test@buffalo.edu")); //@
					assertArrayEquals(new Object[]{"call 555-5555"}, 
							runtest("call #555-5555")); //hash
					assertArrayEquals(new Object[]{"total is 5000.00"}, 
							runtest("total is $5000.00")); //dollar
					assertArrayEquals(new Object[]{"discounted at 15"}, 
							runtest("discounted at 15%")); //percentage
					assertArrayEquals(new Object[]{"x 2 x x"}, 
							runtest("x^2 = x*x")); //crows feet, asterisk and equal to
					assertArrayEquals(new Object[]{"proctor gamble"}, 
							runtest("proctor & gamble")); //&
					assertArrayEquals(new Object[]{"a b-c"}, 
							runtest("a+b-c")); //+, -
					assertArrayEquals(new Object[]{"case x continue"}, 
							runtest("case x: continue;")); //: ;
					assertArrayEquals(new Object[]{"stdin cut -f1 sort myfile"}, 
							runtest("stdin < cut -f1 | sort > myfile")); //< > |
					assertArrayEquals(new Object[]{"pray to"}, 
							runtest("pray to __/\\__"));
				} else {
					//special symbols one by one
					assertArrayEquals(new Object[]{"destructor","is","method"}, 
							runtest("destructor","is","~method()")); //tilda, brackets
					assertArrayEquals(new Object[]{"email","is","test","buffalo.edu"}, 
							runtest("email","is","test@buffalo.edu")); //@
					assertArrayEquals(new Object[]{"call","555-5555"}, 
							runtest("call","#555-5555")); //hash
					assertArrayEquals(new Object[]{"total","is","5000.00"}, 
							runtest("total","is","$5000.00")); //dollar
					assertArrayEquals(new Object[]{"discounted","at","15"}, 
							runtest("discounted","at","15%")); //percentage
					assertArrayEquals(new Object[]{"x","2","x","x"}, 
							runtest("x^2","=","x*x")); //crows feet, asterisk and equal to
					assertArrayEquals(new Object[]{"proctor","gamble"}, 
							runtest("proctor","&","gamble")); //&
					assertArrayEquals(new Object[]{"a","b","-","c"}, 
							runtest("a","+","b","-","c")); //+, -
					assertArrayEquals(new Object[]{"case","x","continue"}, 
							runtest("case","x:","continue;")); //: ;
					assertArrayEquals(new Object[]{"stdin","cut","-f1","sort","myfile"}, 
							runtest("stdin","<","cut","-f1","|", "sort", ">", "myfile")); //< > |
					assertArrayEquals(new Object[]{"pray","to"}, 
							runtest("pray","to","__/\\__"));
				}
				
				
			} catch (TokenizerException e) {
				
			}
		}
	}
}
