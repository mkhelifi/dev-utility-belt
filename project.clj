(defproject javaee-utility-belt "0.1.0-SNAPSHOT"
  :description "This project aims to fullfil the gap to effective productivity on the development of Java EE projects."
  :url "https://github.com/htmfilho/javaee-utility-belt"
  :license {:name "Apache License V2"
            :url "https://www.apache.org/licenses/LICENSE-2.0"}
  :dependencies [[org.clojure/clojure        "1.6.0" ] ; Latest stable vertion of Clojure.
                 ; Database
                 [org.clojure/java.jdbc      "0.3.6" ] ; Clojure JDBC wrapper
                 [mysql/mysql-connector-java "5.1.25"] ; MySQL JDBC Driver
                 [honeysql                   "0.5.1" ] ; SQL as Clojure data structures
                 [joplin.core                "0.2.9" ] ; Database migration
                 [joplin.jdbc                "0.2.9" ] ; Database migration for relational dbs
                 [com.zaxxer/HikariCP        "2.3.5" ] ; Database connection pooling
                 ; Web
                 [compojure                  "1.1.6"]
                 [hiccup                     "1.0.5"]
                 [ring-server                "0.3.1"]]
  :plugins [[lein-ring "0.8.12"]]
  :ring {:handler library.handler/app
         :init    library.handler/init
         :destroy library.handler/destroy}
  :main ^:skip-aot jub.core
  ;:target-path "target/%s"
  :profiles {:uberjar {:aot :all}
             :production {:ring {:open-browser? false,
                                 :stacktraces? false,
                                 :auto-reload? false}}
             :dev {:dependencies [[ring-mock       "0.1.5"]
                                  [ring/ring-devel "1.3.1"]]}})
