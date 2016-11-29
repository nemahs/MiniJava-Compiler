/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Semantics;

import java.util.HashMap;
import java.util.LinkedList;
import syntax.And;
import syntax.Type;
import syntax.ArrayAssign;
import syntax.ArrayLength;
import syntax.ArrayLookup;
import syntax.Assign;
import syntax.Block;
import syntax.BooleanType;
import syntax.Call;
import syntax.ClassDecl;
import syntax.ExtendingClassDecl;
import syntax.False;
import syntax.Formal;
import syntax.Identifier;
import syntax.IdentifierExp;
import syntax.IdentifierType;
import syntax.If;
import syntax.IntArrayType;
import syntax.IntegerLiteral;
import syntax.IntegerType;
import syntax.LessThan;
import syntax.MainClass;
import syntax.MethodDecl;
import syntax.Minus;
import syntax.NewArray;
import syntax.NewObject;
import syntax.Not;
import syntax.Plus;
import syntax.Print;
import syntax.Program;
import syntax.SimpleClassDecl;
import syntax.SyntaxTreeVisitor;
import syntax.This;
import syntax.Times;
import syntax.True;
import syntax.VarDecl;
import syntax.While;

/**
 *
 * @author Nemahs
 */
public class ExtendingClassVisitor implements SyntaxTreeVisitor<Void>{
    
    boolean changes;
    HashMap<String, LinkedList<Type>> validTypes = new HashMap<>();
    
    @Override
    public Void visit(Program n) {
        changes = true;
        
        while(changes)
        {
            changes = false;
            for (ClassDecl c: n.cl)
                c.accept(this);
        }
        
        return null;
    }

    @Override
    public Void visit(MainClass n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Void visit(SimpleClassDecl n) {
        //If we aren't in the map, put us in the map and flag changes
        if (!validTypes.containsKey(n.i.s))
        {
            LinkedList<Type> t = new LinkedList<>();
            t.add(new IdentifierType(n.i.lineNumber, n.i.columnNumber, n.i.s));
            validTypes.put(n.i.s, t);
            changes = true;
        }
        
        return null;
    }

    @Override
    public Void visit(ExtendingClassDecl n) {
        //If we aren't in the map, put us in the map and flag changes
        if (!validTypes.containsKey(n.i.s))
        {
            LinkedList<Type> t = new LinkedList<>();
            t.add(new IdentifierType(n.i.lineNumber, n.i.columnNumber, n.i.s));
            validTypes.put(n.i.s, t);
            changes = true;
        }
        else
        {
            //If we are, make out linked list us + linked list of base class. And flag
            if (validTypes.containsKey(n.j.s))
            {
                LinkedList<Type> t = validTypes.get(n.j.s);
                LinkedList<Type> us = validTypes.get(n.i.s);
                for (Type type : t)
                {
                    if (!us.contains(type))
                    {
                        us.add(type);
                        changes = true;
                    }
                }
                
                validTypes.put(n.i.s, us);
            }
        }
        
        return null;
    }

    @Override
    public Void visit(VarDecl n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Void visit(MethodDecl n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Void visit(Formal n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Void visit(IntArrayType n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Void visit(BooleanType n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Void visit(IntegerType n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Void visit(IdentifierType n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Void visit(Block n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Void visit(If n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Void visit(While n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Void visit(Print n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Void visit(Assign n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Void visit(ArrayAssign n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Void visit(And n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Void visit(LessThan n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Void visit(Plus n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Void visit(Minus n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Void visit(Times n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Void visit(ArrayLookup n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Void visit(ArrayLength n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Void visit(Call n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Void visit(IntegerLiteral n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Void visit(True n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Void visit(False n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Void visit(IdentifierExp n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Void visit(This n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Void visit(NewArray n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Void visit(NewObject n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Void visit(Not n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Void visit(Identifier n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
