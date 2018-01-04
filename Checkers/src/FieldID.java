
public class FieldID {
	private
		int x,y;
		FieldID changeXY(int newX, int newY) {
			x = newX;
			y = newY;
			return this;
		}
	public
		FieldID(int _x, int _y) {x = _x; y =_y;}
		FieldID() {x = -1; y = -1;};
		int getX() {return x;}
		int getY() {return y;}
		public String toString() {
			return ("x: " + getX() + " y: " + getY());
		}
		
}
