package in.tanjo.sushi.adapter.holder;

import com.google.common.base.Strings;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.tanjo.sushi.R;
import in.tanjo.sushi.model.License;


public class LicenseViewHolder extends RecyclerView.ViewHolder {

    private static final ButterKnife.Setter VISIBILITY = new ButterKnife.Setter<View, Boolean>() {
        @Override
        public void set(@NonNull View view, Boolean value, int index) {
            view.setVisibility(value ? View.VISIBLE : View.GONE);
        }
    };

    @BindView(R.id.license_view_name)
    TextView nameView;

    @BindView(R.id.license_view_copyright)
    TextView copyrightView;

    @BindView(R.id.license_view_license)
    TextView licenseView;

    @BindView(R.id.license_view_card)
    CardView cardView;

    private int position = -1;

    private Listener listener;

    public LicenseViewHolder(View itemView, Listener listener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.listener = listener;
    }

    public void bind(License license, int position) {
        this.position = position;
        ButterKnife.apply(cardView, VISIBILITY, license != null);
        if (license == null) {
            nameView.setText("");
            copyrightView.setText("");
            licenseView.setText("");
            return;
        }
        ButterKnife.apply(nameView, VISIBILITY, !Strings.isNullOrEmpty(license.getName()));
        ButterKnife.apply(copyrightView, VISIBILITY, !Strings.isNullOrEmpty(license.toCopyright()));
        ButterKnife.apply(licenseView, VISIBILITY, !Strings.isNullOrEmpty(license.toLicense()));
        nameView.setText(license.getName());
        copyrightView.setText(license.toCopyright());
        licenseView.setText(license.toLicense());
    }

    @OnClick(R.id.license_view_card)
    void onLicenseClick(View view) {
        if (listener != null) {
            listener.onLicenseClick(position);
        }
    }

    public interface Listener {

        void onLicenseClick(int position);
    }
}