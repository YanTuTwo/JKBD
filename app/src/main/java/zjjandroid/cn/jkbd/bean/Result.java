package zjjandroid.cn.jkbd.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/28 0028.
 */

public class Result {


    /**
     * error_code : 0
     * reason : ok
     * result : [{"id":12,"question":"这个标志是何含义？","answer":"4","item1":"前方40米减速","item2":"最低时速40公里","item3":"限制40吨轴重","item4":"限制最高时速40公里","explains":"限制最高时速40公里：表示该标志至前方限制速度标志的路段内，机动车行驶速度不得超过标志所示数值。此标志设在需要限制车辆速度的路段的起点。以图为例：限制行驶时速不得超过40公里。","url":"http://images.juheapi.com/jztk/c1c2subject1/12.jpg"},
     */

    private int error_code;
    private String reason;
    private List<Exam> result;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<Exam> getResult() {
        return result;
    }

    public void setResult(List<Exam> result) {
        this.result = result;
    }


}
