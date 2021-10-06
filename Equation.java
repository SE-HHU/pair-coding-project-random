import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

/**
 * this class is used to restore numerator and denominator.
 */
class restoreunit{
    private String numerator;
    private String denominator=1+"";
    public int restore=0;
    public restoreunit() {

    }
    public void setnumerator(String numerator){
        this.numerator=numerator;
    }
    public void setdenominator(String denominator){
        this.denominator=denominator;
    }
    public String getdenominator(){
        return denominator;
    }
    public String getnumerator(){
        return numerator;
    }
}

/**
 * This class is used to represent an expression that can be compared to another expression to see if it is repeated.
 * @author mentos
 */
public class Equation{
    Stack<String> stack; // a stack is used to storing operator symbols in method "toReversePolishNotation" and "getOP".
    ArrayList<String> e = new ArrayList<String>(); // store an expression with e.
    ArrayList<String> suffixExpression = new ArrayList<String>(); // store suffix expression of e with suffixExpression.
    String StrE; // store expression as a String.
    String op;  // operator symbol.
    String num1; // number before op in an operation.
    String num2; // number after op in an operation.
    int index; // index of e or suffixExpression.

    /**
     *construct an instance object from a String.
     * @param  equation:a string expression.
     */
    public Equation(String equation){
        this.toArrayList(equation);
        this.suffixExpression = toReversePolishNotation(this.e);
        stack = new Stack<String>();
        op = "";
        num1 = "";
        num2 = "";
        index = 0;
        this.StrE = equation;
    }

    public Equation(ArrayList<String> e){
        this.e = e;
        this.suffixExpression = toReversePolishNotation(e);
        stack = new Stack<String>();
        op = "";
        num1 = "";
        num2 = "";
        index = 0;
        StrE = "";
    }

    /**
     * construct an expression randomly.
     * @param max:max operand in the expression.
     * @param judge:whether have bracket in the expression.
     */
    public Equation(int max, boolean judge){
        stack = new Stack<String>();
        this.e =  getQ(max,judge);
        this.suffixExpression = toReversePolishNotation(this.e);
        op = "";
        num1 = "";
        num2 = "";
        index = 0;
        this.StrE = "";
    }

    /**
     * randomly create an ArrayList which contains expression.
     * @param max:max operand in the expression.
     * @param judge:whether have bracket in the expression.
     * @return an ArrayList which contains expression.
     */
     public static ArrayList<String> getQ(int max,boolean judge){
         ArrayList<String> myList=new ArrayList<String>();
         Random rd=new Random(); // construct a instance object of Random.
         int charcount=rd.nextInt(3)+1; // generate a random number used to represent number of op.
         int mirror=charcount;
         while(charcount>0) {
             myList.add(String.valueOf(rd.nextInt(max)));
             switch(rd.nextInt(4))  // generate a random number used to represent op.
             {
                 case 0:myList.add("+");charcount--;break;
                 case 1:myList.add("-");charcount--;break;
                 case 2:myList.add("*");charcount--;break;
                 case 3:myList.add("/");charcount--;break;
             }
         }
         myList.add(String.valueOf(rd.nextInt(max)));
         if(judge){ // whether have brackets in expression.
             if(mirror==1){ // if expression has one op.
               return myList;
             }else if(mirror==2){ // if expression has two ops.
                 switch(rd.nextInt(2)){ // randomly choose a place to insert brackets.
                     case 0:myList.add(0,"(");myList.add(4,")");break;
                     case 1:myList.add(2,"(");myList.add(6,")");break;
                 }
             }else if(mirror==3){ // if expression has three ops.
                 switch(rd.nextInt(5)){ // randomly choose a place to insert brackets.
                     case 0:myList.add(0,"(");myList.add(4,")");break;
                     case 1:myList.add(0,"(");myList.add(6,")");break;
                     case 2:myList.add(2,"(");myList.add(6,")");break;
                     case 3:myList.add(2,"(");myList.add(8,")");break;
                     case 4:myList.add(4,"(");myList.add(8,")");break;
                 }
             }
         }
         return myList;
     }

