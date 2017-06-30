package android.cn.jkbd.activity;

import android.cn.jkbd.ExamApplication;
import android.cn.jkbd.R;
import android.cn.jkbd.bean.ExamInfo;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/6/29.
 */

public class ExamActivity extends AppCompatActivity {
    TextView tvExamInfo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
//        ExamInfo examInfo =(ExamInfo) getIntent().getSerializableExtra("aaa");
//        TextView value = (TextView)findViewById(R.id.txt_examinfo);
//        value.setText(examInfo.toString());
        intView();
        initData();
    }

    private void intView() {
        tvExamInfo=(TextView)findViewById(R.id.tv_examinfo);
    }

    private void initData() {
        ExamInfo examInfo=ExamApplication.getInstance().getmExamInfo();
        if (examInfo!=null){
            showData(examInfo);
        }
    }

    private void showData(ExamInfo examInfo) {
        tvExamInfo.setText(examInfo.toString());
    }
}
