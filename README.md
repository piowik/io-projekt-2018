# io-projekt-2018

Instrukcja kompilacji:
Projekt otworzyć w Android Studio, wygenerować w usłudze konsoli deweloperskiej Google plik google-services.json, a następnie umieścić go w app/
Program jest gotowy do kompilacji.

Instrukcja instalacji
Sposób 1:
Podłączyć telefon do komputera, a następnie uruchomić aplikację przez Android Studio, jako cel wybierając telefon

Sposób 2:
Wygenerować plik .apk w Android Studio, przenieść go na telefon, a następnie otworzyć.

Instrukcja konfiguracji:
Serwer:
Tworzymy bazę danych z dołączonego schematu schema.sql
Konfigurujemy połączenie do bazy danych oraz usługi Firebase w pliku config.php
define('DB_USERNAME', 'uzytkownik_bazy');
define('DB_PASSWORD', 'haslo');
define('DB_HOST', 'host');
define('DB_NAME', 'nazwa_bazy');
define('FIREBASE_API_KEY', 'token firebase');

Aplikacja:
W ApiUtils ustawiamy endpoint kierujący na nasz serwer
public static final String BASE_URL = "http://naszadres.pl/";

