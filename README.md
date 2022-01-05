<details>
<summary>Two Pointers</summary>
<p>
    
# [Product of Array Except Self](https://leetcode.com/problems/product-of-array-except-self/)

```Java
public int[] productExceptSelf(int[] nums) {
    int[] output = new int[nums.length];
    for (int i = 0; i < output.length; i++) {
        output[i] = 1;
    }

    // Stores multiplication of values from left of array
    int product_front = nums[0];
    // Stores multiplaction of values from right of array
    int product_back = nums[nums.length - 1];
    for (int i = 1, j = nums.length - 2; i < nums.length && j >= 0; i++, j--) {
        // Multiply the output[i] with the product of all left side of output[i]
        output[i] *= product_front;
        // Multiply the output[j] with the product of all right side of output[j]
        output[j] *= product_back;
        // Update product_front to store product of left side up to pointer i
        product_front *= nums[i];
        // Update product_front to store product of right side up to pointer j
        product_back *= nums[j];
    }

    return output;
}
```
# [Find the Duplicate Number](https://leetcode.com/problems/find-the-duplicate-number/)

```Java
public int findDuplicate(int[] nums) {
    // Initially not set slow and fast to be the same
    // by moving it one ahead so rest of code executes
    int slow = nums[nums[0]];
    int fast = nums[nums[nums[0]]];

    // Keep moving fast and slow accordinly until they
    // are at same node
    while(slow != fast) {
        slow = nums[slow];
        fast = nums[nums[fast]];
    }

    // Set slow back to start
    slow = nums[0];

    // Keep moving fast and slow at both slow speed until
    // they are at same node
    while(slow != fast) {
        slow = nums[slow];
        fast = nums[fast];
    }

    // Both fast and node reached the first node of the cycle
    // which is the repeated node since two nodes point to
    // it
    return slow;
}
```
</p>
</details>

<details><summary>Breadth First Search</summary>
<p>

# [Clone Graph](https://leetcode.com/problems/clone-graph/)

```Java
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
```
    
# [Minimum Depth Binary Tree](https://leetcode.com/problems/minimum-depth-of-binary-tree/)
## (Best Solution) Breadth First Search

```Java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
 
 class MinDepthBinaryTreeBFS {
    public int minDepth(TreeNode root) {
        if (root == null)
            return 0;
        
        // Stores each level of the tree
        Queue<TreeNode> q = new LinkedList<TreeNode>();
        // Put in first level into queue
        q.offer(root);
        int depth = 1;
        
        // While there is still levels in the tree to traverse
        while(!q.isEmpty()) {
            // Get the exact number of nodes in the current layer to
            // traverse because adding it's children will change the size
            // of the queue
            int numNodesInLayer = q.size();

            // Traverse through the nodes in the layer in the queue
            // and check to see if there is a leaf, otherwise add it's
            // children to the queue to represent the new layer.
            for(int i = 0; i < numNodesInLayer; i++) {
                TreeNode node = q.poll();

                //If leaf is found, that is the minimum depth
                if(node.left == null && node.right == null) {
                    return depth;
                }
                if (node.left != null) {
                    q.offer(node.left);
                }
                if (node.right != null) {
                    q.offer(node.right);
                }
            }
            depth++;
        }

        // This theoretically shouldn't execute but it is added
        // so the method has a return statement
        return depth;
    }
}
```

## Depth First Search

```Java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
 
class MinDepthBinaryTree {
    public int minDepth(TreeNode root) {
        if (root != null) {
            return minDepth(root, 0);
        }
        return 0;
    }
    
    private int minDepth(TreeNode node, int numNodes) {
        // If node is null, that means we never found the leaf node
        // so this path shouldn't even be considered, that's why
        // Integer.MAX_VALUE is returned
        if (node == null) {
            return Integer.MAX_VALUE;
        }

        //If leaf is reached, return the depth
        else if (node.left == null && node.right == null) {
            return numNodes + 1;
        }

        // Return the smallest depth from either the left subtree or right subtree
        return Math.min(minDepth(node.left, numNodes + 1), minDepth(node.right, numNodes + 1));
    }
}
```
                
# [Average of Levels in Binary Tree](https://leetcode.com/problems/average-of-levels-in-binary-tree/)
```Java
public List<Double> averageOfLevels(TreeNode root) {
    List<Double> averageLevels = new ArrayList<Double>();
    // Store each layer of nodes in tree
    Queue<TreeNode> q = new LinkedList<TreeNode>();
    // Adding first layer of tree
    q.offer(root);

    // While there are still layers to compute average
    while(!q.isEmpty()) {
        // Find number of nodes in layer so we can process
        // only those nodes, since we are adding the nodes
        // children to the queue, changing the size
        int numNodeLevel = q.size();
        double sum = 0;
        // Go through all nodes in layer, add its value to
        // sum, and add its children to queue if it has
        // children
        for(int i = 0; i < numNodeLevel; i++) {
            TreeNode node = q.poll();
            sum += node.val;
            if (node.left != null) {
                q.offer(node.left);
            }
            if (node.right != null) {
                q.offer(node.right);
            }
        }
        // Compute average by dividing sum with
        // number of nodes in layer and adding to lust
        averageLevels.add(sum / numNodeLevel);
    }
    return averageLevels;
}
```
                
</p>
</details>
