package com.haitiand.modbuslib.interfaces;

/**
 * Created by lny on 2018/4/23.
 * 初始化监听
 */

public interface OnInitListener {
    void success();
    void error(int code);
}
