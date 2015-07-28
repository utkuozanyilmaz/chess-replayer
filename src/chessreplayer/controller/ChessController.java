/**
 * Controller of the MVC pattern, also includes the main method.
 */

package chessreplayer.controller;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import chessreplayer.model.Game;
import chessreplayer.move.IllegalPromotionException;
import chessreplayer.move.InvalidMoveException;
import chessreplayer.parser.PortableGameNotationException;
import chessreplayer.parser.PortableGameNotationParser;
import chessreplayer.view.ChessFrame;
import chessreplayer.view.ChessImage;
import chessreplayer.view.ChessImageException;

public class ChessController
{
	private Game game;
	private ChessFrame view;
	private PortableGameNotationParser parser;
	private String inputFilePath;
	
	private boolean imagesLoaded;
	private boolean autoPlay;
	private Thread autoPlayThread;
	
	private volatile ChessProperties properties = new ChessProperties();
	
	public ChessController()
	{
		// Create the main frame
		view = new ChessFrame( this, properties.getFrameTitle());
		
		// Load the necessary images and initialize the view
		imagesLoaded = false;
		try {
			ChessImage.loadImages( properties);
			imagesLoaded = true;
			view.initialize( properties);
		} catch( ChessImageException e) {
			JOptionPane.showMessageDialog( view, e.getMessage(), "Image IO Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
		this.setAutoPlay( false); // Autoplay is turned off by default
	}
	
	private boolean isAutoPlay()
	{
		return autoPlay;
	}
	
	private void setAutoPlay( boolean autoPlay)
	{
		this.autoPlay = autoPlay;
	}
	
	public String getInputFilePath()
	{
		return inputFilePath;
	}
	
	public void setInputFilePath( String inputFilePath)
	{
		this.inputFilePath = inputFilePath;
	}
	
	public ChessProperties getProperties()
	{
		return properties;
	}
	
	public boolean isImagesLoaded()
	{
		return imagesLoaded;
	}
	
	// Reset the board and load a new game
	public void loadGame( File inputFile)
	{
		boolean error = false;
		view.reset();
		
		// Parse the given file and initialize the board
		try {
			parser = new PortableGameNotationParser();
			game = parser.parse( inputFile);
			game.initializeBoard();
		} catch( PortableGameNotationException e) {
			error = true;
			JOptionPane.showMessageDialog( view, e.getMessage(), "Portable Game Notation Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch( IOException e) {
			error = true;
			JOptionPane.showMessageDialog( view, e.getMessage(), "IO Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
		// Validate the moves in the file
		try {
			game.validateMoves();
		} catch( InvalidMoveException e) {
			error = true;
			JOptionPane.showMessageDialog( view, e.getMessage(), "Invalid Move", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch( IllegalPromotionException e) {
			error = true;
			JOptionPane.showMessageDialog( view, e.getMessage(), "Illegal Promotion", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
		// If everything is OK with the given file, make the remaining connections between MVC parts
		if( !error)
		{
			view.registerController( this);
			view.initializeModel( game);
			view.updateView( game);
		}
	}
	
	// Start automatic playing and go on until either the auto play is stopped, or the game ends
	public synchronized void startAutoPlay()
	{
		this.setAutoPlay( true);
		
		autoPlayThread = new Thread( new Runnable()
			{
				public void run()
				{
					while( !game.hasEnded() && isAutoPlay())
					{
						// Execute moves one after another, and wait for turn time milliseconds between each move
						try {
							Thread.sleep( properties.getTurnTime());
						}  catch (InterruptedException e) {
							break;
						}
						playMove();
					}
					view.updateView( game);
				}
			}
		);
		
		autoPlayThread.start();
	}
	
	// Stop automatic playing
	public synchronized void stopAutoPlay()
	{
		this.setAutoPlay( false);
		if( autoPlayThread != null && autoPlayThread.isAlive())
			autoPlayThread.interrupt();
	}
	
	// If the game hasn't ended, play one move
	public synchronized void playMove()
	{
		if( !game.hasEnded())
		{
			game.playTurn();
			view.updateView( game);
		}
	}
	
	// If the game has started, take back one turn
	public synchronized void takeBackMove()
	{
		if( game.hasStarted())
		{
			game.takeBackTurn();
			view.updateView( game);
		}
	}
	
	public static void main( String[] args)
	{
		new ChessController();
	}
}
