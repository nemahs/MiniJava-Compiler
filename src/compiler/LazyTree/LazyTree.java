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
public abstract class LazyTree {
    public abstract Exp asExp();
    public abstract Stm asStm();
    public abstract Stm asCond(NameOfLabel t, NameOfLabel f);
}
