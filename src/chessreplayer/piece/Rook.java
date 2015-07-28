/**
 * Models a rook. Includes standard piece methods and methods that hold information about if and when this rook has moved.
 */

package chessreplayer.piece;

import chessreplayer.move.Move.File;
import chessreplayer.move.Move.PieceLetter;
import chessreplayer.move.Move.Rank;

public class Rook implements Piece
{
	private Color color;
	private boolean isCaptured;
	private Rank capturedOnRank;
	private File capturedOnFile;
	
	private boolean hasMoved;
	private int firstMovedOnTurn;

	// Creates a non-captured rook that hasn't moved yet with the given color.
	public Rook( Color color)
	{
		this.color = color;
		this.isCaptured = false;
		this.capturedOnRank = null;
		this.capturedOnFile = null;
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
		return isCaptured;
	}

	public void setCaptured( boolean isCaptured)
	{
		this.isCaptured = isCaptured;
	}

	public Rank getCapturedOnRank()
	{
		return capturedOnRank;
	}

	public void setCapturedOnRank( Rank capturedOnRank)
	{
		this.capturedOnRank = capturedOnRank;
	}

	public File getCapturedOnFile()
	{
		return capturedOnFile;
	}

	public void setCapturedOnFile( File capturedOnFile)
	{
		this.capturedOnFile = capturedOnFile;
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

	// Returns PieceLetter R, which stands for rook.
	public PieceLetter getRealPieceLetter()
	{
		return PieceLetter.R;
	}
}
