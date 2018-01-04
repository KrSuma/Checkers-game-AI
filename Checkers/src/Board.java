import java.util.ArrayList;

public class Board {
	private
		Field gridArray[][] = new Field[8][8];
		Field getField(FieldID id) {return gridArray[id.getX()][id.getY()];}
		Field getField(int x, int y) {return gridArray[x][y];}
		Field accessField(FieldID id, int player) {
			Field wanted = getField(id);
			State fieldState = wanted.getState();
			if (player == 0)
				if (fieldState==State.RED || fieldState==State.REDSPECIAL )
					return wanted;
				else 
					return null;
			else if (player == 1)
				if (fieldState==State.BLACK || fieldState==State.BLACKSPECIAL )
					return wanted;
				else 
					return null;
			else
				return null;
			
		}
		boolean isBorderField(FieldID id) {
			if (id.getX() == 0 || id.getX() == 7 || id.getY() == 0 ||id.getY() == 7)
				return true;
			else
				return false;
		}
		int whichBorder(FieldID id) {
			if (id.getX() == 0) 
				return 2;
			else if (id.getX() == 7)
				return 0;
			else if (id.getY() == 0)
				return 1;
			else
				return 3;
		}
		boolean isOpponent(FieldID start, FieldID toCheck) {
			if ((getField(toCheck).getState().getColor() != getField(start).getState().getColor()) && getField(toCheck).getState().getColor() != -1)
				return true;
			else
				return false;
		}
		
		int isKillable(FieldID toKill, FieldID opponent) {
			if (isBorderField(toKill))
				return 0;
			int posX = toKill.getX() - opponent.getX();
			int posY = toKill.getY() - opponent.getY();
			int numberOfFields = 0;
			if (posX < 0) {
				if (posY < 0)
					if (getField(toKill.getX()-1, toKill.getY()-1).getState() == State.EMPTY)
						numberOfFields++;
				else
					if (getField(toKill.getX()-1, toKill.getY()+1).getState() == State.EMPTY)
						numberOfFields++;
			}
			else {
				if (posY < 0)
					if (getField(toKill.getX()+1, toKill.getY()-1).getState() == State.EMPTY)
						numberOfFields++;
				else
					if (getField(toKill.getX()+1, toKill.getY()+1).getState() == State.EMPTY)
						numberOfFields++;
			}
			return numberOfFields;
		
		}
		
		boolean hasKillableOpponents(FieldID id) {
			int x = id.getX(), y = id.getY();
			FieldID temp = new FieldID();
			if (!isBorderField(id)) {
				if (isOpponent(id, temp.changeXY(x+1, y+1)))
					if (isKillable(temp, id)>0)
						return true;
				if (isOpponent(id, temp.changeXY(x+1, y-1)))
					if (isKillable(temp, id)>0)
						return true;
				if (isOpponent(id, temp.changeXY(x-1, y+1)))
					if (isKillable(temp, id)>0)
						return true;
				if (isOpponent(id, temp.changeXY(x-1, y-1)))
					if (isKillable(temp, id)>0)
						return true;
			}
			else {
				switch(whichBorder(id)) {
					case 0:
						if (id.getY()==0) {
							if (isOpponent(id, temp.changeXY(x-1, y+1)))
								if (isKillable(temp, id)>0)
									return true;
							}
						else {
							if (isOpponent(id, temp.changeXY(x-1, y+1)))
								if (isKillable(temp, id)>0)
									return true;
							if (isOpponent(id, temp.changeXY(x-1, y-1)))
								if (isKillable(temp, id)>0)
									return true;
							}
						break;
					case 1:
						if (isOpponent(id, temp.changeXY(x-1, y+1)))
							if (isKillable(temp, id)>0)
								return true;
						if (isOpponent(id, temp.changeXY(x+1, y+1)))
							if (isKillable(temp, id)>0)
								return true;
						break;
					case 2:
						if (id.getY() == 7) {
							if (isOpponent(id, temp.changeXY(x+1, y-1)))
								if (isKillable(temp, id)>0)
									return true;
							}
						else {
							if (isOpponent(id, temp.changeXY(x+1, y+1)))
								if (isKillable(temp, id)>0)
									return true;
							if (isOpponent(id, temp.changeXY(x+1, y-1)))
								if (isKillable(temp, id)>0)
									return true;
							}
						break;
					case 3:
						if (isOpponent(id, temp.changeXY(x+1, y-1)))
							if (isKillable(temp, id)>0)
								return true;
						if (isOpponent(id, temp.changeXY(x-1, y-1)))
							if (isKillable(temp, id)>0)
								return true;
						break;
				}
			}
			return false;			
		}
		FieldID goodMove(FieldID end, FieldID start, boolean hasKillableOpponents) {
			if (end.outOfRange)
				return null;
			State fieldState = getField(end).getState();
			if (!hasKillableOpponents)	
				if (fieldState==State.EMPTY)
					return end;
			else 
				if (isOpponent(start, end))
					if (isKillable(end, start) > 0)
						return end;
			return null;		
		}
		
