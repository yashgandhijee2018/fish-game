package com.example.hp.practice1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class Working extends View {

    private Bitmap fish[]=new Bitmap[2];
    private Bitmap life[]=new Bitmap[2];

    private Bitmap background_image;
    private Paint score=new Paint();
    private int fish_posX=20,fish_posY,fishSpeed;

    private int yellow_posX,yellow_posY,yellowSpeed=16;
    private Paint yellowPaint=new Paint();

    private int green_posX,green_posY,greenSpeed=20;
    private Paint greenPaint=new Paint();

    private int black_posX,black_posY,blackSpeed=10;
    private Paint blackPaint=new Paint();
    /*
    private Bitmap plant;
    private int plant_posX,plant_posY,plantSpeed=8;
     */

    private Bitmap plant;
    private int plant_posX[]=new int[5];
    private int plant_posY,plantSpeed=8;
    private int canvasL,canvasH;
    private boolean is_touched=false;

    private int score_text,life_counter;

    public Working(Context context) {
        super(context);
        fish[0]= BitmapFactory.decodeResource(getResources(),R.drawable.fish1);
        fish[1]= BitmapFactory.decodeResource(getResources(),R.drawable.fish2);
        background_image=BitmapFactory.decodeResource(getResources(),R.drawable.background);
        //plant=BitmapFactory.decodeResource(getResources(),R.drawable.plant);

        score.setColor(Color.WHITE);
        score.setTextSize(70);
        score.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        score.setAntiAlias(true);

        life[0]=BitmapFactory.decodeResource(getResources(),R.drawable.hearts);
        life[1]=BitmapFactory.decodeResource(getResources(),R.drawable.heart_grey);
        fish_posY=550;

        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setAntiAlias(true);

        greenPaint.setColor(Color.GREEN);
        greenPaint.setAntiAlias(true);

        blackPaint.setColor(Color.BLACK);
        blackPaint.setAntiAlias(true);

        //plant_posY=0;
        //plant_posX=0;
        plant=BitmapFactory.decodeResource(getResources(),R.drawable.plant);
        for(int i=0;i<5;i++)
        {
            plant_posX[i]=0;
        }
        plant_posY=0;

        score_text=0;
        life_counter=3;
    }

    public Working(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Working(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public Working(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvasL = canvas.getWidth();
        canvasH = canvas.getHeight();
        canvas.drawBitmap(background_image, 0, 0, null);

        final int minY = fish[0].getHeight();
        final int maxY = canvasH - (fish[0].getHeight() * 3);
        fish_posY += fishSpeed;
        if (fish_posY < minY)
            fish_posY = minY;
        if (fish_posY > maxY)
            fish_posY = maxY;
        fishSpeed += 2;
        if (is_touched) {
            canvas.drawBitmap(fish[1], fish_posX, fish_posY, null);
            is_touched = false;
        } else {
            canvas.drawBitmap(fish[0], fish_posX, fish_posY, null);
        }

        //YELLOW BALL
        yellow_posX -= yellowSpeed;
        if (is_ball_hit(yellow_posX, yellow_posY)) {
            score_text += 10;
            yellow_posX = -100;
        }
        if (yellow_posX < 0) {
            yellow_posX = canvasL + 21;
            yellow_posY = (int) Math.floor(Math.random() * (maxY - minY)) + minY;
        }
        canvas.drawCircle(yellow_posX, yellow_posY, 25, yellowPaint);

        //GREEN BALL
        green_posX -= greenSpeed;
        if (is_ball_hit(green_posX, green_posY)) {
            score_text += 15;
            green_posX = -100;
        }
        if (green_posX < 0) {
            green_posX = canvasL + 21;
            green_posY = (int) Math.floor(Math.random() * (maxY - minY)) + minY;
        }
        canvas.drawCircle(green_posX, green_posY, 20, greenPaint);

        //BLACK BALL
        black_posX -= blackSpeed;
        if (is_ball_hit(black_posX, black_posY)) {
            score_text = 0;
            black_posX = -100;
            function_reduce_life();
        }
        if (black_posX < 0) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Do something after 5s = 5000ms
                    black_posX = canvasL ;
                    black_posY = (int) Math.floor(Math.random() * (maxY - minY)) + minY;
                }
            }, (int)(Math.floor(Math.random()*1000)));
        }
        canvas.drawCircle(black_posX, black_posY, 25, blackPaint);
        /*
///////////////////
        //PLANT IN BACKGROUND
        plant_posY=canvasH-plant.getWidth();
        plant_posX -= plantSpeed;
        if (plant_posX < 0) {
            plant_posX = canvasL;
        }
        for(int i=0;i<canvasL;i+=plant.getWidth()*0.9)
        {
            canvas.drawBitmap(plant, plant_posX+i,plant_posY, null);
        }
        for(int i=0;i<canvasL;i+=plant.getWidth()*0.9)
        {
            canvas.drawBitmap(plant, plant_posX+i,plant_posY, null);
        }
////////////////
         */
        plant_posY=canvasH-plant.getWidth();
        for(int i=0;i<5;i++)
        {
            if (plant_posX[i] < 0) {
                plant_posX[i] = canvasL;
            }
            plant_posX[i]=i*plant.getWidth();
            canvas.drawBitmap(plant, plant_posX[i],plant_posY, null);
            plant_posX[i] -= plantSpeed;
        }

        canvas.drawText("Score : " + Integer.toString(score_text), 20, 60, score);
        for (int i = 0; i < 3; i++)
        {
            int x=(int)(450+life[0].getWidth()*1.5*i);
            int y=35;

            if(i<life_counter)
            {
                canvas.drawBitmap(life[0], x, y, null);
            }
            else
            {
                canvas.drawBitmap(life[1], x, y, null);
            }
        }

    }

    private void function_reduce_life() {
        life_counter--;
        if(life_counter==0)
        {
            Toast.makeText(getContext(), "GAME OVER!!", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean is_ball_hit(int x,int y)
    {
        if(fish_posX<x && x<(fish_posX+fish[0].getWidth())&&fish_posY<y && y<(fish_posY+fish[0].getHeight()))
        {
            return true;
        }
        return false;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN)
        {
            is_touched=true;
            fishSpeed=-25;
        }
        return true;
    }
}
