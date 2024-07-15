package com.revature.rba.util.interfaces;

import io.javalin.Javalin;

public interface Controller {
    void registerPaths(Javalin app);
}
