(ns callcongressnow.utils
  (:require [cljs.reader :as reader]
            [cljs.core.async :refer [put! chan]])
  (:import [goog.net Jsonp]
           [goog Uri]
           [goog.ui IdGenerator]))

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
