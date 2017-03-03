package com.tj.chaersi.rxjavademo;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import rx.Observable;
import rx.Observer;
import rx.Single;
import rx.Subscriber;
import rx.functions.Action1;

public class SimpleActivity extends AppCompatActivity implements View.OnClickListener{

    private Button simpleBtn,SimpleBtn2;
    private LinearLayout parentLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);

        parentLayout= (LinearLayout) findViewById(R.id.parentLayout);
        SimpleBtn2= (Button) findViewById(R.id.SimpleBtn2);
        SimpleBtn2.setOnClickListener(this);
        simpleBtn= (Button) findViewById(R.id.SimpleBtn);
        simpleBtn.setOnClickListener(this);

        for(int i=0;i<10;i++){
            resArr.add("第"+i+"个");
        }
    }

    int[] imgRes = new int[]{
            R.mipmap.emoji_1f44f, R.mipmap.emoji_1f48e, R.mipmap.emoji_1f48f, R.mipmap.emoji_1f49a,
            R.mipmap.emoji_1f49b, R.mipmap.emoji_1f49c, R.mipmap.emoji_1f49e, R.mipmap.emoji_1f60b,
    };

    ArrayList<String> resArr=new ArrayList<>();
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.SimpleBtn:
                Observable.from(resArr).subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.i("simple",s);
                    }
                });

                break;
            case R.id.SimpleBtn2:
                Observable.create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        for(Integer res:imgRes){
                            subscriber.onNext(res);
                        }
                        subscriber.onCompleted();
                    }
                }).subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer ints) {
                        ImageView subView=new ImageView(SimpleActivity.this);
                        subView.setImageResource(ints);
                        parentLayout.addView(subView);
                    }
                });
                break;
        }
    }
}
