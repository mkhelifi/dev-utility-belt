(ns jub.repository.log-record
  (:require [clojure.java.jdbc :as    jdbc]
            [honeysql.core     :as    sql]
            [honeysql.helpers  :refer :all]
            [jub.datasource    :refer (db-spec)]))

(defn create [record]
    (jdbc/insert! db-spec :log_record record))

(defn find-all []
  (let [query {:select [:*] :from [:log-record]}]
      (jdbc/query db-spec (sql/format query))))

(defn unique-id []
  (.replace (.toUpperCase (str (java.util.UUID/randomUUID))) "-" ""))

(create {:id (unique-id)
         :filename "server.log"
         :instant  "2015-03-24"
         :level    "INFO"
         :name     "Test"
         :thread   "Threads"
         :message  "Message"})

(count (find-all))
