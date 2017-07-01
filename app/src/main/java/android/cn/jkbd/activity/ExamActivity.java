package android.cn.jkbd.activity;

import android.cn.jkbd.ExamApplication;
import android.cn.jkbd.R;
import android.cn.jkbd.bean.Exam;
import android.cn.jkbd.bean.ExamInfo;
import android.cn.jkbd.biz.ExamBiz;
import android.cn.jkbd.biz.IExamBiz;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2017/6/29.
 */

public class ExamActivity extends AppCompatActivity {
    TextView tvExamInfo,tv_title,tv_op;
    ImageView mImageView;
    IExamBiz biz;
    boolean isLoadExamInfo=false;
    boolean isLoadQuestions=false;

    LoadExamBroadcast mLoadExamBroadcast;
    LoadQuestionBroadcast mLoadQuestionBroadcast;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
//        ExamInfo examInfo =(ExamInfo) getIntent().getSerializableExtra("aaa");
//        TextView value = (TextView)findViewById(R.id.txt_examinfo);
//        value.setText(examInfo.toString());
        mLoadExamBroadcast=new LoadExamBroadcast();
        mLoadQuestionBroadcast=new LoadQuestionBroadcast();
        setListenr();
        intView();
        loadData();
    }

    private void setListenr() {
        registerReceiver(mLoadExamBroadcast,new IntentFilter(ExamApplication.LOAD_EXAM_INFO));
        registerReceiver(mLoadQuestionBroadcast,new IntentFilter(ExamApplication.LOAD_EXAM_QUESTION));
    }

    private void loadData() {
        biz=new ExamBiz();
        new Thread(new Runnable() {
            @Override
            public void run() {
                biz.beginExam();
            }
        }).start();
    }

    private void intView() {
        tvExamInfo=(TextView)findViewById(R.id.tv_examinfo);
        tv_title=(TextView)findViewById(R.id.tv_title);
        tv_op=(TextView)findViewById(R.id.tv_op);
        mImageView=(ImageView)findViewById(R.id.iv_exam_image);
    }

    private void initData() {
        if(isLoadExamInfo&&isLoadExamInfo){
        ExamInfo examInfo=ExamApplication.getInstance().getmExamInfo();
        if (examInfo!=null){
            showData(examInfo);
        }
        List<Exam> examlist=ExamApplication.getInstance().getmExamList();
            if (examlist!=null){
                showExam(examlist);
            }
        }
    }

    private void showExam(List<Exam> examlist) {
        Exam exam=examlist.get(0);
        tv_title.setText(exam.getQuestion());
        tv_op.setText("A."+exam.getItem1()+"\n"+"B."+exam.getItem2()+"\n"+"C."+exam.getItem3()+"\n"+"D."+exam.getItem4());
        Picasso.with(ExamActivity.this).load(exam.getUrl()).into(mImageView);
    }

    private void showData(ExamInfo examInfo) {
        tvExamInfo.setText(examInfo.toString());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLoadExamBroadcast!=null){
            unregisterReceiver(mLoadExamBroadcast);
        }
        if (mLoadQuestionBroadcast!=null){
            unregisterReceiver(mLoadQuestionBroadcast);
        }
    }

    class LoadExamBroadcast extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isSucces=intent.getBooleanExtra(ExamApplication.LOAD_DATA_SUCCESS,false);
            Log.e("LoadExamBroadcast","LoadExamBroadcast,isSucess="+isSucces);
            if(isSucces){
                isLoadExamInfo=true;

            }initData();
        }
    }
    class LoadQuestionBroadcast extends  BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isSucces=intent.getBooleanExtra(ExamApplication.LOAD_DATA_SUCCESS,false);
            Log.e("LoadQuestionBroadcast","LoadQuestionBroadcast,isSucess="+isSucces);
            if(isSucces){
                isLoadQuestions=true;

            }initData();
        }
    }
}
