package fenetre;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import controle.Search;

public class FenRecherche extends JFrame {

	private JTextField pdbID;
	private JTextField auteur;
	private JTextField organisme;
	private JTextField dateMin;
	private JTextField dateMax;
	private JTextField motCle;
	private JButton submitSimple;
	private JButton submitAv;
	private JTabbedPane pan;
	private Box3D panVisu;

	public FenRecherche(Box3D panVisu) {
		pan = panelSearch();
		this.panVisu=panVisu;
		this.add(pan);
		this.setSize(800, 500);
		this.setVisible(true);
	}

	// Met les panneaux recherche sous forme d'onglet
	public JTabbedPane panelSearch() {
		JTabbedPane onglet = new JTabbedPane(JTabbedPane.TOP);
		onglet.add("Recherche simple", rechercheSimple());
		onglet.add("Recherche avancée", rechercheAvancee());
		return onglet;
	}

	// Panneau recherche simple
	private JPanel rechercheSimple() {

		JPanel rechSimple = new JPanel();
		try
		{
			MaskFormatter mask=new MaskFormatter("AAAA");
			pdbID=new JFormattedTextField(mask);
			pdbID.setColumns(5);
		}
		catch(ParseException e)
		{
			
		}
		submitSimple = new JButton("Soumettre");
		rechSimple.add(new JLabel("PDBid:"));
		rechSimple.add(pdbID);
		rechSimple.add(submitSimple);
		submitSimple.addActionListener(new ecouteurRecherche());
		return rechSimple;
	}

	// Panneau recherche avancée
	public JPanel rechercheAvancee() {
		JPanel rechAv = new JPanel();
		rechAv.setLayout(new BorderLayout());
		JPanel champs = new JPanel();
		champs.setLayout(new BoxLayout(champs, BoxLayout.Y_AXIS));
		auteur = new JTextField();
		auteur.setPreferredSize(getPreferredSize());
		organisme = new JTextField(10);
		dateMin = new JTextField(10);
		dateMax = new JTextField(10);
		motCle = new JTextField(10);
		submitAv = new JButton("Soumettre");
		champs.add(new JLabel("Auteur:"));
		champs.add(auteur);
		champs.add(new JLabel("Organisme:"));
		champs.add(organisme);
		champs.add(new JLabel("Date Min:"));
		champs.add(dateMin);
		champs.add(new JLabel("Date Max:"));
		champs.add(dateMax);
		champs.add(new JLabel("Mot-cl�:"));
		champs.add(motCle);
		rechAv.add(champs);
		rechAv.add(submitAv, "South");
		submitAv.addActionListener(new ecouteurRecherche());
		return rechAv;
	}

	class ecouteurRecherche implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			String idpdb = null;
			String orga = null;
			String aut = null;
			String datem = null;
			String dateM = null;
			String mc = null;
			if (e.getSource() == submitSimple) {
					idpdb = pdbID.getText();
			} 
			else if(e.getSource() == submitAv){
					orga = organisme.getText();
					aut = auteur.getText();
					datem = dateMin.getText();
					dateM = dateMax.getText();
					mc = motCle.getText();
			}
			PanResultatRecherche resultat = new PanResultatRecherche(pan,panVisu);
			Search recherche=new Search(idpdb, orga, aut, datem, dateM, mc,resultat);
			recherche.start();
		}
	}
}
