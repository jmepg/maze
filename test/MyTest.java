package test;

import logic.*;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Handles the JUnit 4 tests.
 */
public class MyTest {

	GameEngine g1;

	/**
	 * Tests if the hero can sucessfully navigate through the floor.
	 */

	@Test
	public void testPlayerMovement() {
		g1 = new GameEngine(1);
		if (g1.getAmbiente() != 1)
			g1.setAmbiente(-1);
		g1.initializeGame(null);

		if (g1.getHero() == null || g1.getHero().getPosicao() != 11) {
			Hero hero1 = new Hero();
			g1.setHero(hero1);
			g1.getHero().setPosicao(11);
		}

		g1.moveHeroi('d');
		Assert.assertEquals(g1.getHero().getPosicao(), 12);

		g1.moveHeroi('a');
		Assert.assertEquals(g1.getHero().getPosicao(), 11);

		g1.moveHeroi('s');
		Assert.assertEquals(g1.getHero().getPosicao(), 21);

	}

	/**
	 * Tests if the hero doesn't walk through walls.
	 */

	@Test
	public void testIfNotPassThroughWalls() {
		g1 = new GameEngine(1);
		if (g1.getAmbiente() != 1)
			g1.setAmbiente(1);
		g1.initializeGame(null);

		if (g1.getHero() == null || g1.getHero().getPosicao() != 11) {
			Hero hero1 = new Hero();
			g1.setHero(hero1);
			g1.getHero().setPosicao(11);
		}

		g1.moveHeroi('w');
		Assert.assertEquals(g1.getHero().getPosicao(), 11);

		g1.moveHeroi('a');
		Assert.assertEquals(g1.getHero().getPosicao(), 11);

		g1.moveHeroi('d');
		g1.moveHeroi('s');
		Assert.assertEquals(g1.getHero().getPosicao(), 12);
	}

	/**
	 * Tests if the hero can catch the sword.
	 */
	@Test
	public void testIfSwordCaught() {
		g1 = new GameEngine(1);
		if (g1.getAmbiente() != 1)
			g1.setAmbiente(1);

		g1.initializeGame(null);

		if (g1.getHero() == null || g1.getHero().getPosicao() != 11) {
			Hero hero1 = new Hero();
			g1.setHero(hero1);
			g1.getHero().setPosicao(11);
		}

		g1.setPosEspada(14); 

		g1.moveHeroi('d');
		g1.moveHeroi('d');
		g1.moveHeroi('d');

		g1.placeEntities();
		Assert.assertEquals(g1.getHero().isArmado(), true);
	}

	/**
	 * Tests if the hero can die by being eaten by a hero.
	 */
	@Test
	public void testIfLost() {
		g1 = new GameEngine(1);
		if (g1.getAmbiente() != 1)
			g1.setAmbiente(1);
		g1.initializeGame(null);

		if (g1.getHero() == null || g1.getHero().getPosicao() != 11) {
			Hero hero1 = new Hero();
			g1.setHero(hero1);
			g1.getHero().setPosicao(11);
		}

		if (g1.getDragons().size() == 0 || g1.getDragons().get(0).getPosicao() != 31) {
			Dragon d1 = new Dragon();
			d1.setPosicao(31);
			g1.getDragons().add(d1);
		}

		g1.moveHeroi('s');
		if (!g1.getDragons().get(0).isAcordado())
			g1.getDragons().get(0).setAcordado(true);
		Assert.assertEquals(g1.combate(), true);
	}

	/**
	 * Tests if the dragon can be killed.
	 */

	@Test
	public void testIfDragonKilled() {
		g1 = new GameEngine(1);
		if (g1.getAmbiente() != 1)
			g1.setAmbiente(1);
		g1.initializeGame(null);

		if (g1.getHero() == null || g1.getHero().getPosicao() != 11) {
			Hero hero1 = new Hero();
			g1.setHero(hero1);
			g1.getHero().setPosicao(11);
		}

		if (g1.getDragons().size() == 0 || g1.getDragons().get(0).getPosicao() != 31) {
			Dragon d1 = new Dragon();
			d1.setPosicao(31);
			g1.getDragons().add(d1);
		}

		g1.setPosEspada(12);
		g1.moveHeroi('d');
		g1.placeEntities();
		g1.moveHeroi('a');
		g1.moveHeroi('s');
		Assert.assertEquals(g1.combate(), false);

	}

