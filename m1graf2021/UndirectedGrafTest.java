package m1graf2021;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UndirectedGrafTest {

    UndirectedGraf ungraf=new UndirectedGraf();
    Node n1=new Node(1);
    Node n2=new Node(2,"A");
    Node n3=new Node(3);
    Node n4=new Node(4);
    Node n5=new Node(5);
    Node n7=new Node(7,"I");
    Node n22=new Node(2);
    Node n12=new Node(12);
    Edge e1_1=new Edge(1,1);
    Edge e1_2=new Edge(n1,n2);
    Edge e2_5=new Edge(2,5);

    List<Node> list_node;
    List<Edge> list_edge;


    @Test
    void nbNodes() {
        ungraf=new UndirectedGraf();
        Assert.assertEquals(0,ungraf.nbNodes());
        ungraf.addNode(n1);
        Assert.assertEquals(1,ungraf.nbNodes());
        ungraf.addNode(n2);
        Assert.assertEquals(2,ungraf.nbNodes());
        ungraf.addNode(n22);
        Assert.assertEquals(2,ungraf.nbNodes());
        ungraf.addNode(3);
        Assert.assertEquals(3,ungraf.nbNodes());
        ungraf.addNode(4);
        Assert.assertEquals(4,ungraf.nbNodes());
    }

    @BeforeEach
    public void initialiser() throws Exception {
        ungraf.addNode(n1);
        ungraf.addNode(n2);
        ungraf.addNode(n3);
        ungraf.addNode(n4);
        ungraf.addEdge(e1_1);
        ungraf.addEdge(e1_2);
        ungraf.addEdge(e2_5);
        // list_node=new ArrayList<>();
        // list_edge=new ArrayList<>();
    }

    @Test
    void existsNode() {
        //Node and int
        Assert.assertTrue(ungraf.existsNode(1));
        Assert.assertTrue(ungraf.existsNode(n1));
        Assert.assertTrue(ungraf.existsNode(2));
        Assert.assertTrue(ungraf.existsNode(n22));
        Assert.assertTrue(ungraf.existsNode(n5));
        Assert.assertTrue(ungraf.existsNode(n2));
        Assert.assertTrue(ungraf.existsNode(n3));
        Assert.assertFalse(ungraf.existsNode(9));
        Assert.assertFalse(ungraf.existsNode(n12));
    }

    @Test
    void getNode() {
        Assert.assertEquals(1,ungraf.getNode(1).getId());
        Assert.assertEquals(n3,ungraf.getNode(3));
        Assert.assertEquals(2,ungraf.getNode(2).getId());
    }

    @Test
    void addNode() {
        //Node and int
        Assert.assertEquals(5,ungraf.nbNodes());
        Assert.assertFalse(ungraf.existsNode(6));
        Assert.assertFalse(ungraf.existsNode(n12));
        ungraf.addNode(n12);
        ungraf.addNode(6);
        ungraf.addNode(n12);
        Assert.assertEquals(7,ungraf.nbNodes());
        Assert.assertTrue(ungraf.existsNode(6));
        Assert.assertTrue(ungraf.existsNode(n12));

    }

    @Test
    void removeNode() {
        //Node and int
        Assert.assertEquals(5,ungraf.nbNodes());
        Assert.assertTrue(ungraf.existsNode(n3));
        Assert.assertTrue(ungraf.existsNode(n22));

        ungraf.removeNode(n3);
        ungraf.removeNode(2);
        ungraf.removeNode(n3);
        Assert.assertEquals(3,ungraf.nbNodes());
        Assert.assertFalse(ungraf.existsNode(3));
        Assert.assertFalse(ungraf.existsNode(n2));
    }

    @Test
    void getSuccessors() {
        //Node and int
        list_node=ungraf.getSuccessors(1);
        Assert.assertTrue(list_node.contains(n1));
        Assert.assertTrue(list_node.contains(n2));
        Assert.assertTrue(list_node.contains(n22));
        Assert.assertFalse(list_node.contains(n12));
        Assert.assertFalse(list_node.contains(n3));

        list_node=ungraf.getSuccessors(n2);
        Assert.assertTrue(list_node.contains(n5));
        Assert.assertTrue(list_node.contains(n5));
        Assert.assertFalse(list_node.contains(n2));
        Assert.assertTrue(list_node.contains(n1));
    }

    @Test
    void adjacent() {
        //Node and int
        Assert.assertTrue(ungraf.adjacent(n1,n2));Assert.assertTrue(ungraf.adjacent(1,2));
        Assert.assertTrue(ungraf.adjacent(n22,n1));Assert.assertTrue(ungraf.adjacent(2,1));
        Assert.assertTrue(ungraf.adjacent(n1,n1));Assert.assertTrue(ungraf.adjacent(1,1));
        Assert.assertTrue(ungraf.adjacent(n2,n5));Assert.assertTrue(ungraf.adjacent(5,2));
        Assert.assertTrue(ungraf.adjacent(n5,n22));
        Assert.assertFalse(ungraf.adjacent(n1,n3)); Assert.assertFalse(ungraf.adjacent(1,5));
    }

    @Test
    void getAllNodes() {
        list_node=ungraf.getAllNodes();
        Assert.assertTrue(list_node.contains(n1));Assert.assertTrue(list_node.contains(n22));
        Assert.assertTrue(list_node.contains(n2));Assert.assertTrue(list_node.contains(n3));
        Assert.assertTrue(list_node.contains(n4));Assert.assertTrue(list_node.contains(n5));
        Assert.assertFalse(list_node.contains(n7));Assert.assertFalse(list_node.contains(n12));
    }

    @Test
    void largestNodeId() {
        Assert.assertEquals(5,ungraf.largestNodeId());
        ungraf.removeNode(n5);
        Assert.assertEquals(4,ungraf.largestNodeId());
        ungraf.addNode(8);
        ungraf.addNode(7);
        Assert.assertEquals(8,ungraf.largestNodeId());
        ungraf.addEdge(n7,n12);
        ungraf.addNode(10);
        ungraf.addEdge(10,9);
        Assert.assertEquals(12,ungraf.largestNodeId());
        Assert.assertEquals(9,ungraf.nbNodes());

    }

    @Test
    void nbEdges() {
        Assert.assertEquals(3,ungraf.nbEdges());
        ungraf.addEdge(n7,n12);
        ungraf.addNode(10);
        ungraf.addEdge(10,9);
        Assert.assertEquals(5,ungraf.nbEdges());
        ungraf.removeEdge(e2_5);
        Assert.assertEquals(4,ungraf.nbEdges());
    }

    @Test
    void existsEdge() {
        //Node, int and edge
        Assert.assertTrue(ungraf.existsEdge(e1_1));
        Assert.assertTrue(ungraf.existsEdge(1,1));
        Assert.assertTrue(ungraf.existsEdge(1,2));
        Assert.assertTrue(ungraf.existsEdge(n1,n2));
        Assert.assertTrue(ungraf.existsEdge(2,5));
        Assert.assertTrue(ungraf.existsEdge(2,1));
        Assert.assertTrue(ungraf.existsEdge(5,2));
        ungraf.addEdge(7,12);
        ungraf.addNode(10);
        ungraf.addEdge(10,9);
        ungraf.addEdge(n5,n2); //Déjà dedans
        Assert.assertEquals(6,ungraf.nbEdges());
        Assert.assertTrue(ungraf.existsEdge(7,12));
        Assert.assertTrue(ungraf.existsEdge(12,7));
        Assert.assertFalse(ungraf.existsEdge(7,10));
        Assert.assertTrue(ungraf.existsEdge(9,10));
        Assert.assertTrue(ungraf.existsEdge(10,9));
        ungraf.addEdge(1,1);
        ungraf.removeEdge(e1_1);
        Assert.assertEquals(6,ungraf.nbEdges());
        Assert.assertTrue(ungraf.existsEdge(e1_1));
        Assert.assertTrue(ungraf.existsEdge(1,1));
        ungraf.removeEdge(e1_1);
        Assert.assertFalse(ungraf.existsEdge(n1,n1));
    }

    @Test
    void addEdge() {
        //Node, int and edge
        Assert.assertEquals(3,ungraf.nbEdges());
        ungraf.addEdge(1,1);
        ungraf.addEdge(1,2);
        ungraf.addEdge(n2,n1);
        Assert.assertEquals(6,ungraf.nbEdges());
        ungraf.addEdge(2,4);
        ungraf.addEdge(n2,n12);
        Edge e = new Edge(n3,n4);
        ungraf.addEdge(e);
        Assert.assertEquals(9,ungraf.nbEdges());
        Assert.assertTrue(ungraf.existsEdge(n2,n4));
        Assert.assertTrue(ungraf.existsEdge(n2,n12));
        Assert.assertTrue(ungraf.existsEdge(2,4));
        Assert.assertTrue(ungraf.existsEdge(e));
        Assert.assertTrue(ungraf.existsEdge(n3,n4));
        Assert.assertTrue(ungraf.existsEdge(4,2));
        Assert.assertTrue(ungraf.existsEdge(n4,n3));
        Assert.assertFalse(ungraf.existsEdge(1,3));
        Assert.assertTrue(ungraf.existsEdge(4,3));
        ungraf.addEdge(e.getSymmetric());
        Assert.assertEquals(10,ungraf.nbEdges());
    }

    @Test
    void removeEdge() {
        //Node, int and edge
        Assert.assertEquals(3,ungraf.nbEdges());
        ungraf.addEdge(n7,n12);
        ungraf.addNode(10);
        ungraf.addEdge(10,9);
        Assert.assertEquals(5,ungraf.nbEdges());
        Assert.assertTrue(ungraf.existsEdge(7,12));
        Assert.assertTrue(ungraf.existsEdge(12,7));
        Assert.assertTrue(ungraf.existsEdge(1,1));
        ungraf.removeEdge(e1_1);
        Assert.assertFalse(ungraf.existsEdge(1,1));
        ungraf.removeEdge(e2_5);
        ungraf.removeEdge(2,5);
        ungraf.removeEdge(n7,n12);
        Assert.assertFalse(ungraf.existsEdge(e2_5));
        Assert.assertFalse(ungraf.existsEdge(new Node(5),n2));
        Assert.assertFalse(ungraf.existsEdge(7,12));
        Assert.assertFalse(ungraf.existsEdge(12,7));
        Assert.assertEquals(2,ungraf.nbEdges());
        ungraf.removeEdge(10,9);
        Assert.assertEquals(1,ungraf.nbEdges());
    }
    @Test
    void removeNode_and_Edge() {
        //Node, int and edge
        ungraf.addEdge(2,7);
        ungraf.addEdge(5,6);
        ungraf.addEdge(20,2);
        ungraf.addEdge(new Edge(n2,n12,5));
        Assert.assertEquals(7,ungraf.nbEdges());
        Assert.assertTrue(ungraf.existsEdge(2,5));
        Assert.assertTrue(ungraf.existsEdge(20,2));
        Assert.assertTrue(ungraf.existsEdge(2,12));
        Assert.assertTrue(ungraf.existsEdge(e1_1));

        ungraf.removeEdge(n22,n12);
        ungraf.removeEdge(2,12);
        Assert.assertEquals(6,ungraf.nbEdges());
        Assert.assertFalse(ungraf.existsEdge(n2,n12));
        Assert.assertTrue(ungraf.existsEdge(n1,n2));
        Assert.assertTrue(ungraf.existsEdge(2,5));
        ungraf.removeNode(2);
        Assert.assertEquals(2,ungraf.nbEdges());
        Assert.assertFalse(ungraf.existsEdge(20,2));
        Assert.assertFalse(ungraf.existsEdge(2,5));
        Assert.assertFalse(ungraf.existsEdge(1,2));

        ungraf.removeNode(1);
        Assert.assertEquals(1,ungraf.nbEdges());
        Assert.assertFalse(ungraf.existsEdge(1,1));


        Assert.assertTrue(ungraf.existsEdge(5,6));
        ungraf.removeNode(6);
        Assert.assertEquals(0,ungraf.nbEdges());
        Assert.assertFalse(ungraf.existsEdge(5,6));

    }

    @Test
    void getOutEdges() {
        //Node and int
        Edge e=new Edge(2,6);
        ungraf.addEdge(2,8);
        ungraf.addEdge(e);
        ungraf.addEdge(5,8);

        list_edge=ungraf.getOutEdges(n1);
        Assert.assertTrue(list_edge.contains(e1_1));
        Assert.assertTrue(list_edge.contains(e1_2));
        Assert.assertEquals(2,list_edge.size());

        list_edge=ungraf.getOutEdges(n22);
        Assert.assertTrue(list_edge.contains(e2_5));
        Assert.assertTrue(list_edge.contains(new Edge(2,8)));
        Assert.assertTrue(list_edge.contains(e));
        Assert.assertEquals(4,list_edge.size());

        list_edge=ungraf.getOutEdges(5);
        Assert.assertTrue(list_edge.contains(new Edge(5,8)));
        Assert.assertEquals(2,list_edge.size());
    }

    @Test
    void getInEdges() {
        //Node and int
        Edge e=new Edge(3,6);
        ungraf.addEdge(2,6);
        ungraf.addEdge(e);
        ungraf.addEdge(5,2);

        list_edge=ungraf.getInEdges(n1);
        Assert.assertTrue(list_edge.contains(e1_1));
        Assert.assertEquals(2,list_edge.size());

        list_edge=ungraf.getInEdges(n22);
        Assert.assertTrue(list_edge.contains(new Edge(5,2)));
        Assert.assertTrue(list_edge.contains(e1_2));
        Assert.assertEquals(4,list_edge.size());

        list_edge=ungraf.getInEdges(6);
        Assert.assertTrue(list_edge.contains(new Edge(2,6)));
        Assert.assertTrue(list_edge.contains(e));
        Assert.assertEquals(2,list_edge.size());
    }

    @Test
    void getIncidentEdges() {
        Edge ee=new Edge(2,6);
        ungraf.addEdge(2,8);
        ungraf.addEdge(ee);
        Edge e=new Edge(3,6);
        ungraf.addEdge(e);
        ungraf.addEdge(6,5);
        ungraf.addEdge(6,2);

        list_edge=ungraf.getIncidentEdges(n1);
        Assert.assertTrue(list_edge.contains(e1_1));
        Assert.assertTrue(list_edge.contains(e1_2));
        Assert.assertEquals(2,list_edge.size());

        list_edge=ungraf.getIncidentEdges(n22);
        Assert.assertFalse(list_edge.contains(new Edge(6,2)));
        Assert.assertTrue(list_edge.contains(new Edge(2,8)));
        Assert.assertFalse(list_edge.contains(e1_2));
        Assert.assertTrue(list_edge.contains(e2_5));
        Assert.assertTrue(list_edge.contains(ee));
        Assert.assertEquals(5,list_edge.size());

        list_edge=ungraf.getIncidentEdges(6);
        Assert.assertTrue(list_edge.contains(new Edge(6,5)));
        Assert.assertTrue(list_edge.contains(new Edge(6,2)));
        Assert.assertTrue(list_edge.contains(ee.getSymmetric()));
        Assert.assertEquals(4,list_edge.size());
        list_edge=ungraf.getIncidentEdges(4);
        Assert.assertEquals(0,list_edge.size());
        list_edge=ungraf.getIncidentEdges(new Node(5));
        Assert.assertEquals(2,list_edge.size());
        list_edge=ungraf.getIncidentEdges(n3);
        Assert.assertEquals(1,list_edge.size());
    }

    @Test
    void getAllEdges() {
        ungraf.addEdge(n7,n12);
        ungraf.addEdge(10,9);
        Edge e=new Edge(3,6);
        ungraf.addEdge(e);
        list_edge=ungraf.getAllEdges();
        Assert.assertEquals(6,list_edge.size());
        Assert.assertTrue(list_edge.contains(e));
        Assert.assertTrue(list_edge.contains(e1_1));
        Assert.assertTrue(list_edge.contains(e1_2));
        Assert.assertTrue(list_edge.contains(new Edge(2,5)));
        Assert.assertTrue(list_edge.contains(new Edge(7,12)));
        Assert.assertTrue(list_edge.contains(new Edge(n7,new Node(12,"yy"))));
    }

    @Test
    void constructorGraf() {
       // ungraf=new Graf();
        //Assert.assertEquals(0,ungraf.nbNodes());
        //ungraf= new Graf(1,2,0,2,0,3,7);
    }

    @Test
    void inDegree(){
        //1-1 1-2 2-5 2-1 5-2
        ungraf.addEdge(2,1);
        ungraf.addEdge(2,2);
        Assert.assertEquals(4,ungraf.inDegree(1));
        Assert.assertEquals(5,ungraf.inDegree(2));
    }
    @Test
    void outDegree(){
        //1-1 1-2 2-5 2-1 5-2
        ungraf.addEdge(2,1);
        ungraf.addEdge(2,2);
        Assert.assertEquals(4,ungraf.outDegree(1));
        Assert.assertEquals(5,ungraf.outDegree(2));
    }
    @Test
    void Degree(){
        //1-1 1-2 2-5 2-1 5-2
        ungraf.addEdge(2,1);
        ungraf.addEdge(2,2);
        ungraf.addEdge(3,5);
        ungraf.addEdge(5,1);
        Assert.assertEquals(5,ungraf.degree(1));
        Assert.assertEquals(5,ungraf.degree(2));
        Assert.assertEquals(3,ungraf.degree(5));
    }
    @Test
    void TotalDegree(){
        //1-1 1-2 2-5 2-1 5-2
        ungraf.addEdge(2,1);
        ungraf.addEdge(2,2);
        ungraf.addEdge(3,5);
        ungraf.addEdge(5,1);
        ungraf.addEdge(1,4);
        ungraf.addEdge(5,4);
        ungraf.addEdge(2,3);
        ungraf.addEdge(3,1);ungraf.addEdge(3,3);
        Assert.assertEquals(ungraf.inDegree(1),ungraf.degree(1));
        Assert.assertEquals(ungraf.outDegree(1),ungraf.degree(1));
        Assert.assertEquals(ungraf.inDegree(2),ungraf.degree(2));
        Assert.assertEquals(ungraf.outDegree(2),ungraf.degree(2));
        Assert.assertEquals(ungraf.inDegree(3),ungraf.degree(3));
        Assert.assertEquals(ungraf.outDegree(3),ungraf.degree(3));
        Assert.assertEquals(ungraf.inDegree(4),ungraf.degree(4));
        Assert.assertEquals(ungraf.outDegree(4),ungraf.degree(4));
        Assert.assertEquals(ungraf.inDegree(5),ungraf.degree(5));
        Assert.assertEquals(ungraf.outDegree(5),ungraf.degree(5));
    }

    @Test
    void getReverse(){
        Graf a=ungraf.getReverse();
        Assert.assertEquals(3,a.nbEdges());
    }

    @Test
    void testGrafUn(){
        UndirectedGraf gu = new UndirectedGraf(1,1,2,2,3,0, 2,3,0, 0);
        for (Node u: gu.getAllNodes()) {
            System.out.println("Node "+u+". Degree: "+gu.degree(u.getId())+" (In: "+gu.inDegree(u.getId())+" / Out: "+gu.outDegree(u.getId())+")");
            System.out.println("\tSuccessors: "+gu.getSuccessors(u));
            /** Expected :
             * Node 1. Degree: 7 (In: 7 / Out: 7)
             * 	Successors: [1, 2, 3]
             * Node 2. Degree: 5 (In: 5 / Out: 5)
             * 	Successors: [1, 2, 3]
             * Node 3. Degree: 2 (In: 2 / Out: 2)
             * 	Successors: [1, 2]
             */
        }
        for (Node u: gu.getAllNodes())
            System.out.println(""+u+": "+gu.getInEdges(u));

        System.out.println("\n>>>>>> Successor Array, Adjacency Matrix, and Graph Reverse");
        gu.toSuccessorArray();
        System.out.println("gu Successor array\n"+ Arrays.toString(gu.toSuccessorArray()));

        System.out.println("gu Adjacency Matrix");
        for (int[] row: gu.toAdjMatrix())
            System.out.println("\t"+Arrays.toString(row));
        String dotGU = gu.toDotString();
        System.out.println("Testing via toDotString() the equality with the reverse graph");
        String dotRGU = gu.getReverse().toDotString();
        System.out.println("DOT of the reverse of gu\n"+dotRGU);
        System.out.println("Graph gu and its reverse "+(dotGU.equals(dotRGU)?"are identical":"differ"));

        System.out.println("-----------------\n      NOW a disconnected GRAPH    \n----------------");
        System.out.println("Building guDisc, a disconnected undirected graph with multi-edges and self-loops");
        UndirectedGraf guDisc = new UndirectedGraf(1,1,2,2,6,0, 2,3,6,0, 0, 6,0, 6,0, 0, 0, 9,10,0, 0, 0);
        System.out.println(guDisc.toDotString());

        System.out.println(">>>> DFS of guDisc: "+guDisc.getDFS());
        System.out.println(">>>> BFS of guDisc: "+guDisc.getBFS());

        System.out.println(">>>>>>> Computing guDisc's transitive closure");
        UndirectedGraf guDiscTC = guDisc.getTransitiveClosure();
        System.out.println(guDiscTC.toDotString());
    }

    @Test
    void testConstrDot(){
        int [] expect={1,2,3,3,0,2,2,3,0,3,5,5,5,0,5,0,0};
        UndirectedGraf test=new UndirectedGraf(expect);
        test.toDotFile("crr");
        File f=new File("crr.gv");
        UndirectedGraf g=new UndirectedGraf(f);
        System.out.println(g.toDotString());
        int[] mat_2= g.toSuccessorArray();
        for(int i=0;i<expect.length;i++){
            Assert.assertEquals(expect[i],mat_2[i]);
        }
    }


}