/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.LazyTree;

import tree.NameOfLabel;
import tree.Stm;
import tree.Exp;
import tree.CJUMP;

/**
 *
 * @author Nemahs
 */
public class RelCx extends Conditional{

    final private int relop;
    final private Exp left, right;
    
    public RelCx(int op, Exp l, Exp r)
    {
        relop = op;
        left = l;
        right = r;
    }
    
    @Override
    public Stm asStm() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Stm asCond(NameOfLabel t, NameOfLabel f) {
        return new CJUMP(relop, left, right, t, f);
    }
    
}
