
public class Game {
	private
		Board board;
		int RedPieces, BlackPieces;
		int move(int player) {
			return 0;
		};
	public
		Game() {
			board = new Board();
			RedPieces = BlackPieces = 12;
		};
		void play() {
			while (RedPieces!=0 || BlackPieces!=0) {
				
			}
		};
	
	public static void main(String args[]) {
		Game game = new Game();
		game.board.print();
		game.move(1);
	};
}
