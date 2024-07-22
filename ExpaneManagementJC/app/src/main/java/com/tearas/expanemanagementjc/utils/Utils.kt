package com.tearas.expanemanagementjc.utils

import android.util.Log
import com.tearas.expanemanagementjc.ExpenseApplication
import com.tearas.expanemanagementjc.domain.model.Category
import com.tearas.expanemanagementjc.domain.model.CategoryType
import com.tearas.expanemanagementjc.domain.model.CurrencyFormat
import com.tearas.expanemanagementjc.domain.model.Lang
import com.tearas.expanemanagementjc.domain.model.Language
import com.tearas.expanemanagementjc.domain.model.TimeFormat
import com.tearas.expanemanagementjc.domain.model.TransactionType
import java.text.DateFormatSymbols
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

val translatedCategoryTypeList = listOf(
    "Giải trí" to "Entertainment",
    "Thức ăn" to "Food",
    "Mua sắm" to "Shopping",
    "Cá nhân" to "Personal",
    "Giáo dục" to "Education",
    "Lễ hội" to "Festival",
    "Thể thao" to "Sports",
    "Văn phòng" to "Office",
    "Giao thông" to "Transportation",
    "Sức khỏe" to "Health",
    "Khác" to "Other"
)

val listCateLanguage = listOf(
    "Cửa hàng" to "Shop",
    "Rau củ" to "Vegetable",
    "Đồ ăn vặt" to "Snacks",
    "Đồ ăn nhanh" to "Fastfood",
    "Quà tặng" to "Present",
    "Học tập" to "Study",
    "Máy móc" to "Machines",
    "Sức khỏe" to "Health",
    "Du lịch" to "Tourism",
    "Điện tử" to "Electronic",
    "Wifi" to "Wifi",
    "Đua xe" to "Car racing",
    "Bơi lội" to "Swim",
    "Trang phục" to "Dress",
    "Phương tiện đi lại" to "Carriage",
    "Xã hội" to "Society",
    "Thí nghiệm" to "Experiment",
    "Tình yêu" to "Love",
    "Giáo dục" to "Education",
    "Khiêu vũ" to "Dance",
    "Chuột máy tính" to "Computer mouse",
    "Khác" to "Other",
    "Giáo dục" to "Education",
    "Khiêu vũ" to "Dance",
    "Sàn nhảy" to "Dance-floor",
    "Trò chơi" to "Game",
    "Bóng đá" to "Soccer-ball",
    "Đua xe" to "Sport-car",
    "Khác" to "Other",
)

val dataCategoriesDefaults = mapOf(
    "shopping-bag" to Category(
        0,
        0,
        TransactionType.SPEND,
        CategoryType.SHOPPING,
        listCateLanguage[0].first,
        true
    ),
    "vegetable" to Category(
        0,
        0,
        TransactionType.SPEND,
        CategoryType.SHOPPING,
        listCateLanguage[1].first,
        true
    ),
    "snacks" to Category(
        0,
        0,
        TransactionType.SPEND,
        CategoryType.SHOPPING,
        listCateLanguage[2].first,
        true
    ),
    "hamburger" to Category(
        0,
        0,
        TransactionType.SPEND,
        CategoryType.SHOPPING,
        listCateLanguage[3].first,
        true
    ),
    "flower-bouquet" to Category(
        0,
        0,
        TransactionType.SPEND,
        CategoryType.SHOPPING,
        listCateLanguage[4].first,
        true
    ),
    "education" to Category(
        0,
        0,
        TransactionType.SPEND,
        CategoryType.SHOPPING,
        listCateLanguage[5].first,
        true
    ),

    "robot" to Category(
        0,
        0,
        TransactionType.SPEND,
        CategoryType.SHOPPING,
        listCateLanguage[6].first,
        true
    ),
    "health-check" to Category(
        0,
        0,
        TransactionType.SPEND,
        CategoryType.SHOPPING,
        listCateLanguage[7].first,
        true
    ),
    "flight" to Category(
        0,
        0,
        TransactionType.SPEND,
        CategoryType.SHOPPING,
        listCateLanguage[8].first,
        true
    ),
    "game" to Category(
        0,
        0,
        TransactionType.SPEND,
        CategoryType.SHOPPING,
        listCateLanguage[9].first,
        true
    ),
    "wifi-router" to Category(
        0,
        0,
        TransactionType.SPEND,
        CategoryType.OFFICE,
        listCateLanguage[10].first,
        true
    ),
    "sport-car" to Category(
        0,
        0,
        TransactionType.SPEND,
        CategoryType.SHOPPING,
        listCateLanguage[11].first,
        true
    ),
    "swimming" to Category(
        0,
        0,
        TransactionType.SPEND,
        CategoryType.SHOPPING,
        listCateLanguage[12].first,
        true
    ),
    "dress" to Category(
        0,
        0,
        TransactionType.SPEND,
        CategoryType.SHOPPING,
        listCateLanguage[13].first,
        true
    ),
    "truck" to Category(
        0,
        0,
        TransactionType.SPEND,
        CategoryType.SHOPPING,
        listCateLanguage[14].first,
        true
    ),
    "parents" to Category(
        0,
        0,
        TransactionType.SPEND,
        CategoryType.SHOPPING,
        listCateLanguage[15].first,
        true
    ),
    "test" to Category(
        0,
        0,
        TransactionType.SPEND,
        CategoryType.SHOPPING,
        listCateLanguage[16].first,
        true
    ),
    "love" to Category(
        0,
        0,
        TransactionType.SPEND,
        CategoryType.SHOPPING,
        listCateLanguage[17].first,
        true
    ),
    "school" to Category(
        0,
        0,
        TransactionType.SPEND,
        CategoryType.SHOPPING,
        listCateLanguage[18].first,
        true
    ),
    "dance-floor" to Category(
        0,
        0,
        TransactionType.SPEND,
        CategoryType.SHOPPING,
        listCateLanguage[19].first,
        true
    ),
    "mouse" to Category(
        0,
        0,
        TransactionType.SPEND,
        CategoryType.SHOPPING,
        listCateLanguage[20].first,
        true
    ),
    "application" to Category(
        0,
        0,
        TransactionType.SPEND,
        CategoryType.OTHER,
        listCateLanguage[21].first,
        true
    ),
    "education" to Category(
        0,
        0,
        TransactionType.COLLECT,
        CategoryType.EDUCATION,
        listCateLanguage[22].first,
        true
    ),
    "dance" to Category(
        0,
        0,
        TransactionType.COLLECT,
        CategoryType.ENTERTAINMENT,
        listCateLanguage[23].first,
        true
    ),
    "dance-floor" to Category(
        0,
        0,
        TransactionType.COLLECT,
        CategoryType.ENTERTAINMENT,
        listCateLanguage[24].first,
        true
    ),
    "game" to Category(
        0,
        0,
        TransactionType.COLLECT,
        CategoryType.ENTERTAINMENT,
        listCateLanguage[25].first,
        true
    ),
    "soccer-ball" to Category(
        0,
        0,
        TransactionType.COLLECT,
        CategoryType.ENTERTAINMENT,
        listCateLanguage[26].first,
        true
    ),
    "sport-car" to Category(
        0,
        0,
        TransactionType.COLLECT,
        CategoryType.SPORTS,
        listCateLanguage[27].first,
        true
    ),
    "application.png" to Category(
        0,
        0,
        TransactionType.COLLECT,
        CategoryType.OTHER,
        listCateLanguage[28].first,
        true
    )
)


