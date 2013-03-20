package controle;

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

import fenetre.PanResultatRecherche;

/**
 * Classe de recherche d'une molecule presente dans la ProteinDataBank en
 * fonction de differents parametres (mode online).<br/>
 * Utilise l'API REST de la PDB (requetes XML).<br/>
 * 
 * @author Lavergne Kévin & Kozak Damien & Coquerelle Grégory & Belahseme Isame & Internet
 * @version 1.13.2.12
 * 
 */
public class Search extends Thread {

	public static final String SERVICELOCATION = "http://www.rcsb.org/pdb/rest/search";

	private String Id = "";
	private String organisme = "";
	private String author = "";
	private String dateMin = "";
	private String dateMax = "";
	private String motCle = "";
	private PanResultatRecherche panResultat;
	private ParserReport pars;

	/*
	 * TO-DO Ajouter les autres variables.
	 */

	public Search(String Id, String organisme, String author, String dateMin,
			String dateMax, String motCle, PanResultatRecherche panResultat) {
		this.Id = Id;
		this.organisme = organisme;
		this.author = author;
		this.dateMin = dateMin;
		this.dateMax = dateMax;
		this.motCle = motCle;
		this.panResultat=panResultat;
		pars = new ParserReport();
	}

	public ArrayList<String[]> getInfo() {
		return pars.getInfo();
	}

	public void init() throws Exception{

		if (this.validFields()) {
			List<String> pdbIds = this.postQuery(this.XmlCooker());
			for (String string : pdbIds) 
			{
					if (string.length() == 4)
						pars.init(string);
					panResultat.afficherRecherche(getInfo());
					panResultat.afficherEtatRecherche("Recherche en cours :"+getInfo().size()+"/"+pdbIds.size()+"resultats");
			}
			panResultat.afficherEtatRecherche("Recherche termin�:"+pdbIds.size()+"resultats");
		} 
	}

