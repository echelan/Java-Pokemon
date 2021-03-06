/*
 *  Pokemon Violet - A University Project by Andres Movilla
 *  Pokemon COPYRIGHT 2002-2016 Pokemon.
 *  COPYRIGHT 1995-2016 Nintendo/Creatures Inc./GAME FREAK inc. TRADEMARK, REGISTERED TRADEMARK
 *  and Pokemon character names are trademarks of Nintendo.
 *  No copyright or trademark infringement is intended in using Pokemon content on Pokemon Violet.
 */
package pokemonviolet.builder;

import java.awt.Graphics2D;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Andres
 */
public class MapBuilder extends JFrame implements WindowListener, ActionListener, MouseListener, KeyListener {

	//<editor-fold defaultstate="collapsed" desc="Attributes">
		//<editor-fold defaultstate="collapsed" desc="Statics">
			private static final int TILE_WIDTH = 16;
			private static final int TILE_HEIGHT = 16;
			private static final int TILE_RESIZED_WIDTH = 32;
			private static final int TILE_RESIZED_HEIGHT = 32;

			private static final int MAX_TILE_IN_TILESET = 120;
			private static final int MAX_TILE_IN_TILESET_ROW = 20;
			private static final int MAX_SET_IN_TILESET = 8;
			private static final int MAX_TILE_IN_SET = 15;
			private static final int MAX_TILE_IN_SET_ROW = 5;
			private static final int MAX_TILE_IN_SET_COL = 3;
			private static final int MAX_SET_IN_TILE_SET_ROW = 4;

			private static final int MAP_ROW_TILES = 20;
			private static final int START_X = 5;
			private static final int START_Y = 5;

			private static final int WINDOW_X = 10;
			private static final int WINDOW_Y = 10;

			private static final int MAIN_WINDOW_WIDTH = 655;
			private static final int MAIN_WINDOW_HEIGHT = 680;

			private static final int TOP_WINDOW_WIDTH = 200;
			private static final int TOP_WINDOW_HEIGHT = 350;

			private static final int BOT_WINDOW_WIDTH = 200;
			private static final int BOT_WINDOW_HEIGHT = 300;

			private static final int WINDOW_GAP = 20;
		//</editor-fold>
			
		//<editor-fold defaultstate="collapsed" desc="Static Images">
			private static BufferedImage tileset;
			private static BufferedImage gym;
			private static BufferedImage center;
			private static BufferedImage house;
			private static BufferedImage house2;
			private static BufferedImage house3;
			private static BufferedImage objects;
			private static BufferedImage shop;
			private static BufferedImage tree;
			private static BufferedImage tree2;
			private static BufferedImage wstone;
			private static BufferedImage wstone2;

			private static BufferedImage tilesetSMALL;
			private static BufferedImage gymSMALL;
			private static BufferedImage centerSMALL;
			private static BufferedImage houseSMALL;
			private static BufferedImage house2SMALL;
			private static BufferedImage house3SMALL;
			private static BufferedImage objectsSMALL;
			private static BufferedImage shopSMALL;
			private static BufferedImage treeSMALL;
			private static BufferedImage tree2SMALL;
			private static BufferedImage wstoneSMALL;
			private static BufferedImage wstone2SMALL;

			private static BufferedImage[] objSets;
		//</editor-fold>
		
		//<editor-fold defaultstate="collapsed" desc="Variables">
			private JLabel[][] tileGridLabel;
			private ImageIcon[][] tileGridImage;
			private int[][] tile;
			private int[][] setT;
			private JLabel[][] objGridLabel;
			private ImageIcon[][] objGridImage;
			private int[][] obj;
			private int[][] setO;
			private int[][] objDims;

			private boolean isSelecting = false;
			private int xStart, yStart, xEnd, yEnd;

			private final pokemonManager windowPokemon;
			private final objectManager windowObject;
			private final tileManager windowTile;
			private final generalControls windowGeneral;
		//</editor-fold>
	//</editor-fold>
	
	public MapBuilder(int operation) {
		setLayout(null);
		setSize(MAIN_WINDOW_WIDTH, MAIN_WINDOW_HEIGHT);
		setResizable(false);
		setLocation(WINDOW_X, WINDOW_Y);
		setTitle("Map Builder");
		addWindowListener(this);
		setDefaultCloseOperation(operation);

		JTextField hiddenListener = new JTextField();
		hiddenListener.setBounds(800, 0, 1, 1);
		hiddenListener.addKeyListener(this);
		add(hiddenListener);

		try {

			//<editor-fold defaultstate="collapsed" desc="Image Load">
			tilesetSMALL = ImageIO.read(new File("assets/map/tileset.png"));
			tileset = scale(tilesetSMALL, tilesetSMALL.getWidth(), tilesetSMALL.getHeight(), 2.0, 2.0);

			shopSMALL = ImageIO.read(new File("assets/map/shop.png"));
			shop = scale(shopSMALL, shopSMALL.getWidth(), shopSMALL.getHeight(), 2.0, 2.0);

			gymSMALL = ImageIO.read(new File("assets/map/gym.png"));
			gym = scale(gymSMALL, gymSMALL.getWidth(), gymSMALL.getHeight(), 2.0, 2.0);

			centerSMALL = ImageIO.read(new File("assets/map/center.png"));
			center = scale(centerSMALL, centerSMALL.getWidth(), centerSMALL.getHeight(), 2.0, 2.0);

			objectsSMALL = ImageIO.read(new File("assets/map/objects.png"));
			objects = scale(objectsSMALL, objectsSMALL.getWidth(), objectsSMALL.getHeight(), 2.0, 2.0);

			houseSMALL = ImageIO.read(new File("assets/map/house.png"));
			house = scale(houseSMALL, houseSMALL.getWidth(), houseSMALL.getHeight(), 2.0, 2.0);

			treeSMALL = ImageIO.read(new File("assets/map/tree.png"));
			tree = scale(treeSMALL, treeSMALL.getWidth(), treeSMALL.getHeight(), 2.0, 2.0);

			tree2SMALL = ImageIO.read(new File("assets/map/tree2.png"));
			tree2 = scale(tree2SMALL, tree2SMALL.getWidth(), tree2SMALL.getHeight(), 2.0, 2.0);

			house2SMALL = ImageIO.read(new File("assets/map/house2.png"));
			house2 = scale(house2SMALL, house2SMALL.getWidth(), house2SMALL.getHeight(), 2.0, 2.0);

			house3SMALL = ImageIO.read(new File("assets/map/house3.png"));
			house3 = scale(house3SMALL, house3SMALL.getWidth(), house3SMALL.getHeight(), 2.0, 2.0);

			wstoneSMALL = ImageIO.read(new File("assets/map/wstone.png"));
			wstone = scale(wstoneSMALL, wstoneSMALL.getWidth(), wstoneSMALL.getHeight(), 2.0, 2.0);

			wstone2SMALL = ImageIO.read(new File("assets/map/wstone2.png"));
			wstone2 = scale(wstone2SMALL, wstone2SMALL.getWidth(), wstone2SMALL.getHeight(), 2.0, 2.0);
			//</editor-fold>

			objSets = new BufferedImage[]{objects, house, house2, house3, center, shop, gym, tree, tree2, wstone, wstone2};
			objDims = new int[][]{{1, 1}, {5, 5}, {5, 3}, {6, 4}, {5, 5}, {4, 4}, {6, 5}, {2, 3}, {2, 3}, {2, 2}, {2, 2}};

			xStart = 0;
			yStart = 0;
			xEnd = MAP_ROW_TILES - 1;
			yEnd = MAP_ROW_TILES - 1;

			tileGridLabel = new JLabel[MAP_ROW_TILES][MAP_ROW_TILES];
			tileGridImage = new ImageIcon[MAP_ROW_TILES][MAP_ROW_TILES];
			objGridLabel = new JLabel[MAP_ROW_TILES][MAP_ROW_TILES];
			objGridImage = new ImageIcon[MAP_ROW_TILES][MAP_ROW_TILES];
			tile = new int[MAP_ROW_TILES][MAP_ROW_TILES];
			setT = new int[MAP_ROW_TILES][MAP_ROW_TILES];
			obj = new int[MAP_ROW_TILES][MAP_ROW_TILES];
			setO = new int[MAP_ROW_TILES][MAP_ROW_TILES];

			//<editor-fold defaultstate="collapsed" desc="Label & Icon Setup">
			for (int i = 0; i < MAP_ROW_TILES; i++) {
				for (int j = 0; j < MAP_ROW_TILES; j++) {
					int xTile, yTile, xPos, yPos;

					obj[i][j] = 0;
					setO[i][j] = 0;

					xTile = getObjX(setO[i][j], obj[i][j]);
					yTile = getObjY(setO[i][j], obj[i][j]);

					objGridImage[i][j] = new ImageIcon(objSets[obj[i][j]].getSubimage(xTile, yTile, TILE_RESIZED_WIDTH, TILE_RESIZED_HEIGHT));

					xPos = START_X + (i * TILE_RESIZED_WIDTH);
					yPos = START_Y + (j * TILE_RESIZED_HEIGHT);
					objGridLabel[i][j] = new JLabel(objGridImage[i][j]);
					objGridLabel[i][j].addMouseListener(this);
					objGridLabel[i][j].setBounds(xPos, yPos, TILE_RESIZED_WIDTH, TILE_RESIZED_HEIGHT);
					add(objGridLabel[i][j]);

					tile[i][j] = 0;
					setT[i][j] = 0;

					xTile = getTileX(setT[i][j], tile[i][j]);
					yTile = getTileY(setT[i][j], tile[i][j]);

					tileGridImage[i][j] = new ImageIcon(tileset.getSubimage(xTile, yTile, TILE_RESIZED_WIDTH, TILE_RESIZED_HEIGHT));

					xPos = START_X + (i * TILE_RESIZED_WIDTH);
					yPos = START_Y + (j * TILE_RESIZED_HEIGHT);
					tileGridLabel[i][j] = new JLabel(tileGridImage[i][j]);
					tileGridLabel[i][j].addMouseListener(this);
					tileGridLabel[i][j].setBounds(xPos, yPos, TILE_RESIZED_WIDTH, TILE_RESIZED_HEIGHT);
					add(tileGridLabel[i][j]);

				}
			}
			//</editor-fold>
		} catch (IOException ex) {

		}

		setIconImage(objects.getSubimage(32, 0, 32, 32));

		setVisible(true);

		windowPokemon = new pokemonManager();
		windowGeneral = new generalControls();
		windowObject = new objectManager();
		windowTile = new tileManager();
	}

