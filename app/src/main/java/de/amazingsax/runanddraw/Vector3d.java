package de.amazingsax.runanddraw;

/**
 * Created by becker on 11/29/17.
 * a minimal vector class
 */

class Vector3d {
    // Members
    private float x;
    private float y;
    private float z;

    // Contructors
    public Vector3d() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public Vector3d(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3d(
            Vector3d v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }
    // getter amnd setter


    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    // Methods
    // add vector to vector
    public Vector3d add(Vector3d v) {
        this.x += v.x;
        this.y += v.y;
        this.z += v.z;
        return this;
    }

    // add scalar to vector
    public Vector3d add(float s) {
        Vector3d v = new Vector3d(s, s, s);
        this.add(v);
        return this;
    }

    // mulitply a scalar to the vector
    public Vector3d mul(float s) {
        this.x *= s;
        this.y *= s;
        this.z *= s;
        return this;
    }

    public void copy(Vector3d v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }

    public boolean equals(Vector3d v,float delta) {
        float squaredModulus=(this.x-v.x)*(this.x-v.x);
        squaredModulus+=(this.y-v.y)*(this.y-v.y);
        squaredModulus+=(this.z-v.z)*(this.z-v.z);
        return squaredModulus < delta*delta;
    }

}
