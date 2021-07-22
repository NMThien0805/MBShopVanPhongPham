package com.example.doancuoiki.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doancuoiki.Adapter.HistoryProductAdapter;
import com.example.doancuoiki.DataLocalManage;
import com.example.doancuoiki.MainActivity;
import com.example.doancuoiki.R;
import com.example.doancuoiki.UserLogged;
import com.example.doancuoiki.Users;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link inFoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class inFoFragment extends Fragment implements Animation.AnimationListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Animation FadeIn,SlideDown,Blink,Move,MoveNguoc;

    private View mView;
    private TextView tv_ten,tv_email,tv_sdt,tv_diachi;
    private ImageView img_avatar;
    private Button btn_dangxuat;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    UserLogged users;

    public UserLogged getUsers() {
        return users;
    }

    public void setUsers(UserLogged users) {
        this.users = users;
    }

    public inFoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment inFoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static inFoFragment newInstance(String param1, String param2) {
        inFoFragment fragment = new inFoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private void Anhxa() {
        tv_ten = (TextView)mView.findViewById(R.id.frag_user_hoten);
        tv_sdt=(TextView)mView.findViewById(R.id.frag_user_sdt);
        tv_diachi=(TextView)mView.findViewById(R.id.frag_user_diachi);
        tv_email=(TextView)mView.findViewById(R.id.frag_user_email);
        img_avatar=(ImageView)mView.findViewById(R.id.frag_user_img);
        btn_dangxuat=(Button)mView.findViewById(R.id.frag_user_button_dangxuat);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public Bitmap chuyen_ArrByte_sang_Bitmap(byte[] img){
//        System.out.println("show" + img);
        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        return  bitmap;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView =  inflater.inflate(R.layout.fragment_in_fo, container, false);
        Anhxa();
        Bundle bundle=getArguments();
        if(bundle!=null){
            setUsers((UserLogged) bundle.getSerializable("object_user"));
            DataLocalManage.setUser(getUsers());
        }

        Users usertam= DataLocalManage.getUser();

        if(usertam!=null){
            tv_ten.setText(usertam.getName());
            tv_email.setText(usertam.getMail());
            tv_diachi.setText(usertam.getDiachi());
            tv_sdt.setText(usertam.getSdt());
//            img_avatar.setImageBitmap(chuyen_ArrByte_sang_Bitmap(usertam.getImg()));

            Picasso.get().load(usertam.getImg()).into(img_avatar);
        }
        FadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.fadein);
        FadeIn.setAnimationListener(this);
        SlideDown=AnimationUtils.loadAnimation(getContext(), R.anim.slidedown);
        SlideDown.setAnimationListener(this);
        Move=AnimationUtils.loadAnimation(getContext(), R.anim.movex);
        Move.setAnimationListener(this);
        MoveNguoc=AnimationUtils.loadAnimation(getContext(), R.anim.movexnguoc);
        MoveNguoc.setAnimationListener(this);

        tv_ten.setAnimation(FadeIn);
        tv_sdt.setAnimation(FadeIn);
        tv_email.setAnimation(FadeIn);
        tv_diachi.setAnimation(FadeIn);
        img_avatar.setAnimation(FadeIn);
        btn_dangxuat.setAnimation(MoveNguoc);
        btn_dangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });

        return mView;
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}