	public void run()
	{
		try {
			this.init();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Verifie que les champs de la recherche ne sont pas vides (au moins un
	 * champ rempli).
	 * 
	 * @return Retourne "true" si l'utilisateur �� au moins renseigne un
	 *         champ.
	 * 
	 */
	public boolean validFields() {
		boolean valid = false;
		if (this.Id != null && !this.Id.equals(""))
			valid = true;
		if (this.author != null && !this.author.equals(""))
			valid = true;
		if (this.dateMin != null && !this.dateMin.equals(""))
			valid = true;
		if (this.dateMax != null && !this.dateMax.equals(""))
			valid = true;
		if (this.motCle != null && !this.motCle.equals(""))
			valid = true;
		if (this.organisme != null && !this.organisme.equals(""))
			valid = true;
		return valid;
	}

	/**
	 * Prepare la requete XML (PDB XML query format) en fonctions des champs de
	 * recherche renseignes par l'utilisateur:<br/>
	 * <br/>
	 * - PDBiD <br/>
	 * - auteur(s) <br/>
	 * - release date de la molecule<br/>
	 * - texte present dans le resume molecule<br/>
	 * - organisme d'origine
	 * 
	 * @throws IOException
	 * 
	 */
	public String XmlCooker() {
		String[] memberXML = new String[5];
		memberXML[0] = this.xmlId();
		memberXML[1] = this.xmlAuteurs();
		memberXML[2] = this.xmlOrganisme();
		memberXML[3] = this.xmlKeyWord();
		memberXML[4] = this.xmlDate();
		String xml = "";
		int i = 0;
		for (String s : memberXML) {
			if (!s.equals("")) {
				xml += this.memberQueryXml(s, i);
				i++;
			}
		}
		if (champRempli() > 1)
			xml = multiQueryXml(xml);
		return xml;
	}

	private String multiQueryXml(String queryXml) {
		String s = queryXml;
		s = "<orgPdbCompositeQuery version=\"1.0\">" + s;
		s = s + "</orgPdbCompositeQuery>";
		return s;
	}

	private String memberQueryXml(String member, int nMember) {
		String s = "";
		if (champRempli() > 1) {
			s = "<queryRefinement>" + "<queryRefinementLevel>" + nMember
					+ "</queryRefinementLevel>";
			if (nMember > 0 && nMember < this.champRempli())
				s += "<conjunctionType>and</conjunctionType>";
			s += member + "</queryRefinement>";

		} else
			return member;
		return s;
	}

	private int champRempli() {
		int nbChamp = 0;
		if (this.Id != null && !this.Id.equals(""))
			nbChamp++;
		if (this.author != null && !this.author.equals(""))
			nbChamp++;
		if (this.dateMin != null && !this.dateMin.equals(""))
			nbChamp++;
		if (this.dateMax != null && !this.dateMax.equals(""))
			nbChamp++;
		if (this.motCle != null && !this.motCle.equals(""))
			nbChamp++;
		if (this.organisme != null && !this.organisme.equals(""))
			nbChamp++;
		return nbChamp;
	}

	public String xmlId() {
		String s = "";
		if (this.Id != null && (!this.Id.equals(""))) { // Recherche PDBiD
			s += "<orgPdbQuery>"
					+ "<version>head</version>"
					+ "<queryType>org.pdb.query.simple.StructureIdQuery</queryType>"
					+ "<structureIdList>" + Id + "</structureIdList>"
					+ "</orgPdbQuery>";
		}
		return s;
	}

	public String xmlAuteurs() {
		String s = "";
		if (author != null && (!author.equals(""))) { // Recherche auteur

			s += "<orgPdbQuery>"
					+ "<version>head</version>"
					+ "<queryType>org.pdb.query.simple.AdvancedAuthorQuery</queryType>"
					+ "<searchType>All Authors</searchType>"
					+ "<audit_author.name>" + this.author
					+ "</audit_author.name>" + "<exactMatch>false</exactMatch>"
					+ "</orgPdbQuery>";
		}
		return s;
	}

	// Recherche
	// organisme.
	public String xmlOrganisme() {
		String s = "";
		if (this.organisme != null && (!this.organisme.equals(""))) {
			s += "<orgPdbQuery>"
					+ "<version>head</version>"
					+ "<queryType>org.pdb.query.simple.ExpressionOrganismQuery</queryType>"
					+ "<entity_src_gen.pdbx_host_org_scientific_name.comparator>contains</entity_src_gen.pdbx_host_org_scientific_name.comparator>"
					+ "<entity_src_gen.pdbx_host_org_scientific_name.value>"
					+ this.organisme
					+ "</entity_src_gen.pdbx_host_org_scientific_name.value>"
					+ "</orgPdbQuery>";
		}
		return s;
	}

	// Recherche
	// intervalle
	// Datemin-Datemax
	public String xmlDate() {
		String s = "";
		if (this.dateMin != null && this.dateMax != null
				&& (!this.dateMin.equals("")) && (!this.dateMax.equals(""))) {

			s += "<orgPdbQuery>"
					+ "<version>head</version>"
					+ "<queryType>org.pdb.query.simple.ReleaseDateQuery</queryType>"
					+ "<database_PDB_rev.date.comparator>between</database_PDB_rev.date.comparator>"
					+ "<database_PDB_rev.date.min>"
					+ this.dateMin
					+ "</database_PDB_rev.date.min>"
					+ "<database_PDB_rev.date.max>"
					+ this.dateMax
					+ "</database_PDB_rev.date.max>"
					+ "<database_PDB_rev.mod_type.comparator><![CDATA[<]]></database_PDB_rev.mod_type.comparator>"
					+ "<database_PDB_rev.mod_type.value>1</database_PDB_rev.mod_type.value>"
					+ "</orgPdbQuery>";
		}
		return s;
	}

	// Recherche
	// mots-cl�.
	public String xmlKeyWord() {
		String s = "";
		if (this.motCle != null && (!this.motCle.equals(""))) {

			s += "<orgPdbQuery>"
					+ "<version>head</version>"
					+ "<queryType>org.pdb.query.simple.AdvancedKeywordQuery</queryType>"
					+ "<keywords>" + this.motCle + "</keywords>"
					+ "</orgPdbQuery>";
		}
		return s;
	}

	/**
	 * Poste une requete XML (PDB XML query format) -> RESTful RCSB web service
	 * 
	 * @param xml
	 *            requete XML finale.
	 * @see #xmlCooker()
	 * @return une liste de PDBid.
	 */
	public ArrayList<String> postQuery(String xml) throws IOException {

		panResultat.afficherEtatRecherche("Envoie de la requete");
		URL u = new URL(SERVICELOCATION);
		String encodedXML = URLEncoder.encode(xml, "UTF-8");
		InputStream in = doPOST(u, encodedXML);
		ArrayList<String> pdbIds = new ArrayList<String>();
		panResultat.afficherEtatRecherche("Reception");
		BufferedReader rd = new BufferedReader(new InputStreamReader(in));
		String line;
		while ((line = rd.readLine()) != null) {
			pdbIds.add(line);
			panResultat.afficherEtatRecherche("Recherche en cours :"+getInfo().size()+"/"+pdbIds.size()+"resultats");
		}
		rd.close();
		return pdbIds;
	}

	/**
	 * Fait une requete POST sur une URL et retourne la reponse.
	 * 
	 * @param url
	 * @return reponse a la requete POST.
	 * @throws IOException
	 */
	public static InputStream doPOST(URL url, String data) throws IOException {

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
