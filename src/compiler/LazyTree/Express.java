/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.LazyTree;

import tree.CJUMP;
import tree.Exp;
import tree.EVAL;
import tree.NameOfLabel;
import tree.Stm;
import tree.CONST;

/**
 *
 * @author Nemahs
 */
public class Express extends LazyTree {

    Exp e;
    
    public Express (Exp ex) {e = ex;}
    
    @Override
    public Exp asExp() {
       return e;
    }

    @Override
    public Stm asStm() {
        return new EVAL(e);
    }

    @Override
    public Stm asCond(NameOfLabel t, NameOfLabel f) {
        return new CJUMP(CJUMP.EQ, e, new CONST(1), t, f);
    }
    
}
