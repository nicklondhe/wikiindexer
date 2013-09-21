/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.tokenizer.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;

/**
 * @author nikhillo
 *
 */
public class TokenStreamTest {

	/**
	 * Test method for {@link edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream#append(java.lang.String[])}.
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void testAppend() {
		//appending null
		TokenStream stream = new TokenStream("test");
		stream.append((String[])null);
		assertEquals(new Object[]{"test"}, stream.getAllTokens().toArray());
		stream = null;
		
		//appending empty string
		stream = new TokenStream("test");
		stream.append("");
		assertEquals(new Object[]{"test"}, stream.getAllTokens().toArray());
		stream = null;
		
		//one token
		stream = new TokenStream("test");
		stream.append("string");
		assertEquals(new Object[]{"test", "string"}, stream.getAllTokens().toArray());
		stream = null;
		
		//multiple tokens
		stream = new TokenStream("test");
		stream.append("string","with","multiple","tokens");
		assertEquals(new Object[]{"test", "string","with","multiple","tokens"}, stream.getAllTokens().toArray());
		stream = null;
		
		//intermediate nulls and emptys
		stream = new TokenStream("test");
		stream.append("string","with",null,"and","","tokens");
		assertEquals(new Object[]{"test", "string","with","and","tokens"}, stream.getAllTokens().toArray());
		stream = null;
	}

	/**
	 * Test method for {@link edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream#getTokenMap()}.
	 */
	@Test
	public void testGetTokenMap() {
		//null string based stream
		TokenStream stream = new TokenStream((String)null);
		assertEquals(null, stream.getTokenMap());
		stream = null;
		
		//empty string
		stream = new TokenStream("");
		assertEquals(null, stream.getTokenMap());
		stream = null;
		
		//unique tokens
		stream = new TokenStream("this");
		stream.append("is", "a", "test", "string");
		Map<String, Integer> smap = getSortedMap(stream.getTokenMap());
		assertEquals("[a, is, string, test, this]", smap.keySet().toString());
		assertEquals("[1, 1, 1, 1, 1]", smap.values().toString());
		stream = null;
		smap = null;
		
		//same token repeated
		stream = new TokenStream("hello");
		stream.append("hello", "hello", "hello", "hello");
		smap = getSortedMap(stream.getTokenMap());
		assertEquals("[hello]", smap.keySet().toString());
		assertEquals("[5]", smap.values().toString());
		stream = null;
		smap = null;
		
		//combination
		stream = new TokenStream("to");
		stream.append("be", "or", "not", "to", "be");
		smap = getSortedMap(stream.getTokenMap());
		assertEquals("[be, not, or, to]", smap.keySet().toString());
		assertEquals("[2, 1, 1, 2]", smap.values().toString());
		stream = null;
		smap = null;
		
		//with remove
		stream = new TokenStream("to");
		stream.append("be", "or", "not", "to", "be");
		stream.remove();
		smap = getSortedMap(stream.getTokenMap());
		assertEquals("[be, not, or, to]", smap.keySet().toString());
		assertEquals("[2, 1, 1, 1]", smap.values().toString());
		stream.seekEnd();
		stream.previous(); //be
		stream.previous(); //to
		stream.remove();
		stream.previous();
		stream.remove();
		smap = getSortedMap(stream.getTokenMap());
		assertEquals("[be, or]", smap.keySet().toString());
		assertEquals("[2, 1]", smap.values().toString());
		stream = null;
		smap = null;
		
		//with merge with previous
		stream = new TokenStream("to");
		stream.append("be", "or", "not", "to", "be");
		stream.next(); //at be
		stream.mergeWithPrevious();
		stream.seekEnd();
		stream.previous();
		stream.mergeWithPrevious();
		smap = getSortedMap(stream.getTokenMap());
		assertEquals("[not, or, to be]", smap.keySet().toString());
		assertEquals("[1, 1, 2]", smap.values().toString());
		stream = null;
		
		//with merge with next
		stream = new TokenStream("to");
		stream.append("be", "or", "not", "to", "be");
		stream.mergeWithNext();
		stream.seekEnd();
		stream.previous();
		stream.previous();
		stream.mergeWithNext();
		smap = getSortedMap(stream.getTokenMap());
		assertEquals("[not, or, to be]", smap.keySet().toString());
		assertEquals("[1, 1, 2]", smap.values().toString());
		stream = null;
	}
	
