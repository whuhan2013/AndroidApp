package com.itheima.rocket;

import java.nio.channels.AlreadyConnectedException;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private ImageView iv;
	private ImageView iv_bottom; 
	private ImageView iv_top;
	private AnimationDrawable rocketAnimation;
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			int position = (Integer) msg.obj ;
			iv.layout(iv.getLeft(), position, iv.getRight(), position+iv.getHeight());
			if(position<320){
				iv_top.setVisibility(View.VISIBLE);
				AlphaAnimation aa = new AlphaAnimation(0.6f, 1.0f);
				aa.setDuration(300);
				iv_top.startAnimation(aa);
			}
			if(position<20){
				AlphaAnimation aa = new AlphaAnimation(1.0f, 0.0f);
				aa.setDuration(300);
				aa.setFillAfter(true);
				iv_top.startAnimation(aa);
			}
			
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		iv = (ImageView) findViewById(R.id.iv);
		iv.setBackgroundResource(R.drawable.rocket);
		iv_top = (ImageView) findViewById(R.id.iv_top);
		iv_bottom = (ImageView) findViewById(R.id.iv_bottom);
		rocketAnimation = (AnimationDrawable) iv.getBackground();
		rocketAnimation.start();

		iv.setOnTouchListener(new OnTouchListener() {
			int startX;
			int startY;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					startX =(int) event.getRawX();
					startY =(int) event.getRawY();
					break;

				case MotionEvent.ACTION_MOVE:
					int newX =(int) event.getRawX();
					int newY =(int) event.getRawY();
					int dx = newX - startX;
					int dy = newY - startY;
					//wm.updateViewLayout();
					iv.layout(iv.getLeft()+dx, iv.getTop()+dy, iv.getRight()+dx, iv.getBottom()+dy);
					startX =(int) event.getRawX();
					startY =(int) event.getRawY();
					break;
				case MotionEvent.ACTION_UP:
					int top = iv.getTop();
					int left = iv.getLeft();
					int right = iv.getRight();
					if(top>300&&left>100&&right<220){
						Toast.makeText(getApplicationContext(), "发射火箭", 0).show();
						iv_bottom.setVisibility(View.VISIBLE);
						AlphaAnimation aa = new AlphaAnimation(0.0f, 1.0f);
						aa.setDuration(500);
						aa.setRepeatCount(1);
						aa.setRepeatMode(Animation.REVERSE);
						aa.setFillAfter(true);
						iv_bottom.startAnimation(aa);
						sendRocket();
					}
					break;
				}
				return true;
			}
		});
	}

	protected void sendRocket() {
		new Thread(){
			public void run() {
				int start = 380;
				for(int i = 0 ;i<=11;i++){
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					//更新一下ui
					Message msg = Message.obtain();
					msg.obj = 380 - i*38;//计算出火箭的高度
					handler.sendMessage(msg);
				}
			};
		}.start();
		
	}
}
