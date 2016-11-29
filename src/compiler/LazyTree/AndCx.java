/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.LazyTree;

import tree.JUMP;
import tree.LABEL;
import tree.NameOfLabel;
import tree.SEQ;
import tree.Stm;

/**
 *
 * @author Nemahs
 */
public class AndCx extends Conditional {

    LazyTree left;
    LazyTree right;
    
    
    public AndCx(LazyTree l, LazyTree r) {left = l; right = r;}
    
    @Override
    public Stm asStm() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Stm asCond(NameOfLabel t, NameOfLabel f) {
        NameOfLabel if2 = NameOfLabel.generateLabel("if","then");
        NameOfLabel join = NameOfLabel.generateLabel("join");
        return new SEQ(left.asCond(if2, f),
        new SEQ(new LABEL(if2),
        new SEQ(right.asCond(t, f),
        new SEQ(new LABEL(t),
        new SEQ(new JUMP(join),
        new SEQ(new LABEL(f),
        new SEQ(new JUMP(join),
        new LABEL(join))))))));
    }
    
}
