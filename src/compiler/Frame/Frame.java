/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.Frame;

import assem.Instruction;
import java.util.LinkedList;
import tree.NameOfTemp;
import tree.NameOfLabel;
import tree.Stm;
/**
 *
 * @author Nemahs
 */
abstract public class Frame{
    abstract public Frame newFrame(NameOfLabel name);
    public NameOfLabel name;
    abstract public String tempMap(NameOfTemp temp);
    public AccessList formals = new AccessList();
    public LinkedList<NameOfTemp> args = new LinkedList<>();
    public static LinkedList<String> progPrologue(LinkedList<String> l){throw new UnsupportedOperationException("Implement me!");}
    abstract public Access allocLocal();
    abstract public NameOfTemp FP();
    public boolean main;
    abstract public int wordSize();
    abstract public NameOfTemp RV();
    abstract public NameOfTemp Link();
    abstract public Stm procEntryExit1 (Stm body);
    abstract public LinkedList<String> allocateRegisters (LinkedList<Instruction> body);
    abstract public LinkedList<String> codeGen (LinkedList<String> body);
}

