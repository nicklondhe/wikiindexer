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
public class ApostropheRuleTest extends TokenizerRuleTest {

	public ApostropheRuleTest(Properties props) {
		super(props, IndexerConstants.APOSTROPHERULE);
	}
	
	@Test
	public void testRule() {
		if (rule == null) {
			 fail("Rule not implemented");
		} else {
			try {
				if (isPreTokenization) {
					//basic rules
					assertArrayEquals(new Object[]{"Finland"}, runtest("Finland's"));
					assertArrayEquals(new Object[]{"Gladys house"}, runtest("Gladys' house"));
		
					//contractions
					assertArrayEquals(new Object[]{"is","not"}, runtest("isn't"));
					assertArrayEquals(new Object[]{"do","not"}, runtest("don't"));
					assertArrayEquals(new Object[]{"will","not"}, runtest("won't"));
					assertArrayEquals(new Object[]{"shall","not"}, runtest("shan't"));
					assertArrayEquals(new Object[]{"let","us"}, runtest("let's"));
					assertArrayEquals(new Object[]{"I","am"}, runtest("I'm"));
					assertArrayEquals(new Object[]{"we","are"}, runtest("we're"));
					assertArrayEquals(new Object[]{"they","are"}, runtest("they're"));
					assertArrayEquals(new Object[]{"I","have"}, runtest("I've"));
					assertArrayEquals(new Object[]{"Should","have"}, runtest("Should've"));
					assertArrayEquals(new Object[]{"They","would"}, runtest("They'd"));
					assertArrayEquals(new Object[]{"She","will"}, runtest("She'll"));
					assertArrayEquals(new Object[]{"Put","them"}, runtest("Put 'em"));
					
					//as single quotes
					assertArrayEquals(new Object[]{"quote","test"}, runtest("'quote","test'"));
					assertArrayEquals(new Object[]{"f(x)","=","df/dx"},runtest("f'(x)","=","df/dx"));
					assertArrayEquals(new Object[]{"f(x)","=","df/dx" }, runtest("f''(x)","=","df'/dx"));
				} else {
					//basic rules
					assertArrayEquals(new Object[]{"Finland"}, runtest("Finland's"));
					assertArrayEquals(new Object[]{"Gladys", "house"}, runtest("Gladys'", "house"));
		
					//contractions
					assertArrayEquals(new Object[]{"is","not"}, runtest("isn't"));
					assertArrayEquals(new Object[]{"do","not"}, runtest("don't"));
					assertArrayEquals(new Object[]{"will","not"}, runtest("won't"));
					assertArrayEquals(new Object[]{"shall","not"}, runtest("shan't"));
					assertArrayEquals(new Object[]{"let","us"}, runtest("let's"));
					assertArrayEquals(new Object[]{"I","am"}, runtest("I'm"));
					assertArrayEquals(new Object[]{"we","are"}, runtest("we're"));
					assertArrayEquals(new Object[]{"they","are"}, runtest("they're"));
					assertArrayEquals(new Object[]{"I","have"}, runtest("I've"));
					assertArrayEquals(new Object[]{"Should","have"}, runtest("Should've"));
					assertArrayEquals(new Object[]{"They","would"}, runtest("They'd"));
					assertArrayEquals(new Object[]{"She","will"}, runtest("She'll"));
					assertArrayEquals(new Object[]{"Put","them"}, runtest("Put","'em"));
					
					//as single quotes
					assertArrayEquals(new Object[]{"quote","test"}, runtest("'quote","test'"));
					assertArrayEquals(new Object[]{"f(x)","=","df/dx"},runtest("f'(x)","=","df/dx"));
					assertArrayEquals(new Object[]{"f(x)","=","df/dx" }, runtest("f''(x)","=","df'/dx"));
				}
				
				
			} catch (TokenizerException e) {
				
			}
		}
	}
}
