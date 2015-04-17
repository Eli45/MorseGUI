object UIKeyListener 
{

    class CustomKeyListener()
    {
        private var keysPressedInLastSecond:Map[java.awt.event.KeyEvent, Long] = Map();
        private var internalTime = java.lang.System.currentTimeMillis();

        def addNewKey(key:java.awt.event.KeyEvent) =
        {
            if (!this.customContains(key.getKeyCode()))
            {
            	this.keysPressedInLastSecond += (key -> java.lang.System.currentTimeMillis());
            }
        }

        private def customContains(keyCode:Int):Boolean = 
        {
        	for ((key, value) <- this.keysPressedInLastSecond)
        	{
        	    if (key.getKeyCode() == keyCode) return true;
        	}
        	return false;  
        }
        
        private def removeKey(key:java.awt.event.KeyEvent) =
        {
            this.keysPressedInLastSecond = this.keysPressedInLastSecond - key;
        }

        def updateTime() =
        {
            this.internalTime = java.lang.System.currentTimeMillis();

            val kr = this.keysPressedInLastSecond;
            for ((curKey, curLong) <- kr)
            {
                if (this.internalTime - 1000 > curLong)
                {
                    this.removeKey(curKey)
                }
            }
            
        }

        def getKeysPressedInLastSecond():Array[java.awt.event.KeyEvent] =
        {
            this.updateTime();
            
            var RET:Array[java.awt.event.KeyEvent] = Array();
            for ((key, value) <- this.keysPressedInLastSecond)
            {
                RET = RET :+ key;
            }     
            
            return RET;
        }

    }
    
}