package controle;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;

import domaine.StructurePDB;

/**
 * Classe permettant de créer un rapport de plusieurs molécules sous forme de page HTML
 * @author Kozak Damien & Lavergne Kévin
 * @version v1.2
 */
public class RapportMolecule extends Thread {

	private String[] idMols;
	private JLabel text;
	private File[] fichiers;
	
	/**
	 * Initialise le constructeur de rapport avec une liste d'identifiant de molécules
	 * @param idMols
	 */
	public RapportMolecule(String[] idMols) {
		this.idMols = idMols;
	}
	/**
	 * Initialise le constructeur de rapport avec une liste de fichier
	 * @param fichiers
	 */
	public RapportMolecule(File[] fichiers) {
		this.fichiers = fichiers;
	} 
	
	/**
	 * Lancement du thread
	 */
	public void run() {
		this.genererRapport();
	}
	/**
	 * Gérère une page HTML contenant les informations trouvées dans les fichiers PDBs des molécules choisies
	 */
	public void genererRapport(){

		JFrame frame = new JFrame();
		frame.setSize(300, 100);
		text=new JLabel();
		frame.add(text);
		frame.setVisible(true);
		ParseurPDB p = new ParseurPDB();
		Date d = new Date();
		String date=(1900+d.getYear())+"-"+d.getMonth()+"-"+d.getDate()+"_"+d.getHours()+"-"+d.getMinutes()+"-"+d.getSeconds();
		File dossier=new File("rapportMol");
		if(!dossier.exists())
			dossier.mkdir();
		File f = new File("rapportMol/rapportMolecule"+date+".htm");
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(f));
			bw.write("<html>");
			bw.write("<body>");
			int length = 0;
			if(idMols!=null)
			{
				length=idMols.length;
			}
			else if(fichiers!=null)
			{
				length=fichiers.length;
			}
			for (int i = 0; i < length; i++) {
				StructurePDB s=null;
				if(idMols!=null)
				{
					text.setText("Téléchargement et parsage de "+idMols[i]+".pdb "+i+"/"+idMols.length);
					s = p.creerStruturePDB(idMols[i]);
					text.setText("Rapport de "+idMols[i]+".pdb "+i+"/"+idMols.length);
				}
				else if(fichiers!=null)
				{
					text.setText("Parsage de "+fichiers[i].getName()+" "+i+"/"+fichiers.length);
					s = p.parsage(fichiers[i]);
					text.setText("Rapport de "+fichiers[i].getName()+" "+i+"/"+fichiers.length);
				}
				
				bw.write("<h1>" + s.getPdbId() + "</h1>");
				bw.write("<ul>");
				bw.write("<li> Titre : " + s.getTitre() + "</li>");
				bw.write("</br>");
				bw.write("<li> Auteur(s) :</li>");
				bw.write("<li><ul>");
				for(String auteur:s.getAuteurs())
				{
					bw.write("<li>"+auteur+"</li>");
				}
				bw.write("</ul></li>");
				bw.write("</br>");
				bw.write("<li> Date de publication : " + s.getDatePublication()
						+ "</li>");
				bw.write("</br>");
				bw.write("<li> Nombre de modèles : " + s.size() + "</li>");
				bw.write("</br>");
				bw.write("<li> Sequences : </li>");
				bw.write("<li><ul>");
				for(String seq:s.getSequences())
				{
					bw.write("<li>"+seq+"</li>");
				}
				bw.write("</ul></li>");
				bw.write("</br>");
				bw.write("</ul>");
			}
			bw.write("</body>");
			bw.write("</html>");
			text.setText("Rapport réalisé");
			bw.close();
			Thread.sleep(2000);
			frame.dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			Desktop.getDesktop().browse(f.toURI());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
