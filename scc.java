import java.io.*;
import java.util.*;

class scc {

    public static void printgraph(ArrayList<ArrayList<Integer>> adjmatrix) throws IOException{
                    int i = 0,r = adjmatrix.size(),c = adjmatrix.get(0).size();
                    File graphf = new File("graph.dot");
                    graphf.createNewFile();
                    try (FileWriter fwrite = new FileWriter(graphf)) {
                        fwrite.write("digraph dr{ \nnode [style=filled]\n");
                        for(; i<r; i++){
                            for(int j=0; j<c; j++){
                                    if(adjmatrix.get(i).get(j) != 0){
                                        fwrite.write(i+" -> "+j+" [color=blue]\n");
                                    }
                            } 
                        }
                        for(i=0; i<r; i++){
                            fwrite.write(i+" [color=blue fontcolor=blue fillcolor=yellow]\n");
                        }
                        fwrite.write("}\n");
                        fwrite.close();
                    }
    }
    public static void showsccs(ArrayList<ArrayList<Integer>> sccomponents,ArrayList<ArrayList<Integer>> adjmatrix) throws IOException{
                    int i = 0,sccs = sccomponents.size();
                    File graphv = new File("sccs.dot");
                    graphv.createNewFile();
                    try (FileWriter fwrite = new FileWriter(graphv)) {
                        fwrite.write("digraph dr{ \nnode [style=filled]\n");
                        for(; i<sccs; i++){
                            int nodes = sccomponents.get(i).size();
                            if(nodes == 1){
                                fwrite.write(sccomponents.get(i).get(0)+" [color=blue fontcolor=blue fillcolor=yellow]\n");
                            }
                            for(int j=0; j<nodes; j++){
                                for(int k=0; k<nodes; k++){
                                    if(adjmatrix.get(sccomponents.get(i).get(j)).get(sccomponents.get(i).get(k)) != 0){
                                        fwrite.write(sccomponents.get(i).get(j)+" -> "+sccomponents.get(i).get(k)+" [color=blue]\n");
                                        fwrite.write(sccomponents.get(i).get(j)+" [color=blue fontcolor=blue fillcolor=yellow]\n");
                                        fwrite.write(sccomponents.get(i).get(k)+" [color=blue fontcolor=blue fillcolor=yellow]\n");
                                    }
                                }
                            } 
                        }
                        fwrite.write("}\n");
                        fwrite.close();
                    }
    }
    public static void showcgraph(ArrayList<ArrayList<Integer>> sccomponents,ArrayList<ArrayList<Integer>> adjmatrix) throws IOException{
                    int i = 0,sccs = sccomponents.size();
                    Set<ArrayList<Integer>> cedges = new HashSet<ArrayList<Integer>>();
                    File graphg = new File("componentg.dot");
                    graphg.createNewFile();
                    for(; i<sccs; i++){
                        ArrayList<Integer> c1 = new ArrayList<Integer>();
                        c1 = sccomponents.get(i);
                        int s = c1.size(); 
                        for(int j=i+1; j<sccs; j++){
                            ArrayList<Integer> c2 = new ArrayList<Integer>();
                            c2 = sccomponents.get(j);
                            int t = c2.size();
                            for(int m=0; m<s; m++){
                                for(int n=0; n<t; n++){
                                    if(adjmatrix.get(c1.get(m)).get(c2.get(n)) != 0 || adjmatrix.get(c2.get(n)).get(c1.get(m)) != 0){
                                        if(adjmatrix.get(c1.get(m)).get(c2.get(n)) != 0){
                                            ArrayList<Integer> cedge = new ArrayList<Integer>();
                                            cedge.add(i);
                                            cedge.add(j);
                                            cedges.add(cedge);
                                        }
                                        else{
                                            ArrayList<Integer> cedge = new ArrayList<Integer>();
                                            cedge.add(j);
                                            cedge.add(i);
                                            cedges.add(cedge);
                                        }
                                    }
                                }
                            }  
                        }   
                    }
                    try (FileWriter fwrite = new FileWriter(graphg)) {
                        fwrite.write("digraph dr{ \nnode [style=filled]\nforcelabels=true;\n");
                        for(int c=0; c<sccs; c++){
                            fwrite.write(c+" [label=");
                            fwrite.write('"');
                            fwrite.write(" ");
                            int csize = sccomponents.get(c).size();
                            int node;
                            for(node=0; node<csize; node++){
                                fwrite.write(sccomponents.get(c).get(node)+" ");
                            }
                            fwrite.write('"');
                            fwrite.write("color=blue fontcolor=blue fillcolor=yellow");
                            fwrite.write("]\n");
                        }
                        for(ArrayList<Integer> element : cedges) { 
                            fwrite.write(element.get(0)+" -> "+element.get(1)+" [color=blue]\n");
                        }
                        fwrite.write("}\n");
                        fwrite.close();
                    }                
    }
    public static ArrayList<ArrayList<Integer>> kosaraju(ArrayList<ArrayList<Integer>> adjmatrix){
                    ArrayList<ArrayList<Integer>> sccomponents = new ArrayList<ArrayList<Integer>>();
                    int v = adjmatrix.size();
                    Stack<Integer> s1 = new Stack<Integer>(),s2 = new Stack<Integer>(),s3 = new Stack<Integer>(),s4 = new Stack<Integer>();
                    ArrayList<Boolean> visited1 = new ArrayList<Boolean>(), visited2 = new ArrayList<Boolean>();
                    Set<Integer> hash = new HashSet<Integer>();
                    for(int i = 0; i<v; i++){
                        visited1.add(false);
                        visited2.add(false);
                    } 

                    /////////////////////////////////////////////////////////////////////////////dfs1 for topological sort
                    for(int i=0; i<v; i++){
                        if(!visited1.get(i)){
                            int cur_ch = 0,cur_node = i,cur_idx = 0;
                            s1.push(cur_node);
                            s3.push(cur_ch);
                            s4.push(cur_idx);
                            while(!s1.empty() && !s3.empty() && !s4.empty()){
                                    Boolean v_ch = true;
                                    cur_node = s1.peek();
                                    cur_idx = s4.peek();
                                    cur_ch = s3.peek();
                                    for(int j=0; j<v; j++){
                                        if(j!= cur_node && visited1.get(cur_node) == false && adjmatrix.get(cur_node).get(j) == 1)
                                            cur_ch++;
                                    }
                                    s3.pop();
                                    s3.push(cur_ch);
                                    int j;
                                    for(j=cur_idx; j<v; j++){
                                        if(j!= cur_node && adjmatrix.get(cur_node).get(j) == 1 && visited1.get(j) == false){
                                            v_ch = false;
                                            break;
                                        }
                                    }
                                    if(cur_ch == 0 || v_ch == true){
                                        int node = s1.peek();
                                        s1.pop();
                                        s3.pop();
                                        s4.pop();
                                        if(!hash.contains(node)){
                                            s2.push(node);
                                            hash.add(node);
                                            visited1.set(node,true);
                                        }
                                        if(s1.empty() && s4.empty() && s3.empty())
                                            break;
                                        cur_node = s1.peek();
                                        cur_ch = s3.peek();
                                        cur_idx = s4.peek();
                                    }
                                    int k;
                                    for(k=cur_idx; k<v; k++){
                                        if(k!= cur_node && adjmatrix.get(cur_node).get(k) == 1 && visited1.get(k) == false){
                                            visited1.set(cur_node,true);
                                            s1.push(k);
                                            s4.pop();
                                            cur_idx = k+1;
                                            s4.push(cur_idx);
                                            s4.push(0);
                                            cur_ch = s3.pop();
                                            cur_ch--;
                                            s3.push(cur_ch);
                                            s3.push(0);
                                            break;
                                        }
                                    }
                                    
                            }
                        }
                    }


                    //////////////////////////////////////////////////////////////////////////////////reverse the edges

                    ArrayList<ArrayList<Integer>> reverse = new ArrayList<ArrayList<Integer>>();
                    int r = 0, c = 0;
                    while(r < v){
                        ArrayList<Integer> rowr = new ArrayList<Integer>();
                        c = v;
                        while(c > 0){
                            rowr.add(0);
                            c--;
                        }
                        reverse.add(rowr);
                        r++;
                    }
                    for(int i=0; i<v; i++){
                        for(int j=0; j<v; j++){
                            if(adjmatrix.get(i).get(j) == 1)
                                reverse.get(j).set(i,1);
                        }
                    }

                    ////////////////////////////////////////////////////////////////////////////////////dfs2 for scc's
                    hash.clear();
                    
                    while(!s2.empty()){
                        if(!visited2.get(s2.peek())){
                            ArrayList<Integer> sccomponent = new ArrayList<Integer>();
                            int cur_ch = 0,cur_node = s2.peek(),cur_idx = 0;
                            s1.push(cur_node);
                            s3.push(cur_ch);
                            s4.push(cur_idx);
                            while(!s1.empty() && !s3.empty() && !s4.empty()){
                                    Boolean v_ch = true;
                                    cur_node = s1.peek();
                                    cur_idx = s4.peek();
                                    cur_ch = s3.peek();
                                    for(int j=0; j<v; j++){
                                        if(j!= cur_node && visited2.get(cur_node) == false && reverse.get(cur_node).get(j) == 1)
                                            cur_ch++;
                                    }
                                    s3.pop();
                                    s3.push(cur_ch);
                                    int j;
                                    for(j=cur_idx; j<v; j++){
                                        if(j!= cur_node && reverse.get(cur_node).get(j) == 1 && visited2.get(j) == false){
                                            v_ch = false;
                                            break;
                                        }
                                    }
                                    if(cur_ch == 0 || v_ch == true){
                                        int node = s1.peek();
                                        s1.pop();
                                        s3.pop();
                                        s4.pop();
                                        if(!hash.contains(node)){
                                            hash.add(node);
                                            visited2.set(node,true);
                                            sccomponent.add(node);
                                        }
                                        if(s1.empty() && s4.empty() && s3.empty())
                                            break;
                                        cur_node = s1.peek();
                                        cur_ch = s3.peek();
                                        cur_idx = s4.peek();
                                    }
                                    int k;
                                    for(k=cur_idx; k<v; k++){
                                        if(k!= cur_node && reverse.get(cur_node).get(k) == 1 && visited2.get(k) == false){
                                            visited2.set(cur_node,true);
                                            if(!hash.contains(cur_node)){
                                                hash.add(cur_node);
                                                sccomponent.add(cur_node);    
                                            }
                                            s1.push(k);
                                            s4.pop();
                                            cur_idx = k+1;
                                            s4.push(cur_idx);
                                            s4.push(0);
                                            cur_ch = s3.pop();
                                            cur_ch--;
                                            s3.push(cur_ch);
                                            s3.push(0);
                                            break;
                                        }
                                    }
                            }
                            sccomponents.add(sccomponent);
                        }
                        s2.pop();
                    }
                return sccomponents;

    }
    
    public static void main(String[] args)throws IOException{

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
            ArrayList<ArrayList<Integer>> sccomponents = new ArrayList<ArrayList<Integer>>();
            while(r < rows){
                 ArrayList<Integer> rowr = new ArrayList<Integer>();
                 c = columns;
                 while(c > 0){
                       rowr.add(0);
                       c--;
                 }
                 adjmatrix.add(rowr);
                 r++;
            }
            for(int i=0; i<e; i++){
                int v1 = fread.nextInt();
                int v2 = fread.nextInt();
                adjmatrix.get(v1).set(v2,1);
            }
            printgraph(adjmatrix);
            sccomponents = kosaraju(adjmatrix);
            showsccs(sccomponents,adjmatrix);
            showcgraph(sccomponents, adjmatrix);
          t--;
        }
        fread.close();
    }
}

//testcasesui

// 1
// 5 6
// 0 1
// 1 2
// 1 4
// 2 3
// 3 2
// 4 0

// 1
// 5 7
// 0 1
// 0 3
// 0 4
// 1 2
// 1 3
// 2 3
// 3 1

// 1
// 7 9
// 1 2
// 2 1
// 2 5
// 2 3
// 3 4
// 5 6
// 4 2
// 5 3
// 6 5
