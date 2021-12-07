import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import m1graf2021.*;

public class TestChinese {

    @Test
    public void testPair(){
        Pair<Integer,Boolean> p=new Pair<>(4,true);
        Assert.assertEquals((Integer) 4,p.getFirst());
        Assert.assertEquals(true,p.getSecond());
        p.setValue(8,false);
        Assert.assertEquals((Integer) 8,p.getFirst());
        Assert.assertEquals(false,p.getSecond());
        Assert.assertEquals(p,p.getValue());
        Pair<Node,Edge> p2=new Pair<>(new Node(1),new Edge(1,2));
        Assert.assertEquals(1,p2.getFirst().getId());
        Assert.assertEquals(2,p2.getSecond().to().getId());
    }

    @Test
    void testEulerianGraf(){
        File f=new File("semi-Eulerian.gv");
        ChinesePostman graf=new ChinesePostman(f);
        //4 et 2
        System.out.println(graf.getGraf().toDotString());
        Assert.assertEquals(1,graf.isEulerian());
        f=new File("Eulerian.gv");
        graf=new ChinesePostman(f);
        //4 et 2
        System.out.println(graf.getGraf().toDotString());
        Assert.assertEquals(0,graf.isEulerian());
        f=new File("NotEulerian.gv");
        graf=new ChinesePostman(f);
        //4 et 2
        System.out.println(graf.getGraf().toDotString());
        Assert.assertEquals(-1,graf.isEulerian());

        f=new File("src/exempleFinal");
        graf=new ChinesePostman(f);
        System.out.println(graf.toDotString());
    }

    @Test
    public void testchineseproductEulerian(){
        File f=new File("src/Eulerian.gv");
        UndirectedGraf g=new UndirectedGraf();
        ChinesePostman graf=new ChinesePostman(f);
        System.out.println(graf.toDotString());
        List<Edge> list=graf.getEulerianCircuitEdge();
        System.out.println(list);
        //1 2 3 4 5 3 2 4 1
        f=new File("src/Eulerian2.gv");
        graf=new ChinesePostman(f);
        System.out.println(graf.toDotString());
        int[]expect2={1,2,3,6,5,7,8,9,10,8,6,2,4,1,4,5,1};
        list=graf.getEulerianCircuitEdge();
        System.out.println(list);
        // 1 2 3 6 5 7 8 9 10 8 6 2 4 1 4 5 1
    }

    @Test
    public void testchineseproductSemiEuleurian(){
        File f=new File("src/semi-Eulerian.gv");
        UndirectedGraf g=new UndirectedGraf();
        ChinesePostman graf=new ChinesePostman(f);
        Node n= graf.getMinNodeOdd();
        int[] expect={2,1,4,3,2,3,5,4};
        List<Edge> list=graf.getSemiEulerianCircuitEdge(n);
        System.out.println(list);
        // 2 1 4 3 2 3 5 4

        f=new File("src/semi-Eulerian2.gv");
        graf=new ChinesePostman(f);
        n= graf.getMinNodeOdd();
        int[] expect2={3,2,1,4,5,2,7,3,5,7,6,4};
        list=graf.getSemiEulerianCircuitEdge(n);
        System.out.println(list);
        // 3 2 1 4 5 2 7 3 5 7 6 4
    }

    @Test
    public void testtest(){
        UndirectedGraf g=new UndirectedGraf();
        File f=new File("src/Eulerian.gv");
        ChinesePostman graf=new ChinesePostman(f);
        //System.out.println(graf.Floyd_Warshall().toString());

        //1,2 = 2   1,3 = 3  1,4 = 2    1,5 = 5
        //2,3 = 1   2,4 = 1   2,5 = 3
        // 3,4 = 2   3,5 2
        // 4,5 = 4
        Map map=graf.Floyd_Warshall();
        System.out.println(map);
    }

    @Test
    public void testTD(){
        UndirectedGraf g=new UndirectedGraf();
        File f=new File("src/Exemple_TD.gv");
        ChinesePostman graf=new ChinesePostman(f);
        // 3 5 9 10
        // 3,5 9,10 =12    3,9 5,10 =16      3,10 5,9 = 15
        Map map=graf.Floyd_Warshall();
        System.out.println(map);
        List<Pair<Node,Node>> list=graf.getListPair(map,true);
        System.out.println("list of pair : "+list);
        //graf.duplicateEdge
        for(Pair p : list){
            System.out.println(p+" -> "+map.get(p));
        }
        graf.duplicateEdge(map,list);
        //System.out.println(graf.toDotString());
        System.out.println(graf.getEulerianCircuitEdge());
        System.out.println(graf.toDotString());
    }

    @Test
    public void testGiven(){
        UndirectedGraf g=new UndirectedGraf();
        File f=new File("src/exempleFinal");
        ChinesePostman graf=new ChinesePostman(f);
        graf.isEulerian();
        Map map=graf.Floyd_Warshall();
        System.out.println(map);
        List<Pair<Node,Node>> list=graf.getListPair(map,false);
        System.out.println("list of pair : "+list);
        //graf.duplicateEdge
        for(Pair p : list){
            System.out.println(map.get(p));
        }
        graf.duplicateEdge(map,list);
        System.out.println(graf.getEulerianCircuitEdge());
        System.out.println(graf.toDotString());
    }

    @Test
    public void TestGeneral(){
        File f=new File("src/NotEulerianTD.gv");
        ChinesePostman g=new ChinesePostman(f);
        List<Edge> l=g.getEulerianCircuit(false);
        System.out.println("Eulerian circuit : \n"+l);
        //random false :  1->2  2->3  3->6  6->2   2->6   6->3   3->7   7->4   4->5   5->7   7->8   8->9   9->7  7->9   9->10  10->11  11->10  10->7   7->11   11->7  7->2  2->1  1->5  5->1
        System.out.println(g.toDotString());

        f=new File("src/exempleFinal");
         g=new ChinesePostman(f);
        System.out.println(g.getEulerianCircuitEdge());
         l=g.getEulerianCircuit(false);
        System.out.println("Eulerian circuit : \n"+l);
        System.out.println(g.toDotString());
        //random false : 1->2  2->1  1->3 3->4 4->3 3->2 2->4 4->1
    }

}
