/**
 * The input file containing game data is handled as a parse tree. Its leaf (terminal) nodes are modeled by this class. Includes standard node methods.
 */

package chessreplayer.parser;

public class LeafNode implements Node
{
	private final NodeType nodeType;
	private final String leafNodeText;
	
	public LeafNode( NodeType nodeType, String leafNodeText)
	{
		this.nodeType = nodeType;
		this.leafNodeText = leafNodeText;
	}
	
	@Override
	public NodeType getNodeType()
	{
		return nodeType;
	}
	
	@Override
	public String getText()
	{
		return leafNodeText;
	}
	
	@Override
	public void print( String prefix)
	{
		System.out.println( prefix + nodeType + "-" + leafNodeText);
	}
}
