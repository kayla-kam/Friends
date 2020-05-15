package friends;

import java.io.*;
import java.util.*;

public class FriendsDriver {

    public static void main(String[] args) throws FileNotFoundException {
        // TODO Auto-generated method stub
        
        Friends friendObject = new Friends();

        System.out.println("++++++++++ FRIENDS DRIVER ++++++++++\n");
        String file = "assnsample.txt";
        Scanner fileScanner = new Scanner(new File(file));
        
        System.out.println("[FILE] ==> " + (new File(file)).getAbsolutePath() + "\n");
        
        Graph graph = new Graph(fileScanner);
        
        String person1 = "p1";
        String person2 = "p198";
        
        //System.out.println("++++++++++ S H O R T E S T    C H A I N ++++++++++");
//        
        //System.out.println(Friends.shortestChain(graph, person1, person2));
        
       System.out.println("++++++++++ C L I Q U E S ++++++++++");
       System.out.println(Friends.cliques(graph, "rutgers"));
        
        System.out.println("++++++++++ C O N N E C T O R S ++++++++++");
        
        System.out.println(Friends.connectors(graph));
        
    }

}