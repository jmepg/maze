package logic;

import java.io.Serializable;

/**
 * Manages the information regarding the darts.
 */
public class Dart implements Serializable{
	
	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = -6878183323940828823L;
	
	/**
	 * Dart position.
	 */
	private int posicao=0;
	
	/**
	 * Class constructor.
	 * @param pos The dart's position.
	 */
	public Dart(int pos){
		posicao=pos;
	}
	
	/**
	 * Get @see posicao
	 * @return {@link posicao}
	 */
	public int getPosicao(){
		return posicao;
	}
	
	/**
	 * Set @see posicao
	 * @param pos {@link posicao}
	 */
	public void setPosicao(int pos){
		this.posicao=pos;
	}
}
