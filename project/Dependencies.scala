import sbt._

object Dependencies {
  def circe = {
    val version = "0.8.0"

    Seq(
      "io.circe" %% "circe-core" % version,
      "io.circe" %% "circe-generic" % version,
      "io.circe" %% "circe-generic-extras" % version,
      "io.circe" %% "circe-parser" % version,
      "io.circe" %% "circe-literal" % version,
      "io.circe" %% "circe-java8" % version
    )
  }

  def http4s = {
    val version = "0.17.0-RC2"

    Seq(
      "org.http4s" %% "http4s-dsl" % version,
      "org.http4s" %% "http4s-blaze-server" % version,
      "org.http4s" %% "http4s-blaze-client" % version,
      "org.http4s" %% "http4s-circe" % version
    )
  }

  def logback = {
    val version = "1.2.3"

    Seq("ch.qos.logback" % "logback-classic" % version)
  }

  def log4s = {
    val version = "1.3.5"

    Seq("org.log4s" %% "log4s" % version)
  }

  def scalaTest = {
    val version = "3.0.1"

    Seq("org.scalatest" %% "scalatest" % version % Test)
  }

  def all = circe ++ http4s ++ logback ++ log4s ++ scalaTest
}

