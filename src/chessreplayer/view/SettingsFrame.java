/**
 * Part of the view of the MVC pattern. Handles the settings screen.
 */

package chessreplayer.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import chessreplayer.controller.ChessProperties;

public class SettingsFrame extends JFrame
{
	private static final long serialVersionUID = 9102927458814851324L;
	private static int TEXT_FIELD_WIDTH = 25;

	private JPanel mainPanel;
	private JPanel buttonPanel;
	private JScrollPane mainScrollPane;
	
	private ChessProperties properties;
	private Map<JButton,JTextField> buttonFieldMap;
	
	private JButton loadDefaultSettingsButton;
	private JButton saveButton;
	private JButton cancelButton;

	private static final String newLine = System.getProperty( "line.separator");
	
	// Labels of text fields
	private static JLabel turnTimeLabel;
	private static JLabel frameTitleLabel;
	private static JLabel whiteWinsTextLabel;
	private static JLabel blackWinsTextLabel;
	private static JLabel drawTextLabel;
	private static JLabel fileAImageFilePathLabel;
	private static JLabel fileBImageFilePathLabel;
	private static JLabel fileCImageFilePathLabel;
	private static JLabel fileDImageFilePathLabel;
	private static JLabel fileEImageFilePathLabel;
	private static JLabel fileFImageFilePathLabel;
	private static JLabel fileGImageFilePathLabel;
	private static JLabel fileHImageFilePathLabel;
	private static JLabel rank1ImageFilePathLabel;
	private static JLabel rank2ImageFilePathLabel;
	private static JLabel rank3ImageFilePathLabel;
	private static JLabel rank4ImageFilePathLabel;
	private static JLabel rank5ImageFilePathLabel;
	private static JLabel rank6ImageFilePathLabel;
	private static JLabel rank7ImageFilePathLabel;
	private static JLabel rank8ImageFilePathLabel;
	private static JLabel emptySquareImageFilePathLabel;
	private static JLabel blackBackgroundImageFilePathLabel;
	private static JLabel whiteBackgroundImageFilePathLabel;
	private static JLabel destinationBackgroundImageFilePathLabel;
	private static JLabel sourceBackgroundImageFilePathLabel;
	private static JLabel blackPawnImageFilePathLabel;
	private static JLabel blackKnightImageFilePathLabel;
	private static JLabel blackBishopImageFilePathLabel;
	private static JLabel blackRookImageFilePathLabel;
	private static JLabel blackQueenImageFilePathLabel;
	private static JLabel blackKingImageFilePathLabel;
	private static JLabel whitePawnImageFilePathLabel;
	private static JLabel whiteKnightImageFilePathLabel;
	private static JLabel whiteBishopImageFilePathLabel;
	private static JLabel whiteRookImageFilePathLabel;
	private static JLabel whiteQueenImageFilePathLabel;
	private static JLabel whiteKingImageFilePathLabel;
	private static JLabel playOneMoveButtonImageFilePathLabel;
	private static JLabel takeBackOneMoveButtonImageFilePathLabel;
	private static JLabel startAutoPlayButtonImageFilePathLabel;
	private static JLabel stopAutoPlayButtonImageFilePathLabel;
	
	// Text fields containing settings
	private JTextField turnTimeField;
	private JTextField frameTitleField;
	private JTextField whiteWinsTextField;
	private JTextField blackWinsTextField;
	private JTextField drawTextField;
	private JTextField fileAImageFilePathField;
	private JTextField fileBImageFilePathField;
	private JTextField fileCImageFilePathField;
	private JTextField fileDImageFilePathField;
	private JTextField fileEImageFilePathField;
	private JTextField fileFImageFilePathField;
	private JTextField fileGImageFilePathField;
	private JTextField fileHImageFilePathField;
	private JTextField rank1ImageFilePathField;
	private JTextField rank2ImageFilePathField;
	private JTextField rank3ImageFilePathField;
	private JTextField rank4ImageFilePathField;
	private JTextField rank5ImageFilePathField;
	private JTextField rank6ImageFilePathField;
	private JTextField rank7ImageFilePathField;
	private JTextField rank8ImageFilePathField;
	private JTextField emptySquareImageFilePathField;
	private JTextField blackBackgroundImageFilePathField;
	private JTextField whiteBackgroundImageFilePathField;
	private JTextField destinationBackgroundImageFilePathField;
	private JTextField sourceBackgroundImageFilePathField;
	private JTextField blackPawnImageFilePathField;
	private JTextField blackKnightImageFilePathField;
	private JTextField blackBishopImageFilePathField;
	private JTextField blackRookImageFilePathField;
	private JTextField blackQueenImageFilePathField;
	private JTextField blackKingImageFilePathField;
	private JTextField whitePawnImageFilePathField;
	private JTextField whiteKnightImageFilePathField;
	private JTextField whiteBishopImageFilePathField;
	private JTextField whiteRookImageFilePathField;
	private JTextField whiteQueenImageFilePathField;
	private JTextField whiteKingImageFilePathField;
	private JTextField playOneMoveButtonImageFilePathField;
	private JTextField takeBackOneMoveButtonImageFilePathField;
	private JTextField startAutoPlayButtonImageFilePathField;
	private JTextField stopAutoPlayButtonImageFilePathField;
	
	// Buttons for opening file chooser dialogs. They are used for settings pertaining to file paths.
	private JButton fileAImageFilePathButton;
	private JButton fileBImageFilePathButton;
	private JButton fileCImageFilePathButton;
	private JButton fileDImageFilePathButton;
	private JButton fileEImageFilePathButton;
	private JButton fileFImageFilePathButton;
	private JButton fileGImageFilePathButton;
	private JButton fileHImageFilePathButton;
	private JButton rank1ImageFilePathButton;
	private JButton rank2ImageFilePathButton;
	private JButton rank3ImageFilePathButton;
	private JButton rank4ImageFilePathButton;
	private JButton rank5ImageFilePathButton;
	private JButton rank6ImageFilePathButton;
	private JButton rank7ImageFilePathButton;
	private JButton rank8ImageFilePathButton;
	private JButton emptySquareImageFilePathButton;
	private JButton blackBackgroundImageFilePathButton;
	private JButton whiteBackgroundImageFilePathButton;
	private JButton destinationBackgroundImageFilePathButton;
	private JButton sourceBackgroundImageFilePathButton;
	private JButton blackPawnImageFilePathButton;
	private JButton blackKnightImageFilePathButton;
	private JButton blackBishopImageFilePathButton;
	private JButton blackRookImageFilePathButton;
	private JButton blackQueenImageFilePathButton;
	private JButton blackKingImageFilePathButton;
	private JButton whitePawnImageFilePathButton;
	private JButton whiteKnightImageFilePathButton;
	private JButton whiteBishopImageFilePathButton;
	private JButton whiteRookImageFilePathButton;
	private JButton whiteQueenImageFilePathButton;
	private JButton whiteKingImageFilePathButton;
	private JButton playOneMoveButtonImageFilePathButton;
	private JButton takeBackOneMoveButtonImageFilePathButton;
	private JButton startAutoPlayButtonImageFilePathButton;
	private JButton stopAutoPlayButtonImageFilePathButton;
	
