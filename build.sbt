lazy val commonSettings = Seq(
  version := "1.0",
  organization := "Eli",
  scalaVersion := "2.11.4"
)

lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    name                        := "MorseGUI",
    assemblyJarName in assembly := "MorseGUI.jar"
  )