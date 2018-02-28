addSbtPlugin("io.spray" % "sbt-revolver" % "0.9.1")

resolvers += "Flyway" at "https://davidmweber.github.io/flyway-sbt.repo"

// Database migration
addSbtPlugin("org.flywaydb" % "flyway-sbt" % "4.2.0")

libraryDependencies += "org.postgresql" % "postgresql" % "9.4.1208"
