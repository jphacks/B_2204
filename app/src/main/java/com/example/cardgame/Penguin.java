package com.example.cardgame;

import android.graphics.Point;
import android.graphics.PointF;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Penguin {
    public PointF rx;
    public PointF cx; // 中心座標
    public float theta; // 移動角度
    public int state;
    public ImageView img;
    public List<PointF> feeds = new ArrayList<>(); // 餌配列
    public List<ImageView> feed_imgs = new ArrayList<>(); // 餌画像配列
    public boolean isFind = false; // 餌を見つけたか
    public int idx = -1; // 餌番号

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
        if(!isFind && feeds.size() != 0)
            findFeed();
        PointF feed_walk = feedWalk();
        if(isFind) {
            rand_walk = new PointF(0, 0);
            cent_walk = new PointF(0, 0);
            if(calcDistance(rx, feeds.get(idx))<10) {
                try {
                    this.eat();
                } catch (InterruptedException e) {
                    Log.e("EROOR: ", String.valueOf(e));
                }
            }
        }

        this.rx.x += rand_walk.x + cent_walk.x + feed_walk.x;
        this.rx.y += rand_walk.y + cent_walk.y + feed_walk.y;
        this.img.setX((float)(this.rx.x));
        this.img.setY((float)(this.rx.y));
    }

    // 運動方程式
    private PointF randomWalk(){
        return new PointF(2f*(float)Math.cos(this.theta), 2f*(float)Math.sin(this.theta));
    }

    private PointF centerWalk(){
        if(calcDistance(this.rx, this.cx)>=200)
            return new PointF((this.cx.x - this.rx.x)*0.02f,  ( this.cx.y - this.rx.y)*0.02f);
        else
            return new PointF(0f,0f);
    }

    private PointF feedWalk(){
        if(idx < 0) return new PointF(0, 0);
        return new PointF((this.feeds.get(idx).x - this.rx.x)*0.05f,  ( this.feeds.get(idx).y - this.rx.y)*0.05f);
    }

    private float calcDistance(PointF p1, PointF p2){
        return (float) Math.sqrt((p1.x - p2.x)*(p1.x - p2.x) + (p1.y - p2.y)*(p1.y - p2.y));
    }

    public void addFeed(PointF p, ImageView feed_img){
        feeds.add(p);
        isFind = false;
        feed_imgs.add(feed_img);
    }

    private int findFeed(){
        float min_dis = 400; // 理論上は400が最大距離
        int i = 0;
        try {
            for (PointF feed : feeds) {
                float dis = calcDistance(this.rx, feed);
                if (dis < min_dis) {
                    min_dis = dis;
                    isFind = true;
                    idx = i;
                }
                i++;
            }
        }catch(Exception e){
            return -1;
        }
        return -1;
    }

    // 餌を食う
    private void eat() throws InterruptedException {
        for(int i=0; i<10; i++) {
            Thread.sleep(100);//0.05秒停止
            img.setImageResource(R.drawable.baby_feed);
            Thread.sleep(100);//0.1秒停止
            img.setImageResource(R.drawable.baby_settle);
        }
        feed_imgs.get(idx).setImageResource(R.drawable.bone);
        feeds.remove(idx); // 削除
        feed_imgs.remove(idx);
        idx = -1;
        isFind = false;
    }
}
