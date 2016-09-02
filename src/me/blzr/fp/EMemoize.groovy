package me.blzr.fp

import groovy.transform.Memoized

int fib(int cnt) {
    if (cnt <= 1) {
        1
    } else {
        fib(cnt - 1) + fib(cnt - 2)
    }
}

@Memoized
int fib2(int cnt) {
    if (cnt <= 1) {
        1
    } else {
        fib2(cnt - 1) + fib2(cnt - 2)
    }
}

def t1 = System.currentTimeMillis()
fib(40)
println System.currentTimeMillis() - t1

def t2 = System.currentTimeMillis()
fib2(40)
println System.currentTimeMillis() - t2
