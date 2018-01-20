import java.util.ArrayList;

public class Board {
	private
		Field gridArray[][] = new Field[8][8];
		ArrayList<FieldID> RedPieces = new ArrayList<FieldID>();
		ArrayList<FieldID> BlackPieces = new ArrayList<FieldID>();
		ArrayList<FieldID> RedKills = new ArrayList<FieldID>();
		ArrayList<FieldID> BlackKills = new ArrayList<FieldID>();
		ArrayList<FieldID> RedRegular = new ArrayList<FieldID>();
		ArrayList<FieldID> BlackRegular = new ArrayList<FieldID>();
		boolean NoBlackMoves = false;
		boolean NoRedMoves = false;
		Field getField(FieldID id) {return gridArray[id.getX()][id.getY()];}
		Field getField(int x, int y) {return gridArray[x][y];}
		
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
				if (posY < 0) {
					if (getField(toKill.getX()-1, toKill.getY()-1).getState() == State.EMPTY)
						numberOfFields++;
					}
				else  {
					if (getField(toKill.getX()-1, toKill.getY()+1).getState() == State.EMPTY)
						numberOfFields++;
					}
			}
			else {
				if (posY < 0) {
					if (getField(toKill.getX()+1, toKill.getY()-1).getState() == State.EMPTY)
						numberOfFields++;
					}
				else {
					if (getField(toKill.getX()+1, toKill.getY()+1).getState() == State.EMPTY)
						numberOfFields++;
					}
			}
			return numberOfFields;
		
		}
		
		boolean hasKillableOpponents(FieldID id) {
			int x = id.getX(), y = id.getY();
			FieldID temp = new FieldID();
			if (!isBorderField(id)) {
				if (isOpponent(id, temp.changeXY(x+1, y+1))) {
					if (isKillable(temp, id)>0)
						return true;
					}
				if (isOpponent(id, temp.changeXY(x+1, y-1))) {
					if (isKillable(temp, id)>0)
						return true;
					}
				if (isOpponent(id, temp.changeXY(x-1, y+1))) {
					if (isKillable(temp, id)>0)
						return true;
					}
				if (isOpponent(id, temp.changeXY(x-1, y-1))) {
					if (isKillable(temp, id)>0)
						return true;
					}
			}
			else {
				switch(whichBorder(id)) {
					case 0:
						if (id.getY()==0) {
							if (isOpponent(id, temp.changeXY(x-1, y+1))) {
								if (isKillable(temp, id)>0)
									return true;
								}
							}
						else {
							if (isOpponent(id, temp.changeXY(x-1, y+1))) {
								if (isKillable(temp, id)>0)
									return true;
								}
							if (isOpponent(id, temp.changeXY(x-1, y-1))) {
								if (isKillable(temp, id)>0)
									return true;
								}
							}
						break;
					case 1:
						if (isOpponent(id, temp.changeXY(x-1, y+1))) {
							if (isKillable(temp, id)>0)
								return true;
							}
						if (isOpponent(id, temp.changeXY(x+1, y+1))) {
							if (isKillable(temp, id)>0)
								return true;
							}
						break;
					case 2:
						if (id.getY() == 7) {
							if (isOpponent(id, temp.changeXY(x+1, y-1))) {
								if (isKillable(temp, id)>0)
									return true;
								}
							}
						else {
							if (isOpponent(id, temp.changeXY(x+1, y+1))) {
								if (isKillable(temp, id)>0)
									return true;
								}
							if (isOpponent(id, temp.changeXY(x+1, y-1))) {
								if (isKillable(temp, id)>0)
									return true;
								}
							}
						break;
					case 3:
						if (isOpponent(id, temp.changeXY(x+1, y-1))) {
							if (isKillable(temp, id)>0)
								return true;
							}
						if (isOpponent(id, temp.changeXY(x-1, y-1))) {
							if (isKillable(temp, id)>0)
								return true;
							}
						break;
				}
			}
			return false;			
		}
		FieldID goodMove(FieldID end, FieldID start, boolean hasKillableOpponents) {
			if (end.outOfRange)
				return null;
			State fieldState = getField(end).getState();
			if (!hasKillableOpponents) {
				if (fieldState==State.EMPTY)
					return end;
				}
			else { 
				if (isOpponent(start, end))
					if (isKillable(end, start) > 0)
						return goodMove(start.changeXY(start.getX() - (start.getX()-end.getX())*2, start.getY() - (start.getY()-end.getY())*2), end, false);
				}
			return null;		
		}
		
		ArrayList<FieldID> FieldAvailableMoves(FieldID id, int player){
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
						temp = goodMove(temp.changeXY(x+offset, y+i), new FieldID(x,y), hasKillableOpponents);
						if (temp!=null)
							moves.add(temp);
					};
					break;
				case 1:
					for (int i=-1; i<3; i+=2) {
						temp = new FieldID();
						temp = goodMove(temp.changeXY(x+offset, y+i), new FieldID(x,y), hasKillableOpponents);
						if (temp!=null)
							moves.add(temp);
						temp = new FieldID();
						temp = goodMove(temp.changeXY(x-offset, y+i), new FieldID(x,y), hasKillableOpponents);
						if (temp!=null)
							moves.add(temp);
					};
					break;
			}
			
			return moves;
		}
		

		void MoveablePieces(int player ) {
			switch(player) {
				case 0:
					for (FieldID id : RedPieces) {
						if (hasKillableOpponents(id))
							RedKills.add(id);
						else
							if (FieldAvailableMoves(id, player).size()!=0)
								RedRegular.add(id);
						}
					if (RedKills.size() == 0 && RedRegular.size() == 0)
						NoRedMoves = true;
					return;
				case 1:
					for (FieldID id : BlackPieces) {
						if (hasKillableOpponents(id))
							BlackKills.add(id);
						else
							if (FieldAvailableMoves(id, player).size()!=0)
								BlackRegular.add(id);
						}
					if (BlackKills.size() == 0 && BlackRegular.size() == 0)
						NoBlackMoves = true;
					return;
				default:
					return;
			}
		}
		
		boolean hasKillMoves(int player) {
			switch(player) {
				case 0:
					if (RedKills.size()!=0)
						return true;
					else
						return false;
				case 1:	
					if (BlackKills.size()!=0)
						return true;
					else
						return false;
				default:
					return false;
			}
		}
		boolean hasRegularMoves(int player) {
			switch(player) {
				case 0:
					if (RedRegular.size()!=0)
						return true;
					else
						return false;
				case 1:	
					if (BlackRegular.size()!=0)
						return true;
					else
						return false;
				default:
					return false;
			}
		}
		void resetArrays(int player) {
			switch(player) {
				case 0:
					RedKills.clear();
					RedRegular.clear();
					return;
				case 1:
					BlackKills.clear();
					BlackRegular.clear();
			}
		}
		boolean allowedKillMove(int player, FieldID id) {
			switch(player) {
				case 0:
					if (RedKills.contains(id))
						return true;
					else
						return false;
				case 1:
					if (BlackKills.contains(id))
						return true;
					else
						return false;
				default:
					return false;
					
			}
		}
		boolean allowedRegularMove(int player, FieldID id) {
			switch(player) {
				case 0:
					if (RedRegular.contains(id))
						return true;
					else
						return false;
				case 1:
					if (BlackRegular.contains(id))
						return true;
					else
						return false;
				default:
					return false;
					
			}
		}
		void KillPiece(FieldID id) {
			Field toKill = getField(id);
			if (toKill.StateToInt()==0) {
				RedPieces.remove(id);
				toKill.changeState(State.EMPTY);
			}
			else {
				BlackPieces.remove(id);
				toKill.changeState(State.EMPTY);
			}
		}
		void MovePiece(FieldID start, FieldID end, int player) {
			getField(start).changeState(State.EMPTY);
			switch(player) {
			case 0:
				if (end.getX() == 7)
					getField(end).changeState(State.REDSPECIAL);
				else
					getField(end).changeState(State.RED);
				RedPieces.remove(start);
				RedPieces.add(end);
				return;
			case 1:
				if (end.getX() == 0)
					getField(end).changeState(State.BLACKSPECIAL);
				else
					getField(end).changeState(State.BLACK);
				BlackPieces.remove(start);
				BlackPieces.add(end);
				return;
			}
		}
		
	public
		Board() {
				for (int i=0; i<8; i++) {
					if (i%2==0) {
						gridArray[0][i] = new Field(State.UNAVAILABLE);
						gridArray[1][i] = new Field(State.RED);
						RedPieces.add(new FieldID(1,i));
						gridArray[2][i] = new Field(State.UNAVAILABLE);
						gridArray[3][i] = new Field(State.EMPTY);
						//gridArray[3][i] = new Field(State.UNAVAILABLE);
						gridArray[4][i] = new Field(State.UNAVAILABLE);
						gridArray[5][i] = new Field(State.BLACK);
						BlackPieces.add(new FieldID(5,i));
						//gridArray[5][i] = new Field(State.EMPTY);
						//gridArray[5][i] = new Field(State.BLACKSPECIAL);
						//BlackPieces.add(new FieldID(5,i));
						gridArray[6][i] = new Field(State.UNAVAILABLE);
						gridArray[7][i] = new Field(State.BLACK);
						BlackPieces.add(new FieldID(7,i));
						//gridArray[7][i] = new Field(State.EMPTY);
					}
					else {
						gridArray[0][i] = new Field(State.RED);
						RedPieces.add(new FieldID(0,i));
						gridArray[1][i] = new Field(State.UNAVAILABLE);
						gridArray[2][i] = new Field(State.RED);
						RedPieces.add(new FieldID(2,i));
						gridArray[3][i] = new Field(State.UNAVAILABLE);
						gridArray[4][i] = new Field(State.EMPTY);
						//gridArray[4][i] = new Field(State.RED);
						//RedPieces.add(new FieldID(4,i));
						gridArray[5][i] = new Field(State.UNAVAILABLE);
						gridArray[6][i] = new Field(State.BLACK);
						BlackPieces.add(new FieldID(6,i));
						//gridArray[6][i] = new Field(State.EMPTY);
						//RedPieces.add(new FieldID(6,i));
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
		
		void printHello(int player) {
			switch(player) {
				case 0:
					System.out.println("Red's turn");
					return;
				case 1:
					System.out.println("Black's turn");
					
 			}
		}
};
