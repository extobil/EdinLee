<<<<<<< HEAD
=======
<<<<<<< HEAD
=======

>>>>>>> f2cb6faf489d2697f7df7569dcdb12cea4ac2e14
>>>>>>> 52c3001d310bd25828220ae841d1469673023b2f
package com.project.edwinuas_nasmoco.api.ui.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
<<<<<<< HEAD
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
<<<<<<< HEAD
=======
import androidx.lifecycle.ViewModelProvider;
=======
import androidx.fragment.app.Fragment;
>>>>>>> f2cb6faf489d2697f7df7569dcdb12cea4ac2e14
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
>>>>>>> 52c3001d310bd25828220ae841d1469673023b2f
import androidx.recyclerview.widget.GridLayoutManager;

<<<<<<< HEAD
import com.bumptech.glide.Glide;
=======
>>>>>>> f2cb6faf489d2697f7df7569dcdb12cea4ac2e14
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.project.edwinuas_nasmoco.R;
<<<<<<< HEAD
=======
import com.project.edwinuas_nasmoco.api.RegisterAPI;
<<<<<<< HEAD
import com.project.edwinuas_nasmoco.api.ServerAPI;
=======
>>>>>>> f2cb6faf489d2697f7df7569dcdb12cea4ac2e14
>>>>>>> 52c3001d310bd25828220ae841d1469673023b2f
import com.project.edwinuas_nasmoco.api.ui.dashboard.OrderHelper;

import com.project.edwinuas_nasmoco.api.ui.product.Product;
import com.project.edwinuas_nasmoco.api.ui.product.ProductAdapter;
import com.project.edwinuas_nasmoco.api.ui.product.RetrofitClient;
import com.project.edwinuas_nasmoco.databinding.FragmentHomeBinding;

<<<<<<< HEAD
import org.json.JSONObject;

=======
>>>>>>> f2cb6faf489d2697f7df7569dcdb12cea4ac2e14
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
<<<<<<< HEAD
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
=======
>>>>>>> f2cb6faf489d2697f7df7569dcdb12cea4ac2e14

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";
    private FragmentHomeBinding binding;
    private String email;
    private OrderHelper orderHelper;
    private ProductAdapter adapter;
    private List<Product> fullProductList;

<<<<<<< HEAD
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize OrderHelper
        orderHelper = new OrderHelper(requireContext());

        // Setup RecyclerView
        binding.recyclerTrending.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.recyclerTrending.setNestedScrollingEnabled(false);

        // Get email from SharedPreferences
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("user_session", getContext().MODE_PRIVATE);
        email = sharedPreferences.getString("email", "");


        // Set up image slider
        setupImageSlider();

        setupCategoryClickListeners();
        fetchTrendingProducts();
=======
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        orderHelper = new OrderHelper(requireContext());

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("user_session", getContext().MODE_PRIVATE);
        email = sharedPreferences.getString("email", "");

        setupImageSlider();
        setupCategoryClickListeners();
        setupTrendingProducts();
>>>>>>> f2cb6faf489d2697f7df7569dcdb12cea4ac2e14

        return root;
    }

    private void setupImageSlider() {
        ImageSlider imageSlider = binding.imageSlider;
        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.img_3, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.img_2, ScaleTypes.FIT));
        imageSlider.setImageList(slideModels, ScaleTypes.FIT);
    }

