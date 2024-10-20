package com.example.myapplication;

import java.io.Serializable;
import java.util.List;

/**
 * Parser used to search according to the String in tokenizer
 * Method parseContent() is used to search related data containing the input in data's name and description.
 * Method parseType() can filter data types and is used in search input and filter button.
 * Grammar: search content(optional) + "#" data type(optional) + search content(optional)
 * i.e. for this class, <parserContent> ::= <parserContent> | <parserType>
 * (At start, parserType in meant to be a terminal. However, we found
 * people may want to add search content after filtering data type,
 * so we revise the grammar to make it available. )
 * @author Qifeng Zheng
 */
public class Parser implements Serializable {
    Tokenizer tokenizer;
    List<CarInfo> carInfoList;

    public Parser(Tokenizer tokenizer, List<CarInfo> carInfoList){
        this.tokenizer = tokenizer;
        this.carInfoList = carInfoList;
    }

    public void parseContent(){
        if (tokenizer.hasNext()){
            if (tokenizer.current().getType() == Token.Type.SEARCH){
                carInfoList.removeIf(carInfo -> ((!carInfo.name.toLowerCase().contains(tokenizer.current().getToken()))
                        && (!carInfo.description.toLowerCase().contains(tokenizer.current().getToken()))));
            }else if (tokenizer.current().getType() == Token.Type.VIDEO ||
                    tokenizer.current().getType() == Token.Type.ARTIC){
                parseType();
            }
        }tokenizer.next();
        if (tokenizer.hasNext()){
            parseContent();
        }
    }

    public void parseType(){
        if (tokenizer.hasNext()){
            if (tokenizer.current().getType() == Token.Type.VIDEO){
                carInfoList.removeIf(carInfo -> !carInfo.type.equals("video"));
            } else if (tokenizer.current().getType() == Token.Type.ARTIC) {
                carInfoList.removeIf(carInfo -> !carInfo.type.equals("article"));
            }
        }
    }

    public List<CarInfo> getCarInfoList(){
        return carInfoList;
    }
}
