package com.geno1024.glang

enum class Token
{
    NUMBER, // 1, 3.5, 2.7F, 4.8d
    STRING, // "A", "BCD"
    OPERATOR, // +, -
    IDENTIFIER, // if, do
    FIELD, // main,
}