package searchengine.utils;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Properties;

public class SearchEngineUtils {
	static Properties props = new Properties();
	private static PrintStream out =  System.out;	
	private static final String CONFIG_FILE = "SearchEngine.properties";
	
	public static String getConfig(String key){
		String keyString="";
		try {

			if(props==null || props.isEmpty()){
				out.println("loading properties...");
				InputStream is = SearchEngineUtils.class.getClassLoader().getResourceAsStream(CONFIG_FILE);
		        props.load(is);
		        out.println("properties loaded successfully");
			}	

			keyString = props.getProperty(key);
			if(keyString == null) {
				out.println("ERROR: getConfig(key): Resource not found. Key= " + key);
				return "";
			} else {
				return keyString; 
			}
		} catch (Exception e) {
			out.print("ERROR: getConfig(key): Resource not found. Key= " + key + " | Exception= " + e.toString());			
			return "";
		}		
	}
}
