import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Queue;
import java.security.SecureRandom;


public class MainDemo
{

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
        Node startNode;
        Node endNode;
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
                            {
                                System.out.println(Paint.paintTextOrange("ERROR: Invalid inputs of ID"));
                                break;
                            }
                            startNode = findNode(nodeInfos, selectA);
                            endNode = findNode(nodeInfos, selectB);
                            if( startNode != null || endNode != null)
                            {
                                path = md.performBFS(startNode, endNode);

                                md.displayConnections(selectA, selectB, path);

                                
                                
                            }
                            else
                            {
                                System.out.println(Paint.paintTextOrange("There is no connection found between "+ startNode + " and " + endNode));
                                break;
                            }
                               


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

        i =0;

        if (path != null && !path.isEmpty() && (path.size() > 1 && path.get(path.size() - 1) == secNum))

        {
            System.out.println(Paint.paintTextGreen("There is a connection found between "+ firstNum + " and " + secNum));
            
            
            for(int num: path)
            {
                
                
                if(path.get(path.indexOf(num)+1) == path.size())
                    break;
                    
                System.out.println(Paint.paintTextCyan(num + "") + " is friends with " + Paint.paintTextCyan(path.get(path.indexOf(num)+1)  + ""));


                i+=1;
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

    private ArrayList<Integer> performBFS(Node start, Node end) 
    {
        Queue<Node> que = new LinkedList<>();
        HashSet<Integer> visited = new HashSet<>();
        HashMap<Node, Node> parentMapping = new HashMap<>();
        // Node current;
        
        que.add(start);
        visited.add(start.getSelfNum());

        while(!que.isEmpty())
        {
            Node current = que.poll();
            if(current.equals(end))
                return backTrackPath(parentMapping, start, end);
        
        
            for(Node neighbor : current.getRelations())
            {
                if(!visited.contains(neighbor.getSelfNum()))
                {
                    que.add(neighbor);
                    visited.add(neighbor.getSelfNum());
                    parentMapping.put(neighbor, current);
                }
            }
        
        }


        return null;
    


    }

    private ArrayList<Integer> backTrackPath(HashMap<Node, Node> parentMapping, Node start, Node end)
    {
        ArrayList<Integer> path = new ArrayList<>();

        Node current = end;

        while(current != null)
        {
            path.add(current.getSelfNum());
            current = parentMapping.get(current);
        }
        Collections.reverse(path);
        if(path.get(0).equals(start.getSelfNum()))
            return path;
        else
            return null;
    }

}
