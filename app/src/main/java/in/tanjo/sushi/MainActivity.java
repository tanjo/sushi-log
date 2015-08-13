package in.tanjo.sushi;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.tanjo.sushi.model.SushiModel;

public class MainActivity extends AppCompatActivity {
  @Bind(R.id.main_framelayout) FrameLayout mFrameLayout;
  @Bind(R.id.main_recycler_view) RecyclerView mRecyclerView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    mRecyclerView.setHasFixedSize(true);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    String[] myDataset = {
        "まぐろ", "いか", "たこ",
        "ほたて", "あぶりまぐろ", "ビントロ",
        "コーン", "シーサラダ",
        "ツナ", "かに", "サーモン",
        "あぶりサーモンチーズ", "中トロ",
        "ハンバーグ", "カリフォルニアロール"};

    mRecyclerView.setAdapter(new MainAdapter(myDataset));
  }

  @OnClick(R.id.main_floating_action_button)
  void add() {
    AddSushiActivity.startActivityWithSushiRequestCode(this);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK) {
      switch (requestCode) {
        case AddSushiActivity.REQUESTCODE_SUSHI_OBJECT:
          Bundle bundle = data.getExtras();
          SushiModel sushiModel = (SushiModel) bundle.getSerializable(AddSushiActivity.BUNDLEKEY_SUSHI_MODEL);
          if (sushiModel != null) {
            final Snackbar snackbar = Snackbar.make(mFrameLayout, sushiModel.getName() + " " + sushiModel.getPriceText(), Snackbar.LENGTH_LONG);
            snackbar.setAction("OK", new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                snackbar.dismiss();
              }
            });
            snackbar.show();
          }
          break;
      }
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.action_settings) {
      Toast.makeText(this, "設定", Toast.LENGTH_SHORT).show();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public boolean dispatchKeyEvent(KeyEvent event) {
    return super.dispatchKeyEvent(event);
  }
}

