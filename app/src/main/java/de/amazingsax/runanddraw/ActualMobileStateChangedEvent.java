package de.amazingsax.runanddraw;

import java.util.EventObject;

/**
 * Created by becker on 12/1/17.
 */

public class ActualMobileStateChangedEvent extends EventObject {
    private ActualMobileState state;

    public ActualMobileStateChangedEvent(ActualMobileState source) {
        super(source);
        state=source;
    }

    public Vector3d velocity() {
        return state.getVelocity();
    }

    public Vector3d position() {
        return state.getPosition();
    }

    public Vector3d acceleration() {
        return state.getAcceleration();
    }
}
