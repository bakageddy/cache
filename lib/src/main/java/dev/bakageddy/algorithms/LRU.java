package dev.bakageddy.cache;

import java.util.HashMap;
import java.util.Optional;

import dev.bakageddy.cache.CacheAlgorithm;

// Not Thread Safe
public class LRU<Key, Value> implements CacheAlgorithm<Key, Value> {
	class Node<Key, Value> {
		private Key key;
		private Value val;
		public Node<Key, Value> prev;
		public Node<Key, Value> next;

		public Node(Key key, Value val, Node prev, Node next) {
			this.key = key;
			this.val = val;
			this.next = next;
			this.prev = prev;
		}

		public Node(Key key, Value val) {
			this(key, val, null, null);
		}

		// TODO: implementation
		public static <Key,Value> remove_self(Node<Key, Value> node) {
		}

		// TODO: implementation
		public static <Key,Value> insert_before(Node<Key, Value> before, Node<Key, Value> node) {
		}
	}

	private HashMap<Key, Node<Key, Value>> map;
	private Node<Key, Value> head; // Insertion helper;
	private Node<Key, Value> tail; // Deletion helper;

	/*
	 *	This method is not thread safe
	 */
	@Override
	public Optional<Value> put(Key key, Value val) {
		Optional<Value> opt_val = Optional.empty();
		if (map.containsKey(key)) {
			Node<Key, Value> node = map.get(key);
			Node.remove_self(node);
			opt_val = Optional.of(node.val);
		}

		Node<Key, Value> prev = head.prev;
		Node<Key, Value> node = new Node(key, val);
		Node.insert_before(prev, node);
		map.put(key, val);
		return opt_val;
	}

	/*
	 *	This method is not thread safe
	 */
	@Override
	public Optional<Value> get(Key key) {
		if (!map.containsKey(key)) {
			return Optional.empty();
		}

		Node<Key, Value> node = map.get(key);
		Node.remove_self(node);
		Node.insert_before(head, node);

		return Optional.empty();
	}

	// This method is not thread safe
	@Override
	public void evict(Key key) {
		if (!map.containsKey(key)) {
			return;
		}

		Node<Key, Value> node = map.get(key);
		map.remove(key);
		Node.remove_self(node);
		return;
	}

	@Override
	public void flush() {
		return flush(false);
	}

	@Override
	public void flush(bool stale) {
	}
}
