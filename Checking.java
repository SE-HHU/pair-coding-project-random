import java.io.*;
import java.util.ArrayList;

/**
 * this class is used to check whether exist repetitive expressions in a supplied file which suffix is ".txt".
 * the only one available method is "public static void checkReverse(String ePath)";
 * @author mentos
 */
public class Checking {
    ArrayList<Equation> equations; // store expressions from file.
    ArrayList<String> grade; // store repetitive expressions and their number.

    /**
     * private; initialize variable.
     */
    private Checking(){
        equations = new ArrayList<Equation>();
        grade = new ArrayList<String>();
    }

    /**
     * public, static method, used to check whether exist repetitive expressions in a file which path is ePath.
     * expression example in file: 10,3+2+4
     * @param ePath:the path of file which suffix is ".txt".
     */
    public static void checkReverse(String ePath){
        Checking checking = new Checking();
        checking.load(ePath);
        checking.check();
        checking.print();
    }

    /**
     * load expressions in this.equations from file which path is ePath.
     * expression example: 1,3+2+5
     * @param ePath:the file path.
     */
    private void load(String ePath){
        BufferedReader bufferedReader = null;
        try {
             bufferedReader = new BufferedReader(new FileReader(ePath)); // construct a BufferedReader with ePath.
            try {
                String strEquation = bufferedReader.readLine(); // load the first expression in file.

                // loop to load expressions in file and store them into this.equations.
                while (strEquation != null){
                    int i=0;

                    // find index of "," or ".".
                    for (i = 0; i<strEquation.length(); i++){
                        if(strEquation.charAt(i) == ',' || strEquation.charAt(i) == '.'){
                            i++;
                            break;
                        }
                    }
                    strEquation = strEquation.substring(i);
                    equations.add(new Equation(strEquation)); // new an Equation object and add it into equations.
                    strEquation = bufferedReader.readLine();
                }

                bufferedReader.close(); // close bufferedReader
            }
            catch (IOException e) {
                e.printStackTrace();
                System.out.println("fail in reading exercises");
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("can not open exercises file");
        }
    }

    /**
     * traverse expressions in this.equations and check whether it have repetitive expressions, store them into this.grade.
     * one kind of same expressions in this.grade are divided by an element "stop".
     */
    private void check(){
       // Stack<Equation> stack = new Stack<Equation>();
        //Stack<Integer> numbers = new Stack<Integer>();
        // traverse expressions
        boolean[] flags = new boolean[equations.size()];
        for (int i=0; i<equations.size();i++){
            flags[i] = true;
        }

        for(int i=0;i<equations.size()-1; i++){
            if(flags[i]){
                Equation equation1 = equations.get(i); // get expression which is compared with other expressions.
                boolean flag = false; // judge whether have found a expression same with equation1
                // traverse expressions to find expressions are same with equation1.
                for (int j=i+1;j<equations.size();j++){
                    Equation equation2 = equations.get(j); // get another expression compared with equation1.
                    equation1.index = 0;
                    equation2.index =0;
                    while (!(equation1.stack.empty())){
                        equation1.stack.pop();
                    }
                    while(!(equation2.stack.empty())){
                        equation2.stack.pop();
                    }
                    if(equation1.check(equation2)){ // check whether equation1 are same with equation2.
                        if(!flag){ // if haven't found a same expression with equation1, add equation1 into this.grade with the form of string.
                            grade.add(i+1+","+equation1.StrE); // "i+1" is the number of expression.
                            flag = true;
                        }
                        flags[j] = false;
                        grade.add(j+1+","+equation2.StrE); //"j+1" is the number of expression.
                    }
                }
                // when finish finding repetitive expressions of equation1, add a "stop" if equation1 have same expressions.
                if(!(grade.isEmpty()) && !(grade.get(grade.size()-1).equals("stop"))) {
                    grade.add("stop");
                }
            }
        }

    }


    /**
     *  output those repetitive expressions to file "grade.txt", with correct format;
     */
    public void print(){
        int repeatNum = 0; // repetitive expression's number.
        //if grade is not empty, record the number.
        if(!grade.isEmpty()){
            for(int i = 0; i < grade.size(); i++){
                if(grade.get(i).equals("stop")){
                    repeatNum++;
                }
            }
        }
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter("Grade.txt", true)); // new a BufferedWriter object.
            bufferedWriter.write("Repeat:"+repeatNum+"\n" +
                    "RepeatDetail:\n");
            bufferedWriter.flush();
            //if repeatNum != 0, output repetitive expressions.
            if(repeatNum != 0){
                int num = 1;
                for (int i = 0; i < grade.size(); i++) {
                    boolean flag = true;
                    bufferedWriter.write("(" + num + ")");
                    bufferedWriter.flush();
                    num++;
                    while (flag) {
                        bufferedWriter.write(grade.get(i));
                        bufferedWriter.flush();
                        if (!(grade.get(i + 1).equals("stop"))) {
                            bufferedWriter.write(" Repeat ");
                            bufferedWriter.flush();
                        } else {
                            bufferedWriter.write("\n");
                            bufferedWriter.flush();
                        }
                        flag = !(grade.get(i + 1).equals("stop"));
                        i++;
                    }
                }
            }
        }
            catch (IOException e) {
                e.printStackTrace();
            }
    }
}

