(ns jub.web.routes.home
  (:require [compojure.core :refer :all]
            [jub.web.views.layout :as layout]))

(defn home []
  (layout/common [:h1 "JavaEE Utility Belt"]))

(defroutes home-routes
  (GET "/" [] (home)))
