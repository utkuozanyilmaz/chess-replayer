/**
 * This exception is thrown if the input file isn't syntactically correct according to portable game notation.
 */

package chessreplayer.parser;

public class PortableGameNotationException extends Exception
{
	private static final long serialVersionUID = 307553191195476544L;

	public PortableGameNotationException( String message)
	{
		super( message);
	}
}
