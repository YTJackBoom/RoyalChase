package gameObjects;

import javax.swing.JPanel;

import basics.GameScreen;

public abstract class  GameObject {
 private int height;
 private int width;
 private int range;
 private Coordinate pos;
 private boolean agro; 
 private int movingDirection; //0=nicht,1=rechts,2=unten,3=links
 private int type;
 private JPanel gS;
public GameObject(JPanel GameScreen, Coordinate pos, int width, int height, int type, int range) {
	 
	 this.setPos(pos);
	 this.setWidth(width);
	 this.setHeight(height);
	 this.setType(type);
	 this.setRange(range);
	 this.range = range;
	 this.gS = GameScreen;
	 this.pos = pos;
	 
 }




public Coordinate getPos() {
	return pos;
}


public abstract void setPos(Coordinate pos);


public double getWidth() {
	return width;
}


public void setWidth(int width) {
	this.width = width;
}


public double getHeight() {
	return height;
}


public void setHeight(int height) {
	this.height = height;
}


public boolean isAgro() {
	return agro;
}


public void setAgro(boolean agro) {
	this.agro = agro;
}

public int getMovingDirection() {
	return movingDirection;
}

public void setType(int type) {
	this.type = type;
}

public void setRange(int range) {
	this.range = range;
}


public void setMovingDirection(int movingDirection) {
	this.movingDirection = movingDirection;
}
public abstract void vanish();

public int getType() {
	return type;
}
public int getRange() {
	return range;
}
}

