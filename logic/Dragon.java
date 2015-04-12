package logic;

import java.io.Serializable;

/**
 * Manages the information regarding the dragons.
 */
public class Dragon implements Serializable {

	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = -7248944592308816164L;

	/**
	 * The dragon's position. When the dragon dies, this sets to -1.
	 */
	private int posicao = 31;

	/**
	 * Whether the dragon is awake or not.
	 */
	private boolean acordado = true;

	/**
	 * Stores information regarding the dragon's behaviour.
	 */
	public static enum Mode {
		/** The dragons never move */
		STATIC,
		/** The dragons always move */ MOVABLE,
		/** The dragons move but sometimes fall asleep */
		SLEEPING
	};

	/**
	 * The constructor.
	 * 
	 * @param pos The dragon's position.
	 */
	public Dragon(int pos) {
		posicao = pos;
	}

	/**
	 * Default constructor. Sets the dragon's position to 31 when used (its
	 * position on the static maze).
	 */
	public Dragon() {
	}

	/**
	 * Get @see posicao
	 * 
	 * @return {@link posicao}
	 */
	public int getPosicao() {
		return posicao;
	}

	/**
	 * Set @see posicao
	 * @param posicao {@link posicao}
	 */
	public void setPosicao(int posicao) {
		this.posicao = posicao;
	}

	/**
	 * Get @see acordado
	 * @return {@link acordado}
	 */
	public boolean isAcordado() {
		return acordado;
	}

	/**
	 * Set @see acordado
	 * @param acordado {@link acordado}
	 */
	public void setAcordado(boolean acordado) {
		this.acordado = acordado;
	}

}