package cn.edu.sicnu.cs.ys.cardgame.ui

import cn.edu.sicnu.cs.ys.cardgame.model.CardMatchingGame

/**
 * 第2个Fragment：2张牌翻牌游戏（12张牌，6对，3列）
 */
class TwoCardGameFragment : BaseGameFragment() {

    companion object {
        private const val SAVE_FILE = "game_2card.dat"
        private const val CARD_COUNT = 12
        private const val SPAN_COUNT = 3
    }

    override fun getSaveFileName() = SAVE_FILE
    override fun getGameTitle() = "2张牌匹配游戏"
    override fun getSpanCount() = SPAN_COUNT
    override fun createNewGame() = CardMatchingGame(CARD_COUNT, 2)
}
