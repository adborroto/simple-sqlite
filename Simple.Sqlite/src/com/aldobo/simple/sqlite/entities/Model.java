package com.aldobo.simple.sqlite.entities;

import com.aldobo.simple.sqlite.tags.Column;
import com.aldobo.simple.sqlite.tags.Table;
import com.aldobo.simple.sqlite.utils.Utils;

public abstract class Model {

    public static Schema schema;

    @Column(type = SQLiteType.INT,primaryKey = true,autoIncrement = true)
    public Integer _id;

    public Model()
    {
    }

    public Schema getSchema()
    {
       if(schema==null)
           schema = Schema.Builder.initialize(getClass());
       return schema;
    }
}
