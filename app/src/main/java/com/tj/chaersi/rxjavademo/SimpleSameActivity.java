package com.tj.chaersi.rxjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

public class SimpleSameActivity extends AppCompatActivity implements View.OnClickListener{

    private String testStr = "Hello World Welcome to Android";
    private String[] testStrs = new String[]{
            "Hello", "World", "Welcome", "to", "Android"
    };
    @BindView(R.id.showView) TextView showView;
    @BindView(R.id.abtn) Button aBtn;
    @BindView(R.id.bbtn) Button bBtn;
    @BindView(R.id.cbtn) Button cbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_same);
        ButterKnife.bind(this);

        aBtn.setOnClickListener(this);
        bBtn.setOnClickListener(this);
        cbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.abtn:
                Observable<String> obShow = Observable.just(testStr);
                obShow.observeOn(AndroidSchedulers.mainThread())
                        .map(new Func1<String, String>() {
                            @Override
                            public String call(String s) {
                                return s.toUpperCase();
                            }
                        })
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                showView.setText(s);
                            }
                        });

                break;
            case R.id.bbtn:
                showView.setText("");
                Observable<String> obMap = Observable.from(testStrs);
                obMap.observeOn(AndroidSchedulers.mainThread())
                        .map(new Func1<String, String>() {
                            @Override
                            public String call(String s) {
                                return s.toLowerCase();
                            }
                        })
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                showView.setText(showView.getText().toString()+" "+s);
                            }
                        });
                break;
            case  R.id.cbtn:
                Observable.just(testStrs)
                        .flatMap(new Func1<String[], Observable<String>>() {
                            @Override
                            public Observable<String> call(String[] strings) {

                                return Observable.from(strings);
                            }
                        })
                        .reduce(new Func2<String, String, String>() {
                            @Override
                            public String call(String s, String s2) {
                                return String.format("%s %s", s, s2);
                            }
                        })
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                showView.setText(s);
                            }
                        });
                break;
        }
    }
}
