package com.revature.rba.util.interfaces;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Crudable<O> extends Serviceable{
    boolean update(O updatedObject);
    boolean delete();
}
