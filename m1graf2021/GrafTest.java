package m1graf2021;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

class GrafTest {

    UndirectedGraf ungraf=new UndirectedGraf();
    Graf mygraf=new Graf();
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

        mygraf=new Graf();
        Assert.assertEquals(0,mygraf.nbNodes());
        mygraf.addNode(n1);
        Assert.assertEquals(1,mygraf.nbNodes());
        mygraf.addNode(n2);
        Assert.assertEquals(2,mygraf.nbNodes());
        mygraf.addNode(n22);
        Assert.assertEquals(2,mygraf.nbNodes());
        mygraf.addNode(3);
        Assert.assertEquals(3,mygraf.nbNodes());
        mygraf.addNode(4);
        Assert.assertEquals(4,mygraf.nbNodes());
    }

    @BeforeEach
    public void initialiser() throws Exception {
        mygraf.addNode(n1);
        mygraf.addNode(n2);
        mygraf.addNode(n3);
        mygraf.addNode(n4);
        mygraf.addEdge(e1_1);
        mygraf.addEdge(e1_2);
        mygraf.addEdge(e2_5);
    }

    @Test
    void existsNode() {
        //Node and int
        Assert.assertTrue(mygraf.existsNode(1));
        Assert.assertTrue(mygraf.existsNode(n1));
        Assert.assertTrue(mygraf.existsNode(2));
        Assert.assertTrue(mygraf.existsNode(n22));
        Assert.assertTrue(mygraf.existsNode(n5));
        Assert.assertTrue(mygraf.existsNode(n2));
        Assert.assertTrue(mygraf.existsNode(n3));
        Assert.assertFalse(mygraf.existsNode(9));
        Assert.assertFalse(mygraf.existsNode(n12));
    }

    @Test
    void getNode() {
        Assert.assertEquals(1,mygraf.getNode(1).getId());
        Assert.assertEquals(n3,mygraf.getNode(3));
        Assert.assertEquals(2,mygraf.getNode(2).getId());
    }

    @Test
    void addNode() {
        //Node and int
        Assert.assertEquals(5,mygraf.nbNodes());
        Assert.assertFalse(mygraf.existsNode(6));
        Assert.assertFalse(mygraf.existsNode(n12));
        mygraf.addNode(n12);
        mygraf.addNode(6);
        mygraf.addNode(n12);
        Assert.assertEquals(7,mygraf.nbNodes());
        Assert.assertTrue(mygraf.existsNode(6));
        Assert.assertTrue(mygraf.existsNode(n12));

    }

    @Test
    void removeNode() {
        //Node and int
        Assert.assertEquals(5,mygraf.nbNodes());
        Assert.assertTrue(mygraf.existsNode(n3));
        Assert.assertTrue(mygraf.existsNode(n22));

        mygraf.removeNode(n3);
        mygraf.removeNode(2);
        mygraf.removeNode(n3);
        Assert.assertEquals(3,mygraf.nbNodes());
        Assert.assertFalse(mygraf.existsNode(3));
        Assert.assertFalse(mygraf.existsNode(n2));
    }

    @Test
    void getSuccessors() {
        //Node and int
        list_node=mygraf.getSuccessors(1);
        Assert.assertTrue(list_node.contains(n1));
        Assert.assertTrue(list_node.contains(n2));
        Assert.assertTrue(list_node.contains(n22));
        Assert.assertFalse(list_node.contains(n12));
        Assert.assertFalse(list_node.contains(n3));

        list_node=mygraf.getSuccessors(n2);
        Assert.assertTrue(list_node.contains(n5));
        Assert.assertTrue(list_node.contains(n5));
        Assert.assertFalse(list_node.contains(n2));
        Assert.assertFalse(list_node.contains(n1));

    }

    @Test
    void adjacent() {
        //Node and int
        Assert.assertTrue(mygraf.adjacent(n1,n2));Assert.assertTrue(mygraf.adjacent(1,2));
        Assert.assertTrue(mygraf.adjacent(n22,n1));Assert.assertTrue(mygraf.adjacent(2,1));
        Assert.assertTrue(mygraf.adjacent(n1,n1));Assert.assertTrue(mygraf.adjacent(1,1));
        Assert.assertTrue(mygraf.adjacent(n2,n5));Assert.assertTrue(mygraf.adjacent(5,2));
        Assert.assertTrue(mygraf.adjacent(n5,n22));
        Assert.assertFalse(mygraf.adjacent(n1,n3)); Assert.assertFalse(mygraf.adjacent(1,5));
    }

    @Test
    void getAllNodes() {
        list_node=mygraf.getAllNodes();
        Assert.assertTrue(list_node.contains(n1));Assert.assertTrue(list_node.contains(n22));
        Assert.assertTrue(list_node.contains(n2));Assert.assertTrue(list_node.contains(n3));
        Assert.assertTrue(list_node.contains(n4));Assert.assertTrue(list_node.contains(n5));
        Assert.assertFalse(list_node.contains(n7));Assert.assertFalse(list_node.contains(n12));
    }

    @Test
    void largestNodeId() {
        Assert.assertEquals(5,mygraf.largestNodeId());
        mygraf.removeNode(n5);
        Assert.assertEquals(4,mygraf.largestNodeId());
        mygraf.addNode(8);
        mygraf.addNode(7);
        Assert.assertEquals(8,mygraf.largestNodeId());
        mygraf.addEdge(n7,n12);
        mygraf.addNode(10);
        mygraf.addEdge(10,9);
        Assert.assertEquals(12,mygraf.largestNodeId());
        Assert.assertEquals(9,mygraf.nbNodes());

    }

    @Test
    void nbEdges() {
        Assert.assertEquals(3,mygraf.nbEdges());
        mygraf.addEdge(n7,n12);
        mygraf.addNode(10);
        mygraf.addEdge(10,9);
        Assert.assertEquals(5,mygraf.nbEdges());
        mygraf.removeEdge(e2_5);
        Assert.assertEquals(4,mygraf.nbEdges());
    }

    @Test
    void existsEdge() {
        //Node, int and edge
        Assert.assertTrue(mygraf.existsEdge(e1_1));
        Assert.assertTrue(mygraf.existsEdge(1,1));
        Assert.assertTrue(mygraf.existsEdge(1,2));
        Assert.assertTrue(mygraf.existsEdge(n1,n2));
        Assert.assertTrue(mygraf.existsEdge(2,5));
        Assert.assertFalse(mygraf.existsEdge(2,1));
        Assert.assertFalse(mygraf.existsEdge(5,2));
        mygraf.addEdge(7,12);
        mygraf.addNode(10);
        mygraf.addEdge(10,9);
        mygraf.addEdge(n5,n2);
        mygraf.removeEdge(e1_1);
        Assert.assertTrue(mygraf.existsEdge(7,12));
        Assert.assertFalse(mygraf.existsEdge(12,7));
        Assert.assertFalse(mygraf.existsEdge(7,10));
        Assert.assertFalse(mygraf.existsEdge(e1_1));
        Assert.assertFalse(mygraf.existsEdge(1,1));
        Assert.assertFalse(mygraf.existsEdge(n1,n1));
        Assert.assertTrue(mygraf.existsEdge(e2_5));
        Assert.assertTrue(mygraf.existsEdge(e2_5.getSymmetric()));
        Assert.assertTrue(mygraf.existsEdge(5,2));
    }

    @Test
    void addEdge() {
        //Node, int and edge
        Assert.assertEquals(3,mygraf.nbEdges());
        mygraf.addEdge(1,1);
        mygraf.addEdge(1,2);
        Assert.assertEquals(5,mygraf.nbEdges());
        mygraf.addEdge(2,4);
        mygraf.addEdge(n2,n12);
        Edge e = new Edge(n3,n4);
        mygraf.addEdge(e);
        Assert.assertEquals(8,mygraf.nbEdges());
        Assert.assertTrue(mygraf.existsEdge(n2,n4));
        Assert.assertTrue(mygraf.existsEdge(n2,n12));
        Assert.assertTrue(mygraf.existsEdge(2,4));
        Assert.assertTrue(mygraf.existsEdge(e));
        Assert.assertTrue(mygraf.existsEdge(n3,n4));
        Assert.assertFalse(mygraf.existsEdge(4,2));
        Assert.assertFalse(mygraf.existsEdge(n4,n3));
        Assert.assertFalse(mygraf.existsEdge(1,3));
        Assert.assertFalse(mygraf.existsEdge(4,3));
        mygraf.addEdge(e.getSymmetric());
     //   mygraf.toDotFile("hello.gv");
        Assert.assertTrue(mygraf.existsEdge(n4,n3));
    }

    @Test
    void removeEdge() {
        //Node, int and edge
        Assert.assertEquals(3,mygraf.nbEdges());
        mygraf.addEdge(n7,n12);
        mygraf.addNode(10);
        mygraf.addEdge(10,9);
        Assert.assertEquals(5,mygraf.nbEdges());
        Assert.assertTrue(mygraf.existsEdge(7,12));
        Assert.assertTrue(mygraf.existsEdge(1,1));
        mygraf.removeEdge(e1_1);
        Assert.assertFalse(mygraf.existsEdge(1,1));
        mygraf.removeEdge(e2_5);
        mygraf.removeEdge(2,5);
        mygraf.removeEdge(n7,n12);
        Assert.assertFalse(mygraf.existsEdge(e2_5));
        Assert.assertFalse(mygraf.existsEdge(7,12));
        Assert.assertEquals(2,mygraf.nbEdges());
        mygraf.removeEdge(10,9);
        Assert.assertEquals(1,mygraf.nbEdges());
    }
    @Test
    void removeNode_and_Edge() {
        //Node, int and edge
        mygraf.addEdge(2,7);
        mygraf.addEdge(5,6);
        mygraf.addEdge(20,2);
        mygraf.addEdge(new Edge(n2,n12,5));
        Assert.assertEquals(7,mygraf.nbEdges());
        Assert.assertTrue(mygraf.existsEdge(2,5));
        Assert.assertTrue(mygraf.existsEdge(20,2));
        Assert.assertTrue(mygraf.existsEdge(2,12));
        Assert.assertTrue(mygraf.existsEdge(e1_1));

        mygraf.removeEdge(n22,n12);
        mygraf.removeEdge(2,12);
        Assert.assertEquals(6,mygraf.nbEdges());
        Assert.assertFalse(mygraf.existsEdge(n2,n12));
        Assert.assertTrue(mygraf.existsEdge(n1,n2));
        Assert.assertTrue(mygraf.existsEdge(2,5));
        mygraf.removeNode(2);
        Assert.assertEquals(2,mygraf.nbEdges());
        Assert.assertFalse(mygraf.existsEdge(20,2));
        Assert.assertFalse(mygraf.existsEdge(2,5));
        Assert.assertFalse(mygraf.existsEdge(1,2));

        mygraf.removeNode(1);
        Assert.assertEquals(1,mygraf.nbEdges());
        Assert.assertFalse(mygraf.existsEdge(1,1));


        Assert.assertTrue(mygraf.existsEdge(5,6));
        mygraf.removeNode(6);
        Assert.assertEquals(0,mygraf.nbEdges());
        Assert.assertFalse(mygraf.existsEdge(5,6));

    }

    @Test
    void getOutEdges() {
        //Node and int
        Edge e=new Edge(2,6);
        mygraf.addEdge(2,8);
        mygraf.addEdge(e);
        mygraf.addEdge(5,8);

        list_edge=mygraf.getOutEdges(n1);
        Assert.assertTrue(list_edge.contains(e1_1));
        Assert.assertTrue(list_edge.contains(e1_2));
        Assert.assertEquals(2,list_edge.size());

        list_edge=mygraf.getOutEdges(n22);
        Assert.assertTrue(list_edge.contains(e2_5));
        Assert.assertTrue(list_edge.contains(new Edge(2,8)));
        Assert.assertTrue(list_edge.contains(e));
        Assert.assertEquals(3,list_edge.size());

        list_edge=mygraf.getOutEdges(5);
        Assert.assertTrue(list_edge.contains(new Edge(5,8)));
        Assert.assertEquals(1,list_edge.size());
    }

    @Test
    void getInEdges() {
        //Node and int
        Edge e=new Edge(3,6);
        mygraf.addEdge(2,6);
        mygraf.addEdge(e);
        mygraf.addEdge(5,2);

        list_edge=mygraf.getInEdges(n1);
        Assert.assertTrue(list_edge.contains(e1_1));
        Assert.assertEquals(1,list_edge.size());

        list_edge=mygraf.getInEdges(n22);
        Assert.assertTrue(list_edge.contains(new Edge(5,2)));
        Assert.assertTrue(list_edge.contains(e1_2));
        Assert.assertEquals(2,list_edge.size());

        list_edge=mygraf.getInEdges(6);
        Assert.assertTrue(list_edge.contains(new Edge(2,6)));
        Assert.assertTrue(list_edge.contains(e));
        Assert.assertEquals(2,list_edge.size());
    }

    @Test
    void getIncidentEdges() {
        Edge ee=new Edge(2,6);
        mygraf.addEdge(2,8);
        mygraf.addEdge(ee);
        Edge e=new Edge(3,6);
        mygraf.addEdge(e);
        mygraf.addEdge(6,5);
        mygraf.addEdge(6,2);

        list_edge=mygraf.getIncidentEdges(n1);
        Assert.assertTrue(list_edge.contains(e1_1));
        Assert.assertTrue(list_edge.contains(e1_2));
        Assert.assertEquals(2,list_edge.size());

        list_edge=mygraf.getIncidentEdges(n22);
        Assert.assertTrue(list_edge.contains(new Edge(6,2)));
        Assert.assertTrue(list_edge.contains(new Edge(2,8)));
        Assert.assertTrue(list_edge.contains(e1_2));
        Assert.assertTrue(list_edge.contains(e2_5));
        Assert.assertTrue(list_edge.contains(ee));
        Assert.assertEquals(5,list_edge.size());

        list_edge=mygraf.getIncidentEdges(6);
        Assert.assertTrue(list_edge.contains(new Edge(6,5)));
        Assert.assertTrue(list_edge.contains(new Edge(6,2)));
        Assert.assertTrue(list_edge.contains(e));
        Assert.assertTrue(list_edge.contains(ee));
        Assert.assertEquals(4,list_edge.size());
        list_edge=mygraf.getIncidentEdges(4);
        Assert.assertEquals(0,list_edge.size());
        list_edge=mygraf.getIncidentEdges(new Node(5));
        Assert.assertEquals(2,list_edge.size());
        list_edge=mygraf.getIncidentEdges(n3);
        Assert.assertEquals(1,list_edge.size());

    }

    @Test
    void getAllEdges() {
        mygraf.addEdge(n7,n12);
        mygraf.addEdge(10,9);
        Edge e=new Edge(3,6);
        mygraf.addEdge(e);
        list_edge=mygraf.getAllEdges();
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
        mygraf=new Graf();
        Assert.assertEquals(0,mygraf.nbNodes());
        mygraf= new Graf(1,2,0,2,0,3,1,0);
        Assert.assertEquals(3,mygraf.nbNodes());
        Assert.assertEquals(5,mygraf.nbEdges());
        Assert.assertTrue(mygraf.existsEdge(1,1));
        Assert.assertTrue(mygraf.existsEdge(1,2));
        Assert.assertTrue(mygraf.existsEdge(2,2));
        Assert.assertTrue(mygraf.existsEdge(3,3));
        Assert.assertTrue(mygraf.existsEdge(3,1));
    }
    @Test
    void constructorGraf_2() {
        mygraf=new Graf();
        Assert.assertEquals(0,mygraf.nbNodes());
        mygraf= new Graf(1,2,0,2,0,3,7,0,0,0,0,0);
        Assert.assertEquals(7,mygraf.nbNodes());
        Assert.assertTrue(mygraf.existsNode(1));Assert.assertTrue(mygraf.existsNode(2));
        Assert.assertTrue(mygraf.existsNode(3));Assert.assertTrue(mygraf.existsNode(7));
        Assert.assertEquals(5,mygraf.nbEdges());
        Assert.assertTrue(mygraf.existsEdge(1,1));
        Assert.assertTrue(mygraf.existsEdge(1,2));
        Assert.assertTrue(mygraf.existsEdge(2,2));
        Assert.assertTrue(mygraf.existsEdge(3,3));
        Assert.assertTrue(mygraf.existsEdge(3,7));
    }

    @Test
    void inDegree(){
        //1-1 1-2 2-5
        mygraf.addEdge(2,1);
        mygraf.addEdge(2,2);
        Assert.assertEquals(2,mygraf.inDegree(1));
        Assert.assertEquals(2,mygraf.inDegree(2));
    }
    @Test
    void outDegree(){
        //1-1 1-2 2-5
        mygraf.addEdge(2,1);
        mygraf.addEdge(2,2);
        Assert.assertEquals(2,mygraf.outDegree(1));
        Assert.assertEquals(3,mygraf.outDegree(2));
    }
    @Test
    void Degree(){
        //1-1 1-2 2-5
        mygraf.addEdge(2,1);
        mygraf.addEdge(2,2);
        mygraf.addEdge(3,5);
        mygraf.addEdge(5,1);
        Assert.assertEquals(5,mygraf.degree(1));
        Assert.assertEquals(5,mygraf.degree(2));
        Assert.assertEquals(3,mygraf.degree(5));
    }

    @Test
    void successorArray(){
        mygraf.addEdge(2,3);mygraf.addEdge(3,4);
        //mygraf.toDotFile("test.gv");
        int[] a=mygraf.toSuccessorArray();
        //int[] b={1,2,0,3,5,0,4,0};
        int [] expect={1,2,0,3,5,0,4,0,0,0};
        System.out.println(Arrays.toString(a));
        for(int i=0;i<expect.length;i++){
            Assert.assertEquals(expect[i],a[i]);
        }
        Assert.assertEquals(expect.length,a.length);
    }
    @Test
    void successorArray_2(){
        Graf t=new Graf(2,0,0,5,12,0,0,0,0,0,0,0,0,0,0);
        System.out.println(t);
        t=new Graf(2,3,0,4,2,0,1,4,0,3,0);
    }

    @Test
    void toAdjMatrix(){
        mygraf.addEdge(2,3);mygraf.addEdge(3,4);
        //mygraf.toDotFile("testt");
        int[][] a=mygraf.toAdjMatrix();
        //int[] b={1,2,0,3,5,0,4,0};
        int [][] expect=new int[5][5];
        expect[0]= new int[]{1, 1, 0, 0,0};
        expect[1]= new int[]{0, 0, 1, 0,1};
        expect[2]= new int[]{0, 0, 0, 1,0};
        expect[3]= new int[]{0, 0, 0, 0,0};
        expect[4]= new int[]{0, 0, 0, 0,0};
        for(int i=0;i<expect.length;i++){
            for(int j=0;j<expect.length;j++){
                Assert.assertEquals(expect[i][j],a[i][j]);
            }
        }
        Assert.assertEquals(expect.length,a.length);
        //multigraf
        mygraf.addEdge(4,5);
        mygraf.addEdge(new Edge(n4,n5,2));
        mygraf.addEdge(5,5);mygraf.addEdge(5,2);mygraf.addEdge(4,4);mygraf.addEdge(4,4);mygraf.addEdge(new Edge(4,4,-2));
        a=mygraf.toAdjMatrix();
        expect[3]= new int[]{0, 0, 0, 3,2};
        expect[4]= new int[]{0, 1, 0, 0,1};
        for(int i=0;i<expect.length;i++){
            for(int j=0;j<expect.length;j++){
                Assert.assertEquals(expect[i][j],a[i][j]);
            }
        }
        Assert.assertEquals(expect.length,a.length);
        System.out.println(mygraf.toDotString());

    }

    @Test
    void toAdjMatrix_2(){
        Graf g=new Graf(1,0,3,0,8,0,0,0,6,0,6,0,0);
        int[][] a=g.toAdjMatrix();
        //int[] b={1,2,0,3,5,0,4,0};
        int [][] expect=new int[8][8];
        expect[0]= new int[]{1, 0, 0, 0,0,0,0,0};
        expect[1]= new int[]{0, 0, 1, 0,0,0,0,0};
        expect[2]= new int[]{0, 0, 0, 0,0,0,0,1};
        expect[3]= new int[]{0, 0, 0, 0,0,0,0,0};
        expect[4]= new int[]{0, 0, 0, 0,0,0,0,0};
        expect[5]= new int[]{0, 0, 0, 0,0,1,0,0};
        expect[6]= new int[]{0, 0, 0, 0,0,1,0,0};
        expect[7]= new int[]{0, 0, 0, 0,0,0,0,0};
        for(int i=0;i<expect.length;i++){
            for(int j=0;j<expect.length;j++){
                Assert.assertEquals(expect[i][j],a[i][j]);
            }
        }
        Assert.assertEquals(expect.length,a.length);
    }

    @Test
    void getReverse(){
        Graf g=new Graf();
        g.addNode(n1);
        g.addNode(n2);
        g.addNode(n3);
        g.addNode(n4);
        g.addEdge(e1_1);
        g.addEdge(2,1);
        g.addEdge(5,2);
        mygraf.addEdge(3,2);mygraf.addEdge(1,3);mygraf.addEdge(3,1);
        g.addEdge(2,3);g.addEdge(1,3);g.addEdge(3,1);
        Graf test=mygraf.getReverse();
        for(Edge e : test.getAllEdges()){
            Assert.assertTrue(g.existsEdge(e));
        }
    }

    @Test
    void testNotNull(){
        List<Node> list=mygraf.getSuccessors(7);
        Assert.assertTrue(list.isEmpty());
        List<Edge> list_2=mygraf.getOutEdges(7);
        Assert.assertTrue(list_2.isEmpty());
        list_2=mygraf.getInEdges(7);
        Assert.assertTrue(list_2.isEmpty());
        list_2=mygraf.getIncidentEdges(7);
        Assert.assertTrue(list_2.isEmpty());

        int a=mygraf.inDegree(7);
        Assert.assertEquals(0,a);
        a=mygraf.outDegree(7);
        Assert.assertEquals(0,a);
        a=mygraf.degree(7);
        Assert.assertEquals(0,a);
    }

    @Test
    void getTransitive(){
        Graf g = new Graf();
        g.addNode(1);g.addNode(2);g.addNode(3);g.addNode(4);g.addNode(5);g.addNode(6);
        g.addEdge(1,2);g.addEdge(1,3);g.addEdge(2,4);g.addEdge(3,4);g.addEdge(4,5);g.addEdge(4,6);
        Graf test=new Graf(2,3,0,4,0,4,0,5,6,0,0,0);

        test=test.getTransitiveClosure();
        System.out.println(test.toDotString());
        int[] mat_2= test.toSuccessorArray();
        int [] expect={2,3,4,5,6,0,4,5,6,0,4,5,6,0,5,6,0,0,0};
         for(int i=0;i<expect.length;i++){
                 Assert.assertEquals(expect[i],mat_2[i]);
         }

        Graf test2=new Graf(2,3,3,0,2,4,4,0,3,4,0,5,6,0,0,0);
        System.out.println(test2.toDotString());
        test2=test2.getTransitiveClosure();
        System.out.println(test2.toDotString());
    }

    @Test
    void testMultigrafconstr(){
        Graf test=new Graf(1,2,3,3,0,2,2,3,0,3,5,5,5,0,5,0,0);
        int[] mat_2= test.toSuccessorArray();
        int [] expect={1,2,3,3,0,2,2,3,0,3,5,5,5,0,5,0,0};
        for(int i=0;i<expect.length;i++){
            Assert.assertEquals(expect[i],mat_2[i]);
        }
    }

    @Test
    void getBFS(){
        Graf g=new Graf(2,3,0,4,0,5,6,0,0,7,0,8,0,0,0);
        List<Node> list=g.getBFS();
        System.out.print("\n");
        for(int i=0;i<list.size();i++){
            System.out.print(list.get(i)+", ");
        }
        //expected : 1,2,3,4,5,6,7,8 ou 1,3,2,5,6,4,7,8 ou 1,3,2,6,5,4,8,7
    }

    @Test
    void getDFS(){
        Graf g=new Graf(2,3,6,0,5,0,4,0,1,8,0,6,7,8,0,2,7,0,0,7,0);
        List<Node> list=g.getDFS();
        System.out.print("\n");
        for(int i=0;i<list.size();i++){
            System.out.print(list.get(i)+", ");
        }
        //expected : 1,2,5,6,7,8,3,4
        //multi
        g=new Graf(2,3,6,0,5,5,0,4,0,1,8,1,1,0,6,7,8,0,2,7,0,0,7,0);
        list=g.getDFS();
        System.out.print("\n");
        for(int i=0;i<list.size();i++){
            System.out.print(list.get(i)+", ");
        }
    }

    @Test
    void testDot() {
        Graf t = new Graf(2, 3, 0, 4, 2, 0, 1, 4, 0, 3, 0);
        System.out.println(t.toDotString());
        Node na = new Node(6, "I");
        Node nb = new Node(7, "B");
        mygraf.addNode(na);mygraf.addNode(nb);
        Edge e10= new Edge(na,nb,10);
        Edge e20= new Edge(2,4,20);
        mygraf.addEdge(6,7);mygraf.addEdge(2,1);mygraf.addEdge(2,2);mygraf.addEdge(new Edge(2,3,6));
        mygraf.addEdge(e10);mygraf.addEdge(e20);mygraf.addEdge(2,7);
        mygraf.addEdge(3,4);
        mygraf.addEdge(new Edge(3,6,1));
        mygraf.addEdge(new Edge(7,2,3));
        System.out.println(mygraf.toDotString());
    }
    @Test
    void testConstrDot(){
        int [] expect={1,0,2,3,3,0,2,2,3,0,3,5,5,5,0,5,0,2,7,0,7,0,0};
        Graf test=new Graf(expect);
        test.toDotFile("crr");
        File f=new File("crr.gv");
        Graf g=new Graf(f);
        System.out.println(g.toDotString());
        int[] mat_2= g.toSuccessorArray();
        for(int i=0;i<expect.length;i++){
            Assert.assertEquals(expect[i],mat_2[i]);
        }

    }
    @Test
    void testEulerianGraf(){
        File f=new File("crr.gv");
        Graf g=new Graf(f);
        System.out.println(g.toDotString());
        //Assert.assertEquals(1,g);
    }
}
