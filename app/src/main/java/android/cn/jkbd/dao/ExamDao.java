package android.cn.jkbd.dao;

import android.cn.jkbd.ExamApplication;
import android.cn.jkbd.bean.Exam;
import android.cn.jkbd.bean.ExamInfo;
import android.cn.jkbd.bean.Result;
import android.cn.jkbd.until.OkHttpUtils;
import android.cn.jkbd.until.ResultUtils;
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

                    }

                    @Override
                    public void onError(String error) {
                        Log.e("main","error="+error);
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
                        Result result= ResultUtils.getListResultFromJson(jsonStr);
                        if (result!=null&&result.getError_code()==0){
                            List<Exam> list=result.getResult();
                            if (list!=null&&list.size()>0){
                                ExamApplication.getInstance().setmExamList(list);
                            }
                        }
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("main","error="+error);
                    }
                });
    }
}
