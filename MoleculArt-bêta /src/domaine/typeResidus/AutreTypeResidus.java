/**
 * @author Kevin Lavergne & Damien Kozak
 * @version v1.3
 */
package domaine.typeResidus;

import java.awt.Color;


public class AutreTypeResidus extends TypeResidus {

	private String nomType;
	
	public AutreTypeResidus(String nomType) {
		this.nomType=nomType;
	}
	public String getType()
	{
		return this.nomType;
	}
	public Color getCouleur()
	{
		return new Color(190,160,110);
	}
	public String toString()
	{
		String s="";
		s+=nomType+"(Autre type)\n";
		s+="couleurResidus:"+getCouleur()+"\n";
		return s;
	}
	public void afficher() {
		System.out.println("\t\t\t"+nomType);
		System.out.println("\t\t\tcouleurResidus:"+getCouleur());
	}

}
