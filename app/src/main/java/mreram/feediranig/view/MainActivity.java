package mreram.feediranig.view;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import mreram.feediranig.IntentManager;
import mreram.feediranig.R;
import mreram.feediranig.TimeAgo;
import mreram.feediranig.VerticalSpaceItemDecoration;
import mreram.feediranig.ViewAnimationUtils;
import mreram.feediranig.db.Bookmark;
import mreram.feediranig.interfaces.NewsInteractor;
import mreram.feediranig.model.Drawer;
import mreram.feediranig.model.News;
import mreram.feediranig.presenter.NewsPresenter;

public class MainActivity extends CActivity implements NewsInteractor<List<News>> {

    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.rvDrawer)
    RecyclerView rvDrawer;
    @Bind(R.id.drawLayout)
    DrawerLayout drawerLayout;
    @Bind(R.id.progress_wheel)
    ProgressWheel progressWheel;
    @Bind(R.id.txtTitleActionBar)
    TextView txtTitle;


    private GlideRequests glideRequest;
    private GlideRequest<Bitmap> fullRequest;
    private GlideRequest<Bitmap> thumbRequest;
    private List<News> listSrc;
    private NewsPresenter newsPresenter;
    private DrawerAdapter drawerAdapter;
    private Adapter adapter;
    private List<Drawer> listDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();

        initGlide();

        newsPresenter = new NewsPresenter(this);
        newsPresenter.getNewsData();
    }

    @OnClick(R.id.imgAction)
    public void doRefresh() {
        newsPresenter.getNewsData();
    }

    @OnClick(R.id.txtTitleActionBar)
    public void doOpenMenu() {
        drawerLayout.openDrawer(Gravity.RIGHT);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.RIGHT))
            drawerLayout.closeDrawer(Gravity.RIGHT);
        else
            super.onBackPressed();

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

    private void initView() {
        rv.setLayoutManager(new LinearLayoutManager(this));
        rvDrawer.setLayoutManager(new LinearLayoutManager(this));

        adapter = new Adapter();
        rv.setAdapter(adapter);

        drawerAdapter = new DrawerAdapter();
        initDrawer();
        rvDrawer.setAdapter(drawerAdapter);

        listSrc = new ArrayList<>();
        rv.addItemDecoration(new VerticalSpaceItemDecoration(40));
        rv.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(RecyclerView.ViewHolder holder) {
                glideRequest.clear(((Adapter.Holder) holder).img);
            }
        });


    }

    private void initDrawer() {
        listDrawer = new ArrayList<>();
        listDrawer.add(new Drawer(Drawer.Type.header, null));
        listDrawer.add(new Drawer(Drawer.Type.item, getString(R.string.bookmarks), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentManager.startBookmarkActivity();
            }
        }));
        listDrawer.add(new Drawer(Drawer.Type.item, getString(R.string.setting), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentManager.startSettingActivity();
            }
        }));

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
    public void setData(List<News> data) {
        if (data != null) {
            this.listSrc.clear();
            this.listSrc.addAll(data);
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public void setError(Throwable throwable) {
        throwable.printStackTrace();
    }


    class Adapter extends RecyclerView.Adapter<Adapter.Holder> {


        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(LayoutInflater.from(getContext()).inflate(R.layout.item_main_news, parent, false));
        }

        @Override
        public void onBindViewHolder(final Holder holder, int position) {
            News item = listSrc.get(position);

            holder.txtDescription.setVisibility(View.GONE);
            //todo set rate
            holder.txtRate.setText(String.valueOf(item.id * 10));
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
            fullRequest.load(item.imageUrl)
                    .thumbnail(thumbRequest.load(item.imageUrl))
                    .into(holder.img);

        }

        @Override
        public int getItemCount() {
            return listSrc.size();
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
                        News item = listSrc.get(getAdapterPosition());

                        if (item.isBookmark) {
                            newsPresenter.deleteBookmarkWithId(item.id);
                            imgBookmark.setImageResource(R.drawable.ic_bookmark_border);
                            item.isBookmark = false;
                        } else {
                            Bookmark bookmark = new Bookmark(item.id, item.imageUrl, item.title, item.description,item.time);
                            newsPresenter.insertBookmark(bookmark);
                            imgBookmark.setImageResource(R.drawable.ic_bookmark);
                            item.isBookmark = true;
                        }


                    }
                });
            }
        }
    }

    class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.Holder> {

        @Override
        public int getItemViewType(int position) {
            if (position == 0)
                return R.layout.item_header_drawer;
            return R.layout.item_drawer;
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(LayoutInflater.from(getContext()).inflate(viewType, parent, false));
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            if (getItemViewType(position) == R.layout.item_drawer)
                holder.txt.setText(listDrawer.get(position).name);
        }

        @Override
        public int getItemCount() {
            return listDrawer.size();
        }

        class Holder extends RecyclerView.ViewHolder {
            TextView txt;

            public Holder(View itemView) {
                super(itemView);
                txt = (TextView) itemView.findViewById(R.id.txt);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Drawer item = listDrawer.get(getAdapterPosition());
                        if (item.onClickListener != null)
                            item.onClickListener.onClick(v);
                    }
                });
            }
        }
    }
}
