package com.revature.objectMapper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.revature.util.MetaModel;

public class ObjectCache<T> {
	
	private final static ObjectCache objCache = new ObjectCache();
	private Logger logger = Logger.getLogger(this.getClass());

	private final int capacity;
	private int size;
	private final Map<String, Node> hashmap;
	private final DoublyLinkedList internalQueue;
	private boolean isAllFetched = false;

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
	
	public boolean getIsAllFetched() {
		return this.isAllFetched;
	}
	
	public void isAllFetchedTrue() {
		this.isAllFetched = true;
	}
	
	public boolean updateFromCache(Object obj)  {
		
		logger.info("Updating cache - " + obj);
		
		Node node = hashmap.get(obj.getClass().getSimpleName());
		MetaModel<?> model = MetaModel.of(obj.getClass());
		
		if (node == null) {
			System.out.println("Object does not exist in cache, inserting");
			insertToCache(obj);
			return true;
		}
		
		for(Object ob : node.hashSet) {
			try {
				Field field = ob.getClass().getDeclaredField(model.getPrimaryKey().getName());
				field.setAccessible(true);
				if ((int) field.get(ob) == (int) field.get(obj)) {
					System.out.println("Object exists " + field.get(ob));
					System.out.println(obj);
					node.hashSet.remove(ob);
					node.hashSet.add((T) obj);
					internalQueue.moveNodeToFront(node);
					return true;
					
				}
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				logger.error("Something went wrong updating the cache \n" + e);
			}
			
		}
	
		internalQueue.moveNodeToFront(node);
		return false;
	}
	
	public HashMap<String, HashSet<T>> getCache() {
		logger.info("Getting all of cache");
		HashMap<String, HashSet<T>> hm = new HashMap<>();
		Iterator iterator = hashmap.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Node> me = (Map.Entry) iterator.next();
			String key = me.getKey();
			Node value = me.getValue();
			hm.put(key, value.hashSet);
		}
		
		return hm;
	}
	
	
	public boolean removeFromCacheId(Class clazz, int id) {
		Node node = hashmap.get(clazz.getSimpleName());
		MetaModel<?> model = MetaModel.of(clazz);
		
		if (node == null) {
			return false;
		}
		
		for(Object ob : node.hashSet) {
			try {
				Field field = ob.getClass().getDeclaredField(model.getPrimaryKey().getName());
				field.setAccessible(true);
				if ((int) field.get(ob) == id) {
					logger.info("Object in cache to remove " + field.get(ob));
					
					node.hashSet.remove(ob);
					internalQueue.moveNodeToFront(node);
					return true;
				}
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				logger.error("Something wrong happened trying to delete from cache \n " + e);
			}
			
		}
		
		return false;
	}

	public List<T> getFromAllCache(Class clazz) {
		logger.info("Getting objects<"+ clazz.getSimpleName() +"> from cache");
		
		Node node = hashmap.get(clazz.getSimpleName());
		List<T> objList = new ArrayList<T>();
		
		if (node == null) {
			return null;
		}
		internalQueue.moveNodeToFront(node);
		objList.addAll(hashmap.get(clazz.getSimpleName()).hashSet);
		return objList;
	}
	
	public Object getFromCacheById(Class clazz, int id) {
		Node node = hashmap.get(clazz.getSimpleName());
		MetaModel<?> model = MetaModel.of(clazz);
		
		
		if (node == null) {
			return null;
		}
		
		for(Object ob : node.hashSet) {
			try {
				Field field = ob.getClass().getDeclaredField(model.getPrimaryKey().getName());
				field.setAccessible(true);
				if ((int) field.get(ob) == id) {
					System.out.println("Object exists " + field.get(ob));
					internalQueue.moveNodeToFront(node);
					return ob;
				}
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				logger.error("Something went wrong getting all from cache \n" + e);
			}
			
		}
		
		return null;
	}

	public void insertToCache(Object obj) {
		Node currentNode = hashmap.get(obj.getClass().getSimpleName());
	
		logger.info("Inserting into cache: " + obj.getClass().getSimpleName());
		
		if (currentNode != null) {
			
			currentNode.hashSet.add((T) obj);
			internalQueue.moveNodeToFront(currentNode);
			
			// Test array nodes
			for (Object objHash : currentNode.hashSet) {
				System.out.println(objHash);
			}
			return;
		}

		if (size == capacity) {
			String rearNodeKey = internalQueue.getRearKey();
			internalQueue.removeNodeFromRear();
			hashmap.remove(rearNodeKey);
			size--;
		}
		
		Node node = new Node(obj.getClass().getSimpleName());
		node.hashSet.add((T) obj);
		internalQueue.addNodeToTheFront(node);
		hashmap.put(obj.getClass().getSimpleName(), node);
		size++;

	}
	
	public void insertToCacheAll(List<Object> objList) {
		String className = objList.get(0).getClass().getSimpleName();
		Node currentNode = hashmap.get(className);
	
		logger.info("Inserting all objects into cache: " + className);
		
		if (currentNode != null) {
			
			currentNode.hashSet.add((T) objList);
			internalQueue.moveNodeToFront(currentNode);
			
			return;
		}

		if (size == capacity) {
			String rearNodeKey = internalQueue.getRearKey();
			internalQueue.removeNodeFromRear();
			hashmap.remove(rearNodeKey);
			size--;
		}
		
		Node node = new Node(className);
		node.hashSet.add((T) objList);
		internalQueue.addNodeToTheFront(node);
		hashmap.put(className, node);
		size++;

	}
	

	// Node to set LRU config 
	private class Node {
		String key;
		HashSet<T> hashSet;
		Node next, prev;

		public Node(final String key) {
			this.key = key;
			this.hashSet = new HashSet<>();
			this.next = null;
			this.prev = null;
		}
	}
	
	// Doubly linked list to use LRU cache
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
