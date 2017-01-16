package com.example.cephea.kocilaserfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class ButtonFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.button_fragment, container, false);
        Button upB = (Button) rootView.findViewById(R.id.up);
        Button downB = (Button) rootView.findViewById(R.id.down);
        Button rightB = (Button) rootView.findViewById(R.id.right);
        Button leftB = (Button) rootView.findViewById(R.id.left);

        leftB.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        sendData("l");
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        sendData("h");
                    }
                return false;
            }
        });
        rightB.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        sendData("r");
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        sendData("h");
                    }
                return false;
            }
        });
        upB.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        sendData("u");
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        sendData("h");
                    }
                return false;
            }
        });
        downB.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        sendData("d");
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        sendData("h");
                    }
                return false;
            }
        });
        return rootView;
    }


    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void sendData(String message) {
        ((MainActivity)getActivity()).sendData(message);
    }
}
