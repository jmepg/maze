package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cli.Cli;
import test.MyTest;

public class GameEngine {

    public List<Dragon> dragons = new ArrayList<Dragon>();
    public List<Dart> darts = new ArrayList<Dart>();
    public Hero h1 = new Hero();
    public Maze board;
    public int posEspada = 81;
    public int posEscudo= -1;
    public Cli cli = new Cli();
    public MyTest test = new MyTest();
    public int dragonMode;
    public int ambiente; //0=cli 1=test

    public GameEngine(int ambiente){
        this.ambiente = ambiente;
    }

    public GameEngine(){
        ambiente = 0;
    }

    public static void main(String[] args) {
        GameEngine g = new GameEngine();
        g.initializeGame();
        g.jogar();
    }

    /*
     * @brief Ciclo principal do jogo.
     */
    public void jogar() {
        /* Esta funcao vai ter de ser alterada de forma a ir para o cli, vai ser preciso fazer um jogar() novo para
        test e gui. Os ciclos dos dragoes vao ter de passar para outra funcao na game engine, e no main vamos ter de
        ter um if que decida qual dos jogar() chamar consoante estarmos num ambiente de texto, grafico ou de teste.
         */

        while (true) {
            // System.out.println("h1.posicao: " + h1.posicao);
            for(int i=0; i<dragons.size(); i++){
                if (dragons.get(i).posicao != -1) {
                    if (combate())
                        return;
                }
            }
            placeEntities();
            if(ambiente == 0)
                cli.printMaze(board.getDados());
            deleteEntities();
            if (testWinCondition()) {
                if(ambiente == 0)
                    cli.estadoFinal(0);
                if(ambiente == 1)
                    test.estadoFinal(0);

                return;
            }
            // System.out.println(h1.posicao + " " + board.saida);
            if(ambiente == 0)
                moveHeroi(cli.askForDirection());
            for(int i=0; i<dragons.size(); i++){
                if (dragons.get(i).posicao != -1)
                    moveDragao();
                if (ambiente == 0)
                    Cli.clearConsole();
            }
        }
    }

    /*
     * @brief Inicializa as variaveis necessarias ao bom funcionamento do jogo.
     */
    public void initializeGame() {
        MazeBuilder mb = new MazeBuilder();

        if(ambiente == 0)
            mb.setMazeType(cli.askForType());
        if(ambiente == 1)
            mb.setMazeType(0);

        if (mb.opcao == 1)
            mb.setMazeDim(cli.askForSize());

        board = mb.getMaze();
        board.gera();

        if(ambiente == 0 && mb.opcao == 1)
            dragonMode = cli.askForMode();

        if(ambiente == 1)
            dragonMode = 1;

        if(ambiente == 0 && mb.opcao == 1)
            generateDragons(cli.askForDragons());
        else if(mb.opcao == 0)
            dragons.add(new Dragon());

        /*if(ambiente == 1)
            generateDragons(1);*/

        if(ambiente == 0 && mb.opcao == 1)
            generateDarts();



        Random r = new Random();
        Random r2 = new Random();
        int n;

        if (mb.opcao == 1) {
            do {
                n = r.nextInt((board.dimension * board.dimension) - 1);
            } while (board.checkTile(n) == 'X');

            posEspada = n;

            do {
                n = r2.nextInt((board.dimension * board.dimension) - 1);
            } while (board.checkTile(n) == 'X');

            posEscudo = n;

            do {
                n = r.nextInt(board.dimension * board.dimension);
            } while (board.checkTile(n) == 'X'|| n == posEspada);
            h1.posicao = n;
        }
    }

