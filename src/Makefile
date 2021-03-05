JFLAGS = -g
JC= javac
DIR= -d ./bin

CLASSES = com/apd/tema2/Main.java com/apd/tema2/*/*.java

build:
	$(JC) $(JFLAGS) $(CLASSES) $(DIR)

run: build
	java src.com.apd.tema2.Main
clean: build
	$(RM) .class
