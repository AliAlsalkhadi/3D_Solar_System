package Codes;



import java.awt.Font;

import org.jogamp.java3d.Appearance;
import org.jogamp.java3d.Font3D;
import org.jogamp.java3d.FontExtrusion;
import org.jogamp.java3d.LineArray;
import org.jogamp.java3d.Material;
import org.jogamp.java3d.Node;
import org.jogamp.java3d.Shape3D;
import org.jogamp.java3d.Text3D;
import org.jogamp.java3d.Transform3D;
import org.jogamp.java3d.TransformGroup;
import org.jogamp.vecmath.Point3f;
/**
 * This class creates 3D shapes to be used in the main class
 * @author Mahmoud Alkwisem
 *
 */
public abstract class SolarSysShapes {
	protected abstract Node create_Object();	           // use 'Node' for both Group and Shape3D
	public abstract Node position_Object();
}
/**
 * This class creates a string
 * @author Mahmoud Alkwisem
 *
 */
class StringShape extends SolarSysShapes {
    private TransformGroup objTG;                              // use 'objTG' to position an object
    private String str;
    /**
     * class constructor
     * @param str_ltrs
     */
    public StringShape(String str_ltrs) {
        str = str_ltrs;
        Transform3D scaler = new Transform3D();
        scaler.setScale(0.1);                              // scaling 4x4 matrix 
        objTG = new TransformGroup(scaler);
        objTG.addChild(create_Object());           // apply scaling to change the string's size
    }
    /**
     * creates an object
     * @return a 3D shape
     */
    protected Node create_Object() {
        Font my2DFont = new Font("Arial", Font.PLAIN, 1);  // font's name, style, size
        FontExtrusion myExtrude = new FontExtrusion();
        Font3D font3D = new Font3D(my2DFont, myExtrude);

        Point3f pos = new Point3f(-str.length()/2.4f, 6.2f, 1.2f);// position for the string 
        Text3D text3D = new Text3D(font3D, str, pos);      // create a text3D object
        Material m = new Material(CommonsMA.White,CommonsMA.White, CommonsMA.White,CommonsMA.White, 100.0f);
        Appearance a = new Appearance();
        a.setMaterial(m);

        Shape3D wack = new Shape3D();
        wack.setGeometry(text3D);
        wack.setAppearance(a);
        return wack;
    }
    /**
     * @return the created object
     */
    public Node position_Object() {
        return objTG;
    }
}
/**
 *This class create a 3D axis 
 * @author Mahmoud Alkwisem
 *
 */
class _3dAxis extends SolarSysShapes{

    @Override
    /**
     * This method creates the object
     * @return a 3D shape
     */
    protected Node create_Object() {
        //float r = 0.6f, x, y;                              // vertices at 0.6 away from origin
        Point3f coor[] = new Point3f[4];                   // declare 5 points for star shape
        LineArray lineArr = new LineArray(8, LineArray.COLOR_3 | LineArray.COORDINATES);
        coor[0] = new Point3f(0,0,0);
        coor[1] = new Point3f(.8f,0,0);
        coor[2] = new Point3f(0,.8f,0);
        coor[3] = new Point3f(0,0,0.8f);

        for (int i = 0; i <= 3; i++) {
            lineArr.setCoordinate(i * 2, coor[i]);         // define point pairs for each line
            lineArr.setCoordinate(i * 2 , coor[(i+1) %4]);
            lineArr.setColor(i * 2, CommonsMA.Cyan);        // specify color for each pair of points
            lineArr.setColor(i * 2 + 1, CommonsMA.White);
        }
        return new Shape3D(lineArr);                        // create and return a Shape3D
    }
    /**
     * @return the object
     */
    public Node position_Object() {
        // TODO Auto-generated method stub
        return create_Object();
    }

}
