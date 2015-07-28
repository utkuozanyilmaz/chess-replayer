/**
 * Models a chess board. Chess rules are contained in and enforced by methods of this class.
 */

package chessreplayer.model;

import java.util.ArrayList;

import chessreplayer.move.CastlingMove;
import chessreplayer.move.EnPassantMove;
import chessreplayer.move.IllegalPromotionException;
import chessreplayer.move.InvalidMoveException;
import chessreplayer.move.Move;
import chessreplayer.move.Move.PieceLetter;
import chessreplayer.move.Move.Rank;
import chessreplayer.move.PromotionMove;
import chessreplayer.piece.Bishop;
import chessreplayer.piece.Color;
import chessreplayer.piece.King;
import chessreplayer.piece.Knight;
import chessreplayer.piece.Pawn;
import chessreplayer.piece.Piece;
import chessreplayer.piece.Queen;
import chessreplayer.piece.Rook;

public class Board
{
	public static final int BOARD_SIZE = 8;
	private ArrayList<Piece> inGamePieces;
	private ArrayList<Piece> capturedPieces;
	private Piece[][] squares; // file first
	
	// Create an empty board.
	public Board()
	{
		inGamePieces = new ArrayList<Piece>();
		capturedPieces = new ArrayList<Piece>();
		squares = new Piece[BOARD_SIZE][BOARD_SIZE];
		
		for( int i = 0; i < BOARD_SIZE; i++)
			for( int j = 0; j < BOARD_SIZE; j++)
				squares[i][j] = null;
	}
	
	public Piece[][] getSquares()
	{
		return squares;
	}
	
	// Fill the board with pieces in their starting positions.
	public void initializePieces()
	{
		// Rank 1
		Rook whiteRook1 = new Rook( Color.WHITE);
		inGamePieces.add( whiteRook1);
		squares[0][0] = whiteRook1; // a1
		
		Knight whiteKnight1 = new Knight( Color.WHITE);
		inGamePieces.add( whiteKnight1);
		squares[1][0] = whiteKnight1; // b1
		
		Bishop whiteBishop1 = new Bishop( Color.WHITE);
		inGamePieces.add( whiteBishop1);
		squares[2][0] = whiteBishop1; // c1
		
		Queen whiteQueen = new Queen( Color.WHITE);
		inGamePieces.add( whiteQueen);
		squares[3][0] = whiteQueen; // d1
		
		King whiteKing = new King( Color.WHITE);
		inGamePieces.add( whiteKing);
		squares[4][0] = whiteKing; // e1
		
		Bishop whiteBishop2 = new Bishop( Color.WHITE);
		inGamePieces.add( whiteBishop2);
		squares[5][0] = whiteBishop2; // f1
		
		Knight whiteKnight2 = new Knight( Color.WHITE);
		inGamePieces.add( whiteKnight2);
		squares[6][0] = whiteKnight2; // g1
		
		Rook whiteRook2 = new Rook( Color.WHITE);
		inGamePieces.add( whiteRook2);
		squares[7][0] = whiteRook2; // h1
		
		// Rank 2
		Pawn whitePawn;
		for( int i = 0; i < BOARD_SIZE; i++)
		{
			whitePawn = new Pawn( Color.WHITE);
			inGamePieces.add( whitePawn);
			squares[i][1] = whitePawn; // a-h2
		}
		
		// Rank 7
		Pawn blackPawn;
		for( int i = 0; i < BOARD_SIZE; i++)
		{
			blackPawn = new Pawn( Color.BLACK);
			inGamePieces.add( blackPawn);
			squares[i][6] = blackPawn; // a-h7
		}
		
		// Rank 8
		Rook blackRook1 = new Rook( Color.BLACK);
		inGamePieces.add( blackRook1);
		squares[0][7] = blackRook1; // a1
		
		Knight blackKnight1 = new Knight( Color.BLACK);
		inGamePieces.add( blackKnight1);
		squares[1][7] = blackKnight1; // b1
		
		Bishop blackBishop1 = new Bishop( Color.BLACK);
		inGamePieces.add( blackBishop1);
		squares[2][7] = blackBishop1; // c1
		
		Queen blackQueen = new Queen( Color.BLACK);
		inGamePieces.add( blackQueen);
		squares[3][7] = blackQueen; // d1
		
		King blackKing = new King( Color.BLACK);
		inGamePieces.add( blackKing);
		squares[4][7] = blackKing; // e1
		
		Bishop blackBishop2 = new Bishop( Color.BLACK);
		inGamePieces.add( blackBishop2);
		squares[5][7] = blackBishop2; // f1
		
		Knight blackKnight2 = new Knight( Color.BLACK);
		inGamePieces.add( blackKnight2);
		squares[6][7] = blackKnight2; // g1
		
		Rook blackRook2 = new Rook( Color.BLACK);
		inGamePieces.add( blackRook2);
		squares[7][7] = blackRook2; // h1
	}

