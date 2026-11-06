package cn.edu.sicnu.cs.ys.cardgame

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import cn.edu.sicnu.cs.ys.cardgame.EditInfoActivity
import android.content.Intent
import cn.edu.sicnu.cs.ys.cardgame.data.PlayerData

class MainActivity : AppCompatActivity() {
    private lateinit var tvName: TextView
    private lateinit var tvGender: TextView
    private lateinit var tvPhone: TextView
    private lateinit var tvHighScore: TextView
    private lateinit var btnEditInfo: Button
    private lateinit var btnStartGame: Button
    private lateinit var tvStudentId: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvHobby: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 绑定控件
        tvName = findViewById(R.id.tv_name)
        tvGender = findViewById(R.id.tv_gender)
        tvPhone = findViewById(R.id.tv_phone)
        tvHighScore = findViewById(R.id.tv_high_score)
        btnEditInfo = findViewById(R.id.btn_edit_info)
        btnStartGame = findViewById(R.id.btn_start_game)
        tvStudentId = findViewById(R.id.tv_student_id)
        tvEmail = findViewById(R.id.tv_email)
        tvHobby = findViewById(R.id.tv_hobby)


        // 按钮点击事件
        btnEditInfo.setOnClickListener {
            startActivity(Intent(this, EditInfoActivity::class.java))
        }

        btnStartGame.setOnClickListener {
            startActivity(Intent(this, GameActivity::class.java))
        }
    }

    // 页面返回时刷新玩家信息
    override fun onResume() {
        super.onResume()
        refreshPlayerInfo()
    }

    private fun refreshPlayerInfo() {
        tvName.text = getString(R.string.tv_name_label, PlayerData.name)
        tvGender.text = getString(R.string.tv_gender_label, PlayerData.gender)
        tvPhone.text = getString(R.string.tv_phone_label, PlayerData.phone)
        tvHighScore.text = getString(R.string.tv_highscore_label, PlayerData.highScore)

        // 新增以下3行，原有逻辑不动
        tvStudentId.text = getString(R.string.label_student_id, PlayerData.studentId)
        tvEmail.text = getString(R.string.label_email, PlayerData.email)
        tvHobby.text = getString(R.string.label_hobby, PlayerData.hobby)
    }

}