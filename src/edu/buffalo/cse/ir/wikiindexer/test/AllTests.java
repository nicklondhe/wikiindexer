package edu.buffalo.cse.ir.wikiindexer.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import edu.buffalo.cse.ir.wikiindexer.parsers.test.ParsersSuite;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.test.TokenizerSuite;
import edu.buffalo.cse.ir.wikiindexer.wikipedia.test.WikipediaSuite;

@RunWith(Suite.class)
@SuiteClasses({ParsersSuite.class, WikipediaSuite.class, TokenizerSuite.class})
public class AllTests {

}