	// Validate given move
	public Move validateMove( Move move) throws InvalidMoveException, IllegalPromotionException
	{
		// If the move is a castling move
		if( move instanceof CastlingMove)
		{
			// If the player is the black side
			if( Color.BLACK.equals( move.getPlayerSide()))
			{
				// If the move is a kingside castling move
				if( ((CastlingMove)move).isKingSideCastling())
				{
					if( squares[4][7] != null && PieceLetter.K.equals( squares[4][7].getRealPieceLetter()) && 
						!((King)squares[4][7]).hasMoved() &&  // King must be in e8 and mustn't have moved
						squares[7][7] != null && PieceLetter.R.equals( squares[7][7].getRealPieceLetter()) && 
						!((Rook)squares[7][7]).hasMoved()) // Rook must be in h8 and mustn't have moved
					{
						// If e8, f8 or g8 is threatened, or f8 or g8 isn't empty, throw an invalid move exception
						if( isThreatened( 4, 7, Color.BLACK) || squares[5][7] != null || isThreatened( 5, 7, Color.BLACK) || 
							squares[6][7] != null || isThreatened( 6, 7, Color.BLACK))
							throw new InvalidMoveException( "Move " + move.getTurnIndex() + ". " + 
									move.getMoveText() + " is not a valid move.");
					}
					/* If any of the following conditions are true, throw an invalid move exception: 
					 * the king isn't in e8, it has previously moved, the rook isn't in h8, it has previously moved
					 */
					else
						throw new InvalidMoveException( "Move " + move.getTurnIndex() + ". " + 
								move.getMoveText() + " is not a valid move.");
				}
				else // The move is a queenside castling move
				{
					if( squares[4][7] != null && PieceLetter.K.equals( squares[4][7].getRealPieceLetter()) && 
							!((King)squares[4][7]).hasMoved() &&  // King is in e8 and has not moved
							squares[0][7] != null && PieceLetter.R.equals(squares[0][7].getRealPieceLetter()) && 
							!((Rook)squares[0][7]).hasMoved()) // Rook is in a8 and has not moved
					{
						// If c8, d8 or e8 is threatened, or b8, c8 or d8 isn't empty, throw an invalid move exception
						if( squares[1][7] != null || isThreatened( 2, 7, Color.BLACK) || 
							squares[2][7] != null || isThreatened( 3, 7, Color.BLACK) || 
							squares[3][7] != null || isThreatened( 4, 7, Color.BLACK))
							throw new InvalidMoveException( "Move " + move.getTurnIndex() + ". " + 
									move.getMoveText() + " is not a valid move.");
					}
					/* If any of the following conditions are true, throw an invalid move exception: 
					 * the king isn't in e8, it has previously moved, the rook isn't in a8, it has previously moved
					 */
					else
						throw new InvalidMoveException( "Move " + move.getTurnIndex() + ". " + 
								move.getMoveText() + " is not a valid move.");
				}
			}
			else
			{
				// If the move is a kingside castling move
				if( ((CastlingMove)move).isKingSideCastling())
				{
					if( squares[4][0] != null && PieceLetter.K.equals( squares[4][0].getRealPieceLetter()) && 
						!((King)squares[4][0]).hasMoved() &&  // King is in e1 and has not moved
						squares[7][0] != null && PieceLetter.R.equals( squares[7][0].getRealPieceLetter()) && 
						!((Rook)squares[7][0]).hasMoved()) // Rook is in h1 and has not moved
					{
						// If e1, f1 or g1 is threatened, or f1 or g1 isn't empty, throw an invalid move exception
						if( isThreatened( 4, 0, Color.WHITE) || squares[5][0] != null || isThreatened( 5, 0, Color.WHITE) || 
							squares[6][0] != null || isThreatened( 6, 0, Color.WHITE))
							throw new InvalidMoveException( "Move " + move.getTurnIndex() + ". " + 
									move.getMoveText() + " is not a valid move.");
					}
					/* If any of the following conditions are true, throw an invalid move exception: 
					 * the king isn't in e1, it has previously moved, the rook isn't in h1, it has previously moved
					 */
					else
						throw new InvalidMoveException( "Move " + move.getTurnIndex() + ". " + 
								move.getMoveText() + " is not a valid move.");
				}
				else // The move is a queenside castling move
				{
					if( squares[4][0] != null && PieceLetter.K.equals( squares[4][0].getRealPieceLetter()) && 
							!((King)squares[4][0]).hasMoved() &&  // King is in e1 and has not moved
							squares[0][0] != null && PieceLetter.R.equals( squares[0][0].getRealPieceLetter()) && 
							!((Rook)squares[0][0]).hasMoved()) // Rook is in a1 and has not moved
					{
						// If c1, d1 or e1 is threatened, or b1, c1 or d1 isn't empty, throw an invalid move exception
						if( squares[1][0] != null || isThreatened( 2, 0, Color.WHITE) || 
							squares[2][0] != null || isThreatened( 3, 0, Color.WHITE) || 
							squares[3][0] != null || isThreatened( 4, 0, Color.WHITE))
							throw new InvalidMoveException( "Move " + move.getTurnIndex() + ". " + 
									move.getMoveText() + " is not a valid move.");
					}
					/* If any of the following conditions are true, throw an invalid move exception: 
					 * the king isn't in e1, it has previously moved, the rook isn't in a1, it has previously moved
					 */
					else
						throw new InvalidMoveException( "Move " + move.getTurnIndex() + ". " + 
								move.getMoveText() + " is not a valid move.");
				}
			}
		}
		else // The move is not a castling move
		{
			ArrayList<Integer> possibleSourceFiles = new ArrayList<Integer>();
			ArrayList<Integer> possibleSourceRanks = new ArrayList<Integer>();
			
			// If the file of the piece before the move is not known, try to find possible files
			if( move.getSourceFile() == null)
			{
				// If the rank of the piece before the move is not known, try to find possible ranks
				if( move.getSourceRank() == null)
				{
					for( int i = 0; i < BOARD_SIZE; i++) // For each file
					{
						for( int j = 0; j < BOARD_SIZE; j++) // For each rank
						{
							/* If square i,j contains a piece, the type of which is the same as the type of the piece in the move, 
							 * and belonging to the player' side, square i,j is a possible source square for this move.
							 */
							if( squares[i][j] != null && squares[i][j].getColor().equals( move.getPlayerSide()) && 
								squares[i][j].getRealPieceLetter().equals( move.getPieceLetter()))
							{
								possibleSourceFiles.add( i);
								possibleSourceRanks.add( j);
							}
						}
					}
				}
				else // The rank of the piece before the move is known
				{
					int j = move.getSourceRank().ordinal();
					for( int i = 0; i < BOARD_SIZE; i++) // For each file
					{
						/* If square i,j contains a piece, the type of which is the same as the type of the piece in the move, 
						 * and belonging to the player' side, square i,j is a possible source square for this move.
						 */
						if( squares[i][j] != null && squares[i][j].getColor().equals( move.getPlayerSide()) && 
							squares[i][j].getRealPieceLetter().equals( move.getPieceLetter()))
						{
							possibleSourceFiles.add( i);
							possibleSourceRanks.add( j);
						}
					}
				}
			}
			else // The file of the piece before the move is known
			{
				int i = move.getSourceFile().ordinal();
				
				// If the rank of the piece before the move is not known, try to find possible ranks
				if( move.getSourceRank() == null)
				{
					for( int j = 0; j < BOARD_SIZE; j++)
					{
						if( squares[i][j] != null && squares[i][j].getColor().equals( move.getPlayerSide()) && 
							squares[i][j].getRealPieceLetter().equals( move.getPieceLetter()))
						{
							possibleSourceFiles.add( i);
							possibleSourceRanks.add( j);
						}
					}
				}
				else // The rank of the piece before the move is known
				{
					int j = move.getSourceRank().ordinal();
					if( squares[i][j] != null && squares[i][j].getColor().equals( move.getPlayerSide()) && 
						squares[i][j].getRealPieceLetter().equals( move.getPieceLetter()))
					{
						possibleSourceFiles.add( i);
						possibleSourceRanks.add( j);
					}
				}
			}
			
			// If no possible squares are found for the moving piece's position before the move, throw an invalid move exception.
			if( possibleSourceFiles.isEmpty())
				throw new InvalidMoveException( "Move " + move.getTurnIndex() + ". " + 
												move.getMoveText() + " is not a valid move.");
			
			/* If the move is a promotion move, but one of the conditions below are true, throw an invalid move exception.
			 * - The moving piece is not a pawn
			 * - The player side is white, but the piece is not moving to the 8th rank
			 * - The player side is black, but the piece is not moving to the 1st rank
			 */
			if( move instanceof PromotionMove && ( !PieceLetter.P.equals( move.getPieceLetter()) || 
				!(( Rank._8.equals( move.getDestinationRank()) && Color.WHITE.equals( move.getPlayerSide())) || 
				( Rank._1.equals( move.getDestinationRank()) && Color.BLACK.equals( move.getPlayerSide())))))
					throw new InvalidMoveException( "Move " + move.getTurnIndex() + ". " + 
													move.getMoveText() + " is not a valid move.");
			
			// Get destination file and rank for further validity checks
			int fileDest = move.getDestinationFile().ordinal();
			int rankDest = move.getDestinationRank().ordinal();
			
			/* Note: This loop can be moved inside the if-else if blocks and repeated 6 times for each piece type, 
			 * to increase performance by executing the piece letter check only once outside the loop, 
			 * instead of executing it for each possible source square. However, it will result in repetitive code, 
			 * as the beginning and ending lines of the loop is the same for each piece type. 
			 * As high performance is not too critical for this application, neater code is preferred to executing 
			 * 0 to 7 fewer enum comparisons.
			 */
			for( int i = possibleSourceFiles.size() - 1; i >= 0; i--) // For each possible source square
			{
				int fileSrc = possibleSourceFiles.get( i);
				int rankSrc = possibleSourceRanks.get( i);
				
				// If the moving piece is a pawn
				if( PieceLetter.P.equals( move.getPieceLetter()))
				{
					/* Pawns can only move forward. Remove this square from the possible source squares, 
					 * if this source-destination pair requires the pawn to go backwards/horizontally
					 */
					if(( Color.BLACK.equals( move.getPlayerSide()) && rankSrc <= rankDest) || 
						( Color.WHITE.equals( move.getPlayerSide()) && rankSrc >= rankDest))
					{
						possibleSourceFiles.remove( i);
						possibleSourceRanks.remove( i);
						continue;
					}

					// If the moving piece captures another piece as a result of this move
					if( move.isCapture())
					{
						// Source and destination files should differ by one, destination rank should be source rank +1 (white) / -1 (black) and 
						// destination square shouldn't be empty. Otherwise, remove this square from the possible source squares.
						if(( possibleSourceFiles.get( i) != fileDest -1 && 
							possibleSourceFiles.get( i) != fileDest +1) ||
							(( Color.BLACK.equals( move.getPlayerSide()) && 
							possibleSourceRanks.get( i) - 1 != rankDest) || 
							( Color.WHITE.equals( move.getPlayerSide()) &&
							possibleSourceRanks.get( i) + 1 != rankDest)))
						{
							possibleSourceFiles.remove( i);
							possibleSourceRanks.remove( i);
							continue;
						}
						
						// If the destination square is empty even though this move is a capturing move, this can only be valid in case of an en passant move.
						if( squares[fileDest][rankDest] == null)
						{
							// Handle en passant move for the black side
							if( Color.BLACK.equals( move.getPlayerSide()))
							{
								/* In an en passant move by the black side, the conditions below must be true. Otherwise, throw an invalid move exception.
								 * - The black pawn must be at rank 4 before the move
								 * - The white pawn must be at the next rank of black pawn's destination and in the same file as the black pawn's destination
								 * - The white pawn must be moved for the first time at the previous turn
								 */
								if( possibleSourceRanks.get( i) == Rank._4.ordinal() && 
									squares[fileDest][rankDest+1] != null && 
									PieceLetter.P.equals( squares[fileDest][rankDest+1].getRealPieceLetter()) && 
									((Pawn)squares[fileDest][rankDest+1]).getFirstMovedOnTurn() == move.getTurnIndex()-1)
								{
									// Replace move with en passant move
									move = new EnPassantMove( move);
								}
								else
									throw new InvalidMoveException( "Move " + move.getTurnIndex() + ". " + 
											move.getMoveText() + " is not a valid move.");
							}
							else // Handle en passant move for the white side
							{
								/* In an en passant move by the white side, the conditions below must be true. Otherwise, throw an invalid move exception.
								 * - The white pawn must be at rank 5 before the move
								 * - The black pawn must be at the previous rank of white pawn's destination and in the same file as the white pawn's destination
								 * - The black pawn must be moved for the first time at the previous turn
								 */
								if( possibleSourceRanks.get( i) == Rank._5.ordinal() && 
									squares[fileDest][rankDest-1] != null && 
									PieceLetter.P.equals( squares[fileDest][rankDest-1].getRealPieceLetter()) && 
									((Pawn)squares[fileDest][rankDest-1]).getFirstMovedOnTurn() == move.getTurnIndex()-1)
								{
									// Replace move with en passant move
									move = new EnPassantMove( move);
								}
								else
									throw new InvalidMoveException( "Move " + move.getTurnIndex() + ". " + 
											move.getMoveText() + " is not a valid move.");
							}
						}
					}
					// If the move is not a capturing move, source and destination files should be the same
					else if( possibleSourceFiles.get( i) == fileDest)
					{
						/* If none of the following is true, remove this square from the possible source squares: 
						 * The moving pawn belongs to the black side, it is trying to move 1 square forward and the destination square is empty
						 * The moving pawn belongs to the black side, it is trying to move 2 squares forward, is at rank 7, and rank 6 and 5 of the corresponding file is empty
						 * The moving pawn belongs to the white side, it is trying to move 1 square forward and the destination square is empty
						 * The moving pawn belongs to the white side, it is trying to move 2 squares forward, is at rank 2, and rank 3 and 4 of the corresponding file is empty
						 */
						if(( Color.BLACK.equals( move.getPlayerSide()) && 
							!(possibleSourceRanks.get( i) - 1 == rankDest && 
							squares[fileDest][rankDest] == null) && 
							!( possibleSourceRanks.get( i) == Rank._7.ordinal() && 
							possibleSourceRanks.get( i) - 2 == rankDest && 
							squares[fileDest][rankDest+1] == null && squares[fileDest][rankDest] == null)) || 
							( Color.WHITE.equals( move.getPlayerSide()) && 
							!(possibleSourceRanks.get( i) + 1 == rankDest && 
							squares[fileDest][rankDest] == null) && 
							!( possibleSourceRanks.get( i) == Rank._2.ordinal() && 
							possibleSourceRanks.get( i) + 2 == rankDest && 
							squares[fileDest][rankDest-1] == null && squares[fileDest][rankDest] == null)))
						{
							possibleSourceFiles.remove( i);
							possibleSourceRanks.remove( i);
							continue;
						}
					}
					/* The move is not a capturing move and source and destination files are different. 
					 * It means this square is not a valid source square, remove it from the possible source squares.
					 */
					else
					{
						possibleSourceFiles.remove( i);
						possibleSourceRanks.remove( i);
						continue;
					}
				}
				// If the moving piece is a knight
				else if( PieceLetter.N.equals( move.getPieceLetter()))
				{
					/* If the move is a capturing move and the destination square is empty, or the move is not a capturing move 
					 * and the destination square is not empty, throw an invalid move exception.
					 */
					if(( move.isCapture() && squares[fileDest][rankDest] == null) || 
						( !move.isCapture() && squares[fileDest][rankDest] != null))
						throw new InvalidMoveException( "Move " + move.getTurnIndex() + ". " + 
								move.getMoveText() + " is not a valid move.");
					
					// If the move is not an L-shaped move, this square is not a valid source square, remove it from the possible source squares.
					if( !(Math.abs( fileSrc - fileDest) == 2 && Math.abs( rankSrc - rankDest) == 1) && 
						!(Math.abs( fileSrc - fileDest) == 1 && Math.abs( rankSrc - rankDest) == 2))
					{
						possibleSourceFiles.remove( i);
						possibleSourceRanks.remove( i);
						continue;
					}
				}
				// If the moving piece is a bishop
				else if( PieceLetter.B.equals( move.getPieceLetter()))
				{
					/* If the move is a capturing move and the destination square is empty, or the move is not a capturing move 
					 * and the destination square is not empty, throw an invalid move exception.
					 */
					if(( move.isCapture() && squares[fileDest][rankDest] == null) || 
						( !move.isCapture() && squares[fileDest][rankDest] != null))
						throw new InvalidMoveException( "Move " + move.getTurnIndex() + ". " + 
								move.getMoveText() + " is not a valid move.");
					
					// If the move is not diagonal, this square is not a valid source square, remove it from the possible source squares.
					if( !Move.isDiagonal( fileSrc, rankSrc, fileDest, rankDest))
					{
						possibleSourceFiles.remove( i);
						possibleSourceRanks.remove( i);
						continue;
					}
					
					// Check if squares between source and destination are empty
					if( fileSrc < fileDest && rankSrc < rankDest) // The move is towards the upper right corner
					{
						/* If there is a non-empty square between source and destination squares, 
						 * this square is not a valid source square, remove it from the possible source squares.
						 */
						boolean removed = false;
						for( int j = 1; j < fileDest - fileSrc; j++)
						{
							if( squares[fileSrc+j][rankSrc+j] != null)
							{
								possibleSourceFiles.remove( i);
								possibleSourceRanks.remove( i);
								removed = true;
								break; // No need to check the other squares;
							}
						}
						if( removed) // Skip rest of the main loop
							continue;
					}
					else if( fileSrc < fileDest && rankDest < rankSrc) // The move is towards the lower right corner
					{
						/* If there is a non-empty square between source and destination squares, 
						 * this square is not a valid source square, remove it from the possible source squares.
						 */
						boolean removed = false;
						for( int j = 1; j < fileDest - fileSrc; j++)
						{
							if( squares[fileSrc+j][rankSrc-j] != null)
							{
								possibleSourceFiles.remove( i);
								possibleSourceRanks.remove( i);
								removed = true;
								break; // No need to check the other squares;
							}
						}
						if( removed) // Skip rest of the main loop
							continue;
					}
					else if( fileDest < fileSrc && rankSrc < rankDest) // The move is towards the upper left corner
					{
						/* If there is a non-empty square between source and destination squares, 
						 * this square is not a valid source square, remove it from the possible source squares.
						 */
						boolean removed = false;
						for( int j = 1; j < fileSrc - fileDest; j++)
						{
							if( squares[fileSrc-j][rankSrc+j] != null)
							{
								possibleSourceFiles.remove( i);
								possibleSourceRanks.remove( i);
								removed = true;
								break; // No need to check the other squares;
							}
						}
						if( removed) // Skip rest of the main loop
							continue;
					}
					else if( fileDest < fileSrc && rankDest < rankSrc) // The move is towards the lower left corner
					{
						/* If there is a non-empty square between source and destination squares, 
						 * this square is not a valid source square, remove it from the possible source squares.
						 */
						boolean removed = false;
						for( int j = 1; j < fileSrc - fileDest; j++)
						{
							if( squares[fileSrc-j][rankSrc-j] != null)
							{
								possibleSourceFiles.remove( i);
								possibleSourceRanks.remove( i);
								removed = true;
								break; // No need to check the other squares;
							}
						}
						if( removed) // Skip rest of the main loop
							continue;
					}
				}
				// If the moving piece is a rook
				else if( PieceLetter.R.equals( move.getPieceLetter()))
				{
					/* If the move is a capturing move and the destination square is empty, or the move is not a capturing move 
					 * and the destination square is not empty, throw an invalid move exception.
					 */
					if(( move.isCapture() && squares[fileDest][rankDest] == null) || 
						( !move.isCapture() && squares[fileDest][rankDest] != null))
						throw new InvalidMoveException( "Move " + move.getTurnIndex() + ". " + 
								move.getMoveText() + " is not a valid move.");
					
					// If the move is not horizontal or vertical, this square is not a valid source square, remove it from the possible source squares.
					if( !Move.isHorizontalOrVertical( fileSrc, rankSrc, fileDest, rankDest))
					{
						possibleSourceFiles.remove( i);
						possibleSourceRanks.remove( i);
						continue;
					}
					
					// Check if squares between source and destination are empty
					if( fileSrc == fileDest) // The move is vertical
					{
						if( rankSrc < rankDest) // The move is towards top
						{
							/* If there is a non-empty square between source and destination squares, 
							 * this square is not a valid source square, remove it from the possible source squares.
							 */
							boolean removed = false;
							for( int j = 1; j < rankDest - rankSrc; j++)
							{
								if( squares[fileSrc][rankSrc+j] != null)
								{
									possibleSourceFiles.remove( i);
									possibleSourceRanks.remove( i);
									removed = true;
									break; // No need to check the other squares;
								}
							}
							if( removed) // Skip rest of the main loop
								continue;
						}
						else // The move is towards bottom
						{
							/* If there is a non-empty square between source and destination squares, 
							 * this square is not a valid source square, remove it from the possible source squares.
							 */
							boolean removed = false;
							for( int j = 1; j < rankSrc - rankDest; j++)
							{
								if( squares[fileSrc][rankSrc-j] != null)
								{
									possibleSourceFiles.remove( i);
									possibleSourceRanks.remove( i);
									removed = true;
									break; // No need to check the other squares;
								}
							}
							if( removed) // Skip rest of the main loop
								continue;
						}
					}
					else if( rankSrc == rankDest) // The move is horizontal
					{
						if( fileSrc < fileDest) // The move is towards right
						{
							/* If there is a non-empty square between source and destination squares, 
							 * this square is not a valid source square, remove it from the possible source squares.
							 */
							boolean removed = false;
							for( int j = 1; j < fileDest - fileSrc; j++)
							{
								if( squares[fileSrc+j][rankSrc] != null)
								{
									possibleSourceFiles.remove( i);
									possibleSourceRanks.remove( i);
									removed = true;
									break; // No need to check the other squares;
								}
							}
							if( removed) // Skip rest of the main loop
								continue;
						}
						else // The move is towards left
						{
							/* If there is a non-empty square between source and destination squares, 
							 * this square is not a valid source square, remove it from the possible source squares.
							 */
							boolean removed = false;
							for( int j = 1; j < fileSrc - fileDest; j++)
							{
								if( squares[fileSrc-j][rankSrc] != null)
								{
									possibleSourceFiles.remove( i);
									possibleSourceRanks.remove( i);
									removed = true;
									break; // No need to check the other squares;
								}
							}
							if( removed) // Skip rest of the main loop
								continue;
						}
					}
				}
				// If the moving piece is a queen
				else if( PieceLetter.Q.equals( move.getPieceLetter()))
				{
					/* If the move is a capturing move and the destination square is empty, or the move is not a capturing move 
					 * and the destination square is not empty, throw an invalid move exception.
					 */
					if(( move.isCapture() && squares[fileDest][rankDest] == null) || 
						( !move.isCapture() && squares[fileDest][rankDest] != null))
						throw new InvalidMoveException( "Move " + move.getTurnIndex() + ". " + 
								move.getMoveText() + " is not a valid move.");
					
					// If the move is not horizontal or vertical or diagonal, this square is not a valid source square, remove it from the possible source squares.
					if( !Move.isHorizontalOrVertical( possibleSourceFiles.get( i), possibleSourceRanks.get( i), fileDest, rankDest) && 
						!Move.isDiagonal( possibleSourceFiles.get( i), possibleSourceRanks.get( i), fileDest, rankDest))
					{
						possibleSourceFiles.remove( i);
						possibleSourceRanks.remove( i);
						continue;
					}
					
					// Check if squares between source and destination are empty
					if( Move.isHorizontalOrVertical( fileSrc, rankSrc, fileDest, rankDest))
					{
						if( fileSrc == fileDest) // The move is vertical
						{
							if( rankSrc < rankDest) // The move is towards top
							{
								/* If there is a non-empty square between source and destination squares, 
								 * this square is not a valid source square, remove it from the possible source squares.
								 */
								boolean removed = false;
								for( int j = 1; j < rankDest - rankSrc; j++)
								{
									if( squares[fileSrc][rankSrc+j] != null)
									{
										possibleSourceFiles.remove( i);
										possibleSourceRanks.remove( i);
										removed = true;
										break; // No need to check the other squares;
									}
								}
								if( removed) // Skip rest of the main loop
									continue;
							}
							else // The move is towards bottom
							{
								/* If there is a non-empty square between source and destination squares, 
								 * this square is not a valid source square, remove it from the possible source squares.
								 */
								boolean removed = false;
								for( int j = 1; j < rankSrc - rankDest; j++)
								{
									if( squares[fileSrc][rankSrc-j] != null)
									{
										possibleSourceFiles.remove( i);
										possibleSourceRanks.remove( i);
										removed = true;
										break; // No need to check the other squares;
									}
								}
								if( removed) // Skip rest of the main loop
									continue;
							}
						}
						else if( rankSrc == rankDest) // The move is horizontal
						{
							if( fileSrc < fileDest) // The move is towards right
							{
								/* If there is a non-empty square between source and destination squares, 
								 * this square is not a valid source square, remove it from the possible source squares.
								 */
								boolean removed = false;
								for( int j = 1; j < fileDest - fileSrc; j++)
								{
									if( squares[fileSrc+j][rankSrc] != null)
									{
										possibleSourceFiles.remove( i);
										possibleSourceRanks.remove( i);
										removed = true;
										break; // No need to check the other squares;
									}
								}
								if( removed) // Skip rest of the main loop
									continue;
							}
							else // The move is towards left
							{
								/* If there is a non-empty square between source and destination squares, 
								 * this square is not a valid source square, remove it from the possible source squares.
								 */
								boolean removed = false;
								for( int j = 1; j < fileSrc - fileDest; j++)
								{
									if( squares[fileSrc-j][rankSrc] != null)
									{
										possibleSourceFiles.remove( i);
										possibleSourceRanks.remove( i);
										removed = true;
										break; // No need to check the other squares;
									}
								}
								if( removed) // Skip rest of the main loop
									continue;
							}
						}
					}
					else if( Move.isDiagonal( possibleSourceFiles.get( i), possibleSourceRanks.get( i), fileDest, rankDest)) // The move is diagonal
					{
						if( fileSrc < fileDest && rankSrc < rankDest) // The move is towards the upper right corner
						{
							/* If there is a non-empty square between source and destination squares, 
							 * this square is not a valid source square, remove it from the possible source squares.
							 */
							boolean removed = false;
							for( int j = 1; j < fileDest - fileSrc; j++)
							{
								if( squares[fileSrc+j][rankSrc+j] != null)
								{
									possibleSourceFiles.remove( i);
									possibleSourceRanks.remove( i);
									removed = true;
									break; // No need to check the other squares;
								}
							}
							if( removed) // Skip rest of the main loop
								continue;
						}
						else if( fileSrc < fileDest && rankDest < rankSrc) // The move is towards the lower right corner
						{
							/* If there is a non-empty square between source and destination squares, 
							 * this square is not a valid source square, remove it from the possible source squares.
							 */
							boolean removed = false;
							for( int j = 1; j < fileDest - fileSrc; j++)
							{
								if( squares[fileSrc+j][rankSrc-j] != null)
								{
									possibleSourceFiles.remove( i);
									possibleSourceRanks.remove( i);
									removed = true;
									break; // No need to check the other squares;
								}
							}
							if( removed) // Skip rest of the main loop
								continue;
						}
						else if( fileDest < fileSrc && rankSrc < rankDest) // The move is towards the upper left corner
						{
							/* If there is a non-empty square between source and destination squares, 
							 * this square is not a valid source square, remove it from the possible source squares.
							 */
							boolean removed = false;
							for( int j = 1; j < fileSrc - fileDest; j++)
							{
								if( squares[fileSrc-j][rankSrc+j] != null)
								{
									possibleSourceFiles.remove( i);
									possibleSourceRanks.remove( i);
									removed = true;
									break; // No need to check the other squares;
								}
							}
							if( removed) // Skip rest of the main loop
								continue;
						}
						else if( fileDest < fileSrc && rankDest < rankSrc) // The move is towards the lower left corner
						{
							/* If there is a non-empty square between source and destination squares, 
							 * this square is not a valid source square, remove it from the possible source squares.
							 */
							boolean removed = false;
							for( int j = 1; j < fileSrc - fileDest; j++)
							{
								if( squares[fileSrc-j][rankSrc-j] != null)
								{
									possibleSourceFiles.remove( i);
									possibleSourceRanks.remove( i);
									removed = true;
									break; // No need to check the other squares;
								}
							}
							if( removed) // Skip rest of the main loop
								continue;
						}
					}
				}
				// If the moving piece is a king
				else if( PieceLetter.K.equals( move.getPieceLetter()))
				{
					/* If the move is a capturing move and the destination square is empty, or the move is not a capturing move 
					 * and the destination square is not empty, throw an invalid move exception.
					 */
					if(( move.isCapture() && squares[fileDest][rankDest] == null) || 
						( !move.isCapture() && squares[fileDest][rankDest] != null))
						throw new InvalidMoveException( "Move " + move.getTurnIndex() + ". " + 
								move.getMoveText() + " is not a valid move.");
					
					/* If the move is more than one square in either direction, or the destination square is the source square, 
					 * this square is not a valid source square, remove it from the possible source squares.
					 */
					int fileDifference = Math.abs( fileSrc - fileDest);
					int rankDifference = Math.abs( rankSrc - rankDest);
					if( fileDifference > 1 || rankDifference > 1 || (fileDifference == 0 && rankDifference == 0))
					{
						possibleSourceFiles.remove( i);
						possibleSourceRanks.remove( i);
						continue;
					}
				}
				
				// Check if the move results in a discovered check by temporarily executing the move and inspecting the board for a check for the playing side.
				Piece tmpSource = squares[fileSrc][rankSrc];
				Piece tmpDestination = squares[fileDest][rankDest];
				squares[fileSrc][rankSrc] = null;
				squares[fileDest][rankDest] = tmpSource;
				
				boolean checkingCondition = checkCheckingCondition( move.getPlayerSide());
				
				// After the inspection is complete, take back the temporarily executed move.
				squares[fileSrc][rankSrc] = tmpSource;
				squares[fileDest][rankDest] = tmpDestination;
				
				// If the move results in a discovered check, this square is not a valid source square, remove it from the possible source squares.
				if( checkingCondition)
				{
					possibleSourceFiles.remove( i);
					possibleSourceRanks.remove( i);
				}
			}
			
			// If there are no more possible source squares/more than one possible source square after removing the invalid ones, throw an invalid move exception.
			if( possibleSourceFiles.isEmpty())
				throw new InvalidMoveException( "Move " + move.getTurnIndex() + ". " + 
												move.getMoveText() + " is not a valid move.");
			else if( possibleSourceFiles.size() > 1)
				throw new InvalidMoveException( "Move " + move.getTurnIndex() + ". " + 
						move.getMoveText() + " is ambigious.");
			else // There is only one possible source square, set the source file/rank of the move if it's not already known.
			{
				if( move.getSourceFile() == null)
					move.setSourceFile( Move.File.class.getEnumConstants()[ possibleSourceFiles.get(0)]);
				if( move.getSourceRank() == null)
					move.setSourceRank( Move.Rank.class.getEnumConstants()[ possibleSourceRanks.get(0)]);
			}
		}
		
		// Execute the move and return it.
		executeMove( move);
		return move;
	}
	
