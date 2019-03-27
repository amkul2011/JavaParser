package com.acn;


/*
function::= B{statemt}
statemt  ::=  assnmt | ifstmt | loop | read | output|funcall
assnmt   ::=  ident ~ exprsn ;
ifstmt   ::=  I comprsn @ {statemt} [% {statemt}] &
loop     ::= W comprsn L {statemt} T
read     ::= R ident{,ident};
output   ::= O ident{,ident};
funcall  ::= C function E
 
comprsn  ::=  ( oprnd opratr oprnd )
exprsn   ::=  factor {+ factor}
factor   ::=  oprnd {* oprnd}
oprnd    ::=  integer | ident | ( exprsn )
opratr   ::=  < | = | > | ! 
ident    ::=  letter {char}
char     ::=  letter | digit
integer  ::=  digit {digit}
letter   ::=  X | Y | Z
digit    ::=  0 | 1 | 2| 3| 4| 5| 6| 7|








/* Amit Kulkarni
 * Below are the Test cases which include legal and illegal string of tokens
 * Legal cases:-
 * BX~1;$
 * BOX1;$
 * BCBE$
 * BI(6>5)@X~1;%I(1<2)@&&$
 * BRX,Y,Z;$
 * BZ~((1));$
 * BOX123;$
 * BOX,Y123;$
 * BW(1<2)LX~1;T$
 * BW(1<2)LCBET$
 * BW(1<2)LTW(1<2)LTW(1<2)LTW(1<2)LT$
 * 
 * Illegal cases:-
 * BI$
 * BCB$
 * BCBI(112)$
 * BCBCBE$
 * BW(2<4)LRT$
 * BOXTR$
 * BOX1,2,3;$
 * BOX,Y,Z,1,2,3;$
 * BY~(1;$
 * BW(1<2)LW(2<3)LW(4>3)LT$
 * BW(2>4)LI(5<6)@B%&T$
 * */

import java.util.Scanner;

public class Recognizer {
	
    static int index=0;
	static int errorflag=0;
	static String ipString;
	
	 
	private char token(){
		return(ipString.charAt(index));
	 }
	  
	  
	  private void match(char T)
	  { 
		 // System.out.println(index);
	  if (T == token())
		  advancePtr();
	  else{
		//  System.out.println(token()+" "+T);
		  error(); 
		 }
	  }
	  
	 //Reference from Prof. Scott Gordon's method 
	  private void advancePtr()
	  { 
		  if (index < (ipString.length()-1)) 
	      index++; 
	  }

	 
	  
	  private void error(){
		  System.out.println("Error at position"+index);
		 // System.out.println("Token"+token());
		  errorflag=1;
		  advancePtr();
		 }

	
	  private void function(){
		if((token()=='B')){
			match('B');
		//System.out.println("HELLO"+token());
		while((token()=='X')||(token()=='Y')||(token()=='Z')|| (token()=='I') || (token()=='W') || 
			  (token()=='R')|| (token()=='O')|| (token()=='C')){
			statement();
		  }
		}
		
		else
			error();
		
	}
	
	
	
	private void statement(){
		
		if((token()=='X') || (token()=='Y')|| (token()=='Z')){
			
			assnmt();
		}
		
		else if (token()=='I')
			ifstmt();
		
		else if ((token()=='W'))
			loop();
		
		
		else if((token()=='R'))
			read();
		
		
		else if((token()=='O'))
			output();
		
		
		else if((token()=='C'))
				funcall();
		
		else
			error();
		
	}
	
	
	private void assnmt() {
		//System.out.println(token());
		if((token()=='X')||(token()=='Y')||(token()=='Z')){
			ident();
		if((token()=='~'))
			match('~');
		if((token()=='0')||(token()=='1')||(token()=='2')||(token()=='3')||(token()=='4')||
			(token()=='5')||(token()=='6')||(token()=='7')||(token()=='X')||(token()=='Y')||(token()=='Z')||(token()=='(')){
			exprsn();
			match(';');
		}
		else 
			error();
		}
		else
			error();
			
		
	}
	
	private void loop() {
	   
       match('W');
       if((token()=='(')){
    	   comprsn();
       match('L');
       while((token()=='X')||(token()=='Y')||(token()=='Z')|| (token()=='I') || (token()=='W') || 
 			  (token()=='R')|| (token()=='O')|| (token()=='C')){
 			statement();
 		  }
       
       match('T');
       }
       else
    	   error();
       
	}
	
	private void read() {
		
		match('R');
		
		if((token()=='X')||(token()=='Y')||(token()=='Z'))
		{
		ident();	
		
		while(token()==','){
		  match(',');
		  ident();
		}
		
		match(';');
	}
		else
			 error();
	}
	
	
	private void ifstmt() {
		
		match('I');
		if(token()=='('){
			comprsn();
		if(token()=='@')
		match('@');
	 while((token()=='X')||(token()=='Y')||(token()=='Z')||(token()=='I')
			 ||(token()=='R')||(token()=='O')||(token()=='C')||(token()=='W'))	{
		  statement();
		 }
	 if((token()=='%')){
		 match('%');
		 while((token()=='X')||(token()=='Y')||(token()=='Z')||(token()=='I')
				 ||(token()=='R')||(token()=='O')||(token()=='C')||(token()=='W')){
			 statement();
		 }	 
		 
		}
	 match('&');
		}
		else error();
}

	
	private void output() {
		
        match('O');
		
		if((token()=='X')||(token()=='Y')||(token()=='Z')){
		ident();	
		
		while(token()==','){
	      match(',');
		  ident();
		}
		match(';');
	}
		else
			 error();
		
}
	
