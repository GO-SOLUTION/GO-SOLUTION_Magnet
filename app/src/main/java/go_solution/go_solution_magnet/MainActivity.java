package go_solution.go_solution_magnet;

import android.content.Context;
import android.hardware.GeomagneticField;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.renderscript.Sampler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    ProgressBar bar;
    SensorManager manager;
    Sensor detektor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bar = (ProgressBar) findViewById(R.id.Bar);
        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        detektor = manager.getSensorList(Sensor.TYPE_MAGNETIC_FIELD).get(0);
        bar.setMax((int)detektor.getMaximumRange());

    }

    protected void onResume() {
        super.onResume();
        manager.registerListener(this, detektor, manager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        manager.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == detektor) {
            float[] mag = event.values;
            double result = Math.sqrt(mag[0] * mag[0] + mag[1] * mag[1] + mag[2] * mag[2]);
            bar.setProgress((int)result);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        bar.setProgress(accuracy);
    }

}
