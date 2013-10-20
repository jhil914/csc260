package edu.smith.csc.csc260.hjin;


public class testing {
	
	
	
	
	public static void main(String args){
		float count = (float) (Math.random()*2);
		
	System.out.println(count);
		if(Math.abs(count) <=1){
			System.out.println("t");
			test2 t = new test2();
		}
		else{
			System.out.println("s");
			snake s = new snake();
			
		}
	}
}
