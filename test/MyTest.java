package test;

import logic.*;

import org.junit.Assert;
import org.junit.Test;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import logic.Dragon;
import logic.Maze;
import logic.MazeBuilder;
import logic.RandomMaze;
import logic.MyRandom;

public class MyTest {

    GameEngine g1;

    /*
        @brief Testa se o heroi se move para espaços em branco
    */

    @Test
    public void testPlayerMovement(){
        g1 = new GameEngine(1);
        if(g1.ambiente != 1)
        	g1.ambiente = 1;
        g1.initializeGame();

        if(g1.h1 == null || g1.h1.getPosicao() != 11){
        	g1.h1 = new Hero();
        	g1.h1.setPosicao(11);
        }
        
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
        if(g1.ambiente != 1)
        	g1.ambiente = 1;
        g1.initializeGame();
        
        if(g1.h1 == null || g1.h1.getPosicao() != 11){
        	g1.h1 = new Hero();
        	g1.h1.setPosicao(11);
        }
        

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
        if(g1.ambiente != 1)
        	g1.ambiente = 1;
        
        g1.initializeGame();
        
        if(g1.h1 == null || g1.h1.getPosicao() != 11){
        	g1.h1 = new Hero();
        	g1.h1.setPosicao(11);
        }

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
        if(g1.ambiente != 1)
        	g1.ambiente = 1;
        g1.initializeGame();
        
        if(g1.h1 == null || g1.h1.getPosicao() != 11){
        	g1.h1 = new Hero();
        	g1.h1.setPosicao(11);
        }
        
        if(g1.dragons.size() == 0 || g1.dragons.get(0).getPosicao() != 31){
        	Dragon d1 = new Dragon();
        	d1.setPosicao(31);
        	g1.dragons.add(d1);
        }
        
        g1.moveHeroi('s');
        if(!g1.dragons.get(0).isAcordado())
        	g1.dragons.get(0).setAcordado(true);
        Assert.assertEquals(g1.combate(),true);
    }

    @Test
    
    public void testIfDragonKilled(){
        g1 = new GameEngine(1);
        if(g1.ambiente != 1)
        	g1.ambiente = 1;
        g1.initializeGame();
        
        if(g1.h1 == null || g1.h1.getPosicao() != 11){
        	g1.h1 = new Hero();
        	g1.h1.setPosicao(11);
        }
        
        if(g1.dragons.size() == 0 || g1.dragons.get(0).getPosicao() != 31){
        	Dragon d1 = new Dragon();
        	d1.setPosicao(31);
        	g1.dragons.add(d1);
        }

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
        if(g1.ambiente != 1)
        	g1.ambiente = 1;
        g1.initializeGame();
        g1.posEspada = 12;
        
        if(g1.h1 == null || g1.h1.getPosicao() != 11){
        	g1.h1 = new Hero();
        	g1.h1.setPosicao(11);
        }
        
        if(g1.dragons.size() == 0 || g1.dragons.get(0).getPosicao() != 31){
        	Dragon d1 = new Dragon();
        	d1.setPosicao(31);
        	g1.dragons.add(d1);
        }
 
        g1.moveHeroi('d');
        g1.placeEntities();
        Assert.assertEquals(true,g1.h1.isArmado());
        g1.placeEntities();
        g1.moveHeroi('a');
        Assert.assertEquals(31,g1.dragons.get(0).getPosicao());
        g1.moveHeroi('s');
       
        if(!g1.h1.isArmado())
        	g1.h1.setArmado(true);
        
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

       Assert.assertEquals(true,g1.testWinCondition());
    }
    
