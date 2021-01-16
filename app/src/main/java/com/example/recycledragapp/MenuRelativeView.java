package com.example.recycledragapp;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MenuRelativeView extends RelativeLayout {

    private Context context;

    private RecyclerView rv_select, rv_all;
    private TextView tv_name;
    private MenuSelectAdapter menuSelectAdapter;
    private MenuAdapter menuAdapter;
    private ImageView iv_open;
    private LinearLayout ll_more;

    //更多布局是否打开
    private boolean isOpen = true;
    private int height;

    private static final int MAX_COUNT = 12;

    public MenuRelativeView(Context context) {
        super(context);
        initView(context);
    }

    public MenuRelativeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MenuRelativeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public MenuRelativeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

//    private List<GridItemBean> allData = new ArrayList<>();
//    private List<GridItemBean> selectData = new ArrayList<>();

    private GridItemBean itemBean;

    public void setData(final GridItemBean itemBean) {
        this.itemBean = itemBean;
        tv_name.setText(itemBean.Name);

        if (menuSelectAdapter == null){
            menuSelectAdapter = new MenuSelectAdapter(context, itemBean.getSelectItems());
            rv_select.setLayoutManager(new GridLayoutManager(context, 4));
            rv_select.setAdapter(menuSelectAdapter);
            rv_select.addItemDecoration(new SpaceDecoration(4, dip2px( 1)));

            DefaultItemCallback callback = new DefaultItemCallback(menuSelectAdapter);
            ItemTouchHelper helper = new ItemTouchHelper(callback);
            helper.attachToRecyclerView(rv_select);

            GridLayoutManager gridManager = new GridLayoutManager(context, 4);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    GridItemBean fi = itemBean.getItem().get(position);
                    // 因为分成了 4份。1代表1份  4代表4份。所有不是功能菜单的时候占4 返回一整行
                    return fi.status == 1 ? 1 : 4;
                }
            });

            menuAdapter = new MenuAdapter(context,  itemBean.getItem());
            rv_all.setLayoutManager(gridManager);
            rv_all.setAdapter(menuAdapter);
            SpaceDecoration spaceDecoration = new SpaceDecoration(4, dip2px( 1));
            rv_all.addItemDecoration(spaceDecoration);
            addListener();
        }else {
            menuSelectAdapter.notifyDataSetChanged();
            menuAdapter.notifyDataSetChanged();
        }


    }

    public  int dip2px(float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    private void initView(Context context) {
        this.context = context;
        itemBean = new GridItemBean();
        itemBean.setItem(new ArrayList<GridItemBean>());
        itemBean.setSelectItems(new ArrayList<GridItemBean>());

        View view = View.inflate(context, R.layout.view_menu, null);
        rv_select = view.findViewById(R.id.rv_select);
        rv_all =  view.findViewById(R.id.rv_all);
        tv_name = view.findViewById(R.id.tv_name);
        iv_open = view.findViewById(R.id.iv_open);
        ll_more = view.findViewById(R.id.ll_more);
        view.findViewById(R.id.iv_up).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onChangePositionListener != null) onChangePositionListener.onUp();
            }
        });
        view.findViewById(R.id.iv_down).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onChangePositionListener != null) onChangePositionListener.onDown();
            }
        });
        iv_open.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (height == 0)
                    height = ll_more.getMeasuredHeight();
                isOpen = !isOpen;
                if (isOpen) {
                    //如果是打开 则关闭
                    iv_open.setImageResource(R.drawable.icon_close);
                } else {
                    iv_open.setImageResource(R.drawable.icon_open);
                }
                setOpen(ll_more, isOpen);
            }
        });


        addView(view);
    }

    public void setOpen(final View v, boolean isOpen) {
        ValueAnimator animator;
        if (isOpen) {
            animator = ValueAnimator.ofInt(0, height);
        } else {
            animator = ValueAnimator.ofInt(height, 0);
        }
        animator.setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (Integer) animation.getAnimatedValue();
                v.getLayoutParams().height = value;
                v.setLayoutParams(v.getLayoutParams());
            }
        });
        animator.start();
    }

    public void addListener() {
        menuAdapter.setOnItemAddListener(new MenuAdapter.OnItemAddListener() {
            @Override
            public boolean add(GridItemBean item) {
                if ( itemBean.getSelectItems() != null && itemBean.getSelectItems().size() < MAX_COUNT) {   // 更新选择列表，所有列表已在内部进行更新
                    try {
                        itemBean.getSelectItems().add(item);
                        menuSelectAdapter.notifyDataSetChanged();
                        item.isSelect = true;
                        getHei();
                        return true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(context,"最多选"+MAX_COUNT+"个",Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        menuSelectAdapter.setOnItemRemoveListener(new MenuSelectAdapter.OnItemRemoveListener() {
            @Override
            public void remove(GridItemBean item) {
                // 更新所有列表，选择列表已在内部进行更新
                try {
                    if (item != null && item.Name != null) {
                        for (int i = 0; i <  itemBean.getItem().size(); i++) {
                            GridItemBean data =  itemBean.getItem().get(i);
                            if (data != null && data.Name != null) {
                                if (item.Name.equals(data.Name)) {
                                    data.isSelect = false;
                                    break;
                                }
                            }
                        }
                        menuAdapter.notifyDataSetChanged();
                        getHei();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private OnChangePositionListener onChangePositionListener;

    public OnChangePositionListener getOnChangePositionListener() {
        return onChangePositionListener;
    }

    public void setOnChangePositionListener(OnChangePositionListener onChangePositionListener) {
        this.onChangePositionListener = onChangePositionListener;
    }

    public interface OnChangePositionListener{
        void onUp();
        void onDown();

    }

    private void getHei(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                height = 0;
                int childCount = ll_more.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View childAt = ll_more.getChildAt(i);
                    height += childAt.getMeasuredHeight();
                }
                Log.d("高度",height+"");
            }
        },0);


    }

}
