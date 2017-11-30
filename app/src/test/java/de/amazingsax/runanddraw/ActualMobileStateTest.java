package de.amazingsax.runanddraw;

/**
 * Created by becker on 11/30/17.
 * this class contains the state of the system
 */

import org.junit.Test;
import static org.junit.Assert.*;


public class ActualMobileStateTest {
    private final float delta=0.0001f;
    @Test
    public void initialstates_test() {
        Vector3d initial=new Vector3d(1,2,3);

        ActualMobileState state=new ActualMobileState();
        assertEquals(0,state.getPosition().getX(),delta);
        assertEquals(0,state.getPosition().getY(),delta);
        assertEquals(0,state.getPosition().getZ(),delta);
        state=new ActualMobileState(initial,initial,initial);
        assertEquals(1,state.getPosition().getX(),delta);
        assertEquals(2,state.getPosition().getY(),delta);
        assertEquals(3,state.getPosition().getZ(),delta);
    }
    // Test function for accelaration
    private Vector3d accelaration(float t) {
        return new Vector3d(1.0f-t,1.0f+t,1.0f);
    }
    // corresponding velocity (v0=0)
    private Vector3d velocity(float t) {
        return new Vector3d(1.0f*t-t*t*0.5f,1.0f*t+0.5f*t*t,1.0f*t);
    }

    // corresponding position (v0=0)
    private Vector3d position(float t) {
        return new Vector3d(0.5f*t*t-t*t*t/6.0f,0.5f*t*t+t*t*t/6.0f,0.5f*t*t);
    }


    @Test
    public void integrationstep_test() throws Exception {

        ActualMobileState state= new ActualMobileState(position(0),velocity(0),accelaration(0));
        final float dt=0.01f;
        final float endTime=3.0f;
        final float tolerance=0.001f;
        for(float t=dt;t<endTime;t+=dt) {
            Vector3d vel=velocity(t);
            Vector3d pos=position(t);
            state.integrationStep(accelaration(t),dt);
            assertEquals("Testvelocity", true, state.getVelocity().equals(vel, tolerance));
            assertEquals("Testpostion", true, state.getPosition().equals(pos, tolerance));
        }
    }

}
