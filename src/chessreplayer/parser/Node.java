/**
 * The input file containing game data is handled as a parse tree. This interface declares the methods every node of the parse tree should have, 
 * and the domain of node types as an enum. The methods are about the node's type and text.
 */

package chessreplayer.parser;

public interface Node
{
	public enum NodeType { PGN_GAME, TAG_SECTION, MOVETEXT_SECTION, TAG_PAIR, ELEMENT_SEQUENCE, GAME_TERMINATION, 
							TAG_NAME, TAG_VALUE, RECURSIVE_VARIATION, MOVE_NUMBER_INDICATION, SAN_MOVE, FULL_TURN, 
							LEFT_BRACKET, RIGHT_BRACKET, LEFT_PARENTHESIS, RIGHT_PARENTHESIS, LEFT_CHEVRON, RIGHT_CHEVRON, 
							PERIOD, ASTERISK, NAG, INTEGER_TOKEN, STRING_TOKEN, SYMBOL_TOKEN, BRACE_COMMENT, REST_OF_LINE_COMMENT};
	
	public NodeType getNodeType();
	public String getText();
	public void print( String prefix);
}
