package main.scala;

import MorseFunc._;
import UIKeyListener._;
import ImageAssetGetter._;

import scala.swing._;

import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import java.awt.Color;

object UI
{   
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
                    * Either correctly translated text as a String or None to signify an error.
                    
            * setButtonSize
                * USE:
                    * Forces the specified button to a specified dimension.

            * getImageDimension
                * USE:
                    * Attempts to return a new Dimension object of the specified image in the ./resources/arts folder.
                * RETURNS:
                    * Returns a new Dimension object containing the width and height of the given image.
                    * Returns Dimension(0, 0) if the image does not exist. 
     */
    class CustomUI()
    extends MainFrame
    {   
        private val MorseTranslator     = new MorseReader();
        private val KeyListener         = new CustomKeyListener();

        private val lblError            = new Label("");
        
        private val TEXT_FIELD_ROW      = 7;
        private val TEXT_FIELD_COLUMN   = 20;
        
        //get heights of our icons
        private val FILE_SEP     = java.lang.System.getProperty("file.separator");
        private val ASSET_FOLDER = "." + FILE_SEP + "src" + FILE_SEP + "main" + FILE_SEP + "resources" + FILE_SEP + "art";
        var imgGetter            = new ImageAssetGetter(new java.io.File(this.ASSET_FOLDER));
        var imageDimensions      = imgGetter.getImageHeights();

        private def tryTranslate():Any  =
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
        
        private def setButtonSize(btn:Button, dim:Dimension) =
        {
            btn.minimumSize   = dim;
            btn.maximumSize   = dim;
            btn.preferredSize = dim;
             
        }
        
        private def getImageDimension(imgName:String):Dimension =
        {
            try
            {
                val imgDim = this.imageDimensions(imgName);
                return new Dimension(imgDim._1, imgDim._2);
            }
            catch
            {
                case e:Exception => return new Dimension(0, 0);
            }
        }
        
        private val lblSwitch = new Label("Mode: English->Morse")
        {
            this.tooltip = "Current translation mode.";
        };
        
        private val btnSwitch = new Button()
        {           
            private val imgName         = "SwitchTranslationModeIcon.png";
            private val imgNamePressed  = "SwitchTranslationModeIcon_PRESSED.png";
            private val DIM             = getImageDimension(imgName);      
            
            setButtonSize(this, DIM);
            this.icon                   = new ImageIcon(ASSET_FOLDER + FILE_SEP + imgName);
            this.pressedIcon            = new ImageIcon(ASSET_FOLDER + FILE_SEP + imgNamePressed);
            this.border                 = BorderFactory.createLineBorder(Color.black);
            this.tooltip                = "Switch language you wish to translate to and from.\nCurrent mode: English->Morse";
            this.reactions += 
            {
                case scala.swing.event.ButtonClicked(_) =>
                {
                    MorseTranslator.switchTranslationMode();
                    
                    if (MorseTranslator.getTranslationMode())  //Mode == English to Morse
                    {
                        this.tooltip    = "Switch language you wish to translate to and from.\nCurrent mode: English->Morse";
                        lblSwitch.text  = "Mode: English->Morse";
                    }
                    else
                    {
                        this.tooltip    = "Switch language you wish to translate to and from.\nCurrent mode: Morse->English";
                        lblSwitch.text  = "Mode: Morse->English";
                    }
                    
                }
            };

        };
        
        private val txtOutput = new TextArea("", TEXT_FIELD_ROW, TEXT_FIELD_COLUMN)
        {
            this.editable   = false;
            this.tooltip    = "Translated text appears here.";
            this.lineWrap   = true;
        };
        
        private val scrollTxtOutput = new ScrollPane(txtOutput);
        
        private val txtInput = new TextArea("", TEXT_FIELD_ROW, TEXT_FIELD_COLUMN)
        {
            this.tooltip    = "What you want to translate.";
            this.enabled    = true;
            this.lineWrap   = true;
            this.focusable  = true;
            this.requestFocus();
            //TODO: Figure out a way to only trigger this if SHIFT and ENTER are pressed at (near) the same time. 
            listenTo(keys); 
            reactions += 
            {
                case keypress:scala.swing.event.KeyPressed =>
                {
                    KeyListener.addNewKey(keypress.peer);
                    val keys  = KeyListener.getKeysPressedInLastSecond();
                    //For the record, I feel as if this is an awful way to do this but at this point I really don't care.
                    //Scala needs a better way to listen to multiple key presses at near the same time without delving into linking the java KeyListener class with scala KeyEvents
                    val bools = Array(false, false);
                    for (key <- keys)
                    {
                        if (key.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) 
                            bools(0) = true;
                        else if (key.getKeyCode() == java.awt.event.KeyEvent.VK_SHIFT)
                            bools(1) = true;
                    }
                    if (!bools.contains(false))
                    {
                        txtOutput.text = "";
                        
                        val result  = tryTranslate();
                        if (result != None)
                        {
                            txtOutput.text = result.toString();
                        }
                    }
                }
            };
              
        };
        
        private val scrollTxtInput = new ScrollPane(txtInput);
        
        private val btnConfirm = new Button()
        { 
            private val imgName         = "ConfirmIcon.png";
            private val imgNamePressed  = "ConfirmIcon_PRESSED.png";
            private val DIM             = getImageDimension(imgName);      
            
            setButtonSize(this, DIM);
            this.icon                   = new ImageIcon(ASSET_FOLDER + FILE_SEP + imgName);
            this.pressedIcon            = new ImageIcon(ASSET_FOLDER + FILE_SEP + imgNamePressed);
            this.border                 = BorderFactory.createLineBorder(Color.black);
            this.tooltip                = "Translate inputted text.";
            this.reactions += 
            {
                case scala.swing.event.ButtonClicked(_) =>
                {
                    txtOutput.text = "";
                    
                    val result  = tryTranslate();
                    if (result != None)
                    {
                        txtOutput.text = result.toString();
                    }
                }
            };
        };
    
        private val btnCopyToClipboard = new Button()
        {
            private val imgName         = "CopyToClipboardIcon.png";
            private val imgNamePressed  = "CopyToClipboardIcon_PRESSED.png";
            private val DIM             = getImageDimension(imgName);      
            
            setButtonSize(this, DIM);
            this.icon                   = new ImageIcon(ASSET_FOLDER + FILE_SEP + imgName);
            this.pressedIcon            = new ImageIcon(ASSET_FOLDER + FILE_SEP + imgNamePressed);
            this.border                 = BorderFactory.createLineBorder(Color.black);
            this.tooltip                = "Copy output contents to clipboard.";
            this.reactions +=  
            {
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
                            throw new Exception("Something very bad has occured while attempting to copy your text and we're not sure what. Please report this on the github repository. Exception details follow: " + e.getMessage());
                        }
                    }
                }
            }
        };
            
        this.title         = "MorseGUI";
        this.preferredSize = new Dimension(750, 500);
        
        try
        {
            this.iconImage = javax.imageio.ImageIO.read(new java.io.File(ASSET_FOLDER + FILE_SEP + "MorseIcon.png"));
        } 
        catch
        {
            case e: Exception => {};
        }
        
        this.contents      = new BoxPanel(Orientation.Vertical)
        {   
            
            this.contents += new FlowPanel(new Label("<HTML><U>INPUT</U></HTML>"));
            this.contents += new FlowPanel(
                scrollTxtInput
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
                scrollTxtOutput
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