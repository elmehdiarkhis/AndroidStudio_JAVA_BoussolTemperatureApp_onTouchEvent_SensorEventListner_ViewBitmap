package com.example.temperatureboussol;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

public class SendToOne extends View {

    private Context ctx;

    //===Logo===
    private Bitmap logo;
    private Bitmap Scaledlogo;


    //===btnTemp===
    private Bitmap btnTemp_Up;
    private Bitmap btnTemp_Down;
    //===
    private int posX_btnTemp_Up;
    private int posX_btnTemp_Down;
    private int posY_btnTemp;
    //===
    private boolean btnTemp_State = false;

    //===btnBoussol===
    private Bitmap btnBoussol_Up;
    private Bitmap btnBoussol_Down;
    //===
    private int posX_btnBoussol_Up;
    private int posX_btnBoussol_Down;
    private int posY_btnBoussol;
    //===
    private boolean btnBoussol_State = false;

    //===
    private int screenW;
    private int screenH;



    public SendToOne(Context _context) {
        super(_context);

        ctx = _context;

        //Logo
        logo = BitmapFactory.decodeResource(getResources(),R.drawable.logo);

        //btnTemp
        btnTemp_Up = BitmapFactory.decodeResource(getResources(),R.drawable.tempup);
        btnTemp_Down = BitmapFactory.decodeResource(getResources(),R.drawable.tempdown);

        //btnBoussol
        btnBoussol_Up = BitmapFactory.decodeResource(getResources(),R.drawable.boussolup);
        btnBoussol_Down = BitmapFactory.decodeResource(getResources(),R.drawable.boussoldown);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        screenW = w;
        screenH = h;
    }

    @Override
    protected void onDraw(Canvas _canvas) {
//        super.onDraw(canvas);


        //Logo
        Scaledlogo = Bitmap.createScaledBitmap(logo, _canvas.getWidth(),_canvas.getHeight(), true);
        _canvas.drawBitmap(Scaledlogo,0,0,null);

        //btnTemp
        posX_btnTemp_Up = (screenW - btnTemp_Up.getWidth())/3;
        posX_btnTemp_Down = (screenW - btnTemp_Down.getWidth())/3;
        posY_btnTemp = (int) (screenH * 0.45);

        if(btnTemp_State==false){
            _canvas.drawBitmap(btnTemp_Up,posX_btnTemp_Up,posY_btnTemp,null);
        }else{
            _canvas.drawBitmap(btnTemp_Down,posX_btnTemp_Down,posY_btnTemp,null);
        }

        //btnBoussol
        posX_btnBoussol_Up = (screenW - btnBoussol_Up.getWidth())/2+250;
        posX_btnBoussol_Down = (screenW - btnBoussol_Down.getWidth())/2+250;
        posY_btnBoussol = (int) (screenH * 0.45);

        if(btnBoussol_State==false){
            _canvas.drawBitmap(btnBoussol_Up,posX_btnBoussol_Up,posY_btnBoussol,null);
        }else{
            _canvas.drawBitmap(btnBoussol_Down,posX_btnBoussol_Down,posY_btnBoussol,null);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent _event) {
//        return super.onTouchEvent(event);

        if(_event.getAction() == MotionEvent.ACTION_DOWN){

            //t>touche
            Boolean t_lmtX_1 = _event.getX() > posX_btnTemp_Up;
            Boolean t_lmtX_2 = _event.getX() < posX_btnTemp_Up + btnTemp_Up.getWidth();
            Boolean t_lmtY_1 = _event.getY() > posY_btnTemp;
            Boolean t_lmtY_2 = _event.getY() < posY_btnTemp + btnTemp_Up.getHeight();

            if(t_lmtX_1 && t_lmtX_2 && t_lmtY_1 && t_lmtY_2){
                btnTemp_State=true;
            }

            //s>Sensor
            Boolean s_lmtX_1 = _event.getX() > posX_btnBoussol_Up;
            Boolean s_lmtX_2 = _event.getX() < posX_btnBoussol_Up + btnBoussol_Up.getWidth();
            Boolean s_lmtY_1 = _event.getY() > posY_btnBoussol;
            Boolean s_lmtY_2 = _event.getY() < posY_btnBoussol + btnBoussol_Up.getHeight();

            if(s_lmtX_1 && s_lmtX_2 && s_lmtY_1 && s_lmtY_2){
                btnBoussol_State = true;
            }

        }


        if(_event.getAction() == MotionEvent.ACTION_UP){

            if(btnTemp_State==true){

                btnTemp_State=false;

                GlobalVar.btnTempClicked = true;
                GlobalVar.btnBoussolClicked = false;

                ctx.startActivity(new Intent(ctx,MainTwo.class));
            }

            if(btnBoussol_State==true){

                btnBoussol_State=false;

                GlobalVar.btnBoussolClicked = true;
                GlobalVar.btnTempClicked = false;

                ctx.startActivity(new Intent(ctx,MainTwo.class));
            }
        }


        invalidate();
        return true;
    }
}
