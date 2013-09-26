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
public class CapitalizationRuleTest extends TokenizerRuleTest {

	public CapitalizationRuleTest(Properties props) {
		super(props, IndexerConstants.CAPITALIZATIONRULE);
	}

	@Test
	public void testRule() {
		if (rule == null) {
			fail("Rule not implemented");
		} else {
			try {
				if (isPreTokenization) {
					assertArrayEquals(new Object[] { "this is a test." },
							runtest("This is a test."));
					assertArrayEquals(new Object[] { "san Francisco is in California." },
							runtest("San Francisco is in California."));
					assertArrayEquals(
							new Object[] { "some bodily fluids, such as saliva and tears, do not transmit HIV" },
							runtest("Some bodily fluids, such as saliva and tears, do not transmit HIV"));
					assertArrayEquals(
							new Object[] { "it runs Apple's iOS mobile operating system," },
							runtest("It runs Apple's iOS mobile operating system,"));
				} else {
					assertArrayEquals(new Object[] { "this", "is", "a", "test." },
							runtest("This", "is", "a", "test."));
					assertArrayEquals(new Object[] { "san", "Francisco", "is",
							"in", "California." },
							runtest("San", "Francisco", "is", "in", "California."));
					assertArrayEquals(
							new Object[] { "some", "bodily", "fluids,", "such",
									"as", "saliva", "and", "tears,", "do", "not",
									"transmit", "HIV" },
							runtest("Some", "bodily", "fluids,", "such", "as",
									"saliva", "and", "tears,", "do", "not",
									"transmit", "HIV"));
					assertArrayEquals(
							new Object[] { "it", "runs", "Apple's", "iOS",
									"mobile", "operating", "system," },
							runtest("It", "runs", "Apple's", "iOS", "mobile",
									"operating", "system,"));
				}
				

			} catch (TokenizerException e) {

			}
		}
	}

}
