/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Semantics;

import compiler.Class;
import compiler.Compiler;
import compiler.Table;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import syntax.*;
/**
 *
 * @author Nemahs
 */
public class Semantics implements SyntaxTreeVisitor<Type> {
    public String cClass;
    public Table<Type> symbolTable = new Table<>();
    public HashMap<String, Class> cl = new HashMap<>();
    public HashMap<String, LinkedList<Type>> validTypes;
    @Override
    public Type visit(Program n) {
        n.m.accept(this);
        
        ExtendingClassVisitor v = new ExtendingClassVisitor();
        v.visit(n);
        validTypes = v.validTypes;
        
        for(ClassDecl c : n.cl)
        {
            c.accept(this);
        }
        
        return null;
    }

    @Override
    public Type visit(MainClass n) {
        symbolTable.beginScope();
            n.i1.accept(this);
            n.i2.accept(this);
            n.s.accept(this);
        symbolTable.endScope();
        return null;
    }

    @Override
    public Type visit(SimpleClassDecl n) {
        symbolTable.declare(n.i.s, new IdentifierType(n.i.lineNumber, n.i.columnNumber, n.i.s));
        symbolTable.beginScope();
        cClass = n.i.s;
            n.i.accept(this);

            for(VarDecl vars : n.vl)
            {
                vars.accept(this);
            }

            for (MethodDecl meth : n.ml)
            {
                meth.accept(this);
            }
        symbolTable.endScope();
        return null;
    }

    @Override
    public Type visit(ExtendingClassDecl n) {
        symbolTable.beginScope();
        cClass = n.i.s;
            n.i.accept(this);
            n.j.accept(this);
            for (VarDecl vars: n.vl)
            {
                vars.accept(this);
            }

            for (MethodDecl meth : n.ml)
            {
                meth.accept(this);
            }
        symbolTable.endScope();
        return null;
    }

    @Override
    public Type visit(VarDecl n) {        
        symbolTable.declare(n.i.s, n.t);
        return n.t;
    }

    @Override
    public Type visit(MethodDecl n) {
        symbolTable.beginScope();
            for (Formal f : n.fl)
                f.accept(this);
            for (VarDecl vars : n.vl)
                vars.accept(this);
            for (Statement state: n.sl)
                state.accept(this);
        
        if (!n.t.getName().equals(n.e.accept(this).getName()))
        {
            System.err.println(n.e.lineNumber+":"+n.e.columnNumber+":Return type incorrect");
            Compiler.errors++;
        }
        
        symbolTable.endScope();
        return null;
    }

    @Override
    public Type visit(Formal n) {
        symbolTable.declare(n.i.s, n.t);
        return null;
    }

    @Override
    public Type visit(IntArrayType n) {
        return n;
    }

    @Override
    public Type visit(BooleanType n) {
        return n;
    }

    @Override
    public Type visit(IntegerType n) {
        return n;
    }

    @Override
    public Type visit(IdentifierType n) {
        return n;
    }

    @Override
    public Type visit(Block n) {
        symbolTable.beginScope();
            for (Statement s: n.sl)
                s.accept(this);
        symbolTable.endScope();
        return null;
    }

    @Override
    public Type visit(If n) {
        n.e.accept(this);
        n.s1.accept(this);
        n.s2.accept(this);
        return null;
    }

    @Override
    public Type visit(While n) {
        n.e.accept(this);
        n.s.accept(this);
        return null;
    }

    @Override
    public Type visit(Print n) {
        n.e.accept(this);
        return null;
    }

    @Override
    public Type visit(Assign n) {
        n.e.accept(this);
        Type t = symbolTable.lookup(n.i.s);
        if (t == null)
        {
            //Try lookup in class super table
            t = cl.get(cClass).symbolTable.lookup(n.i.s);
            if (t== null)
            {
                System.err.println(n.i.lineNumber+":"+n.i.columnNumber + ":Use of undeclared variable");
                Compiler.errors++;
            }
        }
        return null;
    }

    @Override
    public Type visit(ArrayAssign n) {
        if (!(n.e1.accept(this) instanceof IntegerType))
        {
            System.err.println("Expression must be an integer");
            Compiler.errors++;
        }
        
        n.e2.accept(this);
        n.i.accept(this);
        return null;
    }

    @Override
    public Type visit(And n) {
        if (!(n.e1.accept(this) instanceof BooleanType))
        {
            System.err.println("Left hand expression must be a bool");
            Compiler.errors++;       
        }
        
        if (!(n.e2.accept(this) instanceof BooleanType))
        {
            System.err.println("Right hand expression must be a bool");
            Compiler.errors++;              
        }
        
        return Type.THE_BOOLEAN_TYPE;
    }

    @Override
    public Type visit(LessThan n) {
        if (!(n.e1.accept(this) instanceof IntegerType))
        {
            System.err.println(n.e1.lineNumber +":"+n.e1.columnNumber+":Left hand expression must be an integer");
            Compiler.errors++;       
        }
        
        if (!(n.e2.accept(this) instanceof IntegerType))
        {
            System.err.println(n.e2.lineNumber +":"+n.e2.columnNumber+":Right hand expression must be an integer");
            Compiler.errors++;              
        } 
        return Type.THE_BOOLEAN_TYPE;
    }

