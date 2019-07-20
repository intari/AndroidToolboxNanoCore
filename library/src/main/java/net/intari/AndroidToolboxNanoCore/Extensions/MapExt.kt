package net.intari.AndroidToolboxNanoCore.Extensions

/**
 * Created by Dmitriy Kazimirov on 2019-07-20.
 */

//version of .forEach from regular maps which doesn't require API 24
fun <T1,T2> Map<T1,T2>.for_each(block: (k:T1,v:T2) -> Unit):Unit {
    for (key in this.keys) {
        block(key,this.get(key).getOrDie())
    }
}

