package edu.buffalo.cse.ir.wikiindexer.tokenizer.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({TokenStreamTest.class, AccentRuleTest.class,
	ApostropheRuleTest.class, CapitalizationRuleTest.class,
	DateRuleTest.class, HyphenRuleTest.class, NumberRuleTest.class,
	PunctuationRuleTest.class, SpecialCharRuleTest.class,
	StopWordsRuleTest.class, WhitespaceRuleTest.class})
public class TokenizerSuite {

}
