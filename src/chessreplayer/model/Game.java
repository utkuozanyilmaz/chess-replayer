/**
 * Model of the MVC pattern. Contains a board, list of moves and some additional elements pertaining to game info and state.
 */

package chessreplayer.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import chessreplayer.move.CastlingMove;
import chessreplayer.move.IllegalPromotionException;
import chessreplayer.move.InvalidMoveException;
import chessreplayer.move.Move;
import chessreplayer.piece.Color;

public class Game
{
	public static enum Result { BLACK_WINS, WHITE_WINS, DRAW };
								
	private ArrayList<Move> moveList;
	private HashMap<String,String> tagMap;
	private Result result;
	private ArrayList<String> trailingCommentsList;
	
	private Board board;
	
	private int lastPlayedMoveIndex;
	
	private static final String newLine = System.getProperty( "line.separator");
	
	// Create a new game from given list of moves, game tags, game result and ending comments
	public Game( ArrayList<Move> moveList, HashMap<String,String> tagMap, Result result, ArrayList<String> trailingCommentsList)
	{
		if( moveList == null)
			this.moveList = new ArrayList<Move>();
		else
			this.moveList = moveList;

		if( tagMap == null)
			this.tagMap = new HashMap<String,String>();
		else
			this.tagMap = tagMap;
		
		this.result = result;
		this.trailingCommentsList = trailingCommentsList;
	}
	
	public Result getResult()
	{
		return result;
	}
	
	// Return a deep copy of tagMap
	public HashMap<String,String> getTagMap()
	{
		HashMap<String,String> deepCopy = new HashMap<String,String>();
		
		for( String key : tagMap.keySet())
		{
			String value = tagMap.get( key);
			deepCopy.put( key, value);
		}
		
		return deepCopy;
	}
	
	public void initializeBoard()
	{
		this.board = new Board();
		board.initializePieces();
		lastPlayedMoveIndex = 0;
	}
	
	// Validate the move list. Throws InvalidMoveException or IllegalPromotionException in case of invalid moves.
	public void validateMoves() throws InvalidMoveException, IllegalPromotionException
	{
		Board validationBoard = new Board();
		validationBoard.initializePieces();
		
		for( int i = 0; i < moveList.size(); i++)
		{
			Move move = moveList.get( i);
			moveList.set( i, validationBoard.validateMove( move));
		}
	}
	
	// Play one turn and increment last played move index, if the game hasn't ended already.
	public void playTurn()
	{
		if( !hasEnded())
		{
			try {
				board.executeMove( moveList.get( lastPlayedMoveIndex));
			} catch (IllegalPromotionException e) { // Should never happen, the exception should be thrown during validateMoves
			} catch (InvalidMoveException e) {} // Should never happen, the exception should be thrown during validateMoves
			
			lastPlayedMoveIndex++;
		}
	}

	// Take back one turn and decrement last played move index, if the game hasn't ended already.
	public void takeBackTurn()
	{
		if( hasStarted())
		{
			try {
				lastPlayedMoveIndex--;
				board.takeBackMove( moveList.get( lastPlayedMoveIndex));
			} catch (IllegalPromotionException e) { // Should never happen, the exception should be thrown during validateMoves
			} catch (InvalidMoveException e) {} // Should never happen, the exception should be thrown during validateMoves
			
		}
	}
	
	public boolean hasEnded()
	{
		return( moveList != null && moveList.size() == lastPlayedMoveIndex);
	}
	
	public boolean hasStarted()
	{
		return( lastPlayedMoveIndex > 0);
	}
	
	// Return a string list containing move texts of the moves in list of moves
	public List<String> getFullTextList()
	{
		List<String> fullTextList = new ArrayList<String>();
		
		if( moveList != null)
		{
			for( Move m : moveList)
			{
				fullTextList.add( m.getFullText());
			}
		} 
		
		return fullTextList;
	}
	
	public int getLastPlayedMoveIndex()
	{
		return lastPlayedMoveIndex;
	}
	
