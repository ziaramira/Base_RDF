package jena;

import headers.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;





import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;

import rest.*;

public class Model1{
	
	public static void model (String path) {
		
        try {
        	
        List<Object> list1= new ArrayList<Object>();
        List<Object> input= new ArrayList<Object>();
        List<Object> output= new ArrayList<Object>();
        //String Uri="http://localhost:8080/Service3/tram/affichage";
        
       // String Uri="http://localhost:8080/Client/trains/recherche?villeDepart=alger";
            list1            = ResponseHeaderUtil.Headers(path);
            input            =(List<Object>) list1.get(5);
   		    output            = (List<Object>) list1.get(6);
       	    String uri       =(String)list1.get(0);
   		    String name      =(String)list1.get(1);
   		    String operation  =(String)list1.get(2);
   		    String date       =(String)list1.get(3);
   		    String type       =(String)list1.get(4);
   		    
   		    
   		 //test
   		 
   		  	
        	// creating a Model Rdf
   		 
        String nsA = "http://www.VocServiceREST#";
        String nsB = "http://www.OntologyDomain#"; 
        String nsC = uri; 
        
         int a=0;
		 String id=Integer.toString(a);	 
    		 
		 Model m        = ModelFactory.createDefaultModel();
		 Resource root  = m.createResource( nsC  );
		
		
		
		 // Add property
		 Property P0 = m.createProperty( nsA + "hasUri" );
		 Property P1 = m.createProperty( nsA + "hasDate" );
		 Property P2 = m.createProperty( nsA + "hasTypeRep" );
		 Property P3 = m.createProperty( nsA + "hasName" );
		 Property P4 = m.createProperty( nsA + "hasInput" );
		 Property P5 = m.createProperty( nsA + "hasOutput" );
		 Property P6 = m.createProperty( nsA + "hasOperation" );
		 Property P7 = m.createProperty( nsA + "hasTag" );
		 Property P8 = m.createProperty( nsA + "hasNameOpr" );
		 Property P9 = m.createProperty( nsA + "hasPath" );
		 
		// Property Q = m.createProperty( nsB + "Q" );
		 
		 Resource r1 = m.createResource( nsB + name );
		 Resource r2 = m.createResource(nsC + "#"+operation  );
		// Property Q = m.createProperty( nsB + "Q" );
		 //Resource x = m.createResource( nsA + "x" );
		// Resource y = m.createResource( nsA + "y" );
		// Resource z = m.createResource( nsA + "z" );
		 
		 m.add( root, P3, name);
		 m.add( root, P6, r2);
		 m.add( root, P7, r1);
		 
		 m.add( r2, P1, date);
		 m.add( r2, P2, type );
		 m.add( r2, P9,path  );
		 
		 m.add( r2, P8, operation);
		 
		
		
		
		 
		
		for(int k=0;k<input.size();k++){
		  if((String) input.get(k) != null){
			  String inpt=TestInput.getInput((String)input.get(k));
			  
			 m.add( r2, P4, inpt); 
		  }
		 }
		  
   		 for(int i=0;i<output.size();i++){
  			String [] listWord=((String) output.get(i)).split(" ");
  			for(int j=0;j<listWord.length;j++){
  				if(j%2==0){
  				String word=listWord[j];
  				 m.add( r2, P5,  word);
  			           	}	
      	                                     }
		
   		                                  }
		
		 m.setNsPrefix( "rest", nsA );
		 m.write( System.out );
		 
		
		 
		 FileWriter fich = null;
	  		try {
	  			fich = new FileWriter("BASE_RDF.xml");
	  		} catch (IOException e) {
	  			// TODO Auto-generated catch block
	  			e.printStackTrace();
	  		}
	  	    BufferedWriter stock = new BufferedWriter(fich);
	  		
	  	    /////////stock le model dans le fichier//////////
	  	    
	  	    m.write(stock);
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
		 
        }catch (Exception e) {
            System.out.println("Failed: " + e);
        }
        }
	public static void main(String [] args){
		Model1.model("http://localhost:8080/Client/trains/affichage");
	}
        }
	

