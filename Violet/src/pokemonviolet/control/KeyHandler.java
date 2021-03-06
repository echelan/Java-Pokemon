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

	public final static int ACTION_NONE = 0;
	public final static int ACTION_A = 1;
	public final static int ACTION_B = 2;
	public final static int ACTION_START = 3;
	public final static int ACTION_UP = 4;
	public final static int ACTION_DOWN = 5;
	public final static int ACTION_LEFT = 6;
	public final static int ACTION_RIGHT = 7;
	public final static int STATE_PRESS = 0;
	public final static int STATE_RELEASE = 1;
	
	@Override
	public void keyReleased(KeyEvent key) {
		boolean acceptedKey = false;
		int sendAction = ACTION_NONE;

		if (key.getKeyCode() == KeyEvent.VK_UP || key.getKeyCode() == KeyEvent.VK_W) {
			acceptedKey = true;
			sendAction = ACTION_UP;
		} else if (key.getKeyCode() == KeyEvent.VK_DOWN || key.getKeyCode() == KeyEvent.VK_S) {
			acceptedKey = true;
			sendAction = ACTION_DOWN;
		} else if (key.getKeyCode() == KeyEvent.VK_LEFT || key.getKeyCode() == KeyEvent.VK_A) {
			acceptedKey = true;
			sendAction = ACTION_LEFT;
		} else if (key.getKeyCode() == KeyEvent.VK_RIGHT || key.getKeyCode() == KeyEvent.VK_D) {
			acceptedKey = true;
			sendAction = ACTION_RIGHT;
		} else if (key.getKeyCode() == KeyEvent.VK_K) {
			acceptedKey = true;
			sendAction = ACTION_B;
		} else if (key.getKeyCode() == KeyEvent.VK_J) {
			acceptedKey = true;
			sendAction = ACTION_A;
		} else if (key.getKeyCode() == KeyEvent.VK_ENTER) {
			acceptedKey = true;
			sendAction = ACTION_START;
		}

		if (acceptedKey) {
			pokemonviolet.model.Handler.gameState.get(pokemonviolet.model.Handler.gameState.size() - 1).receiveKeyAction(sendAction, STATE_RELEASE);
		}
	}
	
	@Override
	public void keyPressed(KeyEvent key) {
		boolean acceptedKey = false;
		int sendAction = ACTION_NONE;

		if (key.getKeyCode() == KeyEvent.VK_UP || key.getKeyCode() == KeyEvent.VK_W) {
			acceptedKey = true;
			sendAction = ACTION_UP;
		} else if (key.getKeyCode() == KeyEvent.VK_DOWN || key.getKeyCode() == KeyEvent.VK_S) {
			acceptedKey = true;
			sendAction = ACTION_DOWN;
		} else if (key.getKeyCode() == KeyEvent.VK_LEFT || key.getKeyCode() == KeyEvent.VK_A) {
			acceptedKey = true;
			sendAction = ACTION_LEFT;
		} else if (key.getKeyCode() == KeyEvent.VK_RIGHT || key.getKeyCode() == KeyEvent.VK_D) {
			acceptedKey = true;
			sendAction = ACTION_RIGHT;
		} else if (key.getKeyCode() == KeyEvent.VK_K) {
			acceptedKey = true;
			sendAction = ACTION_B;
		} else if (key.getKeyCode() == KeyEvent.VK_J) {
			acceptedKey = true;
			sendAction = ACTION_A;
		} else if (key.getKeyCode() == KeyEvent.VK_ENTER) {
			acceptedKey = true;
			sendAction = ACTION_START;
		}

		if (acceptedKey) {
			pokemonviolet.model.Handler.gameState.get(pokemonviolet.model.Handler.gameState.size() - 1).receiveKeyAction(sendAction, STATE_PRESS);
		}
	}
}
