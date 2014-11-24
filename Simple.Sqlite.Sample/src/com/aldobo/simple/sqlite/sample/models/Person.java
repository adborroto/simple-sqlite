package com.aldobo.simple.sqlite.sample.models;

import com.aldobo.simple.sqlite.entities.Model;
import com.aldobo.simple.sqlite.entities.SQLiteType;
import com.aldobo.simple.sqlite.tags.Column;


public class Person extends Model {

    @Column(type = SQLiteType.VARCHAR,length = 20)
    public String name;

    @Column(type = SQLiteType.VARCHAR,length = 30)
    public String surname;

    @Column(type = SQLiteType.INT,nullable = true)
    public Integer age;

    @Column(type = SQLiteType.BLOB,nullable = true)
    public byte[] avatar;

    @Column(type = SQLiteType.BOOLEAN,nullable = true)
    public Boolean androidDevelop;

    @Column(type = SQLiteType.TEXT,nullable = true)
    public String description;

    @Column(type = SQLiteType.FLOAT,nullable = true)
    public Float money;

    @Column(type = SQLiteType.DOUBLE,nullable = true)
    public Double size;

    @Column(type = SQLiteType.REAL,nullable = true)
    public Double real;


    @Override
    public String toString() {
        return String.format("%s-%s %s",String.valueOf(_id),name,surname);
    }
}
