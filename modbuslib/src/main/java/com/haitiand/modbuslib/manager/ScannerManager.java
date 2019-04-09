package com.haitiand.modbuslib.manager;

import android.os.StatFs;
import android.os.SystemClock;
import android.text.TextUtils;
import android.text.method.KeyListener;
import android.util.Log;

import com.ala.ALAdevicestatusrw.ALA_Device_SerialApi;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lny on 2018/4/24.
 */

public class ScannerManager {

    public static String none = "none";

    public static String TAG = "ScannerManager";

    private ALA_Device_SerialApi serial_tty_barcode = null;
    private ALA_Device_SerialApi serial_tty_barcode_1 = null;

    public void setDefaultBarcode(String barcode){
        none = barcode;
    }

    private String barcode;



    private static ScannerManager instance;

    public static ScannerManager getInstance() {
        if (instance == null) {
            synchronized (ScannerManager.class) {
                if (instance == null) {
                    instance = new ScannerManager();
                }
            }
        }
        return instance;
    }

    public ScannerManager() {
        serial_tty_barcode = new ALA_Device_SerialApi("/dev/ttyS2", 115200, 8, 0, 0, 1, 1);
        serial_tty_barcode_1 = new ALA_Device_SerialApi("/dev/ttyS3", 115200, 8, 0, 0, 1, 1);
    }

