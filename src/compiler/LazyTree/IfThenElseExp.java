/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.LazyTree;

/**
 *
 * @author Nemahs
 */

import tree.*;

public class IfThenElseExp extends LazyTree {

   private final LazyTree cond, e2, e3;
   final NameOfLabel t = NameOfLabel.generateLabel("if","then");
   final NameOfLabel f = NameOfLabel.generateLabel("if","else");
   final NameOfLabel join = NameOfLabel.generateLabel("if","end");

   public IfThenElseExp (LazyTree c, LazyTree thenClause, LazyTree elseClause) {
      cond = c; e2 = thenClause; e3 = elseClause;

   }

   @Override
   public Exp asExp() { 
      final NameOfTemp result = NameOfTemp.generateTemp();
      final Stm seq;
      if (e3 == null) {
	 seq = new SEQ(cond.asCond(t, f),
	       new SEQ(new LABEL(t),                            // T:  
	       new SEQ(new MOVE(new TEMP(result), e2.asExp()),   //     result := then expr
                       new LABEL(f) )));                        // F:
      } else {
	 seq = new SEQ(cond.asCond(t, f),
	       new SEQ(new LABEL(t),
	       new SEQ(new MOVE(new TEMP(result), e2.asExp()),  //      result := then expr
	       new SEQ(new JUMP(join),                          //      goto join
	       new SEQ(new LABEL(f),                            // F:
	       new SEQ(new MOVE(new TEMP(result), e3.asExp()),  //      result := else expr
		       new LABEL(join) ))))));                  // join:
      }
      return new ESEQ(seq, new TEMP(result));
   }


   @Override
   public Stm asStm() {
       SEQ seq = new SEQ(asCond(t,f),
               new SEQ(new LABEL(t),
               new SEQ(e2.asStm() ,
               new SEQ(new JUMP(join), 
               new SEQ(new LABEL(f), 
               new SEQ(e3.asStm(),
               new LABEL(join)))))));
       
       return seq;
   }

   @Override
   public Stm asCond (NameOfLabel tt, NameOfLabel ff) {
       return cond.asCond(tt, ff);
   }
}
