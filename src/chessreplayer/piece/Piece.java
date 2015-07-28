/**
 * An interface declaring the methods every piece type should have. The methods are about the piece's color, if it's captured, 
 * where was it captured, and the type of the piece.
 */

package chessreplayer.piece;

import chessreplayer.move.InvalidMoveException;
import chessreplayer.move.Move.File;
import chessreplayer.move.Move.PieceLetter;
import chessreplayer.move.Move.Rank;

public interface Piece
{
	public Color getColor();
	public void setColor( Color color);
	public boolean isCaptured();
	public void setCaptured( boolean isCaptured);
	public Rank getCapturedOnRank();
	public void setCapturedOnRank( Rank rank) throws InvalidMoveException;
	public File getCapturedOnFile();
	public void setCapturedOnFile( File file) throws InvalidMoveException;
	public PieceLetter getRealPieceLetter();
}
