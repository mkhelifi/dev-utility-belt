(ns jub.datasource
  (:require [clojure.java.jdbc    :as jdbc]
            [joplin.core          :as joplin]
            [joplin.jdbc.database])
  (:import com.zaxxer.hikari.HikariDataSource))

(def mysql-db-spec {:classname   "com.mysql.jdbc.Driver"
                    :subprotocol "mysql"
                    :subname     "//localhost:3306/jub"
                    :user        "jub_user"
                    :password    "senhas"})

(def joplin-target {:db {:type :sql
                         :url (str "jdbc:mysql:" (:subname mysql-db-spec)
                                   "?user="      (:user mysql-db-spec)
                                   "&password="  (:password mysql-db-spec))}
                    :migrator "resources/migrators/sql"})

(defn migrate-mysql-db []
  (joplin/migrate-db joplin-target))

(defn connection-pool []
  (let [hikari (doto (HikariDataSource.)
                 (.setJdbcUrl (str "jdbc:" (:subprotocol mysql-db-spec) ":" (:subname mysql-db-spec)))
                 (.setUsername (:user mysql-db-spec))
                 (.setPassword (:password mysql-db-spec)))]
    {:datasource hikari}))

(def pooled-db (delay (connection-pool)))

(defn db-connection [] @pooled-db)
