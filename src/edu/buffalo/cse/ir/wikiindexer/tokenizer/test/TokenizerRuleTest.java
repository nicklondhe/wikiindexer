/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.tokenizer.test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import edu.buffalo.cse.ir.wikiindexer.IndexerConstants;
import edu.buffalo.cse.ir.wikiindexer.test.PropertiesBasedTest;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.RuleClass;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule;

/**
 * @author nikhillo
 *
 */
@RunWith(Parameterized.class)
public class TokenizerRuleTest extends PropertiesBasedTest {
	protected TokenizerRule rule;
	protected boolean isPreTokenization;
	
	private static Set<String> preTknRuleSet;
	
	public TokenizerRuleTest(Properties props, String constantName) {
		super(props);
		
		if (preTknRuleSet == null) {
			String rules = idxProps.getProperty(IndexerConstants.PRETKNRULES);
			if (rules != null) {
				String [] splits = rules.split(",");
				preTknRuleSet = new HashSet<String>(Arrays.asList(splits));
			}
		}
		
		String className = idxProps.getProperty(constantName);
		if (className != null) {
			try {
				Class cls = Class.forName(className);
				Constructor[] cnstrs = cls.getDeclaredConstructors();
				Class[] ptypes;
				RuleClass rclass = (RuleClass) cls.getAnnotation(RuleClass.class);
				String rval = rclass.className().toString();
				isPreTokenization =  (preTknRuleSet != null && preTknRuleSet.contains(rval));
				
				for (Constructor temp : cnstrs) {
					ptypes = temp.getParameterTypes();
					if (ptypes.length == 0) {
						rule = (TokenizerRule) temp.newInstance(null);
						break;
					} else if (ptypes.length == 1 && "java.util.Properties".equals(ptypes[0].getName())) {
						rule = (TokenizerRule) temp.newInstance(idxProps);
						break;
					} else {
						System.err.println("Unsupported constructor: Should be parameter less or use Properties");
					}
				}
				
				
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	protected Object[] runtest(String... input) throws TokenizerException {
		TokenStream stream = new TokenStream(input[0]);
		if (input.length > 1) {
			stream.append(Arrays.copyOfRange(input, 1, input.length));
		}
		
		rule.apply(stream);
		Collection<String> strtokens = stream.getAllTokens();
		return (strtokens != null) ? strtokens.toArray() : new Object[]{};
	}
}