	private void funcall() {
		
		match('C');
		
		if((token()=='B')){
			function();
		
			match('E');
		}
		else
			error();
		
	}
	
	private void comprsn() {
		
		match('(');
		
		if((token()=='0')||(token()=='1')||(token()=='2')||(token()=='3')||
		   (token()=='4') || (token()=='5') ||(token()=='6')||(token()=='7')||
		   (token()=='X')||(token()=='Y')||(token()=='Z')||(token()=='(')){
			oprnd();
			if((token()=='<')|| (token()=='>')|| (token()=='!')||(token()=='=')){
				opratr();
				oprnd();
				match(')');
			}
			else
				error();
		}
		else
			error();
		
	}
	
	
	private void exprsn() {
		// TODO Auto-generated method stub
		if((token()=='0')||(token()=='1')||(token()=='2')||(token()=='3')||(token()=='4')||
		(token()=='5')||(token()=='6')||(token()=='7')||(token()=='X')||
		(token()=='Y')||(token()=='Z')||(token()=='(')){
			factor();
			while(token()=='+'){
				match('+');
				factor();
			}
		}
		
		else
			error();
	}

	
	private void factor() {
		if((token()=='0')||(token()=='1')||(token()=='2')||(token()=='3')||(token()=='4')||
				(token()=='5')||(token()=='6')||(token()=='7')||(token()=='X')||
				(token()=='Y')||(token()=='Z')||(token()=='(')){
					oprnd();
					while(token()=='*'){
						match('*');
						oprnd();
					}
				}
				else
					error();
	 }
	
	
	private void oprnd() {
		if((token()=='0')||(token()=='1')||(token()=='2')||(token()=='3')||
		   (token()=='4')||(token()=='5')||(token()=='6')||(token()=='7'))
			integer();
		else if((token()=='X')||(token()=='Y')||(token()=='Z'))
			ident();
		else if((token()=='(')){
			match('(');
			if((token()=='0')||(token()=='1')||(token()=='2')||(token()=='3')||(token()=='4')||
					(token()=='5')||(token()=='6')||(token()=='7')||(token()=='X')||
					(token()=='Y')||(token()=='Z')||(token()=='('))
				exprsn();
			match(')');
			}
		else
			error();
		
	}
	
	private void opratr() {
		
		if((token()=='<'))
			match('<');
		else if((token()=='>'))
			match('>');
		else if((token()=='='))
			match('=');
		else if((token()=='!'))
			match('!');
		else
			error();
	}
	
	private void ident() {
		
		if((token()=='X')||(token()=='Y')||(token()=='Z')){
			letter();
			
			while((token()=='0')|| (token()=='1')|| (token()=='2')|| (token()=='3')|| (token()=='4')|| 
					(token()=='5')|| (token()=='6')|| (token()=='7')||(token()=='X')||(token()=='Y')||(token()=='Z')){
				charac();
				
			}
			
		}
		else
			error();
		
	}
	
	

	private void charac() {
		if((token()=='X')||(token()=='Y')||(token()=='Z'))
		 letter();
		else if((token()=='0')|| (token()=='1')|| (token()=='2')|| (token()=='3')|| (token()=='4')|| 
				(token()=='5')|| (token()=='6')|| (token()=='7'))
			digit();
		else
			error();
		
		
	}
	
	private void integer() {
		
		if((token()=='0')|| (token()=='1')|| (token()=='2')|| (token()=='3')|| (token()=='4')|| 
				(token()=='5')|| (token()=='6')|| (token()=='7')){
			digit();
			while(((token()=='0')|| (token()=='1')|| (token()=='2')|| (token()=='3')|| (token()=='4')|| 
					(token()=='5')|| (token()=='6')|| (token()=='7'))){
				digit();
			}
		}
		else
			error();
	}

	
	private void letter() {
		
		if((token()=='X'))
			match('X');
		else if((token()=='Y'))
			match('Y');
		else if((token()=='Z'))
			match('Z');
		else
			error();

		
	}
	
	private void digit() {
		if((token()=='0'))
			match('0');

		else if((token()=='1'))
			match('1');

		else if((token()=='2'))
			match('2');

		else if((token()=='3'))
			match('3');

		else if((token()=='4'))
			match('4');

		else if((token()=='5'))
			match('5');

		else if((token()=='6'))
			match('6');
		
		else if((token()=='7'))
			match('7');
		else
			error();
		
	}

	private void driver(){
		 function();
		 match('$');

		    if (errorflag == 0)
		      System.out.println("legal." + "\n");
		    else
		      System.out.println("errors found." + "\n");
	}

	

	public static void main(String[] args) {
		Recognizer rec =new Recognizer();
		Scanner scan= new Scanner(System.in);
		
		System.out.println("Enter String");
		ipString=scan.nextLine();
		
		rec.driver();
	    scan.close();

	}

}
