import m1graf2021.*;

import java.io.File;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MyMain {
    static ChinesePostman g=new ChinesePostman();
    static Graf graf=null;
    static String path="";
    /**
     * Display Graph's creation menu
     * @return
     */
    public static int aff_creation_menu(){
        System.out.println("\n***** GRAPH CREATION *****\nChoose what kind of graph you want to create : ");
        System.out.println("- 1 - for a directed graph");
        System.out.println("- 2 - for an undirected graph");
        System.out.println("- q - to stop\n\n");
        Scanner input = new Scanner(System.in);
        char choice = input.nextLine().charAt(0);
        switch (choice){
            case '1' :
                return 1;
            case '2' :
                return 2;
            case 'q' :
                return -1;
            default:
                System.out.println("Please choose a valid option or tap 'q'.");
                break;
        }
        return 0;
    }

    /**
     * Display Node's menu
     * @return
     */
    public static int nodeMenu(){
        System.out.println("\n***** NODES MENU *****\nWhat do you want to do  : ");
        System.out.println("- 1 - to get the numbers of nodes");//10
        System.out.println("- 2 - to add a node");//11
        System.out.println("- 3 - to remove a node");//12
        System.out.println("- 4 - to get the largest id of node of the graph");//13
        System.out.println("- 5 - to get the list of nodes of the graph");//14
        System.out.println("- 6 - to return to graph menu");//15
        System.out.println("- q - to stop\n\n");
        Scanner input = new Scanner(System.in);
        while (1==1) {
            char choice = input.nextLine().charAt(0);
            switch (choice) {
                case '1':
                    return 10;
                case '2':
                    return 11;
                case '3':
                    return 12;
                case '4':
                    return 13;
                case '5':
                    return 14;
                case '6':
                    return 15;
                case 'q':
                    return -1;
                default:
                    System.out.println("Please choose a valid option or tap 'q'.");
                    break;
            }
        }
    }

    /**
     * Display Edge's Menu
     * @return
     */
    public static int edgeMenu(){
        System.out.println("\n***** EDGES MENU *****\nWhat do you want to do  : ");
        System.out.println("- 1 - to get the numbers of edges");//20
        System.out.println("- 2 - to add an edge");//21
        System.out.println("- 3 - to remove an edge");//22
        System.out.println("- 4 - to get the list of edges of the graph");//23
        System.out.println("- 5 - to return to graph menu");//24
        System.out.println("- q - to stop\n\n");
        Scanner input = new Scanner(System.in);
        while (1==1) {
            char choice = input.nextLine().charAt(0);
            switch (choice) {
                case '1':
                    return 20;
                case '2':
                    return 21;
                case '3':
                    return 22;
                case '4':
                    return 23;
                case '5':
                    return 24;
                case 'q':
                    return -1;
                default:
                    System.out.println("Please choose a valid option or tap 'q'.");
                    break;
            }
        }
    }

    /**
     * Display Dot Menu
     * @return
     */
    public static int dotMenu(){
        System.out.println("\n***** DOT MENU *****\nWhat do you want to do  : ");
        System.out.println("- 1 - to print the dot representation of the graph");//30
        System.out.println("- 2 - to extract in a new file the graph in dot format");//31
        System.out.println("- 3 - to return to graph menu");//32
        System.out.println("- q - to stop\n\n");
        Scanner input = new Scanner(System.in);
        char choice = input.nextLine().charAt(0);
        switch (choice){
            case '1' :
                return 30;
            case '2' :
                return 31;
            case '3' :
                return 32;
            case 'q' :
                return -1;
            default:
                System.out.println("Please choose a valid option or tap 'q'.");
                break;
        }
        return 0;
    }

    /**
     * Display print Menu
     * @return
     */
    public static int printMenu(){
        System.out.println("\n***** PRINT MENU *****\nWhat do you want to do  : ");
        System.out.println("- 1 - to print the Successor Array of the graph");//40
        System.out.println("- 2 - to print the matrice");//41
        System.out.println("- 3 - to print the reverse Graph in S.A format");//42
        System.out.println("- 4 - to print the transitive closure in S.A format");//43
        System.out.println("- 5 - to print the BFS");//44
        System.out.println("- 6 - to print the DFS");//45
        System.out.println("- 7 - to return to graph menu");//46
        System.out.println("- q - to stop\n\n");
        Scanner input = new Scanner(System.in);
        while (true) {
            char choice = input.nextLine().charAt(0);
            switch (choice) {
                case '1':
                    return 40;
                case '2':
                    return 41;
                case '3':
                    return 42;
                case '4':
                    return 43;
                case '5':
                    return 44;
                case '6':
                    return 45;
                case '7':
                    return 46;
                case 'q':
                    return -1;
                default:
                    System.out.println("Please choose a valid option or tap 'q'.");
                    break;
            }
        }
    }

    /**
     * Display print Menu
     * @return
     */
    public static void dotFile(){
        System.out.println("\n***** Get a Graph from a Dot File *****\n\nwrite the path to your dot file : \nTap ENTER to validate\n\n");
        Scanner input = new Scanner(System.in);
        path = input.nextLine();
    }


    /**
     * ChinesePostmanMenu
     * @return
     */
    public static int ChinesePostmanMenu(){
        System.out.println("\n***** CHINESEPOSTMAN MENU *****\nWhat do you want to do : ");
        System.out.println("- 0 - to init the problem with your actual graph");
        System.out.println("- 1 - to load a graph from a dot File");
        System.out.println("- 2 - to know if your graph is Eulerian, semi-Eulerian or not Eulerian");
        System.out.println("- 3 - to get the Eulerian circuit of your graph, (resolving the ChinesePostman Problem if needed) ");
        System.out.println("- 4 - print your graph in the Dot format (do this after doing step '3')");
        System.out.println("- 5 - to extract your graph in a dot file");
        System.out.println("- q - to stop\n\n");

        Scanner input = new Scanner(System.in);
        char choice = input.nextLine().charAt(0);
        switch (choice){
            case '0' :
                try{
                    g=new ChinesePostman((UndirectedGraf) graf);
                }catch (Exception e ){
                    System.err.println("The actual graph is not an undirected graph");
                }
                return ChinesePostmanMenu();

            case '1' :
                dotFile();
                try{
                    File f =new File(path);
                    if(!(f.exists() && f.canRead())){
                        System.err.println("File not exist, please retry");
                        return ChinesePostmanMenu();
                    }
                    try {
                        g=new ChinesePostman(f);
                    }catch (Exception e){
                        System.err.println("invalide file");
                        return ChinesePostmanMenu();
                    }

                }catch (Exception e){
                    System.err.println("Invalide path\nPlease Retry\n");
                }finally {
                    return ChinesePostmanMenu();
                }

            case '2' : //Graph Eulerian or not
                int n=g.isEulerian();
                System.out.println("\n\n");
                if(n==0){
                    System.out.println("The graph is Eulerian");
                }else if(n==1){
                    System.out.println("The graph is Semi-Eulerian");
                }else if (n==-1){
                    System.out.println("The graph is not Eulerian");
                }

                return ChinesePostmanMenu();
            case '3' :
                boolean b=false;
                System.out.println("Do you want to compute the ChineseProblem with random Pair ?\ny for Yes     n for No\n");
                choice = input.nextLine().charAt(0);
                if(choice=='y') b=true;
                List<Edge> l=g.getEulerianCircuit(b);
                return ChinesePostmanMenu();
            case '4' : //print dotFile
                System.out.println(g.toDotString());
                return ChinesePostmanMenu();
            case '5' :
                System.out.println("Please write the name of the file\n");
                String strr=input.nextLine();
                g.toDotFile(strr);
                return ChinesePostmanMenu();
            case 'q' :
                return -1;
            default:
                System.out.println("Please choose a valid option or tap 'q'.");
                break;
        }
        return 0;
    }

    /**
     * Graph Menu
     * @return
     */
    public static int grafMenu(){
        System.out.println("\n***** GRAPH MENU *****\nWhat do you want to do : ");
        System.out.println("- 1 - to interact with Nodes");
        System.out.println("- 2 - to interact with Edges");
        System.out.println("- 3 - to interact with DotFormat");
        System.out.println("- 4 - to print the graph in different ways");
        System.out.println("- 5 - to access to the ChinesePostman Menu");
        System.out.println("- q - to stop\n\n");
        Scanner input = new Scanner(System.in);
        char choice = input.nextLine().charAt(0);
        switch (choice){
            case '1' :
                return nodeMenu();
            case '2' :
                return edgeMenu();
            case '3' :
                return dotMenu();
            case '4' :
                return printMenu();
            case '5' :
                return ChinesePostmanMenu();
            case 'q' :
                return -1;
            default:
                System.out.println("Please choose a valid option or tap 'q'.");
                break;
        }
        return 0;
    }
    public static void main(String[] args) throws Exception {
        //create new Graf with 7 nodes and 15 edges
        Graf g = new Graf(2, 3, 5, 0, 4, 1, 0, 4, 5, 7, 0,6,7 ,0, 4, 7, 0, 3,2, 0, 1, 0);
        System.out.println(g.nbNodes());
        System.out.println(g.nbEdges());
        //add and node and edges
        g.addNode(8);
        g.addEdge(5,8);g.addEdge(1,8);
        //remove node
        g.removeNode(7);
        g.removeEdge(1,5);
        //15 edges + 2 edges - all edges associated to node 7 - 1 = 12
        System.out.println(g.nbEdges());
        //Get the list of OutEdge of Node 1 : 1->2, 1->3, 1->8
        System.out.println(g.getOutEdges(1));
        //Get the matrix and Successor Array
        System.out.println("Matrice :");
        int[][] mat=g.toAdjMatrix();
        for(int a = 0;a<mat.length;a++) {
            System.out.println(Arrays.toString(mat[a]));
        }
        System.out.println("\n Successor Array :\n"+Arrays.toString(g.toSuccessorArray()));

        System.out.println("Get the reverse graph");
        System.out.println(g.getReverse().toDotString());
        System.out.println("Get the transitive closure");
        System.out.println(g.getTransitiveClosure().toDotString());
        //We can also get the dot format
        System.out.println(g.toDotString());
        //Or directly get a file
        g.toDotFile("myGraf");
        System.out.println(">>>> DFS of graf: \n"+g.getDFS());
        System.out.println(">>>> BFS of graf: \n"+g.getBFS());

        //create empty Undirected Graf
        UndirectedGraf ung=new UndirectedGraf();

        for(int k=1;k<5;k++){
            Node n=new Node(k);
            ung.addNode(n);
        }
        ung.addEdge(1,2);ung.addEdge(1,3);ung.addEdge(2,4);ung.addEdge(4,5);
        ung.addEdge(2,3);ung.addEdge(3,1);ung.addEdge(3,2);
        //5 nodes and 7 edges
        System.out.println(ung.nbNodes());
        System.out.println(ung.nbEdges());
        //Get the list of Out Edges of Node 1 : 1->2, 1->3, 1->3
        System.out.println(ung.getOutEdges(1));
        //Get the matrix and Successor Array
        System.out.println("Matrice :");
        mat=ung.toAdjMatrix();
        for(int a = 0;a<mat.length;a++) {
            System.out.println(Arrays.toString(mat[a]));
        }
        System.out.println("\n Successor Array :\n"+Arrays.toString(ung.toSuccessorArray()));
        //We can also get the dot format
        System.out.println(ung.toDotString());
        System.out.println(">>>> DFS of undirected graf: \n"+ung.getDFS());
        System.out.println(">>>> BFS of undirected graf: \n"+ung.getBFS());

        /******************************
         ******* Intercative menu *****
         *****************************/
        ChinesePostman chinese=new ChinesePostman(ung);
        System.out.println("\n\n\nInteractive menu :\n\n");
        Scanner input = new Scanner(System.in);
        boolean activ_menu=true;
        boolean digraf=false;

        int r=0;
        r=aff_creation_menu();
        switch (r){
            case 0:
                System.out.println("Time");
                return;
            case 1:
                graf=new Graf();
                digraf=true;
                break;
            case 2:
                graf=new UndirectedGraf();
                digraf=false;
                break;
            case -1:
                activ_menu=false;
                System.out.println("\nClosing menu\n");
                return ;
        }
        String choice="";
        int nb=-1;
        int nb1=-1;int nb2=-1;
        Edge ee=null;
        while (activ_menu){
            r=grafMenu();
            switch (r){
                case 0:
                    System.out.println("Time");
                    break;
                //NODES
                case 10: //get number of node
                    System.out.println("Number of nodes in the graph : "+graf.nbNodes()+" nodes.\n");
                    break;
                case 11://add node
                    System.out.println("Add node : Give the ID of the node you want to add");
                    choice = input.nextLine();
                    nb=Integer.parseInt(choice);
                    while (nb<0){
                        System.err.println("The ID must be a positive number");
                        choice = input.nextLine();
                        nb=Integer.parseInt(choice);
                    }
                    graf.addNode(nb);
                    System.out.println("Node "+nb+" Added");
                    break;
                case 12://Remove Node
                    System.out.println("Remove node : Give the ID of the node you want to remove");
                    choice = input.nextLine();
                    nb=Integer.parseInt(choice);
                    while (nb<0){
                        System.err.println("The ID must be a positive number");
                        choice = input.nextLine();
                        nb=Integer.parseInt(choice);
                    }
                    if (!graf.existsNode(nb)) {
                        System.err.println("The node doesn't exist");
                    }else {
                        graf.removeNode(nb);
                        System.out.println("Node " + nb + " Removed");
                    }
                    break;
                case 13://Largest ID
                    System.out.println("Largest Node ID : "+graf.largestNodeId()+".\n");
                    break;
                case 14://getAllNodes
                    System.out.println("List of nodes : "+graf.getAllNodes().toString()+"\n");
                    break;
                case 15://return to graph menu
                case 24:
                case 32:
                case 46:
                    break;
                //EDGES
                case 20://get number of edges
                    System.out.println("Number of edges in the graph : "+graf.nbEdges()+" edges.\n");
                    break;
                case 21://add edge
                    System.out.println("Add Edge : Give the ID of the from Node");
                    choice = input.nextLine();
                    nb1=Integer.parseInt(choice);
                    while (nb1<0){
                        System.err.println("The From Node's ID must be a positive number");
                        choice = input.nextLine();
                        nb1=Integer.parseInt(choice);
                    }
                    System.out.println("Add Edge : Give the ID of the Target Node");
                    choice = input.nextLine();
                    nb2=Integer.parseInt(choice);
                    while (nb2<0){
                        System.err.println("The Target Node's ID must be a positive number");
                        choice = input.nextLine();
                        nb2=Integer.parseInt(choice);
                    }
                    //weight?
                    ee=new Edge(nb1,nb2);
                    graf.addEdge(ee);
                    System.out.println("Edge "+ee+" Added");
                    break;
                case 22://remove edge
                    System.out.println("Remove Edge : Give the ID of the from Node");
                    choice = input.nextLine();
                    nb1=Integer.parseInt(choice);
                    while (nb1<0){
                        System.err.println("The From Node's ID must be a positive number");
                        choice = input.nextLine();
                        nb1=Integer.parseInt(choice);
                    }
                    choice = input.nextLine();
                    System.out.println("Remove Edge : Give the ID of the Target Node");
                    nb2=Integer.parseInt(choice);
                    while (nb2<0){
                        System.err.println("The Target Node's ID must be a positive number");
                        choice = input.nextLine();
                        nb2=Integer.parseInt(choice);
                    }
                    //weight?
                    ee=new Edge(nb1,nb2);
                    if (!graf.existsEdge(ee)) {
                        System.err.println("The Edge doesn't exist");
                    }else {
                        System.out.println("Edge " + ee + " Removed");
                    }
                    break;
                case 23://get the list of edge
                    System.out.println("List of edges : "+graf.getAllEdges().toString()+"\n");
                    break;
                //Dot Menu
                case 30://print dot representation
                    System.out.println(graf.toDotString());
                    break;
                case 31://extract in file
                    System.out.println("write the name of the file without the extension");
                    String strrr=input.nextLine();
                    graf.toDotFile(strrr);
                    System.out.println("File "+strrr+".gv created");
                    break;
                //Print Menu
                case 40://Successor  Array
                    System.out.println("Successor Array : \n"+Arrays.toString(graf.toSuccessorArray()));
                    break;
                case 41://Matrice
                    int[][] matt =graf.toAdjMatrix();
                    System.out.println("Adjacent Matrix : ");
                    for(int k=0;k<matt.length;k++){
                        System.out.println(Arrays.toString(matt[k]));
                    }
                    break;
                case 42://reverse Graph
                    System.out.println("Reverse Graph : \n"+Arrays.toString(graf.getReverse().toSuccessorArray()));
                    break;
                case 43://transitive closure
                    System.out.println("Transitive closure : \n"+Arrays.toString(graf.getTransitiveClosure().toSuccessorArray()));
                    break;
                case 44 : //BFS
                    System.out.println("BFS : \n"+graf.getBFS().toString());
                    break;
                case 45 : //DFS
                    System.out.println("DFS : \n"+graf.getDFS().toString());
                    break;

                case -55 : //get graph from dot
                    File f = new File(path);
                    if(!(f.exists() && f.canRead())) {
                        System.err.println("File doesn't exist, please retry\n");
                    }
                    break;

                case -1:
                    activ_menu=false;
                    System.out.println("\nClosing menu\n");
            }
        }
        return ;






    }
}
