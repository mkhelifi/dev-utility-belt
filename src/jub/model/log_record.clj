(ns jub.model.log-record
  (:require [clojure.java.jdbc :as    jdbc]
            [honeysql.core     :as    sql]
            [honeysql.helpers  :refer :all]
            [jub.datasource    :refer (db-connection)]))

(defn insert-record []
  (jdbc/with-db-connection
    (datasource/db-connection)
    (insert-into)))

(defn find-all []
  (let [sql {:select :*
             :from   :log_record}]))

(defn find-by-interval [begin end]
  (let [sql {:select :*
             :from   :log_record
             :where  [:and
                      [:>= :l.instant begin]
                      [:<= :l.instant end]]}]))
