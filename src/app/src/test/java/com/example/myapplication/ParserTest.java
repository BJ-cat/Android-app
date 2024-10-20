package com.example.myapplication;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Test the parser and tokenizer classes
 * including three test cases, to check parserContent() method, parserType() method,
 * and the condition when not filtering data type in searching input.
 * @author Qifeng Zheng
 */
public class ParserTest {
    CarInfo c1 = new CarInfo("5 steps to mental wellbeing",
            "Some useful methods to improve your mental health and wellbeing.",
            "article", "https://www.nhs.uk/");
    CarInfo c2 = new CarInfo("Top 10 tips to maintain your mental health",
            "The video provide useful tips to maintain your mental health",
            "video","https://www.youtube.com/watch?v=-OAjfrhuwRk");
    CarInfo c3 = new CarInfo("5 steps to mental wellbeing",
            "Some useful methods to improve your mental health and wellbeing.",
            "video", "https://www.nhs.uk/");
    CarInfo c4 = new CarInfo("10 steps to mental wellbeing",
            "Some useful methods to improve your mental health and wellbeing.",
            "article", "https://www.nhs.uk/");
    List<CarInfo> C = new ArrayList<>(Arrays.asList(c1, c2, c3, c4));
    String search = "wellbeing 5 #article";
    String withoutType = "wellbeing 5";
    Tokenizer tokens = new Tokenizer(search);
    Tokenizer token2 = new Tokenizer(withoutType);
    Parser parser = new Parser(tokens, C);
    Parser parser2 = new Parser(token2, C);

    @Test
    public void checkParserType(){
        tokens.next();
        for (;tokens.current().getType()!=Token.Type.ARTIC; tokens.next()){
            //make current token is "#article"
        }
        parser.parseType();
        assertEquals(Arrays.asList(c1, c4), C);
    }

    @Test
    public void checkParserContent(){
        tokens.next();
        parser.parseContent();
        assertEquals(Collections.singletonList(c1), C);
    }

    @Test
    public void checkWithoutType(){
        tokens.next();
        parser2.parseContent();
        assertEquals(Arrays.asList(c1, c3), C);
    }
}
