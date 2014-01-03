(ns callcongressnow.utils
  (:require [cljs.reader :as reader]
            [cljs.core.async :refer [put! chan]])
  (:import [goog.net Jsonp]
           [goog Uri]
           [goog.ui IdGenerator]))

(def apikey "0c5186d6a83149a5abdc4b29f76ae080")
(def sunlighturl
  "http://congress.api.sunlightfoundation.com/legislators" )

(defn jsonp [url params]
  (let [out (chan)
        jsonp (Jsonp. (Uri. url
                            ))
        ob (js-obj)]
    (doseq [[k v] params] (aset ob k v))
    (.send jsonp ob
           (fn [res] (put! out res))
           (fn [e]   (println "error") (println e)))
    out))
