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
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2017/6/29.
 */

public class ExamActivity extends AppCompatActivity {
    TextView tvExamInfo,tv_title,tv_op,tv_load,tvNO;
    CheckBox cb_01,cb_02,cb_03,cb_04;
    CheckBox[] cbs=new CheckBox[4];
    LinearLayout linearLoading;
    ImageView mImageView;
    ProgressBar dialog;
    IExamBiz biz;
    boolean isLoadExamInfo=false;
    boolean isLoadQuestions=false;

    boolean isLoadExamInfoRecevier=false;
    boolean isLoadQuestionsRecevier=false;

    LoadExamBroadcast mLoadExamBroadcast;
    LoadQuestionBroadcast mLoadQuestionBroadcast;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        mLoadExamBroadcast=new LoadExamBroadcast();
        mLoadQuestionBroadcast=new LoadQuestionBroadcast();
        setListenr();
        intView();
        biz=new ExamBiz();
        loadData();
    }

    private void setListenr() {
        registerReceiver(mLoadExamBroadcast,new IntentFilter(ExamApplication.LOAD_EXAM_INFO));
        registerReceiver(mLoadQuestionBroadcast,new IntentFilter(ExamApplication.LOAD_EXAM_QUESTION));
    }

    private void loadData() {
        linearLoading.setEnabled(false);
        dialog.setVisibility(View.VISIBLE);
        tv_load.setText("下载数据...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                biz.beginExam();
            }
        }).start();
    }

    private void intView() {
        linearLoading=(LinearLayout)findViewById(R.id.layout_loading);
        dialog=(ProgressBar) findViewById(R.id.load_dialog);
        tvExamInfo=(TextView)findViewById(R.id.tv_examinfo);
        tv_title=(TextView)findViewById(R.id.tv_title);
        tv_op=(TextView)findViewById(R.id.tv_op);
        cb_01=(CheckBox)findViewById(R.id.cb_01);
        cb_02=(CheckBox)findViewById(R.id.cb_02);
        cb_03=(CheckBox)findViewById(R.id.cb_03);
        cb_04=(CheckBox)findViewById(R.id.cb_04);
        cbs[0]=cb_01;
        cbs[1]=cb_02;
        cbs[2]=cb_03;
        cbs[3]=cb_04;
        tv_load=(TextView) findViewById(R.id.tv_load);
        tvNO=(TextView)findViewById(R.id.tv_exam_no);
        mImageView=(ImageView)findViewById(R.id.iv_exam_image);
        linearLoading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
        cb_01.setOnCheckedChangeListener(listener);
        cb_02.setOnCheckedChangeListener(listener);
        cb_03.setOnCheckedChangeListener(listener);
        cb_04.setOnCheckedChangeListener(listener);
    }

    CompoundButton.OnCheckedChangeListener listener=new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    int userAnswer=0;
                    switch (buttonView.getId()){
                        case R.id.cb_01:
                            userAnswer=1;
                            break;
                        case R.id.cb_02:
                            userAnswer=2;
                            break;
                        case R.id.cb_03:
                            userAnswer=3;
                            break;
                        case R.id.cb_04:
                            userAnswer=4;
                            break;
                    }
                    Log.e("checkedChange","usera="+userAnswer+",isChecked"+isChecked);
                    if (userAnswer>0){
                        for (CheckBox cb:cbs) {
                            cb.setChecked(false);
                        }
                        cbs[userAnswer-1].setChecked(true);
                    }
                }

            }
    };

    private void initData() {
        if (isLoadQuestionsRecevier&&isLoadExamInfoRecevier) {
            if (isLoadExamInfo && isLoadExamInfo) {
                linearLoading.setVisibility(View.GONE);
                ExamInfo examInfo = ExamApplication.getInstance().getmExamInfo();
                if (examInfo != null) {
                    showData(examInfo);
                }
                showExam(biz.getExam());
            }else{
                linearLoading.setEnabled(true);
                dialog.setVisibility(View.GONE);
                tv_load.setText("下载失败，点击重新下载");
            }
        }
    }

    private void showExam(Exam exam) {
        if (exam!=null) {
            tvNO.setText(biz.getExamIndex());
            tv_title.setText(exam.getQuestion());
            tv_op.setText("A." + exam.getItem1() + "\n" + "B." + exam.getItem2() + "\n" + "C." + exam.getItem3() + "\n" + "D." + exam.getItem4());
            if (exam.getUrl()!=null&&!exam.getUrl().equals("")){
                mImageView.setVisibility(View.VISIBLE);
                Picasso.with(ExamActivity.this)
                        .load(exam.getUrl())
                        .into(mImageView);
            }else{
                mImageView.setVisibility(View.GONE);
            }
            if (exam.getItem3().equals("")&&exam.getItem4().equals("")){
                tv_op.setText("A." + exam.getItem1() + "\n" + "B." + exam.getItem2() );
                cb_03.setVisibility(View.GONE);
                cb_04.setVisibility(View.GONE);
            }
        }
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

    public void preExam(View view) {
        showExam(biz.preQuestion());
    }

    public void nextExam(View view) {
        showExam(biz.nextQuestion());
    }

    class LoadExamBroadcast extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isSucces=intent.getBooleanExtra(ExamApplication.LOAD_DATA_SUCCESS,false);
            Log.e("LoadExamBroadcast","LoadExamBroadcast,isSucess="+isSucces);
            if(isSucces){
                isLoadExamInfo=true;
            }
            isLoadExamInfoRecevier=true;
            initData();
        }
    }
    class LoadQuestionBroadcast extends  BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isSucces=intent.getBooleanExtra(ExamApplication.LOAD_DATA_SUCCESS,false);
            Log.e("LoadQuestionBroadcast","LoadQuestionBroadcast,isSucess="+isSucces);
            if(isSucces){
                isLoadQuestions=true;
            }
            isLoadQuestionsRecevier=true;
            initData();
        }
    }
}
