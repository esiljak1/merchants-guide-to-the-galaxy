# Merchants guide to the galaxy

### Info about building and running the solution
Solution is written in java </br>
Java version: 17 </br>
To run the program meant for user start the *Program.java* class

### Assumptions made
- Number code can only be made out of a single word (different words are seperated by whitespace)
- User can only write queries in the form it's written in the *test input*
  - To register a word *one* to have value *1* you use: **one is I**
  - To register the cost of an item you use: **_itemAmount_ _itemName_ is _itemPrice_ Credits**
  - To find out what is the value of some sequence of number codes you use: **how much is _codeNumberSequence_**
  - To find out what is the price of some item you use: **how many Credits is _itemAmount_ _itemName_**