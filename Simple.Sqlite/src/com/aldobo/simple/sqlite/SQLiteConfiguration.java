package com.aldobo.simple.sqlite;

import com.aldobo.simple.sqlite.entities.Model;
import com.aldobo.simple.sqlite.entities.Schema;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SQLiteConfiguration {

    private String mDatabaseName;
    private Integer mDatabaseVersion;
    private List<Schema> mSchemas;
    private SQLiteOpenHelperExtendable mExtension;

    private SQLiteConfiguration(Builder builder)
    {
        mDatabaseName = builder.mDatabaseName;
        mDatabaseVersion= builder.mDatabaseVersion;
        mSchemas = builder.mSchemas;
        mExtension = builder.mExtension;
    }

    public String getDatabaseName()
    {
        return this.mDatabaseName;
    }

    public Integer getDatabaseVersion()
    {
        return this.mDatabaseVersion;
    }

    public Iterator<Schema> getSchemas()
    {
        return mSchemas.iterator();
    }

    public SQLiteOpenHelperExtendable getExtension()
    {
        return mExtension;
    }

    public Boolean hasExtension()
    {
        return mExtension!=null;
    }

    public static class Builder
    {
        private String mDatabaseName;
        private Integer mDatabaseVersion;
        private List<Schema> mSchemas;
        private SQLiteOpenHelperExtendable mExtension;

        public Builder()
        {
            mSchemas = new ArrayList<Schema>();
        }

        public Builder setDatabaseName(String name)
        {
            mDatabaseName = name;
            return this;
        }
        public Builder setDatabaseVersion(Integer version)
        {
            mDatabaseVersion = version;
            return this;
        }

        public SQLiteConfiguration build()
        {
            return new SQLiteConfiguration(this);
        }
        private Builder registerTable(Schema schema)
        {
            mSchemas.add(schema);
            return this;
        }

        public<T extends Model> Builder registerTable(Class<T> model)
        {
            try {
                Schema schema= model.newInstance().getSchema();
                return registerTable(schema);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        }

        public Builder setExtension(SQLiteOpenHelperExtendable extension)
        {
            mExtension  = extension;
            return this;
        }




    }

}
