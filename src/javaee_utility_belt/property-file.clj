(ns javaee-utility-belt.property-file
  (:require [clojure.string :refer :all]))

(defn load-properties [file]
  (filterv (fn [line] (not (= 0 (.indexOf line "#"))))
  (filterv (fn [line] (not (blank? line)))
    (split-lines
      (slurp file)))))

(defn merge-properties [file1 file2]
  (let [props1 (load-properties file1) props2 (load-properties file2) size-props1 (count props1) size-props2 (count props2)]
    (loop [i 0 j 0 merged-properties []]
      (if (if (>= i j) (= i size-props1) (= j size-props2))
        merged-properties
        (let [c (compare (nth props1 i) (nth props2 j))]
        (if (= c 0)
          (recur (if (< i size-props1) (inc i) i)
                 (if (< j size-props2) (inc j) j)
                 (conj merged-properties (nth props1 i)))
          (if (< c 0)
            (recur (if (< i size-props1) (inc i) i) j
                   (conj merged-properties (nth props1 i)))
            (recur i (if (< j size-props2) (inc j) j)
                   (conj merged-properties (nth props2 j))))))))))
