package android.cn.jkbd.activity;

import android.cn.jkbd.ExamApplication;
import android.cn.jkbd.R;
import android.cn.jkbd.bean.Exam;
import android.cn.jkbd.bean.ExamInfo;
import android.cn.jkbd.biz.ExamBiz;
import android.cn.jkbd.biz.IExamBiz;
import android.cn.jkbd.view.QuestionAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/6/29.
 */

public class ExamActivity extends AppCompatActivity {
    TextView tvExamInfo,tv_title,tv_op,tv_load,tvNO,tvtime,tv_Answer,tv_Explains;
    CheckBox cb_01,cb_02,cb_03,cb_04;
    CheckBox[] cbs=new CheckBox[4];
    LinearLayout linearLoading;
    ImageView mImageView;
    ProgressBar dialog;
    Gallery mGallery;
    IExamBiz biz;
    QuestionAdapter mAdapter;
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
        tvtime=(TextView)findViewById(R.id.tv_time);
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
        tv_Answer=(TextView)findViewById(R.id.tv_Answer);
        tv_Explains=(TextView)findViewById(R.id.tv_Explains) ;
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
        mGallery=(Gallery) findViewById(R.id.gallery);
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
                    initTimer(examInfo);
                }
                initGallery();
                showExam(biz.getExam());

            }else{
                linearLoading.setEnabled(true);
                dialog.setVisibility(View.GONE);
                tv_load.setText("下载失败，点击重新下载");
            }
        }
    }

    private void initGallery() {
        mAdapter=new QuestionAdapter(this);
        mGallery.setAdapter(mAdapter);
        mGallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("gallery","gallery item posotion="+position);
                saveUserAnswer();
                showExam(biz.getExam(position));
            }
        });
    }

    private void initTimer(ExamInfo examInfo) {
        int sumTime=examInfo.getLimitTime()*60*1000;
//        int sumTime=2*60*1000;
        final long overTime=(sumTime+System.currentTimeMillis());
        final Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                long l=overTime-System.currentTimeMillis();
                final long min=(long)l/1000/60;
                final long sec=(long)l/1000%60;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvtime.setText("剩余时间"+min+"分"+sec+"秒");
                        if (min==1&&sec==0){
                            Toast.makeText(getApplicationContext(), "距离考试结束还有1分钟",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        },0,1000);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timer.cancel();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        commit(null);
                    }
                });
            }
        },sumTime);
    }


    private void showExam(Exam exam) {
        Log.e("showExam","showExam,exam="+exam);
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
            }else {
                cb_03.setVisibility(View.VISIBLE);
                cb_04.setVisibility(View.VISIBLE);
            }
            resetOptions();
            String userAnswer=exam.getUserAnswer();
            if (userAnswer!=null && !userAnswer.equals("")){
                int userCB=Integer.parseInt(userAnswer)-1;
                cbs[userCB].setChecked(true);
                setOption(false);
                if (userAnswer.equals(exam.getAnswer())){
                    tv_Answer.setTextColor(this.getResources().getColor(R.color.colorgreen));
                }else {
                    tv_Answer.setTextColor(this.getResources().getColor(R.color.colorAccent));
                }
                changeResult(Integer.parseInt(exam.getAnswer()));
                tv_Explains.setText("解析："+exam.getExplains());
            }
            else {
                setOption(true);
                tv_Answer.setText("");
                tv_Explains.setText("");
            }

        }
    }

    private void changeResult(int answer) {
        switch (answer){
            case 1:
                tv_Answer.setText("答案：A");
                break;
            case 2:
                tv_Answer.setText("答案：B");
                break;
            case 3:
                tv_Answer.setText("答案：C");
                break;
            case 4:
                tv_Answer.setText("答案：D");
                break;

        }
    }

    private void setOption(boolean a) {
        for (int i=0;i<cbs.length;i++){
            cbs[i].setEnabled(a);
        }
    }

    private void resetOptions() {
        for (CheckBox cb : cbs) {
            cb.setChecked(false);
        }
    }

    private void saveUserAnswer(){

        for (int i = 0; i < cbs.length; i++) {
            if (cbs[i].isChecked()){
                biz.getExam().setUserAnswer(String.valueOf(i+1));
                mAdapter.notifyDataSetChanged();
                return;
            }
        }
        Log.e("save","saveUserAnswer，biz.getExam()="+biz.getExam());
        biz.getExam().setUserAnswer("");
        mAdapter.notifyDataSetChanged();


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
        saveUserAnswer();
        showExam(biz.preQuestion());
    }

    public void nextExam(View view) {
        saveUserAnswer();
        showExam(biz.nextQuestion());
    }

    public void commit(View view) {
        saveUserAnswer();
        int s=biz.commitExam();
        View inflate=View.inflate(this,R.layout.layout_result,null);
        TextView tvResult=(TextView) inflate.findViewById(R.id.tv_result);
        tvResult.setText("你的分数为\n"+s+"分");
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.exam_commit32x32)
                .setTitle("交卷")
//                .setMessage("你的分数为\n"+s+"分")
                .setView(inflate)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        builder.create().show();
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
