import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;


public class FileReader 
{
    

    public boolean readFileNodes(String fileName, ArrayList<Node> nodeData )
    {
        int dataSize;
        int relationSize;
        
        int selectedFirst;
        int selectedRelate;
        Node tempNode;
        Node tempRelated;
        HashSet<Node> nodeInfos = new HashSet<Node>();


        tempNode = null;
        tempRelated = null;
        try
        {
            File f = new File(fileName);
            Scanner scFile = new Scanner(f);
            dataSize = scFile.nextInt();
            relationSize = scFile.nextInt();

            for(int i = 0; i< dataSize/2; i++)
            {
                
                selectedFirst = scFile.nextInt();
                selectedRelate = scFile.nextInt();


                tempNode = findNode(nodeInfos, selectedFirst);
                if(tempNode == null)
                {
                    tempNode = new Node(selectedFirst);
                    nodeInfos.add(tempNode);
                }

                tempRelated = findNode(nodeInfos, selectedRelate);
                if(tempRelated == null)
                {
                    tempRelated = new Node(selectedRelate);
                    nodeInfos.add(tempRelated);
                }
                   

                tempNode.addRelation(tempRelated);
                tempRelated.addRelation(tempNode);

                    


            }
            nodeData.clear();
            nodeData.addAll(nodeInfos);
            




            scFile.close();

        }
        catch (FileNotFoundException e)
        {
            System.err.println("File not found.");
            return false;
        }

        return true;

    }
    private Node findNode(HashSet<Node> nodeList, int num)
    {

    

        for(Node getNode : nodeList)
        {
            if(getNode.getSelfNum() == num)
            {
                return getNode;
            }
        }

        return null;
    }

}
