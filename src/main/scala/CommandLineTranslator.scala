/**                                         NOTE
    The command line option included with this program was only used to test the program in it's early phasees.
    There is no exception checking and will CRASH if you do something incorrectly.
    Do not use this mode unless you know what you're doing. You will only be frusturated.
*/
package main.scala;

import MorseFunc._;

object CommandLineTranslator    
{
    
    def help() =
    {
        println("Commands:");
        println("\t[1]help: displays this menu.\n\t[2]translate morse to english(alt=mte): prompts for a phrase to translate from morse to english."
                + "\n\t[3]translate english to morse(alt=etm): prompts for a phrase to translate from english to morse."
                + "\n\t[4]translate: prompts for type of translation and then prompts for a string to translate."
                + "\n\t[5]quit: terminates program"
        );
    }
    
    def transMtoE() =
    {
        val mr = new MorseFunc.MorseReader();
        println("Enter phrase to translate.");
        println(mr.translate(readLine(), "--mte"));
    }
    
    def transEtoM() =
    {
        val mr = new MorseFunc.MorseReader();
        println("Enter phrase to translate.");
        println(mr.translate(readLine(), "--etm"));
    }
    
    def getTranslateType() =
    {
        val in = readLine("1: Translate to Morse\n2: Translate to English");
        in match
        {
            case "1" => 
            {
                transEtoM();
            }
                
            case "2" =>
            {
                transMtoE();
            }
        }
    }
    
    def quit() =
    {
        sys.exit(0);
    }
    
    def ListenToInputs() = 
    {
        val commands:Array[String] = Array(
            "none",
            "help",
            "translate morse to english",
            "mte",
            "translate english to morse",
            "etm",
            "translate",
            "quit"
        );
        
        printCommands();
        while (true)
        {
            val input = readLine().toLowerCase().trim();
            
            if (commands.contains(input))
            {
                input match 
                {
                    case "help"                         =>  { help(); };
                    case "translate morse to english"   =>  { transMtoE(); };
                    case "mte"                          =>  { transMtoE(); };
                    case "translate english to morse"   =>  { transEtoM(); };
                    case "etm"                          =>  { transEtoM(); };
                    case "translate"                    =>  { getTranslateType(); };
                    case "quit"                         =>  { quit(); };
                }
            }
            else if (input.split(' ')(0).equals("help"))    //edge case if user inputs "help command"
            {
                if (input.split(' ').length < 2) throw new IllegalArgumentException("'help command' parameters incorrectly initiated.");
                val cmd = input.split(' ')(1);
                
                cmd match
                {
                    case "help" =>
                    {
                        println("911 has been called and the authorities have been notified. Help is on the way.");
                    };
                    case "mte" =>
                    {
                        println("Prompts for a phrase to translate from Morse to English.");
                    };
                    case "etm" =>
                    {
                        println("Prompts for a phrase to translate from English to Morse.");
                    };
                    case "translate" =>
                    {
                        println("Prompts for what you want to translate to, then prompts for a phrase.");
                    };
                    case "quit" =>
                    {
                        println("Terminates program");
                    };
                }
            }
            else
            {
                input match
                {
                    case "1" => { help(); };
                    case "2" => { transMtoE(); };
                    case "3" => { transEtoM(); };
                    case "4" => { getTranslateType(); };
                    case "5" => { quit(); };
                    case default => {};
                }
            }
        }
    }
    
    def printCommands() =
    {
        println("1: help");
        println("2: mte");
        println("3: etm");
        println("4: translate");
        println("5: quit");
    }
    
}