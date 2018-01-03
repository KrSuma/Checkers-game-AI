
public class Board {
	private
		Field gridArray[][] = new Field[8][8];
		Field getField(FieldID id) {return gridArray[id.getX()][id.getY()];}
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
