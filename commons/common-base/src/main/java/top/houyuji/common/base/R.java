package top.houyuji.common.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import top.houyuji.common.base.enums.ErrorCodeEnums;

import java.io.Serializable;

@Data
public class R<T> implements Serializable {
    /**
     * 业务成功码
     */
    public static final Integer OK = 0;
    /**
     * 默认业务失败码（未知异常）
     */
    public static final Integer INTERNAL_SERVER_ERROR = 1099;

    /**
     * 成功标志
     */
    @Schema(description = "成功标志")
    private boolean success = true;
    /**
     * 返回处理消息
     */
    @Schema(description = "返回处理消息")
    private String message = "操作成功！";
    /**
     * 返回代码
     */
    @Schema(description = "返回代码")
    private Integer code = 0;
    /**
     * 返回数据对象 data
     */
    @Schema(description = "返回数据对象")
    private T data;
    /**
     * 时间戳
     */
    @Schema(description = "时间戳")
    private long timestamp = System.currentTimeMillis();


    /**
     * 接口返回数据
     */
    public R() {
    }

    /**
     * 成功并携带数据
     */
    public R(T data) {
        this.code = OK;
        this.data = data;
    }

    /***/
    public R(boolean success, String message) {
        this.success = success;
        this.message = message;
        this.code = success ? OK : INTERNAL_SERVER_ERROR;
    }

    /**
     * 接口返回数据:失败
     *
     * @param code 代码
     * @param msg  消息
     * @param <T>  数据类型
     * @return 接口返回数据:失败
     */
    public static <T> R<T> error(int code, String msg) {
        R<T> r = new R<T>();
        r.setCode(code);
        r.setMessage(msg);
        r.setSuccess(false);
        return r;
    }

    /**
     * 接口返回数据:成功
     *
     * @param <T> 数据类型
     * @return 接口返回数据:成功
     */
    public static <T> R<T> OK() {
        return OK(OK, "成功", null);
    }

    /**
     * 接口返回数据:成功
     *
     * @param data 数据
     * @param <T>  数据类型
     * @return 接口返回数据:成功
     */
    public static <T> R<T> OK(T data) {
        return OK(OK, "成功", data);
    }

    /**
     * 接口返回数据:成功
     *
     * @param msg  消息
     * @param data 数据
     * @param <T>  数据类型
     * @return 接口返回数据:成功
     */
    public static <T> R<T> OK(String msg, T data) {
        return OK(OK, msg, data);
    }

    /**
     * 接口返回数据:成功
     *
     * @param code 代码
     * @param msg  消息
     * @param data 数据
     * @param <T>  数据类型
     * @return 接口返回数据:成功
     */
    public static <T> R<T> OK(int code, String msg, T data) {
        R<T> r = new R<>();
        r.setSuccess(true);
        r.setCode(code);
        r.setMessage(msg);
        r.setData(data);
        return r;
    }

    /**
     * 接口返回数据:失败
     *
     * @param msg 消息
     * @param <T> 数据类型
     * @return 接口返回数据:失败
     */
    public static <T> R<T> NG(String msg) {
        return NG(INTERNAL_SERVER_ERROR, msg, null);
    }

    /**
     * 接口返回数据:失败
     *
     * @param msg  消息
     * @param data 数据
     * @param <T>  数据类型
     * @return 接口返回数据:失败
     */
    public static <T> R<T> NG(String msg, T data) {
        return NG(INTERNAL_SERVER_ERROR, msg, data);
    }

    /**
     * 接口返回数据:失败
     *
     * @param enums Response枚举
     * @param data  数据
     * @param <T>   数据类型
     * @return 接口返回数据:失败
     */
    public static <T> R<T> NG(ErrorCodeEnums enums, T data) {
        return NG(enums.getCode(), enums.getMessage(), data);
    }

    /**
     * 接口返回数据:失败
     *
     * @param code 代码
     * @param msg  消息
     * @param <T>  数据类型
     * @return 接口返回数据:失败
     */
    public static <T> R<T> NG(int code, String msg) {
        return NG(code, msg, null);
    }

    /**
     * 接口返回数据:失败
     *
     * @param code 代码
     * @param msg  消息
     * @param data 数据
     * @param <T>  数据类型
     * @return 接口返回数据:失败
     */
    public static <T> R<T> NG(int code, String msg, T data) {
        R<T> r = new R<>();
        r.setCode(code);
        r.setMessage(msg);
        r.setSuccess(false);
        r.setData(data);
        return r;
    }

    /**
     * 接口返回数据:成功
     *
     * @param message 消息
     * @return 接口返回数据:成功
     */
    public R<T> success(String message) {
        this.message = message;
        this.code = OK;
        this.success = true;
        return this;
    }

    /**
     * 接口返回数据:失败
     *
     * @param msg 消息
     * @return 接口返回数据:失败
     */
    public R<T> error(String msg) {
        return error(INTERNAL_SERVER_ERROR, msg);
    }

}