package game;

import java.util.ArrayList;
import java.util.Random;

public class Platform {
	private Cell cells[][];
	private boolean[][] previousState;
	private int cellSize;
	private long born;

	private long dead;

	public Platform(int size, int width, int height) {
		born = 0;
		dead = 0;
		cellSize = size;
		cells = new Cell[width / cellSize][height / cellSize];
		previousState = new boolean[width / cellSize][height / cellSize];

		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				cells[i][j] = new Cell(i * cellSize, j * size, cellSize);
				previousState[i][j] = false;
			}
		}
	}

	public void updateAll() {
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				previousState[i][j] = cells[i][j].isAlive();

			}
		}
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				updateCell(cells[i][j]);

			}
		}
	}

	public ArrayList<Cell> getCells() {

		ArrayList<Cell> cellArray = new ArrayList<>();
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				cellArray.add(cells[i][j]);
			}
		}
		return cellArray;
	}

	private void updateCell(Cell cell) {
		int x = cell.getXPos();
		int y = cell.getYPos();

		int neighbours = numOfAliveNeighbours(x, y);
		if (cell.isAlive() && neighbours < 2 || neighbours > 3) {
			cell.off();
			dead++;
		} else if (!cell.isAlive() && neighbours == 3) {
			cell.on();
			born++;
		}
	}

	private boolean indexInRange(int index, int length) {
		return index < length && index >= 0;
	}

	private int numOfAliveNeighbours(int x, int y) {
		int numOfAliveNeighbours = 0;
		boolean xInRange = indexInRange(x - 1, previousState.length);
		boolean yInRange = indexInRange(y, previousState[y].length);
		if (xInRange && yInRange && previousState[x - 1][y]) {
			numOfAliveNeighbours++;
		}

		xInRange = indexInRange(x + 1, previousState.length);
		yInRange = indexInRange(y, previousState[y].length);
		if (xInRange && yInRange && previousState[x + 1][y]) {
			numOfAliveNeighbours++;
		}

		xInRange = indexInRange(x, previousState.length);
		yInRange = indexInRange(y - 1, previousState[y].length);
		if (xInRange && yInRange && previousState[x][y - 1]) {
			numOfAliveNeighbours++;
		}

		xInRange = indexInRange(x, previousState.length);
		yInRange = indexInRange(y + 1, previousState[y].length);
		if (xInRange && yInRange && previousState[x][y + 1]) {
			numOfAliveNeighbours++;
		}

		xInRange = indexInRange(x - 1, previousState.length);
		yInRange = indexInRange(y + 1, previousState[y].length);
		if (xInRange && yInRange && previousState[x - 1][y + 1]) {
			numOfAliveNeighbours++;
		}

		xInRange = indexInRange(x + 1, previousState.length);
		yInRange = indexInRange(y + 1, previousState[y].length);
		if (xInRange && yInRange && previousState[x + 1][y + 1]) {
			numOfAliveNeighbours++;
		}

		xInRange = indexInRange(x - 1, previousState.length);
		yInRange = indexInRange(y - 1, previousState[y].length);
		if (xInRange && yInRange && previousState[x - 1][y - 1]) {
			numOfAliveNeighbours++;
		}

		xInRange = indexInRange(x + 1, previousState.length);
		yInRange = indexInRange(y - 1, previousState[y].length);
		if (xInRange && yInRange && previousState[x + 1][y - 1]) {
			numOfAliveNeighbours++;
		}

		return numOfAliveNeighbours;
	}

	public void reset() {

		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				previousState[i][j] = false;
				cells[i][j].off();
			}
		}
		born = 0;
		dead = 0;
	}

	public void toggleCell(double d, double e) {
		
		int x = (int) d / cellSize;
		int y = (int) e / cellSize;
		if (cells[x][y].isAlive()) {
			previousState[x][y] = false;
			cells[x][y].off();
		} else {
			previousState[x][y] = true;
			cells[x][y].on();
		}
	}

	public void randomize() {
		Random rand = new Random();
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				if (rand.nextInt(1001) < 50) {
					previousState[i][j] = true;
					cells[i][j].on();
				} else {
					previousState[i][j] = false;
					cells[i][j].off();
				}
			}
			if (rand.nextInt(1001) < 100) {
				i = i + 5;
			}
		}
	}

	public long getBorn() {
		return born;
	}

	public long getDead() {
		return dead;
	}
}
