package m1graf2021;

public class Node implements Comparable<Node>{
    private int id; //Node's number
    private String name; // Node's name (optional)

    /**
     * Create a Node from an int
     * @param id : Node's id
     */
    public Node(int id){
        this.id=id;
    }
    /**
     * Create a Node from an int and a String
     * @param id : Node's id
     * @param name : Node's name
     */
    public Node(int id, String name){
        this.id=id;
        if(name!=null && !name.isEmpty())
            this.name=name;
    }

    /**
     * Get the id of the Node
     * @return this.id : node's id
     */
    public int getId(){
        return this.id;
    }

    /**
     * Get the name of the Node
     * @return this.name : node's name
     */
    public String getName(){
        return this.name;
    }

    @Override
    public boolean equals(Object obj){
        if(obj==null) return false;
        if(obj instanceof Node && this==obj) return true;
        Node node= (Node) obj;
        if(node.getId()!=this.id ) return false;
        return true;
    }
    @Override
    public int hashCode() {
        int res = 23;
        return this.id * res + 10;
    }

    @Override
    public int compareTo(Node o) {
        return (this.id - o.id);
    }
    /**
     * Write a Node in console
     * @return String : (Node id)
     */
    public String toString() {
        if(this.name!=null && this.name!=""){
            return String.format(this.name+this.id);
        }
        return Integer.toString(this.id);

    }
}
