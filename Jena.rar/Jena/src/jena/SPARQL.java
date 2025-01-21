package jena;

import org.apache.log4j.chainsaw.Main;

import jena.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.util.FileManager;

public class SPARQL {
	
	



public static String search(String qs, String value ){
		
	org.apache.log4j.Logger.getRootLogger().setLevel(org.apache.log4j.Level.OFF);

	FileManager.get().addLocatorClassLoader(Main.class.getClassLoader());
	//créer un modele à partir de la base RDF
	Model model = FileManager.get().loadModel("BASE_RDF.xml");
   //Générer une requete SPARQL pour séléctionner tous les ressource
 /* String qs
            = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" 
             
             + "PREFIX  rest: <http://www.VocServiceREST#>"  
             + " SELECT ?x  WHERE {"
              +  " ?x ?y ?z ." 
              +  "}";*/

    Query q = QueryFactory.create(qs);
    //executer le requete
    QueryExecution qexec = QueryExecutionFactory.create(q, model); 
    
    try{
    	//récupere le résultats
    ResultSet res = qexec.execSelect();
  
    
    StringBuilder builder = new StringBuilder();
      while(res.hasNext()) {
       
          QuerySolution row = res.nextSolution();
           RDFNode c = row.get(value);
          
          builder.append(c);
          builder.append('\n');
        
           
         
      }
      return builder.toString();
    }
    finally {
	    // QueryExecution objects should be closed to free any system resources
	    qexec.close();
	}
     
   
    
    
    
    
    

}
public static  void main (String [] args) throws IOException{
	
	 String req="PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
				+ "PREFIX rest: <http://www.VocServiceREST#>"
				+ "SELECT  ?path   WHERE {"
			       	+"{"
				 
                     +  " ?x  rest:hasName \"train\" ." 
                     +" OPTIONAL { ?x ?y  ?o } ."
                     +"?o rest:hasPath ?path."
				 +  "}"
				 +"UNION"
				          +"{"
				          +  " ?x  rest:hasName \"bus\" ." 
		                     +" OPTIONAL { ?x ?y  ?o } ."
		                     +"?o rest:hasPath ?path."
				          +  "}"
			   +"UNION"
				          +"{"
				          +  " ?x  rest:hasName \"velo\" ." 
		                     +" OPTIONAL { ?x ?y  ?o } ."
		                     +"?o rest:hasPath ?path."
				          +  "}"
				          +"UNION"
				          +"{"
				          +  " ?x  rest:hasName \"public\" ." 
		                     +" OPTIONAL { ?x ?y  ?o } ."
		                     +"?o rest:hasPath ?path."
				          +  "}"
		         +  "}";
	 String R=SPARQL .search(req,"path");
	 System.out.println(req);
		System.out.println(R);
		
}
				
		 }

