# word-density-analysis

Interview Project: 

OBJECTIVES:
---------------------------------------------------------------------------------------------------
Given any page (URL), be able to classify the page, and return a list of relevant topics.      

DESSIGN:
---------------------------------------------------------------------------------------------------
1. Parse url. In this part, I used Jsoup to parse url and get Document. Parse every word 
   from the Document and add them to the Word Trie(word trie will be introduced later). 
   jsoup is a Java library for working with real-world HTML. It provides a very convenient 
   API for extracting and manipulating data, using the best of DOM, CSS, and jquery-like methods.

2. Parse key words. Count the frequency of each key word. Key word might be one single word, or 
   might be consist of multiple words like Compact toaster. So I need to determine which words are 
   candidates. The idea is that the candidates should be the words after exclusing the stop words 
   and punctuation("?",",",".","!"......).
   Therefore, first, create stop words list from stopwords.txt. Then remove all punctuation 
   when parsing each word.
   In this project, return the top five most frequency of key words. 

Step 1: Parsing HTML
--------------------
jsoup library is used.
acknoledgement: http://jsoup.org


Step 2: Parsing text string into semantic sentence
--------------------------------------------------
A pre-defined list of stopwords is used. stopwords usually refer to the most common words in a language. Usually, a
paragraph can be seperated semantically by stopwords.
Scan through the text and seperate the whole String into a 'List<List<String>> phrases' by either stopwords or
punctuation marks. Each semantic short sentence is stored in phrases as List<String>.


Step 3: Count words
-------------------
Iterate through 'List<List<String>> phrases' and count the word frequency. Store the results in Map<String, Integer>.
The project implemented one-word, two-word-phrases, three-word-phrases counting.
For multi-word-phrases conting, a sliding window iteration through each short sentenct, i.e., the List<String> is implemented.


Step 4: Count for different part of the HTML code
-------------------------------------------------
Since different element of HTML can have different significance, I implemented counting word frequency in all of the parts
Therefore the whole data structure is Map<String, List<List<Map.Entry<String, Integer>>>>,
It is a map with key of all parts
the value is List<List<Map.Entry<String, Integer>>>, representing a list of 1-word, 2-words, 3-words'  "word-count"
entry.

DESSIGN:
---------------------------------------------------------------------------------------------------

Outputs:

One word density:


Word: toaster Count: 85
Word: amazon Count: 47
Word: bread Count: 39
Word: toast Count: 36
Word: cuisinart Count: 34
Word: prime Count: 32
Word: stars Count: 31
Word: stainless Count: 25
Word: 2-slice Count: 22

Two word density:


Word: stainless steel Count: 15
Word: prime feedback Count: 12
Word: published days Count: 9
Word: 2-slice toaster Count: 9
Word: days ago Count: 9
Word: high lift Count: 9
Word: review helpful Count: 8
Word: toaster white Count: 8
Word: yesnoreport abuse Count: 8

Three word density:


Word: published days ago Count: 9
Word: high lift lever Count: 7
Word: compact plastic toaster Count: 7
Word: prime feedback kitchenaid Count: 6
Word: dials buttons lever Count: 6
Word: toaster brushed stainless Count: 6
Word: details prime feedback Count: 5
Word: 2-slice compact plastic Count: 5
Word: cpt-122 2-slice compact Count: 5