	public void setObj(int type) {
		int x = (xEnd - xStart) + 1;
		int y = (yEnd - yStart) + 1;
		if (objDims[type][0] == x && objDims[type][1] == y) {
			for (int i = xStart; i <= xEnd; i++) {
				for (int j = yStart; j <= yEnd; j++) {
					setO[i][j] = type;
				}
			}

			createObj();
		}
	}

	public void setOutBox() {
		if (xStart - xEnd != 0 && yStart - yEnd != 0) {
			for (int i = xStart; i <= xEnd; i++) {
				for (int j = yStart; j <= yEnd; j++) {
					tile[i][j] = 0;
					if (i > xStart) {
						tile[i][j] = tile[i][j] + 1;
						if (i == xEnd) {
							tile[i][j] = tile[i][j] + 1;
						}
					}
					if (j > yStart) {
						tile[i][j] = tile[i][j] + MAX_TILE_IN_SET_ROW;
						if (j == yEnd) {
							tile[i][j] = tile[i][j] + MAX_TILE_IN_SET_ROW;
						}
					}
					int xTile = getTileX(setT[i][j], tile[i][j]);
					int yTile = getTileY(setT[i][j], tile[i][j]);
					tileGridImage[i][j] = new ImageIcon(tileset.getSubimage(xTile, yTile, TILE_RESIZED_WIDTH, TILE_RESIZED_HEIGHT));
					tileGridLabel[i][j].setIcon(tileGridImage[i][j]);
				}
			}
		}
	}

	public void setInBox() {
		if (xStart - xEnd != 0 && yStart - yEnd != 0) {
			for (int i = xStart; i <= xEnd; i++) {
				for (int j = yStart; j <= yEnd; j++) {
					tile[i][j] = 12;
					if (i > xStart) {
						tile[i][j] = tile[i][j] - 1;
						if (i == xEnd) {
							tile[i][j] = tile[i][j] - 1;
						}
					}
					if (j > yStart) {
						tile[i][j] = tile[i][j] - MAX_TILE_IN_SET_ROW;
						if (j == yEnd) {
							tile[i][j] = tile[i][j] - MAX_TILE_IN_SET_ROW;
						}
					}
					if (i == xStart && j == yStart) {
						tile[i][j] = 8;
					} else if (i == xStart && j == yEnd) {
						tile[i][j] = 13;
					} else if (i == xEnd && j == yStart) {
						tile[i][j] = 9;
					} else if (i == xEnd && j == yEnd) {
						tile[i][j] = 14;
					}
					int xTile = getTileX(setT[i][j], tile[i][j]);
					int yTile = getTileY(setT[i][j], tile[i][j]);
					tileGridImage[i][j] = new ImageIcon(tileset.getSubimage(xTile, yTile, TILE_RESIZED_WIDTH, TILE_RESIZED_HEIGHT));
					tileGridLabel[i][j].setIcon(tileGridImage[i][j]);
				}
			}
		}
	}

	public void prevSet(String reference) {
		int[][] tileInfo;
		int[][] setInfo;
		ImageIcon[][] imageInfo;
		JLabel[][] labelInfo;
		boolean canContinue = true;

		if (reference.compareTo("Tile") == 0) {
			tileInfo = tile;
			setInfo = setT;
			imageInfo = tileGridImage;
			labelInfo = tileGridLabel;
		} else if (reference.compareTo("Object") == 0) {
			tileInfo = obj;
			setInfo = setO;
			imageInfo = objGridImage;
			labelInfo = objGridLabel;
		} else {
			canContinue = false;
			setInfo = null;
			tileInfo = null;
			imageInfo = null;
			labelInfo = null;
		}
		if (canContinue) {
			for (int i = xStart; i <= xEnd; i++) {
				for (int j = yStart; j <= yEnd; j++) {
					setInfo[i][j] = setInfo[i][j] - 1;
					if (reference.compareTo("Tile") == 0) {
						if (tileInfo[i][j] >= MAX_TILE_IN_SET) {
							tileInfo[i][j] = tileInfo[i][j] - MAX_TILE_IN_SET;
						} else if (tile[i][j] < 0) {
							tileInfo[i][j] = MAX_TILE_IN_SET + tileInfo[i][j];
						}
						if (setInfo[i][j] >= MAX_SET_IN_TILESET) {
							setInfo[i][j] = setInfo[i][j] - MAX_SET_IN_TILESET;
						} else if (setInfo[i][j] < 0) {
							setInfo[i][j] = MAX_SET_IN_TILESET + setInfo[i][j];
						}
					} else if (reference.compareTo("Object") == 0) {
						int maxset = objSets.length;
						if (setInfo[i][j] >= maxset) {
							setInfo[i][j] = setInfo[i][j] - maxset;
						} else if (setInfo[i][j] < 0) {
							setInfo[i][j] = maxset + setInfo[i][j];
						}
						int maxtile = (objSets[setInfo[i][j]].getWidth() / TILE_RESIZED_WIDTH) * (objSets[setInfo[i][j]].getHeight() / TILE_RESIZED_HEIGHT);
						if (tileInfo[i][j] >= maxtile) {
							tileInfo[i][j] = tileInfo[i][j] - maxtile;
						} else if (tile[i][j] < 0) {
							tileInfo[i][j] = maxtile + tileInfo[i][j];
						}
					}

					int xTile = 0;
					int yTile = 0;
					if (reference.compareTo("Tile") == 0) {
						xTile = getTileX(setInfo[i][j], tileInfo[i][j]);
						yTile = getTileY(setInfo[i][j], tileInfo[i][j]);
						imageInfo[i][j] = new ImageIcon(tileset.getSubimage(xTile, yTile, TILE_RESIZED_WIDTH, TILE_RESIZED_HEIGHT));
					} else if (reference.compareTo("Object") == 0) {
						xTile = getObjX(setInfo[i][j], tileInfo[i][j]);
						yTile = getObjY(setInfo[i][j], tileInfo[i][j]);
						imageInfo[i][j] = new ImageIcon(objSets[setInfo[i][j]].getSubimage(xTile, yTile, TILE_RESIZED_WIDTH, TILE_RESIZED_HEIGHT));
					}

					labelInfo[i][j].setIcon(imageInfo[i][j]);
				}
			}
		}
	}

