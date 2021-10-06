import java.io.*;
import java.util.*;

public class selfcalculator {
	public static void main(String[] args) throws Exception {
		Scanner in=new Scanner(System.in);
		List<ArrayList<String>> copyList=new ArrayList<ArrayList<String>>();
		List<restoreunit> answers=new ArrayList<restoreunit>();
		System.out.println("Please select whether you test the calculation");
		String input = "";
		boolean ifcheck=in.nextBoolean();
		if(ifcheck) {
			System.out.println("Please enter the formula address");
			input=in.next();
			List<ArrayList<String>> inputList=new ArrayList<ArrayList<String>>();  //to restore the given Q.txt File
			List<ArrayList<String>> outputList=new ArrayList<ArrayList<String>>(); //to restore the given A.txt File
			List<restoreunit> correctAnswerList=new ArrayList<restoreunit>();  //to restore the correct answers
			inputList=getFile(input);
			for(ArrayList<String> item:inputList) {
				restoreunit correctanswer=new restoreunit();
				correctanswer=calculate(Equation.toReversePolishNotation(item));
				correctAnswerList.add(correctanswer);
			}
			System.out.println("Please enter the answer address");
			String output=in.next();
			List<restoreunit> answerList=new ArrayList<restoreunit>();  //to restore the answer
			outputList=getFile(output);
			answerList=torestoreunit(outputList);
			int[] wrongnumarr=new int[answerList.size()+1];    //to restore the wrong question number
			int wrongnum=0;                                    //to restore the wrong number
			int[] rightnumarr=new int[answerList.size()+1];    //to restore the wrong question number
			int rightnum=0;                                    //to restore the wrong number	
			for(int i3=0;i3<answerList.size();i3++) {
				if(!isright(answerList.get(i3),correctAnswerList.get(i3))) {
					wrongnumarr[wrongnum++]=i3+1;
				}else {
					rightnumarr[rightnum++]=i3+1;
				}
			}
		 	try {
		 		BufferedWriter c=new BufferedWriter(new FileWriter("Grade.txt")); //output
		 		c.write("Right");
	 			c.write(":");
	 			c.write(rightnum+" ");
	 			c.write("[");
		 		for(int i4=0;rightnumarr[i4]!=0;i4++) {
					c.write(rightnumarr[i4]+" ");
				}
		 		c.write("]");
		 		c.newLine();
		 		c.write("Wrong");
	 			c.write(":");
	 			c.write(wrongnum+" ");
	 			c.write("[");
		 		for(int i4=0;wrongnumarr[i4]!=0;i4++) {
					c.write(wrongnumarr[i4]+" ");
				}
		 		c.write("]");
		 		c.newLine();
		 		c.close();
		 		Checking.checkReverse(input);
		     }
		 	
		 	 catch(IOException ex){
		 		 ex.printStackTrace();		 
		 	 }
			}	
		System.out.println("Please enter the number of expressions you want to produce");
		int num=in.nextInt();
		System.out.println("Please enter the maximum value you want to generate");
		int max=in.nextInt();
		System.out.println("Decide if you want to produce a bracketed expression");
		boolean judge=in.nextBoolean();
		ArrayList<String> test=new ArrayList<String>();
		for(int i=0;i<num;i++) {
			test=Equation.getQ(max,judge);
			boolean ifrepeat=true;
			Equation e1=new Equation(test);
			for(int i5=0;i5<copyList.size();i5++) {
				Equation e2=new Equation(copyList.get(i5));
				if(!e1.check(e2)) {
					ifrepeat=false; //if has repeat,isrepeat=false;
				}
			}
			boolean ifexceed=true;
             if((calculate(Equation.toReversePolishNotation(test)).getdenominator().equals("1")&&Integer.parseInt(calculate(Equation.toReversePolishNotation(test)).getnumerator())>max)||Integer.parseInt(calculate(Equation.toReversePolishNotation(test)).getnumerator())<0||Integer.parseInt(calculate(Equation.toReversePolishNotation(test)).getdenominator())<0) {
            	 ifexceed=false;
             }
			//judge if extend the range
             if(!ifrepeat) {
            	 test=Equation.getQ(max,judge);
             }
			while(!ifexceed)
			{ test=Equation.getQ(max,judge);
			if((calculate(Equation.toReversePolishNotation(test)).getdenominator().equals("1")&&Integer.parseInt(calculate(Equation.toReversePolishNotation(test)).getnumerator())<max)&&Integer.parseInt(calculate(Equation.toReversePolishNotation(test)).getnumerator())>0&&Integer.parseInt(calculate(Equation.toReversePolishNotation(test)).getdenominator())>0) {
           	 ifexceed=true;}    
			}
		    // System.out.println("The formulas for");
		     for(int index=0;index<test.size();index++) {
		    	 System.out.print(test.get(index));
		     }
		     System.out.print("=");
		     System.out.println(" ");
		     restoreunit rightAnswer=new restoreunit();
		     rightAnswer=calculate(Equation.toReversePolishNotation(test));
		     answers.add(rightAnswer);
		    // System.out.println(rightAnswer.getnumerator());
		     copyList.add((ArrayList<String>) test);
		     //System.out.println("Please enter your results");
		     //restoreunit myAnswer=new restoreunit();
		     //System.out.println("Molecules for");
		     //myAnswer.setnumerator(in.next());
		     //System.out.println("The denominator for");
		     //myAnswer.setdenominator(in.next());
	
	try {
	 		BufferedWriter bw=new BufferedWriter(new FileWriter("Excercises.txt")); //Write the topic to TXT file
	 		for(int m=0;m<copyList.size();m++) {
	 			bw.append(String.valueOf((m+1)));
	 			bw.append(".");
	 			ArrayList<String> temptList=copyList.get(m);
	 		for(int t=0;t<temptList.size();t++)
	 		 {
	 			 bw.write(temptList.get(t));
	 		 }
	 		     bw.write("=");
	 			 bw.newLine();
	 	 }
	 		bw.close();
	     }
	 	 catch(IOException ex){
	 		 ex.printStackTrace();		 
	 	 }
	 	 try {
	 		 BufferedWriter b=new BufferedWriter(new FileWriter("Answers.txt")); //Write the answer to TXT file
	 		 for(int i1=0;i1<answers.size();i1++) {
	 			
	 			b.append(String.valueOf((i1+1)));
	 			b.append(".");
	 			if(answers.get(i1).getdenominator().equals("1")) {
	 				 b.write(answers.get(i1).getnumerator());
	 				b.newLine();
	 			}else if(answers.get(i1).restore!=0) {
	 				b.append(String.valueOf(answers.get(i1).restore));
	 				b.write(answers.get(i1).getnumerator());
	 				 b.write("/");
	 				 b.write(answers.get(i1).getdenominator());
	 				 b.newLine();
	 			}else {
	 				 b.write(answers.get(i1).getnumerator());
	 				 b.write("/");
	 				 b.write(answers.get(i1).getdenominator());
	 				 b.newLine();
	 			 }
	 			}
	 	 
	 		b.close();
	 	 }
	 	 catch(IOException ex){
	 		 ex.printStackTrace();		 
	 	 }
	 	 }
	 
	}
	public static List<restoreunit> torestoreunit(List<ArrayList<String>> outputList) {
		List<restoreunit> myunit=new ArrayList<restoreunit>();
		for(ArrayList<String> item:outputList) {
			restoreunit littleunit=new restoreunit();
			littleunit.setnumerator(item.get(0));
			littleunit.setdenominator(item.get(2));
	
			myunit.add(littleunit);
		}
		return myunit;
	}
	public static List<ArrayList<String>> getFile(String abs){
		List<ArrayList<String>> myList =new ArrayList<ArrayList<String>>();
	
		try {
			BufferedReader a=new BufferedReader(new FileReader(abs)); //Read the title into the file
			String line=null;
			while((line=a.readLine())!=null) {
			 char[] ch = line.toCharArray();
				
				ArrayList<String> everyList=new ArrayList<String>();
				for(int index1=0;index1<ch.length;index1++) {
					everyList.add(String.valueOf(ch[index1]));
				}
				int index=0;
				while(index<everyList.size()) {
					if(everyList.get(index).equals("."))break;
					index++;
				}

			while(index>=0) {
					everyList.remove(0);
					index--;
				}
			   myList.add(everyList);
			}
			a.close();
		}catch(IOException ex){
	 		 ex.printStackTrace();		 
	 	 }
		
		return myList;
	}

