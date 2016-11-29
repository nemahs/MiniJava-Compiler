/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

import java.util.HashMap;
import syntax.And;
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
import tree.NameOfLabel;

/**
 *
 * @author Nemahs
 */
public class LabelGen implements SyntaxTreeVisitor<HashMap<String,NameOfLabel>>{

    @Override
    public HashMap<String, NameOfLabel> visit(Program n) {
        HashMap<String, NameOfLabel> l = new HashMap<>();
        for (ClassDecl c: n.cl)
            l.putAll(c.accept(this));
        
        return l;
    }

    @Override
    public HashMap<String, NameOfLabel> visit(MainClass n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HashMap<String, NameOfLabel> visit(SimpleClassDecl n) {
        HashMap<String, NameOfLabel> l = new HashMap<>();
        for (MethodDecl m : n.ml)
        {
          l.put(n.i.s + "." + m.i.s, NameOfLabel.generateLabel(n.i.s ,m.i.s));
        }
        
        return l;
    }

    @Override
    public HashMap<String, NameOfLabel> visit(ExtendingClassDecl n) {
                HashMap<String, NameOfLabel> l = new HashMap<>();
        for (MethodDecl m : n.ml)
        {
          l.put(n.i.s + "." + m.i.s, NameOfLabel.generateLabel(n.i.s ,m.i.s));
        }
        
        return l;
    }

    @Override
    public HashMap<String, NameOfLabel> visit(VarDecl n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HashMap<String, NameOfLabel> visit(MethodDecl n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HashMap<String, NameOfLabel> visit(Formal n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HashMap<String, NameOfLabel> visit(IntArrayType n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HashMap<String, NameOfLabel> visit(BooleanType n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HashMap<String, NameOfLabel> visit(IntegerType n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HashMap<String, NameOfLabel> visit(IdentifierType n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HashMap<String, NameOfLabel> visit(Block n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HashMap<String, NameOfLabel> visit(If n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HashMap<String, NameOfLabel> visit(While n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HashMap<String, NameOfLabel> visit(Print n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HashMap<String, NameOfLabel> visit(Assign n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HashMap<String, NameOfLabel> visit(ArrayAssign n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HashMap<String, NameOfLabel> visit(And n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HashMap<String, NameOfLabel> visit(LessThan n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HashMap<String, NameOfLabel> visit(Plus n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HashMap<String, NameOfLabel> visit(Minus n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HashMap<String, NameOfLabel> visit(Times n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HashMap<String, NameOfLabel> visit(ArrayLookup n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HashMap<String, NameOfLabel> visit(ArrayLength n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HashMap<String, NameOfLabel> visit(Call n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HashMap<String, NameOfLabel> visit(IntegerLiteral n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HashMap<String, NameOfLabel> visit(True n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HashMap<String, NameOfLabel> visit(False n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HashMap<String, NameOfLabel> visit(IdentifierExp n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HashMap<String, NameOfLabel> visit(This n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HashMap<String, NameOfLabel> visit(NewArray n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HashMap<String, NameOfLabel> visit(NewObject n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HashMap<String, NameOfLabel> visit(Not n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HashMap<String, NameOfLabel> visit(Identifier n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
