package com.trackrepository.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.trackrepository.Activities.OpenRepo;
import com.trackrepository.R;
import com.trackrepository.interfaces.OnRecyclerViewClickListener;
import com.trackrepository.model.RepoListResponse;

import java.util.List;

public class ReposListAdapter extends RecyclerView.Adapter<ReposListAdapter.MyViewHolder>{// implements Filterable {
    private Context context;
    private OnRecyclerViewClickListener clickListener;
    private List<RepoListResponse> listRepo;
    private Activity mActivity;

    public ReposListAdapter(Context context, List<RepoListResponse> listRepo) {
        this.context = context;
        this.listRepo = listRepo;
    }

    public void setClickListener(OnRecyclerViewClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView reponame,discription;
        ImageView share;
        LinearLayout repo_lin;

        public MyViewHolder(View view,Activity mActivity) {
            super(view);

            reponame = view.findViewById(R.id.repo_name);
            discription = view.findViewById(R.id.discription);
            share = view.findViewById(R.id.share);
            repo_lin = view.findViewById(R.id.repo_lin);
            itemView.setOnClickListener(this);
            mActivity = mActivity;

        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) {
                clickListener.onClick(view, getAdapterPosition());
            }
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.repos_list_items, parent, false);

        return new MyViewHolder(itemView, mActivity);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        RepoListResponse repoListResponse = listRepo.get(position);

        holder.reponame.setText(""+repoListResponse.getName());
        holder.discription.setText(""+repoListResponse.getDescription());

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Log.d("holder", String.valueOf(holder.getAdapterPosition()));
                    RepoListResponse al = listRepo.get(holder.getAdapterPosition());
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, al.getName()+"\n"+al.getUrl());
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(shareIntent);
            }
        });

        holder.repo_lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RepoListResponse al = listRepo.get(holder.getAdapterPosition());
                Intent intent = new Intent(context, OpenRepo.class);
                intent.putExtra("repourl",al.getUrl());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemViewType(int position)
    {
        return position;
    }

    @Override
    public int getItemCount() {
        return listRepo.size();
    }
}