	private Map<String, Integer> getSortedMap(Map<String, Integer> inputMap) {
		return new TreeMap<String, Integer>(inputMap);
		
	}

	/**
	 * Test method for {@link edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream#query(java.lang.String)}.
	 */
	@Test
	public void testQuery() {
		//null string based stream
		TokenStream stream = new TokenStream((String)null);
		assertEquals(0, stream.query("test"));
		stream = null;
		
		//empty string
		stream = new TokenStream("");
		assertEquals(0, stream.query("test"));
		stream = null;
		
		//unique tokens
		stream = new TokenStream("this");
		stream.append("is", "a", "test", "string");
		
		assertEquals(1, stream.query("test"));
		assertEquals(0, stream.query("hello"));
		stream = null;
		
		//same token repeated
		stream = new TokenStream("hello");
		stream.append("hello","hello","hello","hello");
		assertEquals(0, stream.query("test"));
		assertEquals(5, stream.query("hello"));
		stream = null;
		
		//combination
		stream = new TokenStream("to");
		stream.append("be", "or", "not", "to", "be");
		assertEquals(2, stream.query("be"));
		assertEquals(1, stream.query("not"));
		assertEquals(0, stream.query("test"));
		stream = null;
		
		//with remove
		stream = new TokenStream("this");
		stream.append("is", "a", "test", "string");
		stream.remove(); //this removed
		assertEquals(0, stream.query("this"));
		stream = null;
		
		//with merge with previous
		stream = new TokenStream("this");
		stream.append("is", "a", "test", "string");
		stream.next();
		stream.mergeWithPrevious();
		assertEquals(0, stream.query("this"));
		assertEquals(1, stream.query("this is"));
		stream = null;
		
		//with merge with next
		stream = new TokenStream("this");
		stream.append("is", "a", "test", "string");
		stream.mergeWithNext();
		assertEquals(0, stream.query("this"));
		assertEquals(1, stream.query("this is"));
		stream = null;
	}

	/**
	 * Test method for {@link edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream#hasNext()}.
	 */
	@Test
	public void testHasNext() {
		//null
		TokenStream stream = new TokenStream((String)null);
		assertEquals(false, stream.hasNext());
		stream = null;
		
		//empty
		stream = new TokenStream("");
		assertEquals(false, stream.hasNext());
		stream = null;
		
		//some text and iteration
		stream = new TokenStream("this");
		stream.append("is","a","test","stream");
		assertTrue(stream.hasNext()); 
		stream.next(); //after this
		assertTrue(stream.hasNext()); 
		stream.next(); //after is
		assertTrue(stream.hasNext()); 
		stream.next(); //after a
		assertTrue(stream.hasNext()); 
		stream.next(); //after test
		assertTrue(stream.hasNext()); 
		stream.next(); //after stream
		assertFalse(stream.hasNext());
		stream = null;
		
		//with seek
		stream = new TokenStream("this");
		stream.append("is","a","test","stream");
		stream.seekEnd();
		assertFalse(stream.hasNext());
		stream = null;
		
		//forward and reverse
		stream = new TokenStream("this");
		stream.append("is","a","test","stream");
		while (stream.hasNext()) {
			stream.next();
		}
		
		stream.previous();
		assertTrue(stream.hasNext());
		stream.next();
		assertFalse(stream.hasNext());
		stream = null;
		
		//with remove
		stream = new TokenStream("this");
		stream.append("is","a","test","stream");
		stream.seekEnd();
		stream.previous();
		stream.remove();
		assertFalse(stream.hasNext());
		stream = null;
		
		//with merge with previous
		stream = new TokenStream("this");
		stream.append("is","a","test","stream");
		stream.next();
		stream.mergeWithPrevious();
		assertTrue(stream.hasNext());
		stream.seekEnd();
		stream.previous();
		stream.mergeWithPrevious();
		assertTrue(stream.hasNext());
		stream = null;
		
		//with merge with next
		stream = new TokenStream("this");
		stream.append("is","a","test","stream");
		stream.mergeWithNext();
		assertTrue(stream.hasNext());
		stream.seekEnd();
		stream.previous();
		stream.previous();
		stream.mergeWithNext();
		stream.next();
		assertFalse(stream.hasNext());
		stream = null;
	}

