/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;
import tree.Stm;
import compiler.Frame.Frame;
import java.util.List;
/**
 *
 * @author Nemahs
 */
public class ProcFrag extends Frag
{
    public Stm body;
    public Frame frame;
    public List<Stm> flatStm;
}
