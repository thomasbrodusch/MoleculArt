package controle;

/**
 * @author Brodusch Thomas
 * @version 2.13.2.8
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;
/**
 * Classe permettant de décomposer la réponse envoie par le RESTful Service
 * @author Brodusch Thomas
 * @version 2.13.2.8
 */
public class ParserReport {

	public static final String ReportLOCATION = "http://www.rcsb.org/pdb/rest/customReport";
	private ArrayList<String[]> info;
/**
 * Constructeur
 */
	public ParserReport() {
		info = new ArrayList<String[]>();
	}
	/*
	 * Initialise le parseur
	 */
	public void init(String ids) throws IOException, Exception {
		this.parseData(ids);
	}
/**
 * Renvoie les données du résultat de la recherche sous forme de liste de tableau
 */
	public ArrayList<String[]> getInfo() {
		return info;

	}

	
/**
 * Parsing des infos de la molÃ©cule de la PDB.
 * @throws IOException
 * @throws Exception
 */
	public void parseData(String ids) throws IOException, Exception {

		String qstr = "?pdbids="
				+ ids
				+ "&customReportColumns=structureId,structureTitle,structureAuthor,releaseDate";
		String urlStr = ReportLOCATION + qstr
				+ "&service=wsdisplay&format=csv&ssa=n";
		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		if (conn.getResponseCode() != 200) {
			throw new IOException(conn.getResponseMessage() + " "
					+ conn.getResponseCode());
		}

		BufferedReader rd = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));
		String line;
		String[] attributs = null;

		int i;

		while ((line = rd.readLine()) != null) {

			attributs = new String[4];
			String a = new String("\",\"");
			String b = new String("\t");
			String c = new String("\"");
			String d = new String("");
			String e="<br />";
			String f="\n";
			line = line.replaceAll(a, b);
			line = line.replaceAll(c, d);
			line = line.replaceAll(e, f);
			StringTokenizer st = new StringTokenizer(line, "\t");
			i = 0;
			while (st.hasMoreElements()) {
				String elem = (String) st.nextElement();
				StringTokenizer st2 = new StringTokenizer(elem, "\n");
				while(st2.hasMoreTokens())
				{
					String subElem=(String) st2.nextElement();
					if(!subElem.equals("structureId,structureTitle,structureAuthor,releaseDate"))
					{
							attributs[i] = subElem;
							i++;
					}
				}
			}
			info.add(attributs);
		}
		rd.close();
		conn.disconnect();
	}
}
