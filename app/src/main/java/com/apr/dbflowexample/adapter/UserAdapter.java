package com.apr.dbflowexample.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.apr.dbflowexample.R;
import com.apr.dbflowexample.dao.UserDao;
import com.apr.dbflowexample.model.User;

import java.util.List;

/**
 * Created by AntonioPC on 04/12/2016.
 */

public class UserAdapter extends BaseAdapter {

    private static final String TAG = "UserAdapter";
    private final UserDao userDao = new UserDao();


    List<User> data;
    private Context context; //context

    public UserAdapter(Context context, List<User> items) {
        this.context = context;
        this.data = items;

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public User getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        Log.v(TAG, "in getView for position " + position + ", convertView is "
                + ((convertView == null) ? "null" : "being recycled"));

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.user_adapter, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final User currentItem = getItem(position);
        viewHolder.tvId.setText(String.valueOf(currentItem.getId()));
        viewHolder.tvNombre.setText(String.valueOf(currentItem.getName()));
        viewHolder.tvEdad.setText(String.valueOf(currentItem.getAge()));

        viewHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(context);
                View mView = layoutInflaterAndroid.inflate(R.layout.user_input_dialog_box, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(context);
                alertDialogBuilderUserInput.setView(mView);

                final EditText userInputDialogName = (EditText) mView.findViewById(R.id.userInputDialogName);
                userInputDialogName.setText(String.valueOf(currentItem.getName()));
                final EditText userInputDialogAge = (EditText) mView.findViewById(R.id.userInputDialogAge);
                userInputDialogAge.setText(String.valueOf(currentItem.getAge()));
                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                currentItem.setName(userInputDialogName.getText().toString());
                                currentItem.setAge(Integer.parseInt(userInputDialogAge.getText().toString()));
                                userDao.updateUser(currentItem);
                                data.remove(currentItem);
                                data.add(currentItem);
                                notifyDataSetChanged();
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

        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Eliminar el usuario?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int choice) {
                                switch (choice) {
                                    case DialogInterface.BUTTON_POSITIVE:
                                        if (currentItem != null) {
                                            userDao.deleteUser(currentItem);
                                            data.remove(currentItem);
                                            notifyDataSetChanged();
                                        }
                                        break;
                                    case DialogInterface.BUTTON_NEGATIVE:
                                        break;
                                }
                            }
                        })
                        .setNegativeButton("No", null).show();

            }
        });

        return convertView;
    }

    //ViewHolder inner class
    private class ViewHolder {
        TextView tvId;
        TextView tvNombre;
        TextView tvEdad;
        ImageButton btnEdit;
        ImageButton btnDelete;

        public ViewHolder(View view) {
            tvId = (TextView) view
                    .findViewById(R.id.tvIdUsuario);
            tvNombre = (TextView) view
                    .findViewById(R.id.tvNombreUsuario);
            tvEdad = (TextView) view
                    .findViewById(R.id.tvEdadUsuario);
            btnEdit = (ImageButton) view
                    .findViewById(R.id.btnEdit);
            btnDelete = (ImageButton) view
                    .findViewById(R.id.btnDelete);
        }
    }

    public void refresh() {
        notifyDataSetChanged();
    }


}

