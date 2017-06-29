package android.cn.jkbd.activity;

import android.cn.jkbd.R;
import android.cn.jkbd.bean.ExamInfo;
import android.cn.jkbd.until.OkHttpUtils;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void test(View view) {
        OkHttpUtils<ExamInfo>until=new OkHttpUtils<>(getApplicationContext());
        String uri="http://101.251.196.90:8080/JztkServer/examInfo";
        until.url(uri)
                .targetClass(ExamInfo.class)
                .execute(new OkHttpUtils.OnCompleteListener<ExamInfo>() {
                    @Override
                    public void onSuccess(ExamInfo result) {
                        Log.e("main","result="+result);
                        Intent intent=new Intent(MainActivity.this,ExamActivity.class);
                        intent.putExtra("examInfo",result);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("main","error="+error);
                    }
                });

    }

    public void exit(View view) {
        finish();
    }
}
