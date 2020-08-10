/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author page_
 */
public class minHeap {

    int size = -1;
    int max_size = 2000;
    int executed_time;
    public static Node_mh heap[];

    public minHeap(int size) {
        this.max_size = max_size;
        H = new Node_mh[size];
        for (int i = 0; i < size; i++) {
            H[i] = null;
        }
    }

    static final Node_mh nil = new Node_mh(0, 0, 0);
    Node_mh root = nil;
    Node_mh[] H = new Node_mh[2000];

    public void buildHeap(Node_mh[] H) {
        int heap_size = size;
        for (int i = (size / 2); i >= 1; i--) {
            minHeapify(H, i);
        }
    }

    public int parent(int p) {                               //p is position here

        return (p - 1) / 2;

    }

    public int leftChild(int p) {
        return 2 * p;
    }

    public int rightChild(int p) {
        return 2 * p + 1;
    }

    public Node_mh ongoingConstruction(Node_mh n) {        //when construction of a building has begun
        if (n == null) {                                   //when construction completed 
            n = deleteNode();
        }
        if (n != null) {                                   //when contruction not completed
            n.executed_Time++;
            n.rbtnode.executed_Time++;
        }
        return n;
    }

    public int insertNode_mh(Node node) {                                    //inserting as per minheap property wrt execution time and required conditions 

        Node_mh temp = new Node_mh(node.bldg_ID, node.executed_Time, node.total_Time);
        temp.rbtnode = node;
        H[++size] = temp;
        int current = size;
        while (current > 0) {
            if (H[current].executed_Time <= H[parent(current)].executed_Time) {     //since we insert in the heap wrt execution time
                if (H[current].executed_Time == H[parent(current)].executed_Time) {
                    if (H[current].bldg_ID < H[parent(current)].bldg_ID) {          //resolving the case when both exeution times are equal
                        swap(current, parent(current));
                        current = parent(current);
                    } else {
                        current = parent(current);
                    }
                } else {

                    swap(current, parent(current));
                    current = parent(current);

                }
            } else {
                break;
            }
        }
        return 0;
    }

    public void printNodes(Node_mh[] H) {                                   //prints nodes as are inserted 

        for (int i = 0; i <= size; i++) {
            System.out.println(" " + H[i]);
        }
    }

    public Node_mh deleteNode() {                                           //deletes the minimum node in the heap and returns it
        if (size >= 0) {
            Node_mh temp = H[0];
            H[0] = H[size];
            size = size - 1;
            minHeapify(H, 0);
            return temp;
        }
        return null;

    }

    public void minHeapify(Node_mh[] H, int index) {                          //uses minheapify for creating a min heap
        int l = leftChild(index);
        int r = rightChild(index);
        int smallest = index;

        if (l <= size && H[l].executed_Time <= H[index].executed_Time) {     //comparing with left child
            if (H[l].executed_Time < H[index].executed_Time) {              //general condition
                smallest = l;
            } else if (H[l].executed_Time == H[index].executed_Time) {
                if (H[l].bldg_ID < H[index].bldg_ID) {                      //when the executed times are equal, we choose the building with the smaller building ID
                    smallest = l;
                } else {
                    smallest = index;
                }
            }
        }

        if (r <= size && H[r].executed_Time <= H[smallest].executed_Time) {     //comparing with right child
            if (H[r].executed_Time < H[smallest].executed_Time) {
                smallest = r;
            } else if (H[r].executed_Time == H[smallest].executed_Time) {
                if (H[r].bldg_ID < H[smallest].bldg_ID) {
                    smallest = r;
                }
            }
        }

        if (smallest != index) {
            swap(index, smallest);
            minHeapify(H, smallest);
        }

    }

    public void swap(int pos1, int pos2) {          //swapping the nodes using their positions
        Node_mh temp;
        temp = H[pos1];
        H[pos1] = H[pos2];
        H[pos2] = temp;
    }

}
