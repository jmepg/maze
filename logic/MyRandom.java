package logic;

import java.util.Random;

public class MyRandom {
	
	public boolean rand = true;
	public int[] v;
	public Random r = new Random();
	public int i = 0;
	
	public int nextInt(){
		if(rand)
			return r.nextInt(4);
		else
			return v[i++];
	}
	
	public MyRandom(int[] n){
		v = n;
		i = 0;
		rand = false;
	}
	
	public MyRandom(){
		int n[] = {0,1,2,3};
		v = n;
		i = 0;
		rand = true;
	}
	
}
