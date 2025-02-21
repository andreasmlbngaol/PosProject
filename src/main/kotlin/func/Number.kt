package func

fun Float.formatted(): String = "%,.0f".format(this)

fun Double.formatted(): String = "%,.0f".format(this)

fun Int.formatted(): String = "%,d".format(this)

fun Long.formatted(): String = "%,d".format(this)