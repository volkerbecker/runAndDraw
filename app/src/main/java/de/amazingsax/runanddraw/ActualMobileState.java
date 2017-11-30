package de.amazingsax.runanddraw;

import java.util.Vector;

/**
 * Created by becker on 11/29/17.
 */

class ActualMobileState {

    // Attribute - Zustand des Systems
    private Vector3d velocity;
    private Vector3d position;
    private Vector3d acceleration;

    // Konstruktor setzt Zustand auf 0
    ActualMobileState() {
        this.velocity= new Vector3d(0,0,0);
        this.position=new Vector3d(0,0,0);
        this.acceleration=new Vector3d(0,0,0);
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
    /*
    Methoden
    Integriert nach erhalt von neuem beschleunigungswert die Geschwindigkeit und Position
    */
    public void integrationStep(Vector3d newAcceleration,float dt) {
        // we use the velocity verlet integration sheme
        // todo: maybe a other sheme is more suitable
        Vector3d newVelocity=new Vector3d(velocity);

        //calculate new position
        Vector3d increment=new Vector3d(velocity);
        increment.mul(dt); // add v*dt
        this.position.add(increment); // to position
        increment.copy(acceleration);
        increment.mul(0.5f*dt*dt); // add a/t^2 to position
        this.position.add(increment);
        // calculate new velocity
        increment.copy(acceleration);
        increment.add(newAcceleration);
        increment.mul(0.5f*dt); // calculate (a(t)+a(t+dt))/2*dt
        this.velocity.add(increment);  // v(t+dt)=v(t) + (a(t)+a(t+dt))/2*dt
        acceleration=newAcceleration;
    }
}
