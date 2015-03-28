(ns jub.core
  (:require [jub.model.property-file :refer (organize-alphabetically merge-properties replace-properties)]
            [jub.model.log-file      :refer (persist-log-records)]
            [jub.datasource          :refer (migrate-db)])
  (:gen-class))

(migrate-db)
