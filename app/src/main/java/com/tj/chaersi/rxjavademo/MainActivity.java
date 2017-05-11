package com.tj.chaersi.rxjavademo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.tj.chaersi.rxjavademo.rxjava.ColorContainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action;
import rx.functions.Action1;
import rx.functions.Func1;

public class MainActivity extends AppCompatActivity {
    private String TAG="MainActivity";

    @BindView(R.id.jump2Btn) Button jump2Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Observable<String> mObServable=Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                Log.i(TAG,"我的输出－");
                subscriber.onNext("德玛西亚万岁");
                subscriber.onCompleted();
            }
        });
        Subscriber mSubscriber=new Subscriber() {
            @Override
            public void onCompleted() {
                Log.i(TAG,"我完成了");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {
                Log.i(TAG,"next"+o);
            }
        };
        mObServable.subscribe(mSubscriber);


        Observable<String> simpleObservable=Observable.just("Hello World");
        Action1<String> onNextAct=new Action1<String>() {
            @Override
            public void call(String s) {
                Log.i(TAG,"第一种简约写法："+s);
            }
        };
        simpleObservable.subscribe(onNextAct);


        Observable.just("hello world").subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.i(TAG,"第二种简约写法："+s);
            }
        });

        Log.i(TAG,"---------------华丽丽的分割线--------------------");

        Observable.just("Hello World").map(new Func1<String, Integer>() {
            @Override
            public Integer call(String s) {
                Log.i(TAG,"item map1-----"+s);
                return 1;
            }
        }).map(new Func1<Integer, String>() {
            @Override
            public String call(Integer integer) {
                Log.i(TAG,"item map2-----"+integer);

                return "第二步";
            }
        }).map(new Func1<String, Integer>() {
            @Override
            public Integer call(String s) {
                Log.i(TAG,"item map3-----"+s);

                return 3;
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.i(TAG,"item map final-----"+integer);
            }
        });

        Button jumpBtn= (Button) findViewById(R.id.jumpBtn);
        jumpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        jump2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorContainActivity.startAct(MainActivity.this);
            }
        });

    }
}
