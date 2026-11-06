package cn.edu.sicnu.cs.ys.cardgame.data

/**
 * 全局玩家数据（内存存储，退出应用即清空）
 */
object PlayerData {
    // 原有字段 保留不动
    var name: String = ""
    var gender: String = ""
    var phone: String = ""
    var highScore: Int = 0

    // 新增字段
    var studentId: String = ""
    var email: String = ""
    var hobby: String = ""
}
