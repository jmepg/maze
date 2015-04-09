package logic;

import java.io.Serializable;

public class Dragon implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7248944592308816164L;
	int posicao = 31;	//-1 = dragao morto
	boolean acordado = true;
	public static enum Mode {STATIC,MOVABLE,SLEEPING};
	
	public Dragon(int pos){
		posicao = pos;
	}
	public Dragon(){
	}
	public int getPosicao() {
		return posicao;
	}
	public void setPosicao(int posicao) {
		this.posicao = posicao;
	}
	public boolean isAcordado() {
		return acordado;
	}
	public void setAcordado(boolean acordado) {
		this.acordado = acordado;
	}
	
}