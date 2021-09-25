package com.revature.objectMapper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class ObjectCache {
	
	private final static ObjectCache objCache = new ObjectCache();
	

	private final int capacity;
	private int size;
	private final Map<String, Node> hashmap;
	private final DoublyLinkedList internalQueue;

	private ObjectCache() {
		this.capacity = 3;
		this.hashmap = new HashMap<>();
		this.internalQueue = new DoublyLinkedList();
		this.size = 0;
	}
	
	public static ObjectCache getInstance() {
		return objCache;
	}
		
	public Map<String, Node> getAllCacheTest() {
		return hashmap;
	}

	public HashSet<Object> getFromCache(Object obj) {
		Node node = hashmap.get(obj.getClass().getSimpleName());
		if (node == null) {
			return null;
		}
		internalQueue.moveNodeToFront(node);
		return hashmap.get(obj.getClass().getSimpleName()).hashSet;
	}

	public void insertToCache(Object value) {
		Node currentNode = hashmap.get(value.getClass().getSimpleName());
		if (currentNode != null) {
			currentNode.hashSet.add(currentNode);
			internalQueue.moveNodeToFront(currentNode);
			return;
		}

		if (size == capacity) {
			String rearNodeKey = internalQueue.getRearKey();
			internalQueue.removeNodeFromRear();
			hashmap.remove(rearNodeKey);
			size--;
		}
		
		Node node = new Node(value.getClass().getSimpleName(), value);
		internalQueue.addNodeToTheFront(node);
		hashmap.put(value.getClass().getSimpleName(), node);
		size++;

	}

	// Node containing
	private class Node {
		String key;
		HashSet<Object> hashSet = new HashSet<>();
		Node next, prev;

		public Node(final String key, Object value) {
			this.key = key;
			this.hashSet.add(value);
			this.next = null;
			this.prev = null;
		}
	}

	private class DoublyLinkedList {
		private Node front, rear;

		public DoublyLinkedList() {
			front = rear = null;
		}

		private void addNodeToTheFront(final Node node) {
			if (rear == null) {
				front = rear = node;
				return;
			}
			node.next = front;
			front.prev = node;
			front = node;
		}

		public void moveNodeToFront(final Node node) {
			if (front == node) {
				return;
			}

			if (node == rear) {
				rear = node.prev;
				rear.next = null;
			} else {
				node.prev.next = node.next;
				node.next.prev = node.prev;
			}

			node.prev = null;
			node.next = front;
			front.prev = node;
			front = node;
		}

		private void removeNodeFromRear() {

			if (rear == null) {
				return;
			}

			System.out.println("Deleting key: " + rear.key);

			if (front == rear) {
				front = rear = null;
			} else {
				rear = rear.prev;
				rear.next = null;
			}

		}

		private String getRearKey() {
			return rear.key;
		}
	}

}
