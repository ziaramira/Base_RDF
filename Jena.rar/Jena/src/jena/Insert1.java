package jena;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.update.UpdateAction;
import com.hp.hpl.jena.util.FileManager;

import org.apache.jena.iri.impl.Main;

import rest.*;
import headers.*;
/**
 *
 * @author Seddam
 */
public class Insert1{

    /**
     * @param args the command line arguments
     */
    public static void Insert(String path) {

        org.apache.log4j.Logger.getRootLogger().setLevel(org.apache.log4j.Level.OFF);

        FileManager.get().addLocatorClassLoader(Main.class.getClassLoader());
        Model model = FileManager.get().loadModel("base_rdf.xml");
        // insert into a model
     	
       // String Uri="http://localhost:8080/Service3/bus/recherche?villeDepart=Alger";
        List<Object> list1= new ArrayList<Object>();
        List<Object> input= new ArrayList<Object>();
        List<Object> output= new ArrayList<Object>();
            list1            = ResponseHeaderUtil.Headers(path);
            input            =(List<Object>) list1.get(5);
   		    output            = (List<Object>) list1.get(6);
       	    String uri       =(String)list1.get(0);
   		    String name      =(String)list1.get(1);
   		    String operation  =(String)list1.get(2);
   		    String date       =(String)list1.get(3);
   		    String type       =(String)list1.get(4);
   		    
   		    
   		   
   		    
   		 
   		
        
        ///////////////////////////////////
   		 
   		int a=1;
		String id=Integer.toString(a);	
		// insert paramettre
       String insertString 
        		= "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                  "PREFIX rest: <http://www.VocServiceREST#>" +
                  
        		" INSERT DATA "                                                 +
        		" { <"+uri+">   rest:hasName \""+name+"\"; "          +
        		"                          rest:hasOperation <"+uri+"#"+operation+"> ; "    +
        		"                          rest:hasTag <http://www.OntologyDomain#"+name+"> . "         +
        		
        		 "<"+uri+"#"+operation+"> rest:hasDate \""+date+"\";" + 
        		 "                          rest:hasTypeRep \""+type+"\"; "         +
        		 "                          rest:hasNameOpr \""+operation+"\"; "         +
        		 "                          rest:hasPath \""+path+"\"; "         +
        		 
        		" } ";
       UpdateAction.parseExecute(insertString, model);
		//insert Input
       for(int k=0;k<input.size();k++){
 		  if((String) input.get(k) != null){
 			  String inpt=TestInput.getInput((String)input.get(k));
        		 
        	String insertString1 = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                  "PREFIX rest: <http://www.VocServiceREST#>" +
                  
        " INSERT DATA "                                                 +
        " { <"+uri+"#"+operation+"> rest:hasInput \""+inpt+"\" . "  +
       

        " } ";
        		
                		
                		UpdateAction.parseExecute(insertString1, model);
                		}
 		                     }
        //insert output
        		
       for(int i=0;i<output.size();i++){
 			String [] listWord=((String) output.get(i)).split(" ");
 			  for(int j=0;j<listWord.length;j++){
 				   if(j%2==0){
 				String word=listWord[j];
       		 
       String	insertString2 = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                 "PREFIX rest: <http://www.VocServiceREST#>" +
                 
       " INSERT DATA "                                                 +
       " { <"+uri+"#"+operation+"> rest:hasOutput \""+word+"\" . "  +
      

       " } ";
       		
               		
               		UpdateAction.parseExecute(insertString2, model);
               		}
 			}
       }
        // select table of base rdf

     /*   String qs
                = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" 
                +  "PREFIX j.0: <http://burningbird.net/postcon/elements/1.0/#>"  
                 + "PREFIX rest: <http://burningbird.net/postcon/elements/1.0/>"  
                 + " SELECT ?subject ?predicate ?object WHERE {"
                  +  " ?subject ?predicate ?object ." 
                  +  "}";

        Query q = QueryFactory.create(qs);
        QueryExecution qexec = 

QueryExecutionFactory.create(q, model);

        try {
            ResultSet res = qexec.execSelect();

            ResultSetFormatter.out(System.out, res,q);*/
            // enregistrer dans un fichier
            FileWriter fich = null;
	  		try {
	  			fich = new FileWriter("base_rdf.xml");
	  		} catch (IOException e) {
	  			// TODO Auto-generated catch block
	  			e.printStackTrace();
	  		}
	  	    BufferedWriter stock = new BufferedWriter(fich);
	  	    
	  	//Affichage du resultat
         /*   while (res.hasNext()) {

                QuerySolution row = (QuerySolution)res.next();

                RDFNode c = row.get("y");
                System.out.print(c.toString());

            }*/
	  		
	  	    /////////stock le model dans le fichier//////////
	  	    
	  	    model.write(stock);
	  		try {
	  			stock.newLine();
	  		} catch (IOException e) {
	  			// TODO Auto-generated catch block
	  			e.printStackTrace();
	  		}
	  		try {
	  			stock.close();
	  		} catch (IOException e) {
	  			// TODO Auto-generated catch block
	  			e.printStackTrace();
	  		}
		      
       /* finally {
            // QueryExecution objects should be   closed to free any system resources

        qexec.close();
        }*/
    }
    public static void main(String[] args) {
    	
    	Insert1.Insert("http://localhost:8080/Service1/taxi/recherche?villeDepart=setif");
    }
}