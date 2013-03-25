
package domaine;

import java.awt.Color;
import java.util.ArrayList;

import domaine.typeResidus.AutreTypeResidus;
/**
 * Classe représentant une chaine dans une molécule
 * @author Kevin Lavergne & Damien Kozak
 * @version v1.3
 */
public class Chaine {
	private String id;
	private Color couleur;
	private ArrayList<Residus> residus;

/**
 * Constructeur
 */
	public Chaine(String id) {
		this.id = id;
		this.residus = new ArrayList<Residus>();
		this.couleurChaine();
	}
/**
 * Definie la couleur de la chaine en fonction de son identifiant
 */
	private void couleurChaine() {
		if (id.equals("A") || id.equals("a"))
			couleur = new Color(192, 208, 255);
		else if (id.equals("B") || id.equals("b"))
			couleur = new Color(176, 255, 176);
		else if (id.equals("C") || id.equals("c"))
			couleur = new Color(255, 192, 200);
		else if (id.equals("D") || id.equals("d"))
			couleur = new Color(255, 255, 128);
		else if (id.equals("E") || id.equals("e"))
			couleur = new Color(255, 192, 255);
		else if (id.equals("F") || id.equals("f"))
			couleur = new Color(176, 240, 240);
		else if (id.equals("G") || id.equals("g"))
			couleur = new Color(255, 208, 112);
		else if (id.equals("H") || id.equals("h"))
			couleur = new Color(240, 128, 128);
		else if (id.equals("I") || id.equals("i"))
			couleur = new Color(245, 222, 179);
		else if (id.equals("J") || id.equals("j"))
			couleur = new Color(0, 191, 255);
		else if (id.equals("K") || id.equals("k"))
			couleur = new Color(205, 92, 92);
		else if (id.equals("L") || id.equals("l"))
			couleur = new Color(102, 205, 170);
		else if (id.equals("M") || id.equals("m"))
			couleur = new Color(154, 205, 50);
		else if (id.equals("N") || id.equals("n"))
			couleur = new Color(238, 130, 238);
		else if (id.equals("O") || id.equals("o"))
			couleur = new Color(0, 206, 209);
		else if (id.equals("P") || id.equals("p") || id.equals("0"))
			couleur = new Color(0, 255, 127);
		else if (id.equals("Q") || id.equals("q") || id.equals("1"))
			couleur = new Color(60, 179, 113);
		else if (id.equals("R") || id.equals("r") || id.equals("2"))
			couleur = new Color(0, 0, 139);
		else if (id.equals("S") || id.equals("s") || id.equals("3"))
			couleur = new Color(189, 183, 107);
		else if (id.equals("T") || id.equals("t") || id.equals("4"))
			couleur = new Color(0, 100, 0);
		else if (id.equals("U") || id.equals("u") || id.equals("5"))
			couleur = new Color(128, 0, 0);
		else if (id.equals("V") || id.equals("v") || id.equals("6"))
			couleur = new Color(128, 128, 0);
		else if (id.equals("W") || id.equals("w") || id.equals("7"))
			couleur = new Color(128, 0, 128);
		else if (id.equals("X") || id.equals("x") || id.equals("8"))
			couleur = new Color(0, 128, 128);
		else if (id.equals("Y") || id.equals("y") || id.equals("9"))
			couleur = new Color(184, 134, 11);
		else if (id.equals("Z") || id.equals("z"))
			couleur = new Color(178, 34, 34);
		else
			couleur = new Color(255, 255, 255);

	}
	/**
	 * Renvoie le nombre de résidus dans la chaine
	 */
	public int size() {
		return this.residus.size();
	}
	/**
	 * Renvoie un résidus dans la chaine
	 */
	public Residus getResidus(int indice) {
		return residus.get(indice);
	}
	/**
	 * Ajoute un résidus dans la chaine
	 */
	public void addResidus(Residus residus) {
		this.residus.add(residus);
	}
	/**
	 * Renvoie l'identifiant de la chaine
	 */
	public String getId() {
		return id;
	}
	/**
	 * Renvoie la couleur de la chaine
	 */
	public Color getCouleur() {
		return couleur;
	}
	/**
	 * Renvoie la listes de la chaine
	 */
	public ArrayList<Residus> getResidus() {
		return residus;
	}
	/**
	 * Renvoie la séquence de cette chaine
	 */
	public String getSequence()
	{
		String seq="";
		for(Residus r:this.residus)
		{
			if(!(r.getType() instanceof AutreTypeResidus))
				seq+=r.getType().getType()+" ";
		}
		return seq;
	}
	/**
	 * Change la couleur de la chaine
	 */
	public void setCouleur(Color couleur) {
		this.couleur = couleur;
		this.redessiner(couleur);
	}
	/**
	 * Redessine l'ensemble des atomes associées au résidus selon une certaine contrainte de colorisation
	 */
	public void redessiner(int contrainte)
	{
		if(contrainte==2)
		{
			this.redessiner(getCouleur());
		}
		else
		{
			for(Residus r:this.residus)
			{
				r.redessiner(contrainte);
			}
		}
	}
	/**
	 * Redessine l'ensemble des atomes associées au résidus selon une couleur
	 */
	public void redessiner(Color couleur)
	{
		for(Residus r:this.residus)
		{
			r.redessiner(couleur);
		}
	}

}
