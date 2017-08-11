package mreram.feediranig.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mreram.feediranig.CActivity;
import mreram.feediranig.IntentManager;
import mreram.feediranig.R;

public class SettingActivity extends CActivity {

    @Bind(R.id.imgAction)
    ImageView imgAction;
    @Bind(R.id.txtTitleActionBar)
    TextView txtTitleActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        txtTitleActionBar.setText(R.string.setting);
        //remove drawable icon menu
        txtTitleActionBar.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

        imgAction.setImageResource(R.drawable.ic_back);
    }

    @OnClick(R.id.imgPishkhan)
    public void onClickPishkhan(){
        IntentManager.startMainActivity();
    }
    @OnClick(R.id.imgBookmark)
    public void onClickBookmark(){
        IntentManager.startBookmarkActivity();
    }

    @OnClick(R.id.imgAction)
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void onCategory(View view){
        view.setSelected(!view.isSelected());
        //todo writing function...
        switch (view.getId()){
            case R.id.varzeshi:

                break;
            case R.id.siasi:

                break;
            case R.id.omumi:

                break;
            case R.id.beinolmelali:

                break;
            case R.id.tech:

                break;
            case R.id.eghtesadi:

                break;

        }
    }
}
