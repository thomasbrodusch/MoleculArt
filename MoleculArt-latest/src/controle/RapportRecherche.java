package controle;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
/**
 * Classe qui permet de créer un rapport sous forme de fichier HTML 
 *
 */
public class RapportRecherche extends Thread {

	private String Id;
	private String organisme;
	private String author;
	private String dateMin;
	private String dateMax;
	private String motCle;
	private String[][] donneesRecherche;
	private JLabel text;
	/**
	 * Constructeur
	 * @param Id
	 * @param organisme
	 * @param author
	 * @param dateMin
	 * @param dateMax
	 * @param motCle
	 * @param donneesRecherche
	 */
	public RapportRecherche(String Id, String organisme, String author, String dateMin,
			String dateMax, String motCle,String[][] donneesRecherche)
	{
		this.Id=Id;
		this.organisme=organisme;
		this.author=author;
		this.dateMin=dateMin;
		this.dateMax=dateMax;
		this.motCle=motCle;
		this.donneesRecherche=donneesRecherche;
	}
	/**
	 * Lancement de la génération du rapport
	 */
	public void run()
	{
		JFrame frame = new JFrame();
		frame.setSize(300, 100);
		text=new JLabel();
		frame.add(text);
		frame.setVisible(true);
		ParseurPDB p = new ParseurPDB();
		Date d = new Date();
		String date=(1900+d.getYear())+"-"+d.getMonth()+"-"+d.getDate()+"_"+d.getHours()+"-"+d.getMinutes()+"-"+d.getSeconds();
		File dossier=new File("rapportRecherche");
		if(!dossier.exists())
			dossier.mkdir();
		File f = new File("rapportRecherche/rapportRecherche"+date+".htm");
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(f));
			bw.write("<html>");
			bw.write("<body>");
			bw.write("<h1>Recherche du " + date + "</h1>");
			bw.write("<ul>");
			if(Id!=null && !Id.equals(""))
				bw.write("<li>Id:"+Id+"</li>");
			if(organisme!=null && !organisme.equals(""))
				bw.write("<li>Organisme:"+organisme+"</li>");
			if(author!=null && !author.equals(""))
				bw.write("<li>Auteurs:"+author+"</li>");
			if(dateMin!=null && !dateMin.equals("") && dateMax!=null && !dateMax.equals(""))
				bw.write("<li>Intervale de dates:"+dateMin+" "+dateMax+"</li>");
			if(motCle!=null && !motCle.equals(""))
				bw.write("<li>Mot-Cle:"+motCle+"</li>");
			bw.write("</ul>");
			bw.write("<table>");
			bw.write("<tr>");
			bw.write("<th>");
			bw.write("PDBid");
			bw.write("</th>");
			bw.write("<th>");
			bw.write("Titre Structure");
			bw.write("</th>");
			bw.write("<th>");
			bw.write("Auteurs");
			bw.write("</th>");
			bw.write("<th>");
			bw.write("Date de publication");
			bw.write("</th>");
			bw.write("</tr>");
			for(int i=0;i<donneesRecherche.length;i++)
			{
				bw.write("<tr>");
				for(int j=0;j<donneesRecherche[i].length;j++)
				{
					bw.write("<td>");
					bw.write(donneesRecherche[i][j]);
					bw.write("</td>");
				}
				bw.write("</tr>");
			}
			bw.write("</table>");
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
