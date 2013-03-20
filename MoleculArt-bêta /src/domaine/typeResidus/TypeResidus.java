/**
 * @author Kevin Lavergne & Damien Kozak
 * @version v1.3
 */
package domaine.typeResidus;

import java.awt.Color;

public abstract class TypeResidus implements ConstantesCouleurResidus
{
	public TypeResidus()
	{
		
	}
	
	public String getType()
	{
		return this.getClass().getSimpleName();
	}
	public abstract Color getCouleur();
	
	public String toString()
	{
		String s="";
		s+=this.getClass().getSimpleName()+"\n";
		s+="couleurResidus:"+getCouleur()+"\n";
		return s;
	}

	public void afficher() {
		System.out.println("\t\t\t"+this.getClass().getSimpleName());
		System.out.println("\t\t\tcouleurResidus:"+getCouleur());
	}
}