		ArrayList<FieldID> availableMoves(FieldID id, int player){
			ArrayList<FieldID> moves = new ArrayList<FieldID>();
			FieldID temp;
			int x = id.getX(), y=id.getY(), offset;
			boolean hasKillableOpponents = hasKillableOpponents(id);
			if (player == 0) 
				offset = 1;
			else
				offset = -1;
			switch(getField(id).isSpecial()) {
				case 0:
					for (int i=-1; i<3; i+=2) {
						temp = new FieldID();
						temp = goodMove(temp.changeXY(x+offset, y+i), id, hasKillableOpponents);
						if (temp!=null)
							moves.add(temp);
					};
					break;
				case 1:
					for (int i=-1; i<3; i+=2) {
						temp = new FieldID();
						temp = goodMove(temp.changeXY(x+offset, y+i), id, hasKillableOpponents);
						if (temp!=null)
							moves.add(temp);
						temp = goodMove(temp.changeXY(x-offset, y+i), id, hasKillableOpponents);
						if (temp!=null)
							moves.add(temp);
					};
					break;
			}
			
			return moves;
		}
		
	public
		Board() {
				for (int i=0; i<8; i++) {
					if (i%2==0) {
						gridArray[0][i] = new Field(State.UNAVAILABLE);
						gridArray[1][i] = new Field(State.RED);
						gridArray[2][i] = new Field(State.UNAVAILABLE);
						gridArray[3][i] = new Field(State.EMPTY);
						gridArray[4][i] = new Field(State.UNAVAILABLE);
						gridArray[5][i] = new Field(State.BLACK);
						gridArray[6][i] = new Field(State.UNAVAILABLE);
						gridArray[7][i] = new Field(State.BLACK);
					}
					else {
						gridArray[0][i] = new Field(State.RED);
						gridArray[1][i] = new Field(State.UNAVAILABLE);
						gridArray[2][i] = new Field(State.RED);
						gridArray[3][i] = new Field(State.UNAVAILABLE);
						gridArray[4][i] = new Field(State.EMPTY);
						gridArray[5][i] = new Field(State.UNAVAILABLE);
						gridArray[6][i] = new Field(State.BLACK);
						gridArray[7][i] = new Field(State.UNAVAILABLE);
				}
			}
			
	};
		void print() {
			char arr[] = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
			for (int i=0; i<9; i++) { 
				if (i!=0)
					System.out.print(i + " ");
				else
					System.out.print("   ");
			}
			System.out.println();
			System.out.println();
			for (int i=0; i<8; i++) {
				System.out.print(arr[i]+ "  ");
				for (int j=0; j<8; j++) {
					if (gridArray[i][j].getState()==State.RED)
						System.out.print("R ");
					else if (gridArray[i][j].getState()==State.BLACK)
						System.out.print("B ");
					else if (gridArray[i][j].getState()==State.EMPTY)
						System.out.print("X ");
					else
						System.out.print("O ");
					if (j==7)
						System.out.println();
				}
			}
		}
};
