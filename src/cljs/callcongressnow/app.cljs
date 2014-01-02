(ns callcongressnow.app
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [callcongressnow.legislators :refer [legislator-box]]
            [goog.events :as events]
            [cljs.core.async :refer [<!]]
            [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [callcongressnow.state :refer 
             [app-state find-local find-all find-query router]]
            [callcongressnow.utils :refer [jsonp]]))

(defn navbar [app opts]
  (reify 
    om/IRender
    (render [_ owner]
      (dom/div #js {:className "navbar navbar-default navbar-fixed-top"}
               (dom/div 
                #js {:className "container"}
                (dom/div 
                 #js {:className "navbar-header"}
                 (dom/a 
                  #js {:href "/" :className "navbar-brand" } 
                  "Call Congress Now"))
                (dom/div 
                 #js {:className "navbar-collapse collapse"}
                 (dom/ul 
                  #js {:className "nav navbar-nav"}
                  (dom/li 
                   nil 
                   (dom/a #js {:href "/about"} "About"))
                  (dom/li 
                   nil 
                   (dom/a #js {:href "/contact"} "Contact"))
                  (dom/li 
                   nil 
                   (dom/a #js 
                          {:href "https://github.com/zmaril/callcongressnow"} "Source")))))))))

(go (let [{:keys [token]} (js->clj (<! (jsonp "/token" nil)) 
                           :keywordize-keys true)]
      (js/Twilio.Device.setup token #js {:debug true})))


