object ImageAssetGetter
{
	import javax.imageio.ImageIO;
	import java.awt.image.BufferedImage;
	import java.io.File;
	
    class ImageAssetGetter(folder:File)
    {
    	///CONSTRUCTOR
        val ASSET_FOLDER 							= folder.getAbsolutePath();
        val VALID_IMAGE_EXTENSIONS:Array[String] 	= Array(".png", ".jpg", ".gif");
        private var ImageAssets:Array[File] 		= Array();
        
        
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
        
        private def getFileExtension(file:File):String =
        {
            var name:String = file.getName();
            var finalIndex  = name.lastIndexOf(".");
            
            if (finalIndex == -1) 
                return "";
            
            return name.substring(finalIndex);
        }
        
        //returns FileName -> (Width, Height)
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