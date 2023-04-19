import java.io.*;
import java.util.*;

public class kruskal {

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
    public static void showMST(ArrayList<ArrayList<Integer>> adjmatrix,ArrayList<ArrayList<Integer>> mst) throws IOException{
        int i = 0,r = adjmatrix.size(),c = adjmatrix.get(0).size();
        File grapht = new File("graph_mst.dot");
        grapht.createNewFile();
        try (FileWriter fwrite = new FileWriter(grapht)) {
            fwrite.write("graph dr{ \n");
            for(; i<r; i++){
                for(int j=0; j<c; j++){
                    if(j<i){
                        if(adjmatrix.get(i).get(j) != Integer.MIN_VALUE){
                           boolean edge_present = false;
                           for(int k=0; k<mst.size(); k++){
                               ArrayList<Integer> edge = new ArrayList<Integer>(2);
                               edge = mst.get(k);
                               if(edge.get(0) == i && edge.get(1) == j)
                                  edge_present = true;
                           }
                           if(edge_present)
                              fwrite.write(i+" -- "+j+" [label="+adjmatrix.get(i).get(j)+"]\n");
                        }
                    }
                } 
            }
            fwrite.write("}\n");
            fwrite.close();
        }
    }
    public static void showMSTInTheGraph(ArrayList<ArrayList<Integer>> adjmatrix,ArrayList<ArrayList<Integer>> mst) throws IOException{
        int i = 0,r = adjmatrix.size(),c = adjmatrix.get(0).size();
        File graphv = new File("graph_in_mst.dot");
        graphv.createNewFile();
        try (FileWriter fwrite = new FileWriter(graphv)) {
            fwrite.write("graph dr{ \nnode [style=filled]\n");
            for(; i<r; i++){
                for(int j=0; j<c; j++){
                    if(j<i){
                        if(adjmatrix.get(i).get(j) != Integer.MIN_VALUE){
                           boolean edge_present = false;
                           for(int k=0; k<mst.size(); k++){
                               ArrayList<Integer> edge = new ArrayList<Integer>(2);
                               edge = mst.get(k);
                               if(edge.get(0) == i && edge.get(1) == j)
                                  edge_present = true;
                           }
                           if(edge_present){
                              fwrite.write(i+" -- "+j+" [label="+adjmatrix.get(i).get(j)+" color=red]\n");
                              fwrite.write(i+" [color=red fontcolor=red fillcolor=pink]\n");
                              fwrite.write(j+" [color=red fontcolor=red fillcolor=pink]\n");
                           }
                           else
                              fwrite.write(i+" -- "+j+" [label="+adjmatrix.get(i).get(j)+"]\n");
                        }
                    }
                } 
            }
            fwrite.write("}\n");
            fwrite.close();
        }
    }
    public static int findroot(ArrayList<Integer> parent,int vertex){
           if(parent.get(vertex) == vertex)
              return vertex;
           return findroot(parent,parent.get(vertex));
    }
    public static void union(ArrayList<Integer> parent,int v1,int v2){
           parent.set(v1,v2);
    }
    public static ArrayList<ArrayList<Integer>> Kruskals(ArrayList<ArrayList<Integer>> adjmatrix){
           int e = 0;
           ArrayList<ArrayList<Integer>> edges = new ArrayList<ArrayList<Integer>>(adjmatrix.size());
           ArrayList<ArrayList<Integer>> minimum_spanning_tree = new ArrayList<ArrayList<Integer>>(adjmatrix.size()-1);
           for(int i = adjmatrix.size()-1; i>=0; i--){
               int j = 0;
               for(; j<adjmatrix.get(i).size(); j++){
                   if(j<i){
                      if(adjmatrix.get(i).get(j) != Integer.MIN_VALUE){
                         int v1 = i,v2 = j;
                         ArrayList<Integer> edge = new ArrayList<Integer>(2);
                         edge.add(v1);
                         edge.add(v2);
                         edges.add(edge);
                         e++;
                      }
                   }
               }
           }

           for(int i=0; i<e-1; i++){
               int min_idx = i,j = min_idx+1;
               for(;j<e;){
                   if(adjmatrix.get(edges.get(j).get(0)).get(edges.get(j).get(1)) < adjmatrix.get(edges.get(min_idx).get(0)).get(edges.get(min_idx).get(1)))
                      min_idx = j;
                   j++;
               }
               ArrayList<Integer> temp = new ArrayList<Integer>();
               temp = edges.get(min_idx);
               edges.set(min_idx,edges.get(i));
               edges.set(i,temp);
           }
           int e_idx = 0,idx = adjmatrix.size()-1,v = idx+1;
           ArrayList<Integer> parent = new ArrayList<Integer>();
           while(idx >= 0){
                 parent.add(v-idx-1);
                 idx--;
           }
           for(int edges_included = 0; edges_included < adjmatrix.size() - 1;){
               ArrayList<Integer> shortest_edge_notincluded = new ArrayList<Integer>();
               shortest_edge_notincluded = edges.get(e_idx);
               int root_u = findroot(parent,shortest_edge_notincluded.get(0));
               int root_v = findroot(parent,shortest_edge_notincluded.get(1));
               if(root_u != root_v){
                  minimum_spanning_tree.add(shortest_edge_notincluded);
                  union(parent,root_u,root_v);
                  edges_included++;
               } 
               e_idx++;
          }
          return minimum_spanning_tree; 
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

             int v,e,rows,columns,r = 0,c = 0;
             v = fread.nextInt();
             e = fread.nextInt();
             rows = v;
             columns = rows;
             ArrayList<ArrayList<Integer>> adjmatrix = new ArrayList<ArrayList<Integer>>();
             ArrayList<ArrayList<Integer>> mst = new ArrayList<ArrayList<Integer>>();
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
             showAdjencyList(adjmatrix);
             ShowGraph(adjmatrix);
             mst = Kruskals(adjmatrix);
             showMST(adjmatrix, mst);  
             showMSTInTheGraph(adjmatrix, mst);  
             System.out.println("------------------------------------------------------------------------");
             System.out.println(); 
           t--;
        }
        fread.close();
    } 
}
