package com.example.myapplication;

/**
 * Token used for tokenizer
 * token is the String input, while there are three types for string, video filter and article filter respectively.
 * @author Qifeng Zheng
 */
public class Token {
    public enum Type{SEARCH, VIDEO, ARTIC}

    private final String token;
    private final Type type;

    public Token(String token, Type type){
        this.token = token;
        this.type = type;
    }

    public String getToken(){return token;}
    public Type getType(){return type;}

}

