/**
 * Holds the necessary properties and default properties.
 */

package chessreplayer.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ChessProperties implements ChessPropertiesReader
{
	private static final String PROPERTIES_FILE_PATH = "properties.xml";
	
	private static final Properties defaultProperties;
	private Properties properties;
	
	// Define default property values
	private static final String TURN_TIME_PROPERTY_NAME = "turnTime";
	private static final int DEFAULT_TURN_TIME = 1000;
	private static final int MINIMUM_TURN_TIME = 10;
	
	private static final String FRAME_TITLE_PROPERTY_NAME = "frameTitle";
	private static final String DEFAULT_FRAME_TITLE = "Chess Replayer";
	
	private static final String WHITE_WINS_TEXT_PROPERTY_NAME = "whiteWinsText";
	private static final String DEFAULT_WHITE_WINS_TEXT = "White Wins";
	
	private static final String BLACK_WINS_TEXT_PROPERTY_NAME = "blackWinsText";
	private static final String DEFAULT_BLACK_WINS_TEXT = "Black Wins";
	
	private static final String DRAW_TEXT_PROPERTY_NAME = "drawText";
	private static final String DEFAULT_DRAW_TEXT = "Draw";
	
	private static final String FILE_A_IMAGE_PROPERTY_NAME = "fileAImageFilePath";
	private static final String DEFAULT_FILE_A_IMAGE_FILE_PATH = ".\\images\\fileA.png";

	private static final String FILE_B_IMAGE_PROPERTY_NAME = "fileBImageFilePath";
	private static final String DEFAULT_FILE_B_IMAGE_FILE_PATH = ".\\images\\fileB.png";

	private static final String FILE_C_IMAGE_PROPERTY_NAME = "fileCImageFilePath";
	private static final String DEFAULT_FILE_C_IMAGE_FILE_PATH = ".\\images\\fileC.png";

	private static final String FILE_D_IMAGE_PROPERTY_NAME = "fileDImageFilePath";
	private static final String DEFAULT_FILE_D_IMAGE_FILE_PATH = ".\\images\\fileD.png";

	private static final String FILE_E_IMAGE_PROPERTY_NAME = "fileEImageFilePath";
	private static final String DEFAULT_FILE_E_IMAGE_FILE_PATH = ".\\images\\fileE.png";

	private static final String FILE_F_IMAGE_PROPERTY_NAME = "fileFImageFilePath";
	private static final String DEFAULT_FILE_F_IMAGE_FILE_PATH = ".\\images\\fileF.png";

	private static final String FILE_G_IMAGE_PROPERTY_NAME = "fileGImageFilePath";
	private static final String DEFAULT_FILE_G_IMAGE_FILE_PATH = ".\\images\\fileG.png";

	private static final String FILE_H_IMAGE_PROPERTY_NAME = "fileHImageFilePath";
	private static final String DEFAULT_FILE_H_IMAGE_FILE_PATH = ".\\images\\fileH.png";

	private static final String RANK_1_IMAGE_PROPERTY_NAME = "rank1ImageFilePath";
	private static final String DEFAULT_RANK_1_IMAGE_FILE_PATH = ".\\images\\rank1.png";

	private static final String RANK_2_IMAGE_PROPERTY_NAME = "rank2ImageFilePath";
	private static final String DEFAULT_RANK_2_IMAGE_FILE_PATH = ".\\images\\rank2.png";

	private static final String RANK_3_IMAGE_PROPERTY_NAME = "rank3ImageFilePath";
	private static final String DEFAULT_RANK_3_IMAGE_FILE_PATH = ".\\images\\rank3.png";

	private static final String RANK_4_IMAGE_PROPERTY_NAME = "rank4ImageFilePath";
	private static final String DEFAULT_RANK_4_IMAGE_FILE_PATH = ".\\images\\rank4.png";

	private static final String RANK_5_IMAGE_PROPERTY_NAME = "rank5ImageFilePath";
	private static final String DEFAULT_RANK_5_IMAGE_FILE_PATH = ".\\images\\rank5.png";

	private static final String RANK_6_IMAGE_PROPERTY_NAME = "rank6ImageFilePath";
	private static final String DEFAULT_RANK_6_IMAGE_FILE_PATH = ".\\images\\rank6.png";

	private static final String RANK_7_IMAGE_PROPERTY_NAME = "rank7ImageFilePath";
	private static final String DEFAULT_RANK_7_IMAGE_FILE_PATH = ".\\images\\rank7.png";

	private static final String RANK_8_IMAGE_PROPERTY_NAME = "rank8ImageFilePath";
	private static final String DEFAULT_RANK_8_IMAGE_FILE_PATH = ".\\images\\rank8.png";

	private static final String EMPTY_SQUARE_IMAGE_PROPERTY_NAME = "emptySquareImageFilePath";
	private static final String DEFAULT_EMPTY_SQUARE_IMAGE_FILE_PATH = ".\\images\\emptySquare.png";

	private static final String BLACK_BACKGROUND_IMAGE_PROPERTY_NAME = "blackBackgroundImageFilePath";
	private static final String DEFAULT_BLACK_BACKGROUND_IMAGE_FILE_PATH = ".\\images\\blackBackground.png";

	private static final String WHITE_BACKGROUND_IMAGE_PROPERTY_NAME = "whiteBackgroundImageFilePath";
	private static final String DEFAULT_WHITE_BACKGROUND_IMAGE_FILE_PATH = ".\\images\\whiteBackground.png";

	private static final String DESTINATION_BACKGROUND_IMAGE_PROPERTY_NAME = "destinationBackgroundImageFilePath";
	private static final String DEFAULT_DESTINATION_BACKGROUND_IMAGE_FILE_PATH = ".\\images\\destinationBackground.png";

	private static final String SOURCE_BACKGROUND_IMAGE_PROPERTY_NAME = "sourceBackgroundImageFilePath";
	private static final String DEFAULT_SOURCE_BACKGROUND_IMAGE_FILE_PATH = ".\\images\\sourceBackground.png";

	private static final String BLACK_PAWN_IMAGE_PROPERTY_NAME = "blackPawnImageFilePath";
	private static final String DEFAULT_BLACK_PAWN_IMAGE_FILE_PATH = ".\\images\\blackPawn.png";

	private static final String BLACK_KNIGHT_IMAGE_PROPERTY_NAME = "blackKnightImageFilePath";
	private static final String DEFAULT_BLACK_KNIGHT_IMAGE_FILE_PATH = ".\\images\\blackKnight.png";

	private static final String BLACK_BISHOP_IMAGE_PROPERTY_NAME = "blackBishopImageFilePath";
	private static final String DEFAULT_BLACK_BISHOP_IMAGE_FILE_PATH = ".\\images\\blackBishop.png";

	private static final String BLACK_ROOK_IMAGE_PROPERTY_NAME = "blackRookImageFilePath";
	private static final String DEFAULT_BLACK_ROOK_IMAGE_FILE_PATH = ".\\images\\blackRook.png";

	private static final String BLACK_QUEEN_IMAGE_PROPERTY_NAME = "blackQueenImageFilePath";
	private static final String DEFAULT_BLACK_QUEEN_IMAGE_FILE_PATH = ".\\images\\blackQueen.png";

	private static final String BLACK_KING_IMAGE_PROPERTY_NAME = "blackKingImageFilePath";
	private static final String DEFAULT_BLACK_KING_IMAGE_FILE_PATH = ".\\images\\blackKing.png";

	private static final String WHITE_PAWN_IMAGE_PROPERTY_NAME = "whitePawnImageFilePath";
	private static final String DEFAULT_WHITE_PAWN_IMAGE_FILE_PATH = ".\\images\\whitePawn.png";

	private static final String WHITE_KNIGHT_IMAGE_PROPERTY_NAME = "whiteKnightImageFilePath";
	private static final String DEFAULT_WHITE_KNIGHT_IMAGE_FILE_PATH = ".\\images\\whiteKnight.png";

	private static final String WHITE_BISHOP_IMAGE_PROPERTY_NAME = "whiteBishopImageFilePath";
	private static final String DEFAULT_WHITE_BISHOP_IMAGE_FILE_PATH = ".\\images\\whiteBishop.png";

	private static final String WHITE_ROOK_IMAGE_PROPERTY_NAME = "whiteRookImageFilePath";
	private static final String DEFAULT_WHITE_ROOK_IMAGE_FILE_PATH = ".\\images\\whiteRook.png";

	private static final String WHITE_QUEEN_IMAGE_PROPERTY_NAME = "whiteQueenImageFilePath";
	private static final String DEFAULT_WHITE_QUEEN_IMAGE_FILE_PATH = ".\\images\\whiteQueen.png";

	private static final String WHITE_KING_IMAGE_PROPERTY_NAME = "whiteKingImageFilePath";
	private static final String DEFAULT_WHITE_KING_IMAGE_FILE_PATH = ".\\images\\whiteKing.png";

	private static final String PLAY_ONE_MOVE_BUTTON_IMAGE_PROPERTY_NAME = "playOneMoveButtonImageFilePath";
	private static final String DEFAULT_PLAY_ONE_MOVE_BUTTON_IMAGE_FILE_PATH = ".\\images\\playOneMove.png";

	private static final String TAKE_BACK_ONE_MOVE_BUTTON_IMAGE_PROPERTY_NAME = "takeBackOneMoveButtonImageFilePath";
	private static final String DEFAULT_TAKE_BACK_ONE_MOVE_BUTTON_IMAGE_FILE_PATH = ".\\images\\takeBackOneMove.png";

	private static final String START_AUTO_PLAY_BUTTON_IMAGE_PROPERTY_NAME = "startAutoPlayButtonImageFilePath";
	private static final String DEFAULT_START_AUTO_PLAY_BUTTON_IMAGE_FILE_PATH = ".\\images\\startAutoPlay.png";

	private static final String STOP_AUTO_PLAY_BUTTON_IMAGE_PROPERTY_NAME = "stopAutoPlayButtonImageFilePath";
	private static final String DEFAULT_STOP_AUTO_PLAY_BUTTON_IMAGE_FILE_PATH = ".\\images\\stopAutoPlay.png";

	// Load properties from the properties file with the given name, and the default properties
	public ChessProperties()
	{
		try {
			properties = new Properties(); // Handle default properties manually. Want to print a message when using a default value.
			
			FileInputStream fis = new FileInputStream( PROPERTIES_FILE_PATH);
			properties.loadFromXML( fis);
		} catch( IOException e) {
			System.err.println( "Cannot load the configuration file at " + PROPERTIES_FILE_PATH + ". Using default values.");
		}
	}
	
	public ChessProperties( ChessProperties chessProperties)
	{
		properties = new Properties();
		this.set( chessProperties);
	}

	// Set default properties
	static
	{
		defaultProperties = new Properties();
		defaultProperties.setProperty( TURN_TIME_PROPERTY_NAME, String.valueOf( DEFAULT_TURN_TIME));
		defaultProperties.setProperty( FRAME_TITLE_PROPERTY_NAME, DEFAULT_FRAME_TITLE);
		defaultProperties.setProperty( WHITE_WINS_TEXT_PROPERTY_NAME, DEFAULT_WHITE_WINS_TEXT);
		defaultProperties.setProperty( BLACK_WINS_TEXT_PROPERTY_NAME, DEFAULT_BLACK_WINS_TEXT);
		defaultProperties.setProperty( DRAW_TEXT_PROPERTY_NAME, DEFAULT_DRAW_TEXT);
		defaultProperties.setProperty( FILE_A_IMAGE_PROPERTY_NAME, DEFAULT_FILE_A_IMAGE_FILE_PATH);
		defaultProperties.setProperty( FILE_B_IMAGE_PROPERTY_NAME, DEFAULT_FILE_B_IMAGE_FILE_PATH);
		defaultProperties.setProperty( FILE_C_IMAGE_PROPERTY_NAME, DEFAULT_FILE_C_IMAGE_FILE_PATH);
		defaultProperties.setProperty( FILE_D_IMAGE_PROPERTY_NAME, DEFAULT_FILE_D_IMAGE_FILE_PATH);
		defaultProperties.setProperty( FILE_E_IMAGE_PROPERTY_NAME, DEFAULT_FILE_E_IMAGE_FILE_PATH);
		defaultProperties.setProperty( FILE_F_IMAGE_PROPERTY_NAME, DEFAULT_FILE_F_IMAGE_FILE_PATH);
		defaultProperties.setProperty( FILE_G_IMAGE_PROPERTY_NAME, DEFAULT_FILE_G_IMAGE_FILE_PATH);
		defaultProperties.setProperty( FILE_H_IMAGE_PROPERTY_NAME, DEFAULT_FILE_H_IMAGE_FILE_PATH);
		defaultProperties.setProperty( RANK_1_IMAGE_PROPERTY_NAME, DEFAULT_RANK_1_IMAGE_FILE_PATH);
		defaultProperties.setProperty( RANK_2_IMAGE_PROPERTY_NAME, DEFAULT_RANK_2_IMAGE_FILE_PATH);
		defaultProperties.setProperty( RANK_3_IMAGE_PROPERTY_NAME, DEFAULT_RANK_3_IMAGE_FILE_PATH);
		defaultProperties.setProperty( RANK_4_IMAGE_PROPERTY_NAME, DEFAULT_RANK_4_IMAGE_FILE_PATH);
		defaultProperties.setProperty( RANK_5_IMAGE_PROPERTY_NAME, DEFAULT_RANK_5_IMAGE_FILE_PATH);
		defaultProperties.setProperty( RANK_6_IMAGE_PROPERTY_NAME, DEFAULT_RANK_6_IMAGE_FILE_PATH);
		defaultProperties.setProperty( RANK_7_IMAGE_PROPERTY_NAME, DEFAULT_RANK_7_IMAGE_FILE_PATH);
		defaultProperties.setProperty( RANK_8_IMAGE_PROPERTY_NAME, DEFAULT_RANK_8_IMAGE_FILE_PATH);
		defaultProperties.setProperty( EMPTY_SQUARE_IMAGE_PROPERTY_NAME, DEFAULT_EMPTY_SQUARE_IMAGE_FILE_PATH);
		defaultProperties.setProperty( BLACK_BACKGROUND_IMAGE_PROPERTY_NAME, DEFAULT_BLACK_BACKGROUND_IMAGE_FILE_PATH);
		defaultProperties.setProperty( WHITE_BACKGROUND_IMAGE_PROPERTY_NAME, DEFAULT_WHITE_BACKGROUND_IMAGE_FILE_PATH);
		defaultProperties.setProperty( DESTINATION_BACKGROUND_IMAGE_PROPERTY_NAME, DEFAULT_DESTINATION_BACKGROUND_IMAGE_FILE_PATH);
		defaultProperties.setProperty( SOURCE_BACKGROUND_IMAGE_PROPERTY_NAME, DEFAULT_SOURCE_BACKGROUND_IMAGE_FILE_PATH);
		defaultProperties.setProperty( BLACK_PAWN_IMAGE_PROPERTY_NAME, DEFAULT_BLACK_PAWN_IMAGE_FILE_PATH);
		defaultProperties.setProperty( BLACK_KNIGHT_IMAGE_PROPERTY_NAME, DEFAULT_BLACK_KNIGHT_IMAGE_FILE_PATH);
		defaultProperties.setProperty( BLACK_BISHOP_IMAGE_PROPERTY_NAME, DEFAULT_BLACK_BISHOP_IMAGE_FILE_PATH);
		defaultProperties.setProperty( BLACK_ROOK_IMAGE_PROPERTY_NAME, DEFAULT_BLACK_ROOK_IMAGE_FILE_PATH);
		defaultProperties.setProperty( BLACK_QUEEN_IMAGE_PROPERTY_NAME, DEFAULT_BLACK_QUEEN_IMAGE_FILE_PATH);
		defaultProperties.setProperty( BLACK_KING_IMAGE_PROPERTY_NAME, DEFAULT_BLACK_KING_IMAGE_FILE_PATH);
		defaultProperties.setProperty( WHITE_PAWN_IMAGE_PROPERTY_NAME, DEFAULT_WHITE_PAWN_IMAGE_FILE_PATH);
		defaultProperties.setProperty( WHITE_KNIGHT_IMAGE_PROPERTY_NAME, DEFAULT_WHITE_KNIGHT_IMAGE_FILE_PATH);
		defaultProperties.setProperty( WHITE_BISHOP_IMAGE_PROPERTY_NAME, DEFAULT_WHITE_BISHOP_IMAGE_FILE_PATH);
		defaultProperties.setProperty( WHITE_ROOK_IMAGE_PROPERTY_NAME, DEFAULT_WHITE_ROOK_IMAGE_FILE_PATH);
		defaultProperties.setProperty( WHITE_QUEEN_IMAGE_PROPERTY_NAME, DEFAULT_WHITE_QUEEN_IMAGE_FILE_PATH);
		defaultProperties.setProperty( WHITE_KING_IMAGE_PROPERTY_NAME, DEFAULT_WHITE_KING_IMAGE_FILE_PATH);
		defaultProperties.setProperty( PLAY_ONE_MOVE_BUTTON_IMAGE_PROPERTY_NAME, DEFAULT_PLAY_ONE_MOVE_BUTTON_IMAGE_FILE_PATH);
		defaultProperties.setProperty( TAKE_BACK_ONE_MOVE_BUTTON_IMAGE_PROPERTY_NAME, DEFAULT_TAKE_BACK_ONE_MOVE_BUTTON_IMAGE_FILE_PATH);
		defaultProperties.setProperty( START_AUTO_PLAY_BUTTON_IMAGE_PROPERTY_NAME, DEFAULT_START_AUTO_PLAY_BUTTON_IMAGE_FILE_PATH);
		defaultProperties.setProperty( STOP_AUTO_PLAY_BUTTON_IMAGE_PROPERTY_NAME, DEFAULT_STOP_AUTO_PLAY_BUTTON_IMAGE_FILE_PATH);
	}
	
	// Copy properties of the given ChessProperties to properties of this
	public void set( ChessProperties chessProperties)
	{
		properties.setProperty( TURN_TIME_PROPERTY_NAME, String.valueOf( chessProperties.getTurnTime()));
		properties.setProperty( FRAME_TITLE_PROPERTY_NAME, chessProperties.getFrameTitle());
		properties.setProperty( WHITE_WINS_TEXT_PROPERTY_NAME, chessProperties.getWhiteWinsText());
		properties.setProperty( BLACK_WINS_TEXT_PROPERTY_NAME, chessProperties.getBlackWinsText());
		properties.setProperty( DRAW_TEXT_PROPERTY_NAME, chessProperties.getDrawText());
		properties.setProperty( FILE_A_IMAGE_PROPERTY_NAME, chessProperties.getFileAImageFilePath());
		properties.setProperty( FILE_B_IMAGE_PROPERTY_NAME, chessProperties.getFileBImageFilePath());
		properties.setProperty( FILE_C_IMAGE_PROPERTY_NAME, chessProperties.getFileCImageFilePath());
		properties.setProperty( FILE_D_IMAGE_PROPERTY_NAME, chessProperties.getFileDImageFilePath());
		properties.setProperty( FILE_E_IMAGE_PROPERTY_NAME, chessProperties.getFileEImageFilePath());
		properties.setProperty( FILE_F_IMAGE_PROPERTY_NAME, chessProperties.getFileFImageFilePath());
		properties.setProperty( FILE_G_IMAGE_PROPERTY_NAME, chessProperties.getFileGImageFilePath());
		properties.setProperty( FILE_H_IMAGE_PROPERTY_NAME, chessProperties.getFileHImageFilePath());
		properties.setProperty( RANK_1_IMAGE_PROPERTY_NAME, chessProperties.getRank1ImageFilePath());
		properties.setProperty( RANK_2_IMAGE_PROPERTY_NAME, chessProperties.getRank2ImageFilePath());
		properties.setProperty( RANK_3_IMAGE_PROPERTY_NAME, chessProperties.getRank3ImageFilePath());
		properties.setProperty( RANK_4_IMAGE_PROPERTY_NAME, chessProperties.getRank4ImageFilePath());
		properties.setProperty( RANK_5_IMAGE_PROPERTY_NAME, chessProperties.getRank5ImageFilePath());
		properties.setProperty( RANK_6_IMAGE_PROPERTY_NAME, chessProperties.getRank6ImageFilePath());
		properties.setProperty( RANK_7_IMAGE_PROPERTY_NAME, chessProperties.getRank7ImageFilePath());
		properties.setProperty( RANK_8_IMAGE_PROPERTY_NAME, chessProperties.getRank8ImageFilePath());
		properties.setProperty( EMPTY_SQUARE_IMAGE_PROPERTY_NAME, chessProperties.getEmptySquareImageFilePath());
		properties.setProperty( BLACK_BACKGROUND_IMAGE_PROPERTY_NAME, chessProperties.getBlackBackgroundImageFilePath());
		properties.setProperty( WHITE_BACKGROUND_IMAGE_PROPERTY_NAME, chessProperties.getWhiteBackgroundImageFilePath());
		properties.setProperty( DESTINATION_BACKGROUND_IMAGE_PROPERTY_NAME, chessProperties.getDestinationBackgroundImageFilePath());
		properties.setProperty( SOURCE_BACKGROUND_IMAGE_PROPERTY_NAME, chessProperties.getSourceBackgroundImageFilePath());
		properties.setProperty( BLACK_PAWN_IMAGE_PROPERTY_NAME, chessProperties.getBlackPawnImageFilePath());
		properties.setProperty( BLACK_KNIGHT_IMAGE_PROPERTY_NAME, chessProperties.getBlackKnightImageFilePath());
		properties.setProperty( BLACK_BISHOP_IMAGE_PROPERTY_NAME, chessProperties.getBlackBishopImageFilePath());
		properties.setProperty( BLACK_ROOK_IMAGE_PROPERTY_NAME, chessProperties.getBlackRookImageFilePath());
		properties.setProperty( BLACK_QUEEN_IMAGE_PROPERTY_NAME, chessProperties.getBlackQueenImageFilePath());
		properties.setProperty( BLACK_KING_IMAGE_PROPERTY_NAME, chessProperties.getBlackKingImageFilePath());
		properties.setProperty( WHITE_PAWN_IMAGE_PROPERTY_NAME, chessProperties.getWhitePawnImageFilePath());
		properties.setProperty( WHITE_KNIGHT_IMAGE_PROPERTY_NAME, chessProperties.getWhiteKnightImageFilePath());
		properties.setProperty( WHITE_BISHOP_IMAGE_PROPERTY_NAME, chessProperties.getWhiteBishopImageFilePath());
		properties.setProperty( WHITE_ROOK_IMAGE_PROPERTY_NAME, chessProperties.getWhiteRookImageFilePath());
		properties.setProperty( WHITE_QUEEN_IMAGE_PROPERTY_NAME, chessProperties.getWhiteQueenImageFilePath());
		properties.setProperty( WHITE_KING_IMAGE_PROPERTY_NAME, chessProperties.getWhiteKingImageFilePath());
		properties.setProperty( PLAY_ONE_MOVE_BUTTON_IMAGE_PROPERTY_NAME, chessProperties.getPlayOneMoveButtonImageFilePath());
		properties.setProperty( TAKE_BACK_ONE_MOVE_BUTTON_IMAGE_PROPERTY_NAME, chessProperties.getTakeBackOneMoveButtonImageFilePath());
		properties.setProperty( START_AUTO_PLAY_BUTTON_IMAGE_PROPERTY_NAME, chessProperties.getStartAutoPlayButtonImageFilePath());
		properties.setProperty( STOP_AUTO_PLAY_BUTTON_IMAGE_PROPERTY_NAME, chessProperties.getStopAutoPlayButtonImageFilePath());
	}
	
	// Save properties to the properties file with the given name. If the file doesn't exist, or the write operation is unsuccessful, throw an IO exception.
	public void save() throws IOException
	{
		try {
			if( !new File( PROPERTIES_FILE_PATH).exists())
				throw new IOException( "Cannot save the configuration file to " + PROPERTIES_FILE_PATH);
			
			FileOutputStream fos = new FileOutputStream( PROPERTIES_FILE_PATH);
			properties.storeToXML( fos, null, "ISO-8859-1");
		} catch( IOException e) {
			throw new IOException( "Cannot save the configuration file to " + PROPERTIES_FILE_PATH);
		}
	}
	
	public int getTurnTime()
	{
		int turnTime;
		String strTurnTime = properties.getProperty( TURN_TIME_PROPERTY_NAME);
		
		if( strTurnTime != null) // Found the property in the given file
		{
			try {
				turnTime = Integer.parseInt( strTurnTime); // The property is not formatted properly
			} catch( NumberFormatException e) {
				System.err.println( "Cannot read " + TURN_TIME_PROPERTY_NAME + " from " + PROPERTIES_FILE_PATH +  
									" . Will use the default turn time of " + DEFAULT_TURN_TIME + " milliseconds.");
				turnTime = Integer.parseInt( defaultProperties.getProperty( TURN_TIME_PROPERTY_NAME));
			}
		}
		else // Couldn't find the property in the given file
		{
			System.err.println( "Cannot read turn time from " + PROPERTIES_FILE_PATH + " . Will use the default turn time of " + 
					DEFAULT_TURN_TIME + " milliseconds.");
			turnTime = Integer.parseInt( defaultProperties.getProperty( TURN_TIME_PROPERTY_NAME));
		}
		
		return Math.max( turnTime, MINIMUM_TURN_TIME); // Turn time cannot be less than 10 ms
	}
	
	public int getDefaultTurnTime()
	{
		return Integer.parseInt( defaultProperties.getProperty( TURN_TIME_PROPERTY_NAME));
	}
	
	public void setTurnTime( int turnTime)
	{
		properties.setProperty( TURN_TIME_PROPERTY_NAME, String.valueOf( turnTime));
	}
	
	public String getFrameTitle()
	{
		String frameTitle = properties.getProperty( FRAME_TITLE_PROPERTY_NAME);
		
		if( frameTitle == null) // Couldn't find the property in the given file
		{
			frameTitle = defaultProperties.getProperty( FRAME_TITLE_PROPERTY_NAME);
			System.err.println( "Cannot read " + FRAME_TITLE_PROPERTY_NAME + " from " + PROPERTIES_FILE_PATH + 
								" . Will use the default value " + frameTitle + " .");
		}
		
		return frameTitle;
	}
	
	public String getDefaultFrameTitle()
	{
		return defaultProperties.getProperty( FRAME_TITLE_PROPERTY_NAME);
	}
	
	public void setFrameTitle( String frameTitle)
	{
		properties.setProperty( FRAME_TITLE_PROPERTY_NAME, frameTitle);
	}
	
	public String getWhiteWinsText()
	{
		String whiteWinsText = properties.getProperty( WHITE_WINS_TEXT_PROPERTY_NAME);
		
		if( whiteWinsText == null) // Couldn't find the property in the given file
		{
			whiteWinsText = defaultProperties.getProperty( WHITE_WINS_TEXT_PROPERTY_NAME);
			System.err.println( "Cannot read " + WHITE_WINS_TEXT_PROPERTY_NAME + " from " + PROPERTIES_FILE_PATH + 
								" . Will use the default value " + whiteWinsText + " .");
		}
		
		return whiteWinsText;
	}
	
	public String getDefaultWhiteWinsText()
	{
		return defaultProperties.getProperty( WHITE_WINS_TEXT_PROPERTY_NAME);
	}
	
	public void setWhiteWinsText( String whiteWinsText)
	{
		properties.setProperty( WHITE_WINS_TEXT_PROPERTY_NAME, whiteWinsText);
	}
	
	public String getBlackWinsText()
	{
		String blackWinsText = properties.getProperty( BLACK_WINS_TEXT_PROPERTY_NAME);
		
		if( blackWinsText == null) // Couldn't find the property in the given file
		{
			blackWinsText = defaultProperties.getProperty( BLACK_WINS_TEXT_PROPERTY_NAME);
			System.err.println( "Cannot read " + BLACK_WINS_TEXT_PROPERTY_NAME + " from " + PROPERTIES_FILE_PATH + 
								" . Will use the default value " + blackWinsText + " .");
		}
		
		return blackWinsText;
	}
	
	public String getDefaultBlackWinsText()
	{
		return defaultProperties.getProperty( BLACK_WINS_TEXT_PROPERTY_NAME);
	}
	
	public void setBlackWinsText( String blackWinsText)
	{
		properties.setProperty( BLACK_WINS_TEXT_PROPERTY_NAME, blackWinsText);
	}
	
	public String getDrawText()
	{
		String drawText = properties.getProperty( DRAW_TEXT_PROPERTY_NAME);
		
		if( drawText == null) // Couldn't find the property in the given file
		{
			drawText = defaultProperties.getProperty( DRAW_TEXT_PROPERTY_NAME);
			System.err.println( "Cannot read " + DRAW_TEXT_PROPERTY_NAME + " from " + PROPERTIES_FILE_PATH + 
								" . Will use the default value " + drawText + " .");
		}
		
		return drawText;
	}
	
	public String getDefaultDrawText()
	{
		return defaultProperties.getProperty( DRAW_TEXT_PROPERTY_NAME);
	}
	
	public void setDrawText( String drawText)
	{
		properties.setProperty( DRAW_TEXT_PROPERTY_NAME, drawText);
	}
	
	public String getFileAImageFilePath()
	{
		return getFilePath( FILE_A_IMAGE_PROPERTY_NAME);
	}
	
	public String getDefaultFileAImageFilePath()
	{
		return defaultProperties.getProperty( FILE_A_IMAGE_PROPERTY_NAME);
	}
	
	public void setFileAImageFilePath( String fileAImageFilePath)
	{
		properties.setProperty( FILE_A_IMAGE_PROPERTY_NAME, fileAImageFilePath);
	}
	
	public String getFileBImageFilePath()
	{
		return getFilePath( FILE_B_IMAGE_PROPERTY_NAME);
	}
	
	public String getDefaultFileBImageFilePath()
	{
		return defaultProperties.getProperty( FILE_B_IMAGE_PROPERTY_NAME);
	}
	
	public void setFileBImageFilePath( String fileBImageFilePath)
	{
		properties.setProperty( FILE_B_IMAGE_PROPERTY_NAME, fileBImageFilePath);
	}
	
	public String getFileCImageFilePath()
	{
		return getFilePath( FILE_C_IMAGE_PROPERTY_NAME);
	}
	
	public String getDefaultFileCImageFilePath()
	{
		return defaultProperties.getProperty( FILE_C_IMAGE_PROPERTY_NAME);
	}
	
	public void setFileCImageFilePath( String fileCImageFilePath)
	{
		properties.setProperty( FILE_C_IMAGE_PROPERTY_NAME, fileCImageFilePath);
	}
	
	public String getFileDImageFilePath()
	{
		return getFilePath( FILE_D_IMAGE_PROPERTY_NAME);
	}
	
	public String getDefaultFileDImageFilePath()
	{
		return defaultProperties.getProperty( FILE_D_IMAGE_PROPERTY_NAME);
	}
	
	public void setFileDImageFilePath( String fileDImageFilePath)
	{
		properties.setProperty( FILE_D_IMAGE_PROPERTY_NAME, fileDImageFilePath);
	}
	
	public String getFileEImageFilePath()
	{
		return getFilePath( FILE_E_IMAGE_PROPERTY_NAME);
	}
	
	public String getDefaultFileEImageFilePath()
	{
		return defaultProperties.getProperty( FILE_E_IMAGE_PROPERTY_NAME);
	}
	
	public void setFileEImageFilePath( String fileEImageFilePath)
	{
		properties.setProperty( FILE_E_IMAGE_PROPERTY_NAME, fileEImageFilePath);
	}
	
	public String getFileFImageFilePath()
	{
		return getFilePath( FILE_F_IMAGE_PROPERTY_NAME);
	}
	
	public String getDefaultFileFImageFilePath()
	{
		return defaultProperties.getProperty( FILE_F_IMAGE_PROPERTY_NAME);
	}
	
	public void setFileFImageFilePath( String fileFImageFilePath)
	{
		properties.setProperty( FILE_F_IMAGE_PROPERTY_NAME, fileFImageFilePath);
	}
	
	public String getFileGImageFilePath()
	{
		return getFilePath( FILE_G_IMAGE_PROPERTY_NAME);
	}
	
	public String getDefaultFileGImageFilePath()
	{
		return defaultProperties.getProperty( FILE_G_IMAGE_PROPERTY_NAME);
	}
	
	public void setFileGImageFilePath( String fileGImageFilePath)
	{
		properties.setProperty( FILE_G_IMAGE_PROPERTY_NAME, fileGImageFilePath);
	}
	
	public String getFileHImageFilePath()
	{
		return getFilePath( FILE_H_IMAGE_PROPERTY_NAME);
	}
	
	public String getDefaultFileHImageFilePath()
	{
		return defaultProperties.getProperty( FILE_H_IMAGE_PROPERTY_NAME);
	}
	
	public void setFileHImageFilePath( String fileHImageFilePath)
	{
		properties.setProperty( FILE_H_IMAGE_PROPERTY_NAME, fileHImageFilePath);
	}
	
	public String getRank1ImageFilePath()
	{
		return getFilePath( RANK_1_IMAGE_PROPERTY_NAME);
	}
	
	public String getDefaultRank1ImageFilePath()
	{
		return defaultProperties.getProperty( RANK_1_IMAGE_PROPERTY_NAME);
	}
	
	public void setRank1ImageFilePath( String rank1ImageFilePath)
	{
		properties.setProperty( RANK_1_IMAGE_PROPERTY_NAME, rank1ImageFilePath);
	}
	
	public String getRank2ImageFilePath()
	{
		return getFilePath( RANK_2_IMAGE_PROPERTY_NAME);
	}
	
	public String getDefaultRank2ImageFilePath()
	{
		return defaultProperties.getProperty( RANK_2_IMAGE_PROPERTY_NAME);
	}
	
	public void setRank2ImageFilePath( String rank2ImageFilePath)
	{
		properties.setProperty( RANK_2_IMAGE_PROPERTY_NAME, rank2ImageFilePath);
	}
	
	public String getRank3ImageFilePath()
	{
		return getFilePath( RANK_3_IMAGE_PROPERTY_NAME);
	}
	
	public String getDefaultRank3ImageFilePath()
	{
		return defaultProperties.getProperty( RANK_3_IMAGE_PROPERTY_NAME);
	}
	
	public void setRank3ImageFilePath( String rank3ImageFilePath)
	{
		properties.setProperty( RANK_3_IMAGE_PROPERTY_NAME, rank3ImageFilePath);
	}
	
	public String getRank4ImageFilePath()
	{
		return getFilePath( RANK_4_IMAGE_PROPERTY_NAME);
	}
	
	public String getDefaultRank4ImageFilePath()
	{
		return defaultProperties.getProperty( RANK_4_IMAGE_PROPERTY_NAME);
	}
	
	public void setRank4ImageFilePath( String rank4ImageFilePath)
	{
		properties.setProperty( RANK_4_IMAGE_PROPERTY_NAME, rank4ImageFilePath);
	}
	
	public String getRank5ImageFilePath()
	{
		return getFilePath( RANK_5_IMAGE_PROPERTY_NAME);
	}
	
	public String getDefaultRank5ImageFilePath()
	{
		return defaultProperties.getProperty( RANK_5_IMAGE_PROPERTY_NAME);
	}
	
	public void setRank5ImageFilePath( String rank5ImageFilePath)
	{
		properties.setProperty( RANK_5_IMAGE_PROPERTY_NAME, rank5ImageFilePath);
	}
	
	public String getRank6ImageFilePath()
	{
		return getFilePath( RANK_6_IMAGE_PROPERTY_NAME);
	}
	
	public String getDefaultRank6ImageFilePath()
	{
		return defaultProperties.getProperty( RANK_6_IMAGE_PROPERTY_NAME);
	}
	
	public void setRank6ImageFilePath( String rank6ImageFilePath)
	{
		properties.setProperty( RANK_6_IMAGE_PROPERTY_NAME, rank6ImageFilePath);
	}
	
	public String getRank7ImageFilePath()
	{
		return getFilePath( RANK_7_IMAGE_PROPERTY_NAME);
	}
	
	public String getDefaultRank7ImageFilePath()
	{
		return defaultProperties.getProperty( RANK_7_IMAGE_PROPERTY_NAME);
	}
	
	public void setRank7ImageFilePath( String rank7ImageFilePath)
	{
		properties.setProperty( RANK_7_IMAGE_PROPERTY_NAME, rank7ImageFilePath);
	}
	
	public String getRank8ImageFilePath()
	{
		return getFilePath( RANK_8_IMAGE_PROPERTY_NAME);
	}
	
	public String getDefaultRank8ImageFilePath()
	{
		return defaultProperties.getProperty( RANK_8_IMAGE_PROPERTY_NAME);
	}
	
	public void setRank8ImageFilePath( String rank8ImageFilePath)
	{
		properties.setProperty( RANK_8_IMAGE_PROPERTY_NAME, rank8ImageFilePath);
	}
	
	public String getEmptySquareImageFilePath()
	{
		return getFilePath( EMPTY_SQUARE_IMAGE_PROPERTY_NAME);
	}
	
	public String getDefaultEmptySquareImageFilePath()
	{
		return defaultProperties.getProperty( EMPTY_SQUARE_IMAGE_PROPERTY_NAME);
	}
	
	public void setEmptySquareImageFilePath( String emptySquareImageFilePath)
	{
		properties.setProperty( EMPTY_SQUARE_IMAGE_PROPERTY_NAME, emptySquareImageFilePath);
	}
	
	public String getBlackBackgroundImageFilePath()
	{
		return getFilePath( BLACK_BACKGROUND_IMAGE_PROPERTY_NAME);
	}
	
	public String getDefaultBlackBackgroundImageFilePath()
	{
		return defaultProperties.getProperty( BLACK_BACKGROUND_IMAGE_PROPERTY_NAME);
	}
	
	public void setBlackBackgroundImageFilePath( String blackBackgroundImageFilePath)
	{
		properties.setProperty( BLACK_BACKGROUND_IMAGE_PROPERTY_NAME, blackBackgroundImageFilePath);
	}
	
	public String getWhiteBackgroundImageFilePath()
	{
		return getFilePath( WHITE_BACKGROUND_IMAGE_PROPERTY_NAME);
	}
	
	public String getDefaultWhiteBackgroundImageFilePath()
	{
		return defaultProperties.getProperty( WHITE_BACKGROUND_IMAGE_PROPERTY_NAME);
	}
	
	public void setWhiteBackgroundImageFilePath( String whiteBackgroundImageFilePath)
	{
		properties.setProperty( WHITE_BACKGROUND_IMAGE_PROPERTY_NAME, whiteBackgroundImageFilePath);
	}
	
	public String getDestinationBackgroundImageFilePath()
	{
		return getFilePath( DESTINATION_BACKGROUND_IMAGE_PROPERTY_NAME);
	}
	
	public String getDefaultDestinationBackgroundImageFilePath()
	{
		return defaultProperties.getProperty( DESTINATION_BACKGROUND_IMAGE_PROPERTY_NAME);
	}
	
	public void setDestinationBackgroundImageFilePath( String destinationBackgroundImageFilePath)
	{
		properties.setProperty( DESTINATION_BACKGROUND_IMAGE_PROPERTY_NAME, destinationBackgroundImageFilePath);
	}
	
	public String getSourceBackgroundImageFilePath()
	{
		return getFilePath( SOURCE_BACKGROUND_IMAGE_PROPERTY_NAME);
	}
	
	public String getDefaultSourceBackgroundImageFilePath()
	{
		return defaultProperties.getProperty( SOURCE_BACKGROUND_IMAGE_PROPERTY_NAME);
	}
	
	public void setSourceBackgroundImageFilePath( String sourceBackgroundImageFilePath)
	{
		properties.setProperty( SOURCE_BACKGROUND_IMAGE_PROPERTY_NAME, sourceBackgroundImageFilePath);
	}
	
	public String getBlackPawnImageFilePath()
	{
		return getFilePath( BLACK_PAWN_IMAGE_PROPERTY_NAME);
	}
	
	public String getDefaultBlackPawnImageFilePath()
	{
		return defaultProperties.getProperty( BLACK_PAWN_IMAGE_PROPERTY_NAME);
	}
	
	public void setBlackPawnImageFilePath( String blackPawnImageFilePath)
	{
		properties.setProperty( BLACK_PAWN_IMAGE_PROPERTY_NAME, blackPawnImageFilePath);
	}
	
	public String getBlackKnightImageFilePath()
	{
		return getFilePath( BLACK_KNIGHT_IMAGE_PROPERTY_NAME);
	}
	
	public String getDefaultBlackKnightImageFilePath()
	{
		return defaultProperties.getProperty( BLACK_KNIGHT_IMAGE_PROPERTY_NAME);
	}
	
	public void setBlackKnightImageFilePath( String blackKnightImageFilePath)
	{
		properties.setProperty( BLACK_KNIGHT_IMAGE_PROPERTY_NAME, blackKnightImageFilePath);
	}
	
	public String getBlackBishopImageFilePath()
	{
		return getFilePath( BLACK_BISHOP_IMAGE_PROPERTY_NAME);
	}
	
	public String getDefaultBlackBishopImageFilePath()
	{
		return defaultProperties.getProperty( BLACK_BISHOP_IMAGE_PROPERTY_NAME);
	}
	
	public void setBlackBishopImageFilePath( String blackBishopImageFilePath)
	{
		properties.setProperty( BLACK_BISHOP_IMAGE_PROPERTY_NAME, blackBishopImageFilePath);
	}
	
	public String getBlackRookImageFilePath()
	{
		return getFilePath( BLACK_ROOK_IMAGE_PROPERTY_NAME);
	}
	
	public String getDefaultBlackRookImageFilePath()
	{
		return defaultProperties.getProperty( BLACK_ROOK_IMAGE_PROPERTY_NAME);
	}
	
	public void setBlackRookImageFilePath( String blackRookImageFilePath)
	{
		properties.setProperty( BLACK_ROOK_IMAGE_PROPERTY_NAME, blackRookImageFilePath);
	}
	
	public String getBlackQueenImageFilePath()
	{
		return getFilePath( BLACK_QUEEN_IMAGE_PROPERTY_NAME);
	}
	
	public String getDefaultBlackQueenImageFilePath()
	{
		return defaultProperties.getProperty( BLACK_QUEEN_IMAGE_PROPERTY_NAME);
	}
	
	public void setBlackQueenImageFilePath( String blackQueenImageFilePath)
	{
		properties.setProperty( BLACK_QUEEN_IMAGE_PROPERTY_NAME, blackQueenImageFilePath);
	}
	
	public String getBlackKingImageFilePath()
	{
		return getFilePath( BLACK_KING_IMAGE_PROPERTY_NAME);
	}
	
	public String getDefaultBlackKingImageFilePath()
	{
		return defaultProperties.getProperty( BLACK_KING_IMAGE_PROPERTY_NAME);
	}
	
	public void setBlackKingImageFilePath( String blackKingImageFilePath)
	{
		properties.setProperty( BLACK_KING_IMAGE_PROPERTY_NAME, blackKingImageFilePath);
	}
	
	public String getWhitePawnImageFilePath()
	{
		return getFilePath( WHITE_PAWN_IMAGE_PROPERTY_NAME);
	}
	
	public String getDefaultWhitePawnImageFilePath()
	{
		return defaultProperties.getProperty( WHITE_PAWN_IMAGE_PROPERTY_NAME);
	}
	
	public void setWhitePawnImageFilePath( String whitePawnImageFilePath)
	{
		properties.setProperty( WHITE_PAWN_IMAGE_PROPERTY_NAME, whitePawnImageFilePath);
	}
	
	public String getWhiteKnightImageFilePath()
	{
		return getFilePath( WHITE_KNIGHT_IMAGE_PROPERTY_NAME);
	}
	
	public String getDefaultWhiteKnightImageFilePath()
	{
		return defaultProperties.getProperty( WHITE_KNIGHT_IMAGE_PROPERTY_NAME);
	}
	
	public void setWhiteKnightImageFilePath( String whiteKnightImageFilePath)
	{
		properties.setProperty( WHITE_KNIGHT_IMAGE_PROPERTY_NAME, whiteKnightImageFilePath);
	}
	
	public String getWhiteBishopImageFilePath()
	{
		return getFilePath( WHITE_BISHOP_IMAGE_PROPERTY_NAME);
	}
	
	public String getDefaultWhiteBishopImageFilePath()
	{
		return defaultProperties.getProperty( WHITE_BISHOP_IMAGE_PROPERTY_NAME);
	}
	
	public void setWhiteBishopImageFilePath( String whiteBishopImageFilePath)
	{
		properties.setProperty( WHITE_BISHOP_IMAGE_PROPERTY_NAME, whiteBishopImageFilePath);
	}
	
	public String getWhiteRookImageFilePath()
	{
		return getFilePath( WHITE_ROOK_IMAGE_PROPERTY_NAME);
	}
	
	public String getDefaultWhiteRookImageFilePath()
	{
		return defaultProperties.getProperty( WHITE_ROOK_IMAGE_PROPERTY_NAME);
	}
	
	public void setWhiteRookImageFilePath( String whiteRookImageFilePath)
	{
		properties.setProperty(WHITE_ROOK_IMAGE_PROPERTY_NAME , whiteRookImageFilePath);
	}
	
	public String getWhiteQueenImageFilePath()
	{
		return getFilePath( WHITE_QUEEN_IMAGE_PROPERTY_NAME);
	}
	
	public String getDefaultWhiteQueenImageFilePath()
	{
		return defaultProperties.getProperty( WHITE_QUEEN_IMAGE_PROPERTY_NAME);
	}
	
	public void setWhiteQueenImageFilePath( String whiteQueenImageFilePath)
	{
		properties.setProperty( WHITE_QUEEN_IMAGE_PROPERTY_NAME, whiteQueenImageFilePath);
	}
	
	public String getWhiteKingImageFilePath()
	{
		return getFilePath( WHITE_KING_IMAGE_PROPERTY_NAME);
	}
	
	public String getDefaultWhiteKingImageFilePath()
	{
		return defaultProperties.getProperty( WHITE_KING_IMAGE_PROPERTY_NAME);
	}
	
	public void setWhiteKingImageFilePath( String whiteKingImageFilePath)
	{
		properties.setProperty( WHITE_KING_IMAGE_PROPERTY_NAME, whiteKingImageFilePath);
	}
	
	public String getPlayOneMoveButtonImageFilePath()
	{
		return getFilePath( PLAY_ONE_MOVE_BUTTON_IMAGE_PROPERTY_NAME);
	}
	
	public String getDefaultPlayOneMoveButtonImageFilePath()
	{
		return defaultProperties.getProperty( PLAY_ONE_MOVE_BUTTON_IMAGE_PROPERTY_NAME);
	}
	
	public void setPlayOneMoveButtonImageFilePath( String playOneMoveButtonImageFilePath)
	{
		properties.setProperty( PLAY_ONE_MOVE_BUTTON_IMAGE_PROPERTY_NAME, playOneMoveButtonImageFilePath);
	}
	
	public String getTakeBackOneMoveButtonImageFilePath()
	{
		return getFilePath( TAKE_BACK_ONE_MOVE_BUTTON_IMAGE_PROPERTY_NAME);
	}
	
	public String getDefaultTakeBackOneMoveButtonImageFilePath()
	{
		return defaultProperties.getProperty( TAKE_BACK_ONE_MOVE_BUTTON_IMAGE_PROPERTY_NAME);
	}
	
	public void setTakeBackOneMoveButtonImageFilePath( String takeBackOneMoveButtonImageFilePath)
	{
		properties.setProperty( TAKE_BACK_ONE_MOVE_BUTTON_IMAGE_PROPERTY_NAME, takeBackOneMoveButtonImageFilePath);
	}
	
	public String getStartAutoPlayButtonImageFilePath()
	{
		return getFilePath( START_AUTO_PLAY_BUTTON_IMAGE_PROPERTY_NAME);
	}
	
	public String getDefaultStartAutoPlayButtonImageFilePath()
	{
		return defaultProperties.getProperty( START_AUTO_PLAY_BUTTON_IMAGE_PROPERTY_NAME);
	}
	
	public void setStartAutoPlayButtonImageFilePath( String startAutoPlayButtonImageFilePath)
	{
		properties.setProperty( START_AUTO_PLAY_BUTTON_IMAGE_PROPERTY_NAME, startAutoPlayButtonImageFilePath);
	}
	
	public String getStopAutoPlayButtonImageFilePath()
	{
		return getFilePath( STOP_AUTO_PLAY_BUTTON_IMAGE_PROPERTY_NAME);
	}
	
	public String getDefaultStopAutoPlayButtonImageFilePath()
	{
		return defaultProperties.getProperty( STOP_AUTO_PLAY_BUTTON_IMAGE_PROPERTY_NAME);
	}
	
	public void setStopAutoPlayButtonImageFilePath( String stopAutoPlayButtonImageFilePath)
	{
		properties.setProperty( STOP_AUTO_PLAY_BUTTON_IMAGE_PROPERTY_NAME, stopAutoPlayButtonImageFilePath);
	}
	
	// Get file path from value of the property with the given name
	private String getFilePath( String propertyName)
	{
		String FilePath = properties.getProperty( propertyName);
		
		if( FilePath == null) // Couldn't find the property in the given file
		{
			FilePath = defaultProperties.getProperty( propertyName);
			System.err.println( "Cannot read " + propertyName + " from " + PROPERTIES_FILE_PATH + 
								" . Will use the default file path " + FilePath + " .");
		}
		
		return FilePath;
	}
}
