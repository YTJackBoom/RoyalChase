package helpers;

import java.io.Serializable;

public class AbsoluteCoordinate implements Serializable {
	protected int x;
	protected int y;

	public AbsoluteCoordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

		final AbsoluteCoordinate other = (AbsoluteCoordinate) obj;
		if (x != other.getX()) {
			return false;
		}

		if (y != other.getY()) {
			return false;
		}
		return true;
	}

	public double distanceTo(AbsoluteCoordinate pos2) {
		double deltaX = pos2.getX() - this.getX();
		double deltaY = pos2.getY() - this.getY();

		return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
	}

}
