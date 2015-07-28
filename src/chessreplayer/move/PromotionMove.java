/**
 * Models a move that results in promotion of the moving pawn, by extending a generic move. 
 * In addition to the generic move information, holds information about the piece to which the pawn has been promoted.
 */

package chessreplayer.move;

import chessreplayer.piece.Color;

public class PromotionMove extends Move
{
	private PieceLetter promotedPieceLetter;

	private static final String newLine = System.getProperty( "line.separator");

	// Create a promotion move with given move text, turn index, letter denoting the promoted piece and player side.
	public PromotionMove( String moveText, int turnIndex, PieceLetter promotedPieceLetter, Color playerSide)
	{
		super( moveText, turnIndex, playerSide);
		this.promotedPieceLetter = promotedPieceLetter;
	}

	public PieceLetter getPromotedPieceLetter()
	{
		return promotedPieceLetter;
	}

	public void setPromotedPieceLetter( PieceLetter promotedPieceLetter)
	{
		this.promotedPieceLetter = promotedPieceLetter;
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
		
		str += " playerSide: " + this.getPlayerSide() + " pieceLetter: " + this.getPieceLetter() + 
				" promotedPieceLetter: " + promotedPieceLetter + " sourceFile: " + this.getSourceFile() + 
				" sourceRank: " + this.getSourceRank() + " destinationFile: " + this.getDestinationFile() + 
				" destinationRank: " + this.getDestinationRank() + " isCapture: " + this.isCapture() + 
				" isCheck: " + this.isCheck() + " isCheckMate: " + this.isCheckMate();
		
		return str;
	}
}