    public void init() {
        Observable.just(1)
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        while (true) {
                            barcode = DW_X1000_Dewo_GetValue(serial_tty_barcode, 13);
                            Log.d(TAG, "扫码结果:" + barcode);
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String value) throws Exception {

                    }
                });

        Observable.just(1)
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        while (true) {
                            barcode = DW_X1000_Dewo_GetValue1(serial_tty_barcode_1, 13);
                            Log.d(TAG, "扫码结果:" + barcode);
                            SystemClock.sleep(300);
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String value) throws Exception {

                    }
                });
    }

    private void openScanLight() {
        byte[] sendstr = {0x1A, 0x54, 0x0D};
//        byte[] sendstr = {0x16,0x54,0x0d};
        serial_tty_barcode.writeserial(sendstr, sendstr.length);
        serial_tty_barcode_1.writeserial(sendstr, sendstr.length);
    }

    private void closeScanLight() {
        byte[] sendstr1 = {0x1A, 0x55, 0x0D};
        serial_tty_barcode.writeserial(sendstr1, sendstr1.length);
        serial_tty_barcode_1.writeserial(sendstr1, sendstr1.length);
    }


    public void resetScan() {
        this.barcode = null;
    }

    public String scan(long timeout) {
        openScanLight();
        long time = System.currentTimeMillis();
        while (true) {
            if (!TextUtils.isEmpty(barcode)) {
                SystemClock.sleep(100);
                closeScanLight();
                return barcode;
            }
            if (timeout < System.currentTimeMillis() - time) {
                closeScanLight();
                break;
            }
            SystemClock.sleep(100);
        }
        return none;
    }


    public String DW_X1000_Dewo_GetValue1(ALA_Device_SerialApi api, int barcode_len) {
        boolean reset = false;
        String ret = "";
        int recv = 0, recv1 = 0;

        int readlen = barcode_len;
        byte recvbuf[] = new byte[barcode_len];
        byte recvbuf1[] = new byte[barcode_len];
        int len = barcode_len;

        int i = 0;

        while (recv < len) {
            recv1 = api.readserial(recvbuf, readlen);
            if (recv1 < 0)
                break;

            for (i = 0; i < recv1; i++) {
                // Log.i(TAG,"i = " + (recv+i));
                recvbuf1[recv + i] = recvbuf[i];
                if (recvbuf[i]<0){
                    reset = true;
                }
            }
            recv += recv1;
            // sb1.append(new String(recvbuf).substring(0,recv1));
            readlen -= recv1;
            //Log.i(TAG,"readlen = " + readlen + " recv1 ="+recv1+ "  recv ="+recv );

        }

        if (recv > 0) {
            String tmp = String.format("%d %d %d %d %d %d %d %d %d %d %d %d %d", recvbuf[0], recvbuf[1], recvbuf[2], recvbuf[3],
                    recvbuf[4], recvbuf[5], recvbuf[6], recvbuf[7], recvbuf[8], recvbuf[9], recvbuf[10],
                    recvbuf[11], recvbuf[12]);
            Log.i(TAG, " recv = " + recv + "  recvbuf =" + tmp);


            String tmp1 = String.format("%d %d %d %d %d %d %d %d %d %d %d %d %d", recvbuf1[0], recvbuf1[1], recvbuf1[2], recvbuf1[3],
                    recvbuf1[4], recvbuf1[5], recvbuf1[6], recvbuf1[7], recvbuf1[8], recvbuf1[9], recvbuf1[10],
                    recvbuf1[11], recvbuf1[12]);
            Log.i(TAG, " recv = " + recv + "  recvbuf1 =" + tmp1);

            StringBuilder temp2 = new StringBuilder();
            for (int j = 0; j < barcode_len; j++) {
                temp2.append(String.format("%d", recvbuf1[j]));
            }
            Log.i(TAG, " recv = " + recv + "  temp2 =" + temp2);

            ret = new String(recvbuf1);
        }

        Log.i(TAG, "ret = " + ret);
        if (reset){
            reset(api);
        }
        return ret;
    }


    public String DW_X1000_Dewo_GetValue(ALA_Device_SerialApi api, int barcode_len) {
        boolean reset = false;
        String ret = "";
        int recv = 0, recv1 = 0;

        int readlen = barcode_len;
        byte recvbuf[] = new byte[barcode_len];
        byte recvbuf1[] = new byte[barcode_len];
        int len = barcode_len;

        int i = 0;

        while (recv < len) {
            recv1 = api.readserial(recvbuf, readlen);
            if (recv1 < 0)
                break;

            for (i = 0; i < recv1; i++) {
                // Log.i(TAG,"i = " + (recv+i));
                recvbuf1[recv + i] = recvbuf[i];
                if (recvbuf[i]<0){
                    reset = true;
                }
            }
            recv += recv1;
            // sb1.append(new String(recvbuf).substring(0,recv1));
            readlen -= recv1;
            //Log.i(TAG,"readlen = " + readlen + " recv1 ="+recv1+ "  recv ="+recv );

        }

        if (recv > 0) {
            String tmp = String.format("%d %d %d %d %d %d %d %d %d %d %d %d %d", recvbuf[0], recvbuf[1], recvbuf[2], recvbuf[3],
                    recvbuf[4], recvbuf[5], recvbuf[6], recvbuf[7], recvbuf[8], recvbuf[9], recvbuf[10],
                    recvbuf[11], recvbuf[12]);
            Log.i(TAG, " recv = " + recv + "  recvbuf =" + tmp);


            String tmp1 = String.format("%d %d %d %d %d %d %d %d %d %d %d %d %d", recvbuf1[0], recvbuf1[1], recvbuf1[2], recvbuf1[3],
                    recvbuf1[4], recvbuf1[5], recvbuf1[6], recvbuf1[7], recvbuf1[8], recvbuf1[9], recvbuf1[10],
                    recvbuf1[11], recvbuf1[12]);
            Log.i(TAG, " recv = " + recv + "  recvbuf1 =" + tmp1);

            StringBuilder temp2 = new StringBuilder();
            for (int j = 0; j < barcode_len; j++) {
                temp2.append(String.format("%d", recvbuf1[j]));
            }
            Log.i(TAG, " recv = " + recv + "  temp2 =" + temp2);

            ret = new String(recvbuf1);
        }

        Log.i(TAG, "ret = " + ret);
        if (reset){
            reset(api);
        }
        return ret;
    }

    public void reset(ALA_Device_SerialApi api) {
        Log.d(TAG,"重置设备 start");
        byte[] reset = {
                0x1A,
                0x4B,
                0x0D,
                0x38,
                0x30,
                0x30,
                0x30,
                0x30,
                0x36,
                0x3B,
                0x37,
                0x35,
                0x38,
                0x30,
                0x30,
                0x34,
                0x31,
                0x3B,
                0x37,
                0x35,
                0x38,
                0x30,
                0x30,
                0x31,
                0x31,
                0x3B,
                0x37,
                0x35,
                0x38,
                0x30,
                0x30,
                0x32,
                0x39,
                0x30,
                0x30,
                0x3B,
                0x37,
                0x35,
                0x38,
                0x30,
                0x30,
                0x33,
                0x33,
                0x35,
                0x3B,
                0x38,
                0x35,
                0x39,
                0x30,
                0x30,
                0x37,
                0x31,
                0x39,
                0x39,
                0x2E
        };
        serial_tty_barcode.writeserial(reset, reset.length);
        SystemClock.sleep(100);
        byte[] qrset = {
                0x1A,
                0x4B,
                0x0D,
                0x39,
                0x32,
                0x34,
                0x30,
                0x30,
                0x31,
                0x30,
                0x3B,
                0x39,
                0x32,
                0x35,
                0x30,
                0x30,
                0x31,
                0x30,
                0x3B,
                0x39,
                0x32,
                0x38,
                0x30,
                0x30,
                0x31,
                0x30,
                0x3B,
                0x39,
                0x32,
                0x39,
                0x30,
                0x30,
                0x31,
                0x30,
                0x3B,
                0x39,
                0x33,
                0x30,
                0x30,
                0x30,
                0x31,
                0x30,
                0x3B,
                0x39,
                0x33,
                0x31,
                0x30,
                0x30,
                0x31,
                0x30,
                0x3B,
                0x39,
                0x33,
                0x32,
                0x30,
                0x30,
                0x31,
                0x30,
                0x2E
        };
        serial_tty_barcode.writeserial(qrset, qrset.length);
        Log.d(TAG,"重置设备 end");
        SystemClock.sleep(100);
    }


