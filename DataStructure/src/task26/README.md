26. Чёрный ящик

Чёрный ящик организован наподобие примитивной базы данных. Он может хранить набор целых чисел и имеет выделенную переменную i. В начальный момент времени чёрный ящик пуст, а значение переменной i равно нулю. Чёрный ящик обрабатывает последовательность поступающих команд (запросов). Существуют два вида запросов:

    Добавить(x) — положить в чёрный ящик элемент x;
    Получить() — увеличить значение переменной i на 1 и выдать копию i-го по величине элемента чёрного ящика (напомним, что i-м по величине элементом называется число, стоящее на i-м месте в отсортированной по неубыванию последовательности элементов чёрного ящика). 

Необходимо разработать алгоритм, обрабатывающий заданную последовательность поступающих команд (запросов) за время O(m ⋅ logm), где m — число запросов Добавить.

Input
В первой строке задано число m запросов Добавить и число n запросов Получить (1 ≤ n ≤ m ≤ 2 000 000).

Во второй строке — последовательность a1, a2, …, am включаемых в чёрный ящик элементов — целых чисел, не превосходящих по абсолютной величине 2 000 000 000.

В третьей строке — последовательность u1, u2, …, un, задающая число содержащихся в чёрном ящике элементов в момент выполнения первой, второй, …, n-й команды Получить.

Схема работы чёрного ящика предполагает, что последовательность u1, u2, …, un целых чисел упорядочена по неубыванию, n ≤ m и для всех p (1 ≤ p ≤ n) выполняется соотношение p ≤ up ≤ m. Последнее следует из того, что для p-го элемента последовательности u мы выполняем запрос Получить, выдающий p-е по величине число из набора a1, a2, …, aup.

Output
Выведите полученную последовательность ответов чёрного ящика для заданной последовательности запросов (т. е. результаты работы команды Получить).

Example
input.txt 	        output.txt
6 4                  3 3 1 2
3 1 -4 2 8 -1000
1 2 6 6

  
 Запрос 	        i 	 Чёрный ящик 	 Результат обработки запроса 
 Добавить3	      0	    3	
 Получить         1	    3	                  3
 Добавить1	      1	    1 3	
 Получить         2	    1 3	                3
 Добавитm−4	      2	    −4 1 3	
 Добавить2	      2	    −4 1 2 3	
 Добавить8	      2 	  −4 1 2 3 8	
 Добавить−1000 	  2 	  −1000 −4 1 2 3 8 	
 Получить         3 	  −1000 −4 1 2 3 8 	  1
 Получить         4 	  −1000 −4 1 2 3 8 	  2