	// Check if given position is threatened by any of the opponents pieces.
	private boolean isThreatened( int i, int j, Color playerSide)
	{
		Color otherPlayer = null;
		int f, r;
		
		// Find the opponent's color
		if( Color.BLACK.equals( playerSide))
			otherPlayer = Color.WHITE;
		else
			otherPlayer = Color.BLACK;
		
		// Check diagonally towards upper right corner. Move in that direction until finding a piece, or reaching the edge of the board.
		for( f = i+1, r = j+1; f < BOARD_SIZE && r < BOARD_SIZE; f++, r++)
		{
			if( squares[f][r] != null)
			{
				// If the piece on the board belongs to the player, stop checking this diagonal.
				if( playerSide.equals( squares[f][r].getColor()))
					break;
				else // The piece on the board belongs to the opponent, take action depending on type of the piece.
				{
					// Knight and rook cannot threaten diagonally, stop checking this diagonal.
					if( PieceLetter.N.equals( squares[f][r].getRealPieceLetter()) || 
						PieceLetter.R.equals( squares[f][r].getRealPieceLetter()))
						break;
					// Bishop and queen can threaten diagonally, return true.
					else if( PieceLetter.B.equals( squares[f][r].getRealPieceLetter()) || 
							PieceLetter.Q.equals( squares[f][r].getRealPieceLetter()))
						return true;
					/* King can threaten diagonally, but only the square immediately next to itself. 
					 * Check if that's the case and return true if necessary, otherwise stop checking this diagonal.
					 */
					else if( PieceLetter.K.equals( squares[f][r].getRealPieceLetter()))
					{
						if( r == j+1)
							return true;
						else
							break;
					}
					/* The only remaining piece to check is pawn. Pawn can threaten diagonally, but only the square immediately next to itself and only in one direction 
					 * (black can threaten rank r-1 and white can threaten rank r+1).
					 * Check if that's the case and return true if necessary, otherwise stop checking this diagonal.
					*/
					else
					{
						assert PieceLetter.P.equals( squares[f][r].getRealPieceLetter()) : squares[f][r].getRealPieceLetter();
						if( r == j+1 && Color.WHITE.equals( playerSide))
							return true;
						else
							break;
					}
				}
			}
		}
		
		// Check diagonally towards upper left corner. Move in that direction until finding a piece, or reaching the edge of the board.
		for( f = i-1, r = j+1; f >= 0 && r < BOARD_SIZE; f--, r++)
		{
			if( squares[f][r] != null)
			{
				// If the piece on the board belongs to the player, stop checking this diagonal.
				if( playerSide.equals( squares[f][r].getColor()))
					break;
				else // The piece on the board belongs to the opponent, take action depending on type of the piece.
				{
					// Knight and rook cannot threaten diagonally, stop checking this diagonal.
					if( PieceLetter.N.equals( squares[f][r].getRealPieceLetter()) || 
						PieceLetter.R.equals( squares[f][r].getRealPieceLetter()))
						break;
					// Bishop and queen can threaten diagonally, return true.
					else if( PieceLetter.B.equals( squares[f][r].getRealPieceLetter()) || 
							PieceLetter.Q.equals( squares[f][r].getRealPieceLetter()))
						return true;
					/* King can threaten diagonally, but only the square immediately next to itself. 
					 * Check if that's the case and return true if necessary, otherwise stop checking this diagonal.
					 */
					else if( PieceLetter.K.equals( squares[f][r].getRealPieceLetter()))
					{
						if( r == j+1)
							return true;
						else
							break;
					}
					/* The only remaining piece to check is pawn. Pawn can threaten diagonally, but only the square immediately next to itself and only in one direction 
					 * (black can threaten rank r-1 and white can threaten rank r+1).
					 * Check if that's the case and return true if necessary, otherwise stop checking this diagonal.
					 */
					else
					{
						assert PieceLetter.P.equals( squares[f][r].getRealPieceLetter()) : squares[f][r].getRealPieceLetter();
						if( r == j+1 && Color.WHITE.equals( playerSide))
							return true;
						else
							break;
					}
				}
			}
		}
		
		// Check diagonally towards lower left corner. Move in that direction until finding a piece, or reaching the edge of the board.
		for( f = i-1, r = j-1; f >= 0 && r >= 0; f--, r--)
		{
			if( squares[f][r] != null)
			{
				// If the piece on the board belongs to the player, stop checking this diagonal.
				if( playerSide.equals( squares[f][r].getColor()))
					break;
				else // The piece on the board belongs to the opponent, take action depending on type of the piece.
				{
					// Knight and rook cannot threaten diagonally, stop checking this diagonal.
					if( PieceLetter.N.equals( squares[f][r].getRealPieceLetter()) || 
						PieceLetter.R.equals( squares[f][r].getRealPieceLetter()))
						break;
					// Bishop and queen can threaten diagonally, return true.
					else if( PieceLetter.B.equals( squares[f][r].getRealPieceLetter()) || 
							PieceLetter.Q.equals( squares[f][r].getRealPieceLetter()))
						return true;
					/* King can threaten diagonally, but only the square immediately next to itself. 
					 * Check if that's the case and return true if necessary, otherwise stop checking this diagonal.
					 */
					else if( PieceLetter.K.equals( squares[f][r].getRealPieceLetter()))
					{
						if( r == j-1)
							return true;
						else
							break;
					}
					/* The only remaining piece to check is pawn. Pawn can threaten diagonally, but only the square immediately next to itself and only in one direction 
					 * (black can threaten rank r-1 and white can threaten rank r+1).
					 * Check if that's the case and return true if necessary, otherwise stop checking this diagonal.
					 */
					else
					{
						assert PieceLetter.P.equals( squares[f][r].getRealPieceLetter()) : squares[f][r].getRealPieceLetter();
						if( r == j-1 && Color.BLACK.equals( playerSide))
							return true;
						else
							break;
					}
				}
			}
		}
		
		// Check diagonally towards lower right corner
		for( f = i+1, r = j-1; f < BOARD_SIZE && r >= 0; f++, r--)
		{
			if( squares[f][r] != null)
			{
				// If the piece on the board belongs to the player, stop checking this diagonal.
				if( playerSide.equals( squares[f][r].getColor()))
					break;
				else // The piece on the board belongs to the opponent, take action depending on type of the piece.
				{
					// Knight and rook cannot threaten diagonally, stop checking this diagonal.
					if( PieceLetter.N.equals( squares[f][r].getRealPieceLetter()) || 
						PieceLetter.R.equals( squares[f][r].getRealPieceLetter()))
						break;
					// Bishop and queen can threaten diagonally, return true.
					else if( PieceLetter.B.equals( squares[f][r].getRealPieceLetter()) || 
							PieceLetter.Q.equals( squares[f][r].getRealPieceLetter()))
						return true;
					/* King can threaten diagonally, but only the square immediately next to itself. 
					 * Check if that's the case and return true if necessary, otherwise stop checking this diagonal.
					 */
					else if( PieceLetter.K.equals( squares[f][r].getRealPieceLetter()))
					{
						if( r == j-1)
							return true;
						else
							break;
					}
					/* The only remaining piece to check is pawn. Pawn can threaten diagonally, but only the square immediately next to itself and only in one direction 
					 * (black can threaten rank r-1 and white can threaten rank r+1).
					 * Check if that's the case and return true if necessary, otherwise stop checking this diagonal.
					 */
					else
					{
						assert PieceLetter.P.equals( squares[f][r].getRealPieceLetter()) : squares[f][r].getRealPieceLetter();
						if( r == j-1 && Color.BLACK.equals( playerSide))
							return true;
						else
							break;
					}
				}
			}
		}
		
		// Check horizontally towards left
		for( f = i-1, r = j; f >= 0; f--)
		{
			if( squares[f][r] != null)
			{
				// If the piece on the board belongs to the player, stop checking this rank.
				if( playerSide.equals( squares[f][r].getColor()))
					break;
				else // The piece on the board belongs to the opponent, take action depending on type of the piece.
				{
					// Pawn, knight and bishop cannot threaten horizontally, stop checking this rank.
					if( PieceLetter.P.equals( squares[f][r].getRealPieceLetter()) || 
						PieceLetter.N.equals( squares[f][r].getRealPieceLetter()) || 
						PieceLetter.B.equals( squares[f][r].getRealPieceLetter()))
						break;
					// Rook and queen can threaten horizontally, return true.
					else if( PieceLetter.R.equals( squares[f][r].getRealPieceLetter()) || 
							PieceLetter.Q.equals( squares[f][r].getRealPieceLetter()))
						return true;
					/* The only remaining piece to check is king. King can threaten horizontally, but only the square immediately next to itself. 
					 * Check if that's the case and return true if necessary, otherwise stop checking this rank.
					 */
					else
					{
						assert PieceLetter.K.equals( squares[f][r].getRealPieceLetter()) : squares[f][r].getRealPieceLetter();
						if( f == i-1)
							return true;
						else
							break;
					}
				}
			}
		}

		// Check horizontally towards right
		for( f = i+1, r = j; f < BOARD_SIZE; f++)
		{
			if( squares[f][r] != null)
			{
				// If the piece on the board belongs to the player, stop checking this rank.
				if( playerSide.equals( squares[f][r].getColor()))
					break;
				else // The piece on the board belongs to the opponent, take action depending on type of the piece.
				{
					// Pawn, knight and bishop cannot threaten horizontally, stop checking this rank.
					if( PieceLetter.P.equals( squares[f][r].getRealPieceLetter()) || 
							PieceLetter.N.equals( squares[f][r].getRealPieceLetter()) || 
							PieceLetter.B.equals( squares[f][r].getRealPieceLetter()))
						break;
					// Rook and queen can threaten horizontally, return true.
					else if( PieceLetter.R.equals( squares[f][r].getRealPieceLetter()) || 
							PieceLetter.Q.equals( squares[f][r].getRealPieceLetter()))
						return true;
					/* The only remaining piece to check is king. King can threaten horizontally, but only the square immediately next to itself. 
					 * Check if that's the case and return true if necessary, otherwise stop checking this rank.
					 */
					else
					{
						assert PieceLetter.K.equals( squares[f][r].getRealPieceLetter()) : squares[f][r].getRealPieceLetter();
						if( f == i+1)
							return true;
						else
							break;
					}
				}
			}
		}

		// Check vertically upwards
		for( r = j+1, f = i; r < BOARD_SIZE; r++)
		{
			if( squares[f][r] != null)
			{
				// If the piece on the board belongs to the player, stop checking this file.
				if( playerSide.equals( squares[f][r].getColor()))
					break;
				else // The piece on the board belongs to the opponent, take action depending on type of the piece.
				{
					// Pawn, knight and bishop cannot threaten vertically, stop checking this file.
					if( PieceLetter.P.equals( squares[f][r].getRealPieceLetter()) || 
							PieceLetter.N.equals( squares[f][r].getRealPieceLetter()) || 
							PieceLetter.B.equals( squares[f][r].getRealPieceLetter()))
						break;
					// Rook and queen can threaten vertically, return true.
					else if( PieceLetter.R.equals( squares[f][r].getRealPieceLetter()) || 
							PieceLetter.Q.equals( squares[f][r].getRealPieceLetter()))
						return true;
					/* The only remaining piece to check is king. King can threaten vertically, but only the square immediately next to itself. 
					 * Check if that's the case and return true if necessary, otherwise stop checking this file.
					 */
					else
					{
						assert PieceLetter.K.equals( squares[f][r].getRealPieceLetter()) : squares[f][r].getRealPieceLetter();
						if( r == j+1)
							return true;
						else
							break;
					}
				}
			}
		}

		// Check vertically downwards
		for( r = j-1, f = i; r >= 0; r--)
		{
			if( squares[f][r] != null)
			{
				// If the piece on the board belongs to the player, stop checking this file.
				if( playerSide.equals( squares[f][r].getColor()))
					break;
				else // The piece on the board belongs to the opponent, take action depending on type of the piece.
				{
					// Pawn, knight and bishop cannot threaten vertically, stop checking this file.
					if( PieceLetter.P.equals( squares[f][r].getRealPieceLetter()) || 
							PieceLetter.N.equals( squares[f][r].getRealPieceLetter()) || 
							PieceLetter.B.equals( squares[f][r].getRealPieceLetter()))
						break;
					// Rook and queen can threaten vertically, return true.
					else if( PieceLetter.R.equals( squares[f][r].getRealPieceLetter()) || 
							PieceLetter.Q.equals( squares[f][r].getRealPieceLetter()))
						return true;
					/* The only remaining piece to check is king. King can threaten vertically, but only the square immediately next to itself. 
					 * Check if that's the case and return true if necessary, otherwise stop checking this file.
					 */
					else
					{
						assert PieceLetter.K.equals( squares[f][r].getRealPieceLetter()) : squares[f][r].getRealPieceLetter();
						if( r == j-1)
							return true;
						else
							break;
					}
				}
			}
		}
		
		// Check for knights. There are eight possible locations for knights to threaten this square.
		int[] possibleKnightFiles = {i+1, i+2, i+2, i+1, i-1, i-2, i-2, i-1};
		int[] possibleKnightRanks = {j+2, j+1, j-1, j-2, j-2, j-1, j+1, j+2};
		
		for( int index = 0; index < possibleKnightFiles.length; index++)
		{
			f = possibleKnightFiles[index];
			r = possibleKnightRanks[index];
			// Check if the possible knight location is within the board.
			if( f >= 0 && f < BOARD_SIZE && r >= 0 && r < BOARD_SIZE)
			{
				// If the square contains a knight belonging to the opponent, return true.
				if( squares[f][r] != null && otherPlayer.equals( squares[f][r].getColor()) &&
					PieceLetter.N.equals( squares[f][r].getRealPieceLetter()))
					return true;
			}
		}
		
		return false;
	}
	
