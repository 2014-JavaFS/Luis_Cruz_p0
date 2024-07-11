package com.revature.rba.util.interfaces;

public interface Crudable<O> extends Serviceable{
    boolean update(O updatedObject);
    boolean delete();
}
