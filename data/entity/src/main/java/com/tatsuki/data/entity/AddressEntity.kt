package com.tatsuki.data.entity

sealed class AddressEntity(
    open val code: String,
    open val name: String
) {
    sealed class Prefecture(
        override val code: String,
        override val name: String
    ) : AddressEntity(code, name) {

        object Hokkaido : Prefecture(code = "01", name = "北海道")
        object Aomori : Prefecture(code = "02", name = "青森県")
        object Iwate : Prefecture(code = "03", name = "岩手県")
        object Miyagi : Prefecture(code = "04", name = "宮城県")
        object Akita : Prefecture(code = "05", name = "秋田県")
        object Yamagata : Prefecture(code = "06", name = "山形県")
        object Fukushima : Prefecture(code = "07", name = "福島県")
        object Ibaraki : Prefecture(code = "08", name = "茨城県")
        object Tochigi : Prefecture(code = "09", name = "栃木県")
        object Gunma : Prefecture(code = "10", name = "群馬県")
        object Saitama : Prefecture(code = "11", name = "埼玉県")
        object Chiba : Prefecture(code = "12", name = "千葉県")
        object Tokyo : Prefecture(code = "13", name = "東京都")
        object Kanagawa : Prefecture(code = "14", name = "神奈川県")
        object Niigata : Prefecture(code = "15", name = "新潟県")
        object Toyama : Prefecture(code = "16", name = "富山県")
        object Ishikawa : Prefecture(code = "17", name = "石川県")
        object Fukui : Prefecture(code = "18", name = "福井県")
        object Yamanashi : Prefecture(code = "19", name = "山梨県")
        object Nagano : Prefecture(code = "20", name = "長野県")
        object Gifu : Prefecture(code = "21", name = "岐阜県")
        object Shizuoka : Prefecture(code = "22", name = "静岡県")
        object Aichi : Prefecture(code = "23", name = "愛知県")
        object Mie : Prefecture(code = "24", name = "三重県")
        object Shiga : Prefecture(code = "25", name = "滋賀県")
        object Kyoto : Prefecture(code = "26", name = "京都府")
        object Osaka : Prefecture(code = "27", name = "大阪府")
        object Hyogo : Prefecture(code = "28", name = "兵庫県")
        object Nara : Prefecture(code = "29", name = "奈良県")
        object Wakayama : Prefecture(code = "30", name = "和歌山県")
        object Tottori : Prefecture(code = "31", name = "鳥取県")
        object Shimane : Prefecture(code = "32", name = "島根県")
        object Okayama : Prefecture(code = "33", name = "岡山県")
        object Hiroshima : Prefecture(code = "34", name = "広島県")
        object Yamaguchi : Prefecture(code = "35", name = "山口県")
        object Tokushima : Prefecture(code = "36", name = "徳島県")
        object Kagawa : Prefecture(code = "37", name = "香川県")
        object Ehime : Prefecture(code = "38", name = "愛媛県")
        object Kochi : Prefecture(code = "39", name = "高知県")
        object Fukuoka : Prefecture(code = "40", name = "福岡県")
        object Saga : Prefecture(code = "41", name = "佐賀県")
        object Nagasaki : Prefecture(code = "42", name = "長崎県")
        object Kumamoto : Prefecture(code = "43", name = "熊本県")
        object Oita : Prefecture(code = "44", name = "大分県")
        object Miyazaki : Prefecture(code = "45", name = "宮崎県")
        object Kagoshima : Prefecture(code = "46", name = "鹿児島県")
        object Okinawa : Prefecture(code = "47", name = "沖縄県")
    }

    data class City(
        override val code: String,
        override val name: String
    ) : AddressEntity(code, name)
}
