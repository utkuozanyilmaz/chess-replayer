/**
 * Utility class that provides methods for reading the image files from the file system.
 */

package chessreplayer.view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import chessreplayer.controller.ChessPropertiesReader;

public class ChessImage
{
	// Images used by the user interface of chess replayer
	private static BufferedImage fileAImage = null;
	private static BufferedImage fileBImage = null;
	private static BufferedImage fileCImage = null;
	private static BufferedImage fileDImage = null;
	private static BufferedImage fileEImage = null;
	private static BufferedImage fileFImage = null;
	private static BufferedImage fileGImage = null;
	private static BufferedImage fileHImage = null;
	private static BufferedImage rank1Image = null;
	private static BufferedImage rank2Image = null;
	private static BufferedImage rank3Image = null;
	private static BufferedImage rank4Image = null;
	private static BufferedImage rank5Image = null;
	private static BufferedImage rank6Image = null;
	private static BufferedImage rank7Image = null;
	private static BufferedImage rank8Image = null;
	private static BufferedImage emptySquareImage = null;
	private static BufferedImage blackBackgroundImage = null;
	private static BufferedImage whiteBackgroundImage = null;
	private static BufferedImage destinationBackgroundImage = null;
	private static BufferedImage sourceBackgroundImage = null;
	private static BufferedImage blackPawnImage = null;
	private static BufferedImage blackKnightImage = null;
	private static BufferedImage blackBishopImage = null;
	private static BufferedImage blackRookImage = null;
	private static BufferedImage blackQueenImage = null;
	private static BufferedImage blackKingImage = null;
	private static BufferedImage whitePawnImage = null;
	private static BufferedImage whiteKnightImage = null;
	private static BufferedImage whiteBishopImage = null;
	private static BufferedImage whiteRookImage = null;
	private static BufferedImage whiteQueenImage = null;
	private static BufferedImage whiteKingImage = null;
	private static BufferedImage playOneMoveButtonImage = null;
	private static BufferedImage takeBackOneMoveButtonImage = null;
	private static BufferedImage startAutoPlayButtonImage = null;
	private static BufferedImage stopAutoPlayButtonImage = null;

	private static final String newLine = System.getProperty( "line.separator");
	
	// Methods for getting the images, once they are loaded from the file system.
	public static BufferedImage getFileAImage()
	{
		return fileAImage;
	}

	public static BufferedImage getFileBImage()
	{
		return fileBImage;
	}

	public static BufferedImage getFileCImage()
	{
		return fileCImage;
	}

	public static BufferedImage getFileDImage()
	{
		return fileDImage;
	}

	public static BufferedImage getFileEImage()
	{
		return fileEImage;
	}

	public static BufferedImage getFileFImage()
	{
		return fileFImage;
	}

	public static BufferedImage getFileGImage()
	{
		return fileGImage;
	}

	public static BufferedImage getFileHImage()
	{
		return fileHImage;
	}

	public static BufferedImage getRank1Image()
	{
		return rank1Image;
	}

	public static BufferedImage getRank2Image()
	{
		return rank2Image;
	}

	public static BufferedImage getRank3Image()
	{
		return rank3Image;
	}

	public static BufferedImage getRank4Image()
	{
		return rank4Image;
	}

	public static BufferedImage getRank5Image()
	{
		return rank5Image;
	}

	public static BufferedImage getRank6Image()
	{
		return rank6Image;
	}

	public static BufferedImage getRank7Image()
	{
		return rank7Image;
	}

	public static BufferedImage getRank8Image()
	{
		return rank8Image;
	}

	public static BufferedImage getEmptySquareImage()
	{
		return emptySquareImage;
	}

	public static BufferedImage getBlackBackgroundImage()
	{
		return blackBackgroundImage;
	}

	public static BufferedImage getWhiteBackgroundImage()
	{
		return whiteBackgroundImage;
	}

	public static BufferedImage getSourceBackgroundImage()
	{
		return sourceBackgroundImage;
	}

	public static BufferedImage getDestinationBackgroundImage()
	{
		return destinationBackgroundImage;
	}

	public static BufferedImage getBlackPawnImage()
	{
		return blackPawnImage;
	}

	public static BufferedImage getBlackKnightImage()
	{
		return blackKnightImage;
	}

	public static BufferedImage getBlackBishopImage()
	{
		return blackBishopImage;
	}

	public static BufferedImage getBlackRookImage()
	{
		return blackRookImage;
	}

	public static BufferedImage getBlackQueenImage()
	{
		return blackQueenImage;
	}

	public static BufferedImage getBlackKingImage()
	{
		return blackKingImage;
	}

	public static BufferedImage getWhitePawnImage()
	{
		return whitePawnImage;
	}

	public static BufferedImage getWhiteKnightImage()
	{
		return whiteKnightImage;
	}

	public static BufferedImage getWhiteBishopImage()
	{
		return whiteBishopImage;
	}

	public static BufferedImage getWhiteRookImage()
	{
		return whiteRookImage;
	}

	public static BufferedImage getWhiteQueenImage()
	{
		return whiteQueenImage;
	}

	public static BufferedImage getWhiteKingImage()
	{
		return whiteKingImage;
	}

	public static BufferedImage getPlayOneMoveButtonImage()
	{
		return playOneMoveButtonImage;
	}

	public static BufferedImage getTakeBackOneMoveButtonImage()
	{
		return takeBackOneMoveButtonImage;
	}

