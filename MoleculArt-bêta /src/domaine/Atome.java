/**
 * @author Kevin Lavergne & Damien Kozak
 * @version v1.3
 */
package domaine;

import java.awt.Color;

import javax.media.j3d.TransformGroup;

import domaine.typeAtomique.Ag;
import domaine.typeAtomique.Al;
import domaine.typeAtomique.Au;
import domaine.typeAtomique.AutreTypeAtomique;
import domaine.typeAtomique.B;
import domaine.typeAtomique.Ba;
import domaine.typeAtomique.Bn;
import domaine.typeAtomique.C;
import domaine.typeAtomique.Ca;
import domaine.typeAtomique.Cl;
import domaine.typeAtomique.Cr;
import domaine.typeAtomique.Cu;
import domaine.typeAtomique.F;
import domaine.typeAtomique.Fe;
import domaine.typeAtomique.H;
import domaine.typeAtomique.He;
import domaine.typeAtomique.I;
import domaine.typeAtomique.Li;
import domaine.typeAtomique.Mg;
import domaine.typeAtomique.Mn;
import domaine.typeAtomique.N;
import domaine.typeAtomique.Na;
import domaine.typeAtomique.Ni;
import domaine.typeAtomique.O;
import domaine.typeAtomique.P;
import domaine.typeAtomique.S;
import domaine.typeAtomique.Si;
import domaine.typeAtomique.Ti;
import domaine.typeAtomique.TypeAtomique;
import domaine.typeAtomique.Zn;

public class Atome {

	private double x;
	private double y;
	private double z;
	private String nomDeRole;
	private Color couleur;
	private int numId;
	private TypeAtomique elem;

	public Atome(double x, double y, double z, String nomDeRole, int numId,String elem) {
		this.x=x;
		this.y=y;
		this.z=z;
		this.nomDeRole=nomDeRole;
		this.numId=numId;
		this.elem=typeAtomique(elem);
	}

	private TypeAtomique typeAtomique(String elem) {
		TypeAtomique type;
		if(elem.equals("O"))
			type=new O();
		else if(elem.equals("N"))
			type=new N();
		else if(elem.equals("C"))
			type=new C();
		else if(elem.equals("AG"))
			type=new Ag();
		else if(elem.equals("AL"))
			type=new Al();
		else if(elem.equals("AU"))
			type=new Au();
		else if(elem.equals("B"))
			type=new B();
		else if(elem.equals("BA"))
			type=new Ba();
		else if(elem.equals("BN"))
			type=new Bn();
		else if(elem.equals("CA"))
			type=new Ca();
		else if(elem.equals("CL"))
			type=new Cl();
		else if(elem.equals("CR"))
			type=new Cr();
		else if(elem.equals("CU"))
			type=new Cu();
		else if(elem.equals("F"))
			type=new F();
		else if(elem.equals("FE"))
			type=new Fe();
		else if(elem.equals("H"))
			type=new H();
		else if(elem.equals("HE"))
			type=new He();
		else if(elem.equals("I"))
			type=new I();
		else if(elem.equals("LI"))
			type=new Li();
		else if(elem.equals("MG"))
			type=new Mg();
		else if(elem.equals("MN"))
			type=new Mn();
		else if(elem.equals("NA"))
			type=new Na();
		else if(elem.equals("NI"))
			type=new Ni();
		else if(elem.equals("P"))
			type=new P();
		else if(elem.equals("S"))
			type=new S();
		else if(elem.equals("SI"))
			type=new Si();
		else if(elem.equals("TI"))
			type=new Ti();
		else if(elem.equals("ZN"))
			type=new Zn();
		else
			type=new AutreTypeAtomique(elem);
		return type;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}



	public Color getCouleur() {
		if(couleur==null)
			return this.getElem().getCouleur();
		else
			return couleur;
	}
	public void setColor(Color couleur)
	{
		this.couleur=couleur;
	}
	public String getNomDeRole() {
		return nomDeRole;
	}

	public int getNumId() {
		return numId;
	}

	public TypeAtomique getElem() {
		return elem;
	}

	public String toString() {
		String s = "";
		s+="numIdAtome:"+numId+"\n";
		s+="elem:"+elem+"\n";
		s+="nomDeRole:"+nomDeRole+"\n";
		s+="x:"+x+"\n";
		s+="y:"+y+"\n";
		s+="z:"+z+"\n";
		return s;
	}

	public void setX(double x) {
		this.x=x;
	}

	public void setY(double y) {
		this.y=y;
	}

	public void setZ(double z) {
		this.z=z;
	}

	public void dessiner(TransformGroup group, double[] barycentre)
	{
		if(this.couleur!=null)
			this.dessiner(couleur, group, barycentre);
		else
			this.dessiner(this.elem.getCouleur(), group, barycentre);
	}

	public void dessiner(Color couleur, TransformGroup group, double[] barycentre) {
		
	}
}
