package searchengine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class ScheduledIndexer {

	final static String FILES_TO_INDEX_DIRECTORY = getConfig("FILES_TO_INDEX_DIRECTORY");
	final static String INDEX_DIRECTORY = getConfig("INDEX_DIRECTORY");
	final static String FIELD_PATH = getConfig("FIELD_PATH");
	final static String FIELD_CONTENTS = getConfig("FIELD_CONTENTS");
	final static String HTML_DIRECTORY = getConfig("HTML_DIRECTORY");
	final static String HTML_TO_TEXT_DIRECTORY = getConfig("HTML_TO_TEXT_DIRECTORY");
	
	public static void main(String[] args) {
		createIndex();
	}
	public static void createIndex() {
		System.out.println("Indexing the files. This may take some time");
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

	private static String getConfig(String key) {
		return SearchEngineUtils.getConfig(key);
	}
}
