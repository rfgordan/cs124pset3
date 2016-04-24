JCC = javac
JVM = java
JFLAGS = -Xlint -g

default: numberpartition.class

run: numberpartition.class
		$(JVM) numberpartition

numberpartition.class: numberpartition.java
		$(JCC) $(JFLAGS) numberpartition.java

clean:
		$(RM) *.class
