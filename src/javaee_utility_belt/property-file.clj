(ns javaee-utility-belt.property-file
  (:require [clojure.string :refer :all]))

(defn load-properties [file]
  "Load the file containing properties, remove blank lines and comments and return a list of properties"
  (filterv (fn [line] (not (= 0 (.indexOf line "#"))))
  (filterv (fn [line] (not (blank? line)))
    (split-lines
      (slurp file)))))

(defn save-properties [filename properties]
  (spit filename (str "# List in alphabetic order\n" (join "\n" properties))))

(defn merge-properties [file1 file2]
  (loop [props1 (load-properties file1) props2 (load-properties file2) merged-properties []]
    (if (empty? props1)
      (concat merged-properties props2)
      (if (empty? props2)
        (concat merged-properties props1)
        (let [c (compare (first props1) (first props2))]
          (if (= c 0)
            (recur (rest props1) (rest props2) (conj merged-properties (first props1)))
            (if (< c 0)
              (recur (rest props1) props2 (conj merged-properties (first props1)))
              (recur props1 (rest props2) (conj merged-properties (first props2))))))))))

(defn group-by-alphabet [properties]
  (let [sorted-properties (sort properties)]
    (loop [letter "" sorted-properties sorted-properties grouped-properties []]
      (if (= (count sorted-properties) 0)
        grouped-properties
        (if (= letter (subs (first sorted-properties) 0 1))
          (recur letter (rest sorted-properties) (conj grouped-properties (first sorted-properties)))
          (recur (subs (first sorted-properties) 0 1) sorted-properties (conj grouped-properties (str "\n# " (subs (first sorted-properties) 0 1)))))))))


