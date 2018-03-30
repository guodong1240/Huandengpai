package com.hzj.hpai.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.hzj.hpai.R;
import com.hzj.hpai.utils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link
 */
public class HelpFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageView help_icon;
    private Button before_bt;
    private Button next_bt;
    private List<Integer> bitmapList;
    private int current = 0;
    private Bitmap bitmap;

    public HelpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HelpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HelpFragment newInstance(String param1, String param2) {
        HelpFragment fragment = new HelpFragment();
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
        View view = inflater.inflate(R.layout.fragment_help, container, false);
        bitmapList = new ArrayList<>();
        initView(view);
        return view;
    }

    private void initView(View view) {
        help_icon = ((ImageView) view.findViewById(R.id.help_icon));
        before_bt = ((Button) view.findViewById(R.id.before_bt));
        next_bt = ((Button) view.findViewById(R.id.next_bt));
        before_bt.setOnClickListener(this);
        before_bt.setVisibility(View.INVISIBLE);
        before_bt.setClickable(false);
        next_bt.setOnClickListener(this);


        getBitmap();

    }

    private void getBitmap() {

        bitmapList.add(R.drawable.one);
        bitmapList.add(R.drawable.two);
        bitmapList.add(R.drawable.three);
        bitmapList.add(R.drawable.four);

        bitmap = BitmapUtils.readBitmap(getContext(), bitmapList.get(0));
        help_icon.setImageBitmap(bitmap);
        current = 0;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.next_bt:
                current++;
                if (current>=0  && current<=3){
                    bitmap=BitmapUtils.readBitmap(getContext(),bitmapList.get(current));
                    help_icon.setImageBitmap(bitmap);
                }
                before_bt.setVisibility(View.VISIBLE);
                before_bt.setClickable(true);
                if (current ==3) {
                    next_bt.setVisibility(View.INVISIBLE);
                    next_bt.setClickable(false);
                } else {
                    next_bt.setVisibility(View.VISIBLE);
                    next_bt.setClickable(true);
                }
                break;
            case R.id.before_bt:
                current--;
                if (current>=0  && current<=3){
                    bitmap=BitmapUtils.readBitmap(getContext(),bitmapList.get(current));
                    help_icon.setImageBitmap(bitmap);
                }
                next_bt.setVisibility(View.VISIBLE);
                next_bt.setClickable(true);
                if (current == 0) {
                    before_bt.setClickable(false);
                    before_bt.setVisibility(View.INVISIBLE);
                } else {
                    before_bt.setVisibility(View.VISIBLE);
                    before_bt.setClickable(true);
                }
                break;
        }
    }

}
