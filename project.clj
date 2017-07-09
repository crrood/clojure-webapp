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
  				[hiccup "1.0.5"]])