package in.tanjo.sushi.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import in.tanjo.sushi.R;
import in.tanjo.sushi.adapter.holder.MainCardViewHolder;
import in.tanjo.sushi.adapter.holder.MainFooterCardViewHolder;
import in.tanjo.sushi.listener.OnRecyclerViewAdapterItemClickListener;
import in.tanjo.sushi.model.CountableSushi;

public class MainAdapter extends Adapter<ViewHolder>
        implements View.OnClickListener, View.OnLongClickListener {

    private List<CountableSushi> mSushiModelList;

    private RecyclerView mRecyclerView;

    private OnRecyclerViewAdapterItemClickListener<CountableSushi> mOnRecyclerViewAdapterItemClickListener;

    public MainAdapter(List<CountableSushi> sushiModelList) {
        mSushiModelList = sushiModelList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == 101) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_card_view_footer, parent, false);
            return new MainFooterCardViewHolder(v);
        }

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_card_view, parent, false);

        v.setOnClickListener(this);
        v.setOnLongClickListener(this);

        return new MainCardViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mSushiModelList.size() == position) {
            return;
        }

        CountableSushi countableSushi = mSushiModelList.get(position);

        if (holder != null && holder instanceof MainCardViewHolder) {
            MainCardViewHolder viewHolder = (MainCardViewHolder) holder;
            viewHolder.bind(countableSushi);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mSushiModelList.size() == position) {
            return 101;
        }
        return 100;
    }

    @Override
    public int getItemCount() {
        return mSushiModelList.size() + 1;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        mRecyclerView = null;
    }

    @Override
    public void onClick(View v) {
        if (mOnRecyclerViewAdapterItemClickListener != null && mRecyclerView != null) {
            int position = mRecyclerView.getChildAdapterPosition(v);
            CountableSushi countableSushiModel = mSushiModelList.get(position);
            mOnRecyclerViewAdapterItemClickListener.onItemClick(v, this, position, countableSushiModel);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (mOnRecyclerViewAdapterItemClickListener != null && mRecyclerView != null) {
            int position = mRecyclerView.getChildAdapterPosition(v);
            CountableSushi countableSushiModel = mSushiModelList.get(position);
            mOnRecyclerViewAdapterItemClickListener.onItemLongClick(v, this, position, countableSushiModel);
            return true;
        }
        return false;
    }

    public void setOnRecyclerViewAdapterItemClickListener(
            OnRecyclerViewAdapterItemClickListener<CountableSushi> listener) {
        mOnRecyclerViewAdapterItemClickListener = listener;
    }
}
