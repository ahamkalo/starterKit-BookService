Book Service

Autor: Aleksander Hamkało

Aplikacja pozwala na wyszukiwanie książek po prefixie tytułu oraz dodawanie nowych książek.

Wyszukiwanie odbywa się poprzez wpisanie w polu title prefixu tytułu książki i kliknięcie przycisku Search. Wielkość liter nie ma znaczenia. Kliknięcie przycisku Search powoduje wysłanie zapytania do serwisu restowego. W odpowiedzi otrzymywane są dane w postaci JSONa, które przetwarza BookProvider. Dane te są mapowane na obiekt BookVO. Dalsze przetwarzanie i wyświetlenie danych książek w tabeli jest obsługiwane przez kontroler.

Kliknięcie przycisku Add Book powoduje otwarcie nowego dialogu. Dialog ten pozwala na dodanie nowej książki do bazy danych. Dodanie książki jest możliwe po uprzednim dodaniu autorów (umożliwa to przycisk Add Author otwierający nowy dialog) oraz wpisaniu tytułu książki. Kliknięcie przycisku Add Book powoduje wysłanie zapytania do serwisu restowego i dodanie nowej książki.