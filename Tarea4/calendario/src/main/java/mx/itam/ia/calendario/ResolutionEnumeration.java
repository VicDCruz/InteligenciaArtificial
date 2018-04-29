/**
 * Paquete que crea la optimización de horario.
 */
package mx.itam.ia.calendario;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Víctor Cruz
 * Clase que obtiene el mejor horario posible.
 */
public class ResolutionEnumeration {
	/**
	 * Nombre de los nodos.
	 */
	private List<String> nameNodes;
	/**
	 * Número de conexiones entre nodos.
	 */
	private int[] numberConnections;
	/**
	 * Arreglo con los grupos.
	 */
	private List<Set<String>> connections;
	private List<Set<Integer>> bloquedGroups;
	private int[] groups;
	private int maxSpace;

	public ResolutionEnumeration(List<String> nameNodes,
			int[] numberConnections,
			List<Set<String>> connections,
			int maxSpace) {
		this.nameNodes = nameNodes;
		this.numberConnections = numberConnections;
		this.connections = connections;
		this.bloquedGroups = new ArrayList<Set<Integer>>();
		for(int i = 0; i < this.connections.size(); i++) {
			this.bloquedGroups.add(new TreeSet<Integer>());
		}
		groups = new int[this.connections.size()];
		this.maxSpace = maxSpace;
	}
	
	public int[] getGroups() {
		return groups;
	}

	public void solve() {
		while (!finish()) {
			int maxNode = this.getMaxNode();
			this.numberConnections[maxNode] = -1;
			this.connections.get(maxNode).clear();
			this.addToGroup(maxNode);
		}
	}
	
	private boolean finish() {
		for (int connection: this.numberConnections) {
			if (connection != -1) {
				return false;
			}
		}
		return true;
	}

	private void addToGroup(int maxNode) {
		boolean isPlaced = false;
		int group = 1;
		while (!isPlaced) {
			isPlaced = freeToPlace(maxNode, group);
			if (isPlaced) {
				String nameMaxNode = this.nameNodes.get(maxNode);
				this.groups[maxNode] = group;
				this.removeNode(nameMaxNode, group);
			}
			group++;
		}
	}

	private boolean freeToPlace(int maxNode, int group) {
		int cont = 0;
		if (maxNode >= this.bloquedGroups.size()) {
			return true;
		} else if (!this.bloquedGroups.get(maxNode).contains(group)) {
			for (Integer tmpGroup: this.groups) {
				if (tmpGroup == group) {
					cont++;
				}
				if (cont >= this.maxSpace) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	private int getMaxNode() {
		int maxNode = 0;
		for (int i = 0; i < this.numberConnections.length; i++) {
			if (this.numberConnections[i] > this.numberConnections[maxNode]) {
				maxNode = i;
			}
			if(this.numberConnections[i] == 0) {
				return i;
			}
		}
		return maxNode;
	}

	public void removeNode(String node, int group) {
		int contNode = 0;
		for (Set<String> connection: connections) {
			if (!connection.isEmpty() && connection.contains(node)) {
				connection.remove(node);
				this.numberConnections[contNode]--;
				blockGroup(group, contNode);
			}
			contNode++;
		}
	}

	private void blockGroup(int group, int node) {
		this.bloquedGroups.get(node).add(group);
	}

}
