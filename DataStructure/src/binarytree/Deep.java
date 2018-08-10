package binarytree;

import binarytree.LevalTrav.TreeNode;

public class Deep {
//二叉树的深度
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			
	}
	public static int level(TreeNode root) {  
        if (root == null)  
            return 0;  
        return level(root.left) + 1 > level(root.right) + 1 ? level(root.left) + 1  
                : level(root.right) + 1;  
    }  

}
