package m1graf2021;

import java.util.Collections;
import java.util.List;

//import m1graf2021.*;


public class TestGrafPW2 {

    public static void main(String[] args) {
        System.out.println(">>>>>>>> Creating the subject example graph in G");
        Graf g = new Graf(2, 4, 0, 0, 6, 0, 2, 3, 5, 8, 0, 0, 4, 7, 0, 3, 0, 7, 0);
        System.out.println(">>>> Graph information");
        System.out.println(">> DOT representation\n"+g.toDotString());
        System.out.println(""+g.nbNodes()+" nodes, "+g.nbEdges()+" edges");
        System.out.println(">> Nodes: ");
        List<Node> nodes = g.getAllNodes();
        Collections.sort(nodes);
        for (Node n: nodes)
            System.out.println("Node "+n+": degree "+g.degree(n)+" (in: "+g.inDegree(n)+"/ out: "+g.outDegree(n)+")");

        List<Edge> edges;
        System.out.println(">> Edges: ");
        System.out.println("---------------------------");
        System.out.println("Out-edges per node");
        for (Node n: nodes) {
            edges = g.getOutEdges(n);
            Collections.sort(edges);
            System.out.println(""+n+": "+edges);
        }

        System.out.println("In-edges per node");
        for (Node n: nodes) {
            edges = g.getInEdges(n);
            Collections.sort(edges);
            System.out.println(""+n+": "+edges);
        }

        /////////////////////////////////////////////////////

        System.out.println("\n>>>>>>>> creating isolated node 12");
        g.addNode(12);
        System.out.println("Graph now:");
        System.out.println(g.toDotString());
        System.out.println(""+g.nbNodes()+" nodes, "+g.nbEdges()+" edges");
        nodes = g.getAllNodes();
        Collections.sort(nodes);
        System.out.println("Nodes list: "+nodes);

        System.out.println("\n>>>>>>>> Removing node 3");
        g.removeNode(3);
        System.out.println("Graph now:");
        System.out.println(g.toDotString());
        System.out.println(""+g.nbNodes()+" nodes, "+g.nbEdges()+" edges");
        nodes = g.getAllNodes();
        Collections.sort(nodes);
        System.out.println("Nodes list: "+nodes);

        System.out.println(">> Edges: ");
        System.out.println("---------------------------");
        System.out.println("Out-edges per node");
        for (Node n: nodes) {
            edges = g.getOutEdges(n);
            Collections.sort(edges);
            System.out.println(""+n+": "+edges);
        }

        System.out.println("In-edges per node");
        for (Node n: nodes) {
            edges = g.getInEdges(n);
            Collections.sort(edges);
            System.out.println(""+n+": "+edges);
        }

        System.out.println("\n>>>>>>>> Recreating edges (4, 3), (3, 6), (7, 3), adding edge (12, 3), creating edge (3, 25)");
        g.addEdge(new Edge(4, 3));
        g.addEdge(new Edge(3, 6));
        g.addEdge(new Edge(7, 3));
        g.addEdge(new Edge(12, 3));
        g.addEdge(3, 25);
        System.out.println("Graph now:");
        System.out.println(g.toDotString());
        System.out.println(""+g.nbNodes()+" nodes, "+g.nbEdges()+" edges");
        nodes = g.getAllNodes();
        Collections.sort(nodes);
        System.out.println("Nodes list: "+nodes);

        System.out.println("");
        System.out.println("\n>>>>>>>>  Edges removal");
        System.out.println(">>>> Removing existing edges (7, 3) and (4, 8)");
        g.removeEdge(7, 3);
        g.removeEdge(4, 8);
        System.out.println(">>>> Removing absent edge (3, 4)");
        g.removeEdge(3, 4);
        System.out.println(">>>> Removing edges whith 1 or 2 not existing end-points: (-3, 4), (6, 0), (4, 11), (-1, -2), (13, 3), (9, 10)");
        g.removeEdge(-3, 4);
        g.removeEdge(6, 0);
        g.removeEdge(4, 11);
        g.removeEdge(-1, -2);
        g.removeEdge(13, 3);
        g.removeEdge(9, 10);

        System.out.println("Graph now:");
        System.out.println(g.toDotString());
        System.out.println(""+g.nbNodes()+" nodes, "+g.nbEdges()+" edges");
        nodes = g.getAllNodes();
        Collections.sort(nodes);
        System.out.println("Nodes list: "+nodes);

        System.out.println("\n>>>>>>>> MULTIGRAPH: adding a self-loop on node 6, and a second edge (1, 4)");
        g.addEdge(6, 6);
        g.addEdge(1, 4);
        System.out.println("Graph now:");
        System.out.println(g.toDotString());
        System.out.println(""+g.nbNodes()+" nodes, "+g.nbEdges()+" edges");
        nodes = g.getAllNodes();
        Collections.sort(nodes);
        System.out.println("Nodes list: "+nodes);
        System.out.println("Degree of node 6: "+g.degree(6)+" (in: "+g.inDegree(6)+"/ out: "+g.outDegree(6)+")");

        System.out.println(">> Edges: ");
        System.out.println("---------------------------");
        System.out.println("Out-edges per node");
        for (Node n: nodes) {
            edges = g.getOutEdges(n);
            Collections.sort(edges);
            System.out.println(""+n+": "+edges);
        }

        System.out.println("In-edges per node");
        for (Node n: nodes) {
            edges = g.getInEdges(n);
            Collections.sort(edges);
            System.out.println(""+n+": "+edges);
        }

        System.out.println(">>>>>>>>>>    Get the reverse graph");
        System.out.println(g.getReverse().toDotString());

        System.out.println(">>>>>>>>>>    Get the transitive closure");
        System.out.println(g.getTransitiveClosure().toDotString());

        System.out.println(">>>>>>>>>>    Emptying the graph by removing all its nodes");
        nodes = g.getAllNodes();
        for (Node u: nodes)
            g.removeNode(u);
        System.out.println("Graph now:");
        System.out.println(g.toDotString());

        System.out.println(">>>> Searching for node 7");
        if (g.existsNode(7))
            System.out.println("Node 7 exists");
        else
            System.out.println("There is no Node 7");

        System.out.println(">>>> Searching for edge (4, 2)");
        if (g.existsEdge(4, 2))
            System.out.println("Edge (4, 2) exists");
        else
            System.out.println("There is no edge (4, 2)");
    }


}
