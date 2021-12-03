/*
// Definition for a Node.
class Node {
    public int val;
    public List<Node> neighbors;
    public Node() {
        val = 0;
        neighbors = new ArrayList<Node>();
    }
    public Node(int _val) {
        val = _val;
        neighbors = new ArrayList<Node>();
    }
    public Node(int _val, ArrayList<Node> _neighbors) {
        val = _val;
        neighbors = _neighbors;
    }
}
*/

//https://leetcode.com/problems/clone-graph/ 

import java.util.*;
class CloneGraph {
    public Node cloneGraph(Node node) {
        if (node != null) {
            // Queue to store the nodes whose children haven't been created
            // and assigned
            Queue<Node> unCompleteNodes = new LinkedList<Node>();

            // Map to store old node to new node pairs so that we have access
            // to the new nodes when we need to assign it as children to other
            // nodes
            Map<Node, Node> oldtoNewNodes = new HashMap<Node, Node>();

            // Creating copy of graph, with val of original graph called node
            Node newGraph = new Node(node.val);

            // Inserting original node into queue so the children can be created
            // and set for the new graph's first node
            unCompleteNodes.offer(node);

            // Putting in pair of original graph and new graph node
            oldtoNewNodes.put(node, newGraph);

            // Go through all uncomplete nodes and add/create their children
            // and attach it to the uncomplete node, making it complete
            while (!unCompleteNodes.isEmpty()) {
                Node unCompNode = unCompleteNodes.poll();

                // Go through every child of unComplete node
                for (Node child : unCompNode.neighbors) {

                    // If child hasn't been created, do it, and add it
                    // to the queue because it's brand new and doesn't have
                    // any children set yet
                    if (!oldtoNewNodes.containsKey(child)) {
                        Node newChild = new Node(child.val);
                        oldtoNewNodes.put(child, newChild);
                        unCompleteNodes.offer(child);
                    }

                    // Add the child to the unComplete node's list of children
                    oldtoNewNodes.get(unCompNode).neighbors.add(oldtoNewNodes.get(child));
                }
            }

            // Return complete new graph
            return newGraph;
        }
        return null;
    }
    
    
}