package com.aldobo.simple.sqlite.entities;

import com.aldobo.simple.sqlite.tags.Column;
import com.aldobo.simple.sqlite.tags.Table;
import com.aldobo.simple.sqlite.utils.Utils;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class Schema {

    private String mTableName;
    private List<Field> mFields;
    private Field mPrimaryKey;
    private String[] mColumns;

    private Schema(Schema.Builder builder) {
        mTableName = builder.mTableName;
        mFields = builder.mFields;
        mPrimaryKey = builder.mPrimaryKey;
    }

    public String getTableName() {
        return mTableName;
    }

    public Iterable<Field> getFields() {
        return mFields;
    }

    public Field getField(int index) {
        return mFields.get(index);
    }

    public Field getPrimaryKey() {
        return mPrimaryKey;
    }

    public String[] getColumns() {
        if (mColumns != null)
            return mColumns;
        mColumns = new String[mFields.size()];
        for (int i = 0; i < mFields.size(); i++)
            mColumns[i] = mFields.get(i).getName();
        return mColumns;
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", getTableName(), getColumns().length);
    }

    public static class Builder {
        private String mTableName;
        private List<Field> mFields;
        private Field mPrimaryKey;

        public Builder() {
            mFields = new ArrayList<Field>();
        }

        public Builder setTableName(String tableName) {
            mTableName = tableName;
            return this;
        }

        public Builder addField(Field field) {
            if (field.isPrimaryKey()) {
                if (mPrimaryKey != null)
                    throw new InvalidParameterException("Primary key is already allocated");
                mPrimaryKey = field;
            }
            mFields.add(field);
            return this;
        }

        public Schema build() {
            return new Schema(this);
        }

        public static <T> Schema initialize(Class<T> model) {
            Schema.Builder builder = new Schema.Builder();
            Table annotation = model.getAnnotation(Table.class);
            String tableName;
            if (annotation == null)
                tableName = model.getSimpleName();
            else
                tableName = annotation.tableName();
            builder.setTableName(tableName);

            for (java.lang.reflect.Field f : model.getFields()) {
                Column fieldAnnotation = f.getAnnotation(Column.class);
                if (fieldAnnotation == null)
                    continue;
                Field.Builder fieldBuilder = new Field.Builder();
                //Java field
                fieldBuilder.setJavaField(f);
                //Type
                fieldBuilder.setType(fieldAnnotation.type() == SQLiteType.NONE ?
                        Utils.getSQLiteType(f.getType()):
                        fieldAnnotation.type()
                );
                //Auto increment
                if (fieldAnnotation.autoIncrement())
                    fieldBuilder.setAsAutoIncrement();
                //Null
                if (fieldAnnotation.nullable())
                    fieldBuilder.setNullable();
                //PrimaryKey
                if (fieldAnnotation.primaryKey())
                    fieldBuilder.setAsPrimaryKey();
                //Length
                fieldBuilder.setLength(fieldAnnotation.length());
                //Default value
                fieldBuilder.setSQliteDefaultValue(fieldAnnotation.defaultValue());
                //Name TODO: Let users modify name
                fieldBuilder.setName(f.getName());

                builder.addField(fieldBuilder.build());
            }
            return builder.build();
        }
    }
}


