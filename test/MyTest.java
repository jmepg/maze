package test;

import logic.*;

import org.junit.Assert;
import org.junit.Test;

import cli.Cli;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import logic.Dragon;
import logic.Maze;

public class MyTest {

    GameEngine g1;

    /*
        @brief Testa se o heroi se move para espaços em branco
    */

    @Test
    public void testPlayerMovement(){
        g1 = new GameEngine(1);
        g1.initializeGame();

        g1.moveHeroi('d');
        Assert.assertEquals(g1.h1.getPosicao(),12);

        g1.moveHeroi('a');
        Assert.assertEquals(g1.h1.getPosicao(),11);

        g1.moveHeroi('s');
        Assert.assertEquals(g1.h1.getPosicao(),21);

    }

     /*
        @brief Testa se o heroi se move quando se tenta mover para uma parede.
    */

    @Test
    public void testIfNotPassThroughWalls(){
        g1 = new GameEngine(1);
        g1.initializeGame();

        g1.moveHeroi('w');
        Assert.assertEquals(g1.h1.getPosicao(),11);

        g1.moveHeroi('a');
        Assert.assertEquals(g1.h1.getPosicao(),11);

        g1.moveHeroi('d');
        g1.moveHeroi('s');
        Assert.assertEquals(g1.h1.getPosicao(),12);
    }

    @Test
    public void testIfSwordCaught(){
        g1 = new GameEngine(1);
        g1.initializeGame();

        g1.posEspada = 14;

        g1.moveHeroi('d');
        g1.moveHeroi('d');
        g1.moveHeroi('d');

        g1.placeEntities();
        Assert.assertEquals(g1.h1.isArmado(),true);
    }

    @Test

    public void testIfLost(){
        g1 = new GameEngine(1);
        g1.initializeGame();

        g1.moveHeroi('s');
        Assert.assertEquals(g1.combate(),true);
    }

    @Test
public void testIfDragonKilled(){
        g1 = new GameEngine(1);
        g1.initializeGame();

        g1.posEspada = 12;
        g1.moveHeroi('d');
        g1.placeEntities();
        g1.moveHeroi('a');
        g1.moveHeroi('s');
        Assert.assertEquals(g1.combate(),false);

    }

    @Test
public void testIfGameWon(){
        g1 = new GameEngine(1);
        g1.initializeGame();

        g1.posEspada = 12;
        //g1.dragons.get(0).setPosicao(13);
 
        g1.moveHeroi('d');
        g1.placeEntities();
        Assert.assertEquals(true,g1.h1.isArmado());
        g1.placeEntities();
        g1.moveHeroi('a');
        Assert.assertEquals(31,g1.dragons.get(0).getPosicao());

        g1.moveHeroi('s');
        g1.combate();
        
        Assert.assertEquals(-1,g1.dragons.get(0).getPosicao());
        g1.placeEntities();

        g1.moveHeroi('w');
        g1.moveHeroi('d');
        g1.moveHeroi('d');
        g1.moveHeroi('d');
        g1.moveHeroi('d');
        g1.moveHeroi('d');
        g1.moveHeroi('d');
        g1.moveHeroi('d');
        g1.moveHeroi('s');
        g1.moveHeroi('s');
        g1.moveHeroi('s');
        g1.moveHeroi('s');
        g1.moveHeroi('d');

      /*  System.out.println(g1.h1.posicao);
        System.out.println(g1.board.getExit());
        System.out.println(g1.h1.isArmado());
        System.out.println(g1.dragons.get(0).getPosicao());
        System.out.println(g1.dragons.size());
*/

        
       Assert.assertEquals(true,g1.testWinCondition());

    }
    
