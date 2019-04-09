package com.ala.libmodbusapi;


public  class libmodbusapi implements  Define{

	public libmodbusapi() {
		// TODO Auto-generated constructor stub
	}
	public native  int  init_modbus(String name, int busid, int baud, int databit, int stopbit , int readtimeout, int writetiemout);
	//FC( 0x01)
	public native  int modbus_read_bits(int busid, int addr, int nb, byte dest[]);
	//FC( 0x02)
	public native  int modbus_read_input_bits(int busid, int addr, int nb, byte dest[]);
	//FC(0X03)
	public native  int modbus_read_registers(int busid, int addr, int nb, int dest[]);
	//FC(0x04)
	public native  int modbus_read_input_registers(int busid, int addr, int nb, int dest[]);
	//FC(0x05)
	public native  int modbus_write_bit(int busid, int coil_addr, int status);
	//FC(0x06)
	public native  int modbus_write_register(int busid,int reg_addr, int value);
	//FC(0x0F)
	public native  int modbus_write_bits(int busid,int addr, int nb, byte data[]);
	//FC(0x10)
	public native  int modbus_write_registers(int busid, int addr, int nb, int data[]);
	//FC(0x16)
	public native  int modbus_mask_write_register(int busid,int addr, int and_mask, int or_mask);
	
	//FC(0X17)
	public native  int modbus_write_and_read_registers(int busid,int write_addr, int write_nb,
	                                               int src[], int read_addr, int read_nb,
	                                               int dest[]);	
	static {
		System.loadLibrary("modbusalalib");

	}
	
	
	
}
