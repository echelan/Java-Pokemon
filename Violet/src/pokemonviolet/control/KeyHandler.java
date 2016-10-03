/*
 *  Pokemon Violet - A University Project by Andres Movilla
 *  Pokemon COPYRIGHT 2002-2016 Pokemon.
 *  COPYRIGHT 1995-2016 Nintendo/Creatures Inc./GAME FREAK inc. TRADEMARK, REGISTERED TRADEMARK
 *  and Pokemon character names are trademarks of Nintendo.
 *  No copyright or trademark infringement is intended in using Pokemon content on Pokemon Violet.
 */
package pokemonviolet.control;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 *
 * @author Andres
 */
public class KeyHandler extends KeyAdapter {

	@Override
	public void keyReleased(KeyEvent key) {
		boolean acceptedKey = false;
		String sendAction = "";

		if (key.getKeyCode() == KeyEvent.VK_UP || key.getKeyCode() == KeyEvent.VK_W) {
			acceptedKey = true;
			sendAction = "UP";
		} else if (key.getKeyCode() == KeyEvent.VK_DOWN || key.getKeyCode() == KeyEvent.VK_S) {
			acceptedKey = true;
			sendAction = "DOWN";
		} else if (key.getKeyCode() == KeyEvent.VK_LEFT || key.getKeyCode() == KeyEvent.VK_A) {
			acceptedKey = true;
			sendAction = "LEFT";
		} else if (key.getKeyCode() == KeyEvent.VK_RIGHT || key.getKeyCode() == KeyEvent.VK_D) {
			acceptedKey = true;
			sendAction = "RIGHT";
		} else if (key.getKeyCode() == KeyEvent.VK_K) {
			acceptedKey = true;
			sendAction = "B";
		} else if (key.getKeyCode() == KeyEvent.VK_J) {
			acceptedKey = true;
			sendAction = "A";
		} else if (key.getKeyCode() == KeyEvent.VK_ENTER) {
			acceptedKey = true;
			sendAction = "START";
		}

		if (acceptedKey) {
			pokemonviolet.model.Handler.gameState.get(pokemonviolet.model.Handler.gameState.size() - 1).receiveKeyAction(sendAction, "RELEASE");
		}
	}
	
	@Override
	public void keyPressed(KeyEvent key) {
		boolean acceptedKey = false;
		String sendAction = "";

		if (key.getKeyCode() == KeyEvent.VK_UP || key.getKeyCode() == KeyEvent.VK_W) {
			acceptedKey = true;
			sendAction = "UP";
		} else if (key.getKeyCode() == KeyEvent.VK_DOWN || key.getKeyCode() == KeyEvent.VK_S) {
			acceptedKey = true;
			sendAction = "DOWN";
		} else if (key.getKeyCode() == KeyEvent.VK_LEFT || key.getKeyCode() == KeyEvent.VK_A) {
			acceptedKey = true;
			sendAction = "LEFT";
		} else if (key.getKeyCode() == KeyEvent.VK_RIGHT || key.getKeyCode() == KeyEvent.VK_D) {
			acceptedKey = true;
			sendAction = "RIGHT";
		} else if (key.getKeyCode() == KeyEvent.VK_K) {
			acceptedKey = true;
			sendAction = "B";
		} else if (key.getKeyCode() == KeyEvent.VK_J) {
			acceptedKey = true;
			sendAction = "A";
		} else if (key.getKeyCode() == KeyEvent.VK_ENTER) {
			acceptedKey = true;
			sendAction = "START";
		}

		if (acceptedKey) {
			pokemonviolet.model.Handler.gameState.get(pokemonviolet.model.Handler.gameState.size() - 1).receiveKeyAction(sendAction, "PRESS");
		}
	}
}
