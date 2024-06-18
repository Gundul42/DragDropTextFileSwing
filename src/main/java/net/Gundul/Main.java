package net.Gundul;

import javax.swing.*;

public class Main
{
	public static void main(String[] args)
	{
		//******************************
		String		yourUserName = null;
		String		yourPassword = null;
		String		yourUrl = "https://www.insertURL.toutedesuite";
		//******************************

		SwingUtilities.invokeLater(() -> {
			DragDropTextFileSwing frame =
					new DragDropTextFileSwing(yourUserName, yourPassword, yourUrl);
			frame.setVisible(true);
		});
	}
}

