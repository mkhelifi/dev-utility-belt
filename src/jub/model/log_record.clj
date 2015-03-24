(ns jub.model.log-record
  (:require [clojure.java.jdbc :as    jdbc]
            [honeysql.core     :as    sql]
            [honeysql.helpers  :refer :all]
            [jub.datasource    :refer (mysql-db-spec)]))

(defn create [record]
    (jdbc/insert! mysql-db-spec :log_record
                    record))

(create {:id "KJSUSIKQJWUSYBCNHSUIMASJSHDYEU74"
                     :filename "server.log"
                     :instant  "2015-03-24"
                     :level    "INFO"
                     :name     "Test"
                     :thread   "Threads"
                     :message  "Message"})

(defn find-all []
  (let [query {:select [:*] :from [:log-record]}]
      (jdbc/query mysql-db-spec (sql/format query))))

(find-all)