//
//    private String DW_X1000_Dewo_GetValue(int barcode_len) {
//
//        String ret = "";
//        int recv = 0, recv1 = 0;
//
//        int readlen = barcode_len;
//        byte recvbuf[] = new byte[barcode_len];
//        byte recvbuf1[] = new byte[barcode_len];
//        int len = barcode_len;
//
//        int i = 0;
//        Log.d(TAG,"DW_X1000_Dewo_GetValue 1");
//        while (recv < len) {
//
//            Log.d(TAG,"DW_X1000_Dewo_GetValue 2");
//            recv1 = serial_tty_barcode.readserial(recvbuf, readlen);
//
//            Log.d(TAG,"DW_X1000_Dewo_GetValue 2 readserial");
//            if (recv1 < 0)
//                break;
//
//            for (i = 0; i < recv1; i++) {
//                // Log.i(TAG,"i = " + (recv+i));
//                recvbuf1[recv + i] = recvbuf[i];
//            }
//            recv += recv1;
//            // sb1.append(new String(recvbuf).substring(0,recv1));
//            readlen -= recv1;
//            //Log.i(TAG,"readlen = " + readlen + " recv1 ="+recv1+ "  recv ="+recv );
//            Log.d(TAG,"DW_X1000_Dewo_GetValue 3");
//        }
//        if (recv > 0) {
//            String tmp = String.format("%d %d %d %d %d %d %d %d %d %d %d %d ", recvbuf[0], recvbuf[1], recvbuf[2], recvbuf[3],
//                    recvbuf[4], recvbuf[5], recvbuf[6], recvbuf[7], recvbuf[8], recvbuf[9], recvbuf[10],
//                    recvbuf[11]);
////            Log.i(TAG, " recv = " + recv + "  recvbuf =" + tmp);
//
//            ret = new String(recvbuf1);
//        }
////        Log.i(TAG, "ret = " + ret);
//        Log.d(TAG,"DW_X1000_Dewo_GetValue 4");
//        return ret;
//    }


}
