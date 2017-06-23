package de.fhwgt.quiz.loader;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;

import de.fhwgt.quiz.application.Catalog;
import de.fhwgt.quiz.application.Question;
import java.io.IOException;


/** Modified Version for XML-Support
 * 
 * @author Eichler Artur, Koch Steffen,  Muss Andreas
 * @version 1.1.0 , 2017-4-22
 */
public class FilesystemLoader implements CatalogLoader {

    private File[] catalogDir;
    private final Map<String, Catalog> catalogs =
        new HashMap<String, Catalog>();
    private final String location;

    public FilesystemLoader(String location) {
        this.location = location;
    }

    @Override
    public Map<String, Catalog> getCatalogs() throws LoaderException {

        if (!catalogs.isEmpty()) {
            return catalogs;
        }
        File dir;
        // Make sure the Java package exists
        if (location != null) {
            dir = new File(location);                
        } else {
            dir = new File("/");
        }
        // Add catalog files
        if (dir.exists() && dir.isDirectory()) {            
        	
            //Creates an File Array with xml filenames from given location
            this.catalogDir = dir.listFiles(new CatalogFilter()); 
            
            for (File f : catalogDir) {
            	QuestionFileLoader fl = new QuestionFileLoader(f);
                catalogs.put(f.getName(),
                    new Catalog(f.getName(), fl));
            }
        }       

        return catalogs;
    }

    @Override
    public Catalog getCatalogByName(String name) throws LoaderException {
        if (catalogs.isEmpty()) {
            getCatalogs();
        }
        
        return this.catalogs.get(name);
    }

    private class CatalogFilter implements FileFilter {

        /**
         * Accepts only files with a .xml extension.
         */
        @Override
        public boolean accept(File pathname) {
            if (pathname.isFile() && pathname.getName().endsWith(".xml"))
                return true;
            else
                return false;
        }
    }

    private class QuestionFileLoader implements QuestionLoader {

        private final File catalogFile;
        private final List <Question> questions = new ArrayList<Question>();

        public QuestionFileLoader(File file) {
        	 
            catalogFile = file;
            
        }
        
        @Override
        public List<Question> getQuestions(Catalog catalog)
            throws LoaderException {
            if (!questions.isEmpty()) {
                return questions;
            }
            
            SAXBuilder builder = new SAXBuilder();
            try{
                Document document = (Document) builder.build(catalogFile);
                Element rootNode = document.getRootElement();
                
                for(int i=0;i<rootNode.getChildren("question").size();i++){
                    
                    Element node = (Element) rootNode.getChildren("question").get(i);
                    List<?> list = node.getChildren("answer");

                    Question question = new Question(node.getChildText("questiontext"));
                  
                    //Set timeout to question from XML-file
                    if(node.getChildText("timeout") != null){
                        question.setTimeout(Long.parseLong(node.getChildText("timeout")));
                    }
                    
                    //Get attribute from answer node and add correct and bogus answer to question
                    for (int j = 0; j < list.size(); j++) {
                        Element node2 = (Element) list.get(j);
                        //System.out.println("Text: " + node2.getText());
                        
                        Attribute value = node2.getAttribute("iscorrect");
                        if ((value.getValue().equals("true"))) {
                            //If attribute iscorrect=true then add correct answer
                            question.addAnswer(node2.getText());     
                           
                        }else{
                            //add false answer
                            question.addBogusAnswer(node2.getText());
                        }
                    }
                
                    // Make sure the question is complete
                    if (question.isComplete()){
                        // Add some randomization
                        question.shuffleAnswers();
                        questions.add(question);
                    }                                                          
                }                
            } catch (FileNotFoundException e) {
            	 System.out.println("FileNotFound-Error");
                throw new LoaderException();            
            } catch (IOException io) {
                  System.out.println(io.getMessage());
                  System.out.println("IO-Error");
            } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
            return questions;
        }
    }    
}
