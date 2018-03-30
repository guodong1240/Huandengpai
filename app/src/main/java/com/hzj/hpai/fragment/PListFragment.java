package com.hzj.hpai.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hzj.hpai.R;
import com.hzj.hpai.activity.MakepptActivity;
import com.hzj.hpai.activity.PostSuccessActivity;
import com.hzj.hpai.adapter.abslistview.CommonAdapter;
import com.hzj.hpai.adapter.abslistview.ViewHolder;
import com.hzj.hpai.bean.Node;
import com.hzj.hpai.bean.NodeParent;
import com.hzj.hpai.bean.PPTListBean;
import com.hzj.hpai.bean.RecordParent;
import com.hzj.hpai.bean.RecordeBean;
import com.hzj.hpai.item.Constents;
import com.hzj.hpai.networkrequests.CommonListener;
import com.hzj.hpai.networkrequests.SendActtionTool;
import com.hzj.hpai.tool.UrlTool;
import com.hzj.hpai.utils.DialogUtils;
import com.hzj.hpai.utils.LogUtils;
import com.hzj.hpai.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.hzj.hpai.R.id.ppt_title;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PListFragment extends Fragment implements CommonListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private List<Node> pptList;
    private CommonAdapter mAdapter;


    private OnFragmentInteractionListener mListener;
    private ListView listView;
    private SwipeRefreshLayout refreshLayout;

    public PListFragment() {
        // Required empty public constructor
    }

    public void updateList() {
        if (pptList != null) {
            pptList.clear();
            getData();
        }
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PListFragment newInstance(String param1, String param2) {
        PListFragment fragment = new PListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
//        pptList.clear();
//        getData();
        LogUtils.d("===setUserVisibleHint==" + isVisibleToUser);
//        if (isVisibleToUser) {
//            if (pptList != null) {
//                pptList.clear();
//            }
//            getData();
//        } else {
//            if (pptList != null && mAdapter != null) {
//                pptList.clear();
//                mAdapter.notifyDataSetChanged();
//            }
//        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        LogUtils.d("oncreate==" + mParam1);
    }


    protected void initView(View view) {
        pptList = new ArrayList<>();
        refreshLayout = ((SwipeRefreshLayout) view.findViewById(R.id.refreshLayout));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pptList.clear();
                getData();
            }
        });

        listView = ((ListView) view.findViewById(R.id.pptfragment_list));
        mAdapter = new CommonAdapter<Node>(getContext(), R.layout.item_download, pptList) {
            @Override
            protected void convert(ViewHolder viewHolder, Node item, int position) {

                int istransing = item.getIstransform();

                viewHolder.setText(ppt_title, item.getTitle());
                viewHolder.setText(R.id.ppt_time, "日期:" + item.getPostdate());
                ImageView imageView = viewHolder.getView(R.id.ppt_icon);
                if (istransing == 0) {
                    Glide.with(getContext()).load(R.mipmap.istransform).into(imageView);
                    viewHolder.setText(R.id.ppt_status, "转换中");
                } else {
                    Glide.with(getContext()).load(item.getCoverimage()).error(R.mipmap.istransform).into(imageView);
                    viewHolder.setText(R.id.ppt_status, item.getPptstatus());
                }
            }
        };
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listView.setAdapter(mAdapter);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                final Node node = pptList.get(position);
                int istransform = node.getIstransform();
                if (istransform == 0) {
                    //未转换完成
                    Dialog dialog = DialogUtils.createStatusDailog(getActivity(), "提示信息", "转换中，请稍后....", true);
                    dialog.show();
                } else if (istransform == 1) {
                    if (node.getPptstatuskey() == 0 || node.getPptstatuskey() == 3) {
                        //未发布dialog
                        showDialogFirst(node);
                    } else if (node.getPptstatuskey() == 1) {
                        //已发布的dialog
                        showDialogTwice(node);
                    }

                }

            }
        });

    }

    //已发布的dialog
    private void showDialogTwice(Node node) {
        String[] strings = new String[]{"制作", "取消发布", "删除", "取消", "分享"};
        Dialog dialog = DialogUtils.createMakepptDialog(getActivity(), new DialogUtils.DialogPListListener() {
            @Override
            public void onMakeListener(Node ppt) {
                Intent intent = new Intent(getActivity(), MakepptActivity.class);
                intent.putExtra("MakepptActivity", ppt);
                getActivity().startActivity(intent);
            }

            @Override
            public void onPostListener(Node ppt) {
                postPPT(ppt, "3", "canclePost");

            }

            @Override
            public void onDeleteLsitener(Node ppt) {
                postPPT(ppt, "2", "delete");

            }

            @Override
            public void onShareListener(Node ppt) {
//                Intent intent = new Intent(getActivity(), PostSuccessActivity.class);
//                intent.putExtra("PostSuccessActivity", ppt);
//                getActivity().startActivity(intent);
                if (ppt.getNid() != null) {
                    getShareMessage(ppt.getNid());
                }

            }

            @Override
            public void onCancleListener() {

            }
        }, node, strings);
        dialog.show();
    }


    //得到分享需要的信息
    private void getShareMessage(String nid) {
        Map<String, String> map = UrlTool.getMapParams("nid", nid);
        String json = new Gson().toJson(map);
        SendActtionTool.getJson(Constents.GetShareMessage, nid, "shareMessage", new CommonListener() {
            @Override
            public void onSuccess(Object action, Object value) {
                LogUtils.d("onSuccess==" + value.toString());
                try {
                    List<RecordParent.RecordData> datas = new Gson().fromJson(((JSONObject) value).getString("datas"), new TypeToken<List<RecordParent.RecordData>>() {
                    }.getType());
                    if (datas != null) {
                        RecordeBean data = datas.get(0).getData();
                        // LogUtils.d("===="+data.toString());
                        String title = "";
                        title = data.getTitle();
                        String summary = "";
                        summary = data.getField_pptf_summary();
                        String shareUrl = "";
                        shareUrl = data.getShare_url();
                        String img_file = "";
                        img_file = data.getField_ppti_image_file();

                        //  if (data.getTitle() != null && data.getField_pptf_summary() != null && data.getShare_url() != null && data.getField_ppti_image_file() != null) {
                        Utils.shareTo(getActivity(), title, summary, shareUrl, img_file, getActivity());
                        //}
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFaile(Object action, Object value) {
                LogUtils.d("onFaile" + value.toString() + action.toString());
            }

            @Override
            public void onException(Object action, Object value) {
                LogUtils.d("onException" + value.toString() + action.toString());

            }

            @Override
            public void onStart(Object action) {

            }

            @Override
            public void onFinish(Object action) {
                LogUtils.d("onfinish");

            }
        });
    }

    //未发布的dialog
    private void showDialogFirst(Node node) {
        String[] str = new String[]{"制作", "发布", "删除", "取消"};
        Dialog dialog = DialogUtils.createMakepptDialog(getActivity(), new DialogUtils.DialogPListListener() {
            @Override
            public void onMakeListener(Node ppt) {
                Intent intent = new Intent(getActivity(), MakepptActivity.class);
                intent.putExtra("MakepptActivity", ppt);
                getActivity().startActivity(intent);
            }

            @Override
            public void onPostListener(Node ppt) {
                Dialog tipDialog = DialogUtils.createMakeTipDialog(getActivity(), new DialogUtils.DialogPListListener() {
                    @Override
                    public void onMakeListener(Node ppt) {
                    }

                    @Override
                    public void onPostListener(Node ppt) {
                    }

                    @Override
                    public void onDeleteLsitener(Node ppt) {
                        //ppt发布
                        postPPT(ppt, "1", "post");
                    }

                    @Override
                    public void onShareListener(Node ppt) {
                    }

                    @Override
                    public void onCancleListener() {

                    }
                }, ppt, "您确定发布么？", "确定");
                tipDialog.show();
            }

            @Override
            public void onDeleteLsitener(Node ppt) {
                postPPT(ppt, "2", "delete");

            }

            @Override
            public void onShareListener(Node ppt) {
                //分享
            }

            @Override
            public void onCancleListener() {

            }
        }, node, str);

        dialog.show();

    }

    //发布ppt /删除ppt /取消发布
    private void postPPT(Node ppt, String status, String tag) {
        Map<String, StatusBean> map = new LinkedHashMap<String, StatusBean>();
        map.put(ppt.getNid(), new StatusBean(status));
        String ss = new Gson().toJson(map);
        LogUtils.d("======ppt发布=====" + Constents.CHANGE_PPT_ONLINE + ss);
    //    SendActtionTool.getJson(Constents.CHANGE_PPT_ONLINE, ss, tag, this);
        SendActtionTool.postJson(Constents.CHANGE_PPT_ONLINE, ss, tag, this);
        postNode = ppt;
    }

    private Node postNode;
    private Node deleteNode;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plist, container, false);
        initView(view);
        LogUtils.d("==onCreateView==");
        return view;
    }


    //    @Override
//    protected View setContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_plist, container, false);
//        initView(view);
//        return view;
//    }

//    @Override
//    protected void stopLoad() {
//        pptList.clear();
//    }
//
//    @Override
//    protected void lazyLoad() {
//        getData();
//    }

    //获取网络数据
    private void getData() {
        long timeMillis = System.currentTimeMillis();
        if (TextUtils.isEmpty(mParam1)) {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
            String uid = sharedPreferences.getString(Constents.UID, null);
            SendActtionTool.getJson(Constents.PPT_LIST, uid + "?" + timeMillis, "list", this);
        } else {
            SendActtionTool.getJson(Constents.PPT_LIST, mParam1 + "?" + timeMillis, "list", this);
        }
    }


    @Override
    public void onSuccess(Object action, Object value) {
        LogUtils.d("onsuccess==" + action + "===" + value.toString());
        switch (((String) action)) {
            case "list":
                refreshLayout.setRefreshing(false);
                LogUtils.d(value.toString());
                try {
                    PPTListBean nodes = new Gson().fromJson(value.toString(), PPTListBean.class);
                    List<NodeParent> nodes1 = nodes.getNodes();
                    for (int i = 0; i < nodes1.size(); i++) {
                        pptList.add(nodes1.get(i).getNode());
                    }
                    LogUtils.d("===listSize==" + pptList.size());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.notifyDataSetChanged();
                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "post":
                // LogUtils.d(value.toString());
                //发布成功跳转到成功界面（显示二维码）
                Intent intent = new Intent(getActivity(), PostSuccessActivity.class);
                if (postNode != null) {
                    intent.putExtra("PostSuccessActivity", postNode);
                }
                getActivity().startActivity(intent);

                if (pptList.contains(postNode)) {
                    int position = pptList.indexOf(postNode);
                    pptList.get(position).setPptstatus("已发布");
                    pptList.get(position).setPptstatuskey(1);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.notifyDataSetChanged();
                        }
                    });
                }
                break;
            case "delete":
                if (pptList.contains(postNode)) {
                    pptList.remove(postNode);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.notifyDataSetChanged();
                        }
                    });
                }
                break;
            case "canclePost":
                if (pptList.contains(postNode)) {
                    int position = pptList.indexOf(postNode);
                    pptList.get(position).setPptstatus("暂不发布");
                    pptList.get(position).setPptstatuskey(3);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.notifyDataSetChanged();
                        }
                    });
                }
                break;
        }
    }

    @Override
    public void onFaile(Object action, Object value) {
        LogUtils.d("onFaile" + value.toString());

    }

    @Override
    public void onException(Object action, Object value) {

    }

    @Override
    public void onPause() {
        super.onPause();
        pptList.clear();
        LogUtils.d("==onPause==");
    }

    @Override
    public void onStart(Object action) {
        LogUtils.d("==onStart==");

    }

    @Override
    public void onFinish(Object action) {
        LogUtils.d("==onFinish==");

    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.d("==onResume==");
        pptList.clear();
        getData();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    class StatusBean implements Serializable {
        private String pptStatus;

        public StatusBean(String pptStatus) {
            this.pptStatus = pptStatus;
        }

        public String getPptStatus() {
            return pptStatus;
        }

        public void setPptStatus(String pptStatus) {
            this.pptStatus = pptStatus;
        }
    }


}
