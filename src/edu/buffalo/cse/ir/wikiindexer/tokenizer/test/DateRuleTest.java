/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.tokenizer.test;

import static org.junit.Assert.fail;

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
public class DateRuleTest extends TokenizerRuleTest {

	public DateRuleTest(Properties props) {
		super(props, IndexerConstants.DATERULE);
	}

	@Test
	public void testRule() {
		if (rule == null) {
			fail("Rule not implemented");

		} else {
			try {
				if (isPreTokenization) {
					assertArrayEquals(
							new Object[] { "Vidya Balan born 19780101 is an Indian actress." },
							runtest("Vidya Balan born 1 January 1978 is an Indian actress."));
					assertArrayEquals(
							new Object[] { "President Franklin D. Roosevelt to proclaim 19411207, 'a date which will live in infamy'" },
							runtest("President Franklin D. Roosevelt to proclaim December 7, 1941, 'a date which will live in infamy'"));
					assertArrayEquals(
							new Object[] { "The Academy operated until it was destroyed by Lucius Cornelius Sulla in -00840101" },
							runtest("The Academy operated until it was destroyed by Lucius Cornelius Sulla in 84 BC"));
					assertArrayEquals(
							new Object[] { "For instance, the 19480101 ABL finalist Baltimore Bullets moved to the BAA and won that league's 19480101 title." },
							runtest("For instance, the 1948 ABL finalist Baltimore Bullets moved to the BAA and won that league's 1948 title."));
					assertArrayEquals(
							new Object[] { "It was now about 10:15:00." },
							runtest("It was now about 10:15 am."));
					assertArrayEquals(
							new Object[] { "Godse approached Gandhi on 19480130 during the evening prayer at 17:15:00." },
							runtest("Godse approached Gandhi on January 30, 1948 during the evening prayer at 5:15PM."));
					assertArrayEquals(
							new Object[] { "Pune is known to have existed as a town since 08470101." },
							runtest("Pune is known to have existed as a town since 847 AD."));
					assertArrayEquals(
							new Object[] { "The 20040101 Indian Ocean earthquake was an undersea megathrust earthquake that occurred at 20041226 00:58:53" },
							runtest("The 2004 Indian Ocean earthquake was an undersea megathrust earthquake that occurred at 00:58:53 UTC on Sunday, 26 December 2004"));
					assertArrayEquals(
							new Object[] { "19000411 is the 101st day of the year (102nd in leap years) in the Gregorian calendar." },
							runtest("April 11 is the 101st day of the year (102nd in leap years) in the Gregorian calendar."));
					assertArrayEquals(
							new Object[] { "Apple is one of the world's most valuable publicly traded companies in 20110101Ð20120101." },
							runtest("Apple is one of the world's most valuable publicly traded companies in 2011Ð12."));
				} else {
					assertArrayEquals(
							new Object[] { "Vidya", "Balan", "born",
									"19780101", "is", "an", "Indian",
									"actress." },
							runtest("Vidya", "Balan", "born", "1", "January",
									"1978", "is", "an", "Indian", "actress."));
					assertArrayEquals(
							new Object[] { "President", "Franklin", "D.",
									"Roosevelt", "to", "proclaim", "19411207,",
									"'a", "date", "which", "will", "live",
									"in", "infamy'" },
							runtest("President", "Franklin", "D.", "Roosevelt",
									"to", "proclaim", "December", "7,",
									"1941,", "'a", "date", "which", "will",
									"live", "in", "infamy'"));
					assertArrayEquals(
							new Object[] { "The", "Academy", "operated",
									"until", "it", "was", "destroyed", "by",
									"Lucius", "Cornelius", "Sulla", "in",
									"-00840101" },
							runtest("The", "Academy", "operated", "until",
									"it", "was", "destroyed", "by", "Lucius",
									"Cornelius", "Sulla", "in", "84", "BC"));
					assertArrayEquals(
							new Object[] { "For", "instance,", "the",
									"19480101", "ABL", "finalist", "Baltimore",
									"Bullets", "moved", "to", "the", "BAA",
									"and", "won", "that", "league's",
									"19480101", "title." },
							runtest("For", "instance,", "the", "1948", "ABL",
									"finalist", "Baltimore", "Bullets",
									"moved", "to", "the", "BAA", "and", "won",
									"that", "league's", "1948", "title."));
					assertArrayEquals(
							new Object[] { "It", "was", "now", "about",
									"10:15:00." },
							runtest("It", "was", "now", "about", "10:15", "am."));
					assertArrayEquals(
							new Object[] { "Godse", "approached", "Gandhi",
									"on", "19480130", "during", "the",
									"evening", "prayer", "at", "17:15:00." },
							runtest("Godse", "approached", "Gandhi", "on",
									"January", "30,", "1948", "during", "the",
									"evening", "prayer", "at", "5:15PM."));
					assertArrayEquals(
							new Object[] { "Pune", "is", "known", "to", "have",
									"existed", "as", "a", "town", "since",
									"08470101." },
							runtest("Pune", "is", "known", "to", "have",
									"existed", "as", "a", "town", "since",
									"847", "AD."));
					assertArrayEquals(
							new Object[] { "The", "20040101", "Indian",
									"Ocean", "earthquake", "was", "an",
									"undersea", "megathrust", "earthquake",
									"that", "occurred", "at",
									"20041226 00:58:53" },
							runtest("The", "2004", "Indian", "Ocean",
									"earthquake", "was", "an", "undersea",
									"megathrust", "earthquake", "that",
									"occurred", "at", "00:58:53", "UTC", "on",
									"Sunday,", "26", "December", "2004"));
					assertArrayEquals(
							new Object[] { "19000411", "is", "the", "101st",
									"day", "of", "the", "year", "(102nd", "in",
									"leap", "years)", "in", "the", "Gregorian",
									"calendar." },
							runtest("April", "11", "is", "the", "101st", "day",
									"of", "the", "year", "(102nd", "in",
									"leap", "years)", "in", "the", "Gregorian",
									"calendar."));
					assertArrayEquals(
							new Object[] { "Apple", "is", "one", "of", "the",
									"world's", "most", "valuable", "publicly",
									"traded", "companies", "in",
									"20110101Ð20120101." },
							runtest("Apple", "is", "one", "of", "the",
									"world's", "most", "valuable", "publicly",
									"traded", "companies", "in", "2011Ð12."));
				}

			} catch (TokenizerException e) {

			}
		}
	}
}
