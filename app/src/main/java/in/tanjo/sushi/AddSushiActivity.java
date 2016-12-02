package in.tanjo.sushi;

import com.jakewharton.rxbinding.widget.RxTextView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import in.tanjo.sushi.model.Sushi;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func2;
import rx.subscriptions.CompositeSubscription;

public class AddSushiActivity extends AppCompatActivity {

    public static final int REQUESTCODE_SUSHI_OBJECT = 33001;

    public static final String BUNDLEKEY_SUSHI_MODEL = "key_sushi_model";

    @BindView(R.id.sushi_name)
    EditText nameEditText;

    @BindView(R.id.sushi_name_layout)
    TextInputLayout nameTextInputLayout;

    @BindView(R.id.sushi_price)
    EditText priceEditText;

    @BindView(R.id.sushi_price_layout)
    TextInputLayout priceTextInputLayout;

    @BindView(R.id.add_sushi_toolbar)
    Toolbar toolbar;

    @BindString(R.string.add_sushi_activity_name_error)
    String nameErrorMessage;

    @BindString(R.string.add_sushi_activity_price_error)
    String priceErrorMessage;

    boolean canSave = false;

    private Sushi sushi = new Sushi();

    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    public static void startActivityWithSushiRequestCode(Activity activity) {
        Intent intent = newInstance(activity);
        activity.startActivityForResult(intent, REQUESTCODE_SUSHI_OBJECT);
    }

    public static Intent newInstance(Context activity) {
        return new Intent(activity, AddSushiActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sushi);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        Observable<CharSequence> nameObservable = RxTextView.textChanges(nameEditText);
        Observable<CharSequence> priceObservable = RxTextView.textChanges(priceEditText);

        compositeSubscription.add(nameObservable.subscribe(new Subscriber<CharSequence>() {
            @Override
            public void onCompleted() {
                // no action.
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(CharSequence charSequence) {
                sushi.setName(charSequence.toString());
                if (charSequence.length() == 0) {
                    nameTextInputLayout.setError(nameErrorMessage);
                } else {
                    nameTextInputLayout.setError(null);
                }
            }
        }));

        compositeSubscription.add(priceObservable.subscribe(new Subscriber<CharSequence>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(CharSequence price) {
                try {
                    sushi.setPrice(Integer.parseInt(price.toString()));
                    priceTextInputLayout.setError(null);
                } catch (NumberFormatException ignore) {
                    priceTextInputLayout.setError(priceErrorMessage);
                }
            }
        }));

        compositeSubscription.add(Observable.combineLatest(nameObservable, priceObservable,
                new Func2<CharSequence, CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence name, CharSequence price) {
                        try {
                            int ignore = Integer.parseInt(price.toString());
                            return name != null && name.length() > 0;
                        } catch (NumberFormatException ignore) {
                            return false;
                        }
                    }
                })
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        canSave = aBoolean;
                    }
                }));
    }

    @Override
    protected void onPause() {
        compositeSubscription.clear();
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_sushi_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_sushi_input_complete) {
            if (canSave) {
                setSushiResult(sushi);
                finish();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setSushiResult(Sushi sushi) {
        if (sushi != null) {
            Intent data = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable(BUNDLEKEY_SUSHI_MODEL, sushi);
            data.putExtras(bundle);
            setResult(RESULT_OK, data);
        }
    }
}
