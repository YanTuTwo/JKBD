package zjjandroid.cn.jkbd.activity;

import zjjandroid.cn.jkbd.R;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void test(View view) {

        Intent intent=new Intent(MainActivity.this,ExamActivity.class);
        startActivity(intent);
    }

    public void exit(View view) {
        finish();
    }
}
