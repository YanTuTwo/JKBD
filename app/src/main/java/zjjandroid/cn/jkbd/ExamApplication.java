package zjjandroid.cn.jkbd;

import android.app.Application;
import zjjandroid.cn.jkbd.bean.Exam;
import zjjandroid.cn.jkbd.bean.ExamInfo;
import zjjandroid.cn.jkbd.biz.IExamBiz;

import java.util.List;

/**
 * Created by Administrator on 2017/6/30.
 */

public class ExamApplication extends Application {
    public static String LOAD_EXAM_INFO="load_exam_info";
    public static String LOAD_EXAM_QUESTION="load_exam_question";
    public static String LOAD_DATA_SUCCESS="load_data_success";
    ExamInfo mExamInfo;
    List<Exam> mExamList;
    private static ExamApplication istance;
    IExamBiz biz;
    @Override
    public void onCreate() {
        super.onCreate();
        istance=this;

    }
    public static ExamApplication getInstance(){
        return istance;
    }

    private void intiData() {


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
