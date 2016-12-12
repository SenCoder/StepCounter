package com.tcl.stepcounter;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.util.LogUtils;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;

    private SensorManager mSensorManager;
    private Sensor mStepCounter;
    private Sensor mStepDetector;

    private SensorEventListener mSensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if (sensorEvent.sensor == mStepCounter) {
                mTextView.setText(getString(R.string.step_number) + " : " + sensorEvent.values[0]);
            } else if (sensorEvent.sensor == mStepDetector) {
                // one more step when the foot hit the ground.
                Toast.makeText(MainActivity.this, "one more step", Toast.LENGTH_SHORT).show();
            } else {
                LogUtils.d("bla bla");
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.tv_step_count);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mStepCounter = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        mStepDetector = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerSensorListener();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterSensorListener();
    }

    public void registerSensorListener() {
        mSensorManager.registerListener(mSensorEventListener, mStepCounter, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(mSensorEventListener, mStepDetector, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void unregisterSensorListener() {
        mSensorManager.unregisterListener(mSensorEventListener);
    }

}
