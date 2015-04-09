package logic;
public class Dragon{
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