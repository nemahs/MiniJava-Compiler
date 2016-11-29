/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

import Semantics.ClassBuilder;
import Semantics.Semantics;
import Sparc.Frame;
import Sparc.Munch;
import canon.Main;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;
import syntax.Program;

/**
 *
 * @author Nemahs
 */
public class Compiler {

    public static int errors = 0;

    public static void main(String[] args) {
        MiniJava parser;
        if (args.length == 0) {
            parser = new MiniJava(System.in);
        } else if (args.length == 1) {
            try {
                parser = new MiniJava(new FileInputStream(args[0]));
            } catch (FileNotFoundException e) {
                return;
            }
        } else {
            return;
        }
        try {
            Program ast = parser.Goal();
            if (Compiler.errors == 0) {
                //Semantic analysis
                ClassBuilder cb = new ClassBuilder();
                cb.visit(ast);
                Semantics sem = new Semantics();
                sem.cl = cb.classes;
                sem.visit(ast);
                //Translate to IR tree
                if (Compiler.errors == 0) {
                    IRVisitor ir = new IRVisitor(sem.cl);
                    ir.visit(ast);
                    LinkedList<ProcFrag> frags = ir.fragments;
                    Munch m;
                    LinkedList<String> prog = new LinkedList<>();
                    frags.get(0).frame.main = true;
                    for (ProcFrag f : frags) {
                        f.flatStm = Main.transform(f.body);
                        m = new Munch();
                        m.munchFunction(f);
                        LinkedList<String> temp = f.frame.allocateRegisters(m.instruct);
                        prog.addAll(f.frame.codeGen(temp));
                    }
                    prog = Frame.progPrologue(prog);
                    String file = args[0].substring(0, args[0].indexOf("."));
                    try ( //Test print
                    final PrintWriter fout = new PrintWriter(file + ".s")) {
                        for (String s : prog) {
                            fout.println(s);
                        }
                    }
                }
            }
        } catch (ParseException e) {
            System.err.println(e.getMessage());
        } catch (UnsupportedOperationException e) {
            System.err.println("UNSUPPORTED -> " + e.getMessage());
            e.printStackTrace();
        } catch (FileNotFoundException e)
        {
            System.err.println("Failed to open file to write to.");
        } // catch (Exception e) {
         //   System.err.println("This shouldn't happen!  " + e.getMessage());
           // e.printStackTrace();
         finally {
            System.out.println("Filename:" + args[0] + " Errors:" + Compiler.errors);
        }
    }
}
