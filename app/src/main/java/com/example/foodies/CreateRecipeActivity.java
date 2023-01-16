package com.example.foodies;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodies.model.Model;
import com.example.foodies.model.Recipe;

import java.io.IOException;
import java.util.Objects;


public class CreateRecipeActivity extends AppCompatActivity implements
        BaseRecipeFragment.ImageListener {
    private static final int REQUEST_OPEN_GALLERY = 10;
    private static final int REQUEST_TO_ACCESS_GALLERY = 11;

    private Recipe currentRecipe;
    private boolean isUpdating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_OPEN_GALLERY && resultCode == RESULT_OK) {
//            Bitmap imageBitmap = Bundle extras = data.getExtras();
            Bitmap imageBitmap = null;
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
            ((BaseRecipeFragment) Objects.requireNonNull(getSupportFragmentManager()
                    .findFragmentById(R.id.frame_fragment))).onImageSelected(imageBitmap);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_TO_ACCESS_GALLERY) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                openGallery();
            else
                Toast.makeText(this, "Permission denied to access the gallery.", Toast.LENGTH_LONG)
                        .show();
        }
    }

    @Override
    public void onSelectImage() {
        openGallery();
    }

    //    @Override
    public void onStepsFinished() {
        if (isUpdating) {
            Model.instance().addRecipe(currentRecipe, () -> {
                System.out.println("success");
            });
        } else {
            Model.instance().addRecipe(currentRecipe, () -> {
                System.out.println("success");
            });
        }

        Log.i("CreateRecipeActivity", "Final recipe: " + currentRecipe);
        finish();
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
        gallery.setType("image/*");
        startActivityForResult(gallery, REQUEST_OPEN_GALLERY);
    }
}