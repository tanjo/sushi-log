package in.tanjo.sushi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import in.tanjo.sushi.model.SushiModel;

public class AddSushiActivity extends AppCompatActivity {
  public static final int REQUESTCODE_SUSHI_OBJECT = 33001;
  public static final String BUNDLEKEY_SUSHI_MODEL = "key_sushi_model";

  @Bind(R.id.sushi_name) EditText mNameEditText;
  @Bind(R.id.sushi_name_layout) TextInputLayout mNameTextInputLayout;
  @Bind(R.id.sushi_price) EditText mPriceEditText;
  @Bind(R.id.sushi_price_layout) TextInputLayout mPriceLayout;

  @BindString(R.string.add_sushi_activity_name_error) String mNameError;

  public static Intent newInstance(Context activity) {
    return new Intent(activity, AddSushiActivity.class);
  }

  public static void startActivityWithSushiRequestCode(Activity activity) {
    Intent intent = newInstance(activity);
    activity.startActivityForResult(intent, REQUESTCODE_SUSHI_OBJECT);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_sushi);
    ButterKnife.bind(this);

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.add_sushi_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.action_sushi_input_complete) {
      complete();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private SushiModel makeSushi() {
    String name = mNameEditText.getText().toString();
    int price = -1;
    try {
      price = Integer.parseInt(mPriceEditText.getText().toString());
    } catch (NumberFormatException e) {
      e.printStackTrace();
    }
    if (name.length() > 0) {
      return new SushiModel(name, price);
    }
    return null;
  }

  private void setSushiResult(SushiModel sushiModel) {
    if (sushiModel != null) {
      Intent data = new Intent();
      Bundle bundle = new Bundle();
      bundle.putSerializable(BUNDLEKEY_SUSHI_MODEL, sushiModel);
      data.putExtras(bundle);
      setResult(RESULT_OK, data);
    }
  }

  private void complete() {
    SushiModel sushiModel = makeSushi();

    if (sushiModel != null) {
      setSushiResult(sushiModel);
      finish();
    } else {
      mNameTextInputLayout.setError(mNameError);
    }
  }
}
