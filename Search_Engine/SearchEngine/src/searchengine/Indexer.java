package searchengine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
class Indexer
{
	
	private static BufferedReader reader;

	public static void main(String a[]) throws IOException{


	Map<String, Integer> content = new HashMap<String, Integer>();
	Map<Integer, Map<String, Integer>> index = new HashMap<Integer, Map<String, Integer>>();
	PrintWriter out=new PrintWriter("E:/UoW/Courses/Fall 2015/Advanced Computing Concepts/Project/Search Engine/SearchEngine/Index.log");
	File folder = new File("E:/UoW/Courses/Fall 2015/Advanced Computing Concepts/Project/Search Engine/SearchEngine/W3C Web Pages/Text/");
	for(File file:folder.listFiles()) {
		String s;
		String token[];
		String fname=file.getPath();
		int hashcodeFname=fname.hashCode();
		reader = new BufferedReader(new FileReader(file));
		while ((s=reader.readLine()) != null)
		{
			s.replaceAll("\\.","");
			token=s.split(" ");
			for (String string : token) {
			    if(content.keySet().contains(string))
			    {
			        content.put(string, content.get(string)+1);

			    }else
			    {
			        content.put(string, 1);
			    }
			}
			index.put(hashcodeFname, content);
		 }      
		}
		Arrays.sort(index);
		out.println(index);
		out.close();
	}
	
}