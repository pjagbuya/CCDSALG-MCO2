import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import java.security.SecureRandom;


public class MainDemo extends JPanel implements MouseListener,ActionListener{
    private static final int CIRCLE_WIDTH = 45;
    private Dimension area;             //indicates area taken up by graphics
    private Vector<Rectangle> circles; //coordinates used to draw graphics
    private JPanel drawingPane;
    
    private int fartherstCoordX;
    private int fartherstCoordY;
    
    private boolean isMouseClicked;
    private boolean isUpdateBasedOnAttached;
    private Node selectedNode;


    private ArrayList<Node> nodeInfos;

    private JButton myButton = new JButton("View Graph Rep.");
    public MainDemo() 
    {
        

        super(new BorderLayout());
        isMouseClicked = false;
        isUpdateBasedOnAttached = false;
        Node tempNode;
        nodeInfos = new ArrayList<Node>();
        SecureRandom randomizer = new SecureRandom();
        fartherstCoordX = 0;
        fartherstCoordY = 0;
        int nodeInd;
        int newX;
        int newY;
        area = new Dimension(0,0);
        circles = new Vector<Rectangle>();

        //Set up the instructions.
        JLabel instructionsLeft = new JLabel(
                        "Social Graph");

        
 
        myButton.setFocusable(false);
        myButton.addActionListener(this);

        JPanel instructionPanel = new JPanel(new GridLayout(0,8));
        instructionPanel.setFocusable(true);
        instructionPanel.add(instructionsLeft);

        instructionPanel.add(myButton);




        //Set up the drawing area.
        drawingPane = new DrawingPane();
        for(int k = 0; k < 20; k++)
        {
            newX = randomizer.nextInt(1200);
            newY = randomizer.nextInt(1200);
            

            tempNode = new Node(k, newX, newY);
            nodeInfos.add(tempNode);
            
        }


        //This block of code prevents overlapping nodes
        nodeInd = 0;
        while(nodeInd < nodeInfos.size())
        {
            Node currNode;
            tempNode = nodeInfos.get(nodeInd);
             newX =  nodeInfos.get(nodeInd).getX();
             newY =  nodeInfos.get(nodeInd).getY();

             int startInd;
             startInd =0;
            while(startInd < nodeInfos.size()) 
            {
                if(startInd == nodeInd)
                {
                    startInd+=1;
                    if(startInd >= nodeInfos.size())
                        break;
                }
                
                while(newX >= nodeInfos.get(startInd).getX() - 100  && 
                    newX <= nodeInfos.get(startInd).getX() + 100  && 
                    newY >= nodeInfos.get(startInd).getY() - 100  &&
                    newY <= nodeInfos.get(startInd).getY() + 100)
                {
                    newX = randomizer.nextInt(1200);
                    newY = randomizer.nextInt(1200);
                    tempNode.setX(newX);
                    tempNode.setY(newY);
                    startInd =0;
                }   
                    
                startInd++;
            }
            

            updateDrawing(tempNode.getX(), tempNode.getY(), tempNode);

            nodeInd++;
        }

        // Sets up random relations to each number
        for(int m = 0; m < nodeInfos.size(); m++)
        {
            int randNum;
            Color tempColor;
            Node tempNode2;
            int rInd;
            randNum =randomizer.nextInt(6);
            int j = 0;
            while(j < randNum)
            {
                tempNode2 = nodeInfos.get(randomizer.nextInt(19));
                if(nodeInfos.get(m) != tempNode2 && !nodeInfos.get(m).isRelated(tempNode2))
                {

                    nodeInfos.get(m).addRelation(tempNode2);
                    tempNode2.addRelation(nodeInfos.get(m));

                    
                }
                    
                j++;
            }

            nodeInfos.get(m).setNodeLineColor(new Color(randomizer.nextInt(211), randomizer.nextInt(211), randomizer.nextInt(211)));


        }

        Node tempNode2;
        // Double checks if each relation is added correctly
        for(int i = 0; i < nodeInfos.size(); i++)
        {
            tempNode = nodeInfos.get(i);
            System.out.println(tempNode);
            for (int j = 0; j < nodeInfos.size(); j++)
            {
                tempNode2 = nodeInfos.get(j);

                if(tempNode != tempNode2 && (
                   tempNode.isRelated(tempNode2)||  
                   tempNode2.isRelated(tempNode)  )  )
                {
                    tempNode.addRelation(tempNode2);
                    tempNode2.addRelation(tempNode);

                }

            }
        }

        drawingPane.setBackground(Color.white);

        drawingPane.addMouseListener(this);
        //Put the drawing area in a scroll pane.
        JScrollPane scroller = new JScrollPane(drawingPane);
        scroller.setPreferredSize(new Dimension(600,600));

        //Lay out this demo.
        add(instructionPanel, BorderLayout.PAGE_START);
        add(scroller, BorderLayout.CENTER);

        // Add key binding for F5 key
        drawingPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F5"), "F5 pressed");
        drawingPane.getActionMap().put("F5 pressed", new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Your action here
            drawingPane.revalidate();
            drawingPane.repaint();
            }
        });

    }

    /** The component inside the scroll pane. */
    public class DrawingPane extends JPanel {

        

 
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            ArrayList<Node> tempRelations;
            Rectangle rect;
            SecureRandom randomizer = new SecureRandom();
            Node tempNode;
            Node relatedNode;
            int rInd;

            rInd = 0;
            for(int i = 0; i< nodeInfos.size(); i++)
            {
                if(nodeInfos.get(i) != null)
                {
                    tempNode = nodeInfos.get(i);
                    tempRelations = tempNode.getRelations();

                    rInd = 0;
                    System.out.println(tempNode);
                    if(!isUpdateBasedOnAttached)
                    {
                        while(rInd < tempRelations.size())
                        {
                            relatedNode = tempRelations.get(rInd);
                            g.setColor(relatedNode.getColor());


                            ((Graphics2D) g).setStroke(new BasicStroke(3));
                            g.drawLine(tempNode.getX() + CIRCLE_WIDTH/2, tempNode.getY()+ CIRCLE_WIDTH/2, relatedNode.getX() + CIRCLE_WIDTH/2, relatedNode.getY() + CIRCLE_WIDTH/2);
                            tempNode.addAttached(relatedNode);
                            relatedNode.addAttached(tempNode);

                

                            rInd++;
                        }
                    }
                    else
                    {
                        tempRelations = selectedNode.getRelations();
                        while(rInd < tempRelations.size())
                        {

                            relatedNode = tempRelations.get(rInd);
                            
                            g.setColor(Color.BLACK);


                            ((Graphics2D) g).setStroke(new BasicStroke(3));
                            
                            if(selectedNode.isAttached(relatedNode))
                                g.drawLine(selectedNode.getX() + CIRCLE_WIDTH/2, selectedNode.getY()+ CIRCLE_WIDTH/2, relatedNode.getX() + CIRCLE_WIDTH/2, relatedNode.getY() + CIRCLE_WIDTH/2);
                            

                

                            rInd++;
                        }
                        
                        break;
                    }
                        
                }
            }
            ((Graphics2D) g).setStroke(new BasicStroke(1));
            if(!isUpdateBasedOnAttached)
            {
                for (int i = 0; i < circles.size(); i++) 
                {

                    rect = circles.elementAt(i);
                    g.setColor(Color.YELLOW);
                    g.fillOval(rect.x, rect.y, rect.width, rect.height);
                    g.setColor(Color.BLACK);
                    g.drawOval(rect.x, rect.y, rect.width, rect.height);
                    g.setColor(Color.black);
                    // Pattern for text adding for a 45x45 circle, posX of Circle + 10, posY of Circle + 30
                    g.drawString(nodeInfos.get(i).getSelfNum() + "", rect.x + 6,rect.y +30);
                }
                
                
            }

            else
            {
                

                for (int i = 0; i < circles.size(); i++) 
                {
                    if(circles.elementAt(i).getX() <= selectedNode.getX() + 5 &&
                       circles.elementAt(i).getX() >= selectedNode.getX() - 5 &&
                       circles.elementAt(i).getY() <= selectedNode.getY() + 5 &&
                       circles.elementAt(i).getY() >= selectedNode.getY() - 5)
                    {

                        rect = circles.elementAt(i);
                        g.setColor(Color.YELLOW);
                        g.fillOval(rect.x, rect.y, rect.width, rect.height);
                        g.setColor(Color.red);
                        g.drawOval(rect.x, rect.y, rect.width, rect.height);
                        g.setColor(Color.black);
                        // Pattern for text adding for a 45x45 circle, posX of Circle + 10, posY of Circle + 30
                        g.drawString(nodeInfos.get(i).getSelfNum() + "", rect.x + 6,rect.y +30);

                    }




                }


                rInd = 0;
                while(rInd < selectedNode.getRelations().size())
                {
                    for(int i = 0; i < circles.size(); i++)
                    {
                        relatedNode = selectedNode.getRelations().get(rInd);
                        if(circles.elementAt(i).getX() <= relatedNode.getX() + 5 &&
                           circles.elementAt(i).getX() >= relatedNode.getX() - 5 &&
                           circles.elementAt(i).getY() <= relatedNode.getY() + 5 &&
                           circles.elementAt(i).getY() >= relatedNode.getY() - 5)
                        {
                            ((Graphics2D) g).setStroke(new BasicStroke(3));
                            rect = circles.elementAt(i);
                            
                            g.setColor(Color.YELLOW);
                            g.fillOval(rect.x, rect.y, rect.width, rect.height);
                            g.setColor(Color.red);
                            g.drawOval(rect.x, rect.y, rect.width, rect.height);
                            g.setColor(Color.black);
                            // Pattern for text adding for a 45x45 circle, posX of Circle + 10, posY of Circle + 30
                            g.drawString(relatedNode.getSelfNum() + "", rect.x + 6,rect.y +30);
                            
                            break;
                        }
                    }
                    rInd++;
                }
                

  
            }
            ((Graphics2D) g).setStroke(new BasicStroke(1));



        }
    }

    
    public void mouseReleased(MouseEvent e)
    {
        int x = e.getX();
        int y = e.getY();
        
        if(SwingUtilities.isRightMouseButton(e))
        {
            System.out.println("X: " + x +", Y: " + y);
            isUpdateBasedOnAttached = true;
            for(int i = 0; i < nodeInfos.size(); i++)
            {
                nodeInfos.get(i).setNodeLineColor(Color.white);
                if(x <= nodeInfos.get(i).getX() + 45 &&
                x >= nodeInfos.get(i).getX() && 
                y <= nodeInfos.get(i).getY() + 45 &&
                y >= nodeInfos.get(i).getY() )
                {

                    selectedNode = nodeInfos.get(i);
                    break;
                }
                
                

            }
            drawingPane.revalidate();
        }
        else
        {
            
            for(int i = 0; i < nodeInfos.size(); i++)
            {
                nodeInfos.get(i).setNodeLineColor(Color.blue);

            }
            drawingPane.revalidate();
            isUpdateBasedOnAttached = false;
        }

        drawingPane.repaint();



    }
    public void mouseClicked(MouseEvent e){
        
    }
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mousePressed(MouseEvent e){
        
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==myButton)
        {
            GraphRepWindow newWindow = new GraphRepWindow(nodeInfos);
  
        }
        

    }

    public void updateDrawing(int posX, int posY, Node givenNode) {
        final int W = 45;
        final int H = 45;
        boolean changed = false;
        area.width = fartherstCoordX;
        area.height = fartherstCoordY;
        int x = posX;
        int y = posY;

        if (x < 0) x = 0;
        if (y < 0) y = 0;
        Rectangle rect = new Rectangle(x, y, W, H);

        circles.addElement(rect);
        drawingPane.scrollRectToVisible(rect);

        

        int this_width = (x + W + 2);
        if (this_width > area.width) {
            
            if(this_width > fartherstCoordX)
            {
                fartherstCoordX = this_width;
                area.width = this_width;
                changed=true;
            }
             
            
        }

        int this_height = (y + H + 2);
        if (this_height > area.height) {
            
            if(this_height > fartherstCoordY)
            {
                fartherstCoordY = this_height;
                area.height = this_height;
                changed=true;
            }
            
        }

        if (changed) {
            //Update client's preferred size because
            //the area taken up by the graphics has
            //gotten larger or smaller (if cleared).
   
            drawingPane.setPreferredSize(area);

            //Let the scroll pane know to update itself
            //and its scrollbars.
            drawingPane.revalidate();
        }
        drawingPane.repaint();
    }
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
       
        //Create and set up the window.
        JFrame frame = new JFrame("MCO2 CCDSALG");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new MainDemo();
        newContentPane.setOpaque(true); //content panes must be opaque

        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                
                createAndShowGUI();
            }
        });
    }
}