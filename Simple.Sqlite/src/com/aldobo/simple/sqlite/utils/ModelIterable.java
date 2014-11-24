package com.aldobo.simple.sqlite.utils;

import android.database.Cursor;

import com.aldobo.simple.sqlite.entities.Field;
import com.aldobo.simple.sqlite.entities.Model;
import com.aldobo.simple.sqlite.entities.Schema;

import java.util.Iterator;


public class ModelIterable<T extends Model> implements Iterable<T> {

    ModelIterator<T> mIterator;

    public ModelIterable(Class<T> type, Cursor cursor)
    {
        mIterator = new ModelIterator<T>();
        mIterator.mCursor = cursor;
        mIterator.mType = type;
        try {
            mIterator.mSchema = type.newInstance().getSchema();
            mIterator.mFields = mIterator.mSchema.getFields();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Iterator<T> iterator() {
        return mIterator;
    }
    class ModelIterator<T extends Model> implements Iterator<T>
    {
        Class<T> mType;
        Cursor mCursor;
        Schema mSchema;
        Iterable<Field> mFields;

        @Override
        public boolean hasNext() {
            return mCursor.moveToNext();
        }

        @Override
        public T next() {
            try {
                T item = mType.newInstance();
                for(Field field:mFields)
                {
                    java.lang.reflect.Field jf= field.getJavaField();
                    int i = mCursor.getColumnIndex(field.getName());
                    Object value = Utils.getValue(mCursor, field, i);
                    jf.set(item,value);
                }
                return item;
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void remove() {
            mCursor.moveToFirst();
        }
    }
}
