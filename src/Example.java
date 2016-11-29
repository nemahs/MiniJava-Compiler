class Example {
   // Stm :: a:=5+3; b := (print (a, a-1), 10*a); print(b)
   // Out ::
   //        8 7
   //        80
   static Stm a_program = 
      new CompoundStm(new AssignStm("a",new OpExp(new NumExp(5), OpExp.Plus, new NumExp(3))),
        new CompoundStm(new AssignStm("b",
           new EseqExp(new PrintStm(new PairExpList(new IdExp("a"),
              new LastExpList(new OpExp(new IdExp("a"), OpExp.Minus, new NumExp(1))))),
                 new OpExp(new NumExp(10), OpExp.Times, new IdExp("a")))),
                    new PrintStm(new LastExpList(new IdExp("b")))));

   
   // Stm ::  a := 6 * 7; b:= a - 7; c := (print(b, a + 9), b / a; print(c);                  /* fill in your program here         */
   // Out ::  35 51
   //             0
   static Stm another_program =   new CompoundStm(new AssignStm("a", new OpExp(new NumExp(6), OpExp.Times, new NumExp(7))),
                                        new CompoundStm(new AssignStm("b", new OpExp(new IdExp("a"), OpExp.Minus, new NumExp(7))),
                                            new CompoundStm(new AssignStm("c", new EseqExp(new PrintStm(new PairExpList(new IdExp("b"), 
                                                    new LastExpList(new OpExp(new IdExp("a"), OpExp.Plus, new NumExp(9))))), 
                                                    new OpExp(new IdExp("b"), OpExp.Div, new IdExp("a")))),
                                                    new PrintStm(new LastExpList(new IdExp("c"))))));
}