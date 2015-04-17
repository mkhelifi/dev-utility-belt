(ns jub.core
  (:require [jub.model.log-file      :refer :all]
            [jub.property-file       :refer (organize-alphabetically merge-properties replace-properties)]
            [jub.model.log-file      :refer (persist-log-records)]
            [jub.datasource          :refer (create-db-spec migrate-db)]
            [jub.repository.database :refer (empty-tables)])
  (:gen-class))

(migrate-db)
