package Codes;


import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jogamp.java3d.Alpha;
import org.jogamp.java3d.Appearance;
import org.jogamp.java3d.BoundingSphere;
import org.jogamp.java3d.BranchGroup;
import org.jogamp.java3d.Canvas3D;
import org.jogamp.java3d.Material;
import org.jogamp.java3d.PointLight;
import org.jogamp.java3d.RotationInterpolator;
import org.jogamp.java3d.Transform3D;
import org.jogamp.java3d.TransformGroup;
import org.jogamp.java3d.utils.behaviors.keyboard.KeyNavigatorBehavior;
import org.jogamp.java3d.utils.geometry.Box;
import org.jogamp.java3d.utils.universe.SimpleUniverse;
import org.jogamp.java3d.utils.universe.ViewingPlatform;
import org.jogamp.vecmath.Color3f;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Point3f;
import org.jogamp.vecmath.Vector3d;
/**
 * This class controls the rotation behavior and color of the objects
 * @author Mahmoud Alkwisem
 *
 */
public class CommonsMA extends JPanel {

	private static final long serialVersionUID = 1L;
	private static JFrame frame;
	
	public final static Color3f Red = new Color3f(1.0f, 0.0f, 0.0f);
	public final static Color3f Green = new Color3f(0.0f, 1.0f, 0.0f);
	public final static Color3f Blue = new Color3f(0.0f, 0.0f, 1.0f);
	public final static Color3f Yellow = new Color3f(1.0f, 1.0f, 0.0f);
	public final static Color3f Cyan = new Color3f(0.0f, 1.0f, 1.0f);
	public final static Color3f Orange = new Color3f(1.0f, 0.5f, 0.0f);
	public final static Color3f Magenta = new Color3f(1.0f, 0.0f, 1.0f);
	public final static Color3f White = new Color3f(1.0f, 1.0f, 1.0f);
	public final static Color3f Grey = new Color3f(0.35f, 0.35f, 0.35f);
	public final static Color3f Black = new Color3f(0.0f, 0.0f, 0.0f);
	public final static Color3f[] clr_list = {Blue, Green, Red, Yellow,
			Cyan, Orange, Magenta, Grey};
	public final static int clr_num = 8;
	private static Color3f[] mtl_clrs = {White, Grey, Black};

	public final static BoundingSphere hundredBS = new BoundingSphere(new Point3d(), 100.0);
	public final static BoundingSphere twentyBS = new BoundingSphere(new Point3d(), 20.0);

    /* A1: function to define object's material and use it to set object's appearance */
	/**
	 * A method to define object's material and use it to set object's appearance
	 * @param m_clr
	 * @return An appearance
	 */
	public static Appearance obj_Appearance(Color3f m_clr) {		
		Material mtl = new Material();                     // define material's attributes
		mtl.setShininess(32);
		mtl.setAmbientColor(mtl_clrs[0]);                   // use them to define different materials
		mtl.setDiffuseColor(m_clr);
		mtl.setSpecularColor(mtl_clrs[1]);
		mtl.setEmissiveColor(mtl_clrs[2]);                  // use it to switch button on/off
		mtl.setLightingEnable(true);

		Appearance app = new Appearance();
		app.setMaterial(mtl);                              // set appearance's material
		return app;
	}	
	
	/* a function to create a rotation behavior and refer it to 'my_TG' */
	/**
	 * A method to create a rotation behavior and refer it to 'my_TG' 
	 * @param r_num
	 * @param rotTG
	 * @return A RotationInterpolator
	 */
	public static RotationInterpolator rotate_Behavior(int r_num, TransformGroup rotTG) {

		rotTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		Transform3D yAxis = new Transform3D();
		Alpha rotationAlpha = new Alpha(-1, r_num);
		RotationInterpolator rot_beh = new RotationInterpolator(
				rotationAlpha, rotTG, yAxis, 0.0f, (float) Math.PI * 2.0f);
		rot_beh.setSchedulingBounds(hundredBS);
		return rot_beh;
	}
	
