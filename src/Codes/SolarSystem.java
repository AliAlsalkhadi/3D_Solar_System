package Codes;


import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jdesktop.j3d.examples.sound.PointSoundBehavior;
import org.jdesktop.j3d.examples.sound.audio.JOALMixer;
import org.jogamp.java3d.Alpha;
import org.jogamp.java3d.Appearance;
import org.jogamp.java3d.Background;
import org.jogamp.java3d.BoundingSphere;
import org.jogamp.java3d.BranchGroup;
import org.jogamp.java3d.Canvas3D;
import org.jogamp.java3d.ColoringAttributes;
import org.jogamp.java3d.DirectionalLight;
import org.jogamp.java3d.GeometryArray;
import org.jogamp.java3d.ImageComponent2D;
import org.jogamp.java3d.LineStripArray;
import org.jogamp.java3d.Material;
import org.jogamp.java3d.Node;
import org.jogamp.java3d.PointLight;
import org.jogamp.java3d.PointSound;
import org.jogamp.java3d.RotationInterpolator;
import org.jogamp.java3d.Shape3D;
import org.jogamp.java3d.Texture;
import org.jogamp.java3d.Texture2D;
import org.jogamp.java3d.Transform3D;
import org.jogamp.java3d.TransformGroup;
import org.jogamp.java3d.loaders.IncorrectFormatException;
import org.jogamp.java3d.loaders.ParsingErrorException;
import org.jogamp.java3d.loaders.Scene;
import org.jogamp.java3d.loaders.objectfile.ObjectFile;
import org.jogamp.java3d.utils.behaviors.mouse.MouseRotate;
import org.jogamp.java3d.utils.geometry.Primitive;
import org.jogamp.java3d.utils.geometry.Sphere;
import org.jogamp.java3d.utils.image.TextureLoader;
import org.jogamp.java3d.utils.picking.PickResult;
import org.jogamp.java3d.utils.picking.PickTool;
import org.jogamp.java3d.utils.universe.SimpleUniverse;
import org.jogamp.java3d.utils.universe.Viewer;
import org.jogamp.vecmath.Color3f;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Point3f;
import org.jogamp.vecmath.Vector3d;
import org.jogamp.vecmath.Vector3f;

/**
 * This class is responsible for generating a visual representation of the solar system
 * @author Mahmoud Alkwisem
 *@version 2.0
 *
 */
public class SolarSystem extends JPanel implements KeyListener, MouseListener{

