package m1graf2021;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class UndirectedGraf extends Graf {

    /**
     * Create an empty undirected graph (default constructor)
     */
    public UndirectedGraf(){
        super();
    }

    /**
     * Create an undirected graph from a Successor Array
     * @param list : Successor Array of the graph
     */
    public UndirectedGraf(int... list){
        super(list);
    }

    /**
     * Create an undirected graph from a file
     * The file must be correct and written with good alignments
     * @param fich
     */
    public UndirectedGraf(File fich){
        super();
        if (!fich.exists()){
            throw new RuntimeException("File doesn't exist");
        }
        if (!fich.canRead()){
            throw new RuntimeException("File cannot be read");
        }
        try {
            FileReader f=new FileReader(fich);
            BufferedReader br = new BufferedReader(f);
            String line;
            String first;
            if((line = br.readLine()) != null){
                first=line.trim();
                first=first.substring(0,5);
                if(first.compareTo("graph")!=0){
                    throw new RuntimeException("File given is not an undirected graph");
                }
            }
            //read the nodes and egdes
            while ((line = br.readLine()) != null) {
                line = line.trim();
                String[] tab_str = line.split("--");
                first = tab_str[0].trim();
                try{
                    int first_length=first.length();
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
                            if(str_2.contains("len")){ //Ex : 1-- 1[len=4,label="4"];
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
                    //System.err.println(e);
                }
            }
        }catch (IOException e) {
            System.err.println("Error while reading file.");
        }

    }

    /**
     * Know the number of edges of the undirected graph
     * @return number of edges
     */
    @Override
    public int nbEdges(){
        return this.getAllEdges().size();
    }

    /**
     * Know whether an edge exists between nodes u and v.
     * @param u : Source or Target Node
     * @param v : Target or Source Node
     * @return boolean : true if it exists both ways, false if not
     */
    @Override
    public boolean existsEdge(Node u, Node v){
        Edge e=new Edge(u,v);
        return this.existsEdge(e);
    }

    /**
     * Know whether an edge exists between nodes u and v.
     * @param u : Number of Source or Target Node
     * @param v : Number of Target or Source Node
     * @return boolean : true if it exists both ways, false if not
     */
    @Override
    public boolean existsEdge(int u, int v){
        Node f=new Node(u);
        Node t=new Node(v);
        return this.existsEdge(f,t);
    }
    /**
     * Know whether an edge exists between nodes u and v.
     * @param e : Edge reference
     * @return boolean : true if it exists both ways, false if not
     */
    @Override
    public boolean existsEdge(Edge e){
        return (super.existsEdge(e) && super.existsEdge(e.getSymmetric()));
    }


    /**
     * Add an edge in both  ways from the source node towards the target node to
     * Add a node if don't exist
     * @param from : Source Node
     * @param to : Target Node
     */
    @Override
    public void addEdge(Node from, Node to) {
        Edge e=new Edge(from,to);
        this.addEdge(e);
    }

    /**
     * Add an edge in both ways from the source node towards the target node to from numbers.
     * Add a node if doesn't exist
     * @param from : Number of Source Node
     * @param to : Number of Target Node
     */
    @Override
    public void addEdge(int from, int to) {
        Node f=new Node(from);
        Node t=new Node(to);
        this.addEdge(f,t);
    }
    /**
     * Add an edge in both ways from the source node towards the target node to from numbers.
     * Add a node if doesn't exist
     * @param from : Number of Source Node
     * @param to : Number of Target Node
     * @param weight : weight of edge
     */
    @Override
    public void addEdge(int from, int to,Integer weight) {
        Node f=this.getNode(from);
        Node t=this.getNode(to);
        //Create Nodes if don't exist, they will be add in addEdge(Edge e)
        if(f==null) f=new Node(from);
        if(t==null) t=new Node(to);
        this.addEdge(f,t,weight);
    }
    /**
     * Add an edge in both ways from the source node towards the target node to
     * Add a node if doesn't exist
     * @param from : Source Node
     * @param to : Target Node
     * @param weight : weight of edge
     */
    @Override
    public void addEdge(Node from, Node to, Integer weight) {
        Edge e=new Edge(from,to,weight);
        this.addEdge(e);
    }
    /**
     * Add an edge in both ways to the graph.
     * Add a node if doesn't exist
     * @param e : Edge to add
     */
    @Override
    public void addEdge(Edge e) {
        super.addEdge(e);
        super.addEdge(e.getSymmetric());
    }

    /**
     * Remove an edge between nodes from and to.
     * Write on the error output if the edge doesn't exist.
     * @param from : Number of Source Node
     * @param to : Number of Target Node
     */
    @Override
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
    @Override
    public void removeEdge(int from, int to){
        //Pas de mélange node int ?
        Node f = new Node(from);
        Node t = new Node(to);
        this.removeEdge(f,t);
    }

    /**
     * Remove an edge to the graph.
     * Write on the error output if the edge doesn't exist.
     * @param e : Edge to remove
     */
    @Override
    public void removeEdge(Edge e){
        if(!existsEdge(e)) {
            System.err.println("Edge to remove doesn't exist");
        }else{
           super.removeEdgeBool(e,true);
           super.removeEdgeBool(e.getSymmetric(),true);
        }
    }

    /**
     * Get a list of the successors of node n in undirected graf
     * @param n : Node whose successors are sought
     * @return list : list of successors
     */
    @Override
    public List<Node> getSuccessors(Node n){
        List<Edge> list=super.getAllEdges();
        List<Node> res=new ArrayList<>();
        for(Edge e : list){
            if(e.from().getId()==n.getId()) {
                if (!res.contains(e.to())) res.add(e.to());
            }
        }
        return res;
    }

    /**
     * Get the list of all edges entering node n for undirected graf
     * @param n : Node
     * @return list : list of all edges entering node n
     */
    @Override
    public List<Edge> getInEdges(Node n){
        if(n==null || !this.existsNode(n)) return new ArrayList<>();
        List<Edge> list_e=super.getAllEdges();
        List<Edge> list=new ArrayList<>();
        boolean b=false;
        for(Edge e : list_e){
            if(e.to().getId()==n.getId()){
                //keep only one self loop
                if(e.isSelfLoop()){
                    if(!b){
                        list.add(e);
                        b=true;
                    }else{
                        b=false;
                    }
                }else{
                    list.add(e);
                    b=false;
                }
            }

        }
        return list;
    }

    /**
     * Get the list of all edges leaving node n for undirected graf
     * @param n : Node
     * @return list : list of all edges leaving node n
     */
    @Override
    public List<Edge> getOutEdges(Node n){
        if(n==null || !this.existsNode(n)) return new ArrayList<>();
        List<Edge> list_e=super.getAllEdges();
        List<Edge> list=new ArrayList<>();
        boolean b=false;
        for(Edge e : list_e){
            if(e.from().getId()==n.getId()){
                //keep only one self loop
                if(e.isSelfLoop()){
                    if(!b){
                        list.add(e);
                        b=true;
                    }else{
                        b=false;
                    }
                }else{
                    list.add(e);
                    b=false;
                }
            }

        }
        return list;
    }

    /**
     * Know the in-degree of node n
     * @param n : Node
     * @return nb : in-degree
     */
    @Override
    public int inDegree(Node n){
        if(n==null || !this.existsNode(n)) return 0;
        List<Edge> list_e=super.getAllEdges();
        List<Edge> list=new ArrayList<>();
        for(Edge e : list_e){
            if(e.to().getId()==n.getId()){
                list.add(e);
            }
        }
        return list.size();
    }

    /**
     * Know the in-degree of node n from id
     * @param id : Node's number
     * @return nb : in-degree
     */
    @Override
    public int inDegree(int id){
        return this.inDegree(this.getNode(id));
    }

    /**
     * Know the out-degree of node n
     * @param n : Node
     * @return nb : out-degree
     */
    @Override
    public int outDegree(Node n){
        if(n==null || !this.existsNode(n)) return 0;
        List<Edge> list_e=super.getAllEdges();
        List<Edge> list=new ArrayList<>();
        for(Edge e : list_e){
            if(e.from().getId()==n.getId()){
                list.add(e);
            }
        }
        return list.size();
    }

    /**
     * Know the out-degree of node n from id
     * @param id : Node's number
     * @return nb : out-degree
     */
    @Override
    public int outDegree(int id){
        return this.outDegree(this.getNode(id));
    }

    /**
     * Know the degree of node n
     * @param n : Node
     * @return nb : degree of node
     */
    @Override
    public int degree(Node n){
        return this.outDegree(n);
    }

    /**
     * Know the degree of node n from id
     * @param id : Node's number
     * @return nb : degree of node
     */
    @Override
    public int degree(int id){
        return this.degree(this.getNode(id));
    }

    /**
     * Get the list of all edges incident to node n
     * @param n : Node
     * @return list : list of all edges incident to node n
     */
    @Override
    public List<Edge> getIncidentEdges(Node n){
        List<Edge> list=this.getOutEdges(n);
        return list;
    }

    /**
     * Get the list of all edges incident to node n from node's number
     * @param nb : Node's number
     * @return list : list of all edges incident to a node
     */
    @Override
    public List<Edge> getIncidentEdges(int nb){
        return this.getIncidentEdges(this.getNode(nb));
    }

    @Override
    /**
     * Get the list of all the edges of the undirected graph
     * @return list : list of all the edges
     */
    public List<Edge> getAllEdges(){
        //Parcours 1 à 1
        List<Edge> list=super.getAllEdges();
        List<Edge> toremove=new ArrayList<>();
        for(Edge e:list){
            if(e.from().getId()>=e.to().getId()){
                toremove.add(e);
            }
        }
        boolean b=false;
        //self loop : keep only one
        for(Edge e : toremove){
            if(e.isSelfLoop()){
                if(b) {
                    list.remove(e);
                    b=false;
                }else{
                    b=true;
                }
            }else {
                list.remove(e);
                b=false;
            }
        }
        return list;
    }

    /**
     * Obtain a representation of the graph as an adjacency matrix.
     * @return a : adjacency matrix
     */
    @Override
    public int[] toSuccessorArray() {
        int maxNode = this.largestNodeId();
        int[] list_node = new int[maxNode];
        for (int i = 0; i < maxNode; i++) {
            list_node[i] = i + 1;
        }
        List<Edge> edgeList = this.getAllEdges();
        int size = list_node.length + edgeList.size() ;
        int[] a = new int[size];
        int ind = 0;
        for (int k : list_node) {
            edgeList = getOutEdges(k);
            Collections.sort(edgeList);
            for (Edge e : edgeList) {
                if(e.from().getId() > e.to().getId()) continue;
                a[ind] = e.to().getId();
                ind++;
            }
            a[ind] = 0;
            ind++;
        }
        return a;
    }

    /**
     * Obtain a representation of the graph in the SA formalism.
     * @return a : adjMatrix
     */
    @Override
    public int[][] toAdjMatrix(){
        int maxNode=this.largestNodeId();
        List<Edge> edgeList=super.getAllEdges();
        int[][] a = new int[maxNode][maxNode];
        for(int i=0;i<maxNode;i++){
            for(int j=0;j<maxNode;j++){
                a[i][j]=0;
            }
        }
        boolean b=false;
        for(Edge e : edgeList){
            if(e.isSelfLoop()){
                if(!b){
                    a[e.from().getId()-1][e.to().getId()-1]+=1;
                    b=true;
                }else{
                    b=false;
                }


            }else{
                a[e.from().getId()-1][e.to().getId()-1]+=1;
                b=false;
            }
        }
        return a;
    }

    /**
     * Compute in a new graph the reverse of the graph.
     * Same graph is returned in the case of undirected graph.
     * @return g : reverse of the graph
     */
    @Override
    public UndirectedGraf getReverse(){
        return this;
    }

    /**
     * Compute in a new graph the transitive closure of the graph
     * @return g : transitive closure of the graph
     */
    public UndirectedGraf getTransitiveClosure(){
        UndirectedGraf res = new UndirectedGraf();
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
     * Export the undirected graph as a String in the DOT syntax
     * @return str : graph as a String in the DOT syntax
     */
    @Override
    public String toDotString(){
        String str="";
        //  str+="graph {\n\trankdir=LR;\n";
        str+="graph {\n";
        List<Node> list_node=this.getAllNodes();
        List<Edge> list_total_edge=this.getAllEdges();
        for(Node n : list_node){
            String nb=Integer.toString(n.getId());
            if(n.getName()!=null && !n.getName().isEmpty()){
                str+="\t"+nb+"[label=\""+n.getName()+"\"];\n";
            }
            List<Edge> list_edges=new ArrayList<>();
            for(Edge e : list_total_edge){
                if(e.from().getId()==n.getId()) list_edges.add(e);
            }
            if(list_edges==null || list_edges.isEmpty()){
                str+="\t"+nb+";\n";
                continue;
            }
            boolean new_l=true; //Know if we have to put a "," and know if it's a new line or not
            for(Edge e : list_edges){
                if(new_l) str+="\t"+nb+" -- ";
                if(e.getWeight()!=null){
                    String w = Integer.toString(e.getWeight()); //get weight
                    if(new_l){
                        str += e.to().getId() + "[len=" + w + ",label=" + w + "];\n";
                    }else{ //write a new line
                        str += ";\n" + nb + " -- " + e.to().getId() + "[len=" + w + ",label=" + w + "];\n";
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
    }
}