	// Find position of the player's king and check if it's threatened
	private boolean checkCheckingCondition( Color playerSide)
	{
		for( int i = 0; i < BOARD_SIZE; i++)
		{
			for( int j = 0; j < BOARD_SIZE; j++)
			{
				if( squares[i][j] != null && PieceLetter.K.equals( squares[i][j].getRealPieceLetter()) && 
					playerSide.equals( squares[i][j].getColor()))
					return isThreatened( i, j, playerSide);
			}
		}
		
		assert false; // Code should never reach here
		return false;
	}
	
	// Execute given move
	public void executeMove( Move move) throws IllegalPromotionException, InvalidMoveException
	{
		// If the move is a castling move
		if( move instanceof CastlingMove)
		{
			// If the move is a kingside castling move
			if( ((CastlingMove)move).isKingSideCastling())
			{
				// For the black side; move king from e8 to g8, and rook from h8 to f8
				if( Color.BLACK.equals( move.getPlayerSide()))
				{
					Piece king = squares[4][7]; // e8
					Piece rook = squares[7][7]; // h8

					squares[6][7] = king; // g8
					squares[5][7] = rook; // f8

					squares[4][7] = null;
					squares[7][7] = null;
				}
				else // For the white side; move king from e1 to g1, and rook from h1 to f1
				{
					Piece king = squares[4][0]; // e1
					Piece rook = squares[7][0]; // h1

					squares[6][0] = king; // g1
					squares[5][0] = rook; // f1

					squares[4][0] = null;
					squares[7][0] = null;
				}
			}
			else //The move is a queenside castling move
			{
				// For the black side; move king from e8 to c8, and rook from a8 to d8
				if( Color.BLACK.equals( move.getPlayerSide()))
				{
					Piece king = squares[4][7]; // e8
					Piece rook = squares[0][7]; // a8

					squares[2][7] = king; // c8
					squares[3][7] = rook; // d8

					squares[4][7] = null;
					squares[0][7] = null;
				}
				else // For the white side; move king from e1 to c1, and rook from a1 to d1
				{
					Piece king = squares[4][0]; // e1
					Piece rook = squares[0][0]; // a1

					squares[2][0] = king; // c1
					squares[3][0] = rook; // d1

					squares[4][0] = null;
					squares[0][0] = null;
				}
			}
		}
		else // The move is not a castling move
		{
			// Get the moving piece from the source square
			Piece source = squares[move.getSourceFile().ordinal()][move.getSourceRank().ordinal()];
			
			// If it's a capturing move
			if( move.isCapture())
			{
				// If it's an en passant move
				if( move instanceof EnPassantMove)
				{
					/* En passant move is an exception in that the captured piece is not in the destination square of the capturing piece.
					 * For the black side, the captured white pawn is in the next rank, compared to the destination of the capturing black pawn.
					 * Get the captured piece, which is at destination file/destination rank+1, and set it as captured.
					 */
					if( Color.BLACK.equals( move.getPlayerSide()))
					{
						Piece capturedPiece = squares[move.getDestinationFile().ordinal()][move.getDestinationRank().ordinal()+1];
						capturedPiece.setCaptured( true);
						capturedPiece.setCapturedOnFile( move.getDestinationFile());
						capturedPiece.setCapturedOnRank( Rank.values()[move.getDestinationRank().ordinal()+1]);
						
						capturedPieces.add( capturedPiece);
						inGamePieces.remove( capturedPiece);
						
						if( move.getCapturedPiece() == null)
							move.setCapturedPiece( capturedPiece);
						
						squares[move.getDestinationFile().ordinal()][move.getDestinationRank().ordinal()+1] = null;
					}
					/* For the white side, the captured black pawn is in the previous rank, compared to the destination of the capturing white pawn.
					 * Get the captured piece, which is at destination file/destination rank-1, and set it as captured.
					 */
					else
					{
						Piece capturedPiece = squares[move.getDestinationFile().ordinal()][move.getDestinationRank().ordinal()-1];
						capturedPiece.setCaptured( true);
						capturedPiece.setCapturedOnFile( move.getDestinationFile());
						capturedPiece.setCapturedOnRank( Rank.values()[move.getDestinationRank().ordinal()-1]);
						
						capturedPieces.add( capturedPiece);
						inGamePieces.remove( capturedPiece);

						if( move.getCapturedPiece() == null)
							move.setCapturedPiece( capturedPiece);
						
						squares[move.getDestinationFile().ordinal()][move.getDestinationRank().ordinal()-1] = null;
					}
				}
				else // The move is not an en passant move, get the captured piece, which is at destination file/destination rank, and set it as captured.
				{
					Piece capturedPiece = squares[move.getDestinationFile().ordinal()][move.getDestinationRank().ordinal()];
					capturedPiece.setCaptured( true);
					capturedPiece.setCapturedOnFile( move.getDestinationFile());
					capturedPiece.setCapturedOnRank( move.getDestinationRank());
					
					capturedPieces.add( capturedPiece);
					inGamePieces.remove( capturedPiece);

					if( move.getCapturedPiece() == null)
						move.setCapturedPiece( capturedPiece);
				}
			}
			
			// Move the moving piece from the source square to the destination square.
			squares[move.getSourceFile().ordinal()][move.getSourceRank().ordinal()] = null;
			squares[move.getDestinationFile().ordinal()][move.getDestinationRank().ordinal()] = source;
			
			/* We should know if and when a pawn has moved, as en passant move can only occur immediately after a pawn moves two ranks forward from its starting position.
			 * So, set the pawn as first moved in this turn, if it hasn't moved already.
			 */
			if( PieceLetter.P.equals( source.getRealPieceLetter()))
			{
				if( ((Pawn)source).getFirstMovedOnTurn() == -1)
					((Pawn)source).setFirstMovedOnTurn( move.getTurnIndex());
				
				// If this is a promotion move, promote the moved pawn.
				if( move instanceof PromotionMove)
					((Pawn)source).promote( ((PromotionMove)move).getPromotedPieceLetter());
			}
			// We should know if and when a rook/king has moved, to handle castling. So, set the rook/king as first moved in this turn, if it hasn't moved already.
			else if( PieceLetter.R.equals( source.getRealPieceLetter()))
				if( !((Rook)source).hasMoved())
				{
					((Rook)source).setHasMoved( true);
					((Rook)source).setFirstMovedOnTurn( move.getTurnIndex());
				}
			else if( PieceLetter.K.equals( source.getRealPieceLetter()))
				if( !((King)source).hasMoved())
				{
					((King)source).setHasMoved( true);
					((King)source).setFirstMovedOnTurn( move.getTurnIndex());
				}
		}
	}

