package de.fhwgt.quiz.loader;

import de.fhwgt.quiz.application.Catalog;
import de.fhwgt.quiz.application.Question;
import java.util.List;
import java.util.Map;

/** Tests the implementaion for Excercise 2 - WebProg Homework
 *
 * @author Eichler Artur, Koch Steffen,  Muss Andreas
 * @version 1.0 , 2017-04-22
 */
public class TestFilesystemLoader {
    
    public static void main(String args[]) throws LoaderException{
        
        //File path to Catalogs
        String location="C:\\Users\\Steffen\\Documents\\GitHub\\WebProg\\src\\catalogs\\";
        
        String selectedCatalog="simple.xml"; //simple.xml,Systemprogrammierung.xml
        
        FilesystemLoader fl=new FilesystemLoader(location);        
        
        //allCatalogsMap contains all catalogs and associate Catalog Objects
        Map<String, Catalog> allCatalogsMap=fl.getCatalogs(); 
        
        //Prints available Catalogs in directory
        System.out.println("WebProg. Excercise 2 ");
        System.out.println("---------------------");
        System.out.println("by Eichler Artur, Koch Steffen,  Muss Andreas\n");
        System.out.println("Available question catalogs from given path: " + location + "\n" + allCatalogsMap.keySet()+"\n");
        
        
        System.out.println("Questiontext for: " +selectedCatalog);
        
        Catalog q1=allCatalogsMap.get(selectedCatalog);
        List<Question> questions=q1.getQuestions();
        
        for (int i=0;i<questions.size();i++){
            System.out.println((i+1)+". "+questions.get(i).getQuestion());
        }
                
        System.out.println("\n");
        
        
    }
}
