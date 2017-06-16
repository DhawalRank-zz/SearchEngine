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
	private static List<String> searchResults = new ArrayList<String>();
	private static List<String> searchResultContents = new ArrayList<String>();

	public static void run(String searchQuery) throws Exception {
		File filesToIndexCheckModified = new File(FILES_TO_INDEX_DIRECTORY);
		if(filesToIndexCheckModified.isDirectory()){
			if (filesToIndexCheckModified.list().length == 0){
				System.out.println("No files present to Index!!!");
				ScheduledHTMLtoText.runHtmltoText();
				ScheduledIndexer.createIndex();
				Searcher.setSearchResultsAndContents(searchQuery);    
				searchResults = Searcher.getsearchResults();
				searchResultContents = Searcher.getsearchResultsContents();
			}
			else{
			    File fileCheckModified = new File(INDEX_DIRECTORY);
				if(fileCheckModified.isDirectory()){
					if (fileCheckModified.list().length == 0 ) {
						ScheduledIndexer.createIndex();
						Searcher.setSearchResultsAndContents(searchQuery);    
						searchResults = Searcher.getsearchResults();	
						searchResultContents = Searcher.getsearchResultsContents();
						
					}
					else{
						Searcher.setSearchResultsAndContents(searchQuery);    
						searchResults = Searcher.getsearchResults();					
						searchResultContents = Searcher.getsearchResultsContents();
					}	
				}
				else{
					
					fileCheckModified.mkdirs();
					ScheduledIndexer.createIndex();
					Searcher.setSearchResultsAndContents(searchQuery);    
					searchResults = Searcher.getsearchResults();				
					searchResultContents = Searcher.getsearchResultsContents();
				}
			}
		}
		else{
			filesToIndexCheckModified.mkdirs();
			ScheduledHTMLtoText.runHtmltoText();
			ScheduledIndexer.createIndex();
			Searcher.setSearchResultsAndContents(searchQuery);    
			searchResults = Searcher.getsearchResults();					
			searchResultContents = Searcher.getsearchResultsContents();
		}
		
	}
	
	private static String getConfig(String key) {
		return SearchEngineUtils.getConfig(key);
	}
	
	public static List<String> getSearchResults(){
		return searchResults;
	}
	
	public static List<String> getSearchResultsContents(){
		return searchResultContents;
	}
}