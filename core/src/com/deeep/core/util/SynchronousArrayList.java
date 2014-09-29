package com.deeep.core.util;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 10/26/13
 * Time: 7:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class SynchronousArrayList<E> extends ArrayList<E> {
    private ArrayList<E> removeList = new ArrayList<E>();
    private ArrayList<E> addList = new ArrayList<E>();

    public void update() {
        for (E e : addList) {
            super.add(e);
            System.out.println("Added, new size: " + super.size());
        }
        for (E e : removeList) {
            super.remove(e);
            System.out.println("removed, new size: " + super.size());
        }
        addList.clear();
        removeList.clear();
    }

    @Override
    public boolean add(E e) {
        addList.add(e);
        return true;
    }

    public void removeSynchronous(E e) {
        removeList.add(e);
    }
}
