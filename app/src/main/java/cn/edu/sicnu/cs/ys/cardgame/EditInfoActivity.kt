package cn.edu.sicnu.cs.ys.cardgame

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cn.edu.sicnu.cs.ys.cardgame.data.PlayerData
import com.google.android.material.textfield.TextInputEditText

class EditInfoActivity : AppCompatActivity() {
    private lateinit var etName: TextInputEditText
    private lateinit var etGender: TextInputEditText
    private lateinit var etPhone: TextInputEditText
    private lateinit var btnConfirm: Button
    private lateinit var btnCancel: Button

    private lateinit var etStudentId: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var rgGender: RadioGroup
    private lateinit var cbTravel: CheckBox
    private lateinit var cbRead: CheckBox
    private lateinit var cbGame: CheckBox
    private lateinit var cbShop: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_info)

        etName = findViewById(R.id.et_name)
        etGender = findViewById(R.id.et_gender)
        etPhone = findViewById(R.id.et_phone)
        btnConfirm = findViewById(R.id.btn_confirm)
        btnCancel = findViewById(R.id.btn_cancel)

        etStudentId = findViewById(R.id.et_student_id)
        etEmail = findViewById(R.id.et_email)
        rgGender = findViewById(R.id.rg_gender)
        cbTravel = findViewById(R.id.cb_travel)
        cbRead = findViewById(R.id.cb_read)
        cbGame = findViewById(R.id.cb_game)
        cbShop = findViewById(R.id.cb_shop)

        // 数据回显
        etName.setText(PlayerData.name)
        etGender.setText(PlayerData.gender)
        etPhone.setText(PlayerData.phone)
        etStudentId.setText(PlayerData.studentId)
        etEmail.setText(PlayerData.email)

        val defaultGenderId = if (PlayerData.gender == "女") R.id.rb_female else R.id.rb_male
        rgGender.check(defaultGenderId)
        etGender.setText(if (defaultGenderId == R.id.rb_male) "男" else "女")

        rgGender.setOnCheckedChangeListener { _, checkedId ->
            etGender.setText(if (checkedId == R.id.rb_male) "男" else "女")
        }

        // 爱好回显
        val hobbyList = PlayerData.hobby.split("、")
        cbTravel.isChecked = hobbyList.contains("旅游")
        cbRead.isChecked = hobbyList.contains("阅读")
        cbGame.isChecked = hobbyList.contains("游戏")
        cbShop.isChecked = hobbyList.contains("购物")

        btnCancel.setOnClickListener {
            finish()
        }

        btnConfirm.setOnClickListener {
            val name = etName.text.toString().trim()
            val gender = etGender.text.toString().trim()
            val phone = etPhone.text.toString().trim()

            if (name.isEmpty() || gender.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, getString(R.string.toast_empty_input), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val studentId = etStudentId.text.toString().trim()
            val email = etEmail.text.toString().trim()

            val hobbySb = StringBuilder()
            if (cbTravel.isChecked) hobbySb.append("旅游、")
            if (cbRead.isChecked) hobbySb.append("阅读、")
            if (cbGame.isChecked) hobbySb.append("游戏、")
            if (cbShop.isChecked) hobbySb.append("购物、")
            val hobby = if (hobbySb.isNotEmpty()) hobbySb.dropLast(1).toString() else ""

            // 更新内存并立即写入文件
            PlayerData.updateProfile(name, gender, phone, studentId, email, hobby)
            PlayerData.save(this)

            Toast.makeText(this, getString(R.string.toast_modify_success), Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
