package com.example.project;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Hair_routine extends AppCompatActivity {

    RadioGroup rgHairType, rgHairCondition;
    CheckBox cbAllergy;
    EditText etAllergyDetails;
    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hair_routine);

        rgHairType = findViewById(R.id.rgHairType);
        rgHairCondition = findViewById(R.id.rgHairCondition);
        cbAllergy = findViewById(R.id.cbAllergy);
        etAllergyDetails = findViewById(R.id.etAllergyDetails);
        btnNext = findViewById(R.id.btnNext);

        // Show the allergy detail input only if the allergy checkbox is checked
        cbAllergy.setOnCheckedChangeListener((buttonView, isChecked) -> {
            etAllergyDetails.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        });

        btnNext.setOnClickListener(view -> {
            String hairType = getSelectedRadioText(rgHairType);
            String hairCondition = getSelectedRadioText(rgHairCondition);
            String allergyDetails = cbAllergy.isChecked() ? etAllergyDetails.getText().toString() : "None";

            if (hairType == null || hairCondition == null || (cbAllergy.isChecked() && allergyDetails.isEmpty())) {
                Toast.makeText(Hair_routine.this, "Please answer all questions", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(Hair_routine.this, cosmetic_recommendation_h.class);
            intent.putExtra("hairType", hairType);
            intent.putExtra("hairCondition", hairCondition);
            intent.putExtra("hasAllergy", cbAllergy.isChecked());
            intent.putExtra("allergyDetails", allergyDetails);
            startActivity(intent);

            Log.d("CosmeticHairActivity", "Transitioning with hairType: " + hairType + " and hairCondition: " + hairCondition);
        });
    }

    private String getSelectedRadioText(RadioGroup radioGroup) {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        if (selectedId != -1) {
            RadioButton radioButton = findViewById(selectedId);
            return radioButton.getText().toString();
        }
        return null;
    }
}
