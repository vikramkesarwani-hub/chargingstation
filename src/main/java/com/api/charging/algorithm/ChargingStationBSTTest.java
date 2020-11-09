/**
 * Licensed Materials - Property of VK
 * 
 * (C) Copyright VK Corp. 2020. All Rights Reserved.
 * 
 */
package com.api.charging.algorithm;

import java.util.UUID;

import com.api.charging.constants.StatusEnum;
import com.api.charging.entity.ChargingStationSession;

/**
 * 
 * Binary Search Tree of charging station session objects
 * @author VK
 * 
 * 
 * Sep 6, 2020
 */
//Java program to demonstrate insert operation in binary search tree 
class ChargingStationBSTTest { 

	/* Class containing left and right child of current node and key value*/
	class Node { 
		ChargingStationSession chargingStationSession; 
		Node left, right; 

		public Node(ChargingStationSession chargingStationSession) { 
			this.chargingStationSession = chargingStationSession; 
			left = right = null; 
		} 
	} 

	// Root of BST 
	Node root; 

	// Constructor 
	ChargingStationBSTTest() { 
			if(root == null) {
				final ChargingStationSession chargingSessionROOT = new ChargingStationSession();
				chargingSessionROOT.setId(UUID.fromString("00000000-0000-0000-0000-000000000000")); 
				chargingSessionROOT.setStatus(StatusEnum.ROOT);
				insert(chargingSessionROOT);
			}
	} 

	// This method mainly calls insertRec() 
	void insert(ChargingStationSession chargingStationSession) { 
	root = insertRec(root, chargingStationSession); 
	} 
	
	/* A recursive function to insert a new key in BST */
	synchronized Node insertRec(Node root, ChargingStationSession chargingStationSession) { 
		
		
		/* If the tree is empty, return a new node */
		if (root == null) { 
			root = new Node(chargingStationSession); 
			
			return root; 
		} 

		/* Otherwise, recur down the tree */
		if (chargingStationSession.getStatus() == StatusEnum.FINISHED ) {
			System.out.println("Finished------------>"+root.chargingStationSession.getId());
			root.left = insertRec(root.left, chargingStationSession); 
		}
		else if (chargingStationSession.getStatus() == StatusEnum.IN_PROGRESS) {
			System.out.println("In Progress------------>"+root.chargingStationSession.getId());
			root.right = insertRec(root.right, chargingStationSession);
		}

		/* return the (unchanged) node pointer */
		return root; 
	} 

	// This method mainly calls deleteRecord() 
    void deleteKey(ChargingStationSession chargingStationSession) 
    { 
        root = deleteRecord(root, chargingStationSession); 
    } 
  
    /* A recursive function to delete in BST */
    Node deleteRecord(Node root, ChargingStationSession chargingStationSession) 
    { 
        /* Base Case: If the tree is empty */
        if (root == null)  return root; 
  
        if (chargingStationSession.getStatus() == StatusEnum.IN_PROGRESS && root.chargingStationSession
				.getId() == chargingStationSession.getId()) 
            root.right = deleteRecord(root.right, chargingStationSession); 
  
        // if key is same as root's key, then This is the node 
        // to be deleted 

		return root;
    } 
  
    
	void retrieveAll() {
		retrieve(root);
	}
	
		void retrieve(Node root) {
	        if (root != null) { 
	        	retrieve(root.left); 
	            retrieve(root.right); 
	            if(root.chargingStationSession.getStatus() != StatusEnum.ROOT)
	            System.out.println("retrieve-------------->"+root.chargingStationSession.getId()); 
	        } 
		}
	
	// This method mainly calls InorderRec() 
	void inorder() { 
		inorderleft(root);
		inorderright(root);
	} 

