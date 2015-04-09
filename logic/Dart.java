package logic;

import java.io.Serializable;

public class Dart implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6878183323940828823L;
	private int posicao=0;
	
	public Dart(int pos){
		posicao=pos;
	}
	
	public int getPosicao(){
		return posicao;
	}
	public void setPosicao(int pos){
		this.posicao=pos;
	}
}
