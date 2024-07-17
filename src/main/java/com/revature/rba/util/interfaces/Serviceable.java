package com.revature.rba.util.interfaces;

import java.util.List;

public interface Serviceable <O>{
    List<O> findAll();
    O create(O object);
}
