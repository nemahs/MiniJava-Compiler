/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sparc;

import tree.BINOP;
import tree.CONST;
import tree.Exp;
import tree.MEM;
import tree.Stm;
import tree.TEMP;
import assem.*;
import compiler.ProcFrag;
import java.util.Arrays;
import java.util.LinkedList;
import tree.CALL;
import tree.CJUMP;
import tree.ESEQ;
import tree.EVAL;
import tree.ExpList;
import tree.JUMP;
import tree.NAME;
import tree.NameOfTemp;

/**
 *
 * @author Nemahs
 */
public class Munch {
    
    public LinkedList<Instruction> instruct = new LinkedList<>();
    
    public void munchFunction(ProcFrag f)
    {
        //change to -4*(16+1+t+x)&-8 t = number of locals and temps, x is max subroutines  
        for (Stm s : f.flatStm)
        {
            munchStm(s);
       //     emit(new OPER("!NEXT"));
        }
    }
    
    public void munchMove(MEM dst, Exp src)
    {
        //Array DEAR GOD HELP ME
        if (dst.exp instanceof BINOP && ((BINOP)dst.exp).right instanceof BINOP &&
                ((BINOP)((BINOP)dst.exp).right).left instanceof BINOP) 
        {
            NameOfTemp array = munchExp(((BINOP)dst.exp).left);
            NameOfTemp off;
            if (((BINOP)((BINOP)((BINOP)dst.exp).right).left).left instanceof CONST)
            {
                off = NameOfTemp.generateTemp();
                int i = ((CONST)((BINOP)((BINOP)((BINOP)dst.exp).right).left).left).value;
                i = (i + 1) * 4;
                emit (new OPER("SET " + i + ",`d0", off));
                emit (new OPER("ADD `s0,`s1,`d0", L(array), L(array,off)));
            }
            else
            {
                off = munchExp(((BINOP)((BINOP)((BINOP)dst.exp).right).left).left);
                NameOfTemp toff = NameOfTemp.generateTemp();
                emit(new OPER("\tMOV `s0, `d0", toff, off));
                emit (new OPER("\tADD 1,`s0,`d0", toff, toff));
                emit (new OPER("\tSMUL `s0,4,`d0", toff, toff));
                emit(new OPER("\tADD `s0,`s1,`d0", L(array), L(array,toff)));
            }
            if (src instanceof CONST)
            {
                int value = ((CONST)src).value;
                NameOfTemp t = NameOfTemp.generateTemp();
                emit(new MOVE("\tSET " + value + ",`d0", t, null));
                emit(new OPER("\tST `s0,[`d0]", array, t));
            }
            else
            {
                NameOfTemp value = munchExp(src);
                emit(new OPER("\tST `s0, [`d0]", L(array), L(value)));
            }
            
        }
        else if (dst.exp instanceof BINOP && ((BINOP)dst.exp).binop == BINOP.MINUS
                && ((BINOP)dst.exp).right instanceof CONST)
        {
            if (src instanceof CONST)
            {
                NameOfTemp t = NameOfTemp.generateTemp();
                if (((CONST)src).value == 0)
                    emit (new MOVE("\tMOV %g0,`d0", t, null));
                else
                    emit (new MOVE("\tSET "+ ((CONST)src).value + ",`d0", t, null));
                emit(new MOVE("\tST `s0,[`d0-" + ((CONST)((BINOP)dst.exp).right).value + "]", munchExp(((BINOP)dst.exp).left), t));
                
                //emit(new MOVE("\tST "+ ((CONST)src).value +",[`d0-" + ((CONST)((BINOP)dst.exp).right).value + "]", munchExp(((BINOP)dst.exp).left), null));
            }

            else
            {
                emit(new MOVE("\tST `s0,[`d0-" + ((CONST)((BINOP)dst.exp).right).value + "]", munchExp(((BINOP)dst.exp).left), munchExp(src)));
            }
        } 
        else if (src instanceof CALL)
        {
            munchCall((CALL)src, dst);
        }
        else if (src instanceof MEM)
        {
            NameOfTemp t = NameOfTemp.generateTemp();
            emit (new MOVE ("\tLD [`s0],`d0", munchExp(((MEM)src).exp), t));
            emit (new MOVE("\tST `s0,[`d0]", t, munchExp(dst)));
        }
        else if (src instanceof CONST)
        {
            NameOfTemp t = NameOfTemp.generateTemp();
            emit (new MOVE("\tSET "+ ((CONST)src).value + ",`d0", t, null));
            emit (new MOVE("\tST `s0,[`d0]", munchExp(dst), t));
        }
        else
        {
            emit(new MOVE("\tST `s0,[`d0]", munchExp(dst.exp), munchExp(src)));
        }
    }
    
