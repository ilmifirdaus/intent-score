package id.putraprima.skorbola;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import static android.content.Intent.ACTION_PICK;

public class MainActivity extends AppCompatActivity {

    ImageView homeLogo, awayLogo;
    EditText homeName, awayName;
    Uri imageUri1;
    Uri imageUri2;
    Bitmap bitmap1;
    Bitmap bitmap2;
    private static final String TAG = MainActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TODO
        //Fitur Main Activity
        //1. Validasi Input Home Team
        homeName = findViewById(R.id.home_team);
        //2. Validasi Input Away Team
        awayName = findViewById(R.id.away_team);

        homeLogo = findViewById(R.id.home_logo);
        awayLogo = findViewById(R.id.away_logo);


    }

    //3. Ganti Logo Home Team
    public void handleHomeImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    //4. Ganti Logo Away Team
    public void handleAwayImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (requestCode == 1) {
            if (data != null) {
                try {
                    imageUri1 = data.getData();
                    bitmap1 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri1);
                    homeLogo.setImageBitmap(bitmap1);
                } catch (IOException e) {
                    Toast.makeText(this, "Can't Load Image", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, e.getMessage());
                }
            }
        } else if (requestCode == 2) {
            if (data != null) {
                try {
                    imageUri2 = data.getData();
                    bitmap2 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri2);
                    awayLogo.setImageBitmap(bitmap2);
                } catch (IOException e) {
                    Toast.makeText(this, "Can't Load Image", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, e.getMessage());
                }
            }
        }
    }
    //5. Next Button Pindah Ke MatchActivity
    public void handleSubmit(View view) {
        String homeInput = homeName.getText().toString();
        String awayInput = awayName.getText().toString();
        if (!homeInput.equals("") && !awayInput.equals("") && bitmap1 != null && bitmap2 != null) {
            Intent intent = new Intent(this, MatchActivity.class);
            intent.putExtra("inputHome", homeInput);
            intent.putExtra("inputAway", awayInput);
            intent.putExtra("homeLogo", imageUri1.toString());
            intent.putExtra("awayLogo", imageUri2.toString());

            startActivity(intent);

        }else {
            Toast.makeText(this, "Harap isi data terlebih dahulu !!!", Toast.LENGTH_SHORT).show();
        }

    }
}
