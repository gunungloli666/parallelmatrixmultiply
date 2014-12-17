package algo.test.multiply;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class CompareKaliMatrix {
	
	
	public static void main(String[] args){
		
		Writer.openWriter(); 
		
		int[][] matriksA = generateMatriks(20, 10); 
		int[][] matriksB = generateMatriks(10, 30); 
		
		long now = System.currentTimeMillis(); 
		
		int[][] matriksResultA = multiplyDirectly(matriksA, matriksB); 
		
		now = (System.currentTimeMillis() - now) / 1000; 
		
		long  now1 = System.currentTimeMillis(); 
		
		int[][] matriksC = new int[matriksA.length][matriksB[0].length];  
		
		ParallelMultiply parallel = new ParallelMultiply(matriksA, matriksB, matriksC, 
				matriksB.length, 0 , matriksC.length, 0, matriksC[0].length); 
//		
//		ForkJoinPool pool = new ForkJoinPool(); 
//		
		parallel.invoke();
		
//		pool.invoke(parallel); 
		
		now1 = (System.currentTimeMillis() - now1) / 1000; 

		int[][] matriksResultB = parallel.getResult(); 
		
		int[][] hasilCompare  = compareMatriks(matriksResultA, matriksResultB);   
		
		try {
			printMatriks(hasilCompare);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		try {
			printMatriks(matriksResultA);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		try {
			printMatriks(matriksResultB);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		System.out.println(now); 
		System.out.println(now1);
		System.out.println("FINISH"); 
		
		
		Writer.closeWriter();
	}
	
	
	
	public static int[][] multiplyDirectly(int [][] matriksA, int[][] matriksB){
		int result[][] = new int[matriksA.length][matriksB[0].length]; 
		for(int i= 0; i< matriksA.length ;i++){
			for(int j=0; j< matriksB[0].length ;j++){
				int sum  = 0; 
				for( int k = 0 ; k < matriksB.length ;k ++){
					sum += matriksA[i][k] * matriksB[k][j]; 
				}
				result[i][j] = sum; 
			}
		}
		return  result; 
	}
	
	
	public static int [][] compareMatriks(int [][] matriksA, int[][] matriksB){
		int  C[][] = new int [matriksA.length][matriksA[0].length]; 
		for( int i=0; i < matriksA.length ;i++){
			for(int j=0; j < matriksA[0].length ;j++){
				if(matriksA[i][j] == matriksB[i][j]){
					C[i][j] = 1;
				}else{
					C[i][j] = 0; 
				}
			}
		}
		return C; 
	}
	
	
	public static int [][] generateMatriks(int baris, int kolom){
		int  matriks[][] = new int[baris][kolom]; 
		Random rn = new Random(); 
		for(int i=0; i < matriks.length ; i++){
			for( int j=0; j < matriks[0].length ;j++){
				matriks[i][j] = rn.nextInt(100); 
			}
		}
		return matriks ;
	}
	
	
	public static void printMatriks(int [][] matriks) throws Exception{
		for (int i = 0; i < matriks.length; i++) {
			for (int j = 0; j < matriks[0].length; j++) {
				Writer.getBuff().write(matriks[i][j] + "|");
			}
			Writer.getBuff().write("\n");
		}
		Writer.getBuff().write("\n"); 
	}

}
