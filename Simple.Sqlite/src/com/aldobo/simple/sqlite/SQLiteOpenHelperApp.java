package com.aldobo.simple.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.aldobo.simple.sqlite.entities.Field;
import com.aldobo.simple.sqlite.entities.Schema;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class SQLiteOpenHelperApp extends SQLiteOpenHelper {

    private final String TAG ="SQliteManager";

    private SQLiteConfiguration mConfiguration;

    public SQLiteOpenHelperApp(Context context, SQLiteConfiguration configuration)
    {
        super(context,configuration.getDatabaseName(),null,configuration.getDatabaseVersion());
        mConfiguration = configuration;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Iterator<Schema> schemas = mConfiguration.getSchemas();
        List<Field> indexFields = new ArrayList<Field>();

        while (schemas.hasNext())
        {
            Schema schema = schemas.next();
            StringBuilder stringBuilder = new StringBuilder();
            String separator = "";
            for(Field field:schema.getFields())
            {
               if(field.isIndex())
                   indexFields.add(field);

               stringBuilder.append(separator + field.getSQliteCreateRepresentation());
               separator = ",";
            }
            String query = String.format("CREATE TABLE [%s] (%s);",schema.getTableName(),stringBuilder.toString());
            db.execSQL(query);
            for(Field field:indexFields)
            {
                query = String.format("CREATE INDEX '%s' ON [%s]('%s')",field.getIndex(),schema.getTableName(),field.getName());
                db.execSQL(query);
            }
        }

        if(mConfiguration.hasExtension())
        {
            mConfiguration.getExtension().onCreate(db);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Iterator<Schema> schemas = mConfiguration.getSchemas();
        while (schemas.hasNext())
        {
            Schema schema = schemas.next();
            String query = String.format("DROP TABLE IF EXISTS [%s];",schema.getTableName());
            db.execSQL(query);
        }

        if(mConfiguration.hasExtension())
        {
            mConfiguration.getExtension().onUpgrade(db, oldVersion, newVersion);
        }

        onCreate(db);
    }
}
