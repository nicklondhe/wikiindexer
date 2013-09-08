/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.wikipedia.test;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.buffalo.cse.ir.wikiindexer.wikipedia.WikipediaParser;

/**
 * @author nikhillo
 *
 */
public class WikipediaParserTest {

	/**
	 * Test method for {@link edu.buffalo.cse.ir.wikiindexer.wikipedia.WikipediaParser#parseSectionTitle(java.lang.String)}.
	 */
	@Test
	public final void testParseSectionTitle() {
		//null test
		assertEquals(null, WikipediaParser.parseSectionTitle(null));
		
		//empty test
		assertEquals("", WikipediaParser.parseSectionTitle(""));
		
		//level 1 test
		assertEquals("section", WikipediaParser.parseSectionTitle("== section =="));
		
		//level 2 test
		assertEquals("section", WikipediaParser.parseSectionTitle("=== section ==="));
		
		//level 3 test
		assertEquals("section", WikipediaParser.parseSectionTitle("==== section ===="));
		
		//level 4 test
		assertEquals("section", WikipediaParser.parseSectionTitle("===== section ====="));
		
		//level 5 test
		assertEquals("section", WikipediaParser.parseSectionTitle("====== section ======"));
	}

	/**
	 * Test method for {@link edu.buffalo.cse.ir.wikiindexer.wikipedia.WikipediaParser#parseListItem(java.lang.String)}.
	 */
	@Test
	public final void testParseListItem() {
		//null test
		assertEquals(null, WikipediaParser.parseListItem(null));
		
		//empty test
		assertEquals("", WikipediaParser.parseListItem(""));
		
		//unordered list level 1
		assertEquals("unordered item", WikipediaParser.parseListItem("* unordered item"));
		
		//unordered list level 2
		assertEquals("unordered item", WikipediaParser.parseListItem("** unordered item"));
		
		//unordered list level 4
		assertEquals("unordered item", WikipediaParser.parseListItem("**** unordered item"));
		
		//ordered list level 1
		assertEquals("ordered item", WikipediaParser.parseListItem("# ordered item"));
		
		//ordered lists level 3
		assertEquals("ordered item", WikipediaParser.parseListItem("### ordered item"));
		
		//definition list
		assertEquals("definition item", WikipediaParser.parseListItem(": definition item"));
		
		/* TODO: Add more tests */
	}

	/**
	 * Test method for {@link edu.buffalo.cse.ir.wikiindexer.wikipedia.WikipediaParser#parseTextFormatting(java.lang.String)}.
	 */
	@Test
	public final void testParseTextFormatting() {
		//null tests
		assertEquals(null, WikipediaParser.parseTextFormatting(null));
		
		//empty test
		assertEquals("", WikipediaParser.parseTextFormatting(""));
		
		//test italics
		assertEquals("This is italicized text test", WikipediaParser.parseTextFormatting("This is ''italicized text'' test"));
		
		//test bold
		assertEquals("This is bold text test", WikipediaParser.parseTextFormatting("This is '''bold text''' test"));
		
		//test both
		assertEquals("This is italics bold test", WikipediaParser.parseTextFormatting("This is '''''italics bold''''' test"));
		
		//test both 2
		assertEquals("This is italics and bold text test", WikipediaParser.parseTextFormatting("This is ''italics'' and '''bold''' text test"));
		
		/* TODO: Add more tests */
	}

	/**
	 * Test method for {@link edu.buffalo.cse.ir.wikiindexer.wikipedia.WikipediaParser#parseTagFormatting(java.lang.String)}.
	 */
	@Test
	public final void testParseTagFormatting() {
		//null test
		assertEquals(null, WikipediaParser.parseTagFormatting(null));
		
		//empty test
		assertEquals("", WikipediaParser.parseTagFormatting(""));
		
		//empty tag test
		assertEquals("Watch the disappear", WikipediaParser.parseTagFormatting("Watch the <tag/> disappear"));
		
		//tag with content
		assertEquals("I should not vanish", WikipediaParser.parseTagFormatting("<random> I should not vanish </random>"));
		
		//tag with attributes
		assertEquals("Attributes or not, I'm still here", WikipediaParser.parseTagFormatting("<mytag val='some' otherval ='more'> Attributes or not, I'm still here</mytag>"));
		
		//html escaping
		assertEquals("Did you get me right?", WikipediaParser.parseTagFormatting("&lt;painful attr1='yes' attr2='no' &gt;Did you get me right?&lt;/pain&gt;"));
		
		/* TODO: Add more tests */
	}

	/**
	 * Test method for {@link edu.buffalo.cse.ir.wikiindexer.wikipedia.WikipediaParser#parseTemplates(java.lang.String)}.
	 */
	@Test
	public final void testParseTemplates() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.buffalo.cse.ir.wikiindexer.wikipedia.WikipediaParser#parseLinks(java.lang.String)}.
	 */
	@Test
	public final void testParseLinks() {
		fail("Not yet implemented"); // TODO
	}

}
