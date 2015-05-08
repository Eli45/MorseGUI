package main.scala;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

object ImageAssetGetter
{
    /**
     * ImageAssetGetter which will return all image files and their dimensions in a given folder.
     * METHODS:
        * getImageHeights:
            * USE:
                * Used to get a map of images and their respective dimensions.
            * RETURNS:
                * A mutable map containing multiple string -> Tuple[Int, Int] pairs.
                * String denotes image name
                * Tuple format is (width, height).
     */
    class ImageAssetGetter(folder:File)
    {
        ///CONSTRUCTOR
        val ASSET_FOLDER                            = folder.getAbsolutePath();
        val VALID_IMAGE_EXTENSIONS:Array[String]    = Array(".png", ".jpg", ".gif");
        private var ImageAssets:Array[File]         = Array();
        
        
        for (file <- folder.listFiles())
        {
            if (!file.isDirectory())
            {
                if (this.VALID_IMAGE_EXTENSIONS.contains(this.getFileExtension(file)))
                {
                    ImageAssets = ImageAssets :+ file;
                }
            }
        }      
        ///CONSTRUCTOR_END
        
        //Returns the file extension including the ".".
        private def getFileExtension(file:File):String =
        {
            val name:String = file.getName();
            val finalIndex  = name.lastIndexOf(".");
            
            if (finalIndex == -1) 
                return "";
            
            return name.substring(finalIndex);
        }
        
        //returns String FileName -> Tuple[Int, Int](Width, Height)
        def getImageHeights():scala.collection.mutable.Map[String, (Int, Int)] =
        {
            var mapOfImages = scala.collection.mutable.Map[String, (Int, Int)]();
            
            for (image <- this.ImageAssets)
            {
                val bimg:BufferedImage = ImageIO.read(image);               
                val width  = bimg.getWidth();
                val height = bimg.getHeight();
                mapOfImages += (image.getName() -> (width, height))
                
            }
            
            return mapOfImages;
        }
    }
    
}