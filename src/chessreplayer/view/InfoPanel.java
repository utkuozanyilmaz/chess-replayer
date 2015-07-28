/**
 * Part of the view of the MVC pattern. Handles the information panel, which includes game tags and the list of moves, on the right side of the user interface.
 */

package chessreplayer.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.HashMap;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

public class InfoPanel extends JPanel
{
	private static final long serialVersionUID = 2870472471090251407L;
	public static final int WIDTH = 250;
	public static final Color BACKGROUND_COLOR = Color.LIGHT_GRAY;
	public static final Color HIGHLIGHT_COLOR = Color.GRAY;
	
	private List<String> moveTextList;
	private JTextArea textArea;
	private Caret caret;
	private JScrollPane scrollPane;
	private Highlighter highlighter;
	private DefaultHighlighter.DefaultHighlightPainter highLightPainter;
	
	private static final String newLine = System.getProperty( "line.separator");

	/* Create the info panel with a fixed width and the given height. The info panel has a non-editable text area in a scroll pane. 
	 * Also set up a highlight painter, to highlight the last move in the list of moves.
	 */
	public InfoPanel( int initialHeight)
	{
		super();
		this.setMinimumSize( new Dimension( WIDTH, 0));
		this.setMaximumSize( new Dimension( WIDTH, Integer.MAX_VALUE));
		this.setPreferredSize( new Dimension( WIDTH, initialHeight));
		this.setSize( WIDTH, initialHeight);
		
		textArea = new JTextArea();
		textArea.setEditable( false);
		textArea.setWrapStyleWord( true);
		textArea.setLineWrap( true);
		textArea.setBackground( BACKGROUND_COLOR);
		
		highLightPainter = new DefaultHighlighter.DefaultHighlightPainter( HIGHLIGHT_COLOR);
		caret = textArea.getCaret();
		caret.setVisible( false);
		
		scrollPane = new JScrollPane();
		scrollPane.setBorder( null);
		scrollPane.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setViewportView( textArea);
		
		this.setLayout( new GridLayout( 1,1));
		this.add( scrollPane);
	}
	
	// Initialize the info panel by adding the given game tags and move texts to the text area.
	public void initializeView( List<String> moveTextList, HashMap<String, String> tagMap)
	{
		this.moveTextList = moveTextList;
		
		for( String key : tagMap.keySet())
		{
			String value = tagMap.get( key);
			textArea.append( key + " : " + value + newLine);
		}
		
		if( !tagMap.isEmpty())
			textArea.append( newLine);
		
		for( String str : this.moveTextList)
			textArea.append( str + newLine);
		
		highlighter = textArea.getHighlighter();
	}
	
	// Clear the info panel
	public void reset()
	{
		this.moveTextList = null; 
		this.textArea.setText( null);
		this.scrollPane.getViewport().setViewPosition( new Point( 0,0));
		if( this.highlighter != null)
			this.highlighter.removeAllHighlights();
	}
	
	/* Update the info panel by highlighting the text of the last played move, using the given last played move index; 
	 * and scroll the panel as necessary by setting the caret position.
	 */
	public void updateView( int lastPlayedMoveIndex)
	{
		int moveIndex = lastPlayedMoveIndex - 1; // Index starts from 0
		
		// If there are no move texts, there is nothing to highlight
		if( this.moveTextList != null && moveIndex < this.moveTextList.size())
		{
			// No moves has been played yet. Remove all highlights and scroll to the beginning.
			if( moveIndex < 0)
			{
				highlighter.removeAllHighlights();
				textArea.setCaretPosition( textArea.getText().indexOf( this.moveTextList.get( 0) + newLine));
			}
			else
			{
				// Find the beginning and end indexes of the text of the last played move in the text area.
				int startIndex = textArea.getText().indexOf( this.moveTextList.get( moveIndex) + newLine);
				int endIndex = startIndex + this.moveTextList.get( moveIndex).length();
				
				// Highlight the text between the beginning and end indexes, and set the caret position to the end index to scroll as necessary.
				highlighter.removeAllHighlights();
				textArea.setCaretPosition( endIndex);
				try {
					highlighter.addHighlight( startIndex, endIndex, highLightPainter);
				} catch (BadLocationException e) {}
			}
		}
	}
}
