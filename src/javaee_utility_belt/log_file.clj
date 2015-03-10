(ns javaee-utility-belt.log-file
  (:require [clojure.string :refer (blank? join split split-lines)]))

(defn log-records [log-file]
  (loop [lines (split-lines (slurp log-file))
         record nil
         records []]
    (if (empty? lines)
      (if (nil? record) records (conj records record))
      (let [line (first lines)]
        (let [level (re-find #"DEBUG|ERROR|INFO|SEVERE|WARNING" line)]
          (case level
            "DEBUG"   (recur (rest lines) line                   (if (nil? record) records (conj records record)))
            "ERROR"   (recur (rest lines) line                   (if (nil? record) records (conj records record)))
            "INFO"    (recur (rest lines) line                   (if (nil? record) records (conj records record)))
            "SEVERE"  (recur (rest lines) line                   (if (nil? record) records (conj records record)))
            "WARNING" (recur (rest lines) line                   (if (nil? record) records (conj records record)))
                      (recur (rest lines) (str record "\n" line) records)))))))

(map #(re-find #"ERROR|INFO|SEVERE|WARNING" %) (split-lines (slurp "test/logs/server.log")))