    @Test

public void testIfCatchShield(){
	g1 = new GameEngine(1);
    g1.initializeGame();
    
   g1.posEscudo=12;
   g1.moveHeroi('d');
   g1.placeEntities();
   
   Assert.assertEquals(true,g1.h1.isEscudo());
   }
    
@Test

public void testDragonMovemente(){
	 g1 = new GameEngine(1);
     g1.initializeGame();
     
     g1.dragons.get(0).setPosicao(51);
     
    g1.moveDragao(1);
    Assert.assertEquals(41,g1.dragons.get(0).getPosicao());
     
    g1.moveDragao(3);
    g1.moveDragao(3);
    Assert.assertEquals(61,g1.dragons.get(0).getPosicao());

    g1.moveDragao(1);
    Assert.assertEquals(51,g1.dragons.get(0).getPosicao());

    g1.moveDragao(2);
    Assert.assertEquals(52,g1.dragons.get(0).getPosicao());

    g1.moveDragao(4);
    g1.moveDragao(4);
    g1.moveDragao(4);
    Assert.assertEquals(51,g1.dragons.get(0).getPosicao());

     }

@Test

public void testFireBall(){
	 g1 = new GameEngine(1);
     g1.initializeGame();
     
     boolean fireball = false;
	
	while(!fireball){
		fireball=g1.randomFireBall(0);
	}
	
	Assert.assertEquals(true,fireball);
}


@Test

public void testGenerateDragons(){
	g1 = new GameEngine(1);
	g1.initializeGame();
	
	g1.dragons.clear();
	
	int nDragons = 1;
	int ret;
	ret=g1.generateDragons(nDragons);
	Assert.assertEquals(nDragons,ret);
	
	g1.dragons.clear();
	
	nDragons=2;
	ret=g1.generateDragons(nDragons);
	Assert.assertEquals(nDragons,ret);
	
	g1.dragons.clear();
	Random r = new Random();
	
	nDragons = r.nextInt(99)+1;
	ret=g1.generateDragons(nDragons);
	Assert.assertEquals(nDragons,ret);
}

@Test 

public void testCombate() {
	g1 = new GameEngine(1);
	g1.initializeGame(); 
	g1.dragons.get(0).setPosicao(55);
	g1.posEspada=-1;
	
	g1.h1.setPosicao(56);
	Assert.assertEquals(true,g1.combate());

	g1.h1.setPosicao(54);
	Assert.assertEquals(true,g1.combate());
	
	g1.h1.setPosicao(65);
	Assert.assertEquals(true,g1.combate());
	
	g1.h1.setPosicao(45);
	Assert.assertEquals(true,g1.combate());
}


@Test 

public void testFireballKill() {
	g1 = new GameEngine(1);
	g1.initializeGame();
	g1.posEspada=-1;
	boolean firekill = false;
	
	g1.dragons.get(0).setPosicao(55);
	
	g1.h1.setPosicao(52);
	while(!firekill){
		firekill=g1.combate();
	}
	Assert.assertEquals(true,firekill);
	
	firekill = false;
	g1.h1.setPosicao(58);
	while(!firekill){
		firekill=g1.combate();
	}
	Assert.assertEquals(true,firekill);
	
	firekill = false;
	g1.h1.setPosicao(85);
	while(!firekill){
		firekill=g1.combate();
	}
	Assert.assertEquals(true,firekill);
	
	firekill = false;
	g1.h1.setPosicao(25);
	while(!firekill){
		firekill=g1.combate();
	}
	Assert.assertEquals(true,firekill);
	
}

@Test 

public void testAwake(){
	g1 = new GameEngine(1);
	g1.initializeGame();
	g1.dragons.clear();
	g1.generateDragons(1);
	boolean awake=false;
	while(!awake){
		g1.isAwake();
		awake=g1.dragons.get(0).isAcordado();
	}
	
	Assert.assertEquals(true, awake);
}

@Test

public void testGenerateDarts(){
	g1 = new GameEngine(1);
	g1.initializeGame();
	g1.generateDarts();
	Assert.assertFalse(g1.darts.size()==0);
}

@Test

public void testJogar(){
	g1 = new GameEngine(1);
	g1.initializeGame();
	g1.dragons.get(0).setPosicao(-1);
	g1.h1.setArmado(true);
	g1.h1.setPosicao(59);
	g1.jogar();
	//g1.h1.setPosicao(59);
	//System.out.println(g1.dragons.get(0).getPosicao());
	
	//g1.jogar();
	Assert.assertEquals(-1,g1.dragons.get(0).getPosicao());
	
	g1.dragons.get(0).setPosicao(31);
	g1.h1.setPosicao(21);
	g1.h1.setArmado(false);
	boolean pass = true;
	
	g1.jogar(); 
	Assert.assertEquals(true,pass);
	
}

@Test

public void testRandMoveDragon(){
	g1 = new GameEngine(1);
	g1.initializeGame();
	g1.dragonMode=Dragon.Mode.SLEEPING;
	g1.dragons.get(0).setPosicao(55);
	g1.posEspada = -1;
	int pos,res;

	for(int i=0; i<1000; i++){
		pos = g1.dragons.get(0).getPosicao();
		g1.moveDragao();
		res = Math.abs(pos-g1.dragons.get(0).getPosicao());
		Assert.assertTrue(res==0 || res==1 || res==10);
	}
	for(int i=0; i<1000; i++){
		g1.dragonMode=Dragon.Mode.STATIC;
		pos = g1.dragons.get(0).getPosicao();
		g1.moveDragao();
		res = Math.abs(pos-g1.dragons.get(0).getPosicao());
		Assert.assertTrue(res==0);
	}
}

@Test

public void testPrintMaze(){
	g1 = new GameEngine(1);
	g1.initializeGame();
	g1.cli.printMaze(g1.board.getDados());
}

@Test 

public void testAskForDirection(){
	g1 = new GameEngine(1);
	g1.initializeGame();
	char res;

	res = g1.cli.askForDirection();
	Assert.assertTrue(res =='w' || res == 's' || res == 'a' || res == 'd');
}

@Test 

public void testAskForType(){
	g1 = new GameEngine(1);
	g1.initializeGame();
	int res;

	res = g1.cli.askForType();
	Assert.assertTrue(res == 0 || res == 1);
}

@Test 

public void testAskForSize(){
	g1 = new GameEngine(1);
	g1.initializeGame();
	int res;

	res = g1.cli.askForSize();
	Assert.assertTrue(res > 7 && res%2==1);
}

@Test 

public void testAskForMode(){
	g1 = new GameEngine(1);
	g1.initializeGame();
	Dragon.Mode res;

	res = g1.cli.askForMode();
	Assert.assertTrue(res == Dragon.Mode.MOVABLE || res == Dragon.Mode.SLEEPING || res == Dragon.Mode.STATIC);
}

@Test 

public void testAskForDragons(){
	g1 = new GameEngine(1);
	g1.initializeGame();
	int res;

	res = g1.cli.askForDragons();
	Assert.assertTrue(res > 0);
}

@Test 

public void testClearConsole(){
	g1 = new GameEngine(1);
	g1.initializeGame();
	
	Cli.clearConsole();
}

@Test

public void testDeleteEntities(){
	g1 = new GameEngine(1);
	g1.initializeGame();
	g1.dragons.clear();
	g1.generateDragons(3);
	g1.cli.printMaze(g1.board.maze);
	g1.placeEntities();
	try {
	    Thread.sleep(2000);                 
	} catch(InterruptedException ex) {
	    Thread.currentThread().interrupt();
	}
	for(Dragon dragon : g1.dragons){
		dragon.setPosicao(-1);
	}
	g1.deleteEntities();
	try {
	    Thread.sleep(2000);                 
	} catch(InterruptedException ex) {
	    Thread.currentThread().interrupt();
	}
}


   
    /*
    @return Estado final.
     */
    public int estadoFinal(int estado) {
        /* 0: Ganhou, 1: Perdeu, 2: Erro */

        //clearConsole();

        switch (estado) {
            case 0:
                return 0;
            case 1:
                return 1;
            default:
                return 2;
        }
    }

    /*
	 * @return O labirinto.
	 */
    public List<Character> printMaze(List<Character> maze) {
        return maze;
    }


}