import scala.swing._, UI._;

//class MainWindow(ui:UI) extends SimpleSwingApplication
//{
//	def top = ui;
//}

class MainWindow(ui:CustomUI) extends SimpleSwingApplication
{
	def top = ui;
}

object program
{
	def main(args: Array[String])
	{	 
		var myUI = new MainWindow(new CustomUI());
		myUI.top.visible = true;		
	}
}