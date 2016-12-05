package in.tanjo.sushi;

import com.google.common.base.Strings;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import in.tanjo.sushi.adapter.LicenseAdapter;
import in.tanjo.sushi.model.License;

public class LicenseActivity extends AbsActivity implements LicenseAdapter.Listener {

    @BindView(R.id.activity_license_recyclerview)
    RecyclerView recyclerView;

    public static void startActivity(Activity activity) {
        Intent intent = new Intent(activity, LicenseActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView.setAdapter(new LicenseAdapter(this));
    }

    @Override
    public int getContentViewLayout() {
        return R.layout.activity_license;
    }

    @Override
    public int getToolbarId() {
        return R.id.activity_license_toolbar;
    }

    @Override
    public void onLicenseClick(@Nullable License license) {
        if (license == null || Strings.isNullOrEmpty(license.getUrl())) {
            return;
        }
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(license.getUrl())));
    }
}
