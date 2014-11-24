package com.aldobo.simple.sqlite.entities;


public enum SQLiteDefaultValue {

    CURRENT_TIMESTAMP("CURRENT_TIMESTAMP"),
    CURRENT_TIME("CURRENT_TIME"),
    CURRENT_DATE("CURRENT_DATE"),
    NONE(null);

    private String mSqliteRepresentation;

    public String getSQliteRepresentation()
    {
        return mSqliteRepresentation;
    }

    private SQLiteDefaultValue(String sqliteRepresentation)
    {
        mSqliteRepresentation = sqliteRepresentation;
    }

    @Override
    public String toString() {
        return mSqliteRepresentation;
    }
}
