package ru.evtukhov.android.homework6.posts

class Post(
    var id: Long,
    var author: String,
    var content: String,
    var dateStamp: Long,
    var likedByMe: Boolean,
    var likedCount: Int,
    var sharedByMe: Boolean,
    var sharedCount: Int,
    var commentsByMe: Boolean,
    var commentsCount: Int,
    var address: String? = null,
    var lat: Double?  = null,
    var lng: Double? = null,
    var postType: PostType,
    var videoLink: String? = null,
    var intentLink: String? = null,
    var imageLink: Int? = null
) {
    var dateText: String = format(dateStamp)
    private fun format (postDateSeconds: Long): String {
        val currentDate: Long = System.currentTimeMillis()
        val currentDateSeconds: Long = currentDate / 1000
        val dateSeconds: Long = currentDateSeconds - postDateSeconds
        val dateMinutes: Long = dateSeconds / 60
        val dateHours: Long = dateMinutes / 60
        val dateDays: Long = dateHours / 24
        val dateMonth: Long = dateDays / 30
        val dateYear: Long = dateMonth / 12
        val secondText = "$dateSeconds секунд(у/ы) назад"
        val minuteText = "$dateMinutes минут(у/ы) назад"
        val hourText = "$dateHours час(а/ов) назад"
        val dayText = "$dateDays ден(ь/я/ей) назад"
        val monthText = "$dateMonth месяц(а/ев) назад"
        val yearText = "$dateYear год(а)/лет назад"
        return when(dateSeconds) {
            in 0..59 -> secondText
            in 60..3599 -> minuteText
            in 3600..86399 -> hourText
            in 86400..2591999 -> dayText
            in 2592000..31103999 -> monthText
            else -> yearText
        }
    }
}