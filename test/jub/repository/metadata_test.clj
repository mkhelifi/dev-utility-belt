(ns jub.repository.metadata-test
  (:require [clojure.test :refer :all]
            [jub.repository.metadata :refer :all]))

(deftest all-tables-test
  (testing "Checks if the function is returning something from the default database configuration."
    (is (not (empty? (all-tables {:classname   "com.mysql.jdbc.Driver"
                                  :subprotocol "mysql"
                                  :subname     "//localhost:3306/jub"
                                  :user        "jub_user"
                                  :password    "senhas"}))))))