    /*
     * @brief Coloca as entidades do jogo no mapa.
     */
    public void placeEntities() {
    	if(board.checkTile(board.getExit())==' ')
    		 board.changeBoard(board.getExit(), 'S');
    		
        if (h1.posicao == posEspada) {
            h1.armado = true;
            posEspada = -1;
        }

        if (h1.posicao == posEscudo) {
            h1.escudo= true;
            posEscudo = -1;
        }

        for(int j=0;j<darts.size();j++){
            if(h1.posicao == darts.get(j).posicao){
                h1.dardo=true;
                darts.get(j).posicao = -1;
            }
        }
        if (h1.armado)
            board.changeBoard(h1.posicao, 'A');
        else
            board.changeBoard(h1.posicao, 'H');

		/* Este conjunto de condicoes pode ser optimizado */
        for(int i=0;i<dragons.size();i++){

            if (posEspada != -1)
                if (dragons.get(i).posicao == posEspada)
                    board.changeBoard(posEspada, 'F');
                else
                    board.changeBoard(posEspada, 'E');

            if (posEscudo != -1)
                if (dragons.get(i).posicao == posEspada)
                    board.changeBoard(posEscudo, 'F');
                else
                    board.changeBoard(posEscudo, 'P');

            /* Oh Estrada, e se nao houver dardos como nao ha no estatico? Rip dragoes xD */
            /*Nota, fazer isto de uma forma menos trolha */
            if(darts.size() == 0)
                for(int k = 0; k < dragons.size();k++){
                	 if (dragons.get(k).posicao != -1)
                		 board.changeBoard(dragons.get(k).posicao, 'D');
                }

            for(int j=0;j<darts.size();j++){

                if (dragons.get(i).posicao != -1 && dragons.get(i).posicao != posEspada && dragons.get(i).acordado
                        && dragons.get(i).posicao != posEscudo && dragons.get(i).posicao != darts.get(j).posicao)
                    board.changeBoard(dragons.get(i).posicao, 'D');

                if (dragons.get(i).posicao != -1 && dragons.get(i).posicao != posEspada && !dragons.get(i).acordado
                        && dragons.get(i).posicao != posEscudo && dragons.get(i).posicao != darts.get(j).posicao)
                    board.changeBoard(dragons.get(i).posicao, 'd');


                if(darts.get(j).posicao != -1){
                    if (dragons.get(i).posicao == darts.get(j).posicao)
                        board.changeBoard(darts.get(j).posicao, 'F');
                    else
                        board.changeBoard(darts.get(j).posicao, 'T');
                }
            }
        }

    }

	/*
	 * @brief Remove as entidades do jogo do mapa, para serem repostas depois
	 * nas novas posicoes. NOTA: E possivel fazer isto de forma mais eficaz.
	 */

    public void deleteEntities() {
        board.changeBoard(h1.posicao, ' ');
        for(int i=0;i<dragons.size();i++)
            if (dragons.get(i).posicao != -1)
                board.changeBoard(dragons.get(i).posicao, ' ');
        if (posEspada != -1)
            board.changeBoard(posEspada, ' ');
        if (posEscudo != -1)
            board.changeBoard(posEscudo, ' ');
        for(int i=0;i<darts.size();i++)
            if (darts.get(i).posicao != -1)
                board.changeBoard(darts.get(i).posicao, ' ');
    }

    /*
     * @brief Testa se a posicao dada e transponivel ou nao.
     *
     * @param pos posicao a testar.
     */
    public boolean canMove(int pos) {
        for(int i=0; i<dragons.size(); i++){
         //   if(!dragons.get(i).acordado){
                if(dragons.get(i).posicao==pos && !h1.armado)
                    return false;
          //  }
        }
        return (board.checkTile(pos) != 'X');
    }

    /*
     * @brief Move o heroi de posicao.
     *
     * @param direcao Direcao para onde ele se vai mover. (W/A/S/D)
     */
    public int moveHeroi(char direcao) {
		/*
		 * W -> -10 posicoes no array S -> +10 posicoes no array A -> -1 posicao
		 * no array D -> +1 posicao no array
		 */
        switch (direcao) {
            case 'w':
                if (canMove(h1.posicao - board.dimension))
                    h1.posicao -= board.dimension;
                break;
            case 's':
                if (canMove(h1.posicao + board.dimension))
                    h1.posicao += board.dimension;
                break;
            case 'a':
                if (canMove(h1.posicao - 1))
                    h1.posicao -= 1;
                break;
            case 'd':
                if (canMove(h1.posicao + 1))
                    h1.posicao += 1;
                break;
            default:
                break;
        }
        //throwDarts(direcao);
        Darts(direcao);
        return 0;
    }
	
