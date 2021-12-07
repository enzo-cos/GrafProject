import m1graf2021.*;

import java.io.*;
import java.util.*;

/******************************
 *****     PW3 PROJECT   ******
 *****************************/
public class ChinesePostman {
    private final UndirectedGraf graf;
    private String optionDot;
    private String finalLabel;
    private final Map<Edge,ArrayList<String>> mapOptionsEdge;
    private List<Edge> doublons;
    private int costEdgeAdded =0;
    private String typeGraf;

    /**
     * Constructor of Chinese Postman Problem
     */
    public ChinesePostman(){
        graf=new UndirectedGraf();
        optionDot="";
        finalLabel="";
        mapOptionsEdge=new HashMap<>();
        typeGraf="";
        doublons=new ArrayList<>();
    }

    /**
     * Constructor of Chinese Postman Problem from a given graph
     * @param g     graph given
     */
    public ChinesePostman(UndirectedGraf g){
        graf=g;
        optionDot="";
        finalLabel="";
        typeGraf="";
        mapOptionsEdge=new HashMap<>();
        doublons=new ArrayList<>();
    }

    /**
     * Constructor from Dot File
     * @param fich File
     */
    public ChinesePostman(File fich){
        graf=new UndirectedGraf();
        optionDot="";
        finalLabel="";
        typeGraf="";
        mapOptionsEdge=new HashMap<>();
        doublons=new ArrayList<>();
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
                if(line.contains("rank") || line.contains("RANK")) {
                    optionDot=optionDot.concat(line.trim()+"\n");
                }
                line = line.trim();
                String[] tab_str = line.split("--");
                first = tab_str[0].trim();
                try{
                    int first_length=first.length();

                    if(first_length>0) {
                        if(first_length>5 && (first.substring(0,5).compareTo("label")==0)){
                            break;
                        }

                        if(first.contains("label")){//Ex : 5[label=A] : Label on Node only
                            String lab=tab_str[0].split("=")[1].trim();
                            lab=lab.substring(0,lab.indexOf("]"));
                            int ind=first.indexOf("[");
                            first=first.substring(0,ind).trim();
                            if(lab.contains("\"")) lab=lab.replaceAll("\"","");
                            Node n=new Node(Integer.parseInt(first),lab);
                            graf.addNode(n);
                        }else if (first.substring(first_length - 1).compareTo(";") == 0) { //Ex : 5; Node only
                            first = first.substring(0, first_length - 1);
                            graf.addNode(Integer.parseInt(first));
                        }else { //Edge
                            int nb = Integer.parseInt(first);
                            String str_2=tab_str[1];

                            if(str_2.contains("[")){ //Ex : 1-- 1[len=4,label="4"];
                                //+ de Précisions : rechercher dans tab_str[0] .split l'élément contenant le len et faire indice+1
                                int ind=str_2.indexOf("[");
                                int to=Integer.parseInt(str_2.substring(0,ind).trim());
                                if(str_2.contains("len")) {
                                    String len = str_2.split("=")[1].trim();
                                    int ind_virg = len.indexOf(",");
                                    if (ind_virg == -1) {
                                        len = len.substring(0, len.indexOf("]")).trim();
                                    } else {
                                        len = len.substring(0, ind_virg).trim();
                                    }
                                    Integer weight = Integer.parseInt(len);
                                    Edge e = new Edge(nb, to, weight);
                                    graf.addEdge(e);
                                    //Add Edge options
                                    addOptionsEdge(str_2, e);
                                }else{
                                    //option but not edge
                                    Edge e = new Edge(nb, to);
                                    graf.addEdge(e);
                                    //Add Edge options
                                    addOptionsEdge(str_2, e);
                                }
                            }else {
                                String[] sub = str_2.split(",");
                                int len = sub.length;
                                int i = 0;
                                while (i < len) {
                                    String str = sub[i].trim();
                                    String ff = str.substring(str.length() - 1);
                                    if (ff.compareTo(";") == 0)
                                        str = str.substring(0, str.length() - 1);
                                    int nbNode = Integer.parseInt(str);
                                    Edge e = new Edge(nb, nbNode);
                                    graf.addEdge(e);
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
     * Obtain Dot Format of the graph
     * @return Dot Format
     */
    public String toDotString(){
        String str="";
        str=str.concat("graph {\n");
        if(!optionDot.isEmpty()){
            str+=optionDot+";\n";
        }
        List<Node> list_node=graf.getAllNodes();
        List<Edge> list_total_edge=graf.getAllEdges();
        for(Node n : list_node){
            String nb=Integer.toString(n.getId());
            if(n.getName()!=null && !n.getName().isEmpty()){
                str=str.concat("\t"+nb+"[label=\""+n.getName()+"\"];\n");
            }
            List<Edge> list_edges=new ArrayList<>();
            for(Edge e : list_total_edge){
                if(e.from().getId()==n.getId()) list_edges.add(e);
            }
            if(list_edges.isEmpty()){
                str=str.concat("\t"+nb+";\n");
                continue;
            }
            boolean new_l=true; //Know if we have to put a "," and know if it's a new line or not
            for(Edge e : list_edges){
                String optEdge="";
                ArrayList<String> list=mapOptionsEdge.get(e);
                if(list!=null && !list.isEmpty()){
                    for(String s : list) {
                        optEdge =optEdge.concat("," + s);
                    }
                    if(doublons.contains(e)){
                        doublons.remove(e);
                    }else {
                        mapOptionsEdge.remove(e);
                    }
                }
                if(new_l) str=str.concat("\t"+nb+" -- ");
                if(e.getWeight()!=null){
                    String w = Integer.toString(e.getWeight()); //get weight
                    if(new_l){
                        str =str.concat( e.to().getId() + "[len=" + w + ",label=" + w + optEdge+"];\n");
                    }else{ //write a new line
                        str=str.concat( ";\n\t" + nb + " -- " + e.to().getId() + "[len=" + w + ",label=" + w + optEdge+"];\n");
                        new_l=true;
                    }
                }else{ //no weight
                    if(!optEdge.isEmpty()){
                        if(new_l){
                            str=str.concat( e.to().getId() + "["+ optEdge.substring(1)+"];\n");
                        }else{ //write a new line
                            str=str.concat( ";\n\t" + nb + " -- " + e.to().getId() + "["+ optEdge.substring(1)+"];\n");
                            new_l=true;
                        }
                    }else {
                        if (!new_l) str=str.concat( ", ");
                        str=str.concat(Integer.toString(e.to().getId()));
                        new_l = false;
                    }
                }
            }
            if(!new_l) str=str.concat(";\n");
        }
        if(!finalLabel.isEmpty()){
            str+="label=\""+finalLabel+"\"";
        }
        str+="}";
        return str;
    }

    /**
     * Getter of graf
     * @return graf
     */
    public Graf getGraf() {
        return graf;
    }

    /**
     * Attribute Edge's options to an Edge
     * @param str Options chaine
     * @param e current Edge
     */
    private void addOptionsEdge(String str, Edge e){

        if(str.contains("color")){
            if(mapOptionsEdge.containsKey(e)) doublons.add(e);
            mapOptionsEdge.put(e,new ArrayList<>());

            if(str.contains("]")){
                str=str.substring(str.indexOf("[")+1);
                if(str.contains("]"))  str=str.substring(0,str.indexOf("]"));
            }
            String[] tab=str.split(",");
            for(String s : tab){
                if(s.contains("color")){
                    mapOptionsEdge.get(e).add(s);
                }
            }
        }
    }

    /**
     * Know if a graf is eulerian
     * @return   0 if Eulerian
     *           1 if semi-Eulerian
     *          -1 if not Eulerian
     */
    public int isEulerian(){
        int nbOddDegree=0; //Number of odd degree node
        for(Node n : graf.getAllNodes()){ //For each Node, if degree(node) = odd => nbOddDegree+1
            if(graf.degree(n)%2!=0){
                nbOddDegree++;
            }
        }
        if(nbOddDegree==0) return 0;//Eulerian
        if(nbOddDegree==2) return 1;//Semi-Eulerian
        //if(nbOddDegree>2) return -1;//Not Eulerian
        return -1;
    }

    /**
     * Get the smallest of the node
     * @param n Node we are looking for his smallest successor
     * @param g copy of the graf
     * @return node
     */
    public Node getMinSuccessor(Node n, UndirectedGraf g){
        if(g==null) return null;
        List<Node> list=g.getSuccessors(n);
        if(list.size()<1) return null;
        Node node=list.get(0);
        for(Node n1 : list){
            if(n1.getId()< node.getId()){
                node=n1;
            }
        }
        return node;
    }

    /**
     * Get and compute the eulerian circuit of the graph.
     * if the graph is Eulerian -> call getEulerianCircuitNode
     * if the graph is Semi-Eulerian -> call getSemiEulerianCircuitNode
     * if the graph is not Eulerian -> compute the ChineseProblem
     * @param random if true -> compute ChinesePostman with random Pair
     *               else -> compute with the minimal weighted path
     * @return list : list of edge of the circuit
     */
    public List<Edge> getEulerianCircuit(boolean random){
        List<Edge> list_edge=new ArrayList<>();
        String strCircuit="";
        int isEulerian=isEulerian();
        if(isEulerian==0){
            //Eulerian Graph
            typeGraf="Eulerian";
            strCircuit="Eulerian circuit : ";
            list_edge= getEulerianCircuitEdge();
        }else if(isEulerian==1){
            //Semi Eulerian Graph
            typeGraf="Semi-Eulerian";
            strCircuit="Eulerian trail : ";
            Node nfirst=getMinNodeOdd();
            list_edge= getSemiEulerianCircuitEdge(nfirst);
        }else if(isEulerian==-1){
            //Not Eulerian -> Chinese Postman problem
            typeGraf="Non-Eulerian";
            strCircuit="Chinese circuit : ";
            Map<Pair<Node, Node>, Pair<Integer, Node>> map=Floyd_Warshall();
            System.out.println("Floyd_Warshall map : \n"+map);
            List<Pair<Node, Node>> listOfPair=getListPair(map,random);
            System.out.println("List of pairs used : \n"+listOfPair);
            duplicateEdge(map,listOfPair);
            list_edge= getEulerianCircuitEdge();
        }
        int totalLength=0;
        String str="\nType: "+typeGraf+"\n"+strCircuit+" [";
        for(Edge e: list_edge){
            totalLength+=e.getWeight();
            str=str.concat(e+" ");
        }
        str=str.concat("]\nTotal length : "+totalLength+"\n");
        if(isEulerian==-1) str=str.concat("ExtraCost : "+ costEdgeAdded +"\n");
        finalLabel=str;
        return list_edge;
    }

    /**
     * Obtain Eulerian circuit from a graf
     * @return list Eulerian Circuit
     */
    public List<Edge> getEulerianCircuitEdge(){
        UndirectedGraf g=new UndirectedGraf();
        //Create copy
        for(Edge e : graf.getAllEdges())
            g.addEdge(e);

        List<Edge> list=new LinkedList<>();
        Node n=g.getNode(1); //Starting Node
        int ind=-1;
        list= getEulerian_rec(n,list,g,ind);
        return list;
    }

    /**
     * Récursive Algorithme to get semiEulerianCircuit
     * @param n Current visited Node
     * @param list Eulerian Circuit
     * @param g Copy of the graf
     * @param ind Indice to add a node into the list
     * @return Eulerian Circuit
     */
    private List<Edge> getEulerian_rec(Node n,List<Edge> list,UndirectedGraf g,int ind){
        Node nmin=getMinSuccessor(n,g);
        boolean symetric=false;
        if(nmin==null){
            for(Edge e2 : list){
                Node ncurr=this.getMinSuccessor(e2.to(),g);
                if(ncurr!=null){
                    //get edge from the graph
                    Edge e_curr=new Edge(e2.to(),ncurr);
                    if(e_curr.from().getId()>e_curr.to().getId()){
                        e_curr=e_curr.getSymmetric();
                        symetric=true;
                    }
                    e_curr=g.getAllEdges().get(g.getAllEdges().indexOf(e_curr));
                    if(symetric) e_curr=e_curr.getSymmetric();

                    g.removeEdge(e_curr);
                    ind=list.indexOf(e2)+1;
                    list.add(ind,e_curr);
                    ind++;
                    list=getEulerian_rec(ncurr,list,g,ind);
                    break;
                }
            }
        }else{
            //Get edge from graph
            Edge e_curr=new Edge(n,nmin);
            if(e_curr.from().getId()>e_curr.to().getId()){
                e_curr=e_curr.getSymmetric();
                symetric=true;
            }
            e_curr=g.getAllEdges().get(g.getAllEdges().indexOf(e_curr));
            if(symetric) e_curr=e_curr.getSymmetric();

            g.removeEdge(e_curr);
            if(ind==-1){
                list.add(e_curr);
            }else{
                list.add(ind,e_curr);
            }
            if(ind!= -1) ind++;

            list=getEulerian_rec(e_curr.to(),list,g,ind);
        }


        return list;
    }

    /**
     * Obtain Eulerian circuit from a graf
     * @param nfirst Starting node
     * @return list Eulerian Circuit
     */
    public List<Edge> getSemiEulerianCircuitEdge(Node nfirst){
        UndirectedGraf g = new UndirectedGraf();
        //Creat copy
        for(Edge e : graf.getAllEdges())
            g.addEdge(e);
        return getEulerian_rec(nfirst,new LinkedList<>(),g,-1);
    }

    /**
     * Get the minimal Odd node of the graf
     * @return Minimum Odd Node
     */
    public Node getMinNodeOdd(){
        List<Node> list_impair=new ArrayList<>();
            List<Node> list=graf.getAllNodes();
            for(Node node : list){
                if(graf.degree(node)%2!=0){
                    list_impair.add(node);
                }
            }
            Collections.sort(list_impair);
        return list_impair.get(0);
    }

    /**
     * Get the edge with the minimum weight between two Nodes
     * @param n1    Started Node
     * @param n2    Target Node
     * @param list_edge List of edge of the graph
     * @return e : Edge with the minimal weight
     */
    public Edge getMinEdge(Node n1, Node n2,List<Edge> list_edge){
        List<Edge> list=new ArrayList<>(list_edge);
        Edge e=new Edge(n1,n2);
        if(e.from().getId()>e.to().getId()) e=e.getSymmetric();
        int k=list.indexOf(e);
        if(k!=-1){
            e=list.remove(k);
        }else{
            return null;
        }
        k=list.indexOf(e);
        while(k!=-1){
            Edge curr=list.remove(k);
            if(curr.getWeight()<e.getWeight()){
                e=curr;
            }
            k=list.indexOf(e);
        }

        return e;
    }

    /**
     * Compute the shortest path between two Nodes
     * @return map : Map linking Pair of Node with the distance
     */
    public Map<Pair<Node,Node>,Pair<Integer,Node>> Floyd_Warshall(){
        final int INF=(int)Double.POSITIVE_INFINITY - 10000000;
        Map<Pair<Node,Node>,Pair<Integer,Node>> map=new HashMap<>();
        List<Node> list_node=graf.getAllNodes();
        List<Edge> list_edge=graf.getAllEdges();
        Node[][] pred=new Node[list_node.size()+1][list_node.size()+1];
        int[][] mat=new int[list_node.size()+1][list_node.size()+1];
        for(Node n1 : list_node){
            for(Node n2 : list_node){
                if(n1.equals(n2)){
                    map.put(new Pair<>(n1,n2),new Pair<>(0,n1));
                    mat[n1.getId()][n2.getId()]=0;
                    pred[n1.getId()][n2.getId()]=n1;
                }else{
                    //Comparer les multi edges et prendre le plus petit
                    Edge e=getMinEdge(n1,n2,list_edge);
                    if(e!=null){
                        map.put(new Pair<>(n1,n2),new Pair<>(e.getWeight(),n1));
                        mat[n1.getId()][n2.getId()]=e.getWeight();
                        pred[n1.getId()][n2.getId()]=n1;
                    }else{
                        map.put(new Pair<>(n1,n2),new Pair<>(INF,null));
                        mat[n1.getId()][n2.getId()]=INF;
                    }
                }
            }
        }
        for(Node x : list_node) {
            for (Node y : list_node) {
                for (Node z : list_node) {
                    Pair<Node,Node> xy=new Pair<>(x,y);
                    Pair<Node,Node> xz=new Pair<>(x,z);Pair<Node,Node> zy=new Pair<>(z,y);
                    if(x.equals(z) || z.equals(y)) continue;
                    Pair<Integer,Node> res_xy=map.get(xy);Pair<Integer,Node> res_xz=map.get(xz);Pair <Integer,Node>res_zy=map.get(zy);
                    int dist2=res_zy.getFirst();
                    //Compare with the reverse of the Pair
                    if(map.get(zy.getReverse()).getFirst() < dist2) {
                        res_zy=map.get(zy.getReverse());
                        dist2= res_zy.getFirst();
                    }
                    int nb=res_xz.getFirst() + dist2;
                    if(res_xz.getFirst()!=INF && res_zy.getFirst()!=INF && (nb< res_xy.getFirst())){
                        map.replace(xy,new Pair<>(nb,z));
                        mat[x.getId()][y.getId()]=nb;
                        pred[x.getId()][y.getId()]=pred[z.getId()][y.getId()];
                    }
                }
            }
        }
        return map;
    }

    /**
     * get all Pairs combinaisons
     * @param list_impair   list of odd degree's Node
     * @param list_Pair     list of pairs to compute
     * @param k             index
     */
    public void getHeap(List<Node> list_impair, List<Pair<Node,Node>> list_Pair, int k){
        if(k==1){
            for(int i=0;i<list_impair.size()-1;i+=2) {
                Pair<Node,Node> p = new Pair<>(list_impair.get(i),list_impair.get(i+1));
                if((list_Pair.contains(p)) || list_Pair.contains(p.getReverse())) continue;
                list_Pair.add(p);
            }
            return;
        }
        getHeap(list_impair,list_Pair,k-1);
        for(int i=0;i<k-1;++i){
            Node curr;
            if(k%2==0){
                curr=list_impair.get(i);
                list_impair.set(i,list_impair.get(k-1));
                list_impair.set(k-1,curr);
            }else{
                curr=list_impair.get(0);
                list_impair.set(0,list_impair.get(k-1));
                list_impair.set(k-1,curr);
            }
            getHeap(list_impair,list_Pair,k-1);
        }

    }


    /**
     * Get the list of pair of odd Degree Node for compute the Chinese Postman Algorithme
     * @param map       Map linking Pair of Node with the distance
     * @param random    Boolean for getting Random Pair or with minimum distances
     * @return list of pair of edges that we will duplicate
     */
    public List<Pair<Node,Node>> getListPair(Map<Pair<Node,Node>,Pair<Integer,Node>> map, boolean random){
        List<Node> list_impair=new ArrayList<>();
        List<Pair<Node,Node>> list_final=new ArrayList<>();

        List<Pair<Node,Node>> list_Pair=new ArrayList<>();

        List<Node> list=graf.getAllNodes();
        for(Node node : list){
            if(graf.degree(node)%2!=0){
                list_impair.add(node);
            }
        }
        if(random) {
            for(Pair<Node,Node> p : map.keySet()){
                if(list_impair.contains(p.getFirst()) && list_impair.contains(p.getSecond()) && !(p.getFirst().equals(p.getSecond()))){
                    list_Pair.add(p);
                }
            }
            Collections.shuffle(list_Pair);
            //get final list random
            List<Node> node_used = new ArrayList<>(); //Look if the node has already been add in the list of pair
            for (Pair<Node, Node> p : list_Pair) {
                if (!(node_used.contains(p.getFirst())) && !(node_used.contains(p.getSecond()))) {
                    list_final.add(p);
                    node_used.add(p.getFirst());
                    node_used.add(p.getSecond());
                }
            }
        }else{
            int k=list_impair.size();
            int nb_pair=k/2;
            getHeap(list_impair,list_Pair,k);
            int dist=0;
            for (int i=0;i<nb_pair;i++){
                Pair<Node,Node> _p=list_Pair.get(i);
                dist+=map.get(_p).getFirst();
                list_final.add(_p);
            }
            int nb_combinaison=list_Pair.size();
            for(int i=nb_pair;i<nb_combinaison;i+=nb_pair){
                int dist2=0;
                List<Pair<Node,Node>> list_curr=new ArrayList<>();
                for(int n=i;n<i+nb_pair;n++){
                    Pair<Node,Node> _p=list_Pair.get(n);
                    list_curr.add(_p);
                    dist2+=map.get(_p).getFirst();
                }
                if(dist2<dist){
                    list_final=list_curr;
                    dist=dist2;
                }
            }
        }

        return list_final;
    }

    /**
     * Duplicate Edges through which we pass for the Chinese Postman Problem
     * @param map Map linking Pair of Node with the distance
     * @param list_pair List of pairs used
     */
    public void duplicateEdge(Map<Pair<Node,Node>,Pair<Integer,Node>> map, List<Pair<Node,Node>> list_pair){
        for(Pair<Node,Node> p : list_pair){
            duplicateEdgeRec(map,p);
        }
    }

    /**
     * Recursive fonction to duplicate edges
     * @param map   Map linking Pair of Node with the distance
     * @param pair  pair for which we must duplicate the paths
     */
    public void duplicateEdgeRec(Map<Pair<Node,Node>,Pair<Integer,Node>> map,Pair<Node,Node> pair){
        Node start= pair.getFirst();
        Node target= pair.getSecond();
        Node pass=map.get(pair).getSecond();
        if(pass.equals(start)){
            int w =getMinEdge(start,target,graf.getAllEdges()).getWeight();
            Edge e = new Edge(start,target,w);
            if(e.from().getId()>e.to().getId()) e=e.getSymmetric();
            graf.addEdge(e);
            costEdgeAdded+=e.getWeight();
            ArrayList<String> list=new ArrayList<>();
            list.add("color=red");list.add("fontcolor=red");
            if(mapOptionsEdge.put(e, list)!=null){
                doublons.add(e);
            }
            return;
        }
        Pair<Node,Node> p2=new Pair<>(pass,target);
        duplicateEdgeRec(map,p2);
        Pair<Node,Node> p=new Pair<>(start,pass);
        duplicateEdgeRec(map,p);
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

}
