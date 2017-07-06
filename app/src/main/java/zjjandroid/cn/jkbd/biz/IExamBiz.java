package zjjandroid.cn.jkbd.biz;


import zjjandroid.cn.jkbd.bean.Exam;

/**
 * Created by Administrator on 2017/7/1 0001.
 */

public interface IExamBiz {
    void beginExam();
    Exam getExam();
    Exam getExam(int index);
    Exam nextQuestion();
    Exam preQuestion();
    int commitExam();
    String getExamIndex();
}
