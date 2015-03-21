(ns jub.core
  (:require [jub.log-file      :refer :all]
            [jub.property-file :refer (organize-alphabetically merge-properties replace-properties)]
            [jub.datasource    :refer (migrate-mysql-db)])
  (:gen-class))

(migrate-mysql-db)
