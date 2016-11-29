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
public class Statem extends LazyTree {

    Stm st;
    
    public Statem(Stm s) {st = s;}
    
    @Override
    public Exp asExp() {
        throw new UnsupportedOperationException("Exp shouldn't happen."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Stm asStm() {
        return st;
    }

    @Override
    public Stm asCond(NameOfLabel t, NameOfLabel f) {
        throw new UnsupportedOperationException("Condition shouldn't happen."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
