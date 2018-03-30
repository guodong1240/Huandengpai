package com.hzj.hpai.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hzj.hpai.R;
import com.hzj.hpai.adapter.abslistview.CommonAdapter;
import com.hzj.hpai.adapter.abslistview.ViewHolder;
import com.hzj.hpai.item.Constents;
import com.hzj.hpai.maganer.DBManager;
import com.hzj.hpai.networkrequests.CommonListener;
import com.hzj.hpai.networkrequests.SendActtionTool;
import com.hzj.hpai.tool.UrlTool;
import com.hzj.hpai.utils.DialogUtils;
import com.hzj.hpai.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import db.bean.LocalPPT;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LocalFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LocalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LocalFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ListView listView;
    private CommonAdapter<LocalPPT> mAdapter;
    private List<LocalPPT> pptList;
    private TextView ceshi;
    private SwipeRefreshLayout refreshLayout;
    private PostSuccess postSuccess;

    public interface  PostSuccess{
        void onPostSuccess(boolean b);
    }

    public LocalFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LocalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LocalFragment newInstance(String param1, String param2) {
        LocalFragment fragment = new LocalFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        postSuccess= ((PostSuccess) getActivity());
        View view = inflater.inflate(R.layout.fragment_local, container, false);
        listView = ((ListView) view.findViewById(R.id.local_list));
        refreshLayout = ((SwipeRefreshLayout) view.findViewById(R.id.refreshLayout));
        pptList = new ArrayList<>();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pptList.clear();
                getListData();
            }
        });


        initAdapter();
        getListData();
        return view;
    }

    private void initAdapter() {

        mAdapter = new CommonAdapter<LocalPPT>(getContext(), R.layout.ppt_item, pptList) {
            @Override
            protected void convert(ViewHolder viewHolder, LocalPPT item, int position) {
                if (item.getTitle() != null) {
                    viewHolder.setText(R.id.ppt_title, item.getTitle());
                } else {
                    viewHolder.setText(R.id.ppt_title, "===");
                }
                if (item.getStatus() != null) {
                    viewHolder.setText(R.id.ppt_status, "已上传");
                } else {
                    viewHolder.setText(R.id.ppt_status, "未上传");
                }
                if (item.getTime() != null) {
                    viewHolder.setText(R.id.ppt_time, "日期：" + item.getTime());
                } else {
                    viewHolder.setText(R.id.ppt_time, "日期：");
                }
                LogUtils.d("====" + item.getStatus());
                if (item.getTitle().contains("pdf")) {
                    viewHolder.setImageResource(R.id.ppt_icon, R.mipmap.pdf_icon);
                } else if (item.getTitle().contains("ppt")) {
                    viewHolder.setImageResource(R.id.ppt_icon, R.mipmap.ppticon);
                }

            }
        };
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, final View view, final int position, long id) {
                if (TextUtils.isEmpty(pptList.get(position).getStatus())) {
                    Dialog dialog = DialogUtils.createPPTDialog(getActivity(), new DialogUtils.DialogMakeListener() {
                        @Override
                        public void onDeleteListener(LocalPPT ppt) {
                            DBManager manager = DBManager.getInstance(getContext());

                            manager.deleteUser(ppt);
                            if (pptList.contains(ppt)) {
                                pptList.remove(ppt);
                            }
                            mAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCountinue(final LocalPPT ppt) {
                            ((TextView) view.findViewById(R.id.ppt_status)).setText("上传中");
                            refreshLayout.setRefreshing(false);
                            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
                            String uid = sharedPreferences.getString(Constents.UID, "");
                            LogUtils.d("==uid==" + uid);
                            if (!TextUtils.isEmpty(uid)) {
                                String filePath = ppt.getPath();
                                String fileName = ppt.getTitle();
                                File file = new File(filePath);
                                LogUtils.d(fileName);
                                final Map<String, String> map = UrlTool.getMapParams("title", fileName, Constents.UID, uid);

                                SendActtionTool.postFile(Constents.UPLOAD_PPT, new CommonListener() {
                                    @Override
                                    public void onSuccess(Object action, Object value) {
                                        LogUtils.d("===onSuccess=update==" + value.toString());
                                        Toast.makeText(getContext(), "上传成功！", Toast.LENGTH_SHORT).show();
                                        postSuccess.onPostSuccess(true);
                                        try {
                                            String nid = new Gson().fromJson((String) ((JSONObject) value).get("data"), String.class);
                                            LogUtils.d("=====nid==" + nid);
                                            ppt.setStatus(nid);
                                            DBManager manager = DBManager.getInstance(getContext());
                                            manager.updateUser(ppt);
                                            mAdapter.notifyDataSetChanged();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onFaile(Object action, Object value) {
                                        LogUtils.d("=onFaile==" + value.toString());
                                        Toast.makeText(getContext(), "上传失败，请重新上传", Toast.LENGTH_SHORT).show();
                                        mAdapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onException(Object action, Object value) {
                                        LogUtils.d("==onException==" + value.toString());
                                        Toast.makeText(getContext(), "网络不给力，请重新上传", Toast.LENGTH_SHORT).show();
                                        mAdapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onStart(Object action) {

                                    }

                                    @Override
                                    public void onFinish(Object action) {

                                    }
                                }, map, fileName, file, " ");
                            }


                        }
                    }, pptList.get(position));
                    dialog.show();
                } else {
                    Dialog dialog = DialogUtils.createTipDialog(getActivity(), new DialogUtils.DialogTipListener() {
                        @Override
                        public void onDeleteListener(LocalPPT ppt) {
                            DBManager manager = DBManager.getInstance(getContext());
                            manager.deleteUser(ppt);
                            pptList.remove(ppt);
                            mAdapter.notifyDataSetChanged();
                        }
                    }, pptList.get(position), "删除");
                    dialog.show();
                }
            }
        });

    }

    private void getListData() {

        DBManager manager = DBManager.getInstance(getContext());
        pptList.addAll(manager.queryUserList());
        if (pptList.size() > 0) {
            mAdapter.notifyDataSetChanged();
        }
        refreshLayout.setRefreshing(false);


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
}
