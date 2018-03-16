package com.geno1024.glang

object Lexer1
{
    fun getNextTokenLength(string: String): Int
    {
        return if (string == "") 0 // empty string
        else when (string[0])
        {
            // integer
            in ('1'..'9') -> kotlin.math.min(string.indexOfFirst { it !in ('0'..'9') + '.' }.takeIf { it != -1 }?:string.length, string.indexOf('.', string.indexOf('.') + 1).takeIf { it != -1 }?:string.length)
            '0' -> when (string[1])
            {
                // hexadecimal
                'x', 'X' -> with(string.substring(2), { this.indexOfFirst { it !in ('0'..'9') + ('A'..'F') + ('a'..'f') }.takeIf { it != -1 }?:this.length }) + 2
                // decimal
                '.' -> with(string.substring(2), { this.indexOfFirst { it !in ('0'..'9') }.takeIf { it != -1 }?:this.length}) + 2
                // octal at parser
                in ('0'..'9') -> string.indexOfFirst { it !in ('0'..'9') }
                else -> 1
            }
            '.' -> with(string.substring(1), { this.indexOfFirst { it !in('0'..'9') }.takeIf { it != -1 }?:this.length }) + 1
            '(', ')', '[', ']', '{', '}', ';' -> 1
            '+', '-' -> if (string[1] == string[0] || string[1] == '=') 2 else 1
            '*', '/', '&', '^', '%', '|', '\\', '!', '=', '<', '>' -> if (string[1] == '=') 2 else 1
            '"' -> string.indexOf('"', string.indexOf('"') + 1) + 1
            in ('A'..'Z') + ('a'..'z') + '_' -> string.indexOfFirst { it !in ('A'..'Z') + ('a'..'z') + ('0'..'9') + '_' }
            else -> 1
        }.takeIf { it != -1 }?:string.length
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
    }
}