/**
 * An interface declaring the methods every parser should have. It has only one method, which parses the given input file and returns a game object.
 */

package chessreplayer.parser;

import java.io.File;
import java.io.IOException;

import chessreplayer.model.Game;

public interface Parser
{
	public Game parse( File inputFile) throws PortableGameNotationException, IOException;
}
