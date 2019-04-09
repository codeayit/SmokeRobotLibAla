package com.ala.libmodbusapi;


import java.util.ArrayList;

public interface Define {
		public  final int MODBUS_RBIT = 1;
		public  final int MODBUS_WBIT= 2;
		public  final int MODBUS_RREG= 3;
		public  final int MODBUS_WREG= 4;
		public  final int MODBUS_BARCODEGET= 5;
		public  final int MODBUS_RESET= 6;
		public  final int MODBUS_CHE= 7;

		public  final int MODBUS_RBIT_MSG = 1;
		public  final int MODBUS_RREG_MSG= 2;

		public final int arr43[] = {43};
		public final int arr42[] = {42};

		// 开门
		public final int opendoor44[] = {44};
		public final int opengoods46[] = {46};

		// 关门
		public final int closedoor44[] = {45};
		public final int closegoods46[] = {47};





	//action 传递参数的标识

	public final String TAKE_GOODS_STR = "action_code";
	public final  int TAKE_GOODS_INT = 102;
	public final String SENDDATA = "data";


	public final String TY_MSG_ACTION_MSG_INFO ="msg_info";
	public final String TY_MSG_ACTION_MSG_NO ="msg_id";
	public final String TY_MSG_ACTION_MSG_ERR ="msg_error";

	public  final  int TY_MSG_ACTION_MSG_NO_0  = 0;

	//public final  String  KXD_MSG_ACTION_MSG_ERR_0  ="设备操作正确";
	public final String TY_MSG_ACTION_MSG_ERR_0  ="device work ok .";
	public  final  int TY_MSG_ACTION_MSG_NO_1  = 1;
	//   public final  String  KXD_MSG_ACTION_MSG_ERR_1  ="设备连接错误";
	public final String TY_MSG_ACTION_MSG_ERR_1  ="device connect error";
	public  final  int TY_MSG_ACTION_MSG_NO_2  = 2;
	//    public final  String  KXD_MSG_ACTION_MSG_ERR_2  ="设备读写错误";
	public final String TY_MSG_ACTION_MSG_ERR_2  ="device rw error.";
	public  final  int TY_MSG_ACTION_MSG_NO_3  = 3;
	//   public final  String  KXD_MSG_ACTION_MSG_ERR_3  ="设备访问超时";
	public final String TY_MSG_ACTION_MSG_ERR_3  ="device rw timeout";

	public final int TY_MSG_ACTION_MSG_NO_4 = 4;
	public final String TY_MSG_ACTION_MSG_ERR_4 = "data error  .";

	public final int TY_MSG_ACTION_MSG_NO_5 = 5;
	public final String TY_MSG_ACTION_MSG_ERR_5 = "data alread in table  .";

	public final int TY_MSG_ACTION_MSG_NO_6 = 6;
	public final String TY_MSG_ACTION_MSG_ERR_6 = "operation   table error .";


	public final int TY_MSG_ACTION_MSG_NO_70 = 70;
	public final String TY_MSG_ACTION_MSG_ERR_70 = "save goods ,insert tytobaccocell ok .";

	public final int TY_MSG_ACTION_MSG_NO_701 = 701;
	public final String TY_MSG_ACTION_MSG_ERR_701 = "take goods update tytobaccocell ok .";

	public final int TY_MSG_ACTION_MSG_NO_702 = 702;
	public final String TY_MSG_ACTION_MSG_ERR_702 = "read & write modbus plc error .";

	public final int TY_MSG_ACTION_MSG_NO_703 = 703;
	public final String TY_MSG_ACTION_MSG_ERR_703 = "read modbus reg=299 byte 3 .";

	public final int TY_MSG_ACTION_MSG_NO_704 = 704;
	public final String TY_MSG_ACTION_MSG_ERR_704 = "read modbus reg=0 byte 4 .";




	public final int TY_MSG_ACTION_MSG_NO_71 = 71;
	public final String TY_MSG_ACTION_MSG_ERR_71 = "write modbus reg=0 .";

	public final int TY_MSG_ACTION_MSG_NO_72 = 72;
	public final String TY_MSG_ACTION_MSG_ERR_72 = "write modbus reg=99 .";

	public final int TY_MSG_ACTION_MSG_NO_73 = 73;
	public final String TY_MSG_ACTION_MSG_ERR_73 = "write modbus reg=199 .";


	public final int TY_MSG_ACTION_MSG_NO_74 = 74;
	public final String TY_MSG_ACTION_MSG_ERR_74 = "write modbus reg=299 .";

	public final int TY_MSG_ACTION_MSG_NO_75 = 75;
	public final String TY_MSG_ACTION_MSG_ERR_75 = "write modbus reg=299 .";


	public final int TY_MSG_ACTION_MSG_NO_8 = 8;
	public final String TY_MSG_ACTION_MSG_ERR_8 = "read barcode  .";

	public final int TY_MSG_ACTION_MSG_NO_9 = 9;
	public final String TY_MSG_ACTION_MSG_ERR_9 = "read barcode  .";

	//
	public final int TY_MSG_ACTION_MSG_NO_10 = 10;
	public final String TY_MSG_ACTION_MSG_ERR_10 = "open door     .";

	public final int TY_MSG_ACTION_MSG_NO_11 = 11;
	public final String TY_MSG_ACTION_MSG_ERR_11 = "set zero point .";

	public final int TY_MSG_ACTION_MSG_NO_12 = 12;
	public final String TY_MSG_ACTION_MSG_ERR_12 = "get machine tmp & dampness .";

	public final int TY_MSG_ACTION_MSG_NO_13 = 13;
	public final String TY_MSG_ACTION_MSG_ERR_13 = "get machine param .";

	public final int TY_MSG_ACTION_MSG_NO_14 = 14;
	public final String TY_MSG_ACTION_MSG_ERR_14 = "set machine param .";

	public final int TY_MSG_ACTION_MSG_NO_15 = 15;
	public final String TY_MSG_ACTION_MSG_ERR_15 = "start machine tmp or dampness .";

	public final String TAKE_ACTION = "com.haitiand.smokerobot.api";

	//接收身份证读消息的ACTION 名称
	public final String TTTC_ACTION="com.ala.modbuslibtest.TYTobaccoActivity";
	//public final String KXD_PINGAN_IDCARD_ACTION="ACTION_READ_IDCARD";
	//接收打印保单信息的ACTION 名称
	public final String KXD_PINGAN_PRINTINSURANCE_ACTION="com.ala.middlewareofcard.KXD_PinganInsuranceActivity";
	//public final String KXD_PINGAN_PRINTINSURANCE_ACTION="ACTION_PRINT_INSURANCE";
	ArrayList<String> errmsg = new ArrayList<String>();


}
