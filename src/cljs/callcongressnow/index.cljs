(ns callcongressnow.index
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [callcongressnow.legislators :refer [legislator-box]]
            [cljs.core.async :refer [<!]]
            [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [callcongressnow.state :refer 
             [app-state find-local find-all find-query router]]
            [callcongressnow.app :refer 
             [navbar]]
            [callcongressnow.utils :refer [jsonp]]))

(enable-console-print!)

(defn jumbotron [app opts]
  (om/component
   (dom/div #js {:id "jumbocontain" :className "row"}
            (dom/div 
             #js {:className "col-lg-12"}
             (dom/div 
              #js {:className "jumbotron"}
              (dom/p nil (dom/h1 nil         
                                 "Give Congress a piece of your mind."))
              (dom/p #js {:className "lead"} 
                     "Use the buttons or search bar to find legislators to call from your browser (for free!)"
                     )
              (dom/p nil 
                     (dom/a #js {:onClick (partial find-local app)
                                 :className "btn btn-lg btn-primary"}
                            "Find your legislators"
                            )
                     "  "
                     (dom/a #js {:onClick (partial find-all app)
                                 :className "btn btn-lg btn-primary"}
                            "View all legislators"))
              (dom/p nil
                     "Search by name: "
                     (dom/input #js {:type "text"                       
                                     :onChange #(find-query 
                                                 app 
                                                 (.-value (.-target %)))})))))))

(defn index-app [app]
  (reify     
    om/IRender
    (render [_ owner]
      (dom/div nil
               (om/build navbar app)
               (dom/div #js {:id "content"}
                        (dom/div #js {:ref "query"}
                                 (om/build jumbotron app)
                                 (om/build legislator-box app)))))))

(om/root app-state index-app
         (.getElementById js/document "container"))

(go (let [{:keys [token]} (js->clj (<! (jsonp "/token" nil)) 
                           :keywordize-keys true)]
      (js/Twilio.Device.setup token #js {:debug true})))


