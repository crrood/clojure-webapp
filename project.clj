(defproject clojure-webapp "0.0.1"
  :description "A simple clojure webapp"
  :url "http://github.com/crrood/clojure-webapp"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                [org.clojure/clojurescript "1.9.229"]
        				[org.clojure/java.jdbc "0.6.1"]
        				[org.postgresql/postgresql "9.4-1201-jdbc41"]
        				[compojure "1.4.0"]
                [ring/ring-jetty-adapter "1.4.0"]
        				[ring/ring-defaults "0.3.0"]
        				[hiccup "1.0.5"]
                [org.omcljs/om "1.0.0-beta1"]]
  :main ^:skip-aot clojure-webapp.web
  :uberjar-name "clojure-webapp-standalone.jar"
  :plugins [[lein-ring "0.12.0"]
            [lein-figwheel "0.5.10"]]
  :ring {:handler clojure-webapp.web/application
         :init clojure-webapp.models.migration/migrate}
  :profiles {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                                  [ring/ring-mock "0.3.1"]
                                  [figwheel-sidecar "0.5.4-6"]]}
              :uberjar {:aot :all}}
  :cljsbuild {
        :builds [{:id "dev"
                  :figwheel true
                  :source-paths ["src/"]
                  :compiler {:main "clojure-webapp.web"
                            :optimizations :none
                            :asset-path "js"
                            :output-to "resources/public/js/main.js"
                            :output-dir "resources/public/js"
                            :verbose true}}]})