package controle;

import javax.swing.JFrame;
import javax.swing.JLabel;

import domaine.StructurePDB;
import fenetre.Box3D;

public class Visualisation extends Thread
{
	private Box3D visualisation;
	private String idMol;
	private StructurePDB struc;
	
	public Visualisation(Box3D visualisation)
	{
		this.visualisation=visualisation;
	}
		
	public void init(String idMol)
	{
		this.idMol=idMol;
	}
	
	public void run()
	{
		JFrame frame=new JFrame();
		frame.setSize(300, 100);
		JLabel text=new JLabel("Construction de la molécule");
		frame.add(text);
		frame.setVisible(true);
		ParseurPDB parseur=new ParseurPDB();
		try {
			struc=parseur.creerStruturePDB(idMol);
			visualisation.dessiner(struc);
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
