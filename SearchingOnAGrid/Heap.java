package SearchingOnAGrid;

import java.util.Comparator;

public class Heap<T> implements PriorityQueue<T>{

    private static class Node<T>{
        Node<T> left, right, parent;
        T data;

        public Node(T data, Node<T> left, Node<T> right, Node<T> parent){
            this.data = data;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }
    }

    private int size;
    private Node<T> root, last;
    private Comparator<T> comparator;

    public Heap(){
        this(null, false);
    }

    public Heap(boolean maxHeap){
        this(null, maxHeap);
    }

    public Heap(Comparator<T> comparator){
        this(comparator, false);
    }

    public Heap(Comparator<T> comparator, boolean maxHeap){
        if(comparator != null){
            this.comparator = comparator;
        }
        else {
            this.comparator = new Comparator<T>() {
                @Override
                public int compare(T o1, T o2){
                    return Heap.this.comparator.compare(o2, o1);
                }
            };
        }
    }

    private void swap(Node<T> node1, Node<T> node2) {
        T temp = node1.data;
        node1.data = node2.data;
        node2.data = temp;
    }

    private void bubbleUp(Node<T> curNode){
        if (curNode == root) {
            return;
        }

        T myData = curNode.data;
        T parentData = curNode.parent.data;

        if (comparator.compare(myData, parentData) < 0){
            swap(curNode, curNode.parent);
            bubbleUp(curNode.parent);
        }
    }

    // Bubbles node down where it should be in the miniHeap
    private void bubbleDown(Node<T> curNode){
        if (curNode.left == null){
            return;
        }
        else if(curNode.right == null){
            if (comparator.compare(curNode.left.data, curNode.data) < 0){
                swap(curNode, curNode.left);
                bubbleDown(curNode.left);
            }
        }
        else{
            if (comparator.compare(curNode.left.data, curNode.right.data) < 0){
                if (comparator.compare(curNode.left.data, curNode.data) < 0){
                    swap(curNode, curNode.left);
                    bubbleDown(curNode.left);
                }
            }
            else{
                if (comparator.compare(curNode.right.data, curNode.data) < 0){
                    swap(curNode, curNode.right);
                    bubbleDown(curNode.right);
                }
            }
        }
    }

    // adds an item to the priority Queue
    public void offer(T item){
        if (size == 0){
            root = new Node<T>(item, null, null, null);
            last = root;
            size++;
            return;
        }
        if(size % 2 == 0){
            last.parent.right = new Node<T>(item, null, null, last.parent);
            size++;
            last = last.parent.right;
            bubbleUp(last);
            return;
        }
        else {
            Node<T> curNode = last;
            while(curNode != root && curNode == curNode.parent.right){
                curNode = curNode.parent;
            }
            if (curNode != root){
                curNode = curNode.parent.right;
            }
            while (curNode.left != null){
                curNode = curNode.left;
            }
            curNode.left = new Node<T>(item, null, null, curNode);
            last = curNode.left;
        }
        size++;
        bubbleUp(last);
        return;
    }

    //removes the item of highest priority from the priority queue
    public T poll(){
        if(size == 0){
            return null;
        }
        if (size == 1){
            T removed = root.data;
            root = null;
            last = null;
            size--;
            return removed;
        }
        swap(root, last);
        if(size % 2 == 1){
            T removed = last.data;
            last.parent.right = null;
            last = last.parent.left;
            bubbleDown(root);
            size--;
            return removed;
        }
        else{
            T removed = last.data;
            Node<T> curNode = last;
            while(curNode != root && curNode == curNode.parent.left){
                curNode = curNode.parent;
            }
            if(curNode != root){
                curNode = curNode.parent.left;
            }
            while(curNode.right != null){
                curNode = curNode.right;
            }
            last.parent.left = null;
            last = curNode;
            size--;
            bubbleDown(root);
            return removed;
        }
    }

    //returns the size of the priority queue
    public int size(){
        return size;
    }

    //returns the data from the highest priority object in the priority queue
    public T peek(){
        return root.data;
    }

    //updates the priority of a specified item in the priority queue
    public void updatePriority(T item){
        Node<T> node = findNode(item);
        updatePriority(item, node);
    }

    //recursive method to update the priority of a specified item, but this method 
    //takes in a node parameter to find the node who has data with the parameter: T item
    private void updatePriority(T item, Node<T> node)
    {
        if (comparator.compare(node.data, node.parent.data) < 0)
        {
            swap(node, node.parent);
            node = node.parent;
        }
        else if (comparator.compare(node.data, node.left.data) > 0)
        {
            swap(node, node.left);
            node = node.left;
        }
        else if (comparator.compare(node.data, node.right.data) > 0)
        {
            swap(node, node.right);
            node = node.right;
        }
        else 
        {
            return;
        }
        updatePriority(item, node);
    }

    //finds a specified node in the heap with a specifed set of data
    private Node<T> findNode(T item)
    {
        if (size == 0)
        {
            return null;
        }
        return findNode(item, root);
    }

    //recursive method to find the specified node
    private Node<T> findNode(T item, Node<T> curNode)
    {
        if (curNode == null)
        {
            return null;
        }
        if (comparator.compare(curNode.data, item) == 0)
        {
            return curNode;
        }
        Node<T> node = findNode(item, curNode.left);
        node = findNode(item, curNode.right);
        return node;
    }
}