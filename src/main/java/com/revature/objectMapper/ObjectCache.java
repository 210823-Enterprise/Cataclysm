package com.revature.objectMapper;

import java.util.HashMap;
import java.util.Map;

public class ObjectCache<T> {

	private final int capacity;
	private int size;
	private final Map<String, Node> hashmap;
	private final DoublyLinkedList internalQueue;

	public ObjectCache(final int capacity) {
		this.capacity = capacity;
		this.hashmap = new HashMap<>();
		this.internalQueue = new DoublyLinkedList();
		this.size = 0;
	}

	public T get(final String key) {
		Node node = hashmap.get(key);
		if (node == null) {
			return null;
		}
		internalQueue.moveNodeToFront(node);
		return hashmap.get(key).value;
	}

	public void put(final String key, final T value) {
		Node currentNode = hashmap.get(key);
		if (currentNode != null) {
			currentNode.value = value;
			internalQueue.moveNodeToFront(currentNode);
			return;
		}

		if (size == capacity) {
			String rearNodeKey = internalQueue.getRearKey();
			internalQueue.removeNodeFromRear();
			hashmap.remove(rearNodeKey);
			size--;
		}
		
		Node node = new Node(key, value);
		internalQueue.addNodeToTheFront(node);
		hashmap.put(key, node);
		size++;

	}

	// Node containing
	private class Node {
		String key;
		T value;
		Node next, prev;

		public Node(final String key, final T value) {
			this.key = key;
			this.value = value;
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
