package in.tanjo.sushi.task;

import android.os.AsyncTask;
import android.widget.TextView;

import java.util.List;

import in.tanjo.sushi.model.CountableSushi;
import in.tanjo.sushi.model.Note;

public class ResultCalcAsyncTask extends AsyncTask<Void, Void, Void> {

    private TextView mTextView;

    private Note mNote;

    private int mResult;

    public ResultCalcAsyncTask(TextView textView, Note note) {
        mTextView = textView;
        mNote = note;
        mResult = 0;
    }

    @Override
    protected Void doInBackground(Void... params) {
        List<CountableSushi> list = mNote.getSushis();
        for (CountableSushi sushi : list) {
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
