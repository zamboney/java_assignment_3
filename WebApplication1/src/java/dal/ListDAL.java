/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ritzhaki
 */
public class ListDAL<T> implements List<T> {

    private String fileName;
    private List<T> list = new ArrayList<>();

    public ListDAL(String filePath) {
        fileName = filePath;
        synchronized (fileName) {
            try {
                File file = new File(fileName);
                if (!file.exists()) {
                    this.list = new ArrayList<>();
                    file.createNewFile();
                } else {
                    FileInputStream fis = new FileInputStream(fileName);
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    this.list = (List<T>) ois.readObject();
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ListDAL.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(ListDAL.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private boolean SaveData() {
        synchronized (fileName) {
            ObjectOutputStream oos;
            try {
                FileOutputStream fos = new FileOutputStream(fileName);
                oos = new ObjectOutputStream(fos);
                oos.writeObject(this.list);
                oos.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ListDAL.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            } catch (IOException ex) {
                Logger.getLogger(ListDAL.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
            return true;
        }
    }

    @Override
    public int size() {
        return this.list.size();
    }

    @Override
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return this.list.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return this.list.iterator();
    }

    @Override
    public Object[] toArray() {
        return this.list.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return this.list.toArray(a);
    }

    public boolean remove(Object o) {
        return this.list.remove(o) && this.SaveData();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return this.list.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return this.list.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return this.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return this.list.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return this.list.retainAll(c);
    }

    @Override
    public void clear() {
        this.list.clear();
    }

    @Override
    public T get(int index) {
        return this.list.get(index);
    }

    @Override
    public T set(int index, T element) {
        return this.list.set(index, element);
    }

    @Override
    public void add(int index, T element) {
        this.list.add(index, element);
    }

    @Override
    public T remove(int index) {
        return this.list.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return this.list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return this.list.lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        return this.listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return this.list.listIterator(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return this.list.subList(fromIndex, toIndex);
    }

    @Override
    public boolean add(T e) {
        return this.list.add(e) && this.SaveData();
    }

}
