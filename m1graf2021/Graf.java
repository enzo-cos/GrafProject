package m1graf2021;

import java.io.*;
import java.util.*;

public class Graf {
    private Map<Node, List<Edge>> adjEdList;
    private Map<Integer,Node> NodesList; //Recup easily the node

    /**
     * Create an empty graph (default constructor)
     */
    public Graf(){
        this.adjEdList=new HashMap<Node,List<Edge>>();
        this.NodesList=new HashMap<Integer,Node>();
    }

    /**
     * Create a graph from a Successor Array
     * Throw RuntimeExcpeption if SA isn't valid
     * @param list : Successor Array of the graph
     */
    public Graf(int... list){
        this.adjEdList=new HashMap<Node,List<Edge>>();
        this.NodesList=new HashMap<Integer,Node>();
        int nbN=1;
        this.addNode(nbN);
        for(int i=0;i<list.length-1;i++){
            if(list[i]==0){ //
                nbN+=1;
                if(!this.existsNode(nbN)) this.addNode(nbN);
            }else{
                this.addEdge(nbN,list[i]);
            }
        }
        if(nbN!=this.nbNodes()){
            throw new RuntimeException("Invalid Graf : Error in constructor");
        }
    }

    /**
     * Create a directed graph from a file
     * The file must be correct and written with good alignments
     * @param fich
     */
    public Graf(File fich){
        if (!fich.exists()){
            throw new RuntimeException("File doesn't exist");
        }
        if (!fich.canRead()){
            throw new RuntimeException("File cannot be read");
        }
        this.adjEdList=new HashMap<Node,List<Edge>>();
        this.NodesList=new HashMap<Integer,Node>();
        try {
            FileReader f=new FileReader(fich);
            BufferedReader br = new BufferedReader(f);
            String line;
            String first;
            if((line = br.readLine()) != null){
                first=line.trim();
                first=first.substring(0,7);
                if(first.compareTo("digraph")!=0){
                    throw new RuntimeException("File given is not a directed graph");
                }
            }
            //read the nodes and egdes
            while ((line = br.readLine()) != null) {
                line = line.trim();
                String[] tab_str = line.split("->");
                first = tab_str[0].trim();
                try{
                    int first_length=first.length();
                    //Ex : 5[label="A"];
                    if(first_length>0) {
                        if(first.contains("label")){
                            String lab=tab_str[0].split("=")[1].trim();
                            lab=lab.substring(0,lab.indexOf("]"));
                            int ind=first.indexOf("[");
                            first=first.substring(0,ind).trim();
                            if(lab.contains("\"")) lab=lab.replaceAll("\"","");
                            Node n=new Node(Integer.parseInt(first),lab);
                            this.addNode(n);
                        }else if (first.substring(first_length - 1).compareTo(";") == 0) { //Ex : 5;
                            first = first.substring(0, first_length - 1);
                            this.addNode(Integer.parseInt(first));
                        }else {
                            Integer nb = Integer.parseInt(first);
                            String str_2=tab_str[1];
                            if(str_2.contains("len")){ //Ex : 1-> 1[len=4,label="4"];
                                int ind=str_2.indexOf("[");
                                Integer to=Integer.parseInt(str_2.substring(0,ind).trim());
                                String len=str_2.split("=")[1].trim();
                                int ind_virg=len.indexOf(",");
                                if(ind_virg==-1){
                                    len = len.substring(0, len.indexOf("]")).trim();
                                }else {
                                    len = len.substring(0, ind_virg).trim();
                                }
                                Integer weight=Integer.parseInt(len);
                                Edge e=new Edge(nb,to,weight);
                                this.addEdge(e);
                            }else {
                                String[] sub = str_2.split(",");
                                int len = sub.length;
                                int i = 0;
                                while (i < len) {
                                    String str = sub[i].trim();
                                    String ff = str.substring(str.length() - 1);
                                    if (ff.compareTo(";") == 0)
                                        str = str.substring(0, str.length() - 1);
                                    Integer nbNode = Integer.parseInt(str);
                                    this.addEdge(nb, nbNode);
                                    i++;
                                }
                            }
                        }
                    }
                }catch (NumberFormatException e){
                    //System.out.println(e);
                }
            }
        }catch (IOException e) {
            System.err.println("Error while reading file.");
        }

    }

