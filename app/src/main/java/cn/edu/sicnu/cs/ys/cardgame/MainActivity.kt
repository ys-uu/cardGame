package cn.edu.sicnu.cs.ys.cardgame

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import cn.edu.sicnu.cs.ys.cardgame.data.PlayerData
import cn.edu.sicnu.cs.ys.cardgame.ui.ProfileFragment
import cn.edu.sicnu.cs.ys.cardgame.ui.ThreeCardGameFragment
import cn.edu.sicnu.cs.ys.cardgame.ui.TwoCardGameFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 启动时从文件加载玩家历史数据
        PlayerData.load(this)

        bottomNav = findViewById(R.id.bottom_nav)

        // 默认显示个人信息 Fragment
        if (savedInstanceState == null) {
            switchFragment(ProfileFragment())
        }

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_profile -> {
                    switchFragment(ProfileFragment())
                    true
                }
                R.id.nav_game2 -> {
                    switchFragment(TwoCardGameFragment())
                    true
                }
                R.id.nav_game3 -> {
                    switchFragment(ThreeCardGameFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        // 退出时持久化玩家数据
        PlayerData.save(this)
    }
}
