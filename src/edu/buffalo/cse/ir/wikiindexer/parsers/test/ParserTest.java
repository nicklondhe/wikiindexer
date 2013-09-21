/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.parsers.test;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
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
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	
	private static final String[] titles = {"Russ Conway","Kathie Kay","Alan Breeze",
		"Billy Cotton Band Show","Billy Cotton"};
	private static final int[] ids = {1356489, 1587174,12879874,1276897,252332};
	private static final String[] authors = {"2.96.87.226","1exec1","Addbot","Johnpacklambert","Narrow Feint"};
	private static final String[] dates = {"2013-08-22T23:11:44Z","2012-11-26T22:46:48Z",
		"2013-03-17T11:44:07Z","2013-02-15T01:55:34Z","2013-08-10T07:38:40Z"};
	private static final int[] numsections = {8,4,3,1,6};
	private static final String[] sectitles = {"Default", "Biography", "References", "Default", "References"};
	private static final int[] numcategories = {8,6,4,5,13};
	private static final String[] cattitles = {"1925 births","2005 deaths","English male singers",
		"1949 establishments in the United Kingdom","Brentford F.C. players"};
	
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
		int cnt = 0;
		for (WikipediaDocument doc : list) {
			validateTitle(doc, cnt);
			validateId(doc, cnt);
			validateAuthor(doc, cnt);
			validateDate(doc, cnt);
			validateSections(doc,cnt);
			validateCategories(doc, cnt);
			//add links test
			cnt++;
		}
		
	}


	private void validateTitle(WikipediaDocument doc, int cnt) {
		assertEquals(titles[cnt], doc.getTitle());
	}
	
	private void validateId(WikipediaDocument doc, int cnt) {
		assertEquals(ids[cnt], doc.getId());
	}
	
	private void validateAuthor(WikipediaDocument doc, int cnt) {
		assertEquals(authors[cnt], doc.getAuthor());
	}
	
	private void validateDate(WikipediaDocument doc, int cnt) {
		assertEquals(dates[cnt], sdf.format(doc.getPublishDate()));
	}
	
	private void validateSections(WikipediaDocument doc, int cnt) {
		//we validate count and the kth section title for the kth document
		int ns = numsections[cnt];
		int idx = (cnt <= ns - 1) ? cnt : ns - 1;
		assertEquals(ns, doc.getSections().size());
		assertEquals(sectitles[cnt], doc.getSections().get(idx).getTitle());
	}
	
	private void validateCategories(WikipediaDocument doc, int cnt) {
		//we validate count and the kth category for the kth document
		int nc = numcategories[cnt];
		int idx = (cnt <= nc - 1) ? cnt : nc - 1;
		assertEquals(nc, doc.getCategories().size());
		assertEquals(cattitles[cnt], doc.getCategories().get(idx));
	}
}
