package scenes;

/**
 * Enum für die verschiedenen GameStates^(=GameScene)
 */
public enum GameStates {
	MENU, PLAYING, SETTINGS, GAMEOVER, LEVELCLEARED,LEVELSELECT,TUTORIAL,TOWN;
	
	public static GameStates gameState = MENU;
}
