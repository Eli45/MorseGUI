package main.scala
import MorseFunc._;

object UnitTester
{

    class UnitTester()
    {
     
        def testMap(m: Map[String, String], TransToMorse:Boolean):String =
        {
            var RESULTS = "";
            val mr = new MorseReader();
            var mode = "";
            
            if (TransToMorse)
                mode = "--etm"
            else
                mode = "--mte"
            
            for ((key, value) <- m)
            {
                val KEY_IN  = key.trim().stripMargin;
                val VAL_OUT = value.trim().stripMargin;
                var output:String = "";

                try
                {
                    output = mr.translate(KEY_IN, mode);
                }
                catch
                {
                    case e:Exception => { output = e.getMessage() };
                }         
            
                if (VAL_OUT.toLowerCase().equals(output.toLowerCase()))
                    output = "PASSED: " + output;
                else
                {
                    //import scala.reflect.runtime.universe._
                    //println(
                    //    Literal(Constant(VAL_OUT)).toString + "\n",
                    //    Literal(Constant(output)).toString
                    //)
                    output = "FAILED: " + output;
                }
                
                RESULTS += output + "\n\n"
            }
            
            return RESULTS;
        }
        
        def test()
        {
            //This is so ugly I'm actually crying.
            var unitTestsEnglishToMorse = Map(
                "rekt-9"                -> ".-. . -.- - -....- ----.",      
                "*(#mcx 3)Ps; ;."       -> "ERROR: Unknown symbols:[* #]",    
                "hey_haha@gmail.com"    -> "ERROR: Unknown symbols:[@]",   
                "this worked right?"    -> "- .... .. ... / .-- --- .-. -.- . -.. / .-. .. --. .... - ..--..",
                "Eli Furland"           -> ". .-.. .. / ..-. ..- .-. .-.. .- -. -..",
                
                """Multiple Lines Should Work!
                |Right?
                |They Do Right?
                """ 
                ->
                """-- ..- .-.. - .. .--. .-.. . / .-.. .. -. . ... / ... .... --- ..- .-.. -.. / .-- --- .-. -.- ---.
                |.-. .. --. .... - ..--..
                |- .... . -.-- / -.. --- / .-. .. --. .... - ..--..
                """
            );
            
            var unitTestsMorseToEnglish = Map(
                
                "-- --- .-. ... . / - --- / . -. --. .-.. .. ... .... / .. ... / .- .-.. ... --- / --. --- --- -.." -> "MORSE TO ENGLISH IS ALSO GOOD",
                "asdfasdfasdf blah" -> "ERROR: Unknown symbols:[asdfasdfasdf blah]",
                
                """-- ..- .-.. - .. .-.. .. -. . ...
                .- .-. .
                -.-. --- --- .-.."""
                ->
                """MULTILINES
                |ARE
                |COOL
                """
            );
           
            val outputMorse   = this.testMap(unitTestsEnglishToMorse, true);
            val outputEnglish = this.testMap(unitTestsMorseToEnglish, false);
            
            val total_results = "[Mode: English To Morse]\n\n" + outputMorse + "\n\n[Mode: Morse To English]\n\n" + outputEnglish;
    
            import java.io.FileWriter;
            import java.io.File;
            
            val fw = new FileWriter(new File("unit_tests.log"));
            try 
            {
                fw.write(total_results);
            }
            catch
            {
                case e:Exception => { /*Something went wrong */ };
            }
            finally
            {
                fw.close();
            }
            
        }
        
    }
    
}