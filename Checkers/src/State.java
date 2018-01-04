public enum State {
	EMPTY, RED(0), BLACK(1), REDSPECIAL(0), BLACKSPECIAL(1), UNAVAILABLE;
	int color;
	State() {color = -1;}
	State(int _color) {color = _color;};
	int getColor() {return color;}
};