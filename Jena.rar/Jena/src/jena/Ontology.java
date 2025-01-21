package jena;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

public class Ontology {
	   /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
    	 List<String> lclass=new ArrayList<>();
    	 List<String> lsubclass=new ArrayList<>();
    	 lclass=Ontology.getClas("train");
    	 for(int i=0;i<lclass.size();i++){
    		System.out.println("les superclasss"+lclass.get(0));
    		 lsubclass=Ontology.getSubClas(lclass.get(0));
    		 for(int j=0;j< lsubclass.size();j++){
    			 System.out.println("les sous class"+lsubclass.get(j));
    		 }
    		 }
    	 }
    	
    public static List<String> getClas(String val) throws IOException{
    	 List<String> lclass=new ArrayList<>();
    	 List<String> lexeption=new ArrayList<>();
    	
         String fileName="ontologie.xml";
    try{
        File file=new File(fileName);
        //lire l'ontologie Domaine
        FileReader r=new FileReader(file);
        OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM_RDFS_INF);
        model.read(r,null);
       
        //lister tous les super classes
        ExtendedIterator classes = model.listClasses();
        while (classes.hasNext())
        {
          OntClass thisClass = (OntClass) classes.next();
          String name=thisClass.getLocalName();
          //lister tous les sous classes
          for (Iterator i = thisClass.listSubClasses(); i.hasNext(); ) {
              OntClass c = (OntClass) i.next();
              if(c.getLocalName().equals(val))
            	  lclass.add(name)	;  
             
            }
          
        }
        return lclass;
    }catch(Exception e){
    	  //System.out.println(e.toString());
    	lexeption.add(e.toString());
    	return lexeption;
    	//e.printStackTrace();
    }
   // return lclass;
    } 
    public static List<String> getSubClas(String val) throws IOException{
   	 List<String> lclass=new ArrayList<>();
   	 List<String> lexeption=new ArrayList<>();
   	 
   	 
     String fileName="ontologie.xml";
   try{
       File file=new File(fileName);
       //lire l'ontologie Domaine
       FileReader r=new FileReader(file);
       OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM_RDFS_INF);
       model.read(r,null);
     
       //lister tous les sous classes
       ExtendedIterator classes = model.listClasses();
       while (classes.hasNext())
       {
         OntClass thisClass = (OntClass) classes.next();
         String name=thisClass.getLocalName();
         //
         
         if(name.equals(val)){
             for (Iterator i = thisClass.listSubClasses(); i.hasNext(); ) {
                 OntClass c = (OntClass) i.next();
                 String subName=c.getLocalName();
                 lclass.add(subName);
                
               }
         }
       }
       return lclass;
   }catch(Exception e){
   //	e.printStackTrace();
	   lexeption.add(e.toString());
	   return lexeption;
   }
   
   }
}
