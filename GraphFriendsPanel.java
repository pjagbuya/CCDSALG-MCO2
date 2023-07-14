import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
public class GraphFriendsPanel extends JPanel {
    private ArrayList<Node> nodeData;
    private ArrayList<Integer> XPOS;
    private Node displayNode;
    private boolean updateLine;
    private Node selectedNode;

    private int maxStringLen;
    public GraphFriendsPanel(ArrayList<Node> nodeInfos, Node displayNode)
    {
        nodeData = nodeInfos;
        XPOS = new ArrayList<Integer>();
        updateLine = false;
        maxStringLen = 0;
        this.displayNode = displayNode;

    }   
    public void setSelectedNode(Node given)
    {
        selectedNode = given;
    }
    public void setUpdateLineBool(boolean val)
    {
        updateLine =val;
    }
    public ArrayList<Integer> getXPOS() {
        return XPOS;
    }
 
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        

      
        ArrayList<Node> uniqueNodes;
  

        ArrayList<Node> YPOS;
        int maxStringLen;
        int origStartX = 70;
        int origStartY = 70;
        int startX;;
        int startY;

        startX = origStartX;
        startY = origStartY;
        uniqueNodes = new ArrayList<Node>();
        XPOS = new ArrayList<Integer>();

        maxStringLen = 0;

        g.setFont(new Font("default", Font.BOLD, 20));
        for(Node temp: nodeData)
        {

            if(!uniqueNodes.contains(temp))
            {
                uniqueNodes.add(temp);
                if((temp.getSelfNum() + "").length() > maxStringLen)
                
                    maxStringLen = (temp.getSelfNum() + "").length();
                
            }


            
        }



        g.setColor(Color.BLACK);
        ((Graphics2D)g).setStroke(new BasicStroke(5));
        g.drawLine(startX + 150, startY+10, startX +150 + uniqueNodes.size()*50+50, startY+10); // TOP LINE
        g.drawLine(startX + 150, startY+10, startX+150, startY+10  + 50+50); // LEFT LINE
        g.drawLine(startX +150 + uniqueNodes.size()*50+50, startY+10, startX +150+ uniqueNodes.size()*50+50, startY+10  + 50+50); // RIGHT LINE
        g.drawLine(startX +150, startY+10  + 50+50, startX +150 + uniqueNodes.size()*50+50, startY+10  + 50+50); // BOTTOM LINE
        int i = 0;
        //setup labels
        for(Node temp: uniqueNodes)
        {
            i += 1;


            g.drawString(temp.getSelfNum() + "", startX+ 150 + i*50 - 5*((temp.getSelfNum() +"").length()-1), startY);


            XPOS.add(startX+ 150 + i*50 - 5*((temp.getSelfNum() +"").length()-1));


        }

        g.drawString(displayNode.getSelfNum() + "", startX + 120 - 10*(displayNode.getSelfNum() +"").length() , startY+ 50);


        int j = 0;
        origStartY = startY;
        startY += 50;
        
        origStartX = startX; //  new Integer(startX + "");



        for(Node potRelNode : uniqueNodes)
        {
            j+= 1;
            if(displayNode.isRelated(potRelNode))
            {
                
                g.setColor(Color.RED);

                g.drawString("1", startX + 150 + j*50 + ((potRelNode.getSelfNum() +"").length()-1) , startY);
            }
            else
            {
                ((Graphics2D)g).setStroke(new BasicStroke(2));
                g.setColor(Color.BLUE);
                g.drawString("0", startX + 150 + j*50 + ((potRelNode.getSelfNum() +"").length()-1), startY);
            }

            // 10*(temp.getSelfNum() +"").length()
        }


        

        // Update the size
        setPreferredSize(new Dimension(Math.max(getPreferredSize().width, XPOS.get(XPOS.size()-1)+500), Math.max(getPreferredSize().height, startY + uniqueNodes.size()*50 + 150)));
        revalidate();


        startY = origStartY;
        startX = origStartX;
        if(updateLine)
        {
            
            g.drawLine(XPOS.get(uniqueNodes.indexOf(selectedNode))  + 20 + ((selectedNode.getSelfNum()+"").length()-1)*10, startY-5, XPOS.get(uniqueNodes.indexOf(selectedNode)) + 20 + ((selectedNode.getSelfNum()+"").length()-1)*10, startY - 5  + 50+50);
            g.drawLine(XPOS.get(uniqueNodes.indexOf(selectedNode)) -15 + ((selectedNode.getSelfNum()+"").length()-1), startY-5, XPOS.get(uniqueNodes.indexOf(selectedNode)) -15 + ((selectedNode.getSelfNum()+"").length()-1), startY - 5  + 50+50);
            
        }




    }



}
