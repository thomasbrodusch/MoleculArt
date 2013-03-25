package controle;

/**
 * Classe de MÀJ de la BDD application.
 *	@authors Brodusch Thomas & Lavergne Kevin
 *	@version 1.0
 * 
 * 
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.sql.*;

public class BDMaJ {
	private Statement stmt;

	public static final String ReportLOCATION = "http://www.rcsb.org/pdb/rest/customReport";
	public static final String searchLOCATION="http://www.rcsb.org/pdb/rest/search";
	public static final String xmlRequete="<orgPdbQuery>"
			+ "	<queryType>org.pdb.query.simple.HoldingsQuery</queryType>"
			+ "	<description>Holdings : All Structures</description>"
			+ "	<experimentalMethod>ignore</experimentalMethod>"
			+ "	<moleculeType>ignore</moleculeType>" + "</orgPdbQuery>";
	
	
	
	public BDMaJ(Statement stmt){
		this.stmt = stmt;
	}
	/**
	 *  Initialisation du Parseur.
	 */
	public void init(){
		try{
			for(String id:postQuery())
			{
				this.parseData(id); // si on remplace id par un id mol��cule sa fait la recherche ?! note: alors pas besoin de database intermediaire ?!
			}
		}catch(IOException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static List<String> postQuery() throws Exception{
		List<String> pdbIds = new ArrayList<String>();
		URL u = new URL(searchLOCATION);
		String encodedXML = URLEncoder.encode(xmlRequete, "UTF-8");
		InputStream in = doPOST(u, encodedXML);
		BufferedReader rd = new BufferedReader(new InputStreamReader(in));
		String line;
			
		while ( (line = rd.readLine()) != null ) {
			pdbIds.add(line);
		}
		rd.close();
		return pdbIds;

	}

	
	
	public static InputStream doPOST(URL url, String data) throws IOException {
			// Envoi data.
		URLConnection conn = url.openConnection();
		conn.setDoOutput(true);
		OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		wr.write(data);
		wr.flush();
			// R��cup��re la r��ponse.
		return conn.getInputStream();

	}
	
	public void parseData(String id) throws IOException,Exception {
			// Parsing des infos mol��cules de la PDB.
		String qstr = "?pdbids="+id+"&customReportColumns=structureId,structureTitle,structureAuthor,releaseDate&format=csv&service=wsfile";
		String urlStr = ReportLOCATION + qstr;
		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		
		if ( conn.getResponseCode() != 200 ) {
			throw new IOException(conn.getResponseMessage());
		}
		
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		String[] attributs = new String[4];
		
		int i;
		
		while ((line = rd.readLine()) != null) {
/* D E B U T	C O R R E C T I O N 		D U		 B U G */
			String a=new String("\",\"");
			String b=new String("\t");
			String c=new String("\"");
			String d=new String("");
			
			// Remplacement des caract��res  "," d��finissant un d��limiteur de champs, par _ 
			line = line.replaceAll(a,b);
			// Suppression des " pr��sent dans la line lu.
			line = line.replaceAll(c,d);
			
/* F I N 	C O R R E C T I O N 		D U		 B U G */
			
			if(!line.equals("structureId,structureTitle,structureAuthor,releaseDate"))
			{
				StringTokenizer st=new StringTokenizer(line,"\t"); 
				i=0;
				
				while( st.hasMoreElements() ){
					String elem=(String) st.nextElement();
				
					if( !elem.equals("+") ){
						System.out.println(elem);
						attributs[i] = elem;
						i++;
					}
				}
				this.insertInBd(attributs[0],attributs[1],attributs[2],attributs[3]);
			}//end if. 
		} //end while.
		rd.close();
	}
	
	public boolean isExist(String idmol){
		ResultSet resultats = null;
		Boolean test = true;
		try {
			resultats = this.stmt.executeQuery("SELECT idmol FROM molecules WHERE idmol = '"+idmol+"';");
			
			
			String resultQuery = null;
			boolean asNext = resultats.next();
			while (asNext) {
				resultQuery = resultats.getString(1);
				
				
				asNext = resultats.next();
		}
		resultats.close();
				// Si la query a renvoy�� un r��sultat (!= null) c'est que la mol��cule est d��ja existante
				if (resultQuery != null){
					test = true; 
				}
				// Si la query n'a pas renvoy�� de r��sultat c'est que la mol��cule n'existe pas dans la database
				else{ 
					
					test = false;
				}
				
		} catch (SQLException e) {
		e.printStackTrace();
		}
		return test;
	}
	public void insertInBd(String idmol, String nom, String auteur, String date){
		// Insertion sous la forme "idmol" "nom" "auteur" "date" dans la table molecules de la database application.
		try{
				// Si TRUE c'est que la mol��cule est d��ja existante -> On ne charge pas la mol��cule dans la database.
				if ( isExist(idmol) ){ System.out.println("[ INFO ] - existing data. ###\n"); }
				// Si FALSE c'est que la mol��cule n'existe pas dans la database -> On charge la mol��cule dans la database.
				else { 
					this.stmt.executeUpdate("INSERT INTO molecules(idmol,nom,auteur,date) VALUES ('"+idmol+"', '"+nom+"', '"+auteur+"', '"+date+"');");
					System.out.println("[ INFO ] - new data import w/ success ! ###\n");
				}
		}catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
}
