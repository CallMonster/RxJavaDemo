package com.tj.chaersi.rxjavademo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

public class DataConvertActivity extends AppCompatActivity implements View.OnClickListener{

    private Button goBtn;

    private String[] students;
    private ArrayList<Course> courseArr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_convert);
        goBtn= (Button) findViewById(R.id.goBtn);
        goBtn.setOnClickListener(this);

        students=new String[]{
            "老王","隔壁老王","邻居老王","楼下老王"
        };

        initCourseArr();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.SimpleBtn:
                Observable.from(courseArr).flatMap(new Func1<Course, Observable<String>>(){

                    @Override
                    public Observable<String> call(Course course) {
                        String[] info=new String[]{
                                course.getName(),course.getAge()+"",
                                course.isSex()+"",course.getRemark()+"",
                        };
                        return Observable.from(info);
                    }
                }).subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.i("dataConvert",s);
                    }
                });
                break;
        }
    }

    private void initCourseArr(){
        courseArr=new ArrayList<>();
        Course item1=new Course();
        item1.setName("老王");
        item1.setAge(12);
        item1.setSex(true);
        item1.setRemark("一个邻居");
        courseArr.add(item1);

        Course item2=new Course();
        item2.setName("隔壁老王");
        item2.setAge(14);
        item2.setSex(false);
        item2.setRemark("另一个邻居");
        courseArr.add(item2);

        Course item3=new Course();
        item3.setName("邻居老王");
        item3.setAge(15);
        item3.setSex(true);
        item3.setRemark("还有一个邻居");
        courseArr.add(item3);

        Course item4=new Course();
        item4.setName("楼下老王");
        item4.setAge(18);
        item4.setSex(false);
        item4.setRemark("最后一个邻居");
        courseArr.add(item4);
    }
}
