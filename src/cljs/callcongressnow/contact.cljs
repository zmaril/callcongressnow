(ns callcongressnow.contact
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]            
            [call-congress-now.app :refer 
             [navbar footer]]
            [callcongressnow.state :refer 
             [app-state]]
            [callcongressnow.twilio :refer 
             [call-button]]))

(defn contact [_ _]
  (om/component
   (dom/div 
    #js {:id "content" :className "col-lg-6"}
    (dom/h2 nil 
            "Contact")
    (dom/p nil
           "\"Call Congress Now\" was made by Zack Maril over winter
           break (in lieu of relaxing and catching up on netflix). You
           can get in contact with him via "
           (dom/a #js {:href "http://twitter.com/ZackMaril"} "twitter")
           " or "
           (dom/a #js {:href "mailto:zack@zacharymaril.com"} 
                  "email. ")
           "He will try to reply as soon as he can to any and all of
           your complaints, compliments or concerns.")
    (dom/p nil)
    (dom/p nil
           "Zack will be graduating in Decemeber of 2014 and is
    looking for something interesting to do during this upcoming
    summer. He has a varitey of experience in all sorts of exciting
    fields, including factory work, house painting and consulting. A
    resume and references are available upon request. In particular,
    if you work at a place whose name rhymes with Funlight Soundation
    or RoRublica, please have someone get in touch.")
    (dom/p 
     nil 
     "You could also, if you were so inclined, call him right now:")
    (om/build call-button 
              {:phone "Zack" :bioguide_id "me"}))))

(defn contact-app [app]
  (reify 
    om/IRender
    (render [_ owner]
      (dom/div nil
               (om/build navbar app)
               (om/build contact  app)))))

(om/root app-state contact-app
         (.getElementById js/document "container"))


