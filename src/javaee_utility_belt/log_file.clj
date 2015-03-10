(ns javaee-utility-belt.log-file
  (:require [clojure.string :refer (blank? join split split-lines)]))

(loop [lines (split-lines (slurp "test/logs/server.log"))
       open false
       record nil
       records []]
  (if (empty? lines)
    records
    (let [line (first lines)]
      (let [level (re-find #"ERROR|INFO|SEVERE|WARNING" line)]
        (case level
          "ERROR"   (recur (rest lines) true  line records)
          "INFO"    (recur (rest lines) false nil  (conj records line))
          "SEVERE"  (recur (rest lines) true  line records)
          "WARNING" (recur (rest lines) true  line records)
          (recur (rest lines) true record records))))))



(map #(re-find #"ERROR|INFO|SEVERE|WARNING" %) (split-lines (slurp "test/logs/server.log")))
