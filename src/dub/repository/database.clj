(ns dub.repository.database
  (:require [clojure.java.jdbc       :as    jdbc]
            [honeysql.core           :as    sql]
            [honeysql.helpers        :refer :all]
            [dub.datasource          :refer (db-spec)]
            [dub.repository.metadata :refer (all-tables)]))

(defn count-records [db-spec table]
  ((first (jdbc/query db-spec (-> (select :%count.*) (from (keyword table)) sql/format)))
   (keyword "count(*)")))

(defn empty-tables [db-spec]
  (let [tables     (all-tables db-spec)
        quantities (map #(count-records db-spec %) tables)]
    (filter #(not (nil? %))
            (map #(when (= %1 0) %2) quantities tables))))
