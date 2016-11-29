/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;
import java.util.LinkedList;
import syntax.*;
import tree.*;
import compiler.LazyTree.*;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Nemahs
 */
public class IRVisitor implements SyntaxTreeVisitor<LazyTree>{

    public LinkedList<ProcFrag> fragments = new LinkedList<>();
    private final static NameOfLabel print = new NameOfLabel("println");
    private final static NameOfLabel alloc = new NameOfLabel("_alloc_object");
    public HashMap<String, Class> classes;
    private HashMap<String,NameOfTemp> formals;
    private HashMap<String, NameOfLabel> methods;
    private String cClass;
    private compiler.Frame.Frame f;
    private compiler.Frame.Frame classFrame;
    
    public IRVisitor(HashMap<String, Class> symbolTable) { classes = symbolTable;}
    
    @Override
    public LazyTree visit(Program n) {
        
        NameOfLabel main = NameOfLabel.generateLabel("Main", "main");
        f = new Sparc.Frame(main);
        classFrame = new Sparc.Frame(main);
        methods = new LabelGen().visit(n);
        //TreePrint.print(new PrintWriter(System.out), n.m.accept(this).asStm());        
        //System.out.println();
        n.m.accept(this);
        
        for (ClassDecl c: n.cl)
        {
            c.accept(this);
        }
        
        return null;
    }

    @Override
    public LazyTree visit(MainClass n) {
        Stm s =  n.s.accept(this).asStm();
        ProcFrag frag = new ProcFrag();
        frag.frame = classFrame.newFrame(new NameOfLabel("main"));
        frag.body = new SEQ(new LABEL("main") , new SEQ(s, new JUMP("mainFuncEnd")));
        fragments.add(frag);
        return null;
    }

    @Override
    public LazyTree visit(SimpleClassDecl n) {
        cClass = n.i.s;
        classFrame = classFrame.newFrame(NameOfLabel.generateLabel(n.i.s));
        for (VarDecl f : n.vl)
        {
            compiler.Frame.Access a = classFrame.allocLocal();
            a.name = f.i.s;
            classFrame.formals.addVar(a);
        }
        
        for (MethodDecl d : n.ml)
        {
            NameOfLabel l = methods.get(n.i.s + "." + d.i.s) ;
            methods.put(n.i.s + "." + d.i.s, l);
            f = new Sparc.Frame(l);
            ProcFrag frag = new ProcFrag();
            Stm s = d.accept(this).asStm();
            frag.body = new SEQ(new LABEL(l),new SEQ(s,new SEQ(new MOVE(new TEMP(f.RV()), d.e.accept(this).asExp()),new JUMP(f.name.toString() + "FuncEnd"))));
            frag.frame = f;
            //TreePrint.print(new PrintWriter(System.out), frag.body);
            fragments.add(frag);
        }
        return null;
    }

    @Override
    public LazyTree visit(ExtendingClassDecl n) {
        /*FIX ME: NO INHERITENCE*/
        cClass = n.i.s;
        classFrame.newFrame(NameOfLabel.generateLabel(n.i.s));
        
        
        for (VarDecl f : n.vl)
        {
            compiler.Frame.Access a = classFrame.allocLocal();
            a.name = f.i.s;
            classFrame.formals.addVar(a);
        }
        
        for (MethodDecl d : n.ml)
        {
            NameOfLabel l = methods.get(n.i.s + "." + d.i.s) ;
            methods.put(n.i.s + "." + d.i.s, l);
            f = new Sparc.Frame(l);
            ProcFrag frag = new ProcFrag();
            Stm s = d.accept(this).asStm();
            frag.body = new SEQ(new LABEL(l),new SEQ(s,new SEQ(new MOVE(new TEMP(f.RV()), d.e.accept(this).asExp()),new JUMP(f.name.toString() + "FuncEnd"))));
            frag.frame = f;
            //TreePrint.print(new PrintWriter(System.out), frag.body);
            fragments.add(frag);
        }
        return null;
    }

    @Override
    public LazyTree visit(VarDecl n) {
        throw new UnsupportedOperationException("How did you get here?");
    }

