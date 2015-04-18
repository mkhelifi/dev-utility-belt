(defproject dev-utility-belt "0.1.0-SNAPSHOT"
  :description "This project offers a set of functionalities to make the life of programmers easier."
  :url "https://github.com/EsmerilProgramming/dev-utility-belt"
  :license {:name "Apache License V2"
            :url "https://www.apache.org/licenses/LICENSE-2.0"}
  :dependencies [[org.clojure/clojure        "1.6.0" ] ; Latest stable vertion of Clojure.
                 [org.clojure/java.jdbc      "0.3.6" ] ; Clojure JDBC wrapper
                 [org.xerial/sqlite-jdbc     "3.7.2" ] ; SQLite JDBC Driver
                 [mysql/mysql-connector-java "5.1.25"] ; MySQL JDBC Driver
                 [honeysql                   "0.5.1" ] ; SQL as Clojure data structures
                 [joplin.core                "0.2.9" ] ; Database migration
                 [joplin.jdbc                "0.2.9" ] ; Database migration for relational dbs
                 [com.zaxxer/HikariCP        "2.3.5" ]]; Database connection pooling
  :main ^:skip-aot dub.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