	private static final long serialVersionUID = 1L;
	private static JFrame frame;
	private static final int OBJ_NUM = 1;
	private static boolean isON = true;
	private Canvas3D canvas;
	private static PickTool pickTool;
	private static SoundUtilityJOAL soundJOAL;

	
	private static PointSound ps;
	private static PointSound psSun;
	private static PointSound psMercury;
	private static PointSound psVenus;
	private static PointSound psEarth;
	private static PointSound psMars;
	private static PointSound psJupiter;
	private static PointSound psSaturn;
	private static PointSound psUranus;
	private static PointSound psNeptune;
	private static PointSound psPluto;
	SimpleUniverse su;
	/**
	 * This is the instructor where everything is ran
	 * @param sceneBG
	 */
	public SolarSystem(BranchGroup sceneBG) {
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		canvas = new Canvas3D(config);
		canvas.addKeyListener(this);
		canvas.addMouseListener(this);
		su = new SimpleUniverse(canvas);    // create a SimpleUniverse
		enableAudio(su);  

		CommonsMA.define_Viewer(su, new Point3d(20.0d, 6, 5.0d));
		
		sceneBG.addChild(CommonsMA.key_Navigation(su));     // allow key navigation
		sceneBG.compile();		                           // optimize the BranchGroup
		su.addBranchGraph(sceneBG);                        // attach the scene to SimpleUniverse
		
        setLayout(new BorderLayout());
        add(canvas, BorderLayout.CENTER);

		frame.setSize(800, 800);                           // set the size of the JFrame
		frame.setVisible(true);
	}
	
	
	
	
	/* a function to build the content branch */
	/**
	 * This method creates the scene graph which includes all the objects in this program
	 * @return a branch group
	 */
	public static BranchGroup create_Scene() {
			
		BranchGroup sceneBG = new BranchGroup(); // create the scene's BranchGroup
		TransformGroup sceneTG = new TransformGroup(); // create the scene's TransformGroup
		SolarSysShapes[] assignment1MA = new SolarSysShapes[OBJ_NUM];
		assignment1MA[0] = new _3dAxis();
		TransformGroup sceneTGMercury = new TransformGroup();
		TransformGroup sceneTGVenus = new TransformGroup();
		TransformGroup sceneTGEarth = new TransformGroup();
		TransformGroup sceneTGMars = new TransformGroup();
		TransformGroup sceneTGJupiter = new TransformGroup();
		TransformGroup sceneTGSatRings = new TransformGroup();
		TransformGroup sceneTGUranus = new TransformGroup();
		TransformGroup sceneTGNeptune = new TransformGroup();
		TransformGroup sceneTGPluto = new TransformGroup();
		TransformGroup sceneTGo = new TransformGroup();

		float mercuryOrbitRadius = 3.5f;
	    float venusOrbitRadius = 5.0f;
	    float earthOrbitRadius = 6.0f;
	    float marsOrbitRadius = 7f;
	    float jupiterOrbitRadius = 8.5f;
	    float saturnOrbitRadius = 10.0f;
	    float uranusOrbitRadius = 11.5f;
	    float neptuneOrbitRadius = 12.5f;
	    float plutoOrbitRadius = 13.5f;


	    Shape3D mercuryOrbit = createOrbit(mercuryOrbitRadius);
	    Shape3D venusOrbit = createOrbit(venusOrbitRadius);
	    Shape3D earthOrbit = createOrbit(earthOrbitRadius);
	    Shape3D marsOrbit = createOrbit(marsOrbitRadius);
	    Shape3D jupiterOrbit = createOrbit(jupiterOrbitRadius);
	    Shape3D saturnOrbit = createOrbit(saturnOrbitRadius);
	    Shape3D uranusOrbit = createOrbit(uranusOrbitRadius);
	    Shape3D neptuneOrbit = createOrbit(neptuneOrbitRadius);
	    Shape3D plutoOrbit = createOrbit(plutoOrbitRadius);
	    
	    sceneTGo.addChild(mercuryOrbit);
	    sceneTGo.addChild(venusOrbit);
	    sceneTGo.addChild(earthOrbit);
	    sceneTGo.addChild(marsOrbit);
	    sceneTGo.addChild(jupiterOrbit);
	    sceneTGo.addChild(saturnOrbit);
	    sceneTGo.addChild(uranusOrbit);
	    sceneTGo.addChild(neptuneOrbit);
	    sceneTGo.addChild(plutoOrbit);
		
		sceneTGMercury.addChild(mercury());
		sceneTGMercury.addChild(psMercury());
		psMercury.setEnable(false);
		
		sceneTGVenus.addChild(venus());
		sceneTGVenus.addChild(psVenus());
		psVenus.setEnable(false);
		
		sceneTGEarth.addChild(earth());
		sceneTGEarth.addChild(psEarth());
		psEarth.setEnable(false);
		
		sceneTGMars.addChild(mars());
		sceneTGMars.addChild(psMars());
		psMars.setEnable(false);
		
		sceneTGJupiter.addChild(jupiter());
		sceneTGJupiter.addChild(psJupiter());
		psJupiter.setEnable(false);
		
		
		sceneTGUranus.addChild(uranus());
		sceneTGUranus.addChild(psUranus());
		psUranus.setEnable(false);
		
		sceneTGNeptune.addChild(neptune());
		sceneTGNeptune.addChild(psNeptune());
		psNeptune.setEnable(false);
		
		sceneTGPluto.addChild(pluto());
		sceneTGPluto.addChild(psPluto());
		psPluto.setEnable(false);
		
		sceneTGSatRings.addChild(saturnR());
		sceneTGSatRings.addChild(psSaturn());
		psSaturn.setEnable(false);
		
		sceneTG.addChild(sun());

		// Add the point sound and psSun to the sceneTG
		sceneTG.addChild(pointSound());
		sceneTG.addChild(psSun());
		ps.setEnable(false);
	    psSun.setEnable(false);
	    
	    

		BoundingSphere bounds = new BoundingSphere( new Point3d(0.0, 0.0, 0.0), Double.MAX_VALUE);
		sceneTG.addChild(createBkground(CommonsMA.Cyan, bounds));


		for (int i = 0; i < OBJ_NUM; i++)
			sceneTG.addChild(assignment1MA[i].position_Object());
		
		sceneBG.addChild(CommonsMA.add_Lights(CommonsMA.White, 1));	
		sceneBG.addChild(CommonsMA.rotate_Behavior(7500, sceneTG));	
		sceneBG.addChild(CommonsMA.rotate_Behavior(7000, sceneTGMercury));
		sceneBG.addChild(CommonsMA.rotate_Behavior(8000, sceneTGVenus));
		sceneBG.addChild(CommonsMA.rotate_Behavior(9000, sceneTGEarth));
		sceneBG.addChild(CommonsMA.rotate_Behavior(11000, sceneTGMars));
		sceneBG.addChild(CommonsMA.rotate_Behavior(13000, sceneTGJupiter));
		sceneBG.addChild(CommonsMA.rotate_Behavior(16000, sceneTGUranus));
		sceneBG.addChild(CommonsMA.rotate_Behavior(18000, sceneTGNeptune));
		sceneBG.addChild(CommonsMA.rotate_Behavior(20000, sceneTGPluto));
		sceneBG.addChild(CommonsMA.rotate_Behavior(15000, sceneTGSatRings));
		TransformGroup solarSystemTransformGroup = new TransformGroup();
	    solarSystemTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	    solarSystemTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

	    // Add all individual TransformGroup objects for planets, sun, and other solar system elements
	    solarSystemTransformGroup.addChild(sceneTGMercury);
	    solarSystemTransformGroup.addChild(sceneTGVenus);
	    solarSystemTransformGroup.addChild(sceneTGEarth);
	    solarSystemTransformGroup.addChild(sceneTGMars);
	    solarSystemTransformGroup.addChild(sceneTGJupiter);
	    solarSystemTransformGroup.addChild(sceneTGUranus);
	    solarSystemTransformGroup.addChild(sceneTGNeptune);
	    solarSystemTransformGroup.addChild(sceneTGPluto);
	    solarSystemTransformGroup.addChild(sceneTG);  
	    solarSystemTransformGroup.addChild(sceneTGSatRings);
	    //if you want to see the planet's orbits uncomment this line
	    //solarSystemTransformGroup.addChild(sceneTGo);

	    MouseRotate mouseRotate = new MouseRotate(solarSystemTransformGroup);
	    mouseRotate.setSchedulingBounds(new BoundingSphere(new Point3d(), 1000.0));
	    sceneBG.addChild(mouseRotate);
	    
	    // Add solarSystemTransformGroup to sceneBG
	    sceneBG.addChild(solarSystemTransformGroup);
	    

		pickTool = new PickTool( sceneBG );                // allow picking of objects in 'sceneBG'
		pickTool.setMode(PickTool.BOUNDS);
		
		
		return sceneBG;
	}
	/**
	 * This method imports a background image for the simple universe
	 * @param clr 
	 * @param bounds 
	 * @return return a branch group
	 */
	private static Background createBkground(Color3f clr, BoundingSphere bounds) {
		Background bg = new Background();
		bg.setImage(new TextureLoader("models/8k_space.jpg", null).getImage());
		bg.setImageScaleMode(Background.SCALE_FIT_MAX);
		bg.setApplicationBounds(bounds);  // SCALE_REPEAT, SCALE_NONE_CENTER
		bg.setColor(clr);
		return bg;
	}
	/**
	 * This method allows sounds to be played in the scene
	 * @param simple_U
	 */
	private void enableAudio(SimpleUniverse simple_U) {

		JOALMixer mixer = null;		                         // create a null mixer as a joalmixer
		Viewer viewer = simple_U.getViewer();
		viewer.getView().setBackClipDistance(20.0f);         // make object(s) disappear beyond 20f 

		if (mixer == null && viewer.getView().getUserHeadToVworldEnable()) {			                                                 
			mixer = new JOALMixer(viewer.getPhysicalEnvironment());
			if (!mixer.initialize()) {                       // add mixer as audio device if successful
				System.out.println("Open AL failed to init");
				viewer.getPhysicalEnvironment().setAudioDevice(null);
			}
		}
	}

