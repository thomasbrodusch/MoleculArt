/**
 * @author Brodusch Thomas
 * @version 2.130208
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.StringTokenizer;
	
	

	public class Parser {
		

		public static final String ReportLOCATION = "http://www.rcsb.org/pdb/rest/customReport";
		public static final String searchLOCATION="http://www.rcsb.org/pdb/rest/search";
		public String PDBiD;
		
		
		public Parser(String id){ this.PDBiD = id;}
		
		
		public void init(){
			try{ 
				this.parseData(PDBiD); 
				}
				catch(IOException e){ 
					e.printStackTrace();
					}
				catch(Exception e) { 
					e.printStackTrace();
					} 
			}
		
		
		public void parseData(String id) throws IOException,Exception {
			// Parsing des infos de la molécule de la PDB.
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
			String b=new String("_");
			String c=new String("\"");
			String d=new String("");
			
			// Remplacement des caractères  "," définissant un délimiteur de champs, par _ 
			line = line.replaceAll(a,b);
			// Suppression des " présent dans la line lu.
			line = line.replaceAll(c,d);
			
/* F I N 	C O R R E C T I O N 		D U		 B U G */
			
			if(!line.equals("structureId,structureTitle,structureAuthor,releaseDate"))
			{
				StringTokenizer st=new StringTokenizer(line,"_"); 
				i=0;
				
				while( st.hasMoreElements() ){
					String elem=(String) st.nextElement();
				
					if( !elem.equals("_") ){
						//System.out.println(elem);
						attributs[i] = elem;
						i++;
					}
				}
				System.out.println(attributs[0]+" | "+attributs[1]+" | "+attributs[2]+" | "+attributs[3]);
			} 
		} 
		rd.close();
	}
	
}
