package in.tanjo.sushi.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.tanjo.sushi.R;
import in.tanjo.sushi.model.AbsNoteModel;

public class NavigationAdapter extends RecyclerView.Adapter<NavigationAdapter.ViewHolder>
    implements View.OnClickListener, View.OnLongClickListener {

  /**
   * ViewHolder
   */
  public static class ViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.navigation_card_view_title) TextView mTitleView;
    @Bind(R.id.navigation_card_view_subtitle) TextView mSubtitleView;

    public ViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
    }

    public TextView getTitleView() {
      return mTitleView;
    }

    public TextView getSubtitleView() {
      return mSubtitleView;
    }
  }

  /**
   * OnMainAdapterItemClickListener
   */
  public interface OnNavigationAdapterItemClickListener {
    void onItemClick(View v, NavigationAdapter adapter, int position, AbsNoteModel noteModel);
    void onItemLongClick(View v, NavigationAdapter adapter, int position, AbsNoteModel noteModel);
  }

  private List<AbsNoteModel> mAbsNoteModelList;
  private RecyclerView mRecyclerView;
  private OnNavigationAdapterItemClickListener mOnNavigationAdapterItemClickListener;

  public NavigationAdapter(List<AbsNoteModel> absNoteModelList) {
    mAbsNoteModelList = absNoteModelList;
  }

  @Override
  public int getItemCount() {
    return mAbsNoteModelList.size();
  }

  @Override
  public void onClick(View v) {
    if (mOnNavigationAdapterItemClickListener != null && mRecyclerView != null) {
      int position = mRecyclerView.getChildAdapterPosition(v);
      AbsNoteModel noteModel = mAbsNoteModelList.get(position);
      mOnNavigationAdapterItemClickListener.onItemClick(v, this, position, noteModel);
    }
   }

  @Override
  public boolean onLongClick(View v) {
    if (mOnNavigationAdapterItemClickListener != null && mRecyclerView != null) {
      int position = mRecyclerView.getChildAdapterPosition(v);
      AbsNoteModel noteModel = mAbsNoteModelList.get(position);
      mOnNavigationAdapterItemClickListener.onItemLongClick(v, this, position, noteModel);
      return true;
    }
    return false;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater
        .from(parent.getContext())
        .inflate(R.layout.navigation_card_view, parent, false);
    v.setOnClickListener(this);
    v.setOnLongClickListener(this);

    return new ViewHolder(v);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    AbsNoteModel noteModel = mAbsNoteModelList.get(position);
    holder.getTitleView().setText(noteModel.getTitle());
    holder.getSubtitleView().setText(noteModel.getId());
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

  public void setOnNavigationAdapterItemClickListener(OnNavigationAdapterItemClickListener listener) {
    mOnNavigationAdapterItemClickListener = listener;
  }
}
