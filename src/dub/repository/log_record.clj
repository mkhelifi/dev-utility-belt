(ns dub.repository.log-record
  (:require [clojure.java.jdbc :as    jdbc]
            [honeysql.core     :as    sql]
            [honeysql.helpers  :refer :all]
            [dub.datasource    :refer (db-spec unique-id)]))

(defn create [record]
    (jdbc/insert! db-spec :log_record (assoc record :id (unique-id))))

(defn find-all []
  (let [query {:select [:*] :from [:log-record]}]
      (jdbc/query db-spec (sql/format query))))
