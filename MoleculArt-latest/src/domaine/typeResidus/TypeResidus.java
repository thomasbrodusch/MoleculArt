
package domaine.typeResidus;

import java.awt.Color;
/**
 * Classe repr�sentant le type d'un r�sidus
 * @author Kevin Lavergne & Damien Kozak
 * @version v1.3
 */
public abstract class TypeResidus implements ConstantesCouleurResidus
{
	/**
	 * Constructeur
	 */
	public TypeResidus()
	{
		
	}
	/**
	 * Renvoie le type du r�sidus
	 */
	public String getType()
	{
		return this.getClass().getSimpleName();
	}
	/**
	 * Renvoie la couleur par d�faut du r�sidus
	 */
	public abstract Color getCouleur();
}
