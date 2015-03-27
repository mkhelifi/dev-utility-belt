(ns jub.core
  (:require [jub.log-file       :refer :all]
            [jub.property-file  :refer (organize-alphabetically merge-properties replace-properties)]
            [jub.model.log-file :refer (persist-log-records)]
            [jub.datasource     :refer (migrate-db)])
  (:gen-class))

(migrate-db)
