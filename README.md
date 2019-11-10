EsempioApplication

Annotazioni
https://developer.android.com/studio/write/annotations.html#thread-annotations 
https://developer.android.com/reference/android/support/annotation/WorkerThread

Fragment
Retain this Fragment across configuration changes in the host Activity: setRetainInstance(true);


howto pair BT
http://www.londatiga.net/it/programming/android/how-to-programmatically-pair-or-unpair-android-bluetooth-device/
https://github.com/lorensiuswlt/AndroBluetooth


https://stackoverflow.com/questions/3350283/any-way-to-run-shell-commands-on-android-programmatically




1) factory x singletong
2) dentro uno stato (varianti)
3) mappa/e nel singletong di singletong manager con chiave la variante (se non esisste istanzio e salvo nella mappa, altrimenti prelevo dalla mappa)
4) prevedere per chi crea un canale anche in interfaccia il metodo swap tra canali dello stesso tipo (lo stesso non fa niente, altrimenti chiude quello vecchio e apre quello nuovo)
5) se cambia variante (nel metodo setVariante) prevedere di chiamare la chiusura del manager precedente (server e canale) e poi cambiare la variante (così close manager vecchio e open manager nuovo), se invece non cambia variante si chiama il metodo swap
6) nel finalize del singletong fare il close dei manager nella mappa
X) e ultimo fare una branch daggerizzata di tutto il progetto sopra
