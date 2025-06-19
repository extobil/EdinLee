package com.project.edwinuas_nasmoco.api.ui.notifications;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.project.edwinuas_nasmoco.R;

public class Informasi extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public Informasi() {
        // Diperlukan konstruktor kosong
    }

    public static Informasi newInstance(String param1, String param2) {
        Informasi fragment = new Informasi();
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
        View view = inflater.inflate(R.layout.fragment_informasi, container, false);

        // Inisialisasi view
        TextView textWebsite = view.findViewById(R.id.textWebsite);
        TextView textPhone = view.findViewById(R.id.textPhone);
        ImageView btnBack = view.findViewById(R.id.back);

        // Buka Instagram
        textWebsite.setOnClickListener(v -> openWebsite());

        // Panggil nomor telepon
        textPhone.setOnClickListener(v -> callPhone());

        // Tombol kembali ke fragment sebelumnya
        btnBack.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
            navController.navigate(R.id.navigation_notifications);
        });

        return view;
    }

    private void openWebsite() {
        String url = "https://www.instagram.com/nasmocosemarangmajapahit/";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    private void callPhone() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:+6282243749774"));
        startActivity(intent);
    }
}