    /**
     * Get a copy of the graf
     * @return this
     */
    public Graf getGraf(){
        return this;
    }

    /*********************************************************************
     ******************             NODES                  ***************
     ********************************************************************/


    /**
     * Get the number of nodes of the graph
     * @return nb : number of nodes
     */
    public int nbNodes(){
        return this.NodesList.size();
    }

    /**
     * Know if the Node n already exists in the graph
     * @param n : given node
     * @return boolean : true if it exists, false if not
     */
    public boolean existsNode(Node n){
        return this.adjEdList.containsKey(n);
    }
    /**
     * Know if a node's number already exists in the graph
     * @param id : id of given node
     * @return boolean : true if it exists, false if not
     */
    public boolean existsNode(int id){
        return this.NodesList.containsKey(id);
    }
    /**
     * Get the node instance whose number is id
     * @param id : id of given node
     * @return n : Node whose number is id
     */
    public Node getNode(int id){
        return this.NodesList.get(id);
    }

    /**
     * Add a node to the graph
     * Write on the error output if the node already exists
     * @param n : Node to add
     */
    public void addNode(Node n) {
        if(this.existsNode(n)){
            //Error
            System.err.println("Node "+n+" already exists");
        }else{
            this.adjEdList.put(n,new ArrayList<>());
            this.NodesList.put(n.getId(),n);
        }
    }
    /**
     * Add a node to the graph from an id
     * Write on the error output if the node already exists
     * @param id : Node's number to add
     */
    public void addNode(int id){
        Node n=new Node(id);
        this.addNode(n);
        return;
    }
    /**
     * Remove a node from the graph
     * Remove also the edges connected to this node
     * @param n : Node to remove
     */
    public void removeNode(Node n){
        if(!this.existsNode(n)) return;
        this.adjEdList.remove(n);
        this.NodesList.remove(n.getId());
        //Use getInEdge & remove Edge : big complexity
        for(Node node : this.adjEdList.keySet()){
            for(Edge e:this.adjEdList.get(node)){
                if(e.to().getId()==n.getId()){
                    this.adjEdList.get(e.from()).remove(e);
                    break; //it can't have several identical edges
                }
            }
        }

    }
    /**
     * Remove a node from the graph from an id
     * Remove also the edges connected to this node
     * @param id : Node's number to remove
     */
    public void removeNode(int id){
        this.removeNode(this.NodesList.get(id));
    }

    /**
     * Get a list of the successors of node n
     * @param n : Node whose successors are sought
     * @return list : list of successors
     */
    public List<Node> getSuccessors(Node n){
        List<Node> list=new ArrayList<>();
        if(n==null || !this.existsNode(n)) return list;
        for(Edge e : this.adjEdList.get(n)){
            list.add(e.to());
        }

        return list;
    }
    /**
     * Get a list of the successors of node whose number is id
     * @param id : Node's number whose successors are sought
     * @return list : list of successors
     */
    public List<Node> getSuccessors(int id){
        return this.getSuccessors(this.getNode(id));
    }

    /**
     * Know whether two nodes are adjacent in the graph
     * @param u : Node 1
     * @param v : Node 2
     * @return boolean : true if adjacent, else otherwise
     */
    public boolean adjacent(Node u, Node v){
        //better complexity than existEdge(u,v), existEdge(v,u)
        for(Node n : this.adjEdList.keySet()){
            if((u.getId()!=n.getId()) && (v.getId()!=n.getId())) continue;
            for(Edge e : this.adjEdList.get(n)){
                if((e.from().getId()==u.getId() && e.to().getId()==v.getId()) || (e.from().getId()==v.getId() && e.to().getId()==u.getId())) return true;
            }
        }
        return false;
    }
    /**
     * Know whether two nodes are adjacent in the graph
     * @param u : Node's number 1
     * @param v : Node's number 2
     * @return boolean : true if adjacent, else otherwise
     */
    public boolean adjacent(int u, int v){
        Node n1=this.getNode(u);
        Node n2=this.getNode(v);
        if(n1==null || n2==null) return false;
        return adjacent(n1,n2);
    }

