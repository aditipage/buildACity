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
import java.io.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author page_
 */
public class risingCity {
    static PrintStream ps;
    public static void main(String[] args) throws FileNotFoundException, IOException, NumberFormatException {
        RBTFinal rbtf = new RBTFinal();
        minHeap mh = new minHeap(2000);
        int global_timer = 0;
        int local_timer = 0;
        int days_worked = 0;
        Node_mh current_building = null;

        File file = new File(args[0]);																							//Creates a file object for the input file
        Scanner sc = new Scanner(file);
        String st = sc.nextLine();
        

        if (args.length == 0) {
            System.out.println("No input file specified. Exiting.");
            System.exit(-1);
        }
        ps = new PrintStream("output_file.txt");
        
        //extracting first part of input
        String split_input[] = st.split(":");        
        local_timer = Integer.parseInt(split_input[0]);
        while (true) {
            current_building = mh.ongoingConstruction(current_building);
            //construction has started on this building
            if (current_building != null) {
                days_worked = days_worked + 1;
            }

            //For taking in the input at the correct time 
            if (local_timer == global_timer) {
                if (split_input[1].contains("PrintBuilding")) {
                    String split_input1[] = split_input[1].split("\\(");
                    split_input1[1] = split_input1[1].replace(')', ' ');
                    String split_input2[] = split_input1[1].split(",");
                    if (split_input2.length == 2) {
                        //Print(bn1, bn2) will be called
                        Print(Integer.parseInt(split_input2[0]), Integer.parseInt(split_input2[1].trim()));

                    } else {
                        //Print(bn1) will be called
                        Print(Integer.parseInt(split_input2[0].trim()));
                    }
                } else { //insertNode() for inserting in RBT and then MinHeap  
                    String split_input1[] = split_input[1].split("\\(");
                    split_input1[1] = split_input1[1].replace(')', ' ');
                    String split_input2[] = split_input1[1].split(",");        //thirdarray has the total_time taken from input
                    Node node = new Node(Integer.parseInt(split_input2[0]), 0, Integer.parseInt(split_input2[1].trim()));
                    node = rbtf.insertNode(node);
                    mh.insertNode_mh(node);
                }

                if (sc.hasNextLine()) {
                    st = sc.nextLine();
                    split_input = st.split(":");
                    local_timer = Integer.parseInt(split_input[0]);
                }
            }

            if (!sc.hasNextLine() && RBTFinal.root == Node.nil) //when file end reached nd rbt empty
            {
                break;
            }

            if (current_building != null) {
                if (current_building.executed_Time == current_building.total_Time) {   //checking if construction complete
                    ps.print("(" + current_building.bldg_ID + "," + global_timer + ")");
                    ps.println();
                    rbtf.deleteNode(current_building.rbtnode);                          //deleting from rbt
                    current_building = null;
                    days_worked = 0;                                                    //resetting the varialble
                } else if (days_worked == 5) {
                    mh.insertNode_mh(current_building.rbtnode);
                    days_worked = 0;
                    current_building = null;                                            //removing and comparing execution times; picking the bldg with least execution_time
                }
            }
            global_timer = global_timer + 1;
        }
    }

    //Prints the bldg_ID, execeuted_time, total_time of given building ID as argument
    public static void Print(int bn) throws FileNotFoundException {
        Node node = new Node(bn, 0, 0);
        node = new RBTFinal().findNode(node, RBTFinal.root);
        if(node == null)
            ps.println("(" + 0 +","+0+"," + 0 + ")");
        else
            ps.println("(" + node.bldg_ID + "," + node.executed_Time + "," + node.total_Time + ")");
    }

    //Prints building IDs, execution time, total time in the given range of buildig IDs
    public static void Print(int bn1, int bn2) {
        Node[] node_list = new Node[bn2 - bn1 + 1];         //For bldgs in this range
        int j = 0;
        for (int i = bn1; i <= bn2; i++) {
            Node node = new Node(i, 0, 0);
            RBTFinal rbt = new RBTFinal();
            if (rbt.findNode(node, RBTFinal.root) != null) {
                node = rbt.findNode(node, RBTFinal.root);
                node_list[j] = node;
                j++;
            }
        }

        if (j == 0) {
            ps.println("(" + 0 +","+0+"," + 0 + ")");
        } else {
            for (int i = 0; i < j; i++) {
                ps.print("(" + node_list[i].bldg_ID + "," + node_list[i].executed_Time + "," + node_list[i].total_Time + ")");
                if (i < j - 1) {
                    ps.print(",");
                }
            }
            ps.println();
        }
    }
}
