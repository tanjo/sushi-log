package in.tanjo.sushi.listener;

import android.view.View;

import static android.support.v7.widget.RecyclerView.Adapter;

public interface OnRecyclerViewAdapterItemClickListener<T> {

    void onItemClick(View v, Adapter adapter, int position, T model);

    void onItemLongClick(View v, Adapter adapter, int position, T model);
}
