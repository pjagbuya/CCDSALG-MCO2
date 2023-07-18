import java.awt.Color;
import java.util.ArrayList;
import java.security.SecureRandom;
import java.util.LinkedHashMap;


public class Node {
    private int num;
    private ArrayList<Node> relations;


    public Node(int num)
    {

        this.num = num;

        relations = new ArrayList<Node>();


    }
    //delete
    public void setNum(int n)
    {
        num =n;
    }

    public boolean isRelated(Node givenNum)
    {
        if(relations.contains(givenNum) || givenNum.getRelations() != null && givenNum.getRelations().contains(this))
        {
            return true;
        }
        return false;
    }

    public void addRelation(Node node)
    {
        if(!relations.contains(node))
        {
            relations.add(node);

        }
            

    }
    public ArrayList<Node> getRelations()
    {
        return relations;
    }

    public int getSelfNum()
    {
        return num;
    }



    @Override
    public String toString()
    {
        String temp;
        temp = "";
        for(int i = 0; i < relations.size(); i++)
        {
            temp += getSelfNum() + " is related to: " + relations.get(i).getSelfNum() + "\n";
        }
        return temp;
    }


}