<details>
<summary>Arrays</summary>
<p>

# [Contains Duplicate](https://leetcode.com/problems/contains-duplicate/)
    
```Java
public boolean containsDuplicate(int[] nums) {
    HashSet<Integer> set = new HashSet<Integer>();
    for(int i = 0; i < nums.length; i++) {
        if(set.contains(nums[i]))
            return true;
        set.add(nums[i]);
    }
    return false;
}
```

# [Missing Number](https://leetcode.com/problems/missing-number/)

```Java
public int missingNumber(int[] nums) {
    // A number that stores sum if all numbers existed
    int sum = 0;

    // Create the sum
    for(int i = 0; i < nums.length; i++) {
        sum += i;

        // Loop through nums and subtract each found number
        // from sum
        sum -= nums[i];
    }
    sum += nums.length;

    // Final remaining number is the missing number
    return sum;
}
```

</p>
</details>

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

<details><summary>Binary Search</summary>
<p>

# [Find Minimum in Rotated Sorted Array](https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/)

```Java
public int findMin(int[] nums) {        
    if (nums.length > 1) {
        // Binary seach to find the pivot, where
        // m is the index pointer to find the pivot
        int l = 0;
        int r = nums.length - 1;
        int m = (l + r) / 2;

        // while midpoint > left of midpoint
        // (take into account boundary)
        while(nums[m] > nums[m-1 < 0 ? nums.length - 1 : m-1]) {
            // left is midpoint if right is < midpoint
            if (nums[r] < nums[m])
                l = m + 1;
            // right is midpoint if left is < midpoint
            else
                r = m - 1;
            // Calculate new midpoint to be between left and right
            m = (l + r) / 2;
        }
        return nums[m];
    }
    return nums[0];
}
```

# [Search in Rotated Sorted Array](https://leetcode.com/problems/search-in-rotated-sorted-array/)

```Java
public int search(int[] nums, int target) {
    int l = 0;
    int r = nums.length - 1;

    while(l <= r) {
        int m = (l + r) / 2;

        if(target == nums[m])
            return m;

        // Inside left portion of array
        if(nums[m] >= nums[l]) {
            // Two conditions that show that target
            // is in right side
            if(target > nums[m] || target < nums[l])
                l = m + 1;
            // On left side
            else
                r = m - 1;
        }
        // Inside right portion of array
        else {
            // Two conditions that show that target is
            // in left side
            if(target < nums[m] || target > nums[r])
                r = m - 1;
            // On right side
            else
                l = m + 1;
        }
    }
    return -1;
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

                
<details><summary>Dynamic Programming</summary>
<p>
                
# [Coin Change](https://leetcode.com/problems/coin-change/)
                
```Java
public int coinChange(int[] coins, int amount) {
    if(amount > 0) {
        // Memoization by storing minimum number of coins
        // for each coin amount from 0 to amount
        int[] memo = new int[amount + 1];

        // Setting the minimum coins of amount of 1 to amount
        // to be largest value, but memo[0] to have 0 coins as
        // solution
        for(int i = 1; i < memo.length; i++) {
            memo[i] = amount + 1;
        }

        // Bottom up Dynamic Programming, finding minimum coins
        // starting from 1 and going to amount
        for(int a = 1; a <= amount; a++) {
            // Go through every coin option
            for(int c = 0; c < coins.length; c++) {
                // Check if coin is not greater then amount
                // from amount for loop
                if(coins[c] <= a) {
                    // Change solution if new computed solution
                    // with current coin is smaller
                    memo[a] = Math.min(memo[a], 1 + memo[a - coins[c]]);
                }
            }
        }

        // If solution to amount did not change return -1
        // otherwise return solution
        return memo[amount] <= amount ? memo[amount] : -1;
    }
    // If amount is 0, no coins can make 0
    return 0;
}
```

# [Target Sum](https://leetcode.com/problems/target-sum/)

```Java
public int findTargetSumWays(int[] nums, int target) {
    // Memoization HashMap to store index and associated
    // sum as a pair and the number of solutions found
    // for that pair
    HashMap<Pair<Integer, Integer>, Integer> memo = new HashMap<Pair<Integer, Integer>, Integer>();

    // Call DFS at the beginning
    return backtrack(0, 0, target, nums, memo);
}

// DFS with memoization
private int backtrack(int i, int total, int target, int[] nums, HashMap<Pair<Integer, Integer>, Integer> memo) {
    // Generate key pair for memo
    Pair<Integer, Integer> key = new Pair<Integer, Integer>(i, total);
    // If last index is reached, time to check if sum is
    // target and return 1 if it is true
    if (i == nums.length)
        return total == target ? 1 : 0;
    // If the key pair exists, that's means it's already
    // been solved, return the solutions at the key from memo
    else if (memo.containsKey(key))
        return memo.get(key);
    // Solve the numSol for key pair, accounting for adding
    // and subtracting num
    // Insert key pair and solution into memo
    // Return solution
    else {
        int numSol =  backtrack(i + 1, total + nums[i], target, nums, memo) + backtrack(i + 1, total - nums[i], target, nums, memo);
        memo.put(key, numSol);
        return numSol;
    }            
}
```

# [House Robber](https://leetcode.com/problems/house-robber/)

```Java
public int rob(int[] nums) {
    // Maximum money solution at index -1
    // stores the money solution for two
    // indices before the target index
    int left = 0;
    // Maximum money solution at index 0
    // Stores money solution one index before
    // target index
    int right = nums[0];

    // Bottom Up Dynamic Programming of solving
    // max money from left of array and working up
    for(int i = 1; i < nums.length; i++) {
        // Calculate max money for target index
        // This enforces the rule of not picking adjacent
        // houses
        int maxMoney = Math.max(right, left + nums[i]);
        // Update solutions because next target index don't
        // need left but needs maxMoney
        left = right;
        right = maxMoney;
    }

    // Contains the final solution for entire houses
    return right;
}
```

# [Maximum Product Subarray](https://leetcode.com/problems/maximum-product-subarray/)

```Java
public int maxProduct(int[] nums) {
    // Stores largest product
    int result = nums[0];

    // Stores largest product as we move
    // from left to right of array
    int curMax = 1;
    // Stores smallest product as we move
    // from left to right of array
    // This is used to deal with negative
    // numbers multiplied by another negative
    // which can create a possible new curMax
    int curMin = 1;

    // Go through each index in array
    for(int i = 0; i < nums.length; i++) {
        // If zero is encountered, reset and ignore
        // the zero, result still contains latest curMax
        if(nums[i] == 0) {
            curMax = 1;
            curMin = 1;
        }

        // Stores old curMax plus extra calculation
        // used for calculating curMax and curMin
        int temp = curMax * nums[i];
        // Three largest possible values could now become new curMax
        curMax = Math.max(Math.max(temp, curMin * nums[i]), nums[i]);
        // Three smallest possible values could now become new curMin
        curMin = Math.min(Math.min(temp, curMin * nums[i]), nums[i]);
        // Update result
        result = Math.max(result, curMax);
    }

    return result;
}
```

</p>
</details>
                
