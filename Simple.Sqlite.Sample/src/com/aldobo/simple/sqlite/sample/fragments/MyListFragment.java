package com.aldobo.simple.sqlite.sample.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.aldobo.simple.sqlite.SQLiteManager;
import com.aldobo.simple.sqlite.sample.R;
import com.aldobo.simple.sqlite.sample.models.Person;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class MyListFragment extends BaseFragment{

    ListView mListUsers;
    SQLiteManager mSQLiteManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSQLiteManager = new SQLiteManager(getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list,container,false);
        mListUsers=(ListView)view.findViewById(R.id.list_users);

        //Adding some people
        addPerson();
        addPerson();
        addPerson();
        addPerson();
        //Listing all people
        List<Person> people= getPeople();
        Person p1 = people.get(0);
        Person p2 = people.get(1);
        Person p3 = people.get(3);
        //Deleting some of them
        deletePerson( p1);
        deletePerson(p2._id);
        //Updating a person
        updatePerson(p3);
        //Using get
        getPerson(p3._id);




        //Filling the list
        mListUsers.setAdapter(new ArrayAdapter<Person>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, getPeople()));
        return view;

    }
    public void addPerson()
    {
        Person p = new Person();
        p.name = "Pepe";
        p.surname = "Perez Perez";
        p.age = 21;
        p.avatar = new byte[]{1,2,3,4,5};
        p.androidDevelop = Boolean.TRUE;
        p.description = "Small text";
        p.money = 100.56f;
        p.size = 100.50;
        p.real = 20.4;
        mSQLiteManager.insert(p);
        this.toastInfo("Inserting a new person");
    }

    public void deletePerson(Integer id)
    {
        mSQLiteManager.delete(Person.class,id);
        this.toastInfo("Delete person by ID");
    }

    public void updatePerson(Person p)
    {
        p.name = "Other name";
        mSQLiteManager.update(p);
        this.toastInfo("Update person name");
    }

    public void deletePerson(Person p)
    {
        mSQLiteManager.delete(p);
        this.toastInfo("Delete person by entity");
    }

    public Person getPerson(Integer id)
    {
        Person p = mSQLiteManager.get(Person.class,id);
        this.toastInfo("Get a person");
        return p;
    }

    public List<Person> getPeople()
    {
        Iterable<Person> people= mSQLiteManager.excecute(Person.class,"SELECT * FROM [Person]");
        List<Person> personList = new ArrayList<Person>();
        for(Person p : people)
        {
            personList.add(p);
        }
        return personList;
    }


}
