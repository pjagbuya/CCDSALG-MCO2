import java.awt.Color;
import java.util.ArrayList;
import java.security.SecureRandom;
import java.util.LinkedHashMap;


public class Node {
    private int num;
    private ArrayList<Node> relations;
    private LinkedHashMap<Integer, Boolean> isAttached;
    private Color nodeLineColor;
    private int posX;
    private int posY;

    public Node(int num, int X, int Y)
    {

        this.num = num;
        posX = X;
        posY = Y;
        relations = new ArrayList<Node>();
        isAttached = new LinkedHashMap<Integer, Boolean>();
        nodeLineColor = null;

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
    public void addAttached(Node givenNum)
    {
        isAttached.put(givenNum.getSelfNum(), true);
    }
    public boolean isAttached(Node givenNum)
    {
        return isAttached.containsKey(givenNum.getSelfNum());
        
    }
    public Color isRelatedHaveColor()
    {
        for(Node tempNode : relations)
        {
            if (tempNode.getColor() != null);
                return tempNode.getColor();
        }
        return null;
    }
    public void addRelation(Node newNum)
    {
        if(!relations.contains(newNum))
        {
            relations.add(newNum);

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
    public void setX(int x)
    {
        posX = x;
    }
    public void setY(int y)
    {
        posY = y;
    }
    public int getX()
    {
        return posX;
    }

    public int getY()
    {
        return posY;
    }
    public Color getColor()
    {
        return nodeLineColor;
    }
    public void setNodeLineColor(Color color)
    {
        nodeLineColor = color;
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