(ns jub.datasource
  (:require [clojure.java.jdbc    :as jdbc]
            [joplin.core          :as joplin]
            [joplin.jdbc.database])
  (:import com.zaxxer.hikari.HikariDataSource))

(def db-spec {:classname   "com.mysql.jdbc.Driver"
              :subprotocol "mysql"
              :subname     "//localhost:3306/jub"
              :user        "jub_user"
              :password    "senhas"})

(def joplin-target {:db {:type :sql
                         :url (str "jdbc:mysql:" (:subname  db-spec)
                                   "?user="      (:user     db-spec)
                                   "&password="  (:password db-spec))}
                    :migrator "resources/migrators/sql"})

(defn migrate-db []
  (joplin/migrate-db joplin-target))

(defn connection-pool []
  (let [hikari (doto (HikariDataSource.)
                 (.setJdbcUrl (str "jdbc:" (:subprotocol db-spec) ":" (:subname db-spec)))
                 (.setUsername (:user db-spec))
                 (.setPassword (:password db-spec)))]
    {:datasource hikari}))

(def pooled-db (delay (connection-pool)))

(defn db-connection [] @pooled-db)
