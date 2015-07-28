/**
 * Models a king. Includes standard piece methods and methods that hold information about if and when this king has moved.
 */

package chessreplayer.piece;

import chessreplayer.move.InvalidMoveException;
import chessreplayer.move.Move.File;
import chessreplayer.move.Move.PieceLetter;
import chessreplayer.move.Move.Rank;

public class King implements Piece
{
	private Color color;
	
	private boolean hasMoved;
	private int firstMovedOnTurn;

	// Creates a king that hasn't moved yet with the given color.
	public King( Color color)
	{
		this.color = color;
		this.firstMovedOnTurn = -1;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public void setColor( Color color)
	{
		this.color = color;
	}
	
	public boolean isCaptured()
	{
		return false;
	}

	public void setCaptured( boolean isCaptured)
	{
	}

	public Rank getCapturedOnRank()
	{
		return null;
	}

	// Throw an invalid move exception, king cannot be captured.
	public void setCapturedOnRank( Rank capturedOnRank) throws InvalidMoveException
	{
		throw new InvalidMoveException( "Invalid move, king cannot be captured.");
	}

	public File getCapturedOnFile()
	{
		return null;
	}

	// Throw an invalid move exception, king cannot be captured.
	public void setCapturedOnFile( File capturedOnFile) throws InvalidMoveException
	{
		throw new InvalidMoveException( "Invalid move, king cannot be captured.");
	}
	
	public boolean hasMoved() 
	{
		return hasMoved;
	}

	public void setHasMoved( boolean hasMoved)
	{
		this.hasMoved = hasMoved;
	}
	
	public int getFirstMovedOnTurn()
	{
		return firstMovedOnTurn;
	}

	public void setFirstMovedOnTurn( int firstMovedOnTurn)
	{
		this.firstMovedOnTurn = firstMovedOnTurn;
	}

	// Returns PieceLetter K, which stands for king.
	public PieceLetter getRealPieceLetter()
	{
		return PieceLetter.K;
	}
}
