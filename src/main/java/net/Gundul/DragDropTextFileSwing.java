package net.Gundul;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.io.*;
import java.net.ConnectException;
import java.util.List;
import java.util.Vector;

public class DragDropTextFileSwing extends JFrame
{

	private JTextArea		textArea;
	private final String	loginName;
	private final String	loginPwd;
	private final String	url;

	public DragDropTextFileSwing(String loginName, String loginPwd, String url)
	{
		super("Drag and Drop Text File Reader");
		this.loginName = loginName;
		this.loginPwd = loginPwd;
		this.url = url;

		textArea = new JTextArea();
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);

		getContentPane().add(scrollPane, BorderLayout.CENTER);

		new DropTarget(textArea, new FileDropTargetListener());

		setSize(600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	private class FileDropTargetListener extends DropTargetAdapter
	{
		@Override
		public void drop(DropTargetDropEvent event)
		{
			try
			{
				event.acceptDrop(DnDConstants.ACTION_COPY);
				List<File> droppedFiles = (List<File>)
						event.getTransferable().
								getTransferData(DataFlavor.javaFileListFlavor);
				if (droppedFiles.size() > 0)
				{
					File file = droppedFiles.get(0);
					readFile(file);
				}
				event.dropComplete(true);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	private String parseFile(BufferedReader br)
	{
		String	nextLine = null;
		String	wholeLine = null;
		int		i = 1;

		try
		{
			nextLine = br.readLine();
			while (nextLine != null && !nextLine.equals("drag"))
			{
				if (nextLine.length() > 0)
				{
					if (nextLine.matches("[0-9]+"))
					{
						wholeLine = nextLine + " -- ";
					}
					else
					{
						if (wholeLine != null)
						{
							wholeLine = wholeLine + nextLine;
						}
						else
						{
							wholeLine = nextLine;
						}
					}
				}
				nextLine = br.readLine();
			}
			return wholeLine;
		}
		catch (IOException e)
		{
			return e.getMessage();
		}
	}

	private void readFile(File file)
	{
		String			line;
		String			path;
		BufferedReader	br = null;
		PrintWriter		pw = null;
		File			writeFile = null;
		Config			nextCloud = new Config(loginName, loginPwd, url);

		textArea.setText("");
		try
		{
			br = new BufferedReader(new FileReader(file));
			path = file.getAbsolutePath() + ".upl";
			writeFile = new File(path);
			pw = new PrintWriter(path);

			line = parseFile(br);
			while (line != null)
			{
				textArea.append(line + "\n");
				pw.println(line);
				line = parseFile(br);
			}
			pw.close();
			nextCloud.fileUpload(writeFile);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			textArea.setText("Error accessing file: " + e.getMessage());
		}
	}
}