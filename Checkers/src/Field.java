
public class Field {
	private
		State state;
	public 
		Field(State _state) {
			state = _state;
		};
		Field(){
			state = State.EMPTY;
		};
		State getState() {return state;}
	}