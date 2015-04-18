(ns dub.core
  (:require [dub.model.log-file      :refer :all]
            [dub.property-file       :refer (organize-alphabetically merge-properties replace-properties)]
            [dub.model.log-file      :refer (persist-log-records)]
            [dub.datasource          :refer (create-db-spec migrate-db)]
            [dub.repository.database :refer (empty-tables)])
  (:gen-class))

(migrate-db)