    public void munchMove(TEMP dst, MEM src)
    {
        //MEM(e - (4 + (4 * i)))
        if (src.exp instanceof BINOP && ((BINOP)src.exp).binop == BINOP.MINUS
                && ((BINOP)src.exp).right instanceof CONST)
        {
            emit(new MOVE("\tLD [`s0-" + ((CONST)((BINOP)src.exp).right).value + "]" + ",`d0",munchExp(dst),  munchExp(((BINOP)src.exp).left)));
        }
    }
    
    public void munchMove(TEMP dst, Exp src)
    {
        if (src instanceof CALL)
            munchCall((CALL)src, dst.temp);
        else if (src instanceof BINOP)
            emit (munchBINOP(dst.temp, (BINOP)src));
        else if (src instanceof CONST)
            emit (new MOVE("\tSET " + ((CONST)src).value + ",`d0", dst.temp, null));
        else
            emit(new MOVE("\tMOV `s0,`d0",dst.temp ,munchExp(src)));
    }
    
    public void munchMove(Exp dst, Exp src)
    {
        throw new UnsupportedOperationException("Implement munchMove");
    }
    
    public void munchStm(Stm s)
    {
        //MOVE
        if (s instanceof tree.MOVE)
        {
            tree.MOVE m = (tree.MOVE)s;
            
            if (m.dst instanceof TEMP && m.src instanceof MEM)
                munchMove((TEMP)m.dst, (MEM)m.src);
            else if (m.dst instanceof MEM)
                munchMove((MEM)m.dst, m.src);
            else if (m.dst instanceof TEMP)
                munchMove((TEMP)m.dst, m.src);
            else
                munchMove(m.dst, m.src);
        }
        //JUMP
        if (s instanceof JUMP)
        {
            emit(new OPER("\tBA " + ((NAME)((JUMP)s).exp).label));
            emit(new OPER("\tNOP"));
        }
        //EVAL
        if (s instanceof EVAL)
        {
            munchExp(((EVAL)s).exp);
        }
        //CJUMP HELP ME
        if (s instanceof CJUMP)
        {
            CJUMP c  = (CJUMP)s;
            
            if (c.left instanceof CONST && c.right instanceof CONST)
            {
                munchConstCompare(c);
                return;
            }
            else if (c.left instanceof CONST)
            {
                emit(new OPER("\tCMP " + ((CONST)c.left).value + ",`s0", munchExp(c.right)));
            }
            else if (c.right instanceof CONST)
            {
                emit(new OPER("\tCMP `s0," + ((CONST)c.right).value, (NameOfTemp)null, munchExp(c.left)));
            }
            else
            {
                emit (new OPER("\tCMP `s0,`s1", L((NameOfTemp)null), L(munchExp(c.left),munchExp(c.right))));
            }
            switch (c.relop)
            {
                case CJUMP.EQ:
                    emit(new OPER("\tBE " + c.iftrue.toString()));
                    break;
                case CJUMP.GE:
                    emit(new OPER("\tBGE " + c.iftrue.toString()));
                    break;
                case CJUMP.GT:
                    emit(new OPER("\tBG " + c.iftrue.toString()));
                    break;
                case CJUMP.LE:
                    emit(new OPER("\tBLE " + c.iftrue.toString()));
                    break;
                case CJUMP.LT:
                    emit(new OPER("\tBL " + c.iftrue.toString()));
                    break;
                case CJUMP.NE:
                    emit(new OPER("\tBNE " + c.iftrue.toString()));
                    break;
                default:
                    throw new UnsupportedOperationException("Fix CJUMP");
            }
            emit(new OPER("\tNOP"));
            emit(new OPER("\tBA " + c.iffalse.toString()));
            emit(new OPER("\tNOP"));
        }
        //LABEL
        if (s instanceof tree.LABEL)
            emit (new LabelInstruction(((tree.LABEL)s).label + ":", ((tree.LABEL)s).label));
    }
    
    
    public void munchConstCompare(CJUMP c)
    {
        int left = ((CONST)c.left).value;
        int right = ((CONST)c.right).value;
        
        switch (c.relop)
        {
            case CJUMP.EQ:
                if (left == right)
                    emit(new OPER("\tBA " + c.iftrue.toString()));
                else
                    emit(new OPER("\tBN " + c.iftrue.toString()));
                break;
            case CJUMP.GE:
                if (left >= right)
                    emit(new OPER("\tBA " + c.iftrue.toString()));
                else
                    emit(new OPER("\tBN " + c.iftrue.toString()));
                break;
            case CJUMP.GT:
                if (left > right)
                    emit(new OPER("\tBA " + c.iftrue.toString()));
                else
                    emit(new OPER("\tBN " + c.iftrue.toString()));
                break;
            case CJUMP.LE:
                if (left <= right)
                    emit(new OPER("\tBA " + c.iftrue.toString()));
                else
                    emit(new OPER("\tBN " + c.iftrue.toString()));
                break;
            case CJUMP.LT:
                if (left < right)
                    emit(new OPER("\tBA " + c.iftrue.toString()));
                else
                    emit(new OPER("\tBN " + c.iftrue.toString()));
                break;
            case CJUMP.NE:
                if (left != right)
                    emit(new OPER("\tBA " + c.iftrue.toString()));
                else
                    emit(new OPER("\tBN " + c.iftrue.toString()));
                break;
            default:
                throw new UnsupportedOperationException("Missed a CONST compare");
                    
        }
         emit(new OPER("\tNOP"));
         emit(new OPER("\tBA " + c.iffalse.toString()));
         emit(new OPER("\tNOP"));
    }
    