	// A utility function to do inorder traversal of BST 
	int counter = -1;
	void inorderleft(Node root) { 
		if (root != null) { 
			
			inorderleft(root.left);
			counter++;
			System.out.println(root.chargingStationSession.getId() + " COunterFinished--->" +counter); 
		}
	} 
	int i = -1; 
	void inorderright(Node root) { 
			if (root != null ) {
				
				inorderright(root.right);
				i++;
				System.out.println(root.chargingStationSession.getId() + " COunterInProgress--->" +i); 
		}
	} 

	public Node searchNode(ChargingStationSession chargingSession) { 
		return search(root, chargingSession);
		
	} 
	// A utility function to search a given key in BST 
	public Node search(Node root, ChargingStationSession chargingSession) 
	{ 
	    // Base Cases: root is null or key is present at root 
	    if (root==null || root.chargingStationSession.getId()==chargingSession.getId()) 
	        return root; 
	  
	    // val is greater than root's key 
	    if (chargingSession.getStatus() == StatusEnum.FINISHED ) 
	        return search(root.left, chargingSession); 
	  
	    // val is less than root's key 
	    return search(root.right, chargingSession); 
	} 
	
	// Driver Program to test above functions 
	public static void main(String[] args) { 
		ChargingStationBSTTest tree = new ChargingStationBSTTest(); 

		/* Let us create following BST 
		 ROOT 
		/	 \ 
		F	 IN
		/     \ 
	   F     IN  */

		final ChargingStationSession chargingSession = new ChargingStationSession();
		chargingSession.setId(UUID.randomUUID()); 
		chargingSession.setStatus(StatusEnum.IN_PROGRESS);
		tree.insert(chargingSession); 
		final ChargingStationSession chargingSession1 = new ChargingStationSession();
		chargingSession1.setId(UUID.randomUUID()); 
		chargingSession1.setStatus(StatusEnum.FINISHED);
		tree.insert(chargingSession1);
		final ChargingStationSession chargingSession2 = new ChargingStationSession();
		chargingSession2.setId(UUID.randomUUID()); 
		chargingSession2.setStatus(StatusEnum.IN_PROGRESS);
		tree.insert(chargingSession2);
		final ChargingStationSession chargingSession3 = new ChargingStationSession();
		chargingSession3.setId(UUID.randomUUID()); 
		chargingSession3.setStatus(StatusEnum.FINISHED);
		tree.insert(chargingSession3);
		final ChargingStationSession chargingSession4 = new ChargingStationSession();
		chargingSession4.setId(UUID.randomUUID()); 
		chargingSession4.setStatus(StatusEnum.IN_PROGRESS);
		tree.insert(chargingSession4);
		final ChargingStationSession chargingSession5 = new ChargingStationSession();
		chargingSession5.setId(UUID.randomUUID()); 
		chargingSession5.setStatus(StatusEnum.FINISHED);
		tree.insert(chargingSession5);
		final ChargingStationSession chargingSession6 = new ChargingStationSession();
		chargingSession6.setId(UUID.randomUUID()); 
		chargingSession6.setStatus(StatusEnum.IN_PROGRESS);
		tree.insert(chargingSession6);
		final ChargingStationSession chargingSession7 = new ChargingStationSession();
		chargingSession7.setId(UUID.randomUUID()); 
		chargingSession7.setStatus(StatusEnum.IN_PROGRESS);
		tree.insert(chargingSession7);
		final ChargingStationSession chargingSession8 = new ChargingStationSession();
		chargingSession8.setId(UUID.randomUUID()); 
		chargingSession8.setStatus(StatusEnum.IN_PROGRESS);
		tree.insert(chargingSession8);
		// print inorder traversal of the BST 
		tree.deleteKey(chargingSession7);
		chargingSession7.setStatus(StatusEnum.FINISHED);
		tree.insert(chargingSession7);
		tree.inorder(); 
		Node node= tree.searchNode(chargingSession6);
		System.out.println(node.chargingStationSession.getId().toString());
		tree.retrieveAll();
	} 
} 
//This code is contributed by Ankur Narain Verma 
