package in.tanjo.sushi.adapter.holder;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.tanjo.sushi.R;
import in.tanjo.sushi.model.CountableSushiModel;

public class MainCardViewHolder extends RecyclerView.ViewHolder {

  @Bind(R.id.main_sushi_name_text_view) TextView mNameView;
  @Bind(R.id.main_sushi_price_text_view) TextView mPriceView;
  @Bind(R.id.main_sushi_count_text_view) TextView mCountView;

  public MainCardViewHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
    init(itemView);
  }

  private void init(View itemView) {
    Typeface tf = Typeface.createFromAsset(itemView.getContext().getAssets(), "Ounen-mouhitsu.ttf");
    mNameView.setTypeface(tf);
    mPriceView.setTypeface(tf);
    mCountView.setTypeface(tf);
  }

  public void bind(CountableSushiModel sushiModel) {
    if (sushiModel != null) {
      mNameView.setText(sushiModel.getName());
      mPriceView.setText(sushiModel.getPriceText());
      mCountView.setText(sushiModel.getCountText());
    }
  }
}
