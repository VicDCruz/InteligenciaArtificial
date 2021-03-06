/**
 * Paquete que crea la optimización de horario.
 */
package mx.itam.ia.calendario;

/**
 * Importaciones de JAVA
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Víctor Cruz
 * Clase que permite obtener las relaciones entre cursos y
 * crear una tabla con horarios óptimos.
 */
public class Calendario {
	/**
	 * Nombre de los cursos.
	 */
	private List<String> nameCourses;
	/**
	 * Arreglo de conjuntos por cada curso.
	 */
	private List<Set<String>> courses;
	/**
	 * Total de elementos de cada conjunto.
	 */
	private int[] numberCourses, blockedGroups;
	private int maxNumberSchedules;

	private ResolutionEnumeration re;

	/**
	 * Constructor de Calendario.
	 * @param relations Arreglo de Strings con las relaciones
	 * de los nodos (horarios).
	 */
	public Calendario(String[] relations) {
		this.nameCourses = new ArrayList<String>();
		this.courses = new ArrayList<Set<String>>();
		this.setRelations(relations);
		this.numberCourses = new int[this.courses.size()];
		this.setNumberCourses();
		this.maxNumberSchedules = 0;
	}

	public void setBloquedGroups(int[] groups) {
		this.blockedGroups = groups;
	}

	public String[] getSolution() {
		String[] res;
		re = new ResolutionEnumeration(this.nameCourses,
				this.numberCourses, this.courses, this.maxNumberSchedules);
		re.setBloquedGroups(this.blockedGroups);
		re.solve();
		re.optimize(this.blockedGroups);
		int[] groups = re.getGroups();
		int max = Arrays.stream(groups).max().getAsInt();
		res = new String[max];
		System.out.println(max);
		for(int i = 0; i < max; i++) {
			res[i] = "";
		}
		for(int i = 0; i < groups.length; i++) {
			res[groups[i]-1] += this.nameCourses.get(i)+",";
		}
		return res;
	}

	/**
	 * Creamos las relaciones.
	 * @param relations Arreglo de Strings con las
	 * relaciones de los nodos (horarios).
	 */
	private void setRelations(String[] relations) {
		String[] connections;
		int positionCourse;
		for (String relation: relations) {
			connections = relation.split("-");
			for (String tmpCourse: connections) {
				positionCourse =
						this.nameCourses.indexOf(tmpCourse);
				if (positionCourse == -1) {
					this.nameCourses.add(tmpCourse);
					this.courses.add(new TreeSet<String>());
					positionCourse =
							this.courses.size() - 1;
				}
				for (String tmpCourse1: connections) {
					if (!tmpCourse1.equals(tmpCourse)) {
						this.courses.
						get(positionCourse).
						add(tmpCourse1);
					}
				}
			}
		}
	}

	public void setRestriction(String[] restriction) {
		this.setRelations(restriction);
	}

	/**
	 * Imprimimos la relación entre cursos.
	 * @return Cadena con las relaciones.
	 */
	public String getCourses() {
		String res = "";
		for (String name: this.nameCourses) {
			res += name + ": ";
			int positionCourse =
					this.nameCourses.indexOf(name);
			for (String course: this.courses.
					get(positionCourse)) {
				res += course + ", ";
			}
			res += "\n";
		}
		return res.substring(0,res.length()-2);
	}

	public String[] getNameCourses() {
		return this.nameCourses.toArray(new String[this.nameCourses.size()]);
	}

	/**
	 * Obtenemos el número de conexiones totales de cada curso.
	 */
	public void setNumberCourses() {
		int cont = 0;
		while (cont < this.courses.size()) {
			this.numberCourses[cont] =
					this.courses.get(cont).size();
			cont++;
		}
	}

	public void setMaxNumberSchedules(int newMax) {
		this.maxNumberSchedules = newMax;
	}

	/**
	 * Imprimimos el número de conexiones entre cada nodo.
	 * @return Cadena con el resultado del total de cada nodo.
	 */
	public String getNumberCourses() {
		String res = "";
		int cont = 0;
		for (String name: this.nameCourses) {
			res += name + ": ";
			res += this.numberCourses[cont] + "\n";
			cont++;
		}
		return res;
	}

	/**
	 * Dar resultados finales.
	 * @param args Argumentos para imprimir.
	 */
	public static void main(String[] args) {
		String[] relacion = {"A-E", "B-C", "C-D", "E-D", "A-B-D",
				"A-H-I", "F-G-H", "B-H-I", "D-F-I", "D-H-I"};
		Calendario c = new Calendario(relacion);
		System.out.println(c.getCourses());
		System.out.println(c.getNumberCourses());
		// c.setMaxNumberSchedules(2);
		// String[] sol = c.getSolution();
		// for(int i = 0; i < sol.length; i++) {
		// 	System.out.println(i+1+": "+sol[i]);
		// }
	}

}
