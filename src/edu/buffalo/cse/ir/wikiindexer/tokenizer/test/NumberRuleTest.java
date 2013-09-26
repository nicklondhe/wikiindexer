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
public class NumberRuleTest extends TokenizerRuleTest {

	public NumberRuleTest(Properties props) {
		super(props, IndexerConstants.NUMBERSRULE);
	}

	@Test
	public void testRule() {
		if (rule == null) {
			fail("Rule not implemented");
		} else {
			try {
				if (isPreTokenization) {
					assertArrayEquals(
							new Object[] { "The App Store offered more than apps by Apple and third parties." },
							runtest("The App Store offered more than 775,000 apps by Apple and third parties."));
					assertArrayEquals(
							new Object[] { "The game received average review scores of % and / for the Xbox version" },
							runtest("The game received average review scores of 96.92% and 98/100 for the Xbox 360 version"));
					assertArrayEquals(
							new Object[] { "The number is the sixth prime number" },
							runtest("The number 13 is the sixth prime number"));
				} else {
					assertArrayEquals(
							new Object[] { "The", "App", "Store", "offered",
									"more", "than", "apps", "by", "Apple",
									"and", "third", "parties." },
							runtest("The", "App", "Store", "offered", "more",
									"than", "775,000", "apps", "by", "Apple",
									"and", "third", "parties."));
					assertArrayEquals(
							new Object[] { "The", "game", "received",
									"average", "review", "scores", "of", "%",
									"and", "/", "for", "the", "Xbox", "version" },
							runtest("The", "game", "received", "average",
									"review", "scores", "of", "96.92%", "and",
									"98/100", "for", "the", "Xbox", "360",
									"version"));
					assertArrayEquals(
							new Object[] { "The", "number", "is", "the",
									"sixth", "prime", "number" },
							runtest("The", "number", "13", "is", "the",
									"sixth", "prime", "number"));
				}

			} catch (TokenizerException e) {

			}
		}
	}

}
