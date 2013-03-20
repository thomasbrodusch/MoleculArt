/**
 * @author Kevin Lavergne & Damien Kozak
 * @version v1.3
 */

package domaine;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.media.j3d.TransformGroup;

import org.biojava.bio.structure.Author;

public class StructurePDB {

	private String pdbId;
	private String titre;
	private String[] auteurs;
	private Date datePublication;
	private ArrayList<Modele> modeles;

	public StructurePDB(String pdbId, String titre, List<Author> auteurs,Date datePublication) {
		this.pdbId = pdbId;
		this.titre = titre;
		this.auteurs=new String[auteurs.size()];
		for(int i=0;i<auteurs.size();i++)
		{
			this.auteurs[i]=auteurs.get(i).toString();
		}
		this.datePublication = datePublication;
		this.modeles = new ArrayList<Modele>();
	}
	 
	public String[] getSequences()
	{
		return this.getModele(0).getSequences();
	}
	
	
	
	public Modele getModele(int indice) {
		return modeles.get(indice);
	}

	public int size() {
		return this.modeles.size();
	}

	public void addModele(Modele modele) {
		this.modeles.add(modele);
	}

	public double[] barycentre(int indiceModel)
	{
		double x=0;
		double y=0;
		double z=0;
		int nbAtome=0;
		for(int i=0;i<this.getModele(indiceModel).size();i++)
		{
			for(Residus r:this.getModele(indiceModel).getChaine(i).getResidus())
			{
				for(Atome a:r.getAtomes())
				{
					x+=a.getX();
					y+=a.getY();
					z+=a.getZ();
					nbAtome++;
				}
			}
		}
		x=x/nbAtome;
		y=y/nbAtome;
		z=z/nbAtome;
		return new double[]{x,y,z};
	}
	
	public String getPdbId() {
		return pdbId;
	}

	public String getTitre() {
		return titre;
	}

	public String[] getAuteurs() {
		return auteurs;
	}

	public Date getDatePublication() {
		return datePublication;
	}

	public ArrayList<Modele> getModeles() {
		return modeles;
	}

	public void dessiner(int numModele, String contrainteCouleur,TransformGroup group) {
		modeles.get(numModele).dessiner(contrainteCouleur, group, this.barycentre(numModele));
	}

	public String toString() {
		String s = "";
		s += this.pdbId + "\n";
		s += this.titre + "\n";
		s += this.auteurs + "\n";
		s += this.datePublication + "\n";
		for (Modele m : modeles) {
			s += m + "\n";
		}
		return s;
	}
}
