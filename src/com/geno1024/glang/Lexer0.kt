package com.geno1024.glang

/**
 * @author Geno1024
 * @date 2018-03-12 02:45:57
 *
 * Lexer, tokenize a string.
 */
object Lexer0
{
    /**
     * Return the length of beginning white spaces.
     * @param string the string being trimmed.
     * @return the length of heading spaces.
     */
    fun trimSize(string: String): Int = string.indexOfFirst { it !in arrayOf(' ', '\t') }

    /**
     * Return the length of next token with trimming.
     * @param string the string being gotten next token.
     * @return the next token's length, without the beginning spaces.
     */
    fun getNextToken(string: String): Int
    {
        val str = string.trim()
        return when (str[0])
        {
            // single char tokens
            '(', ')', '[', ']', '{', '}', ';', ':' -> 1
            // = combine-able tokens
            '+', '-', '*', '/', '^', '%', '&', '|', '!', '?', '=' -> if (str[1] == '=') 2 else 1
            // variable length tokens
            '0' ->
                when
                {
                    str[1] == 'x' /* hexadecimal */ -> str.indexOfFirst { it !in ('0'..'9') + ('A'..'F') + ('a'..'f') }
                    str[1] != '.' /* octal */ -> str.indexOfFirst { it !in ('0'..'8') }
                    str[1] == '.' /* decimal */ -> str.indexOfFirst { it !in ('0'..'9') }
                    else -> 1
                }
            // string literal
            '"' -> str.indexOfFirst { it != '"' }
            else -> str.indexOfFirst { it !in arrayOf(' ', '\t', '\n') }
        }
    }

    fun _getNextToken(string: String): String
    {
        val str = string.trim()
        return when (str[0])
        {
            '(', ')', '[', ']', '{', '}', ';' -> str[0].toString()
            '+', '-', '*', '/', '^', '%',
            '&', '|',
            '!', '?', ':', '=' -> if (str[1] == '=') "${str[0]}=" else str[0].toString()
            '0' -> if (str[1] == 'x') str.takeWhile { it in ('0'..'9') + ('A'..'F') + ('a'..'f') } else str.takeWhile { it in ('0'..'7') }
            in ('1'..'9') + '.' -> str.takeWhile { it in ('0'..'9') + '.' }
            in ('A'..'Z') + ('a'..'z') -> str.takeWhile { it in ('A'..'Z') + ('a'..'z') + ('0'..'9') }
            else -> str[0].toString()
        }
    }

    fun lex(string: String): ArrayList<String>
    {
        val result = ArrayList<String>()
        var str = string
        while (str != "")
        {
            str = str.trim()
            val nextToken = _getNextToken(str)
            result += nextToken
            str = str.substring(nextToken.length)
        }
        return result
    }
}