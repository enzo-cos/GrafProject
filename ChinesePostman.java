import m1graf2021.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.*;

/******************************
 *****     PW3 PROJECT   ******
 *****************************/
public class ChinesePostman {
    private UndirectedGraf graf;
    private String rankdir;
    private String optionDot;
    private String finalLabel;
    private Map<Edge,ArrayList<String>> mapOptionsEdge;

    public ChinesePostman(){
        graf=new UndirectedGraf();
        rankdir="";
        optionDot="";
        finalLabel="";
        mapOptionsEdge=new HashMap<>();
    }
    public ChinesePostman(UndirectedGraf g){
        graf=g;
        rankdir="";
        optionDot="";
        finalLabel="";
        mapOptionsEdge=new HashMap<>();
    }

    /**
     * Constructor from Dot File
     * @param fich File
     */
    public ChinesePostman(File fich){
        graf=new UndirectedGraf();
        rankdir="";
        optionDot="";
        finalLabel="";
        mapOptionsEdge=new HashMap<>();
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
            String strOptionEdge="";

            if((line = br.readLine()) != null){
                first=line.trim();
                first=first.substring(0,5);
                if(first.compareTo("graph")!=0){
                    throw new RuntimeException("File given is not an undirected graph");
                }
            }
            //read the nodes and egdes
            while ((line = br.readLine()) != null) {
                if(line.contains("rankdir")||line.contains("RANKDIR")){
                    rankdir=line.trim();
                }
                if(line.contains("rank")) {
                    optionDot+=line.trim()+"\n";
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
                            Integer nb = Integer.parseInt(first);
                            String str_2=tab_str[1];

                            if(str_2.contains("[")){ //Ex : 1-- 1[len=4,label="4"];
                                //+ de Précisions : rechercher dans tab_str[0] .split l'élément contenant le len et faire indice+1
                                int ind=str_2.indexOf("[");
                                Integer to=Integer.parseInt(str_2.substring(0,ind).trim());
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
                                    Integer nbNode = Integer.parseInt(str);
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
        str+="graph {\n";
        if(!optionDot.isEmpty()){
            str+=optionDot+";\n";
        }
        List<Node> list_node=graf.getAllNodes();
        List<Edge> list_total_edge=graf.getAllEdges();
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
                String optEdge="";
                ArrayList<String> list=mapOptionsEdge.get(e);
                if(list!=null && !list.isEmpty()){
                    for(String s : list) {
                        optEdge += "," + s;
                    }
                    mapOptionsEdge.remove(e);
                }
                if(new_l) str+="\t"+nb+" -- ";
                if(e.getWeight()!=null){
                    String w = Integer.toString(e.getWeight()); //get weight
                    if(new_l){
                        str += e.to().getId() + "[len=" + w + ",label=" + w + optEdge+"];\n";
                    }else{ //write a new line
                        str += ";\n\t" + nb + " -- " + e.to().getId() + "[len=" + w + ",label=" + w + optEdge+"];\n";
                        new_l=true;
                    }
                }else{ //no weight
                    if(!optEdge.isEmpty()){
                        if(new_l){
                            str += e.to().getId() + "["+ optEdge.substring(1)+"];\n";
                        }else{ //write a new line
                            str += ";\n\t" + nb + " -- " + e.to().getId() + "["+ optEdge.substring(1)+"];\n";
                            new_l=true;
                        }
                    }else {
                        if (!new_l) str += ", ";
                        str += Integer.toString(e.to().getId());
                        new_l = false;
                    }
                }
            }
            if(!new_l) str+=";\n";
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
     * @return 0 if Eulerian
     * @return 1 if semi-Eulerian
     * @return -1 if not Eulerian
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
        List<Node> list=g.getSuccessors(n);
        int i=0;
        Node node=null;
        if(list.size()<1) return null;
        node=list.get(0);
        for(Node n1 : list){
            if(n1.getId()< node.getId()){
                node=n1;
            }
        }
        return node;
    }

    /**
     * Obtain Eulerian circuit from a graf
     * @return list Eulerian Circuit
     */
    public List<Node> getEulerianCircuit(){
        UndirectedGraf g=graf;
        List<Node> list=new LinkedList<>();
        Node n=g.getNode(1);
        int ind=-1;
        return getEulerian_rec(n,list,g,ind);
    }

    /**
     * Récursive Algorithme to get semiEulerianCircuit
     * @param n Current visited Node
     * @param list Eulerian Circuit
     * @param g Copy of the graf
     * @param ind Indice to add a node into the list
     * @return Eulerian Circuit
     */
    private List<Node> getEulerian_rec(Node n,List<Node> list,UndirectedGraf g,int ind){
        if(ind==-1){
            list.add(n);
        }else{
            list.add(ind,n);
        }
        if(ind!= -1) ind++;
        Node nmin=getMinSuccessor(n,g);
        if(nmin==null){
            for(Node n2 : list){
                Node ncurr=this.getMinSuccessor(n2,g);
                if(ncurr!=null){
                    g.removeEdge(n2, ncurr);
                    n=ncurr;
                    ind=list.indexOf(n2)+1;
                    list=getEulerian_rec(n,list,g,ind);
                    break;
                }
            }
        }else{
            g.removeEdge(n,nmin);
            list=getEulerian_rec(nmin,list,g,ind);
        }

        return list;
    }

    /**
     * Obtain Eulerian circuit from a graf
     * @return list Eulerian Circuit
     */
    public List<Node> getSemiEulerianCircuit(Node nfirst){
        UndirectedGraf g = (UndirectedGraf) graf.getGraf();
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

    public Edge getMinEdge(Node n1, Node n2,List<Edge> list_edge){
        List<Edge> list=new ArrayList<>();
        list.addAll(list_edge);
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
     */
    //Map<Pair<Node,Node>,Pair<Integer,Node>>
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
                    map.put(new Pair<>(n1,n2),new Pair<>(0,null));
                    mat[n1.getId()][n2.getId()]=0;
                    pred[n1.getId()][n2.getId()]=n1;
                }else{
                    //Comparer les multi edges et prendre le plus petit
                    Edge e=getMinEdge(n1,n2,list_edge);
                    if(e!=null){
                        map.put(new Pair<>(n1,n2),new Pair<>(e.getWeight(),null));
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
                    Pair xy=new Pair(x,y);
                    Pair xz=new Pair(x,z);Pair zy=new Pair(z,y);
                    Pair res_xy=map.get(xy);Pair res_xz=map.get(xz);Pair res_zy=map.get(zy);
                    int nb=((int)res_xz.getFirst() + (int)res_zy.getFirst());
                    if((int)res_xz.getFirst()!=INF && (int)res_zy.getFirst()!=INF && (nb< (int)res_xy.getFirst())){
                        map.replace(xy,new Pair<>(nb,z));
                        mat[x.getId()][y.getId()]=nb;
                        pred[x.getId()][y.getId()]=pred[z.getId()][y.getId()];
                    }
                }
            }
        }
        return map;
    }

    public List<Pair<Node,Node>> getListPair(Map<Pair<Node,Node>,Pair<Integer,Node>> map){
        List<Node> list_impair=new ArrayList<>();
        List<Pair<Node,Node>> list_final=new ArrayList<>();

        List<Pair<Node,Node>> list_Pair=new ArrayList<>();

        List<Node> list=graf.getAllNodes();
        for(Node node : list){
            if(graf.degree(node)%2!=0){
                list_impair.add(node);
            }
        }
        Collections.sort(list_impair);
        for(Pair p : map.keySet()){
            if(list_impair.contains(p.getFirst()) && list_impair.contains(p.getSecond()) && !(p.getFirst().equals(p.getSecond()))){
                list_Pair.add(p);
            }
        }

        //get final list random
        List<Node> node_used=new ArrayList<>(); //Look if the node has already been add in the list of pair
        for(Pair<Node,Node> p : list_Pair){
            if(!(node_used.contains(p.getFirst())) && !(node_used.contains(p.getSecond()))){
                list_final.add(p);
                node_used.add(p.getFirst());
                node_used.add(p.getSecond());
            }
        }

        return list_final;
    }



    /**
     * Retourner le DotFile
     */
    public String getDotFile(){
        return graf.toDotString();
    }
}
