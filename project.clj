(defproject callcongressnow "1.0.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://callcongressnow.herokuapp.com"
  :license {:name "FIXME: choose"
            :url "http://example.com/FIXME"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/tools.reader "0.8.2"]

                 ;;CLJ
                 [compojure "1.1.6"]
                 [ring/ring-jetty-adapter "1.1.0"]
                 [ring/ring-devel "1.1.0"]
                 [ring-basic-authentication "1.0.1"]
                 [environ "0.2.1"]
                 [com.cemerick/drawbridge "0.0.6"]
                 [cheshire "5.2.0"]
                 [clj-http "0.7.8"]
                 [com.twilio.sdk/twilio-java-sdk "3.3.15"]

                 ;; CLJS
                 [org.clojure/clojurescript "0.0-2127"]
                 [org.clojure/core.async "0.1.267.0-0d7780-alpha"]
                 [secretary "0.4.0"]
                 [cljs-http "0.1.2"]]
  :min-lein-version "2.0.0"
  :plugins [[environ/environ.lein "0.2.1"]
            [lein-cljsbuild "1.0.0"]
            [lein-ring "0.8.7"]]
  :hooks [environ.leiningen.hooks]
  :profiles {:production {:env {:production true}}})
