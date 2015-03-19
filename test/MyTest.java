package test;

import logic.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

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
        g1.moveHeroi('d');
        g1.placeEntities();
        g1.moveHeroi('a');
        g1.moveHeroi('s');

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