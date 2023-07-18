import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import java.security.SecureRandom;


public class MainDemo{

    private Node selectedNode;


    private static ArrayList<Node> nodeInfos;




    private static boolean isTerminate;
    public static void main(String[] args) 
    {

        boolean isValidFile;

        boolean isRenewFile;
        Node tempNode;

        SecureRandom randomizer = new SecureRandom();
        Scanner sc = new Scanner(System.in);


        int nodeInd;
        Node tempNode2;
        String userInputC;



        MainDemo md = new MainDemo();
        isTerminate = false;


        // Double checks if each relation is added correctly

        isValidFile = false;
        nodeInfos = new ArrayList<Node>();
      
        isRenewFile = false;
        while(!isValidFile)
        {
            FileReader fr = new FileReader();
            if(!isRenewFile)
            {
                while(!isValidFile)
                {
                    if(nodeInfos != null)
                        nodeInfos.clear();
                    System.out.print("Input file: ");
                    isValidFile = fr.readFileNodes(sc.nextLine(), nodeInfos);
                    if(isValidFile && nodeInfos != null)
                    {
                        System.out.println("File is found. Graph is Loaded");
                    }
                    else
                        System.out.println("ERROR: Unable to load file");
                }

            }
            isRenewFile = true;

            // for(Node temp : nodeInfos)
            // {
            //     System.out.println(temp);
            // }

            while(isValidFile)
            {
                if(!nodeInfos.isEmpty())
                    md.displayMenu();
                else
                {
                    System.out.println(Paint.paintTextOrange("No data to display"));
                    break;
                }
                    

                System.out.println();
                System.out.print("Run another file? (Y/N): ");
                userInputC = sc.nextLine();


                // Cleanup section
                if(userInputC.equalsIgnoreCase("y"))
                {

                    isRenewFile = false;
                    break;


                    
                }



            }
            if(!isTerminate)
            {
                isValidFile = false;
            }



        }
        System.out.println();
        System.out.println("Terminating program...");

        sc.close();
         



    }
    private void displayMenu()
    {
        boolean isExit;
        HashSet<Integer> visited;
        ArrayList<Integer> path;
        
        boolean isValidID;
        int input;
        int inputSelected;
        int selectA;
        int selectB;
        Scanner scMenu = new Scanner(System.in);
        MainDemo md = new MainDemo();

        System.out.println();
        System.out.println("Main Menu");
        System.out.println(Paint.paintTextCyan("[1]") + " Get friend list");
        System.out.println(Paint.paintTextCyan("[2]") + " Get connection");
        System.out.println(Paint.paintTextCyan("[3]") + " Exit");

        isExit = false;

        visited = new HashSet<Integer>();
        path = new ArrayList<Integer>();
        while(!isExit)
        {
            System.out.print("Input choice: ");
            input = Integer.parseInt(scMenu.nextLine());
            
            switch(input)
            {
                case 1:


                    System.out.println();
                    System.out.print("Enter person ID: ");
                    inputSelected = scMenu.nextInt();
                    scMenu.nextLine();
                    if(findNode(nodeInfos, inputSelected) != null)
                        md.displaySelected(findNode(nodeInfos, inputSelected));
                    else
                        System.out.println(Paint.paintTextOrange("ERROR: ID does not exist"));

                    

                    isExit = true;
                    break;
                case 2:
                    
                    isValidID = false;
                    while(!isValidID)
                    {
                        try
                        {
                            System.out.println();
                            System.out.println("Enter person ID of " + Paint.paintTextCyan("first") + " and " + Paint.paintTextCyan("second to get connection of first to second"));
                            System.out.print("Enter first person ID: ");
                
                            selectA = scMenu.nextInt();
                            scMenu.nextLine();

                            System.out.print("Enter second person ID: ");
                            selectB = scMenu.nextInt();
                            scMenu.nextLine();

                            if(selectA < 0 || selectB < 0)
                                System.out.println(Paint.paintTextOrange("ERROR: Invalid inputs of ID"));
                            if(findNode(nodeInfos, selectA) == null || findNode(nodeInfos, selectA) == null)
                                break;

                            md.performDFS(findNode(nodeInfos, selectA), findNode(nodeInfos, selectB), visited, path);
                            md.displayConnections(selectA, selectB, path);
                        }
                        catch(Exception e)
                        {

                            isValidID = true;
                        }

                    }




                    isExit = true;
                    break;
                case 3:
                    isTerminate = true;
                    isExit = true;
                    break;
                default:
                    isExit = false;
                    System.out.println(Paint.paintTextOrange("ERROR: Please choos from 1- 3"));
                


            }
        }

  

        scMenu = null;
        md = null;
        

    }

    private void displayConnections(int firstNum, int secNum, ArrayList<Integer> path)
    {
        int i;
        int prev = firstNum;
        if((path.size() > 1 && path.get(path.size()-1) == secNum) || (path.size()== 1 && path.get(path.size()-1) == secNum))
        {
            System.out.println(Paint.paintTextGreen("There is a connection found between "+ firstNum + " and " + secNum));
            
            
            for(int num: path)
            {

                System.out.println(Paint.paintTextCyan(prev + "") + " is friends with " + Paint.paintTextCyan(num  + ""));
                prev = num;

                
            }
        }
        else
        {
            System.out.println(Paint.paintTextOrange("There is no connection found between "+ firstNum + " and " + secNum));
        }


    }
    private void displaySelected(Node givenNode)
    {

        System.out.println();
        System.out.println("Person of ID " + Paint.paintTextCyan(givenNode.getSelfNum()+"") + " has " + Paint.paintTextYellow(givenNode.getRelations().size()+"") + " friend/s");
        System.out.print("List of friends: ");
        for(Node temp : givenNode.getRelations())
        {

            Paint.turnOnGreen();
            System.out.print(temp.getSelfNum() + " ");
            Paint.turnOffColor();

        }

        if(givenNode.getRelations().size() == 0)
        {
            System.out.print(Paint.paintTextOrange("NONE"));
        }


    }

    
    private Node findNode(ArrayList<Node> nodeList, int num)
    {
        if(nodeList == null) 
        {
            throw new IllegalArgumentException("The node list is null");
        }
    

        for(Node getNode : nodeList)
        {
            if(getNode.getSelfNum() == num)
            {
                return getNode;
            }
        }

        return null;
    }
    private void performDFS(Node start, Node end, HashSet<Integer> visited, ArrayList<Integer> path) 
    {
        visited.add(start.getSelfNum());

        if(start.getSelfNum() == end.getSelfNum())
            return;
        
        for (Node tempNode : start.getRelations()) {
            if (!visited.contains(tempNode.getSelfNum())) {

                // Add the path
                path.add(tempNode.getSelfNum());
                performDFS(tempNode, end, visited, path);
                // When the relations on the branches of this node have the goal node, return
                if(path.contains(end.getSelfNum())) {

                    return;
                }

                // Remove node considered as path
                path.remove(tempNode);

            }
        }
    


    }
}
