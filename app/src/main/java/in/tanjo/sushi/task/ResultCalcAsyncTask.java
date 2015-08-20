package in.tanjo.sushi.task;

import android.os.AsyncTask;
import android.widget.TextView;

import java.util.List;

import in.tanjo.sushi.model.CountableSushiModel;
import in.tanjo.sushi.model.NoteModel;
import in.tanjo.sushi.model.SushiModel;

public class ResultCalcAsyncTask extends AsyncTask<Void, Void, Void> {

  private TextView mTextView;
  private NoteModel mNoteModel;
  private int mResult;

  public ResultCalcAsyncTask(TextView textView, NoteModel noteModel) {
    mTextView = textView;
    mNoteModel = noteModel;
    mResult = 0;
  }

  @Override
  protected Void doInBackground(Void... params) {
    List<CountableSushiModel> list = mNoteModel.getSushiModelList();
    for (CountableSushiModel sushi: list) {
      mResult += sushi.getPrice() * sushi.getCount();
      publishProgress();
    }
    return null;
  }

  @Override
  protected void onPostExecute(Void aVoid) {
    super.onPostExecute(aVoid);
    mTextView.setText(String.valueOf(mResult) + "円");
  }

  @Override
  protected void onProgressUpdate(Void... values) {
    super.onProgressUpdate(values);
    mTextView.setText(String.valueOf(mResult) + "円");
  }
}
