package top.houyuji.common.api;

import top.houyuji.common.base.R;

public interface ResponseUtil {

    /**
     * 转换
     *
     * @param JSFResponse .
     * @param <T>         .
     * @return .
     */
    static <T> R<T> converter(JR<T> JSFResponse) {
        R<T> result = new R<T>();
        result.setSuccess(JSFResponse.isSuccess());
        int code = JSFResponse.isSuccess() ? R.OK : (JSFResponse.getCode() == JR.SUC_1 ? R.INTERNAL_SERVER_ERROR : JSFResponse.getCode());
        result.setCode(code);
        result.setMessage(JSFResponse.getMessage());
        result.setData(JSFResponse.getResult());
        return result;
    }
}
