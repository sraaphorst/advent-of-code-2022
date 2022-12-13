# Day 12

This day requires use of BFS or Djikstra's algorithm.

Implementing Djikstra's algorithm with immutable data structures in Kotlin is very inefficient because of the reliance
on Java's data structures, which require full copies to be made instead of using clever techniques to manipulate and
maintain data structures.

Thus, I provide both a:
* Immutable implementation (part 2 take about 35 seconds)
* Mutable implementation (part 2 takes about 7 seconds)

There is a lot of code duplication between the two implementations, but I did not feel like extracting the common code
and changing things to accept functions, etc.