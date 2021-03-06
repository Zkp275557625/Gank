package com.zkp.androidwan.module.project.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zkp.androidwan.R;
import com.zkp.androidwan.app.App;
import com.zkp.androidwan.base.fragment.BaseFragment;
import com.zkp.androidwan.bean.ProjectListBean;
import com.zkp.androidwan.module.home.detail.ArticleDetailActivity;
import com.zkp.androidwan.module.project.adapter.ProjectListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;

/**
 * @author: zkp
 * @project: Gank
 * @package: com.zkp.gank.module.project.detail
 * @time: 2019/4/30 10:42
 * @description:
 */
public class ProjectListFragment extends BaseFragment<ProjectListPresenter> implements ProjectListFragmentContract.View {

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    /**
     * 分类的id，上述二级目录的id
     */
    private int cid;

    private ProjectListAdapter mAdapter;

    public static ProjectListFragment newInstance(Bundle bundle) {
        ProjectListFragment fragment = new ProjectListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_project_list;
    }

    @Override
    protected void initView() {

        App.getApplication().addFragment(this);

        List<ProjectListBean.DataBean.DatasBean> mArticleList = new ArrayList<>();
        mAdapter = new ProjectListAdapter(R.layout.item_project_list, mArticleList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initEventAndData() {
        assert getArguments() != null;
        cid = getArguments().getInt("cid");

        mPresenter = new ProjectListPresenter();
        mPresenter.attachView(this);
        mPresenter.refresh(cid);

        mRefreshLayout.setOnRefreshListener(refreshLayout -> {
            mPresenter.refresh(cid);
            refreshLayout.finishRefresh();
        });

        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mPresenter.loadMore();
            refreshLayout.finishLoadMore();
        });


        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (mAdapter.getData().size() <= 0 || mAdapter.getData().size() < position) {
                return;
            }
            Intent intent = new Intent(getActivity(), ArticleDetailActivity.class);
            intent.putExtra("title", mAdapter.getData().get(position).getTitle());
            intent.putExtra("articleLink", mAdapter.getData().get(position).getLink());
            intent.putExtra("articleId", mAdapter.getData().get(position).getId());
            intent.putExtra("isCollected", mAdapter.getData().get(position).isCollect());
            intent.putExtra("isShowCollectIcon", true);
            intent.putExtra("articleItemPosition", position);
            Objects.requireNonNull(getActivity()).startActivity(intent);
        });

        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.ivLike:
                    if (Objects.requireNonNull(mAdapter.getItem(position)).isCollect()) {
                        mPresenter.unCollectArticle(Objects.requireNonNull(mAdapter.getItem(position)).getId());
                    } else {
                        mPresenter.collectArticle(Objects.requireNonNull(mAdapter.getItem(position)).getId());
                    }
                    break;
                default:
                    break;
            }
        });

    }

    @Override
    public void getProjectListSuccess(ProjectListBean data, boolean isFresh) {
        if (mAdapter == null) {
            return;
        }
        if (isFresh) {
            mAdapter.replaceData(data.getData().getDatas());
        } else {
            mAdapter.addData(data.getData().getDatas());
        }
    }

    @Override
    public void getProjectListError(String errMsg, boolean isFresh) {
        SmartToast.show(errMsg);
    }

    @Override
    public void collectArticleSuccess() {
        mPresenter.refresh(cid);
    }

    @Override
    public void collectArticleError(String errMsg) {
        SmartToast.show(errMsg);
    }

    @Override
    public void unCollectArticleSuccess() {
        mPresenter.refresh(cid);
    }

    @Override
    public void unCollectArticleError(String errMsg) {
        SmartToast.show(errMsg);
    }

    public void jumpToTop() {
        if (mRecyclerView != null) {
            mRecyclerView.smoothScrollToPosition(0);
        }
    }
}