    @Override
    public Type visit(Plus n) {
        if (!(n.e1.accept(this) instanceof IntegerType))
        {
            System.err.println(n.lineNumber +":"+n.columnNumber+":Left hand expression must be an integer");
            Compiler.errors++;       
        }
        
        if (!(n.e2.accept(this) instanceof IntegerType))
        {
            System.err.println(n.lineNumber +":"+n.columnNumber+":Right hand expression must be an integer");
            Compiler.errors++;              
        }      
        
        return Type.THE_INTEGER_TYPE;
    }

    @Override
    public Type visit(Minus n) {
        if (!(n.e1.accept(this) instanceof IntegerType))
        {
            System.err.println(n.lineNumber +":"+n.columnNumber+":Left hand expression must be an integer");
            Compiler.errors++;       
        }
        
        if (!(n.e2.accept(this) instanceof IntegerType))
        {
            System.err.println(n.lineNumber +":"+n.columnNumber+":Right hand expression must be an integer");
            Compiler.errors++;              
        }      
        
        return Type.THE_INTEGER_TYPE;
    }

    @Override
    public Type visit(Times n) {
        if (!(n.e1.accept(this) instanceof IntegerType))
        {
            System.err.println(n.e1.lineNumber +":"+n.e1.columnNumber+":Left hand expression must be an integer");
            Compiler.errors++;       
        }
        
        if (!(n.e2.accept(this) instanceof IntegerType))
        {
            System.err.println(n.e2.lineNumber +":"+n.e2.columnNumber+":Right hand expression must be an integer");
            Compiler.errors++;              
        }      
        return Type.THE_INTEGER_TYPE;
    }

    @Override
    public Type visit(ArrayLookup n) {
        n.e2.accept(this);
        return symbolTable.lookup(n.e1.accept(this).getName());  
    }

    @Override
    public Type visit(ArrayLength n) {
        return Type.THE_INTEGER_TYPE;
    }

    @Override
    public Type visit(Call n) {
        List<Formal> expressions;
        if (n.e instanceof NewObject)
        {
            Type e = n.e.accept(this);
            n.setReceiverClassName(e.getName());
            expressions = cl.get(e.getName()).methods.lookup(n.i.s);
        }
        else 
        {
            Type name = n.e.accept(this);
            n.setReceiverClassName(name.getName());
            expressions = cl.get(name.getName()).methods.lookup(n.i.s);
        }
        if (expressions == null)
        {
            System.err.println("Method does not exist in the current context");
            Compiler.errors++;
            return n.e.accept(this);          
        }
        if (n.el.size() != expressions.size())
        {
            System.err.println("Param count mismatch");
            Compiler.errors++;
            return n.e.accept(this);
        }
        
        for (int i = 0; i < n.el.size(); i++)
        {
            Expression e1 = n.el.get(i);
            Type cType = e1.accept(this);
            
            if (validTypes != null && validTypes.containsKey(expressions.get(i).t.getName()))
            {
                //Check class specific stuff here
                LinkedList<Type> types = validTypes.get(cType.getName());
                boolean found = false;
                for (Type t : types)
                {
                    if (t.getName().equals(expressions.get(i).t.getName()))
                    {
                        found = true;
                        break;                  
                    }
                }
                
                if (!found)
                {
                    System.err.println("Expected " + expressions.get(i).t.getName() + ", found" + cType.getName());
                        Compiler.errors++;
                        return null;  
                }         
            }
            else if (!expressions.get(i).t.getName().equals(cType.getName()))
            {
                System.err.println("Expected " + expressions.get(i).t.getName() + ", found" + cType.getName());
                Compiler.errors++;
                return null;
            }
        }
        
        return cl.get(n.e.accept(this).getName()).symbolTable.lookup(n.i.s);
    }

    @Override
    public Type visit(IntegerLiteral n) {
        return Type.THE_INTEGER_TYPE;
    }

    @Override
    public Type visit(True n) {
        return Type.THE_BOOLEAN_TYPE;
    }

    @Override
    public Type visit(False n) {
        return Type.THE_BOOLEAN_TYPE;
    }

    @Override
    public Type visit(IdentifierExp n) {
        Type name = symbolTable.lookup(n.s);
                    
        if (name == null)
        {
            //Go hunt for it in the classbuilder table
            name = cl.get(cClass).symbolTable.lookup(n.s);
        }
            
        return name;
    }

    @Override
    public Type visit(This n) {
        return new IdentifierType(n.columnNumber, n.lineNumber, cClass);
    }

    @Override
    public Type visit(NewArray n) {
        return Type.THE_INT_ARRAY_TYPE;
    }

    @Override
    public Type visit(NewObject n) {
        return new IdentifierType(n.columnNumber, n.lineNumber, n.i.s);
    }

    @Override
    public Type visit(Not n) {
        if (!(n.e.accept(this) instanceof BooleanType))
        {
            System.err.println(n.e.lineNumber+":"+n.e.columnNumber+":Expected a boolean type");
            Compiler.errors++;
        }
        
        return Type.THE_BOOLEAN_TYPE;
    }

    @Override
    public Type visit(Identifier n) {
        return null;
    }
    
}