fun String.getCategoryByLanguage(lang: Language): String {
    val index =
        listCateLanguage.indexOfFirst {
            if (lang == Language.VI) it.second == this else it.first == this
        }
    if (index == -1) return this
    val cate = when (lang) {
        Language.VI -> {
            listCateLanguage[index].first
        }

        Language.EN -> {
            listCateLanguage[index].second
        }
    }
    Log.d("sándkalsdasd", cate)
    return cate
}

private val locale = Locale.US
private val numberFormatter = DecimalFormat()
private val numberFormatter1 = DecimalFormat()
private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", locale)
private val formatDateMonth = SimpleDateFormat()
private val formatTime = SimpleDateFormat("HH:mm", locale)
private val formatDateTime = SimpleDateFormat()

fun Date.formatTime(): String = formatTime.format(this)

fun String.parseTime(): Date? = formatTime.parse(this)

fun Date.formatDateMonth(): String {
    formatDateMonth.dateFormatSymbols = DateFormatSymbols(locale)
    val month = if (ExpenseApplication.LANGUAGE == Language.EN) "Month" else "Tháng"
    val patternFormat = "dd '$month' MM yyyy"
    formatDateMonth.applyPattern(patternFormat)
    return formatDateMonth.format(this)
}

fun Date.formatDateTime(): String {
    val month = if (ExpenseApplication.LANGUAGE == Language.EN) "Mth" else "Thg"
    val patternFormat = if (ExpenseApplication.TIME_FORMAT == TimeFormat.H12) {
        "dd '$month' MM yyyy HH:mm:ss aa"
    } else {
        "dd '$month' MM yyyy hh:mm:ss"
    }
    formatDateTime.applyPattern(patternFormat)
    return formatDateTime.format(this)
}

fun Number.format(): String {
    val symbols = DecimalFormatSymbols(locale)

    if (ExpenseApplication.CURRENCY_FORMAT == CurrencyFormat.OPTION_2) {
        symbols.apply {
            decimalSeparator = '.'
            groupingSeparator = ' '
        }
    } else {
        symbols.apply {
            decimalSeparator = '.'
            groupingSeparator = ','
        }
    }
    numberFormatter.applyPattern("#,###.##")
    numberFormatter.decimalFormatSymbols = symbols
    return numberFormatter.format(this)
}



fun Number.format1(): String {
    return numberFormatter1.format(this)
}

fun Date.formatParseDate(): Date {
    val formattedDate = simpleDateFormat.format(this)
    return simpleDateFormat.parse(formattedDate)!!
}

fun Date.getYearDate(): Int {
    val calendar = Calendar.getInstance()
    calendar.time = this
    return calendar.get(Calendar.YEAR)
}

fun Date.getMonthDate(): String {
    val calendar = Calendar.getInstance()
    calendar.time = this
    return (calendar.get(Calendar.MONTH) + 1).toString().padStart(2, '0')
}