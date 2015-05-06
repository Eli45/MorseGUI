package main.scala;

object UIKeyListener 
{

    /**
     * KeyListener class which allows pressed keys to be kept in a timed array.
     * METHODS:
        * addNewKey
            * USE:
                * PARAMETERS:
                    * key:java.awt.event.KeyEvent => key you wish to add to the class' internal list.
                * The time of the button press will be automatically logged along with the key.

        * getKeysPressedInLastSecond
            * USE:
                * Returns all keys pressed in the last second.
            * RETURNS:
                * An array of keys which have been pressed in the last 1 second.
     */
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

        private def updateTime() =
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