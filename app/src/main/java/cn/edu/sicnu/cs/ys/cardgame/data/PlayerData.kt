package cn.edu.sicnu.cs.ys.cardgame.data

import android.content.Context
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable

/**
 * 全局玩家数据（内存 + 文件持久化）
 * 退出应用后再次打开，从 player_info.dat 恢复
 */
object PlayerData {
    private const val FILE_NAME = "player_info.dat"

    var name: String = ""
        private set
    var gender: String = ""
        private set
    var phone: String = ""
        private set
    var studentId: String = ""
        private set
    var email: String = ""
        private set
    var hobby: String = ""
        private set

    /** 2张牌模式最高分 */
    var highScore2Card: Int = 0
        private set

    /** 3张牌模式最高分 */
    var highScore3Card: Int = 0
        private set

    /** 更新个人信息（由 EditInfoActivity 保存时调用） */
    fun updateProfile(
        name: String,
        gender: String,
        phone: String,
        studentId: String,
        email: String,
        hobby: String
    ) {
        this.name = name
        this.gender = gender
        this.phone = phone
        this.studentId = studentId
        this.email = email
        this.hobby = hobby
    }

    /** 如果当前分数更高，则更新对应模式的最高分 */
    fun updateHighScore(matchMode: Int, score: Int): Boolean {
        return if (matchMode == 2) {
            if (score > highScore2Card) {
                highScore2Card = score
                true
            } else false
        } else {
            if (score > highScore3Card) {
                highScore3Card = score
                true
            } else false
        }
    }

    /** 从文件加载玩家信息 */
    fun load(context: Context) {
        try {
            context.openFileInput(FILE_NAME).use { fis ->
                ObjectInputStream(fis).use { ois ->
                    val info = ois.readObject() as PlayerInfo
                    name = info.name
                    gender = info.gender
                    phone = info.phone
                    studentId = info.studentId
                    email = info.email
                    hobby = info.hobby
                    highScore2Card = info.highScore2Card
                    highScore3Card = info.highScore3Card
                }
            }
        } catch (e: Exception) {
            // 文件不存在或损坏，保持默认值
        }
    }

    /** 将玩家信息写入文件 */
    fun save(context: Context) {
        try {
            context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE).use { fos ->
                ObjectOutputStream(fos).use { oos ->
                    oos.writeObject(
                        PlayerInfo(
                            name, gender, phone, studentId, email, hobby,
                            highScore2Card, highScore3Card
                        )
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /** 可序列化的玩家信息快照 */
    private data class PlayerInfo(
        val name: String,
        val gender: String,
        val phone: String,
        val studentId: String,
        val email: String,
        val hobby: String,
        val highScore2Card: Int,
        val highScore3Card: Int
    ) : Serializable {
        companion object {
            private const val serialVersionUID = 1L
        }
    }
}