	/*
	 * @brief Sorteia se o dragao fica acordado ou adormecido.
	 * 
	 */

    public void isAwake(){
        Random r = new Random();
        int n;
        for(int i=0; i<dragons.size(); i++){
            n  = r.nextInt(2)+1;
            if(n==1)
                dragons.get(i).acordado=true;
            else
                dragons.get(i).acordado=false;
        }
    }

    /*
     * @brief Move o dragao de posicao.
     *
     * @param direcao Direcao para onde ele se vai mover. (W/A/S/D)
     */
    public void moveDragao(int move){
    	if(ambiente==0) return;
    
    	switch(move){
    	case 1: 
    		if(canMove(dragons.get(0).getPosicao()-10))
    			dragons.get(0).setPosicao(dragons.get(0).getPosicao()-10);
    		break;
    	case 2:
    		if(canMove(dragons.get(0).getPosicao()+1))
    			dragons.get(0).setPosicao(dragons.get(0).getPosicao()+1);
    		break;
    	case 3:
    		if(canMove(dragons.get(0).getPosicao()+10))
    			dragons.get(0).setPosicao(dragons.get(0).getPosicao()+10);
    		break;
    	case 4:
    		if(canMove(dragons.get(0).getPosicao()-1))
    			dragons.get(0).setPosicao(dragons.get(0).getPosicao()-1);
    		break;
    	default:
    		break;    	
    	}
    }
    
    
    
    public void moveDragao() {
        if(dragonMode == 1)
            return;

        if(dragonMode == 3)
            isAwake();

        for(int i=0; i<dragons.size();i++){
            Random r = new Random();

			/* 1 - cima 2 - baixo 3-esquerda 4- direita */
            int n, t;
            int ret = 1;

            if(!dragons.get(i).acordado) return;
            do {
                n = r.nextInt(5) + 1;
                if (n == 5)
                    return;
                if (n <= 2) {
                    t = (board.dimension * (n * 2 - 3));// Calculo feito para
                    // minimizar o
                    // numero de ifs feitos pela
                    // funcao
                    if (canMove(dragons.get(i).posicao + t)) {
                        dragons.get(i).posicao += t;
                        ret = 0;
                    }
                } else {

                    t = (n * 2 - 7); // f(3) = -1,f(4) = 1
                    if (canMove(dragons.get(i).posicao + t)) {
                        dragons.get(i).posicao += t;
                        ret = 0;
                    }
                }
            } while (ret != 0);
        }
        //System.out.println("Direccao: " + n);
    }

	/*
	 * @brief Testa se houve combate entre o h1.posicao e o
	 * dragons.get(i).posicao, e determina o vencedor
	 */

    public boolean combate() {
    	/*Esta a depender da place Entities, mudar depois*/
        for(int i=0; i<dragons.size(); i++){
            if (h1.posicao == dragons.get(i).posicao + 1
                    || h1.posicao == dragons.get(i).posicao - 1
                    || h1.posicao == dragons.get(i).posicao + 10
                    || h1.posicao == dragons.get(i).posicao - 10
                    || h1.posicao == dragons.get(i).posicao) {
                if (!h1.armado && dragons.get(i).acordado) {
                    placeEntities();

                    if(ambiente == 0) {
                        cli.printMaze(board.getDados());
                        cli.estadoFinal(1);
                    }
                    if(ambiente == 1){
                        test.printMaze(board.getDados());
                        test.estadoFinal(1);
                    }
                    return true;
                } else {
                    if(!h1.armado && !dragons.get(i).acordado)
                        return false;
                    else
                        dragons.get(i).posicao = -1;
                }

            }
            if (h1.posicao == dragons.get(i).posicao + 3
                    || h1.posicao == dragons.get(i).posicao - 3
                    || h1.posicao == dragons.get(i).posicao + 30
                    || h1.posicao == dragons.get(i).posicao - 30
                    || h1.posicao == dragons.get(i).posicao) {

                if(!h1.escudo && dragons.get(i).acordado && randomFireBall()){
                    placeEntities();
                    if(ambiente == 0) {
                        cli.printMaze(board.getDados());
                        cli.estadoFinal(1);
                    }
                    if(ambiente == 1){
                        test.printMaze(board.getDados());
                        test.estadoFinal(1);
                    }
                    return true;
                }
            }

        }

        return false;
    }

