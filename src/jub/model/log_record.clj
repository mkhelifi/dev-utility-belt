(ns jub.model.log-record
  (:require [clojure.java.jdbc :as jdbc]
            [jub.datasource :refer (db-connection) :as datasource]))

(defn insert-record []
  (jdbc/with-db-connection (datasource/db-connection) ))