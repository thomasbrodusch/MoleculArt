package domaine;

import java.util.ArrayList;
/**
 * Modele de repr�sentation d'une molecule
 * @author Kevin Lavergne & Damien Kozak
 * @version v1.3
 */
public class Modele {

	private ArrayList<Chaine> chaines;

	/**
	 * Constructeur
	 */
	public Modele() {
		this.chaines = new ArrayList<Chaine>();
	}
	/**
	 * Renvoie le nombre de chaine dans ce modele
	 */
	public int size() {
		return this.chaines.size();
	}
	/**
	 * Renvoie la liste des chaines dans ce modele
	 */
	public ArrayList<Chaine> getChaines()
	{
		return chaines;
	}
	/**
	 * Renvoie la s�quence de chaque chaine de ce modele
	 */
	public String[] getSequences()
	{
		String[] seqs=new String[this.size()];
		for(int i=0;i<this.size();i++)
		{
			seqs[i]="Chaine "+this.getChaine(i).getId()+": "+this.getChaine(i).getSequence();
		}
		return seqs;
	}
	
	
	/**
	 * Renvoie une chaine de ce modele
	 */
	public Chaine getChaine(int indice) {
		return chaines.get(indice);
	}

	/**
	 * Ajoute une chaine dans ce modele
	 */
	public void addChaine(Chaine chaine) {
		this.chaines.add(chaine);
	}
	/**
	 * Redessine l'ensemble des atomes associ�es au r�sidus selon une certaine contrainte de colorisation
	 */
	public void redessiner(int contrainte)
	{
		for(Chaine c:this.chaines)
		{
			c.redessiner(contrainte);
		}	
	}
	
	public String toString() {
		String s = "";
		for (Chaine c : chaines) {
			s += c;
		}
		return s;
	}

}
