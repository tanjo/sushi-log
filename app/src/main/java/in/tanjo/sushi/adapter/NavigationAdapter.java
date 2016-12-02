package in.tanjo.sushi.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import in.tanjo.sushi.R;
import in.tanjo.sushi.adapter.holder.NavigationCardViewHolder;
import in.tanjo.sushi.listener.OnRecyclerViewAdapterItemClickListener;
import in.tanjo.sushi.model.AbsNoteModel;

public class NavigationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements View.OnClickListener, View.OnLongClickListener {

    private List<AbsNoteModel> mAbsNoteModelList;

    private RecyclerView mRecyclerView;

    private OnRecyclerViewAdapterItemClickListener<AbsNoteModel> mOnRecyclerViewAdapterItemClickListener;

    public NavigationAdapter(List<AbsNoteModel> absNoteModelList) {
        mAbsNoteModelList = absNoteModelList;
    }

    @Override
    public void onClick(View v) {
        if (mOnRecyclerViewAdapterItemClickListener != null && mRecyclerView != null) {
            int position = mRecyclerView.getChildAdapterPosition(v);
            AbsNoteModel noteModel = mAbsNoteModelList.get(position);
            mOnRecyclerViewAdapterItemClickListener.onItemClick(v, this, position, noteModel);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (mOnRecyclerViewAdapterItemClickListener != null && mRecyclerView != null) {
            int position = mRecyclerView.getChildAdapterPosition(v);
            AbsNoteModel noteModel = mAbsNoteModelList.get(position);
            mOnRecyclerViewAdapterItemClickListener.onItemLongClick(v, this, position, noteModel);
            return true;
        }
        return false;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.navigation_card_view, parent, false);
        v.setOnClickListener(this);
        v.setOnLongClickListener(this);

        return new NavigationCardViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AbsNoteModel noteModel = mAbsNoteModelList.get(position);

        if (holder != null && holder instanceof NavigationCardViewHolder) {
            NavigationCardViewHolder viewHolder = (NavigationCardViewHolder) holder;
            viewHolder.bind(noteModel);
        }
    }

    @Override
    public int getItemCount() {
        return mAbsNoteModelList.size();
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

    public void setOnRecyclerViewAdapterItemClickListener(OnRecyclerViewAdapterItemClickListener<AbsNoteModel> listener) {
        mOnRecyclerViewAdapterItemClickListener = listener;
    }
}
