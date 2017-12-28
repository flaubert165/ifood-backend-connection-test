name := "ifood-backend-connection-test"
 
version := "1.0" 
      
lazy val `ifood-backend-connection-test` = (project in file(".")).enablePlugins(PlayJava)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
scalaVersion := "2.11.11"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  filters,
  "org.mybatis" % "mybatis" % "3.4.1",
  "org.mybatis" % "mybatis-guice" % "3.8",
  "org.mybatis.caches" % "mybatis-ignite" % "1.0.6",
  "com.google.inject.extensions" % "guice-multibindings" % "4.0",
  "org.mindrot" % "jbcrypt" % "0.3m",
  "mysql" % "mysql-connector-java" % "6.0.6",
  "org.eclipse.paho" % "org.eclipse.paho.client.mqttv3" % "1.1.0",
  "io.reactivex.rxjava2" % "rxjava" % "2.1.0",
  "com.baidu.unbiz" % "fluent-validator" % "1.0.5" exclude("org.slf4j", "slf4j-log4j12"),
  "io.jsonwebtoken" % "jjwt" % "0.5"
)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

      