	/* a function to place one light or two lights at opposite locations */
	/**
	 * A method to place one light or two lights at opposite locations 
	 * @param clr
	 * @param p_num
	 * @return A branch group
	 */
	public static BranchGroup add_Lights(Color3f clr, int p_num) {
		BranchGroup lightBG = new BranchGroup();
		Point3f atn = new Point3f(0.5f, 0.0f, 0.0f);
		PointLight ptLight;
		float adjt = 1f;
		for (int i = 0; (i < p_num) && (i < 2); i++) {
			if (i > 0) 
				adjt = -1f; 
			ptLight = new PointLight(clr, new Point3f(3.0f * adjt, 1.0f, 3.0f  * adjt), atn);
			ptLight.setInfluencingBounds(hundredBS);
			lightBG.addChild(ptLight);
		}
		return lightBG;
	}

	/* a function to position viewer to 'eye' location */
	/**
	 * A method to postion viewer to'eye' location
	 * @param simple_U
	 * @param eye
	 */
	public static void define_Viewer(SimpleUniverse simple_U, Point3d eye) {

	    TransformGroup viewTransform = simple_U.getViewingPlatform().getViewPlatformTransform();
		Point3d center = new Point3d(0, 0, 0);             // define the point where the eye looks at
		Vector3d up = new Vector3d(0, 1, 0);               // define camera's up direction
		Transform3D view_TM = new Transform3D();
		view_TM.lookAt(eye, center, up);
		view_TM.invert();
	    viewTransform.setTransform(view_TM);               // set the TransformGroup of ViewingPlatform
	}

	/* a function to allow key navigation with the ViewingPlateform */
	/**
	 * A method to allow key navigation with the ViewingPlateform
	 * @param simple_U
	 * @return A KeyNavigatorBehavior
	 */
	public static KeyNavigatorBehavior key_Navigation(SimpleUniverse simple_U) {
		ViewingPlatform view_platfm = simple_U.getViewingPlatform();
		TransformGroup view_TG = view_platfm.getViewPlatformTransform();
		KeyNavigatorBehavior keyNavBeh = new KeyNavigatorBehavior(view_TG);
		keyNavBeh.setSchedulingBounds(twentyBS);
		return keyNavBeh;
	}

	/* a function to build the content branch and attach to 'scene' */
	/**
	 * A method to build the content branch and attach to 'scene'
	 * @return A branch group
	 */
	public static BranchGroup create_Scene() {
		BranchGroup sceneBG = new BranchGroup();
		TransformGroup sceneTG = new TransformGroup();
		sceneTG.addChild(new Box(0.5f, 0.5f, 0.5f, obj_Appearance(Cyan)));
		sceneBG.addChild(rotate_Behavior(7500, sceneTG));
		
		sceneBG.addChild(sceneTG);
		return sceneBG;
	}

	/* a constructor to set up for the application */
	/**
	 * The constructor
	 * @param sceneBG
	 */
	public CommonsMA(BranchGroup sceneBG) {
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		Canvas3D canvas = new Canvas3D(config);
		
		SimpleUniverse su = new SimpleUniverse(canvas);    // create a SimpleUniverse
		define_Viewer(su, new Point3d(1.0d, 1.0d, 4.0d));  // set the viewer's location
		
		sceneBG.addChild(add_Lights(White, 1));	
		sceneBG.addChild(key_Navigation(su));              // allow key navigation
		sceneBG.compile();		                           // optimize the BranchGroup
		su.addBranchGraph(sceneBG);                        // attach the scene to SimpleUniverse

		setLayout(new BorderLayout());
		add("Center", canvas);
		frame.setSize(800, 800);                           // set the size of the JFrame
		frame.setVisible(true);
	}
	/**
	 * The main method
	 * @param args
	 */
	public static void main(String[] args) {
		frame = new JFrame("MA's Common File");            // NOTE: change MA to student's initials
		frame.getContentPane().add(new CommonsMA(create_Scene()));  // create an instance of the class
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}