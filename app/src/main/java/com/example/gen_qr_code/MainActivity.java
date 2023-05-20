package com.example.gen_qr_code;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        imageView = findViewById(R.id.imageView);

        findViewById(R.id.button).setOnClickListener(v -> {
            String text = editText.getText().toString();
            byte[] utf8Bytes = text.getBytes(StandardCharsets.UTF_8);
            String utf8BytesString = new String(utf8Bytes, StandardCharsets.UTF_8);
            if (!TextUtils.isEmpty(utf8BytesString)) {
                Bitmap qrCode = null;
                try {
                    qrCode = createQRCode(utf8BytesString);
                } catch (WriterException e) {
                    throw new RuntimeException(e);
                }
                imageView.setImageBitmap(qrCode);
            }
        });
    }

    private Bitmap createQRCode(String text) throws WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 300, 300);
        Bitmap bitmap = Bitmap.createBitmap(300, 300, Bitmap.Config.RGB_565);
        for (int x = 0; x < 300; x++) {
            for (int y = 0; y < 300; y++) {
                bitmap.setPixel(x, y, bitMatrix.get(x, y) ? getResources().getColor(R.color.black) : getResources().getColor(R.color.white));
            }
        }
        return bitmap;
    }
}