	public void nextSet(String reference) {
		int[][] tileInfo;
		int[][] setInfo;
		ImageIcon[][] imageInfo;
		JLabel[][] labelInfo;
		boolean canContinue = true;

		if (reference.compareTo("Tile") == 0) {
			tileInfo = tile;
			setInfo = setT;
			imageInfo = tileGridImage;
			labelInfo = tileGridLabel;
		} else if (reference.compareTo("Object") == 0) {
			tileInfo = obj;
			setInfo = setO;
			imageInfo = objGridImage;
			labelInfo = objGridLabel;
		} else {
			canContinue = false;
			tileInfo = null;
			setInfo = null;
			imageInfo = null;
			labelInfo = null;
		}
		if (canContinue) {
			for (int i = xStart; i <= xEnd; i++) {
				for (int j = yStart; j <= yEnd; j++) {
					setInfo[i][j] = setInfo[i][j] + 1;
					if (reference.compareTo("Tile") == 0) {
						if (tileInfo[i][j] >= MAX_TILE_IN_SET) {
							tileInfo[i][j] = tileInfo[i][j] - MAX_TILE_IN_SET;
						} else if (tile[i][j] < 0) {
							tileInfo[i][j] = MAX_TILE_IN_SET + tileInfo[i][j];
						}
						if (setInfo[i][j] >= MAX_SET_IN_TILESET) {
							setInfo[i][j] = setInfo[i][j] - MAX_SET_IN_TILESET;
						} else if (setInfo[i][j] < 0) {
							setInfo[i][j] = MAX_SET_IN_TILESET + setInfo[i][j];
						}
					} else if (reference.compareTo("Object") == 0) {
						int maxset = objSets.length;
						if (setInfo[i][j] >= maxset) {
							setInfo[i][j] = setInfo[i][j] - maxset;
						} else if (setInfo[i][j] < 0) {
							setInfo[i][j] = maxset + setInfo[i][j];
						}
						int maxtile = (objSets[setInfo[i][j]].getWidth() / TILE_RESIZED_WIDTH) * (objSets[setInfo[i][j]].getHeight() / TILE_RESIZED_HEIGHT);
						if (tileInfo[i][j] >= maxtile) {
							tileInfo[i][j] = tileInfo[i][j] - maxtile;
						} else if (tile[i][j] < 0) {
							tileInfo[i][j] = maxtile + tileInfo[i][j];
						}
					}

					int xTile;
					int yTile;
					if (reference.compareTo("Tile") == 0) {
						xTile = getTileX(setInfo[i][j], tileInfo[i][j]);
						yTile = getTileY(setInfo[i][j], tileInfo[i][j]);
						imageInfo[i][j] = new ImageIcon(tileset.getSubimage(xTile, yTile, TILE_RESIZED_WIDTH, TILE_RESIZED_HEIGHT));
					} else if (reference.compareTo("Object") == 0) {
						xTile = getObjX(setInfo[i][j], tileInfo[i][j]);
						yTile = getObjY(setInfo[i][j], tileInfo[i][j]);
						imageInfo[i][j] = new ImageIcon(objSets[setInfo[i][j]].getSubimage(xTile, yTile, TILE_RESIZED_WIDTH, TILE_RESIZED_HEIGHT));
					}

					labelInfo[i][j].setIcon(imageInfo[i][j]);
				}
			}
		}
	}

	public void prevTile(String reference) {
		int[][] tileInfo;
		int[][] setInfo;
		ImageIcon[][] imageInfo;
		JLabel[][] labelInfo;
		boolean canContinue = true;
		if (reference.compareTo("Tile") == 0) {
			tileInfo = tile;
			setInfo = setT;
			imageInfo = tileGridImage;
			labelInfo = tileGridLabel;
		} else if (reference.compareTo("Object") == 0) {
			tileInfo = obj;
			setInfo = setO;
			imageInfo = objGridImage;
			labelInfo = objGridLabel;
		} else {
			canContinue = false;
			tileInfo = null;
			setInfo = null;
			imageInfo = null;
			labelInfo = null;
		}
		if (canContinue) {
			for (int i = xStart; i <= xEnd; i++) {
				for (int j = yStart; j <= yEnd; j++) {
					tileInfo[i][j] = tileInfo[i][j] - 1;
					if (reference.compareTo("Tile") == 0) {
						if (tileInfo[i][j] >= MAX_TILE_IN_SET) {
							tileInfo[i][j] = tileInfo[i][j] - MAX_TILE_IN_SET;
						} else if (tileInfo[i][j] < 0) {
							tileInfo[i][j] = MAX_TILE_IN_SET + tileInfo[i][j];
						}
					} else if (reference.compareTo("Object") == 0) {
						int maxtile = (objSets[setInfo[i][j]].getWidth() / TILE_RESIZED_WIDTH) * (objSets[setInfo[i][j]].getHeight() / TILE_RESIZED_HEIGHT);
						if (tileInfo[i][j] >= maxtile) {
							tileInfo[i][j] = tileInfo[i][j] - maxtile;
						} else if (tileInfo[i][j] < 0) {
							tileInfo[i][j] = maxtile + tileInfo[i][j];
						}
					}

					int xTile;
					int yTile;
					if (reference.compareTo("Tile") == 0) {
						xTile = getTileX(setInfo[i][j], tileInfo[i][j]);
						yTile = getTileY(setInfo[i][j], tileInfo[i][j]);
						imageInfo[i][j] = new ImageIcon(tileset.getSubimage(xTile, yTile, TILE_RESIZED_WIDTH, TILE_RESIZED_HEIGHT));
					} else if (reference.compareTo("Object") == 0) {
						xTile = getObjX(setInfo[i][j], tileInfo[i][j]);
						yTile = getObjY(setInfo[i][j], tileInfo[i][j]);
						imageInfo[i][j] = new ImageIcon(objSets[setInfo[i][j]].getSubimage(xTile, yTile, TILE_RESIZED_WIDTH, TILE_RESIZED_HEIGHT));
					}

					labelInfo[i][j].setIcon(imageInfo[i][j]);
				}
			}
		}
	}

