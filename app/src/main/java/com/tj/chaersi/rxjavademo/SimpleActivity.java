package com.tj.chaersi.rxjavademo;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

import rx.Observable;
import rx.Observer;
import rx.Single;
import rx.functions.Action1;

public class SimpleActivity extends AppCompatActivity implements View.OnClickListener{

    private Button simpleBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);

        simpleBtn= (Button) findViewById(R.id.SimpleBtn);
        simpleBtn.setOnClickListener(this);

        for(int i=0;i<10;i++){
            resArr.add("第"+i+"个");
        }
    }

    ArrayList<String> resArr=new ArrayList<>();
    @Override
    public void onClick(View v) {

        Observable.from(resArr).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.i("simple",s);
            }
        });

    }
}
