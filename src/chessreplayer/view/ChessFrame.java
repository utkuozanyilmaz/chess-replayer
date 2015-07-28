/**
 * Main view of the MVC pattern. Includes a chess menu bar (at the top), a play panel (below the chess menu bar), 
 * a board panel(below the play panel, on the left hand side) and an info panel (below the play panel, on the right hand side). 
 * Also includes methods to initialize, update and reset those panels.
 */

package chessreplayer.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.UIManager;

import chessreplayer.controller.ChessController;
import chessreplayer.controller.ChessPropertiesReader;
import chessreplayer.model.Game;

public class ChessFrame extends JFrame
{
	private static final long serialVersionUID = -3920721261807804716L;
	
	private ChessMenuBar chessMenuBar;
	private BoardPanel boardPanel;
	private PlayPanel playPanel;
	private InfoPanel infoPanel;
	private GridBagConstraints  playPanelConstraints, boardPanelConstraints, infoPanelConstraints;

	/* Create the chess frame with current look and feel of the system; set frame title and default close operation.
	 * The minimum size of the frame is 520 x 350; and its initial size is 700 x 530 or 3/4 of screen width x 3/4 of screen height, whichever is smaller.
	 */
	public ChessFrame( ChessController chessController, String frameTitle)
	{
		super( frameTitle);
		
		try {
			UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE);
		
		Dimension screenDimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

		chessMenuBar = new ChessMenuBar( chessController);
	    this.setJMenuBar( chessMenuBar);
		chessMenuBar.setVisible( true);
		
		// Create the layout constraints of panels
		playPanelConstraints = new GridBagConstraints();
		playPanelConstraints.anchor = GridBagConstraints.NORTH;
		playPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
		playPanelConstraints.gridheight = 1;
		playPanelConstraints.gridwidth = 2;
		playPanelConstraints.gridx = 0;
		playPanelConstraints.gridy = 0;
		playPanelConstraints.insets = new Insets( 0, 0, 0, 0);
		playPanelConstraints.ipadx = 0;
		playPanelConstraints.ipady = 0;
		playPanelConstraints.weightx = 0;
		playPanelConstraints.weighty = 0;
		
		boardPanelConstraints = new GridBagConstraints();
		boardPanelConstraints.anchor = GridBagConstraints.EAST;
		boardPanelConstraints.fill = GridBagConstraints.BOTH;
		boardPanelConstraints.gridheight = 1;
		boardPanelConstraints.gridwidth = 1;
		boardPanelConstraints.gridx = 0;
		boardPanelConstraints.gridy = 1;
		boardPanelConstraints.insets = new Insets( 0, 0, 0, 0);
		boardPanelConstraints.ipadx = 0;
		boardPanelConstraints.ipady = 0;
		boardPanelConstraints.weightx = 1.0;
		boardPanelConstraints.weighty = 1.0;
		
		infoPanelConstraints = new GridBagConstraints();
		infoPanelConstraints.anchor = GridBagConstraints.WEST;
		infoPanelConstraints.fill = GridBagConstraints.VERTICAL;
		infoPanelConstraints.gridheight = GridBagConstraints.REMAINDER;
		infoPanelConstraints.gridwidth = GridBagConstraints.REMAINDER;
		infoPanelConstraints.gridx = 1;
		infoPanelConstraints.gridy = 1;
		infoPanelConstraints.insets = new Insets( 0, 0, 0, 0);
		infoPanelConstraints.ipadx = 0;
		infoPanelConstraints.ipady = 0;
		infoPanelConstraints.weightx = 0;
		infoPanelConstraints.weighty = 0;
		
		// Set size and minimum size of the chess frame
		this.setSize( Math.min( BoardPanel.SIZE + InfoPanel.WIDTH, (int)( screenDimension.getWidth() * 0.75)), // Don't take more than 9/16 of the screen at the beginning
						Math.min( BoardPanel.SIZE + PlayPanel.HEIGHT * 2, (int)( screenDimension.getHeight() * 0.75)));
		this.setMinimumSize( new Dimension( BoardPanel.MINIMUM_SIZE + InfoPanel.WIDTH, 
											BoardPanel.MINIMUM_SIZE + PlayPanel.HEIGHT * 2));
		
		// Make the frame visible and resizable
		this.setVisible( true);
		this.setResizable(true);
		this.repaint();
	}
	
	/* Initialize the chess frame by enabling the menu bar, creating/initializing/adding a play panel, 
	 * creating/initializing/adding a board panel and creating/adding an info panel.
	 */
	public void initialize( ChessPropertiesReader properties)
	{
		this.setLayout( new GridBagLayout());
		chessMenuBar.enable();
		
		playPanel = new PlayPanel( BoardPanel.SIZE + InfoPanel.WIDTH);
		playPanel.initialize();
		this.getContentPane().add( playPanel, playPanelConstraints);
		
		boardPanel = new BoardPanel( properties);
		boardPanel.initialize();
		this.getContentPane().add( boardPanel, boardPanelConstraints);
		
		infoPanel = new InfoPanel( BoardPanel.SIZE);
		this.getContentPane().add( infoPanel, infoPanelConstraints);
		
		this.setVisible( true);
	}
	
	// Reset the panels
	public void reset()
	{
		playPanel.reset();
		boardPanel.reset();
		infoPanel.reset();
	}

	// Update the views of board panel and play panel, and initialize the view of info panel, using the given model.
	public void initializeModel( Game model)
	{
		boardPanel.updateView( model);
		playPanel.updateView( model.hasStarted(), model.hasEnded());
		infoPanel.initializeView( model.getFullTextList(), model.getTagMap());
	}

	// Update the views of panels using the given model
	public void updateView( Game model)
	{
		boardPanel.updateView( model);
		playPanel.updateView( model.hasStarted(), model.hasEnded());
		infoPanel.updateView( model.getLastPlayedMoveIndex());
		this.repaint();
	}
	
	/* Register the controller with the play panel to establish the link between view and controller. 
	 * It will be used by action listeners of buttons inside play panel.
	 */
	public void registerController( ChessController chessController)
	{
		playPanel.registerController( chessController);
	}
}
