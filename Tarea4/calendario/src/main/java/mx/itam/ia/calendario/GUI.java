package mx.itam.ia.calendario;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;

/**
 * @author daniel
 *
 */
/**
 * @author daniel
 *
 */
/**
 * @author daniel
 *
 */
public class GUI {

	private JFrame frame;
	private JTextField txtMaterias;
	private JTextField txtProfesores;
	private JButton btnProfesor;
	private JTextArea txtArea;
	private Calendario c;
	private JTextField txtSalonesdisponibles;
	private OpenFile of = new OpenFile();
	private String initialPath;
	private JTable table, table1;
	private DefaultTableModel model, model1;
	private JScrollPane scrollPane_1;
	private String[] csvHorario;
	private String[] csvRestriccion = null;
	private JTextField txtRelaciones;
	private boolean isRelation = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Optimización de Horario");
		frame.setBounds(100, 100, 722, 497);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton btnMaterias = new JButton("Materias");
		btnMaterias.setBounds(231, 36, 144, 25);
		btnMaterias.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					of.pickMe();
					if(!of.getPath().substring(of.getPath().length()-3, of.getPath().length()).equals("csv")) {
						JOptionPane.showMessageDialog(null, "Ingresar un CSV", "Archivo no compatible", JOptionPane.INFORMATION_MESSAGE);
					} else {
						isRelation = false;
						initialPath = of.getPath();
						txtMaterias.setText(of.getPath());
						csvHorario = of.parseCsv(of.getSb().toString());
						for(String elem: csvHorario) {
							System.out.println(elem);
						}
						c = new Calendario(csvHorario);
						String courses = c.getCourses();
						String[] nameCourses = c.getNameCourses();
						txtArea.setText(courses);
						for(int i = 0; i< c.getNameCourses().length; i++) {
							model.insertRow(i, new Object[] {nameCourses[i], 1+""});
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		txtArea = new JTextArea();
		txtArea.setEditable(false);
		txtArea.setBounds(12, 114, 209, 162);
		frame.getContentPane().add(txtArea);
		
		txtMaterias = new JTextField();
		txtMaterias.setBounds(12, 39, 209, 19);
		txtMaterias.setColumns(10);
		
		JLabel lblHorario = new JLabel("Horario");
		lblHorario.setBounds(12, 12, 53, 15);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(lblHorario);
		frame.getContentPane().add(txtMaterias);
		
		txtProfesores = new JTextField();
		txtProfesores.setColumns(10);
		txtProfesores.setBounds(12, 67, 209, 19);
		frame.getContentPane().add(txtProfesores);
		
		btnProfesor = new JButton("Profesores");
		btnProfesor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					of.pickMe();
					if(!of.getPath().substring(of.getPath().length()-3, of.getPath().length()).equals("csv")) {
						JOptionPane.showMessageDialog(null, "Ingresar un CSV", "Archivo no compatible", JOptionPane.INFORMATION_MESSAGE);
					} else {
						c = new Calendario(csvHorario);
						txtProfesores.setText(of.getPath());
						csvRestriccion = of.parseCsvProfessor(of.getSb().toString());
						for(String elem: csvRestriccion) {
							System.out.println(elem);
						}
						c.setRestriction(csvRestriccion);
						txtArea.setText(c.getCourses());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnProfesor.setBounds(231, 67, 144, 25);
		frame.getContentPane().add(btnProfesor);
		frame.getContentPane().add(btnMaterias);
		
		txtRelaciones = new JTextField();
		txtRelaciones.setBounds(12, 100, 209, 19);
		txtRelaciones.setColumns(10);
		frame.getContentPane().add(txtRelaciones);
		
		JButton btnRelaciones = new JButton("Relaciones");
		btnRelaciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					of.pickMe();
					if(!of.getPath().substring(of.getPath().length()-3, of.getPath().length()).equals("csv")) {
						JOptionPane.showMessageDialog(null, "Ingresar un CSV", "Archivo no compatible", JOptionPane.INFORMATION_MESSAGE);
					} else {
						isRelation = true;
						initialPath = of.getPath();
						txtRelaciones.setText(of.getPath());
						csvHorario = of.getSb().toString().replaceAll(",", "-").split("\n");
						for(String elem: csvHorario) {
							System.out.println(elem);
						}
						c = new Calendario(csvHorario);
						String courses = c.getCourses();
						String[] nameCourses = c.getNameCourses();
						txtArea.setText(courses);
						for(int i = 0; i< c.getNameCourses().length; i++) {
							model.insertRow(i, new Object[] {nameCourses[i], 1+""});
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnRelaciones.setBounds(231, 100, 144, 25);
		frame.getContentPane().add(btnRelaciones);
		
		JButton btnProcesar = new JButton("Procesar");
		btnProcesar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				c = new Calendario(csvHorario);
				if(csvRestriccion != null) {
					c.setRestriction(csvRestriccion);
				}
				c.setMaxNumberSchedules(Integer.parseInt(txtSalonesdisponibles.getText()));
				int [] restriccionHorario = new int[model.getRowCount()];
				for(int i = 0; i < model.getRowCount(); i++) {
					restriccionHorario[i] = Integer.parseInt(model.getValueAt(i, 1).toString());
				}
				c.setBloquedGroups(restriccionHorario);
				if (!isRelation) {
					String calendar = of.createCalendar(c.getSolution());
					System.out.println(calendar);
					if (of.createCsvCalendar(calendar, initialPath)) {
						JOptionPane.showMessageDialog(null, "CSV creado con éxito en "+initialPath.substring(0, initialPath.length()-4)+"Result.csv", "Creación de calendario", JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "Error, revisar solución de ARE", "Creación de calendario", JOptionPane.INFORMATION_MESSAGE);
					}
					StringBuilder sb = new StringBuilder();
					Scanner input;
					try {
						input = new Scanner(new File(initialPath.substring(0, initialPath.length()-4)+"Result.csv"));
						while(input.hasNext()) {
							sb.append(input.nextLine());
							sb.append("\n");
						}
						input.close();
						for(int i = model1.getRowCount()-1; i >= 0; i--) {
							model1.removeRow(i);
						}
						String[] horario = sb.toString().split("\n");
						for(int i = 1; i < horario.length; i++) {
							String[] elems = horario[i].split(",");
							if (elems.length < 6) {
								String[] tmpElems = new String[6];
								for(int j = 0; j < 6; j++) {
									tmpElems[j] = "";
								}
								for(int j = 0; j < elems.length; j++) {
									tmpElems[j] = elems[j];
								}
								elems = new String[6];
								for(int j = 0; j < tmpElems.length; j++) {
									elems[j] = tmpElems[j];
								}
							}
							model1.insertRow(i-1, elems);
						}
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					if (of.createTxtGroups(c.getSolution(), initialPath)) {
						JOptionPane.showMessageDialog(null, "TXT creado con éxito en "+initialPath.substring(0, initialPath.length()-4)+"Result.txt", "Creación de grupos", JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "Error, revisar solución de ARE", "Creación de calendario", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});
		btnProcesar.setBounds(230, 158, 117, 25);
		frame.getContentPane().add(btnProcesar);
		
		JScrollPane scrollPane = new JScrollPane(txtArea);
		scrollPane.setBounds(10, 130, 209, 158);
		frame.getContentPane().add(scrollPane);
		
		JLabel lblSalonesDisponibles = new JLabel("Salones Disponibles");
		lblSalonesDisponibles.setBounds(398, 41, 144, 15);
		frame.getContentPane().add(lblSalonesDisponibles);
		
		txtSalonesdisponibles = new JTextField("2");
		txtSalonesdisponibles.setBounds(560, 39, 114, 19);
		frame.getContentPane().add(txtSalonesdisponibles);
		txtSalonesdisponibles.setColumns(10);
		
		model = new DefaultTableModel();
		table = new JTable(model);
		model.addColumn("Materia");
		model.addColumn("Empezar Desde");
		table.setBounds(231, 246, 191, 85);
		frame.getContentPane().add(table);
		
		model1 = new DefaultTableModel();
		table1 = new JTable(model1);
		model1.addColumn("Hora");
		model1.addColumn("Lunes");
		model1.addColumn("Martes");
		model1.addColumn("Miercoles");
		model1.addColumn("Jueves");
		model1.addColumn("Viernes");
		table1.setBounds(231, 246, 191, 85);
		frame.getContentPane().add(table1);
		
		JScrollPane scrollPane_2 = new JScrollPane(table);
		scrollPane_2.setBounds(398, 130, 276, 158);
		frame.getContentPane().add(scrollPane_2);
		
		scrollPane_1 = new JScrollPane(table1);
		scrollPane_1.setBounds(12, 335, 672, 138);
		frame.getContentPane().add(scrollPane_1);

	}
}
