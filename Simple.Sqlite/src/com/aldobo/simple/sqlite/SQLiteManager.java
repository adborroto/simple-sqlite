package com.aldobo.simple.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.aldobo.simple.sqlite.entities.Field;
import com.aldobo.simple.sqlite.entities.Model;
import com.aldobo.simple.sqlite.entities.Schema;
import com.aldobo.simple.sqlite.utils.ModelIterable;
import com.aldobo.simple.sqlite.utils.Utils;


public class SQLiteManager {

    private static SQLiteConfiguration mConfiguration;
    private SQLiteManager mInstance;
    private SQLiteOpenHelperApp mDbHelper;
    private SQLiteDatabase mDb;

    public SQLiteManager(Context context) {
        mDbHelper = new SQLiteOpenHelperApp(context,mConfiguration);
        mDb = mDbHelper.getWritableDatabase();
    }

    public static void setConfiguration(SQLiteConfiguration configuration)
    {
        mConfiguration = configuration;
    }

    public SQLiteConfiguration getConfiguration()
    {
        return mConfiguration;
    }

    public void close()
    {
        mDb.close();
    }

    public SQLiteOpenHelper getSQliteOpenHelper()
    {
        return mDbHelper;
    }

    public long insert(Model item)  {
        Schema schema = item.getSchema();
        Iterable<Field> fieldIterator= schema.getFields();
        ContentValues cv = new ContentValues();
        for(Field field:fieldIterator)
        {
            try
            {
                Object value= item.getClass().getField(field.getName()).get(item);
                if(value!=null)
                    Utils.putInContentValue(cv,field,value);
            }
            catch (NoSuchFieldException e){
                e.printStackTrace();
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return mDb.insert(schema.getTableName(),null,cv);
    }


    public<T extends Model> T get(Class<T> type, Object id)
    {
        try
        {
            T item = type.newInstance();
            Schema schema  = item.getSchema();
            String[] columns = schema.getColumns();
            Iterable<Field> fields = schema.getFields();
            Cursor c= mDb.query(schema.getTableName(),
                    columns,
                    schema.getPrimaryKey().getName()+ " = ?",
                    new String[]{String.valueOf(id)},null,null,null);
            if(c.moveToFirst())
            {
                for(Field f:fields)
                {
                    int i=c.getColumnIndex(f.getName());
                    Object value =Utils.getValue(c, f, i);
                    f.getJavaField().set(item,value);
                }
                return item;
            }
            else
                return null;


        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(Model item)
    {
        Schema schema  = item.getSchema();
        Field primaryKey=schema.getPrimaryKey();
        ContentValues cv = new ContentValues();

        Object value= null;
        Object primaryKeyValue=null;
        for (Field f : schema.getFields())
        {
            try
            {
                value = item.getClass().getField(f.getName()).get(item);
                if(f.isPrimaryKey())
                    primaryKeyValue = value;
                Utils.putInContentValue(cv, f, value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        mDb.update(schema.getTableName(),cv,primaryKey.getName()+" = ?",new String[]{primaryKeyValue.toString()});
    }

    public void delete(Model item)
    {
        Schema schema  = item.getSchema();
        Field primaryKey=schema.getPrimaryKey();
        Object value= null;

        try {
            value = item.getClass().getField(primaryKey.getName()).get(item);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        mDb.delete(schema.getTableName(),primaryKey.getName()+" = ?",new String[]{value.toString()});
    }

    public<T extends Model> void delete(Class<T> type,Integer id)
    {
        Schema schema  = Schema.Builder.initialize(type);
        mDb.delete(schema.getTableName(),"_id = ?",new String[]{id.toString()});
    }

    public<T extends Model> Iterable<T> excecute(Class<T> type, String query,String[] args)
    {
        Cursor c = mDb.rawQuery(query,args);
        return new ModelIterable<T>(type,c);

    }

    public<T extends Model> Iterable<T> excecute(Class<T> type, String query)
    {
       return this.excecute(type,query,null);

    }

    public void execute(String query)
    {
        execute(query,null);
    }

    public void execute(String query, String[] args)
    {
        mDb.rawQuery(query,args);
    }

    public void beginTransaction()
    {
        mDb.beginTransaction();
    }

    public void endTransaction()
    {
        mDb.endTransaction();
    }


}
