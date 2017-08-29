lazy val `autoscout-solution` = project.in(file("."))
  .settings(
    name := "autoscout-solution",
    organization := "com.megri",
    scalaVersion := "2.12.3",
    libraryDependencies ++= Dependencies.all
  )
