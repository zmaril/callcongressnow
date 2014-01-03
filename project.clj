(defproject callcongressnow "1.0.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://callcongressnow.herokuapp.com"
  :license {:name "FIXME: choose"
            :url "http://example.com/FIXME"}
  :uberjar-name "callcongressnow-standalone.jar"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/tools.reader "0.8.2"]

                 ;;CLJ
                 [compojure "1.1.6"]
                 [ring/ring-jetty-adapter "1.1.0"]
                 [ring/ring-devel "1.1.0"]
                 [cheshire "5.2.0"]
                 [clj-http "0.7.8" :exclusions [org.clojure/clojure]]
                 [com.twilio.sdk/twilio-java-sdk "3.3.15"]
                 [environ "0.4.0"]
                 ;; CLJS
                 [org.clojure/clojurescript "0.0-2127"]
                 [org.clojure/core.async "0.1.267.0-0d7780-alpha"]
                 [zmaril/om "0.1.0-SNAPSHOT"]]
  :min-lein-version "2.1.2"
  :source-paths ["src/clj"]
  :cljsbuild 
  {:builds [{:id "dev"
             :source-paths ["src/cljs"]
             :compiler {
                        :output-to "resources/public/js/app.js"
                        :output-dir "resources/public/js/out"
                        :optimizations :advanced
                        :externs ["resources/public/js/react.js"
                                  "resources/public/js/twilio.js"]
                        :closure-warnings
                        {:non-standard-jsdoc :off
                         :externs-validation :off}}}]}
  :plugins [[environ/environ.lein "0.2.1"]
            [lein-cljsbuild "1.0.1"]
            [lein-ring "0.8.7"]])