    /*
     * @brief Sorteia se as Fireballs estao ativas.
     *
     */
    public boolean randomFireBall(){
        Random r = new Random();
        int n  = r.nextInt(10)+1;
        if(n==2)
            return true;
        else
            return false;
    }

    /*
     * @brief Cria o numero de dragoes pedidos pelo user e insere-os na lista de dragoes (dragons).
     *
     * @param number Numero de dragoes pedido pelo user
     */
    public void generateDragons(int number){
        int n=0;
        Random r = new Random();

        for(int i=0;i<number;i++){
            do {
                n = r.nextInt((board.dimension * board.dimension) - 1);
            } while (board.checkTile(n) == 'X');
            Dragon d1= new Dragon(n);
            dragons.add(d1);
        }
    }
	/*
	 * @brief Cria o numero de dardos entre 0 e 5 re-os na lista de dardos (darts).
	 */

    public void generateDarts(){
        int n=0;
        Random r = new Random();
        int number= r.nextInt(3)+1;

        for(int i=0;i<number;i++){
            do {
                n = r.nextInt((board.dimension * board.dimension) - 1);
            } while (board.checkTile(n) == 'X');
            Dart d1= new Dart(n);
            darts.add(d1);
        }
    }
    

    public void Darts(char direcao){
    	if(!h1.dardo) return;
    	int pos = h1.posicao;
    	while(board.checkTile(pos)!='X'){
    		switch (direcao) {
    		case 'w':
    			pos-=10;
    			break;
    		case 's':
    			pos+=10;
    		case 'a':
    			pos-=1;
    			break;
    		case 'd':
    			pos+=1;
    		default:
    			break;
    		}
    		for(int i=0;i<dragons.size();i++){
    			if(dragons.get(i).posicao==pos){
    				dragons.get(i).posicao=-1;
    				h1.dardo=false;
    				return;
    			}
    		}
    		

    	}
    }
/*

    public void Dartsth(char direcao){

    	if(!h1.dardo) return;

    	boolean usedDart=false;


    	for(int i=0;i<dragons.size();i++){

    		int pos=h1.posicao;
    		while(board.checkTile(pos)!='X'){
    			if(usedDart) return;
    			switch (direcao) {
    			case 'w':
    				pos-=10;
    				break;
    			case 's':
    				pos+=10;
    			case 'a':
    				pos-=1;
    				break;
    			case 'd':
    				pos+=1;
    			default:
    				break;
    			}
    			if(dragons.get(i).posicao==pos){
    				dragons.get(i).posicao=-1;
    				usedDart=true;
    				h1.dardo=false;
    				return;
    			}
    		}
    	}
    	
    }
    
    
    
    
    
    
    
    
    
    
    
    
    

    public void throwDarts(char direcao){
        boolean usedDart=false;
        
        if(!h1.dardo) return;
        
        if(h1.dardo){
        	//  for(int i=0;i<dragons.size();i++){
        	int pos=h1.posicao;
        	while(board.checkTile(pos) != 'X'){
        		for(int i=0;i<dragons.size();i++){
        			if(usedDart) return;
        			
        			if(dragons.get(i).posicao==pos){
        				dragons.get(i).posicao=-1;
        				h1.dardo = false;
        				usedDart = true;
        				break;
        			}
        			else{
        				switch (direcao) {
        				case 'w':
        					pos-=10;
        					break;
        				case 's':
        					pos+=10;
        				case 'a':
        					pos-=1;
        					break;
        				case 'd':
        					pos+=1;
        				default:
        					break;
        				}
        			}
        		}

        	}
        }
    }
*/
    public boolean testWinCondition(){
        if((h1.posicao == board.exit) && (h1.isArmado())) {
            for (int i = 0; i < dragons.size(); i++) {
                if (dragons.get(i).posicao != -1)
                    return false;
            }
            return true;
        }
        else
        	return false;
    }
}