    /**
     * Get the list of all the nodes in the graph
     * @return list : list of all the nodes
     */
    public List<Node> getAllNodes(){
       // return new ArrayList<>(this.adjEdList.keySet());
        List<Node> list=new ArrayList<>(this.adjEdList.keySet());
        Collections.sort(list);
        return list;

    }

    /**
     * Know the largest id used by a node in the graph
     * @return id : largest id used by a node
     */
    public int largestNodeId(){
        if(this.adjEdList.keySet().isEmpty()){
            throw new RuntimeException("The graph is empty");
        }
        Node n= Collections.max(this.adjEdList.keySet());
        return n.getId();
    }

    /*********************************************************************
     ******************             EDGES                  ***************
     ********************************************************************/

    /**
     * Know the number of edges of the graph
     * @return cpt : number of Edges
     */
    public int nbEdges(){
        int cpt=0;
        for(Node n : this.adjEdList.keySet()){
            cpt+=this.adjEdList.get(n).size();
        }
        return cpt;
    }

    /**
     * Know whether an edge exists between nodes u and v.
     * @param u : Source or Target Node
     * @param v : Target or Source Node
     * @return boolean : true if it exists, false if not
     */
    public boolean existsEdge(Node u, Node v){
        Edge e=new Edge(u,v);
        return this.existsEdge(e);
    }
    /**
     * Know whether an edge exists between nodes u and v.
     * @param u : Number of Source or Target Node
     * @param v : Number of Target or Source Node
     * @return boolean : true if it exists, false if not
     */
    public boolean existsEdge(int u, int v){
        Node f=this.getNode(u);
        Node t=this.getNode(v);
        if(f==null || t==null) return false; //Nodes don't exist so Edge don't exist
        return this.existsEdge(f,t);
    }
    /**
     * Know whether an edge exists between nodes u and v.
     * @param e : Edge reference
     * @return boolean : true if it exists, false if not
     */
    public boolean existsEdge(Edge e){
        //Parcour 1 à 1
        List<Edge> list=this.adjEdList.get(e.from());
        if(list==null||list.isEmpty()) return false;
        for(Edge curr : list){
            if (curr.from().getId()==e.from().getId() && curr.to().getId()==e.to().getId()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Add an edge from the source node towards the target node to
     * Add a node if doesn't exist
     * @param from : Source Node
     * @param to : Target Node
     */
    public void addEdge(Node from, Node to) {
        Edge e=new Edge(from,to);
        this.addEdge(e);
    }
    /**
     * Add an edge from the source node towards the target node to
     * Add a node if doesn't exist
     * @param from : Source Node
     * @param to : Target Node
     * @param weight : weight of edge
     */
    public void addEdge(Node from, Node to, Integer weight) {
        Edge e=new Edge(from,to,weight);
        this.addEdge(e);
    }
    /**
     * Add an edge from the source node towards the target node to from numbers.
     * Add a node if doesn't exist
     * @param from : Number of Source Node
     * @param to : Number of Target Node
     */
    public void addEdge(int from, int to) {
        Node f=this.getNode(from);
        Node t=this.getNode(to);
        //Create Nodes if don't exist, they will be add in addEdge(Edge e)
        if(f==null) f=new Node(from);
        if(t==null) t=new Node(to);
        this.addEdge(f,t);
    }
    /**
     * Add an edge from the source node towards the target node to from numbers.
     * Add a node if doesn't exist
     * @param from : Number of Source Node
     * @param to : Number of Target Node
     * @param weight : weight of edge
     */
    public void addEdge(int from, int to,Integer weight) {
        Node f=this.getNode(from);
        Node t=this.getNode(to);
        //Create Nodes if don't exist, they will be add in addEdge(Edge e)
        if(f==null) f=new Node(from);
        if(t==null) t=new Node(to);
        this.addEdge(f,t,weight);
    }
    /**
     * Add an edge to the graph.
     * Add a node if doesn't exist
     * @param e : Edge to add
     */
    public void addEdge(Edge e) {
        if(!existsNode(e.from())) this.addNode(e.from());
        if(!existsNode(e.to())) this.addNode(e.to());
        if(!this.adjEdList.get(e.from()).add(e)) System.err.println("ERROR AddEdge : "+e);
    }

    /**
     * Remove an edge between nodes from and to.
     * Write on the error output if the edge doesn't exist.
     * @param from : Number of Source Node
     * @param to : Number of Target Node
     */
    public void removeEdge(Node from, Node to){
        Edge e = new Edge(from,to);
        this.removeEdge(e);
    }

    /**
     * Remove an edge between nodes from and to from numbers.
     * Write on the error output if the edge doesn't exist.
     * @param from : Number of Source Node
     * @param to : Number of Target Node
     */
    public void removeEdge(int from, int to){
        Node f = this.getNode(from);
        Node t = this.getNode(to);
        if(f==null || t==null){
            System.err.println("Edge to remove doesn't exist");
            return;
        }
        this.removeEdge(f,t);
    }

    /**
     * Remove an edge to the graph.
     * Write on the error output if the edge doesn't exist.
     * @param e : Edge to remove
     */
    public void removeEdge(Edge e){
        if(!this.existsEdge(e)){
            System.err.println("Edge to remove doesn't exist");
            //ERROR
        }else{
            if(!this.adjEdList.get(e.from()).remove(e)) System.err.println("ERROR removeEdge : "+e);
        }
    }
    /**
     * Remove an edge to the graph without verify if the edge exist.
     * @param e : Edge to remove
     */
    public void removeEdgeBool(Edge e,boolean b){
        if(!(b)){
            System.err.println("Forbidden to pass here");
        }else{
            if(!this.adjEdList.get(e.from()).remove(e)) System.out.println("ERROR removeEdge : "+e);
        }
    }

    /**
     * Get the list of all edges leaving node n
     * @param n : Node
     * @return list : list of all edges leaving node n
     */
    public List<Edge> getOutEdges(Node n){
        if(n==null || !this.existsNode(n)) return new ArrayList<>();
        List<Edge> list=this.adjEdList.get(n);
        return list;
    }

    /**
     * Get the list of all edges leaving node n from node's number
     * @param nb : Node's number
     * @return list : list of all edges leaving a node
     */
    public List<Edge> getOutEdges(int nb){
        return this.getOutEdges(this.getNode(nb));
    }

    /**
     * Get the list of all edges entering node n
     * @param n : Node
     * @return list : list of all edges entering node n
     */
    public List<Edge> getInEdges(Node n){
        List<Edge> list=new ArrayList<>();
        if(n==null || !this.existsNode(n)) return list;
        for(Node node : this.adjEdList.keySet()){
            for(Edge e : this.adjEdList.get(node)){
                if(e.to().getId()==n.getId()) list.add(e);
            }
        }
        return list;
    }

    /**
     * Get the list of all edges entering node n from node's number
     * @param nb : Node's number
     * @return list : list of all edges entering a node
     */
    public List<Edge> getInEdges(int nb){
        return this.getInEdges(this.getNode(nb));
    }

    /**
     * Get the list of all edges incident to node n
     * @param n : Node
     * @return list : list of all edges incident to node n
     */
    public List<Edge> getIncidentEdges(Node n){
        List<Edge> list=this.getInEdges(n);
        for(Edge e : list){
            if(e.isSelfLoop()){
                list.remove(e); //No duplicates, it will be added with the function getOutEdges
                break;
            }
        }
        list.addAll(this.getOutEdges(n));
        return list;
    }

    /**
     * Get the list of all edges incident to node n from node's number
     * @param nb : Node's number
     * @return list : list of all edges incident to a node
     */
    public List<Edge> getIncidentEdges(int nb){
        return this.getIncidentEdges(this.getNode(nb));
    }

    /**
     * Get the list of all the edges of the graph
     * @return list : list of all the edges
     */
    public List<Edge> getAllEdges(){
        //Parcours 1 à 1
        List<Edge> list=new ArrayList<>();
        for (Node n : this.adjEdList.keySet()){
            for(Edge e : this.adjEdList.get(n)){
                list.add(e);
            }
        }
        Collections.sort(list);
        return list;
    }

    /**
     * Know the in-degree of node n
     * @param n : Node
     * @return nb : in-degree
     */
    public int inDegree(Node n){
        return this.getInEdges(n).size();
    }

    /**
     * Know the in-degree of node n from id
     * @param id : Node's number
     * @return nb : in-degree
     */
    public int inDegree(int id){
        return this.inDegree(this.getNode(id));
    }

    /**
     * Know the out-degree of node n
     * @param n : Node
     * @return nb : out-degree
     */
    public int outDegree(Node n){
        return this.getOutEdges(n).size();
    }

    /**
     * Know the out-degree of node n from id
     * @param id : Node's number
     * @return nb : out-degree
     */
    public int outDegree(int id){
        return this.outDegree(this.getNode(id));
    }

    /**
     * Know the degree of node n
     * @param n : Node
     * @return nb : degree of node
     */
    public int degree(Node n){
        return this.inDegree(n)+this.outDegree(n);
    }

    /**
     * Know the degree of node n from id
     * @param id : Node's number
     * @return nb : degree of node
     */
    public int degree(int id){
        return this.degree(this.getNode(id));
    }

    /**
     * Obtain a representation of the graph as an adjacency matrix.
     * @return a : adjacency matrix
     */
    public int[] toSuccessorArray(){
        int maxNode=this.largestNodeId();
        int[] list_node=new int[maxNode];
        for(int i=0;i<maxNode;i++){
            list_node[i]=i+1;
        }
        List<Edge> edgeList=this.getAllEdges();
        int size = list_node.length+edgeList.size();
        int[] a = new int[size];
        int ind=0;
        for(int k : list_node){
            edgeList=this.getOutEdges(k);
            Collections.sort(edgeList);
            for(Edge e : edgeList){
                a[ind]=e.to().getId();
                ind++;
            }
            a[ind]=0;
            ind++;
        }

        return a;
    }

    /**
     * Obtain a representation of the graph in the SA formalism.
     * @return a : adjMatrix
     */
    public int[][] toAdjMatrix(){
        int maxNode=this.largestNodeId();
        List<Edge> edgeList=this.getAllEdges();
        int[][] a = new int[maxNode][maxNode];
        for(int i=0;i<maxNode;i++){
            for(int j=0;j<maxNode;j++){
                a[i][j]=0;
            }
        }
        for(Edge e : edgeList){
            a[e.from().getId()-1][e.to().getId()-1]+=1;
        }
        return a;
    }

    /**
     * Compute in a new graph the reverse of the graph
     * @return g : reverse of the graph
     */
    public Graf getReverse(){
        Graf g = new Graf();
        for(Node n : this.NodesList.values()){
            g.addNode(n);
        }
        for(Edge e : this.getAllEdges()){
            g.addEdge(e.getSymmetric());
        }
        return g;
    }

    /**
     * Compute in a new graph the transitive closure of the graph
     * @return g : transitive closure of the graph
     */
    public Graf getTransitiveClosure(){
        Graf res = new Graf();
        List<Node> list_node=this.getAllNodes();
        for(Edge e : this.getAllEdges()){
            if(!res.existsEdge(e) && !e.isSelfLoop()) res.addEdge(e);
        }
        for(Node n : list_node){
            for(Node n1 : list_node){
                if(!res.existsEdge(n1,n)) continue;
                for (Node n2 : list_node){
                    if(!res.existsEdge(n,n2)) continue;
                    if(!res.existsEdge(n1,n2) && (n1!=n2)) res.addEdge(n1,n2);
                }
            }
        }
        return res;
    }

    /**
     * Get the Depth-First-Search traversal of the graph
     * @return list_node : list of nodes in DFS order
     */
    public List<Node> getDFS(){
        List<Node> list=new ArrayList<>();
        List<Node> visited=new ArrayList<>();
        //For unconnected graf
        for(Node n: this.getAllNodes()){
            if(!visited.contains(n)){
                list.addAll(getDFS_rec(n,visited));
            }
        }
        return list;
    }

    /** Recursive version for DFS
     * @param n : current Node
     * @param visited : list of visited nodes
     * @return list : list to return
     */
    public List<Node> getDFS_rec(Node n,List<Node> visited){
        List<Node> list=new ArrayList<>();
        visited.add(n);
        list.add(n);
        for (Node node : this.getSuccessors(n)){
            if(!visited.contains(node)){
                list.addAll(getDFS_rec(node,visited));
            }
        }
        return list;
    }

    /**
     * Get the Breadth-First-Search traversal of the graph
     * @return list_node : graph BFS path
     */
    public List<Node> getBFS(){
        List<Node> list_node=new ArrayList<>();
        List<Node> visited=new ArrayList<>();
        List<Node> pile=new ArrayList<>();
        //For unconnected graf
        for(Node node: this.getAllNodes()){
            if(!visited.contains(node)){
                visited.add(node);
                pile.add(node);
                while(!pile.isEmpty()){
                    Node s=pile.remove(0);
                    list_node.add(s);
                    for(Node n : this.getSuccessors(s)){
                        if(!visited.contains(n)){
                            visited.add(n);
                            pile.add(n);
                        }
                    }
                }
            }
        }
        return list_node;
    }

    /**
     * Export the graph as a String in the DOT syntax
     * @return str : graph as a String in the DOT syntax
     */
    public String toDotString(){
        String str="";
      //  str+="digraph {\n\trankdir=LR;\n";
        str+="digraph {\n";
        List<Node> list_node=this.getAllNodes();
        for(Node n : list_node){
            String nb=Integer.toString(n.getId());
            if(n.getName()!=null && !n.getName().isEmpty()){
                str+="\t"+nb+"[label=\""+n.getName()+"\"];\n";
            }
            List<Edge> list_edges=this.adjEdList.get(n);
            if(list_edges==null || list_edges.isEmpty()){
                str+="\t"+nb+";\n";
                continue;
            }
            Collections.sort(list_edges);
            boolean new_l=true; //Know if we have to put a "," and know if it's a new line or not
            for(Edge e : list_edges){
                if(new_l) str+="\t"+nb+" -> ";
                if(e.getWeight()!=null){
                    String w = Integer.toString(e.getWeight()); //get weight
                    if(new_l){
                        str += e.to().getId() + "[len=" + w + ",label=" + w + "];\n";
                    }else{ //write a new line
                        str += ";\n" + nb + " -> " + e.to().getId() + "[len=" + w + ",label=" + w + "];\n";
                        new_l=true;
                    }
                }else{ //no weight
                    if(!new_l) str+=", ";
                    str+=Integer.toString(e.to().getId());
                    new_l=false;
                }
            }
            if(!new_l) str+=";\n";
        }
        str+="}";
        return str;

         /* only line per line

            for(Edge e : list_edges){
                str+=nb+" -> "+e.to().getId();
                if(e.getWeight()!=null){
                    String w = Integer.toString(e.getWeight());
                    str += e.to().getId() + "[len=" + w + ",label=" + w + "];\n";
                }else{
                    str+=e.to().getId()+";\n";
                }
            }
             */
    }

    /**
     * Export the graph as a File in the DOT syntax
     * No extension needed, it will create a file .gv
     * @param fileName : Name of the file to create
     */
    public void toDotFile(String fileName){
        String str=this.toDotString();
        File fich=new File(fileName+".gv");
        try{
            FileWriter fw = new FileWriter(fich);
            fw.write(str);
            fw.close();
        }catch (Exception e ){
            System.err.println("Error while opening file");
        }
    }

    /**
     * Get a String representation of graph
     * @return s : String representation
     */
    public String toString(){
        String s="[ ";
        int[] a=this.toSuccessorArray();
        for(int k : a){
            s+= k+" , ";
        }
        s+="]";
        return s;
    }


}
