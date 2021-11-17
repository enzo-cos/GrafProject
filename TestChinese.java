import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.File;
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
        /*
        1 -- 2[len=2,label=2];
        1 -- 4[len=2,label=2];
        2 -- 3[len=3,label=3];
        3 -- 2[len=1,label=1];
        3 -- 4[len=4,label=4];
        4 -- 5[len=6,label=6];
        5 -- 3[len=2,label=2];
         */
        File f=new File("semi-Eulerian.gv");
        ChinesePostman testgraf=new ChinesePostman();
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
        File f=new File("Eulerian.gv");
        UndirectedGraf g=new UndirectedGraf();
        ChinesePostman graf=new ChinesePostman(f);
        System.out.println(graf.toDotString());
        System.out.println(graf.getEulerianCircuit());
        //123453241
        f=new File("src/Eulerian2.gv");
        graf=new ChinesePostman(f);
        System.out.println(graf.toDotString());
        System.out.println(graf.getEulerianCircuit());
    }

    @Test
    public void testchineseproductSemiEuleurian(){
        File f=new File("semi-Eulerian.gv");
        UndirectedGraf g=new UndirectedGraf();
        ChinesePostman graf=new ChinesePostman(f);
        Node n= graf.getMinNodeOdd();
        System.out.println(graf.getSemiEulerianCircuit(n));
        //
        f=new File("src/semi-Eulerian2.gv");
        graf=new ChinesePostman(f);
        n= graf.getMinNodeOdd();
        System.out.println(graf.getSemiEulerianCircuit(n));
        //// 3 2 1 4 5 2 7 3 5 7 6 4
    }
}
