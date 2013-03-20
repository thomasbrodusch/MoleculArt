/**
 * @author Kevin Lavergne & Damien Kozak
 * @version v1.3
 */
package domaine.typeAtomique;

import java.awt.Color;


public class AutreTypeAtomique extends TypeAtomique
{
	private String nomType;

	public AutreTypeAtomique(String nomType) {
		super();
		this.setRayonVanDerWaals(this.lireFichierRayon(nomType));
		this.nomType=nomType;
	}

	public String getType()
	{
		return this.nomType;
	}
	public Color getCouleur() {
		return deepPink;
	}
	
	public String toString() {
		String s = "";
		s+=nomType+"\n";
		s+="rayon:"+getRayonVanDerWaals()+"\n";
		s+="couleur:"+getCouleur();
		return s;
	}
}