import m1graf2021.*;

import java.io.File;

/******************************
 *****     PW3 PROJECT   ******
 *****************************/
public class Eulerian {
    private Graf graf;

    public Eulerian(){
        graf=new Graf();
    }

    /**
     * Constructor from Dot File
     * @param f File
     */
    public Eulerian(File f){
        graf=new UndirectedGraf(f);
    }

    /**
     * Getter of graf
     * @return graf
     */
    public Graf getGraf() {
        return graf;
    }

    /**
     * Know if a graf is eulerian
     * @return 0 if Eulerian
     * @return 1 if semi-Eulerian
     * @return -1 if not Eulerian
     */
    public int isEulerian(){
        int nbOddDegree=0; //Number of odd degree node
        for(Node n : graf.getAllNodes()){ //For each Node, if degree(node) = odd => nbOddDegree+1
            if(graf.degree(n)%2!=0){
                nbOddDegree++;
            }
        }
        if(nbOddDegree==0) return 0;//Eulerian
        if(nbOddDegree==2) return 1;//Semi-Eulerian
        //if(nbOddDegree>2) return -1;//Not Eulerian
        return -1;
    }
}
