package cn.edu.sicnu.cs.ys.cardgame.ui

import cn.edu.sicnu.cs.ys.cardgame.model.CardMatchingGame

/**
 * 第3个Fragment：3张牌翻牌游戏（12张牌，4组×3，3列）
 */
class ThreeCardGameFragment : BaseGameFragment() {

    companion object {
        private const val SAVE_FILE = "game_3card.dat"
        private const val CARD_COUNT = 12
        private const val SPAN_COUNT = 3
    }

    override fun getSaveFileName() = SAVE_FILE
    override fun getGameTitle() = "3张牌匹配游戏"
    override fun getSpanCount() = SPAN_COUNT
    override fun createNewGame() = CardMatchingGame(CARD_COUNT, 3)
}
