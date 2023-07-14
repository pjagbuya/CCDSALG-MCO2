import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


public class GraphRepWindow extends JFrame implements ActionListener
{

    private JLabel label = new JLabel("Testing");
    private ArrayList<Node> nodeData;

    private JPanel graphPanel;
    private boolean updateLine;
    private Node selectedNode;
    JButton connectionButton;
    JButton searchIDButton = new JButton("Search ID");
    JPanel mainPanel = new JPanel();
    JTextField idField = new JTextField(1);

    JPanel friendListPanel;
    JScrollPane viewFriendListScroll;
    JScrollPane scroller;
    
    
    JTextField textFieldNodeA = new JTextField(10);
    JTextField textFieldNodeB = new JTextField(10);
    private boolean found;

    public GraphRepWindow (ArrayList<Node> nodeInfos)

    {
        JLabel labelForConnectionFinding;
        JPanel btnPanel;
        JPanel txtFieldPanel;
        JPanel menuBar = new JPanel(new GridLayout(2,4, 20, 20));
        


        JPanel connectionPanel = new JPanel();
        connectionButton = new JButton("Find Connection");
        connectionPanel.setLayout(new BoxLayout(connectionPanel, BoxLayout.Y_AXIS));
        labelForConnectionFinding = new JLabel("Find a Connection between two IDs");
        //Traversing Components
        connectionPanel.add(labelForConnectionFinding);
        connectionPanel.add(Box.createRigidArea(new Dimension(0,5))); 
        connectionPanel.add(Box.createRigidArea(new Dimension(0,5))); 
        connectionPanel.add(new JLabel("Node A"));
        connectionPanel.add(textFieldNodeA);
        connectionPanel.add(Box.createRigidArea(new Dimension(0,5))); 
        connectionPanel.add(new JLabel("Node B"));
        connectionPanel.add(textFieldNodeB);
        connectionPanel.add(connectionButton);

        searchIDButton.setMaximumSize(new Dimension(200, 20));
        searchIDButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
        idField.setMaximumSize(new Dimension(300, 20));
        idField.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnPanel = new JPanel();
        txtFieldPanel = new JPanel();

        txtFieldPanel.setLayout(new BoxLayout(txtFieldPanel, BoxLayout.Y_AXIS));
        
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.Y_AXIS));

        txtFieldPanel.add(idField);
        txtFieldPanel.add(new JLabel("Enter 'R' to reset and show whole graph representation"));
        btnPanel.add(searchIDButton);

        menuBar.add(btnPanel);
        menuBar.add(txtFieldPanel);

        
        connectionButton.setFocusable(false);
        connectionButton.addActionListener(this);


        searchIDButton.setFocusable(false);
        searchIDButton.addActionListener(this);


        label.setBounds(0,0,100,50);
        label.setFont(new Font(null, Font.PLAIN,25));
        nodeData = nodeInfos;

        graphPanel = new GraphRepPanel(nodeInfos);
        
        friendListPanel = new JPanel();

        //Add on Enter key press
        idField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // This line 'clicks' the button when Enter is pressed in the text field
                searchIDButton.doClick();
            }
        });
        
        addMouseMovedAtPanel(graphPanel);
        graphPanel.revalidate();
        graphPanel.repaint();


        scroller = new JScrollPane(graphPanel);
        scroller.setPreferredSize(new Dimension(800,800));
        viewFriendListScroll = new JScrollPane(friendListPanel);
        viewFriendListScroll.setPreferredSize(new Dimension(400, 400));
        
        mainPanel.setLayout(new GridLayout(2, 1, 20, 20));

        menuBar.add(viewFriendListScroll);
        menuBar.add(connectionPanel);

        mainPanel.add(menuBar, BorderLayout.NORTH);

        mainPanel.add(scroller, BorderLayout.CENTER);


        this.setContentPane(mainPanel);




        this.setSize(800,800);

        this.setVisible(true);

        
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        JLabel friendListLabel;
        Font heading = new Font("Arial", Font.BOLD, 20);
        Font font = new Font("Roboto", Font.PLAIN, 20);

        
        
        if(e.getSource()== searchIDButton)
        {
            
            
            Node selectNode;
            JLabel tempLabel;
            
            
            selectNode = findNode(nodeData, idField.getText());
            if(selectNode != null && idField.getText().length() != 0)
            {

                graphPanel = new GraphFriendsPanel(nodeData, selectNode);

                friendListPanel.removeAll();
                
                friendListPanel.setLayout(new BoxLayout(friendListPanel, BoxLayout.Y_AXIS));

                friendListLabel = new JLabel("Person with ID " + selectNode.getSelfNum() + " has " + selectNode.getRelations().size() + " friend/s, which have these IDs:");
                friendListLabel.setFont(heading);
                friendListPanel.add(friendListLabel);
                for(Node tempNode: selectNode.getRelations())
                {
                    tempLabel = new JLabel(tempNode.getSelfNum() + "");
                    tempLabel.setFont(font);
                    friendListPanel.add(tempLabel);
                }   

                viewFriendListScroll.setViewportView(friendListPanel);
                friendListPanel.repaint();
                friendListPanel.revalidate();
                addMouseMovedAtPanel(graphPanel);
                scroller.setViewportView(graphPanel);
                graphPanel.repaint();
                graphPanel.revalidate();
            }
            else if(idField.getText().equalsIgnoreCase("R") )
            {
                friendListPanel.removeAll();
                viewFriendListScroll.setViewportView(friendListPanel);
                friendListPanel.repaint();
                friendListPanel.revalidate();


                graphPanel = new GraphRepPanel(nodeData);
                addMouseMovedAtPanel(graphPanel);
                scroller.setViewportView(graphPanel);
                graphPanel.repaint();
                graphPanel.revalidate();
            }
            else
            {
                friendListPanel.removeAll();
                scroller.setViewportView(friendListPanel);
                friendListPanel.repaint();
                friendListPanel.revalidate();
                friendListPanel.removeAll();
                JOptionPane.showMessageDialog(null, "Your ID number entered does not exist");
            }

            // scroller = new JScrollPane(graphPanel);
            // scroller.setPreferredSize(new Dimension(800,800));
            // mainPanel.remove(graphPanel);           

            
  
        }
        else if(e.getSource() == connectionButton)
        {
            int prev;
            Node selA;
            Node selB;
            HashSet<Integer> visitedNodes;
            ArrayList<Integer> path;
            JLabel tempLabel;

            visitedNodes = new HashSet<Integer>();
            path = new ArrayList<Integer>();

            selA = findNode(nodeData, textFieldNodeA.getText());
            selB = findNode(nodeData, textFieldNodeB.getText());

            // visitedNodes.add(selA.getSelfNum());
            // path.add(selA.getSelfNum());
            if(selA.isRelated(selB))
            {
                path.add(selB.getSelfNum());
                return;
            }
            else
            {
                performDFS(selA, selB, visitedNodes, path);
            }


            
            if(!path.contains(selB.getSelfNum()))
                path.clear();
            friendListPanel.removeAll();
            friendListPanel.setLayout(new BoxLayout(friendListPanel, BoxLayout.Y_AXIS));
            

            

            
            if(path.size() != 1)
            {
                friendListLabel = new JLabel("There is connection from " + selA.getSelfNum() + " to " + selB.getSelfNum());
            }
            else
            {
                friendListLabel = new JLabel("There is NO connection from " + selA.getSelfNum() + " to " + selB.getSelfNum());
            }

            friendListPanel.add(friendListLabel);

            prev = selA.getSelfNum();

            for(int num: path)
            {


            
                tempLabel = new JLabel(prev + " is friends with " + num);
                tempLabel.setFont(font);
                friendListPanel.add(tempLabel);
                prev = num;
            
                
                
                
            }   

            viewFriendListScroll.setViewportView(friendListPanel);
            friendListPanel.repaint();
            friendListPanel.revalidate();
            

        }

    }

    private Node findNode(ArrayList<Node> nodeList, String text)
    {

        for(Node getNode : nodeList)
        {
            if((getNode.getSelfNum()+"").equalsIgnoreCase(text))
            {
                return getNode;
            }
        }

        return null;
    }
    public void performDFS(Node start, Node end, HashSet<Integer> visited, ArrayList<Integer> path) 
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
    public void addMouseMovedAtPanel(JPanel givenPanel)
    {
        givenPanel.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                ArrayList<Integer> tempXPOS;
                int diff;
                if(givenPanel instanceof GraphRepPanel)
                    tempXPOS =((GraphRepPanel)givenPanel).getXPOS();
                else
                    tempXPOS =((GraphFriendsPanel)givenPanel).getXPOS();
                for(int temp: tempXPOS)
                {


                    if(tempXPOS.indexOf(temp)==tempXPOS.size()-1)
                    {
                        diff = tempXPOS.get(tempXPOS.indexOf(temp)) - tempXPOS.get(tempXPOS.indexOf(temp)-1); 
                    }
                    else
                    {
                        diff = tempXPOS.get(tempXPOS.indexOf(temp)+1)- tempXPOS.get(tempXPOS.indexOf(temp));
                    }


                    if(x >= temp-15 && x <= temp + diff)
                    {
                        if(givenPanel instanceof GraphRepPanel)
                        {
                            ((GraphRepPanel)givenPanel).setUpdateLineBool(true);
                            ((GraphRepPanel)givenPanel).setSelectedNode(nodeData.get(tempXPOS.indexOf(temp)));
                        }
                        else
                        {
                            ((GraphFriendsPanel)givenPanel).setUpdateLineBool(true);
                            ((GraphFriendsPanel)givenPanel).setSelectedNode(nodeData.get(tempXPOS.indexOf(temp)));

                        }

                        givenPanel.repaint();
                        givenPanel.revalidate();
                        break;
                    }
                    else
                    {
                        if(givenPanel instanceof GraphRepPanel)
                            ((GraphRepPanel)givenPanel).setUpdateLineBool(false);
                        else
                            ((GraphFriendsPanel)givenPanel).setUpdateLineBool(false);

                        givenPanel.repaint();
                        givenPanel.revalidate();
                    }
                    
                }
            }
        });
    }



}
