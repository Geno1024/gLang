package com.geno1024.glang

object Lexer2
{
    fun getNextTokenLength(string: String): Int
    {
        return if (string == "") 0
        else when (string[0])
        {
            in ('A'..'Z') + ('a'..'z') -> string.indexOfFirst { it in arrayOf(' ', '\t') }
            '0' -> { 1 }
            in ('1'..'9') -> string.indexOfFirst { it !in ('0'..'9') }
            else -> { 1 }
        }
    }

    fun lex(string: String): List<String>
    {
        val result: ArrayList<String> = arrayListOf()
        var str: String = string
        while (str != "")
        {
            str = str.trim()
            val nextTokenLength: Int = getNextTokenLength(str)
            result += str.substring(0, nextTokenLength)
            str = str.substring(nextTokenLength)
        }
        return result
    } // 😐
}