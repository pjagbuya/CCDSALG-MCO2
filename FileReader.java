import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileReader {
    public FriendGraph readFileNodes(String fileName) {
        try {
            File file = new File(fileName);
            Scanner scFile = new Scanner(file);
            
            int personCount = scFile.nextInt();
            int relationsCount = scFile.nextInt(); 
            
            FriendGraph graph = new FriendGraph(personCount);

            for (int i = 0; i < relationsCount; i++) {
                int friendNo = scFile.nextInt();
                int personNo = scFile.nextInt();

                graph.addConnection(personNo, friendNo);
            }

            scFile.close();
            return graph;
        } catch (FileNotFoundException e) {
            return null;
        }
    }
}