	/**
	 * Test method for {@link edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream#hasPrevious()}.
	 */
	@Test
	public void testHasPrevious() {
		//null
		TokenStream stream = new TokenStream((String)null);
		assertEquals(false, stream.hasPrevious());
		stream = null;
		
		//empty
		stream = new TokenStream("");
		assertEquals(false, stream.hasPrevious());
		stream = null;
		
		//some text and iteration
		stream = new TokenStream("this");
		stream.append("is","a","test","stream");
		assertFalse(stream.hasPrevious()); //start of stream
		stream.seekEnd();
		assertTrue(stream.hasPrevious()); 
		stream.previous(); //after this
		assertTrue(stream.hasPrevious()); 
		stream.previous(); //after is
		assertTrue(stream.hasPrevious()); 
		stream.previous(); //after a
		assertTrue(stream.hasPrevious()); 
		stream.previous(); //after test
		assertTrue(stream.hasPrevious()); 
		stream.previous(); //after stream
		assertFalse(stream.hasPrevious());
		stream = null;
		
		//with seek
		stream = new TokenStream("this");
		stream.append("is","a","test","stream");
		stream.reset();
		assertFalse(stream.hasPrevious());
		stream = null;
		
		//forward and reverse
		stream = new TokenStream("this");
		stream.append("is","a","test","stream");
		stream.next();
		assertTrue(stream.hasPrevious());
		stream.previous();
		assertFalse(stream.hasPrevious());
		stream = null;
		
		//with remove
		stream = new TokenStream("this");
		stream.append("is","a","test","stream");
		stream.remove();
		assertFalse(stream.hasPrevious());
		stream = null;
		
		//with merge with previous
		stream = new TokenStream("this");
		stream.append("is","a","test","stream");
		stream.next();
		stream.mergeWithPrevious();
		assertFalse(stream.hasPrevious());
		stream = null;
		
		//with merge with next
		stream = new TokenStream("this");
		stream.append("is","a","test","stream");
		stream.mergeWithNext();
		assertFalse(stream.hasPrevious());
		stream = null;
	}

	/**
	 * Test method for {@link edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream#next()}.
	 */
	@Test
	public void testNext() {
		//null
		TokenStream stream = new TokenStream((String) null);
		assertNull(stream.next());
		stream = null;
		
		//empty str
		stream = new TokenStream("");
		assertNull(stream.next());
		stream = null;
		
		//fwd iteration
		stream = new TokenStream("this");
		stream.append("is","a","test","stream");
		assertEquals("this", stream.next());
		assertEquals("is", stream.next());
		assertEquals("a", stream.next());
		assertEquals("test", stream.next());
		assertEquals("stream", stream.next());
		assertNull(stream.next());
		stream = null;
		
		//fwd and reverse
		stream = new TokenStream("this");
		stream.append("is","a","test","stream");
		assertEquals("this", stream.next());
		stream.previous();
		assertEquals("this", stream.next());
		assertEquals("is", stream.next());
		assertEquals("a", stream.next());
		stream.reset();
		assertEquals("this", stream.next());
		stream = null;
		
		//with remove
		stream = new TokenStream("this");
		stream.append("is","a","test","stream");
		stream.remove();
		assertEquals("is", stream.next());
		stream.remove();
		assertEquals("test", stream.next());
		stream = null;
		
		//with merge with previous
		stream = new TokenStream("this");
		stream.append("is","a","test","stream");
		stream.next();
		stream.mergeWithPrevious();
		assertEquals("this is", stream.next());
		stream = null;
		
		//with merge with next
		stream = new TokenStream("this");
		stream.append("is","a","test","stream");
		stream.mergeWithNext();
		assertEquals("this is", stream.next());
		stream = null;
	}

