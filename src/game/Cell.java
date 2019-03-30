package game;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

public class Cell extends Rectangle {
	private boolean isAlive;

	public Cell(int x, int y, double size) {
		this.setWidth(size);
		this.setHeight(size);
		this.setX(x);
		this.setY(y);
		isAlive = false;
		this.setStrokeType(StrokeType.INSIDE);
		this.setStroke(Color.web("#696969"));
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void on() {
		isAlive = true;
		this.setFill(Color.GREENYELLOW);
	}

	public void off() {
		isAlive = false;
		this.setFill(Color.BLACK);
	}

	public int getXPos() {
		return (int) (this.getX() / this.getWidth());
	}

	public int getYPos() {
		return (int) (this.getY() / this.getHeight());
	}
}
