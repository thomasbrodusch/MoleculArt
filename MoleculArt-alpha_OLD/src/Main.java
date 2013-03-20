
/*
*@author Brodusch Thomas
*
**/



public class Main {
	public static void main(String[] args){
		
<<<<<<< HEAD
			
		BDConnect DBConect = new BDConnect("T0maT0","root","postgresql","localhost:5432/T0maT0");
		DBConect.init();
=======
		//Connection base de donnee locale.
		//BDConnect DBConect = new BDConnect("baseDeDonnee","password","postgresql","localhost:5432/nom_database");
		//DBConect.init();
>>>>>>> bêta change
		
		/*/*
		//MÀJ BDD locale.
		BDMaJ parseBd = new BDMaJ(DBConect.getStmt());
		parseBd.init();
		*/
		
//////////////////////////////////////////////////////////////////////

		//DownloaderFTP.
//		DownloaderFTP dLftp = new DownloaderFTP();
//		dL.init("154l.xml.gz");
		
		//DownloaderHTTP. ------// PLUS RAPIDE QUE PAR FTP haha ;) //----
		DownloaderHTTP dLhttp1 = new DownloaderHTTP("100d","xml");
			dLhttp1.init();
//			
//		DownloaderHTTP dLhttp2 = new DownloaderHTTP("100d","pdb");
//			dLhttp2.init();
		
//////////////////////////////////////////////////////////////////////

		
		String idPDB = "100D";	
		String organisme="";
		String auteur= "";
		String dateMin ="";
		String dateMax ="";
		String motCle="";
		
		//Recherche avancee.
		Search testRechercheAvancee = new Search(idPDB,organisme,auteur,dateMin,dateMax,motCle);
		testRechercheAvancee.init();
		
		
	}
}

