/**
 * Licensed Materials - Property of VK
 * 
 * (C) Copyright VK Corp. 2020. All Rights Reserved.
 * 
 */
package com.api.charging.algorithm;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.api.charging.constants.StatusEnum;
import com.api.charging.entity.ChargingStationSession;
import com.api.charging.model.ChargingStationResponse;
import com.api.charging.model.ChargingStationSummaryResponse;

/**
 * 
 * Binary Search Tree of charging station session objects
 * 
 * @author VK
 * 
 * 
 *         Sep 6, 2020
 */
// Algorithm - binary search tree (BST)
public class ChargingStationBST {

	private static final Logger logger = LoggerFactory
			.getLogger(ChargingStationBST.class);

	// static variable single_instance of type ChargingStationBST
	private static ChargingStationBST single_instance = null;

	/*
	 * Class containing left and right child of current node and Session object
	 * value
	 */
	class Node {
		ChargingStationSession chargingStationSession;
		Node left, right; // left not is FINISHED status and right node is
							// INPROGRESS

		public Node(ChargingStationSession chargingStationSession) {
			this.chargingStationSession = chargingStationSession;
			left = right = null;
		}
	}

	// Root of BST
	Node root;

	// Constructor
	public ChargingStationBST() {
		logger.info("I am single---->");
		// inserting root entry
		if (root == null) {
			final ChargingStationSession chargingSessionROOT = new ChargingStationSession();
			chargingSessionROOT.setId(
					UUID.fromString("00000000-0000-0000-0000-000000000000"));
			chargingSessionROOT.setStatus(StatusEnum.ROOT);
			insert(chargingSessionROOT);
		}
	}
	// static method to create instance of Singleton class
	public static ChargingStationBST getInstance() {
		if (single_instance == null)
			single_instance = new ChargingStationBST();

		return single_instance;
	}

	// This method mainly calls insertRecord()
	public void insert(ChargingStationSession chargingStationSession) {
		root = insertRecord(root, chargingStationSession);
	}

	/*
	 * A recursive function to insert a new session object in BST Complexity
	 * -------------O(log(n))
	 */
	synchronized Node insertRecord(Node root,
			ChargingStationSession chargingStationSession) {

		/* If the tree is empty, return a new node */
		if (root == null) {
			root = new Node(chargingStationSession);
			return root;
		}

		/* Otherwise, recur down the tree */
		if (chargingStationSession.getStatus() == StatusEnum.FINISHED) {
			root.left = insertRecord(root.left, chargingStationSession);
		} else if (chargingStationSession
				.getStatus() == StatusEnum.IN_PROGRESS) {
			root.right = insertRecord(root.right, chargingStationSession);
		}
		/* return the (unchanged) node pointer */
		return root;
	}

	/*
	 * A recursive function to search a session object in BST Complexity
	 * -------------O(log(n))
	 */
	public ChargingStationSession searchNode(UUID id) {
		return search(root, id);

	}
	// A utility function to search a given key in BST
	public ChargingStationSession search(Node root, UUID id) {

		// Base Cases: root i
		if (root == null)
			return new ChargingStationSession();

		// Status is IN_PROGRESS
		if (root.chargingStationSession.getId().equals(id)
				&& root.chargingStationSession
						.getStatus() == StatusEnum.IN_PROGRESS) {
			return root.chargingStationSession;
		}
		return search(root.right, id);
	}

	// This method mainly calls deleteRecord()
	public void deleteKey(ChargingStationSession chargingStationSession) {
		root = deleteRecord(root, chargingStationSession);
	}

	public static Node deleteRecord(Node root,
			ChargingStationSession chargingStationSession) {

		// base case, tree is empty
		if (root == null) {
			return root;
		}
		// 1.A node is in left subtree
		// set root's leftchild to result of delete(root.left...)
		else if (chargingStationSession.getStatus() == StatusEnum.FINISHED) {
			root.left = deleteRecord(root.left, chargingStationSession);
		}
		// 1.B node is in right subtree
		// set root's righth child to result of delete(root.right...)
		else if (chargingStationSession.getStatus() == StatusEnum.FINISHED) {
			root.right = deleteRecord(root.right, chargingStationSession);
		}
		// 2 found data!
		else {
			// Case 1: no child
			// just set node to null (remove it) and return it
			if (root.left == null && root.right == null) {
				root = null;
			}
			// Case 2: one child
			// 2.A: no left child
			else if (root.left == null) {
				Node temp = root;
				root = root.right;
				temp = null;
			}
			// 2.B: no right child
			else if (root.right == null) {
				Node temp = root;
				root = root.left;
				temp = null;
			}
			// Case 3: 2 children
			else {
				// get minimum element in right subtree
				// set it to `root` and remove it from its
				// original spot
				Node temp = findMin(root.right);
				root.chargingStationSession = temp.chargingStationSession;
				root.right = deleteRecord(root.right,
						temp.chargingStationSession);
			}
		}

		return root;

	}

	public static Node findMin(Node root) {
		while (root.left != null)
			root = root.left;
		return root;
	}

	public List<ChargingStationResponse> retrieveAll() {
		List<ChargingStationResponse> array = new ArrayList<ChargingStationResponse>();
		retrieveLeft(root.left, array);
		retrieveRight(root.right, array);
		array.forEach(name -> System.out.println(name));
		return array;
	}

	void retrieveLeft(Node root, List<ChargingStationResponse> array) {
		if (root != null) {
			array.add(ChargingStationResponse
					.mapper(root.chargingStationSession));
			retrieveLeft(root.left, array);
		}
	}

	void retrieveRight(Node root, List<ChargingStationResponse> array) {
		if (root != null) {
			array.add(ChargingStationResponse
					.mapper(root.chargingStationSession));
			retrieveRight(root.right, array);
		}
	}
	long stoppedCount, startedCount;
	// This method mainly calls InorderRec()
	public ChargingStationSummaryResponse retrieveAllSummary(
			LocalDateTime oneMinBefore) {
		this.stoppedCount = 0;
		this.startedCount = 0;
		stoppedCount = leftNode(root.left, oneMinBefore);
		startedCount = rightNode(root.right, oneMinBefore);
		return new ChargingStationSummaryResponse(startedCount + stoppedCount,
				startedCount, stoppedCount);
	}

	// A utility function to do traversal of BST
	long leftNode(Node root, LocalDateTime oneMinBefore) {
		if (root != null) {
			if (root.chargingStationSession.getStatus() == StatusEnum.FINISHED
					&& root.chargingStationSession.getStoppedAt()
							.isAfter(oneMinBefore))
				stoppedCount++;
			leftNode(root.left, oneMinBefore);
		}
		return stoppedCount;
	}
	long rightNode(Node root, LocalDateTime oneMinBefore) {
		if (root != null) {
			if (root.chargingStationSession
					.getStatus() == StatusEnum.IN_PROGRESS
					&& root.chargingStationSession.getStartedAt()
							.isAfter(oneMinBefore))
				startedCount++;
			rightNode(root.right, oneMinBefore);
		}
		return startedCount;
	}
}
