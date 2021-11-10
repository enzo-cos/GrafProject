import m1graf2021.*;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.File;

public class TestEulerian {
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
        Eulerian testgraf=new Eulerian();
        Eulerian graf=new Eulerian(f);
        //4 et 2
        System.out.println(graf.getGraf().toDotString());
        Assert.assertEquals(1,graf.isEulerian());
        f=new File("Eulerian.gv");
        graf=new Eulerian(f);
        //4 et 2
        System.out.println(graf.getGraf().toDotString());
        Assert.assertEquals(0,graf.isEulerian());
        f=new File("NotEulerian.gv");
        graf=new Eulerian(f);
        //4 et 2
        System.out.println(graf.getGraf().toDotString());
        Assert.assertEquals(-1,graf.isEulerian());
    }
}
