package searchengine.utils;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Properties;

import javax.naming.InitialContext;

public class SearchEngineUtils {
	static Properties props = new Properties();
	private static PrintStream out =  System.out;
	private static String CONFIG_FILE = "SearchEngine-DEV.properties";
	public static String getConfig(String key){
		if(getEnv() != null){
			CONFIG_FILE = "SearchEngine-" + getEnv() +".properties";
		}	
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
	public static String getEnv() {
		try {
			return (String) new InitialContext().lookup("java:comp/env/environment");
		} catch (Exception e) {
			return null;
		}
	}
}
