package com.hzj.hpai.networkrequests;


/**
 * @author zxm
 *         <p/>
 *         更新 ui界面回调
 */
public interface CommonListener {
    /**
     * @param action 信号指令
     * @param value  返回值
     */
    public void onSuccess(Object action, Object value);

    /**
     * @param action 信号指令
     * @param value  错误信息
     */
    public void onFaile(Object action, Object value);

    /**
     * @param action 信号指令
     * @param value  返回值
     */
    public void onException(Object action, Object value);

    /**
     * 开始请求
     *
     * @param action 信号指令
     * @param //value  返回值
     */
    public void onStart(Object action);

    /**
     * 请求结束
     *
     * @param action 信号指令
     * @param //value  返回值
     */
    public void onFinish(Object action);
}
