package com.example.jdbc.jdbcaux.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention( RetentionPolicy.RUNTIME )
public @interface JdbcIdentity {

    public final static String GENERATED_KEY = "GK";
    public final static String NO_GENERATED_KEY = "NGK";
    String value();
    String typeKey() default GENERATED_KEY;
}