	/**
	 * This method associates an object with a sound
	 * @return
	 */
	private static PointSound pointSound() {
		URL url = null;
		String filename = "sounds/AMU.wav";
		try {
			url = new URL("file", "localhost", filename);
		} catch (Exception e) {
			System.out.println("Can't open " + filename);
		}
		ps = new PointSound(); // create and position a point sound
	    ps.setCapability(PointSound.ALLOW_ENABLE_WRITE);
	    ps.setCapability(PointSound.ALLOW_IS_PLAYING_READ);
		PointSoundBehavior player = new PointSoundBehavior(ps, url, new Point3f(0.0f, 0.0f, 0.0f));
		player.setSchedulingBounds(new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0));
		return ps;
	}

	/**
	 * This method imports a sound for the Sun 
	 * @return a Point Sound
	 */
	private static PointSound psSun() {
		URL url = null;
		String filename = "sounds/venus.wav";
		try {
			url = new URL("file", "localhost", filename);
		} catch (Exception e) {
			System.out.println("Can't open " + filename);
		}
		psSun = new PointSound(); // create and position a point sound
		psSun.setCapability(PointSound.ALLOW_ENABLE_WRITE);
		psSun.setCapability(PointSound.ALLOW_ENABLE_READ);
		PointSoundBehavior player = new PointSoundBehavior(psSun, url, new Point3f(0.0f, 0.0f, 0.0f));
		player.setSchedulingBounds(new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0));
		return psSun;
	}
	
	/**
	 * This method imports a sound for Mercury 
	 * @return a Point Sound
	 */
	private static PointSound psMercury() {
		URL url = null;
		String filename = "sounds/mercury.wav";
		try {
			url = new URL("file", "localhost", filename);
		} catch (Exception e) {
			System.out.println("Can't open " + filename);
		}
		psMercury = new PointSound(); // create and position a point sound
		psMercury.setCapability(PointSound.ALLOW_ENABLE_WRITE);
		psMercury.setCapability(PointSound.ALLOW_ENABLE_READ);
		PointSoundBehavior player = new PointSoundBehavior(psMercury, url, new Point3f(0.0f, 0.0f, 0.0f));
		player.setSchedulingBounds(new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0));
		return psMercury;
	}
	
	/**
	 * This method imports a sound for Venus
	 * @return a Point Sound
	 */
	private static PointSound psVenus() {
		URL url = null;
		String filename = "sounds/sun.wav";
		try {
			url = new URL("file", "localhost", filename);
		} catch (Exception e) {
			System.out.println("Can't open " + filename);
		}
		psVenus = new PointSound(); // create and position a point sound
		psVenus.setCapability(PointSound.ALLOW_ENABLE_WRITE);
		psVenus.setCapability(PointSound.ALLOW_ENABLE_READ);
		PointSoundBehavior player = new PointSoundBehavior(psVenus, url, new Point3f(0.0f, 0.0f, 0.0f));
		player.setSchedulingBounds(new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0));
		return psVenus;
	}
	
	/**
	 * This method imports a sound for Earth
	 * @return a Point Sound
	 */
	private static PointSound psEarth() {
		URL url = null;
		String filename = "sounds/earth.wav";
		try {
			url = new URL("file", "localhost", filename);
		} catch (Exception e) {
			System.out.println("Can't open " + filename);
		}
		psEarth = new PointSound(); // create and position a point sound
		psEarth.setCapability(PointSound.ALLOW_ENABLE_WRITE);
		psEarth.setCapability(PointSound.ALLOW_ENABLE_READ);
		PointSoundBehavior player = new PointSoundBehavior(psEarth, url, new Point3f(0.0f, 0.0f, 0.0f));
		player.setSchedulingBounds(new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0));
		return psEarth;
	}
	/**
	 * This method imports a sound for Mars 
	 * @return a Point Sound
	 */
	private static PointSound psMars() {
		URL url = null;
		String filename = "sounds/mars.wav";
		try {
			url = new URL("file", "localhost", filename);
		} catch (Exception e) {
			System.out.println("Can't open " + filename);
		}
		psMars = new PointSound(); // create and position a point sound
		psMars.setCapability(PointSound.ALLOW_ENABLE_WRITE);
		psMars.setCapability(PointSound.ALLOW_ENABLE_READ);
		PointSoundBehavior player = new PointSoundBehavior(psMars, url, new Point3f(0.0f, 0.0f, 0.0f));
		player.setSchedulingBounds(new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0));
		return psMars;
	}
	/**
	 * This method imports a sound for Jupiter 
	 * @return a Point Sound
	 */
	private static PointSound psJupiter() {
		URL url = null;
		String filename = "sounds/jupiter.wav";
		try {
			url = new URL("file", "localhost", filename);
		} catch (Exception e) {
			System.out.println("Can't open " + filename);
		}
		psJupiter = new PointSound(); // create and position a point sound
		psJupiter.setCapability(PointSound.ALLOW_ENABLE_WRITE);
		psJupiter.setCapability(PointSound.ALLOW_ENABLE_READ);
		PointSoundBehavior player = new PointSoundBehavior(psJupiter, url, new Point3f(0.0f, 0.0f, 0.0f));
		player.setSchedulingBounds(new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0));
		return psJupiter;
	}
	/**
	 * This method imports a sound for Saturn 
	 * @return a Point Sound
	 */
	private static PointSound psSaturn() {
		URL url = null;
		String filename = "sounds/saturn.wav";
		try {
			url = new URL("file", "localhost", filename);
		} catch (Exception e) {
			System.out.println("Can't open " + filename);
		}
		psSaturn = new PointSound(); // create and position a point sound
		psSaturn.setCapability(PointSound.ALLOW_ENABLE_WRITE);
		psSaturn.setCapability(PointSound.ALLOW_ENABLE_READ);
		PointSoundBehavior player = new PointSoundBehavior(psSaturn, url, new Point3f(0.0f, 0.0f, 0.0f));
		player.setSchedulingBounds(new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0));
		return psSaturn;
	}
	/**
	 * This method imports a sound for Uranus 
	 * @return a Point Sound
	 */
	private static PointSound psUranus() {
		URL url = null;
		String filename = "sounds/Uranus.wav";
		try {
			url = new URL("file", "localhost", filename);
		} catch (Exception e) {
			System.out.println("Can't open " + filename);
		}
		psUranus = new PointSound(); // create and position a point sound
		psUranus.setCapability(PointSound.ALLOW_ENABLE_WRITE);
		psUranus.setCapability(PointSound.ALLOW_ENABLE_READ);
		PointSoundBehavior player = new PointSoundBehavior(psUranus, url, new Point3f(0.0f, 0.0f, 0.0f));
		player.setSchedulingBounds(new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0));
		return psUranus;
	}
	/**
	 * This method imports a sound for Neptune 
	 * @return a Point Sound
	 */
	private static PointSound psNeptune() {
		URL url = null;
		String filename = "sounds/neptune.wav";
		try {
			url = new URL("file", "localhost", filename);
		} catch (Exception e) {
			System.out.println("Can't open " + filename);
		}
		psNeptune = new PointSound(); // create and position a point sound
		psNeptune.setCapability(PointSound.ALLOW_ENABLE_WRITE);
		psNeptune.setCapability(PointSound.ALLOW_ENABLE_READ);
		PointSoundBehavior player = new PointSoundBehavior(psNeptune, url, new Point3f(0.0f, 0.0f, 0.0f));
		player.setSchedulingBounds(new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0));
		return psNeptune;
	}
	/**
	 * This method imports a sound for Pluto 
	 * @return a Point Sound
	 */
	private static PointSound psPluto() {
		URL url = null;
		String filename = "sounds/pluto.wav";
		try {
			url = new URL("file", "localhost", filename);
		} catch (Exception e) {
			System.out.println("Can't open " + filename);
		}
		psPluto = new PointSound(); // create and position a point sound
		psPluto.setCapability(PointSound.ALLOW_ENABLE_WRITE);
		psPluto.setCapability(PointSound.ALLOW_ENABLE_READ);
		PointSoundBehavior player = new PointSoundBehavior(psPluto, url, new Point3f(0.0f, 0.0f, 0.0f));
		player.setSchedulingBounds(new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0));
		return psPluto;
	}
	
	/**
	 * This method loads the textures
	 * @return a Texture
	 */
	public static Texture texturedApp(String name) {
		String filename = "models/"+name + ".jpg";
		TextureLoader loader = new TextureLoader(filename, null);
		ImageComponent2D image = loader.getImage();
		if (image == null)
		System.out.println("Cannot load file: " + filename);
		Texture2D texture = new Texture2D(Texture.BASE_LEVEL, 
		Texture.RGBA, image.getWidth(), image.getHeight());
		texture.setImage(0, image);
		return texture;
	}
	/**
	 * This method generates Orbits 
	 * @return a Shape3D
	 */
	private static Shape3D createOrbit(float radius) {
	    int numPoints = 100;
	    Point3f[] points = new Point3f[numPoints];
	    Color3f[] colors = new Color3f[numPoints];

	    for (int i = 0; i < numPoints; i++) {
	        double angle = 2 * Math.PI * i / numPoints;
	        float x = radius * (float) Math.cos(angle);
	        float z = radius * (float) Math.sin(angle);
	        points[i] = new Point3f(x, 0, z);
	        colors[i] = new Color3f(1, 1, 1); // Set the orbit color to white
	    }

	    LineStripArray orbitGeom = new LineStripArray(numPoints, GeometryArray.COORDINATES | GeometryArray.COLOR_3, new int[]{numPoints});
	    orbitGeom.setCoordinates(0, points);
	    orbitGeom.setColors(0, colors);

	    Shape3D orbit = new Shape3D(orbitGeom);
	    return orbit;
	}
	/**
	 * This method generates the sun 
	 * @return a branch group
	 */
	private static BranchGroup sun() {

		  BranchGroup objBG = new BranchGroup();
		  
		  DirectionalLight light = new DirectionalLight(CommonsMA.Orange, new Vector3f(-1f, 0f, -1f));
		  light.setInfluencingBounds(new BoundingSphere(new Point3d(), 1000d));
		  objBG.addChild(light);
		
		  
		
		Appearance sunApp = new Appearance();
		sunApp.setTexture(texturedApp("8k_sun"));
		
		ColoringAttributes ca = new ColoringAttributes(CommonsMA.Orange, ColoringAttributes.SHADE_GOURAUD);
	    sunApp.setColoringAttributes(ca);

	    Material sunMaterial = new Material();
	    sunMaterial.setEmissiveColor(new Color3f(1.0f, 0.5f, 0.0f));
	    sunApp.setMaterial(sunMaterial);
		
		Primitive[] shape = new Primitive[1];
	      shape[0] = new Sphere(1.5f,Sphere.GENERATE_NORMALS | Sphere.GENERATE_TEXTURE_COORDS,30,sunApp);

	        Vector3f[] pos = {
	                new Vector3f(0f, 0f, 0f),
	        };
	        Transform3D rotator = new Transform3D();
	        rotator.rotY(Math.PI/2);

	        Transform3D trans3d = new Transform3D();
	        TransformGroup trans = null;
	        TransformGroup bladeRotation = new TransformGroup();
	        bladeRotation.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	        //assigning sun positions and adding them to the BG
	        
	        trans3d.setTranslation(pos[0]);
	        
	        trans = new TransformGroup(trans3d);
	        trans.setUserData(0);
	        objBG.addChild(trans);
	        trans.addChild(shape[0]);
	        
	        Alpha bladeRotationAlpha = new Alpha(-1,500);
	        RotationInterpolator bladeRotationInterpolator = new RotationInterpolator(bladeRotationAlpha, bladeRotation, rotator, 0.0f, (float) Math.PI *2.0f);
	        bladeRotationInterpolator.setSchedulingBounds(new BoundingSphere(new Point3d(), 1000.0));
	        bladeRotation.addChild(bladeRotationInterpolator);
	        
	        PointLight sunLight = new PointLight();
	        sunLight.setColor(new Color3f(CommonsMA.Orange));
	        sunLight.setPosition(new Point3f(0.0f, 0.0f, 0.0f));
	        sunLight.setAttenuation(1.0f, 0.0f, 0.f); // Adjust attenuation factors (constant, linear, quadratic) for the desired lighting effect
	        sunLight.setInfluencingBounds(new BoundingSphere(new Point3d(), 1000d));
	        objBG.addChild(sunLight);
	        
	        
	        return objBG;
		}
	/**
	 * This method generates Mercury 
	 * @return a branch group
	 */
	private static BranchGroup mercury() {

		  BranchGroup objBG = new BranchGroup();
		  
		  DirectionalLight light = new DirectionalLight(CommonsMA.Orange, new Vector3f(-1f, 0f, -1f));
		  light.setInfluencingBounds(new BoundingSphere(new Point3d(), 1000d));
		  objBG.addChild(light);
		  
		  
		Appearance mercuryApp = new Appearance();
		mercuryApp.setTexture(texturedApp("mercury"));
		
		  
		Primitive[] shape = new Primitive[1];
	      shape[0] = new Sphere(0.1f,Sphere.GENERATE_NORMALS | Sphere.GENERATE_TEXTURE_COORDS,30,mercuryApp);

	        Transform3D rotator = new Transform3D();
	        rotator.rotY(Math.PI/2);

	        Transform3D trans3d = new Transform3D();
	        TransformGroup trans = null;
	        TransformGroup bladeRotation = new TransformGroup();
	        bladeRotation.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	        //assigning sun positions and adding them to the BG
	        trans3d.setTranslation(new Vector3f(3.5f, 0f, 0f));
	        trans = new TransformGroup(trans3d);
	        trans.setUserData(1);
	        objBG.addChild(trans);
	        trans.addChild(shape[0]);      		
	        		
	        
	        Alpha bladeRotationAlpha = new Alpha(-1,1000);
	        RotationInterpolator bladeRotationInterpolator = new RotationInterpolator(bladeRotationAlpha, bladeRotation, rotator, 0.0f, (float) Math.PI *2.0f);
	        bladeRotationInterpolator.setSchedulingBounds(new BoundingSphere(new Point3d(), 1000.0));
	        bladeRotation.addChild(bladeRotationInterpolator);

		  return objBG;
		}
	/**
	 * This method generates Venus 
	 * @return a branch group
	 */
	private static BranchGroup venus() {

		  BranchGroup objBG = new BranchGroup();
		  
		  DirectionalLight light = new DirectionalLight(CommonsMA.Orange, new Vector3f(-1f, 0f, -1f));
		  light.setInfluencingBounds(new BoundingSphere(new Point3d(), 1000d));
		  objBG.addChild(light);
		  
		  
		Appearance venusApp = new Appearance();
		venusApp.setTexture(texturedApp("venus"));
		
		  
		Primitive[] shape = new Primitive[1];
	      shape[0] = new Sphere(0.15f,Sphere.GENERATE_NORMALS | Sphere.GENERATE_TEXTURE_COORDS,30,venusApp);

	       
	        Transform3D rotator = new Transform3D();
	        rotator.rotY(Math.PI/2);

	        Transform3D trans3d = new Transform3D();
	        TransformGroup trans = null;
	        TransformGroup bladeRotation = new TransformGroup();
	        bladeRotation.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	        //assigning sun positions and adding them to the BG
	        
	        trans3d.setTranslation(new Vector3f(5f, 0f, 0f));
	        trans = new TransformGroup(trans3d);
	        trans.setUserData(2);
	        objBG.addChild(trans);
	        trans.addChild(shape[0]); 
	        		
	        
	        Alpha bladeRotationAlpha = new Alpha(-1,1000);
	        RotationInterpolator bladeRotationInterpolator = new RotationInterpolator(bladeRotationAlpha, bladeRotation, rotator, 0.0f, (float) Math.PI *2.0f);
	        bladeRotationInterpolator.setSchedulingBounds(new BoundingSphere(new Point3d(), 1000.0));
	        bladeRotation.addChild(bladeRotationInterpolator);

		  return objBG;
		}
	/**
	 * This method generates the earth 
	 * @return a branch group
	 */
	private static BranchGroup earth() {

		  BranchGroup objBG = new BranchGroup();
		  
		  DirectionalLight light = new DirectionalLight(CommonsMA.Orange, new Vector3f(-1f, 0f, -1f));
		  light.setInfluencingBounds(new BoundingSphere(new Point3d(), 1000d));
		  objBG.addChild(light);
		  
		  
		Appearance earthApp = new Appearance();
		earthApp.setTexture(texturedApp("earth"));
		
		  
		Primitive[] shape = new Primitive[1];
	      shape[0] = new Sphere(0.15f,Sphere.GENERATE_NORMALS | Sphere.GENERATE_TEXTURE_COORDS,30,earthApp);

	       
	        Transform3D rotator = new Transform3D();
	        rotator.rotY(Math.PI/2);

	        Transform3D trans3d = new Transform3D();
	        TransformGroup trans = null;
	        TransformGroup bladeRotation = new TransformGroup();
	        bladeRotation.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	        //assigning sun positions and adding them to the BG
	        	
	        trans3d.setTranslation(new Vector3f(6f, 0f, 0f));
	        trans = new TransformGroup(trans3d);
	        trans.setUserData(3);
	        objBG.addChild(trans);
	        trans.addChild(shape[0]);
	        
	        
	        Alpha bladeRotationAlpha = new Alpha(-1,1000);
	        RotationInterpolator bladeRotationInterpolator = new RotationInterpolator(bladeRotationAlpha, bladeRotation, rotator, 0.0f, (float) Math.PI *2.0f);
	        bladeRotationInterpolator.setSchedulingBounds(new BoundingSphere(new Point3d(), 1000.0));
	        bladeRotation.addChild(bladeRotationInterpolator);

		  return objBG;
		}
	/**
	 * This method generates Mars 
	 * @return a branch group
	 */
	private static BranchGroup mars() {

		  BranchGroup objBG = new BranchGroup();
		  
		  DirectionalLight light = new DirectionalLight(CommonsMA.Orange, new Vector3f(-1f, 0f, -1f));
		  light.setInfluencingBounds(new BoundingSphere(new Point3d(), 1000d));
		  objBG.addChild(light);
		  
		  
		Appearance marsApp = new Appearance();
		marsApp.setTexture(texturedApp("mars"));
		
		  
		Primitive[] shape = new Primitive[1];
	      shape[0] = new Sphere(0.175f,Sphere.GENERATE_NORMALS | Sphere.GENERATE_TEXTURE_COORDS,30,marsApp);

	       
	        Transform3D rotator = new Transform3D();
	        rotator.rotY(Math.PI/2);

	        Transform3D trans3d = new Transform3D();
	        TransformGroup trans = null;
	        TransformGroup bladeRotation = new TransformGroup();
	        bladeRotation.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	        //assigning sun positions and adding them to the BG
	        	
	        trans3d.setTranslation(new Vector3f(7f,0f,0.5f));
	        trans = new TransformGroup(trans3d);
	        trans.setUserData(4);
	        objBG.addChild(trans);
	        trans.addChild(shape[0]);      		
	        		
	        
	        Alpha bladeRotationAlpha = new Alpha(-1,800);
	        RotationInterpolator bladeRotationInterpolator = new RotationInterpolator(bladeRotationAlpha, bladeRotation, rotator, 0.0f, (float) Math.PI *2.0f);
	        bladeRotationInterpolator.setSchedulingBounds(new BoundingSphere(new Point3d(), 1000.0));
	        bladeRotation.addChild(bladeRotationInterpolator);

		  return objBG;
		}
	/**
	 * This method generates Jupiter 
	 * @return a branch group
	 */
	private static BranchGroup jupiter() {

		  BranchGroup objBG = new BranchGroup();
		  
		  DirectionalLight light = new DirectionalLight(CommonsMA.Orange, new Vector3f(-1f, 0f, -1f));
		  light.setInfluencingBounds(new BoundingSphere(new Point3d(), 1000d));
		  objBG.addChild(light);
		  
		  
		Appearance jupiterApp = new Appearance();
		jupiterApp.setTexture(texturedApp("jupiter"));
		
		  
		Primitive[] shape = new Primitive[1];
	      shape[0] = new Sphere(0.3f,Sphere.GENERATE_NORMALS | Sphere.GENERATE_TEXTURE_COORDS,30,jupiterApp);

	       
	        Transform3D rotator = new Transform3D();
	        rotator.rotY(Math.PI/2);

	        Transform3D trans3d = new Transform3D();
	        TransformGroup trans = null;
	        TransformGroup bladeRotation = new TransformGroup();
	        bladeRotation.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	        //assigning sun positions and adding them to the BG
	        	
	        trans3d.setTranslation(new Vector3f(8.5f,0f,-0.5f));
	        trans = new TransformGroup(trans3d);
	        trans.setUserData(5);
	        objBG.addChild(trans);
	        trans.addChild(shape[0]);      		
	        		
	        
	        Alpha bladeRotationAlpha = new Alpha(-1,800);
	        RotationInterpolator bladeRotationInterpolator = new RotationInterpolator(bladeRotationAlpha, bladeRotation, rotator, 0.0f, (float) Math.PI *2.0f);
	        bladeRotationInterpolator.setSchedulingBounds(new BoundingSphere(new Point3d(), 1000.0));
	        bladeRotation.addChild(bladeRotationInterpolator);

		  return objBG;
		}
	/**
	 * This method generates Saturn 
	 * @return a branch group
	 */
	private static BranchGroup saturnR() {
		
		int flags = ObjectFile.RESIZE | ObjectFile.TRIANGULATE | ObjectFile.STRIPIFY;
		ObjectFile f = new ObjectFile(flags, (float) (60 * Math.PI / 180.0));
		
		Scene s = null;
		try {
			s = f.load("models/satRings.obj");
		} catch (FileNotFoundException e) {
			System.err.println(e);
			System.exit(1);
		} catch (ParsingErrorException e){
			System.err.println(e);
			System.exit(1);
		} catch (IncorrectFormatException e){
			System.err.println(e);
			System.exit(1);
		}
		
		BranchGroup branchGroup = new BranchGroup();
		
		TransformGroup transformGroup = new TransformGroup();
		Transform3D transform3D = new Transform3D();
		transform3D.setScale(.6);
		transform3D.setTranslation(new Vector3f(10f,0f,0.5f));
		transformGroup.setTransform(transform3D);
		
		
		
		// Add the following code to set the color of the object to red
		Appearance satApp = new Appearance();
		satApp.setTexture(texturedApp("jupiter"));
		
		 Shape3D shape = (Shape3D) s.getSceneGroup().getChild(0);
		 shape.setAppearance(satApp);
		 
		transformGroup.addChild(s.getSceneGroup());
		transformGroup.setUserData(6);
		branchGroup.addChild(transformGroup);
		
		return branchGroup;
	}
	/**
	 * This method generates Uranus
	 * @return a branch group
	 */
	private static BranchGroup uranus() {

		  BranchGroup objBG = new BranchGroup();
		  
		  DirectionalLight light = new DirectionalLight(CommonsMA.Orange, new Vector3f(-1f, 0f, -1f));
		  light.setInfluencingBounds(new BoundingSphere(new Point3d(), 1000d));
		  objBG.addChild(light);
		  
		  
		Appearance uranusApp = new Appearance();
		uranusApp.setTexture(texturedApp("uranus"));
		
		  
		Primitive[] shape = new Primitive[1];
	      shape[0] = new Sphere(.2f,Sphere.GENERATE_NORMALS | Sphere.GENERATE_TEXTURE_COORDS,30,uranusApp);

	       
	        Transform3D rotator = new Transform3D();
	        rotator.rotY(Math.PI/2);

	        Transform3D trans3d = new Transform3D();
	        TransformGroup trans = null;
	        TransformGroup bladeRotation = new TransformGroup();
	        bladeRotation.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	        //assigning sun positions and adding them to the BG
	        	
	        trans3d.setTranslation(new Vector3f(11.5f,0f,0.5f));
	        trans = new TransformGroup(trans3d);
	        trans.setUserData(7);
	        objBG.addChild(trans);
	        trans.addChild(shape[0]);     		
	        		
	        
	        Alpha bladeRotationAlpha = new Alpha(-1,800);
	        RotationInterpolator bladeRotationInterpolator = new RotationInterpolator(bladeRotationAlpha, bladeRotation, rotator, 0.0f, (float) Math.PI *2.0f);
	        bladeRotationInterpolator.setSchedulingBounds(new BoundingSphere(new Point3d(), 1000.0));
	        bladeRotation.addChild(bladeRotationInterpolator);

		  return objBG;
		}
	/**
	 * This method generates Neptune 
	 * @return a branch group
	 */
	private static BranchGroup neptune() {

		  BranchGroup objBG = new BranchGroup();
		  
		  DirectionalLight light = new DirectionalLight(CommonsMA.Orange, new Vector3f(-1f, 0f, -1f));
		  light.setInfluencingBounds(new BoundingSphere(new Point3d(), 1000d));
		  objBG.addChild(light);
		  
		  
		Appearance neptuneApp = new Appearance();
		neptuneApp.setTexture(texturedApp("neptune"));
		
		  
		Primitive[] shape = new Primitive[1];
	      shape[0] = new Sphere(.199f,Sphere.GENERATE_NORMALS | Sphere.GENERATE_TEXTURE_COORDS,30,neptuneApp);

	       
	        Transform3D rotator = new Transform3D();
	        rotator.rotY(Math.PI/2);

	        Transform3D trans3d = new Transform3D();
	        TransformGroup trans = null;
	        TransformGroup bladeRotation = new TransformGroup();
	        bladeRotation.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	        //assigning sun positions and adding them to the BG
	        	
	        trans3d.setTranslation(new Vector3f(12.5f,0f,-0.5f));
	        trans = new TransformGroup(trans3d);
	        trans.setUserData(8);
	        objBG.addChild(trans);
	        trans.addChild(shape[0]);       		
	        		
	        
	        Alpha bladeRotationAlpha = new Alpha(-1,800);
	        RotationInterpolator bladeRotationInterpolator = new RotationInterpolator(bladeRotationAlpha, bladeRotation, rotator, 0.0f, (float) Math.PI *2.0f);
	        bladeRotationInterpolator.setSchedulingBounds(new BoundingSphere(new Point3d(), 1000.0));
	        bladeRotation.addChild(bladeRotationInterpolator);

		  return objBG;
		}
	/**
	 * This method generates Pluto 
	 * @return a branch group
	 */
	private static BranchGroup pluto() {

		  BranchGroup objBG = new BranchGroup();
		  
		  DirectionalLight light = new DirectionalLight(CommonsMA.Orange, new Vector3f(-1f, 0f, -1f));
		  light.setInfluencingBounds(new BoundingSphere(new Point3d(), 1000d));
		  objBG.addChild(light);
		  
		  
		Appearance plutoApp = new Appearance();
		plutoApp.setTexture(texturedApp("pluto"));
		
		  
		Primitive[] shape = new Primitive[1];
	      shape[0] = new Sphere(.09f,Sphere.GENERATE_NORMALS | Sphere.GENERATE_TEXTURE_COORDS,30,plutoApp);
	       
	        Transform3D rotator = new Transform3D();
	        rotator.rotY(Math.PI/2);

	        Transform3D trans3d = new Transform3D();
	        TransformGroup trans = null;
	        TransformGroup bladeRotation = new TransformGroup();
	        bladeRotation.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	        //assigning sun positions and adding them to the BG
	        	
	        trans3d.setTranslation(new Vector3f(13.5f,0f,-0.8f));
	        trans = new TransformGroup(trans3d);
	        trans.setUserData(9);
	        objBG.addChild(trans);
	        trans.addChild(shape[0]);      		
	        		
	        
	        Alpha bladeRotationAlpha = new Alpha(-1,800);
	        RotationInterpolator bladeRotationInterpolator = new RotationInterpolator(bladeRotationAlpha, bladeRotation, rotator, 0.0f, (float) Math.PI *2.0f);
	        bladeRotationInterpolator.setSchedulingBounds(new BoundingSphere(new Point3d(), 1000.0));
	        bladeRotation.addChild(bladeRotationInterpolator);

		  return objBG;
		}

	/**
	 * Main method
	 * @param args
	 */
	public static void main(String[] args) {
		frame = new JFrame("MA's"); // NOTE: change MA to student's initials
		frame.setSize(800,800);
		frame.getContentPane().add(new SolarSystem(create_Scene()));  // create an instance of the class
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	
	/**
	 * This method controls events influenced by keys
	 * @param KeyEvent e
	 */
	public void keyPressed(KeyEvent e) {
	    if(e.getKeyCode() == KeyEvent.VK_X) {
	        if(!isON) {
	            isON = true;
	            ps.setEnable(false);
	        }
	        else {
	            isON = false;
	            ps.setEnable(true);
	        }
	    }
	    int keyCode = e.getKeyCode();
	    switch(keyCode) {
	        case KeyEvent.VK_W:
	            moveCamera(new Vector3d(-0.1, 0, 0));
	            break;
	        case KeyEvent.VK_S:
	            moveCamera(new Vector3d(0.1, 0, 0));
	            break;
	        case KeyEvent.VK_A:
	            moveCamera(new Vector3d(0, 0, 0.1));
	            break;
	        case KeyEvent.VK_D:
	            moveCamera(new Vector3d(0, 0, -0.1));
	            break;
	        default:
	            break;
	    }
	}
	/**
	 * This method allows for navigation
	 * @param moveVector
	 */
	private void moveCamera(Vector3d moveVector) {
	    TransformGroup cameraTransform = su.getViewingPlatform().getViewPlatformTransform();
	    Transform3D currentTransform = new Transform3D();
	    cameraTransform.getTransform(currentTransform);
	    Vector3d currentPosition = new Vector3d();
	    currentTransform.get(currentPosition);
	    currentPosition.add(moveVector);
	    currentTransform.setTranslation(currentPosition);
	    cameraTransform.setTransform(currentTransform);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * This method finds an object in the simple universe
	 * @param node
	 * @return TransformGroup
	 */
	private TransformGroup findParentTransformGroup(Node node) {
	    Node parent = node.getParent();
	    while (parent != null) {
	        if (parent instanceof TransformGroup) {
	            return (TransformGroup) parent;
	        }
	        parent = parent.getParent();
	    }
	    return null;
	}
	/**
	 * This method controls events influenced by the mouse
	 * @param MouseEvent event
	 */
	public void mouseClicked(MouseEvent event) {

		int x = event.getX(); int y = event.getY();        // mouse coordinates
		Point3d point3d = new Point3d(), center = new Point3d();
		canvas.getPixelLocationInImagePlate(x, y, point3d);// obtain AWT pixel in ImagePlate coordinates
		canvas.getCenterEyeInImagePlate(center);           // obtain eye's position in IP coordinates
		
		Transform3D transform3D = new Transform3D();       // matrix to relate ImagePlate coordinates~
		canvas.getImagePlateToVworld(transform3D);         // to Virtual World coordinates
		transform3D.transform(point3d);                    // transform 'point3d' with 'transform3D'
		transform3D.transform(center);                     // transform 'center' with 'transform3D'

		Vector3d mouseVec = new Vector3d();
		mouseVec.sub(point3d, center);
		mouseVec.normalize();
		pickTool.setShapeRay(point3d, mouseVec);           // send a PickRay for intersection
		
		
		if (pickTool.pickClosest() != null) {
	        PickResult pickResult = pickTool.pickClosest(); // obtain the closest hit

	        Node pick_node = pickResult.getNode(PickResult.SHAPE3D);
	        TransformGroup parentTG = findParentTransformGroup(pick_node);

	        System.out.println("Picked node: " + pick_node + ", parent TG: " + parentTG);

	        if (parentTG != null && parentTG.getUserData() != null) {
	            System.out.println("Parent TG user data: " + parentTG.getUserData());
	            if ((int) parentTG.getUserData() == 0) {            
	                psSun.setEnable(true);
	                parentTG.setUserData(10);
	            } else if((int) parentTG.getUserData() == 10){
	                psSun.setEnable(false);
	                parentTG.setUserData(0);
	            }
	            else if((int) parentTG.getUserData() == 1){
	            	psMercury.setEnable(true);
	                parentTG.setUserData(11);
	            }
	            else if((int) parentTG.getUserData() == 11){
	            	psMercury.setEnable(false);
	                parentTG.setUserData(1);
	            }
	            else if((int) parentTG.getUserData() == 2){
	            	psVenus.setEnable(true);
	                parentTG.setUserData(12);
	            }
	            else if((int) parentTG.getUserData() == 12){
	            	psVenus.setEnable(false);
	                parentTG.setUserData(2);
	            }
	            else if((int) parentTG.getUserData() == 3){
	            	psEarth.setEnable(true);
	                parentTG.setUserData(13);
	            }
	            else if((int) parentTG.getUserData() == 13){
	            	psEarth.setEnable(false);
	                parentTG.setUserData(3);
	            }
	            else if((int) parentTG.getUserData() == 4){
	            	psMars.setEnable(true);
	                parentTG.setUserData(14);
	            }
	            else if((int) parentTG.getUserData() == 14){
	            	psMars.setEnable(false);
	                parentTG.setUserData(4);
	            }
	            else if((int) parentTG.getUserData() == 5){
	            	psJupiter.setEnable(true);
	                parentTG.setUserData(15);
	            }
	            else if((int) parentTG.getUserData() == 15){
	            	psJupiter.setEnable(false);
	                parentTG.setUserData(5);
	            }
	            else if((int) parentTG.getUserData() == 6){
	            	psSaturn.setEnable(true);
	                parentTG.setUserData(16);
	            }
	            else if((int) parentTG.getUserData() == 16){
	            	psSaturn.setEnable(false);
	                parentTG.setUserData(6);
	            }
	            else if((int) parentTG.getUserData() == 7){
	            	psUranus.setEnable(true);
	                parentTG.setUserData(17);
	            }
	            else if((int) parentTG.getUserData() == 17){
	            	psUranus.setEnable(false);
	                parentTG.setUserData(7);
	            }
	            else if((int) parentTG.getUserData() == 8){
	            	psNeptune.setEnable(true);
	                parentTG.setUserData(18);
	            }
	            else if((int) parentTG.getUserData() == 18){
	            	psNeptune.setEnable(false);
	                parentTG.setUserData(8);
	            }
	            else if((int) parentTG.getUserData() == 9){
	            	psPluto.setEnable(true);
	                parentTG.setUserData(19);
	            }
	            else {
	            	psPluto.setEnable(false);
	                parentTG.setUserData(9);
	            }
	        }
	    } 
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * This method initializes a sound
	 */
	public static void initialSound() {
        soundJOAL = new SoundUtilityJOAL();
        if (!soundJOAL.load("solar", 0f, 0f, 10f, true))
            System.out.println("Could not load " + "solar");
        if (!soundJOAL.load("AMU", 0f, 0f, 10f, true))
            System.out.println("Could not load " + "AMU");
    }
	/**
	 * This method allows for sounds to be played
	 * @param key
	 */
	public static void playSound(int key) {
        String snd_pt = "solar";
        if (key > 1)
            snd_pt = "solar";
        soundJOAL.play(snd_pt);
        try {
            Thread.sleep(500); // sleep for 0.5 secs
        } catch (InterruptedException ex) {}
        soundJOAL.stop(snd_pt);
    }
	/**
	 * A second constructor
	 * @param canvas3D
	 */
    public SolarSystem(Canvas3D canvas3D) {
        canvas = canvas3D; 
    }
}
