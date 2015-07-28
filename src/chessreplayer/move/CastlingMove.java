/**
 * Models a castling move, by extending a generic move. In addition to the generic move information, holds information about the type of castling (kingside/queenside).
 */

package chessreplayer.move;

import chessreplayer.piece.Color;

public class CastlingMove extends Move
{
	private boolean isKingSideCastling;

	private static final String newLine = System.getProperty( "line.separator");

	// Create a castling move with given move text, turn index, player side and castling type.
	public CastlingMove( String moveText, int turnIndex, Color playerSide, boolean isKingSideCastling)
	{
		super( moveText, turnIndex, playerSide);
		this.isKingSideCastling = isKingSideCastling;
	}

	public boolean isKingSideCastling()
	{
		return isKingSideCastling;
	}

	public void setKingSideCastling( boolean isKingSideCastling)
	{
		this.isKingSideCastling = isKingSideCastling;
	}

	@Override
	public String toString()
	{
		String str = this.getTurnIndex() + ". " + this.getMoveText();
		
		if( this.getNumericalAnnotationGlyph() != null)
			str += " " + this.getNumericalAnnotationGlyph();
		
		for( String recursiveVariation : this.getRecursiveVariationList())
			str += newLine + recursiveVariation;
		
		for( String comment : this.getCommentList())
			str += newLine + comment;
		
		str += "playerSide: " + this.getPlayerSide() + " isKingSideCastling: " + isKingSideCastling + 
				" isQueenSideCastling: " + !isKingSideCastling + " isCheck: " + this.isCheck() + 
				" isCheckMate: " + this.isCheckMate();
		
		return str;
	}
}
