package ruolan.com.myhearts.ui.fragment.thoughts;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okrx.RxAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ruolan.com.myhearts.R;
import ruolan.com.myhearts.adapter.ThroughtAdapter;
import ruolan.com.myhearts.contant.HttpUrlPaths;
import ruolan.com.myhearts.entity.ThoughtsBean;
import ruolan.com.myhearts.event.CatgidEvent;
import ruolan.com.myhearts.widget.DividerItemDecoration;
import ruolan.com.myhearts.widget.itemanimator.ScaleInOutItemAnimator;
import rx.android.schedulers.AndroidSchedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;

    private List<ThoughtsBean.ResultsBean> mThroughtDatas = new ArrayList<>();
    private ThroughtAdapter mThroughtAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new, container, false);
        EventBus.getDefault().register(this);
        initView(view);
        initData();
        return view;
    }


    /**
     * 请求数据
     */
    private void initData() {

        //labelid=0&type=1&page=1&userid=0
        OkGo.post(HttpUrlPaths.THOUGHTS_URL)
                .params("userid", 54442)
                .params("labelid", catgId)
                .params("type", 2)
                .params("page", 1)
                .getCall(StringConvert.create(), RxAdapter.<String>create())
                .doOnSubscribe(() -> {
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    Type type = new TypeToken<ThoughtsBean>() {
                    }.getType();
                    ThoughtsBean bean = new Gson().fromJson(s, type);
                    if (bean.getErrorCode() == 0
                            && bean.getResultCount() > 0
                            && bean.getErrorStr().equals("success")) {
                        mThroughtDatas = bean.getResults();
                        mThroughtAdapter.setResultsBeen(mThroughtDatas);
//                        mRefreshLayout.setLoadMore(true);
//                        mRefreshLayout.setClickable(true);
                    }
                }, throwable -> {
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void initPopupData(CatgidEvent event) {
        if (event != null) {
            //int id = event.id;
            catgId = event.id;
            refreshId(catgId);
        }
    }

    private int catgId = 1;

    /**
     * 当点击popup的时候,根据所选择的分类,来展示不同分类下面的空间消息内容
     *
     * @param id catgId
     */
    private void refreshId(int id) {
        OkGo.post(HttpUrlPaths.THOUGHTS_URL)
                .params("userid", 54442)
                .params("labelid", catgId)
                .params("type", 2)
                .params("page", 1)
                .getCall(StringConvert.create(), RxAdapter.<String>create())
                .doOnSubscribe(() -> {
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    Type type = new TypeToken<ThoughtsBean>() {
                    }.getType();
                    ThoughtsBean bean = new Gson().fromJson(s, type);
                    if (bean.getErrorCode() == 0
                            && bean.getResultCount() > 0
                            && bean.getErrorStr().equals("success")) {
                        mThroughtDatas.clear();
                        // mRefreshLayout.finishRefresh();
                        mThroughtDatas = bean.getResults();
                        //mThroughtAdapter.notifyDataSetChanged();
                        mThroughtAdapter.setResultsBeen(mThroughtDatas);
                        mRefreshLayout.setRefreshing(false);
                    }
                }, throwable -> {
                });
    }

    private boolean isLoading;
    private int page = 1;

    private void getMoreData() {
        page++;
        OkGo.post(HttpUrlPaths.THOUGHTS_URL)
                .params("userid", 54442)
                .params("labelid", catgId)
                .params("type", 2)
                .params("page", 1)
                .getCall(StringConvert.create(), RxAdapter.<String>create())
                .doOnSubscribe(() -> {
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    Type type = new TypeToken<ThoughtsBean>() {
                    }.getType();
                    ThoughtsBean bean = new Gson().fromJson(s, type);
                    if (bean.getErrorCode() == 0
                            && bean.getResultCount() > 0
                            && bean.getErrorStr().equals("success")) {
                        //  mThroughtDatas.clear();
                        List<ThoughtsBean.ResultsBean> beens = bean.getResults();
                        //  mRefreshLayout.finishRefresh();
                        mThroughtDatas.addAll(mThroughtDatas.size(), beens);
                        //mThroughtAdapter.notifyDataSetChanged();
                        mThroughtAdapter.setResultsBeen(mThroughtDatas);
                    }
                }, throwable -> {
                });


    }


    /**
     * 初始化布局控件
     *
     * @param view
     */
    private void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new ScaleInOutItemAnimator(mRecyclerView));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL_LIST));
        mThroughtAdapter = new ThroughtAdapter(getContext(), mThroughtDatas);
        mRecyclerView.setAdapter(mThroughtAdapter);

        mThroughtAdapter.setOnItemClick((view1, position, bean) -> {
            Intent intent = new Intent(getActivity(),CircleFriendsActivity.class);
            intent.putExtra("eventid",bean.getId());
            intent.putExtra("commentCount",bean.getCommentCnt());
            startActivity(intent);
        });

        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        mRefreshLayout.setColorSchemeColors(Color.YELLOW, Color.RED, Color.BLUE, Color.GREEN);
        mRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastVisiableItemPosition = layoutManager.findLastVisibleItemPosition();
                if (lastVisiableItemPosition + 1 == mThroughtAdapter.getItemCount()) {
                    if (!isLoading) {
                        isLoading = true;
                        new Handler().postDelayed(() -> {
                            getMoreData();
                            isLoading = false;
                            mThroughtAdapter.notifyItemRemoved(mThroughtAdapter.getItemCount());
                        }, 1000);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        // mRefreshLayout.setLoadMore(false);
//        mRefreshLayout.setClickable(false);
//        mRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
//            @Override
//            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
//                refreshData();
//            }
//
//            @Override
//            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
//                super.onRefreshLoadMore(materialRefreshLayout);
//                new Handler().postDelayed(() -> lordMoreData(materialRefreshLayout),3000);
//            }
//        });

    }


    /**
     * 刷新数据
     */
    private void refreshData() {
        page = 1;
        refreshId(catgId);
//        OkGo.post(HttpUrlPaths.THOUGHTS_URL)
//                .params("userid",54442)
//                .params("labelid",catgId)
//                .params("type",1)
//                .params("page",1)
//                .getCall(StringConvert.create(), RxAdapter.<String>create())
//                .doOnSubscribe(()->{})
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(s->{
//                    Type type = new TypeToken<ThoughtsBean>(){}.getType();
//                    ThoughtsBean bean = new Gson().fromJson(s,type);
//                    if (bean.getErrorCode()==0
//                            &&bean.getResultCount()>0
//                            &&bean.getErrorStr().equals("success")){
//                        mThroughtDatas.clear();
//                        mRefreshLayout.finishRefresh();
//                        mThroughtDatas = bean.getResults();
//                        //mThroughtAdapter.notifyDataSetChanged();
//                        mThroughtAdapter.setResultsBeen(mThroughtDatas);
//                    }
//                },throwable -> {});
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(() -> refreshData(), 1500);
    }
}
