/**
 * Models a pawn. Includes standard piece methods, methods that hold information about if and to which piece this pawn has been promoted 
 * and methods that hold information about if and when this pawn has moved
 */

package chessreplayer.piece;

import chessreplayer.move.IllegalPromotionException;
import chessreplayer.move.Move.File;
import chessreplayer.move.Move.PieceLetter;
import chessreplayer.move.Move.Rank;

public class Pawn implements Piece
{
	private Color color;
	private boolean isCaptured;
	private Rank capturedOnRank;
	private File capturedOnFile;
	
	private Piece promotedPiece;
	private int firstMovedOnTurn;

	// Creates a non-captured pawn that has neither moved nor promoted yet with the given color.
	public Pawn( Color color)
	{
		this.color = color;
		this.isCaptured = false;
		this.capturedOnRank = null;
		this.capturedOnFile = null;
		this.promotedPiece = null;
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

	public Piece getPromotedPiece()
	{
		return promotedPiece;
	}

	public void setPromotedPiece( Piece promotedPiece)
	{
		this.promotedPiece = promotedPiece;
	}

	public int getFirstMovedOnTurn()
	{
		return firstMovedOnTurn;
	}

	public void setFirstMovedOnTurn( int firstMovedOnTurn)
	{
		this.firstMovedOnTurn = firstMovedOnTurn;
	}

	// Promotes this pawn to the piece with the given PieceLetter, unless it is PieceLetter P (stands for pawn) or PieceLetter K (stands for king).
	public void promote( PieceLetter promotedPieceLetter) throws IllegalPromotionException
	{
		if( promotedPieceLetter.equals( PieceLetter.P) || promotedPieceLetter.equals( PieceLetter.K))
			throw new IllegalPromotionException( "Cannot promote to " + promotedPiece.getClass().getSimpleName());
		else if( promotedPieceLetter.equals( PieceLetter.N))
			setPromotedPiece( new Knight( color));
		else if( promotedPieceLetter.equals( PieceLetter.B))
			setPromotedPiece( new Bishop( color));
		else if( promotedPieceLetter.equals( PieceLetter.R))
			setPromotedPiece( new Rook( color));
		else if( promotedPieceLetter.equals( PieceLetter.Q))
			setPromotedPiece( new Queen( color));
		else
			throw new IllegalPromotionException( "Cannot promote to " + promotedPieceLetter);
	}

	// Promotes this pawn to the given piece, unless the given piece is a pawn or a king.
	public void promote( Piece promotedPiece) throws IllegalPromotionException
	{
		if( promotedPiece instanceof Pawn || promotedPiece instanceof King)
			throw new IllegalPromotionException( "Cannot promote to " + promotedPiece.getClass().getSimpleName());
		else
			setPromotedPiece( promotedPiece);
	}

	// Demotes this pawn. Normally, there is no such thing as demoting a promoted pawn in chess; this method is used when taking back a move.
	public void demote() throws IllegalPromotionException
	{
		if( promotedPiece == null)
			throw new IllegalPromotionException( "This pawn has not been promoted");
		else
			setPromotedPiece( null);
	}

	// If this pawn has been promoted, returns the PieceLetter of the promoted piece, otherwise returns PieceLetter P, which stands for pawn.
	public PieceLetter getRealPieceLetter()
	{
		if( getPromotedPiece() != null)
			return getPromotedPiece().getRealPieceLetter();
		else
			return PieceLetter.P;
	}
}
