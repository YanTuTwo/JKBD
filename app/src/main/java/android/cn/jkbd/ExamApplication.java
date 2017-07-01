package android.cn.jkbd;

import android.app.Application;
import android.cn.jkbd.bean.Exam;
import android.cn.jkbd.bean.ExamInfo;
import android.cn.jkbd.bean.Result;
import android.cn.jkbd.biz.ExamBiz;
import android.cn.jkbd.biz.IExamBiz;
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
    IExamBiz biz;
    @Override
    public void onCreate() {
        super.onCreate();
        istance=this;
        biz=new ExamBiz();
        intiData();
    }
    public static ExamApplication getInstance(){
        return istance;
    }

    private void intiData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                biz.beginExam();
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
