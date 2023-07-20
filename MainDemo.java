import java.util.*;

public class MainDemo {
    private static Scanner sc;
    private static FriendGraph friendGraph;
    private static boolean shouldTerminate;

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        friendGraph = null;
        shouldTerminate = false;

        while (true) {
            FileReader fr = new FileReader();

            System.out.print("Input file: ");
            friendGraph = fr.readFileNodes(sc.nextLine());

            if (friendGraph != null) {
                System.out.println("File is found. Graph is Loaded.");
                break;
            } else {
                System.out.println("ERROR: Unable to load file! Kindly try again.");
            }
        }

        if (friendGraph.isEmpty()) {
            System.out.println(Paint.paintTextOrange("No data to display"));
            sc.close();
            return;
        }

        while (!shouldTerminate) {
            System.out.println();
            displayMenu();
        }

        System.out.println("Terminating program...");
        sc.close();
    }

    private static void displayMenu() {
        int input;
        boolean invalidOption = true;

        while (invalidOption) {
            System.out.println(Paint.paintTextMagenta("MAIN MENU"));
            System.out.println(Paint.paintTextCyan("[1]") + " Get friend list");
            System.out.println(Paint.paintTextCyan("[2]") + " Get connection");
            System.out.println(Paint.paintTextCyan("[3]") + " Exit");
            
            System.out.print("\nInput choice: ");
            input = Integer.parseInt(sc.nextLine());

            switch (input) {
                case 1: {
                    System.out.println();
                    System.out.println(Paint.paintTextMagenta("GET FRIEND LIST"));
                    System.out.println(Paint.paintTextMagenta("---------------"));

                    System.out.print("\nEnter person ID: ");

                    int inputSelected = sc.nextInt();
                    sc.nextLine();

                    Node personNode = friendGraph.getPersonNode(inputSelected);

                    if (personNode != null) {
                        displayFriendList(personNode);
                    } else {
                        System.out.print(Paint.paintTextOrange("ERROR: ID does not exist."));
                    }

                    invalidOption = false;
                    break;
                }

                case 2: {
                    System.out.println();
                    System.out.println(Paint.paintTextMagenta("GET CONNECTIONS"));
                    System.out.println(Paint.paintTextMagenta("---------------"));
                    System.out.println();

                    System.out.print(
                        "Enter " + Paint.paintTextCyan("first") + " person ID: ");
                    int selectA = sc.nextInt();
                    sc.nextLine();

                    System.out.print(
                        "Enter " + Paint.paintTextCyan("second") + " person ID: ");
                    int selectB = sc.nextInt();
                    sc.nextLine();

                    if (
                        friendGraph.getPersonNode(selectA) == null || 
                        friendGraph.getPersonNode(selectB) == null
                    ) {
                        System.out.println(Paint.paintTextOrange("ERROR: Invalid IDs."));
                        break;
                    }
                    
                    System.out.println();

                    List<Node> path = friendGraph.findPath(selectA, selectB);
                    displayPath(selectA, selectB, path);
                    break;
                }

                case 3: {
                    invalidOption = false;
                    shouldTerminate = true;
                    break;
                }

                default:
                    System.out.println(Paint.paintTextOrange("ERROR: Please choose from 1-3."));
            }

            System.out.println();
        }
    }

    private static void displayFriendList(Node givenNode) {
        System.out.println();
        System.out.println(
            "Person of ID " + Paint.paintTextCyan(givenNode.getSelfNum()+"") + 
            " has " + Paint.paintTextYellow(givenNode.getRelations().size()+"") + 
            " friend/s.");

        System.out.print("List of friends: ");

        for (Node temp : givenNode.getRelations()) {
            System.out.print(Paint.paintTextGreen(temp.getSelfNum() + " "));
        }

        if (givenNode.getRelations().size() == 0) {
            System.out.print(Paint.paintTextOrange("NONE"));
        }
    }

    private static void displayPath(int firstNum, int secNum, List<Node> path) {
        if (path == null) {
            System.out.println(Paint.paintTextOrange(
                "Cannot find a connection between " + firstNum + 
                " and " + secNum + "."));
            return;
        }

        System.out.println(Paint.paintTextGreen(
            "There is a connection from "+ firstNum + " and " + secNum + "!"));

        for (int i = 1; i < path.size(); i++) {
            Node prev = path.get(i - 1);
            Node curr = path.get(i);

            System.out.println(
                Paint.paintTextCyan(prev.getSelfNum() + "") + " is friends with " + 
                Paint.paintTextCyan(curr.getSelfNum() + ""));
        }
    }

}
