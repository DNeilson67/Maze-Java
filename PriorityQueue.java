import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class PriorityQueue<T extends Comparable<T>> {

  private int size = 0;
  private int capacity = 0;
  private List<Node> heap;
  private Map<Node, TreeSet<Integer >> map = new HashMap<>();

  public PriorityQueue() {
    this(1);
  }

  public PriorityQueue(int sz) {
    heap = new ArrayList<>(sz);
  }

  public int size() {
    return size;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public void clear() {
    for(int i = 0; i< capacity; i++)
      heap.set(i, null);
    size = 0;
    map.clear();
  }


  // Polls an element from the priority queue.
  // Make sure the queue is not empty before calling.
  public Node poll() {
    return removeAt(0);
  }

  public boolean contains(Node node){
    if(node == null) return false;
    return map.containsKey(node);
  }

  // Adds a none null element to the priority queue
  public void add(Node node) {
    if (node == null) throw new IllegalArgumentException("No null elements please :)");
    if(size < capacity){
      heap.set(size, node);
    }else {
      heap.add(node);
      capacity++;
    }

    mapAdd(node, size);
    swim(size);
    size++;
  }

  private boolean less(int i, int j){
    Node node1 = heap.get(i);
    Node node2 = heap.get(j);

    return node1.distance <= node2.distance;
  }
  private void sink(int i) {
    while(true){
      int left = 2*i+1;
      int right = 2*i+2;
      int smallest  = left;

      if (right < size && less(right, left))
        smallest = right;

      if( left>= size || less(i, smallest)) break;

      swap(smallest, i);
      i = smallest;
    }
  }

  private void swim(int i) {
    int parent = (i-1)/2;
    while (i > 0 && less(i, parent)){
      swap(parent, i);
      i = parent;

      parent = (i-1)/2;
    }

  }


  public boolean remove(Node i){
    if(i == null) return false;

    Integer index = mapGet(i);
    if(index != null) removeAt(index);
    return index != null;
  }

  private Node removeAt(int i){
    if(isEmpty()) return null;

    size--;
    Node data = heap.get(i);
    swap(i, size);

    heap.set(size, null);
    mapRemove(data, size);

    if(i == size) return data;

    Node elem = heap.get(i);

    sink(i);
    if(heap.get(i).equals(elem)){
      swim(i);
    }
    return data;
  }


  private void swap(int i, int j) {
    Node node1 = heap.get(i);
    Node node2 = heap.get(j);

    heap.set(i, node2);
    heap.set(j, node1);

    mapSwap(node1, node2, i, j);
  }

  public boolean isMinHeap(int i){
    if(i >= size) return true;

    int left = 2*i+1;
    int right = 2*i+2;

    if( left < size && !less(i, left)) return false;
    if( right < size && !less(i, left)) return false;

    return isMinHeap(left) && isMinHeap(right);
  }

  private void mapAdd(Node elem, int index){
    TreeSet <Integer> set = map.get(elem);

    if(set == null){
      set = new TreeSet<>();
      set.add(index);
      map.put(elem, set);
    }else set.add(index);
  }

  private void mapRemove(Node elem, int index){
    TreeSet <Integer> set = map.get(elem);
    set.remove(index);
    if(set.size() == 0) map.remove(elem);
  }

  private Integer mapGet(Node elem){
    TreeSet <Integer> set = map.get(elem);
    if(set != null) return set.last();
    return null;
  }

  private void mapSwap(Node node1, Node node2, int node1Index, int node2Index){
    TreeSet <Integer> set1 = map.get(node1);
    TreeSet <Integer> set2 = map.get(node2);

    set1.remove(node1Index);
    set2.remove(node2Index);

    set1.add(node2Index);
    set2.add(node1Index);
  }
}