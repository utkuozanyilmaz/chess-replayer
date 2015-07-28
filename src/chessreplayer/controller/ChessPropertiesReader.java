/**
 * This interface is used to expose only the read methods of ChessProperties.
 */

package chessreplayer.controller;

public interface ChessPropertiesReader
{
	public int getTurnTime();
	public String getFrameTitle();
	public String getWhiteWinsText();
	public String getBlackWinsText();
	public String getDrawText();
	public String getFileAImageFilePath();
	public String getFileBImageFilePath();
	public String getFileCImageFilePath();
	public String getFileDImageFilePath();
	public String getFileEImageFilePath();
	public String getFileFImageFilePath();
	public String getFileGImageFilePath();
	public String getFileHImageFilePath();
	public String getRank1ImageFilePath();
	public String getRank2ImageFilePath();
	public String getRank3ImageFilePath();
	public String getRank4ImageFilePath();
	public String getRank5ImageFilePath();
	public String getRank6ImageFilePath();
	public String getRank7ImageFilePath();
	public String getRank8ImageFilePath();
	public String getEmptySquareImageFilePath();
	public String getBlackBackgroundImageFilePath();
	public String getWhiteBackgroundImageFilePath();
	public String getDestinationBackgroundImageFilePath();
	public String getSourceBackgroundImageFilePath();
	public String getBlackPawnImageFilePath();
	public String getBlackKnightImageFilePath();
	public String getBlackBishopImageFilePath();
	public String getBlackRookImageFilePath();
	public String getBlackQueenImageFilePath();
	public String getBlackKingImageFilePath();
	public String getWhitePawnImageFilePath();
	public String getWhiteKnightImageFilePath();
	public String getWhiteBishopImageFilePath();
	public String getWhiteRookImageFilePath();
	public String getWhiteQueenImageFilePath();
	public String getWhiteKingImageFilePath();
	public String getPlayOneMoveButtonImageFilePath();
	public String getTakeBackOneMoveButtonImageFilePath();
	public String getStartAutoPlayButtonImageFilePath();
	public String getStopAutoPlayButtonImageFilePath();
}
