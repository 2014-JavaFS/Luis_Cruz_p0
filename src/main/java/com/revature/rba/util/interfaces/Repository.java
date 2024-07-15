package com.revature.rba.util.interfaces;

import com.revature.rba.Member.Member;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Repository<O> extends Crudable{
    public O generateFromResult(ResultSet rs) throws SQLException;
}