	/**
	 * Test method for {@link edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream#previous()}.
	 */
	@Test
	public void testPrevious() {
		//null
		TokenStream stream = new TokenStream((String) null);
		assertNull(stream.previous());
		stream = null;
		
		//empty str
		stream = new TokenStream("");
		assertNull(stream.previous());
		stream = null;
		
		//reverse iteration
		stream = new TokenStream("this");
		stream.append("is","a","test","stream");
		stream.seekEnd();
		assertEquals("stream", stream.previous());
		assertEquals("test", stream.previous());
		assertEquals("a", stream.previous());
		assertEquals("is", stream.previous());
		assertEquals("this", stream.previous());
		assertNull(stream.previous());
		stream = null;
		
		//fwd and reverse
		stream = new TokenStream("this");
		stream.append("is","a","test","stream");
		stream.seekEnd();
		assertEquals("stream", stream.previous());
		stream.next();
		assertEquals("stream", stream.previous());
		assertEquals("test", stream.previous());
		assertEquals("a", stream.previous());
		stream.reset();
		assertEquals("this", stream.next());
		stream = null;
		
		//with remove
		stream = new TokenStream("this");
		stream.append("is","a","test","stream");
		stream.remove();
		stream.next();
		assertEquals("is", stream.previous());
		stream.next();
		stream.remove();
		assertEquals("is", stream.previous());
		stream = null;
		
		//with merge with previous
		stream = new TokenStream("this");
		stream.append("is","a","test","stream");
		stream.next();
		stream.mergeWithPrevious();
		assertNull(stream.previous());
		stream.next();
		assertEquals("this is", stream.previous());
		stream = null;
		
		//with merge with next
		stream = new TokenStream("this");
		stream.append("is","a","test","stream");
		stream.mergeWithNext();
		assertNull(stream.previous());
		stream.next();
		assertEquals("this is", stream.previous());
		stream = null;
	}

	/**
	 * Test method for {@link edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream#remove()}.
	 */
	@Test
	public void testRemove() {
		//remove on null
		TokenStream stream = new TokenStream((String) null);
		stream.remove();
		assertNull(stream.getAllTokens());
		stream = null;
		
		//remove on empty
		stream = new TokenStream("");
		stream.remove();
		assertNull(stream.getAllTokens());
		stream = null;
		
		//remove till empty
		stream = new TokenStream("this");
		stream.append("is","a","test","stream");
		
		int currcnt = 5;
		while (stream.hasNext()) {
			assertEquals(currcnt--, stream.getAllTokens().size());
			stream.remove();
		}
		stream = null;
		
		//remove from invalid position
		stream = new TokenStream("this");
		stream.append("is","a","test","stream");
		stream.seekEnd();
		stream.remove();
		assertEquals(5, stream.getAllTokens().size());
		stream = null;
	}

	/**
	 * Test method for {@link edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream#mergeWithPrevious()}.
	 */
	@Test
	public void testMergeWithPrevious() {
		//everything is null, empty
		TokenStream stream = new TokenStream((String) null);
		assertFalse(stream.mergeWithPrevious());
		stream = null;
		
		stream = new TokenStream("");
		assertFalse(stream.mergeWithPrevious());
		stream = null;
		
		//previous is null
		stream = new TokenStream("this");
		stream.append("is","a","test","stream");
		assertFalse(stream.mergeWithPrevious());
		
		//proper merge
		stream.seekEnd();
		stream.previous();
		assertTrue(stream.mergeWithPrevious());
		assertEquals("test stream", stream.next());
		assertEquals(4, stream.getAllTokens().size());
		stream = null;
		
		//full merge - reverse
		stream = new TokenStream("this");
		stream.append("is","a","test","stream");
		stream.seekEnd();
		stream.previous();
		assertTrue(stream.mergeWithPrevious());
		assertEquals("test stream", stream.next());
		stream.previous();
		assertTrue(stream.mergeWithPrevious());
		assertEquals("a test stream", stream.next());
		stream.previous();
		assertTrue(stream.mergeWithPrevious());
		assertEquals("is a test stream", stream.next());
		stream.previous();
		assertTrue(stream.mergeWithPrevious());
		assertEquals("this is a test stream", stream.next());
		stream.previous();
		assertFalse(stream.mergeWithPrevious());
		stream = null;
		
		//full merge - forward
		stream = new TokenStream("this");
		stream.append("is","a","test","stream");
		stream.next();
		assertTrue(stream.mergeWithPrevious());
		assertEquals("this is", stream.next());
		assertTrue(stream.mergeWithPrevious());
		assertEquals("this is a", stream.next());
		assertTrue(stream.mergeWithPrevious());
		assertEquals("this is a test", stream.next());
		assertTrue(stream.mergeWithPrevious());
		assertEquals("this is a test stream", stream.next());
		assertFalse(stream.mergeWithPrevious());
		stream = null;
	}

