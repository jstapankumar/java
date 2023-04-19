import java.util.*;
import java.util.ArrayList;

class arrays {
    
    public static void main(String[] args){
       
        //////////////////////////////////////////////static 1D arrays
        /* *
        Scanner num = new Scanner(System.in);
        int size = num.nextInt();
        int[] arr = new int[size];

        //input
        for(int i=0; i<size; i++)
            arr[i] = num.nextInt();

        Arrays.sort(arr);
        num.close();

        //output
        for(int i=0; i<arr.length; i++)
            //arr[i] = i + 1;                      //initialization
            System.out.println(arr[i]);
        //*/




        //////////////////////////////////////dynamic arrays 1D arrays
        /* *
        ArrayList<Integer> array = new ArrayList<Integer>();

        Scanner sc = new Scanner((System.in));
        int sz = sc.nextInt();
        sc.close();

        for(int i=0; i<sz; i++)
            array.add(i + 1);
        array.add(53);
        System.out.println(array);

        array.remove(array.indexOf(3));
        array.set(2,21);
        System.out.println(array);

        array.sort(null);
        System.out.println(array);
        //*/




        ////////////////////////////////////static 2D arrays
        /* *
        Scanner sc = new Scanner(System.in);
        int row = sc.nextInt();
        int col = sc.nextInt();

        int[][] arr2d = new int[row][col];
        for(int i=0; i<row; i++){
            for(int j=0; j<col; j++){    
                int v = sc.nextInt();
                arr2d[i][j] = v;
            }
        }
        sc.close();
        for(int i=0; i<row; i++){
            for(int j=0; j<col; j++)
                System.out.printf("%-3d",arr2d[i][j]);
            System.out.printf("\n");
        }
        //*/

       /////////////////////////////////dynamic 2D arrays
      

       
       //* *
       Scanner scan = new Scanner(System.in);
       ArrayList<ArrayList<Integer>> arrd2 = new ArrayList<ArrayList<Integer>>();
       int rs = scan.nextInt();
       
       for(int i=0; i<rs; i++){
          ArrayList<Integer> row = new ArrayList<Integer>();
          int cs = scan.nextInt();
          for(int j=0; j<cs; j++)
              row.add(scan.nextInt());
          row.remove(cs-1);
          row.sort(null);
          row.set(cs-2,cs*cs);
          arrd2.add(row);
       }
       ArrayList<Integer> extra = new ArrayList<Integer>();
       arrd2.add(extra);
       arrd2.set(0,extra);
       System.out.println(arrd2);
       scan.close();
       // */
       
    }
}
