package mx.itam.ia.calendario;

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

	private Map<String, String> schedule = new HashMap<String, String>();

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

	public String[] parseCsv(String csv) {
		List<String> res = new ArrayList<String>();
		List<String> lunes = new ArrayList<String>();
		List<String> martes = new ArrayList<String>();
		List<String> miercoles = new ArrayList<String>();
		List<String> jueves = new ArrayList<String>();
		List<String> viernes = new ArrayList<String>();
		String line;
		String[] courses;

		csv = csv.substring(csv.indexOf("\n")+1, csv.length());
		while(!csv.equals("")) {
			if(csv.contains("\n")) {
				line = csv.substring(0, csv.indexOf("\n"));
			} else {
				line = csv;
			}
			courses = line.split(",");
			int cont = 0;
			for(int i = 0; i < courses.length; i+=2) {
				schedule.put(courses[i], courses[i+1]);
				if(courses[i+1].contains("L")) {
					if(cont < lunes.size()) {
						lunes.set(cont, lunes.get(cont) + courses[i]+"-");
					} else {
						lunes.add(courses[i]+"-");
					}
				}
				if(courses[i+1].contains("M") && !courses[i+1].contains("Mi")) {
					if(cont < martes.size()) {
						martes.set(cont, martes.get(cont) + courses[i]+"-");
					} else {
						martes.add(courses[i]+"-");
					}
				}
				if(courses[i+1].contains("Mi")) {
					if(cont < miercoles.size()) {
						miercoles.set(cont, miercoles.get(cont) + courses[i]+"-");
					} else {
						miercoles.add(courses[i]+"-");
					}
				}
				if(courses[i+1].contains("J")) {
					if(cont < jueves.size()) {
						jueves.set(cont, jueves.get(cont) + courses[i]+"-");
					} else {
						jueves.add(courses[i]+"-");
					}
				}
				if(courses[i+1].contains("V")) {
					if(cont < viernes.size()) {
						viernes.set(cont, viernes.get(cont) + courses[i]+"-");
					} else {
						viernes.add(courses[i]+"-");
					}
				}
				cont++;
			}
			if(csv.contains("\n")) {
				csv = csv.substring(csv.indexOf("\n")+1);
			} else {
				csv = "";
			}
		}
		
		for(String elem: lunes) {
			res.add(elem.substring(0, elem.length()-1));
		}
		for(String elem: martes) {
			res.add(elem.substring(0, elem.length()-1));
		}
		for(String elem: miercoles) {
			res.add(elem.substring(0, elem.length()-1));
		}
		for(String elem: jueves) {
			res.add(elem.substring(0, elem.length()-1));
		}
		for(String elem: viernes) {
			res.add(elem.substring(0, elem.length()-1));
		}
		
		return res.toArray(new String[res.size()]);
	}

	public String createCalendar(String[] groups) {
		for(String elem: groups) {
			System.out.println("GROUPS:"+elem);
		}
		String res = "Hora,Lunes,Martes,Miercoles,Jueves,Viernes\n";
 		String[] lunes = new String[groups.length];
 		String[] martes = new String[groups.length];
 		String[] miercoles = new String[groups.length];
 		String[] jueves = new String[groups.length];
 		String[] viernes = new String[groups.length];
		int hora = 7, cont = 0;

		for (int i = 0; i < groups.length; i++) {
			lunes[i] = "";
			martes[i] = "";
			miercoles[i] = "";
			jueves[i] = "";
			viernes[i] = "";
		}

		for(String group: groups) {
			if(!group.equals("")) {
				for(String elem: group.split(",")) {
					String days = schedule.get(elem);
					if(days.contains("L")) {
						if(!lunes[cont].equals("")){
							lunes[cont] = lunes[cont]+"/"+elem;
						} else{
							lunes[cont] = elem;
						}
					}
					if(days.contains("M") && !days.contains("Mi")) {
						if(!martes[cont].equals("")){
							martes[cont] = martes[cont]+"/"+elem;
						} else{
							martes[cont] = elem;
						}
					}
					if(days.contains("Mi")) {
						if(!miercoles[cont].equals("")){
							miercoles[cont] = miercoles[cont]+"/"+elem;
						} else{
							miercoles[cont] = elem;
						}
					}
					if(days.contains("J")) {
						if(!jueves[cont].equals("")){
							jueves[cont] = jueves[cont]+"/"+elem;
						} else{
							jueves[cont] = elem;
						}
					}
					if(days.contains("V")) {
						if(!viernes[cont].equals("")){
							viernes[cont] = viernes[cont]+"/"+elem;
						} else{
							viernes[cont] = elem;
						}
					}
				}
			} else {
				lunes[cont] = "";
			}
			cont++;
		}
		for(int i = 0; i < lunes.length; i++) {
			if(hora <= 9) {
				res+= "0"+hora+":00,";
			} else {
				res+= hora+":00,";
			}
			res += lunes[i]+","+martes[i]+","+miercoles[i]+","+jueves[i]+","+viernes[i]+"\n";
			hora++;
		}
		return res;
	}

	public boolean createTxtGroups(String[] groups, String path) {
		boolean res = false;
		String groupsStr = "";
		BufferedWriter bw = null;
		FileWriter fw = null;
		int cont = 1;
		for(String group: groups) {
			groupsStr += "GRUPO "+cont+": "+group.substring(0, group.length()-1)+"\n";
			cont++;
		}
		try {
			fw = new FileWriter(path.substring(0, path.length()-4)+"Result.txt");
			bw = new BufferedWriter(fw);
			bw.write(groupsStr);
			res = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return res;
	}
	
	public boolean createCsvCalendar(String calendar, String path) {
		boolean res = false;
		BufferedWriter bw = null;
		FileWriter fw = null;
		try {
			fw = new FileWriter(path.substring(0, path.length()-4)+"Result.csv");
			bw = new BufferedWriter(fw);
			bw.write(calendar);
			res = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return res;
	}

	public String[] parseCsvProfessor(String csv) {
		List<String> profesores = new ArrayList<String>();
		List<String> clases = new ArrayList<String>();
		String line;
		String[] courses;
		csv = csv.substring(csv.indexOf("\n")+1, csv.length());
		
		while(!csv.equals("")) {
			if(csv.contains("\n")) {
				line = csv.substring(0, csv.indexOf("\n"));
			} else {
				line = csv;
			}
			courses = line.split(",");
			String prof = courses[0];
			String clase = courses[1];
			if(!profesores.contains(prof)) {
				profesores.add(prof);
				clases.add(clase+"-");
			}else {
				int indice = profesores.indexOf(prof);
				boolean status = false;
				String dias = schedule.get(clase);
				if(dias != null) {
					for(String tmpClase: clases.get(indice).split("-")) {
						String tmpDias = schedule.get(tmpClase);
						for(String dia: dias.split("/")) {
							if(status) {
								break;
							}
							if(dia.equals("M") && tmpDias.contains("Mi")) {
								String tmpElem = tmpDias.replace("Mi", "");
								status = tmpElem.contains(dia+"/");
							} else {
								status = tmpDias.contains(dia);
							}
						}
					}
					if(status) {
						clases.set(indice, clases.get(indice)+clase+"-");
					}
				}
			}
			if(csv.contains("\n")) {
				csv = csv.substring(csv.indexOf("\n")+1);
			} else {
				csv = "";
			}
		}
		for(int i = 0; i < clases.size(); i++) {
			clases.set(i, clases.get(i).substring(0, clases.get(i).length()-1));
		}
		return clases.toArray(new String[clases.size()]);
	}
}
