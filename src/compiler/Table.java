/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Stack;

/**
 *
 * @author Nemahs
 * @param <V>
 */
public class Table<V> implements Cloneable{
    HashMap<String, Binder<V>> map = new HashMap<>();
    Stack<String> lastIndex = new Stack<>();   
    String top;
    
    public void Table()
    {
        top = "[bot]";
    }
    
    public void beginScope()
    {
        lastIndex.push(top);
        top = "[None]";
    }
    
    public void endScope()
    {
        String id = top;
        for (;;)
        {
            if (id.equals("[None]"))
                   break;
            Binder<V> binder = map.get(id);
            map.put(id, binder.tail);
            id = binder.last;
        }
        top = lastIndex.pop();
    }
    
    public void declare(String id, V item)
    {
        Binder<V> stuff = map.get(id);
        Binder<V> nStuff = new Binder<>(top, item, stuff);
        map.put(id, nStuff);
        top = id;
    }
    
    public V lookup(String item)
    {
        if (map.get(item) == null)
            return null;
        return map.get(item).value;
    }
    
    private class Binder <V>
    {
        public String last;
        public V value;
        public Binder<V> tail;
        
        public Binder (String l, V v, Binder<V> t)
        {
            last = l;
            value = v;
            tail = t;
        }
    }
    
    
    public Table<V> cloneTable()
    {
       Table<V> newTable = new Table<>();
       
       for (Entry<String,Binder<V>> entry : map.entrySet())
       {
           newTable.map.put(entry.getKey(), entry.getValue());

       }
        newTable.lastIndex = (Stack<String>)lastIndex.clone();
        newTable.top = top;
        return newTable;
    }
    
    public int size()
    {
        return map.size();
    }
    
}