	/* Create a settings frame with the given frame title set and default close operation. 
	 * The minimum size of the frame is 1/4 of screen width x 1/4 of screen height; and its initial size is 4/5 of screen width x 4/5 of screen height.
	 * The frame includes a main panel that includes fields containing game settings, and a panel for buttons.
	 */
	public SettingsFrame( String title)
	{
		super( title);
		this.setDefaultCloseOperation ( JFrame.DISPOSE_ON_CLOSE);
		
		Dimension screenDimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize( (int)(screenDimension.getWidth() * 0.8), (int)(screenDimension.getHeight() * 0.8));
		this.setMinimumSize( new Dimension( (int)(screenDimension.getWidth() * 0.25), (int)(screenDimension.getHeight() * 0.25)));
		
		mainPanel = new JPanel();
		mainPanel.setLayout( new GridBagLayout());
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout( new FlowLayout( FlowLayout.RIGHT));
		
		mainScrollPane = new JScrollPane( mainPanel);
		this.getContentPane().add( mainScrollPane);
	}
	
	// Initialize the static labels
	static
	{
		turnTimeLabel = new JLabel( "Turn time (ms): ");
		frameTitleLabel = new JLabel( "Frame title: ");
		whiteWinsTextLabel = new JLabel( "White wins text: ");
		blackWinsTextLabel = new JLabel( "Black wins text: ");
		drawTextLabel = new JLabel( "Draw text: ");
		
		fileAImageFilePathLabel = new JLabel( "File A image file: ");
		fileBImageFilePathLabel = new JLabel( "File B image file: ");
		fileCImageFilePathLabel = new JLabel( "File C image file: ");
		fileDImageFilePathLabel = new JLabel( "File D image file: ");
		fileEImageFilePathLabel = new JLabel( "File E image file: ");
		fileFImageFilePathLabel = new JLabel( "File F image file: ");
		fileGImageFilePathLabel = new JLabel( "File G image file: ");
		fileHImageFilePathLabel = new JLabel( "File H image file: ");
		
		rank1ImageFilePathLabel = new JLabel( "Rank 1 image file: ");
		rank2ImageFilePathLabel = new JLabel( "Rank 2 image file: ");
		rank3ImageFilePathLabel = new JLabel( "Rank 3 image file: ");
		rank4ImageFilePathLabel = new JLabel( "Rank 4 image file: ");
		rank5ImageFilePathLabel = new JLabel( "Rank 5 image file: ");
		rank6ImageFilePathLabel = new JLabel( "Rank 6 image file: ");
		rank7ImageFilePathLabel = new JLabel( "Rank 7 image file: ");
		rank8ImageFilePathLabel = new JLabel( "Rank 8 image file: ");
		
		emptySquareImageFilePathLabel = new JLabel( "Empty square image file: ");
		blackBackgroundImageFilePathLabel = new JLabel( "Black background image file: ");
		whiteBackgroundImageFilePathLabel = new JLabel( "White background image file: ");
		destinationBackgroundImageFilePathLabel = new JLabel( "Destination background image file: ");
		sourceBackgroundImageFilePathLabel = new JLabel( "Source background image file: ");
		
		blackPawnImageFilePathLabel = new JLabel( "Black pawn image file: ");
		blackKnightImageFilePathLabel = new JLabel( "Black knight image file: ");
		blackBishopImageFilePathLabel = new JLabel( "Black bishop image file: ");
		blackRookImageFilePathLabel = new JLabel( "Black rook image file: ");
		blackQueenImageFilePathLabel = new JLabel( "Black queen image file: ");
		blackKingImageFilePathLabel = new JLabel( "Black king image file: ");
		
		whitePawnImageFilePathLabel = new JLabel( "White pawn image file: ");
		whiteKnightImageFilePathLabel = new JLabel( "White knight image file: ");
		whiteBishopImageFilePathLabel = new JLabel( "White bishop image file: ");
		whiteRookImageFilePathLabel = new JLabel( "White rook image file: ");
		whiteQueenImageFilePathLabel = new JLabel( "White queen image file: ");
		whiteKingImageFilePathLabel = new JLabel( "White king image file: ");
		
		playOneMoveButtonImageFilePathLabel = new JLabel( "Play one move button image file: ");
		takeBackOneMoveButtonImageFilePathLabel = new JLabel( "Take back one move button image file: ");
		startAutoPlayButtonImageFilePathLabel = new JLabel( "Start auto play button image file: ");
		stopAutoPlayButtonImageFilePathLabel = new JLabel( "Stop auto play button image file: ");
	}
	
