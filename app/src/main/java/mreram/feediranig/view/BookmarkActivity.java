package mreram.feediranig.view;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mreram.feediranig.CActivity;
import mreram.feediranig.GlideApp;
import mreram.feediranig.GlideRequest;
import mreram.feediranig.GlideRequests;
import mreram.feediranig.R;
import mreram.feediranig.TimeAgo;
import mreram.feediranig.ViewAnimationUtils;
import mreram.feediranig.db.Bookmark;
import mreram.feediranig.interfaces.NewsInteractor;
import mreram.feediranig.presenter.BookmarkPresenter;
import mreram.feediranig.presenter.NewsPresenter;

/**
 * Created by Reza on 11/08/2017.
 */

public class BookmarkActivity extends CActivity implements NewsInteractor<List<Bookmark>> {

    @Bind(R.id.imgAction)
    ImageView imgAction;
    @Bind(R.id.txtTitleActionBar)
    TextView txtTitleActionBar;
    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.progress_wheel)
    ProgressWheel progressWheel;

    List<Bookmark> bookmarks;
    @Bind(R.id.llBookmark)
    LinearLayout llBookmark;
    private NewsPresenter newsPresenter;

    private GlideRequests glideRequest;
    private GlideRequest<Bitmap> fullRequest;
    private GlideRequest<Bitmap> thumbRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        ButterKnife.bind(this);

        initGlide();
        initUi();

        new BookmarkPresenter(this).getBookmarkFromDBLocal();
        newsPresenter = new NewsPresenter(null);

    }

    private void initUi() {
        txtTitleActionBar.setText(R.string.bookmarks);
        //remove drawable icon menu
        txtTitleActionBar.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        imgAction.setImageResource(R.drawable.ic_back);


        rv.setLayoutManager(new LinearLayoutManager(this));
        bookmarks = new ArrayList<>();

    }

    private void initGlide() {
        glideRequest = GlideApp.with(this);
        fullRequest = glideRequest
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .placeholder(new ColorDrawable(Color.GRAY));
        thumbRequest = glideRequest
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.DATA);
    }

    class Adapter extends RecyclerView.Adapter<Adapter.Holder> {
        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(LayoutInflater.from(BookmarkActivity.this).inflate(R.layout.item_main_news, parent, false));
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            Bookmark item = bookmarks.get(position);

            holder.txtDescription.setVisibility(View.GONE);
            //todo set rate
            holder.txtRate.setText(String.valueOf(item.newsId * 10));
            holder.txtTitle.setText(item.title);

            //todo set source news
            String strSource = "@khabarPlus";
            String str = TimeAgo.DateDifference(item.time) + " - " + strSource;
            Spannable wordtoSpan = new SpannableString(str);
            int startSpan = str.indexOf(strSource);
            wordtoSpan.setSpan(new ForegroundColorSpan(Color.parseColor("#ffcfa1")), startSpan, str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.txtTime.setText(wordtoSpan);

            //set icon img bookmark
            holder.imgBookmark.setImageResource(item.isBookmark ? R.drawable.ic_bookmark : R.drawable.ic_bookmark_border);

            holder.txtDescription.setText(item.description);
            fullRequest.load(item.imgUrl)
                    .thumbnail(thumbRequest.load(item.imgUrl))
                    .into(holder.img);
        }

        @Override
        public int getItemCount() {
            return bookmarks.size();
        }

        class Holder extends RecyclerView.ViewHolder {
            @Bind(R.id.imageView)
            ImageView img;
            @Bind(R.id.imgBookmark)
            ImageView imgBookmark;
            @Bind(R.id.txtDescription)
            TextView txtDescription;
            @Bind(R.id.txtTitle)
            TextView txtTitle;
            @Bind(R.id.txtRate)
            TextView txtRate;
            @Bind(R.id.txtTime)
            TextView txtTime;
            @Bind(R.id.imgArrowUp)
            ImageView imgArrowUp;
            @Bind(R.id.imgArrowDown)
            ImageView imgArrowDown;

            public Holder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        txtDescription.setPivotY(0);
                        if (txtDescription.getVisibility() == View.VISIBLE) {
                            ViewAnimationUtils.collapse(txtDescription);
                            imgArrowDown.setVisibility(View.VISIBLE);
                            imgArrowUp.setVisibility(View.GONE);

                        } else {
                            ViewAnimationUtils.expand(txtDescription);
                            imgArrowDown.setVisibility(View.GONE);
                            imgArrowUp.setVisibility(View.VISIBLE);
                        }
                    }
                });

                txtRate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //todo check and set rate
                        txtRate.setText((String.valueOf(Integer.parseInt(txtRate.getText().toString()) + 1)));
                    }
                });


                imgBookmark.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bookmark item = bookmarks.get(getAdapterPosition());

                        if (item.isBookmark) {
                            newsPresenter.deleteBookmarkWithId(item.newsId);
                            imgBookmark.setImageResource(R.drawable.ic_bookmark_border);
                            item.isBookmark = false;
                        } else {
                            Bookmark bookmark = new Bookmark(item.newsId, item.imgUrl, item.title, item.description, item.time);
                            newsPresenter.insertBookmark(bookmark);
                            imgBookmark.setImageResource(R.drawable.ic_bookmark);
                            item.isBookmark = true;
                        }


                    }
                });
            }

        }
    }

    @OnClick(R.id.imgAction)
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void showProgress() {
        progressWheel.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressWheel.setVisibility(View.GONE);
    }

    @Override
    public void setData(List<Bookmark> data) {
        bookmarks = data;
        rv.setAdapter(new Adapter());
        if (data.size() == 0) {
            llBookmark.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setError(Throwable throwable) {

    }
}
