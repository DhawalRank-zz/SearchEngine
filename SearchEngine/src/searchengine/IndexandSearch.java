package searchengine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Scanner;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class IndexandSearch {

	public static final String FILES_TO_INDEX_DIRECTORY = "filesToIndex";
	public static final String INDEX_DIRECTORY = "indexDirectory";

	public static final String FIELD_PATH = "path";
	public static final String FIELD_CONTENTS = "contents";

	public static void main(String[] args) throws Exception {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter Search query: ");
		String keyword = in.next();
		in.close();
	    File fileCheckModified = new File(INDEX_DIRECTORY);
		if(fileCheckModified.isDirectory()){
			long diff = new Date().getTime() - fileCheckModified.lastModified();
			if (diff > 24 * 60 * 60 * 1000 || fileCheckModified.list().length == 0 ) {
				createIndex();
				searchIndex(keyword);    
			}
			else{
				System.out.println("Recently indexed files found. Skipping indexing....");
				searchIndex(keyword);
			}	
		}
		else{
			
			fileCheckModified.mkdirs();
			createIndex();
			searchIndex(keyword);
		}

	}
	private static void createIndex(){
		try{
			Analyzer analyzer = new StandardAnalyzer();
		    Path pathToIndex =Paths.get(INDEX_DIRECTORY);
		    Directory directory = FSDirectory.open(pathToIndex);
			IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
		    iwc.setOpenMode(OpenMode.CREATE);
		    iwc.setRAMBufferSizeMB(512.0);
		    IndexWriter iwriter = new IndexWriter(directory, iwc);
		    System.out.println("Indexing files....");
			File dir = new File(FILES_TO_INDEX_DIRECTORY);
			File[] files = dir.listFiles();
			for (File file : files){
			    Document document = new Document();
			    String path = file.getCanonicalPath();
				document.add(new Field(FIELD_PATH, path, TextField.TYPE_STORED));
				String content = "";
				BufferedReader br = new BufferedReader(new FileReader(file));
				String currentLine = br.readLine();
				while(currentLine != null){
					content = content + currentLine;
					currentLine = br.readLine();
				}
				br.close();
				document.add(new Field(FIELD_CONTENTS, content, TextField.TYPE_STORED));
				iwriter.addDocument(document);			
			}
			iwriter.commit();
			iwriter.close();
			directory.close();
		}
	    catch (Exception e){
	    	e.printStackTrace();
	    }
	}
	
	private static void searchIndex(String keyword){
		try{
			Analyzer analyzer = new StandardAnalyzer();
		    Path pathToIndex =Paths.get(INDEX_DIRECTORY);
		    Directory directory = FSDirectory.open(pathToIndex);
			System.out.println("Searching....");
		    DirectoryReader ireader = DirectoryReader.open(directory);
		    IndexSearcher isearcher = new IndexSearcher(ireader);
		    // Parse a simple query that searches for "text":
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
}