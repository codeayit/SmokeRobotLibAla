//package com.haitiand.modbuslib.manager;
//
//import android.os.SystemClock;
//import android.text.TextUtils;
//import android.util.Log;
//
//import com.ala.ALAdevicestatusrw.ALA_Device_SerialApi;
//
//import io.reactivex.Observable;
//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.functions.Consumer;
//import io.reactivex.functions.Function;
//import io.reactivex.schedulers.Schedulers;
//
///**
// * Created by lny on 2018/4/24.
// */
//
//public class ScannerManager2 {
//
//    public static final String none = "none";
//
//    public static String TAG = "ScannerManager";
//
//    private Scanner scanner1;
//    private Scanner scanner2;
//
//
//    private static ScannerManager2 instance;
//
//    public static ScannerManager2 getInstance(){
//        if (instance==null){
//            synchronized (ScannerManager2.class){
//                if (instance==null){
//                    instance = new ScannerManager2();
//                }
//            }
//        }
//        return instance;
//    }
//    public ScannerManager2() {
//        scanner1 = new Scanner("/dev/ttyS2");
//        scanner2 = new Scanner("/dev/ttyS3");
//    }
//
//    public void init(){
//        Observable.just(1)
//                .map(new Function<Integer, String>() {
//                    @Override
//                    public String apply(Integer integer) throws Exception {
//                        while (true){
//
//                            Log.d(TAG,"扫码结果:"+barcode);
//                            SystemClock.sleep(300);
//                        }
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(String value) throws Exception {
//
//                    }
//                });
//
//        Observable.just(1)
//                .map(new Function<Integer, String>() {
//                    @Override
//                    public String apply(Integer integer) throws Exception {
//                        while (true){
//
//                            Log.d(TAG,"扫码结果:"+barcode);
//                            SystemClock.sleep(300);
//                        }
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(String value) throws Exception {
//
//                    }
//                });
//    }
//
//    private void openScanLight() {
//
//    }
//
//    private void closeScanLight() {
//
//    }
//
//
//    public void resetScan(){
//        this.barcode = null;
//    }
//
//    public String scan(long timeout){
//        openScanLight();
//        long time = System.currentTimeMillis();
//        while (true){
//            if (!TextUtils.isEmpty(barcode)){
//                closeScanLight();
//                return barcode;
//            }
//            if (timeout<System.currentTimeMillis()-time){
//                closeScanLight();
//                break;
//            }
//            SystemClock.sleep(300);
//        }
//        return none;
//    }
//
//}
