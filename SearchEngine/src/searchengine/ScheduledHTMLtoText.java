package searchengine;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;

import org.jsoup.Jsoup;


public class ScheduledHTMLtoText extends HTMLEditorKit.ParserCallback {
	StringBuffer s;
	final static String HTML_DIRECTORY = getConfig("HTML_DIRECTORY");
	final static String HTML_TO_TEXT_DIRECTORY = getConfig("FILES_TO_INDEX_DIRECTORY");

	public ScheduledHTMLtoText() {}

	public void parse(Reader in) throws IOException {
		s = new StringBuffer();
		ParserDelegator delegator = new ParserDelegator();
		// the third parameter is TRUE to ignore charset directive
		delegator.parse(in, this, Boolean.TRUE);
	}

	public String getText() {
		return s.toString();
	}

	public static void runHtmltoText(){
		try {  
			System.out.println("Converting HTML to test. This may take sometime.");
			File folder = new File(HTML_DIRECTORY);

			for(File file : folder.listFiles())
			{
				if(!file.isDirectory()){
					String name = file.getName();
					String[] fileName = name.split("\\.htm");
					String outFileName = fileName[0];
					System.out.println(outFileName);
					BufferedReader reader = new BufferedReader(new FileReader(file));
					String inputLine;
					String finalContents = "";
					while ((inputLine = reader.readLine()) != null) {
						finalContents += Jsoup.parse(inputLine).text();
					}
					reader.close();
					BufferedWriter writerTxt = new BufferedWriter(new FileWriter(HTML_TO_TEXT_DIRECTORY + outFileName + ".txt"));
					writerTxt.write(finalContents);
					writerTxt.close();
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String args[]){
		runHtmltoText();
	}
	private static String getConfig(String key) {
		return SearchEngineUtils.getConfig(key);
	}
}