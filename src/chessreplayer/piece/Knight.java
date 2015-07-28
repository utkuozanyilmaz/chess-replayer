/**
 * Models a knight. Includes standard piece methods.
 */

package chessreplayer.piece;

import chessreplayer.move.Move.File;
import chessreplayer.move.Move.PieceLetter;
import chessreplayer.move.Move.Rank;

public class Knight implements Piece
{
	private Color color;
	private boolean isCaptured;
	private Rank capturedOnRank;
	private File capturedOnFile;

	// Creates a non-captured knight with the given color.
	public Knight( Color color)
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

	// Returns PieceLetter N, which stands for knight.
	public PieceLetter getRealPieceLetter()
	{
		return PieceLetter.N;
	}
}
