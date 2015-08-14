package in.tanjo.sushi;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.tanjo.sushi.model.CountableSushiModel;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> implements View.OnClickListener, View.OnLongClickListener {

  private List<CountableSushiModel> mSushiModelList;
  private RecyclerView mRecyclerView;
  private OnMainAdapterItemClickListener mOnMainAdapterItemClickListener;

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

  public void setOnMainAdapterItemClickListener(OnMainAdapterItemClickListener onMainAdapterItemClickListener) {
    mOnMainAdapterItemClickListener = onMainAdapterItemClickListener;
  }

  @Override
  public void onClick(View v) {
    if (mRecyclerView == null) {
      return;
    }
    if (mOnMainAdapterItemClickListener != null) {
      int position = mRecyclerView.getChildAdapterPosition(v);
      CountableSushiModel countableSushiModel = mSushiModelList.get(position);
      mOnMainAdapterItemClickListener.onItemClick(v, this, position, countableSushiModel);
    }
  }

  @Override
  public boolean onLongClick(View v) {
    if (mRecyclerView == null) {
      return false;
    }
    if (mOnMainAdapterItemClickListener != null) {
      int position = mRecyclerView.getChildAdapterPosition(v);
      CountableSushiModel countableSushiModel = mSushiModelList.get(position);
      mOnMainAdapterItemClickListener.onItemLongClick(v, this, position, countableSushiModel);
      return true;
    }
    return false;
  }

  public MainAdapter(List<CountableSushiModel> sushiModelList) {
    mSushiModelList = sushiModelList;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_card_view, parent, false);
    v.setOnClickListener(this);
    v.setOnLongClickListener(this);
    return new ViewHolder(v);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    CountableSushiModel sushiModel = mSushiModelList.get(position);
    holder.mNameView.setText(sushiModel.getName());
    holder.mPriceView.setText(sushiModel.getPriceText());
    holder.mCountView.setText(sushiModel.getCountText());
  }

  @Override
  public int getItemCount() {
    return mSushiModelList.size();
  }

  /**
   * ViewHolder
   */
  public static class ViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.main_sushi_name_text_view)
    public TextView mNameView;

    @Bind(R.id.main_sushi_price_text_view)
    public TextView mPriceView;

    @Bind(R.id.main_sushi_count_text_view)
    public TextView mCountView;

    public ViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
      setupTypeface(view);
    }

    private void setupTypeface(View view) {
      Typeface tf = Typeface.createFromAsset(view.getContext().getAssets(), "Ounen-mouhitsu.ttf");
      mNameView.setTypeface(tf);
      mPriceView.setTypeface(tf);
      mCountView.setTypeface(tf);
    }
  }

  public static interface OnMainAdapterItemClickListener {
    public void onItemClick(View v, MainAdapter adapter, int position, CountableSushiModel countableSushiModel);

    public void onItemLongClick(View v, MainAdapter adapter, int position, CountableSushiModel countableSushiModel);
  }
}
