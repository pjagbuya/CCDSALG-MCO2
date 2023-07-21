import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class FriendGraph {
    private ArrayList<Node> adjacencyList;

    public FriendGraph(int nodeCount) {
        adjacencyList = new ArrayList<>(Collections.nCopies(nodeCount, null));
    }

    public boolean isEmpty() {
        return adjacencyList.isEmpty();
    }

    public void addConnection(int personNo, int friendNo) {
        Node personNode = adjacencyList.get(personNo);
        Node friendNode = adjacencyList.get(friendNo);

        if (personNode == null) {
            personNode = new Node(personNo);
            adjacencyList.set(personNo, personNode);
        }

        if (friendNode == null) {
            friendNode = new Node(friendNo);
            adjacencyList.set(friendNo, friendNode);
        }

        personNode.addRelation(friendNode);
        friendNode.addRelation(personNode);
    }

    public Node getPersonNode(int personNo) {
        if (personNo >= adjacencyList.size()) {
            return null;
        }
        
        return adjacencyList.get(personNo);
    }

    public List<Node> findPath(int person1No, int person2No) {
        return performBFS(getPersonNode(person1No), getPersonNode(person2No));
    }
    
    private List<Node> performBFS(Node start, Node end) {
        Queue<Node> queue = new LinkedList<>();
        Set<Node> visited = new HashSet<>();
        Map<Node, Node> parentMapping = new HashMap<>();
        
        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            if (current == end) {
                return backtrackPath(parentMapping, start, end);
            }

            for (Node friend : current.getRelations()) {
                if (visited.contains(friend)) {
                    continue;
                }

                queue.add(friend);
                visited.add(friend);
                parentMapping.put(friend, current);                
            }
        }
        
        return null;
    }

    private List<Node> backtrackPath(
        Map<Node, Node> parentMapping, 
        Node start, 
        Node end
    ) {
        List<Node> path = new ArrayList<>();

        Node current = end;
        while (current != null) {
            path.add(current);
            current = parentMapping.get(current);
        }

        return path;
    }
}
