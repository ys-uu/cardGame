package cn.edu.sicnu.cs.ys.cardgame.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.edu.sicnu.cs.ys.cardgame.R
import cn.edu.sicnu.cs.ys.cardgame.model.Card

class CardAdapter(
    private val cards: List<Card>,
    private val onCardClick: (Int) -> Unit
) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // 牌背改为 ImageView，id对应 item_card.xml 里 iv_card_back
        val ivBack: ImageView = itemView.findViewById(R.id.iv_card_back)
        val tvFront: TextView = itemView.findViewById(R.id.tv_card_front)

        init {
            itemView.setOnClickListener {
                val pos = adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    onCardClick(pos)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return CardViewHolder(v)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = cards[position]
        holder.tvFront.text = card.toString() // ♥A ♠K 这种扑克牌文字

        // isChosen=true翻开显示牌面，isMatched匹配成功也保持正面
        if (card.isChosen || card.isMatched) {
            holder.ivBack.visibility = View.GONE
            holder.tvFront.visibility = View.VISIBLE
            if (card.isMatched) {
                holder.tvFront.setBackgroundColor(0xFF4CAF50.toInt())
                holder.tvFront.setTextColor(0xFFFFFFFF.toInt())
            } else {
                holder.tvFront.setBackgroundColor(0xFFFFFFFF.toInt())
                holder.tvFront.setTextColor(0xFF000000.toInt())
            }
        } else {
            holder.ivBack.visibility = View.VISIBLE
            holder.tvFront.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = cards.size
}
