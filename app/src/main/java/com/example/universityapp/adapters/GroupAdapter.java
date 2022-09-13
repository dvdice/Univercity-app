package com.example.universityapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.universityapp.R;
import com.example.universityapp.updateActivities.UpdateGroupActivity;

import java.util.List;

import com.example.universityapp.DB.DBGroups;
import com.example.universityapp.Entities.Group;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder>{
    private List<Group> groupList;
    private Context context;
    private RecyclerView recyclerView;

    public GroupAdapter(List<Group> groupList, Context context, RecyclerView recyclerView) {
        this.groupList = groupList;
        this.context = context;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public GroupAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_group, parent, false);
        return new GroupAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupAdapter.ViewHolder holder, int position) {
        final Group group = groupList.get(position);
        holder.itemGroupID.setText(String.valueOf(group.getGroupNumber()));
        holder.nameOfFaculty.setText(String.valueOf(group.getNameOfFaculty()));

        Runnable r = ()->{
            holder.view.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Подтвердите действие");
                builder.setMessage("Удалить?");
                builder.setPositiveButton("Изменить", (dialog, which) -> {
                    goToUpdateActivity(group.get_id());
                });
                builder.setNeutralButton("удалить", (dialog, which) -> {
                    DBGroups dbHelper = new DBGroups(context);
                    dbHelper.deleteGroup(group.get_id());
                    groupList.remove(position);
                    recyclerView.removeViewAt(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, groupList.size());
                    notifyDataSetChanged();
                });
                builder.setNegativeButton(R.string.cancel_str, (dialog, which) -> dialog.dismiss());
                builder.create().show();
            });
        };

        Thread myThread = new Thread(r,"MyThread");
        myThread.start();
    }
    private void goToUpdateActivity(long groupId){
        Intent goToUpdate = new Intent(context, UpdateGroupActivity.class);
        goToUpdate.putExtra("GROUPID", groupId);
        context.startActivity(goToUpdate);
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameOfFaculty, itemGroupID;
        public View view;
        public ViewHolder(View v) {
            super(v);
            this.view = v;
            nameOfFaculty = (TextView) v.findViewById(R.id.nameOfFaculty);
            itemGroupID = (TextView) v.findViewById(R.id.item_groupID);
        }
    }
}