	// Take back given move
	public void takeBackMove( Move move) throws IllegalPromotionException, InvalidMoveException
	{
		// If the move was a castling move
		if( move instanceof CastlingMove)
		{
			// If the move was a kingside castling move
			if( ((CastlingMove)move).isKingSideCastling())
			{
				// For the black side; move king back from g8 to e8, and rook from f8 to h8
				if( Color.BLACK.equals( move.getPlayerSide()))
				{
					Piece king = squares[6][7]; // g8
					Piece rook = squares[5][7]; // f8

					squares[4][7] = king; // e8
					squares[7][7] = rook; // h8

					squares[6][7] = null;
					squares[5][7] = null;
				}
				else // For the white side; move king back from g1 to e1, and rook from f1 to h1
				{
					Piece king = squares[6][0]; // g1
					Piece rook = squares[5][0]; // f1

					squares[4][0] = king; // e1
					squares[7][0] = rook; // h1

					squares[6][0] = null;
					squares[5][0] = null;
				}
			}
			else // The move was a queenside castling move
			{
				// For the black side; move king back from c8 to e8, and rook from d8 to a8
				if( Color.BLACK.equals( move.getPlayerSide()))
				{
					Piece king = squares[2][7]; // c8
					Piece rook = squares[3][7]; // d8

					squares[4][7] = king; // e8
					squares[0][7] = rook; // a8

					squares[2][7] = null;
					squares[3][7] = null;
				}
				else // For the white side; move king back from c1 to e1, and rook from d1 to a1
				{
					Piece king = squares[2][0]; // c1
					Piece rook = squares[3][0]; // d1

					squares[4][0] = king; // e1
					squares[0][0] = rook; // a1

					squares[2][0] = null;
					squares[3][0] = null;
				}
			}
		}
		else // The move was not a castling move
		{
			// Get the moving piece from the destination square
			Piece source = squares[move.getDestinationFile().ordinal()][move.getDestinationRank().ordinal()];

			// If it was a capturing move
			if( move.isCapture())
			{
				// If it was an en passant move
				if( move instanceof EnPassantMove)
				{
					/* En passant move is an exception in that the captured piece is not in the destination square of the capturing piece.
					 * For the black side, the captured white pawn is in the next rank, compared to the destination of the capturing black pawn.
					 * Get the captured piece from the move, set it as not captured, and put it back on the board at destination file/destination rank+1.
					 */
					if( Color.BLACK.equals( move.getPlayerSide()))
					{
						Piece capturedPiece = move.getCapturedPiece();
						capturedPiece.setCaptured( false);
						capturedPiece.setCapturedOnFile( null);
						capturedPiece.setCapturedOnRank( null);
						
						capturedPieces.remove( capturedPiece);
						inGamePieces.add( capturedPiece);
						
						if( move.getCapturedPiece() != null)
							move.setCapturedPiece( null);

						squares[move.getDestinationFile().ordinal()][move.getDestinationRank().ordinal()+1] = capturedPiece;
					}
					/* For the white side, the captured black pawn is in the previous rank, compared to the destination of the capturing white pawn.
					 * Get the captured piece from the move, set it as not captured, and put it back on the board at destination file/destination rank-1.
					 */
					else
					{
						Piece capturedPiece = move.getCapturedPiece();
						capturedPiece.setCaptured( false);
						capturedPiece.setCapturedOnFile( null);
						capturedPiece.setCapturedOnRank( null);
						
						capturedPieces.remove( capturedPiece);
						inGamePieces.add( capturedPiece);

						if( move.getCapturedPiece() != null)
							move.setCapturedPiece( null);

						squares[move.getDestinationFile().ordinal()][move.getDestinationRank().ordinal()-1] = capturedPiece;
					}
				}
				/* If the move is not an en passant move, get the captured piece from the move, set it as not captured, 
				 * and put it back on the board at destination file/destination rank.
				 */
				else
				{
					Piece capturedPiece = move.getCapturedPiece();
					capturedPiece.setCaptured( false);
					capturedPiece.setCapturedOnFile( null);
					capturedPiece.setCapturedOnRank( null);
					
					capturedPieces.remove( capturedPiece);
					inGamePieces.add( capturedPiece);

					if( move.getCapturedPiece() != null)
						move.setCapturedPiece( null);

					squares[move.getDestinationFile().ordinal()][move.getDestinationRank().ordinal()] = capturedPiece;
				}
			}
			else // The move was not a capturing move, it means the destination square was empty before the move, so it should be empty after taking the move back.
			{
				squares[move.getDestinationFile().ordinal()][move.getDestinationRank().ordinal()] = null;
			}
			
			// Move the moving piece back from the destination square to the source square.
			squares[move.getSourceFile().ordinal()][move.getSourceRank().ordinal()] = source;

			/* We should know if and when a pawn has moved, as en passant move can only occur immediately after a pawn moves two ranks forward from its starting position.
			 * So, if the pawn has moved for the first time in this turn, set it as not moved.
			 */
			if( source instanceof Pawn)
			{
				if( ((Pawn)source).getFirstMovedOnTurn() == move.getTurnIndex())
					((Pawn)source).setFirstMovedOnTurn( -1);

				// If this was a promotion move, demote the moved pawn.
				if( move instanceof PromotionMove)
					((Pawn)source).demote();
			}
			// We should know if and when a rook/king has moved, to handle castling. So, if the rook/king has moved for the first time in this turn, set it as not moved.
			else if( PieceLetter.R.equals( source.getRealPieceLetter()))
				if( ((Rook)source).hasMoved() && ((Rook)source).getFirstMovedOnTurn() == move.getTurnIndex())
				{
					((Rook)source).setHasMoved( false);
					((Rook)source).setFirstMovedOnTurn( -1);
				}
			else if( PieceLetter.K.equals( source.getRealPieceLetter()))
				if( ((King)source).hasMoved() && ((King)source).getFirstMovedOnTurn() == move.getTurnIndex())
				{
					((King)source).setHasMoved( false);
					((King)source).setFirstMovedOnTurn( -1);
				}
		}
	}
}
