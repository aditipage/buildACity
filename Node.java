


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
;


/**
 *
 * @author page_
 */
// This class is for RBT's node
public class Node {
        static final int red = 1;
        static final int black = 0;
        int bldg_ID;
        int executed_Time;
        int total_Time;
        public static final Node nil = new Node(0, 0, 0);
        int color = black;
        Node left = nil;
        Node right = nil;
        Node parent = nil;
        static Node root = nil;

        public Node(int bldg_id, int executed_time, int total_time) {
            this.bldg_ID = bldg_id;
            this.executed_Time = executed_time;
            this.total_Time = total_time;
        }
    }
