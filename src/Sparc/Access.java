/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sparc;
import tree.*;
/**
 *
 * @author Nemahs
 */
public class Access extends compiler.Frame.Access {
    
    Access(int k) {this.k = k;}
    
    int k;
    
    @Override
    public Exp exp(Exp framePtr) {
        return new MEM(new BINOP(BINOP.MINUS, framePtr, new CONST(4 * k)));
    }

    @Override
    public String load() {
        return "[%fp - " + (4 * k) + "]";
    }
    
}
