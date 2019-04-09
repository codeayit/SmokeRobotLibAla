package com.haitiand.modbuslib.interfaces;

/**
 * Created by lny on 2018/4/23.
 */

public interface OnReadLinstener {
    void success(int result);
    void error();
}
