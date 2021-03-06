package zjjandroid.cn.jkbd.biz;

import zjjandroid.cn.jkbd.ExamApplication;
import zjjandroid.cn.jkbd.bean.Exam;
import zjjandroid.cn.jkbd.dao.ExamDao;
import zjjandroid.cn.jkbd.dao.IExamDao;

import java.util.List;

/**
 * Created by Administrator on 2017/7/1 0001.
 */

public class ExamBiz implements IExamBiz {
    IExamDao dao;
    int examIndex = 0;
    List<Exam>examList=null;

    public ExamBiz(){
        this.dao=new ExamDao();
    }

    @Override
    public void beginExam() {
        examIndex=0;
        dao.loadExamInfo();
        dao.loadQuestionLists();

    }

    @Override
    public Exam getExam() {
        examList=ExamApplication.getInstance().getmExamList();
        if (examList!=null){
            return examList.get(examIndex);
        }else{
            return null;
        }

    }

    @Override
    public Exam getExam(int index) {
        examList=ExamApplication.getInstance().getmExamList();
        examIndex=index;
        if (examList!=null){
            return examList.get(examIndex);
        }else{
            return null;
        }

    }

    @Override
    public Exam nextQuestion() {
        if (examList!=null){
            if (examIndex<examList.size()-1){
                examIndex++;
            }
            return examList.get(examIndex);
        }else{
            return null;
        }
    }

    @Override
    public Exam preQuestion() {
        if (examList!=null){
            if(examIndex>0){
                examIndex--;
            }
            return examList.get(examIndex);
        }else{
            return null;
        }
    }

    @Override
    public int commitExam() {
        int s=0;
        for (Exam exam : examList) {
            String userAnswer=exam.getUserAnswer();
            if (userAnswer!=null&&!userAnswer.equals("")){
                if (exam.getAnswer().equals(userAnswer)){
                    s++;
                }
            }
        }
        return s;
    }

    @Override
    public String getExamIndex() {
        return (examIndex+1)+".";
    }
}