	public static BufferedImage getStartAutoPlayButtonImage()
	{
		return startAutoPlayButtonImage;
	}

	public static BufferedImage getStopAutoPlayButtonImage()
	{
		return stopAutoPlayButtonImage;
	}
	
	/* Load all the necessary images from the file system, using file paths from the given chess properties reader.
	 * If the image cannot be found, put the path of the file into an array list.
	 */
	public static void loadImages( ChessPropertiesReader properties) throws ChessImageException
	{
		ArrayList<String> notFoundList = new ArrayList<String>();
		
		rank1Image = loadImage( properties.getRank1ImageFilePath(), notFoundList);
		rank2Image = loadImage( properties.getRank2ImageFilePath(), notFoundList);
		rank3Image = loadImage( properties.getRank3ImageFilePath(), notFoundList);
		rank4Image = loadImage( properties.getRank4ImageFilePath(), notFoundList);
		rank5Image = loadImage( properties.getRank5ImageFilePath(), notFoundList);
		rank6Image = loadImage( properties.getRank6ImageFilePath(), notFoundList);
		rank7Image = loadImage( properties.getRank7ImageFilePath(), notFoundList);
		rank8Image = loadImage( properties.getRank8ImageFilePath(), notFoundList);
		
		fileAImage = loadImage( properties.getFileAImageFilePath(), notFoundList);
		fileBImage = loadImage( properties.getFileBImageFilePath(), notFoundList);
		fileCImage = loadImage( properties.getFileCImageFilePath(), notFoundList);
		fileDImage = loadImage( properties.getFileDImageFilePath(), notFoundList);
		fileEImage = loadImage( properties.getFileEImageFilePath(), notFoundList);
		fileFImage = loadImage( properties.getFileFImageFilePath(), notFoundList);
		fileGImage = loadImage( properties.getFileGImageFilePath(), notFoundList);
		fileHImage = loadImage( properties.getFileHImageFilePath(), notFoundList);
		
		emptySquareImage = loadImage( properties.getEmptySquareImageFilePath(), notFoundList);
		blackBackgroundImage = loadImage( properties.getBlackBackgroundImageFilePath(), notFoundList);
		whiteBackgroundImage = loadImage( properties.getWhiteBackgroundImageFilePath(), notFoundList);
		destinationBackgroundImage = loadImage( properties.getDestinationBackgroundImageFilePath(), notFoundList);
		sourceBackgroundImage = loadImage( properties.getSourceBackgroundImageFilePath(), notFoundList);
		
		blackPawnImage = loadImage( properties.getBlackPawnImageFilePath(), notFoundList);
		blackKnightImage = loadImage( properties.getBlackKnightImageFilePath(), notFoundList);
		blackBishopImage = loadImage( properties.getBlackBishopImageFilePath(), notFoundList);
		blackRookImage = loadImage( properties.getBlackRookImageFilePath(), notFoundList);
		blackQueenImage = loadImage( properties.getBlackQueenImageFilePath(), notFoundList);
		blackKingImage = loadImage( properties.getBlackKingImageFilePath(), notFoundList);
		
		whitePawnImage = loadImage( properties.getWhitePawnImageFilePath(), notFoundList);
		whiteKnightImage = loadImage( properties.getWhiteKnightImageFilePath(), notFoundList);
		whiteBishopImage = loadImage( properties.getWhiteBishopImageFilePath(), notFoundList);
		whiteRookImage = loadImage( properties.getWhiteRookImageFilePath(), notFoundList);
		whiteQueenImage = loadImage( properties.getWhiteQueenImageFilePath(), notFoundList);
		whiteKingImage = loadImage( properties.getWhiteKingImageFilePath(), notFoundList);
		
		playOneMoveButtonImage = loadImage( properties.getPlayOneMoveButtonImageFilePath(), notFoundList);
		takeBackOneMoveButtonImage = loadImage( properties.getTakeBackOneMoveButtonImageFilePath(), notFoundList);
		startAutoPlayButtonImage = loadImage( properties.getStartAutoPlayButtonImageFilePath(), notFoundList);
		stopAutoPlayButtonImage = loadImage( properties.getStopAutoPlayButtonImageFilePath(), notFoundList);
		
		/* If the array list containing paths of image files that cannot be read from the file system is not empty, 
		 * throw a chess image exception. The message of the exception includes the paths of the files in the array list, 
		 * and says they cannot be read from the file system, the images should be provided or the corresponding 
		 * image file paths should be corrected from the settings, and the application should be restarted.
		 */
		if( !notFoundList.isEmpty())
		{
			String message =  notFoundList.size() + " errors occured while trying to read the images below from the file system." 
									+ newLine + "Please provide the images or correct the corresponding image file paths " 
									+ newLine + "from the settings, and restart the application." 
									+ newLine + "The images are: ";
			for( String s : notFoundList)
				message += newLine + s;
			
			throw new ChessImageException( message);
		}
	}

	/* Load an image from the file system, using the given path. If an IO exception occurs 
	 * when trying to read the image file, put the path of the file into the given array list.
	 */
	private static BufferedImage loadImage( String path, ArrayList<String> notFoundList)
	{
		BufferedImage bufferedImage = null;
		try {
			bufferedImage = ImageIO.read( new File( path));
		} catch( IOException e){
			notFoundList.add( path);
		}
		
		return bufferedImage;
	}
}
