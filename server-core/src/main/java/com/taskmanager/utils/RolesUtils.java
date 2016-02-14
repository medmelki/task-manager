package com.taskmanager.utils;


import java.util.Arrays;
import java.util.List;

public class RolesUtils {

    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_SUPERADMIN = "ROLE_SUPERADMIN";

    public static final String[] ROLES = {ROLE_USER, ROLE_ADMIN, ROLE_SUPERADMIN};
    public static final List<String> ROLES_LIST = Arrays.asList(ROLES);
}
