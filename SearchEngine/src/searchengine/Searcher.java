package searchengine;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
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

public class Searcher {
	final static String FIELD_PATH = getConfig("FIELD_PATH");
	final static String FIELD_CONTENTS = getConfig("FIELD_CONTENTS");
	final static String INDEX_DIRECTORY = getConfig("INDEX_DIRECTORY");

	public static void searchIndex(String keyword){
		System.out.println("Searching..");
		try{
			Analyzer analyzer = new StandardAnalyzer();
		    Path pathToIndex =Paths.get(INDEX_DIRECTORY);
		    Directory directory = FSDirectory.open(pathToIndex);
		    DirectoryReader ireader = DirectoryReader.open(directory);
		    IndexSearcher isearcher = new IndexSearcher(ireader);
		    QueryParser parser = new QueryParser(FIELD_CONTENTS, analyzer);
		    Query query = parser.parse(keyword);
		    TopDocs hits = isearcher.search(query, 1000);
		    ScoreDoc[] scoreDocs = hits.scoreDocs;
		    System.out.println("Found: " + hits.totalHits + " hits.");
		    for (int n = 0; n < scoreDocs.length; ++n) {
	            ScoreDoc sd = scoreDocs[n];
	            int docId = sd.doc;
	            Document d = isearcher.doc(docId);
	            String path = d.get("path");
	            System.out.println(path);
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
		searchIndex("foo");
	}
	
	private static String getConfig(String key) {
		return SearchEngineUtils.getConfig(key);
	}
}
