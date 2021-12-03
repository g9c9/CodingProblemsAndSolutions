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

 // https://leetcode.com/problems/minimum-depth-of-binary-tree/

// Depth First Search Colution
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

// Best Solution: Breadth First Search Solution
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