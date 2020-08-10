/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author page_
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.*;

/**
 *
 * @author page_
 */
public class RBTFinal {

    static final int red = 1;
    static final int black = 0;

    static Node root = Node.nil;

    public Node insertNode(Node node) {
        Node temp = root;
        if (root == Node.nil) {                              //no root node initially
            root = node;
            //then the node inserted is root
            node.color = black;                         //color of root is black as per rbt property
            node.parent = Node.nil;                          //root node's parent is nil
        } else {
            if (findNode(node, root) != null) {
                System.out.println("ERROR");
                System.exit(-1);
            }

            node.color = red;
            //any new node is colored red if root exists
            while (true) {
                if (node.bldg_ID < temp.bldg_ID) {      //comparing bldg_id to be inserted as per bst
                    if (temp.left == Node.nil) {
                        temp.left = node;
                        node.parent = temp;
                        break;
                    } else {
                        temp = temp.left;
                    }
                } else if (node.bldg_ID >= temp.bldg_ID) {
                    if (temp.right == Node.nil) {
                        temp.right = node;
                        node.parent = temp;
                        break;
                    } else {
                        temp = temp.right;
                    }
                }
            }
            fixRBT(node);                                   //to fix the tree as per RBT properties

        }
        return node;
    }

    public Node printRBT(Node node) {
        if (node == Node.nil) {
            return node;
        }

        printRBT(node.left);
        //System.out.print(((node.color == red) ? "Color: Red " : "Color: Black ") + "Key: " + node.bldg_ID + " Parent: " + node.parent.bldg_ID + "\n");
        printRBT(node.right);
        return node;

    }

    private void fixRBT(Node node) {
        while (node.parent.color == red & node.parent != root) {
            Node uncle = Node.nil;
            if (node.parent == node.parent.parent.left) {   //if parent is left child
                uncle = node.parent.parent.right;

                if (uncle != Node.nil && uncle.color == red) {   //case 1:uncle red; colors 
                    node.parent.color = black;              //of uncle, parent and grandparent swapped
                    uncle.color = black;
                    node.parent.parent.color = red;
                    node = node.parent.parent;
                    continue;
                }
                if (node == node.parent.right) {
                    node = node.parent;
                    leftRotation(node);
                }
                node.parent.color = black;
                node.parent.parent.color = red;
                rightRotation(node.parent.parent);
            } else {                                        //if parent is right child
                uncle = node.parent.parent.left;
                if (uncle != Node.nil && uncle.color == red) {
                    node.parent.color = black;
                    uncle.color = black;
                    node.parent.parent.color = red;
                    node = node.parent.parent;
                    continue;
                }
                if (node == node.parent.left) {
                    node = node.parent;
                    rightRotation(node);
                }
                node.parent.color = black;
                node.parent.parent.color = red;
                leftRotation(node.parent.parent);
            }
        }
        root.color = black;
    }

    void leftRotation(Node node) {
        if (node.parent != Node.nil) {
            if (node == node.parent.left) {
                node.parent.left = node.right;
            } else {
                node.parent.right = node.right;
            }
            node.right.parent = node.parent;
            node.parent = node.right;
            if (node.right.left != Node.nil) {
                node.right.left.parent = node;
            }
            node.right = node.right.left;
            node.parent.left = node;
        } else {//Need to rotate root
            Node right = root.right;
            root.right = right.left;
            right.left.parent = root;
            root.parent = right;
            right.left = root;
            right.parent = Node.nil;
            root = right;
        }
    }

    void rightRotation(Node node) {
        if (node.parent != Node.nil) {
            if (node == node.parent.left) {
                node.parent.left = node.left;
            } else {
                node.parent.right = node.left;
            }

            node.left.parent = node.parent;
            node.parent = node.left;
            if (node.left.right != Node.nil) {
                node.left.right.parent = node;
            }
            node.left = node.left.right;
            node.parent.right = node;
        } else {//Need to rotate root
            Node left = root.left;
            root.left = root.left.right;
            left.right.parent = root;
            root.parent = left;
            left.right = root;
            left.parent = Node.nil;
            root = left;
        }
    }

    public Node findNode(Node findNode, Node node) {            //node = root; finding node wrt root 
        if (root == Node.nil) {
            return null;
        }

        if (findNode.bldg_ID < node.bldg_ID) {
            if (node.left != Node.nil) {
                return findNode(findNode, node.left);
            }
        } else if (findNode.bldg_ID > node.bldg_ID) {
            if (node.right != Node.nil) {
                return findNode(findNode, node.right);
            }
        } else if (findNode.bldg_ID == node.bldg_ID) {
            return node;
        }
        return null;
    }

    void deleteRBT() {                                              //to delete complete tree at once
        root = Node.nil;
    }

    void transplant(Node target, Node with) {                       //to exchange/replace during rotations
        if (target.parent == Node.nil) {
            root = with;
        } else if (target == target.parent.left) {
            target.parent.left = with;
        } else {
            target.parent.right = with;
        }
        with.parent = target.parent;
    }

    boolean deleteNode(Node z) {                                       //deleting the given node 
        if ((z = findNode(z, root)) == null) {
            return false;
        }
        Node x;
        Node y = z;
        int y_original_color = y.color;

        if (z.left == Node.nil) {
            x = z.right;
            transplant(z, z.right);
        } else if (z.right == Node.nil) {
            x = z.left;
            transplant(z, z.left);
        } else {
            y = treeMinimum(z.right);
            y_original_color = y.color;
            x = y.right;
            if (y.parent == z) {
                x.parent = y;
            } else {
                transplant(y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }
            transplant(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.color = z.color;
        }
        if (y_original_color == black) {
            deleteFixup(x);
        }
        return true;
    }

    void deleteFixup(Node x) {                              //adjusting as per RBT properties
        while (x != root && x.color == black) {
            if (x == x.parent.left) {
                Node w = x.parent.right;
                if (w.color == red) {
                    w.color = black;
                    x.parent.color = red;
                    leftRotation(x.parent);
                    w = x.parent.right;
                }
                if (w.left.color == black && w.right.color == black) {
                    w.color = red;
                    x = x.parent;
                    continue;
                } else if (w.right.color == black) {
                    w.left.color = black;
                    w.color = red;
                    rightRotation(w);
                    w = x.parent.right;
                }
                if (w.right.color == red) {
                    w.color = x.parent.color;
                    x.parent.color = black;
                    w.right.color = black;
                    leftRotation(x.parent);
                    x = root;
                }
            } else {
                Node w = x.parent.left;
                if (w.color == red) {
                    w.color = black;
                    x.parent.color = red;
                    rightRotation(x.parent);
                    w = x.parent.left;
                }
                if (w.right.color == black && w.left.color == black) {
                    w.color = red;
                    x = x.parent;
                    continue;
                } else if (w.left.color == black) {
                    w.right.color = black;
                    w.color = red;
                    leftRotation(w);
                    w = x.parent.left;
                }
                if (w.left.color == red) {
                    w.color = x.parent.color;
                    x.parent.color = black;
                    w.left.color = black;
                    rightRotation(x.parent);
                    x = root;
                }
            }
        }
        x.color = black;
    }

    Node treeMinimum(Node subTreeRoot) {                        //to find min value in a subtree
        while (subTreeRoot.left != Node.nil) {
            subTreeRoot = subTreeRoot.left;
        }
        return subTreeRoot;
    }

}
