(defproject clojure-webapp "0.0.1"
  :description "A simple clojure webapp"
  :url "http://github.com/crrood/clojure-webapp"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
  				[org.clojure/java.jdbc "0.6.1"]
  				[org.postgresql/postgresql "9.4-1201-jdbc41"]
  				[ring/ring-jetty-adapter "1.4.0"]
  				[compojure "1.4.0"]
  				[ring/ring-defaults "0.3.0"]
  				[hiccup "1.0.5"]
  				[reagent "0.7.0"]]
  :main ^:skip-aot clojure-webapp.web
  :uberjar-name "clojure-webapp-standalone.jar"
  :plugins [[lein-ring "0.12.0"]]
  :ring {:handler clojure-webapp.web/application
         :init clojure-webapp.models.migration/migrate}
  :profiles {:dev {:dependencies [[javax.servlet/servlet-api "3.1.0"]
                                  [ring-mock "0.3.1"]]}
              :uberjar {:aot :all}})