	// Get ordinal of source file of the last played move, adjusted from 0-7 range to 1-8 range
	public int lastPlayedFromFileOrdinal()
	{
		// Return -1 if the game hasn't started yet
		if( lastPlayedMoveIndex < 1)
			return -1;
		
		Move move = moveList.get( lastPlayedMoveIndex-1);
		
		// Handle castling moves by using source and destination of the king in the castling
		if( move instanceof CastlingMove)
		{
			if( ((CastlingMove)move).isKingSideCastling())
			{
				if( Color.BLACK.equals( move.getPlayerSide()))
					return Move.File.E.ordinal() + 1; // From e8
				else
					return Move.File.E.ordinal() + 1; // From e1
			}
			else
			{
				if( Color.BLACK.equals( move.getPlayerSide()))
					return Move.File.E.ordinal() + 1; // From e8
				else
					return Move.File.E.ordinal() + 1; // From e1
			}
		}
		else // For non-castling moves, simply return the ordinal of source file
			return moveList.get( lastPlayedMoveIndex-1).getSourceFile().ordinal() + 1;
	}

	// Get ordinal of source rank of the last played move, adjusted from 0-7 range to 1-8 range
	public int lastPlayedFromRankOrdinal()
	{
		if( lastPlayedMoveIndex < 1)
			return -1;
		
		Move move = moveList.get( lastPlayedMoveIndex-1);
		
		// Handle castling moves by using source and destination of the king in the castling
		if( move instanceof CastlingMove)
		{
			if( ((CastlingMove)move).isKingSideCastling())
			{
				if( Color.BLACK.equals( move.getPlayerSide()))
					return Move.Rank._8.ordinal() + 1; // From e8
				else
					return Move.Rank._1.ordinal() + 1; // From e1
			}
			else
			{
				if( Color.BLACK.equals( move.getPlayerSide()))
					return Move.Rank._8.ordinal() + 1; // From e8
				else
					return Move.Rank._1.ordinal() + 1; // From e1
			}
		}
		else // For non-castling moves, simply return the ordinal of source rank
			return moveList.get( lastPlayedMoveIndex-1).getSourceRank().ordinal() + 1;
	}

	// Get ordinal of destination file of the last played move, adjusted from 0-7 range to 1-8 range
	public int lastPlayedToFileOrdinal()
	{
		if( lastPlayedMoveIndex < 1)
			return -1;
		
		Move move = moveList.get( lastPlayedMoveIndex-1);
		
		// Handle castling moves by using source and destination of the king in the castling
		if( move instanceof CastlingMove)
		{
			if( ((CastlingMove)move).isKingSideCastling())
			{
				if( Color.BLACK.equals( move.getPlayerSide()))
					return Move.File.G.ordinal() + 1; // To g8
				else
					return Move.File.G.ordinal() + 1; // To g1
			}
			else
			{
				if( Color.BLACK.equals( move.getPlayerSide()))
					return Move.File.C.ordinal() + 1; // To c8
				else
					return Move.File.C.ordinal() + 1; // To c1
			}
		}
		else // For non-castling moves, simply return the ordinal of destination file
			return moveList.get( lastPlayedMoveIndex-1).getDestinationFile().ordinal() + 1;
	}

	// Get ordinal of destination rank of the last played move, adjusted from 0-7 range to 1-8 range
	public int lastPlayedToRankOrdinal()
	{
		if( lastPlayedMoveIndex < 1)
			return -1;
		
		Move move = moveList.get( lastPlayedMoveIndex-1);
		
		// Handle castling moves by using source and destination of the king in the castling
		if( move instanceof CastlingMove)
		{
			if( ((CastlingMove)move).isKingSideCastling())
			{
				if( Color.BLACK.equals( move.getPlayerSide()))
					return Move.Rank._8.ordinal() + 1; // To g8
				else
					return Move.Rank._1.ordinal() + 1; // To g1
			}
			else
			{
				if( Color.BLACK.equals( move.getPlayerSide()))
					return Move.Rank._8.ordinal() + 1; // To c8
				else
					return Move.Rank._1.ordinal() + 1; // To c1
			}
		}
		else // For non-castling moves, simply return the ordinal of destination rank
			return moveList.get( lastPlayedMoveIndex-1).getDestinationRank().ordinal() + 1;
	}
	
	public Board retrieveBoard()
	{
		return board;
	}

	@Override
	public String toString()
	{
		String str = "Game:" + newLine;
		
		Set<String> keySet = tagMap.keySet();
		for( String key : keySet)
			str += key + " : " + tagMap.get( key) + newLine;
		
		for( Move move : moveList)
			str += move.toString() + newLine;
		
		str += "Result : " + result + newLine;
		
		for( String trailingComment : trailingCommentsList)
			str += trailingComment + newLine;
		
		return str;
	}
}
