package algo.test.multiply;

import java.util.Random;

public class RecursiveMatrixMultiplication {

	int[][] matriksA;
	int[][] matriksB;
	int[][] matriksC;

	int awalBaris;
	int awalKolom;
	int akhirBaris;
	int akhirKolom;

	int dimensiTengah;

	public RecursiveMatrixMultiplication(int[][] matriksA, int[][] matriksB,
			int[][] matriksC, int dimensiTengah, int awalBaris, int akhirBaris,
			int awalKolom, int akhirKolom) {

		this.matriksA = matriksA;
		this.matriksB = matriksB;
		this.matriksC = matriksC;

		this.awalBaris = awalBaris;
		this.awalKolom = awalKolom;
		this.akhirBaris = akhirBaris;
		this.akhirKolom = akhirKolom;

		this.dimensiTengah = dimensiTengah;
	}
	
	public void multiply(int barisA, int kolomB) {
		int sum = 0;
		for (int k = 0; k < dimensiTengah; k++) {
			sum += matriksA[barisA][k] * matriksB[k][kolomB];
		}
		matriksC[barisA][kolomB] = sum;
	}
	
	
	private void computeDirectly(int awalBaris, int akhirBaris, int awalKolom,
			int akhirKolom) {
		for (int i = awalBaris; i < akhirBaris; i++) {
			for (int j = awalKolom; j < akhirKolom; j++) {
				multiply(i, j);
			}
		}
	}
	
	
	 public void invokeAll(RecursiveMatrixMultiplication...matrixMultiplications ){
		for(RecursiveMatrixMultiplication m: matrixMultiplications){
			m.compute();
		}
	}
	 
	 public void invoke(){
		 invokeAll(this); 
	 }
	
	public void compute() {
			
			int limit = 10;
			
			int limitKolom = (int) (Math.floor( ((awalKolom  - akhirKolom) / 2)));
			
			int limitBaris = (int) (Math.floor( ((akhirBaris - awalBaris) / 2)));
			
			int halfBaris = awalBaris + limitBaris; 

			int halfKolom = awalKolom + limitKolom;
			

			if (limitKolom <= limit && limitBaris <= limit) {

				computeDirectly(awalBaris, akhirBaris, awalKolom, akhirKolom);

				return;

			} else if (limitBaris <= limit && limitKolom > limit) { // split over column

				RecursiveMatrixMultiplication p2 = new RecursiveMatrixMultiplication(matriksA, matriksB,
						matriksC, dimensiTengah, awalBaris, akhirBaris, awalKolom,
						halfKolom);

				RecursiveMatrixMultiplication p4 = new RecursiveMatrixMultiplication(matriksA, matriksB,
						matriksC, dimensiTengah, awalBaris, akhirBaris, halfKolom,		
						akhirKolom);
				
				invokeAll(p2, p4);

			} else if (limitKolom <= limit && limitBaris > limit) { // split over
																	// row 
				
				RecursiveMatrixMultiplication p1 = new RecursiveMatrixMultiplication(
						matriksA, matriksB,matriksC, 
						dimensiTengah,
						awalBaris, halfBaris, 
						awalKolom, akhirKolom);

				RecursiveMatrixMultiplication p3 = new RecursiveMatrixMultiplication(
						matriksA, matriksB, matriksC, 
						dimensiTengah, 
						halfBaris, akhirBaris,
						awalKolom, akhirKolom);

				invokeAll(p1, p3);

			} else if (limitKolom > limit && limitKolom > limit) { // split to 4 quadran

				RecursiveMatrixMultiplication p1 = new RecursiveMatrixMultiplication(
						matriksA, matriksB, matriksC, dimensiTengah,
						awalBaris, halfBaris,
						awalKolom, halfKolom); // kuadran 1

				RecursiveMatrixMultiplication p2 = new RecursiveMatrixMultiplication(
						matriksA, matriksB,matriksC, dimensiTengah, 
						halfBaris, akhirBaris,
						awalKolom, halfKolom); // kuadran 2

				RecursiveMatrixMultiplication p3 = new RecursiveMatrixMultiplication(
						matriksA, matriksB, matriksC , dimensiTengah,
						awalBaris, halfBaris,
						halfKolom, akhirKolom); // kuadran 3

				RecursiveMatrixMultiplication p4 = new RecursiveMatrixMultiplication(
						matriksA, matriksB, matriksC, dimensiTengah, 
						halfBaris, akhirBaris,
						halfKolom, akhirKolom); // kuadran 4

				invokeAll(p1, p2, p3, p4);
				
			}

		}
	
	
	/*
	 * bandingkan matriksA dan matriksB.. 
	 * jika semua elemennya sama, maka kembalikan matriksC yang isinya semua 1... 
	 * jika ada elemen yang beda isi elemen matriksC dengan 0
	 */
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
	
	
	/*
	 * sebagai pembanding untuk mengetahui operasi berjalan dengan benar.. 
	 * yakni lakukan perkalian secara Langsung..  tanpa rekursive... 
	 */
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
	
	/*
	 * generate random matriks
	 */
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
	
	
	/*
	 *	pretty print matrix 
	 */
	public static void printMatriks(int [][] matriks) {
		for (int i = 0; i < matriks.length; i++) {
			for (int j = 0; j < matriks[0].length; j++) {
				System.out.print(matriks[i][j] + "|");
			}
			System.out.print("\n");
		}
		System.out.print("\n"); 
	}
	
	
	public int[][] getResult() {
		return matriksC;
	}

	
	public static void main(String[] args){
		
		
		int[][] matriksA = generateMatriks(300, 200); 
		int[][] matriksB = generateMatriks(200, 500); 
		
		long now = System.nanoTime(); 
		
		int[][] matriksResultA = multiplyDirectly(matriksA, matriksB); 
		
		now = (System.nanoTime() - now) / 1000000; 
		
		long  now1 = System.nanoTime(); 
		
		int[][] matriksC = new int[matriksA.length][matriksB[0].length];  
		
		RecursiveMatrixMultiplication parallel = new RecursiveMatrixMultiplication(matriksA, matriksB, matriksC, 
				matriksB.length, 0 , matriksC.length, 0, matriksC[0].length); 
		
		parallel.invoke();
		
		int[][] matriksResultB = parallel.getResult(); 
		
		now1 = (System.nanoTime() - now1) / 1000000; 
		
		int[][] hasilCompare  = compareMatriks(matriksResultA, matriksResultB);   
		
		printMatriks(hasilCompare);
		
		System.out.println(now); 
		System.out.println(now1);
		System.out.println("FINISH"); 
		
	}


}
