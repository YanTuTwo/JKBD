package android.cn.jkbd.biz;

import android.cn.jkbd.dao.ExamDao;
import android.cn.jkbd.dao.IExamDao;

/**
 * Created by Administrator on 2017/7/1 0001.
 */

public class ExamBiz implements IExamBiz {
    IExamDao dao;
    public ExamBiz(){
        this.dao=new ExamDao();
    }
    @Override
    public void beginExam() {
        dao.loadExamInfo();
        dao.loadQuestionLists();
    }

    @Override
    public void nextQuestion() {

    }

    @Override
    public void preQuestion() {

    }

    @Override
    public void commitExam() {

    }
}
