
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


/** ######	UNDER CONSTRUCTION ######<br/>
* Classe de recherche d'une molecule presente dans la ProteinDataBank en fonction de differents parametres (mode online).<br/>
* Utilise l'API REST de la PDB (requetes XML).<br/>
* 
* @author Brodusch Thomas
* @version 1.13.2.8
* 
*/
public class Search {
	
	   public static final String SERVICELOCATION="http://www.rcsb.org/pdb/rest/search";
	   public static final String ReportLOCATION = "http://www.rcsb.org/pdb/rest/customReport";
	   

	   public String xml="<orgPdbCompositeQuery version=\"1.0\">"; // header de la requete
	   public  String operateur ="or";
	   public String Id="";
	   public String author="";
	   public int dateMin;
	   public int dateMax;
	   /*
	    * TO-DO Ajouter les autres variables.
	    */
	      
	       	public Search(String Id,String author,int dateMin, int dateMax) {
	       		 this.Id = Id;
	        	 this.author = author;
	        	 this.dateMin = dateMin;
	        	 this.dateMax = dateMax;
	       	}
	       	
	         public void init(){
	        	//System.out.println(validFields()); /**/
	        	
	        	if( this.validFields() ){
	        		
				         try {
				            List<String> pdbIds = this.postQuery(this.xmlCooker());
			
				            System.out.println("\n\n\t\t\t#########################\n\t\t\t"
			    		   			+"Resultat de la recherche:\n\t\t\t"
			    		   			+"#########################\n\n");
				            System.out.println("\t\t     "+pdbIds.size()+" molecule(s) trouves correspondante(s) a votre recherche:\n\n");
				            
				            for (String string : pdbIds) {
				               //System.out.println(string); string=PDBiD
				               
				            	Parser pars = new Parser(string);
				            	pars.init();
				            	
				            }
				            System.out.println("\n\nFin de la recherche: "+pdbIds.size()+" molecule(s) trouves correspondante(s) a votre recherche.");
				         }catch (Exception e){ e.printStackTrace(); }
	        	 }else{ System.out.println("Veuillez renseigner au moins un champ de recherche."); }
	   }
	
/**
 * 	Verifie que les champs de la recherche ne sont pas vides (au moins un champ rempli).
 * @return Retourne "true" si l'utilisateur �� au moins renseigne un champ.
 * 
 * */
	         public boolean validFields(){ return ( (this.Id != "") || (this.author!="") || (this.dateMin != 0) || (this.dateMax != 0) ); }
		        
	         
	         
/** Prepare la requete XML (PDB XML query format) en fonctions des champs de recherche renseignes par l'utilisateur:<br/><br/>
*  - PDBiD <br/>
*  - auteur(s) <br/>
*  - release date de la molecule<br/>
*  - texte present dans le resume molecule<br/>
*  - organisme d'origine	
*  	 
*/    
	          public String xmlCooker(){
	        	
	        	if(this.Id != ""){ 
	        				 this.xml =this.xml
 			 	
 			 	+"<queryRefinement>"
 			 	+"<queryRefinementLevel>0</queryRefinementLevel>"
 			 	+"<orgPdbQuery>"
		        	 +"<version>head</version>"
		        	 +"<queryType>org.pdb.query.simple.StructureIdQuery</queryType>"
		        	 +"<runtimeStart>2013-02-08T02:43:04Z</runtimeStart>"
		        	 +"<structureIdList>"+Id+"</structureIdList>"
		        	 +"</orgPdbQuery>"
		        	 +"</queryRefinement>";
	        	 } 
		        	 if(author != ""){
		        		 	if(this.Id != ""){ this.operateur="and"; }
		        		 this.xml= this.xml		       
		        	 +"<queryRefinement>"
		        	 +"<queryRefinementLevel>1</queryRefinementLevel>"
					  +"<orgPdbQuery>"
					    +"<version>head</version>"
					    +"<queryType>org.pdb.query.simple.AdvancedAuthorQuery</queryType>"
					   +"<searchType>All Authors</searchType>"
					    +"<audit_author.name>"+this.author+"</audit_author.name>"
					    +"<exactMatch>false</exactMatch>"
					  +"</orgPdbQuery>"
					 +"</queryRefinement>";
		        	 }
		        	 
		        	 if ((this.dateMin > 1992) && (this.dateMax < 2013) ){
		        		 if(this.author != ""){ this.operateur ="and"; }
		        		 this.xml = this.xml
		        				 +"<orgPdbQuery>"
		        		    +"<version>head</version>"
		        		   +" <queryType>org.pdb.query.simple.ReleaseDateQuery</queryType>"
		        		   +" <database_PDB_rev.date.comparator>between</database_PDB_rev.date.comparator>"
		        		    +"<database_PDB_rev.date.min>"+this.dateMin+"</database_PDB_rev.date.min>"
		        		    +"<database_PDB_rev.date.max>"+this.dateMax+"</database_PDB_rev.date.max>"
		        		    +"<database_PDB_rev.mod_type.comparator><![CDATA[<]]></database_PDB_rev.mod_type.comparator>"
		        		    +"<database_PDB_rev.mod_type.value>1</database_PDB_rev.mod_type.value>"
		        		 +"</orgPdbQuery>";
		        	 }
					
		        	 
		        	// System.out.println(this.xml);
	        	 return (this.xml+"<conjunctionType>"+this.operateur+"</conjunctionType>"+"</orgPdbCompositeQuery>");
	         }
	  
/** Poste une requete XML (PDB XML query format) -> RESTful RCSB web service
* 
* @param xml
* 		requete XML finale. 
* @see #xmlCooker()
* @return une liste de PDBid.
*/
	   public List<String> postQuery(String xml) 
	      throws IOException{

	      
	      
	      URL u = new URL(SERVICELOCATION);

	      
	      String encodedXML = URLEncoder.encode(xml,"UTF-8");

	      
	      InputStream in =  doPOST(u,encodedXML);
	      
	      List<String> pdbIds = new ArrayList<String>();

	      
	      BufferedReader rd = new BufferedReader(new InputStreamReader(in));

	      String line;
	      while ((line = rd.readLine()) != null) {

	        pdbIds.add(line);
	         
	      }      
	      rd.close();

	           
	      return pdbIds;

	  
	      
	   }
	   
/** Fait une requete POST sur une URL et retourne la reponse.
* 
* 
* @param url
* @return	reponse a la requete POST.
* @throws IOException
*/
	   public static InputStream doPOST(URL url, String data)

	   throws IOException 
	   {

	   // Envoi de data.
	      
	      URLConnection conn = url.openConnection();

	      conn.setDoOutput(true);
	      
	      OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

	      wr.write(data);
	      wr.flush();

	  
	      // Retourne la reponse.
	      return conn.getInputStream();
	                
	   }
}
	   
	 


	
	


