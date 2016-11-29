
import java.io.IOException;
import java.util.HashMap;

class Interp {

   public static void interp(Stm s) {
       HashMap h = new HashMap();
       interpStm(s, h);
   }

   private static HashMap interpStm(Stm s, HashMap h) {
       if (s instanceof CompoundStm) {
           CompoundStm compound  = (CompoundStm) s;
           h = interpStm(compound.stm1, h);
           h = interpStm(compound.stm2, h);
           return h;
       } else if (s instanceof AssignStm) {
           AssignStm asn = (AssignStm) s;
           h.put(asn.id, interpExp(asn.exp, h));
       } else if (s instanceof PrintStm) {
           PrintStm print = (PrintStm) s;
           ExpList e = print.exps;
           while (e instanceof PairExpList) {
               PairExpList pair = (PairExpList) e;
               System.out.print(interpExp(pair.head, h));
               System.out.print(" ");
               e = pair.tail;
           }
           LastExpList l = (LastExpList) e;
           System.out.println(interpExp(l.head, h));
           return h;
       }
       return h;
   }
   
   private static int interpExp(Exp e, HashMap h) {
       if (e instanceof IdExp) {
           IdExp id = (IdExp) e;
           return (int)h.get(id.id);
       } else if (e instanceof NumExp) {
           NumExp num = (NumExp) e;
           return num.num;
       } else if (e instanceof OpExp) {
           OpExp op = (OpExp) e;
           if (op.oper == 1) {
               return interpExp(op.left, h) + interpExp(op.right, h);
           } else if (op.oper == 2) {
               return interpExp(op.left, h) - interpExp(op.right, h); 
           } else if (op.oper == 3) {
               return interpExp(op.left, h) * interpExp(op.right, h);
           } else if (op.oper == 4) {
               return interpExp(op.left, h) / interpExp(op.right, h);
           }
       } else if (e instanceof EseqExp) {
           EseqExp seq = (EseqExp) e;
           h = interpStm(seq.stm, h);
           return interpExp(seq.exp, h);
       }
       return 0;
   }
   //Recursively calls itself to find the max # of args in a print statement
   public static int maxargs(Stm s) {
       
       if (s instanceof CompoundStm) {
           CompoundStm statement = (CompoundStm) s;
           int a = maxargs(statement.stm1);
           int b = maxargs(statement.stm2);
           if (b > a) { return b;}
           else {return a;}
       } else if (s instanceof PrintStm) {
           PrintStm print = (PrintStm) s;
           return stepList(print.exps);
       } else if (s instanceof AssignStm) {
           AssignStm asn = (AssignStm) s;
           if (asn.exp instanceof EseqExp) {
               EseqExp seqExp = (EseqExp) asn.exp;
               return checkSeqExp(seqExp);
           }
       }
       
       return 0;
   }
   
   //Recursively check the EseqExp incase a Print is hiding inside
   private static int checkSeqExp(EseqExp seq) {
       int a = maxargs(seq.stm);
       
       if (seq.exp instanceof EseqExp) {
           int b = checkSeqExp((EseqExp) seq.exp);
           
           if (b > a) { return b;}
           else {return a;}
       } else { return a;}
   }
   
   //Recusrively goes through ExpLists to count arg count and check args for any potential Prints
   private static int stepList(ExpList e) {
       if (e instanceof PairExpList)
       {
           PairExpList pair = (PairExpList) e;
           
           if (pair.head instanceof EseqExp)
           {
               EseqExp seq = (EseqExp) pair.head;
                int a = checkSeqExp(seq);
                int b = 1 + stepList(pair.tail);
                if (b > a) {return b;}
                else {return a;}
           }
           
           return 1 + stepList(pair.tail);
       } else if (e instanceof LastExpList)
       {
           LastExpList last = (LastExpList) e;
           if (last.head instanceof EseqExp)
           {
               EseqExp seq = (EseqExp) last.head;
               if (seq.stm instanceof PrintStm) {
                   int a = maxargs(seq.stm);
                   int b = 1;
                   
                   if (b > a) {return b;}
                   else {return a;}
               }
           }
           return 1;
       }
       return 0;
   }
   
   public static void main (Stm s) throws IOException {
      System.out.println (maxargs(s));
      interp(s);
   }

   public static void main(String args[]) throws IOException {
      main (Example.a_program);
      main (Example.another_program);
   }
}