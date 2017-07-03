package android.cn.jkbd.biz;


import android.cn.jkbd.bean.Exam;

/**
 * Created by Administrator on 2017/7/1 0001.
 */

public interface IExamBiz {
    void beginExam();
    Exam getExam();
    Exam nextQuestion();
    Exam preQuestion();
    void commitExam();
    String getExamIndex();
}
