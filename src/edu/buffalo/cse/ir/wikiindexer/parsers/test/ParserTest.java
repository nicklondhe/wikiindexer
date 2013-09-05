/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.parsers.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Properties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import edu.buffalo.cse.ir.wikiindexer.FileUtil;
import edu.buffalo.cse.ir.wikiindexer.parsers.Parser;
import edu.buffalo.cse.ir.wikiindexer.test.PropertiesBasedTest;
import edu.buffalo.cse.ir.wikiindexer.wikipedia.WikipediaDocument;

/**
 * @author nikhillo
 *
 */
@RunWith(Parameterized.class)
public class ParserTest extends PropertiesBasedTest {
	
	public ParserTest(Properties props) {
		super(props);
	}
	
	
	/**
	 * Test method for {@link edu.buffalo.cse.ir.wikiindexer.parsers.Parser#parse(java.lang.String, java.util.Collection)}.
	 */
	@Test
	public final void testParse() {
		Parser testClass = new Parser(idxProps);
		ArrayList<WikipediaDocument> list = new ArrayList<WikipediaDocument>();
		
		//null file
		testClass.parse(null, list);
		assertTrue(list.isEmpty());
		
		//empty filename
		testClass.parse("", list);
		assertTrue(list.isEmpty());
		
		//invalid filename
		testClass.parse("abc.xml", list);
		assertTrue(list.isEmpty());
		
		//five documents
		testClass.parse(FileUtil.getRootFilesFolder(idxProps) + "five_entries.xml", list);
		assertEquals(5, list.size());
		/* TODO: Add structural test here */
		
		
	}

}
