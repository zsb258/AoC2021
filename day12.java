import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class day12 {
	static class Node {
		String name;
		List<Node> connected = new ArrayList<>();
		final boolean large;

		Node (String name){
			this.name = name;
			this.large = Character.isUpperCase(name.charAt(0));
		}

		String getName(){
			return this.name;
		}

		void addConnection(Node node) {
			if (!this.connected.contains(node)) {
				this.connected.add(node);
				node.addConnection(this);
			}
		}
	}


	static int part1(HashMap<String, Node> nodesMap){
		int res = 0;
		for (String elem : depthFirstSearch(nodesMap, emptyVisitMap(nodesMap),
				new LinkedList<String>(), "start")){
			System.out.println(elem);
			res++;
		}



		return res;
	}

	static int part2(){
		return 0;
	}

	static LinkedList<String> depthFirstSearch(HashMap<String, Node> nodesMap,
											   HashMap<String, Integer> visitMap,
											   LinkedList<String> traversed,
											   String nextNode){
		System.out.printf("Started dfs. Next: %s%n", nextNode);
		if (nextNode.equals("end")){
			System.out.println("reached end");

			return new LinkedList<String>(List.of(new String[]{"end"}));
		}
		if (!nodesMap.get(nextNode).large && visitMap.get(nextNode) > 0){
			// node is small and has been visited

			System.out.println("reached small and visited node: " + nextNode);
			return new LinkedList<String>();
		}
//		else if (!nodesMap.get(nextNode).large && visitMap.get(nextNode) > 0){
//			// node is large and has been visited
//		}
		visitMap.replace(nextNode, visitMap.get(nextNode) + 1);
		traversed.add(nextNode);

		System.out.println("explore next nodes of: " + nextNode);
		for (Node node : nodesMap.get(nextNode).connected){
			System.out.println("from " + nextNode + " to " + node.getName());
			if (!traversed.contains(node.getName()) | node.large) {
				System.out.println("success from " + nextNode + " to " + node.getName());
				LinkedList<String> temp =
						depthFirstSearch(nodesMap, visitMap, traversed, node.getName());
				temp.addFirst(nextNode);
				return temp;
			}


		}

		return new LinkedList<String>();
	}

	static HashMap<String, Integer> emptyVisitMap(HashMap<String, Node> nodesMap){
		HashMap<String, Integer> res = new HashMap<>();
		for (String nodeName : nodesMap.keySet()){
			res.put(nodeName, 0);
		}
		return res;
	}

	public static void main(String[] args) throws FileNotFoundException {
		File input = new File("./Inputs/day12_test_input.txt");
		Scanner sc = new Scanner(input);
		HashMap<String, Node> nodesMap = new HashMap<>();
		nodesMap.put("deadEnd", new Node("deadEnd"));

		while (sc.hasNextLine()) {
			String[] line = sc.nextLine().split("-");
			String one = line[0];
			String two = line[1];
			nodesMap.putIfAbsent(one, new Node(one));
			nodesMap.putIfAbsent(two, new Node(two));
			nodesMap.get(one).addConnection(nodesMap.get(two));
		}
//		for (String nodeName : nodesMap.keySet()){
//			System.out.println(nodesMap.get(nodeName).getName());
//		}

//		for (Node node : nodesMap.get("A").connected) {
//			System.out.println(node.getName());
//		}


		System.out.println("Part1: " + part1(nodesMap));
	}
}
