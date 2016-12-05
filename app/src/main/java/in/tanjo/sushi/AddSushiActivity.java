package in.tanjo.sushi;

import com.google.common.base.Strings;

import com.jakewharton.rxbinding.widget.RxTextView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;

import butterknife.BindString;
import butterknife.BindView;
import in.tanjo.sushi.model.Sushi;
import in.tanjo.sushi.model.SushiMaker;
import rx.Observable;
import rx.Subscriber;

public class AddSushiActivity extends AbsActivity {

    public static final int REQUESTCODE_SUSHI_OBJECT = 33001;

    public static final String BUNDLE_KEY_SUSHI_MODEL = "key_sushi_model";

    @BindView(R.id.sushi_name)
    EditText nameEditText;

    @BindView(R.id.sushi_name_layout)
    TextInputLayout nameTextInputLayout;

    @BindView(R.id.sushi_price)
    EditText priceEditText;

    @BindView(R.id.sushi_price_layout)
    TextInputLayout priceTextInputLayout;

    @BindString(R.string.add_sushi_activity_name_error)
    String nameErrorMessage;

    @BindString(R.string.add_sushi_activity_price_error)
    String priceErrorMessage;

    private SushiMaker sushiMaker = new SushiMaker();

    public static void startActivityWithSushiRequestCode(Activity activity) {
        Intent intent = new Intent(activity, AddSushiActivity.class);
        activity.startActivityForResult(intent, REQUESTCODE_SUSHI_OBJECT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        initRx();
    }

    private void initRx() {
        Observable<CharSequence> nameObservable = RxTextView.textChanges(nameEditText);
        Observable<CharSequence> priceObservable = RxTextView.textChanges(priceEditText);

        addSubscription(nameObservable.subscribe(new Subscriber<CharSequence>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(CharSequence charSequence) {
                sushiMaker.setName(charSequence.toString());
                if (Strings.isNullOrEmpty(charSequence.toString())) {
                    nameTextInputLayout.setError(nameErrorMessage);
                } else {
                    nameTextInputLayout.setError(null);
                }
            }
        }));

        addSubscription(priceObservable.subscribe(new Subscriber<CharSequence>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(CharSequence price) {
                try {
                    sushiMaker.setPrice(Integer.parseInt(price.toString()));
                    priceTextInputLayout.setError(null);
                } catch (NumberFormatException ignore) {
                    priceTextInputLayout.setError(priceErrorMessage);
                }
            }
        }));
    }

    @Override
    public int getContentViewLayout() {
        return R.layout.activity_add_sushi;
    }

    @Override
    public int getToolbarId() {
        return R.id.add_sushi_toolbar;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_sushi_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_sushi_input_complete) {
            if (sushiMaker.canMake()) {
                setSushiResult(sushiMaker);
                finish();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 寿司モデルを呼び出し元に返す.
     */
    private void setSushiResult(@NonNull Sushi sushi) {
        Intent data = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_KEY_SUSHI_MODEL, sushi);
        data.putExtras(bundle);
        setResult(RESULT_OK, data);
    }
}
