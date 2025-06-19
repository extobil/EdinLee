package com.project.edwinuas_nasmoco.api.ui.product;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip; // Import Chip yang benar
import com.project.edwinuas_nasmoco.R;
import com.project.edwinuas_nasmoco.api.ServerAPI;
import com.project.edwinuas_nasmoco.api.ui.dashboard.OrderHelper;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class ProductDetailActivity extends AppCompatActivity {

    private ImageView imgProductDetail;
    private TextView tvProductName, tvProductPrice, tvProductDescription, tvProductViews;
    // Menggunakan Chip untuk status dan kategori
    private Chip chipProductStatus, chipProductCategory;
    private TextView tvQuantity; // Hanya menampilkan kuantitas, bukan selector
    private MaterialButton btnAddToCart;
    // btnIncrease dan btnDecrease tidak lagi dideklarasikan
    private OrderHelper orderHelper;
    private Product product;

    // Kuantitas default, bisa diatur secara programatik jika ada logika lain
    private int quantity = 1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        // Inisialisasi view
        imgProductDetail = findViewById(R.id.imgProductDetail);
        tvProductName = findViewById(R.id.tvProductName);
        tvProductPrice = findViewById(R.id.tvProductPrice);
        // Pastikan inisialisasi Chip sesuai dengan ID di XML
        chipProductStatus = findViewById(R.id.chipProductStatus);
        chipProductCategory = findViewById(R.id.chipProductCategory);
        tvProductDescription = findViewById(R.id.tvProductDescription);
        tvProductViews = findViewById(R.id.tvProductViews);

        // Pastikan btnAddToCart diinisialisasi sebelum digunakan
        btnAddToCart = findViewById(R.id.btnAddToCart);

        tvQuantity = findViewById(R.id.tvQuantity);
        // Tombol increase/decrease tidak perlu diinisialisasi lagi di sini

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        orderHelper = new OrderHelper(this);

        // Ambil data produk dari intent
        product = getIntent().getParcelableExtra("product");

        if (product != null) {
            tampilkanDetailProduk();
        }

        // Listener untuk tombol + dan - sudah dihapus dari layout dan kode Java

        btnAddToCart.setOnClickListener(v -> {
            if (product.getStok() > 0) {
                // Gunakan nilai 'quantity' yang sudah ada (default 1 atau dari mana pun Anda mengaturnya)
                product.setOrderQuantity(quantity);
                orderHelper.addToOrder(product);
                Toast.makeText(this, "Produk ditambahkan ke keranjang (" + quantity + " item)", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Produk tidak tersedia", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Metode updateQuantity akan selalu menampilkan nilai 'quantity' saat ini
//    private void updateQuantity() {
//        tvQuantity.setText(String.valueOf(quantity));
//    }

    private void tampilkanDetailProduk() {
        tvProductName.setText(product.getMerk());

        // Format harga
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');
        DecimalFormat formatter = new DecimalFormat("#,###", symbols);
        String hargaFormatted = formatter.format(product.getHargajual());

        tvProductPrice.setText("Rp " + hargaFormatted); // Tambahkan "Rp " di sini

        // Tampilan kategori menggunakan Chip
        chipProductCategory.setText(product.getKategori());
        tvProductDescription.setText(product.getDeskripsi());
        tvProductViews.setText("Dilihat " + product.getViewCount() + "x");

        // Atur status stok pada Chip dan tombol Add To Cart
        if (product.getStok() == 0) {
            chipProductStatus.setText("Stok Habis");
            chipProductStatus.setChipBackgroundColorResource(R.color.red_inactive);
            chipProductStatus.setChipStrokeColorResource(R.color.red_border);
            chipProductStatus.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            btnAddToCart.setEnabled(false);
            btnAddToCart.setText("Stok Habis");
        } else {
            chipProductStatus.setText("Tersedia");
            chipProductStatus.setChipBackgroundColorResource(R.color.green_active);
            chipProductStatus.setChipStrokeColorResource(R.color.green_border);
            chipProductStatus.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
            btnAddToCart.setEnabled(true);
            btnAddToCart.setText("Tambah ke Keranjang"); // Pastikan teks ini konsisten
        }

//        updateQuantity(); // Untuk memastikan TextView kuantitas menampilkan nilai 'quantity' awal

        // Tampilkan gambar produk
        Glide.with(this)
                .load(new ServerAPI().IMAGE_BASE_URL + product.getFoto())
                .placeholder(android.R.drawable.ic_menu_report_image)
                .error(android.R.drawable.stat_notify_error)
                .into(imgProductDetail);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}