package algo.test.multiply;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.Buffer;

public class Writer {

	static BufferedWriter buff;
	
	public static void openWriter(){
			try {
				buff = new BufferedWriter(new FileWriter(new File("D:/out.txt")));
			} catch (IOException e) {
				e.printStackTrace();
				closeWriter();
			}
			
	}
	
	
	public static BufferedWriter getBuff(){
		return buff; 
	}
	
	public static void closeWriter(){
		try {
			buff.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			buff.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
