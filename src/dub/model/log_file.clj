(ns dub.model.log-file
  (:require [clojure.string            :refer (blank? join split split-lines)]
            [dub.repository.log-record :as rep]
            [dub.utils                 :as utils]))

(def log-levels #"DEBUG|ERROR|INFO|SEVERE|WARNING|WARN")

(defn enclosed-within [sentence closure]
  "Returns the content inside of parentheses, brackets or braces found within a sentence."
  (let [openning-closure (case closure :parentheses \( :brackets \[ :curly-braces \{)
        closing-closure (case closure  :parentheses \) :brackets \] :curly-braces \})]
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
  "Analyses and decomposes log record's parts into a map."
  (let [mapped-record {}]
    (assoc mapped-record :message   (.substring record (+ (.indexOf record ")") 2))
                         :thread    (enclosed-within record :parentheses)
                         :name      (enclosed-within record :brackets)
                         :level     (re-find log-levels record)
                         :instant   (.parse (java.text.SimpleDateFormat. "yyyy-MM-dd HH:mm:ss,SSS")
                                            (re-find #"\d\d\d\d-\d\d-\d\d \d\d:\d\d:\d\d,\d\d\d" record)))))

(defn conj-record [records record]
  "Adds a record in a collection of records but rejecting nil values."
  (if (nil? record)
    records
    (conj records (decompose-log-record record))))

(defn log-records [log-file]
  "Takes a log file and returns a structured representation of it in a vector of maps."
  (loop [lines (split-lines (slurp log-file))
         record nil
         records []]
    (if (empty? lines)
      (conj-record records record)
      (let [line (first lines)]
        (let [level (re-find log-levels line)]
          (if (nil? level)
            (recur (rest lines)
                   (str record "\n" line)
                   records)
            (recur (rest lines)
                   line
                   (conj-record records record))))))))

(defn scan-log-directory [path]
  "Scans the log directory and returns a sequence of files"
  (let [directory (java.io.File. path)
        files     (.listFiles directory)]
    (map #(.getAbsolutePath %) (filter #(.isFile %) files))))

(defn persist-log-records [log-file]
  (for [record (map #(assoc % :filename (utils/filename-from-path log-file))
                    (log-records log-file))]
    (rep/create record)))

(defn filter-by-level [log-file level]
  "Goes through the log records and returns the ones with the informed log level."
  (filter #(= level (get % :level)) (log-records log-file)))

(defn print-feedback [text]
  "Formats a text to be beautifully printed by the repl."
  (loop [out (split text #"\n")]
    (if (empty? out)
      "--------------"
      (let [to-print (first out)]
        (println to-print)
        (recur (rest out))))))

(defn filter-by-level [log-file level]
  "Goes through the log records and returns the ones with the informed log level."
  (filter #(= level (get % :level)) (log-records log-file)))