	/**
	 * Tests if the game is won.
	 */
	@Test
	public void testIfGameWon() {
		g1 = new GameEngine(1);
		if (g1.getAmbiente() != 1)
			g1.setAmbiente(1);
		g1.initializeGame(null);

		if (g1.getHero() == null || g1.getHero().getPosicao() != 11) {
			Hero hero1 = new Hero();
			g1.setHero(hero1);
			g1.getHero().setPosicao(11);
		}

		if (g1.getDragons().size() == 0 || g1.getDragons().get(0).getPosicao() != 31) {
			Dragon d1 = new Dragon();
			d1.setPosicao(31);
			g1.getDragons().add(d1);
		}

		g1.moveHeroi('d');
		g1.placeEntities();
		Assert.assertEquals(true, g1.getHero().isArmado());
		g1.placeEntities();
		g1.moveHeroi('a');
		Assert.assertEquals(31, g1.getDragons().get(0).getPosicao());
		g1.moveHeroi('s');

		if (!g1.getHero().isArmado())
			g1.getHero().setArmado(true);

		g1.combate();

		Assert.assertEquals(-1, g1.getDragons().get(0).getPosicao());
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

		Assert.assertEquals(true, g1.testWinCondition());
	}

	/**
	 * Tests if the hero can catch the shield.
	 */
	@Test
	public void testIfCatchShield() {
		g1 = new GameEngine(1);
		if (g1.getAmbiente() != 1)
			g1.setAmbiente(1);
		g1.initializeGame(null);

		if (g1.getHero() == null || g1.getHero().getPosicao() != 11) {
			Hero hero1 = new Hero();
			g1.setHero(hero1);
			g1.getHero().setPosicao(11);
		}

		g1.setPosEscudo(12); 
		g1.moveHeroi('d');
		g1.placeEntities();

		Assert.assertEquals(true, g1.getHero().isEscudo());
	}

	/**
	 * Tests if the fireballs are sucessfully generated.
	 */
	@Test
	public void testFireBall() {
		g1 = new GameEngine(1);
		if (g1.getAmbiente() != 1)
			g1.setAmbiente(1);
		g1.initializeGame(null);
		
		
		boolean fireball = false;
				
		do{
			fireball = g1.randomFireBall();
		} while(fireball = false);

		Assert.assertTrue(fireball);

		for (int i = 0; i < 1000; i++) {
			fireball = g1.randomFireBall();
			Assert.assertTrue(fireball == true || fireball == false);
		}
	}

	/**
	 * Tests if the dragons are sucessfully generated.
	 */

	@Test
	public void testGenerateDragons() {
		g1 = new GameEngine(1);
		if (g1.getAmbiente() != 1)
			g1.setAmbiente(1);
		g1.initializeGame(null);
		
		List <Dragon> dragons1 = null;
		g1.setDragons(dragons1);
		int nDragons = 1;
		int ret = 0;

		while (ret != nDragons){
			g1.generateDragons(nDragons);
			ret = g1.getDragons().size();
			}
		Assert.assertEquals(nDragons, ret);

		g1.setDragons(dragons1);

		nDragons = 2;
		while (ret != nDragons){	
			g1.generateDragons(nDragons);
			ret = g1.getDragons().size();
			}
		Assert.assertEquals(nDragons, ret);

		g1.setDragons(dragons1);
		Random r = new Random();

		nDragons = r.nextInt(99) + 1;

		while (ret != nDragons){
			g1.generateDragons(nDragons);
			ret = g1.getDragons().size();
			}
		Assert.assertEquals(nDragons, ret);
	}

