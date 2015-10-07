package com.leo.test.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class WriteFile {
	public static void main(String[] args) throws IOException {
		BufferedReader bf=new BufferedReader(new FileReader("src\\main\\resources\\keywords.txt"));
		String line=null;
		while((line=bf.readLine())!=null){
			System.out.println(line);
		}
		
		BufferedWriter bw=new BufferedWriter(new  FileWriter("src\\main\\resources\\keywords.txt",true));
		
		bw.write("Leo");

		bw.newLine();
		bw.flush();
		bw.close();
		bf.close();
	}
}