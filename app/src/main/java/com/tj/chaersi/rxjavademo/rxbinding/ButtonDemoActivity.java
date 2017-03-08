package com.tj.chaersi.rxjavademo.rxbinding;

import android.support.v4.widget.CompoundButtonCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.tj.chaersi.rxjavademo.R;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

public class ButtonDemoActivity extends RxAppCompatActivity {
    private String TAG="ButtonDemoActivity";
    @BindView(R.id.mBtn) Button mBtn;
    @BindView(R.id.mEdit) EditText mEdit;

    private Subscription buttonSub,buttonSub2,buttonSub3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_demo);
        ButterKnife.bind(this);

        clickDemo();
        edittextDemo();
    }

    /**
     * 点击事件区别
     */
    private void clickDemo() {
        //old method
//        mBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showTip("old click");
//            }
//        });

        CompositeSubscription mCompositeSubscription=new CompositeSubscription();
        Observable<Void> clickObservable= RxView.clicks(mBtn).share();
        buttonSub = clickObservable.subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                showTip("new click");
            }
        });

        buttonSub2 = clickObservable.subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                showTip("new click2");
            }
        });

        buttonSub3 =clickObservable.subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                showTip("new click3");
            }
        });
        mCompositeSubscription.add(buttonSub);
        mCompositeSubscription.add(buttonSub2);
        mCompositeSubscription.add(buttonSub3);

        //当添加share()后，即可进行链式监听
    }

    private void edittextDemo(){
        Observable editobServable=RxTextView.textChanges(mEdit);
        editobServable.map(new Func1<CharSequence, String>() {
            @Override
            public String call(CharSequence charSequence) {
                return new StringBuilder(charSequence).reverse().toString();
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                showTip(s);
            }
        });


    }

    private void showTip(String tipStr) {
        Log.d(TAG,tipStr+"-");
//        Toast.makeText(this, tipStr, Toast.LENGTH_SHORT).show();
    }

}
