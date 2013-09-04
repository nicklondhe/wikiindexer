/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author nikhillo This class provides utility file lookup methods that can be
 *         safely used without worrying about OS dependencies.
 */
public class FileUtil {
	private static final String FILESEP = File.separator;

	/**
	 * Method to get the fully qualified file name for the dump file
	 * 
	 * @param props
	 *            : The loaded Properties object
	 * @return The fully qualified file name
	 */
	public static String getDumpFileName(Properties props) {
		return props.getProperty(IndexerConstants.ROOT_DIR) + FILESEP + "files"
				+ FILESEP + props.getProperty(IndexerConstants.DUMP_FILENAME);
	}

	public static Properties loadProperties(String filename) throws IOException {
		Properties props = new Properties();
		FileInputStream inStream = null;
		inStream = new FileInputStream(filename);
		props.load(inStream);
		inStream.close();
		return props;
	}
}
