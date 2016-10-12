/*
 *  Pokemon Violet - A University Project by Andres Movilla
 *  Pokemon COPYRIGHT 2002-2016 Pokemon.
 *  COPYRIGHT 1995-2016 Nintendo/Creatures Inc./GAME FREAK inc. TRADEMARK, REGISTERED TRADEMARK
 *  and Pokemon character names are trademarks of Nintendo.
 *  No copyright or trademark infringement is intended in using Pokemon content on Pokemon Violet.
 */
package pokemonviolet.scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import pokemonviolet.model.Handler;

/**
 *
 * @author Andres
 */
public class Starter extends Scene {

	private int[] pokeID = {1, 4, 7};
	private String[] pokeNames = {"Bulbasaur", "Charmander", "Squirtle"};
	private int chosen;

	private boolean konamiExecuted;
	private String[] konamiCode = {"UP", "UP", "DOWN", "DOWN", "LEFT", "RIGHT", "LEFT", "RIGHT", "B", "A", "START"};
	private ArrayList<String> konamiInput;
	private String playerGender;

	public Starter(Handler main, String gender) {
		super(main, "STARTER", true);

		playerGender = gender;

		konamiInput = new ArrayList<String>();

		chosen = 0;

	}

	@Override
	public void receiveKeyAction(String action, String state) {
		boolean konamid = false;
		if (!konamiExecuted) {
			if (state.compareTo("RELEASE") == 0) {
				if (action.compareTo(konamiCode[konamiInput.size()]) == 0) {
					konamiInput.add(action);
					konamid = true;
					if (konamiInput.size() == konamiCode.length) {
						konamiInput.clear();
						pokeID[0] = 135;
						pokeID[1] = 136;
						pokeID[2] = 134;

						pokeNames[0] = "Jolteon";
						pokeNames[1] = "Flareon";
						pokeNames[2] = "Vaporeon";

						konamiExecuted = true;
					}
				} else {
					konamiInput.clear();
				}
			}
		}

		if (!konamid) {
			if (state.compareTo("RELEASE") == 0) {
				if (action.compareTo("A") == 0) {
					accept();
				} else if (action.compareTo("B") == 0) {
					cancel();
				}
			} else if (state.compareTo("PRESS") == 0) {
				if (action.compareTo("LEFT") == 0 || action.compareTo("RIGHT") == 0) {
					move(action);
				}
			}
		}
	}

	@Override
	protected void accept() {
		this.dispose();
		main.gameState.add(new Game(main, new pokemonviolet.model.Player("Player", playerGender, new pokemonviolet.model.Pokemon(pokeID[chosen], 5, "POKEBALL"))));
	}

	@Override
	protected void cancel() {
		this.dispose();
		main.gameState.add(new Gender(main));
	}

	@Override
	protected void move(String dir) {
		if (dir.compareTo("LEFT") == 0) {
			chosen = chosen - 1;
		} else if (dir.compareTo("RIGHT") == 0) {
			chosen = chosen + 1;
		}

		if (chosen < 0) {
			chosen = 2;
		} else if (chosen > 2) {
			chosen = 0;
		}
	}

	@Override
	protected void start() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
	@Override
	public BufferedImage getDisplay() throws IOException {
		BufferedImage display = new BufferedImage(resizedValue(ssX), resizedValue(ssY), BufferedImage.TYPE_INT_ARGB);
		Graphics g = display.getGraphics();

		g.drawImage(ImageIO.read(new File("assets/title/background.png")), 0, 0, resizedValue(ssX), resizedValue(ssY), null);

		g.setFont(new Font("Arial", Font.BOLD, resizedValue(15)));
		g.drawString("Choose your starter!", resizedValue(ssX / 5), resizedValue(15));

		int characterWidth = 80, characterHeight = 80;

		g.setFont(new Font("Arial", Font.BOLD, resizedValue(7.5)));
		for (int i = 0; i < pokeID.length; i++) {
			int x = 5 + (i * (ssX / 3));
			
			g.drawImage(ImageIO.read(new File("assets/pokemon/" + pokeID[i] + "mfn.png")), resizedValue(x), resizedValue(ssY / 5), resizedValue(characterWidth), resizedValue(characterHeight), null);
			if (chosen == i) {
				g.setColor(Color.WHITE);
			} else {
				g.setColor(Color.GRAY);
			}
			g.drawString(pokeNames[i], resizedValue(x + (characterWidth / 4)), resizedValue(ssY / 5 + characterHeight + 2.5));
		}
			
		g.drawImage(ImageIO.read(new File("assets/moveLeft.png")), resizedValue(30), resizedValue(ssY - 40), resizedValue(70), resizedValue(35), null);
		g.drawImage(ImageIO.read(new File("assets/moveRight.png")), resizedValue(105), resizedValue(ssY - 40), resizedValue(70), resizedValue(35), null);
		g.drawImage(ImageIO.read(new File("assets/buttonB.png")), resizedValue(180), resizedValue(ssY - 40), resizedValue(35), resizedValue(35), null);

		return display;
	}

}
