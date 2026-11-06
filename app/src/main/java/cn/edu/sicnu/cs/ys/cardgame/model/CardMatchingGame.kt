package cn.edu.sicnu.cs.ys.cardgame.model

import java.io.Serializable

class CardMatchingGame(
    val gameCount: Int,
    val matchMode: Int = 2
) : Serializable {
    companion object {
        const val serialVersionUID = 1L
        const val MISMATCH_PENALTY = 2
        const val MATCH_BONUS = 4
        const val COST_TO_CHOOSE = 1
    }

    var score = 0
        private set

    var flipCount = 0
        private set

    private val cards: MutableList<Card>

    init {
        require(gameCount % matchMode == 0) { "卡牌数量必须是 $matchMode 的倍数" }
        val deck = Deck()
        cards = mutableListOf()

        var added = 0
        while (added < gameCount / matchMode) {
            val card = deck.drawRandomCard()
            if (card != null) {
                repeat(matchMode) {
                    cards.add(card.copy())
                }
                added++
            }
        }
        cards.shuffle()
    }

    fun cardAtIndex(index: Int): Card {
        return cards[index]
    }

    fun getAllCards(): List<Card> = cards

    fun getFlippedCount(): Int {
        return cards.count { it.isChosen && !it.isMatched }
    }

    fun chooseCardAtIndex(index: Int) {
        val card = cardAtIndex(index)
        if (card.isMatched) return
        if (getFlippedCount() >= matchMode) return

        if (card.isChosen) {
            card.isChosen = false
        } else {
            card.isChosen = true
            flipCount++
        }
    }

    /**
     * 翻满 matchMode 张牌后统一结算
     */
    fun finishCompare() {
        val openList = cards.filter { it.isChosen && !it.isMatched }
        if (openList.size == matchMode) {
            val matchScore = calculateMatchScore(openList)
            if (matchScore > 0) {
                score += matchScore * MATCH_BONUS
                openList.forEach { it.isMatched = true }
            } else {
                score -= MISMATCH_PENALTY
                openList.forEach { it.isChosen = false }
            }
        }
    }

    /**
     * 2张模式：沿用原有 rank=4 / suit=1 规则
     * 3张模式：3张同rank=16，2张同rank=4，3张同suit=2，2张同suit=1
     */
    private fun calculateMatchScore(openCards: List<Card>): Int {
        if (matchMode == 2) {
            val c1 = openCards[0]
            val c2 = openCards[1]
            return c1.match(arrayOf(c2))
        }

        val ranks = openCards.map { it.getRank() }
        val suits = openCards.map { it.getSuit() }
        val rankMax = ranks.groupingBy { it }.eachCount().values.maxOrNull() ?: 0
        val suitMax = suits.groupingBy { it }.eachCount().values.maxOrNull() ?: 0

        return when {
            rankMax == 3 -> 16
            rankMax == 2 -> 4
            suitMax == 3 -> 2
            suitMax == 2 -> 1
            else -> 0
        }
    }

    fun isGameWon(): Boolean {
        return cards.all { it.isMatched }
    }

    fun resetGame() {
        score = 0
        flipCount = 0
        cards.clear()
        val deck = Deck()
        var added = 0
        while (added < gameCount / matchMode) {
            val card = deck.drawRandomCard()
            if (card != null) {
                repeat(matchMode) {
                    cards.add(card.copy())
                }
                added++
            }
        }
        cards.shuffle()
    }
}
