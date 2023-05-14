package com.example.temperatureboussol;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class SendToTwo extends View implements SensorEventListener {

    private Context ctx;



    //thermo
    private Bitmap thermoBit;
    private int thermoBit_PosX;
    private int thermoBit_PosY;

    //Icon
    private Bitmap iconBit;


    //line
    private Bitmap line;
    private Paint myPaint;
    private float line_posX_start;
    private float line_posY_start;
    private float line_posX_end;
    private float line_posY_end;


    int screenW;
    int screenH;

    //Sensor
    private SensorManager mySensorManager;
    public static Sensor mySensor;


    //Compass=====================
    Bitmap myCompass;
    Matrix mtx;

    private Sensor sensorAccelerometer;
    private Sensor sensorMagneticField;

    //===========================



    public SendToTwo(Context _context) {
        super(_context);

        ctx=_context;

        //Sensor ======
        mySensorManager = MainTwo.mySensorManager;
        //======


        if(GlobalVar.btnTempClicked==true){

            //Sensor ======
            mySensor = MainTwo.mySensor;
            mySensorManager.registerListener(this,mySensor,SensorManager.SENSOR_DELAY_GAME);
            //======

            //ThermoBit======
            thermoBit = BitmapFactory.decodeResource(getResources(),R.drawable.thermo);
            //======

            //Icon====
            iconBit = BitmapFactory.decodeResource(getResources(),R.drawable.clear);
            //=====

            //line=========
            myPaint = new Paint();
            myPaint.setAntiAlias(true);
            myPaint.setColor(Color.RED);
            myPaint.setStrokeWidth(50);

            line_posX_start = (float) 710;
            line_posX_end = (float) 710;

            line_posY_start =(float) 1757;
            line_posY_end = 1760;
            //=========
        }

        if(GlobalVar.btnBoussolClicked==true){

            sensorAccelerometer = MainTwo.mySensorAccelerometre;
            sensorMagneticField = MainTwo.mySensorMagnetude;

            //Sensor ======
            mySensorManager.registerListener(this,sensorAccelerometer,SensorManager.SENSOR_DELAY_GAME);
            mySensorManager.registerListener(this,sensorMagneticField,SensorManager.SENSOR_DELAY_GAME);
            //======

            myPaint = new Paint();
            myPaint.setColor(Color.BLACK);
            myPaint.setStrokeWidth(2);
            myPaint.setStyle(Paint.Style.STROKE);

            //Logo======
            myCompass = BitmapFactory.decodeResource(getResources(),R.drawable.comps);
            //======
        }



    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        screenW = w;
        screenH = h;

        //spaceShip=====================================
        thermoBit_PosX=(int)(screenW*0.37);
        thermoBit_PosY=(int)(screenH*0.65);
        //==============================================
    }

    @Override
    protected void onDraw(Canvas _canvas) {
//        super.onDraw(canvas);

        if(GlobalVar.btnTempClicked==true){

            //thermoBit===================================
            Bitmap ScaledBitmapThermo = Bitmap.createScaledBitmap(thermoBit, _canvas.getWidth(),_canvas.getHeight(), true);
            _canvas.drawBitmap(ScaledBitmapThermo,0,0,null);

            //Icon===================================
            Bitmap ScaledBitmapIcon = Bitmap.createScaledBitmap(iconBit, 300,300, true);
            _canvas.drawBitmap(ScaledBitmapIcon,50,50,null);


            //Line===========
            _canvas.drawLine(line_posX_start,line_posY_start,line_posX_end,line_posY_end,myPaint);
        }


        if(GlobalVar.btnBoussolClicked==true){
//            Bitmap newBitmap = Bitmap.createBitmap(ScaledBitmapCompass,0,0,_canvas.getWidth(),_canvas.getHeight()-1000,mtx,true);
//            Bitmap ScaledBitmapThermo = Bitmap.createScaledBitmap(myCompass, _canvas.getWidth(),_canvas.getHeight(), true);
            _canvas.drawBitmap(myCompass,mtx,null);
            mtx.reset();

        }



    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        if(GlobalVar.btnTempClicked==true){

            
            if(event.values[0]>-40 && event.values[0]<41){


                float tempActuel = event.values[0];
                float counter =0;
                counter= (float)((tempActuel+43)*17.5);
                line_posY_end =  line_posY_start-counter;



                if (event.values[0]<-30){
                    iconBit = BitmapFactory.decodeResource(getResources(),R.drawable.vcold);
                    myPaint.setColor(Color.parseColor("#00e5ff"));
                }
                else if (event.values[0]>=-30 && event.values[0]<-20){
                    iconBit = BitmapFactory.decodeResource(getResources(),R.drawable.snowman);
                    myPaint.setColor(Color.parseColor("#00beef"));
                }
                else if (event.values[0]>=-20 && event.values[0]<-10){
                    iconBit = BitmapFactory.decodeResource(getResources(),R.drawable.rainy);
                    myPaint.setColor(Color.parseColor("#5fd6ef"));
                }
                else if (event.values[0]>=-10 && event.values[0]<0){
                    iconBit = BitmapFactory.decodeResource(getResources(),R.drawable.cloud);
                    myPaint.setColor(Color.parseColor("#beeeef"));
                }
                else if(event.values[0]>=0 && event.values[0]<10){
                    iconBit = BitmapFactory.decodeResource(getResources(),R.drawable.clear);
                    myPaint.setColor(Color.parseColor("#beef78"));

                }
                else if (event.values[0]>=10 && event.values[0]<20){
                    iconBit = BitmapFactory.decodeResource(getResources(),R.drawable.sunny);
                    myPaint.setColor(Color.parseColor("#ff4f4f"));

                }
                else if (event.values[0]>=20 && event.values[0]<30){
                    iconBit = BitmapFactory.decodeResource(getResources(),R.drawable.hot);
                    myPaint.setColor(Color.parseColor("#e14343"));
                }
                else if (event.values[0]>=30){
                    iconBit = BitmapFactory.decodeResource(getResources(),R.drawable.hell);
                    myPaint.setColor(Color.parseColor("#ae2626"));
                }

            }
        }

        if(GlobalVar.btnBoussolClicked==true){

            if(event.sensor==sensorMagneticField){


                float degree = (float) ((90-Math.atan2(event.values[0],event.values[1])*180/Math.PI));
                if (degree > 90)
                {
                    degree = 450 - degree;
                }
                else
                {
                    degree = 90 - degree;
                }
                mtx = new Matrix();
                mtx.setRotate(degree,myCompass.getWidth()/2,myCompass.getHeight()/2);
                mtx.postTranslate((float) (screenW*0.01),(float) (screenH*0.18));
            }
        }


        invalidate();
    }










    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
