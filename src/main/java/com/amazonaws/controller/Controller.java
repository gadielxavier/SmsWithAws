package com.amazonaws.controller;

import java.io.File;
import java.io.IOException;

import com.amazonaws.samples.LerExcel;


public class Controller {
	
	private File file;
	
	public Controller(File file, String sms) throws IOException {
		callModel(file, sms);
	}
	
	public void callModel(File file, String sms) throws IOException {
		LerExcel.enviarSms(file, sms);
	}

}
