package in.tanjo.sushi.adapter;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.tanjo.sushi.R;
import in.tanjo.sushi.model.CountableSushiModel;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder>
    implements View.OnClickListener, View.OnLongClickListener {

  /**
   * ViewHolder
   */
  public static class ViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.main_sushi_name_text_view) TextView mNameView;
    @Bind(R.id.main_sushi_price_text_view) TextView mPriceView;
    @Bind(R.id.main_sushi_count_text_view) TextView mCountView;

    public ViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
      setupTypeface(view);
    }

    public TextView getNameView() {
      return mNameView;
    }

    public TextView getPriceView() {
      return mPriceView;
    }

    public TextView getCountView() {
      return mCountView;
    }

    private void setupTypeface(View view) {
      Typeface tf = Typeface.createFromAsset(view.getContext().getAssets(), "Ounen-mouhitsu.ttf");
      mNameView.setTypeface(tf);
      mPriceView.setTypeface(tf);
      mCountView.setTypeface(tf);
    }
  }

  /**
   * OnMainAdapterItemClickListener
   */
  public interface OnMainAdapterItemClickListener {
    void onItemClick(View v, MainAdapter adapter, int position, CountableSushiModel countableSushiModel);
    void onItemLongClick(View v, MainAdapter adapter, int position, CountableSushiModel countableSushiModel);
  }

  private List<CountableSushiModel> mSushiModelList;
  private RecyclerView mRecyclerView;
  private OnMainAdapterItemClickListener mOnMainAdapterItemClickListener;

  /**
   * コンストラクタ
   */
  public MainAdapter(List<CountableSushiModel> sushiModelList) {
    mSushiModelList = sushiModelList;
  }

  @Override
  public int getItemCount() {
    return mSushiModelList.size();
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    CountableSushiModel sushiModel = mSushiModelList.get(position);
    holder.getNameView().setText(sushiModel.getName());
    holder.getPriceView().setText(sushiModel.getPriceText());
    holder.getCountView().setText(sushiModel.getCountText());
    if (sushiModel.getPrice() > 0) {
      holder.getPriceView().setVisibility(View.VISIBLE);
    } else {
      holder.getPriceView().setVisibility(View.GONE);
    }
  }

  @Override
  public void onClick(View v) {
    if (mOnMainAdapterItemClickListener != null && mRecyclerView != null) {
      int position = mRecyclerView.getChildAdapterPosition(v);
      CountableSushiModel countableSushiModel = mSushiModelList.get(position);
      mOnMainAdapterItemClickListener.onItemClick(v, this, position, countableSushiModel);
    }
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater
        .from(parent.getContext())
        .inflate(R.layout.main_card_view, parent, false);
    v.setOnClickListener(this);
    v.setOnLongClickListener(this);

    return new ViewHolder(v);
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
  public boolean onLongClick(View v) {
    if (mOnMainAdapterItemClickListener != null && mRecyclerView != null) {
      int position = mRecyclerView.getChildAdapterPosition(v);
      CountableSushiModel countableSushiModel = mSushiModelList.get(position);
      mOnMainAdapterItemClickListener.onItemLongClick(v, this, position, countableSushiModel);
      return true;
    }
    return false;
  }

  public void setOnMainAdapterItemClickListener(OnMainAdapterItemClickListener listener) {
    mOnMainAdapterItemClickListener = listener;
  }
}
