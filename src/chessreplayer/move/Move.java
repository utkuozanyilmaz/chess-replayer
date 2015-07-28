/**
 * Models a generic chess move. Contains information about the move itself, the turn of the move and the pieces relevant to the move. 
 * Also contains extra information about the move given in the input file, and the necessary enumerations.
 */

package chessreplayer.move;

import java.util.ArrayList;

import chessreplayer.piece.Color;
import chessreplayer.piece.Piece;

public class Move
{
	public enum File { A, B, C, D, E, F, G, H};
	public enum Rank { _1, _2, _3, _4, _5, _6, _7, _8};
	public enum PieceLetter { P, N, B, R, Q, K};
	private static final String newLine = System.getProperty( "line.separator");
	
	private int turnIndex;
	private String moveText;
	private String numericalAnnotationGlyph;
	private ArrayList<String> commentList;
	private ArrayList<String> recursiveVariationList;
	
	private Color playerSide;
	
	private File destinationFile;
	private Rank destinationRank;
	private File sourceFile;
	private Rank sourceRank;
	private PieceLetter pieceLetter;
	
	private Piece capturedPiece;
	
	private boolean isCapture;
	private boolean isCheck;
	private boolean isCheckMate;
	
	// Create a move with given move text, turn index and player side; with no comments/recursive variations and no check/checkmate.
	public Move( String moveText, int turnIndex, Color playerSide)
	{
		this.moveText = moveText;
		this.setTurnIndex(turnIndex);
		this.playerSide = playerSide;
		
		commentList = new ArrayList<String>();
		recursiveVariationList = new ArrayList<String>();

		isCheck = false;
		isCheckMate = false;
	}

	public int getTurnIndex()
	{
		return turnIndex;
	}

	public void setTurnIndex( int turnIndex)
	{
		this.turnIndex = turnIndex;
	}

	public String getMoveText()
	{
		return moveText;
	}

	public void setMoveText( String moveText)
	{
		this.moveText = moveText;
	}

	public String getNumericalAnnotationGlyph()
	{
		return numericalAnnotationGlyph;
	}

	public void setNumericalAnnotationGlyph( String numericalAnnotationGlyph)
	{
		this.numericalAnnotationGlyph = numericalAnnotationGlyph;
	}

	public ArrayList<String> getCommentList()
	{
		return commentList;
	}

	public void setCommentList( ArrayList<String> commentList)
	{
		this.commentList = commentList;
	}

	public ArrayList<String> getRecursiveVariationList() {
		return recursiveVariationList;
	}

	public void setRecursiveVariationList( ArrayList<String> recursiveVariationList)
	{
		this.recursiveVariationList = recursiveVariationList;
	}
	
	public Color getPlayerSide()
	{
		return playerSide;
	}

	public void setPlayerSide( Color playerSide)
	{
		this.playerSide = playerSide;
	}

	public File getDestinationFile()
	{
		return destinationFile;
	}

	public void setDestinationFile( File destinationFile) 
	{
		this.destinationFile = destinationFile;
	}

	public Rank getDestinationRank()
	{
		return destinationRank;
	}

	public void setDestinationRank( Rank destinationRank)
	{
		this.destinationRank = destinationRank;
	}

	public File getSourceFile()
	{
		return sourceFile;
	}

	public void setSourceFile( File sourceFile)
	{
		this.sourceFile = sourceFile;
	}

	public Rank getSourceRank()
	{
		return sourceRank;
	}

	public void setSourceRank( Rank sourceRank)
	{
		this.sourceRank = sourceRank;
	}

	public PieceLetter getPieceLetter()
	{
		return pieceLetter;
	}

	public void setPieceLetter( PieceLetter pieceLetter) 
	{
		this.pieceLetter = pieceLetter;
	}

	public Piece getCapturedPiece()
	{
		return capturedPiece;
	}

	public void setCapturedPiece( Piece capturedPiece)
	{
		this.capturedPiece = capturedPiece;
	}

	public boolean isCapture()
	{
		return isCapture;
	}

	public void setCapture( boolean isCapture) 
	{
		this.isCapture = isCapture;
	}

	public boolean isCheck()
	{
		return isCheck;
	}

	public void setCheck( boolean isCheck)
	{
		this.isCheck = isCheck;
	}

	public boolean isCheckMate()
	{
		return isCheckMate;
	}

	public void setCheckMate( boolean isCheckMate)
	{
		this.isCheckMate = isCheckMate;
	}

	public void addComment( String comment)
	{
		commentList.add( comment);
	}
	
	public void addRecursiveVariation( String recursiveVariation)
	{
		recursiveVariationList.add( recursiveVariation);
	}
	
	// Create a move text by concatenating turn index, move text, comments and recursive variations; and return it.
	public String getFullText()
	{
		String str = turnIndex + ". " + moveText;
		
		if( numericalAnnotationGlyph != null)
			str += numericalAnnotationGlyph;
		
		for( String s : commentList)
			str += newLine + s;
		
		for( String s : recursiveVariationList)
			str += newLine + s;
		
		return str;
	}
	
	// Decide if the given move is a diagonal move (file displacement=rank displacement)
	public static boolean isDiagonal( int file1, int rank1, int file2, int rank2)
	{
		return ( file1 != file2 && Math.abs( file1 - file2) == Math.abs( rank1 - rank2));
	}

	// Decide if the given move is a diagonal move (either no file displacement, or no rank displacement)
	public static boolean isHorizontalOrVertical( int file1, int rank1, int file2, int rank2)
	{
		return (( file1 == file2 && rank1 != rank2) || ( file1 != file2 && rank1 == rank2));
	}

	@Override
	public String toString()
	{
		String str = turnIndex + ". " + moveText;
		
		if( numericalAnnotationGlyph != null)
			str += " " + numericalAnnotationGlyph;
		
		for( String recursiveVariation : recursiveVariationList)
			str += newLine + recursiveVariation;
		
		for( String comment : commentList)
			str += newLine + comment;
		
		str += " playerSide: " + playerSide + " pieceLetter: " + pieceLetter + 
				" sourceFile: " + sourceFile + " sourceRank: " + sourceRank + 
				" destinationFile: " + destinationFile + " destinationRank: " + destinationRank + 
				" isCapture: " + isCapture + " isCheck: " + isCheck + 
				" isCheckMate: " + isCheckMate;
		
		return str;
	}
}
