package helpers;

public class Coordinate {
	private int x;
	private int y;
	
	public Coordinate(int x, int y) {
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

        final Coordinate other = (Coordinate) obj;
        if(x!=other.getX()) {
        	return false;
        }
        
        if(y!=other.getY()) {
        	return false;
        }
        return true;
    }
	public double distanceTo(Coordinate pos2) {
		double deltaX = pos2.getX() - this.getX();
		double deltaY = pos2.getY() - this.getY();

		return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
	}
}
