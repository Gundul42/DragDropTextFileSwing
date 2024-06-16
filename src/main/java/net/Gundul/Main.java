package net.Gundul;

import javax.swing.*;

public class Main
{
	public static void main(String[] args)
	{
		//******************************
		String		yourUserName = null;
		String		yourPassword = null;
		//******************************

		SwingUtilities.invokeLater(() -> {
			DragDropTextFileSwing frame =
					new DragDropTextFileSwing(yourUserName, yourPassword);
			frame.setVisible(true);
		});
	}
}

