package com.geno1024.glang

import com.geno1024.glang.Lexer2.lex
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

object LexerTest
{
    @Test fun empty()
    {
        assertEquals(arrayListOf(), lex(""), "Empty failed")
    }

    @Test fun integer()
    {
        assertEquals(arrayListOf("123"), lex("123"), "Integer::Dec failed")
        assertEquals(arrayListOf("0x123"), lex("0x123"), "Integer::Hex failed")
        assertEquals(arrayListOf("0123"), lex("0123"), "Integer::Oct failed")
    }

    @Test fun decimal()
    {
        assertEquals(arrayListOf("1.23"), lex("1.23"), "Decimal::Orig failed")
        assertEquals(arrayListOf("1.23", ".45"), lex("1.23.45"), "Decimal::Orig failed")
        assertEquals(arrayListOf("0.23"), lex("0.23"), "Decimal::Orig failed")
        assertEquals(arrayListOf("0.23", ".45"), lex("0.23.45"), "Decimal::Orig failed")
        assertEquals(arrayListOf(".123"), lex(".123"), "Decimal::OmitLeading failed")
        assertEquals(arrayListOf(".123", ".456"), lex(".123.456"), "Decimal::OmitLeading failed")
    }

    @Test fun tightExpression()
    {
        assertEquals(arrayListOf("1", "+", "2"), lex("1+2"), "Expression::Tight::Integer failed")
        assertEquals(arrayListOf("0.1", "+", "0.2"), lex("0.1+0.2"), "Expression::Tight::Decimal::Orig failed")
        assertEquals(arrayListOf(".1", "+", ".2"), lex(".1+.2"), "Expression::Tight::Decimal::OmitLeading failed")
    }

    @Test fun looseExpression()
    {
        assertEquals(arrayListOf("1", "+", "2"), lex("1 + 2"), "Expression::Loose::Integer failed")
        assertEquals(arrayListOf("0.1", "+", "0.2"), lex("0.1 + 0.2"), "Expression::Loose::Decimal failed")
        assertEquals(arrayListOf(".1", "+", ".2"), lex(".1 + .2"), "Expression::Loose::Decimal::OmitLeading failed")
    }

    @Test fun combinedExpressions()
    {
        assertEquals(arrayListOf("a", "+=", "2"), lex("a+=2"), "Expression::Tight::Combined failed")
        assertEquals(arrayListOf("1", "+", "2", "=", "3"), lex("1+2=3"), "Expression::Tight::Combined failed")
        assertEquals(arrayListOf("1", "+", "2", "==", "3"), lex("1+2==3"), "Expression::Tight::Combined failed")
        assertEquals(arrayListOf("(", "1", "+", "2", ")", "*", "3", "==", "9"), lex("(1+2)*3==9"), "Expression::Tight::Combined failed")
    }

    @Test fun stringLiteral()
    {
        assertEquals(arrayListOf("\"string\""), lex("\"string\""), "String::Single failed")
        assertEquals(arrayListOf("\"string with spaces\""), lex("\"string with spaces\""), "String::Spaces failed")
        assertEquals(arrayListOf("\"string with spaces\"", "+", "\"and another string\""), lex("\"string with spaces\" + \"and another string\""), "String::Spaces::Combined failed")
    }

    @Test fun variables()
    {
        assertEquals(arrayListOf("a"), lex("a"), "Variables::Single failed")
        assertEquals(arrayListOf("a_b"), lex("a_b"), "Variables::Underlined failed")
        assertEquals(arrayListOf("a_b", "c_d"), lex("a_b c_d"), "Variables::Underlined failed")
    }

    @Test fun raw()
    {
        assertEquals(arrayListOf("int", "i", "=", "0", ";"), lex("int i = 0;"), "RAW failed")
        assertEquals(arrayListOf("for", "(", "int", "i", "=", "0", ";", "i", "<", "10", ";", "i", "++", ")", "{", "doSth", "(", "i", ")", ";", "}"), lex("for (int i = 0; i < 10; i++) { doSth(i); }"), "RAW failed")
    }
}