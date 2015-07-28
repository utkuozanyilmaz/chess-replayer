/**
 * Models an en passant move, by extending a generic move.
 */

package chessreplayer.move;

import chessreplayer.piece.Color;

public class EnPassantMove extends Move
{
	public EnPassantMove( String moveText, int turnIndex, Color playerSide)
	{
		super( moveText, turnIndex, playerSide);
	}

	// Create an en passant move from the given move.
	public EnPassantMove( Move move)
	{
		super( move.getMoveText(), move.getTurnIndex(), move.getPlayerSide());
		
		this.setNumericalAnnotationGlyph( move.getNumericalAnnotationGlyph());
		this.setCommentList( move.getCommentList());
		this.setRecursiveVariationList( move.getRecursiveVariationList());
		this.setDestinationFile( move.getDestinationFile());
		this.setDestinationRank( move.getDestinationRank());
		this.setSourceFile( move.getSourceFile());
		this.setSourceRank( move.getSourceRank());
		this.setPieceLetter( move.getPieceLetter());
		this.setCapture( move.isCapture());
		this.setCheck( move.isCheck());
		this.setCheckMate( move.isCheckMate());
	}
}
