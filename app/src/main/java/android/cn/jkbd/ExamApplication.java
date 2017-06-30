package android.cn.jkbd;

import android.app.Application;
import android.cn.jkbd.bean.Exam;
import android.cn.jkbd.bean.ExamInfo;
import android.cn.jkbd.bean.Result;
import android.cn.jkbd.until.OkHttpUtils;
import android.cn.jkbd.until.ResultUtils;
import android.util.Log;

import java.util.List;

/**
 * Created by Administrator on 2017/6/30.
 */

public class ExamApplication extends Application {
    ExamInfo mExamInfo;
    List<Exam> mExamList;
    private static ExamApplication istance;
    @Override
    public void onCreate() {
        super.onCreate();
        istance=this;
        intiData();
    }
    public static ExamApplication getInstance(){
        return istance;
    }

    private void intiData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpUtils<ExamInfo> until=new OkHttpUtils<>(istance);
                String uri="http://101.251.196.90:8080/JztkServer/examInfo";
                until.url(uri)
                        .targetClass(ExamInfo.class)
                        .execute(new OkHttpUtils.OnCompleteListener<ExamInfo>() {
                            @Override
                            public void onSuccess(ExamInfo result) {
                                Log.e("main","result="+result);
                                mExamInfo=result;
                            }

                            @Override
                            public void onError(String error) {
                                Log.e("main","error="+error);
                            }
                        });

                OkHttpUtils<String> utils1=new OkHttpUtils<String>(istance);
                String url2="http://101.251.196.90:8080/JztkServer/getQuestions?testType=rand";
                utils1.url(url2)
                        .targetClass(String.class)
                        .execute(new OkHttpUtils.OnCompleteListener<String>() {
                            @Override
                            public void onSuccess(String jsonStr) {
                               Result result= ResultUtils.getListResultFromJson(jsonStr);
                                if (result!=null&&result.getError_code()==0){
                                    List<Exam> list=result.getResult();
                                    if (list!=null&&list.size()>0){
                                        mExamList=list;
                                    }
                                }
                            }

                            @Override
                            public void onError(String error) {
                                Log.e("main","error="+error);
                            }
                        });

            }
        }).start();

    }

    public ExamInfo getmExamInfo() {
        return mExamInfo;
    }

    public void setmExamInfo(ExamInfo mExamInfo) {
        this.mExamInfo = mExamInfo;
    }

    public List<Exam> getmExamList() {
        return mExamList;
    }

    public void setmExamList(List<Exam> mExamList) {
        this.mExamList = mExamList;
    }
}
