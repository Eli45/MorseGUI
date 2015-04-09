object GeneralFunc
{
	/**
	 * USE:
	 	* Translates a char array to an array of 1 length strings.
	 * RETURNS:
	 	* An array of 1 length strings.
	 */
	def CharArrayToStringArray(charArr:Array[Char]):Array[String] =
	{
		var charStrings:Array[String] = Array();
		
		for (replace <- 0 to charArr.length - 1)
		{
			charStrings = charStrings :+ charArr(replace).toString();
		}
		
		return charStrings;
	}
	
	
	/**
	 * USE:
	 	* Function will take an array of strings and create a singular string by adding each element to a string.
	 	* This is used when we are forming a word from our array of individual string characters.
	 	* Only required since I decided to use an array of single length strings instead of chars due to 
	 	* the ease which they could be replaced by the >1 length of their corresponding Morse translations.
	 * RETURNS:
	 	* A string with each array element added one after the other.
	 	* OR
	 	* A string with each array element interpolated with spaces.
	 */
	def ArrayStringToString(stringArr:Array[String], spaceString:Boolean = false):String =
	{
		var RET:String = "";
		
		for (i <- 0 to stringArr.length - 1)
		{
			RET += stringArr(i);
			if (spaceString)
			{
				if (i != stringArr.length - 1)
				{
					RET += " "; 
				}
			}
		}
		
		return RET;
	}
	
	/**
	 * USE:
	 	* Function serves the same purpose as ArrayStringToString except we use this on Morse->English.
	 	* Because of we remove the \n's after each line when the line gets trimmed.
	 * RETURNS:
	 	* A string with each array element interpolated with newlines after each element.
	 */
	def ArrayStringToStringInterpolateNewLines(stringArr:Array[String]):String =
	{
		var RET:String = "";
		
		for (i <- 0 to stringArr.length - 1)
		{
			RET += stringArr(i) + "\n";
		}
		
		return RET;
	}
	
	/**
	 * USE:
	 	* Copies selected string to the users clipboard.
	 */
	def copyToClipboard(string:String)	
	{	
		val clipboard = java.awt.Toolkit.getDefaultToolkit.getSystemClipboard;
		var selection = new java.awt.datatransfer.StringSelection(string);
		clipboard.setContents(selection, selection)
	}

}