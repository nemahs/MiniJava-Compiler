/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sparc;
import assem.Instruction;
import java.util.HashMap;
import java.util.LinkedList;
import tree.NameOfLabel;
import tree.NameOfTemp;
import tree.Stm;
/**
 *
 * @author Nemahs
 */
public class Frame extends compiler.Frame.Frame {
    int offset = 1;
    NameOfTemp fp = NameOfTemp.generateTemp();
    NameOfTemp rv = NameOfTemp.generateTemp();
    NameOfTemp link = NameOfTemp.generateTemp();
    HashMap<NameOfTemp, String> specialRegisters = new HashMap<>();
    HashMap<NameOfTemp, Access> spills = new HashMap<>();
    String[] argregs = {"%o0", "%o1", "%o2", "%o3", "%o4", "%o5"};
    String[] calleesaves = {"%i1", "%i2", "%i3", "%i4", "%i5"};
    String[] spillRegisters = {"%g1", "%g2", "%g3"};
    RegTable locals = new RegTable();
    String save = "";
    
    
    private Frame ()
    {
        specialRegisters.put(rv, "%i0");
        specialRegisters.put(fp, "%fp");
        specialRegisters.put(link , "%i0");
    }
        
    public Frame (NameOfLabel name) {this(); this.name = name;}
    
    public Frame (NameOfLabel name,  NameOfTemp classLink)
    {
        this(name);
        link = classLink;
    }
    
    @Override
    public Frame newFrame(NameOfLabel name) {
        return new Frame(name);
    }

    @Override
    public Access allocLocal() {
        Access n = new Access(offset);
        n.name = "";
        offset++;
        return n;
    }

    @Override
    public NameOfTemp FP() {
        return fp;
    }

    @Override
    public int wordSize() {
        return 4;
    }

    @Override
    public NameOfTemp RV() {
        return rv;
    }

    @Override
    public String tempMap(NameOfTemp temp) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Stm procEntryExit1(Stm body) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LinkedList<String> allocateRegisters(LinkedList<Instruction> body) {
        LinkedList<String> prog = new LinkedList<>();
        
        
        for (Instruction i : body)
        {
            String assem = i.assem;
            save = "";
            if (assem.contains("`s0"))
            {
                try
                {
                    String s = getRegister(i.use().get(0));
                    assem = assem.replace("`s0", s);
                } catch (SpillException e)
                {
                    assem = spill(assem, "`s0", 0, i.use().get(0), prog);
                }
            }
            if (assem.contains("`s1"))
            {
                try
                {
                    String s = getRegister(i.use().get(1));
                    assem = assem.replace("`s1", s);
                } catch (SpillException e)
                {
                    assem = spill(assem, "`s1", 1, i.use().get(1), prog);
                }
            }
            if (assem.contains("`d0"))
            {
                try
                {
                    String s = getRegister(i.def().get(0));
                    assem = assem.replace("`d0", s);
                } catch (SpillException e)
                {
                    assem = spill(assem, "`d0", 2, i.def().get(0), prog);                    
                }
            }
            prog.add(assem);
            if (!save.equals(""))
                prog.add(save);
        }
        
        return prog;
    }

    
    private String spill(String assem, String replace, int item, NameOfTemp temp, LinkedList<String> prog)
    {
                //Do some spilling!                
                //SHITTY CODE :D
                LinkedList<String> saves = new LinkedList<>();                
                String s = specialRegisters(temp);
                //Check for current spill
                if (s == null)
                {
                    if (!spills.containsKey(temp))
                    {
                        spills.put(temp, allocLocal());
                    }
                    
                    if (replace.contains("d"))
                    {
                        save = "\t\tST "+ spillRegisters[item] + "," +spills.get(temp).load();                    
                    }
                    //else
                   // {
                        String load = "\t\tLD " +
                        spills.get(temp).load() +
                        "," +spillRegisters[item];
                        prog.add(load);
                   // }
                    //Great! Use it!
                    s = spillRegisters[item];
                }
                assem = assem.replace(replace, s);          
                return assem;
    }
      
    @Override
    public LinkedList<String> codeGen(LinkedList<String> body) {
        if (main)
        {
         body.add(1,"\tsave %sp,-96,%sp");            
        }
        else
        {     
            //change to -4*(16+1+t+x)&-8 t = number of locals and temps, x is max subroutines  
            body.add(1,"\tsave %sp," + (-4*(60 + offset)) + "&-8,%sp"); 
        }
        body.addFirst("!------" + name + "----");
        body.addFirst("");
        body.addLast(name.toString() + "FuncEnd:");
        
        if (main)
        {
            body.addLast("\texit_program");
        }
        else
        {
            body.addLast("\tret");
            body.addLast("\trestore");
        }
        return body;
    }
    
    private String specialRegisters(NameOfTemp s)
    {
        if (specialRegisters.containsKey(s))
        {
            return specialRegisters.get(s);
        }
        //Is it an arg?
        else if (args.contains(s))
        {
            return calleesaves[args.indexOf(s)];
        }
        return null;
    }
    
    
    private String getRegister(NameOfTemp s) throws SpillException
    {
        //Is it a special register?
        if (specialRegisters.containsKey(s))
        {
            return specialRegisters.get(s);
        }    
        //Is it an arg?
        else if (args.contains(s))
        {
            return calleesaves[args.indexOf(s)];
        }
        else if (locals.getRegister(s) != -1)
        {
            return locals.registers[locals.getRegister(s)];
        }
        else
        {
            //Alloc a register!
            int i = locals.allocRegister(s);
            if (i != -1)
            {
                return locals.registers[i];
            }
            
            //Spill
            throw new SpillException();
        }
    }
    
    public static LinkedList<String> progPrologue(LinkedList<String> l)
    {
        LinkedList<String> s = new LinkedList<>();
        s.add(".section \".text\"");
        s.add(".global main");
        s.add(".align 4");
        s.addAll(l);
        return s;
    }

    @Override
    public NameOfTemp Link() {
        return link;
    }
    
    class SpillException extends Exception
    {
        
    }
    
}
