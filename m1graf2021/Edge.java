package m1graf2021;

public class Edge implements Comparable<Edge>{
    private Node from;
    private Node to;
    private Integer weight=null;

    /**
     * Create an Edge from two nodes
     * @param n_from : Source Node
     * @param n_to : Target Node
     */
    public Edge(Node n_from, Node n_to){
        this.from=n_from;
        this.to=n_to;
    }
    /**
     * Create an Edge from two nodes
     * @param idFrom : Number of Source Node
     * @param idTo : Number of Target Node
     */
    public Edge(int idFrom, int idTo){
        this.from=new Node(idFrom);
        this.to=new Node(idTo);
    }

    /**
     * Create an Edge from two nodes and a weight
     * @param n_from : Source Node
     * @param n_to : Target Node
     * @param weight : Node's weight
     */
    public Edge(Node n_from, Node n_to, Integer weight){
        this.from=n_from;
        this.to=n_to;
        this.weight=weight;
    }

    /**
     * Create an Edge from two nodes and a weight
     * @param idFrom : Number of Source Node
     * @param idTo : Number of Target Node
     * @param weight : Node's weight
     */
    public Edge(int idFrom, int idTo, Integer weight){
        this.from=new Node(idFrom);
        this.to=new Node(idTo);
        this.weight=weight;
    }

    /**
     * Get the source node
     * @return this.from : Source Node
     */
    public Node from(){
        return this.from;
    }

    /**
     * Get the target node
     * @return this.to : Target Node
     */
    public Node to(){
        return this.to;
    }

    /**
     * Get the edge's weigth
     * @return this.wieght : Edge's weight
     */
    public Integer getWeight(){
        return this.weight;
    }

    /**
     * Get the symmetric of an edge
     * @return
     */
    public Edge getSymmetric(){
        if(this.weight==null) return new Edge(this.to,this.from);
        return new Edge(this.to,this.from,this.weight);
    }

    /**
     * Know if an edge is a self-loop or no
     * @return boolean : true if it is, false if no
     */
    public boolean isSelfLoop(){
        return this.from.getId()==this.to.getId();
    }

    @Override
    public int compareTo(Edge o) {
        if(this.from.getId()!=o.from.getId()){
            return this.from.getId()-o.from.getId();
        }else{
            return this.to.getId()-o.to.getId();
        }
    }

    @Override
    public boolean equals(Object obj){
        if(obj==null) return false;
        if(obj instanceof Edge && this==obj) return true;
        Edge e= (Edge) obj;
        if(e.from.getId()!=this.from.getId() || e.to.getId()!=this.to.getId() ) return false;
        return true;
    }
    @Override
    public int hashCode() {
        int res = 23;
        return this.from().getId() * res + this.to().getId();
    }

    /**
     * Write an Edge in console
     * @return String : (from,to)
     */
    public String toString() {
        return String.format(this.from+"->"+this.to);
    }
}
