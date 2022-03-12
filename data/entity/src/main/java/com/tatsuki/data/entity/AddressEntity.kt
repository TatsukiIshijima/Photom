package com.tatsuki.data.entity

import kotlinx.serialization.Serializable

@Serializable
sealed class AddressEntity {
    abstract val code: String
    abstract val name: String

    @Serializable
    sealed class Prefecture(
        override val code: String,
        override val name: String
    ) : AddressEntity() {

        @Serializable
        object Hokkaido : Prefecture(code = "01", name = "北海道")

        @Serializable
        object Aomori : Prefecture(code = "02", name = "青森県")

        @Serializable
        object Iwate : Prefecture(code = "03", name = "岩手県")

        @Serializable
        object Miyagi : Prefecture(code = "04", name = "宮城県")

        @Serializable
        object Akita : Prefecture(code = "05", name = "秋田県")

        @Serializable
        object Yamagata : Prefecture(code = "06", name = "山形県")

        @Serializable
        object Fukushima : Prefecture(code = "07", name = "福島県")

        @Serializable
        object Ibaraki : Prefecture(code = "08", name = "茨城県")

        @Serializable
        object Tochigi : Prefecture(code = "09", name = "栃木県")

        @Serializable
        object Gunma : Prefecture(code = "10", name = "群馬県")

        @Serializable
        object Saitama : Prefecture(code = "11", name = "埼玉県")

        @Serializable
        object Chiba : Prefecture(code = "12", name = "千葉県")

        @Serializable
        object Tokyo : Prefecture(code = "13", name = "東京都")

        @Serializable
        object Kanagawa : Prefecture(code = "14", name = "神奈川県")

        @Serializable
        object Niigata : Prefecture(code = "15", name = "新潟県")

        @Serializable
        object Toyama : Prefecture(code = "16", name = "富山県")

        @Serializable
        object Ishikawa : Prefecture(code = "17", name = "石川県")

        @Serializable
        object Fukui : Prefecture(code = "18", name = "福井県")

        @Serializable
        object Yamanashi : Prefecture(code = "19", name = "山梨県")

        @Serializable
        object Nagano : Prefecture(code = "20", name = "長野県")

        @Serializable
        object Gifu : Prefecture(code = "21", name = "岐阜県")

        @Serializable
        object Shizuoka : Prefecture(code = "22", name = "静岡県")

        @Serializable
        object Aichi : Prefecture(code = "23", name = "愛知県")

        @Serializable
        object Mie : Prefecture(code = "24", name = "三重県")

        @Serializable
        object Shiga : Prefecture(code = "25", name = "滋賀県")

        @Serializable
        object Kyoto : Prefecture(code = "26", name = "京都府")

        @Serializable
        object Osaka : Prefecture(code = "27", name = "大阪府")

        @Serializable
        object Hyogo : Prefecture(code = "28", name = "兵庫県")

        @Serializable
        object Nara : Prefecture(code = "29", name = "奈良県")

        @Serializable
        object Wakayama : Prefecture(code = "30", name = "和歌山県")

        @Serializable
        object Tottori : Prefecture(code = "31", name = "鳥取県")

        @Serializable
        object Shimane : Prefecture(code = "32", name = "島根県")

        @Serializable
        object Okayama : Prefecture(code = "33", name = "岡山県")

        @Serializable
        object Hiroshima : Prefecture(code = "34", name = "広島県")

        @Serializable
        object Yamaguchi : Prefecture(code = "35", name = "山口県")

        @Serializable
        object Tokushima : Prefecture(code = "36", name = "徳島県")

        @Serializable
        object Kagawa : Prefecture(code = "37", name = "香川県")

        @Serializable
        object Ehime : Prefecture(code = "38", name = "愛媛県")

        @Serializable
        object Kochi : Prefecture(code = "39", name = "高知県")

        @Serializable
        object Fukuoka : Prefecture(code = "40", name = "福岡県")

        @Serializable
        object Saga : Prefecture(code = "41", name = "佐賀県")

        @Serializable
        object Nagasaki : Prefecture(code = "42", name = "長崎県")

        @Serializable
        object Kumamoto : Prefecture(code = "43", name = "熊本県")

        @Serializable
        object Oita : Prefecture(code = "44", name = "大分県")

        @Serializable
        object Miyazaki : Prefecture(code = "45", name = "宮崎県")

        @Serializable
        object Kagoshima : Prefecture(code = "46", name = "鹿児島県")

        @Serializable
        object Okinawa : Prefecture(code = "47", name = "沖縄県")
    }

    @Serializable
    data class City(
        override val code: String,
        override val name: String
    ) : AddressEntity()
}
