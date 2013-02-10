/** 
 * Classe de telechargement de fichier .PDB ou .XML
 * @author Brodusch Thomas
 * @version 1.13.2.10
 */
import org.apache.commons.net.ftp.FTPClient;
import java.io.IOException;
import java.io.FileOutputStream;

	  
public class Downloader {
	
	
	         FTPClient client = new FTPClient();
	         FileOutputStream fos = null;
	         String dossierPDB;
	         String filename;
	         String server;
	       
/**
 * Constructeur
 * @param filename
 * 			nom du fichier .pdb/.xml a telecharger.
 */
	         public Downloader(String filename){
	        	 this.filename = filename;
	        	 
	        	 if (this.filename.matches(".*pdb.*") ){
	        		 this.dossierPDB = "pub/pdb/data/structures/divided/pdb/";
	        	 }
	        	 if (this.filename.matches(".*xml.*")){
	        		 this.dossierPDB = "pub/pdb/data/structures/divided/XML/";
	        	 }
	         }
	         

	         	
/**
 * Initialisation
 * @see #connectServer()      
 * @see #execDownload()  
 */
	         public void init(){
	        	  System.out.println("file: "+this.filename+" dossier distant: "+this.dossierPDB); /**/
	         
	        	 this.connectServer();
	        	 this.execDownload();
	        
	         }
	         
/**
* Gestion de la connection au server FTP<br/>
* Va tester dans l'ordre si le server le plus proche EU/US/JP est up: <br/> 
* Si server est up -> connection. <br/>
* Si server est down -> test le server suivant.
*/
	         	         public void connectServer(){
	         	        	 try{
	         	        		 client.connect("ftp.ebi.ac.uk");
	         	        		 System.out.println("### connected to ftp.ebi.ac.uk - EU server ###");
	         	        	 }catch(Exception e1){ System.out.println("EU Server Down");
	         	        	 
	         	        	 		try{
	         	        	 			client.connect("ftp.wwpdb.org");
	         	        	 			System.out.println("### connected to ftp.wwpdb.org - US server ###");
	         	        	 		}catch(Exception e2){ System.out.println("US Server Down");
	         	        	 			try{
	         	        	 				client.connect("ftp.pdbj.org");
	         	        	 				System.out.println("### connected to ftp.pdbj.org - JP server ###");
	         	        	 			}catch(Exception e3){ System.out.println("JP Server Down");
	         	        	 			
	         	        	 			}//end catch e3
	         	        	 		}//end catch e2
	         	        	 }//end catch e1 
	         	        	
	         	         }//end connectServer()
	         	         

/**
 * Telechargement
 * S'occupe de rapatrier le fichier .pdb/.xml distant, 
 * dans le dossier local tmp/ de l'application.
 */
	         public void execDownload(){
	        	try{
	        	 fos = new FileOutputStream("tmp/"+this.filename);
	        	 
	             //Telechargement du PDB
	             client.retrieveFile(this.dossierPDB + "00/"+ this.filename, fos);
	             
	             System.out.println("Fichier Dl !");					/**/
	        	}catch (IOException e) { e.printStackTrace();} 
	        	
	        	finally {
	        		try {
	        			if (fos != null) {
	        				fos.close();
	        			}
	        			//Déconnection du server FTP.
	                 client.disconnect();
	        		} catch (IOException e) {e.printStackTrace();}
	        	}//end finally.
	         }//end execDownload().
	 
}//end Class Downloader.java