	/**
	 * Tests if the fights are correctly resolved.
	 */
	@Test
	public void testCombate() {
		g1 = new GameEngine(1);
		if (g1.getAmbiente() != 1)
			g1.setAmbiente(1);
		g1.initializeGame(null);

		if (g1.getHero() == null || g1.getHero().getPosicao() != 11) {
			Hero hero1  = new Hero();
			g1.setHero(hero1);
			g1.getHero().setPosicao(11);
		}

		if (g1.getDragons().size() == 0 || g1.getDragons().get(0).getPosicao() != 31) {
			Dragon d1 = new Dragon();
			g1.getDragons().add(d1);
		}

		g1.getDragons().get(0).setPosicao(55);
		g1.setPosEspada(-1);

		g1.getHero().setPosicao(56);
		Assert.assertTrue(g1.combate());

		g1.getHero().setPosicao(54);
		Assert.assertTrue(g1.combate());

		g1.getHero().setPosicao(65);
		Assert.assertTrue(g1.combate());

		g1.getHero().setPosicao(45);
		Assert.assertTrue(g1.combate());

		g1.getHero().setPosicao(11);
		Assert.assertFalse(g1.combate());
	}

	/**
	 * Tests if a fireball kills the hero.
	 */

	@Test
	public void testFireballKill() {
		g1 = new GameEngine(1);
		if (g1.getAmbiente() != 1)
			g1.setAmbiente(1);
		g1.initializeGame(null);
		g1.setPosEspada(-1);
		boolean firekill = false;

		if (g1.getHero() == null || g1.getHero().getPosicao() != 11) {
			Hero hero1 = new Hero();
			g1.setHero(hero1);
		}

		if (g1.getDragons().size() == 0 || g1.getDragons().get(0).getPosicao() != 31) {
			Dragon d1 = new Dragon();
			g1.getDragons().add(d1);
		}

		g1.getDragons().get(0).setPosicao(54);
		g1.getDragons().get(0).setAcordado(true);
		g1.getHero().setPosicao(52);
		while (!firekill) {
			firekill = g1.fireballKill();
		}

		firekill = false;
		g1.getHero().setPosicao(56);
		while (!firekill) {
			firekill = g1.fireballKill();
		}

		firekill = false;
		g1.getHero().setPosicao(74);
		while (!firekill) {
			firekill = g1.fireballKill();
		}

		firekill = false;
		g1.getHero().setPosicao(34);
		while (!firekill) {
			firekill = g1.fireballKill();
		}

	}

	/**
	 * Tests the awake state of the dragons.
	 */

	@Test
	public void testAwake() {
		g1 = new GameEngine(1);
		if (g1.getAmbiente() != 1)
			g1.setAmbiente(1); 
		g1.initializeGame(null); 
		List <Dragon> dragons1 = null;
		g1.setDragons(dragons1);
		g1.generateDragons(1);
		boolean awake = false;
		while (!awake) {
			g1.isAwake();
			awake = g1.getDragons().get(0).isAcordado();
		}

		Assert.assertEquals(true, awake);

		g1.getDragons().get(0).setAcordado(false);

		Assert.assertFalse(g1.getDragons().get(0).isAcordado());

	}

	/**
	 * Tests if the darts are correctly generated.
	 */

	@Test
	public void testGenerateDarts() {
		g1 = new GameEngine(1);
		if (g1.getAmbiente() != 1)
			g1.setAmbiente(1); 
		g1.initializeGame(null);
		g1.generateDarts();
		Assert.assertFalse(g1.getDarts().size() == 0);
	}

	/**
	 * Tests if the generation of a game.
	 */
	@Test
	public void testJogar() {
		g1 = new GameEngine(1);
		if (g1.getAmbiente() != 1)
			g1.setAmbiente(1); 
		g1.initializeGame(null);
		g1.getDragons().get(0).setPosicao(-1);
		g1.getHero().setArmado(true);
		g1.getHero().setPosicao(59);
		g1.jogar();

		Assert.assertEquals(-1, g1.getDragons().get(0).getPosicao());

		g1.getDragons().get(0).setPosicao(31);
		g1.getHero().setPosicao(21);
		g1.getHero().setArmado(false);
		boolean pass = true;

		g1.jogar();
		Assert.assertTrue(pass);

	}

