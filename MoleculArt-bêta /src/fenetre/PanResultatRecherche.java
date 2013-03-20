package fenetre;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import controle.RapportMolecule;
import controle.Visualisation;

public class PanResultatRecherche extends JPanel {
	
	private JTable tableRecherche;
	private JPanel panTable;
	private JTabbedPane pan;
	private JLabel etat;
	private Box3D panVisu;
	
	public PanResultatRecherche(JTabbedPane pan,Box3D panVisu)
	{
		this.setLayout(new BorderLayout());
		this.pan=pan;
		this.panVisu=panVisu;
		etat=new JLabel();
		panTable=new JPanel();
		JPanel buttons=new JPanel();
		JButton visu=new JButton("Visualisation");
		visu.addActionListener(new VisualisationListener());
		JButton rapMol=new JButton("Rapport Molecule");
		rapMol.addActionListener(new RapportMoleculeListener());
		JButton rapRech=new JButton("Rapport Recherche");
		rapRech.addActionListener(new RapportRechercheListener());
		JButton exit=new JButton("Fermer l'onglet");
		exit.addActionListener(new ecouteurExit());
		buttons.add(visu);
		buttons.add(rapMol);
		buttons.add(rapRech);
		buttons.add(exit);
		this.add(buttons,"South");
		this.add(panTable);
		this.add(etat,"North");
		pan.add("ResultatRecherche", this);
		pan.setSelectedComponent(this);
	}

	public void afficherRecherche(ArrayList<String[]> tabInfo)
	{
		panTable.removeAll();
		panTable.setLayout(new BorderLayout());
		String[] entete={"PDBid","Titre Structure","Auteur(s)","Date de publication"};
		String[][] donnees=new String[tabInfo.size()][4];
		for(int i=0;i<tabInfo.size();i++)
		{
			donnees[i]=tabInfo.get(i);
		}
		tableRecherche=new JTable(donnees,entete);
		tableRecherche.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableRecherche.setAutoscrolls(true);
		int width=(this.getWidth()-170)/2;
		tableRecherche.getColumnModel().getColumn(0).setPreferredWidth(50);
		tableRecherche.getColumnModel().getColumn(1).setPreferredWidth(width);
		tableRecherche.getColumnModel().getColumn(2).setPreferredWidth(width);
		tableRecherche.getColumnModel().getColumn(3).setPreferredWidth(120);
		JScrollPane scrollpan=new JScrollPane(tableRecherche);
		panTable.add(scrollpan);
		this.updateUI();
	}
	public void afficherEtatRecherche(String message)
	{
		etat.setText(message);
	}
	
	class RapportMoleculeListener implements ActionListener
	{

		public void actionPerformed(ActionEvent arg0) {

			ArrayList<String> list=new ArrayList<String>();
			if(tableRecherche.getSelectedRowCount()>0)
			{
				for(int i:tableRecherche.getSelectedRows())
				{	
					list.add((String) tableRecherche.getValueAt(i, 0));
					
				}
				RapportMolecule rapMol=new RapportMolecule(list.toArray(new String[1]));
				rapMol.start();
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Pas de molécule selectionné");
			}
		}
		
	}
	class ecouteurExit implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) {
			pan.remove(PanResultatRecherche.this);
		}		
	}
	class RapportRechercheListener implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) {
			
		}
	}
	class VisualisationListener implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) {
			if(tableRecherche.getSelectedRowCount()>0)
			{
				Visualisation visu=new Visualisation(panVisu);
				String idMol=(String) tableRecherche.getValueAt(tableRecherche.getSelectedRow(), 0);
				visu.init(idMol);
				visu.start();
			}
			
		}
	}
}
