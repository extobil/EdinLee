package com.project.edwinuas_nasmoco.api.ui.home;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.edwinuas_nasmoco.R;
import com.project.edwinuas_nasmoco.api.RegisterAPI;
import com.project.edwinuas_nasmoco.api.ui.product.Product;
import com.project.edwinuas_nasmoco.api.ui.product.ProductAdapter;
import com.project.edwinuas_nasmoco.api.ui.product.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryProductActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList = new ArrayList<>();
    private String selectedCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_product);

        recyclerView = findViewById(R.id.recyclerCategoryProduct);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        selectedCategory = getIntent().getStringExtra("selectedCategory");
        fetchProductsByCategory(selectedCategory);
    }

    private void fetchProductsByCategory(String category) {
        RegisterAPI api = RetrofitClient.getRetrofitInstance().create(RegisterAPI.class);
        Call<List<Product>> call = api.getProducts(); // Asumsikan endpoint getProducts() mengembalikan semua produk

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (Product product : response.body()) {
                        if (product.getKategori().equalsIgnoreCase(category)) {
                            productList.add(product);
                        }
                    }
                    productAdapter = new ProductAdapter(CategoryProductActivity.this, productList, null);
                    recyclerView.setAdapter(productAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(CategoryProductActivity.this, "Gagal memuat kategori", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
