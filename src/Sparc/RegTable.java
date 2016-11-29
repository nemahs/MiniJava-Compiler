/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sparc;

import tree.NameOfTemp;

/**
 *
 * @author Nemahs
 */
public class RegTable {
    public String[] registers = {"%l0", "%l1", "%l2", "%l3", "%l4", "%l5", "%l6", "%l7"};
    private NameOfTemp[] temps = new NameOfTemp[registers.length];
    
    //Returns -1 on error
    
    public int allocRegister(NameOfTemp t)
    {
        for (int i = 0; i < temps.length; i++)
        {
            if (temps[i] == null)
            {
                temps[i] = t;
                return i;
            }
        }
        
        return -1;
    }
    
    public int getRegister(NameOfTemp t)
    {
        for (int i = 0; i < temps.length; i++)
        {
            if (temps[i] == t)
                return i;
        }
        
        return -1;
    }
    
    
}
