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

        Intent intent=new Intent(MainActivity.this,ExamActivity.class);
//        intent.putExtra("aaa",result);
        startActivity(intent);
    }

    public void exit(View view) {
        finish();
    }
}
