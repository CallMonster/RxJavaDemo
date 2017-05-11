package com.tj.chaersi.rxjavademo.rxjava;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tj.chaersi.rxjavademo.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.subjects.PublishSubject;

public class SelfAddActivity extends AppCompatActivity {

    @BindView(R.id.tagView) TextView tagView;
    @BindView(R.id.addBtn) Button addBtn;

    private PublishSubject numObservable;
    private int indexNum=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_add);
        ButterKnife.bind(this);


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                numObservable.onNext(indexNum++);
            }
        });

        numObservable=PublishSubject.create();
        numObservable.subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                tagView.setText(""+e);
            }

            @Override
            public void onNext(Integer integer) {
                tagView.setText("计数："+integer);
            }
        });

    }

    public static final void startAct(Context context){
        Intent intent=new Intent(context,SelfAddActivity.class);
        context.startActivity(intent);
    }
}
