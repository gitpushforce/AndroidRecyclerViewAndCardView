package sample.android.com.androidrecyclerviewandcardview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    private List<String> names;
    private int layout;
    private OnItemClickListener itemClickListener;
    private OnLoadMoreListener onLoadMoreListener;

    public MyAdapter(List<String> names, int layout, OnItemClickListener listener, OnLoadMoreListener loadMoreListener) {
        this.names = names;
        this.layout = layout;
        this.itemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(names.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewName;


        public ViewHolder(View itemView) {
            super(itemView);
            this.textViewName = itemView.findViewById(R.id.textViewName);
        }

        public void bind(final String name, final OnItemClickListener listener) {
            this.textViewName.setText(name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(name, getAdapterPosition());
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(String name, int position);

    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }
}
