package com.example.project;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class cosmetic_recommendation_h extends AppCompatActivity {

    ListView listView;
    ProductAdapter adapter;
    List<Product> cosmeticProducts;
    TextView tvTotalPrice;
    Button btnCustomRoutine; // Button to navigate to CustomRoutineActivity

    String hairType, hairCondition, allergyDetails;
    boolean hasAllergy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cosmetic_recommendation_h);

        listView = findViewById(R.id.lvCosmeticProducts);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        btnCustomRoutine = findViewById(R.id.btnCustomRoutine);

        // Get Intent data
        hairType = getIntent().getStringExtra("hairType");
        hairCondition = getIntent().getStringExtra("hairCondition");
        hasAllergy = getIntent().getBooleanExtra("hasAllergy", false);
        allergyDetails = getIntent().getStringExtra("allergyDetails");

        // Set up the button click listener
        btnCustomRoutine.setOnClickListener(v -> {
            Intent intent = new Intent(cosmetic_recommendation_h.this, Custom_hair_routine.class);
            intent.putExtra("hairType", hairType);
            intent.putExtra("hairCondition", hairCondition);
            intent.putExtra("hasAllergy", hasAllergy);
            intent.putExtra("allergyDetails", allergyDetails);
            startActivity(intent);
        });

        cosmeticProducts = new ArrayList<>();
        setupCosmeticProducts();
        recommendProducts();
        calculateTotalPrice();
    }

    private void setupCosmeticProducts() {
        // Adding cosmetic hair products with ingredients and allergens
        cosmeticProducts.add(new Product("Moisturizing Shampoo", "Ideal for dry hair", 10, "Water, Sodium Laureth Sulfate, Cocamidopropyl Betaine, Fragrance", "Sodium Laureth Sulfate"));
        cosmeticProducts.add(new Product("Volume Conditioner", "Adds volume to thin hair", 12, "Water, Polyquaternium-7, Dimethicone, Fragrance", "Dimethicone"));
        cosmeticProducts.add(new Product("Clarifying Shampoo", "Removes excess oil and buildup", 8, "Water, Sodium Lauryl Sulfate, Citric Acid, Fragrance", "Sodium Lauryl Sulfate"));
        cosmeticProducts.add(new Product("Hypoallergenic Conditioner", "Gentle for sensitive scalps", 15, "Water, Cetyl Alcohol, Stearyl Alcohol, No added fragrance", "None"));
        cosmeticProducts.add(new Product("Hydrating Hair Mask", "Provides moisture for dry, wavy hair", 13, "Water, Shea Butter, Glycerin, Fragrance", "Shea Butter"));
        cosmeticProducts.add(new Product("Lightweight Conditioner", "Perfect for oily hair, adds shine", 11, "Water, Polyquaternium-10, Fragrance, Silicone-free", "None"));
        cosmeticProducts.add(new Product("Intensive Moisture Treatment", "For extremely dry, coily hair", 16, "Water, Coconut Oil, Shea Butter, Fragrance", "Coconut Oil"));
        cosmeticProducts.add(new Product("Gentle Care Shampoo", "Suitable for sensitive and coily hair", 14, "Water, Sodium Cocoyl Isethionate, No parabens, fragrance-free", "None"));
    }

    private void recommendProducts() {
        List<Product> recommendedProducts = new ArrayList<>();
        String[] allergens = (allergyDetails == null || allergyDetails.isEmpty()) ? new String[0] : allergyDetails.split(",\\s*");

        for (Product product : cosmeticProducts) {
            boolean containsAllergen = false;

            for (String allergen : allergens) {
                if (product.getIngredients().toLowerCase().contains(allergen.toLowerCase().trim())) {
                    containsAllergen = true;
                    break;
                }
            }

            if (containsAllergen) {
                continue;
            }

            // Product recommendations based on hairType and hairCondition
            if (hairType.equals("Curly")) {
                if (hairCondition.equals("Dry") && product.getName().equals("Moisturizing Shampoo")) {
                    recommendedProducts.add(product);
                } else if (hairCondition.equals("Normal") && product.getName().equals("Volume Conditioner")) {
                    recommendedProducts.add(product);
                }
            } else if (hairType.equals("Straight")) {
                if (hairCondition.equals("Oily") && product.getName().equals("Clarifying Shampoo")) {
                    recommendedProducts.add(product);
                } else if (hairCondition.equals("Sensitive") && product.getName().equals("Hypoallergenic Conditioner")) {
                    recommendedProducts.add(product);
                }
            } else if (hairType.equals("Wavy")) {
                if (hairCondition.equals("Dry") && product.getName().equals("Hydrating Hair Mask")) {
                    recommendedProducts.add(product);
                } else if (hairCondition.equals("Oily") && product.getName().equals("Lightweight Conditioner")) {
                    recommendedProducts.add(product);
                }
            } else if (hairType.equals("Coily")) {
                if (hairCondition.equals("Dry") && product.getName().equals("Intensive Moisture Treatment")) {
                    recommendedProducts.add(product);
                } else if (hairCondition.equals("Sensitive") && product.getName().equals("Gentle Care Shampoo")) {
                    recommendedProducts.add(product);
                }
            }
        }

        // If no products match, recommend all non-allergenic products
        if (recommendedProducts.isEmpty()) {
            for (Product product : cosmeticProducts) {
                boolean containsAllergen = false;
                for (String allergen : allergens) {
                    if (product.getIngredients().toLowerCase().contains(allergen.toLowerCase().trim())) {
                        containsAllergen = true;
                        break;
                    }
                }
                if (!containsAllergen) {
                    recommendedProducts.add(product);
                }
            }
        }

        // Set the adapter with recommended products
        adapter = new ProductAdapter(this, recommendedProducts);
        listView.setAdapter(adapter);
    }

    private void calculateTotalPrice() {
        double total = 0;
        if (adapter != null && adapter.getProducts() != null) {
            for (Product product : adapter.getProducts()) {
                total += product.getPrice();
            }
        }
        tvTotalPrice.setText("Total: Rs." + total);
    }
}