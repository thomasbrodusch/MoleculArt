
package domaine.typeResidus;

import java.awt.Color;
/**
 * Classe représentant le type d'un résidus
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
	 * Renvoie le type du résidus
	 */
	public String getType()
	{
		return this.getClass().getSimpleName();
	}
	/**
	 * Renvoie la couleur par défaut du résidus
	 */
	public abstract Color getCouleur();
}
