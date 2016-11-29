/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.Frame;

import java.util.LinkedList;

/**
 *
 * @author Nemahs
 */
public class AccessList {
    LinkedList<Access> vars = new LinkedList<>();
    
    public void addVar(Access a)
    {
        vars.add(a);
    }
    
    public boolean contains (String s)
    {
        for (Access a : vars)
        {
            if (a.name.equals(s))
                return true;
        }
        return false;
    }
    
    public Access find (String s)
    {
        for (Access a : vars)
        {
            if (a.name.equals(s))
                return a;
        }
        return null;
    }
    
    
    public int count()
    {
        return vars.size();
    }
}
