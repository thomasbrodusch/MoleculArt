package controle;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JLabel;

import domaine.StructurePDB;
import fenetre.Box3D;
/**
 * Classe permettant de lancer la visualisation en 3D en créeant une structurePDB à partir d'un fichier PDB
 *
 */
public class Visualisation extends Thread
{
	private Box3D visualisation;
	private String idMol;
	private File fichier;
	private StructurePDB struc;
	
	/**
	 * Constructeur qui associe à la scène 3D
	 * @param visualisation
	 */
	public Visualisation(Box3D visualisation)
	{
		this.visualisation=visualisation;
	}
	/**
	 * Initialise la séquence de visualisation avec un identifiant de molécule
	 * @param idMol
	 */
	public void init(String idMol)
	{
		this.idMol=idMol;
	}
	/**
	 * Initialise la séquence de visualisation avec un fichier PDB
	 * @param fichier
	 */
	public void init(File fichier)
	{
		this.fichier=fichier;
	}
	/**
	 * Execution de la séquence de visualisation
	 */
	public void run()
	{
		JFrame frame=new JFrame();
		frame.setSize(300, 100);
		JLabel text=new JLabel("Construction de la molécule");
		frame.add(text);
		frame.setVisible(true);
		ParseurPDB parseur=new ParseurPDB();
		try {
			if(idMol!=null)
				struc=parseur.creerStruturePDB(idMol);
			else if(fichier!=null)
				struc=parseur.parsage(fichier);
			visualisation.init(struc);
			visualisation.dessiner(0);
			frame.dispose();
			visualisation.requestFocus();
		} catch (Exception e) {
			text.setText("Error:"+e.getMessage());
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
			}
			frame.dispose();
		}
	}
}
