package in.tanjo.sushi.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.tanjo.sushi.R;
import in.tanjo.sushi.model.store.StoreModel;

public class StoreAdapter extends ArrayAdapter<StoreModel> {
  private static final String TAG = StoreAdapter.class.getSimpleName();

  private LayoutInflater mLayoutInflater;

  public StoreAdapter(Context context, int resource, List<StoreModel> objects) {
    super(context, resource, objects);
    mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  public static class ViewHolder {
    @Bind(R.id.store_list_cell_textview) TextView mTextView;

    public ViewHolder(View v) {
      ButterKnife.bind(this, v);
      setupTypeface(v);
    }

    private void setupTypeface(View v) {
      Typeface tf = Typeface.createFromAsset(v.getContext().getAssets(), "Ounen-mouhitsu.ttf");
      mTextView.setTypeface(tf);
    }

    public TextView getTextView() {
      return mTextView;
    }
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder holder;

    if (convertView == null) {
      convertView = mLayoutInflater.inflate(R.layout.store_list_cell, null);
      holder = new ViewHolder(convertView);
      convertView.setTag(holder);
    } else {
      holder = (ViewHolder) convertView.getTag();
    }

    StoreModel storeModel = getItem(position);
    if (storeModel != null && holder.getTextView() != null) {
      holder.getTextView().setText(storeModel.getName());
    }

    return convertView;
  }
}
