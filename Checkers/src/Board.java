
public class Board {
	private
		Field gridArray[][] = new Field[8][8];
	public
		Board() {
		for (int i=0; i<8; i++)
			for (int j=0;j<8;j++) {
				gridArray[i][j] = new Field();
			}
			for (int i=0; i<8; i++) {
				if (i%2==0) {
					gridArray[1][i] = new Field(State.RED);
					gridArray[5][i] = new Field(State.BLACK);
					gridArray[7][i] = new Field(State.BLACK);
				}
				else {
					gridArray[0][i] = new Field(State.RED);
					gridArray[2][i] = new Field(State.RED);
					gridArray[6][i] = new Field(State.BLACK);
					
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
					else
						System.out.print("O ");
					if (j==7)
						System.out.println();
				}
			}
		}


public static void main(String args[]) {
	Board game = new Board();
	game.print();
};
};