<<<<<<< HEAD

    private void setupCategoryClickListeners() {
        binding.tabung.setOnClickListener(v -> navigateToProduct("Televisi Tabung"));
        binding.led.setOnClickListener(v -> navigateToProduct("Televisi LED"));
        binding.oled.setOnClickListener(v -> navigateToProduct("Televisi OLED"));
=======
    private void setupCategoryClickListeners() {
<<<<<<< HEAD
        binding.carMpv.setOnClickListener(v -> openCategoryActivity("MPV"));
        binding.carHybrid.setOnClickListener(v -> openCategoryActivity("Hybrid"));
        binding.carSuv.setOnClickListener(v -> openCategoryActivity("SUV"));
        binding.carSedan.setOnClickListener(v -> openCategoryActivity("Sedan"));
        binding.carCommercial.setOnClickListener(v -> openCategoryActivity("Commercial"));
        binding.carHatchback.setOnClickListener(v -> openCategoryActivity("Hatchback"));
    }

    private void openCategoryActivity(String category) {
        Intent intent = new Intent(requireContext(), CategoryProductActivity.class);
        intent.putExtra("selectedCategory", category);
        startActivity(intent);
=======
        binding.carMpv.setOnClickListener(v -> navigateToProduct("MPV"));
        binding.carHybrid.setOnClickListener(v -> navigateToProduct("Hybrid"));
        binding.carSuv.setOnClickListener(v -> navigateToProduct("SUV"));
        binding.carSedan.setOnClickListener(v -> navigateToProduct("Sedan"));
        binding.carCommercial.setOnClickListener(v -> navigateToProduct("Commercial"));
        binding.carHatchback.setOnClickListener(v -> navigateToProduct("Hatchback"));
>>>>>>> f2cb6faf489d2697f7df7569dcdb12cea4ac2e14
    }

    private void navigateToProduct(String category) {
        try {
            Bundle result = new Bundle();
            result.putString("selectedCategory", category);
            getParentFragmentManager().setFragmentResult("categoryRequest", result);

            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
            navController.navigate(R.id.navigation_product);

<<<<<<< HEAD
            // Update bottom nav selection
=======
>>>>>>> f2cb6faf489d2697f7df7569dcdb12cea4ac2e14
            BottomNavigationView bottomNav = requireActivity().findViewById(R.id.nav_view);
            if (bottomNav != null) {
                bottomNav.post(() -> bottomNav.setSelectedItemId(R.id.navigation_product));
            }
        } catch (Exception e) {
            Log.e(TAG, "Navigation error", e);
            Toast.makeText(getContext(), "Navigation error", Toast.LENGTH_SHORT).show();
        }
>>>>>>> 52c3001d310bd25828220ae841d1469673023b2f
    }

<<<<<<< HEAD
    private void navigateToProductWithSearch(String searchQuery) {
        try {
            Bundle result = new Bundle();
            result.putString("searchQuery", searchQuery);
            getParentFragmentManager().setFragmentResult("searchRequest", result);

            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
            navController.navigate(R.id.navigation_product);

            // Update bottom nav selection
            BottomNavigationView bottomNav = requireActivity().findViewById(R.id.nav_view);
            if (bottomNav != null) {
                bottomNav.post(() -> bottomNav.setSelectedItemId(R.id.navigation_product));
            }
        } catch (Exception e) {
            Log.e(TAG, "Navigation error", e);
            Toast.makeText(getContext(), "Navigation error", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchTrendingProducts() {
=======
    private void setupTrendingProducts() {
        binding.recyclerTrending.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.recyclerTrending.setNestedScrollingEnabled(false);

<<<<<<< HEAD
        Call<List<Product>> call = RetrofitClient.getRetrofitInstance()
                .create(com.project.edwinuas_nasmoco.api.RegisterAPI.class)
                .getProducts();
=======
>>>>>>> f2cb6faf489d2697f7df7569dcdb12cea4ac2e14
        RegisterAPI apiService = RetrofitClient.getRetrofitInstance().create(RegisterAPI.class);
        Call<List<Product>> call = apiService.getProducts();
>>>>>>> 52c3001d310bd25828220ae841d1469673023b2f

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Product> allProducts = response.body();
                    allProducts.sort((p1, p2) -> Integer.compare(p2.getViewCount(), p1.getViewCount()));
                    fullProductList = allProducts.subList(0, Math.min(6, allProducts.size()));
<<<<<<< HEAD

                    setupProductAdapter();
                } else {
                    Toast.makeText(getContext(), "Failed to load products", Toast.LENGTH_SHORT).show();
=======
                    setupProductAdapter();
                } else {
                    Toast.makeText(getContext(), "Gagal memuat produk", Toast.LENGTH_SHORT).show();
>>>>>>> f2cb6faf489d2697f7df7569dcdb12cea4ac2e14
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupProductAdapter() {
        adapter = new ProductAdapter(getContext(), fullProductList, new ProductAdapter.OnProductClickListener() {
            @Override
            public void onProductClick(Product product) {
                orderHelper.addToOrder(product);
<<<<<<< HEAD
                Toast.makeText(getContext(), product.getMerk() + " added to cart", Toast.LENGTH_SHORT).show();
=======
                Toast.makeText(getContext(), product.getMerk() + " ditambahkan ke keranjang", Toast.LENGTH_SHORT).show();
>>>>>>> f2cb6faf489d2697f7df7569dcdb12cea4ac2e14
            }

            @Override
            public void onProductViewClick(Product product, ProductAdapter.ViewHolder holder) {
                int newViewCount = product.getViewCount() + 1;
                product.setViewCount(newViewCount);
<<<<<<< HEAD
                holder.tvView.setText("Viewed " + newViewCount + "x");

=======
                holder.tvView.setText("Dilihat " + newViewCount + "x");
>>>>>>> f2cb6faf489d2697f7df7569dcdb12cea4ac2e14
                updateProductViewCount(product.getKode(), newViewCount);
            }
        });

        binding.recyclerTrending.setAdapter(adapter);
    }

    private void updateProductViewCount(String productCode, int viewCount) {
        Call<ResponseBody> updateCall = RetrofitClient.getRetrofitInstance()
                .create(com.project.edwinuas_nasmoco.api.RegisterAPI.class)
                .updateProductView(productCode, viewCount);

        updateCall.enqueue(new Callback<ResponseBody>() {
            @Override
<<<<<<< HEAD
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // Optional success handling
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Failed to update view count", Toast.LENGTH_SHORT).show();
=======
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) { }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Gagal update view count", Toast.LENGTH_SHORT).show();
>>>>>>> f2cb6faf489d2697f7df7569dcdb12cea4ac2e14
            }
        });
    }

<<<<<<< HEAD

=======
>>>>>>> f2cb6faf489d2697f7df7569dcdb12cea4ac2e14
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> f2cb6faf489d2697f7df7569dcdb12cea4ac2e14