    public NameOfTemp munchExp(Exp e)
    {
        //TODO: Add in the if statements for constants
        if (e instanceof CONST)
        {
            throw new UnsupportedOperationException("Missed a constant");
        }
        //PLUS
        else if (e instanceof BINOP)
        {
            NameOfTemp r = NameOfTemp.generateTemp();
            emit(munchBINOP(r, (BINOP)e));
            return r;
        }
        //NAME
        else if (e instanceof NAME)
        {
            NAME n = (NAME) e;
            NameOfTemp t = NameOfTemp.generateTemp();
            emit(new MOVE("\tMOV " + n.label.toString() + ",`d0", t, null));
            return t;
        }
        //MEM
        else if (e instanceof MEM)
        {
            NameOfTemp t = NameOfTemp.generateTemp();
            
            if (((MEM) e).exp instanceof BINOP && ((BINOP)((MEM)e).exp).right instanceof BINOP)
            {
                BINOP thing = (BINOP)((MEM)e).exp;
                NameOfTemp array = munchExp(thing.left);
                
                if (((BINOP)((BINOP)thing.right).left).left instanceof CONST)
                {
                    int i = ((CONST)((BINOP)((BINOP)thing.right).left).left).value;
                    i = (i + 1) * 4;
                    emit (new OPER("\tADD `s0,"+ i +",`d0", L(array), L(array)));
                }
                else
                {
                    NameOfTemp oldi = munchExp(((BINOP)((BINOP)thing.right).left).left);
                    NameOfTemp i = NameOfTemp.generateTemp();
                    emit(new OPER("\tMOV `s0,`d0", i, oldi));
                    emit(new OPER("\tADD `s0,1,`d0", i, i));
                    emit(new OPER("\tSMUL `s0,4,`d0", i, i));
                    emit(new OPER("\tADD `s0,`s1,`d0", L(array), L(array,i)));
                }
                emit(new OPER("\tLD [`s0],`d0", t, array));
            }
            else
            {
                emit(new MOVE("\tLD [`s0],`d0", t, munchExp(((MEM)e).exp)));
            }
            return t;
        }
        //TEMP
        else if (e instanceof TEMP)
        {
            return ((TEMP)e).temp;
        }
        //CALL
        else if (e instanceof CALL)
        {
            NameOfTemp r = NameOfTemp.generateTemp();
            CALL c = (CALL)e;
            munchCall(c, r);
            return r;
        }
        //ESEQ
        else if (e instanceof ESEQ)
            throw new UnsupportedOperationException("ESEQ still exists??");
        throw new UnsupportedOperationException();
    }
    
