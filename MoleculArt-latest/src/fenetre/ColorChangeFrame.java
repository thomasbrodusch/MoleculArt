package fenetre;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.media.j3d.Background;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.vecmath.Color3f;

import domaine.StructurePDB;
/**
 * Classe permettant de changer la couleur du fond de la scene 3D ou d'une partie de la structure
 *
 */
public class ColorChangeFrame extends JFrame {

	private Background back;
	private StructurePDB struc;
	private int type;
	private String cible;
	private JColorChooser colorChooser;
	private Box3D scene;
	private int choixModele;
	/**
	 * Constructeur
	 */
	public ColorChangeFrame()
	{
		this.setSize(600,400);
		this.setLayout(new BorderLayout());
		colorChooser=new JColorChooser();
		this.add(colorChooser,"North");
		JButton changer=new JButton("Changer couleur");
		changer.addActionListener(new ActionChangeListener());
		this.add(changer,"South");
	}
	public void init(Background back)
	{
		this.back=back;
	}
	public void init(StructurePDB struc,int type,int choixModele,String cible,Box3D scene)
	{
		this.struc=struc;
		this.type=type;
		this.choixModele=choixModele;
		this.cible=cible;
		this.scene=scene;
	}
	class ActionChangeListener implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) {
			if(back!=null)
			{
				back.setColor(new Color3f(colorChooser.getColor()));
			}
			else if(struc!=null)
			{
				switch(type)
				{
				case 0:struc.changeColorAtome(cible, colorChooser.getColor());
					break;
				case 1:struc.changeColorResidus(cible, colorChooser.getColor());
					break;
				case 2:struc.changeColorChaine(cible, colorChooser.getColor());
					break;
				}
				scene.panCouleur();
			}
			ColorChangeFrame.this.dispose();
		}
		
	}
}