	// Initialize the settings frame by creating the necessary UI elements, setting up listeners and adding the UI elements to their respective containers.
	public void initialize( final ChessProperties properties)
	{
		this.properties = properties;
		
		/* Create fields containing game settings, all of which have the same fixed width. 
		 * Fields are populated with current settings, which are retrieved from the given chess properties object. 
		 */
		turnTimeField = new JTextField( String.valueOf( properties.getTurnTime()), TEXT_FIELD_WIDTH);
		frameTitleField = new JTextField( properties.getFrameTitle(), TEXT_FIELD_WIDTH);
		whiteWinsTextField = new JTextField( properties.getWhiteWinsText(), TEXT_FIELD_WIDTH);
		blackWinsTextField = new JTextField( properties.getBlackWinsText(), TEXT_FIELD_WIDTH);
		drawTextField = new JTextField( properties.getDrawText(), TEXT_FIELD_WIDTH);
		
		fileAImageFilePathField = new JTextField( properties.getFileAImageFilePath(), TEXT_FIELD_WIDTH);
		fileBImageFilePathField = new JTextField( properties.getFileBImageFilePath(), TEXT_FIELD_WIDTH);
		fileCImageFilePathField = new JTextField( properties.getFileCImageFilePath(), TEXT_FIELD_WIDTH);
		fileDImageFilePathField = new JTextField( properties.getFileDImageFilePath(), TEXT_FIELD_WIDTH);
		fileEImageFilePathField = new JTextField( properties.getFileEImageFilePath(), TEXT_FIELD_WIDTH);
		fileFImageFilePathField = new JTextField( properties.getFileFImageFilePath(), TEXT_FIELD_WIDTH);
		fileGImageFilePathField = new JTextField( properties.getFileGImageFilePath(), TEXT_FIELD_WIDTH);
		fileHImageFilePathField = new JTextField( properties.getFileHImageFilePath(), TEXT_FIELD_WIDTH);
		
		rank1ImageFilePathField = new JTextField( properties.getRank1ImageFilePath(), TEXT_FIELD_WIDTH);
		rank2ImageFilePathField = new JTextField( properties.getRank2ImageFilePath(), TEXT_FIELD_WIDTH);
		rank3ImageFilePathField = new JTextField( properties.getRank3ImageFilePath(), TEXT_FIELD_WIDTH);
		rank4ImageFilePathField = new JTextField( properties.getRank4ImageFilePath(), TEXT_FIELD_WIDTH);
		rank5ImageFilePathField = new JTextField( properties.getRank5ImageFilePath(), TEXT_FIELD_WIDTH);
		rank6ImageFilePathField = new JTextField( properties.getRank6ImageFilePath(), TEXT_FIELD_WIDTH);
		rank7ImageFilePathField = new JTextField( properties.getRank7ImageFilePath(), TEXT_FIELD_WIDTH);
		rank8ImageFilePathField = new JTextField( properties.getRank8ImageFilePath(), TEXT_FIELD_WIDTH);
		
		emptySquareImageFilePathField = new JTextField( properties.getEmptySquareImageFilePath(), TEXT_FIELD_WIDTH);
		blackBackgroundImageFilePathField = new JTextField( properties.getBlackBackgroundImageFilePath(), TEXT_FIELD_WIDTH);
		whiteBackgroundImageFilePathField = new JTextField( properties.getWhiteBackgroundImageFilePath(), TEXT_FIELD_WIDTH);
		destinationBackgroundImageFilePathField = new JTextField( properties.getDestinationBackgroundImageFilePath(), TEXT_FIELD_WIDTH);
		sourceBackgroundImageFilePathField = new JTextField( properties.getSourceBackgroundImageFilePath(), TEXT_FIELD_WIDTH);
		
		blackPawnImageFilePathField = new JTextField( properties.getBlackPawnImageFilePath(), TEXT_FIELD_WIDTH);
		blackKnightImageFilePathField = new JTextField( properties.getBlackKnightImageFilePath(), TEXT_FIELD_WIDTH);
		blackBishopImageFilePathField = new JTextField( properties.getBlackBishopImageFilePath(), TEXT_FIELD_WIDTH);
		blackRookImageFilePathField = new JTextField( properties.getBlackRookImageFilePath(), TEXT_FIELD_WIDTH);
		blackQueenImageFilePathField = new JTextField( properties.getBlackQueenImageFilePath(), TEXT_FIELD_WIDTH);
		blackKingImageFilePathField = new JTextField( properties.getBlackKingImageFilePath(), TEXT_FIELD_WIDTH);
		
		whitePawnImageFilePathField = new JTextField( properties.getWhitePawnImageFilePath(), TEXT_FIELD_WIDTH);
		whiteKnightImageFilePathField = new JTextField( properties.getWhiteKnightImageFilePath(), TEXT_FIELD_WIDTH);
		whiteBishopImageFilePathField = new JTextField( properties.getWhiteBishopImageFilePath(), TEXT_FIELD_WIDTH);
		whiteRookImageFilePathField = new JTextField( properties.getWhiteRookImageFilePath(), TEXT_FIELD_WIDTH);
		whiteQueenImageFilePathField = new JTextField( properties.getWhiteQueenImageFilePath(), TEXT_FIELD_WIDTH);
		whiteKingImageFilePathField = new JTextField( properties.getWhiteKingImageFilePath(), TEXT_FIELD_WIDTH);
		
		playOneMoveButtonImageFilePathField = new JTextField( properties.getPlayOneMoveButtonImageFilePath(), TEXT_FIELD_WIDTH);
		takeBackOneMoveButtonImageFilePathField = new JTextField( properties.getTakeBackOneMoveButtonImageFilePath(), TEXT_FIELD_WIDTH);
		startAutoPlayButtonImageFilePathField = new JTextField( properties.getStartAutoPlayButtonImageFilePath(), TEXT_FIELD_WIDTH);
		stopAutoPlayButtonImageFilePathField = new JTextField( properties.getStopAutoPlayButtonImageFilePath(), TEXT_FIELD_WIDTH);
		
		// Create buttons for opening file chooser dialogs. They are used for settings pertaining to file paths.
		fileAImageFilePathButton = new JButton( "...");
		fileBImageFilePathButton = new JButton( "...");
		fileCImageFilePathButton = new JButton( "...");
		fileDImageFilePathButton = new JButton( "...");
		fileEImageFilePathButton = new JButton( "...");
		fileFImageFilePathButton = new JButton( "...");
		fileGImageFilePathButton = new JButton( "...");
		fileHImageFilePathButton = new JButton( "...");
		
		rank1ImageFilePathButton = new JButton( "...");
		rank2ImageFilePathButton = new JButton( "...");
		rank3ImageFilePathButton = new JButton( "...");
		rank4ImageFilePathButton = new JButton( "...");
		rank5ImageFilePathButton = new JButton( "...");
		rank6ImageFilePathButton = new JButton( "...");
		rank7ImageFilePathButton = new JButton( "...");
		rank8ImageFilePathButton = new JButton( "...");
		
		emptySquareImageFilePathButton = new JButton( "...");
		blackBackgroundImageFilePathButton = new JButton( "...");
		whiteBackgroundImageFilePathButton = new JButton( "...");
		destinationBackgroundImageFilePathButton = new JButton( "...");
		sourceBackgroundImageFilePathButton = new JButton( "...");
		
		blackPawnImageFilePathButton = new JButton( "...");
		blackKnightImageFilePathButton = new JButton( "...");
		blackBishopImageFilePathButton = new JButton( "...");
		blackRookImageFilePathButton = new JButton( "...");
		blackQueenImageFilePathButton = new JButton( "...");
		blackKingImageFilePathButton = new JButton( "...");
		
		whitePawnImageFilePathButton = new JButton( "...");
		whiteKnightImageFilePathButton = new JButton( "...");
		whiteBishopImageFilePathButton = new JButton( "...");
		whiteRookImageFilePathButton = new JButton( "...");
		whiteQueenImageFilePathButton = new JButton( "...");
		whiteKingImageFilePathButton = new JButton( "...");
		
		playOneMoveButtonImageFilePathButton = new JButton( "...");
		takeBackOneMoveButtonImageFilePathButton = new JButton( "...");
		startAutoPlayButtonImageFilePathButton = new JButton( "...");
		stopAutoPlayButtonImageFilePathButton = new JButton( "...");
		
		// Add an action listener to each button
		ActionListener FilePathButtonListener = new FilePathButtonListener();
		
		fileAImageFilePathButton.addActionListener( FilePathButtonListener);
		fileBImageFilePathButton.addActionListener( FilePathButtonListener);
		fileCImageFilePathButton.addActionListener( FilePathButtonListener);
		fileDImageFilePathButton.addActionListener( FilePathButtonListener);
		fileEImageFilePathButton.addActionListener( FilePathButtonListener);
		fileFImageFilePathButton.addActionListener( FilePathButtonListener);
		fileGImageFilePathButton.addActionListener( FilePathButtonListener);
		fileHImageFilePathButton.addActionListener( FilePathButtonListener);
		
		rank1ImageFilePathButton.addActionListener( FilePathButtonListener);
		rank2ImageFilePathButton.addActionListener( FilePathButtonListener);
		rank3ImageFilePathButton.addActionListener( FilePathButtonListener);
		rank4ImageFilePathButton.addActionListener( FilePathButtonListener);
		rank5ImageFilePathButton.addActionListener( FilePathButtonListener);
		rank6ImageFilePathButton.addActionListener( FilePathButtonListener);
		rank7ImageFilePathButton.addActionListener( FilePathButtonListener);
		rank8ImageFilePathButton.addActionListener( FilePathButtonListener);
		
		emptySquareImageFilePathButton.addActionListener( FilePathButtonListener);
		blackBackgroundImageFilePathButton.addActionListener( FilePathButtonListener);
		whiteBackgroundImageFilePathButton.addActionListener( FilePathButtonListener);
		destinationBackgroundImageFilePathButton.addActionListener( FilePathButtonListener);
		sourceBackgroundImageFilePathButton.addActionListener( FilePathButtonListener);
		
		blackPawnImageFilePathButton.addActionListener( FilePathButtonListener);
		blackKnightImageFilePathButton.addActionListener( FilePathButtonListener);
		blackBishopImageFilePathButton.addActionListener( FilePathButtonListener);
		blackRookImageFilePathButton.addActionListener( FilePathButtonListener);
		blackQueenImageFilePathButton.addActionListener( FilePathButtonListener);
		blackKingImageFilePathButton.addActionListener( FilePathButtonListener);
		
		whitePawnImageFilePathButton.addActionListener( FilePathButtonListener);
		whiteKnightImageFilePathButton.addActionListener( FilePathButtonListener);
		whiteBishopImageFilePathButton.addActionListener( FilePathButtonListener);
		whiteRookImageFilePathButton.addActionListener( FilePathButtonListener);
		whiteQueenImageFilePathButton.addActionListener( FilePathButtonListener);
		whiteKingImageFilePathButton.addActionListener( FilePathButtonListener);
		
		playOneMoveButtonImageFilePathButton.addActionListener( FilePathButtonListener);
		takeBackOneMoveButtonImageFilePathButton.addActionListener( FilePathButtonListener);
		startAutoPlayButtonImageFilePathButton.addActionListener( FilePathButtonListener);
		stopAutoPlayButtonImageFilePathButton.addActionListener( FilePathButtonListener);
		
		// Map text fields to buttons, so it's easy to determine which text field to use when a button is clicked.
		buttonFieldMap = new HashMap<JButton,JTextField>();
		
		buttonFieldMap.put( fileAImageFilePathButton, fileAImageFilePathField);
		buttonFieldMap.put( fileBImageFilePathButton, fileBImageFilePathField);
		buttonFieldMap.put( fileCImageFilePathButton, fileCImageFilePathField);
		buttonFieldMap.put( fileDImageFilePathButton, fileDImageFilePathField);
		buttonFieldMap.put( fileEImageFilePathButton, fileEImageFilePathField);
		buttonFieldMap.put( fileFImageFilePathButton, fileFImageFilePathField);
		buttonFieldMap.put( fileGImageFilePathButton, fileGImageFilePathField);
		buttonFieldMap.put( fileHImageFilePathButton, fileHImageFilePathField);
			
		buttonFieldMap.put( rank1ImageFilePathButton, rank1ImageFilePathField);
		buttonFieldMap.put( rank2ImageFilePathButton, rank2ImageFilePathField);
		buttonFieldMap.put( rank3ImageFilePathButton, rank3ImageFilePathField);
		buttonFieldMap.put( rank4ImageFilePathButton, rank4ImageFilePathField);
		buttonFieldMap.put( rank5ImageFilePathButton, rank5ImageFilePathField);
		buttonFieldMap.put( rank6ImageFilePathButton, rank6ImageFilePathField);
		buttonFieldMap.put( rank7ImageFilePathButton, rank7ImageFilePathField);
		buttonFieldMap.put( rank8ImageFilePathButton, rank8ImageFilePathField);
			
		buttonFieldMap.put( emptySquareImageFilePathButton, emptySquareImageFilePathField);
		buttonFieldMap.put( blackBackgroundImageFilePathButton, blackBackgroundImageFilePathField);
		buttonFieldMap.put( whiteBackgroundImageFilePathButton, whiteBackgroundImageFilePathField);
		buttonFieldMap.put( destinationBackgroundImageFilePathButton, destinationBackgroundImageFilePathField);
		buttonFieldMap.put( sourceBackgroundImageFilePathButton, sourceBackgroundImageFilePathField);
			
		buttonFieldMap.put( blackPawnImageFilePathButton, blackPawnImageFilePathField);
		buttonFieldMap.put( blackKnightImageFilePathButton, blackKnightImageFilePathField);
		buttonFieldMap.put( blackBishopImageFilePathButton, blackBishopImageFilePathField);
		buttonFieldMap.put( blackRookImageFilePathButton, blackRookImageFilePathField);
		buttonFieldMap.put( blackQueenImageFilePathButton, blackQueenImageFilePathField);
		buttonFieldMap.put( blackKingImageFilePathButton, blackKingImageFilePathField);
			
		buttonFieldMap.put( whitePawnImageFilePathButton, whitePawnImageFilePathField);
		buttonFieldMap.put( whiteKnightImageFilePathButton, whiteKnightImageFilePathField);
		buttonFieldMap.put( whiteBishopImageFilePathButton, whiteBishopImageFilePathField);
		buttonFieldMap.put( whiteRookImageFilePathButton, whiteRookImageFilePathField);
		buttonFieldMap.put( whiteQueenImageFilePathButton, whiteQueenImageFilePathField);
		buttonFieldMap.put( whiteKingImageFilePathButton, whiteKingImageFilePathField);
			
		buttonFieldMap.put( playOneMoveButtonImageFilePathButton, playOneMoveButtonImageFilePathField);
		buttonFieldMap.put( takeBackOneMoveButtonImageFilePathButton, takeBackOneMoveButtonImageFilePathField);
		buttonFieldMap.put( startAutoPlayButtonImageFilePathButton, startAutoPlayButtonImageFilePathField);
		buttonFieldMap.put( stopAutoPlayButtonImageFilePathButton, stopAutoPlayButtonImageFilePathField);
		
		// Create load default settings/save/cancel buttons and add an action listener to each of them.
		ActionListener mainButtonListener = new MainButtonListener();
		loadDefaultSettingsButton = new JButton( "Load Default Settings");
		loadDefaultSettingsButton.addActionListener( mainButtonListener);
		saveButton = new JButton( "Save");
		saveButton.addActionListener( mainButtonListener);
		cancelButton = new JButton( "Cancel");
		cancelButton.addActionListener( mainButtonListener);
		
		/* Start adding fields containing settings, their labels and buttons (if any) to the main panel. 
		 * These UI elements are arranged in rows and similar settings are more or less grouped together. Each row contains at most 3 settings
		 */
		// Row 1 (turn time and frame title)
		mainPanel.add( turnTimeLabel, new GridBagConstraints( 0, 0, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( turnTimeField, new GridBagConstraints( 1, 0, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( frameTitleLabel, new GridBagConstraints( 3, 0, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( frameTitleField, new GridBagConstraints( 4, 0, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		
		// Row 2 (end game texts for black win/white win/draw)
		mainPanel.add( whiteWinsTextLabel, new GridBagConstraints( 0, 1, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( whiteWinsTextField, new GridBagConstraints( 1, 1, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( blackWinsTextLabel, new GridBagConstraints( 3, 1, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( blackWinsTextField, new GridBagConstraints( 4, 1, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( drawTextLabel, new GridBagConstraints( 6, 1, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( drawTextField, new GridBagConstraints( 7, 1, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		
		// Row 3 (image files for files A-C)
		mainPanel.add( fileAImageFilePathLabel, new GridBagConstraints( 0, 2, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( fileAImageFilePathField, new GridBagConstraints( 1, 2, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( fileAImageFilePathButton, new GridBagConstraints( 2, 2, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 0, 5, 5), 0, 0));
		mainPanel.add( fileBImageFilePathLabel, new GridBagConstraints( 3, 2, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( fileBImageFilePathField, new GridBagConstraints( 4, 2, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( fileBImageFilePathButton, new GridBagConstraints( 5, 2, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 0, 5, 5), 0, 0));
		mainPanel.add( fileCImageFilePathLabel, new GridBagConstraints( 6, 2, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( fileCImageFilePathField, new GridBagConstraints( 7, 2, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( fileCImageFilePathButton, new GridBagConstraints( 8, 2, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 0, 5, 5), 0, 0));
		
		// Row 4 (image files for files D-F)
		mainPanel.add( fileDImageFilePathLabel, new GridBagConstraints( 0, 3, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( fileDImageFilePathField, new GridBagConstraints( 1, 3, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( fileDImageFilePathButton, new GridBagConstraints( 2, 3, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 0, 5, 5), 0, 0));
		mainPanel.add( fileEImageFilePathLabel, new GridBagConstraints( 3, 3, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( fileEImageFilePathField, new GridBagConstraints( 4, 3, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( fileEImageFilePathButton, new GridBagConstraints( 5, 3, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 0, 5, 5), 0, 0));
		mainPanel.add( fileFImageFilePathLabel, new GridBagConstraints( 6, 3, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( fileFImageFilePathField, new GridBagConstraints( 7, 3, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( fileFImageFilePathButton, new GridBagConstraints( 8, 3, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 0, 5, 5), 0, 0));
		
		// Row 5 (image files for files G-H)
		mainPanel.add( fileGImageFilePathLabel, new GridBagConstraints( 0, 4, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( fileGImageFilePathField, new GridBagConstraints( 1, 4, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( fileGImageFilePathButton, new GridBagConstraints( 2, 4, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 0, 5, 5), 0, 0));
		mainPanel.add( fileHImageFilePathLabel, new GridBagConstraints( 3, 4, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( fileHImageFilePathField, new GridBagConstraints( 4, 4, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( fileHImageFilePathButton, new GridBagConstraints( 5, 4, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 0, 5, 5), 0, 0));
		
		// Row 6 (image files for ranks 1-3)
		mainPanel.add( rank1ImageFilePathLabel, new GridBagConstraints( 0, 5, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( rank1ImageFilePathField, new GridBagConstraints( 1, 5, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( rank1ImageFilePathButton, new GridBagConstraints( 2, 5, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 0, 5, 5), 0, 0));
		mainPanel.add( rank2ImageFilePathLabel, new GridBagConstraints( 3, 5, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( rank2ImageFilePathField, new GridBagConstraints( 4, 5, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( rank2ImageFilePathButton, new GridBagConstraints( 5, 5, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 0, 5, 5), 0, 0));
		mainPanel.add( rank3ImageFilePathLabel, new GridBagConstraints( 6, 5, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( rank3ImageFilePathField, new GridBagConstraints( 7, 5, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( rank3ImageFilePathButton, new GridBagConstraints( 8, 5, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 0, 5, 5), 0, 0));
		
		// Row 7 (image files for ranks 4-6)
		mainPanel.add( rank4ImageFilePathLabel, new GridBagConstraints( 0, 6, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( rank4ImageFilePathField, new GridBagConstraints( 1, 6, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( rank4ImageFilePathButton, new GridBagConstraints( 2, 6, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 0, 5, 5), 0, 0));
		mainPanel.add( rank5ImageFilePathLabel, new GridBagConstraints( 3, 6, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( rank5ImageFilePathField, new GridBagConstraints( 4, 6, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( rank5ImageFilePathButton, new GridBagConstraints( 5, 6, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 0, 5, 5), 0, 0));
		mainPanel.add( rank6ImageFilePathLabel, new GridBagConstraints( 6, 6, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( rank6ImageFilePathField, new GridBagConstraints( 7, 6, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( rank6ImageFilePathButton, new GridBagConstraints( 8, 6, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 0, 5, 5), 0, 0));
		
		// Row 8 (image files for ranks 7-8)
		mainPanel.add( rank7ImageFilePathLabel, new GridBagConstraints( 0, 7, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( rank7ImageFilePathField, new GridBagConstraints( 1, 7, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( rank7ImageFilePathButton, new GridBagConstraints( 2, 7, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 0, 5, 5), 0, 0));
		mainPanel.add( rank8ImageFilePathLabel, new GridBagConstraints( 3, 7, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( rank8ImageFilePathField, new GridBagConstraints( 4, 7, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( rank8ImageFilePathButton, new GridBagConstraints( 5, 7, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 0, 5, 5), 0, 0));
		
		// Row 9 (image files for empty square, black background and white background)
		mainPanel.add( emptySquareImageFilePathLabel, new GridBagConstraints( 0, 8, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( emptySquareImageFilePathField, new GridBagConstraints( 1, 8, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( emptySquareImageFilePathButton, new GridBagConstraints( 2, 8, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 0, 5, 5), 0, 0));
		mainPanel.add( blackBackgroundImageFilePathLabel, new GridBagConstraints( 3, 8, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( blackBackgroundImageFilePathField, new GridBagConstraints( 4, 8, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( blackBackgroundImageFilePathButton, new GridBagConstraints( 5, 8, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 0, 5, 5), 0, 0));
		mainPanel.add( whiteBackgroundImageFilePathLabel, new GridBagConstraints( 6, 8, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( whiteBackgroundImageFilePathField, new GridBagConstraints( 7, 8, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( whiteBackgroundImageFilePathButton, new GridBagConstraints( 8, 8, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 0, 5, 5), 0, 0));
		
		// Row 10 (image files for destination background and source background)
		mainPanel.add( destinationBackgroundImageFilePathLabel, new GridBagConstraints( 0, 9, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( destinationBackgroundImageFilePathField, new GridBagConstraints( 1, 9, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( destinationBackgroundImageFilePathButton, new GridBagConstraints( 2, 9, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 0, 5, 5), 0, 0));
		mainPanel.add( sourceBackgroundImageFilePathLabel, new GridBagConstraints( 3, 9, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( sourceBackgroundImageFilePathField, new GridBagConstraints( 4, 9, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( sourceBackgroundImageFilePathButton, new GridBagConstraints( 5, 9, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 0, 5, 5), 0, 0));
		
		// Row 11 (image files for black pawn, knight and bishop)
		mainPanel.add( blackPawnImageFilePathLabel, new GridBagConstraints( 0, 10, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( blackPawnImageFilePathField, new GridBagConstraints( 1, 10, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( blackPawnImageFilePathButton, new GridBagConstraints( 2, 10, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 0, 5, 5), 0, 0));
		mainPanel.add( blackKnightImageFilePathLabel, new GridBagConstraints( 3, 10, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( blackKnightImageFilePathField, new GridBagConstraints( 4, 10, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( blackKnightImageFilePathButton, new GridBagConstraints( 5, 10, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 0, 5, 5), 0, 0));
		mainPanel.add( blackBishopImageFilePathLabel, new GridBagConstraints( 6, 10, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( blackBishopImageFilePathField, new GridBagConstraints( 7, 10, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( blackBishopImageFilePathButton, new GridBagConstraints( 8, 10, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 0, 5, 5), 0, 0));
		
		// Row 12 (image files for black rook, queen and king)
		mainPanel.add( blackRookImageFilePathLabel, new GridBagConstraints( 0, 11, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( blackRookImageFilePathField, new GridBagConstraints( 1, 11, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( blackRookImageFilePathButton, new GridBagConstraints( 2, 11, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 0, 5, 5), 0, 0));
		mainPanel.add( blackQueenImageFilePathLabel, new GridBagConstraints( 3, 11, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( blackQueenImageFilePathField, new GridBagConstraints( 4, 11, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( blackQueenImageFilePathButton, new GridBagConstraints( 5, 11, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 0, 5, 5), 0, 0));
		mainPanel.add( blackKingImageFilePathLabel, new GridBagConstraints( 6, 11, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( blackKingImageFilePathField, new GridBagConstraints( 7, 11, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( blackKingImageFilePathButton, new GridBagConstraints( 8, 11, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 0, 5, 5), 0, 0));
		
		// Row 13 (image files for white pawn, knight and bishop)
		mainPanel.add( whitePawnImageFilePathLabel, new GridBagConstraints( 0, 12, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( whitePawnImageFilePathField, new GridBagConstraints( 1, 12, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( whitePawnImageFilePathButton, new GridBagConstraints( 2, 12, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 0, 5, 5), 0, 0));
		mainPanel.add( whiteKnightImageFilePathLabel, new GridBagConstraints( 3, 12, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( whiteKnightImageFilePathField, new GridBagConstraints( 4, 12, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( whiteKnightImageFilePathButton, new GridBagConstraints( 5, 12, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 0, 5, 5), 0, 0));
		mainPanel.add( whiteBishopImageFilePathLabel, new GridBagConstraints( 6, 12, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( whiteBishopImageFilePathField, new GridBagConstraints( 7, 12, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( whiteBishopImageFilePathButton, new GridBagConstraints( 8, 12, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 0, 5, 5), 0, 0));
		
		// Row 14 (image files for white rook, queen and king)
		mainPanel.add( whiteRookImageFilePathLabel, new GridBagConstraints( 0, 13, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( whiteRookImageFilePathField, new GridBagConstraints( 1, 13, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( whiteRookImageFilePathButton, new GridBagConstraints( 2, 13, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 0, 5, 5), 0, 0));
		mainPanel.add( whiteQueenImageFilePathLabel, new GridBagConstraints( 3, 13, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( whiteQueenImageFilePathField, new GridBagConstraints( 4, 13, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( whiteQueenImageFilePathButton, new GridBagConstraints( 5, 13, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 0, 5, 5), 0, 0));
		mainPanel.add( whiteKingImageFilePathLabel, new GridBagConstraints( 6, 13, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( whiteKingImageFilePathField, new GridBagConstraints( 7, 13, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( whiteKingImageFilePathButton, new GridBagConstraints( 8, 13, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 0, 5, 5), 0, 0));
		
		// Row 15 (image files for play one move, take back one move and start auto play buttons)
		mainPanel.add( playOneMoveButtonImageFilePathLabel, new GridBagConstraints( 0, 14, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( playOneMoveButtonImageFilePathField, new GridBagConstraints( 1, 14, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( playOneMoveButtonImageFilePathButton, new GridBagConstraints( 2, 14, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 0, 5, 5), 0, 0));
		mainPanel.add( takeBackOneMoveButtonImageFilePathLabel, new GridBagConstraints( 3, 14, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( takeBackOneMoveButtonImageFilePathField, new GridBagConstraints( 4, 14, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( takeBackOneMoveButtonImageFilePathButton, new GridBagConstraints( 5, 14, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 0, 5, 5), 0, 0));
		mainPanel.add( startAutoPlayButtonImageFilePathLabel, new GridBagConstraints( 6, 14, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( startAutoPlayButtonImageFilePathField, new GridBagConstraints( 7, 14, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( startAutoPlayButtonImageFilePathButton, new GridBagConstraints( 8, 14, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 0, 5, 5), 0, 0));
		
		// Row 16 (image file for stop auto play button)
		mainPanel.add( stopAutoPlayButtonImageFilePathLabel, new GridBagConstraints( 0, 15, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( stopAutoPlayButtonImageFilePathField, new GridBagConstraints( 1, 15, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5), 0, 0));
		mainPanel.add( stopAutoPlayButtonImageFilePathButton, new GridBagConstraints( 2, 15, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 0, 5, 5), 0, 0));
		
		// Add load default settings/save/cancel buttons to the button panel, and add the button panel to the main panel that includes other UI elements.
		buttonPanel.add( loadDefaultSettingsButton);
		buttonPanel.add( saveButton);
		buttonPanel.add( cancelButton);
		mainPanel.add( buttonPanel, new GridBagConstraints( 6, 17, 3, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 0), 0, 0));
		
		// Make the frame visible and resizable
		this.setVisible( true);
		this.setResizable(true);
		this.repaint();
	}
	
	/* Action listener for buttons that are used for opening file chooser dialogs. When an action is performed, 
	 * text field corresponding to the button is found and the path of the file chosen from the file chooser dialog is written to the field.
	 */
	private class FilePathButtonListener implements ActionListener
	{
		public void actionPerformed( ActionEvent event)
		{
			if( event.getSource() instanceof JButton)
			{
				// Get text field corresponding to the button that is the source of the event, using the map that pairs text fields with buttons.
				JTextField sourceField = buttonFieldMap.get( (JButton)(event.getSource()));
				
				if( sourceField != null) // Should always be true
				{
					// If the text field contains path of an existing file, find the file's directory
					File currentDirectory = null;
					try {
						File tmp = new File( sourceField.getText());
						if( tmp.exists())
							currentDirectory = new File( sourceField.getText()).getParentFile();
					} catch( Exception e) {}
					
					// If a directory was found above, point file chooser there. Otherwise, point it to the user's default directory
					JFileChooser fileChooser;
					if( currentDirectory == null)
						fileChooser = new JFileChooser();
					else
						fileChooser = new JFileChooser( currentDirectory);
					
					// Open file chooser dialog only for PNG files.
					fileChooser.setFileFilter( new FileNameExtensionFilter( "PNG files", "png"));
					
					// If the user chooses a file
					int returnVal = fileChooser.showOpenDialog( SettingsFrame.this);
					if( returnVal == JFileChooser.APPROVE_OPTION)
					{
						// Get the chosen file's canonical path and write it to the corresponding field.
						File imageFile = fileChooser.getSelectedFile();
						String filePath;
						try {
							filePath = imageFile.getCanonicalPath();
							sourceField.setText( filePath);
						} catch( IOException ex) {
							ex.printStackTrace();
						}
					}
				}
			}
		}
	}

	/* Action listener for load default settings/save/cancel buttons. When an action is performed, the default settings are loaded, the current values
	 * in the text fields are saved, or the saving process is cancelled and the last saved values are preserved, depending on the source of the event.
	 */
	private class MainButtonListener implements ActionListener
	{
		public void actionPerformed( ActionEvent event)
		{
			// If event source is load default settings button
			if( event.getSource().equals( loadDefaultSettingsButton))
			{
				// Show a confirmation dialog to the user
				int reply = JOptionPane.showConfirmDialog( SettingsFrame.this, "Are you sure you want to load the default settings?", null, JOptionPane.YES_NO_OPTION);
				
				/* If the user's answer to the confirmation dialog is yes, get the default values of each setting 
				 * from the properties object, and set the corresponding text fields with these default values.
				 */
				if( reply == JOptionPane.YES_OPTION)
				{
					turnTimeField.setText( String.valueOf( properties.getDefaultTurnTime()));
					frameTitleField.setText( properties.getDefaultFrameTitle());
					whiteWinsTextField.setText( properties.getDefaultWhiteWinsText());
					blackWinsTextField.setText( properties.getDefaultBlackWinsText());
					drawTextField.setText( properties.getDefaultDrawText());

					fileAImageFilePathField.setText( properties.getDefaultFileAImageFilePath());
					fileBImageFilePathField.setText( properties.getDefaultFileBImageFilePath());
					fileCImageFilePathField.setText( properties.getDefaultFileCImageFilePath());
					fileDImageFilePathField.setText( properties.getDefaultFileDImageFilePath());
					fileEImageFilePathField.setText( properties.getDefaultFileEImageFilePath());
					fileFImageFilePathField.setText( properties.getDefaultFileFImageFilePath());
					fileGImageFilePathField.setText( properties.getDefaultFileGImageFilePath());
					fileHImageFilePathField.setText( properties.getDefaultFileHImageFilePath());

					rank1ImageFilePathField.setText( properties.getDefaultRank1ImageFilePath());
					rank2ImageFilePathField.setText( properties.getDefaultRank2ImageFilePath());
					rank3ImageFilePathField.setText( properties.getDefaultRank3ImageFilePath());
					rank4ImageFilePathField.setText( properties.getDefaultRank4ImageFilePath());
					rank5ImageFilePathField.setText( properties.getDefaultRank5ImageFilePath());
					rank6ImageFilePathField.setText( properties.getDefaultRank6ImageFilePath());
					rank7ImageFilePathField.setText( properties.getDefaultRank7ImageFilePath());
					rank8ImageFilePathField.setText( properties.getDefaultRank8ImageFilePath());

					emptySquareImageFilePathField.setText( properties.getDefaultEmptySquareImageFilePath());
					blackBackgroundImageFilePathField.setText( properties.getDefaultBlackBackgroundImageFilePath());
					whiteBackgroundImageFilePathField.setText( properties.getDefaultWhiteBackgroundImageFilePath());
					destinationBackgroundImageFilePathField.setText( properties.getDefaultDestinationBackgroundImageFilePath());
					sourceBackgroundImageFilePathField.setText( properties.getDefaultSourceBackgroundImageFilePath());

					blackPawnImageFilePathField.setText( properties.getDefaultBlackPawnImageFilePath());
					blackKnightImageFilePathField.setText( properties.getDefaultBlackKnightImageFilePath());
					blackBishopImageFilePathField.setText( properties.getDefaultBlackBishopImageFilePath());
					blackRookImageFilePathField.setText( properties.getDefaultBlackRookImageFilePath());
					blackQueenImageFilePathField.setText( properties.getDefaultBlackQueenImageFilePath());
					blackKingImageFilePathField.setText( properties.getDefaultBlackKingImageFilePath());

					whitePawnImageFilePathField.setText( properties.getDefaultWhitePawnImageFilePath());
					whiteKnightImageFilePathField.setText( properties.getDefaultWhiteKnightImageFilePath());
					whiteBishopImageFilePathField.setText( properties.getDefaultWhiteBishopImageFilePath());
					whiteRookImageFilePathField.setText( properties.getDefaultWhiteRookImageFilePath());
					whiteQueenImageFilePathField.setText( properties.getDefaultWhiteQueenImageFilePath());
					whiteKingImageFilePathField.setText( properties.getDefaultWhiteKingImageFilePath());

					playOneMoveButtonImageFilePathField.setText( properties.getDefaultPlayOneMoveButtonImageFilePath());
					takeBackOneMoveButtonImageFilePathField.setText( properties.getDefaultTakeBackOneMoveButtonImageFilePath());
					startAutoPlayButtonImageFilePathField.setText( properties.getDefaultStartAutoPlayButtonImageFilePath());
					stopAutoPlayButtonImageFilePathField.setText( properties.getDefaultStopAutoPlayButtonImageFilePath());
				}
			}
			
			// If event source is save button
			else if( event.getSource().equals( saveButton))
			{
				// Show a confirmation dialog to the user
				int reply = JOptionPane.showConfirmDialog( SettingsFrame.this, "Are you sure you want to save the new settings?", null, JOptionPane.YES_NO_OPTION);

				/* If the user's answer to the confirmation dialog is yes, validate the values in the text fields and 
				 * save these values to the properties file if all of them are valid.
				 */
				if( reply == JOptionPane.YES_OPTION)
				{
					// Validate turn time
					ArrayList<String> errorMessagesList = new ArrayList<String>();
					try {
						int turnTime = Integer.parseInt( turnTimeField.getText());
						
						if( turnTime <= 0)
							errorMessagesList.add( "Turn time must be longer than 0 milliseconds!");
					} catch( NumberFormatException nfe) {
						errorMessagesList.add( "Turn time must be an integer!");
					}
					
					// Validate image file paths
					String[] filePaths = {	fileAImageFilePathField.getText(), fileBImageFilePathField.getText(), fileCImageFilePathField.getText(), fileDImageFilePathField.getText(), 
											fileEImageFilePathField.getText(), fileFImageFilePathField.getText(), fileGImageFilePathField.getText(), fileHImageFilePathField.getText(), 
											rank1ImageFilePathField.getText(), rank2ImageFilePathField.getText(), rank3ImageFilePathField.getText(), rank4ImageFilePathField.getText(), 
											rank5ImageFilePathField.getText(), rank6ImageFilePathField.getText(), rank7ImageFilePathField.getText(), rank8ImageFilePathField.getText(), 
											emptySquareImageFilePathField.getText(), blackBackgroundImageFilePathField.getText(), whiteBackgroundImageFilePathField.getText(), 
											destinationBackgroundImageFilePathField.getText(), sourceBackgroundImageFilePathField.getText(), 
											blackPawnImageFilePathField.getText(), blackKnightImageFilePathField.getText(), blackBishopImageFilePathField.getText(), 
											blackRookImageFilePathField.getText(), blackQueenImageFilePathField.getText(), blackKingImageFilePathField.getText(), 
											whitePawnImageFilePathField.getText(), whiteKnightImageFilePathField.getText(), whiteBishopImageFilePathField.getText(), 
											whiteRookImageFilePathField.getText(), whiteQueenImageFilePathField.getText(), whiteKingImageFilePathField.getText(), 
											playOneMoveButtonImageFilePathField.getText(), takeBackOneMoveButtonImageFilePathField.getText(), 
											startAutoPlayButtonImageFilePathField.getText(), stopAutoPlayButtonImageFilePathField.getText()};
					
					/* Iterate over file paths and for each file path, add an error message to the list of error messages if 
					 * it's empty, it doesn't end with .png, or a file (not a directory) with that path doesn't exist.
					 */
					for( String filePath : filePaths)
					{
						String errorMessage = null;
						
						if( filePath == null || filePath.equals( ""))
							errorMessage = "File paths cannot be empty!";
						else if( !filePath.endsWith( ".png"))
							errorMessage = "You can only use .png files!";
						else
						{
							File f = new File( filePath);
							if( !f.exists() || f.isDirectory())
								errorMessage = "Cannot find file " + filePath;
						}
						
						if( errorMessage != null && !errorMessagesList.contains( errorMessage))
							errorMessagesList.add( errorMessage);
					}
					
					// If all image file paths are valid
					if( errorMessagesList.isEmpty())
					{
						// Take a copy of old properties. If something goes wrong while saving new values, properties will be rolled back.
						ChessProperties oldProperties = new ChessProperties( properties);
						
						// Set property values to the values from the text fields
						properties.setTurnTime( Integer.parseInt( turnTimeField.getText()));
						properties.setFrameTitle( frameTitleField.getText());
						properties.setWhiteWinsText( whiteWinsTextField.getText());
						properties.setBlackWinsText( blackWinsTextField.getText());
						properties.setDrawText( drawTextField.getText());
								
						properties.setFileAImageFilePath( fileAImageFilePathField.getText());
						properties.setFileBImageFilePath( fileBImageFilePathField.getText());
						properties.setFileCImageFilePath( fileCImageFilePathField.getText());
						properties.setFileDImageFilePath( fileDImageFilePathField.getText());
						properties.setFileEImageFilePath( fileEImageFilePathField.getText());
						properties.setFileFImageFilePath( fileFImageFilePathField.getText());
						properties.setFileGImageFilePath( fileGImageFilePathField.getText());
						properties.setFileHImageFilePath( fileHImageFilePathField.getText());
								
						properties.setRank1ImageFilePath( rank1ImageFilePathField.getText());
						properties.setRank2ImageFilePath( rank2ImageFilePathField.getText());
						properties.setRank3ImageFilePath( rank3ImageFilePathField.getText());
						properties.setRank4ImageFilePath( rank4ImageFilePathField.getText());
						properties.setRank5ImageFilePath( rank5ImageFilePathField.getText());
						properties.setRank6ImageFilePath( rank6ImageFilePathField.getText());
						properties.setRank7ImageFilePath( rank7ImageFilePathField.getText());
						properties.setRank8ImageFilePath( rank8ImageFilePathField.getText());
								
						properties.setEmptySquareImageFilePath( emptySquareImageFilePathField.getText());
						properties.setBlackBackgroundImageFilePath( blackBackgroundImageFilePathField.getText());
						properties.setWhiteBackgroundImageFilePath( whiteBackgroundImageFilePathField.getText());
						properties.setDestinationBackgroundImageFilePath( destinationBackgroundImageFilePathField.getText());
						properties.setSourceBackgroundImageFilePath( sourceBackgroundImageFilePathField.getText());
								
						properties.setBlackPawnImageFilePath( blackPawnImageFilePathField.getText());
						properties.setBlackKnightImageFilePath( blackKnightImageFilePathField.getText());
						properties.setBlackBishopImageFilePath( blackBishopImageFilePathField.getText());
						properties.setBlackRookImageFilePath( blackRookImageFilePathField.getText());
						properties.setBlackQueenImageFilePath( blackQueenImageFilePathField.getText());
						properties.setBlackKingImageFilePath( blackKingImageFilePathField.getText());
								
						properties.setWhitePawnImageFilePath( whitePawnImageFilePathField.getText());
						properties.setWhiteKnightImageFilePath( whiteKnightImageFilePathField.getText());
						properties.setWhiteBishopImageFilePath( whiteBishopImageFilePathField.getText());
						properties.setWhiteRookImageFilePath( whiteRookImageFilePathField.getText());
						properties.setWhiteQueenImageFilePath( whiteQueenImageFilePathField.getText());
						properties.setWhiteKingImageFilePath( whiteKingImageFilePathField.getText());
								
						properties.setPlayOneMoveButtonImageFilePath( playOneMoveButtonImageFilePathField.getText());
						properties.setTakeBackOneMoveButtonImageFilePath( takeBackOneMoveButtonImageFilePathField.getText());
						properties.setStartAutoPlayButtonImageFilePath( startAutoPlayButtonImageFilePathField.getText());
						properties.setStopAutoPlayButtonImageFilePath( stopAutoPlayButtonImageFilePathField.getText());

						// Try to save properties and inform user of success/failure of the operation. If save operation is successful, close the settings frame. 
						try {
							properties.save();
							String message = "Saved settings successfully!" + newLine + 
												"File settings will take effect after restarting the application, " + 
												"while other settings are effective immediately.";
							JOptionPane.showMessageDialog( SettingsFrame.this, message, null, JOptionPane.INFORMATION_MESSAGE);
							SettingsFrame.this.dispose();
						} catch (IOException e) {
							properties.set( oldProperties); // If the save operation fails, load properties back from old properties
							JOptionPane.showMessageDialog( SettingsFrame.this, e.getMessage(), "Settings Error", JOptionPane.ERROR_MESSAGE);
						}
					}
					// If there are invalid image file paths
					else
					{
						// Display an error massage listing errors in the list of error messages
						String message =  errorMessagesList.size() + " errors occured while trying to save the settings."
												+ newLine + "Please correct the errors before saving.";
						for( String s : errorMessagesList)
							message += newLine + s;

						JOptionPane.showMessageDialog( SettingsFrame.this, message, "Settings Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
			// If event source is cancel button
			else if( event.getSource().equals( cancelButton))
			{
				// Show a confirmation dialog to the user
				int reply = JOptionPane.showConfirmDialog( SettingsFrame.this, "Are you sure you want to cancel? The changes you made will be discarded.", null, JOptionPane.YES_NO_OPTION);

				// If the user's answer to the confirmation dialog is yes, close the settings frame without performing any operation.
				if( reply == JOptionPane.YES_OPTION)
				{
					SettingsFrame.this.dispose();
				}
			}
		}
	}
}
