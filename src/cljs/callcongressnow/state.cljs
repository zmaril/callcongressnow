(ns callcongressnow.state
  (:require-macros [cljs.core.async.macros :refer [go alt!]]
                   [secretary.macros :refer [defroute]])
  (:require [om.core :as om :include-macros true]
            [om.dom  :as dom :include-macros true]
            [cljs.core.async :refer [put! <! >! <!! chan timeout]]
            [callcongressnow.utils :refer [jsonp]]
            [secretary.core :as secretary])
  (:import [goog Uri]))

(def app-state
  (atom {:senators []
         :representatives []
         :calling? false
         :token nil}))

(defn update-legislators [app lst]
  (let [grouped (group-by :title lst)]
    (om/update! app [:senators]  (fn [_] (grouped "Sen")))
    (om/update! app [:representatives]  (fn [_] (grouped "Rep")))))

(defn location-please []
  (let [out (chan)]
    (-> js/navigator
        (.-geolocation)
        (.getCurrentPosition 
         (fn [o]
           (let [c (.-coords o)]
             (put! out {"latitude"  (.-latitude c) 
                        "longitude" (.-longitude c)})))))
    out))

(defn find-local [app _]
  (go (let [coords  (<!  (location-please))
            results (<! (jsonp "/locate" coords))
            results (js->clj results :keywordize-keys true)]
        (update-legislators app results)))
  false)

(defn find-query [app query]
  (go (let [results (<! (jsonp "/query" {"query" query}))
            results (js->clj results :keywordize-keys true)]
        (update-legislators app results)))
  false)

(defn find-all [app _]
  (go (let [results (<! (jsonp "/all" nil))
            results (js->clj results :keywordize-keys true)]
        (update-legislators app results)))
  false)

