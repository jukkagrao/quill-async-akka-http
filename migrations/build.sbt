// Database Migrations:
// run with "sbt flywayMigrate"
// http://flywaydb.org/getstarted/firststeps/sbt.html

libraryDependencies += "org.flywaydb" % "flyway-core" % "5.0.7"

flywayLocations := Seq("classpath:db/migration")

flywayUrl := Common.databaseUrl
flywayUser := Common.databaseUser
flywayPassword := Common.databasePassword