	/**
	 * Test method for {@link edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream#mergeWithNext()}.
	 */
	@Test
	public void testMergeWithNext() {
		//everything is null, empty
		TokenStream stream = new TokenStream((String) null);
		assertFalse(stream.mergeWithNext());
		stream = null;
		
		stream = new TokenStream("");
		assertFalse(stream.mergeWithNext());
		stream = null;
		
		//next is null
		stream = new TokenStream("this");
		stream.append("is","a","test","stream");
		stream.seekEnd();
		assertFalse(stream.mergeWithNext());
		
		//proper merge
		stream.reset();
		assertTrue(stream.mergeWithNext());
		assertEquals("this is", stream.next());
		assertEquals(4, stream.getAllTokens().size());
		stream = null;
		
		//full merge - reverse
		stream = new TokenStream("this");
		stream.append("is","a","test","stream");
		stream.seekEnd();
		stream.previous();
		stream.previous();
		assertTrue(stream.mergeWithNext());
		assertEquals("test stream", stream.next());
		stream.previous();
		stream.previous();
		assertTrue(stream.mergeWithNext());
		assertEquals("a test stream", stream.next());
		stream.previous();
		stream.previous();
		assertTrue(stream.mergeWithNext());
		assertEquals("is a test stream", stream.next());
		stream.previous();
		stream.previous();
		assertTrue(stream.mergeWithNext());
		assertEquals("this is a test stream", stream.next());
		stream.previous();
		assertFalse(stream.mergeWithNext());
		stream = null;
		
		//full merge - forward
		stream = new TokenStream("this");
		stream.append("is","a","test","stream");
		assertTrue(stream.mergeWithNext());
		assertEquals("this is", stream.next());
		stream.previous();
		assertTrue(stream.mergeWithNext());
		assertEquals("this is a", stream.next());
		stream.previous();
		assertTrue(stream.mergeWithNext());
		assertEquals("this is a test", stream.next());
		stream.previous();
		assertTrue(stream.mergeWithNext());
		assertEquals("this is a test stream", stream.next());
		assertFalse(stream.mergeWithNext());
		stream = null;
	}

