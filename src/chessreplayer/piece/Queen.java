/**
 * Models a queen. Includes standard piece methods.
 */

package chessreplayer.piece;

import chessreplayer.move.Move.File;
import chessreplayer.move.Move.PieceLetter;
import chessreplayer.move.Move.Rank;

public class Queen implements Piece
{
	private Color color;
	private boolean isCaptured;
	private Rank capturedOnRank;
	private File capturedOnFile;

	// Creates a non-captured queen with the given color.
	public Queen( Color color)
	{
		this.color = color;
		this.isCaptured = false;
		this.capturedOnRank = null;
		this.capturedOnFile = null;
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

	// Returns PieceLetter Q, which stands for queen.
	public PieceLetter getRealPieceLetter()
	{
		return PieceLetter.Q;
	}
}
