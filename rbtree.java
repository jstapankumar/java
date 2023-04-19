import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class Node{
    public 
    Node right;
    Node left;
    Node par;
    boolean col;
    int data;
    Node(){
        this.right = null;
        this.left = null;
        this.par = null;
        this.col = false;
    }
    Node(int d){
        this.right = null;
        this.left = null;
        this.par = null;
        this.col = false;
        this.data = d;
    }
};

class myrbtree{
    public 
    Node root;
    myrbtree(){ 
        Node r = new Node(); 
        r = null;
        this.root = r;
    }
    Node search(int num){
        Node tmp = this.root;
        while(tmp != null){
            if(tmp.data > num)
                tmp = tmp.left;
            else if(tmp.data < num)
                tmp = tmp.right;
            else
                return tmp;
        }
        return tmp;
    }
    void right_rotate(Node r){
            Node leftptr = r.left;
            Node leftr = r.left.right;
            Node parent = r.par;
            if(parent != null){
                if(r.par.left == r){
                    parent.left = leftptr;
                    leftptr.par = parent;
                    leftptr.right = r;
                    r.par = leftptr;
                    if(leftr != null){
                        r.left = leftr;
                        leftr.par = r;
                    }
                    else
                        r.left = null;
                }
                else{
                    parent.right = leftptr;
                    leftptr.par = parent;
                    leftptr.right = r;
                    r.par = leftptr;
                    if(leftr != null){
                        r.left = leftr;
                        leftr.par = r;
                    }
                    else
                        r.left = null;
                }
            }
            else{
                leftptr.right = r;
                r.par = leftptr;
                leftptr.par = null;
                this.root = leftptr;
                if(leftr != null){
                    r.left = leftr;
                    leftr.par = r; 
                } 
                else
                    r.left = null;             
            }
    }
    void left_rotate(Node l){
        Node rightptr = l.right;
        Node rightl = l.right.left;
        Node parent = l.par;
        if(parent != null){
            if(l.par.left == l){
                parent.left = rightptr;
                rightptr.par = parent;
                rightptr.left = l;
                l.par = rightptr;
                if(rightl != null){
                    l.right = rightl;
                    rightl.par = l;
                }
                else
                    l.right = null;
            }
            else{
                parent.right = rightptr;
                rightptr.par = parent;
                rightptr.left = l;
                l.par = rightptr;
                if(rightl != null){
                    l.right = rightl;
                    rightl.par = l;
                }
                else
                    l.right = null;
            }
        }
        else{
            rightptr.left = l;
            l.par = rightptr;
            rightptr.par = null;
            this.root = rightptr;
            if(rightl != null){
                l.right = rightl; 
                rightl.par = l;
            }
            else
                l.right = null;              
        }

    }
    void fix_rbtree(Node unbalance){
                Node parent = unbalance.par;
                Node grand_parent = parent.par;
                Node parents_sibling = new Node();
                if(grand_parent != null){
                    if(grand_parent.left == parent){
                        /////////////////////////////////////////////////////////////
                        parents_sibling = grand_parent.right;
                        if(parents_sibling == null){
                            if(parent.left == unbalance){
                                right_rotate(grand_parent);
                                parent.col = false;
                                parent.right.col = true;
                            }
                            else{
                                left_rotate(parent);
                                right_rotate(grand_parent);
                                parent.col = false;
                                parent.right.col = true;
                            }
                        }
                        else{
                            if(parents_sibling.col == true){
                                grand_parent.left.col = false;
                                grand_parent.right.col = false;
                                grand_parent.col = true;
                                if(grand_parent.par == null)
                                    grand_parent.col = false;
                                else{
                                    if(grand_parent.par.col == true)
                                        fix_rbtree(grand_parent);
                                }
                            }
                            else{
                                if(parent.left == unbalance){
                                    right_rotate(grand_parent);
                                    parent.col = false;
                                    parent.right.col = true;
                                }
                                else{
                                    left_rotate(parent);
                                    right_rotate(grand_parent);
                                    parent.col = false;
                                    parent.right.col = true;
                                }
                            }
                        }
                        /////////////////////////////////////////////////////////////
                    }
                    else{
                        /////////////////////////////////////////////////////////////
                        parents_sibling = grand_parent.left;
                        if(parents_sibling == null){
                            if(parent.right == unbalance){
                                left_rotate(grand_parent);
                                parent.col = false;
                                parent.left.col = true;
                            }
                            else{
                                right_rotate(parent);
                                left_rotate(grand_parent);
                                parent.col = false;
                                parent.left.col = true;
                            }
                        }
                        else{
                            if(parents_sibling.col == true){
                                grand_parent.left.col = false;
                                grand_parent.right.col = false;
                                grand_parent.col = true;
                                if(grand_parent.par == null)
                                    grand_parent.col = false;
                                else{
                                    if(grand_parent.par.col == true)
                                        fix_rbtree(grand_parent);
                                }
                            }
                            else{
                                if(parent.right == unbalance){
                                    left_rotate(grand_parent);
                                    parent.col = false;
                                    parent.left.col = true;
                                }
                                else{
                                    right_rotate(parent);
                                    left_rotate(grand_parent);
                                    parent.col = false;
                                    parent.left.col = true;
                                }
                            }
                        }
                        ////////////////////////////////////////////////////////////
                    }
                }

    }
    void insert(int num){
        Node tmp = search(num);
        if(tmp != null)
            System.out.println("Exception");
        else{
            if(this.root == null){
                this.root = new Node(num);
                //System.out.println(root.data+" ");
                return;
            }
            Node temp = this.root;
            while(true){
                if(temp.data > num){
                    if(temp.left == null)
                        break;
                    else
                        temp = temp.left;
                }
                else{
                    if(temp.right == null)
                        break;
                    else
                        temp = temp.right;
                }
            }
            if(temp.data > num){
                temp.left = new Node(num);
                temp.left.col = true;
                temp.left.par = temp;
                //System.out.println(temp.data+" ");
                if(temp.col == true)
                    fix_rbtree(temp.left);
            }
            else{
                temp.right = new Node(num);
                temp.right.col = true;
                temp.right.par = temp;
                // System.out.println(temp.data+" ");
                if(temp.col == true)
                    fix_rbtree(temp.right);
            }
        }
    }
    void inorder(Node r){
            if(r != null){
                inorder(r.left);
                System.out.print(r.data+" ");
                inorder(r.right);
            }
            else
                return;
            System.out.println();
    }
    void print(){
            Node r = this.root;
            inorder(r);
    }
    void dfs(Node r,FileWriter fwrite) throws IOException{
            if(r == null)
                return;
            if(r.left != null){
                fwrite.write(r.data+" -> "+r.left.data+"\n");
                String fillcolor = new String();
                if(r.left.col == false)
                    fillcolor = "black";
                else
                    fillcolor = "red";
                fwrite.write(r.left.data+" [color=black fontcolor=white fillcolor="+fillcolor+"]\n");
                dfs(r.left,fwrite);
            }
            String fillcolor = new String();
            if(r.col == false)
                fillcolor = "black";
            else
                fillcolor = "red";
            fwrite.write(r.data+" [color=black fontcolor=white fillcolor="+fillcolor+"]\n");
            if(r.right != null){
                fwrite.write(r.data+" -> "+r.right.data+"\n");
                if(r.right.col == false)
                    fillcolor = "black";
                else
                    fillcolor = "red";
                fwrite.write(r.right.data+" [color=black fontcolor=white fillcolor="+fillcolor+"]\n");
                dfs(r.right,fwrite);
            }
            
    }
    void printrbtree() throws IOException{
            Node r = this.root;
            File graphf = new File("rbtree.dot");
            graphf.createNewFile();
            FileWriter fwrite = new FileWriter(graphf);
            fwrite.write("digraph dr{ \nnode [style=filled]\n");
            dfs(r,fwrite);
            fwrite.write("}\n");
            fwrite.close();
    }
};


