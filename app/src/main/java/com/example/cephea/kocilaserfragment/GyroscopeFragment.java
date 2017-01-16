package com.example.cephea.kocilaserfragment;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class GyroscopeFragment extends Fragment implements SensorEventListener{
    private Sensor mSensor;
    private SensorManager mSensorManager;

    private String TAG = this.getClass().getName();

    private boolean isGyroscope = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.gyroscope_fragment, container, false);
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED);
    }

    @Override
    public void onResume(){
        super.onResume();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onStop(){
        mSensorManager.unregisterListener(this);
        super.onStop();
    }

    private void sendData(String message) {
        ((MainActivity)getActivity()).sendData(message);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        final float alpha = 2;
        if (isGyroscope) {
            if (event.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE) {
                return;
            }

            if (event.values[0] > alpha) {
                String s = "u";
                float v = event.values[0] - 2;
                while (v > 0) {
                    s += "u";
                    v -= 1;
                }
                s += "h";
                sendData(s);
            } else if (event.values[0] < -alpha) {
                String s = "d";
                float v = event.values[0] + 2;
                while (v < 0) {
                    s += "d";
                    v += 1;
                }
                s += "h";
                sendData(s);
            }
            if (event.values[2] > alpha) {
                String s = "l";
                float v = event.values[2] - 2;
                while (v > 0) {
                    s += "l";
                    v -= 1;
                }
                s += "h";
                sendData(s);
            } else if (event.values[2] < -alpha) {
                String s = "r";
                float v = event.values[0] + 2;
                while (v < 0) {
                    s += "r";
                    v += 1;
                }
                s += "h";
                sendData(s);
            }

            Log.d(TAG, Float.toString(event.values[0] * 100) + "   " +
                    Float.toString(event.values[1] * 100) + "   " +
                    Float.toString(event.values[2] * 100));
            }
        }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (this.isVisible()) {
            isGyroscope = true;
            if (!isVisibleToUser) {
                isGyroscope = false;
            }
        }
    }
}
