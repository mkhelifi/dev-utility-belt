(ns jub.datasource
  (:require [clojure.java.jdbc    :as jdbc]
            [joplin.core          :as joplin]
            [joplin.jdbc.database])
  (:import com.zaxxer.hikari.HikariDataSource))

(defn create-db-spec [& {:keys [classname subprotocol subname user password]
                         :or {classname "com.mysql.jdbc.Driver" subprotocol "mysql" subname "//localhost:3306/jub" user "" password ""}}]
  {:classname   classname
   :subprotocol subprotocol
   :subname     subname
   :user        user
   :password    password})

(def db-spec (create-db-spec :user "jub_user" :password "senhas"))

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

(defn unique-id []
  (.replace (.toUpperCase (str (java.util.UUID/randomUUID))) "-" ""))
