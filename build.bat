@call sbt ;compile;assembly;exit

@python copyFile.py %~dp0target\scala-2.11\MorseGUI.jar
