class mystack{
            
        private
        int size;
        int arr[];
        int arrsize;
        public
        
        mystack(){
            size = 0;
            arr = new int[10];
            arrsize = 10;
        }
        int size(){
            return this.size;
        }
        void push(int num){
            if(this.size == this.arrsize){
                this.arrsize = 2*(this.arrsize);
                int arr1[] = new int[this.arrsize];
                int i;
                for(i=0; i<this.size; i++)
                    arr1[i] = this.arr[i];
                arr1[i] = num;
                this.arr = arr1;
                this.size++;
            }
            else{
                this.arr[this.size] = num;
                this.size++;
            }
        }
        boolean empty(){
                if(this.size == 0)
                   return true;
                return false;
        }
        void pop(){
            if(this.empty())
                System.out.println("stack is empty :(");
            else{
                size--;
            }
        }
        int top(){
            if(this.empty())
               return -1;
            else{
                return this.arr[this.size-1];
            }
        }
        void printstack(){
            int i = this.size;
            while(i>1){
                System.out.print("["+this.top()+"] ");
                this.pop();
                i--;
            }
            System.out.println("["+this.top()+"]");
            this.pop();
        }

    };
    public class stack {

    public static void main(String[] args){
        System.out.println("my stack :)");
        mystack stack = new mystack();
        stack.push(10);
        stack.push(20);
        stack.pop();
        stack.push(40);
        stack.pop();
        stack.push(50);
        stack.pop();
        stack.push(60);
        System.out.println(stack.empty());
        System.out.println(stack.size());
        System.out.println(stack.top());
        stack.printstack();
    }
}
