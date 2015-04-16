object MorseLanguageDetails
{
    
    val newLine = java.lang.System.getProperty("line.separator");
    val newLines = Array("\n", "\r\n", newLine);
    val EnglishToMorse = Map(
        "A" -> ".-",        "M" -> "--",
        "B" -> "-...",      "N" -> "-.",
        "C" -> "-.-.",      "O" -> "---",
        "D" -> "-..",       "P" -> ".--.",
        "E" -> ".",         "Q" -> "--.-",
        "F" -> "..-.",      "R" -> ".-.",
        "G" -> "--.",       "S" -> "...",
        "H" -> "....",      "T" -> "-",
        "I" -> "..",        "U" -> "..-",
        "J" -> ".---",      "V" -> "...-",
        "K" -> "-.-",       "X" -> ".--",
        "L" -> ".-..",      "Y" -> "-.--",
        "W" -> ".--",       "Z" -> "--..",
                        
        "1" -> ".----",     "6" -> "-....",
        "2" -> "..---",     "7" -> "--...",
        "3" -> "...--",     "8" -> "---..",
        "4" -> "....-",     "9" -> "----.",
        "5" -> ".....",     "0" -> "-----",
        
        "." -> ".-.-.-",    "," -> "--..--",
        "?" -> "..--..",    ":" -> "---...",
        ";" -> "-.-.-",     "/" -> "-..-.",
        "-" -> "-....-",    "'" -> ".----.",
        "(" -> "-.--.",     "_" -> "..__._",    //Underscore.
        ")" -> "-.--.-",    newLine -> newLine,
        "\n"-> "\n",        "\r\n"  -> "\r\n",
        "!" -> "---.",      ""      -> "",      //Empty string.
        "\"" -> ".-..-.",   "\\"    -> "-..-."
    );
    
    val MorseToEnglish = Map(
        ".-"    -> "A",     "--"    -> "M",
        "-..."  -> "B",     "-."    -> "N",
        "-.-."  -> "C",     "---"   -> "O",
        "-.."   -> "D",     ".--."  -> "P",
        "."     -> "E",     "--.-"  -> "Q",
        "..-."  -> "F",     ".-."   -> "R",
        "--."   -> "G",     "..."   -> "S",
        "...."  -> "H",     "-"     -> "T",
        ".."    -> "I",     "..-"   -> "U",
        ".---"  -> "J",     "...-"  -> "V",
        "-.-"   -> "K",     ".--"   -> "X",
        ".-.."  -> "L",     "-.--"  -> "Y",
        ".--"   -> "W",     "--.."  -> "Z",
                        
        ".----" -> "1",     "-...." -> "6",
        "..---" -> "2",     "--..." -> "7",
        "...--" -> "3",     "---.." -> "8",
        "....-" -> "4",     "----." -> "9",
        "....." -> "5",     "-----" -> "0",
        
        ".-.-.-" -> ".",    "--..--" -> ",",
        "..--.." -> "?",    "---..." -> ":",
        "-.-.-"  -> ";",    "-..-."  -> "/",
        "-....-" -> "-",    ".----." -> "'",
        "-.--."  -> "(",    "..__._" -> "_",    //Underscore.
        "-.--.-" -> ")",    "/"      -> " ",
        newLine  -> newLine,
        "\n"     -> "\n",   "\r\n"   -> "\r\n",
        "---."   -> "!",    ""       -> "",     //Empty string.
        ".-..-." -> "\"",   "-..-."  -> "/"
    );
    
    
}