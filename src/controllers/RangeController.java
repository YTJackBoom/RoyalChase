package controllers;

import gameObjects.Tower;
import helpers.Circle;
import helpers.Constants;
import helpers.Coordinate;
import helpers.variables;
import scenes.Playing;

import java.awt.*;

public class RangeController implements ControllerMethods{ //just to rander the circles for ranges
	private Playing playing;
	private Tower pointer;
	private int draggedType;
	private int mouseY,mouseX;
	public RangeController(Playing playing) {
		this.playing = playing;
	}
	@Override
	public void workAddQueue() {

	}

	@Override
	public void workRemoveQueue() {

	}

	@Override
	public void render(Graphics g) {
		if (pointer != null) {
			g.setColor(Color.BLACK);
			pointer.renderRange(g);
		}
		if (playing.getDragingTower()) {
			int range = variables.Towers.getTowerRange(draggedType);
//			Circle [] circles = new Circle[Constants.UIConstants.NUMBEROFRANGECIRCLES];

			for(int i = 0; i<Constants.UIConstants.NUMBEROFRANGECIRCLES; i++) {
				Circle c = new Circle(new Coordinate(mouseX,mouseY),range- Constants.UIConstants.NUMBEROFRANGECIRCLES +i);
				c.render(g);
			}
		}
	}

	@Override
	public void update() {}

	public void setPointer(Tower selectedTower) {
		pointer = selectedTower;
	}
	public void setDraggedPointer(int type) {
		this.draggedType = type;
	}
	public void mouseDragged(int x, int y) {
		mouseX = x;
		mouseY = y;
	}
}
