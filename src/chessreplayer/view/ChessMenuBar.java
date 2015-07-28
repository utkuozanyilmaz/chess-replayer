/**
 * Part of the view of the MVC pattern. Handles the menu bar at the top of the user interface.
 */

package chessreplayer.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;

import chessreplayer.controller.ChessController;

public class ChessMenuBar extends JMenuBar
{
	private static final long serialVersionUID = 1119135699194973943L;
	private ChessController chessController;

	private JMenu fileMenu;
	private JMenu settingsMenu;
	private JMenuItem loadPgnFileMenuItem;
	private JMenuItem settingsMenuItem;
	
	/* Create the menu bar with two menus (File and Settings), which contain one menu item each (Load PGN File and Change Settings, respectively)
	 * Also add the necessary listeners and set the necessary mnemonics. 
	 * File menu is initially disabled, it's enabled after it's confirmed that all user interface elements are loaded.
	 */
	public ChessMenuBar( ChessController chessController)
	{
		super();
		this.chessController = chessController;
		
	    // File Menu, F - Mnemonic
	    fileMenu = new JMenu( "File");
	    fileMenu.setMnemonic( KeyEvent.VK_F);

	    // File->Load PGN File, L - Mnemonic
	    loadPgnFileMenuItem = new JMenuItem( "Load PGN File", KeyEvent.VK_L);
	    loadPgnFileMenuItem.addActionListener( new LoadPgnFileMenuItemActionListener());

		fileMenu.setEnabled( false);
	    fileMenu.add( loadPgnFileMenuItem);
	    this.add( fileMenu);
		
	    // Settings Menu, S - Mnemonic
	    settingsMenu = new JMenu( "Settings");
	    settingsMenu.setMnemonic( KeyEvent.VK_S);

	    // Settings->Change Settings, C - Mnemonic
	    settingsMenuItem = new JMenuItem( "Change Settings", KeyEvent.VK_C);
	    settingsMenuItem.addActionListener( new SettingsMenuItemActionListener());
	    
	    settingsMenu.add( settingsMenuItem);
	    this.add( settingsMenu);
	}
	
	// Enable file menu.
	public void enable()
	{
		fileMenu.setEnabled( true);
	}
	
	/* Action listener for Load PGN File menu item. When an action is performed, open a file chooser dialog for PGN files.
	 * The file chosen from the dialog is loaded for replay.
	 */
	private class LoadPgnFileMenuItemActionListener implements ActionListener
	{
		public void actionPerformed( ActionEvent event)
		{
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileFilter( new FileNameExtensionFilter( "PGN files", "pgn"));
			
			int returnVal = fileChooser.showOpenDialog( ChessMenuBar.this.getParent());
			if( returnVal == JFileChooser.APPROVE_OPTION)
			{
				File inputFile = fileChooser.getSelectedFile();
				chessController.loadGame( inputFile);
			}
		}
	}

	// Action listener for Change Settings menu item. When an action is performed, the settings screen is opened.
	private class SettingsMenuItemActionListener implements ActionListener
	{
		public void actionPerformed( ActionEvent event)
		{
			SettingsFrame settingsFrame = new SettingsFrame( "Settings");
			settingsFrame.initialize( chessController.getProperties());
		}
	}
}
