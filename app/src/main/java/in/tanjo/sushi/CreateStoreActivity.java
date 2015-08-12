package in.tanjo.sushi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.tanjo.sushi.adapter.StoreAdapter;
import in.tanjo.sushi.model.store.StoreManager;
import in.tanjo.sushi.model.store.StoreModel;

public class CreateStoreActivity extends Activity {

  private static final int MENU_DELETE_LOG = 100;

  @Bind(R.id.create_store_activity_tenmei_view) TextView mTenmeiView;
  @Bind(R.id.create_store_activity_tenmei_edit) EditText mTenmeiEdit;
  @Bind(R.id.create_store_activity_sakusei_button) Button mSakuseiButton;
  @Bind(R.id.create_store_activity_old_store_list) ListView mOldStoreList;
  @Bind(R.id.create_store_activity_old_list_title_view) TextView mOldListTitleView;
  @Bind(R.id.create_store_activity_linearlayout) LinearLayout mLinearLayout;

  private StoreManager mStoreManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_create_store);
    ButterKnife.bind(this);
    setupTypeface();

    mStoreManager = new StoreManager(this);
    mStoreManager.restore();

    StoreAdapter adapter = new StoreAdapter(this, 0, mStoreManager.getStoresModel().getItems());
    mOldStoreList.setAdapter(adapter);
    mOldStoreList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        StoreModel storeModel = mStoreManager.getStoresModel().getItems().get(position);
        if (storeModel != null) {
          next(storeModel);
        }
      }
    });
  }

  private void next(StoreModel storeModel) {
    Intent intent = new Intent(this, CreateEventActivity.class);
    intent.putExtra(StoreModel.INTENT_PUT_EXTRA_KEY, storeModel);
    startActivity(intent);
  }

  private void setupTypeface() {
    Typeface tf = Typeface.createFromAsset(getAssets(), "Ounen-mouhitsu.ttf");
    mTenmeiView.setTypeface(tf);
    mSakuseiButton.setTypeface(tf);
    mOldListTitleView.setTypeface(tf);
  }

  @OnClick(R.id.create_store_activity_sakusei_button)
  void sakusei() {
    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    inputMethodManager.hideSoftInputFromWindow(mSakuseiButton.getWindowToken(), 0);

    String tenmei = mTenmeiEdit.getText().toString();
    if (tenmei.length() > 0) {
      StoreModel storeModel = new StoreModel(tenmei);
      mStoreManager.getStoresModel().getItems().add(storeModel);
      mStoreManager.save();
      mStoreManager.restore();
      StoreAdapter adapter = (StoreAdapter) mOldStoreList.getAdapter();
      adapter.clear();
      adapter.addAll(mStoreManager.getStoresModel().getItems());
      adapter.notifyDataSetChanged();
      mTenmeiEdit.setText("");
      next(storeModel);
    } else {
      Toast.makeText(this, "なんか入力しろよ", Toast.LENGTH_SHORT).show();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    menu.add(0, MENU_DELETE_LOG, 0, "Delete All");
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case MENU_DELETE_LOG:
        mStoreManager.delete();
        StoreAdapter adapter = (StoreAdapter) mOldStoreList.getAdapter();
        adapter.clear();
        adapter.notifyDataSetChanged();
        return true;
    }
    return false;
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    inputMethodManager.hideSoftInputFromWindow(mLinearLayout.getWindowToken(), 0);
    return false;
  }
}
