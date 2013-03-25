package fenetre;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.sun.j3d.utils.applet.MainFrame;

import controle.ParseurPDB;
import controle.RapportMolecule;
import controle.Visualisation;

/**
 * Classe IHM
 * Auteurs: COQUERELLE Grégory
 * Version: 1.4
 */

public class FenApplet extends JApplet{

	private FenRecherche fenRecherche;
	private Box3D panVisualisation;

	 public static void main(String[] args) {
		 new MainFrame( new FenApplet(), args, 400, 400 ); }

	
	/**
	 * initialisation de FenApplet
	 */
	public void init() {
		this.setSize(800,500);
		Container c = this.getContentPane();
		c.setLayout(new BorderLayout());
		c.add(this.panelVide(), "South");
		c.add(this.afficheMol(), "Center");
		c.add(this.createJMenuBar(), "North");
	}
	

	/**
	 *  Panneau vide, on pourra le compléter Ã  l'avenir
	 */
	private JPanel panelVide() {
		JPanel vide = new JPanel();
		vide.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEtchedBorder(),
				(BorderFactory.createEmptyBorder(30, 15, 4, 8))));
		return vide;

	}

	/**
	 *  Panneau central, affichage de la molécule
	 */
	public JPanel afficheMol() {
		panVisualisation=new Box3D();
		return panVisualisation;
	}

	/**
	 *  Barre du menu
	 */
	public JMenuBar createJMenuBar() {

		JMenuBar menu = new JMenuBar();
		JMenu file = new JMenu("Fichier");
		JMenuItem open = new JMenuItem("Ouvrir");
		JMenuItem rapport = new JMenuItem("Générer un rapport");
		JMenuItem search = new JMenuItem("Rechercher une molécule");
		open.addActionListener(new Parcourir());
		rapport.addActionListener(new GenererRappotMol());
		search.addActionListener(new AfficheRecherche());
		file.add(open);
		file.add(rapport);
		file.add(search);

		menu.add(file);

		return menu;

	}
	class GenererRappotMol implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) {
			JFileChooser fc = new JFileChooser();
			FileNameExtensionFilter f = new FileNameExtensionFilter(
					"PDB files", "pdb");
			fc.setCurrentDirectory(new File("tmp"));
			fc.setFileFilter(f);
			fc.setMultiSelectionEnabled(true);
			int returnVal=fc.showOpenDialog(FenApplet.this);
			if(returnVal==JFileChooser.APPROVE_OPTION)
			{
				RapportMolecule rapport=new RapportMolecule(fc.getSelectedFiles());
				rapport.start();
			}
		}
		
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * Si on appuie sur le bouton Ouvrir, on dÃ©clenche l'Ã©venement qui
	 * permet de choisir les fichiers
	 */
	class Parcourir implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				// On choisit que les fichiers XML et PDB
				FileNameExtensionFilter f = new FileNameExtensionFilter(
						"PDB files", "pdb");
				fc.setCurrentDirectory(new File("tmp"));
				fc.setFileFilter(f);
				int returnVal=fc.showOpenDialog(FenApplet.this);
				if(returnVal==JFileChooser.APPROVE_OPTION)
				{
					Visualisation visu=new Visualisation(panVisualisation);
					visu.init(fc.getSelectedFile());
					visu.start();
				}
			}
	}

	/**
	 * Afficher les onglets recherche lorsque qu'on clique sur "Recherche"
	 */
	class AfficheRecherche implements ActionListener {
		public void actionPerformed(ActionEvent e) {
				// Sous forme de fenÃªtre
				if(fenRecherche!=null)
					fenRecherche.dispose();
				fenRecherche = new FenRecherche(panVisualisation);
		}
	}
}
