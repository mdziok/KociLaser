package com.example.cephea.kocilaserfragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.LinkedList;
import java.util.List;

public class DrawingView extends View {

    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Path mPath;
    private Paint mBitmapPaint;
    Context context;
    private Paint circlePaint;
    private Path circlePath;
    private Paint mPaint;

    private FloatingPoint lastPoint;
    private FloatingPoint point0;

    private List<FloatingPoint> pointList;


    private float stepX, stepY;

    private List<Float> possibleX;
    private List<Float> possibleY;

    public void initialize(){
        mPath = new Path();
        mBitmapPaint = new Paint(Paint.DITHER_FLAG);
        circlePaint = new Paint();
        circlePath = new Path();
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(Color.BLUE);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeJoin(Paint.Join.MITER);
        circlePaint.setStrokeWidth(4f);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(12);

        possibleX = new LinkedList<>();
        possibleY = new LinkedList<>();
    }

    public DrawingView(Context c) {
        super(c);
        context=c;
        initialize();
    }

    public DrawingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context=context;
        initialize();
    }

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs); // This should be first line of constructor
        this.context = context;
        initialize();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);

        pointList = new LinkedList<>();

        clearPointList();

        stepX = (float) getWidth()/40;
        stepY = (float) getHeight()/40;

        setPoint0(new FloatingPoint(stepX*20, stepY*20));

        mCanvas.drawPoint(getPoint0().getX(), getPoint0().getY(), circlePaint);

        for (int i = 0; i<40; i++){
            possibleX.add(i*stepX);
            possibleY.add(i*stepY);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap( mBitmap, 0, 0, mBitmapPaint);
        canvas.drawPath( mPath,  mPaint);
        canvas.drawPath( circlePath,  circlePaint);
    }

    private void touch_start(float x, float y) {
        mPath.reset();

        mCanvas.drawColor(Color.WHITE);

        float approxX=0, approxY=0;
        float deltaX = getWidth();
        float deltaY = getHeight();

        for (float pX : possibleX){
            if (Math.abs(pX-x) < deltaX){
                deltaX = Math.abs(pX - x);
                approxX = pX;
            }
        }

        for (float pY : possibleY){
            if (Math.abs(pY-y) < deltaY){
                deltaY = Math.abs(pY - y);
                approxY = pY;
            }
        }

        clearPointList();
        lastPoint = new FloatingPoint(approxX, approxY);
        addPoint(lastPoint);

        mPath.moveTo(approxX, approxY);
    }

    private void lineTo(float x, float y){
        lastPoint = new FloatingPoint(x, y);
        addPoint(lastPoint);
        mPath.lineTo(x, y);
    }

    private void touch_move(float x, float y) {
        if(Math.abs(x - lastPoint.getX()) > stepX/2){
            if (x > lastPoint.getX()){
                lineTo(lastPoint.getX() + stepX, lastPoint.getY());
            } else {
                lineTo(lastPoint.getX() - stepX, lastPoint.getY());
            }
        }
        else if(Math.abs(y-lastPoint.getY()) > stepY/2){
            if (y > lastPoint.getY()) {
                lineTo(lastPoint.getX(), lastPoint.getY() + stepY);
            }
            else {
                lineTo(lastPoint.getX(), lastPoint.getY() - stepY);
            }
        }
    }

    private void touch_up() {
        printPointList();
        mCanvas.drawPath(mPath,  mPaint);
        mPath.reset();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touch_start(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touch_move(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touch_up();
                invalidate();
                break;
        }
        return true;
    }


    public List<FloatingPoint> getPointList(){
        return pointList;
    }

    public void addPoint(FloatingPoint point){
        pointList.add(point);
    }

    public void clearPointList(){
        pointList.clear();
    }

    public void setPoint0(FloatingPoint p){
        point0 = p;
    }

    public FloatingPoint getPoint0(){
        return point0;
    }

    public FloatingPoint getShift(){
        float x,y;
        x = (point0.getX() - pointList.get(0).getX())/stepX;
        y = (point0.getY() - pointList.get(0).getY())/stepY;
        return new FloatingPoint(x,y);
    }

    public void printPointList(){
        String s = "";
        for (FloatingPoint p : pointList){
            s += p;
        }
    }


}

