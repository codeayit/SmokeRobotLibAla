package com.haitiand.modbuslib;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;

import com.ala.libmodbusapi.libmodbusapi;


/**
 * Created by lny on 2018/4/23.
 */

public class MbManager {

    public static final int CODE_OK = 0;
    public static final int CODE_ERROR = -1;

    public static final String TAG = "MbManager";
    public static final long DEFAULT_TIMEOUT = 5*1000;

    public static MbManager instance;
    private Context mContext;
    private static boolean inited;
    private int myBusId;
    private long myTimeOut;

    //     ModbusLib.modbus_write_registers(8, 6, 1, new int[]{257});
    private libmodbusapi modbusApi;





    public MbManager() {

    }

    public void loadLibrary(){
        System.loadLibrary("ala_devicejni");
    }

    public static MbManager getInstance(){
        if (instance==null){
            synchronized (MbManager.class){
                if (instance==null){
                    instance = new MbManager();
                }
            }
        }
        return instance;
    }

    public static boolean isInited(){
        return inited;
    }

    public boolean init(Context context,int busId,String path, int baudrate){
        return init(context,busId,DEFAULT_TIMEOUT,path,baudrate,8,1);
    }

    /**
     *  初始化
     * @param context
     * @param busId
     * @param timeOut  链接超时时间
     * @param path 串口地址
     * @param baudrate 波特率
     * @param dataBits 数据位
     * @param stopBits 停止位
     * @return
     */
    public boolean init(Context context,int busId,long timeOut,String path, int baudrate, int dataBits, int stopBits) {
        mContext = context;
        modbusApi = new libmodbusapi();
        int fid = modbusApi.init_modbus(path, busId, baudrate, dataBits, stopBits, 3000, 3000);
        if (fid < 0) {
            inited = false;
        } else {
            inited = true;
        }
        return inited;
    }

    public int  read_registers(final int addr)   {
        int[] holdingRegs = new int[1];
        int code =  modbusApi.modbus_read_registers(myBusId, addr, 1, holdingRegs);
        Log.d(TAG,"read_registers :addr="+addr+" , value="+holdingRegs[0]+" , code="+code);
        if (code<0){
            code = CODE_ERROR;
            return code;
        }
        holdingRegs[0] = (short)holdingRegs[0];
        return holdingRegs[0];
    }


    public int read_registers32(int addr) {
        int[] holdingRegs = new int[2];
        int code =  modbusApi.modbus_read_registers(myBusId, addr, 2, holdingRegs);
        Log.d(TAG,"read_registers :addr="+addr+" , value="+holdingRegs[0]+" , code="+code);
        if (code<0){
            code = CODE_ERROR;
            return code;
        }
        StringBuilder sb = new StringBuilder();
        int value;
        for(value = holdingRegs.length - 1; value >= 0; --value) {
            sb.append(this.hexNumber(holdingRegs[value]));
        }
        value = Long.valueOf(sb.toString(), 16).intValue();
        return value;
    }


    public int write_registers(final int addr, final int value){
        int code = modbusApi.modbus_write_register(myBusId, addr, value);
        Log.d(TAG,"write_registers :addr="+addr+" , value="+value+" , code="+code);
        if (code<0){
            code = CODE_ERROR;
        }else{
            code = CODE_OK;
        }
        return code;
    }

    public int write_registers32(int addr, int value) {
        int code = modbusApi.modbus_write_register(myBusId, addr, value);
        Log.d(TAG,"write_registers32 :addr="+addr+" , value="+value+" , code="+code);
        if (code<0){
            code = CODE_ERROR;

        }else{
            code = CODE_OK;
        }
        return code;
    }


    /**
     *  读取单个寄存器
     * @param addr
     * @param timeOut 超时时间
     * @return
     */
    public int read_registers_must(int addr,long timeOut) {
        int value = -1;
        long startTime  = SystemClock.elapsedRealtime();
        while(true) {
            value = read_registers(addr);
            if (value!=CODE_ERROR){
                return value;
            }
            if (SystemClock.elapsedRealtime()-startTime>=timeOut){
                return value;
            }
        }
    }

    /**
     *
     * @param addr
     * @return
     */
    public int read_registers_must(int addr) {
        return read_registers_must(addr,myTimeOut);
    }



    /**
     * 读取两个寄存器
     * @param addr
     * @param timeOut
     * @return
     */
    public int read_registers32_must(int addr,long timeOut) {
        int value = -1;
        long startTime  = SystemClock.elapsedRealtime();
        while(true) {
            value = read_registers32(addr);
            if (value!=CODE_ERROR){
                return value;
            }
            if (SystemClock.elapsedRealtime()-startTime>=timeOut){
                return value;
            }
        }
    }
    public int read_registers32_must(int addr) {
        return read_registers32_must(addr,myTimeOut);
    }



    public int write_registers_must(int addr, int value,long timeOut) {
        long startTime = SystemClock.elapsedRealtime();
        while(true) {
            int code = write_registers(addr, value);
            if (code!=CODE_ERROR){
                return code;
            }
            if (SystemClock.elapsedRealtime()-startTime>timeOut){
                return CODE_ERROR;
            }
        }
    }

    public int write_registers_must(int addr, int value) {
        return write_registers_must(addr,value,myTimeOut);
    }


    public int write_registers32_must(int addr, int value,long timeOut) {
        long startTime = SystemClock.elapsedRealtime();
        while(true) {
            int code = write_registers32(addr, value);
            if (code==CODE_OK){
                return CODE_OK;
            }
            if (SystemClock.elapsedRealtime()-startTime>=timeOut){
                return CODE_ERROR;
            }
        }
    }
    public int write_registers32_must(int addr, int value) {
        return write_registers32_must(addr,value);
    }

    private String hexNumber(int number) {
        String numberStr = Integer.toHexString(number);
        switch(numberStr.length()) {
            case 1:
                numberStr = "000" + numberStr;
                break;
            case 2:
                numberStr = "00" + numberStr;
                break;
            case 3:
                numberStr = "0" + numberStr;
            case 4:
        }

        return numberStr;
    }

}
