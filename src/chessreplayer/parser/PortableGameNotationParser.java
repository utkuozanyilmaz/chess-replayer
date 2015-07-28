/** Includes the formal syntax necessary to parse a pgn file. Implements the parse method, as every class implementing the parser interface should. 
 * Also includes methods that help with the parsing by implementing a variety of parsing tasks.
 * 
 * Formal syntax is as follows.
 * Note: This implementation assumes the input file contains a single <PGN-game>. Therefore, <PGN-database> of the formal syntax isn't used.
 * 
 * <PGN-database> ::= <PGN-game> <PGN-database>
 *                    <empty>
 * 
 * <PGN-game> ::= <tag-section> <movetext-section>
 * 
 * <tag-section> ::= <tag-pair> <tag-section>
 *                   <empty>
 * 
 * <tag-pair> ::= [ <tag-name> <tag-value> ]
 * 
 * <tag-name> ::= <identifier>
 * 
 * <tag-value> ::= <string>
 * 
 * <movetext-section> ::= <element-sequence> <game-termination>
 * 
 * <element-sequence> ::= <element> <element-sequence>
 *                        <recursive-variation> <element-sequence>
 *                        <empty>
 * 
 * <element> ::= <move-number-indication>
 *               <SAN-move>
 *               <numeric-annotation-glyph>
 * 
 * <recursive-variation> ::= ( <element-sequence> )
 * 
 * <game-termination> ::= 1-0
 *                        0-1
 *                        1/2-1/2
 *                        *
 * <empty> ::=
 */

package chessreplayer.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import chessreplayer.model.Game;
import chessreplayer.model.Game.Result;
import chessreplayer.move.CastlingMove;
import chessreplayer.move.Move;
import chessreplayer.move.PromotionMove;
import chessreplayer.parser.Node.NodeType;
import chessreplayer.piece.Color;

public class PortableGameNotationParser implements Parser
{
	// Parser state keeps track of the current state of the finite state machine used to create the parse tree.
	private enum ParserState { INITIALIZING, STRING_TOKEN, INTEGER_TOKEN, SYMBOL_TOKEN, 
								NAG, BRACE_COMMENT, REST_OF_LINE_COMMENT };
	
	// Define the special characters as static final, to avoid using literals inside the code.
	private static final char PAWN_SYMBOL = 'P';
	private static final char KNIGHT_SYMBOL = 'N';
	private static final char BISHOP_SYMBOL = 'B';
	private static final char ROOK_SYMBOL = 'R';
	private static final char QUEEN_SYMBOL = 'Q';
	private static final char KING_SYMBOL = 'K';
	private static final char CAPTURE_SYMBOL = 'x';

	private static final char CH_ASTERISK = '*';
	private static final char CH_DOT = '.';
	private static final char CH_LEFT_BRACKET = '[';
	private static final char CH_RIGHT_BRACKET = ']';
	private static final char CH_LEFT_PARENTHESIS = '(';
	private static final char CH_RIGHT_PARENTHESIS = ')';
	private static final char CH_LEFT_CHEVRON = '<';
	private static final char CH_RIGHT_CHEVRON = '>';
	private static final char CH_QUOTE = '"';
	private static final char CH_DOLLAR_SIGN = '$';
	private static final char CH_LEFT_BRACE = '{';
	private static final char CH_RIGHT_BRACE = '}';
	private static final char CH_SEMICOLON = ';';
	private static final char CH_BACKSLASH = '\\';
	private static final char CH_UNDERSCORE = '_';
	private static final char CH_PLUS = '+';
	private static final char CH_HASH = '#';
	private static final char CH_EQUAL_SIGN = '=';
	private static final char CH_COLON = ':';
	private static final char CH_DASH = '-';
	private static final char CH_SLASH = '/';
	private static final char CH_NEWLINE = '\n';
	private static final char CH_CARRIAGE_RETURN = '\r';
	
	// Define the regex strings as static final, to avoid using literals inside the code.
	private static final String KINGSIDE_CASTLING = "O-O";
	private static final String QUEENSIDE_CASTLING = "O-O-O";
	private static final String PROMOTION_REGEX = PAWN_SYMBOL + "?[abcdefgh]?[1-8]?x?[abcdefgh][1-8]=[" + KING_SYMBOL + 
													QUEEN_SYMBOL + ROOK_SYMBOL + BISHOP_SYMBOL + KNIGHT_SYMBOL + "]";

	private static final String GAME_TERMINATION_WHITE_WINS_REGEX = "1-0";
	private static final String GAME_TERMINATION_BLACK_WINS_REGEX = "0-1";
	private static final String GAME_TERMINATION_DRAW_REGEX = "1/2-1/2";
	private static final String GAME_TERMINATION_UNKNOWN_REGEX = "\\*";
	
	private static final String GAME_TERMINATION_REGEX = GAME_TERMINATION_WHITE_WINS_REGEX + "|" + GAME_TERMINATION_BLACK_WINS_REGEX + "|" + 
															GAME_TERMINATION_DRAW_REGEX + "|" + GAME_TERMINATION_UNKNOWN_REGEX;
	private static final String CHECKING_REGEX = "[\\+#]{1}";
	private static final String NAG_REGEX = "[!\\?]{1,2}";
	private static final String PRINTABLE_REGEX = "\\p{Print}";
	
	/* Define the regex for a Standard Algebraic Notation move, and compile it into a pattern.
	 * 
	 * [KQRBN]			1		piece
	 * [abcdefgh]		0-1		source file
	 * [12345678]		0-1		source rank
	 * x				0-1		capture
	 * [abcdefgh]		1		destination
	 * [12345678]		1		destination
	 * 		OR
	 * P				0-1		pawn symbol(normally not used)
	 * [abcdefgh]		0-1		source file
	 * [12345678]		0-1		source rank
	 * x				0-1		capture
	 * [abcdefgh]		1		destination
	 * [12345678]		1		destination
	 * =[KQRBN]			0-1		promotion
	 * 		OR
	 * O-O
	 * 		OR
	 * O-O-O
	 * +#				0-1		check/check-mate
	 * !?				0-2		traditional NAG
	 */
	private static final String SAN_REGEX = "(([" + KING_SYMBOL + QUEEN_SYMBOL + ROOK_SYMBOL + BISHOP_SYMBOL + KNIGHT_SYMBOL + 
									"][abcdefgh]?[1-8]?x?[abcdefgh][1-8])|(" + PAWN_SYMBOL + 
									"?[abcdefgh]?[1-8]?x?[abcdefgh][1-8](?:=[" + KING_SYMBOL + QUEEN_SYMBOL + 
									ROOK_SYMBOL + BISHOP_SYMBOL + KNIGHT_SYMBOL + "])?)|(" + KINGSIDE_CASTLING + ")|(" + 
									QUEENSIDE_CASTLING + "))[\\+#]?[!\\?]{0,2}";
	private Pattern sanPattern = Pattern.compile( SAN_REGEX);
	
