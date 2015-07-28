/**
 * This exception is thrown if an image file used in the user interface cannot be found in the given location.
 */

package chessreplayer.view;

public class ChessImageException extends Exception
{
	private static final long serialVersionUID = 6874714552683716561L;
	
	public ChessImageException( String message)
	{
		super( message);
	}
}
