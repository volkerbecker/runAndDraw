package de.amazingsax.runanddraw;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.Math.round;

public class MainActivity extends AppCompatActivity implements StateChangeListener{

    private SensorManager mSensorManager;
    private Sensor linAccelaration;
    private ActualMobileState state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) != null) {
            linAccelaration = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        } else {
            // no linear accelerometer, app cant be used
            // maybe replace by normal accelerometer with low and high pass filter
        }
        state = new ActualMobileState();
        state.addStateChangeListener(this);
        //state.startMeasurement();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(linAccelaration!= null) {
            mSensorManager.registerListener(state, linAccelaration, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.toast_noaccelerometer)
                    , Toast.LENGTH_SHORT).show();
        }

        // todo block app;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(state);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //todo remove next line
            state.reset();
            state.startMeasurement();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //todo for test only, remove as soon as possible
    @Override
    public void stateChanged(ActualMobileStateChangedEvent event) {
        TextView myText;
        myText=findViewById(R.id.textdef);
        String ausgabe= "p:\t"+((float)round(event.position().getX()*100f))/100f + " \t " + (float)round(event.position().getY()*100f)/100f
                + " \t " + (float)round(event.position().getZ()*100f)/100f + "\n" +
                "v:\t"+(float)round(event.velocity().getX()*100f)/100f +  " \t " + (float)round(event.velocity().getY()*100f)/100f + " \t " +
                (float)round(event.velocity().getZ()*100f)/100f + "\n" +
                "a:\t"+(float)round(event.acceleration().getX()*100f)/100f + " \t " + (float)round(event.acceleration().getY()*100f)/100f + " \t " +
                (float)round(event.acceleration().getZ()*100f)/100f + "\n";
        myText.setText(ausgabe);
    }
}
