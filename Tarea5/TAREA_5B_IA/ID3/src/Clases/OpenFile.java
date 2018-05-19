package Clases;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JFileChooser;

public class OpenFile {
	private JFileChooser fileChooser = new JFileChooser();
	private StringBuilder sb;
	private String path;
	
	public StringBuilder getSb() {
		return sb;
	}

	public void setSb(StringBuilder sb) {
		this.sb = sb;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void pickMe() throws Exception{
		sb = new StringBuilder();
		if (this.fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			java.io.File file = fileChooser.getSelectedFile();
			path = file.getAbsolutePath();

			Scanner input = new Scanner(file);

			while(input.hasNext()) {
				sb.append(input.nextLine());
				sb.append("\n");
			}
			input.close();
		} else {
			path = "No file was selected";
		}
	}
}