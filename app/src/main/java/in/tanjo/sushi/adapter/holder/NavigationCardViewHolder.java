package in.tanjo.sushi.adapter.holder;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.tanjo.sushi.R;
import in.tanjo.sushi.model.AbsNoteModel;

public class NavigationCardViewHolder extends ViewHolder {

    @BindView(R.id.navigation_card_view_title)
    TextView mTitleView;

    @BindView(R.id.navigation_card_view_subtitle)
    TextView mSubtitleView;

    public NavigationCardViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(AbsNoteModel noteModel) {
        if (noteModel != null) {
            mTitleView.setText(noteModel.getTitle());
            mSubtitleView.setText(noteModel.getId());
        }
    }

}
