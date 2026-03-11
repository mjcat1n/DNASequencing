DNA String Comparison Project
Dr. Jody Paul
V.2026-03-10a

In this project we capitalize the acronym DNA fully in identifiers.

To add another algorithm:
Create a class implementing SequenceScoringAlgorithm
 - define getName()
 - define score(...)
 - define higherScoreIsBetter()
Add the algorithm to the list in DNASearchDemo

Here are the implementation and test classes:
DNASearchDemo.java
DNASequence.java
MatchResult.java
SequenceMatcher.java
SequenceMatcherTest.java
SequenceScoringAlgorithm.java
ExamplePositionMatchAlgorithm.java
AlgorithmComparisonManager.java

How shall we compare algorithms?
There are really two different comparisons we want to think about:
- How well an algorithm measures biological/string similarity
- How efficiently the algorithm runs
