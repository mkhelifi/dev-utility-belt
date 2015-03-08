(ns javaee-utility-belt.log-file
  (:require [clojure.string :refer (blank? join split split-lines)]))

(loop [lines (split-lines (slurp "test\\logs\\server.log"))
       open false
       records []]
  (if (empty? lines)
    records
    (let [level (re-find #"ERROR|INFO|SEVERE|WARNING" (first lines))]
      (case level
        "ERROR"   ()
        "INFO"    ()
        "SEVERE"  ()
        "WARNING" ()
        ()))))

(map #(re-find #"ERROR|INFO|SEVERE|WARNING" %) (split-lines (slurp "test\\logs\\server.log")))
