import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        return adjacencyList.get(personNo);
    }

    public List<Node> findPath(int person1No, int person2No) {
        return performBFS(getPersonNode(person1No), getPersonNode(person2No));
    }
    
    // TODO: implement BFS
    private List<Node> performBFS(Node start, Node end) {
        return new ArrayList<>();
    }
}
