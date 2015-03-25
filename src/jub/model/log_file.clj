(ns jub.log-file
  (:require [clojure.string :refer (blank? join split split-lines)]))

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
                         :timestamp (re-find #"\d\d:\d\d:\d\d,\d\d\d" record))))

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

(defn all-log-records [log-files]
  "Takes a collection of log files and returns a structured representation of all records in a vector of maps."
  (reduce (fn [records log-file]
            (conj records (log-records log-file))) [] log-files))

(defn scan-log-directory [path]
  "Scans the log directory and returns a sequence of files"
  (let [directory (java.io.File. path)
        files     (.listFiles directory)]
    (map #(.getAbsolutePath %) (filter #(.isFile %) files))))

(count (scan-log-directory "test/logs"))

(defn filter-by-level [log-file level]
  "Goes through the log records and returns the ones with the informed log level."
  (filter #(= level (get % :level)) (log-records log-file)))

(defn print-feedback [text]
  (loop [out (split text #"\n")]
    (if (empty? out)
      ""
      (let [to-print (first out)]
        (println to-print)
        (recur (rest out))))))
