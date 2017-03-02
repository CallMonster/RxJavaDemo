package com.tj.chaersi.rxjavademo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Action2;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button loginBtn;
    private EditText userEdit,passEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginBtn= (Button) findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(this);
        userEdit= (EditText) findViewById(R.id.telEdit);
        passEdit= (EditText) findViewById(R.id.passEdit);

    }

    @Override
    public void onClick(View v) {
        observable.subscribe(subscriber);
    }

    Subscriber<String> subscriber=new Subscriber<String>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(String s) {
            Log.i("hello",s);
        }

        @Override
        public void onStart() {
            super.onStart();
            Log.i("hello","onStart");
            userEdit.setText("开始监听……");
        }
    };

    Observable observable=Observable.create(new Observable.OnSubscribe<String>() {

        @Override
        public void call(Subscriber<? super String> subscriber) {
            subscriber.onNext("this is one");
            subscriber.onNext("this is two");
            subscriber.onNext("this is three");
            subscriber.onNext("this is four");
            subscriber.onCompleted();
        }
    });


    Action1<String> onNextAct=new Action1<String>() {
        @Override
        public void call(String s) {

        }
    };

    Action1<Throwable> onErr=new Action1<Throwable>() {
        @Override
        public void call(Throwable throwable) {

        }
    };

    Action0 onCompleteAct=new Action0() {
        @Override
        public void call() {

        }
    };
}
