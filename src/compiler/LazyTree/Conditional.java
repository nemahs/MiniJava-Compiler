/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.LazyTree;

import tree.*;
/**
 *
 * @author Nemahs
 */
public abstract class Conditional extends LazyTree {
       @Override
       public Exp asExp()
       {
          NameOfTemp r = NameOfTemp.generateTemp();
          NameOfLabel t = NameOfLabel.generateLabel("if", "then");
          NameOfLabel f = NameOfLabel.generateLabel("if", "else");
          
          return new ESEQ(
                    new SEQ(new MOVE(new TEMP(r), new CONST(1)),
                      new SEQ (asCond(t, f),
                        new SEQ(new LABEL(f),
                         new SEQ(new MOVE(new TEMP(r), new CONST(0)),
                         new LABEL(t))))),
                    new TEMP(r));
          
       }
}
