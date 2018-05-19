/**
 * Paquete que crea la optimización de horario.
 */
package mx.itam.ia.calendario;

import java.util.ArrayList;
import java.util.Arrays;
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
	/**
	 * 
	 */
	private List<Set<Integer>> bloquedGroups;
	/**
	 * 
	 */
	private int[] groups;
	/**
	 * 
	 */
	private int maxSpace;

	/**
	 * 
	 * @param nameNodes
	 * @param numberConnections
	 * @param connections
	 * @param maxSpace
	 */
	public ResolutionEnumeration(List<String> nameNodes,
			int[] numberConnections,
			List<Set<String>> connections,
			int maxSpace) {
		this.nameNodes = nameNodes;
		this.numberConnections = numberConnections;
		this.connections = connections;
		this.bloquedGroups = new ArrayList<Set<Integer>>();
		for (int i = 0; i < this.connections.size(); i++) {
			this.bloquedGroups.add(new TreeSet<Integer>());
		}
		groups = new int[this.connections.size()];
		this.maxSpace = maxSpace;
	}
	
	public void setBloquedGroups(int[] groups) {
		int cont = 0;
		for(int block: groups) {
			for(int i = 1; i < block; i++) {
				this.bloquedGroups.get(cont).add(i);
			}
			cont++;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public int[] getGroups() {
		return groups;
	}

	/**
	 * 
	 */
	public void solve() {
		while (!finish()) {
			int maxNode = this.getMaxNode();
			this.numberConnections[maxNode] = -1;
			this.connections.get(maxNode).clear();
			this.addToGroup(maxNode);
		}
	}
	
	/**
	 * 
	 */
	public void optimize(int[] startedBloquedGroups) {
		int max = Arrays.stream(groups).max().getAsInt();
		int[] formedGroups = new int[max];
		Set<Integer> oddPositions = new TreeSet<Integer>();
		for (int group: groups) {
			formedGroups[group - 1]++;
		}
		for (int group: groups) {
			if (formedGroups[group - 1] == 1) {
				oddPositions.add(group - 1);
			}
		}
		if (oddPositions.size() > 1) {
			int lastGroup = (int) oddPositions.toArray()[0] + 1;
			int firstGroup = (int) oddPositions.toArray()[oddPositions.size()-1] + 1;
			for (Object tmpPosition: oddPositions.toArray()) {
				int position = (int) tmpPosition + 1;
				if (position >= lastGroup) {
					lastGroup = position;
				}
				if (position <= firstGroup) {
					firstGroup = position;
				}
			}
			int elementOfLastGroup = getIndexOfElementsOfGroup(lastGroup)[0];
			int startFrom = startedBloquedGroups[elementOfLastGroup];
			boolean hasChangedGroup = false;
			for (int i = startFrom; i < groups.length; i++) {
				int[] elementsOfGroup = getIndexOfElementsOfGroup(i);
				for(int element: elementsOfGroup) {
					if(!connections.get(elementOfLastGroup).contains(nameNodes.get(element))
							&& !bloquedGroups.get(element).contains(firstGroup)) {
						groups[elementOfLastGroup] = groups[element];
						groups[element] = firstGroup;
						hasChangedGroup = true;
					}
					if (hasChangedGroup) {
						break;
					}
				}
				if (hasChangedGroup) {
					break;
				}
			}
		}
	}
	
	/**
	 * 
	 */
	private int[] getIndexOfElementsOfGroup(int group) {
		List<Integer> res = new ArrayList<Integer>();
		for(int i = 0; i < groups.length; i++) {
			if (groups[i] == group) {
				res.add(i);
			}
		}
		return res.stream().mapToInt(i -> i).toArray();
	}
	
	/**
	 * 
	 * @return
	 */
	private boolean finish() {
		for (int connection: this.numberConnections) {
			if (connection != -1) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * @param maxNode
	 */
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

	/**
	 * 
	 * @param maxNode
	 * @param group
	 * @return
	 */
	private boolean freeToPlace(int maxNode, int group) {
		int cont = 0;
		if (maxNode >= this.bloquedGroups.size()) {
			return true;
		} else if (!this.bloquedGroups.get(maxNode).contains(group)) {
			for (Integer tmpGroup: this.groups) {
				if (tmpGroup == group) {
					cont++;
				}
				if (cont >= this.maxSpace && this.maxSpace > 0) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @return
	 */
	private int getMaxNode() {
		int maxNode = 0;
		for (int i = 0; i < this.numberConnections.length; i++) {
			if (this.numberConnections[i] > this.numberConnections[maxNode]) {
				maxNode = i;
			}
			if (this.numberConnections[i] == 0) {
				return i;
			}
		}
		return maxNode;
	}

	/**
	 * 
	 * @param node
	 * @param group
	 */
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

	/**
	 * 
	 * @param group
	 * @param node
	 */
	private void blockGroup(int group, int node) {
		this.bloquedGroups.get(node).add(group);
	}

}
