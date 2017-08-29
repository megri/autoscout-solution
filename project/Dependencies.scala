import sbt._

object Dependencies {
  def circe = {
    val version = "0.8.0"

    Seq(
      "io.circe" %% "circe-core" % version,
      "io.circe" %% "circe-generic" % version,
      "io.circe" %% "circe-parser" % version,
      "io.circe" %% "circe-literal" % version
    )
  }

  def http4s = {
    val version = "0.17.0-RC2"

    Seq(
      "org.http4s" %% "http4s-dsl" % version,
      "org.http4s" %% "http4s-blaze-server" % version,
      "org.http4s" %% "http4s-blaze-client" % version
    )
  }

  def scalaTest = {
    val version = "3.0.1"

    Seq("org.scalatest" %% "scalatest" % version % Test)
  }

  def all = circe ++ http4s ++ scalaTest
}

