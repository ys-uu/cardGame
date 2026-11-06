package cn.edu.sicnu.cs.ys.cardgame.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import cn.edu.sicnu.cs.ys.cardgame.EditInfoActivity
import cn.edu.sicnu.cs.ys.cardgame.R
import cn.edu.sicnu.cs.ys.cardgame.data.PlayerData

/**
 * 第1个Fragment：个人信息展示
 */
class ProfileFragment : Fragment() {

    private lateinit var tvName: TextView
    private lateinit var tvGender: TextView
    private lateinit var tvPhone: TextView
    private lateinit var tvStudentId: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvHobby: TextView
    private lateinit var tvHighScore2: TextView
    private lateinit var tvHighScore3: TextView
    private lateinit var btnEditInfo: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        tvName = view.findViewById(R.id.tv_name)
        tvGender = view.findViewById(R.id.tv_gender)
        tvPhone = view.findViewById(R.id.tv_phone)
        tvStudentId = view.findViewById(R.id.tv_student_id)
        tvEmail = view.findViewById(R.id.tv_email)
        tvHobby = view.findViewById(R.id.tv_hobby)
        tvHighScore2 = view.findViewById(R.id.tv_high_score_2)
        tvHighScore3 = view.findViewById(R.id.tv_high_score_3)
        btnEditInfo = view.findViewById(R.id.btn_edit_info)

        btnEditInfo.setOnClickListener {
            startActivity(Intent(requireContext(), EditInfoActivity::class.java))
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        refreshInfo()
    }

    private fun refreshInfo() {
        tvName.text = getString(R.string.tv_name_label, PlayerData.name)
        tvGender.text = getString(R.string.tv_gender_label, PlayerData.gender)
        tvPhone.text = getString(R.string.tv_phone_label, PlayerData.phone)
        tvStudentId.text = getString(R.string.label_student_id, PlayerData.studentId)
        tvEmail.text = getString(R.string.label_email, PlayerData.email)
        tvHobby.text = getString(R.string.label_hobby, PlayerData.hobby)
        tvHighScore2.text = getString(R.string.high_score_2card, PlayerData.highScore2Card)
        tvHighScore3.text = getString(R.string.high_score_3card, PlayerData.highScore3Card)
    }
}
