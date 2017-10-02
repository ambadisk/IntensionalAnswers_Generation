# IntensionalAnswers_Generation
This is an approach for computing intensional answers given a set of extensional answers returned as a result of a user query to a database. Intensional answers are considered as descriptions of the actual answers in terms of properties they share and which can enhance a user’s understanding of the answer itself but also of the underlying knowledge base. In our approach, an intensional answer is represented by a clause and computed based on  bottom-up clause generalization which is implemented in Java and MySQL. The approach is evaluated in terms of usefulness and time performance, and its potential for helping to detect flaws in the knowledge base is discussed. While the approach is used in the context of a natural language question answering system in our setting, it clearly has applications beyond.



Please make sure to add "stanford-corenlp-3.7.0-models.JAR" in the lib folder before you compile.


Source to download the above jar file: https://stanfordnlp.github.io/CoreNLP/
