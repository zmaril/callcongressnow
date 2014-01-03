(ns callcongressnow.profile
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [callcongressnow.legislators :refer [legislator-box]]
            [cljs.core.async :refer [<!]]
            [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]           
            [callcongressnow.app :refer 
             [navbar]]
            [callcongressnow.twilio :refer 
             [call-button]]
            [callcongressnow.utils :refer [jsonp apikey sunlighturl]]))

(enable-console-print!)

(def profile-state (atom {:person nil}))

(defn get-id []
  (->> js/window 
      .-location
      .-pathname
      (drop 9 )
      (apply str)))

(defn find-by-id [app id]
  (go     
   (-> (jsonp sunlighturl
        {"bioguide_id" id
         "apikey" apikey})
       <!
       (js->clj :keywordize-keys true)
       :results
       first
       (as-> x (om/update! app [:person] (fn [_] x))))))

(defn- br [] (dom/br nil))
(defn pluralize [i]
  (condp = i
    1 "1st"
    2 "2nd"
    3 "3rd"
    (str i "th")))

(defn capit [s]
  (-> s 
      (.charCodeAt 0)
      (- 32)
      (as-> x (.fromCharCode js/String x))
      (str (apply str (rest s)))))

(defn profile [app {:keys [id]}]
  (reify 
    om/IWillMount
    (will-mount [_ _] (find-by-id app id))
    om/IRender
    (render [this owner]
      (let [{:keys [title first_name last_name
                    phone contact_form bioguide_id
                    website office
                    district
                    state_name
                    state_rank  office birthday
                    twitter_id 
                    ]} (:person app)]
        (dom/div #js {:className "row"}
                 (dom/div #js {:className "col-lg-3"}
                          (dom/img 
                           #js {:src (str "/img/profiles/" id ".jpg")}))

                 (dom/div #js {:className "col-lg-6"}
                          (dom/h2 
                           nil
                           (str first_name " " last_name))
                          (br)
                          (dom/strong 
                           nil
                           (if (= title "Sen")
                             (str (capit state_rank) 
                                  " Senator of " state_name)
                             (str "Representative for the " 
                                  (pluralize district)
                                  " district of " state_name))) (br) (br)

                          (dom/strong nil "Physical Address") (br)
                          office (br)
                          "Washington, DC 20510" (br) (br)
                          
                          (dom/strong nil "Phone Number") (br)
                          (om/build 
                           call-button 
                           {:phone phone :bioguide_id bioguide_id})(br)
                          (br)

                          (dom/strong nil "Internet Addresses") (br)
                          (dom/a #js {:href website} "Website") (br)
                          (dom/a #js {:href contact_form} 
                                 "Contact Form") (br)
                          (dom/a 
                           #js {:href 
                                (str "http://www.twitter.com/" 
                                     twitter_id)} 
                           "Twitter") (br) (br)))))))

(defn profile-app [app]
  (reify 
    om/IRender
    (render [_ owner]
      (dom/div #js {:id "content"}
               (om/build navbar app)
               (om/build profile app {:opts {:id (get-id)}})))))

(when-let [root (.getElementById js/document "profilecontainer")]
  (om/root profile-state profile-app root))


