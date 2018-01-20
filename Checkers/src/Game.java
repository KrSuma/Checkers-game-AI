
import java.util.InputMismatchException;
import java.util.Scanner;
public class Game {
	private
		Board board;
		Scanner read = new Scanner(System.in);
		FieldID getFieldFromUser() {
			System.out.println("Enter x and y");
			int x = -1, y = -1;
			try {
				x = read.nextInt();
				y = read.nextInt();
			}
			catch (InputMismatchException ex) {
				
				read.next();
				return null;
			}
			
			FieldID ID = new FieldID(x,y);
			
			if (ID.outOfRange)
				return null;
			return ID;
		}
		void PlayerMove(int player) {
			board.printHello(player);
			board.MoveablePieces(player);
			FieldID id = null, dest;
			while(id == null)
				id = getFieldFromUser();
			boolean moveWasMade = false;
			while (!moveWasMade) {
				if (board.hasKillMoves(player)) {
					if(board.allowedKillMove(player, id)) {
						System.out.println("Pick where to move:");
						dest  = getFieldFromUser();
						if (board.FieldAvailableMoves(id, player).contains(dest)) {
							board.KillPiece(new FieldID((id.getX()+dest.getX())/2, (id.getY()+dest.getY())/2));
							board.MovePiece(id, dest, player);
							if (board.hasKillableOpponents(dest)) 
								id = dest;
							else
								moveWasMade = true;
							board.resetArrays(player);
						}
						else {
							System.out.println("You can't move there");
						}
						
					}
					else {
						System.out.println("You can't move this piece, pick another");
						board.resetArrays(player);
						PlayerMove(player);
					}
				}
				else if (board.hasRegularMoves(player)){
					if(board.allowedRegularMove(player, id)) {
						System.out.println("Type where you want to move");
						dest  = getFieldFromUser();
						if (board.FieldAvailableMoves(id, player).contains(dest)) {
							board.MovePiece(id, dest, player);
							moveWasMade = true;
							board.resetArrays(player);
						}
						else {
							System.out.println("You can't move there, enter new destination:");
						}
						
					}
					else {
						System.out.println("You can't move this piece, pick another");
						board.resetArrays(player);
						PlayerMove(player);
						moveWasMade = true;
					}
				}
				else {
					
					moveWasMade = true;
				}
			
			
			}	
		};
	public
		Game() {
			board = new Board();
			
		};
		void play() {
			int player = 0;
			while (board.RedPieces.size()!=0 && board.BlackPieces.size()!=0 && !board.NoBlackMoves && !board.NoRedMoves) {
				PlayerMove(player%2);
				board.print();
				player++;
			}
			if (board.RedPieces.size() == 0 || board.NoRedMoves)
				System.out.println("Black wins!");
			else
				System.out.println("Red Wins!");
		};
	
	public static void main(String args[]) {
		Game game = new Game();
		game.board.print();
		game.play();
	};
}
