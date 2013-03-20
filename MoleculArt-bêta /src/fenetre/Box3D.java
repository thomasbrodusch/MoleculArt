package fenetre;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingBox;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Material;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.View;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;

import domaine.Atome;
import domaine.Chaine;
import domaine.Residus;
import domaine.StructurePDB;

public class Box3D extends JPanel {

	private Canvas3D canvas3D;
	private StructurePDB struc;
	private Background background;
	
	public Box3D() {
		this.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.white), "Visualisation"));
		this.setLayout(new BorderLayout());
	}

	public void dessiner(StructurePDB struc) {
		if(canvas3D!=null)
			this.remove(canvas3D);
		this.struc=struc;
		// Etape 3 :
		// Creation du Canvas 3D
		canvas3D = new Canvas3D(
				SimpleUniverse.getPreferredConfiguration());

		this.add(canvas3D, BorderLayout.CENTER);
		this.add(panelParam(), BorderLayout.EAST);
		// Etape 4 :
		// Creation d'un objet SimpleUniverse
		SimpleUniverse simpleU = new SimpleUniverse(canvas3D);

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
		BranchGroup scene = createSceneGraph(this.struc);

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
	 * 
	 * @return scene 3D
	 */
	public BranchGroup createSceneGraph(StructurePDB struc) {
		// Creation de l'objet parent qui contiendra tous les autres objets 3D
		BranchGroup parent = new BranchGroup();
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
		background = new Background(0, 0, 0);
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
		double[] barycentre = struc.barycentre(0);
		for (Chaine c : struc.getModele(0).getChaines()) {
			for (Residus r : c.getResidus()) {
				for (Atome a : r.getAtomes()) {
					DirectionalLight directionalLight = new DirectionalLight();
					directionalLight.setColor(new Color3f (a.getCouleur().darker().darker().darker()));
					directionalLight.setDirection(new Vector3f(1, -1, -1));
					directionalLight.setInfluencingBounds(new BoundingSphere());
					Sphere sphere = new Sphere((float) a.getElem().getRayonVanDerWaals() / 100);
					Material emissiveColor = new Material();
					emissiveColor.setDiffuseColor(new Color3f(a.getCouleur()));
					emissiveColor.setEmissiveColor(new Color3f(a.getCouleur().darker().darker().darker()));
					Appearance appearance = new Appearance();
					appearance.setMaterial(emissiveColor);
					sphere.setAppearance(appearance);
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

		parent.addChild(objSpin);

		return parent;
	}
	public JPanel panelParam() {
		JPanel jParam = new JPanel();
		jParam.setBorder(BorderFactory.createTitledBorder("Paramètres"));
		JComboBox<String> contrainteColor=new JComboBox<String>();
		contrainteColor.addItem("Couleur Atome");
		contrainteColor.addItem("Couleur Residus");
		contrainteColor.addItem("Couleur Chaine");
		JToolBar toolBar = new JToolBar(JToolBar.VERTICAL);
		JButton changerCouler = new JButton("Changer la couleur");
		JButton change = new JButton("Changer la couleur du fond");
		change.addActionListener(new BackgroundChangeListener());
		toolBar.add(contrainteColor);
		toolBar.add(changerCouler);
		toolBar.add(change);
		jParam.add(toolBar);
		return jParam;

	}
	class BackgroundChangeListener implements ActionListener
	{

		public void actionPerformed(ActionEvent arg0) {
			JFrame frame=new JFrame();
			frame.setSize(600,400);
			JColorChooser colorChooser=new JColorChooser();
			frame.add(colorChooser);
			frame.setVisible(true);
//			background.setColor(new Color3f(colorChooser.getColor()));
		}
		
	}
}