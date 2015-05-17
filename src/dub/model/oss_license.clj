(ns dub.model.oss-license
  (:require [clojure.string            :refer (blank? join split split-lines)]
            [dub.utils                 :as utils]))

(defn project-files [project-path]
  "Returns a sequence of absolute paths to all files in the project."
  (let [files (.listFiles (java.io.File. project-path))]
    (flatten 
      (filter #(not (.contains % "/."))
              (map #(if (and (.isDirectory %) (not (.isHidden %)))
                      (project-files (.getAbsolutePath %))
                      (.getAbsolutePath %))
                   files)))))

(defn filter-extensions [files extensions]
  "Takes a sequence of files and return only the ones with the informed extensions."
  extensions)

(defn append-license [license files]
  "Takes each file from the sequence and adds the license to the top of the file."
  license)

(defn detach-license [files]
  "Takes each file from the sequence and detach the license from the top of the file."
  files)

(defn set-license [license project-path & extensions]
  "Define the license of an open source software. It adds the main license file
   to the root of the project and modify the header of all source files adding
   the respective license short note."
  (append-license license
                  (filter-extensions (project-files project-path)
                                     extensions)))

(defn remove-license [project-path & extensions]
  "Remove the license of an open source software. Useful to replace the current license by
  another license or fixing something that went wrong."
  (detach-license (filter-extensions (project-files project-path)
                                     extensions)))

(set-license :apache-v2 "/home/clojure/project/test" :java :properties :html)
