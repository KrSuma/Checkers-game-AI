
public class FieldID {
	private
		int x,y;
		boolean outOfRange;
		FieldID changeXY(int newX, int newY) {
			x = newX;
			y = newY;
			outOfRange = checkRange(x,y);
			return this;
		}
		boolean checkRange(int _x, int _y) {
			if (_x>7 || _x<0 || _y>7 || _y<0) 
				return true;
			else
				return false;
		}
	public
		FieldID(int _x, int _y) {x = _x; y =_y; outOfRange=checkRange(x,y);}
		FieldID() {x = -1; y = -1; outOfRange = true;};
		int getX() {return x;}
		int getY() {return y;}
		public String toString() {
			return ("x: " + getX() + " y: " + getY());
		}
		
}
