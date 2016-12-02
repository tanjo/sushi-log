package in.tanjo.sushi.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

public class MainFooterCardViewHolder extends RecyclerView.ViewHolder {

    public MainFooterCardViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