	/* Parse the given input file and return a game object. If there is a problem while reading the file, throw an IO exception. 
	 * If the input file isn't syntactically correct according to portable game notation, throw a portable game notation exception.
	 */
	@Override
	public Game parse( File inputFile) throws PortableGameNotationException, IOException
	{
		Game game = null;
		ArrayList<Move> moveList = null;
		HashMap<String,String> tagMap = null;
		ArrayList<String> trailingCommentsList = null;
		String result = null;
		
		// Get the leaf nodes by tokenizing the input file
		ArrayList<LeafNode> tokens = this.tokenize( inputFile);
		ArrayList<Node> nodes = new ArrayList<Node>();
		
		for( LeafNode t : tokens)
			nodes.add( t);
		
		// Build the tree bottom-up from the leaf nodes
		InternalNode root = buildTree( nodes);
		
		// Generate/extract different game elements from the parse tree.
		tagMap = generateTagMap( root);
		moveList = generateMoveList( root);
		result = extractResult( root);
		trailingCommentsList = extractTrailingCommentsList( root);
		
		Result gameResult = null;
		if( GAME_TERMINATION_WHITE_WINS_REGEX.equals( result))
			gameResult = Result.WHITE_WINS;
		else if( GAME_TERMINATION_BLACK_WINS_REGEX.equals( result))
			gameResult = Result.BLACK_WINS;
		else if( GAME_TERMINATION_DRAW_REGEX.equals( result))
			gameResult = Result.DRAW;
		
		// Using the elements generated/extracted fro mthe parse tree, create and return a game.
		game = new Game( moveList, tagMap, gameResult, trailingCommentsList);
		return game;
	}
	
	// Tokenize the input file into leaf nodes by using a finite state machine (FSM) to parse the input file.
	public ArrayList<LeafNode> tokenize( File inputFile) throws PortableGameNotationException, IOException
	{
		ArrayList<LeafNode> nodeList = new ArrayList<LeafNode>();
		
		// Create a buffered reader to read the input file
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			fis = new FileInputStream( inputFile);
			isr = new InputStreamReader( fis, Charset.forName( "ISO-8859-1"));
			br = new BufferedReader( isr);
		} catch( FileNotFoundException e) {
			throw e;
		}
		
