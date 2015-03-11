(ns jub.log-file
  (:require [clojure.string :refer (blank? join split split-lines)]))

(defn- conj-record [records record]
  (if (nil? record) records (conj records record)))



(defn- decompose-log-record [record]
  (let [mapped-record {}]
    (assoc mapped-record :message (.substring record (+ (.indexOf record ")") 2))
                         :thread ""
                         :name ""
                         :level (re-find #"DEBUG|ERROR|INFO|SEVERE|WARNING" record)
                         :timestamp (re-find #"\d\d:\d\d:\d\d,\d\d\d" record))))

(defn- log-records [log-file]
  (loop [lines (split-lines (slurp log-file))
         record nil
         records []]
    (if (empty? lines)
      (conj-record records record)
      (let [line (first lines)]
        (let [level (re-find #"DEBUG|ERROR|INFO|SEVERE|WARNING" line)]
          (if (nil? level)
            (recur (rest lines)
                   (str record "\n" line)
                   records)
            (recur (rest lines)
                   line
                   (conj-record records record))))))))

(decompose-log-record (nth (log-records "test/logs/server.log") 12))
