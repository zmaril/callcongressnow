(ns callcongressnow.legislators
  (:require [om.core :as om :include-macros true]
            [om.dom  :as dom :include-macros true]
            [callcongressnow.twilio :refer 
             [call-button hangup]]))

(defn senator
  [{:keys [last_name first_name party state phone bioguide_id]} 
   opts]
  (om/component
   (dom/tr #js {:className "senator"}
           (dom/td nil (dom/a 
                        #js {:href (str "/profile/" bioguide_id )
                             :className "btn btn-success"}
                        (str first_name " " last_name)))
           (dom/td nil party)
           (dom/td nil state)
           (dom/td nil (om/build call-button 
                                 {:phone phone :bioguide_id bioguide_id})))))

(defn senator-list [app opts]
  (reify
    om/IRender
    (render [_ owner]
      (if (empty? (:senators app))
        (dom/div nil "")
        (dom/div
         nil
         (dom/h2 #js {:className "legislator-heading"} "Senators")
         (dom/table 
          #js {:className "table table-striped senatorList"}
          (dom/thead nil
                     (dom/tr nil
                             (dom/th nil "Name")
                             (dom/th nil "Party")
                             (dom/th nil "State")
                             (dom/th nil "Phone")))
          (dom/tbody nil
                     (into-array
                      (map #(om/build senator app
                                      {:path [:senators %]
                                       :key :bioguide_id
                                       :opts {:app app}})
                           (range (count (:senators app))))))))))))

(defn representative
  [{:keys [last_name first_name district party state phone bioguide_id]} 
   opts]
  (reify 
    om/IRender
    (render [a owner]
      (dom/tr #js {:className "representative"}
              (dom/td nil 
                      (dom/a #js {:href (str "/profile/" bioguide_id )
                                  :className "btn btn-success"}
                             (str first_name " " last_name)))
              (dom/td nil party)
              (dom/td nil state)
              (dom/td nil district)
              (dom/td 
               nil (om/build call-button 
                             {:phone phone :bioguide_id bioguide_id}))))))

(defn representative-list [app opts]
  (reify
    om/IRender
    (render [_ owner]
      (if (empty? (:representatives app))
        (dom/div nil "")
        (dom/div
         nil
         (dom/h2 #js {:className "legislator-heading"}  "Representatives")
         (dom/table 
          #js {:className "table table-striped representativesList"}
          (dom/thead nil
                     (dom/tr nil
                             (dom/th nil "Name")
                             (dom/th nil "Party")
                             (dom/th nil "State")
                             (dom/th nil "District")
                             (dom/th nil "Phone")))
          (dom/tbody nil
                     (into-array
                      (map #(om/build representative app
                                      {:path [:representatives %]
                                       :key :bioguide_id})
                           (-> app :representatives count range))))))))))

(defn legislator-box [app opts]
  (reify
    om/IRender
    (render [_ owner]
      (dom/div
       #js {:className "row legislatorsBox"}
       (dom/div #js {:className "col-lg-6"}
                (om/build senator-list app))
       (dom/div #js {:className "col-lg-6"}
                (om/build representative-list app))))))
