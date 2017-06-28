package android.cn.jkbd.bean;

import java.util.Objects;

/**
 * Created by Administrator on 2017/6/28 0028.
 */

public class Ruselt {

    /**
     * error_code : 0
     * reason : ok
     * result :
     */

    private int error_code;
    private String reason;
    private Objects result;

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

    public Objects getResult() {
        return result;
    }

    public void setResult(Objects result) {
        this.result = result;
    }
}
