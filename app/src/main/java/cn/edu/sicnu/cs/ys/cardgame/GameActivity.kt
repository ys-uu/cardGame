package cn.edu.sicnu.cs.ys.cardgame

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.edu.sicnu.cs.ys.cardgame.adapter.CardAdapter
import cn.edu.sicnu.cs.ys.cardgame.data.PlayerData
import cn.edu.sicnu.cs.ys.cardgame.model.CardMatchingGame

var game: CardMatchingGame = CardMatchingGame(24)
class GameActivity : AppCompatActivity() {

    private lateinit var adapter: CardAdapter
    private lateinit var rvCards: RecyclerView
    private lateinit var tvScore: TextView
    private lateinit var btnRestart: Button
    private lateinit var btnExit: Button

    private val handler = Handler(Looper.getMainLooper())
    private var isComparing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        rvCards = findViewById(R.id.rv_cards)
        tvScore = findViewById(R.id.tv_score)
        btnRestart = findViewById(R.id.btn_restart)
        btnExit = findViewById(R.id.btn_exit)

        rvCards.layoutManager = GridLayoutManager(this, 4)
        adapter = CardAdapter(game.getAllCards()) { pos ->
            if (!isComparing) {
                onCardClick(pos)
            }
        }
        rvCards.adapter = adapter

        btnRestart.setOnClickListener {
            game.resetGame()
            isComparing = false
            updateUi()
        }

        btnExit.setOnClickListener {
            finish()
        }
        updateUi()
    }

    private fun onCardClick(position: Int) {
        game.chooseCardAtIndex(position)
        updateUi()

        if (game.getFlippedCount() == 2) {
            isComparing = true
            handler.postDelayed({
                try {
                    game.finishCompare()
                    updateUi()

                    if (game.isGameWon()) {
                        val currentScore = game.score
                        if (currentScore > PlayerData.highScore) {
                            PlayerData.highScore = currentScore
                            Toast.makeText(this, getString(R.string.toast_new_highscore, currentScore), Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(this, getString(R.string.toast_win_score, currentScore), Toast.LENGTH_LONG).show()
                        }
                    }
                } finally {
                    // 兜底：无论匹配成功/失败，强制解锁，杜绝永久卡死
                    isComparing = false
                }
            }, 800)
        }
    }

    private fun updateUi() {
        tvScore.text = getString(R.string.score_text, game.score)
        adapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}
