(ns javaee-utility-belt.property-file
  (:require [clojure.string :refer (blank? join split split-lines)]))

(defn- load-properties-file [file]
  "Opens a property file and returns a lazy vector where each element is a line of the file."
  (filterv (fn [line] (not= 0 (.indexOf line "#"))) ; Ignore comments
  (filterv (fn [line] (not (blank? line)))          ; Ignore blank lines.
    (split-lines                                    ; split the content in lines.
      (slurp file)))))                              ; Read the content of the file

(defn- lines-to-properties [lines]
  "Transforms lines into properties. It takes each line and separates the key from the value.
  It returns a sequence of vectors where each vector contains a property entry."
  (map #(split % #"=" 2) lines))

(defn- properties-to-lines [properties]
  "Transforms properties into lines. It does the inverse of lines-to-properties."
  (map #(str (first (first %)) "=" (last (first %))) properties))

(defn- properties-to-map [properties]
  "Creates a map from the list of properties."
  (apply merge (map #(assoc {} (keyword (first %)) (last %))
     properties)))

(defn- map-to-properties [the-map]
  "Creates a list of properties from a map. It does the inverse of properties-to-map"
  (let [properties {}]
    (into [] (map #(assoc properties (name (key %)) (val %)) the-map))))

(defn- load-properties-map [file]
  "Loads a property file and returns its content as a map."
  (properties-to-map (lines-to-properties (load-properties-file file))))

(defn- save-properties-file [filename properties]
  "Writes down to a text file a collection of properties."
  (spit filename (str "# List in alphabetic order\n" (join "\n" properties))))

(defn- group-by-alphabet [properties]
  "Groups properties alphabetically for better manageability."
  (let [sorted-properties (sort properties)]
    (loop [letter "" sorted-properties sorted-properties grouped-properties []]
      (if (= (count sorted-properties) 0)
        grouped-properties
        (if (= letter (subs (first sorted-properties) 0 1))
          (recur letter
                 (rest sorted-properties)
                 (conj grouped-properties (first sorted-properties)))
          (recur (subs (first sorted-properties) 0 1)
                 sorted-properties
                 (conj grouped-properties (str "\n# " (subs (first sorted-properties) 0 1)))))))))

(defn- replace-keys-values [file another-file]
  "It takes two files. The first file is the one that will have its values replaced by the values in the second file."
  (let [original (load-properties-map file)
        translated (load-properties-map another-file)]
    (loop [original-keys (keys original) merged {}]
      (if (zero? (count original-keys))
        merged
        (recur (rest original-keys)
               (let [k (first original-keys)]
                 (if (contains? translated k)
                   (assoc merged k (get translated k))
                   (assoc merged k (get original k)))))))))

(defn- merge-property-files [file another-file]
  "Merges the properties of two property files and returns a collection of the merged result."
  (loop [props (load-properties-file file)
         other-props (load-properties-file another-file)
         merged-properties []]
    (if (empty? props)
      (concat merged-properties other-props)
      (if (empty? other-props)
        (concat merged-properties props)
        (let [c (compare (first props) (first other-props))]
          (if (zero? c)
            (recur (rest props)
                   (rest other-props)
                   (conj merged-properties (first props)))
            (if (< c 0)
              (recur (rest props)
                     other-props
                     (conj merged-properties (first props)))
              (recur props
                     (rest other-props)
                     (conj merged-properties (first other-props))))))))))

(defn organize-alphabetically [file]
  (save-properties-file file (group-by-alphabet (load-properties-file file))))

(defn merge-properties [original-file to-merge-file new-file]
  (save-properties-file new-file
    (group-by-alphabet (merge-property-files original-file to-merge-file))))

(defn replace-properties [old-values-file new-values-file new-file]
   "Replaces the values of the first file by the equivalent values of the second file"
  (save-properties-file new-file
    (group-by-alphabet
      (properties-to-lines
        (map-to-properties
          (replace-keys-values old-values-file new-values-file))))))