    /**
     * this method is used to compare the repetition with another expression by their suffixExpression.
     * @param equation :another expression.
     * @return true or false to judge whether two expressions are repeated.
     */
    public boolean check(Equation equation) {
        if(this.suffixExpression.size() != equation.suffixExpression.size()){ // Compare the lengths of the two suffixExpressions.
            return false;
        }
        //compare both ops of those two suffixExpressions. when they are not same, return false.
        else{
            while(index<suffixExpression.size()){
                this.op = getOp(); // get one expression's op, and push it's operands into stack.
                equation.op = equation.getOp(); // get another expression's op, and push it's operands into stack.
                if(!(this.op.equals(equation.op))){
                    return false;
                }
                // if their op are same, popping two numbers before op out of their stacks.
                else{
                	try {
                		this.num2 = stack.pop();
                        this.num1 = stack.pop();
                        equation.num2 = equation.stack.pop();
                        equation.num1 = equation.stack.pop();
                	}catch(Exception e){
                		return false;
                	}
                    

                    //if the same ops are "+" or "*", compare their operands without sorting.
                    if(op.equals("+") || op.equals("*")){
                        //if both two operands are different, return false.
                        if(!(((this.num1.equals(equation.num1)) && (this.num2.equals(equation.num2))) ||
                                ((this.num1.equals(equation.num2)) && (this.num2.equals(equation.num1))))){
                            return false;
                        }
                        // if both two operands are same, calculate operands, and the answers are pushed in their stacks.
                        else{
                            int sum1 = cal(num1, num2, op);
                            int sum2 = cal(equation.num1, equation.num2, equation.op);
                            this.stack.push(String.valueOf(sum1));
                            equation.stack.push(String.valueOf(sum2));
                        }

                    }
                    // if the same ops are "-" or "/", compare their operands with sorting.
                    else{
                        // if their operands are different, return false.
                        if(!((num1.equals(equation.num1)) && (num2.equals(equation.num2)))){
                            return false;
                        }
                        // if their operands are same, calculate operands, and the answers are pushed in their stacks.
                        else{
                            int sum1 = cal(num1, num2, op);
                            int sum2 = cal(equation.num1, equation.num2, equation.op);
                            this.stack.push(String.valueOf(sum1));
                            equation.stack.push(String.valueOf(sum2));
                        }
                    }
                }
            }
            return true; // if all repetition conditions are eliminated, return true.
        }
    }

    /**
     *this method turns the string expression into an ArrayList and stores this.e.
     * @param equation:a string expression.
     */
    public void toArrayList(String equation){
        String num = ""; // an operand of an operator symbol.
        // loop and turn string equation into ArrayList.
        for(int i=0; i<equation.length(); i++){
            String op = String.valueOf(equation.charAt(i)); // get an element of equation.
            // if op is equal to "+" or "-" or "*" or "/" or "(" or ")".
            if(op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/") || op.equals("(") || op.equals(")"))
            {
                if(num != ""){
                    this.e.add(num);
                }
                this.e.add(op); // add op into this.e.
                num = "";
            }
            // if op is number, num = num + op.
            else{
                num += op;
            }
            // when it gets to the end of equation, add final num into this.e.
            if ((i == equation.length()-1) && (num != "")){
                this.e.add(num);
            }
        }
    }

    /**
     * get priority of an operator symbol.
     * @param character:"+"or "-" or "*" or "/".
     * @return an "int number" represent priority.
     */
    public static int getPriority(String character) {
        int res=0;//to record the priority.
        switch(character.substring(0)) {
            case "+":res=1;break;
            case "-":res=1;break;
            case "*":res=2;break;
            case "/":res=2;break;
        }
        return res;
    }

    /**
     *to transform an infix expression into suffix expression.
     * @param ls is an ArrayList which stores an expression.
     * @return an ArrayList which stores an suffix expression.
     */
    public static ArrayList<String> toReversePolishNotation(ArrayList<String>ls){
        Stack<String>s1=new Stack<String>(); //build a stack and a List to restore the reverse suffix expression.
        ArrayList<String>s2=new ArrayList<String>();
        //traversing the List.
        for(String item:ls) {
            if(item.matches("\\d+")) { //if item is number, push it into s2.
                s2.add(item);
            }else if(item.equals("(")) { //if item is "(", push it into s1.
                s1.push(item);
            }else if(item.equals(")")) {
                while(!s1.peek().equals("(")) {
                    s2.add(s1.pop());
                }
                s1.pop();
            }else  {
                while(s1.size()!=0&&getPriority(item)<=getPriority(s1.peek())) {
                    s2.add(s1.pop());
                }
                s1.push(item);
            }
        }
        while(s1.size()!=0) {
            s2.add(s1.pop());
        }
        return s2;
    }

    /**
     * symbol calculator to calculate two operands.
     * @param num1:number before op.
     * @param num2:number after op.
     * @param op:operator symbol.
     * @return result of calculation.
     */
    public int cal(String num1, String num2,String op){
        int n1 = Integer.parseInt(num1);
        int n2 = Integer.parseInt(num2);

        switch (op) {
            case "+":return n1 + n2;
            case "-":return n1 - n2;
            case "*":return n1 * n2;
            case "/":return n1 / n2;
            default:return -999;
        }
    }

    /**
     * find the latest op in this.suffixExpression, and push it's operands into this.stack.
     * @return the latest op.
     */
    public String getOp(){
        // loop to find op. it stops when found out op.
        while(true){
            this.op = "";
            this.op = suffixExpression.get(index); // get element of this.suffixExpression with index.
            if (op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/")){
                index++;
                return op;
            }
            // if op is number, push number into this.stack.
            else{
                stack.push(op);
                index++;
            }
        }
    }
}


