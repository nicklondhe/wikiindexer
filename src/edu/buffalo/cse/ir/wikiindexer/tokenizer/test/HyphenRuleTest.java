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
public class HyphenRuleTest extends TokenizerRuleTest {

	public HyphenRuleTest(Properties props) {
		super(props, IndexerConstants.HYPHENRULE);
		// TODO Auto-generated constructor stub
	}
	
	@Test
	public void testRule() {
		if (rule == null) {
			 fail("Rule not implemented");
		} else {
			try {
				//whitespace padded hyphens
				assertArrayEquals(new Object[]{"hyphen", "test"}, runtest("hyphen"," - ", "test"));
				assertArrayEquals(new Object[]{"hyphen", "test"}, runtest("hyphen"," -- ", "test"));
				
				//alphanumeric
				assertArrayEquals(new Object[]{"B-52"}, runtest("B-52"));
				assertArrayEquals(new Object[]{"12-B"}, runtest("12-B"));
				assertArrayEquals(new Object[]{"6-6"}, runtest("6-6"));
				assertArrayEquals(new Object[]{"D-BB3"}, runtest("D-BB3"));
				assertArrayEquals(new Object[]{"week day"}, runtest("week-day"));
				
				//code style
				assertArrayEquals(new Object[]{"c"}, runtest("c--"));
				assertArrayEquals(new Object[]{"c"}, runtest("--c"));
				
			} catch (TokenizerException e) {
				
			}
		}
	}
	
}
