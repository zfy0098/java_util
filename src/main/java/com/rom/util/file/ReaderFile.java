package com.rom.util.file;

import java.io.BufferedReader; 
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class ReaderFile {

	public void ReaderTxt(String path) throws IOException{
		File file = new File(path);
		InputStreamReader reader = new InputStreamReader(new FileInputStream(file),"utf-8");
		BufferedReader bufferedReader = new BufferedReader(reader);
		String textlint = null;
		while((textlint=bufferedReader.readLine())!=null){
			System.out.println(textlint);
		}
		reader.close();
	}
	
	public static void main(String[] args) {
		ReaderFile readerFile = new ReaderFile();
		try {
			readerFile.ReaderTxt("F:/ceshi.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
