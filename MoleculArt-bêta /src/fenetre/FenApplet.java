package fenetre;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileNameExtensionFilter;

import controle.Visualisation;

/*
 * Classe IHM
 * Auteurs: COQUERELLE Gr�gory
 * Version: 1.4
 */

public class FenApplet extends JApplet {

	private JMenuItem search;
	private JMenuItem open;
	private FenRecherche fenRecherche;
	private Box3D panVisualisation;

	public static void main(String[] args)
	{
		JFrame f=new JFrame();
		FenApplet applet=new FenApplet();
		f.setSize(800,500);
		Container c = f.getContentPane();
		c.setLayout(new BorderLayout());
		c.add(applet.panelVide(), "South");
		c.add(applet.afficheMol(), "Center");
		c.add(applet.createJMenuBar(), "North");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
	
	// initialisation de FenApplet
	public void init() {
		this.setSize(800,500);
		Container c = this.getContentPane();
		c.setLayout(new BorderLayout());
		c.add(this.panelVide(), "South");
		c.add(this.afficheMol(), "Center");
		c.add(this.createJMenuBar(), "North");
	}
	
	// Panneau à outils zoom, sauvegarder er changer la couleur
	

	// Panneau vide, on pourra le compléter à l'avenir
	private JPanel panelVide() {
		JPanel vide = new JPanel();
		vide.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEtchedBorder(),
				(BorderFactory.createEmptyBorder(30, 15, 4, 8))));
		return vide;

	}

	// Panneau central, là où on affichera la molécule
	public JPanel afficheMol() {
		panVisualisation=new Box3D();
		return panVisualisation;
	}

	// Barre du menu
	public JMenuBar createJMenuBar() {

		JMenuBar menu = new JMenuBar();
		JMenu file = new JMenu("Fichier");
//		JMenuItem rapport = new JMenuItem("Générer un rapport");
//		JMenuItem save = new JMenuItem("Enregistrer");
//		JMenuItem print = new JMenuItem("Imprimer");
		open = new JMenuItem("Ouvrir");
		open.addActionListener(new Parcourir());
		search = new JMenuItem("Rechercher une molécule");
		search.addActionListener(new AfficheRecherche());

		file.add(open);
//		file.add(save);
//		file.add(print);
//		file.add(rapport);
		file.add(search);

		menu.add(file);

		return menu;

	}

	public void start() {// Méthode vide
	}

	class Parcourir implements ActionListener {
		/*
		 * 
		 * @see
		 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
		 * )
		 * 
		 * si on appuie sur le bouton Ouvrir, on déclenche l'évenement qui
		 * permet de choisir les fichiers
		 */
		public void actionPerformed(ActionEvent arg0) {
			if (arg0.getSource() == open) {
				JFileChooser fc = new JFileChooser();
				// On choisit que les fichiers XML et PDB
				FileNameExtensionFilter f = new FileNameExtensionFilter(
						"PDB files", "pdb");
				fc.setCurrentDirectory(new File("tmp"));
				fc.setFileFilter(f);
				fc.showOpenDialog(null);
				if(fc.getSelectedFile()!=null)
				{
					Visualisation visu=new Visualisation(panVisualisation);
					visu.init(fc.getSelectedFile().getName().substring(0, 4));
					visu.start();
				}
			}
		}
	}

	class AfficheRecherche implements ActionListener {
		/*
		 * Afficher les onglets recherche lorsque qu'on clique sur "Recherche"
		 */
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == search) {
				// Sous forme de fenêtre
				if(fenRecherche!=null)
					fenRecherche.dispose();
				fenRecherche = new FenRecherche(panVisualisation);
			}
		}
	}
}