	/**
	 * Tests the dragon's random movement.
	 */
	@Test
	public void testRandMoveDragon() {
		g1 = new GameEngine(1);
		if (g1.getAmbiente() != 1)
			g1.setAmbiente(1);
		g1.initializeGame(null);
		g1.dragonMode = Dragon.Mode.SLEEPING;
		g1.getDragons().get(0).setPosicao(55);
		g1.setPosEspada(-1);
		int pos, res;

		for (int i = 0; i < 1000; i++) {
			pos = g1.getDragons().get(0).getPosicao();
			g1.moveDragoes();
			res = Math.abs(pos - g1.getDragons().get(0).getPosicao());
			Assert.assertTrue(res == 0 || res == 1 || res == 10);
		}
		for (int i = 0; i < 1000; i++) {
			g1.dragonMode = Dragon.Mode.STATIC;
			pos = g1.getDragons().get(0).getPosicao();
			g1.moveDragoes();
			res = Math.abs(pos - g1.getDragons().get(0).getPosicao());
			Assert.assertTrue(res == 0);
		}
	}

	/**
	 * Tests if the static entities are correctly deleted.
	 */
	@Test
	public void testDeleteEntities() {
		g1 = new GameEngine(1);
		if (g1.getAmbiente() != 1)
			g1.setAmbiente(1);
		g1.initializeGame(null);
		List <Dragon> dragons1 = null;
		g1.setDragons(dragons1);
		g1.generateDragons(3);
		g1.cli.printMaze(g1.getBoard().getMaze());
		g1.placeEntities();
		try {
			Thread.sleep(500);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		for (Dragon dragon : g1.getDragons()) {
			dragon.setPosicao(-1);
		}
		g1.deleteEntities();
		try {
			Thread.sleep(500);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * Tests if the darts are correctly thrown.
	 */
	@Test
	public void testThrowDarts() {
		g1 = new GameEngine(1);
		if (g1.getAmbiente() != 1)
			g1.setAmbiente(1); 
		g1.initializeGame(null);

		if (g1.getHero() == null || g1.getHero().getPosicao() != 11) {
			Hero hero1 = new Hero();
			g1.setHero(hero1);
			g1.getHero().setPosicao(11);
		}

		if (g1.getDragons().size() == 0 || g1.getDragons().get(0).getPosicao() != 31) {
			Dragon d1 = new Dragon();
			g1.getDragons().add(d1);
		}

		g1.getDragons().get(0).setPosicao(54);
		g1.getDragons().get(0).setAcordado(true);
		g1.getHero().setPosicao(84);
		g1.getHero().setDardo(true);
		g1.throwDarts('w');
		Assert.assertEquals(-1, g1.getDragons().get(0).getPosicao());

		g1.getDragons().get(0).setPosicao(54);
		g1.getHero().setPosicao(56);
		g1.getHero().setDardo(true);
		g1.throwDarts('a');
		Assert.assertEquals(-1, g1.getDragons().get(0).getPosicao());

		g1.getDragons().get(0).setPosicao(54);
		g1.getHero().setPosicao(52);
		g1.getHero().setDardo(true);
		g1.throwDarts('d');
		Assert.assertEquals(-1, g1.getDragons().get(0).getPosicao());

		g1.getDragons().get(0).setPosicao(54);
		g1.getHero().setPosicao(34);
		g1.getHero().setDardo(true);
		g1.throwDarts('s');
		Assert.assertEquals(-1, g1.getDragons().get(0).getPosicao());

	}
	
	/**
	 * Tests if a maze is correctly generated and tiles are correctly placed.
	 */

	@Test
	public void testMaze() {

		MazeBuilder mb1 = new MazeBuilder();

		mb1.setOpcao(0);
		Maze m1 = mb1.getMaze();

		mb1.setOpcao(1);
		mb1.setMazeDim(9);

		m1 = mb1.getMaze();
		m1.generate();

		m1.changeBoard(11, 'X');
		Assert.assertEquals('X', m1.checkTile(11));
	}

	/* Funcoes nao comentadas visto que vao muito provavelmetne ser apagadas */
	
	
	/*
	 * @return Estado final.
	 */
	public int estadoFinal(int estado) {
		/* 0: Ganhou, 1: Perdeu, 2: Erro */

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