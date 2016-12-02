package in.tanjo.sushi.adapter.holder;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.tanjo.sushi.R;
import in.tanjo.sushi.model.CountableSushi;

public class MainCardViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.main_sushi_name_text_view)
    TextView mNameView;

    @BindView(R.id.main_sushi_price_text_view)
    TextView mPriceView;

    @BindView(R.id.main_sushi_count_text_view)
    TextView mCountView;

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

    public void bind(CountableSushi sushiModel) {
        if (sushiModel != null) {
            mNameView.setText(sushiModel.getName());
            mPriceView.setText(sushiModel.getPriceText());
            mCountView.setText(sushiModel.getCountText());
        }
    }
}
