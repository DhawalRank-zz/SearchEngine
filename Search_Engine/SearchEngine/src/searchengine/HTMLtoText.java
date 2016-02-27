package searchengine;
import java.io.*;
import javax.swing.text.html.*;
import javax.swing.text.html.parser.*;

public class HTMLtoText extends HTMLEditorKit.ParserCallback {
 StringBuffer s;

 public HTMLtoText() {}

 public void parse(Reader in) throws IOException {
   s = new StringBuffer();
   ParserDelegator delegator = new ParserDelegator();
   // the third parameter is TRUE to ignore charset directive
   delegator.parse(in, this, Boolean.TRUE);
 }

 public void handleText(char[] text, int pos) {
   s.append(text);
 }

 public String getText() {
   return s.toString();
 }

 public static void main(String args[]){
   try {  
    File folder= new File("E:/UoW/Courses/Fall 2015/Advanced Computing Concepts/Project/Search Engine/SearchEngine/W3C Web Pages/");
	for(File file:folder.listFiles())
    {
     String name=file.getName();
     //System.out.println(name);
     String[] file_name=name.split("\\.htm");
    String file_name1=file_name[0];
    System.out.println(file_name1);
    BufferedReader reader = new BufferedReader(new FileReader(file));
    String inputLine;
    String finalContents = "";
    while ((inputLine = reader.readLine()) != null) {
     finalContents += "\n" + inputLine.replace("<br", "\n<br");
    }
    reader.close();
    BufferedWriter writer = new BufferedWriter(new FileWriter(file_name1+".htm"));
    writer.write(finalContents);
    writer.close();
   // System.out.println(finalContents);

     FileReader in = new FileReader(name);
     HTMLtoText parser = new HTMLtoText();
     parser.parse(in);
     in.close();
     String textHTML = parser.getText();
  //   System.out.println(textHTML);
     
     // Write the text to a file  
     BufferedWriter writerTxt = new BufferedWriter(new FileWriter("E:/UoW/Courses/Fall 2015/Advanced Computing Concepts/Project/Search Engine/SearchEngine/W3C Web Pages/Text/"+file_name1+".txt"));
     writerTxt.write(textHTML);
     writerTxt.close();
   }
   }
   catch (Exception e) {
     e.printStackTrace();
   }
 }
}