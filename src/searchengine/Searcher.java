package searchengine;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import searchengine.utils.SearchEngineUtils;

public class Searcher {
	final static String FIELD_PATH = getConfig("FIELD_PATH");
	final static String FIELD_CONTENTS = getConfig("FIELD_CONTENTS");
	final static String INDEX_DIRECTORY = getConfig("INDEX_DIRECTORY");
	final static String DOC_TITLE = getConfig("DOC_TITLE");
	final static int FRAG_SIZE = Integer.parseInt(getConfig("FRAG_SIZE"));
	private static List<String> searchResults = new ArrayList<String>();
	private static List<String> searchResultsContents = new ArrayList<String>();

	
	public static void searchIndex(String keyword){
		System.out.println("Searching..");
		try{
			Analyzer analyzer = new StandardAnalyzer();
		    Path pathToIndex =Paths.get(INDEX_DIRECTORY);
		    Directory directory = FSDirectory.open(pathToIndex);
		    DirectoryReader ireader = DirectoryReader.open(directory);
		    IndexSearcher isearcher = new IndexSearcher(ireader);
		    QueryParser parser = new QueryParser(FIELD_CONTENTS, analyzer);
		    parser.setDefaultOperator(QueryParser.Operator.AND);
		    Query query = parser.parse(keyword);
		    TopDocs hits = isearcher.search(query, 1000);
		    ScoreDoc[] scoreDocs = hits.scoreDocs;
		    System.out.println("Found: " + hits.totalHits + " hits.");
		    for (int n = 0; n < scoreDocs.length; ++n) {
	            ScoreDoc sd = scoreDocs[n];
	            int docId = sd.doc;
	            Document d = isearcher.doc(docId);
	            String docContents = d.get(FIELD_CONTENTS);
	            searchResultsContents.add(getHighlightedField(query, analyzer, FIELD_CONTENTS, docContents));
	            String path = d.get(FIELD_PATH);
	            searchResults.add(path);
	        }
		    ireader.close();
		    directory.close();
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String a[]){
		
		//this assumes that files are present in index directory
		searchIndex("w3");
	}
	
	private static String getConfig(String key) {
		return SearchEngineUtils.getConfig(key);
	}
	
	public static List<String> getsearchResults(){
		return searchResults;
	}
	
	public static void setSearchResultsAndContents(String keyword){
		searchResults = new ArrayList<String>();
		searchResultsContents = new ArrayList<String>();
		searchIndex(keyword);
	}
	
	public static List<String> getsearchResultsContents(){
		return searchResultsContents;
	}
	
    public static String getHighlightedField(Query query, Analyzer analyzer, String fieldName, String fieldValue) throws IOException, InvalidTokenOffsetsException
    {
         Formatter formatter = new SimpleHTMLFormatter();
         QueryScorer queryScorer = new QueryScorer(query);
         Highlighter highlighter = new Highlighter(formatter, queryScorer);
         highlighter.setTextFragmenter(new SimpleSpanFragmenter(queryScorer, FRAG_SIZE));
         highlighter.setMaxDocCharsToAnalyze(Integer.MAX_VALUE);
         return highlighter.getBestFragment(analyzer, fieldName, fieldValue).replaceAll("\"", "'") + ("...");
     }
}
