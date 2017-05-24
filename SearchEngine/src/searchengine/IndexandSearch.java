package searchengine;

import java.io.File;
import java.util.Date;
import java.util.Scanner;

public class IndexandSearch {

	final static String FILES_TO_INDEX_DIRECTORY = getConfig("FILES_TO_INDEX_DIRECTORY");
	final static String INDEX_DIRECTORY = getConfig("INDEX_DIRECTORY");
	final static String FIELD_PATH = getConfig("FIELD_PATH");
	final static String FIELD_CONTENTS = getConfig("FIELD_CONTENTS");

	public static void main(String[] args) throws Exception {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter Search query: ");
		String keyword = in.next();
		in.close();
		File filesToIndexCheckModified = new File(FILES_TO_INDEX_DIRECTORY);
		if(filesToIndexCheckModified.isDirectory()){
			long diffFilesToIndexDirectoryCheck = new Date().getTime() - filesToIndexCheckModified.lastModified();
			if (diffFilesToIndexDirectoryCheck > 24 * 60 * 60 * 1000 || filesToIndexCheckModified.list().length == 0){
				System.out.println("No files present to Index!!!");
				ScheduledHTMLtoText.runHtmltoText();
				ScheduledIndexer.createIndex();
				Searcher.searchIndex(keyword);    
			}
			else{
			    File fileCheckModified = new File(INDEX_DIRECTORY);
				if(fileCheckModified.isDirectory()){
					long diffFileCheckModified = new Date().getTime() - fileCheckModified.lastModified();
					if (diffFileCheckModified > 24 * 60 * 60 * 1000 || fileCheckModified.list().length == 0 ) {
						ScheduledIndexer.createIndex();
						Searcher.searchIndex(keyword);    
					}
					else{
						Searcher.searchIndex(keyword);
					}	
				}
				else{
					
					fileCheckModified.mkdirs();
					ScheduledIndexer.createIndex();
					Searcher.searchIndex(keyword);
				}
			}
		}
		else{
			filesToIndexCheckModified.mkdirs();
			ScheduledHTMLtoText.runHtmltoText();
			ScheduledIndexer.createIndex();
			Searcher.searchIndex(keyword);
		}
	}
	
	private static String getConfig(String key) {
		return SearchEngineUtils.getConfig(key);
	}
}