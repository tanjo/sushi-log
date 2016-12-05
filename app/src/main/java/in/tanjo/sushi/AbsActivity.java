package in.tanjo.sushi;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


public abstract class AbsActivity extends AppCompatActivity {

    @Nullable
    private Toolbar toolbar;

    @NonNull
    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    protected void addSubscription(Subscription subscription) {
        compositeSubscription.add(subscription);
    }

    @Override
    public void finish() {
        if (!isFinishing()) {
            super.finish();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewLayout());
        ButterKnife.bind(this);
        if (getToolbarId() != 0) {
            toolbar = ButterKnife.findById(this, getToolbarId());
            setSupportActionBar(toolbar);
        }
    }

    @Override
    protected void onDestroy() {
        compositeSubscription.unsubscribe();
        super.onDestroy();
    }

    /**
     * Activity の ContentView にセットするレイアウト.
     */
    @LayoutRes
    abstract public int getContentViewLayout();

    /**
     * Activity のツールバーID.
     * 0 の場合は無視.
     */
    @IdRes
    abstract public int getToolbarId();

    @Nullable
    public Toolbar getToolbar() {
        return toolbar;
    }
}
