package com.apr.dbflowexample;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.apr.dbflowexample.adapter.UserAdapter;
import com.apr.dbflowexample.dao.UserDao;
import com.apr.dbflowexample.model.User;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listViewUser;
    UserAdapter adapter;
    List<User> usuarios;
    final UserDao userDao = new UserDao();
    User userTemp;
    final Context c = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
                View mView = layoutInflaterAndroid.inflate(R.layout.user_input_dialog_box, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
                alertDialogBuilderUserInput.setView(mView);

                final EditText userInputDialogName = (EditText) mView.findViewById(R.id.userInputDialogName);
                final EditText userInputDialogAge = (EditText) mView.findViewById(R.id.userInputDialogAge);
                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                User user = new User();
                                user.setName(userInputDialogName.getText().toString());
                                user.setAge(Integer.parseInt(userInputDialogAge.getText().toString()));
                                userDao.addUser(user);
                                usuarios.add(user);
                                adapter.refresh();
                            }
                        })

                        .setNegativeButton("Cancelar",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                });

                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();
            }
        });

        // Get List User from DB
        usuarios = userDao.findAll();

        // Get ListView object from xml
        listViewUser = (ListView) findViewById(R.id.listUser);

        // Define a new Adapter
        //create adapter object
        adapter = new UserAdapter(this, usuarios);

        // Assign adapter to ListView
        listViewUser.setAdapter(adapter);

        ImageButton btnFiltro = (ImageButton) findViewById(R.id.btnFiltro);
        btnFiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
                View mView = layoutInflaterAndroid.inflate(R.layout.filter_user_input_dialog_box, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
                alertDialogBuilderUserInput.setView(mView);

                final EditText filtroInputDialogAge = (EditText) mView.findViewById(R.id.filterUserInputDialogAge);
                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton("Filtrar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                int edad = (Integer.parseInt(filtroInputDialogAge.getText().toString()));
                                List<User> busqueda = userDao.findByAge(edad);
                                if (!busqueda.isEmpty()) {
                                    adapter = new UserAdapter(c, busqueda);
                                    // Assign adapter to ListView
                                    listViewUser.setAdapter(adapter);
                                    adapter.refresh();
                                }
                            }
                        })

                        .setNegativeButton("Limpiar",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        adapter = new UserAdapter(c, usuarios);
                                        // Assign adapter to ListView
                                        listViewUser.setAdapter(adapter);
                                        adapter.refresh();
                                        dialogBox.cancel();
                                    }
                                });

                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_crud_user) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
