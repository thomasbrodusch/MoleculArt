
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


/** 
* Classe de recherche d'une molecule presente dans la ProteinDataBank en fonction de differents parametres (mode online).<br/>
* Utilise l'API REST de la PDB (requetes XML).<br/>
* 
* @author Brodusch Thomas
* @version 1.13.2.12
* 
*/
public class Search {
	
	   public static final String SERVICELOCATION="http://www.rcsb.org/pdb/rest/search";
	   public static final String ReportLOCATION = "http://www.rcsb.org/pdb/rest/customReport";
	   

	   public String xml="<orgPdbCompositeQuery version=\"1.0\">"; // header de la requete
	   public  String operateur ="";
	   public String Id="";
	   public String organisme="";
	   public String author="";
	   public String dateMin="";
	   public String dateMax="";
	   public String motCle="";
	   /*
	    * TO-DO Ajouter les autres variables.
	    */
	      
	       	public Search(String Id,String organisme, String author,String dateMin, String dateMax,String motCle) {
	       		 this.Id = Id;
	       		 this.organisme = organisme;
	        	 this.author = author;
	        	 this.dateMin = dateMin;
	        	 this.dateMax = dateMax;
	        	 this.motCle = motCle;
	       	}
	       	
	         public void init(){
	        	//System.out.println(validFields()); /**/
	        	
	        	if( this.validFields() ){
	        		System.out.println("[ INFO ] - Start search ... ###\n");
				         try {
				            List<String> pdbIds = this.postQuery(this.xmlCooker());
			
				            System.out.println("\n\n\t\t\t#########################\n\t\t\t"
			    		   			+"Resultat de la recherche:\n\t\t\t"
			    		   			+"#########################\n\n");
				            System.out.println("\t\t     "+pdbIds.size()+" molecule(s) trouves correspondante(s) a votre recherche:\n\n");
				            
				            for (String string : pdbIds) {
				               //System.out.println(string); string=PDBiD
				               
				            	ParserReport pars = new ParserReport(string);
				            	pars.init();
				            	
				            }
				            System.out.println("\n\nFin de la recherche: "+pdbIds.size()+" molecule(s) trouves correspondante(s) a votre recherche.");
				         }catch (Exception e){ e.printStackTrace(); }
	        	 }else{ System.err.println("[ ERROR ] - veuillez renseigner au moins un champ de recherche."); }
	   }
	
/**
 * 	Verifie que les champs de la recherche ne sont pas vides (au moins un champ rempli).
 * @return Retourne "true" si l'utilisateur �� au moins renseigne un champ.
 * 
 */
	         public boolean validFields(){ return ( (this.Id != "") || (this.author!="") || 
	        		 (this.dateMin != "") || (this.dateMax != "") || (this.motCle != "") || (this.organisme != "")); }
		        
	         
	         
/** Prepare la requete XML (PDB XML query format) en fonctions des champs de recherche renseignes par l'utilisateur:<br/><br/>
*  - PDBiD <br/>
*  - auteur(s) <br/>
*  - release date de la molecule<br/>
*  - texte present dans le resume molecule<br/>
*  - organisme d'origine	
*  	 
*/    
	          public String xmlCooker(){
	        	this.operateur="and";
	        	if(this.Id != ""){ //Recherche PDBiD
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
		        	 if(author != ""){	//Recherche auteur
		        		 	
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
		        	 
		        	 if ((this.dateMin != "") && (this.dateMax != "") ){ //Recherche intervalle Datemin-Datemax
		        		 
		        		 this.xml = this.xml
		        				 +"<queryRefinement>"
								  +"<queryRefinementLevel>1</queryRefinementLevel>"
								  +"<conjunctionType>and</conjunctionType>"
								  +"<orgPdbQuery>"
								    +"<version>head</version>"
								    +"<queryType>org.pdb.query.simple.DepositDateQuery</queryType>"
								    +"<description><![CDATA[DepositDateQuery: database_PDB_rev.date_original.comparator=between database_PDB_rev.date_original.min=1993-01-01 database_PDB_rev.date_original.max=2006-01-01 database_PDB_rev.mod_type.comparator=< database_PDB_rev.mod_type.value=1 ]]></description>"
								    +"<queryId>83DA881B</queryId>"
								    +"<resultCount>35362</resultCount>"
								    +"<runtimeStart>2013-02-12T19:56:23Z</runtimeStart>"
								    +"<runtimeMilliseconds>530</runtimeMilliseconds>"
								    +"<database_PDB_rev.date_original.comparator>between</database_PDB_rev.date_original.comparator>"
								    +"<database_PDB_rev.date_original.min>"+this.dateMin+"</database_PDB_rev.date_original.min>"
								    +"<database_PDB_rev.date_original.max>"+this.dateMax+"</database_PDB_rev.date_original.max>"
								    +"<database_PDB_rev.mod_type.comparator><![CDATA[<]]></database_PDB_rev.mod_type.comparator>"
								    +"<database_PDB_rev.mod_type.value>1</database_PDB_rev.mod_type.value>"
								 +" </orgPdbQuery>"
								 +"</queryRefinement>";
		        	 }
		        	 
		        	 if(this.motCle != ""){	//Recherche mots-clé.
		        		 	
		        		 this.xml= this.xml		       
		        	 +"<queryRefinement>"
		        	 +"<queryRefinementLevel>1</queryRefinementLevel>"
		        	 +"<conjunctionType>and</conjunctionType>"
		        		  +"<orgPdbQuery>"
		        		   +"<version>head</version>"
		        		   +"<queryType>org.pdb.query.simple.AdvancedKeywordQuery</queryType>"
		        		   +"<description>Text Search for: chen</description>"
		        		    +"<queryId>5026F94E</queryId>"
		        		    +"<resultCount>3126</resultCount>"
		        		   +"<runtimeStart>2013-02-12T20:07:25Z</runtimeStart>"
		        		   +"<runtimeMilliseconds>57</runtimeMilliseconds>"
		        		   +"<keywords>"+this.motCle+"</keywords>"
		        		 +"</orgPdbQuery>"
		        		 +"</queryRefinement>";
		        	 }
		        	 if(this.organisme != ""){	//Recherche organisme.
		        		 	
		        		 this.xml= this.xml	
		        				 +"<queryRefinement>"
		        	  +"<queryRefinementLevel>0</queryRefinementLevel>"
		        	  +"<orgPdbQuery>"
		        	    +"<version>head</version>"
		        	    +"<queryType>org.pdb.query.simple.ExpressionOrganismQuery</queryType>"
		        	    +"<description>ExpressionOrganismQuery: entity_src_gen.pdbx_host_org_scientific_name.comparator=equals entity_src_gen.pdbx_host_org_scientific_name.value="+this.organisme+"</description>"
		        	    +"<queryId>C80B636D</queryId>"
		        	    +"<resultCount>53473</resultCount>"
		        	    +"<runtimeStart>2013-02-12T20:14:37Z</runtimeStart>"
		        	    +"<runtimeMilliseconds>667</runtimeMilliseconds>"
		        	    +"<entity_src_gen.pdbx_host_org_scientific_name.comparator>equals</entity_src_gen.pdbx_host_org_scientific_name.comparator>"
		        	    +"<entity_src_gen.pdbx_host_org_scientific_name.value>Escherichia coli</entity_src_gen.pdbx_host_org_scientific_name.value>"
		        	  +"</orgPdbQuery>"
		        	 +"</queryRefinement>";
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
	      
	      while ((line = rd.readLine()) != null) { pdbIds.add(line); }      
	      rd.close();
	      return pdbIds;
	   }
	   
/** Fait une requete POST sur une URL et retourne la reponse.
*
* @param url
* @return	reponse a la requete POST.
* @throws IOException
*/
	   public static InputStream doPOST(URL url, String data)
			   throws IOException{

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
	   
	 


	
	


