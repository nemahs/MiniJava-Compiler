/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

import java.util.List;
import syntax.Formal;
import syntax.Type;

/**
 *
 * @author Nemahs
 */
public class Class {
    public Table<Type> symbolTable = new Table<>();
    public Table<List<Formal>> methods = new Table<>();    
}
