/**
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
	         String serverPrimary= "ftp.ebi.ac.uk";
	       
	  
	         public Downloader(String filename){
	        	 this.filename = filename;
	        	 
	        	 if (this.filename.matches(".*pdb.*") ){
	        		 this.dossierPDB = "pub/pdb/data/structures/divided/pdb/";
	        	 }
	        	 if (this.filename.matches(".*xml.*")){
	        		 this.dossierPDB = "pub/pdb/data/structures/divided/XML/";
	        	 }
	         }
	         	
	         public void init(){
	        	 //CONNECTION AU FTP
	        	 System.out.println("file: "+filename+" dossier: "+this.dossierPDB); 
	         try {
	             client.connect(serverPrimary);
	             client.login("anonymous", "");
	             System.out.println("connected");
	             
	             execDownload();
	            
	         }catch(Exception e){ 
	        	 e.printStackTrace(); 
	        	 System.out.println("not connected");
	        	 
	        	 
	        } 
	         
	         }
	            
	         public void execDownload(){
	        	 
	        	try{
	        	 fos = new FileOutputStream("tmp/"+this.filename);
	        	 
	             //Telechargement du PDB
	             client.retrieveFile(this.dossierPDB + "00/"+ this.filename, fos);
	             
	             System.out.println("Fichier Dl !");
	         } catch (IOException e) {
	             e.printStackTrace();
	         } finally {
	             try {
	                 if (fos != null) {
	                     fos.close();
	                 }
	                 client.disconnect();
	             } catch (IOException e) {
	                 e.printStackTrace();
	             }
	         }
	         
	  			
	     }
	 
    }
