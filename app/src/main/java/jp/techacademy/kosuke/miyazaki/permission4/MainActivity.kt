package jp.techacademy.kosuke.miyazaki.permission4

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import jp.techacademy.kosuke.miyazaki.permission4.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Log.d("ANDROID", "許可された")
            } else {
                Log.d("ANDROID", "許可されなかった")
            }
        }

    // APIレベルによって許可が必要なパーミッションを切り替える
    private val readImagesPermission =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) Manifest.permission.READ_MEDIA_IMAGES
        else Manifest.permission.READ_EXTERNAL_STORAGE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.button.setOnClickListener {
            // パーミッションの許可状態を確認する
            if (checkSelfPermission(readImagesPermission) == PackageManager.PERMISSION_GRANTED) {
                // 許可されている
                Log.d("ANDROID", "許可されている")
            } else {
                Log.d("ANDROID", "許可されていない")
                // 許可されていないので許可ダイアログを表示する
                requestPermissionLauncher.launch(readImagesPermission)
            }
        }
    }
}
