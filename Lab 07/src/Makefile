# Makefile framework for CSCI3308/Lab7
# by

# You should implement the makefile for the lab

all: facts
	@echo cleaning...
	@make clean
	@echo Success!

facts: facts.c lib/libFunnyFacts1.a lib/libFunnyFacts2.a
	gcc facts.c lib/libFunnyFacts1.a lib/libFunnyFacts2.a -o facts -Iinclude	

lib/libFunnyFacts1.a: object1.o subject1.o verb.o
	ar -cr lib/libFunnyFacts1.a object1.o subject1.o verb.o

lib/libFunnyFacts2.a: object2.o subject2.o verb.o
	ar -cr lib/libFunnyFacts2.a object2.o subject2.o verb.o

object1.o : object1.c
	gcc -c object1.c -Iinclude

object2.o : object2.c
	gcc -c object2.c -Iinclude

subject1.o : subject1.c
	gcc -c subject1.c -Iinclude

subject2.o : subject2.c
	gcc -c subject2.c -Iinclude

verb.o : verb.c 
	gcc -c verb.c -Iinclude


clean: 
	@rm -f *.o lib/*.a
