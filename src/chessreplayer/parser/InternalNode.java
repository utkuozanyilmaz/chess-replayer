/**
 * The input file containing game data is handled as a parse tree. Its internal (non-terminal) nodes are modeled by this class. 
 * Includes standard node methods and methods that manage child nodes.
 */

package chessreplayer.parser;

import java.util.ArrayList;

public class InternalNode implements Node
{
	private final NodeType nodeType;
	private ArrayList<Node> children = new ArrayList<Node>();
	
	public InternalNode( NodeType nodeType)
	{
		this.nodeType = nodeType;
	}
	
	@Override
	public NodeType getNodeType()
	{
		return nodeType;
	}
	
	@Override
	public String getText()
	{
		String text = "";
		
		for( Node node : children)
			text += node.getText();
		
		return text;
	}
	
	@Override
	public void print( String prefix)
	{
		System.out.println( prefix + nodeType);
		for( Node node : children)
			node.print( prefix + "\t");
	}
	
	// Returns the degree (number of children) of this node
	public int getDegree()
	{
		return children.size();
	}
	
	public void addChildren( Node node)
	{
		children.add( node);
	}
	
	public Node getChildren( int index)
	{
		try {
			return children.get( index);
		} catch( IndexOutOfBoundsException e) {
			return null;
		}
	}
}
