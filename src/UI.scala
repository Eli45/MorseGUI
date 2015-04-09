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
	
	//CustomUI class containing all UI components used in our GUI and their reaction events.
	class CustomUI()
	extends MainFrame
	{	
		private var MorseTranslator = new MorseReader();
		
		private var lblError = new Label("");
		
		private var btnSwitch = new Button("Switch to Morse->English")
		{
			this.tooltip = "Switch language you wish to translate to and from.";
			
			this.reactions += 
			{
				case scala.swing.event.ButtonClicked(_) =>
				{
					if (this.text.toLowerCase().equals("switch to morse->english"))
					{
						this.text = "Switch to English->Morse";
					}
					else
					{
						this.text = "Switch to Morse->English";
					}
          
					MorseTranslator.switchTranslationMode();
				}
			};

		};
		
		private var txtOutput = new TextField("", 10)
		{
			this.editable = false;
			this.tooltip = "Translated text appears here.";
			//this.preferredSize = new Dimension(100, 100);
		};
		
		private var txtInput = new TextField("", 10)
		{
			this.tooltip   = "What you want to translate.";
			this.enabled   = true;
			this.focusable = true;
			//this.preferredSize = new Dimension(100, 100);
		};
		
		private var btnConfirm = new Button("Confirm")
		{
			this.tooltip = "Translate inputted text.";
			this.reactions += 
			{
				case scala.swing.event.ButtonClicked(_) =>
				{			
					lblError.text = "";
					try 
					{
						txtOutput.text = MorseTranslator.translate(txtInput.text);
					}
					catch
					{
						case e:Exception => 
						{
							lblError.text = e.getMessage();
						}
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
			
		this.title = "Eli Morse Translator";
		this.preferredSize = new Dimension(600, 300);
		this.contents = new BoxPanel(Orientation.Vertical)
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
		
		//TODO: Doesn't work yet, not sure what's wrong.
		private val keylistener = new java.awt.event.KeyListener()
		{
			//This method never seems to be called even when pressing a key.
			override def keyPressed(e:java.awt.event.KeyEvent)
			{
				if (txtInput.hasFocus && (e.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER))
				{
					scala.swing.event.ButtonClicked(btnSwitch);
				}
			};
			
			override def keyReleased(e:java.awt.event.KeyEvent){};
			override def keyTyped(e:java.awt.event.KeyEvent){};
			
		};
		//Possibly something to do with this not adding it to my UI correctly.
		this.peer.addKeyListener(this.keylistener);
		
	}
	
}