	public void nextTile(String reference) {
		int[][] tileInfo;
		int[][] setInfo;
		ImageIcon[][] imageInfo;
		JLabel[][] labelInfo;
		boolean canContinue = true;

		if (reference.compareTo("Tile") == 0) {
			tileInfo = tile;
			setInfo = setT;
			imageInfo = tileGridImage;
			labelInfo = tileGridLabel;
		} else if (reference.compareTo("Object") == 0) {
			tileInfo = obj;
			setInfo = setO;
			imageInfo = objGridImage;
			labelInfo = objGridLabel;
		} else {
			canContinue = false;
			tileInfo = null;
			setInfo = null;
			imageInfo = null;
			labelInfo = null;
		}
		if (canContinue) {
			for (int i = xStart; i <= xEnd; i++) {
				for (int j = yStart; j <= yEnd; j++) {

					tileInfo[i][j] = tileInfo[i][j] + 1;
					if (reference.compareTo("Tile") == 0) {
						if (tileInfo[i][j] >= MAX_TILE_IN_SET) {
							tileInfo[i][j] = tileInfo[i][j] - MAX_TILE_IN_SET;
						} else if (tileInfo[i][j] < 0) {
							tileInfo[i][j] = MAX_TILE_IN_SET + tileInfo[i][j];
						}
					} else if (reference.compareTo("Object") == 0) {
						if (setInfo[i][j] != 0) {
							tileInfo[i][j] = tileInfo[i][j] - 1;
						} else {
							int maxtile = (objSets[setInfo[i][j]].getWidth() / TILE_RESIZED_WIDTH) * (objSets[setInfo[i][j]].getHeight() / TILE_RESIZED_HEIGHT);
							if (tileInfo[i][j] >= maxtile) {
								tileInfo[i][j] = tileInfo[i][j] - maxtile;
							} else if (tileInfo[i][j] < 0) {
								tileInfo[i][j] = maxtile + tileInfo[i][j];
							}
						}
					}

					int xTile;
					int yTile;
					if (reference.compareTo("Tile") == 0) {
						xTile = getTileX(setInfo[i][j], tileInfo[i][j]);
						yTile = getTileY(setInfo[i][j], tileInfo[i][j]);
						imageInfo[i][j] = new ImageIcon(tileset.getSubimage(xTile, yTile, TILE_RESIZED_WIDTH, TILE_RESIZED_HEIGHT));
					} else if (reference.compareTo("Object") == 0) {
						xTile = getObjX(setInfo[i][j], tileInfo[i][j]);
						yTile = getObjY(setInfo[i][j], tileInfo[i][j]);
						imageInfo[i][j] = new ImageIcon(objSets[setInfo[i][j]].getSubimage(xTile, yTile, TILE_RESIZED_WIDTH, TILE_RESIZED_HEIGHT));
					}

					labelInfo[i][j].setIcon(imageInfo[i][j]);
				}
			}
		}
	}

	public void setTile(ActionEvent e) {
		int tileX = 0, tileY = 0, base = 0;
		for (int i = 0; i < windowTile.tileOutSetBtn.length; i++) {
			for (int j = 0; j < windowTile.tileOutSetBtn[i].length; j++) {
				if (windowTile.tileOutSetBtn[i][j] == e.getSource()) {
					tileX = i;
					tileY = j;
					base = 0;
				}
			}
		}
		for (int i = 0; i < windowTile.tileInSetBtn.length; i++) {
			for (int j = 0; j < windowTile.tileInSetBtn[i].length; j++) {
				if (windowTile.tileInSetBtn[i][j] == e.getSource()) {
					tileX = i;
					tileY = j;
					base = 8;
				}
			}
		}
		int tileID = base + tileX + (tileY * 5);
		for (int i = xStart; i <= xEnd; i++) {
			for (int j = yStart; j <= yEnd; j++) {
				tile[i][j] = tileID;

				int xTile = getTileX(setT[i][j], tile[i][j]);
				int yTile = getTileY(setT[i][j], tile[i][j]);

				tileGridImage[i][j] = new ImageIcon(tileset.getSubimage(xTile, yTile, TILE_RESIZED_WIDTH, TILE_RESIZED_HEIGHT));
				tileGridLabel[i][j].setIcon(tileGridImage[i][j]);
			}
		}
	}

	public void cleanObj() {
		for (int i = xStart; i <= xEnd; i++) {
			for (int j = yStart; j <= yEnd; j++) {
				obj[i][j] = 0;
				setO[i][j] = 0;

				int xTile = getObjX(setO[i][j], obj[i][j]);;
				int yTile = getObjY(setO[i][j], obj[i][j]);;

				objGridImage[i][j] = new ImageIcon(objSets[setO[i][j]].getSubimage(xTile, yTile, TILE_RESIZED_WIDTH, TILE_RESIZED_HEIGHT));
				objGridLabel[i][j].setIcon(objGridImage[i][j]);
			}
		}
	}

	public void createObj() {
		int counter = 0;
		for (int i = xStart; i <= xEnd; i++) {
			for (int j = yStart; j <= yEnd; j++) {

				obj[i][j] = counter;
				int rowtile = (objSets[setO[i][j]].getWidth() / TILE_RESIZED_WIDTH);
				counter = counter + rowtile;

				int maxtile = (objSets[setO[i][j]].getWidth() / TILE_RESIZED_WIDTH) * (objSets[setO[i][j]].getHeight() / TILE_RESIZED_HEIGHT);
				if (counter >= maxtile) {
					counter = counter - (maxtile - 1);
				}

				int xTile = getObjX(setO[i][j], obj[i][j]);
				int yTile = getObjY(setO[i][j], obj[i][j]);

				objGridImage[i][j] = new ImageIcon(objSets[setO[i][j]].getSubimage(xTile, yTile, TILE_RESIZED_WIDTH, TILE_RESIZED_HEIGHT));
				objGridLabel[i][j].setIcon(objGridImage[i][j]);
			}
		}
	}
	
	public void createForest() {
		//	single tree = 7; cluster tree = 8;
		int objID = 0;
		int maxClusterID = 3;
		int maxSingleID = 5;
		int lastX = xStart;
		int thisStartX, thisStartY, thisEndX, thisEndY;
		thisStartX = xStart;
		thisStartY = yStart;
		thisEndX = xEnd + 1;
		thisEndY = yEnd + 1;
		
		if ((thisEndX - thisStartX) % 2 == 1) {
			thisEndX = thisEndX - 1;
		}
		
		if ((thisEndY - thisStartY) % 2 == 0) {
			thisEndY = thisEndY - 1;
			if ((thisEndY - thisStartY) < 3) {
				thisEndX = thisStartX;
			}
		}
		
		for (int i = thisStartX; i < thisEndX; i++) {
			if (lastX != i) {
				objID = objID + 1;
				if (objID == 2) {
					objID = 0;
				}
			}
			for (int j = thisStartY; j < thisEndY; j++) {
				if (j == thisStartY || j == thisEndY - 2 || j == thisEndY - 1) {
					setO[i][j] = 7;
					obj[i][j] = objID;
					objID = objID + 2;
					if (objID > maxSingleID) {
						objID = objID - (maxSingleID + 1);
					}
				} else {
					setO[i][j] = 8;
					obj[i][j] = objID;
					objID = objID + 2;
					if (objID > maxClusterID) {
						objID = objID - (maxClusterID + 1);
					}
				}
			}
		}
		
		refresh();
		
	}

	public static int getTileX(int setinfo, int tileinfo) {
		int regX = (int) (Math.floor((double) tileinfo % MAX_TILE_IN_SET_ROW) * TILE_RESIZED_WIDTH);
		int specialX = (int) (Math.floor((double) setinfo % MAX_SET_IN_TILE_SET_ROW) * (TILE_RESIZED_WIDTH * MAX_TILE_IN_SET_ROW));

		
		return regX + specialX;
	}

