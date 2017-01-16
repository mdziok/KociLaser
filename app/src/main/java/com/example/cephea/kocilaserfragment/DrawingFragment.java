package com.example.cephea.kocilaserfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

public class DrawingFragment extends Fragment{
    DrawingView dv ;
    private List<FloatingPoint> pointList;
    FloatingPoint point0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.drawing_fragment, container, false);
        dv = (DrawingView) rootView.findViewById(R.id.drawingView);
        Button drawB = (Button) rootView.findViewById(R.id.drawB);
        drawB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPath();
            }
        });

        return rootView;
    }

    @Override
    public void onResume(){
        super.onResume();
        sendData("c");
    }

    public void sendPath(){

        pointList = dv.getPointList();
        point0 = dv.getPoint0();

        String s = "cs";

        s += findShift();
        s += "S";
        s += findPath();
        s += "h";
        sendData(s);
    }

    public String findShift(){
        String s="", dir;
        FloatingPoint shift;
        shift = dv.getShift();

        if (shift.getX() > 0){
            dir = "l";
        } else {
            dir = "r";
        }
        for (int i = 0; i<Math.abs(shift.getX()); i++){
            s += dir;
        }

        if (shift.getY() > 0){
            dir = "u";
        } else {
            dir = "d";
        }
        for (int i = 0; i<Math.abs(shift.getY()); i++){
            s += dir;
        }
        return s;
    }

    public String findPath(){
        FloatingPoint cur, next;
        String s="";

        for (int i = 0; i<pointList.size()-1; i++){
            cur = pointList.get(i);
            next = pointList.get(i+1);
            if (cur.getX() == next.getX()){
                if (cur.getY() > next.getY()){
                    s += "u";
                }
                else {
                    s += "d";
                }
            }
            else {
                if (cur.getX() > next.getX()){
                    s += "l";
                }
                else {
                    s += "r";
                }
            }
        }
        return s;
    }

    public void sendData(String message){
        ((MainActivity)getActivity()).sendData(message);
    }
}