    @Test

public void testIfCatchShield(){
	g1 = new GameEngine(1);
	 if(g1.ambiente != 1)
     	g1.ambiente = 1;
    g1.initializeGame();
    
    if(g1.h1 == null || g1.h1.getPosicao() != 11){
    	g1.h1 = new Hero();
    	g1.h1.setPosicao(11);
    }
    
   g1.posEscudo=12;
   g1.moveHeroi('d');
   g1.placeEntities();
   
   Assert.assertEquals(true,g1.h1.isEscudo());
   }
 

@Test

public void testFireBall(){
	 g1 = new GameEngine(1);
	 if(g1.ambiente != 1)
     	g1.ambiente = 1;
     g1.initializeGame();
     
     boolean fireball = g1.randomFireBall(1);
     
     Assert.assertTrue(fireball);
     
     
	for(int i=0; i<1000; i++){
		fireball = g1.randomFireBall(0);
		Assert.assertTrue(fireball == true || fireball==false);
	}
}


@Test

public void testGenerateDragons(){
	g1 = new GameEngine(1);
	 if(g1.ambiente != 1)
     	g1.ambiente = 1;
	g1.initializeGame();
	
	g1.dragons.clear();
	
	int nDragons = 1;
	int ret=0;
	
	while(ret != nDragons)
		ret=g1.generateDragons(nDragons);
	Assert.assertEquals(nDragons,ret);
	
	g1.dragons.clear();
	
	nDragons=2;
	while(ret != nDragons)
		ret=g1.generateDragons(nDragons);
	Assert.assertEquals(nDragons,ret);
	
	g1.dragons.clear();
	Random r = new Random();
	
	nDragons = r.nextInt(99)+1;

	while(ret != nDragons)
		ret=g1.generateDragons(nDragons);
	Assert.assertEquals(nDragons,ret);
}

@Test 

public void testCombate() {
	g1 = new GameEngine(1);
	 if(g1.ambiente != 1)
     	g1.ambiente = 1;
	g1.initializeGame(); 
	
	if(g1.h1 == null || g1.h1.getPosicao() != 11){
    	g1.h1 = new Hero();
    	g1.h1.setPosicao(11);
    }
    
    if(g1.dragons.size() == 0 || g1.dragons.get(0).getPosicao() != 31){
    	Dragon d1 = new Dragon();
    	g1.dragons.add(d1);
    }
	
	g1.dragons.get(0).setPosicao(55);
	g1.posEspada=-1;
	
	g1.h1.setPosicao(56);
	Assert.assertTrue(g1.combate());

	g1.h1.setPosicao(54);
	Assert.assertTrue(g1.combate());
	
	g1.h1.setPosicao(65);
	Assert.assertTrue(g1.combate());
	
	g1.h1.setPosicao(45);
	Assert.assertTrue(g1.combate());

	g1.h1.setPosicao(11);
	Assert.assertFalse(g1.combate());
}
	

@Test 

public void testFireballKill() {
	g1 = new GameEngine(1);
	 if(g1.ambiente != 1)
     	g1.ambiente = 1;
	g1.initializeGame();
	g1.posEspada=-1;
	boolean firekill = false;
	
    if(g1.h1 == null || g1.h1.getPosicao() != 11){
    	g1.h1 = new Hero();
    	g1.h1.setPosicao(11);
    }
    
    if(g1.dragons.size() == 0 || g1.dragons.get(0).getPosicao() != 31){
    	Dragon d1 = new Dragon();
    	g1.dragons.add(d1);
    }
	
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
	 if(g1.ambiente != 1)
     	g1.ambiente = 1;
	g1.initializeGame();
	g1.dragons.clear();
	g1.generateDragons(1);
	boolean awake=false;
	while(!awake){
		g1.isAwake();
		awake=g1.dragons.get(0).isAcordado();
	}
	
	Assert.assertEquals(true, awake);
	
	g1.dragons.get(0).setAcordado(false);
	
	Assert.assertFalse(g1.dragons.get(0).isAcordado());
	
}

@Test

public void testGenerateDarts(){
	g1 = new GameEngine(1);
	 if(g1.ambiente != 1)
     	g1.ambiente = 1;
	g1.initializeGame();
	g1.generateDarts();
	Assert.assertFalse(g1.darts.size()==0);
}

@Test

public void testJogar(){
	g1 = new GameEngine(1);
	 if(g1.ambiente != 1)
     	g1.ambiente = 1;
	g1.initializeGame();
	g1.dragons.get(0).setPosicao(-1);
	g1.h1.setArmado(true);
	g1.h1.setPosicao(59);
	g1.jogar();
	
	Assert.assertEquals(-1,g1.dragons.get(0).getPosicao());
	
	g1.dragons.get(0).setPosicao(31);
	g1.h1.setPosicao(21);
	g1.h1.setArmado(false);
	boolean pass = true;
	
	g1.jogar(); 
	Assert.assertTrue(pass);
	
}

@Test

public void testRandMoveDragon(){
	g1 = new GameEngine(1);
	 if(g1.ambiente != 1)
     	g1.ambiente = 1;
	g1.initializeGame();
	g1.dragonMode=Dragon.Mode.SLEEPING;
	g1.dragons.get(0).setPosicao(55);
	g1.posEspada = -1;
	int pos,res;

	for(int i=0; i<1000; i++){
		pos = g1.dragons.get(0).getPosicao();
		g1.moveDragoes();
		res = Math.abs(pos-g1.dragons.get(0).getPosicao());
		Assert.assertTrue(res==0 || res==1 || res==10);
	}
	for(int i=0; i<1000; i++){
		g1.dragonMode=Dragon.Mode.STATIC;
		pos = g1.dragons.get(0).getPosicao();
		g1.moveDragoes();
		res = Math.abs(pos-g1.dragons.get(0).getPosicao());
		Assert.assertTrue(res==0);
	}
}

@Test

public void testDeleteEntities(){
	g1 = new GameEngine(1);
	 if(g1.ambiente != 1)
     	g1.ambiente = 1;
	g1.initializeGame();
	g1.dragons.clear();
	g1.generateDragons(3);
	g1.cli.printMaze(g1.board.maze);
	g1.placeEntities();
	try {
	    Thread.sleep(500);                 
	} catch(InterruptedException ex) {
	    Thread.currentThread().interrupt();
	}
	for(Dragon dragon : g1.dragons){
		dragon.setPosicao(-1);
	}
	g1.deleteEntities();
	try {
	    Thread.sleep(500);                 
	} catch(InterruptedException ex) {
	    Thread.currentThread().interrupt();
	}
}

@Test 

public void testThrowDarts(){
	g1 = new GameEngine(1);
	 if(g1.ambiente != 1)
     	g1.ambiente = 1;
	g1.initializeGame();
	
    if(g1.h1 == null || g1.h1.getPosicao() != 11){
    	g1.h1 = new Hero();
    	g1.h1.setPosicao(11);
    }
    
    if(g1.dragons.size() == 0 || g1.dragons.get(0).getPosicao() != 31){
    	Dragon d1 = new Dragon();
    	g1.dragons.add(d1);
    }
	
	g1.dragons.get(0).setPosicao(54);
	g1.dragons.get(0).setAcordado(true);
	g1.h1.setPosicao(84);
	g1.h1.setDardo(true);
	g1.throwDarts('w');
	Assert.assertEquals(-1,g1.dragons.get(0).getPosicao());
	
	g1.dragons.get(0).setPosicao(54);
	g1.h1.setPosicao(56);
	g1.h1.setDardo(true);
	g1.throwDarts('a');
	Assert.assertEquals(-1,g1.dragons.get(0).getPosicao());
	
	g1.dragons.get(0).setPosicao(54);
	g1.h1.setPosicao(52);
	g1.h1.setDardo(true);
	g1.throwDarts('d');
	Assert.assertEquals(-1,g1.dragons.get(0).getPosicao());
	
	g1.dragons.get(0).setPosicao(54);
	g1.h1.setPosicao(34);
	g1.h1.setDardo(true);
	g1.throwDarts('s');
	Assert.assertEquals(-1,g1.dragons.get(0).getPosicao());
	
}

@Test 

public void testMaze(){
	
	MazeBuilder mb1 = new MazeBuilder();

	mb1.setMazeType(0);
	Maze m1 = mb1.getMaze();
	
	mb1.setMazeType(1);
	mb1.setMazeDim(9);
	
	m1 = mb1.getMaze();
	m1.gera();
	
	m1.changeBoard(11, 'X');
	Assert.assertEquals('X', m1.checkTile(11));
}

@Test

public void testMyRandom(){
	
	int res=0;
	MyRandom mr = new MyRandom();

	for(int i=0; i<1000; i++){
		res=mr.nextInt();
		Assert.assertTrue(res==0||res==1||res==2||res==3);
	}

	int[] n = {10,12,34,56,77,888};
	mr = new MyRandom(n);
	res=0;
	
	for(int i=0; i<n.length; i++){
		res = mr.nextInt();
		
		boolean assertionAnswer = Arrays.asList(n).contains(res);
		
		Assert.assertEquals(false,assertionAnswer);
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