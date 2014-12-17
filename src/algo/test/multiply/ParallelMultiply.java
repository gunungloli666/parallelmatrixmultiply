package algo.test.multiply;

//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
import java.util.concurrent.RecursiveAction;

public class ParallelMultiply  extends RecursiveAction{


	int[][] matriksA;
	int[][] matriksB;
	int[][] matriksC;

	int panjangBarisA;
	int panjangKolomB;
	int panjangBarisB;
	int panjangKolomA;

	int halfBarisA;
	int halfKolomA;

	int haldBarisB;
	int halfKolomB;

	int currentBarisA;
	int currentKolomA;
	int currentBarisB;
	int currentKolomB;

	int currentBarisC;
	int currentKolomC;

	int awalBaris;
	int awalKolom;
	int akhirBaris;
	int akhirKolom;

	int dimensiTengah;


	public ParallelMultiply(int[][] matriksA, int[][] matriksB, int[][] matriksC, 
			int dimensiTengah, 
			int awalBaris, int akhirBaris, int awalKolom,
			int akhirKolom) {
		
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

	public int[][] getResult() {
		return matriksC;
	}

	public void computeDirectly(int awalBaris, int akhirBaris, int awalKolom,
			int akhirKolom) {
		for (int i = awalBaris; i < akhirBaris; i++) {
			for (int j = awalKolom; j < akhirKolom; j++) {
				multiply(i, j);
			}
		}
	}
	
	
	private  void invoke(ParallelMultiply... p1 ){
		for(ParallelMultiply p : p1){
			p.compute();
		}
	}
	
	/*public void invoke(){
		System.out.println("FISRT INVOKE"); 
		invoke(this); 
	}*/
	
	@Override
	protected void compute() {
		
		int limit = 100;
		
		int limitKolom = (int) (Math.floor( ((awalKolom  - akhirKolom) / 2)));
		
		int limitBaris = (int) (Math.floor( ((akhirBaris - awalBaris) / 2)));
		
		int halfBaris = awalBaris + limitBaris; 

		int halfKolom = awalKolom + limitKolom;
		

		if (limitKolom <= limit && limitBaris <= limit) {

			computeDirectly(awalBaris, akhirBaris, awalKolom, akhirKolom);

			return;

		} else if (limitBaris <= limit && limitKolom > limit) { // split over row

			ParallelMultiply p2 = new ParallelMultiply(matriksA, matriksB,
					matriksC, dimensiTengah, awalBaris, akhirBaris, awalKolom,
					halfKolom);

			ParallelMultiply p4 = new ParallelMultiply(matriksA, matriksB,
					matriksC, dimensiTengah, awalBaris, akhirBaris, halfKolom,		
					akhirKolom);
			
			invokeAll(p2, p4);
			
//			invoke(p2,p4);


		} else if (limitKolom <= limit && limitBaris > limit) { // split over
																// baris
			
			ParallelMultiply p1 = new ParallelMultiply(
					matriksA, matriksB,matriksC, 
					dimensiTengah,
					awalBaris, halfBaris, 
					awalKolom, akhirKolom);

			ParallelMultiply p3 = new ParallelMultiply(
					matriksA, matriksB, matriksC, 
					dimensiTengah, 
					halfBaris, akhirBaris,
					awalKolom, akhirKolom);

			invokeAll(p1, p3);
			
//			invoke(p1,p3); 

		} else if (halfKolom > limit && halfBaris > limit) {

			ParallelMultiply p1 = new ParallelMultiply(
					matriksA, matriksB, matriksC, dimensiTengah,
					awalBaris, halfBaris,
					awalKolom, halfKolom); // kuadran 1

			ParallelMultiply p2 = new ParallelMultiply(
					matriksA, matriksB,matriksC, dimensiTengah, 
					halfBaris, akhirBaris,
					awalKolom, halfKolom); // kuadran 2

			ParallelMultiply p3 = new ParallelMultiply(
					matriksA, matriksB, matriksC , dimensiTengah,
					awalBaris, halfBaris,
					halfKolom, akhirKolom); // kuadran 3

			ParallelMultiply p4 = new ParallelMultiply(
					matriksA, matriksB, matriksC, dimensiTengah, 
					halfBaris, akhirBaris,
					halfKolom, akhirKolom); // kuadran 4

			invokeAll(p1, p2, p3, p4);
			
//			invoke(p1,p2,p3,p4);

		}

	}

}
