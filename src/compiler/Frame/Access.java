/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.Frame;
import tree.Exp;
/**
 *
 * @author Nemahs
 */
public abstract class Access {
    public String name;
    public abstract Exp exp(Exp framePtr);
    public abstract String load();
}