	public static int getTileY(int setinfo, int tileinfo) {
		int regY = (int) (Math.floor((double) tileinfo / MAX_TILE_IN_SET_ROW) * TILE_RESIZED_HEIGHT);
		int specialY = (int) (Math.floor((double) setinfo / MAX_SET_IN_TILE_SET_ROW) * (TILE_RESIZED_HEIGHT * MAX_TILE_IN_SET_COL));

		
		return regY + specialY;
	}

	public static int getObjX(int setinfo, int tileinfo) {
		int maxtileinsetrow = (objSets[setinfo].getWidth() / TILE_RESIZED_WIDTH);
		int regX = (int) (Math.floor((double) tileinfo % maxtileinsetrow) * TILE_RESIZED_WIDTH);
		return regX;
	}

	public static int getObjY(int setinfo, int tileinfo) {
		int maxtileinsetrow = (objSets[setinfo].getWidth() / TILE_RESIZED_WIDTH);
		int regY = (int) (Math.floor((double) tileinfo / maxtileinsetrow) * TILE_RESIZED_HEIGHT);
		return regY;
	}

	public static BufferedImage scale(BufferedImage imageToScale, int dWidth, int dHeight, double xScale, double yScale) {
		BufferedImage scaledImage = null;
		if (imageToScale != null) {
			scaledImage = new BufferedImage((int) (dWidth * xScale), (int) (dHeight * yScale), imageToScale.getType());
			Graphics2D graphics2D = scaledImage.createGraphics();
			graphics2D.drawImage(imageToScale, 0, 0, (int) (dWidth * xScale), (int) (dHeight * yScale), null);
			graphics2D.dispose();
		}
		return scaledImage;
	}

	public void writeMapFile() {
		FileWriter fw = null;
		try {
			File archivo = new File("db/mapX" + windowGeneral.mapIDx.getText() + "Y" + windowGeneral.mapIDy.getText() + ".txt");
			fw = new FileWriter(archivo.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			/*
			if (windowPokemon.pokemonListDisplay.getText().compareTo("")==0){
				windowPokemon.pokemonListDisplay.setText("1;4;7");
			}
			 */
			bw.write(windowGeneral.mapIDx.getText() + ";" + windowGeneral.mapIDy.getText());
			bw.newLine();

			bw.write(windowPokemon.pokemonInfo);
			//	bw.newLine();

			for (int i = 0; i < MAP_ROW_TILES; i++) {
				bw.newLine();
				for (int j = 0; j < MAP_ROW_TILES; j++) {
					String info[] = new String[4];

					info[0] = Integer.toHexString(setT[i][j]);
					info[1] = Integer.toHexString(tile[i][j]);
					info[2] = Integer.toHexString(setO[i][j]);
					info[3] = Integer.toHexString(obj[i][j]);

					for (int k = 0; k < info.length; k++) {
						if (info[k].length() == 1) {
							info[k] = "0" + info[k];
						}
						bw.write(info[k]);
						if (k != (info.length - 1)) {
							bw.write("-");
						}
					}
					bw.write(",");
				}
				//	bw.newLine();
			}
			bw.close();

		} catch (IOException ex) {
		} finally {
			try {
				fw.close();
			} catch (IOException ex) {
			}
		}
	}

	public void loadMapFile() {

		List<String> readMapInfo = null;
		try {
			File archivo = new File("db/mapX" + windowGeneral.mapIDx.getText() + "Y" + windowGeneral.mapIDy.getText() + ".txt");
			readMapInfo = Files.readAllLines(archivo.toPath());
		} catch (IOException ex1) {
			try {
				File archivo = new File("mapBLANK.txt");
				readMapInfo = Files.readAllLines(archivo.toPath());
			} catch (IOException ex2) {

			}
		} finally {
			if (readMapInfo != null) {
				windowPokemon.refreshPokemonList(readMapInfo.get(1));

				readMapInfo.remove(1);
				readMapInfo.remove(0);

				for (int i = 0; i < readMapInfo.size(); i++) {
					String[] thisline = readMapInfo.get(i).split(",");
					for (int j = 0; j < thisline.length; j++) {
						String[] tileInfo = thisline[j].split("-");
						for (int k = 0; k < tileInfo.length; k++) {
							int thisInfo = Integer.parseInt(tileInfo[k], 16);

							switch (k) {
								case 0:
									setT[i][j] = thisInfo;
									break;
								case 1:
									tile[i][j] = thisInfo;
									break;
								case 2:
									setO[i][j] = thisInfo;
									break;
								case 3:
									obj[i][j] = thisInfo;
									break;
							}
						}
					}
				}
				refresh();
			}
		}

	}

	public void refresh() {
		int[][] setInfo = setT;
		int[][] tileInfo = tile;
		ImageIcon[][] imageInfo = tileGridImage;
		JLabel[][] labelInfo = tileGridLabel;
		for (int i = 0; i < MAP_ROW_TILES; i++) {
			for (int j = 0; j < MAP_ROW_TILES; j++) {

				int xTile;
				int yTile;
				xTile = getTileX(setInfo[i][j], tileInfo[i][j]);
				yTile = getTileY(setInfo[i][j], tileInfo[i][j]);

				imageInfo[i][j] = new ImageIcon(tileset.getSubimage(xTile, yTile, TILE_RESIZED_WIDTH, TILE_RESIZED_HEIGHT));
				labelInfo[i][j].setIcon(imageInfo[i][j]);
			}
		}
		setInfo = setO;
		tileInfo = obj;
		imageInfo = objGridImage;
		labelInfo = objGridLabel;
		for (int i = 0; i < MAP_ROW_TILES; i++) {
			for (int j = 0; j < MAP_ROW_TILES; j++) {

				int xTile;
				int yTile;
				xTile = getObjX(setInfo[i][j], tileInfo[i][j]);
				yTile = getObjY(setInfo[i][j], tileInfo[i][j]);

				imageInfo[i][j] = new ImageIcon(objSets[setInfo[i][j]].getSubimage(xTile, yTile, TILE_RESIZED_WIDTH, TILE_RESIZED_HEIGHT));
				labelInfo[i][j].setIcon(imageInfo[i][j]);
			}
		}
	}

	public int[] getPos(int x, int y) {
		int[] pos = new int[2];

		for (int i = 0; i < MAP_ROW_TILES; i++) {
			for (int j = 0; j < MAP_ROW_TILES; j++) {

				if (tileGridLabel[i][j].getX() <= x && x <= (tileGridLabel[i][j].getX() + tileGridLabel[i][j].getWidth())) {
					pos[0] = i;
				}

				if (tileGridLabel[i][j].getY() <= y && y <= (tileGridLabel[i][j].getY() + tileGridLabel[i][j].getHeight())) {
					pos[1] = j;
				}

			}
		}

		return pos;
	}

	public void highlight(int xParam, int yParam) {
		clearHighlight();
		int thisStartX, thisStartY, thisEndX, thisEndY;
		if (xStart < xParam) {
			thisStartX = xStart;
			thisEndX = xParam;
		} else {
			thisEndX = xStart;
			thisStartX = xParam;
		}
		if (yStart < yParam) {
			thisStartY = yStart;
			thisEndY = yParam;
		} else {
			thisEndY = yStart;
			thisStartY = yParam;
		}
		for (int i = thisStartX; i <= thisEndX; i++) {
			for (int j = thisStartY; j <= thisEndY; j++) {
				int xPos = START_X + (i * TILE_RESIZED_WIDTH);
				int yPos = START_Y + (j * TILE_RESIZED_HEIGHT);
				tileGridLabel[i][j].setBounds(xPos + 2, yPos + 2, TILE_RESIZED_HEIGHT - 4, TILE_RESIZED_WIDTH - 4);
			}
		}
		windowGeneral.dimDisplay.setText("(" + (thisEndX + 1 - thisStartX) + "x" + (thisEndY + 1 - thisStartY) + ")");
	}

	public void clearHighlight() {
		for (int i = 0; i < MAP_ROW_TILES; i++) {
			for (int j = 0; j < MAP_ROW_TILES; j++) {
				int xPos = START_X + (i * TILE_RESIZED_WIDTH);
				int yPos = START_Y + (j * TILE_RESIZED_HEIGHT);
				tileGridLabel[i][j].setBounds(xPos, yPos, TILE_RESIZED_HEIGHT, TILE_RESIZED_WIDTH);
			}
		}
	}
	
	class objectManager extends JFrame implements ActionListener {

		private JButton createObjBtn;
		private JButton clearObjBtn;
		private JButton buildCenter;
		private JButton buildGym;
		private JButton buildHouse;
		private JButton buildHouse2;
		private JButton buildHouse3;
		private JButton buildShop;
		private JButton buildTree;
		private JButton buildTree2;
		private JButton buildWStone;
		private JButton buildWStone2;
		private JButton buildForest;

		public objectManager() {
			setLayout(null);
			setSize(TOP_WINDOW_WIDTH, TOP_WINDOW_HEIGHT);
			setResizable(false);
			//setLocationRelativeTo(null);
			setLocation(WINDOW_X + WINDOW_GAP + MAIN_WINDOW_WIDTH, WINDOW_Y);
			setTitle("Object Manager");
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			setIconImage(objects.getSubimage(64, 0, 32, 32));

			createObjBtn = new JButton("Create Object");
			createObjBtn.setBounds(40, 10, 120, 30);
			createObjBtn.addActionListener(this);
			add(createObjBtn);

			clearObjBtn = new JButton("Clear Object");
			clearObjBtn.setBounds(40, 50, 120, 30);
			clearObjBtn.addActionListener(this);
			add(clearObjBtn);

			buildCenter = new JButton("PC(5x5)");
			buildCenter.setBounds(0, 90, 95, 30);
			buildCenter.addActionListener(this);
			add(buildCenter);

			buildShop = new JButton("MRT(4x4)");
			buildShop.setBounds(buildCenter.getX() + 99, buildCenter.getY(), buildCenter.getWidth(), buildCenter.getHeight());
			buildShop.addActionListener(this);
			add(buildShop);

			buildGym = new JButton("GYM(6x5)");
			buildGym.setBounds(buildCenter.getX(), buildCenter.getY() + 40, buildCenter.getWidth(), buildCenter.getHeight());
			buildGym.addActionListener(this);
			add(buildGym);

			buildHouse = new JButton("HSE(5x5)");
			buildHouse.setBounds(buildShop.getX(), buildGym.getY(), buildCenter.getWidth(), buildCenter.getHeight());
			buildHouse.addActionListener(this);
			add(buildHouse);

			buildHouse2 = new JButton("HSE2(5x3)");
			buildHouse2.setBounds(buildCenter.getX(), buildGym.getY() + 40, buildCenter.getWidth(), buildCenter.getHeight());
			buildHouse2.addActionListener(this);
			add(buildHouse2);

			buildHouse3 = new JButton("HSE3(6x4)");
			buildHouse3.setBounds(buildShop.getX(), buildHouse2.getY(), buildCenter.getWidth(), buildCenter.getHeight());
			buildHouse3.addActionListener(this);
			add(buildHouse3);

			buildTree = new JButton("TRE(2x3)");
			buildTree.setBounds(buildCenter.getX(), buildHouse2.getY() + 40, buildCenter.getWidth(), buildCenter.getHeight());
			buildTree.addActionListener(this);
			add(buildTree);

			buildTree2 = new JButton("TRE2(2x3)");
			buildTree2.setBounds(buildShop.getX(), buildTree.getY(), buildCenter.getWidth(), buildCenter.getHeight());
			buildTree2.addActionListener(this);
			add(buildTree2);

			buildWStone = new JButton("STN(2x2)");
			buildWStone.setBounds(buildCenter.getX(), buildTree.getY() + 40, buildCenter.getWidth(), buildCenter.getHeight());
			buildWStone.addActionListener(this);
			add(buildWStone);

			buildWStone2 = new JButton("STN2(2x2)");
			buildWStone2.setBounds(buildShop.getX(), buildWStone.getY(), buildCenter.getWidth(), buildCenter.getHeight());
			buildWStone2.addActionListener(this);
			add(buildWStone2);

			buildForest = new JButton("Forest");
			buildForest.setBounds(40, buildWStone2.getY() + 40, 120, 30);
			buildForest.addActionListener(this);
			add(buildForest);

			setVisible(true);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == clearObjBtn) {
				cleanObj();
			} else if (e.getSource() == createObjBtn) {
				createObj();
			} else if (e.getSource() == buildHouse) {
				setObj(1);
			} else if (e.getSource() == buildHouse2) {
				setObj(2);
			} else if (e.getSource() == buildHouse3) {
				setObj(3);
			} else if (e.getSource() == buildCenter) {
				setObj(4);
			} else if (e.getSource() == buildShop) {
				setObj(5);
			} else if (e.getSource() == buildGym) {
				setObj(6);
			} else if (e.getSource() == buildTree) {
				setObj(7);
			} else if (e.getSource() == buildTree2) {
				setObj(8);
			} else if (e.getSource() == buildWStone) {
				setObj(9);
			} else if (e.getSource() == buildWStone2) {
				setObj(10);
			} else if (e.getSource() == buildForest) {
				createForest();
			}
		}

	}

