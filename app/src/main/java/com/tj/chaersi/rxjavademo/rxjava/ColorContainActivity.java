package com.tj.chaersi.rxjavademo.rxjava;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.tj.chaersi.rxjavademo.R;
import com.tj.chaersi.rxjavademo.utils.RequestUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ColorContainActivity extends AppCompatActivity {

    @BindView(R.id.testListView) ListView testListView;
    @BindView(R.id.titleView) TextView titleView;

    String _url="http://ditu.amap.com/service/regeo?longitude=121.04925573429551&latitude=31.315590522490712";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_contain);
        ButterKnife.bind(this);

        Log.i("Color__","线程号1："+Thread.currentThread().getId());
        /*使用just()的时候，获取数据不能使用耗时的操作(如网络请求)*/
//        Observable<List<String>> listObservable= Observable.just(getColorList());

        /*使用fromCallable()，由于在其中存在耗时的操作，所以在异步的情况下进行了数据的获取。且并不在主线程里*/
        Observable<List<String>> listObservable= Observable.fromCallable(new Callable<List<String>>() {
            @Override
            public List<String> call() throws Exception {
                Log.i("Color__", "线程号2：" + Thread.currentThread().getId());

                return getColorList();
            }
        });
        //subscribeOn()设置了数据源(被监听者)的所属线程
        //observeOn()设置了订阅者(监听者)的所属线程
        listObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("subscribe","subscribe:"+e);
                    }

                    @Override
                    public void onNext(List<String> strings) {
                        Log.i("Color__", "线程号3：" + Thread.currentThread().getId());

                        titleView.setText("请选择颜色");

                        ColorContainAdapter adapter = new ColorContainAdapter(ColorContainActivity.this, strings);
                        testListView.setAdapter(adapter);
                        adapter.addOnItemClickListener(new ColorContainAdapter.OnAdapterListenerImpl() {
                            @Override
                            public void onItemClickListener(int position, String content) {
                                titleView.setText("最新为：" + content);
                                SelfAddActivity.startAct(ColorContainActivity.this);
                                finish();
                            }
                        });

                    }
                });
    }

    List<String> colorArr;
    private List<String> getColorList(){
        if(colorArr!=null&&colorArr.size()>0){
            return colorArr;
        }else{
            String reqStr=RequestUtils.getNet(_url);
            Log.d("netRequest","请求："+reqStr);

            colorArr=new ArrayList<>();
            colorArr.add("黄色");
            colorArr.add("红色");
            colorArr.add("绿色");
            colorArr.add("蓝色");
            colorArr.add("粉色");
            colorArr.add("紫色");
            colorArr.add("青色");
            return colorArr;
        }
    }


    public static final void startAct(Context context){
        Intent intent=new Intent(context,ColorContainActivity.class);
        context.startActivity(intent);
    }

}
