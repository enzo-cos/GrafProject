package m1graf2021;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EdgeTest {
    /*Graf mygraf=new Graf();
    Node n1;Node n2;Node n3;Node n4;
    Edge e1; Edge e2; Edge e3;*/
    Graf mygraf=new Graf();
    Node n1=new Node(1);
    Node n2=new Node(2,"A");
    Node n3=new Node(3);
    Node n4=new Node(4);
    Node n22=new Node(2);
    Edge e1_1=new Edge(1,1);
    Edge e1_2=new Edge(n1,n2);
    Edge e2_5=new Edge(2,5);

    @Test
    public void Testfrom() {
        Assert.assertEquals(1, e1_1.from().getId());
        Assert.assertEquals(n1.getId(), e1_2.from().getId());
        Assert.assertEquals(2, e2_5.from().getId());
    }

    @Test
    void Testto() {
        Assert.assertEquals(1, e1_1.to().getId());
        Assert.assertEquals(n2.getId(), e1_2.to().getId());
        Assert.assertEquals(5, e2_5.to().getId());
    }

    @Test
    void TestgetSymmetric() {
        Edge e=e1_2.getSymmetric();
        Assert.assertEquals(2, e.from().getId()); Assert.assertEquals(1, e.to().getId());
        e=e1_1.getSymmetric();
        Assert.assertEquals(1, e.from().getId()); Assert.assertEquals(1, e.to().getId());
        e=e2_5.getSymmetric();
        Assert.assertEquals(5, e.from().getId()); Assert.assertEquals(2, e.to().getId());
        e=e2_5.getSymmetric().getSymmetric();
        Assert.assertEquals(2, e.from().getId()); Assert.assertEquals(5, e.to().getId());
    }

    @Test
    void TestisSelfLoop() {
        Boolean b=e1_1.isSelfLoop();
        Assert.assertTrue(b);
        b=e1_2.isSelfLoop();
        Assert.assertFalse(b);
    }

    @Test
    void TestcompareTo() {
        Edge e = new Edge(2,5);
        Assert.assertTrue(e1_1.compareTo(e1_2)<0);
        Assert.assertTrue(e2_5.compareTo(e1_2)>0);
        Assert.assertTrue(e.compareTo(e2_5)==0);
    }
    @Test
    void Testequals() {
        Edge e = new Edge(2,5);
        Assert.assertFalse(e1_1.equals(e1_2));
        Assert.assertTrue(e1_1.equals(e1_1));
        Assert.assertTrue(e.equals(e2_5));
    }

    @Test
    void TestNodeequals() {
        Assert.assertFalse(n1.equals(n2));
        Assert.assertTrue(n22.equals(n2));
        Assert.assertTrue(n4.equals(n4));
    }

    @Test
    void testEdgeWeight() {
        Edge e1=new Edge(1,2,7);
        Edge e2=new Edge(1,2);
        Edge e3=new Edge(1,2,null);
        Edge e4=new Edge(1,2,17);
        System.out.println(e1);
        System.out.println(e2);
        System.out.println(e3);System.out.println(e4);

    }
}