package logic;
public class Hero {
	private int posicao = 11;
	private boolean armado = false;
	private boolean dardo = false;
	private boolean escudo = false;
	
	public void resetEquipment(){
		armado = false;
		dardo = false;
		escudo = false;
	}

    public int getPosicao() {
        return posicao;
    }

    public boolean isArmado() {
        return armado;
    }

    public boolean isDardo() {
        return dardo;
    }

    public boolean isEscudo() {
        return escudo;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

    public void setArmado(boolean armado) {
        this.armado = armado;
    }

    public void setDardo(boolean dardo) {
        this.dardo = dardo;
    }

    public void setEscudo(boolean escudo) {
        this.escudo = escudo;
    }
}