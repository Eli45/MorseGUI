import scala.swing._, MorseFunc._;

object UI
{
    //Original attempt at creating a generic UI which you could add whatever components you wished to via an array.
    //Decided to scrap it for a custom class due to the hard to read syntax when giving component parameters.
    class UI(Title:String, Contents:Array[scala.swing.Component]) 
    extends MainFrame
    {
        this.title = Title;
        
        this.contents = new BoxPanel(Orientation.Vertical)
        {
            Contents.foreach
            {
                contents += _;
            }
        }
        
        this.contents.foreach
        {
            listenTo(_);
        }
    }
    
    /**
     * USE:
        * CustomUI class which will contain all GUI elements and reactions for our main frame.
     * METHODS:
        * PRIVATE:
            * tryTranslate
                * USE:
                    * Attempts to translate the string provided in txtInput.
                    * If there is a failure it returns None and changes lblError to inform the user.
                * RETURNS:
                    * Either correctly translated text or None to signify an error.
     */
    class CustomUI()
    extends MainFrame
    {   
        private var MorseTranslator = new MorseReader();
        
        private var lblError        = new Label("");

        private def tryTranslate():Any =
        {
            lblError.text = "";
            try 
            {
                return MorseTranslator.translate(txtInput.text);
            }
            catch
            {
                case e:Exception => 
                {
                    lblError.text = e.getMessage();
                    return None;
                }
            }
        };
        
        
        private var btnSwitch = new Button("Switch to Morse->English")
        {
            this.tooltip = "Switch language you wish to translate to and from.";
            
            this.reactions += 
            {
                case scala.swing.event.ButtonClicked(_) =>
                {
                    MorseTranslator.switchTranslationMode();
                    
                    if (!MorseTranslator.getTranslationMode())  //Mode != Morse To English.
                    {
                        this.text = "Switch to English->Morse";
                    }
                    else
                    {
                        this.text = "Switch to Morse->English";
                    }
                    
                }
            };

        };
        
        private var txtOutput = new TextArea("", 4, 20)
        {
            this.editable = false;
            this.tooltip  = "Translated text appears here.";
            //this.preferredSize = new Dimension(100, 100);
        };
        
        private var txtInput = new TextArea("", 4, 20)
        {
            this.tooltip   = "What you want to translate.";
            this.enabled   = true;
            this.focusable = true;
            //this.preferredSize = new Dimension(100, 100);
            
            //TODO: Figure out a way to only trigger this if SHIFT and ENTER are pressed at (near) the same time.   
            //Until this, I have disabled the enter to confirm functionality.
            /*
            listenTo(keys);
            reactions += 
            {
                case scala.swing.event.KeyPressed(_, scala.swing.event.Key.Enter, _, _) =>
                {
                    txtOutput.text = "";
                    
                    var result  = tryTranslate();
                    if (result != None)
                    {
                        txtOutput.text = result.toString();
                    }
                }
            };
            */
            
        };
        
        private var btnConfirm = new Button("Confirm")
        {
            this.tooltip = "Translate inputted text.";
            this.reactions += 
            {
                case scala.swing.event.ButtonClicked(_) =>
                {
                    txtOutput.text = "";
                    
                    var result  = tryTranslate();
                    if (result != None)
                    {
                        txtOutput.text = result.toString();
                    }
                }
            };
        };
    
        private var btnCopyToClipboard = new Button("Copy to clipboard")
        {
            this.tooltip = "Copy output contents to clipboard.";
            this.reactions +=  {
                case scala.swing.event.ButtonClicked(_) =>
                {
                    GeneralFunc.copyToClipboard(txtOutput.text);
                }
            }
        };
            
        this.title         = "Eli Morse Translator";
        this.preferredSize = new Dimension(600, 300);
        this.contents      = new BoxPanel(Orientation.Vertical)
        {   
            this.contents += new FlowPanel(
                new Label("INPUT:"),
                txtInput
            );
            
            this.contents += new FlowPanel(
                btnSwitch,
                btnConfirm
            );
            
            this.contents += new FlowPanel(
                new Label("OUTPUT:"),
                txtOutput
            );
            
            this.contents += new FlowPanel(
                lblError
            );
      
            this.contents += new FlowPanel(
                btnCopyToClipboard  
            );  
        };
        
    }
    
}