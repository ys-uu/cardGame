package cn.edu.sicnu.cs.ys.cardgame.model

import java.io.Serializable

class Card(
    private var suit: String,
    private var rank: String,
    var isChosen: Boolean = false,
    var isMatched: Boolean = false
) : Serializable {
    companion object {
        const val serialVersionUID = 1L
        val rankStrings = arrayOf("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K")
        val validSuits = arrayOf("♥", "♦", "♠", "♣")
    }

    fun getSuit(): String = suit
    fun getRank(): String = rank

    override fun toString(): String {
        return suit + rank
    }

    /**
     * 2张牌匹配评分（原有逻辑保留）
     */
    fun match(otherCards: Array<Card>): Int {
        var score = 0
        if (otherCards.size == 1) {
            val otherCard = otherCards[0]
            if (otherCard.rank == rank) {
                score = 4
            } else if (otherCard.suit == suit) {
                score = 1
            }
        }
        return score
    }

    fun copy(): Card {
        return Card(suit, rank)
    }
}
