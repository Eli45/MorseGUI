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
        
        private val TEXT_FIELD_ROW    = 7;
        private val TEXT_FIELD_COLUMN = 20;

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
        
        private var lblSwitch = new Label("Mode: English->Morse");
        
        private var btnSwitch = new Button("Switch translation mode")
        {
            this.tooltip = "Switch language you wish to translate to and from.\nCurrent mode: English->Morse";
            
            this.reactions += 
            {
                case scala.swing.event.ButtonClicked(_) =>
                {
                    MorseTranslator.switchTranslationMode();
                    
                    if (MorseTranslator.getTranslationMode())  //Mode == English to Morse
                    {
                    	this.tooltip 	= "Switch language you wish to translate to and from.\nCurrent mode: English->Morse";
                    	lblSwitch.text 	= "Mode: English->Morse";
                    }
                    else
                    {
                    	this.tooltip 	= "Switch language you wish to translate to and from.\nCurrent mode: Morse->English";
                    	lblSwitch.text 	= "Mode: Morse->English";
                    }
                    
                }
            };

        };
        
        private var txtOutput = new TextArea("", TEXT_FIELD_ROW, TEXT_FIELD_COLUMN)
        {
            this.editable = false;
            this.tooltip  = "Translated text appears here.";
            //this.preferredSize = new Dimension(100, 100);
        };
        
        private var txtInput = new TextArea("", TEXT_FIELD_ROW, TEXT_FIELD_COLUMN)
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
                    try
                    {
                    	GeneralFunc.copyToClipboard(txtOutput.text);
                    }
                    catch
                    {
                        case e:Exception =>
                        {
                            throw new Exception("Something very bad has occured. Please report this on the github repository. Exception details follow: " + e.getMessage());
                        }
                    }
                }
            }
        };
            
        this.title         = "Eli Morse Translator";
        this.preferredSize = new Dimension(750, 500);
        this.contents      = new BoxPanel(Orientation.Vertical)
        {   
            
            this.contents += new FlowPanel(new Label("<HTML><U>INPUT</U></HTML>"));
            this.contents += new FlowPanel(
                txtInput
            );
            
            this.contents += new FlowPanel(
            	lblSwitch
            );
            
            this.contents += new FlowPanel(
            	btnSwitch,
            	btnConfirm
            );
            
            this.contents += new FlowPanel(new Label("<HTML><U>OUTPUT</U></HTML>"))
            this.contents += new FlowPanel(
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