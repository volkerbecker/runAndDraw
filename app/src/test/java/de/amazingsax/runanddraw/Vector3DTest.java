package de.amazingsax.runanddraw;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class Vector3DTest {
    private final float delta =0.000001f;
    @Test
    public void vectorAddition_isCorrect() throws Exception {
        Vector3d v1=new Vector3d(1.1f,2.2f,3.3f);
        Vector3d v2=new Vector3d(4.1f,5.2f,6.2f);
        Vector3d v3;

        v3=v1.add(v2);
        assertEquals(5.2f, v1.getX(),delta);
        assertEquals(7.4f, v1.getY(),delta);
        assertEquals(9.5f, v1.getZ(),delta);

        assertEquals(5.2f, v3.getX(),delta);
        assertEquals(7.4f, v3.getY(),delta);
        assertEquals(9.5f, v3.getZ(),delta);
    }

    @Test
    public void scalarAddition_isCorrect() throws Exception {
        Vector3d v1=new Vector3d(1.1f,2.2f,3.3f);
        float s=3.7f;
        v1.add(s);
        assertEquals(4.8f,v1.getX(),delta);
        assertEquals(5.9f,v1.getY(),delta);
        assertEquals(7.0f,v1.getZ(),delta);
    }
    //skalare Multiplikation testen
    @Test
    public void testMul() throws Exception {
        Vector3d v1=new Vector3d( 1.0f, 2.0f, 3.0f );
        v1.mul(2);
        assertEquals(2.0f,v1.getX(),delta);
        assertEquals(4.0f,v1.getY(),delta);
        assertEquals(6.0f,v1.getZ(),delta);
    }
}