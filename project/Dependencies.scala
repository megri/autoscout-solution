import sbt._

object Dependencies {
  def scalaTestVersion = "3.0.1"

  def scalaTest = "org.scalatest" %% "scalatest" % scalaTestVersion % Test
}

