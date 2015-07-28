/**
 * This exception is thrown if the input file includes an invalid move.
 */

package chessreplayer.move;

public class InvalidMoveException extends Exception
{
	private static final long serialVersionUID = -3038267357513634108L;

	public InvalidMoveException( String message)
	{
		super( message);
	}
}
