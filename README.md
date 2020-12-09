\documentclass[12pt]{article}
%\documentclass{article}
\usepackage[utf8]{inputenc}
\usepackage[OT4]{fontenc}
\usepackage[polish]{babel}
\usepackage[hmargin=4.0cm, vmargin=2.5cm, hcentering]{geometry}
\usepackage{indentfirst}
\usepackage{hyperref}
\usepackage{url}
\usepackage{enumitem}
\usepackage{graphicx}


\title{%
  Wizualizacja danych dotyczących kryptowalut \\
  \large Zaawansowane programowanie obiektowe i funkcyjne \\
  \normalsize Konspekt projektu}

\author{Maria Kałuska \and Michał Komorowski \and Marcelina Kurek}
\date{Listopad 2020}



\date{\today}

\begin{document}

\maketitle
\newpage

\tableofcontents
\newpage

\section{Opis projektu}
\subsection{Cel}
Głównym celem aplikacji jest wizualizacja danych dotyczących kryptowalut. Ma ona prezentować kluczowe dane w wygodny i przystępny dla użytkownika sposób.
\subsection{Grupa docelowa}
Aplikacja skierowana jest przede wszystkim do osób, które miały już kontakt z kryptowalutami. Aby użytkownik mógł wygodnie korzystać z aplikacji powinien znać podstawowe pojęcia z nimi związane.
\\ \indent Użytkownik powinien również orientować się jak działa sam rynek kryptowalut, ponieważ dostarczona przez nas aplikacja służy jedynie do zaprezentowania dostępnych danych i nie jest jej celem objaśnianie użytkownikowi zasad działania giełdy.


\subsection{Wartości dla użytkownika}
\begin{itemize}
    \item Użytkownik zyska możliwość bezpłatnego oglądania interesujących go danych, zaprezentowanych w czytelny sposób. 
    \item Aplikacja pokazuje wyłącznie dane związane z kryptowalutami. Nie wyświetlają się w tle żadne reklamy.
    \item Dostęp do najważniejszych danych, zebranych przez nas w jednym miejscu w formie wykresu lub tabelki. - Użytkownik nie musi szukać samodzielnie w kilku miejscach, ponieważ wszystko zostanie mu dostarczone.
    \item Możliwość porównywania kilku kryptowalut.
    \item Możliwość dostępu do wizualizacji bez zbędnego zakładania konta.
\end{itemize}

\newpage
\section{Działanie aplikacji}

\subsection{Funkcje}

Podstawową funkcją aplikacji jest wizualizacja danych dotyczących wybranych przez użytkownika kryptowaluty.
Użytkownik może wybrać jedną z tysięcy dostępnych kryptowalut albo wprowadzić jej nazwę czy skrót. Dodatkowo decyduje też czy chce zobaczyć dane dotyczące jednej kryptowaluty czy kilku na raz. Z powodu ograniczeń API, maksymalnie będzie mógł wybrać trzy kryptowaluty jednocześnie. Najważniejsze statystyki i funkcje, które chcemy zapewnić użytkownikowi to:

\begin{itemize}
    \item Wykres ceny na przestrzeni ostatniego dnia, tygodnia, miesiąca lub roku.
    \item Procentowy stosunek obecnej ceny względem ostatniego dnia, tygodnia, miesiąca i roku.
    \item Dodatkowe informacje prezentowane w formie tabelki, między innymi wolumen obrotu, maksymalna i minimalna historyczna cena.
    \item Przeliczenie ceny na wybraną przez użytkownika realną walutę.
    \item Porównywanie kryptowalut między sobą.
\end{itemize}

\subsection{Wymagania funkcjonalne}

\begin{enumerate}
    \item Aplikacja będzie wyświetlać wykresy dotyczące kryptowalut.
    \begin{enumerate}[label*=\arabic*.]
        \item Aplikacja powinna wyświetlać na wykresie dane z różnych przedziałów czasowych.
        \item Aplikacja będzie wyświetlać wykres ceny kryptowaluty.
        \item Aplikacja powinna mieć różne typy wykresów, na przykład wykresy świecowe lub wykresy wolumenu.
    \end{enumerate}
    \item Aplikacja będzie wyświetlać cenę kryptowaluty przeliczoną na realne waluty.
    \item Aplikacja będzie wyświetlać dodatkowe informacje o kryptowalucie w formie tabeli.
    \item Aplikacja powinna porównywać wybrane przez użytkownika kryptowaluty.
    \item Aplikacja powinna posiadać przycisk do odświeżania danych, który aktualizuje wszystkie obecnie wyświetlane dane.
\end{enumerate}

\newpage

\subsection{Wymagania niefunkcjonalne}
\begin{enumerate}
\item Aplikacja będzie obsługiwać co najwyżej 5 użytkowników jednocześnie.
\item Aplikacja będzie przetwarzać maksymalnie 100 żądań na minutę.
\item Aplikacja powinna mieć czytelny interfejs użytkownika.
\item Wizualizacja danych powinna być w formie czytelnych wykresów i tabel.
\item Interfejs użytkownika powinien być atrakcyjny wizualnie dla użytkownika.
\item Aplikacja powinna wyświetlać na stronie głównej listę kilku najpopularniejszych obecnie kryptowalut.
\end{enumerate}


\newpage

\section{Architektura aplikacji}
\begin{figure}[h]
\includegraphics[width=10cm]{My First Board.jpg}
\centering
\end{figure}
\subsection{Pobieranie danych}
Do pobierania danych będziemy używać API udostępnione przez CoinGecko. Jest ono w pełni publiczne i darmowe, jednak posiada limit 100 żądań na minutę. W związku z tym jesteśmy zmuszeni do skupienia się na co najwyżej kilku kryptowalutach jednocześnie, tzn. nie możemy pokazać podsumowania stu najdroższych kryptowalut. \\

\subsection{Reguły biznesowe}
Reguły biznesowe to kluczowy mechanizm całego projektu. Zadaniem algorytmu będzie przygotowanie danych pobranych z API tak, aby można było je przekazać do funkcji rysujących wykresy i tabele, a następnie zostaną one wygenerowane. \\ \indent
Z drugiej strony algorytm będzie pobierać dane od użytkownika i na ich podstawie dostosowywać obróbkę danych pod jego potrzeby. 


\subsection{Interfejs użytkownika}
Część graficzna aplikacja wyświetlana użytkownikowi. Użytkownik za jego pomocą będzie miał możliwość podania wszystkich informacji, które powinny zostać uwzględnione przy pokazywaniu mu wyników. Interfejs graficzny będzie pokazywał użytkownikowi przygotowane wizualizacje w formie wykresów i tabel.


\end{document}
