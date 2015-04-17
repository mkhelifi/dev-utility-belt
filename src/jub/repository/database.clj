(ns jub.repository.database
  (:require [clojure.java.jdbc       :as    jdbc]
            [honeysql.core           :as    sql]
            [honeysql.helpers        :refer :all]
            [jub.datasource          :refer (db-spec)]
            [jub.repository.metadata :refer (all-tables)]))

(defn count-records [db-spec table]
  (jdbc/query db-spec (-> (select :%count.*) (from (keyword table)) sql/format)))

(defn empty-tables [db-spec]
  (map #(count-records db-spec %) (all-tables db-spec)))
