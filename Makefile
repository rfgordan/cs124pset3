JCC = javac

JFLAGS = -Xlint -g

default: numberpartition.class


numberpartition.class: numberpartition.java
		$(JCC) $(JFLAGS) numberpartition.java

clean:
		$(RM) *.class
