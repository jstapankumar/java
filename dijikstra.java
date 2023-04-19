import java.io.*;
import java.util.*;

public class dijikstra{

    private static final int noparent = -1;
    public static void showAdjencyList(ArrayList<ArrayList<Integer>> adjmatrix){
           int k = 0;
           System.out.println("printing adjency list....");
           System.out.println("------------------------------------------------------------------------");
           for(int i=0; i<adjmatrix.size(); i++){
               System.out.printf("%d ->",i);
               int j = 0,col = adjmatrix.get(i).size();
               for( ; j<adjmatrix.get(i).size(); j++){
                   if(adjmatrix.get(i).get(j) != Integer.MIN_VALUE){
                        boolean last = true;
                        k = j+1;
                        while(k < col){
                              if(adjmatrix.get(i).get(k) != Integer.MIN_VALUE){
                                 last = false;
                                 break;
                              } 
                              k++; 
                        }
                        if(last)
                           System.out.print("["+j+","+adjmatrix.get(i).get(j)+"]");
                        else
                           System.out.print("["+j+","+adjmatrix.get(i).get(j)+"]->");
                  }
               }
               System.out.println();
           }
           System.out.println("------------------------------------------------------------------------");
    }
    public static void ShowGraph(ArrayList<ArrayList<Integer>> adjmatrix) throws IOException{
        int i = 0,r = adjmatrix.size(),c = adjmatrix.get(0).size();
        File graphf = new File("graph.dot");
        graphf.createNewFile();
        try (FileWriter fwrite = new FileWriter(graphf)) {
            fwrite.write("graph dr{ \n");
            for(; i<r; i++){
                for(int j=0; j<c; j++){
                    if(j<i){
                        if(adjmatrix.get(i).get(j) != Integer.MIN_VALUE){
                           fwrite.write(i+" -- "+j+" [label="+adjmatrix.get(i).get(j)+"]\n");
                        }
                    }
                } 
            }
            fwrite.write("}\n");
            fwrite.close();
        }
    }
    public static ArrayList<Integer> Dijikstras(ArrayList<ArrayList<Integer>> adjmatrix,int s,int d){
           
           int v = adjmatrix.size();
           ArrayList<Integer> visited = new ArrayList<Integer>(adjmatrix.size());
           ArrayList<Integer> dist = new ArrayList<Integer>(adjmatrix.size());
           ArrayList<Integer> prev = new ArrayList<Integer>(adjmatrix.size());
           while(v>0){
              dist.add(Integer.MAX_VALUE);
              visited.add(0);
              prev.add(Integer.MIN_VALUE);
              v--;
           }
           
           dist.set(s,0);
           prev.set(s,noparent);
           v++;
           while(v < adjmatrix.size()){

                int cur_best = Integer.MAX_VALUE,cur_shortest = Integer.MAX_VALUE;
                int idx = 0,traversal = adjmatrix.size()-1;
                while(traversal-idx >= 0){
                     
                     if(visited.get(traversal-idx) == 0 && dist.get(traversal-idx) != Integer.MAX_VALUE && dist.get(traversal-idx)<cur_shortest){
                        cur_best = traversal - idx;
                        cur_shortest = dist.get(traversal-idx);
                     }
                     idx++;
                }
                if(cur_best != Integer.MAX_VALUE){
                    visited.set(cur_best,1);
                    int j = adjmatrix.size()-1;
                    while(j>=0){
                        int weight = adjmatrix.get(cur_best).get(j);
                        if(weight != Integer.MIN_VALUE && dist.get(cur_best)+weight < dist.get(j)){
                            dist.set(j,dist.get(cur_best)+weight);
                            prev.set(j,cur_best);
                        }
                        j--;
                    }
                }
                else
                   return null;
             v++;
           }
           return prev;
    }
    public static void find_shortestpath(int d,ArrayList<Integer> prev,ArrayList<Integer> shortest_weighted_path){
        if(d == -1)
           return;
        find_shortestpath(prev.get(d),prev,shortest_weighted_path);
        if(prev.get(d)!=-1){
            System.out.print("->"+d);
        }
        else{
            System.out.println("printing shortest path....");
            System.out.print(d);
        } 
        shortest_weighted_path.add(d);   
    }
    public static void showShortestPath(ArrayList<Integer> shortest_weighted_path,ArrayList<ArrayList<Integer>> adjmatrix) throws IOException{
        File grapht = new File("graph_shortest_path.dot");
        grapht.createNewFile();
        FileWriter fwrite = new FileWriter(grapht);
        fwrite.write("graph dr{ \nnode [style=filled]\n");
        for(int i=0; i<shortest_weighted_path.size()-1; i++){
            fwrite.write(shortest_weighted_path.get(i)+" -- "+shortest_weighted_path.get(i+1)+" [label="+adjmatrix.get(shortest_weighted_path.get(i)).get(shortest_weighted_path.get(i+1))+" color=red]\n");
            fwrite.write(i+" [color=red fontcolor=red fillcolor=pink]\n");
            fwrite.write(i+1+" [color=red fontcolor=red fillcolor=pink]\n");
        }
        fwrite.write("}\n");
        fwrite.close();
        
    }
    public static void showShortestPathInTheGraph(ArrayList<ArrayList<Integer>> adjmatrix,ArrayList<Integer> shortest_path) throws IOException{
        int i = 0,r = adjmatrix.size(),c = adjmatrix.get(0).size();
        File graphv = new File("graph_in_shortest_path.dot");
        graphv.createNewFile();
        try (FileWriter fwrite = new FileWriter(graphv)) {
            fwrite.write("graph dr{ \nnode [style=filled]\n");
            for(; i<r; i++){
                for(int j=0; j<c; j++){
                    if(j<i && adjmatrix.get(i).get(j) != Integer.MIN_VALUE){
                        fwrite.write(i+" -- "+j+" [label="+adjmatrix.get(i).get(j));
                        boolean node1 = false,node2 = false;
                        for(int k=0;k<shortest_path.size();k++){
                             if(shortest_path.get(k) == i)
                                node1 = true;
                             if(shortest_path.get(k) == j)
                                node2 = true;
                        }
                        if(node1 && node2){
                             fwrite.write(" color=red]\n");
                             fwrite.write(i+" [color=red fontcolor=red fillcolor=pink]\n");
                             fwrite.write(j+" [color=red fontcolor=red fillcolor=pink]\n");
                        }
                        else
                            fwrite.write("]\n");
                    }
                } 
            }
            fwrite.write("}\n");
            fwrite.close();
        }
    }
    public static void main(String[] args) throws IOException{
        
        System.out.println();
        System.out.println("Enter the input fine name: ");
        Scanner scan = new Scanner(System.in);
        String filename = scan.nextLine();
        scan.close();
        File input = new File(filename);
        Scanner fread = new Scanner(input);
        int t = fread.nextInt();
        while(t>0){

             int v,e,s,d,rows,columns,r = 0,c = 0;
             v = fread.nextInt();
             e = fread.nextInt();
             rows = v;
             columns = rows;
             ArrayList<ArrayList<Integer>> adjmatrix = new ArrayList<ArrayList<Integer>>();
             ArrayList<Integer> parents = new ArrayList<Integer>(v);
             ArrayList<Integer> shortest_path = new ArrayList<Integer>();
             while(r < rows){
                  ArrayList<Integer> rowr = new ArrayList<Integer>();
                  c = columns;
                  while(c > 0){
                        rowr.add(Integer.MIN_VALUE);
                        c--;
                  }
                  adjmatrix.add(rowr);
                  r++;
             }
             for(int i=0; i<e; i++){
                 int v1 = fread.nextInt();
                 int v2 = fread.nextInt();
                 int wt = fread.nextInt();
                 adjmatrix.get(v1).set(v2,wt);
                 adjmatrix.get(v2).set(v1,wt);
             }
             s = fread.nextInt();
             d = fread.nextInt();
             showAdjencyList(adjmatrix);
             ShowGraph(adjmatrix);
             parents = Dijikstras(adjmatrix, s, d);
             if(parents == null){
                System.out.println("source and destination are not connected");
                System.out.println();
             }
             else{
                find_shortestpath(d, parents, shortest_path);
                System.out.println();
                System.out.println("------------------------------------------------------------------------");
                showShortestPath(shortest_path,adjmatrix);
                showShortestPathInTheGraph(adjmatrix, shortest_path);
                System.out.println();
             }
           t--;
        }
        fread.close();
    } 
}