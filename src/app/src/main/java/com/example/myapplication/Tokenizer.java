package com.example.myapplication;

/**
 * Tokenizer changes the input String into a tree for parser to search
 * There are two types of data in local json file, which are article and video respectively.
 * Users can filter these data types by typing "#article" and "#video" in searching input.
 * @author Qifeng Zheng
 */
public class Tokenizer {
    //the current token
    private Token currentToken;
    private String[] words;
    private RedBlackTree wordlist;


    public Tokenizer(String input){
        //split into individual words when matching whitespaces
        //need to initialize in the constructor
        words = input.split("\\s+");
        wordlist = RedBlackTree.listToRedBlackTree(words);
    }

    //n is the parameter for next()
    private int n = 0;

    public void next(){
        if ((words.length == 0) || (words.length == n)){
            currentToken = null;
            return;
        }
        String nextWord = wordlist.inOrderTraversal(n);
        if (nextWord == null) {
            currentToken = null;
            return;
        }

        n++;

        if (nextWord.equalsIgnoreCase("#article"))
            currentToken = new Token("#article", Token.Type.ARTIC);
        else if (nextWord.equalsIgnoreCase("#video"))
            currentToken = new Token("#video", Token.Type.VIDEO);
        else
            currentToken = new Token(nextWord, Token.Type.SEARCH);
    }

    public Token current(){
        return currentToken;
    }

    public boolean hasNext(){
        return currentToken != null;
    }
}
