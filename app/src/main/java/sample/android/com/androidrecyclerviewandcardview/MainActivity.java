package sample.android.com.androidrecyclerviewandcardview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<String> names;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private int counter = 0;
    private int page_number = 1;

    private static int firstVisibleInListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        names = getNames();
        mRecyclerView = findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);

        mAdapter = new MyAdapter(names, R.layout.recycler_view_item, new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String name, int position) {
                Toast.makeText(MainActivity.this, name + "-" + position, Toast.LENGTH_LONG).show();
            }
        }, new MyAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getMoreNames();
            }
        });

        // Lo usamos en caso de que sepamos que el layout no va a cambiar de tamaño, mejorando la performance
        mRecyclerView.setHasFixedSize(true);
        // Añade un efecto por defecto, si le pasamos null lo desactivamos por completo
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // Enlazamos el layout manager y adaptor directamente al recycler view
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        //mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
           //@Override
           // public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
              /*  super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = mLayoutManager.getChildCount();
                int totalItemCount = mLayoutManager.getItemCount();
                int pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();
                if (pastVisibleItems + visibleItemCount >= totalItemCount) {
                    Toast.makeText(MainActivity.this, "Last", Toast.LENGTH_SHORT).show();
                }


               *//* if (dy > 0) {

                    if (getMoreNames() != null || getMoreNames().size() == 0) {

                    }

                }*//*
            }
        });*/
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    List<String> moreNames = getMoreNames();
                    if(moreNames != null || moreNames.size() == 0) {
                        names.addAll(moreNames);
                        mAdapter.notifyItemInserted(mLayoutManager.getItemCount()+1);
                    }
                }
            }
        });
    }

    /*@Override
    public boolean onTouchEvent (MotionEvent event) {
       int action = event.getActionMasked();

       switch (action) {
           case (MotionEvent.ACTION_UP):
               return true;
           default:
               return false;
       }
    }*/

    private List<String> getNames() {
        List<String> items = new ArrayList<>();
        for (int i = 0; i < 20 ; i++) {
            items.add("name" + i);
        }
        return items;
    }

    private List<String> getMoreNames() {
        List<String> moreNames = new ArrayList<>();
        for (int i = 0; i < 10 ; i++) {
            moreNames.add("added" + i);
        }
        return moreNames;
    }
}
