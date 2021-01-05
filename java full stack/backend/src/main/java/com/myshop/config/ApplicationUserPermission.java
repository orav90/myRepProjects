package com.myshop.config;

public enum ApplicationUserPermission {
    READ("read"),
    WRITE("write"),
    UPDATE("update"),
    DELETE("delete");

    private final String permission;

    ApplicationUserPermission(String permission){
        this.permission = permission;
    }
}
