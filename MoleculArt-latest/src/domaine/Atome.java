
package domaine;

import java.awt.Color;

import javax.media.j3d.Appearance;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Material;
import javax.vecmath.Color3f;

import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;

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
/**
 * Classe représentant un atome dans une molécule
 * @author Kevin Lavergne & Damien Kozak
 * @version v1.3
 */
public class Atome {

	private double x;
	private double y;
	private double z;
	private String nomDeRole;
	private Color couleur;
	private int numId;
	private TypeAtomique elem;
	private Sphere representation;
	private DirectionalLight light;
	/**
	 * Constructeur
	 * @param x
	 * @param y
	 * @param z
	 * @param nomDeRole
	 * @param numId
	 * @param elem
	 */
	public Atome(double x, double y, double z, String nomDeRole, int numId,String elem) {
		this.x=x;
		this.y=y;
		this.z=z;
		this.nomDeRole=nomDeRole;
		this.numId=numId;
		this.elem=typeAtomique(elem);
	}
/**
 * Construit le type atomique de l'atome
 * @param elem
 */
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
/**
 * Renvoie la coordonnée spacial x de l'atome
 */
	public double getX() {
		return x;
	}
	/**
	 * Renvoie la coordonnée spacial y de l'atome
	 */
	public double getY() {
		return y;
	}
	/**
	 * Renvoie la coordonnée spacial z de l'atome
	 */
	public double getZ() {
		return z;
	}


	/**
	 * Renvoie la couleur de l'atome
	 */
	public Color getCouleur() {
		if(couleur==null)
			return this.getElem().getCouleur();
		else
			return couleur;
	}
	/**
	 * Change la couleur de l'atome
	 * @param couleur
	 */
	public void setColor(Color couleur)
	{
		this.couleur=couleur;
		this.redessiner(couleur);
	}
	/**
	 * Renvoie le nom de role de l'atome
	 */
	public String getNomDeRole() {
		return nomDeRole;
	}
	/**
	 * Renvoie l'identifiant de l'atome
	 */
	public int getNumId() {
		return numId;
	}
	/**
	 * Renvoie le type atomique de l'atome
	 */
	public TypeAtomique getElem() {
		return elem;
	}
	/**
	 * Permet de dessiner la sphere qui représentera l'atome dans la scene 3D
	 */
	public Sphere dessiner(Color couleur,DirectionalLight light) {
		representation = new Sphere((float) this.getElem().getRayonVanDerWaals() / 100, Primitive.GENERATE_NORMALS, 86);
		this.light = light;
		Material emissiveColor = new Material();
		light.setColor(new Color3f (couleur.darker().darker()));
		emissiveColor.setDiffuseColor(new Color3f(couleur));
		emissiveColor.setEmissiveColor(new Color3f(couleur.darker().darker()));
		Appearance appearance = new Appearance();
		appearance.setMaterial(emissiveColor);
		representation.setAppearance(appearance);
		emissiveColor.setCapability(Material.ALLOW_COMPONENT_WRITE);
		appearance.setCapability(Appearance.ALLOW_MATERIAL_WRITE);
		representation.setCapability(Sphere.ENABLE_APPEARANCE_MODIFY);
		representation.setCapability(Sphere.ALLOW_CHILDREN_WRITE);
		return representation;
	}
	/**
	 * Permet de redessiner la sphere qui représente l'atome dans la scene 3D
	 */
	public void redessiner(Color couleur)
	{
		Material emissiveColor = new Material();
		light.setColor(new Color3f (couleur.darker().darker()));
		emissiveColor.setDiffuseColor(new Color3f(couleur));
		emissiveColor.setEmissiveColor(new Color3f(couleur.darker().darker()));
		Appearance appearance = representation.getAppearance();
		appearance.setMaterial(emissiveColor);
	}
	public boolean equals(Object o)
	{
		return ((Atome)o).getElem().getType().equals(this.getElem().getType());
	}
}
