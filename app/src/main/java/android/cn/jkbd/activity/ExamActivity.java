package android.cn.jkbd.activity;

import android.cn.jkbd.ExamApplication;
import android.cn.jkbd.R;
import android.cn.jkbd.bean.Exam;
import android.cn.jkbd.bean.ExamInfo;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/6/29.
 */

public class ExamActivity extends AppCompatActivity {
    TextView tvExamInfo,tv_title,tv_op;
    ImageView mImageView;
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
        tv_title=(TextView)findViewById(R.id.tv_title);
        tv_op=(TextView)findViewById(R.id.tv_op);
        mImageView=(ImageView)findViewById(R.id.iv_exam_image);
    }

    private void initData() {
        ExamInfo examInfo=ExamApplication.getInstance().getmExamInfo();
        if (examInfo!=null){
            showData(examInfo);
        }
        List<Exam> examlist=ExamApplication.getInstance().getmExamList();
            if (examlist!=null){
                showExam(examlist);
            }
    }

    private void showExam(List<Exam> examlist) {
        Exam exam=examlist.get(0);
        tv_title.setText(exam.getQuestion());
        tv_op.setText("A."+exam.getItem1()+"\n"+"B."+exam.getItem2()+"\n"+"C."+exam.getItem3()+"\n"+"D."+exam.getItem4());
    }

    private void showData(ExamInfo examInfo) {
        tvExamInfo.setText(examInfo.toString());

    }
}
