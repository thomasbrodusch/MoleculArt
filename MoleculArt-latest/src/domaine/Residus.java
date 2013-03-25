/**
 * @author Kevin Lavergne & Damien Kozak
 * @version v1.3
 */
package domaine;

import java.awt.Color;
import java.util.ArrayList;

import domaine.typeResidus.AutreTypeResidus;
import domaine.typeResidus.TypeResidus;
import domaine.typeResidus.nucleique.A;
import domaine.typeResidus.nucleique.C;
import domaine.typeResidus.nucleique.Da;
import domaine.typeResidus.nucleique.Dc;
import domaine.typeResidus.nucleique.Dg;
import domaine.typeResidus.nucleique.Di;
import domaine.typeResidus.nucleique.Dt;
import domaine.typeResidus.nucleique.Du;
import domaine.typeResidus.nucleique.G;
import domaine.typeResidus.nucleique.I;
import domaine.typeResidus.nucleique.T;
import domaine.typeResidus.nucleique.U;
import domaine.typeResidus.proteine.Ala;
import domaine.typeResidus.proteine.Arg;
import domaine.typeResidus.proteine.Asn;
import domaine.typeResidus.proteine.Asp;
import domaine.typeResidus.proteine.Asx;
import domaine.typeResidus.proteine.Cys;
import domaine.typeResidus.proteine.Gln;
import domaine.typeResidus.proteine.Glu;
import domaine.typeResidus.proteine.Glx;
import domaine.typeResidus.proteine.Gly;
import domaine.typeResidus.proteine.His;
import domaine.typeResidus.proteine.Ile;
import domaine.typeResidus.proteine.Leu;
import domaine.typeResidus.proteine.Lys;
import domaine.typeResidus.proteine.Met;
import domaine.typeResidus.proteine.Phe;
import domaine.typeResidus.proteine.Pro;
import domaine.typeResidus.proteine.Ser;
import domaine.typeResidus.proteine.Thr;
import domaine.typeResidus.proteine.Trp;
import domaine.typeResidus.proteine.Tyr;
import domaine.typeResidus.proteine.Val;
/**
 * Classe représentant un résidus
 * @author kebinu
 *
 */
public class Residus {

	private int numId;
	private Color couleur;
	private ArrayList<Atome> atomes;
	private TypeResidus type;

	/**
	 * Constructeur en foction d'un identifiant et du type du résidus
	 */
	public Residus(int numId, String type) {
		this.numId = numId;
		this.atomes = new ArrayList<Atome>();
		this.type = typeResidus(type);
	}

	/**
	 * Créer le type du résidus
	 * @param type
	 */
	public TypeResidus typeResidus(String type) {
		TypeResidus typeR = null;
		if (type.equals("  A"))
			typeR = new A();
		else if (type.equals("  C"))
			typeR = new C();
		else if (type.equals("  G"))
			typeR = new G();
		else if (type.equals("  I"))
			typeR = new I();
		else if (type.equals("  T"))
			typeR = new T();
		else if (type.equals("  U"))
			typeR = new U();
		else if (type.equals(" DA"))
			typeR = new Da();
		else if (type.equals(" DC"))
			typeR = new Dc();
		else if (type.equals(" DG"))
			typeR = new Dg();
		else if (type.equals(" DT"))
			typeR = new Dt();
		else if (type.equals(" DI"))
			typeR = new Di();
		else if (type.equals(" DU"))
			typeR = new Du();
		else if (type.equals("ALA"))
			typeR = new Ala();
		else if (type.equals("ARG"))
			typeR = new Arg();
		else if (type.equals("ASN"))
			typeR = new Asn();
		else if (type.equals("ASP"))
			typeR = new Asp();
		else if (type.equals("ASX"))
			typeR = new Asx();
		else if (type.equals("CYS"))
			typeR = new Cys();
		else if (type.equals("GLN"))
			typeR = new Gln();
		else if (type.equals("GLU"))
			typeR = new Glu();
		else if (type.equals("GLY"))
			typeR = new Gly();
		else if (type.equals("GLX"))
			typeR = new Glx();
		else if (type.equals("HIS"))
			typeR = new His();
		else if (type.equals("ILE"))
			typeR = new Ile();
		else if (type.equals("LEU"))
			typeR = new Leu();
		else if (type.equals("LYS"))
			typeR = new Lys();
		else if (type.equals("MET"))
			typeR = new Met();
		else if (type.equals("PHE"))
			typeR = new Phe();
		else if (type.equals("PRO"))
			typeR = new Pro();
		else if (type.equals("SER"))
			typeR = new Ser();
		else if (type.equals("THR"))
			typeR = new Thr();
		else if (type.equals("TRP"))
			typeR = new Trp();
		else if (type.equals("TYR"))
			typeR = new Tyr();
		else if (type.equals("VAL"))
			typeR = new Val();
		else
			typeR = new AutreTypeResidus(type);
		return typeR;
	}

	/**
	 * Retourne un atome du résidus
	 * @param indice
	 */
	public Atome getAtome(int indice) {
		return atomes.get(indice);
	}
	/**
	 * Ajout un atome dans le residus
	 * @param atome
	 */
	public void addAtome(Atome atome) {
		this.atomes.add(atome);
	}

	/**
	 * Retourne le nombre d'atome dans le résidus
	 */
	public int size() {
		return this.atomes.size();
	}
	/**
	 * Retourne l'identifiant du résidus 
	 */
	public int getNumId() {
		return numId;
	}

	/**
	 * Retourne la couleur du résidus
	 */
	public Color getCouleur() {
		if (couleur == null)
			return this.getType().getCouleur();
		else
			return couleur;
	}
	/**
	 * Retourne la liste des atomes
	 */
	public ArrayList<Atome> getAtomes() {
		return atomes;
	}
	/**
	 * Retourne le type du résidus
	 */
	public TypeResidus getType() {
		return type;
	}
	/**
	 * Change la couleur du résidus
	 */
	public void setCouleur(Color couleur) {
		this.couleur = couleur;
		this.redessiner(couleur);
	}
	/**
	 * Redessine l'ensemble des atomes associées au résidus selon une certaine contrainte de colorisation
	 */
	public void redessiner(int contrainte) {
		if (contrainte == 1) {
			this.redessiner(getCouleur());
		} else {
			for (Atome a : this.atomes) {
				a.redessiner(a.getCouleur());
			}
		}
	}
	/**
	 * Redessine l'ensemble des atomes associées au résidus selon une couleur
	 */
	public void redessiner(Color couleur) {
		for (Atome a : this.atomes) {
			a.redessiner(couleur);
		}
	}
	/**
	 * Retourne vrai si le type du residus en paramètre est le meme que celui de ce résidus
	 */
	public boolean equals(Object o) {
		return ((Residus) o).getType().getType()
				.equals(this.getType().getType());
	}
}
