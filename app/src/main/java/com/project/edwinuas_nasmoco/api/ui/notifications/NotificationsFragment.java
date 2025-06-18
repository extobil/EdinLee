package com.project.edwinuas_nasmoco.api.ui.notifications;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.project.edwinuas_nasmoco.R;
import com.project.edwinuas_nasmoco.api.ForgotPasswordActivity;
import com.project.edwinuas_nasmoco.api.RegisterAPI;
import com.project.edwinuas_nasmoco.api.ServerAPI;
import com.project.edwinuas_nasmoco.api.login;
import com.project.edwinuas_nasmoco.databinding.FragmentNotificationsBinding;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private String email;
    private LinearLayout btnGantiPassword;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        btnGantiPassword = root.findViewById(R.id.btnGantiPassword);
        btnGantiPassword.setOnClickListener(v -> startActivity(new Intent(getActivity(), ForgotPasswordActivity.class)));
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("user_session", getContext().MODE_PRIVATE);
        email = sharedPreferences.getString("email", "");

        // Mendapatkan NavController
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        
        // Jika tidak login, arahkan ke GuestProfile Fragment
        if (email.isEmpty()) {
            navController.navigate(R.id.GuestProfile);
            return root;
        }

        // Jika login, ambil data profil
        getProfile(email);

        // Navigasi ke Edit Profile
        binding.menuEditProfile.setOnClickListener(v -> {
            navController.navigate(R.id.fragmentProfile);
        });


        // Navigasi ke Informasi Perusahaan
        binding.menuInfoPerusahaan.setOnClickListener(v -> {
            navController.navigate(R.id.fragmentInformasi);
        });

        // Klik menu Logout
        binding.menuLogout.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            Intent intent = new Intent(getActivity(), login.class);
            startActivity(intent);
            requireActivity().finish();
        });

        return root;
    }

    private void getProfile(String vemail) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new ServerAPI().BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RegisterAPI api = retrofit.create(RegisterAPI.class);
        api.getProfile(vemail).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        JSONObject json = new JSONObject(response.body().string());
                        if (json.getString("result").equals("1")) {
                            JSONObject data = json.getJSONObject("data");

                            String nama = data.getString("nama");
                            binding.textNamaUser.setText(nama);

                            String fotoFilename = data.optString("filename", "");
                            if (!fotoFilename.isEmpty()) {
                                String imageUrl = new ServerAPI().IMAGE_BASE_URL+ fotoFilename;

                                Glide.with(requireContext())
                                        .load(imageUrl)
                                        .circleCrop()
                                        .placeholder(R.drawable.logo)
                                        .error(R.drawable.logo)
                                        .into(binding.imgProfileNotif);
                            }
                        } else {
                            Toast.makeText(getContext(), "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Log.e("GetProfileNotif", "Error parsing", e);
                        Toast.makeText(getContext(), "Terjadi kesalahan saat membaca data", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("GetProfileNotif", "onFailure", t);
                Toast.makeText(getContext(), "Terjadi kesalahan koneksi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
