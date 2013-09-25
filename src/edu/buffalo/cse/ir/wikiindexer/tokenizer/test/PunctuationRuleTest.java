/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.tokenizer.test;

import static org.junit.Assert.*;

import java.util.Properties;

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
public class PunctuationRuleTest extends TokenizerRuleTest {
	
	public PunctuationRuleTest(Properties props) {
		super(props, IndexerConstants.PUNCTUATIONRULE);
	}
	

	@Test
	public void testRule() {
		if (rule == null) {
			 fail("Rule not implemented");
			
		} else {
			try {
				//single mark at the end, one token
				assertArrayEquals(new Object[] {"Remove at the end"}, runtest("Remove at the end."));
				assertArrayEquals(new Object[] {"Remove at the end"}, runtest("Remove at the end?"));
				assertArrayEquals(new Object[] {"Remove at the end"}, runtest("Remove at the end!"));
				
				//one token with multiple marks
				assertArrayEquals(new Object[] {"Remove from sentence 1 Remove from sentence 2"},
						runtest("Remove from sentence 1. Remove from sentence 2."));
				assertArrayEquals(new Object[] {"Remove from sentence 1 Remove from sentence 2"},
						runtest("Remove from sentence 1. Remove from sentence 2?"));
				assertArrayEquals(new Object[] {"Remove from sentence 1 Remove from sentence 2"},
						runtest("Remove from sentence 1. Remove from sentence 2!"));
				assertArrayEquals(new Object[] {"Remove from sentence 1 Remove from sentence 2"},
						runtest("Remove from sentence 1? Remove from sentence 2?"));
				assertArrayEquals(new Object[] {"Remove from sentence 1 Remove from sentence 2"},
						runtest("Remove from sentence 1? Remove from sentence 2!"));
				assertArrayEquals(new Object[] {"Remove from sentence 1 Remove from sentence 2"},
						runtest("Remove from sentence 1! Remove from sentence 2!"));
				
				//multiple tokens
				assertArrayEquals(new Object[] {"token","one","token","two"},
						runtest("token","one.","token","two."));
				assertArrayEquals(new Object[] {"token","one","token","two"},
						runtest("token","one.","token","two!"));
				assertArrayEquals(new Object[] {"token","one","token","two"},
						runtest("token","one.","token","two?"));
				assertArrayEquals(new Object[] {"token","one","token","two"},
						runtest("token","one!","token","two!"));
				assertArrayEquals(new Object[] {"token","one","token","two"},
						runtest("token","one!","token","two?"));
				assertArrayEquals(new Object[] {"token","one","token","two"},
						runtest("token","one?","token","two?"));
				
				//negative cases
				assertArrayEquals(new Object[]{"192.168.10.124"}, runtest("192.168.10.124"));
				assertArrayEquals(new Object[]{"!true"}, runtest("!true"));
				assertArrayEquals(new Object[]{"the","search","query","was","em?ty"},
						runtest("the","search","query","was","em?ty"));
				
				//mixed pos, neg, multiple
				assertArrayEquals(new Object[]{"Is","your","ip","address","192.168.10.124"}, 
						runtest("Is","your","ip","address","192.168.10.124?"));
				assertArrayEquals(new Object[]{"Your","query","'em?ty'","returned","0","results"}, 
						runtest("Your","query","'em?ty'","returned","0","results!"));
				assertArrayEquals(new Object[]{"Say", "what"},
						runtest("Say","what??!!!!??"));
				
			} catch (TokenizerException e) {
				e.printStackTrace();
			}
		}
		
	}

}
