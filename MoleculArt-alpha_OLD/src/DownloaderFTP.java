/** 
 * Classe de telechargement de fichier .PDB ou .XML -> FTP
 * @author Brodusch Thomas
 * @version 1.13.2.10
 */
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.util.zip.GZIPInputStream;
import java.io.OutputStream;


	  
public class DownloaderFTP {
	
	
	         public FTPClient client = new FTPClient();
	         public FileOutputStream fos = null;
	         private String dossierPDB;
	         private String lienFinal="";
	         public String filename;
	         
	       

	         public DownloaderFTP(){ }
	         

	         	
/**
 * Initialisation
 * @param filename
 * 			nom du fichier .pdb/.xml a telecharger.
 *
 * @see #connectServer()      
 * @see #execDownload()  
 */
	         public void init(String filename){
	        	 this.filename = filename;
	        	 //Connection. 
	        	 this.connectServer(); 
	        	 
	        	 //Rajout du chemin du dossier distant en fonction de la terminologie du fichier à télécharger ( pas les mêmes répertoires sur le server distant).
	        	 this.subRep();
	        	 //Download.
	        	 this.execDownload();
	        	 
	        	 //Extraction du fichier téléchargé.
	        	 
			        	this.extract();
			        	
			        	
	        	 //Reinitialisation des variable pour le prochain download.
	        	 this.lienFinal="";
	        	
	        
	         }
	         
/**
* Gestion de la connection au server FTP<br/>
* Va tester dans l'ordre si le server le plus proche EU/US/JP est up: <br/> 
* Si server est up -> connection. <br/>
* Si server est down -> test le server suivant.
*/
	         	         public void connectServer(){
	         	        	 //EU SERVER.
	         	        	 try{
	         	        		 client.connect("ftp.ebi.ac.uk",21);
	         	        		 client.login("anonymous", "");
	         	        		 this.dossierPDB = "/pub/databases/rcsb/pdb-remediated/data/structures/divided/";
	         	        		 
	         	        		 System.out.println("[ INFO ] - connected to ftp.ebi.ac.uk - EU server ###");
	         	        	 }catch(Exception e1){ System.err.println("[ ERROR ] - EU Server unreachable ###\n");
	         	        	 		//JP SERVER.
	         	        	 		try{
	         	        	 			client.connect("ftp.pdbj.org",21);
	         	        	 			client.login("anonymous", "");
	         	        	 			this.dossierPDB = "/pub/pdb/data/structures/divided/";
 	        	 				
	         	        	 			System.out.println("[ INFO ] - connected to ftp.pdbj.org - JP server ###");
	         	        	 		}catch(Exception e2){ System.err.println("[ ERROR ] - JP Server unreachable ###\n");		
	         	        	 			//US SERVER.
	         	        	 			try{
	         	        	 				client.connect("ftp.wwpdb.org",21);
	         	        	 				client.login("anonymous", "");
	         	        	 				this.dossierPDB = "/pub/pdb/data/structures/divided/";
	         	        	 			
	         	        	 				System.out.println("[ INFO ] - connected to ftp.wwpdb.org - US server ###");
	         	        	 			}catch(Exception e3){ System.err.println("[ ERROR ] - US Server unreachable ###\n");
	         	        	 			
	         	        	 			
	         	        	 			}//end catch e3
	         	        	 		}//end catch e2
	         	        	 }//end catch e1 
	         	        	
	         	         }
	         	         
/**
 * Deconnection du server ftp.
 */
	        public void disconnect(){
	        	try {
					client.disconnect();
					System.out.println("[ INFO ] - disconnected from ftp. bye :) ###\n");
					
				} catch (IOException e) {
					System.err.println("[ ERROR ] - can't disconnect from ftp. ###\n");
					e.printStackTrace();
				}
	        }
	        
/**
 * Accès au bon sous-répertoires du ftp en fonction du PDBiD
 */
	        public void subRep(){
	        	
	        	
	        	if (this.filename.matches(".*pdb.*") ){
	        		 this.lienFinal += this.dossierPDB + "pdb/" + this.filename.substring(4,6) + "/";
	        		 
	        	 }
	        	 if (this.filename.matches(".*xml.*")){
	        		 this.lienFinal += this.dossierPDB + "XML/" + this.filename.substring(1,3) + "/";
	        	 }
	        	 
	        	 
	        	 
	        	 
	        }
/**
 * Telechargement
 * S'occupe de rapatrier le fichier .pdb/.xml distant, 
 * dans le dossier local tmp/ de l'application.
 * 
 * 
 */
 
	         public void execDownload(){
	        	 
	        try{
	        	 fos = new FileOutputStream("tmp/"+this.filename);
	        	 
	             //Telechargement du PDB
	             client.retrieveFile(this.lienFinal + this.filename, fos);
	             
	             System.out.println("[ INFO ] - file from '"+this.lienFinal + this.filename+"' downloaded with success ! ###");					
	        	}catch (IOException e) { e.printStackTrace();} 
	        	
	        	finally {
	        		try {
	        			if (fos != null) {
	        				fos.close();
	        			}
	        			//Deconnection du server FTP. gerer par l'application
	                // client.disconnect();
	        		} catch (IOException e) {e.printStackTrace();}
	        	}//end finally.
	        	
	        	
	        	
	         }
	         
/**
 * Extraction du fichier precedement telecharge.	         
 * @throws Exception
 */
	 public void extract(){
		 	String source = "tmp/" + this.filename;
		 	String target ="";
		    
		 	// (1) On ne garde que le PDBiD de la molecule pour le nom de fichier de sortie.
		    // (2) On ajoute ensuite l'extension au fichier de sortie.
		    if(this.filename.matches(".*pdb.*") ){
		    	target = "tmp/"+this.filename.substring(3,7) + ".pdb";	 
		    } 
		    if(this.filename.matches(".*xml.*") ){
		    	target = "tmp/"+this.filename.substring(0,4) + ".xml";	 
		    } 
		    
		    // (3) Extraction.
		 	try{
		 		
		    GZIPInputStream in = new GZIPInputStream(new FileInputStream(source));
		   
		    OutputStream out = new FileOutputStream(target);
		    byte[] buf = new byte[1024];
		    int len;
		    
		    while ((len = in.read(buf)) > 0) {
		      out.write(buf, 0, len);
		    }
		    in.close();
		    out.close();
		    System.out.println("[ INFO ] - '"+this.filename+"' successful extracted to '"+target+"' ###");
		    }catch(Exception e){ System.err.println("[ ERROR ] - Unextract error !\n"); e.printStackTrace();}
		    
		    
		    
		    
		    // (4) On supprime le fichier archive pour ne garder que le fichier de sortie precedement cree.
		    deleteFile(this.filename);
		  
	 }
/**
 * Suppression du fichier archive telecharger.	 
 * @param file
 * 			fichier compresse telecharge de la forme: "xxxx.xml.gz" ou "xxxx.pdb.gz"
 */
	 public void deleteFile(String file){
		 File f = new File("tmp/"+file);
	        if (f.exists()) {
	            f.delete(); 
	            
	        }
	    }
	 
	 
}