		ParserState parserStatus = ParserState.INITIALIZING; // Set the initial state of FSM to initializing
		String token = null;
		int i;
		char ch;
		boolean continueWithCurrentChar;
		try {
			i = br.read();
			while( i != -1) // While there are more characters to read, read them
			{
				ch = (char) i;
				continueWithCurrentChar = false;
	
				if( ParserState.INITIALIZING.equals( parserStatus)) // If FSM state is initializing
				{
					/* A dot forms a one-character token. Create a leaf node and add it to the list. 
					 * FSM state is still initializing, as we're expecting a new token to start.
					 */
					if( ch == CH_DOT)
					{
						nodeList.add( new LeafNode( NodeType.PERIOD, Character.toString( ch)));
					}
					/* An asterisk forms a one-character token. Create a leaf node and add it to the list. 
					 * FSM state is still initializing, as we're expecting a new token to start.
					 */
					else if( ch == CH_ASTERISK)
					{
						nodeList.add( new LeafNode( NodeType.ASTERISK, Character.toString( ch)));
					}
					/* A left bracket forms a one-character token. Create a leaf node and add it to the list. 
					 * FSM state is still initializing, as we're expecting a new token to start.
					 */
					else if( ch == CH_LEFT_BRACKET)
					{
						nodeList.add( new LeafNode( NodeType.LEFT_BRACKET, Character.toString( ch)));
					}
					/* An right bracket forms a one-character token. Create a leaf node and add it to the list. 
					 * FSM state is still initializing, as we're expecting a new token to start.
					 */
					else if( ch == CH_RIGHT_BRACKET)
					{
						nodeList.add( new LeafNode( NodeType.RIGHT_BRACKET, Character.toString( ch)));
					}
					/* An left parenthesis forms a one-character token. Create a leaf node and add it to the list. 
					 * FSM state is still initializing, as we're expecting a new token to start.
					 */
					else if( ch == CH_LEFT_PARENTHESIS)
					{
						nodeList.add( new LeafNode( NodeType.LEFT_PARENTHESIS, Character.toString( ch)));
					}
					/* An right parenthesis forms a one-character token. Create a leaf node and add it to the list. 
					 * FSM state is still initializing, as we're expecting a new token to start.
					 */
					else if( ch == CH_RIGHT_PARENTHESIS)
					{
						nodeList.add( new LeafNode( NodeType.RIGHT_PARENTHESIS, Character.toString( ch)));
					}
					/* An left chevron forms a one-character token. Create a leaf node and add it to the list. 
					 * FSM state is still initializing, as we're expecting a new token to start.
					 */
					else if( ch == CH_LEFT_CHEVRON)
					{
						nodeList.add( new LeafNode( NodeType.LEFT_CHEVRON, Character.toString( ch)));
					}
					/* A right chevron forms a one-character token. Create a leaf node and add it to the list. 
					 * FSM state is still initializing, as we're expecting a new token to start.
					 */
					else if( ch == CH_RIGHT_CHEVRON)
					{
						nodeList.add( new LeafNode( NodeType.RIGHT_CHEVRON, Character.toString( ch)));
					}
					// A quote starts a string token. Create a token from the quote and set FSM state to string token.
					else if( ch == CH_QUOTE)
					{
						token = Character.toString( ch);
						parserStatus = ParserState.STRING_TOKEN;
					}
					// A dollar sign starts a NAG (numeric annotation glyph). Create a token from the dollar sign and set FSM state to NAG.
					else if( ch == CH_DOLLAR_SIGN)
					{
						token = Character.toString( ch);
						parserStatus = ParserState.NAG;
					}
					// A left brace starts a brace comment. Create a token from the left brace and set FSM state to brace comment.
					else if( ch == CH_LEFT_BRACE)
					{
						token = Character.toString( ch);
						parserStatus = ParserState.BRACE_COMMENT;
					}
					// A semicolon starts a rest of line comment. Create a token from the semicolon and set FSM state to rest of line comment.
					else if( ch == CH_SEMICOLON)
					{
						token = Character.toString( ch);
						parserStatus = ParserState.REST_OF_LINE_COMMENT;
					}
					// A digit starts an integer token. Create a token from the digit and set FSM state to integer token.
					else if( Character.isDigit( ch))
					{
						token = Character.toString( ch);
						parserStatus = ParserState.INTEGER_TOKEN;
					}
					// A letter starts a symbol token. Create a token from the letter and set FSM state to symbol token.
					else if( Character.isLetter( ch))
					{
						token = Character.toString( ch);
						parserStatus = ParserState.SYMBOL_TOKEN;
					}
					else if( !Character.isWhitespace( ch)) // If Character.isWhitespace( ch), do nothing
					{
						try {
							br.close();
							
							if( nodeList.isEmpty())
								throw new PortableGameNotationException( "Invalid token at the beginning of the file");
							else
								throw new PortableGameNotationException( "Invalid token after " + nodeList.get( nodeList.size()-1).getText());
						} catch (IOException e) {} // Nothing to do here
						
						try {
							br.close();
						} catch (IOException e) {} // Nothing to do here
					}
				}
				else if( ParserState.STRING_TOKEN.equals( parserStatus)) // If FSM state is string token
				{
					/* A quote that's not escaped using a backslash ends a string token. Create a leaf node from the string token and add it to the list. 
					 * Set FSM state to initializing, as we're expecting a new token to start.
					 */
					if( ch == CH_QUOTE && token.charAt( token.length()-1) != CH_BACKSLASH)
					{
						nodeList.add( new LeafNode( NodeType.STRING_TOKEN, token + ch));
						token = null;
						parserStatus = ParserState.INITIALIZING;
					}
					// Extend the string token by appending the printable character. FSM state is still string token.
					else if( Pattern.matches( PRINTABLE_REGEX, Character.toString( ch)))
						token += ch;
					// Non-printing characters are not permitted inside of strings. Throw a portable game notation exception.
					else // if( ch is a non printing character)
					{
						if( nodeList.isEmpty())
							throw new PortableGameNotationException( "Invalid token at the beginning of the file");
						else
							throw new PortableGameNotationException( "Invalid token after " + nodeList.get( nodeList.size()-1).getText());
					}
				}
				else if( ParserState.INTEGER_TOKEN.equals( parserStatus)) // If FSM state is integer token
				{
					// Extend the integer token by appending the digit. FSM state is still integer token.
					if( Character.isDigit( ch))
						token += ch;
					/* If faced with a non-digit symbol continuation character, set FSM state to symbol token and 
					 * extend the symbol token by appending the non-digit symbol continuation character.
					 */
					else if( ch == CH_UNDERSCORE || ch == CH_PLUS || ch == CH_HASH || ch == CH_EQUAL_SIGN || 
							ch == CH_COLON || ch == CH_DASH || ch == CH_SLASH || Character.isLetterOrDigit( ch))
					{
						parserStatus = ParserState.SYMBOL_TOKEN;
						token += ch;
					}
					/* An integer token is terminated just prior to the first non-symbol character following the integer digit sequence. 
					 * Create a leaf node from the integer token and add it to the list. Set FSM state to initializing, as we're expecting a new token to start. 
					 * As the non-symbol character isn't part of the integer token, continue with the current character.
					 */
					else
					{
						nodeList.add( new LeafNode( NodeType.INTEGER_TOKEN, token));
						token = null;
						parserStatus = ParserState.INITIALIZING;
						continueWithCurrentChar = true;
					}
				}
				else if( ParserState.SYMBOL_TOKEN.equals( parserStatus)) // If FSM state is symbol token
				{
					// Extend the symbol token by appending the symbol continuation character. FSM state is still symbol token.
					if( ch == CH_UNDERSCORE || ch == CH_PLUS || ch == CH_HASH || ch == CH_EQUAL_SIGN || 
						ch == CH_COLON || ch == CH_DASH || ch == CH_SLASH || Character.isLetterOrDigit( ch))
					{
						token += ch;
					}
					/* A symbol token is terminated just prior to the first non-symbol character following the symbol character sequence. 
					 * Create a leaf node from the symbol token and add it to the list. Set FSM state to initializing, as we're expecting a new token to start. 
					 * As the non-symbol character isn't part of the symbol token, continue with the current character.
					 */
					else
					{
						nodeList.add( new LeafNode( NodeType.SYMBOL_TOKEN, token));
						token = null;
						parserStatus = ParserState.INITIALIZING;
						continueWithCurrentChar = true;
					}
				}
				else if( ParserState.NAG.equals( parserStatus)) // If FSM state is NAG
				{
					// Extend the NAG by appending the digit. FSM state is still NAG.
					if( Character.isDigit( ch))
						token += ch;
					/* A NAG is terminated just prior to the first non-digit character following the digit sequence. 
					 * Create a leaf node from the NAG and add it to the list. Set FSM state to initializing, as we're expecting a new token to start. 
					 * As the non-digit character isn't part of the NAG, continue with the current character.
					 */
					else
					{
						nodeList.add( new LeafNode( NodeType.NAG, token));
						token = null;
						parserStatus = ParserState.INITIALIZING;
						continueWithCurrentChar = true;
					}
				}
				else if( ParserState.BRACE_COMMENT.equals( parserStatus)) // If FSM state is brace comment
				{
					/* A right brace ends a brace comment. Create a leaf node from the brace comment and add it to the list. 
					 * Set FSM state to initializing, as we're expecting a new token to start.
					 */
					if( ch == CH_RIGHT_BRACE)
					{
						nodeList.add( new LeafNode( NodeType.BRACE_COMMENT, token + ch));
						token = null;
						parserStatus = ParserState.INITIALIZING;
					}
					// Extend the brace comment by appending the character. FSM state is still brace comment.
					else
						token += ch;
				}
				else if( ParserState.REST_OF_LINE_COMMENT.equals( parserStatus)) // If FSM state is rest of line comment
				{
					/* A newline character ends a rest of line comment. Create a leaf node from the rest of line comment and add it to the list. 
					 * Set FSM state to initializing, as we're expecting a new token to start.
					 */
					if( ch == CH_NEWLINE)
					{
						nodeList.add( new LeafNode( NodeType.REST_OF_LINE_COMMENT, token));
						token = null;
						parserStatus = ParserState.INITIALIZING;
					}
					/* Extend the rest of line comment by appending the character, unless it's a carriage return character, which is used together with 
					 * a newline character in some systems, to denote a new line. FSM state is still rest of line comment.
					 */
					else if( ch != CH_CARRIAGE_RETURN)
						token += ch;
				}
				
				// Read a new character, if we're not continuing with the current one
				if( !continueWithCurrentChar)
					i = br.read();
			}
			
			// Handle last token of the file. The file may end while a token is still going.
			if( i == -1)
			{
				/* String tokens and brace comments have explicit ending characters. Therefore, if the file ends while 
				 * a string token or brace comment is still going, throw a portable game notation exception.
				 */
				if( ParserState.STRING_TOKEN.equals( parserStatus) || ParserState.BRACE_COMMENT.equals( parserStatus))
				{
					try {
						br.close();
					} catch (IOException e) {} // Nothing to do here
					
					if( nodeList.isEmpty())
						throw new PortableGameNotationException( "Invalid token at the beginning of the file");
					else
						throw new PortableGameNotationException( "Invalid token after " + nodeList.get( nodeList.size()-1).getText());
				}
				/* If the file ends while an integer token, a symbol token, a NAG or a rest of line comment is still going, 
				 * create a leaf node from the token and add it to the list.
				 */
				else if( ParserState.INTEGER_TOKEN.equals( parserStatus))
					nodeList.add( new LeafNode( NodeType.INTEGER_TOKEN, token));
				else if( ParserState.SYMBOL_TOKEN.equals( parserStatus))
					nodeList.add( new LeafNode( NodeType.SYMBOL_TOKEN, token));
				else if( ParserState.NAG.equals( parserStatus))
					nodeList.add( new LeafNode( NodeType.NAG, token));
				else if( ParserState.REST_OF_LINE_COMMENT.equals( parserStatus))
					nodeList.add( new LeafNode( NodeType.REST_OF_LINE_COMMENT, token));
				// if( ParserStatus.INITIALIZING.equals( parserStatus)), do nothing
			}
		} catch (IOException e) {
			try {
				br.close();
			} catch (IOException ioe) {} // Nothing to do here
			throw e;
		} catch (PortableGameNotationException e) {
			try {
				br.close();
			} catch (IOException ioe) {} // Nothing to do here
			throw e;
		}
		
