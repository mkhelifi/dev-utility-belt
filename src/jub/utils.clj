(ns jub.utils)

(defn filename-from-path [path]
  "Identifies and returns a file name present in a path."
  (let [point-pos (.lastIndexOf path ".")
    bar-pos (.lastIndexOf path "/")]
    (when-not (neg? point-pos) (.substring path (inc bar-pos)))))
