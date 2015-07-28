/**
 * Part of the view of the MVC pattern. Handles take back one move, toggle auto play and play one move buttons. 
 * It's located between the menu bar and the chess board in the user interface.
 */

package chessreplayer.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import chessreplayer.controller.ChessController;

public class PlayPanel extends JPanel
{
	private static final long serialVersionUID = -923493304140630296L;
	public static final int HEIGHT = 40;
	public static final Color BACKGROUND_COLOR = Color.LIGHT_GRAY;
	
	private BufferedImage takeBackOneMoveButtonImage, startAutoPlayButtonImage, stopAutoPlayButtonImage, playOneMoveButtonImage;
	private JButton takeBackOneMove, toggleAutoPlay, playOneMove;
	private boolean autoPlayState;
	private ChessController chessController;
	
	/* Create the play panel with a fixed height and the given width, and create four images: 
	 * one for take back one move button, two for toggle auto play button and one for play one move button.
	 */
	public PlayPanel( int initialWidth)
	{
		super();
		
		this.setMinimumSize( new Dimension( 0, HEIGHT));
		this.setMaximumSize( new Dimension( Integer.MAX_VALUE, HEIGHT));
		this.setPreferredSize( new Dimension( initialWidth, HEIGHT));
		this.setSize( initialWidth, HEIGHT);
		this.setBackground( BACKGROUND_COLOR);

		takeBackOneMoveButtonImage = new BufferedImage( (int)(HEIGHT * 0.75d), (int)(HEIGHT * 0.75d), BufferedImage.TYPE_INT_ARGB);
		startAutoPlayButtonImage = new BufferedImage( (int)(HEIGHT * 0.75d), (int)(HEIGHT * 0.75d), BufferedImage.TYPE_INT_ARGB);
		stopAutoPlayButtonImage = new BufferedImage( (int)(HEIGHT * 0.75d), (int)(HEIGHT * 0.75d), BufferedImage.TYPE_INT_ARGB);
		playOneMoveButtonImage = new BufferedImage( (int)(HEIGHT * 0.75d), (int)(HEIGHT * 0.75d), BufferedImage.TYPE_INT_ARGB);
	}

	/* Initialize the play panel by drawing the button images using image files. Image files are read from the file system by static methods of ChessImage class. 
	 * After the button images are ready, create take back one move, toggle auto play and play one move buttons using the images, set tooltip texts and 
	 * action listeners of the buttons, disable them and add them to the play panel. Initially, auto play state is false, which means toggle auto play button 
	 * is created using start auto play button image instead of stop auto play button image.
	 */
	public void initialize()
	{
		takeBackOneMoveButtonImage.createGraphics().drawImage( ChessImage.getTakeBackOneMoveButtonImage(), 0, 0, (int)(HEIGHT * 0.75d), (int)(HEIGHT * 0.75d), null, null);
		startAutoPlayButtonImage.createGraphics().drawImage( ChessImage.getStartAutoPlayButtonImage(), 0, 0, (int)(HEIGHT * 0.75d), (int)(HEIGHT * 0.75d), null, null);
		stopAutoPlayButtonImage.createGraphics().drawImage( ChessImage.getStopAutoPlayButtonImage(), 0, 0, (int)(HEIGHT * 0.75d), (int)(HEIGHT * 0.75d), null, null);
		playOneMoveButtonImage.createGraphics().drawImage( ChessImage.getPlayOneMoveButtonImage(), 0, 0, (int)(HEIGHT * 0.75d), (int)(HEIGHT * 0.75d), null, null);
		
		takeBackOneMove = new JButton( new ImageIcon( takeBackOneMoveButtonImage));
		toggleAutoPlay = new JButton( new ImageIcon( startAutoPlayButtonImage));
		playOneMove = new JButton( new ImageIcon( playOneMoveButtonImage));
		
		autoPlayState = false;
		
		takeBackOneMove.setToolTipText( "Take back one move");
		toggleAutoPlay.setToolTipText( "Start/stop auto play");
		playOneMove.setToolTipText( "Play one move");

		takeBackOneMove.addActionListener( new TakeBackOneMoveListener());
		toggleAutoPlay.addActionListener( new ToggleAutoPlayListener());
		playOneMove.addActionListener( new PlayOneMoveListener());
		
		takeBackOneMove.setEnabled( false);
		toggleAutoPlay.setEnabled( false);
		playOneMove.setEnabled( false);
		
		this.setLayout( new FlowLayout( FlowLayout.CENTER, (int)(HEIGHT * 0.75d), 0));
		this.add( takeBackOneMove);
		this.add( toggleAutoPlay);
		this.add( playOneMove);
	}

	// Stop auto play if necessary, and disable the buttons.
	public void reset()
	{
		// If the game is on auto play, stop it
		if( autoPlayState)
			toggleAutoPlay.doClick();
		
		takeBackOneMove.setEnabled( false);
		toggleAutoPlay.setEnabled( false);
		playOneMove.setEnabled( false);
	}
	
	// Register the controller to establish the link between view and controller. It will be used by action listeners of the buttons.
	public void registerController( ChessController chessController)
	{
		this.chessController = chessController;
	}

	/* Update the play panel. Stop auto play if it is on and the game has ended; 
	 * enable take back one move button if the game has started and auto play is off, otherwise disable it; 
	 * enable auto play button if the game hasn't ended, otherwise disable it; 
	 * enable play one move button if the game hasn't ended and auto play is off, otherwise disable it.
	 */
	public void updateView( boolean hasStarted, boolean hasEnded)
	{
		if( hasEnded && autoPlayState)
			toggleAutoPlay.doClick();
		
		takeBackOneMove.setEnabled( hasStarted && !autoPlayState);
		toggleAutoPlay.setEnabled( !hasEnded);
		playOneMove.setEnabled( !hasEnded && !autoPlayState);
	}
	
	/* Action listener for take back one move button. When an action is performed, 
	 * calls take back move button of the controller and disables the buttons until the action is complete.
	 */
	private class TakeBackOneMoveListener implements ActionListener
	{
		public void actionPerformed( ActionEvent event)
		{
			takeBackOneMove.setEnabled( false);
			toggleAutoPlay.setEnabled( false);
			playOneMove.setEnabled( false);
			
			chessController.takeBackMove();
		}
	}
	
	/* Action listener for toggle auto play button. When an action is performed, first toggles auto play state. 
	 * If the new state in on, disables the other two buttons, starts auto play and changes button image to stop auto play button image. 
	 * Otherwise, stops auto play and changes button image to start auto play button image. The other two buttons will be enabled 
	 * if necessary during view update.
	 */
	private class ToggleAutoPlayListener implements ActionListener
	{
		public void actionPerformed( ActionEvent event)
		{
			boolean state = !autoPlayState;
			autoPlayState = state;

			if( state)
			{
				takeBackOneMove.setEnabled( false);
				playOneMove.setEnabled( false);
				chessController.startAutoPlay();
				toggleAutoPlay.setIcon( new ImageIcon( stopAutoPlayButtonImage));
			}
			else
			{
				chessController.stopAutoPlay();
				toggleAutoPlay.setIcon( new ImageIcon( startAutoPlayButtonImage));
			}
		}
	}
	
	/* Action listener for play back one move button. When an action is performed, 
	 * calls play move button of the controller and disables the buttons until the action is complete.
	 */
	private class PlayOneMoveListener implements ActionListener
	{
		public void actionPerformed( ActionEvent event)
		{
			takeBackOneMove.setEnabled( false);
			toggleAutoPlay.setEnabled( false);
			playOneMove.setEnabled( false);
			
			chessController.playMove();
		}
	}
}
