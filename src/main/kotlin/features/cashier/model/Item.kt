package features.cashier.model

data class Item(
    val code: String,
    val name: String,
    val price: Float
)

val ITEM_LIST = listOf(
    Item("1111", "Chitato", 10_000f),
    Item("1112", "Chiki", 9_000f),
    Item("1113", "Oreo", 15_000f),
    Item("1114", "Potabee", 11_000f),
    Item("1115", "Taro", 8_500f),
    Item("1116", "Doritos", 12_000f),
    Item("1117", "Lays", 13_000f),
    Item("1118", "Pringles", 25_000f),
    Item("1119", "Good Time", 18_000f),
    Item("1120", "Tim Tam", 20_000f),
    Item("1121", "Beng-Beng", 5_000f),
    Item("1122", "SilverQueen", 27_000f),
    Item("1123", "Toblerone", 32_000f),
    Item("1124", "KitKat", 10_000f),
    Item("1125", "Milo Cube", 15_000f),
    Item("1126", "Roma Kelapa", 8_000f),
    Item("1127", "Sari Gandum", 9_500f),
    Item("1128", "Nextar", 12_500f),
    Item("1129", "Marie Regal", 22_000f),
    Item("1130", "Astor", 17_000f)
)
