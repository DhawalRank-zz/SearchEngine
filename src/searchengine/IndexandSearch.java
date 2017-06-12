package searchengine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import searchengine.utils.SearchEngineUtils;

public class IndexandSearch {

	final static String FILES_TO_INDEX_DIRECTORY = getConfig("FILES_TO_INDEX_DIRECTORY");
	final static String INDEX_DIRECTORY = getConfig("INDEX_DIRECTORY");
	final static String FIELD_PATH = getConfig("FIELD_PATH");
	final static String FIELD_CONTENTS = getConfig("FIELD_CONTENTS");

	public static List<String> run(String searchQuery) throws Exception {
		List<String> searchResult = new ArrayList<String>();
		File filesToIndexCheckModified = new File(FILES_TO_INDEX_DIRECTORY);
		if(filesToIndexCheckModified.isDirectory()){
			if (filesToIndexCheckModified.list().length == 0){
				System.out.println("No files present to Index!!!");
				ScheduledHTMLtoText.runHtmltoText();
				ScheduledIndexer.createIndex();
				searchResult = Searcher.searchIndex(searchQuery);    
			}
			else{
			    File fileCheckModified = new File(INDEX_DIRECTORY);
				if(fileCheckModified.isDirectory()){
					if (fileCheckModified.list().length == 0 ) {
						ScheduledIndexer.createIndex();
						searchResult = Searcher.searchIndex(searchQuery);    
					}
					else{
						searchResult = Searcher.searchIndex(searchQuery);
					}	
				}
				else{
					
					fileCheckModified.mkdirs();
					ScheduledIndexer.createIndex();
					searchResult = Searcher.searchIndex(searchQuery);
				}
			}
		}
		else{
			filesToIndexCheckModified.mkdirs();
			ScheduledHTMLtoText.runHtmltoText();
			ScheduledIndexer.createIndex();
			searchResult = Searcher.searchIndex(searchQuery);
		}
		
		return searchResult;
	}
	
	private static String getConfig(String key) {
		return SearchEngineUtils.getConfig(key);
	}
}