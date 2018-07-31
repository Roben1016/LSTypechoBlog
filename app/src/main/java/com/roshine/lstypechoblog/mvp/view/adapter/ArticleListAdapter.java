package com.roshine.lstypechoblog.mvp.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.roshine.lstypechoblog.R;
import com.roshine.lstypechoblog.mvp.bean.ArticleListBean;
import com.roshine.lstypechoblog.utils.DateUtil;
import com.roshine.lstypechoblog.utils.LogUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Roshine
 * @date 2017/6/25 23:01
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc 我的文章列表adapter
 */
public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.ViewHolder> {
    private Context mContext;
    private List<ArticleListBean> listData;
    private OnItemClickListener mListener;
    private LayoutInflater inflater;

    public ArticleListAdapter(Context context, List<ArticleListBean> listData) {
        this.mContext = context;
        this.listData = listData;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.item_article_list_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final ArticleListBean item = listData.get(position);
        holder.tvArticleDate.setText(DateUtil.formatDate(item.getDateCreated()));
        holder.tvArticleTitle.setText(item.getTitle());
        holder.tvArticleSummary.setText(item.getMt_excerpt());
        if (mListener != null) {
            holder.itemView.setOnClickListener(v -> {
                int layoutPosition = holder.getLayoutPosition();
                mListener.onItemClick(v, layoutPosition);
            });
            holder.itemView.setOnLongClickListener(v -> {
                int layoutPosition = holder.getLayoutPosition();
                mListener.onItemLongClick(v, layoutPosition);
                return false;
            });
        }
    }

    @Override
    public int getItemCount() {
        if (listData != null) {
            return listData.size();
        }
        return 0;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_article_date)
        TextView tvArticleDate;
        @BindView(R.id.tv_article_title)
        TextView tvArticleTitle;
        @BindView(R.id.tv_article_summary)
        TextView tvArticleSummary;
        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
