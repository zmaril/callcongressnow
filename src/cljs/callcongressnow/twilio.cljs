(ns callcongressnow.twilio
  (:require [callcongressnow.state :refer [app-state]]
            [om.core :as om :include-macros true]
            [om.dom  :as dom :include-macros true]))

(defn call-legislator 
  [bioguide_id]
  (when-not (:calling? @app-state)
    (js/Twilio.Device.connect #js {"bioguide_id" bioguide_id}) 
    (swap! app-state update-in [:calling?] (fn [_] true))))

(defn hangup
  []
  (when (:calling? @app-state)
    (js/Twilio.Device.disconnectAll)
    (swap! app-state update-in [:calling?] (fn [_] false))))

(defn call-ref   [id] (str "call_" id))
(defn hangup-ref [id] (str "hangup_" id))

(defn call-button [{:keys [phone bioguide_id] :as c}]
  (reify
    om/IRender 
    (render [_ owner]
      (dom/span 
       nil
       (dom/a 
        #js {:onClick 
             (fn [_] 
               (call-legislator bioguide_id)
               (-> (om/get-node owner (hangup-ref bioguide_id))
                   .-style
                   (.setProperty "display" "" "important"))
               (-> (om/get-node owner (call-ref bioguide_id))
                   .-style
                   (.setProperty "display" "none" "important")))
             :className "btn btn-success"
             :ref (call-ref bioguide_id)}
        (str "Call " phone " now"))
       (dom/a #js {:onClick              
                   (fn [_] 
                     (hangup)
                     (-> (om/get-node owner (call-ref bioguide_id))
                         .-style
                         (.setProperty "display" "" "important"))
                     (-> (om/get-node owner (hangup-ref bioguide_id))
                         .-style
                         (.setProperty "display" "none" "important")))
                   :className "btn btn-danger"
                   :style #js {:display "none"}
                   :ref (hangup-ref bioguide_id)}
              (str "Hang up"))))))