	public static boolean isright(restoreunit num1,restoreunit num2) {
		boolean judge=true;
		if(!num1.getdenominator().equals(num2.getdenominator())) {
			judge=false;
		}
		if(!num1.getnumerator().equals(num2.getnumerator())) {
			judge=false;
		}
		return judge;
	}
	public static boolean Contradiction(int num2,int num1) { 
		int tempt=num2/num1;
				return num1*tempt==num2;
	}

	public static int gcd(restoreunit s1,restoreunit s2) {
		int a=Integer.parseInt(s1.getdenominator());
		int b=Integer.parseInt(s2.getdenominator());
		int ma=a;
		int mb=b;
		if(a<b) {
			int tmp=a;
			a=b;
			b=tmp;
		}
		while(b!=0) {
			int tempt=a;
			a=b;
			b=tempt%b;
		}
		return ma*mb/a;
	}
	public static restoreunit calculate(List<String> suffixexpression) throws Exception {
		int res=0;//restore the final result
		int den=1;//restore the final result
		Stack<restoreunit> stack=new Stack<restoreunit>();
	   for(String element:suffixexpression) {	
		   int tempt=0;
		   restoreunit unit=new restoreunit();
		   unit.setdenominator("1");
		   if(element.matches("\\d+")) {
			   unit.setnumerator(element);
			  stack.push(unit);

		   }else {
			   restoreunit num2=stack.pop();
			   restoreunit num1=stack.pop();
			   num2.setnumerator(String.valueOf(Integer.parseInt(num2.getnumerator())+Integer.parseInt(num2.getdenominator())*(num2.restore)));
			   num1.setnumerator(String.valueOf(Integer.parseInt(num1.getnumerator())+Integer.parseInt(num1.getdenominator())*(num1.restore)));
			 if(gcd(num1,num2)!=1) {
            int isMax=gcd(num1,num2);
            int m1=isMax/Integer.parseInt(num1.getdenominator());
            num1.setdenominator(String.valueOf(isMax));
            num1.setnumerator(String.valueOf(Integer.parseInt(num1.getnumerator())*m1));
            int m2=isMax/Integer.parseInt(num2.getdenominator());
            num2.setdenominator(String.valueOf(isMax));
            num2.setnumerator(String.valueOf(Integer.parseInt(num2.getnumerator())*m2));
			 }
			   if(element.equals("+")) {
				   res=Integer.parseInt(num1.getnumerator())+Integer.parseInt(num2.getnumerator());
				   den=Integer.parseInt(num1.getdenominator());
			   }else  if(element.equals("-")) {
				   res=Integer.parseInt(num1.getnumerator())-Integer.parseInt(num2.getnumerator());
				   den=Integer.parseInt(num1.getdenominator());
			   }else if(element.equals("*")) {
				   res=Integer.parseInt(num1.getnumerator())*Integer.parseInt(num2.getnumerator());
				   den=Integer.parseInt(num1.getdenominator())*Integer.parseInt(num2.getdenominator());
			   }else if(element.equals("/")) {
				   if(Integer.parseInt(num2.getnumerator())==0) {
				try {
					throw new ArithmeticException();
				}catch(Exception e) {
					System.out.println("��������Ϊ0");
				}
				   }
				   if(Contradiction(Integer.parseInt(num1.getnumerator()),Integer.parseInt(num2.getnumerator()))) {
					   res=Integer.parseInt(num1.getnumerator())/Integer.parseInt(num2.getnumerator());	  
				   }else {
					   tempt=Integer.parseInt(num1.getnumerator())/Integer.parseInt(num2.getnumerator());
					  res=Integer.parseInt(num1.getnumerator())%Integer.parseInt(num2.getnumerator());
					  den=Integer.parseInt(num2.getnumerator());
				
			   }
				   }
			   unit.restore=tempt;
			   unit.setnumerator(String.valueOf(res));
			   unit.setdenominator(String.valueOf(den));
			   stack.push(unit);  
		   }
	   }
		return stack.pop();
		}
}

