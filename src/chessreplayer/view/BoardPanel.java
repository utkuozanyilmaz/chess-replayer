/**
 * Part of the view of the MVC pattern. Handles the chess board, on the left side of the user interface.
 */

package chessreplayer.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

import javax.swing.JPanel;

import chessreplayer.controller.ChessPropertiesReader;
import chessreplayer.model.Board;
import chessreplayer.model.Game;
import chessreplayer.model.Game.Result;
import chessreplayer.move.Move.PieceLetter;
import chessreplayer.piece.Color;
import chessreplayer.piece.Piece;

public class BoardPanel extends JPanel
{
	private static final long serialVersionUID = 3645717531040360181L;
	public static final int MINIMUM_SIZE = 270;
	public static final int SIZE = 450;
	public static final java.awt.Color BACKGROUND_COLOR = java.awt.Color.LIGHT_GRAY;
	
	private Board model;
	private boolean gameHasEnded;
	private Result gameResult;
	int lastPlayedFromFile = -1;
	int lastPlayedFromRank = -1;
	int lastPlayedToFile = -1;
	int lastPlayedToRank = -1;
	
	private Semaphore semaphore;
	private BufferedImage[][] board;
	private BufferedImage sourceSquareBackgroundImage;
	private BufferedImage destinationSquareBackgroundImage;
	private HashMap<PieceLetter, Image> blackPieces;
	private HashMap<PieceLetter, Image> whitePieces;
	private ChessPropertiesReader property;

	/* Create a board panel with a fixed height, fixed width, and given properties. Also create a 2-dimensional 9x9 array to 
	 * put images into (an 8x8 board plus 1 row/column for file letters/rank digits), and 2 maps to hold images for each piece type 
	 * (1 map for black side, 1 map for white side). Lastly, create a semaphore with a single permit, which prevents the board from 
	 * updating the model while painting the view.
	 */
	public BoardPanel( ChessPropertiesReader property)
	{
		super();
		
		this.setMinimumSize( new Dimension( MINIMUM_SIZE, MINIMUM_SIZE));
		this.setMaximumSize( new Dimension( Integer.MAX_VALUE, Integer.MAX_VALUE));
		this.setPreferredSize( new Dimension( SIZE, SIZE));
		this.setSize( SIZE, SIZE);
		this.setBackground( BACKGROUND_COLOR);
		
		this.property = property;
		board = new BufferedImage[9][9];
		blackPieces = new HashMap<PieceLetter, Image>();
		whitePieces = new HashMap<PieceLetter, Image>();
		
		semaphore = new Semaphore( 1, true);
	}
	
	/* Initialize the board panel by getting the images for rank digits, file letters, an empty square at their intersection, 
	 * backgrounds of black and white squares and black and white pieces using static methods of ChessImage class that read 
	 * the image files from the file system. Put the images for rank digits, file letters, the empty square and square backgrounds 
	 * into the 9x9 array; and the images for black and white pieces into their respective maps.
	 */
	public void initialize()
	{
		for( int i = 0; i < board.length; i++)
		{
			for( int j = 0; j < board.length; j++)
			{
				if( i == 0)
				{
					switch( j)
					{
						case 1:	board[i][j] = ChessImage.getRank1Image();
								break;
						case 2:	board[i][j] = ChessImage.getRank2Image();
								break;
						case 3: board[i][j] = ChessImage.getRank3Image();
								break;
						case 4: board[i][j] = ChessImage.getRank4Image();
								break;
						case 5: board[i][j] = ChessImage.getRank5Image();
								break;
						case 6: board[i][j] = ChessImage.getRank6Image();
								break;
						case 7: board[i][j] = ChessImage.getRank7Image();
								break;
						case 8: board[i][j] = ChessImage.getRank8Image();
								break;
						default: board[i][j] = ChessImage.getEmptySquareImage();
								break;
					}
				}
				else if( j == 0)
				{
					switch( i)
					{
						case 1:	board[i][j] = ChessImage.getFileAImage();
								break;
						case 2:	board[i][j] = ChessImage.getFileBImage();
								break;
						case 3: board[i][j] = ChessImage.getFileCImage();
								break;
						case 4: board[i][j] = ChessImage.getFileDImage();
								break;
						case 5: board[i][j] = ChessImage.getFileEImage();
								break;
						case 6: board[i][j] = ChessImage.getFileFImage();
								break;
						case 7: board[i][j] = ChessImage.getFileGImage();
								break;
						case 8: board[i][j] = ChessImage.getFileHImage();
								break;
					}
				}
				else
				{
					if( (i+j) % 2 == 0)
						board[i][j] = ChessImage.getBlackBackgroundImage();
					else
						board[i][j] = ChessImage.getWhiteBackgroundImage();
				}
			}
		}
		
		destinationSquareBackgroundImage = ChessImage.getDestinationBackgroundImage();
		sourceSquareBackgroundImage = ChessImage.getSourceBackgroundImage();
		
		// PieceLetter denoting the type of the piece is the key, while image is the value.
		blackPieces.put( PieceLetter.P, ChessImage.getBlackPawnImage());
		blackPieces.put( PieceLetter.N, ChessImage.getBlackKnightImage());
		blackPieces.put( PieceLetter.B, ChessImage.getBlackBishopImage());
		blackPieces.put( PieceLetter.R, ChessImage.getBlackRookImage());
		blackPieces.put( PieceLetter.Q, ChessImage.getBlackQueenImage());
		blackPieces.put( PieceLetter.K, ChessImage.getBlackKingImage());
		
		whitePieces.put( PieceLetter.P, ChessImage.getWhitePawnImage());
		whitePieces.put( PieceLetter.N, ChessImage.getWhiteKnightImage());
		whitePieces.put( PieceLetter.B, ChessImage.getWhiteBishopImage());
		whitePieces.put( PieceLetter.R, ChessImage.getWhiteRookImage());
		whitePieces.put( PieceLetter.Q, ChessImage.getWhiteQueenImage());
		whitePieces.put( PieceLetter.K, ChessImage.getWhiteKingImage());
	}
	
