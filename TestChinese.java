import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

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

        f=new File("myGraf.gv");
        graf=new ChinesePostman(f);
        System.out.println(graf.toDotString());

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
        int[] expect={1,2,3,4,5,3,2,4,1};
        List<Node> list=graf.getEulerianCircuit();
        System.out.println(list);
        int i=0;
        for(Node n : list){
            Assert.assertEquals(expect[i],n.getId());
            i++;
        }
        //1 2 3 4 5 3 2 4 1
        f=new File("src/Eulerian2.gv");
        graf=new ChinesePostman(f);
        System.out.println(graf.toDotString());
        int[]expect2={1,2,3,6,5,7,8,9,10,8,6,2,4,1,4,5,1};
        list=graf.getEulerianCircuit();
        System.out.println(list);
        i=0;
        for(Node n : list){
            Assert.assertEquals(expect2[i],n.getId());
            i++;
        }
        // 1 2 3 6 5 7 8 9 10 8 6 2 4 1 4 5 1
    }

    @Test
    public void testchineseproductSemiEuleurian(){
        File f=new File("src/semi-Eulerian.gv");
        UndirectedGraf g=new UndirectedGraf();
        ChinesePostman graf=new ChinesePostman(f);
        Node n= graf.getMinNodeOdd();
        int[] expect={2,1,4,3,2,3,5,4};
        List<Node> list=graf.getSemiEulerianCircuit(n);
        System.out.println(list);
        int i=0;
        for(Node node : list){
            Assert.assertEquals(expect[i],node.getId());
            i++;
        }
        // 2 1 4 3 2 3 5 4

        f=new File("src/semi-Eulerian2.gv");
        graf=new ChinesePostman(f);
        n= graf.getMinNodeOdd();
        int[] expect2={3,2,1,4,5,2,7,3,5,7,6,4};
        list=graf.getSemiEulerianCircuit(n);
        System.out.println(list);
        i=0;
        for(Node node : list){
            Assert.assertEquals(expect2[i],node.getId());
            i++;
        }
        // 3 2 1 4 5 2 7 3 5 7 6 4
    }

}
