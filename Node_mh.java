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

//This class is for the nodes of Min Heap
public class Node_mh {

    int bldg_ID;
    int executed_Time;
    int total_Time;

    public Node_mh(int bldg_id, int executed_time, int total_time) {
        this.bldg_ID = bldg_id;
        this.executed_Time = executed_time;
        this.total_Time = total_time;
    }
    Node rbtnode = null;
}

