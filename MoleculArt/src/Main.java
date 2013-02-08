/*
*@author Brodusch Thomas
*
**/



public class Main {
	public static void main(String[] args){
		
			
		BDConnect DBConect = new BDConnect("T0maT0","hacka47","postgresql","localhost:5432/T0maT0");
		DBConect.init();
		
		
		
		
		
		//BDMaJ parseBd = new BDMaJ(DBConect.getStmt());
		//parseBd.init();
		
		
		String idPDB = "";
		String auteur= "";
		int dateMin =0;
		int dateMax =0;
		
		Search testRechercheAvancee = new Search(idPDB,auteur,dateMin,dateMax);
		testRechercheAvancee.init();
		
	}
}