	class tileManager extends JFrame implements ActionListener {

		private JButton[][] tileOutSetBtn;
		private JButton[][] tileInSetBtn;
		private JButton outBoxBtn;
		private JButton inBoxBtn;

		public tileManager() {
			setLayout(null);
			setSize(TOP_WINDOW_WIDTH, TOP_WINDOW_HEIGHT);
			setResizable(false);
			setTitle("Tile Manager");
			//setLocationRelativeTo(null);
			setLocation(WINDOW_X + WINDOW_GAP + WINDOW_GAP + MAIN_WINDOW_WIDTH + TOP_WINDOW_WIDTH, WINDOW_Y);
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			setIconImage(objects.getSubimage(64, 0, 32, 32));

			outBoxBtn = new JButton("Set Outer Box");
			outBoxBtn.setBounds(40, 10, 120, 30);
			outBoxBtn.addActionListener(this);
			add(outBoxBtn);

			inBoxBtn = new JButton("Set Inner Box");
			inBoxBtn.setBounds(40, 50, 120, 30);
			inBoxBtn.addActionListener(this);
			add(inBoxBtn);

			tileOutSetBtn = new JButton[3][3];
			for (int i = 0; i < tileOutSetBtn.length; i++) {
				for (int j = 0; j < tileOutSetBtn[i].length; j++) {
					tileOutSetBtn[i][j] = new JButton();
					tileOutSetBtn[i][j].setBounds(35 + (i * 45), 90 + (j * 45), 40, 40);
					tileOutSetBtn[i][j].addActionListener(this);
					add(tileOutSetBtn[i][j]);
				}
			}

			tileInSetBtn = new JButton[2][2];
			for (int i = 0; i < tileInSetBtn.length; i++) {
				for (int j = 0; j < tileInSetBtn[i].length; j++) {
					tileInSetBtn[i][j] = new JButton();
					tileInSetBtn[i][j].setBounds(60 + (i * 45), 230 + (j * 45), 40, 40);
					tileInSetBtn[i][j].addActionListener(this);
					add(tileInSetBtn[i][j]);
				}
			}

			setVisible(true);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == outBoxBtn) {
				setOutBox();
			} else if (e.getSource() == inBoxBtn) {
				setInBox();
			} else {
				setTile(e);
			}
		}

	}

	class pokemonManager extends JFrame implements ActionListener {

		private int[][] pokemonList;

		private JButton addPokemonBtn;
		private JButton delPokemonBtn;
		private JTextField minLevField;
		private JTextField maxLevField;
		private JTextField newPokemonField;
		private JTable pokemonListDisplay;
		private JScrollPane pokemonListScrollPane;
		private String pokemonInfo;

		public pokemonManager() {
			setLayout(null);
			setSize(BOT_WINDOW_WIDTH, BOT_WINDOW_HEIGHT);
			setResizable(false);
			setTitle("Pokemon Manager");
			setLocation(WINDOW_X + WINDOW_GAP + WINDOW_GAP + MAIN_WINDOW_WIDTH + BOT_WINDOW_WIDTH, WINDOW_Y + WINDOW_GAP + TOP_WINDOW_HEIGHT);
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			setIconImage(objects.getSubimage(64, 0, 32, 32));

			pokemonListDisplay = new JTable();
			pokemonListDisplay.setModel(new DefaultTableModel(new Object[][]{}, new String[]{"ID", "levelRange", "From", "To"}));
			pokemonListScrollPane = new JScrollPane(pokemonListDisplay);
			pokemonListScrollPane.setBounds(1, 1, 194, 105);
			pokemonListDisplay.setColumnSelectionAllowed(false);
			ListSelectionModel cellSelectionModel = pokemonListDisplay.getSelectionModel();
			cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					String selectedData = null;

					int selectedRow = pokemonListDisplay.getSelectedRow();

					if (selectedRow != -1) {
						DefaultTableModel model = (DefaultTableModel) pokemonListDisplay.getModel();

						String start = (String) model.getValueAt(selectedRow, 2);
						start = start.substring(1, start.length() - 1);

						String end = (String) model.getValueAt(selectedRow, 3);
						end = end.substring(1, end.length() - 1);

						xStart = Integer.parseInt(start.split(",")[0]);
						yStart = Integer.parseInt(start.split(",")[1]);

						xEnd = Integer.parseInt(end.split(",")[0]);
						yEnd = Integer.parseInt(end.split(",")[1]);

						windowGeneral.xCoordsDisplay.setText("START: (" + xStart + "," + yStart + ")");
						windowGeneral.yCoordsDisplay.setText("END: (" + xEnd + "," + yEnd + ")");

						highlight(xEnd, yEnd);
					}
				}

			});

			add(pokemonListScrollPane);

			newPokemonField = new JTextField();
			newPokemonField.setBounds(60, 110, 80, 30);
			add(newPokemonField);

			addPokemonBtn = new JButton("Add");
			addPokemonBtn.setBounds(10, 150, 80, 30);
			addPokemonBtn.addActionListener(this);
			add(addPokemonBtn);

			delPokemonBtn = new JButton("Del");
			delPokemonBtn.setBounds(100, 150, 80, 30);
			delPokemonBtn.addActionListener(this);
			add(delPokemonBtn);

			minLevField = new JTextField();
			minLevField.setBounds(10, 190, 80, 30);
			add(minLevField);

			maxLevField = new JTextField();
			maxLevField.setBounds(100, 190, 80, 30);
			add(maxLevField);

			pokemonList = new int[151][7];
			for (int i = 0; i < pokemonList.length; i++) {
				pokemonList[i][0] = 0;
			}

			refreshInformation();

			setVisible(true);
		}

		public void refreshPokemonList(String info) {

			pokemonList = new int[151][7];
			for (int i = 0; i < pokemonList.length; i++) {
				pokemonList[i][0] = 0;
			}
			if (info.compareTo("") != 0) {
				String[] list = info.split(";");
				for (int i = 0; i < list.length; i++) {
					int num = Integer.parseInt(list[i].split(":")[1]);
					num = num - 1;
					String[] clusterFuck = list[i].split(":")[0].split(",");

					pokemonList[num][0] = 1;

					pokemonList[num][1] = Integer.parseInt(clusterFuck[0].split("-")[0]);
					pokemonList[num][2] = Integer.parseInt(clusterFuck[0].split("-")[1]);
					pokemonList[num][3] = Integer.parseInt(clusterFuck[1].split("-")[0]);
					pokemonList[num][4] = Integer.parseInt(clusterFuck[1].split("-")[1]);
					pokemonList[num][5] = Integer.parseInt(clusterFuck[2].split("-")[0]);
					pokemonList[num][6] = Integer.parseInt(clusterFuck[2].split("-")[1]);
				}
			}
			refreshInformation();
		}

		public void refreshInformation() {
			DefaultTableModel model = (DefaultTableModel) pokemonListDisplay.getModel();
			pokemonInfo = "";
			model.setRowCount(0);

			for (int i = 0; i < pokemonList.length; i++) {
				if (pokemonList[i][0] == 1) {
					String represent = pokemonList[i][1] + "-" + pokemonList[i][2] + "," + pokemonList[i][3] + "-" + pokemonList[i][4] + "," + pokemonList[i][5] + "-" + pokemonList[i][6] + ":" + (i + 1);
					model.addRow(new Object[]{(i + 1), pokemonList[i][1] + "-" + pokemonList[i][2], "(" + pokemonList[i][3] + "," + pokemonList[i][5] + ")", "(" + pokemonList[i][4] + "," + pokemonList[i][6] + ")"});
					pokemonInfo = pokemonInfo + represent + ";";
				}
			}
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == addPokemonBtn) {
				try {
					int num = Integer.parseInt(newPokemonField.getText());
					num = num - 1;
					pokemonList[num][0] = 1;
					pokemonList[num][1] = Integer.parseInt(minLevField.getText());
					pokemonList[num][2] = Integer.parseInt(maxLevField.getText());
					pokemonList[num][3] = xStart;
					pokemonList[num][4] = xEnd;
					pokemonList[num][5] = yStart;
					pokemonList[num][6] = yEnd;
					newPokemonField.setText("");
					refreshInformation();
				} catch (NumberFormatException ex) {
				}
			} else if (e.getSource() == delPokemonBtn) {
				try {
					int num = Integer.parseInt(newPokemonField.getText());
					num = num - 1;
					pokemonList[num][0] = 0;

					newPokemonField.setText("");
					refreshInformation();
				} catch (NumberFormatException ex) {
				}
			}
		}

	}

	class generalControls extends JFrame implements ActionListener {

		private JLabel xCoordsDisplay;
		private JLabel yCoordsDisplay;
		private JButton saveMapBtn;
		private JButton loadMapBtn;
		private JButton nextSetBtn;
		private JButton prevSetBtn;
		private JButton nextTileBtn;
		private JButton prevTileBtn;
		private JLabel dimDisplay;
		private JTextField mapIDx;
		private JTextField mapIDy;

		public generalControls() {
			setLayout(null);
			setSize(BOT_WINDOW_WIDTH, BOT_WINDOW_HEIGHT);
			setResizable(false);
			setTitle("General Controls");
			//	setLocationRelativeTo(null);
			setLocation(WINDOW_X + WINDOW_GAP + MAIN_WINDOW_WIDTH, WINDOW_Y + WINDOW_GAP + TOP_WINDOW_HEIGHT);
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			setIconImage(objects.getSubimage(64, 0, 32, 32));

			xCoordsDisplay = new JLabel("START: (" + xStart + "," + yStart + ")");
			xCoordsDisplay.setBounds(10, 10, 140, 20);
			add(xCoordsDisplay);

			yCoordsDisplay = new JLabel("END: (" + xEnd + "," + yEnd + ")");
			yCoordsDisplay.setBounds(10, 30, 140, 20);
			add(yCoordsDisplay);

			nextSetBtn = new JButton("Next Set");
			nextSetBtn.setBounds(100, 50, 90, 30);
			nextSetBtn.addActionListener(this);
			add(nextSetBtn);

			prevSetBtn = new JButton("Prev Set");
			prevSetBtn.setBounds(5, 50, 90, 30);
			prevSetBtn.addActionListener(this);
			add(prevSetBtn);

			nextTileBtn = new JButton("Next Obj");
			nextTileBtn.setBounds(100, 90, 90, 30);
			nextTileBtn.addActionListener(this);
			add(nextTileBtn);

			prevTileBtn = new JButton("Prev Obj");
			prevTileBtn.setBounds(5, 90, 90, 30);
			prevTileBtn.addActionListener(this);
			add(prevTileBtn);

			mapIDx = new JTextField("0");
			mapIDx.setBounds(5, 150, 90, 40);
			add(mapIDx);

			mapIDy = new JTextField("0");
			mapIDy.setBounds(100, 150, 90, 40);
			add(mapIDy);

			saveMapBtn = new JButton("Save");
			saveMapBtn.setBounds(5, 200, 90, 40);
			saveMapBtn.addActionListener(this);
			add(saveMapBtn);

			loadMapBtn = new JButton("Load");
			loadMapBtn.setBounds(100, 200, 90, 40);
			loadMapBtn.addActionListener(this);
			add(loadMapBtn);

			dimDisplay = new JLabel("(" + (xEnd + 1 - xStart) + "x" + (yEnd + 1 - yStart) + ")");
			dimDisplay.setBounds(75, 125, 140, 20);
			add(dimDisplay);

			setVisible(true);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == nextSetBtn) {
				nextSet("Tile");
			} else if (e.getSource() == prevSetBtn) {
				prevSet("Tile");
			} else if (e.getSource() == prevTileBtn) {
				prevTile("Object");
			} else if (e.getSource() == nextTileBtn) {
				nextTile("Object");
			} else if (e.getSource() == saveMapBtn) {
				writeMapFile();
			} else if (e.getSource() == loadMapBtn) {
				loadMapFile();
			}
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}

	//<editor-fold defaultstate="collapsed" desc="Window Overrides">
	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
		windowTile.dispose();
		windowPokemon.dispose();
		windowGeneral.dispose();
		windowObject.dispose();
	}

	@Override
	public void windowIconified(WindowEvent e) {
		windowTile.setState(JFrame.ICONIFIED);
		windowPokemon.setState(JFrame.ICONIFIED);
		windowGeneral.setState(JFrame.ICONIFIED);
		windowObject.setState(JFrame.ICONIFIED);
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		windowTile.setState(JFrame.NORMAL);
		windowPokemon.setState(JFrame.NORMAL);
		windowGeneral.setState(JFrame.NORMAL);
		windowObject.setState(JFrame.NORMAL);
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="Mouse Overrides">
	@Override
	public void mouseClicked(MouseEvent e) {
		int[] receive = caller(e);
		xStart = receive[0];
		yStart = receive[1];
		xEnd = receive[0];
		yEnd = receive[1];
		highlight(xEnd, yEnd);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int[] receive = caller(e);
		xStart = receive[0];
		yStart = receive[1];
		isSelecting = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int[] receive = caller(e);

		xEnd = receive[0];
		yEnd = receive[1];

		if (xEnd < xStart) {
			int temp = xEnd;
			xEnd = xStart;
			xStart = temp;
		}
		if (yEnd < yStart) {
			int temp = yEnd;
			yEnd = yStart;
			yStart = temp;
		}

		windowGeneral.xCoordsDisplay.setText("START: (" + xStart + "," + yStart + ")");
		windowGeneral.yCoordsDisplay.setText("END: (" + xEnd + "," + yEnd + ")");

		isSelecting = false;
		//highlight();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (isSelecting) {
			int[] receive = caller(e);
			/*
		xEnd = receive[0];
		yEnd = receive[1];
		
		if (xEnd<xStart){
			int temp=xEnd;
			xEnd=xStart;
			xStart=temp;
		}
		if (yEnd<yStart){
			int temp=yEnd;
			yEnd=yStart;
			yStart=temp;
		}
		
		xCoordsDisplay.setText("START: ("+xStart+","+yStart+")");
		yCoordsDisplay.setText("END: ("+xEnd+","+yEnd+")");
			 */
			highlight(receive[0], receive[1]);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (isSelecting) {
			int[] receive = caller(e);
			/*
		xEnd = receive[0];
		yEnd = receive[1];
		
		if (xEnd<xStart){
			int temp=xEnd;
			xEnd=xStart;
			xStart=temp;
		}
		if (yEnd<yStart){
			int temp=yEnd;
			yEnd=yStart;
			yStart=temp;
		}
		
		xCoordsDisplay.setText("START: ("+xStart+","+yStart+")");
		yCoordsDisplay.setText("END: ("+xEnd+","+yEnd+")");
			 */
			highlight(receive[0], receive[1]);
		}
	}

	public int[] caller(MouseEvent e) {
		return getPos(e.getComponent().getX() + e.getX(), e.getComponent().getY() + e.getY());
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="Key Overrides">
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			clearHighlight();
		} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			xStart = 0;
			yStart = 0;
			xEnd = 19;
			yEnd = 19;
			highlight(xEnd, yEnd);
		} else if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {

			if (e.getModifiers() != KeyEvent.CTRL_MASK) {
				yStart = yStart - 1;
				if (yStart < 0) {
					yStart = 0;
				}
			}

			if (e.getModifiers() != KeyEvent.SHIFT_MASK || e.getModifiers() == KeyEvent.CTRL_MASK) {
				yEnd = yEnd - 1;
				if (yEnd < yStart) {
					yEnd = yStart;
				}
			}
			highlight(xEnd, yEnd);
		} else if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {

			if (e.getModifiers() != KeyEvent.SHIFT_MASK) {
				yEnd = yEnd + 1;
				if (yEnd > 19) {
					yEnd = 19;
				}
			}

			if (e.getModifiers() != KeyEvent.CTRL_MASK || e.getModifiers() == KeyEvent.SHIFT_MASK) {
				yStart = yStart + 1;
				if (yStart > yEnd) {
					yStart = yEnd;
				}
			}
			highlight(xEnd, yEnd);
		} else if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {

			if (e.getModifiers() != KeyEvent.CTRL_MASK) {
				xStart = xStart - 1;
				if (xStart < 0) {
					xStart = 0;
				}
			}

			if (e.getModifiers() != KeyEvent.SHIFT_MASK || e.getModifiers() == KeyEvent.CTRL_MASK) {
				xEnd = xEnd - 1;
				if (xEnd < xStart) {
					xEnd = xStart;
				}
			}
			highlight(xEnd, yEnd);
		} else if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {

			if (e.getModifiers() != KeyEvent.SHIFT_MASK) {
				xEnd = xEnd + 1;
				if (xEnd > 19) {
					xEnd = 19;
				}
			}

			if (e.getModifiers() != KeyEvent.CTRL_MASK || e.getModifiers() == KeyEvent.SHIFT_MASK) {
				xStart = xStart + 1;
				if (xStart > xEnd) {
					xStart = xEnd;
				}
			}
			highlight(xEnd, yEnd);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
	//</editor-fold>
}