		try {
			br.close();
		} catch (IOException e) {} // Nothing to do here
		
		return nodeList;
	}
	
	// Bottom-up tree building
	public InternalNode buildTree( ArrayList<Node> nodeList) throws PortableGameNotationException
	{
		/* At this point, our structure is (LEFT_BRACKET SYMBOL_TOKEN STRING_TOKEN RIGHT_BRACKET)* 
		 * (PERIOD || NAG || INTEGER_TOKEN || SYMBOL_TOKEN || BRACE_COMMENT || REST_OF_LINE_COMMENT || 
		 * LEFT_PARENTHESIS || RIGHT_PARENTHESIS)* <game-termination> (BRACE_COMMENT || REST_OF_LINE_COMMENT)*
		 * Any other token is illegal and will raise a PortableGameNotationException
		 */
		ArrayList<Node> tree = buildTagSectionAndGameTermination( nodeList);
		
		/* At this point, our structure is <tag-section> 
		 * (PERIOD || NAG || INTEGER_TOKEN || SYMBOL_TOKEN || BRACE_COMMENT || REST_OF_LINE_COMMENT || 
		 * LEFT_PARENTHESIS || RIGHT_PARENTHESIS)* <game-termination> and possible trailing comments
		 * Any other token is illegal and will raise a PortableGameNotationException
		 */
		tree = reduceRecursiveVariations( tree);
		
		/* At this point, recursive variations are no longer recursive. 
		 * Later implementations might handle recursive variations properly. 
		 * Our structure is <tag-section> 
		 * (PERIOD || NAG || INTEGER_TOKEN || SYMBOL_TOKEN || BRACE_COMMENT || REST_OF_LINE_COMMENT || 
		 * <no-longer-recursive-variation>)* <game-termination> and possible trailing comments
		 * Any other token is illegal and will raise a PortableGameNotationException
		 */
		tree = buildMoveNumberIndicationsAndSanMoves( tree);
		
		/* At this point, our structure is <tag-section> 
		 * (MOVE_NUMBER_INDICATION || NAG || SAN_MOVE || BRACE_COMMENT || REST_OF_LINE_COMMENT || 
		 * <no-longer-recursive-variation>)* <game-termination> and possible trailing comments
		 * Any other token is illegal and will raise a PortableGameNotationException
		 */
		tree = buildElementSequence( tree);
		
		// At this point, our structure is <tag-section> <element-sequence> <game-termination> and possible trailing comments
		InternalNode root = finalizeTree( tree);
		
		// At this point, we have a single <PGN-game> node as the root of the parse tree; return it.
		return root;
	}
	
	/* Build tag section and game termination parts of the tree.
	 * At this point, our structure is (LEFT_BRACKET SYMBOL_TOKEN STRING_TOKEN RIGHT_BRACKET)* 
	 * (PERIOD || NAG || INTEGER_TOKEN || SYMBOL_TOKEN || BRACE_COMMENT || REST_OF_LINE_COMMENT || 
	 * LEFT_PARENTHESIS || RIGHT_PARENTHESIS)* <game-termination> (BRACE_COMMENT || REST_OF_LINE_COMMENT)*
	 * Any other token is illegal and will raise a PortableGameNotationException
	 */
	public ArrayList<Node> buildTagSectionAndGameTermination( ArrayList<Node> nodeList) throws PortableGameNotationException
	{
		ArrayList<Node> tagPairs = new ArrayList<Node>();
		ArrayList<Node> partialTree = new ArrayList<Node>();
		int index = 0;
		
		// While there are more nodes
		while( index < nodeList.size())
		{
			// If there are enough nodes for a 3 node lookahead
			if( index + 3 < nodeList.size())
			{
				// If the right tokens are in the right order to build a tag pair
				if( NodeType.LEFT_BRACKET.equals( nodeList.get( index).getNodeType()) && 
					NodeType.SYMBOL_TOKEN.equals( nodeList.get( index+1).getNodeType()) && 
					NodeType.STRING_TOKEN.equals( nodeList.get( index+2).getNodeType()) && 
					NodeType.RIGHT_BRACKET.equals( nodeList.get( index+3).getNodeType()))
				{
					// Build a tag name from the symbol token
					InternalNode tagName = new InternalNode( NodeType.TAG_NAME);
					tagName.addChildren( nodeList.get( index+1));

					// Build a tag value from the string token
					InternalNode tagValue = new InternalNode( NodeType.TAG_VALUE);
					tagValue.addChildren( nodeList.get( index+2));
					
					// Create a tag pair and add it to tag pair list
					InternalNode tagPair = new InternalNode( NodeType.TAG_PAIR);
					tagPair.addChildren( nodeList.get( index));
					tagPair.addChildren( tagName);
					tagPair.addChildren( tagValue);
					tagPair.addChildren( nodeList.get( index+3));
					
					tagPairs.add( tagPair);
					index += 4;
				}
				// Tag pairs must be at the beginning of a pgn file. Once they stop we can stop looking for them, as they will never start again.
				else
					break;
			}
			// There aren't enough nodes left for a tag pair, stop looking for them.
			else
				break;
		}
		
		// Create a <tag-section> node from zero or more tag pairs
		InternalNode tagSection = new InternalNode( NodeType.TAG_SECTION);
		for( Node node : tagPairs)
		{
			tagSection.addChildren( node);
		}
		
		// Build a partial tree out of <tag-section> 
		partialTree.add( tagSection);
		
		int reverseIndex = nodeList.size()-1;
		int gameTerminationIndex = -1;
		Node node = null;
		
		// Start from the end of the tree and work in reverse order, to find the game termination
		while( reverseIndex >= index)
		{
			node = nodeList.get( reverseIndex);
			
			// If it is found, set the game termination index and break the loop
			if( Pattern.matches( GAME_TERMINATION_REGEX, node.getText()))
			{
				gameTerminationIndex = reverseIndex;
				break;
			}
			/* There can only be comments after game termination token. If the current token is neither a game termination nor a comment, 
			 * it means there isn't a game termination token, so we can stop looking for it.
			 */
			else if( !NodeType.BRACE_COMMENT.equals( node.getNodeType()) && 
					!NodeType.REST_OF_LINE_COMMENT.equals( node.getNodeType()))
			{
				break;
			}
			reverseIndex--;
		}
		
		// If a game termination token doesn't exist, throw a portable game notation exception.
		if( gameTerminationIndex == -1)
		{
			throw new PortableGameNotationException( "Failed to find game termination token");
		}
		
		// Copy the remaining tree into the partial tree containing <tag-section>.
		while( index < nodeList.size())
		{
			// Build a game termination node from the appropriate token.
			if( index == gameTerminationIndex)
			{
				InternalNode gameTermination = new InternalNode( NodeType.GAME_TERMINATION);
				gameTermination.addChildren( nodeList.get( index));
				partialTree.add( gameTermination);
			}
			else
				partialTree.add( nodeList.get( index));
			++index;
		}
		
		// Return the new tree
		return partialTree;
	}
	
	/* Find recursive variations and reduce each recursive variation to a single node.
	 * At this point, our structure is <tag-section> 
	 * (PERIOD || NAG || INTEGER_TOKEN || SYMBOL_TOKEN || BRACE_COMMENT || REST_OF_LINE_COMMENT || 
	 * LEFT_PARENTHESIS || RIGHT_PARENTHESIS)* <game-termination> and possible trailing comments
	 * Any other token is illegal and will raise a PortableGameNotationException
	 */
	public ArrayList<Node> reduceRecursiveVariations( ArrayList<Node> startingTree) throws PortableGameNotationException
	{
		// Build a new tree and copy <tag-section> into it. 
		ArrayList<Node> endingTree = new ArrayList<Node>();
		endingTree.add( startingTree.get( 0)); // <tag-section>

		int index = 1;
		int noOfOpenParenthesis = 0;
		Node node = startingTree.get( index);
		InternalNode recursiveVariation = null;
		
		// Search for recursive variations between <tag-section> and <game-termination>
		while( !NodeType.GAME_TERMINATION.equals( node.getNodeType()))
		{
			// A left parenthesis indicates the beginning of a recursive variation.
			if( NodeType.LEFT_PARENTHESIS.equals( node.getNodeType()))
			{
				// Create a new recursive variation node, if this is the outermost variation
				if( noOfOpenParenthesis == 0)
					recursiveVariation = new InternalNode( NodeType.RECURSIVE_VARIATION);
				
				recursiveVariation.addChildren( node);
				++noOfOpenParenthesis; // Keep track of the recursion level of recursive variations, so that we can now when we go back to normal moves
			}
			// A right parenthesis indicates the ending of a recursive variation.
			else if( NodeType.RIGHT_PARENTHESIS.equals( node.getNodeType()))
			{
				// If this is the outermost variation, add the recursive variation node to the new tree we are building.
				if( noOfOpenParenthesis == 1)
				{
					recursiveVariation.addChildren( node);
					endingTree.add( recursiveVariation);
					recursiveVariation = null;
				}
				// We're in more than one level of recursive variations, so this right parenthesis doesn't end the recursive variation.
				else if( noOfOpenParenthesis > 1)
				{
					recursiveVariation.addChildren( node);
				}
				// If there exists a right parenthesis when there are no open parenthesis, throw a portable game notation exception.
				else // noOfOpenParenthesis == 0
				{
					throw new PortableGameNotationException( "Unmatched right parenthesis after " + endingTree.get( endingTree.size()-1).getText());
				}
				--noOfOpenParenthesis;
			}
			/* Only accept the following nodes: PERIOD, NAG, INTEGER_TOKEN, SYMBOL_TOKEN, BRACE_COMMENT, 
			 * REST_OF_LINE_COMMENT. Any other node is illegal and will raise a PortableGameNotationException.
			 */
			else if( NodeType.PERIOD.equals( node.getNodeType()) || 
					NodeType.NAG.equals( node.getNodeType()) || 
					NodeType.INTEGER_TOKEN.equals( node.getNodeType()) || 
					NodeType.SYMBOL_TOKEN.equals( node.getNodeType()) || 
					NodeType.BRACE_COMMENT.equals( node.getNodeType()) || 
					NodeType.REST_OF_LINE_COMMENT.equals( node.getNodeType()))
			{
				if( noOfOpenParenthesis > 0) // We're inside a recursive variation, continue building it.
					recursiveVariation.addChildren( node);
				else if( noOfOpenParenthesis == 0) // We're not inside a recursive variation, add the node to the new tree we are building.
					endingTree.add( node);
			}
			// Invalid node, throw a portable game notation exception.
			else
			{
				throw new PortableGameNotationException( "Unexpected " + node.getNodeType().name() + " token after " + endingTree.get( endingTree.size()-1).getText());
			}
			++index;
			node = startingTree.get( index);
		}
		
		// If there still are unclosed parentheses after reaching <game-termination>, throw a portable game notation exception.
		if( noOfOpenParenthesis > 0)
			throw new PortableGameNotationException( "Unmatched left parenthesis after " + endingTree.get( endingTree.size()-1).getText());
		
		// Add <game-termination> and possible trailing comments to the new tree we are building.
		while( index < startingTree.size())
		{
			node = startingTree.get( index);
			endingTree.add( node);
			++index;
		}
		
		// Return the new tree
		return endingTree;
	}
	
	/* Build move number indications and SAN (Standard Algebraic Notation) moves of the tree.
	 * At this point, our structure is <tag-section> 
	 * (PERIOD || NAG || INTEGER_TOKEN || SYMBOL_TOKEN || BRACE_COMMENT || REST_OF_LINE_COMMENT || 
	 * <no-longer-recursive-variation>)* <game-termination> and possible trailing comments
	 */
	public ArrayList<Node> buildMoveNumberIndicationsAndSanMoves( ArrayList<Node> startingTree) throws PortableGameNotationException
	{
		// Build a new tree and copy <tag-section> into it. 
		ArrayList<Node> endingTree = new ArrayList<Node>();
		endingTree.add( startingTree.get( 0)); // <tag-section>
		int index = 1;
		
		Node node = startingTree.get( index);

		// Search for SAN moves and move number indications between <tag-section> and <game-termination>
		while( !NodeType.GAME_TERMINATION.equals( node.getNodeType()))
		{
			// A symbol token at this stage in tree building should be a SAN move
			if( NodeType.SYMBOL_TOKEN.equals( node.getNodeType()))
			{
				// If the symbol token matches the SAN pattern, create a SAN move node and add it to the new tree we are building.
				if( sanPattern.matcher( node.getText()).matches())
				{
					InternalNode sanMove = new InternalNode( NodeType.SAN_MOVE);
					sanMove.addChildren( node);
					endingTree.add( sanMove);
				}
				else // If the symbol token doesn't match the SAN pattern, throw a portable game notation exception.
					throw new PortableGameNotationException( "Syntax error on SAN move token " + node.getText());
			}
			// An integer token at this stage in tree building is a move number indication
			else if( NodeType.INTEGER_TOKEN.equals( node.getNodeType()))
			{
				// Create a move number indication.
				InternalNode moveNumberIndication = new InternalNode( NodeType.MOVE_NUMBER_INDICATION);
				moveNumberIndication.addChildren( node);
				
				// Move number indications may have zero or more period characters following the integer token that gives the move number
				while( NodeType.PERIOD.equals( startingTree.get( index+1).getNodeType()))
				{
					moveNumberIndication.addChildren( startingTree.get( index+1));
					++index;
				}
				
				// Add the move number indication to the new tree we are building.
				endingTree.add( moveNumberIndication);
			}
			// This means we encounter a period, which is not preceded by an integer token, throw a portable game notation exception.
			else if( NodeType.PERIOD.equals( node.getNodeType()))
				throw new PortableGameNotationException( "Unexpected " + NodeType.PERIOD + " token after " + endingTree.get( endingTree.size()-1).getText());
			else // Add any other types of nodes to the new tree we are building.
				endingTree.add( node);
			
			++index;
			node = startingTree.get( index);
		}
		
		// Add <game-termination> and possible trailing comments to the new tree we are building.
		while( index < startingTree.size())
		{
			node = startingTree.get( index);
			endingTree.add( node);
			++index;
		}
		
		// Return the new tree
		return endingTree;
	}
	
	/*  Build the element sequence of the tree.
	 * At this point, our structure is <tag-section> 
	 * (MOVE_NUMBER_INDICATION || NAG || SAN_MOVE || BRACE_COMMENT || REST_OF_LINE_COMMENT || 
	 * <no-longer-recursive-variation>)* <game-termination> and possible trailing comments
	 * Any other token is illegal and will raise a PortableGameNotationException
	 */
	public ArrayList<Node> buildElementSequence( ArrayList<Node> startingTree) throws PortableGameNotationException
	{
		ArrayList<Node> endingTree = new ArrayList<Node>();
		endingTree.add( startingTree.get( 0)); // <tag-section>
		int index = 1;
		
		InternalNode elementSequence = new InternalNode( NodeType.ELEMENT_SEQUENCE);
		InternalNode fullTurn = new InternalNode( NodeType.FULL_TURN);
		int noOfMoves = 0;
		Node node = startingTree.get( index);

		// Search for SAN moves between <tag-section> and <game-termination>
		while( !NodeType.GAME_TERMINATION.equals( node.getNodeType()))
		{
			// Add any nodes we encounter to the current turn, until there are already 2 moves added to it
			if( noOfMoves < 2)
				fullTurn.addChildren( node);
			else // 2 moves are already added to the current turn. Any other nodes must be checked, as they might be the beginning of a new turn.
			{
				/* NAG, BRACE_COMMENT, REST_OF_LINE_COMMENT and RECURSIVE_VARIATION nodes can come after 
				 * the second move of a turn and don't start a new turn. Add these nodes to the current turn.
				 */
				if( NodeType.NAG.equals( node.getNodeType()) || NodeType.BRACE_COMMENT.equals( node.getNodeType()) || 
					NodeType.REST_OF_LINE_COMMENT.equals( node.getNodeType()) || 
					NodeType.RECURSIVE_VARIATION.equals( node.getNodeType()))
				{
					fullTurn.addChildren( node);
				}
				// MOVE_NUMBER_INDICATION and SAN_MOVE nodes start a new turn. Add the current turn to the element sequence, and create a new turn using this node.
				else if( NodeType.MOVE_NUMBER_INDICATION.equals( node.getNodeType()) || 
						NodeType.SAN_MOVE.equals( node.getNodeType()))
				{
					elementSequence.addChildren( fullTurn);
					fullTurn = new InternalNode( NodeType.FULL_TURN);
					noOfMoves = 0;
					fullTurn.addChildren( node);
				}
				else // Should never be executed, unexpected tokens must have been caught earlier.
				{
					if( elementSequence.getDegree() == 0)
						throw new PortableGameNotationException( "Unexpected " + node.getNodeType().name() + " token after " + endingTree.get( 0).getText());
					else
						throw new PortableGameNotationException( "Unexpected " + node.getNodeType().name() + " token after " + 
														elementSequence.getChildren( elementSequence.getDegree()-1).getText());
				}
			}
			
			// If the node just handled is of type SAN_MOVE, increase the no of moves inside the current turn
			if( NodeType.SAN_MOVE.equals( node.getNodeType()))
				++noOfMoves;
			
			++index;
			node = startingTree.get( index);
		}
		
		// If there is an ongoing turn with at least one move after reaching <game-termination>, add the current turn to the element sequence.
		if( noOfMoves > 0)
			elementSequence.addChildren( fullTurn);
		// If there is an ongoing turn without any moves after reaching <game-termination>, throw a portable game notation exception.
		else if( noOfMoves == 0 && fullTurn.getDegree() > 0)
		{
			if( elementSequence.getDegree() == 0)
				throw new PortableGameNotationException( "Empty turn after " + endingTree.get( 0).getText());
			else
				throw new PortableGameNotationException( "Empty turn after " + elementSequence.getChildren( elementSequence.getDegree()-1).getText());
		}
		
		// Add the element sequence to the new tree we are building.
		endingTree.add( elementSequence);
		
		// Add <game-termination> and possible trailing comments to the new tree we are building.
		while( index < startingTree.size())
		{
			node = startingTree.get( index);
			endingTree.add( node);
			++index;
		}
		
		// Return the new tree
		return endingTree;
	}
	
	/* Finalize the tree by creating a root node of type PGN_GAME
	 * At this point, our structure is <tag-section> <element-sequence> <game-termination> and possible trailing comments
	 */
	public InternalNode finalizeTree( ArrayList<Node> startingTree) throws PortableGameNotationException
	{
		// Create a root node and a move text section node
		InternalNode root = new InternalNode( NodeType.PGN_GAME);
		InternalNode moveTextSection = new InternalNode( NodeType.MOVETEXT_SECTION);
		
		// Add <element-sequence>, <game-termination> and possible trailing comments to the move text section
		for( int i = 1; i < startingTree.size(); i++)
			moveTextSection.addChildren( startingTree.get( i));

		// Add <tag-section> and move text section to the root node
		Node tagSection = startingTree.get(0); // <tag-section>
		root.addChildren( tagSection);
		root.addChildren( moveTextSection);

		// Return the root node
		return root;
	}
	
	// Find tags in the parse tree and generate a map containing tag name-value pairs.
	public HashMap<String,String> generateTagMap( InternalNode root)
	{
		HashMap<String,String> tagMap = new HashMap<String,String>();
		
		InternalNode tagSection = ((InternalNode)(root.getChildren( 0))); // As <PGN-game> ::= <tag-section> <movetext-section>, the first child of the root is the tag section
		InternalNode tagPair = null;
		InternalNode tagName = null;
		InternalNode tagValue = null;
		
		Node name = null;
		Node value = null;
		
		// Iterate over tag pair nodes, which are children of the tag section node
		for( int i = 0; i < tagSection.getDegree(); i++)
		{
			tagPair = ((InternalNode)(tagSection.getChildren( i)));
			tagName = ((InternalNode)(tagPair.getChildren( 1)));
			tagValue = ((InternalNode)(tagPair.getChildren( 2)));
			
			// Find tag name and tag value, and put them in the tag map we're building.
			name = tagName.getChildren( 0);
			value = tagValue.getChildren( 0);
			
			tagMap.put( name.getText(), value.getText());
		}
		
		return tagMap;
	}

	// Find SAN moves in the parse tree and generate a list containing moves and complementary move information.
	public ArrayList<Move> generateMoveList( InternalNode root) throws PortableGameNotationException
	{
		ArrayList<Move> moveList = new ArrayList<Move>();
		
		// As <PGN-game> ::= <tag-section> <movetext-section>, the second child of the root is the tag section
		InternalNode moveTextSection = ((InternalNode)(root.getChildren( 1)));
		
		// As <movetext-section> ::= <element-sequence> <game-termination>, the first child of the move text section is the element sequence
		InternalNode elementSequence = ((InternalNode)(moveTextSection.getChildren( 0)));
		
		InternalNode fullTurn = null;
		Node node = null;
		Move move = null;
		int plyNumber = 1;
		
		// Iterate over full turn nodes, which are children of the element sequence node
		for( int i = 0; i < elementSequence.getDegree(); i++)
		{
			fullTurn = ((InternalNode)(elementSequence.getChildren( i)));
			
			/* Iterate over the children of the current full turn nodes, the type of which must be one of the following: 
			 * SAN_MOVE, MOVE_NUMBER_INDICATION, NAG, RECURSIVE_VARIATION, BRACE_COMMENT, REST_OF_LINE_COMMENT
			 */
			for( int j = 0; j < fullTurn.getDegree(); j++)
			{
				node = fullTurn.getChildren( j);
				
				// If the child node is a SAN move node
				if( NodeType.SAN_MOVE.equals( node.getNodeType()))
				{
					// A SAN move node starts a new move. Add the current move to the move list we are building, and create a new move.
					if( move != null)
					{
						moveList.add( move);
						move = null;
					}
					
					/* If this move has an odd ply number (i.e. it is the first move of a turn), it belongs to the white side. 
					 * Create a move by the white side using the move text contained in the SAN_MOVE node.
					 */
					if( plyNumber % 2 == 1)
						move = parseMoveText( node.getText(), (plyNumber+1)/2, Color.WHITE);
					else // Otherwise, do the same for the black side.
						move = parseMoveText( node.getText(), (plyNumber+1)/2, Color.BLACK);
					
					++plyNumber;
				}
				// If the child node is a move number indication node, check its move number. If the move number isn't correct, throw a portable game notation exception.
				else if( NodeType.MOVE_NUMBER_INDICATION.equals( node.getNodeType()))
				{
					Node integerToken = ((InternalNode)node).getChildren( 0);
					try {
						int token = Integer.parseInt( integerToken.getText());
						
						if( token != (plyNumber+1)/2)
							throw new PortableGameNotationException( "Wrong move number at move number indication " + 
																	node.getText() + ", it should be " + (plyNumber+1)/2);
					} catch( NumberFormatException e) { // Should never be thrown
						throw new PortableGameNotationException( "Non-integer move number indication at " + fullTurn.getText());
					}
				}
				// If the child node is a NAG node, set the numerical annotation glyph of the current move.
				else if( NodeType.NAG.equals( node.getNodeType()))
				{
					try {
						move.setNumericalAnnotationGlyph( node.getText());
					} catch( NullPointerException npe) {
						throw new PortableGameNotationException( "NAG token before first move: " + node.getText());
					}
				}
				// If the child node is a recursive variation node, add it to the recursive variation list of the current move.
				else if( NodeType.RECURSIVE_VARIATION.equals( node.getNodeType()))
				{
					try {
						move.addRecursiveVariation( node.getText());
					} catch( NullPointerException npe) {
						throw new PortableGameNotationException( "Recursive variation before first move: " + node.getText());
					}
				}
				// The child node is either a brace comment or a rest of line comment node. Add it to the comment list of the current move.
				else
				{
					try {
						move.addComment( node.getText());
					} catch( NullPointerException npe) {
						throw new PortableGameNotationException( "Comment before first move: " + node.getText());
					}
				}
			}
		}
		
		// Add the last move of the element sequence to the move list we are building.
		if( move != null)
			moveList.add( move);
		
		return moveList;
	}

	// Find the game termination node in the parse tree and return its text as the game result.
	public String extractResult( InternalNode root)
	{
		String result = null;
		
		// As <PGN-game> ::= <tag-section> <movetext-section>, the second child of the root is the tag section
		InternalNode moveTextSection = ((InternalNode)(root.getChildren( 1)));
		
		// As <movetext-section> ::= <element-sequence> <game-termination>, the second child of the move text section is the game termination
		InternalNode gameTermination = ((InternalNode)(moveTextSection.getChildren( 1)));
		
		// The only child of the game termination is the asterisk/symbol token node containing the game termination string. Return its text.
		result = gameTermination.getChildren( 0).getText();
		return result;
	}

	// Find the possible comments after game termination node in the parse tree and generate a list containing them.
	public ArrayList<String> extractTrailingCommentsList( InternalNode root)
	{
		ArrayList<String> trailingCommentsList = new ArrayList<String>();

		// As <PGN-game> ::= <tag-section> <movetext-section>, the second child of the root is the tag section
		InternalNode moveTextSection = ((InternalNode)(root.getChildren( 1)));
		Node trailingComment = null;

		/* As <movetext-section> ::= <element-sequence> <game-termination>, the second child of the move text section is the game termination. 
		 * If the move text section has any more children, they must be comments; add them to the comment list we are building.
		 */
		for( int i = 2; i < moveTextSection.getDegree(); i++)
		{
			trailingComment = moveTextSection.getChildren( i);
			trailingCommentsList.add( trailingComment.getText());
		}
		
		return trailingCommentsList;
	}
	
	// Create a move from the given move textwith the given turn index and for the given player side.
	public static Move parseMoveText( String moveText, int turnIndex, Color playerSide) throws PortableGameNotationException
	{
		Move move = null;
		String textToBeProcessed = moveText;
		
		String nagString = null;

		char destinationFile = 0;
		char destinationRank = 0;
		char sourceFile = 0;
		char sourceRank = 0;
		char pieceLetter = 0;
		
		boolean isCapture = false;
		boolean isCheck = false;
		boolean isCheckMate = false;
		
		// Extract Numerical Annotation Glyph from move text and then remove it from the move text.
		Pattern nagPattern = Pattern.compile( NAG_REGEX);
		Matcher nagMatcher = nagPattern.matcher( textToBeProcessed);
		if( nagMatcher.find())
		{
			nagString = nagMatcher.group();
			textToBeProcessed = textToBeProcessed.replaceAll( NAG_REGEX, "");
		}
		
		/* Extract check/checkmate indication character from move text and then remove it from the move text. 
		 * If there exists a check/checkmate indication character in the move text, set the move as check/checkmate.
		 */
		Pattern checkingPattern = Pattern.compile( CHECKING_REGEX);
		Matcher checkingMatcher = checkingPattern.matcher( textToBeProcessed);
		if( checkingMatcher.find())
		{
			String checkingString = checkingMatcher.group();

			isCheck = checkingString.equals( Character.toString( CH_PLUS));
			isCheckMate = checkingString.equals( Character.toString( CH_HASH));
			
			textToBeProcessed = textToBeProcessed.replaceAll( CHECKING_REGEX, "");
		}
		
		// Handle castling moves. No need to determine source/destination squares.
		if( textToBeProcessed.matches( QUEENSIDE_CASTLING))
		{
			move = new CastlingMove( moveText.replaceAll( NAG_REGEX, ""), turnIndex, playerSide, false);
		}
		else if( textToBeProcessed.matches( KINGSIDE_CASTLING))
		{
			move = new CastlingMove( moveText.replaceAll( NAG_REGEX, ""), turnIndex, playerSide, true);
		}
		else // Handle non-castling moves.
		{
			// Handle moves with a pawn promotion.
			if( textToBeProcessed.matches( PROMOTION_REGEX))
			{
				// A move with a pawn promotion cannot be expressed with less then 4 characters
				if( textToBeProcessed.length() < 4)
					throw new PortableGameNotationException( "Syntax error on move " + moveText + 
															" at turn " + turnIndex);
				
				int index = textToBeProcessed.length()-1;
				
				char promotedPieceLetter = textToBeProcessed.charAt( index--); // Last character is the letter denoting the piece pawn has promoted to
				--index; // Skip equals sign
				
				destinationRank = textToBeProcessed.charAt( index--); // Third from last character is destination rank
				destinationFile = textToBeProcessed.charAt( index--); // Fourth from last character is destination file
				
				// If there are more than 4 characters in the move text, fifth from last character may be the capture symbol (x)
				if( index >= 0 && textToBeProcessed.charAt( index) == CAPTURE_SYMBOL)
				{
					isCapture = true;
					--index;
				}
				
				// Move text may include source rank or source file
				if( index >= 0 && Character.isDigit( textToBeProcessed.charAt( index)))
					sourceRank = textToBeProcessed.charAt( index--);
				if( index >= 0 && Character.isLowerCase( textToBeProcessed.charAt( index)))
					sourceFile = textToBeProcessed.charAt( index--);
				
				// The only other thing we can find in the move text is the letter denoting pawn, which isn't normally used.
				if( index == 0)
					pieceLetter = textToBeProcessed.charAt( index);
				else if( index == -1)
					pieceLetter = PAWN_SYMBOL;
				
				/* Create a promotion move with the given move text, turn index, player side and the PieceLetter denoting the piece pawn has promoted to, 
				 * which is obtained using the promoted piece letter extracted from the move text.
				 */
				move = new PromotionMove( moveText.replaceAll( NAG_REGEX, ""), turnIndex, getPieceLetter( promotedPieceLetter), playerSide);
				
				// Set the capture flag, destination file/rank, source file/rank (either of them might be null) and PieceLetter of the moving piece of the move.
				move.setCapture( isCapture);
				move.setDestinationFile( getFile( destinationFile));
				move.setDestinationRank( getRank( destinationRank));
				move.setSourceFile( getFile( sourceFile));
				move.setSourceRank( getRank( sourceRank));
				move.setPieceLetter( getPieceLetter( pieceLetter));
			}
			else // Handle moves without a pawn promotion.
			{
				// A move cannot be expressed with less then 2 characters
				if( textToBeProcessed.length() < 2)
					throw new PortableGameNotationException( "Syntax error on move " + moveText + 
							" at turn " + turnIndex);
				
				int index = textToBeProcessed.length()-1;
				
				destinationRank = textToBeProcessed.charAt( index--); // Last character is destination rank
				destinationFile = textToBeProcessed.charAt( index--); // Next to last character is destination file
				
				// If there are more than 2 characters in the move text, third from last character may be the capture symbol (x)
				if( index >= 0 && textToBeProcessed.charAt( index) == CAPTURE_SYMBOL)
				{
					isCapture = true;
					--index;
				}
				
				// Move text may include source rank or source file
				if( index >= 0 && Character.isDigit( textToBeProcessed.charAt( index)))
					sourceRank = textToBeProcessed.charAt( index--);
				if( index >= 0 && Character.isLowerCase( textToBeProcessed.charAt( index)))
					sourceFile = textToBeProcessed.charAt( index--);
				
				// The only other thing we can find in the move text is the letter denoting pawn, which isn't normally used.
				if( index == 0)
					pieceLetter = textToBeProcessed.charAt( index);
				else if( index == -1)
					pieceLetter = PAWN_SYMBOL;
				
				// Create a generic move with the given move text, turn index and player side
				move = new Move( moveText.replaceAll( NAG_REGEX, ""), turnIndex, playerSide);
				
				// Set the capture flag, destination file/rank, source file/rank (either of them might be null) and PieceLetter of the moving piece of the move.
				move.setCapture( isCapture);
				move.setDestinationFile( getFile( destinationFile));
				move.setDestinationRank( getRank( destinationRank));
				move.setSourceFile( getFile( sourceFile));
				move.setSourceRank( getRank( sourceRank));
				move.setPieceLetter( getPieceLetter( pieceLetter));
			}
		}
		
		// Set the check/checkmate flag and numerical annotation glyph of the move.
		move.setCheck( isCheck);
		move.setCheckMate( isCheckMate);
		if( nagString != null)
			move.setNumericalAnnotationGlyph( nagString);
		
		return move;
	}
	
	// Find and return the File corresponding to the given char representation.
	private static Move.File getFile( char ch)
	{
		Move.File file;
		switch( ch)
		{
			case 'a': file = Move.File.A;
						break;
			case 'b': file = Move.File.B;
						break;
			case 'c': file = Move.File.C;
						break;
			case 'd': file = Move.File.D;
						break;
			case 'e': file = Move.File.E;
						break;
			case 'f': file = Move.File.F;
						break;
			case 'g': file = Move.File.G;
						break;
			case 'h': file = Move.File.H;
						break;
			default: file = null;
						break;
		}
		return file;
	}
	
	// Find and return the Rank corresponding to the given char representation.
	private static Move.Rank getRank( char ch)
	{
		Move.Rank rank;
		switch( ch)
		{
			case '1': rank = Move.Rank._1;
						break;
			case '2': rank = Move.Rank._2;
						break;
			case '3': rank = Move.Rank._3;
						break;
			case '4': rank = Move.Rank._4;
						break;
			case '5': rank = Move.Rank._5;
						break;
			case '6': rank = Move.Rank._6;
						break;
			case '7': rank = Move.Rank._7;
						break;
			case '8': rank = Move.Rank._8;
						break;
			default: rank = null;
						break;
		}
		return rank;
	}

	// Find and return the PieceLetter corresponding to the given char representation.
	private static Move.PieceLetter getPieceLetter( char ch)
	{
		Move.PieceLetter pieceLetter;
		switch( ch)
		{
			case KING_SYMBOL: pieceLetter = Move.PieceLetter.K;
						break;
			case QUEEN_SYMBOL: pieceLetter = Move.PieceLetter.Q;
						break;
			case ROOK_SYMBOL: pieceLetter = Move.PieceLetter.R;
						break;
			case BISHOP_SYMBOL: pieceLetter = Move.PieceLetter.B;
						break;
			case KNIGHT_SYMBOL: pieceLetter = Move.PieceLetter.N;
						break;
			case PAWN_SYMBOL: pieceLetter = Move.PieceLetter.P;
						break;
			case 0: pieceLetter = Move.PieceLetter.P;
						break;
			default: pieceLetter = null;
						break;
		}
		return pieceLetter;
	}
}