	// Reset the view by updating it using a null model. The result is an empty chess board.
	public void reset()
	{
		this.updateView( null);
	}
	
	/* Paint this component using the given graphics object. The semaphore is acquired before the paint operation and 
	 * it is released after its completion, to prevent updating the view mid-painting.
	 */
	public void paintComponent( Graphics g)
	{
		try {
			semaphore.acquire();
			
			// Call the paint method of JPanel
			super.paintComponent( g);
			
			/* The chess board should be square, so get the smaller of width and height of the board panel. Round it down 
			 * to the nearest multiple of 9, as the board UI is 9x9 (an 8x8 board plus 1 row/column for file letters/rank digits).
			 */
			int panelHeight = this.getHeight();
			int panelWidth = this.getWidth();
			int size = Math.min( panelWidth, panelHeight);
			size = size - size % 9;
			
			// Find x and y coordinates of the upper left corner of the chess board, which center the chess board in the board panel.
			int upperLeftCornerX = (panelWidth - size) / 2;
			int upperLeftCornerY = (panelHeight - size) / 2;
			
			int squareSize = size / 9; // Find the width/height of a square in the chess board.
			
			/* Draw the images of rank digits, file letters, the empty square and square backgrounds from the 9x9 array, source square background and 
			 * destination square background, by resizing them to width/height of a square in the chess board if necessary. 
			 * Horizontal offset increases with increasing files (files increase from left to right, just like x coordinates) and 
			 * vertical offset decreases with increasing ranks (ranks increase from bottom to top, while y coordinates increase from top to bottom). 
			 * Because of that, horizontal offset is i * square size and vertical offset is (8-j) * square size.
			 */
			for( int i = 0; i < board.length; i++)
			{
				for( int j = 0; j < board.length; j++)
				{
					if( i == this.lastPlayedFromFile && j == this.lastPlayedFromRank)
						g.drawImage( sourceSquareBackgroundImage, upperLeftCornerX + i * squareSize, upperLeftCornerY + (8-j) * squareSize, squareSize, squareSize, this);
					else if( i == this.lastPlayedToFile && j == this.lastPlayedToRank)
						g.drawImage( destinationSquareBackgroundImage, upperLeftCornerX + i * squareSize, upperLeftCornerY + (8-j) * squareSize, squareSize, squareSize, this);
					else if( board[i][j] != null)
						g.drawImage( board[i][j], upperLeftCornerX + i * squareSize, upperLeftCornerY + (8-j) * squareSize, squareSize, squareSize, this);
				}
			}
			
			// If the model is not null, draw pieces.
			if( model != null)
			{
				Piece[][] squares = model.getSquares();
				for( int i = 0; i < squares.length; i++)
				{
					for( int j = 0; j < squares.length; j++)
					{
						// If the square is not empty
						if( squares[i][j] != null)
						{
							// Retrieve the image from the map holding piece images, depending on the piece's color and type.
							Image image = null;
							if( Color.BLACK.equals( squares[i][j].getColor()))
								image = blackPieces.get( squares[i][j].getRealPieceLetter());
							else if( Color.WHITE.equals( squares[i][j].getColor()))
								image = whitePieces.get( squares[i][j].getRealPieceLetter());
							
							/* If the image is successfully retrieved, draw it by resizing it to width/height of a square in the chess board if necessary.
							 * The offsets work as explained above, additionally add a square to both horizontal and vertical offsets to account for the row and column 
							 * including file letters and rank digits. Thus, x coordinate offset is (i+1) * square size and y coordinate offset is (7-j) * square size.
							 */
							if( image != null)
								g.drawImage( image, upperLeftCornerX + (i+1) * squareSize, upperLeftCornerY + (7-j) * squareSize, squareSize, squareSize, this);
						}
					}
				}
			}
			
			// If the game has ended, show end game text
			if( gameHasEnded && gameResult != null)
			{
				// Get the end game text corresponding to the game result
				String text = "";
				if( Result.BLACK_WINS.equals( gameResult))
					text = property.getBlackWinsText();
				else if( Result.WHITE_WINS.equals( gameResult))
					text = property.getWhiteWinsText();
				else if( Result.DRAW.equals( gameResult))
					text = property.getDrawText();
				
				String fontName = g.getFont().getName();
				int fontSize = (squareSize * 8) / text.length(); // average pixel width of a character. the final font size will be ~2 * (squareSize * 8) / text.length()
				Font font;
				FontMetrics fontMetrics;
				int width;
				
				// Gradually increase font size, until the text is too large that the text doesn't fit on the board
				do
				{
					fontMetrics = g.getFontMetrics( new Font( fontName, Font.PLAIN, ++fontSize));
					width = fontMetrics.stringWidth( text);
				} while( width <= squareSize * 8 && fontMetrics.getAscent() <= squareSize * 8);
				
				// Find the maximum font size, which makes the text fit on the board
				font = new Font( fontName, Font.PLAIN, --fontSize);
				fontMetrics = g.getFontMetrics( font);
				width = fontMetrics.stringWidth( text);
				
				// Find x and y coordinates, which center the text on the chess board.
				int x = upperLeftCornerX + squareSize * 5 - (width / 2);
				int y = upperLeftCornerY + squareSize * 4 + (fontMetrics.getAscent() / 2);
				
				// Create an attributed string, using the end game text, font built above and partially transparent, red foreground color
				java.awt.Color foregroundColor = new java.awt.Color( 255, 0, 0, 127); // Red, partially transparent
				AttributedString aStr = new AttributedString( text);
				aStr.addAttribute( TextAttribute.FOREGROUND, foregroundColor);
				aStr.addAttribute( TextAttribute.FONT, font);
				
				// Draw end game text on the chess board
				AttributedCharacterIterator iterator = aStr.getIterator();
				g.drawString( iterator, x, y);
			}
			
			semaphore.release();
		} catch( InterruptedException e) {} // Nothing to do
	}
	
	/* Update the view by using the given model. If the model is null, the result is an empty chess board. 
	 * Otherwise, chess board, flag signaling the end of the game, game result and source/destination files/ranks of last played move are updated. 
	 * The semaphore is acquired before these operations and it is released after their completion, 
	 * to prevent painting the view mid-update.
	 */
	public void updateView( Game game)
	{
		try {
			semaphore.acquire();
			if( game == null)
			{
				this.model = null;
				this.gameHasEnded = false;
				this.gameResult = null;
				
				this.lastPlayedFromFile = -1;
				this.lastPlayedFromRank = -1;
				this.lastPlayedToFile = -1;
				this.lastPlayedToRank = -1;
			}
			else
			{
				this.model = game.retrieveBoard();
				this.gameHasEnded = game.hasEnded();
				this.gameResult = game.getResult();
				
				this.lastPlayedFromFile = game.lastPlayedFromFileOrdinal();
				this.lastPlayedFromRank = game.lastPlayedFromRankOrdinal();
				this.lastPlayedToFile = game.lastPlayedToFileOrdinal();
				this.lastPlayedToRank = game.lastPlayedToRankOrdinal();
			}
			semaphore.release();
		} catch( InterruptedException e) {} // Nothing to do
	}
}
