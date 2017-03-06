package com.tj.chaersi.rxjavademo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ThreadCtrlActivity extends AppCompatActivity implements View.OnClickListener{
    private String TAG="ThreadCtrlActivity";
    private Button goBtn,threadBtn;
    private LinearLayout parentLayout;

    int[] imgRes = new int[]{
            R.mipmap.emoji_1f44f, R.mipmap.emoji_1f48e, R.mipmap.emoji_1f48f,
            R.mipmap.emoji_1f49a, R.mipmap.emoji_1f49b, R.mipmap.emoji_1f49c,
            R.mipmap.emoji_1f49e, R.mipmap.emoji_1f60b,
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_ctrl);
        parentLayout= (LinearLayout) findViewById(R.id.parentLayout);
        goBtn= (Button) findViewById(R.id.goBtn);
        goBtn.setOnClickListener(this);

        Log.i(TAG,"主线程："+android.os.Process.myTid());

        threadBtn= (Button) findViewById(R.id.goBtn2);
        threadBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.goBtn:
                //后台线程取数据，主线程显示
                Observable.just(1,2,3,4)
                        .subscribeOn(Schedulers.io())
                        .subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.i(TAG,"输出："+integer+"--"+android.os.Process.myTid());
                        //说明io线程新建了线程，与主线程不同
//                        03-02 22:09:17.026 1756-1756/com.tj.chaersi.rxjavademo I/ThreadCtrlActivity: 主线程：1756
//                        03-02 22:09:23.038 1756-1858/com.tj.chaersi.rxjavademo I/ThreadCtrlActivity: 输出：1--1858
//                        03-02 22:09:23.039 1756-1858/com.tj.chaersi.rxjavademo I/ThreadCtrlActivity: 输出：2--1858
//                        03-02 22:09:23.039 1756-1858/com.tj.chaersi.rxjavademo I/ThreadCtrlActivity: 输出：3--1858
//                        03-02 22:09:23.039 1756-1858/com.tj.chaersi.rxjavademo I/ThreadCtrlActivity: 输出：4--1858
                    }
                });

                break;
            case R.id.goBtn2:

                Observable.create(new Observable.OnSubscribe<Integer>(){
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        Log.i(TAG,"输出：--"+android.os.Process.myTid());
                        for(Integer res:imgRes){
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            subscriber.onNext(res);
                        }
                        subscriber.onCompleted();

                        //这里为新的线程，与主线程不同

                    }
                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Integer>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(Integer integer) {
                                Log.i(TAG, "输出OnNext：--" + android.os.Process.myTid());
                                ImageView subView = new ImageView(ThreadCtrlActivity.this);
                                subView.setImageResource(integer);
                                parentLayout.addView(subView);
                            }
                        });
                break;
        }
    }
}
