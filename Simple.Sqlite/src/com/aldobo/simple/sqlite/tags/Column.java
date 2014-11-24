package com.aldobo.simple.sqlite.tags;
import com.aldobo.simple.sqlite.entities.SQLiteType;
import com.aldobo.simple.sqlite.entities.SQLiteDefaultValue;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {

    SQLiteType type() default SQLiteType.NONE;
    boolean nullable() default false;
    int length() default -1;
    SQLiteDefaultValue defaultValue() default SQLiteDefaultValue.NONE;
    boolean autoIncrement() default false;
    boolean primaryKey() default false;

}
