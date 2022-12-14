package com.example.order.core

import com.example.order.app.domain.model.ListItem

object GlobalConstAndVars {
    const val   ITS_NOT_FAVORITE="0"
    const val ITS_FAVORITE="1"

    const val ERROR_API_KEY_NOT_FOUND="You need API key"
    const val API_KEY ="39019b3af300a1027141bb1d9eb2354e"//так сделал в демонстрационных целях
    const val FIRST_INDEX_OF_SECOND_CURRENCY=3
    const val FIRST_INDEX_OF_CROSSCOUSE=6
    var LIST_OF_CHOSEN_ITEMS: MutableList<ListItem> = mutableListOf()
    var DATABASE1C_NAME: String = "Database1C.db"
    val DEFAULT_lIST= listOf(ListItem("","","","","","","",""))
    var GLOBAL_LIST= DEFAULT_lIST
    val COUNTRIES_AND_CURRENCIES_lIST = listOf(
        ListItem("MGA","Мадагаскар","","mg","","","","Ариари"),
        ListItem("AFN","Афганистан","","af","","","","Афгани"),
        ListItem("PAB","Панама","","pa","","","","Бальбоа"),
        ListItem("THB","Таиланд","","th","","","","Бат"),
        ListItem("VEF","Венесуэла","","ve","","","","Боливар фуэрте"),
        ListItem("BOB","Боливия","","bo","","","","Боливиано"),
        ListItem("ETB","Эфиопия","","et","","","","Быр"),
        ListItem("VUV","Вануату","","vu","","","","Вату"),
        ListItem("KPW","Северная Корея","","kp","","","","Вона"),
        ListItem("KRW","Южная Корея","","kr","","","","Вона"),
        ListItem("UAH","Украина","","ua","","","","Гривна"),
        ListItem("PYG","Парагвай","","py","","","","Гуарани"),
        ListItem("ANG","Нидерландские Антильские острова","","0","","","","Гульден"),
        ListItem("HTG","Гаити","","ht","","","","Гурд"),
        ListItem("GMD","Гамбия","","gm","","","","Даласи"),
        ListItem("MKD","Северная Македония","","mk","","","","Денар"),
        ListItem("DZD","Алжир","","dz","","","","Динар"),
        ListItem("BHD","Бахрейн","","bh","","","","Динар"),
        ListItem("JOD","Иордания","","jo","","","","Динар"),
        ListItem("IQD","Ирак","","iq","","","","Динар"),
        ListItem("KWD","Кувейт","","kw","","","","Динар"),
        ListItem("LYD","Ливия","","ly","","","","Динар"),
        ListItem("RSD","Сербия","","rs","","","","Динар"),
        ListItem("TND","Тунис","","tn","","","","Динар"),
        ListItem("MAD","Марокко","","ma","","","","Дирхам"),

        ListItem("STN","Сан-Томе и Принсипи","","st","","","","Добра"),
        ListItem("AUD","Австралия","","au","","","","Доллар"),
        ListItem("BSD","Багамы","","0","","","","Доллар"),
        ListItem("BBD","Барбадос","","bb","","","","Доллар"),
        ListItem("BZD","Белиз","","bz","","","","Доллар"),
        ListItem("BMD","Бермудские острова","","bm","","","","Доллар"),
        ListItem("BND","Бруней","","bn","","","","Доллар"),
        ListItem("XCD","Ангилья","","0","","","","Доллар"),
        ListItem("GYD","Гайана","","gy","","","","Доллар"),
        ListItem("HKD","Гонконг","","hk","","","","Доллар"),
        ListItem("ZWD","Зимбабве","","zw","","","","Доллар"),
        ListItem("KYD","0","","0","","","","Доллар"),
        ListItem("CAD","Канада","","ca","","","","Доллар"),
        ListItem("LRD","Либерия","","lr","","","","Доллар"),
        ListItem("NAD","Намибия","","na","","","","Доллар"),
        ListItem("NZD","Новая Зеландия","","nz","","","","Доллар"),
        ListItem("SGD","Сингапур","","sg","","","","Доллар"),
        ListItem("SBD","Соломоновы Острова","","sb","","","","Доллар"),
        ListItem("USD","США","","us","","","","Доллар"),
        ListItem("SRD","Суринам","","sr","","","","Доллар"),
        ListItem("TWD","Тайвань","","tw","","","","Доллар новый"),
        ListItem("TTD","Тринидад и Тобаго","","tt","","","","Доллар"),
        ListItem("FJD","Фиджи","","fj","","","","Доллар"),
        ListItem("JMD","Ямайка","","jm","","","","Доллар"),
        ListItem("VND","Вьетнам","","vn","","","","Донг"),
        ListItem("AMD","Армения","","am","","","","Драм"),
        ListItem("EUR","Страны Еврозоны","","eu","","","","Евро"),
        ListItem("PLN","Польша","","pl","","","","Злотый"),
        ListItem("JPY","Япония","","jp","","","","Иена"),
        ListItem("AOA","Ангола","","ao","","","","Кванза"),
        ListItem("ZMK","Замбия","","zm","","","","Квача"),
        ListItem("MWK","Малави","","mw","","","","Квача"),
        ListItem("GTQ","Гватемала","","gt","","","","Кетсаль"),
        ListItem("PGK","Папуа — Новая Гвинея","","","","","","Кина"),
        ListItem("LAK","Лаос","","la","","","","Кип"),
        ListItem("CRC","Коста-Рика","","cr","","","","Колон"),
        ListItem("NIO","Никарагуа","","ni","","","","Кордоба"),
        ListItem("DKK","Дания","","dk","","","","Крона"),
        ListItem("ISK","Исландия","","is","","","","Крона"),
        ListItem("NOK","Норвегия","","no","","","","Крона"),
        ListItem("—","Фарерские острова","","fo","","","","Крона"),
        ListItem("CZK","Чехия","","cz","","","","Крона"),
        ListItem("SEK","Швеция","","se","","","","Крона"),
        ListItem("HRK","Хорватия","","hr","","","","Куна"),

        ListItem("GEL","Грузия","","ge","","","","Лари"),
        ListItem("LVL","Латвия","","lv","","","","Лат"),
        ListItem("BGN","Болгария","","bg","","","","Лев"),

        ListItem("RON","Румыния","","ro","","","","Лей новый"),
        ListItem("ALL","Албания","","al","","","","Лек"),
        ListItem("HNL","Гондурас","","hn","","","","Лемпира"),
        ListItem("SLL","Сьерра-Леоне","","sl","","","","Леоне"),
        ListItem("SZL","Свазиленд","","0","","","","Лилангени"),
        ListItem("TRY","Турция","","tr","","","","Лира"),
        ListItem("LTL","Литва","","lt","","","","Лит"),
        ListItem("LSL","Лесото","","ls","","","","Лоти"),
        ListItem("AZN","Азербайджан","","az","","","","Манат"),
        ListItem("TMТ","Туркмения","","0","","","","Манат"),
        ListItem("BAM","Босния и Герцеговина","","ba","","","","Марка конвертируемая"),
        ListItem("MZN","Мозамбик","","mz","","","","Метикал"),
        ListItem("NGN","Нигерия","","ng","","","","Найра"),
        ListItem("ERN","Эритрея","","er","","","","Накфа"),
        ListItem("BTN","Бутан","","bt","","","","Нгултрум"),
        ListItem("TOP","Тонга","","to","","","","Паанга"),
        ListItem("MOP","Макао","","mo","","","","Патака"),
        ListItem("ARS","Аргентина","","ar","","","","Песо"),
        ListItem("DOP","Доминиканская республика","","0","","","","Песо"),
        ListItem("COP","Колумбия","","co","","","","Песо"),
        ListItem("CUP","Куба","","cu","","","","Песо"),
        ListItem("MXN","Мексика","","mx","","","","Песо"),
        ListItem("UYU","Уругвай","","uy","","","","Песо"),
        ListItem("PHP","Филиппины","","ph","","","","Песо"),
        ListItem("CLP","Чили","","cl","","","","Песо"),
        ListItem("BWP","Ботсвана","","bw","","","","Пула"),
        ListItem("BRL","Бразилия","","br","","","","Реал"),
        ListItem("IRR","Иран","","ir","","","","Риал"),
        ListItem("YER","Йемен","","ye","","","","Риал"),
        ListItem("QAR","Катар","","qa","","","","Риал"),
        ListItem("OMR","Оман","","om","","","","Риал"),
        ListItem("KHR","Камбоджа","","kh","","","","Риель"),
        ListItem("MYR","Малайзия","","my","","","","Ринггит"),
        ListItem("SAR","Саудовская Аравия","","sa","","","","Риял"),

        ListItem("RUB","Россия","","ru","","","","Рубль"),
        ListItem("INR","Индия","","in","","","","Рупия"),
        ListItem("IDR","Индонезия","","id","","","","Рупия"),
        ListItem("MUR","Маврикий","","mu","","","","Рупия"),
        ListItem("NPR","Непал","","np","","","","Рупия"),
        ListItem("PKR","Пакистан","","pk","","","","Рупия"),
        ListItem("SCR","Сейшелы","","нет","","","","Рупия"),
        ListItem("LKR","Шри-Ланка","","lk","","","","Рупия"),
        ListItem("MVR","Мальдивы","","mv","","","","Руфия"),
        ListItem("ZAR","ЮАР","","нет","","","","Рэнд"),
        ListItem("GHS","Гана","","gh","","","","Седи"),
        ListItem("PEN","Перу","","pe","","","","Соль новый"),

        ListItem("TJS","Таджикистан","","tj","","","","Сомони"),
        ListItem("UZS","Узбекистан","","uz","","","","Сум"),
        ListItem("BDT","Бангладеш","","bd","","","","Така"),
        ListItem("WST","Самоа","","ws","","","","Тала"),
        ListItem("KZT","Казахстан","","kz","","","","Тенге"),
        ListItem("MNT","Монголия","","mn","","","","Тугрик"),
        ListItem("MRO","Мавритания","","mr","","","","Угия"),
        ListItem("AWG","Аруба","","aw","","","","Флорин"),
        ListItem("HUF","Венгрия","","hu","","","","Форинт"),
        ListItem("BIF","Бурунди","","bi","","","","Франк"),
        ListItem("GNF","Гвинея","","gn","","","","Франк"),
        ListItem("DJF","Джибути","","dj","","","","Франк"),
        ListItem("KMF","Коморы","","нет","","","","Франк"),
        ListItem("CDF","Демократическая Республика Конго","","нет","","","","Франк"),
        ListItem("RWF","Руанда","","rw","","","","Франк"),
        ListItem("CHF","Швейцария","","ch","","","","Франк"),
        ListItem("XPF","0","","нет","","","","Франк КПФ"),
        ListItem("XOF","Бенин","","нет","","","","Франк КФА"),
        ListItem("XAF","Габон","","нет","","","","Франк КФА"),
        ListItem("GBP","Великобритания","","gb","","","","Фунт стерлингов"),
        ListItem("GIP","Гибралтар","","gi","","","","Фунт"),
        ListItem("EGP","Египет","","eg","","","","Фунт"),
        ListItem("LBP","Ливан","","lb","","","","Фунт"),
        ListItem("SHP","Остров Святой Елены","","sh","","","","Фунт"),
        ListItem("SYP","Сирия","","sy","","","","Фунт"),
        ListItem("SDG","Судан","","sd","","","","Фунт"),
        ListItem("FKP","Фолклендские острова","","fk","","","","Фунт"),
        ListItem("ILS","Израиль","","il","","","","Шекель новый"),
        ListItem("KES","Кения","","ke","","","","Шиллинг"),
        ListItem("SOS","Сомали","","so","","","","Шиллинг"),
        ListItem("TZS","Танзания","","tz","","","","Шиллинг"),
        ListItem("UGX","Уганда","","ug","","","","Шиллинг"),
        ListItem("CVE","Кабо-Верде","","cv","","","","Эскудо"),
        ListItem("CNY","Китай","","cn","","","","Юань"),



        ListItem("BCH","Биткойн Cash","","bch","","","","Биткойн Cash"),
        ListItem("BTC","Биткойн","","bch","","","","Биткойн"),
        ListItem("XRP","Ripple","","bch","","","","Ripple"),
        ListItem("AED","ОАЭ","","ae","","","","Дихрам"),
        ListItem("KGS","Киргизия","","kg","","","","Сом"),
        ListItem("BTG","Bitcoin Gold","","bch","","","","Bitcoin Gold"),
        ListItem("BYN","Белоруссия","","by","","","","Рубль"),
        ListItem("ETH","Etherium","","bch","","","","Etherium"),
        ListItem("MDL","Молдавия","","md","","","","Лей"),
        ListItem("ZEC","ZCash","","bch","","","","ZCash"),
        ListItem("MMK","Мьянма","","mm","","","","Кьят"),




    )


}