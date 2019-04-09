package com.codeayit.smokerobotlibala;

import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import com.ala.ALAdevicestatusrw.ALA_Device_SerialApi;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lny on 2018/5/20.
 */

public class Scanner {


    public static final String none = "none";
    public static String TAG = "ScannerManager";

    private String path;
    private String barcode;
    private ALA_Device_SerialApi serial_tty_barcode = null;

    public Scanner(final String path) {
        this.path = path;

        Observable.just(1)
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        while (true){
                            Log.d(TAG,"扫码结果:"+barcode);
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


    public void init(){
        serial_tty_barcode = new ALA_Device_SerialApi(path, 115200, 8, 0, 0, 1, 1);
    }

    private void openScanLight() {
        byte[] sendstr = {0x1A, 0x54, 0x0D};
//        byte[] sendstr = {0x16,0x54,0x0d};
        serial_tty_barcode.writeserial(sendstr, sendstr.length);
    }

    private void closeScanLight() {
        byte[] sendstr1 = {0x1A, 0x55, 0x0D};
        serial_tty_barcode.writeserial(sendstr1, sendstr1.length);
    }


    public void resetScan(){
        this.barcode = null;
    }

    public String scan(long timeout){
        openScanLight();
        long time = System.currentTimeMillis();
        while (true){
            if (!TextUtils.isEmpty(barcode)){
                closeScanLight();
                return barcode;
            }
            if (timeout<System.currentTimeMillis()-time){
                closeScanLight();
                break;
            }
            SystemClock.sleep(300);
        }
        return none;
    }

    public String  DW_X1000_Dewo_GetValue (ALA_Device_SerialApi api, int barcode_len) {

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
            }
            recv += recv1;
            // sb1.append(new String(recvbuf).substring(0,recv1));
            readlen -= recv1;
            //Log.i(TAG,"readlen = " + readlen + " recv1 ="+recv1+ "  recv ="+recv );
        }

        if (recv > 0) {
            String tmp = String.format("%d %d %d %d %d %d %d %d %d %d %d %d ", recvbuf[0], recvbuf[1], recvbuf[2], recvbuf[3],
                    recvbuf[4], recvbuf[5], recvbuf[6], recvbuf[7], recvbuf[8], recvbuf[9], recvbuf[10],
                    recvbuf[11]);
            Log.i(TAG, " recv = " + recv + "  recvbuf =" + tmp);

            ret = new String(recvbuf1);
        }

        Log.i(TAG, "ret = " + ret);

        return ret;
    }


}
