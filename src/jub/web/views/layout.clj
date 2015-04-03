(ns jub.web.views.layout
  (:require [hiccup.page :refer [html5 include-css]]))

(defn common [& body]
  (html5
    [:head
     [:title "JavaEE Utility Belt"]
     (include-css "/css/screen.css")]
    [:body body]))
