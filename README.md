## Simple Android SQLite

Simple Sqlite library help you to manage database creation, version, query and others very simple. This provide developers with a simple way to define the databases based on classes. 
Rather than implementing the onCreate() and onUpgrade() methods to execute a bunch of SQL statements, developers simply define the schema code-first. 

It is implemented as an extension to SQLiteOpenHelper, and map sql queries to objects classes.


## Basics (Step by Step)

 * Import Simple.Android from project or add .jar (recommended)
 * Create models (See below)
 * Configure the SQliteDatabase in Aplication class (See below)
 * Ready to use 

## Models

The models may extend from com.aldobo.simple.sqlite.entities.Model

```java
public class Person extends Model 
{
	...
}
```
Add properties to this model with the annotations @Column. 

```java
public class Person extends Model {

	...
    @Column(type = SQLiteType.VARCHAR,length = 20)
    public String name;

    @Column(type = SQLiteType.INT,nullable = true)
    public Integer age;

    @Column(type = SQLiteType.BLOB,nullable = true)
    public byte[] avatar;
    ...
}
```
Supported types:

```java
	public enum SQLiteType {

	    INT("INTEGER"), 		//Integer
	    TEXT("TEXT"),			//String
	    REAL("REAL"),			//Double
	    BLOB("BLOB"),			//byte[]
	    NONE(null),		
	    VARCHAR("VARCHAR"),		//String
	    BOOLEAN("BOOLEAN"),		//Boolean
	    FLOAT("FLOAT"),			//Float
	    DOUBLE("DOUBLE");		//Double
	    	
	}
``` 




### SQLiteConfiguration

Configure you database with SQLiteConfiguration. This configuration needs to be doing in the application class. So, define your application class and configure your database:

Configuration:
	+ Database name
	+ Version
	+ Tables
	+ Extensions (Extensions are the way to add behavior to SQLiteOpenHelper across onCreate() and onUpgrade() )

```java
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SQLiteConfiguration configuration = new SQLiteConfiguration.Builder()
                .setDatabaseName("my_database.db")
                .setDatabaseVersion(0)
                .registerTable(Person.class)
                .registerTable(OtherModel.class)
                .build();
        SQLiteManager.setConfiguration(configuration);

    }
}
```
Add to AndroidManifest.xml

```xml
	<application
	...
		android:name=".MainApplication"
	...
	 </application>

```

## SQLiteManager

```java
	...
	SQLiteManager mSQLiteManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSQLiteManager = new SQLiteManager(getActivity().getApplicationContext());
    }
    ...
```

### get()

```java
	Person p= mSQLiteManager.get(Person.class,id);
```

### excecute()

```java
	 Iterable<Person> people= mSQLiteManager.excecute(Person.class,"SELECT * FROM [Person]");
	 mSQLiteManager.excecute("SELECT count(*) FROM [Person]")
```

### insert()

```java
	 Person p = new Person();
	 ...
	 mSQLiteManager.insert(p);
```

### beginTransaction() and endTransaction()

```java
 	mSQLiteManager.beginTransaction();
 	//Code here
 	mSQLiteManager.endTransaction();
```

### delete()

```java
 	mSQLiteManager.delete(Person.class,id);
 	//or
 	Person p;
 	mSQLiteManager.delete(p);
```

### update()

```java
	Person p;
	p.name = "Alex"
	mSQLiteManager.update(p);
```


## Issues

Have a bug? Please create an issue here on GitHub!

https://github.com/AlejandroDominguez/simple-sqlite/issues

## Versioning

For transparency and insight into our release cycle, releases will be numbered with the follow format:

`<major>.<minor>.<patch>`

And constructed with the following guidelines:

* Breaking backwards compatibility bumps the major
* New additions without breaking backwards compatibility bumps the minor
* Bug fixes and misc changes bump the patch

For more information on semantic versioning, please visit http://semver.org/.

## Authors

**Alejandro Dominguez**

+ https://github.com/AlejandroDominguez

## License

Copyright 2014.

Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0