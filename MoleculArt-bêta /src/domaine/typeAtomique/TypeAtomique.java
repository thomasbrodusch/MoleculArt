/**
 * @author Kevin Lavergne & Damien Kozak
 * @version v1.3
 */
package domaine.typeAtomique;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public abstract class TypeAtomique implements ConstantesCouleurAtome {

	private double rayonVanDerWaals;

	public TypeAtomique() {
		rayonVanDerWaals=this.lireFichierRayon(this.getClass().getSimpleName());
	}

	public double lireFichierRayon(String type) {
		double rayon = 0;
		try {

			BufferedReader buff = new BufferedReader(new FileReader("RayonVanDerWaals.txt"));
			try {
				String line;
				StringTokenizer st;
				while ((line = buff.readLine()) != null)
				{
					st=new StringTokenizer(line,":");
					if(st.nextToken().equals(type))
					{
						rayon=Double.parseDouble(st.nextToken());
					}
				}
			} 
			finally
			{
				buff.close();
			}
		} 
		catch (IOException ioe)
		{
			System.out.println("Erreur --" + ioe.toString());
		}
		return rayon;
	}

	public abstract Color getCouleur();

	public double getRayonVanDerWaals() {
		return this.rayonVanDerWaals;
	}

	public void setRayonVanDerWaals(double rayon) {
		this.rayonVanDerWaals = rayon;
	}

	public String getType()
	{
		return this.getClass().getSimpleName();
	}
	
	public String toString() {
		String s = "";
		s += this.getClass().getSimpleName() + "\n";
		s += "rayon:" + getRayonVanDerWaals() + "\n";
		s += "couleur:" + getCouleur();
		return s;
	}
}
