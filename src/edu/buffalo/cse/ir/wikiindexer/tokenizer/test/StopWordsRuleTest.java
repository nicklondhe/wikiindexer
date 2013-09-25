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
public class StopWordsRuleTest extends TokenizerRuleTest {

	public StopWordsRuleTest(Properties props) {
		super(props, IndexerConstants.STOPWORDSRULE);
	}
	
	@Test
	public void testRule() {
		if (rule == null) {
			fail("Rule not implemented");
		} else {
			try {
				if (isPreTokenization) {
					assertArrayEquals(new Object[]{"test"}, 
							runtest("this is a test"));
					assertArrayEquals(new Object[]{}, 
							runtest("do not do this"));
					assertArrayEquals(new Object[]{"ace spades"}, 
							runtest("ace of spades"));
					assertArrayEquals(new Object[]{"valid sentence"}, 
							runtest("valid sentence"));
				} else {
					assertArrayEquals(new Object[]{"test"}, 
							runtest("this","is","a","test"));
					assertArrayEquals(new Object[]{}, 
							runtest("do","not","do","this"));
					assertArrayEquals(new Object[]{"ace","spades"}, 
							runtest("ace","of","spades"));
					assertArrayEquals(new Object[]{"valid","sentence"}, 
							runtest("valid","sentence"));
				}
				
			} catch (TokenizerException e) {
				
			}
		}
	}
}
