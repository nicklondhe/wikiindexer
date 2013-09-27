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
public class AccentRuleTest extends TokenizerRuleTest {

	public AccentRuleTest(Properties props) {
		super(props, IndexerConstants.ACCENTRULE);
	}
	
	@Test
	public void testRule() {
		if (rule == null) {
			fail("Rule not implemented");
		} else {
			try {
				if (isPreTokenization) {
					assertArrayEquals(new Object[]{"The urban counterpart of chateau is palais"},
							runtest("The urban counterpart of château is palais"));
					assertArrayEquals(new Object[]{"The expression hotel particulier is used for an urban 'private house'"}, 
							runtest("The expression hôtel particulier is used for an urban 'private house'"));
					assertArrayEquals(new Object[]{"Resumes can be used for a variety of reasons"}, 
							runtest("Résumés can be used for a variety of reasons"));
					assertArrayEquals(new Object[]{"naра ('steam/vapour') and napa ('cent/penny, money')"},
							runtest("nа̀ра ('steam/vapour') and nара̀ ('cent/penny, money')"));
					assertArrayEquals(new Object[]{"for example vis-a-vis piece de resistance and creme brulee"}, 
							runtest("for example vis-à-vis pièce de résistance and crème brûlée"));
					assertArrayEquals(new Object[]{"Spanish pinguino French aigue or aigue"}, 
							runtest("Spanish pingüino French aiguë or aigüe"));
				} else {
					assertArrayEquals(new Object[]{"The", "urban", "counterpart", "of", "chateau", "is", "palais"}, 
							runtest("The", "urban", "counterpart", "of", "château", "is", "palais"));
					assertArrayEquals(new Object[]{"The", "expression", "hotel", "particulier", "is", "used", "for", "an", "urban", "'private", "house'"}, 
							runtest("The", "expression", "hôtel", "particulier", "is", "used", "for", "an", "urban", "'private", "house'"));
					assertArrayEquals(new Object[]{"Resumes", "can", "be", "used", "for", "a", "variety", "of", "reasons"}, 
							runtest("Résumés", "can", "be", "used", "for", "a", "variety", "of", "reasons"));
					assertArrayEquals(new Object[]{"naра", "('steam/vapour')", "and", "napa", "('cent/penny,", "money')"},
							runtest("nа̀ра", "('steam/vapour')", "and", "nара̀", "('cent/penny,", "money')"));
					assertArrayEquals(new Object[]{"for", "example", "vis-a-vis", "piece", "de", "resistance", "and", "creme", "brulee"}, 
							runtest("for", "example", "vis-à-vis", "pièce", "de", "résistance", "and", "crème", "brûlée"));
					assertArrayEquals(new Object[]{"Spanish", "pinguino", "French", "aigue", "or", "aigue"}, 
							runtest("Spanish", "pingüino", "French", "aiguë", "or", "aigüe"));
				}
				
			} catch (TokenizerException e) {
				
			}
		}
	}

}
