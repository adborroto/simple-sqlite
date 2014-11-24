package com.aldobo.simple.sqlite.entities;

import java.security.InvalidParameterException;
import java.util.Random;

public class Field {

    private String mName;
    private SQLiteType mType;
    private Boolean mNull;
    private Integer mLength;
    private Boolean mAutoIncrement;
    private Boolean mPrimaryKey;
    private SQLiteDefaultValue mDefaultValue;
    private String mIndex;
    private java.lang.reflect.Field mJavaField;

    private Field(Builder builder)
    {
        mName = builder.mName;
        mType = builder.mType;
        mNull = builder.mNull;
        mLength = builder.mLength;
        mAutoIncrement = builder.mAutoIncrement;
        mPrimaryKey = builder.mPrimaryKey;
        mDefaultValue = builder.mDefaultValue;
        mJavaField = builder.mJavaField;
    }

    public String getName()
    {
        return mName;
    }
    public SQLiteType getType()
    {
        return mType;
    }

    public Boolean isNullable()
    {
        return mNull;
    }

    public Integer getLength()
    {
        return mLength;
    }

    public SQLiteDefaultValue  getDefaultValue()
    {
        return mDefaultValue;
    }

    public Boolean hasDefaultValue()
    {
        return mDefaultValue!=null && mDefaultValue!=SQLiteDefaultValue.NONE;
    }

    public Boolean isAutoIncrement()
    {
        return mAutoIncrement;
    }

    public Boolean isIndex()
    {
        return mIndex!=null;
    }

    public java.lang.reflect.Field getJavaField()
    {
        return mJavaField;
    }

    public String getIndex()
    {
        return mIndex;
    }

    public Boolean isPrimaryKey()
    {
        return mPrimaryKey;
    }

    public String getSQliteCreateRepresentation()
    {
        return String.format("'%s' %s%s %s %s %s %s",
                this.getName(),
                this.getType().getSQliteRepresentation(),
                this.getLength()>0?"("+getLength().toString()+")":"",
                isNullable()?"NULL":"NOT NULL",
                !hasDefaultValue()?"":"DEFAULT "+ getDefaultValue().getSQliteRepresentation(),
                isPrimaryKey()?"PRIMARY KEY":"",
                isAutoIncrement()?"AUTOINCREMENT":"");
    }

    @Override
    public String toString() {
        return String.format("[%s] %s",getName(),getType().toString());
    }

    public static class Builder
    {
        private String mName;
        private SQLiteType mType;
        private Boolean mNull = false;
        private Integer mLength=-1;
        private Boolean mAutoIncrement=false;
        private Boolean mPrimaryKey=false;
        private SQLiteDefaultValue mDefaultValue;
        private String mIndex;
        private java.lang.reflect.Field mJavaField;

        public Builder()
        {}

        public Builder setSQliteDefaultValue(SQLiteDefaultValue defaultValue)
        {
            mDefaultValue = defaultValue;
            return this;
        }

        public Builder setName(String name)
        {
            mName = name;
            return this;
        }
        public Builder setType(SQLiteType type)
        {
            mType = type;
            return this;
        }
        public Builder setNullable()
        {
            mNull = true;
            return this;
        }
        public Builder setLength(Integer l)
        {
            mLength = l;
            return this;
        }
        public Builder setAsAutoIncrement()
        {
            mPrimaryKey = true;
            mNull = false;
            mAutoIncrement = true;

            return this;
        }
        public Builder setAsPrimaryKey()
        {
            mPrimaryKey =true;
            mNull =false;
            return this;
        }

        public Builder setJavaField(java.lang.reflect.Field field)
        {
            mJavaField = field;
            return this;
        }

        public Builder setIndex(String name)
        {
            mIndex = name;
            return this;
        }

        public Builder setIndex()
        {
            if(mName==null)
                throw new InvalidParameterException("Set field name first");
            Random r = new Random();
            setIndex(String.format("idx_%s_%s", mName, String.valueOf(r.nextLong())));
            return this;
        }

        public Field build()
        {
            if(mPrimaryKey && mNull==true)
                throw new UnsupportedOperationException("Not valid primary key and null");
            if(mAutoIncrement && mNull==true)
                throw new UnsupportedOperationException("Not valid autoincrement and null");
            if(mType==null)
                throw new UnsupportedOperationException("Type is null");
            return new Field(this);
        }
    }

}
