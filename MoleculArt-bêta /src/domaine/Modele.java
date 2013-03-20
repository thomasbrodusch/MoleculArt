/**
 * @author Kevin Lavergne & Damien Kozak
 * @version v1.3
 */
package domaine;

import java.util.ArrayList;

import javax.media.j3d.TransformGroup;

public class Modele {

	private ArrayList<Chaine> chaines;

	public Modele() {
		this.chaines = new ArrayList<Chaine>();
	}

	public int size() {
		return this.chaines.size();
	}

	public ArrayList<Chaine> getChaines()
	{
		return chaines;
	}
	
	public String[] getSequences()
	{
		String[] seqs=new String[this.size()];
		for(int i=0;i<this.size();i++)
		{
			seqs[i]="Chaine "+this.getChaine(i).getId()+": "+this.getChaine(i).getSequence();
		}
		return seqs;
	}
	
	

	public Chaine getChaine(int indice) {
		return chaines.get(indice);
	}

	public void addChaine(Chaine chaine) {
		this.chaines.add(chaine);
	}

	public void dessiner(String contrainteCouleur,TransformGroup group, double[] barycentre) {
		for (Chaine c : chaines)
			c.dessiner(contrainteCouleur, group, barycentre);
	}

	public String toString() {
		String s = "";
		for (Chaine c : chaines) {
			s += c;
		}
		return s;
	}

}
