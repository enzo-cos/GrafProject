import m1graf2021.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/******************************
 *****     PW3 PROJECT   ******
 *****************************/
public class ChinesePostman {
    private UndirectedGraf graf;
    private String rankdir;

    public ChinesePostman(){
        graf=new UndirectedGraf();
        rankdir="";
    }
    public ChinesePostman(UndirectedGraf g){
        graf=g;
        rankdir="";
    }

    /**
     * Constructor from Dot File
     * @param fich File
     */
    public ChinesePostman(File fich){
        graf=new UndirectedGraf();
        rankdir="";
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
                if(line.contains("rankdir")||line.contains("RANKDIR")){
                    rankdir=line.trim();
                }
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
                            graf.addNode(n);
                        }else if (first.substring(first_length - 1).compareTo(";") == 0) { //Ex : 5;
                            first = first.substring(0, first_length - 1);
                            graf.addNode(Integer.parseInt(first));
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
                                graf.addEdge(e);
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
                                    graf.addEdge(nb, nbNode);
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
    public String toDotString(){
        String str="";
        //  str+="graph {\n\trankdir=LR;\n";
        str+="graph {\n";
        if(!rankdir.isEmpty()){
            str+=rankdir+";\n";
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

    /**
     * Getter of graf
     * @return graf
     */
    public Graf getGraf() {
        return graf;
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
     * Retourner le DotFile
     */
    public String getDotFile(){
        return graf.toDotString();
    }
}
