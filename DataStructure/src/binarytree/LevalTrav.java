package binarytree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

//import jianzhi_offer.Offer4.TreeNode;
//层序遍历



public class LevalTrav {

	 //����������
	  public class TreeNode {
	      int val;
	     TreeNode left;
	     TreeNode right;
	      TreeNode(int x) { val = x; 
	      }
	  }
	 
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public static ArrayList<Integer> PrintFromTopToBottom(TreeNode root) {
		ArrayList<Integer> arr=new ArrayList<Integer>();
		if(root==null) return arr;
     
        Queue<TreeNode> q=new ArrayDeque<TreeNode>();
        q.add(root);
        TreeNode cur;
       
        while(!q.isEmpty())
        {
        	cur=q.peek();
        	arr.add(cur.val);
        	if(cur.left!=null)
        		q.add(cur.left);
        	if(cur.right!=null)
        		q.add(cur.right);
        	q.poll();
        }
		return arr;
    }
}
