package cn.edu.sicnu.cs.ys.cardgame.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.edu.sicnu.cs.ys.cardgame.R
import cn.edu.sicnu.cs.ys.cardgame.adapter.CardAdapter
import cn.edu.sicnu.cs.ys.cardgame.data.PlayerData
import cn.edu.sicnu.cs.ys.cardgame.model.CardMatchingGame
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

/**
 * 翻牌游戏 Fragment 基类
 * 子类只需提供 game 实例、保存文件名、标题文字、网格列数
 */
abstract class BaseGameFragment : Fragment() {

    protected lateinit var game: CardMatchingGame
    private lateinit var adapter: CardAdapter
    private lateinit var rvCards: RecyclerView
    private lateinit var tvScore: TextView
    private lateinit var btnRestart: Button
    private lateinit var btnSave: Button
    private lateinit var tvTitle: TextView

    private val handler = Handler(Looper.getMainLooper())
    private var isComparing = false

    /** 子类返回游戏状态保存的文件名，例如 "game_2card.dat" */
    protected abstract fun getSaveFileName(): String

    /** 子类返回游戏标题 */
    protected abstract fun getGameTitle(): String

    /** 子类返回 RecyclerView 列数 */
    protected abstract fun getSpanCount(): Int

    /** 子类返回新的游戏实例 */
    protected abstract fun createNewGame(): CardMatchingGame

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_game, container, false)

        tvTitle = view.findViewById(R.id.tv_game_title)
        rvCards = view.findViewById(R.id.rv_cards)
        tvScore = view.findViewById(R.id.tv_score)
        btnRestart = view.findViewById(R.id.btn_restart)
        btnSave = view.findViewById(R.id.btn_save)

        tvTitle.text = getGameTitle()

        // 尝试从文件恢复游戏
        game = loadGame() ?: createNewGame()

        rvCards.layoutManager = GridLayoutManager(requireContext(), getSpanCount())
        adapter = CardAdapter(game.getAllCards()) { pos ->
            if (!isComparing) onCardClick(pos)
        }
        rvCards.adapter = adapter

        btnRestart.setOnClickListener {
            game.resetGame()
            isComparing = false
            updateUi()
            saveGame()
        }

        btnSave.setOnClickListener {
            saveGame()
            Toast.makeText(requireContext(), R.string.toast_game_saved, Toast.LENGTH_SHORT).show()
        }

        updateUi()
        return view
    }

    private fun onCardClick(position: Int) {
        game.chooseCardAtIndex(position)
        updateUi()

        if (game.getFlippedCount() == game.matchMode) {
            isComparing = true
            handler.postDelayed({
                try {
                    game.finishCompare()
                    updateUi()

                    if (game.isGameWon()) {
                        val currentScore = game.score
                        val isNewRecord = PlayerData.updateHighScore(game.matchMode, currentScore)
                        PlayerData.save(requireContext())
                        if (isNewRecord) {
                            Toast.makeText(requireContext(),
                                getString(R.string.toast_new_highscore, currentScore),
                                Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(requireContext(),
                                getString(R.string.toast_win_score, currentScore),
                                Toast.LENGTH_LONG).show()
                        }
                    }
                    saveGame()
                } finally {
                    isComparing = false
                }
            }, 800)
        }
    }

    private fun updateUi() {
        tvScore.text = getString(R.string.score_text, game.score)
        adapter.notifyDataSetChanged()
    }

    override fun onPause() {
        super.onPause()
        saveGame()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }

    // ========== 文件序列化读写 ==========

    private fun saveGame() {
        try {
            requireContext().openFileOutput(getSaveFileName(), android.content.Context.MODE_PRIVATE)
                .use { fos ->
                    ObjectOutputStream(fos).use { oos -> oos.writeObject(game) }
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun loadGame(): CardMatchingGame? {
        return try {
            requireContext().openFileInput(getSaveFileName()).use { fis ->
                ObjectInputStream(fis).use { ois ->
                    ois.readObject() as CardMatchingGame
                }
            }
        } catch (e: Exception) {
            null
        }
    }
}
