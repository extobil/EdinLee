package com.project.edwinuas_nasmoco.api.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
// import android.widget.ImageView; // Tidak perlu import ImageView secara eksplisit jika menggunakan binding

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.project.edwinuas_nasmoco.databinding.FragmentOrderBinding;

import java.util.List;

public class OrderFragment extends Fragment {
    private FragmentOrderBinding binding;
    private OrderHelper orderHelper;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentOrderBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.recyclerViewOrders.setNestedScrollingEnabled(false);
        orderHelper = new OrderHelper(requireContext());
        setupRecyclerView();

        binding.btnCheckout.setOnClickListener(v -> {
            List<OrderItem> orderItems = orderHelper.getOrderItems();

            if (orderItems.isEmpty()) {
                Toast.makeText(getContext(), "Keranjang kosong!", Toast.LENGTH_SHORT).show();
                return;
            }

            Gson gson = new Gson();
            String json = gson.toJson(orderItems);

            Log.d("OrderFragment", "Checkout orderItems JSON: " + json);

            Intent intent = new Intent(getContext(), Checkout.class);
            intent.putExtra("orderList", json);
            startActivity(intent);
        });

        return root;
    }

    // ... (kode di atas)

    private void setupRecyclerView() {
        RecyclerView recyclerView = binding.recyclerViewOrders;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<OrderItem> orderItems = orderHelper.getOrderItems();

        if (orderItems.isEmpty()) {
            Log.d("OrderFragment", "Keranjang kosong: Menampilkan emptyCartContainer, menyembunyikan RecyclerView, Total, Checkout.");
            binding.emptyCartContainer.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            binding.tvTotal.setVisibility(View.GONE);
            binding.btnCheckout.setVisibility(View.GONE);
        } else {
            Log.d("OrderFragment", "Keranjang berisi: Menyembunyikan emptyCartContainer, menampilkan RecyclerView, Total, Checkout.");
            binding.emptyCartContainer.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            binding.tvTotal.setVisibility(View.VISIBLE);
            binding.btnCheckout.setVisibility(View.VISIBLE);

            OrderAdapter adapter = new OrderAdapter(requireContext(), orderItems, orderHelper);
            recyclerView.setAdapter(adapter);

            updateTotal();

            if (adapter instanceof OrderAdapterWithListener) {
                ((OrderAdapterWithListener) adapter).setOnOrderChangeListener(this::updateTotal);
            }
        }
    }

// ... (kode di bawah)

    private void updateTotal() {
        double total = orderHelper.getTotal();
        binding.tvTotal.setText(String.format("Total Bayar: Rp %,.0f", total));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public interface OrderAdapterWithListener {
        void setOnOrderChangeListener(Runnable listener);
    }
}