	/**
	 * Test method for {@link edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream#set(java.lang.String[])}.
	 */
	@Test
	public void testSet() {
		//set on null and empty streams
		TokenStream stream = new TokenStream((String)null);
		stream.set("invalid");
		assertNull(stream.getAllTokens());
		stream = null;
		
		stream = new TokenStream("");
		stream.set("invalid");
		assertNull(stream.getAllTokens());
		stream = null;
		
		//valid posiiton, null or empty tokens
		stream = new TokenStream("this");
		stream.append("is","a","test","stream");
		stream.set((String)null);
		assertEquals("this", stream.next());
		stream.previous();
		stream.set("");
		assertEquals("this", stream.next());
		stream = null;
		
		//valid new token, invalid position
		stream = new TokenStream("this");
		stream.append("is","a","test","stream");
		stream.seekEnd();
		stream.set("valid");
		assertEquals("stream", stream.previous());
		stream = null;
		
		//correct set, single token
		stream = new TokenStream("this");
		stream.append("is","a","test","stream");
		stream.set("that");
		assertEquals(5, stream.getAllTokens().size());
		assertEquals("that", stream.next());
		stream = null;
		
		//correct set, multiple tokens at the end
		stream = new TokenStream("this");
		stream.append("is","a","test","stream");
		stream.seekEnd();
		stream.previous();
		stream.set("of", "the", "set", "method");
		assertEquals(8, stream.getAllTokens().size());
		assertEquals("method", stream.next());
		stream = null;
		
		//correct set, multiple tokens at the start
		stream = new TokenStream("this");
		stream.append("is","a","test","stream");
		stream.set("you","think","styx");
		assertEquals(7, stream.getAllTokens().size());
		assertEquals("styx", stream.next());
		stream = null;
		
		//correct set, multiple tokens in the middle
		stream = new TokenStream("this");
		stream.append("is","a","test","stream");
		stream.seekEnd();
		stream.previous();
		stream.previous();
		stream.set("really", "interesting");
		assertEquals(6, stream.getAllTokens().size());
		assertEquals("interesting", stream.next());
		assertEquals("stream", stream.next());
		assertFalse(stream.hasNext());
		stream = null;
	}

	/**
	 * Test method for {@link edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream#reset()}.
	 */
	@Test
	public void testReset() {
		//empty / null
		TokenStream stream = new TokenStream((String)null);
		stream.reset();
		assertNull(stream.next());
		stream = null;
		
		stream = new TokenStream("");
		stream.reset();
		assertNull(stream.next());
		stream = null;
		
		//positive run
		stream = new TokenStream("this");
		stream.append("is","a","test","stream");
		stream.next();
		stream.reset();
		assertEquals("this", stream.next());
		stream = null;
	}

	/**
	 * Test method for {@link edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream#seekEnd()}.
	 */
	@Test
	public void testSeekEnd() {
		//empty / null
		TokenStream stream = new TokenStream((String)null);
		stream.seekEnd();
		assertNull(stream.next());
		stream = null;
		
		stream = new TokenStream("");
		stream.seekEnd();
		assertNull(stream.next());
		stream = null;
		
		//positive run
		stream = new TokenStream("this");
		stream.append("is","a","test","stream");
		stream.seekEnd();
		assertEquals("stream", stream.previous());
		stream = null;
	}

	/**
	 * Test method for {@link edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream#merge(edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream)}.
	 */
	@Test
	public void testMerge() {
		//merge with null
		TokenStream stream1 = new TokenStream("this");
		stream1.append("is","a","test","stream");
		stream1.merge(null);
		assertEquals(5, stream1.getAllTokens().size());
		
		TokenStream stream2 = new TokenStream((String) null);
		stream1.merge(stream2);
		assertEquals(5, stream1.getAllTokens().size());
		
		stream2.merge(stream1);
		assertEquals(5, stream2.getAllTokens().size());
		stream1 = null; stream2 = null;
		
		//proper merge
		stream1 = new TokenStream("this");
		stream1.append("is","a");
		stream2 = new TokenStream("test");
		stream2.append("stream");
		
		stream1.merge(stream2);
		assertEquals(5, stream1.getAllTokens().size());
		assertEquals(5, stream1.getTokenMap().size());
		assertEquals(2, stream2.getAllTokens().size());
		assertEquals(2, stream2.getTokenMap().size());
		assertFalse(stream1.hasPrevious());
		
		for (int i = 0; i < 4; i++)
			stream1.mergeWithNext();
		
		stream1.reset();
		assertEquals("this is a test stream", stream1.next());
		stream1 = null; stream2 = null;
		
		//self merge
		stream1 = new TokenStream("this");
		stream1.append("is","a","test","stream");
		stream2 = new TokenStream("this");
		stream2.append("is","a","test","stream");
		stream1.merge(stream2);
		assertEquals(10, stream1.getAllTokens().size());
		assertEquals(5, stream1.getTokenMap().size());
		assertEquals(5, stream2.getAllTokens().size());
		assertEquals(5, stream2.getTokenMap().size());
		stream1 = null; stream2 = null;
	}

}
