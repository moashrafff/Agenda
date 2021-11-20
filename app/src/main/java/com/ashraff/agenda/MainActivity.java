package com.ashraff.agenda;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.ashraff.agenda.Adapter.RecyclerViewAdapter;
import com.ashraff.agenda.Model.TaskModel;
import com.ashraff.agenda.Utils.DataBaseAccess;
import com.ashraff.agenda.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements DialogCloseListener {
    RecyclerView rv ;
    FloatingActionButton fab ;
    ArrayList<TaskModel> tasks;
    RecyclerViewAdapter adapter;
    DataBaseAccess db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        rv = findViewById(R.id.rv_tasks);
        fab = findViewById(R.id.fab);
        tasks = new ArrayList<>();


        db = DataBaseAccess.getInstance(this);
        db.open();
        tasks = db.getAllTasks();
        Collections.reverse(tasks);
        db.close();

        adapter = new RecyclerViewAdapter(tasks,this);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerItemTouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(rv);





        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.newInstance().show(getSupportFragmentManager(),AddNewTask.TAG);

            }
        });




    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        tasks = db.getAllTasks();
        adapter.setTasks(tasks);
        Collections.reverse(tasks);
        adapter.notifyDataSetChanged();
    }
}