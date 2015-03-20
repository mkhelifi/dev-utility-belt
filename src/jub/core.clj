(ns jub.core
  (:require [jub.log-file      :refer :all]
            [jub.property-file :refer (organize-alphabetically merge-properties replace-properties)]
            [jub.datasource    :refer (migrate-db)])
  (:gen-class))

(migrate-db)
