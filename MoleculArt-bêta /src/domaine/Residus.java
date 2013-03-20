/**
 * @author Kevin Lavergne & Damien Kozak
 * @version v1.3
 */
package domaine;

import java.awt.Color;
import java.util.ArrayList;

import javax.media.j3d.TransformGroup;

import domaine.typeResidus.AutreTypeResidus;
import domaine.typeResidus.TypeResidus;
import domaine.typeResidus.nucleique.*;
import domaine.typeResidus.proteine.*;

public class Residus {

	private int numId;
	private Color couleur;
	private ArrayList<Atome> atomes;
	private TypeResidus type;

	public Residus(int numId, String type) {
		this.numId = numId;
		this.atomes = new ArrayList<Atome>();
		this.type = typeResidus(type);
	}

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

	public Atome getAtome(int indice) {
		return atomes.get(indice);
	}

	public void addAtome(Atome atome) {
		this.atomes.add(atome);
	}

	public int size() {
		return this.atomes.size();
	}

	public int getNumId() {
		return numId;
	}

	public ArrayList<Atome> getAtomes() {
		return atomes;
	}

	public TypeResidus getType() {
		return type;
	}
	public void setCouleur(Color couleur) {
		this.couleur = couleur;
	}

	public void dessiner(String contrainteCouleur,TransformGroup group, double[] barycentre) {
		if (contrainteCouleur.equals("couleurResidus")) {
			if (couleur != null)
				this.dessiner(couleur, group, barycentre);
			else
				this.dessiner(this.type.getCouleur(), group, barycentre);
		} else {
			for (Atome a : atomes) {
				a.dessiner(group, barycentre);
			}
		}
	}

	public void dessiner(Color couleur,TransformGroup group, double[] barycentre) {
		for (Atome a : atomes) {
			a.dessiner(couleur, group, barycentre);
		}
	}

	public String toString() {
		String s = "";
		s += "numId:" + numId + "\n";
		s += "type:" + type + "\n";
		for (Atome a : atomes) {
			s += a + "\n";
		}
		return s;
	}

}
