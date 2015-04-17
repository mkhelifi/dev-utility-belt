(ns jub.repository.metadata
  (:require [clojure.java.jdbc :as    jdbc]
            [jub.datasource    :refer (db-spec)]))

(defn all-tables
  [db-spec]
    (map #(% :table_name)
         (jdbc/with-db-metadata [md db-spec]
          (jdbc/metadata-result (.getTables md nil nil nil (into-array ["TABLE"]))))))
