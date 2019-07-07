package com.yuhdeveloper.konusmaegzersizi.viewPagerSample

import android.net.Uri
import com.yuhdeveloper.konusmaegzersizi.R

enum class PhotoItem(val photoId: Int, val nameId: String, val locationId: String) {

    TANBAR(R.mipmap.ic_launcher_talk, "BU NE ?", "KONUŞMA EGZERSİZLERİ uygulaması senin konuşma becerini geliştirmeyi amaçlamaktadır." ),
    V(R.drawable.ic_help_outline_red_24dp, "NASIL OYNAYACAĞIM ?", "Maraton veya 1 VS 1 oyunlarına girdiğinde ekranına rastgele birbirinden farklı kelimeler gelecektir. <br>" +
            "Her kelime için anlamlı cümleler kurup cümleler arası bağlantı sağlamalısınız.<br><br>" +
            "Örnek : Televizyon,Uyuklamak,Mısır<br><br>" +
            "Babam'ın oturma odasında <b>televizyon</b> izlediğini sanıyordum. Salonda onu <b>uyuklarken</b> buldum. Film izlerken yemesi için verdiğim patlamış <b>mısırı</b> yere dökmüş..."),

    S(R.drawable.gold_medal, "GOLD NE İŞE YARIYOR ?","YAKINDA UYGULAMA İÇİNDE ALIŞVERİŞ YAPABİLECESİNİZ.")
}