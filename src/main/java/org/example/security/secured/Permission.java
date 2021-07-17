package org.example.security.secured;


import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Permission {
    PermissionEnum[] permission();
    String entity();
}
