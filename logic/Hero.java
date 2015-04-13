package logic;

import java.io.Serializable;

/**
 * Manages the information regarding the hero.
 */
public class Hero implements Serializable {
	
	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 4073426483821224846L;
	
	/**
	 * The hero's position.
	 */
	private int posicao = 11;
	
	/**
	 * Whether the hero is armed or not.
	 */
	private boolean armado = false;
	/**
	 * The hero's direction
	 */
	private char direction;
	/**
	 * Whether the hero has darts or not.
	 */

	private boolean dardo = false;
	
	/**
	 * Whether the hero has a shield or not.
	 */
	private boolean escudo = false;
	
	/**
	 * Resets the hero's equipment.
	 */
	public void resetEquipment(){
		armado = false;
		dardo = false;
		escudo = false;
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
	 * Get @see armado
	 * 
	 * @return {@link armado}
	 */
    public boolean isArmado() {
        return armado;
    }

    /**
	 * Get @see dardo
	 * 
	 * @return {@link dardo}
	 */
    public boolean isDardo() {
        return dardo;
    }

    /**
	 * Get @see escudo
	 * 
	 * @return {@link escudo}
	 */
    public boolean isEscudo() {
        return escudo;
    }

    /**
	 * Set @see posicao
	 * 
	 * @param posicao {@link posicao}
	 */
    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

    /**
	 * Set @see armado
	 * 
	 * @param armado {@link armado}
	 */
    public void setArmado(boolean armado) {
        this.armado = armado;
    }

    /**
	 * Set @see dardo
	 * 
	 * @param dardo {@link dardo}
	 */
    public void setDardo(boolean dardo) {
        this.dardo = dardo;
    }

    /**
	 * Set @see escudo
	 * 
	 * @param escudo {@link escudo}
	 */
    public void setEscudo(boolean escudo) {
        this.escudo = escudo;
    }
    /**
   	 * Get @see direction
   	 * 
   	 * @param  {@link direction}
   	 */
    public char getDirection(){
    	return direction;
    }

    /**
	 * Set @see direction
	 * 
	 * @param direction {@link direction}
	 */
    public void setDirection(char dir){
    	this.direction=dir;
    }
    
}