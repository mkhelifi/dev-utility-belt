(ns jub.datasource
  (:require [clojure.java.jdbc :as jdbc]
            [joplin.core       :as joplin]))

(def mysql-db {:subprotocol "mysql"
               :subname     "//localhost:3306/jub"
               :user        "jub_user"
               :password    "senhas"})

(def joplin-target {:db {:type :jdbc :url (str "jdbc:mysql:" (get mysql-db :subname)
                                              "?user="      (get mysql-db :user)
                                              "&password="  (get mysql-db :password))}
                    :migrator "resources/migrators/sql"})

(defn migrate-db []
  (joplin/migrate-db joplin-target))