    private void munchCall (CALL c, MEM dst)
    {
        munchArgs(c.args);            
        emit(new OPER("\tCALL " + ((NAME)c.func).label));
        emit (new OPER("\tNOP"));
        if (dst.exp instanceof BINOP && ((BINOP)dst.exp).binop == BINOP.MINUS
                && ((BINOP)dst.exp).right instanceof CONST)
        {
            emit(new MOVE("\tST %o0,[`d0-" + ((CONST)((BINOP)dst.exp).right).value + "]", munchExp(((BINOP)dst.exp).left), null));   
        }
        else if (dst.exp instanceof TEMP)
        {
            emit(new MOVE("\tST %o0,[`d0]", munchExp(((BINOP)dst.exp).left), munchExp(dst)));              
        }
    }
    
    private void munchCall (CALL c, NameOfTemp r)
    {
        munchArgs(c.args);            
        emit(new OPER("\tCALL " + ((NAME)c.func).label));
        emit (new OPER("\tNOP"));
        emit (new MOVE("\tMOV %o0,`d0", r, null));
    }
    
    private void munchArgs(ExpList e)
    {
        ExpList list = e;
        for (int i = 0; list != null; i++)
        {
            if (list.head instanceof CONST)
                emit(new OPER("\tSET " + ((CONST)list.head).value + ",%o" + i));
            else
                emit(new MOVE("\tMOV `s0,%o" + i, null, munchExp(list.head)));
            list = list.tail;
        }
    }
    
    private Instruction munchBINOP(NameOfTemp dst, BINOP exp)
    {
                //PLUS
        if (exp.binop == BINOP.PLUS)
        {            
            if (exp.left instanceof CONST && exp.right instanceof CONST)
                return new OPER("\tSET " + (((CONST)exp.left).value + ((CONST)exp.right).value) + ",`d0", dst);
            else if (exp.left instanceof CONST)
                return new OPER("\tADD " + ((CONST)exp.left).value + ",`s0,`d0", dst, munchExp(exp.right));
            else if (exp.right instanceof CONST)
                return new OPER("\tADD " + ((CONST)exp.right).value + ",`s0,`d0", dst, munchExp(exp.left));
            else
                return new OPER("\tADD `s0,`s1,`d0", L(dst), L(munchExp(exp.left), munchExp(exp.right)));
        }
        //MINUS
        if (exp.binop == BINOP.MINUS)
        {
            if (exp.left instanceof CONST && exp.right instanceof CONST)
                return new OPER("\tSET " + (((CONST)exp.left).value - ((CONST)exp.right).value) + ",`d0",dst);
            else if (exp.left instanceof CONST)
                return new OPER("\tSUB " + ((CONST)exp.left).value + ",`s0,`d0", dst, munchExp(exp.right));
            else if (exp.right instanceof CONST)
                return new OPER("\tSUB `s0," + ((CONST)exp.right).value + ",`d0", dst, munchExp(exp.left));
            else
                return new OPER("\tSUB `s0,`s1,`d0", L(dst), L(munchExp(exp.left), munchExp(exp.right)));
        }
        //TIMES
        if (exp.binop == BINOP.MUL)
        {
            if (exp.left instanceof CONST && exp.right instanceof CONST)
                return new OPER("\tSET " + (((CONST)exp.left).value * ((CONST)exp.right).value) + ",`d0",dst);
            else if (exp.left instanceof CONST)
                return new OPER("\tSMUL " + ((CONST)exp.left).value + ",`s0,`d0", dst, munchExp(exp.right));
            else if (exp.right instanceof CONST)
                return new OPER("\tSMUL `s0," + ((CONST)exp.right).value + ",`d0", dst, munchExp(exp.left));
            else
                return new OPER("\tSMUL `s0,`s1,`d0", L(dst), L(munchExp(exp.left), munchExp(exp.right)));
        }
        
        throw new UnsupportedOperationException("You broke it!!!");
    }
    
    private LinkedList<NameOfTemp> L (NameOfTemp... args)
    {
        LinkedList<NameOfTemp> temp = new LinkedList<>();
        temp.addAll(Arrays.asList(args));
        return temp;
    }
    
    public void emit (Instruction s)
    {
        instruct.add(s);
    }
    
}
