# KMP-Search
Knuth Morris Pratt pattern searching algorithm utilising a custom-built skip array in java.<br>
KMPtable.java produces the skip array table for a given phrase,<br>
KMPserach uses the reads the file the table is in and searches a given file.

KMPtable <br>
Usage: java KMPtable \<phrase\> \> \<output-filename>

KMPsearch <br>
Usage: java KMPsearch \<kmptable-file\> \<file-to-search\><br>
<br>
More info on [Knuth Morris Pratt algorithm](https://en.wikipedia.org/wiki/Knuth–Morris–Pratt_algorithm).
