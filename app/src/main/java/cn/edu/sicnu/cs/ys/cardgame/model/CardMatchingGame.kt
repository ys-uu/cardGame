package cn.edu.sicnu.cs.ys.cardgame.model

class CardMatchingGame(count: Int) {
    var score = 0
        private set

    var flipCount = 0

    private val cards: MutableList<Card>
    private val gameCount = count

    val MISMATCH_PENALTY = 2
    val MATCH_BONUS = 4
    val COST_TO_CHOOSE = 1

    init {
        require(count % 2 == 0) { "卡牌数量必须是偶数" }
        val deck = Deck()
        cards = mutableListOf()

        var added = 0
        while (added < count / 2) {
            val card = deck.drawRandomCard()
            if (card != null) {
                cards.add(card)
                cards.add(card.copy())
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
        if (getFlippedCount() >= 2) return

        if (card.isChosen) {
            card.isChosen = false
        } else {
            card.isChosen = true
            flipCount++
        }
    }

    fun finishCompare() {
        val openList = cards.filter { it.isChosen && !it.isMatched }
        if (openList.size == 2) {
            val c1 = openList[0]
            val c2 = openList[1]
            val matchScore = c1.match(arrayOf(c2))
            if (matchScore > 0) {
                score += matchScore * MATCH_BONUS
                c1.isMatched = true
                c2.isMatched = true
            } else {
                score -= MISMATCH_PENALTY
                score -= COST_TO_CHOOSE
                c1.isChosen = false
                c2.isChosen = false
            }
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
        while (added < gameCount / 2) {
            val card = deck.drawRandomCard()
            if (card != null) {
                cards.add(card)
                cards.add(card.copy())
                added++
            }
        }
        cards.shuffle()
    }
}
