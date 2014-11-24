package com.aldobo.simple.sqlite.entities;

public enum SQLiteType {

    //Defaults SQLite types
    INT("INTEGER"),
    TEXT("TEXT"),
    REAL("REAL"),
    BLOB("BLOB"),
    NONE(null),
    //Extras data types
    VARCHAR("VARCHAR"),
    BOOLEAN("BOOLEAN"),
    FLOAT("FLOAT"),
    DOUBLE("DOUBLE"),
    DATETIME("DATETIME");

    private String mSQliteRepresentation;

    public String getSQliteRepresentation()
    {
        return mSQliteRepresentation;
    }

    private SQLiteType(String sqliteRepresentation)
    {
        mSQliteRepresentation = sqliteRepresentation;
    }

    @Override
    public String toString() {
        return mSQliteRepresentation;
    }
}
