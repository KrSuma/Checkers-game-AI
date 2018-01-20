
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
		int isSpecial() {
			if (getState()==State.BLACK || getState()==State.RED)
				return 0;
			else
				return 1;
		}
		int StateToInt() {
			if (getState()==State.RED || getState()==State.REDSPECIAL)
				return 0;
			else
				return 1;
		}
		void changeState(State newState) {
			state = newState;
		}
	}