class rbtree{

    public static void main(String[] args) throws IOException{
            myrbtree rbtree = new myrbtree();
            rbtree.insert(1);
            rbtree.insert(2);
            rbtree.insert(3);
            // System.out.println(rbtree.root.data+" "+rbtree.root.col);
            // System.out.println(rbtree.root.left.data+" "+rbtree.root.left.col);
            // System.out.println(rbtree.root.right.data+" "+rbtree.root.right.col);
            // System.out.println(rbtree.root.par);
            // System.out.println(rbtree.root.right.right+" "+rbtree.root.right.left+" "+rbtree.root.right.par.data);
            // System.out.println(rbtree.root.left.right+" "+rbtree.root.left.left+" "+rbtree.root.left.par.data);
            // rbtree.insert(4);
            // rbtree.insert(5);
            // rbtree.insert(6);
            // rbtree.insert(7);
            // rbtree.insert(8);
            // rbtree.print();
            //System.out.println(rbtree.root.data);
            // rbtree.insert(9);
            // rbtree.insert(10);
            // rbtree.insert(11);
            // rbtree.insert(12);
            // rbtree.insert(13);
            // rbtree.insert(14);
            // rbtree.insert(15);
            // rbtree.insert(16);
            // rbtree.insert(17);
            // rbtree.insert(18);
            // rbtree.insert(18);
            // // rbtree.insert(19);
            // rbtree.insert(20);
            // rbtree.insert(21);
            // rbtree.insert(22);
            // rbtree.insert(23);
            // rbtree.insert(24);
            // rbtree.insert(25);
            // rbtree.insert(26);
            // rbtree.insert(27);
            // rbtree.insert(28);
            // rbtree.insert(29);
            // System.out.println(rbtree.root.data);
            rbtree.printrbtree();
            rbtree.print();
    }
}

/*  Case : A
            Parent of ptr is left child
            of Grand-parent of ptr */
    /* Case : 1
            The uncle of ptr is also red
            Only Recolouring required */
    /* Case : 2
                ptr is right child of its parent
                Left-rotation required */
    /* Case : 3
                ptr is left child of its parent
                Right-rotation required */
/* Case : B
           Parent of ptr is right child
           of Grand-parent of ptr */
    /*  Case : 1
            The uncle of ptr is also red
            Only Recolouring required */
    /* Case : 2
                ptr is left child of its parent
                Right-rotation required */
    /* Case : 3
                ptr is right child of its parent
                Left-rotation required */