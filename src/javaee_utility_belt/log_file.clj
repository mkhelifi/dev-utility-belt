(ns javaee-utility-belt.log-file
  (:require [clojure.string :refer (blank? join split split-lines)]))

(defn- conj-record [records record]
  (if (nil? record) records (conj records record)))

(defn log-records [log-file]
  (loop [lines (split-lines (slurp log-file))
         record nil
         records []]
    (if (empty? lines)
      (conj-record records record)
      (let [line (first lines)]
        (let [level (re-find #"DEBUG|ERROR|INFO|SEVERE|WARNING" line)]
          (if (nil? level)
            (recur (rest lines) (str record "\n" line) records)
            (recur (rest lines) line                   (conj-record records record))))))))

(nth (log-records "test/logs/server.log") 13)
