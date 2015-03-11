(ns jub.log-file-test
  (:require [clojure.test :refer :all]
            [jub.log-file :refer :all]))

(deftest enclosed-within-test
  (testing "Checks whether the content of the most comprehensive closure is returned correctly,
            even when it has more closures inside."
    (is (= (enclosed-within "[or[and[xor]]] ()" :brackets) "or[and[xor]]"))))

(deftest log-records-test
  (testing "Checks if the function is returning the correct datastructures."
    (is (vector? (log-records "test/logs/server.log")))
    (is (map? (nth (log-records "test/logs/server.log") 11)))))

(run-all-tests)
