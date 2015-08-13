package in.tanjo.sushi;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
  private String[] mDataset;

  public static class ViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.main_sushi_name_text_view)
    public TextView mNameView;

    @Bind(R.id.main_sushi_price_text_view)
    public TextView mPriceView;

    public ViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
      setupTypeface(view);
    }

    private void setupTypeface(View view) {
      Typeface tf = Typeface.createFromAsset(view.getContext().getAssets(), "Ounen-mouhitsu.ttf");
      mNameView.setTypeface(tf);
      mPriceView.setTypeface(tf);
    }
  }

  public MainAdapter(String[] dataset) {
    mDataset = dataset;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_card_view, parent, false);
    return new ViewHolder(v);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    holder.mNameView.setText(mDataset[position]);
    holder.mPriceView.setText("108円");
  }

  @Override
  public int getItemCount() {
    return mDataset.length;
  }
}
