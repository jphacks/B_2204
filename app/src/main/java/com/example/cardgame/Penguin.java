package com.example.cardgame;

import android.graphics.Point;
import android.graphics.PointF;
import android.util.Log;
import android.widget.ImageView;

public class Penguin {
    public PointF rx;
    public PointF cx; // 中心座標
    public float theta; // 移動角度
    public int state;
    public ImageView img;

    Penguin(ImageView penguin_img, Point window_size){
        this.img = penguin_img;
        initPoint(window_size);
        this.state = 0;
        this.theta = (float) (2 * Math.PI * Math.random());
    }

    public PointF getPoint(){ return this.rx; }

    private void initPoint(Point window_size){
        this.cx = new PointF(window_size.x * 0.35f,window_size.y * 0.5f);
        this.rx = new PointF(window_size.x * 0.35f,window_size.y * 0.5f);
        this.img.setX((float)(this.rx.x));
        this.img.setY((float)(this.rx.y));
    }

    public void move(){
        if(Math.random() < 0.01) // 1%で方向を変える => レヴィウォーク
            this.theta =  (float) (2 * Math.PI * Math.random());
        PointF rand_walk = randomWalk();
        PointF cent_walk = centerWalk();
        this.rx.x += rand_walk.x + cent_walk.x;
        this.rx.y += rand_walk.y + cent_walk.y;
        this.img.setX((float)(this.rx.x));
        this.img.setY((float)(this.rx.y));
    }

    private PointF randomWalk(){
        return new PointF((float)Math.cos(this.theta), (float)Math.sin(this.theta));
    }

    private PointF centerWalk(){
        if(calcDistance()>=200)
            return new PointF((this.cx.x - this.rx.x)*0.01f,  ( this.cx.y - this.rx.y)*0.01f);
        else
            return new PointF(0f,0f);
    }

    private float calcDistance(){
        return (float) Math.sqrt((this.rx.x - this.cx.x)*(this.rx.x - this.cx.x)
                + (this.rx.y - this.cx.y)*(this.rx.y - this.cx.y));
    }
}