    @Override
    public LazyTree visit(MethodDecl n) {
        f = f.newFrame(NameOfLabel.generateLabel(n.i.s));      
        formals = new HashMap<>();
        
        for (Formal form : n.fl)
        {
            NameOfTemp t = NameOfTemp.generateTemp();
            formals.put(form.i.s, t);
            f.args.add(t);
            
        }
        
        for (VarDecl v : n.vl)
        {
            compiler.Frame.Access a = f.allocLocal();
            a.name = v.i.s;
            f.formals.addVar(a);
        }
        Stm s = new EVAL(new CONST(1));
        
        if (n.sl.size() > 1)
        {
            int i = n.sl.size();
            Stm s1 = n.sl.get(--i).accept(this).asStm();
            Stm s2 = n.sl.get(--i).accept(this).asStm();
            s = new SEQ(s2, s1);
            while (i > 0)
            {
                s = new SEQ(n.sl.get(--i).accept(this).asStm(), s); 
            }
        }
        else if (n.sl.size() == 1)
        {
            s = n.sl.get(0).accept(this).asStm();
        }
        
        return new Statem(s);
    }

    @Override
    public LazyTree visit(Formal n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LazyTree visit(IntArrayType n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LazyTree visit(BooleanType n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LazyTree visit(IntegerType n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LazyTree visit(IdentifierType n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LazyTree visit(Block n) {
        Stm s;
        
        if (n.sl.size() > 1)
        {
            int i = n.sl.size();
            Stm s1 = n.sl.get(--i).accept(this).asStm();
            Stm s2 = n.sl.get(--i).accept(this).asStm();
            s = new SEQ(s2, s1);
            while (i > 0)
            {
                s = new SEQ(n.sl.get(--i).accept(this).asStm(), s); 
            }
        }
        else 
        {
            s = n.sl.get(0).accept(this).asStm();
        }
        
        return new Statem(s);
    }

    @Override
    public LazyTree visit(If n) {
        NameOfLabel t = NameOfLabel.generateLabel("if", "then");
        NameOfLabel f = NameOfLabel.generateLabel("if", "else");
        NameOfLabel join = NameOfLabel.generateLabel("if", "end");
        Stm s = new SEQ(new CJUMP(CJUMP.EQ, n.e.accept(this).asExp(), new CONST(1), t, f),
                  new SEQ(new LABEL(t),
                  new SEQ(n.s1.accept(this).asStm(),
                  new SEQ(new JUMP(join),
                  new SEQ(new LABEL(f),
                  new SEQ(n.s2.accept(this).asStm(),
                  new SEQ(new JUMP(join),
                  new LABEL(join))))))));
        return new Statem(s);
    }

    @Override
    public LazyTree visit(While n) {
        NameOfLabel breakOut = NameOfLabel.generateLabel("end");
        NameOfLabel cont = NameOfLabel.generateLabel("continue");
        NameOfLabel again = NameOfLabel.generateLabel("again");
        SEQ s = new SEQ(new LABEL(again),
                new SEQ(n.e.accept(this).asCond(cont, breakOut),
                new SEQ(new LABEL(cont),
                new SEQ(n.s.accept(this).asStm(), 
                new SEQ(new JUMP(again), new LABEL(breakOut))))));
        return new Statem(s); 
    }

    @Override
    public LazyTree visit(Print n) {
        return new Express(new CALL(new NAME(print), n.e.accept(this).asExp()));
    }

    @Override
    public LazyTree visit(Assign n) {
        return new Statem(new MOVE(n.i.accept(this).asExp() ,n.e.accept(this).asExp()));
    }

    @Override
    public LazyTree visit(ArrayAssign n) {
        Stm s = new MOVE(new MEM(new BINOP(BINOP.MINUS, n.i.accept(this).asExp(), new BINOP(BINOP.PLUS, new BINOP(BINOP.MUL, n.e1.accept(this).asExp(), new CONST(4)), new CONST(4)))) , n.e2.accept(this).asExp());
        //Move number to MEM(array - ((1 + i) * 4))
        return new Statem(s);
    }

    @Override
    public LazyTree visit(And n) {
        return new AndCx(n.e1.accept(this), n.e2.accept(this));
        
       // return new IfThenElseExp(n.e1.accept(this), n.e2.accept(this), new Express(new CONST(0)));
    }

    @Override
    public LazyTree visit(LessThan n) {
        return new RelCx(CJUMP.LT, n.e1.accept(this).asExp(), n.e2.accept(this).asExp());
    }

    @Override
    public LazyTree visit(Plus n) {
        LazyTree e1 = n.e1.accept(this);
        LazyTree e2 = n.e2.accept(this);
        Express s = new Express(new BINOP(BINOP.PLUS, e1.asExp(), e2.asExp()));
        return s;
    }

    @Override
    public LazyTree visit(Minus n) {
        LazyTree e1 = n.e1.accept(this);
        LazyTree e2 = n.e2.accept(this);
        Express s = new Express(new BINOP(BINOP.MINUS, e1.asExp(), e2.asExp()));
        return s;
    }

    @Override
    public LazyTree visit(Times n) {
        LazyTree e1 = n.e1.accept(this);
        LazyTree e2 = n.e2.accept(this);
        Express s = new Express(new BINOP(BINOP.MUL, e1.asExp(), e2.asExp()));
        return s;
    }

    @Override
    public LazyTree visit(ArrayLookup n) {
        LazyTree array = n.e1.accept(this);
        LazyTree location = n.e2.accept(this);
        return new Express(new MEM(new BINOP(BINOP.MINUS, array.asExp(), new BINOP(BINOP.PLUS, new BINOP(BINOP.MUL, location.asExp(), new CONST(4)), new CONST(4)))));
        //MEM(e - (4 + (4 * i)))
    }

    @Override
    public LazyTree visit(ArrayLength n) {
        Express name = new Express(new MEM(n.e.accept(this).asExp()));
        return name;
    }

    @Override
    public LazyTree visit(Call n) { 
        LinkedList<Exp> ex = new LinkedList<>();
        ex.add(n.e.accept(this).asExp());        
        String searchName = "";
        if (n.e instanceof IdentifierExp)
            searchName = n.getReceiverClassName() + "." + n.i.s;
        else if (n.e instanceof Call)
        {
            searchName = classes.get(((Call)n.e).getReceiverClassName()).symbolTable.lookup(((Call)n.e).i.s).toString() + "." + n.i.s;
        }
        else if (n.e instanceof This)
            searchName = cClass + "." + n.i.s;
        else if (n.e instanceof NewObject)
            searchName = ((NewObject)n.e).i.s + "." + n.i.s;
        for (Expression e : n.el)
        {
            ex.add(e.accept(this).asExp());
        }
        ExpList list = fromList(ex);
        return new Express(new CALL(new NAME(methods.get(searchName)) , list));
    }

    @Override
    public LazyTree visit(IntegerLiteral n) {
        return new Express(new CONST(n.i));
    }
    
    @Override
    public LazyTree visit(True n) {
        return new Express(new CONST(1));
    }

    @Override
    public LazyTree visit(False n) {
        return new Express(new CONST(0));
    }

    @Override
    public LazyTree visit(IdentifierExp n) {
        if (f.formals.contains(n.s))
        {
            compiler.Frame.Access a = f.formals.find(n.s);
            return new Express(a.exp(new TEMP(f.FP())));
        }
        
        if (formals.containsKey(n.s))
        {
            return new Express(new TEMP(formals.get(n.s)));
        }
        
        return new Express(classFrame.formals.find(n.s).exp(new TEMP(f.Link())));
    }

    @Override
    public LazyTree visit(This n) {
        return new Express(new TEMP(f.Link()));
    }

    @Override
    public LazyTree visit(NewArray n) {
        NameOfTemp array = NameOfTemp.generateTemp(); //Need a register real quick to assign length
        return new Express(new ESEQ(new SEQ(new MOVE(new TEMP(array), new CALL(new NAME(alloc), new BINOP(BINOP.MUL, n.e.accept(this).asExp(), new CONST(4)))),
                        new MOVE(new MEM(new TEMP(array)), new BINOP(BINOP.PLUS, n.e.accept(this).asExp(), new CONST(1)))), new TEMP(array)));
    }

    @Override
    public LazyTree visit(NewObject n) {
        return new Express(new CALL(new NAME(alloc), new CONST((classes.get(n.i.s).symbolTable.size()) * f.wordSize())));
    }

    @Override
    public LazyTree visit(Not n) {
        RelCx a = new RelCx( CJUMP.NE ,n.e.accept(this).asExp(), new CONST(1));
        return a;
    }

    @Override
    public LazyTree visit(Identifier n) {
        if (f.formals.contains(n.s))
        {
            compiler.Frame.Access a = f.formals.find(n.s);
            return new Express(a.exp(new TEMP(f.FP())));
        }
        
        if (formals.containsKey(n.s))
        {
            return new Express(new TEMP(formals.get(n.s)));
        }
        
        return new Express(classFrame.formals.find(n.s).exp(new TEMP(f.Link())));
    }
    
    
    public ExpList fromList (List <Exp> l) {
      final Exp[] el = l.toArray (new Exp[]{});
      ExpList tail = null;
      for (int i=el.length - 1; i>=0; i--) {
         tail = new ExpList (el[i], tail);
      }
      return tail;
   }
    
    
}
