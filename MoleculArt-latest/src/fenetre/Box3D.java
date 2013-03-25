package fenetre;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.media.j3d.Background;
import javax.media.j3d.BoundingBox;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Light;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.View;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;

import domaine.Atome;
import domaine.Chaine;
import domaine.Modele;
import domaine.Residus;
import domaine.StructurePDB;
/**
 * Panneau contenant la représentation 3D de la molécule
 *
 */

public class Box3D extends JPanel {

	private Canvas3D canvas3D;
	private StructurePDB struc;
	private Background background;
	private BranchGroup scene;
	private SimpleUniverse simpleU;
	private JComboBox contrainteColor;
	private JComboBox choixModel;
	private JPanel panCouleur;
	/**
	 * Constructeur
	 */
	public Box3D() {
		this.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.white), "Visualisation"));
		this.setLayout(new BorderLayout());
	}
/**
 * Initialise le paneau de Visualisation 3D
 * @param struc
 */
	public void init(StructurePDB struc)
	{
		this.struc=struc;
		this.removeAll();
		this.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.white), "Visualisation"));
		this.setLayout(new BorderLayout());
		this.add(panelParam(), BorderLayout.EAST);
	}
	/**
	 * Dessiner un certain modele de représentation de la structure PDB
	 * @param choixModele
	 */
	public void dessiner(int choixModele) {
		if(canvas3D!=null)
			this.remove(canvas3D);
		// Etape 3 :
		// Creation du Canvas 3D
		canvas3D = new Canvas3D(
				SimpleUniverse.getPreferredConfiguration());

		this.add(canvas3D, BorderLayout.CENTER);
		// Etape 4 :
		// Creation d'un objet SimpleUniverse
		simpleU = new SimpleUniverse(canvas3D);

		// Etape 5 :
		// Positionnement du point d'observation pour avoir une vue correcte de
		// la
		// scene 3D
		simpleU.getViewingPlatform().setNominalViewingTransform();
		View view = simpleU.getViewer().getView();
		view.setBackClipDistance(80);

		// Etape 6 :
		// Creation de la scene 3D qui contient tous les objets 3D que l'on veut
		// visualiser
		scene = createSceneGraph(struc,choixModele);
		// Etape 7 :
		// Compilation de la scene 3D
		scene.compile();

		// Etape 8:
		// Attachement de la scene 3D a l'objet SimpleUniverse
		simpleU.addBranchGraph(scene);
		this.updateUI();
	}

	/**
	 * Creation de la scene 3D qui contient tous les objets 3D
	 * @param choixModele 
	 * 
	 * @return scene 3D
	 */
	public BranchGroup createSceneGraph(StructurePDB struc, int choixModele) {
		// Creation de l'objet parent qui contiendra tous les autres objets 3D
		BranchGroup parent = new BranchGroup();
		parent.setCapability(BranchGroup.ALLOW_DETACH);
		/************ Partie de code concernant l'animation du cube *************/
		/*
		 * Elle sera expliquee en details dans les chapitres relatifs aux
		 * transformations geometriques et aux animations
		 */
		
		Transform3D trans = new Transform3D();
		trans.set(new Vector3f(0, 0, -160));
		TransformGroup objSpin = new TransformGroup(trans);
		objSpin.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		objSpin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		/*************** Fin de la partie relative a l'animation ***************/
		// Arriere plan en blanc
		background = new Background(0, 0,0);
		background.setCapability(Background.ALLOW_COLOR_WRITE);
		background.setApplicationBounds(new BoundingBox());
		parent.addChild(background);

		MouseZoom zoom = new MouseZoom(objSpin);
		zoom.setFactor(0.4);
		zoom.setSchedulingBounds(new BoundingSphere());
		parent.addChild(zoom);
		
		MouseRotate rotate = new MouseRotate(objSpin);
		rotate.setFactor(0.02);
		BoundingSphere bound = new BoundingSphere();
		bound.setCenter(new Point3d(0, 0, 0));
		rotate.setSchedulingBounds(bound);
		parent.addChild(rotate);
		double[] barycentre = struc.barycentre(choixModele);
		for(Modele m:struc.getModeles())
		{
			for (Chaine c : m.getChaines()) {
				for (Residus r : c.getResidus()) {
					for (Atome a : r.getAtomes()) {

						DirectionalLight directionalLight = new DirectionalLight();
						directionalLight.setDirection(new Vector3f(1, -1, -1));
						directionalLight.setInfluencingBounds(new BoundingSphere());
						directionalLight.setCapability(Light.ALLOW_COLOR_WRITE);
						Sphere sphere=a.dessiner(a.getCouleur(),directionalLight);
						if(struc.getModeles().indexOf(m)==choixModel.getSelectedIndex())
						{
							double x = a.getX() - barycentre[0];
							double y = a.getY() - barycentre[1];
							double z = a.getZ() - barycentre[2];
							Transform3D translate = new Transform3D();
							translate.set(new Vector3f((float) x, (float) y, (float) z));
							TransformGroup TG1 = new TransformGroup(translate);
							objSpin.addChild(TG1);
							TG1.addChild(sphere);
							TG1.addChild(directionalLight);
						}
					}
				}
			}
		}
		parent.addChild(objSpin);
		return parent;
	}
	/**
	 * Créer le paneau de controle de la visualisation
	 */
	public JPanel panelParam() {
		JPanel jParam = new JPanel();
		jParam.setBorder(BorderFactory.createTitledBorder("Paramètres"));
		jParam.setLayout(new BorderLayout());
		choixModel=new JComboBox();
		for(int i=0;i<struc.getModeles().size();i++)
		{
			choixModel.addItem("Modele n°"+i);
		}
		choixModel.addActionListener(new ModeleSelection());
		contrainteColor=new JComboBox();
		contrainteColor.addItem("Couleur Atome");
		contrainteColor.addItem("Couleur Residus");
		contrainteColor.addItem("Couleur Chaine");
		contrainteColor.addActionListener(new SourceColorChangeListener());
		JToolBar toolBar = new JToolBar(JToolBar.VERTICAL);
		JButton change = new JButton("Changer la couleur du fond");
		change.addActionListener(new BackgroundChangeListener());
		toolBar.add(change);
		toolBar.add(choixModel);
		toolBar.add(contrainteColor);
		panCouleur=new JPanel();
		panCouleur();
		jParam.add(toolBar,"North");
		jParam.add(panCouleur);
		return jParam;

	}

	/**
	 * Met à jour les choix de couleur
	 */
	public void panCouleur()
	{
		panCouleur.removeAll();
		switch(contrainteColor.getSelectedIndex())
		{
		case 0:
			for(Atome a:struc.listAtomes())
			{
				panCouleur.setLayout(new GridLayout(struc.listAtomes().length,2));
				JButton colorChange=new JButton(a.getElem().getType());
				colorChange.addActionListener(new ColorChangeListener());
				panCouleur.add(colorChange);
				JPanel pan=new JPanel();
				pan.setBackground(a.getCouleur());
				panCouleur.add(pan);
			}
		break;
		case 1:
			
			for(Residus r:struc.listResidus())
			{
				panCouleur.setLayout(new GridLayout(struc.listResidus().length,2));
				JButton colorChange=new JButton(r.getType().getType());
				colorChange.addActionListener(new ColorChangeListener());
				panCouleur.add(colorChange);
				JPanel pan=new JPanel();
				pan.setBackground(r.getCouleur());
				panCouleur.add(pan);
			}
		break;
		case 2:
			for(Chaine c:struc.listChaines())
			{
				panCouleur.setLayout(new GridLayout(struc.listChaines().length,2));
				JButton colorChange=new JButton(c.getId());
				colorChange.addActionListener(new ColorChangeListener());
				panCouleur.add(colorChange);
				JPanel pan=new JPanel();
				pan.setBackground(c.getCouleur());
				panCouleur.add(pan);
			}
		break;
		}
		panCouleur.updateUI();
	
	}

	class BackgroundChangeListener implements ActionListener
	{

		public void actionPerformed(ActionEvent arg0) {
			ColorChangeFrame frame=new ColorChangeFrame();
			frame.init(background);
			frame.setVisible(true);
		}
		
	}
	class ColorChangeListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			ColorChangeFrame frame=new ColorChangeFrame();
			frame.init(struc,contrainteColor.getSelectedIndex(),choixModel.getSelectedIndex(),e.getActionCommand(),Box3D.this);
			frame.setVisible(true);
		}
	}
	class SourceColorChangeListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			scene.detach();
			struc.redessiner(contrainteColor.getSelectedIndex(),choixModel.getSelectedIndex());
			simpleU.addBranchGraph(scene);
			panCouleur();
		}
	}
	class ModeleSelection implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) {
			dessiner(choixModel.getSelectedIndex());
			struc.redessiner(contrainteColor.getSelectedIndex(),choixModel.getSelectedIndex());
		}
		
	}
}