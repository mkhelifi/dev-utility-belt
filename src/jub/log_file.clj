(ns jub.log-file
  (:require [clojure.string :refer (blank? join split split-lines)]))

(defn enclosed-within [sentence closure]
  (let [openning-closure (case closure :parentheses  \(
                                       :brackets     \[
                                       :curly-braces \{)
        closing-closure (case closure :parentheses   \)
                                       :brackets     \]
                                       :curly-braces \})]
    (let [i (.indexOf sentence (str openning-closure))]
      (if (< i 0)
        nil
        (loop [pos (inc i) parity 1 within ""]
          (if (or (= pos (.length sentence))
                  (= parity 0))
            within
            (let [pos-char (.charAt sentence pos)]
              (recur (inc pos)
                     (if (= pos-char openning-closure)
                       (inc parity)
                       (if (= pos-char closing-closure)
                         (dec parity)
                         parity))
                     (str within (if (and (= pos-char closing-closure)
                                          (= parity 1)) "" pos-char))))))))))

(defn decompose-log-record [record]
  (let [mapped-record {}]
    (assoc mapped-record :message   (.substring record (+ (.indexOf record ")") 2))
                         :thread    (enclosed-within record :parentheses)
                         :name      (enclosed-within record :brackets)
                         :level     (re-find #"DEBUG|ERROR|INFO|SEVERE|WARNING" record)
                         :timestamp (re-find #"\d\d:\d\d:\d\d,\d\d\d" record))))

(defn conj-record [records record]
  (if (nil? record)
    records
    (conj records (decompose-log-record record))))

(defn log-records [log-file]
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

