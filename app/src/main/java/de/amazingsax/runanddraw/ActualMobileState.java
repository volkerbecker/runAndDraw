package de.amazingsax.runanddraw;

import android.hardware.SensorEventListener;
import android.hardware.Sensor;
import android.hardware.SensorEvent;

import java.util.ArrayList;



/**
 * Created by becker on 11/29/17.
 */

class ActualMobileState implements SensorEventListener {

    //Listener Liste
    private ArrayList <StateChangeListener> stateChangeListeners = new ArrayList<StateChangeListener>();

    // Attribute - Zustand des Systems
    private Vector3d velocity;
    private Vector3d position;
    private Vector3d acceleration;

    private long oldtime;
    private boolean active = false;

    // Konstruktor setzt Zustand auf 0
    ActualMobileState() {
        this.velocity = new Vector3d(0, 0, 0);
        this.position = new Vector3d(0, 0, 0);
        this.acceleration = new Vector3d(0, 0, 0);
    }

    // initialisierung mit Anfangswerten
    public ActualMobileState(Vector3d velocity, Vector3d position, Vector3d acceleration) {
        this.velocity = velocity;
        this.position = position;
        this.acceleration = acceleration;
    }

    // getter
    public Vector3d getVelocity() {
        return velocity;
    }

    public Vector3d getPosition() {
        return position;
    }

    public Vector3d getAcceleration() {
        return acceleration;
    }

    //register listener
    public synchronized void addStateChangeListener(StateChangeListener stateChangeListener) {
        stateChangeListeners.add(stateChangeListener);
    }

    public synchronized void removeChangeListener(StateChangeListener stateChangeListener) {
        stateChangeListeners.remove(stateChangeListener);
    }

    private synchronized void emitStateChangedEvent() {
        for(StateChangeListener listener : stateChangeListeners) {
            listener.stateChanged(new ActualMobileStateChangedEvent(this));
        }
    }





    //
    /*
    Methoden
    Integriert nach erhalt von neuem beschleunigungswert die Geschwindigkeit und Position
    */
    public void integrationStep(Vector3d newAcceleration, float dt) {
        // we use the velocity verlet integration sheme
        // todo: maybe a other sheme is more suitable
        Vector3d newVelocity = new Vector3d(velocity);

//        //calculate new position
//        Vector3d increment = new Vector3d(velocity);
//        increment.mul(dt); // add v*dt
//        this.position.add(increment); // to position
//        increment.copy(acceleration);
//        increment.mul(0.5f * dt * dt); // add a/t^2 to position
//        this.position.add(increment);
//        // calculate new velocity
//        increment.copy(acceleration);
//        increment.add(newAcceleration);
//        increment.mul(0.5f * dt); // calculate (a(t)+a(t+dt))/2*dt
//        this.velocity.add(increment);  // v(t+dt)=v(t) + (a(t)+a(t+dt))/2*dt
//        acceleration = newAcceleration;
        Vector3d increment = new Vector3d(velocity);
        increment.mul(dt);
        position.add(increment);
        acceleration.copy(newAcceleration);
        newAcceleration.mul(dt);
        velocity.add(newAcceleration);
    }

    public void reset() {
        active=false;
        position.setX(0);
        position.setY(0);
        position.setZ(0);
        velocity.copy(position);
        position.copy(position);
    }

    public void startMeasurement() {
        active=true;
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not needed in this application
    }

    //
    @Override
    public final void onSensorChanged(SensorEvent event) {
        // Read the current linear acceleration
        Vector3d currentacceleration = new Vector3d(event.values[0],
                event.values[1], event.values[2]);
        //currentacceleration.round(5);
        long time = event.timestamp;
        float dt = (float) (time - oldtime) / 1e9f;
        oldtime = time;
        if (active) {
            integrationStep(currentacceleration, dt);
            emitStateChangedEvent();
        } else  {
            acceleration.copy(currentacceleration);
            emitStateChangedEvent();
        }


    }


}
