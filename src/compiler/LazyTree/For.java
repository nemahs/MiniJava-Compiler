/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.LazyTree;

import tree.Exp;
import tree.NameOfLabel;
import tree.Stm;

/**
 *
 * @author Nemahs
 */
public class For extends LazyTree {

    LazyTree cond;
    LazyTree statement;
    
    public For(LazyTree c, LazyTree stm) {cond = c; statement = stm;}
    
    @Override
    public Exp asExp() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Stm asStm() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Stm asCond(NameOfLabel t, NameOfLabel f) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
