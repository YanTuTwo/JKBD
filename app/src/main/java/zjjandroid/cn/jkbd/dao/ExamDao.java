package zjjandroid.cn.jkbd.dao;

import zjjandroid.cn.jkbd.ExamApplication;
import zjjandroid.cn.jkbd.bean.Exam;
import zjjandroid.cn.jkbd.bean.ExamInfo;
import zjjandroid.cn.jkbd.bean.Result;
import zjjandroid.cn.jkbd.until.OkHttpUtils;
import zjjandroid.cn.jkbd.until.ResultUtils;
import android.content.Intent;
import android.util.Log;

import java.util.List;

/**
 * Created by Administrator on 2017/7/1 0001.
 */

public class ExamDao implements IExamDao {
    @Override
    public void loadExamInfo() {
        OkHttpUtils<ExamInfo> until=new OkHttpUtils<>(ExamApplication.getInstance());
        String uri="http://101.251.196.90:8080/JztkServer/examInfo";
        until.url(uri)
                .targetClass(ExamInfo.class)
                .execute(new OkHttpUtils.OnCompleteListener<ExamInfo>() {
                    @Override
                    public void onSuccess(ExamInfo result) {
                        Log.e("main","result="+result);
                        ExamApplication.getInstance().setmExamInfo(result);
                        ExamApplication.getInstance()
                                .sendBroadcast(new Intent(ExamApplication.LOAD_EXAM_INFO)
                                .putExtra(ExamApplication.LOAD_DATA_SUCCESS,true));
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("main","error="+error);
                        ExamApplication.getInstance()
                                .sendBroadcast(new Intent(ExamApplication.LOAD_EXAM_INFO)
                                .putExtra(ExamApplication.LOAD_DATA_SUCCESS,false));
                    }
                });
    }

    @Override
    public void loadQuestionLists() {
        OkHttpUtils<String> utils1=new OkHttpUtils<String>(ExamApplication.getInstance());
        String url2="http://101.251.196.90:8080/JztkServer/getQuestions?testType=rand";
        utils1.url(url2)
                .targetClass(String.class)
                .execute(new OkHttpUtils.OnCompleteListener<String>() {
                    @Override
                    public void onSuccess(String jsonStr) {
                        boolean success=false;
                        Result result= ResultUtils.getListResultFromJson(jsonStr);
                        if (result!=null&&result.getError_code()==0){
                            List<Exam> list=result.getResult();
                            if (list!=null&&list.size()>0){
                                ExamApplication.getInstance().setmExamList(list);
                               success=true;
                            }
                        }
                        ExamApplication.getInstance()
                                .sendBroadcast(new Intent(ExamApplication.LOAD_EXAM_QUESTION)
                                        .putExtra(ExamApplication.LOAD_DATA_SUCCESS,success));
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("main","error="+error);
                        ExamApplication.getInstance()
                                .sendBroadcast(new Intent(ExamApplication.LOAD_EXAM_QUESTION)
                                        .putExtra(ExamApplication.LOAD_DATA_SUCCESS,false));
                    }
                });
    }
}
