import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
*@author Brodusch Thomas
*
**/



public class Main {
	public static void main(String[] args){
		
			
		BDConnect DBConect = new BDConnect("T0maT0","root","postgresql","localhost:5432/T0maT0");
		DBConect.init();
		
		
		Downloader dL = new Downloader("300d.xml.gz");
		dL.init();
		
		//BDMaJ parseBd = new BDMaJ(DBConect.getStmt());
		//parseBd.init();
		
		/*
		String idPDB = "154L";
		String auteur= "";
		int dateMin =0;
		int dateMax =0;
		
		Search testRechercheAvancee = new Search(idPDB,auteur,dateMin,dateMax);
		testRechercheAvancee.init();
		*/
	}
}

