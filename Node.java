import java.util.LinkedHashSet;
import java.util.Set;

public class Node {
    private int num;
    private Set<Node> relations;

    public Node(int num) {
        this.num = num;
        relations = new LinkedHashSet<Node>();
    }
    
    public Set<Node> getRelations() {
        return relations;
    }

    public int getSelfNum() {
        return num;
    }

    public void addRelation(Node node) {
        relations.add(node);
    }
}