/**
 * This exception is thrown if the input file includes an illegal promotion.
 */

package chessreplayer.move;

public class IllegalPromotionException extends Exception
{
	private static final long serialVersionUID = 5304278853811683007L;
	
	public IllegalPromotionException( String message)
	{
		super( message);
	}
}
