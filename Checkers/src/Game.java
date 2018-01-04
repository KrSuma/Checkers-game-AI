import java.util.Scanner;
public class Game {
	private
		Board board;
		int RedPieces, BlackPieces;
		Scanner read = new Scanner(System.in);
		FieldID getFieldFromUser() {
			System.out.println("Enter x and y");
			int x = read.nextInt(), y = read.nextInt();
			if (x>7 || x<0 || y>7 || y<0) {
				return null;
				}
			FieldID ID = new FieldID(x,y);
			return ID;
		}
		int move(int player) {

			FieldID id = getFieldFromUser();
			if (id == null)
				return -1;
			Field active = board.accessField(id, player);
			if (active!=null) 
				System.out.println(board.availableMoves(id, player));
			else 
				System.out.println("You picked wrong piece");
			return 0;
		};
	public
		Game() {
			board = new Board();
			RedPieces = BlackPieces = 12;
		};
		void play() {
			int player = 0;
			while (RedPieces!=0 && BlackPieces!=0) {
				move(player%2);
				board.print();
				player++;
			}
		};
	
	public static void main(String args[]) {
		Game game = new Game();
		game.board.print();
		game.play();
	};
}
