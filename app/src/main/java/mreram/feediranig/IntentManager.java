package mreram.feediranig;
import android.content.Intent;

import mreram.feediranig.view.BookmarkActivity;
import mreram.feediranig.view.MainActivity;
import mreram.feediranig.view.SettingActivity;


/**
 * Created by Reza on 26/11/2016.
 */
public class IntentManager {
    public static void startSettingActivity(){
        AppLoader.getContext().startActivity(new Intent(AppLoader.getContext(), SettingActivity.class));
    }
 public static void startBookmarkActivity(){
        AppLoader.getContext().startActivity(new Intent(AppLoader.getContext(), BookmarkActivity.class));
    }

    public static void startMainActivity() {
        Intent i=new Intent(AppLoader.getContext(), MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        AppLoader.getContext().startActivity(i);
    }
}
